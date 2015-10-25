package Utility;

import java.sql.*;

public class MysqlManager {

	private static MysqlManager instance = null;
	
	private String ConnectionString = null;
	private String UserName         = null;
	private String Password         = null;
	private Connection connection   = null;
    private Statement  statement    = null;
    
	protected MysqlManager() {
		// Exists only to defeat instantiation.
	}
	
	public static MysqlManager getInstance() {
		if(instance == null) {
			instance = new MysqlManager();
		}
		return instance;
	}

	public boolean init(){
		ConnectionString = "jdbc:mysql://localhost/drugdata";
		UserName         = "root";
		Password         = "krishna";
		
		try {
			connection = DriverManager.getConnection(ConnectionString, UserName, Password);
		    statement = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return !(connection == null);
		
	}
	
	public ResultSet executeQuery(String query){

		ResultSet rs = null;
		try {
			rs = statement.executeQuery(query);
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs;
		
	}
}
