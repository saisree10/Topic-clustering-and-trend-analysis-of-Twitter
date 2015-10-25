package classification;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import clustering.ClusterManager;
import Utility.MysqlManager;
import Utility.StringUtil;
import data.*;

public class classificationMain {

	private Map<Integer,Element>            tweet_map        = null; 
	private ClusterManager clusterManager = null;
	
	public classificationMain() {
		clusterManager = new ClusterManager();
	}
	

	public void readTweets(){
		int progress = 0;
		// MysqlManager is to establish data base connection
		MysqlManager sqlMan = MysqlManager.getInstance();
		if(sqlMan.init()){
		ResultSet rs = sqlMan.executeQuery("SELECT TweetId,TweetText,WEEK(CreationDate) FROM drugdata.tweet LIMIT 10");
		
		try {
			long startTime = System.currentTimeMillis();
			long elapsedTime = System.currentTimeMillis() - startTime;
			//Get individusl tweet 
			while(rs.next()){
				progress += 1;
				if(progress%1000 == 0){
					elapsedTime = System.currentTimeMillis() - startTime;
					System.out.println("Execution time(in Sec) ::"+elapsedTime*0.001);
					System.out.println("Prcessing tweet:"+progress);
					startTime = System.currentTimeMillis();
				}
				//Preprocess the tweet by removing Tags,URL,stopwords
				StringUtil su = new StringUtil(rs.getString("TweetText"));
				String tweet_text= su.removeTag().removeURL().removeStopWords().removeSpecialChar().toString().toLowerCase();
				//coverts to  words array
				String[] tweet_words = tweet_text.split(" ");
				//check if tweet is proper tweet
				if(tweet_words[0].isEmpty() ){
					continue;
				}

				Tweet tweet = new Tweet(rs.getString("TweetText"),rs.getInt("TweetId"));
				for(String tw:tweet_words){	

					if(tw.length() <= 2){
						continue;
					}

					tweet.addToken(tw);

				}
				if(tweet.getTokenMap().size() == 0) continue;
				tweet.calcTF();
  //give the tweet to cluster manager to cluster it
				
				clusterManager.clusterIt(tweet);


			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}else{
			System.out.println("init failed");
		}
	
		//saveCluster("cluster.json");
		clusterManager.clean();
		clusterManager.rearganization();
		clusterManager.clean();
		saveCluster("test.json");
		
	}
	
	/*
	 * this function is to save the cluster information to JSON
	 */
	public void saveCluster(String name){ 
		
		PrintWriter writer=null;
		try {
			writer = new PrintWriter(name, "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clusterManager.SaveCluster(writer);
		//writer.write(clusterManager.toString());
		writer.close();
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		classificationMain cm = new classificationMain();
		//cm.testStringUtil();
		cm.readTweets();
		

	}

	
	
}
