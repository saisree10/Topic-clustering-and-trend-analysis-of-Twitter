package data;

import java.io.PrintWriter;

public class Token implements Comparable<Token>{

	private String TokenStr = null;
	private int    Freq     = 0;
	private double  TermFreq = 0;
	
	public Token(String str){
		this(str,1);
	}
	
	public Token(String str,int Freq){
		TokenStr = str;
		this.Freq = Freq;
	}
	
	public String getTokenString(){
		
		return TokenStr;
	}
	
	public int getFreq(){
		return Freq;
	}
	
	public void setFreq(int f){
		Freq = f;
	}
	
	public double getTF(){
		return TermFreq;
	}
	
	public void setTF(double f){
		TermFreq = f;
	}
	
	public int incrementFreq(){
		Freq += 1;
		return Freq;
	}
	
	public boolean equals(Token other){
		if (!(other instanceof Token)) {
		      return false;
		    }
		    return getTokenString().equals(other.getTokenString());
	}
	public int hashcode(){
		return TokenStr.hashCode();
	}
	public String toXMLString(){
		String result="      <Word>\n";
		result     += "        <Text>"+getTokenString()+"</Text>\n";
		result     += "        <Frequency>"+getFreq()+"</Frequency>\n";
		result     += "      </Word>\n";
		return result;
	}
	
	public String toString(){
		String result="\n        {";
		result += "          \"Word\":\""+getTokenString()+"\",\n";
		result += "          \"Frequency\":\""+getFreq()+"\"\n" ;
		result += "        }";
		return result;
	}
	
	public void SaveToken(PrintWriter PW){
		PW.write("\n        {");
		PW.write("          \"Word\":\""+getTokenString()+"\",\n");
		PW.write("          \"Frequency\":\""+getFreq()+"\"\n" );
		PW.write( "        }");
		PW.flush();
	}
	@Override public int compareTo(Token other){

		return getTokenString().compareTo(other.getTokenString());
		
	}
	
	
}
