package data;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import data.Element;

/*
 * It is class to store information of one cluster
 */
public class Cluster extends Element {

	private ArrayList<Element>  ElementList = null;
	
	public Cluster(int id){
		super(id);
		ElementList = new ArrayList<Element>();
	}
	
	public void addElement(Element e){
		
		Map<String,Token> STMap = e.getTokenMap();
		Set<String> keyset = STMap.keySet();
		for(String key:keyset){
			
			Token t = STMap.get(key);
			addToken(t.getTokenString(),t.getFreq());
		}
		
		ElementList.add(e);
		
		calcTF();
	
	}
	
	public void addElementList(ArrayList<Element> arr){
		
		for (Element e : arr) {	
			ElementList.add(e);
			addElement(e);
		}
	}
	
	public ArrayList<Element> getElementList(){
		return ElementList;
		
	}
	public void SaveElement(PrintWriter PW){
		super.SaveElement(PW);
		int size = ElementList.size();
		PW.write(",    \"Elements\":\n      [\n");
		for (Element e : ElementList) {	
			//Class<? extends Element> s = e.getClass();
			((Tweet)e).SaveElement(PW);
			--size;
			if(size != 0)
			  PW.write(",\n");
		}
		PW.write("      ]");
		PW.flush();
		
	}
	
	 public int getClusterSize(){
		return ElementList.size();
	}
}
