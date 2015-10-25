package Utility;

import java.util.regex.Pattern;


public class StringUtil {

	private String Str = null;
	
	public StringUtil(String Str){
		this.Str = Str;
	}
	
	public StringUtil strip(){
		
		Str = Str.replaceAll("\\s+", " ");
		Str = Str.trim();
		
		
		return this;
		
	}
	
	public StringUtil test(){
		
		String p1="(\\s|\\A)(#|@)(\\w+)";
		p1 += "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
		p1 += "(\\b(?:a|able|about|across|after|all|almost|also|am|among|an|and|any|are|as|at|be|because|been|but|by|can|cannot|could|dear|did|do|does|either|else|ever|every|for|from|get|got|had|has|have|he|her|hers|him|his|how|however|i|if|in|into|is|it|its|just|least|let|like|likely|may|me|might|most|must|my|neither|no|nor|not|of|off|often|on|only|or|other|our|own|rather|said|say|says|she|should|since|so|some|than|that|the|their|them|then|there|these|they|this|tis|to|too|twas|us|wants|was|we|were|what|when|where|which|while|who|whom|why|will|with|would|yet|you|your)\\b\\s*)";
		p1 += "([^a-zA-Z\\s]+)";
		
		Str = Str.replace(p1, "");
		return this;
	}
	
	public StringUtil doStemming(){
		
		Stemmer stemmer = new Stemmer();
		
		String[] strorg = Str.split(" ");
		Str = "";
		for(String s:strorg){
			stemmer.add(s.toCharArray(), s.length());
			stemmer.stem();
			Str += stemmer.toString()+" ";
		}
		Str = Str.trim();
		return this;
		
	}
	public StringUtil removeTag(){
		Str = Str.replaceAll("(\\s|\\A)(#|@)(\\w+)","");
		return this;
		
	}
	
	public StringUtil removeURL(){
		
		String Pattern="((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
		Str = Str.replaceAll(Pattern,"");
		return this;
	}
	
	public StringUtil removeStopWords(){
		
		String stopStr = "a|able|about|across|after|all|almost|also|am|among|an|and|any|are|as|at|be|because|been|but|by|can|cannot|could|dear|did|do|does|either|else|ever|every|for|from|get|got|had|has|have|he|her|hers|him|his|how|however|i|if|in|into|is|it|its|just|least|let|like|likely|may|me|might|most|must|my|neither|no|nor|not|of|off|often|on|only|or|other|our|own|rather|said|say|says|she|should|since|so|some|than|that|the|their|them|then|there|these|they|this|tis|to|too|twas|us|wants|was|we|were|what|when|where|which|while|who|whom|why|will|with|would|yet|you|your";
		/*
		for(int i=0; i <stopWordsArray.length-1; ++i){
			stopStr += stopWordsArray[i]+"|";
		}
		
		stopStr += stopWordsArray[stopWordsArray.length-1];
		*/
		Pattern stopWords = Pattern.compile("\\b(?:"+stopStr+")\\b\\s*", Pattern.CASE_INSENSITIVE);
		Str = stopWords.matcher(Str).replaceAll("");
		
		
		return this;
	}
	
	public StringUtil removeNumericValues(){
		
		Str = Str.replaceAll("[0-9\\s]+","");
		return this;
		
	}
	public StringUtil removeSpecialChar(){
		
		Str = Str.replaceAll("[^a-zA-Z\\s]+","");
		//Str = Str.replaceAll("[`~!@#$%^&*()_+[\\]\\\\;\',./{}|:\"<>?]","");
		return this;
	}
	public String toString(){
		return this.strip().Str;
	}
}
