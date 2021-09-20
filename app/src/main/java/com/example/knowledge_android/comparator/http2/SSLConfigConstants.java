package com.example.knowledge_android.comparator.http2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SSLConfigConstants {
	public static InputStream TRUSTSTORE_INPUTSTREAM;
	public static InputStream P12_INPUTSTREAM;
	public static String TRUSTSTORE_PASSWORD="qscew22esz123456";
	public static String P12_PASSWORD="qweasdz2334xc123456";
	public static String TRUSTSTORE_TYPE_JKS = "JKS";
	public static String P12_TYPE_PKCS12 = "PKCS12";
	static {
		try {
			//java.net.URL url = SSLConfigConstants.class.getProtectionDomain().getCodeSource().getLocation();
			//TRUSTSTORE_INPUTSTREAM = ClassLoader.getSystemResource("ssl/client.truststore").openStream();
			TRUSTSTORE_INPUTSTREAM = new FileInputStream(new File("ssl/client.truststore"));
			//P12_INPUTSTREAM = ClassLoader.getSystemResource("ssl/luosen.p12").openStream();
			P12_INPUTSTREAM = new FileInputStream(new File("ssl/lsen.p12"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		try {
			System.out.println(ClassLoader.getSystemResource("ssl/lsen.p12").openStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
