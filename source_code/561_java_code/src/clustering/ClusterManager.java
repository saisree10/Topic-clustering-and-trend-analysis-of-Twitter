package clustering;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import Utility.statistic;
import data.Cluster;
import data.Element;
import data.Token;

public class ClusterManager {

	private Map<Integer,Element>             clusterMap         = null;
	private Map<String,Set<Integer> >        all_words          = null;
	private double                           cosineThresold     = 0.01; 
	private double                           clustercosineThresold     = 0.75; 
	private boolean                          isOneElementPerCluster = true;
	private boolean                          isReOrganization       = false;

	public ClusterManager(){

		clusterMap         = new HashMap<Integer, Element>();
		all_words          = new TreeMap<String,Set<Integer> >();

	}

	public void clusterIt(Element e){

		// extracts all the words in element e and adds to allword map
		// In order to maintain all words vector
		extractWords(e, all_words);

		//calculating tfIdf for a given vector
		ArrayList<Double> e_tf_idf = statistic.calculateTfIdf(e, all_words);
		Map<Integer, Double> cosineList = new LinkedHashMap<Integer, Double>();

		//check if doc is similar to clusters
		Set<Integer> keyset = clusterMap.keySet();
		for (Integer key : keyset) {

			Element c = clusterMap.get(key);
			ArrayList<Double> c_tf_idf = statistic.calculateTfIdf(c, all_words);
			double cosineSim = statistic.calculateCoSineSimilarity(e_tf_idf, c_tf_idf);

			if (isReOrganization){
				if (cosineSim > clustercosineThresold) {
					cosineList.put(c.getId(), cosineSim);
				}

			}else{
				if (cosineSim > cosineThresold) {
					cosineList.put(c.getId(), cosineSim);
				}
			}
		}

		Cluster cluster = null;
		if (cosineList.isEmpty()) {
			cluster = new Cluster(clusterMap.size());
			clusterMap.put(cluster.getId(), cluster);

		} else {

			if (isOneElementPerCluster) {
				cosineList = MapUtil.sortByValue(cosineList);
				Integer cid = cosineList.keySet().iterator().next();
				cluster=((Cluster) clusterMap.get(cid));
				
			} else {
				Set<Integer> keySet = cosineList.keySet();
				for (Integer key : keySet) {
					cluster = ((Cluster) clusterMap.get(key));
					
				}
			}
		}
		
		if(cluster != null){
			if(isReOrganization){
				cluster.addElementList(((Cluster) e).getElementList());
			}else{
				cluster.addElement(e);
			}
		}
		
	}


	public void clean(){

		Set<Integer> keyset = clusterMap.keySet();
		for (Integer key : keyset) {

			Element e = clusterMap.get(key);
			ArrayList<Double> c_tf_idf = statistic.calculateTfIdf(e, all_words);
			double avg =0;
			for(Double d:c_tf_idf){
				avg+=d;
			}
			
			avg /= c_tf_idf.size();
			
			Iterator<Entry<String, Token>> it = e.getTokenMap().entrySet().iterator();
			int itr = 0;
			while (it.hasNext())
			{
				
				Entry<String, Token> item = it.next();
				if(item.getValue().getTF() < 0.1){
					it.remove();
				}
			}


			if(e.getTokenMap().size() <1){
				System.out.println("Error");
			}

		}

	}

	public void rearganization(){

		isReOrganization = true;
		Map<Integer,Element>             tmpclusterMap         = clusterMap;
	//	Map<Integer,ArrayList<Element> > tmpclusterElementList = clusterElementList;
	//	Map<String,Set<Integer> >        tmpall_words          = all_words;

		clusterMap         = new HashMap<Integer, Element>();
		//clusterElementList = new HashMap<Integer, ArrayList<Element>>();
		all_words          = new TreeMap<String, Set<Integer>>();

		Set<Integer> keySet = tmpclusterMap.keySet();

		for (Integer key : keySet) {

			clusterIt(tmpclusterMap.get(key));

		}

		isReOrganization = false;
	}
	private void extractWords(Element e,Map<String, Set<Integer>> wordsMap){

		Iterator<String> Itr = e.iterator();

		while(Itr.hasNext()){
			String s = Itr.next();
			if(wordsMap.containsKey(s)){
				wordsMap.get(s).add(e.getId());
			}
			else{
				Set<Integer> wSet = new HashSet<Integer>();
				wSet.add(e.getId());
				wordsMap.put(s, wSet);
			}
		}
	}

	public String toXMLString(){


		String result = "<ClusterList>\n";

		Set<Integer> keySet = clusterMap.keySet();

		for(Integer key:keySet){
			result += "  <Cluster id='"+ key +"'>\n";
			result += clusterMap.get(key).toXMLString();
			result += "  </Cluster>\n";
		}
		result += "</ClusterList>";
		return result;

	}



	public String toString(){
		StringBuilder result = new StringBuilder();
		result.append("{\n");

		Set<Integer> keySet = clusterMap.keySet();

		for(Integer key:keySet){
			result.append( "  \"Cluster"+key.intValue()+"\":\n  { \n    \"id\":\""+ key +"\",\n");
			result.append( clusterMap.get(key).toString());
			result.append( "   },");
		}
		result.deleteCharAt(result.length()-1);
		result.append("\n}\n");
		return result.toString(); 
	}

	public void SaveCluster(PrintWriter PW){

		int i = clusterMap.size();
		PW.write("{\n"); // result.append("{\n");

		Set<Integer> keySet = clusterMap.keySet();

		for(Integer key:keySet){
			PW.write( "  \"Cluster"+key.intValue()+"\":\n  { \n    \"id\":\""+ key +"\",\n");
			PW.write("   \"WordCount\":  \""+clusterMap.get(key).getSize()+"\", \n\" TweetCount\":\" "+((Cluster)clusterMap.get(key)).getClusterSize()+"\",\n");
			clusterMap.get(key).SaveElement(PW);
			
			PW.write( "   }");
			--i;
			if(i !=0)
				PW.write( ",");
			PW.flush();
		}

		//result.deleteCharAt(result.length()-1);
		PW.write("\n}\n");
		//return result.toString(); 

	}
}

class MapUtil
{
	public static <K, V extends Comparable<? super V>> Map<K, V> 
	sortByValue( Map<K, V> map )
	{
		List<Map.Entry<K, V>> list =
				new LinkedList<Map.Entry<K, V>>( map.entrySet() );
		Collections.sort( list, new Comparator<Map.Entry<K, V>>()
				{
			public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
			{
				return (o2.getValue()).compareTo( o1.getValue() );
			}
				} );

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list)
		{
			result.put( entry.getKey(), entry.getValue() );
		}
		return result;
	}
}
