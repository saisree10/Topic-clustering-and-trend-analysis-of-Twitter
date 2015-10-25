package Utility;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import data.Element;

public class statistic {
	
	public statistic(){
		
	}
	
	public static Map<Integer, ArrayList<Double>> calculateTfIdf(Map<Integer, Element> docMap,
			ArrayList<String> Terms) {

		int totalDocs = docMap.size();
		int totalTerms = Terms.size();
		Map<Integer, ArrayList<Double>> docVecMap = new TreeMap<Integer, ArrayList<Double>>();
		ArrayList<Double> idf = new ArrayList<Double>(totalTerms);

		Set<Integer> keySet = docMap.keySet();
		System.out.println("test1 "+totalDocs+" "+totalTerms);
		for (int term = 0; term < totalTerms; ++term) {
			int count = 0;
			for (Integer k : keySet) {
				Element curElement = docMap.get(k);
				if(curElement.containWord(Terms.get(term))){
					count+=1;
				}

			}
			
			idf.add(term, Math.log((double)totalDocs/(double)count));
			
		}
		System.out.println("test1");
		for (Integer k : keySet) {
		    ArrayList<Double> curVec = new ArrayList<Double>();
		    
			Element curElement = docMap.get(k);
			for (int term = 0; term < totalTerms; ++term) {
				curVec.add(term, idf.get(term)*curElement.getTF(Terms.get(term)));
			}
			
			docVecMap.put(k, curVec);
		}
		System.out.println("test1");
		return docVecMap;
	}
	
	public static ArrayList<Double> calculateTfIdf(Element e,Map<String,Set<Integer> > all_words){
		ArrayList<Double> e_tf_idf = new ArrayList<Double>(all_words.size());
		int count = 0;
		int Total_Docs = all_words.size();
		
		for(String w:all_words.keySet()){
			
			double tf  = e.getTF(w);
			double idf = Math.log((double)Total_Docs/(double)all_words.get(w).size());
			e_tf_idf.add(count, tf*idf);
			count++;
		}
		return e_tf_idf;
	}
	
	public static double calculateCoSineSimilarity(ArrayList<Double> vec1,ArrayList<Double> vec2){
		
		int vec1_size = vec1.size();
		int vec2_size = vec2.size();
		
		double DotProduct  = 0;
		double Vec1Square  = 0;
		double Vec2Square = 0;
		
	    for(int i=0; i < vec1_size; ++i){
	    	
	    	DotProduct += (vec1.get(i)*vec2.get(i));
	    	Vec1Square += (vec1.get(i)*vec1.get(i));
	    	Vec2Square += (vec2.get(i)*vec2.get(i));
	    }
		
	    
		return DotProduct/((Math.sqrt(Vec1Square) * Math.sqrt(Vec2Square)));
		
	}

	
}
