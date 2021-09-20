package com.example.knowledge_android.knowledge;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/**
 * 请求工具类
 *
 * @author yuanxiaojun
 *
 */
public class RequestUtils {
	private static SSLContext sslContext;
	public static String KEY_STORE_FILE = "testISSUE.pfx";
	public static String KEY_STORE_PASS = "123456";

	/**
	 * 进入test.cer所在目录，执行如下操作（注意配置环境变量），生成testclient.truststore文件:
	 * keytool -import -file fapiao.com2017.cer -storepass 123456 -keystore eInvoice.truststore
	 * 然后输入“y”，回车 命令说明： keytool
	 * JDK提供的证书生成工具，所有参数的用法参见keytool –help -import 执行导入 -file test.cer
	 * 要导入的证书，即从ie上导出的证书 -storepass 123456 证书的存取密码 -keystore
	 * testclient.truststore 保存路径及文件名
	 */
	public static String TRUST_STORE_FILE = "eInvoice.truststore";
	public static String TRUST_STORE_PASS = "123456";

	/**
	 * https post请求方式
	 *
	 * @param xml
	 * @param address
	 * @return
	 */
	public static String getHttpConnectResult(String xml, String address) {
		String resultData = "";
		System.out.println("http请求开始，请求地址：" + address);
		OutputStream wr = null;
		HttpsURLConnection conn = null;
		try {
			URL url = new URL(address);
			conn = (HttpsURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(60000);// 设置连接主机的超时时间
			conn.setReadTimeout(60000);// 设置从主机读取数据的超时时间

			// 打开和URL之间的连接
			if (conn instanceof HttpsURLConnection) {
				((HttpsURLConnection) conn).setSSLSocketFactory(getSSLContext().getSocketFactory());
			}
			wr = conn.getOutputStream();
			wr.write(xml.getBytes("utf-8"));
			wr.flush();
			resultData = IOUtils.toString(conn.getInputStream(), "utf-8");
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.out.println("http请求失败！请求地址不正确！请求地址：" + address);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("http请求失败！发生i/o错误，请求地址：" + address);
		} finally {
			try {
				if (wr != null) {
					wr.close();
				}
				if (conn != null) {
					conn.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultData;
	}

	public static SSLContext getSSLContext() {
		long time1 = System.currentTimeMillis();
		if (sslContext == null) {
			try {
				KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
				kmf.init(getkeyStore(), KEY_STORE_PASS.toCharArray());
				KeyManager[] keyManagers = kmf.getKeyManagers();

				TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
				trustManagerFactory.init(getTrustStore());
				TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();

				sslContext = SSLContext.getInstance("TLS");
				sslContext.init(keyManagers, trustManagers, new SecureRandom());
				HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
					public boolean verify(String hostname, SSLSession session) {
						return true;
					}
				});
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (UnrecoverableKeyException e) {
				e.printStackTrace();
			} catch (KeyStoreException e) {
				e.printStackTrace();
			} catch (KeyManagementException e) {
				e.printStackTrace();
			}
		}
		long time2 = System.currentTimeMillis();
		System.out.println("SSLContext 初始化时间：" + (time2 - time1));
		return sslContext;
	}

	public static KeyStore getkeyStore() {
		KeyStore keySotre = null;
		try {
			keySotre = KeyStore.getInstance("PKCS12");
			File file = new File(KEY_STORE_FILE);

			FileInputStream fis = new FileInputStream(file);
			keySotre.load(fis, KEY_STORE_PASS.toCharArray());
			fis.close();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return keySotre;
	}

	public static KeyStore getTrustStore() throws IOException {
		KeyStore trustKeyStore = null;
		FileInputStream fis = null;
		try {
			trustKeyStore = KeyStore.getInstance("JKS");
			fis = new FileInputStream(new File(TRUST_STORE_FILE));
			trustKeyStore.load(fis, TRUST_STORE_PASS.toCharArray());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			fis.close();
		}
		return trustKeyStore;
	}
}
