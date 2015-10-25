package data;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/*
 * 
 * Its a super class for all kinds of doc,tweets and other form of data
 * 
 */

public class Element implements Iterable<String>{

	private Map<String,Token> TokenMap = null;
	public int MaxFreq = 0;
	int    Id   = -1;
	
	public Element(int id){
		TokenMap = new TreeMap <String,Token>();
		Id= id;
	}
	
	public int getId(){
		return Id;
	}
	
	public void setId(int id){
		Id = id;
	}

	/* to add a token/word*/
	public void addToken(String str){

		if(TokenMap.containsKey(str)){     
			int Freq = TokenMap.get(str).incrementFreq();
			MaxFreq = Freq > MaxFreq ? Freq :MaxFreq;
		}
		else{

			Token nToken = new Token(str);
			nToken.incrementFreq();
			TokenMap.put(str, nToken);
			MaxFreq = 1 > MaxFreq ? 1 :MaxFreq;
		}

	}
	
	/* to add a token/word and word*/
	public void addToken(String str,int f){
		if(TokenMap.containsKey(str)){     
			
			int Freq = TokenMap.get(str).getFreq()+f;
			TokenMap.get(str).setFreq(Freq);
			MaxFreq = Freq > MaxFreq ? Freq :MaxFreq;
		}
		else{

			Token nToken = new Token(str);
			nToken.setFreq(f);
			TokenMap.put(str, nToken);
			MaxFreq = f > MaxFreq ? f :MaxFreq;
		}
		
	}
	
	
	public double getTF(String word){
		
		if(TokenMap.containsKey(word)){
			return TokenMap.get(word).getTF();
		}
		
		return 0.0;
		
	}
	
	/* get number of words in element*/
	public int getSize(){
		return TokenMap.size();
	}
	
	/* method to calc the termfrequency */
	public void calcTF(){
		
		Set<String> keys = TokenMap.keySet();
		
		for(String key:keys){
			
			Token curToken = TokenMap.get(key);
			
			curToken.setTF((double)curToken.getFreq()/(double)MaxFreq);
			
		}
		
	}

   public boolean containWord(String word){
	return TokenMap.containsKey(word);
	   
   }
	
   public Map<String,Token> getTokenMap(){
	   
	return TokenMap;
	   
   }
   
   public String toXMLString(){
	   
	   String result ="    <Document id='"+getId()+"'>\n";
	   
	   Set<String> keySet = TokenMap.keySet();
	   for(String key:keySet){
		 result += TokenMap.get(key).toXMLString();
	   }
	   result += "    </Document>\n";
	   
	   
	return result;
	   
   }
   
   public String toString(){
	   //result="Document:{";
	   StringBuilder result = new StringBuilder();
	   result.append("    \"Document\":\n      [");
	   Set<String> keySet = TokenMap.keySet();
	   for(String key:keySet){
		 result.append(TokenMap.get(key).toString());
		 result.append(",");
	   }
	   result.deleteCharAt(result.length()-1);
	   result.append("      ]");
	   return result.toString();
   }

   
   public void SaveElement(PrintWriter PW){

	   PW.write("    \"Document\":\n      [");
	   int i= TokenMap.size();
	   Set<String> keySet = TokenMap.keySet();
	   for(String key:keySet){
		   //PW.write(TokenMap.get(key).toString());
		   TokenMap.get(key).SaveToken(PW);
		   --i;
		   if(i !=0)
		   PW.write(",");
		   
	   }
	   //result.deleteCharAt(result.length()-1);
	   PW.write("      ]");
	   PW.flush();
	  // return result.toString();
   }
   
@Override
public Iterator<String> iterator() {
	// TODO Auto-generated method stub
	return TokenMap.keySet().iterator();
}
	
}
