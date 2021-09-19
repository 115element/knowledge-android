package com.example.knowledge_android.comparator.util2;


import java.sql.*;
import java.util.*;
/**
 * @author Administrator
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class DBAccess {

	private static Connection con = null;
    private Properties prop = null;
	public DBAccess(Properties prop) {
        this.prop = prop;
    }

	public synchronized Connection getConnection() {
		try {
			if (con == null || con.isClosed()) {
                con = null;
                
				String driver = prop.getProperty("ServerDatabaseDriver", "com.mysql.jdbc.Driver");
				String db = prop.getProperty("ServerDatabaseURL", "jdbc:mysql://localhost/cake");
				String user = prop.getProperty("ServerDatabaseUser", "root");
				String password = prop.getProperty("ServerDatabaseUserPassword", "");
                
                Properties dbProp = new Properties();
				dbProp.put("user", user);
				dbProp.put("password", password);
				dbProp.put("useUnicode", "TRUE");
				//dbProp.put("characterEncoding", CreamToolkit.getEncoding());
				dbProp.put("autoReconnect", "true");

				Class.forName(driver);
				con = DriverManager.getConnection(db, dbProp);

			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

    public static void freeConnection() {
        try {
            con.close();
            con = null;
        } catch (Exception e) {
            con = null;
        }         
    } 

}
