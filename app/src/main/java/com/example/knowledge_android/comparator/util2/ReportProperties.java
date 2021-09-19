/*
 * Created on 2003-8-5
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.example.knowledge_android.comparator.util2;

import java.util.*;
import java.io.*;

/**
 * @author Administrator
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ReportProperties extends Properties {

    
    public static ReportProperties getInstance() {
    
      return null;
    }
    
    
	public ReportProperties(String fileName) {
//		super();
        initProperties(fileName);
	}
    
    private void initProperties(String fileName) {
        try {
            File propFile = new File(fileName);
            if (propFile.exists()) {
                load(new FileInputStream(propFile));
                
            } else {
                put("Locale", "zh_CN");
                put("ServerDatabaseDriver", "com.mysql.jdbc.Driver");
                put("ServerDatabaseURL", "jdbc:mysql://localhost/cake");
                put("ServerDatabaseUser", "root");
                put("ServerDatabaseUserPassword", "");
                put("MysqlBase","c:\\mysql\\bin");
                put("LocalUploadDir","c:\\export\\upload\\");
                FileOutputStream outProp = new FileOutputStream(propFile);
                this.store(outProp, "eReport Configuration");
                outProp.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
