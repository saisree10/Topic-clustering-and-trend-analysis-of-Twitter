package data;

import java.io.PrintWriter;

public class Tweet extends Element {

	String TweetText = null;

	public Tweet(String text,int Id){
		super(Id);
		TweetText = text;
	}
	
	public String getText(){
		return TweetText;
	}
	
	public void setText(String text){
		TweetText = text;
	}
	
	 @Override public void SaveElement(PrintWriter PW){
		// super.SaveElement(PW);
		PW.write("               {");
		PW.write(" \"id\": \""+ getId()+"\",\n");
	    PW.write("                 \"Tweet\": \""+TweetText.replace("\"", "")+"\"\n");
	    PW.write("               }");
	 }

}
