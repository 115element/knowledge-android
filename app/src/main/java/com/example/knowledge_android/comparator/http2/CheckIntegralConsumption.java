package com.example.knowledge_android.comparator.http2;


import com.alibaba.fastjson.JSONObject;

public class CheckIntegralConsumption {
	public static void checkIntegralConsump() {
		try {
			JSONObject checkIntegralConsumption = new JSONObject();
			checkIntegralConsumption.put("businessId", "797d849c69c361");
			checkIntegralConsumption.put("storesId", "A001");
			checkIntegralConsumption.put("cardNumber", "15921213465");
			checkIntegralConsumption.put("amount", "1.50");
			checkIntegralConsumption.put("orderNumber", "401201607250945003");
			checkIntegralConsumption.put("serialNumber", "201607250945003");
			checkIntegralConsumption.put("orderDate", "2016-07-25 09:45:00");
			checkIntegralConsumption.put("verificationCode", "572443315921213465");
//			checkIntegralConsumption.put("payPassword", "0");

			String url = "http://localhost:8080/pay/services/rest/checkIntegralConsumption";//测试环境 //http://localhost:8080/pay/services/rest/checkIntegralConsumption";
			//String url = "https://pay.reach-life.com:8443/api/services/rest/checkIntegralConsumption";
			HTTPSClientUtils.sendPost(checkIntegralConsumption, url);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		checkIntegralConsump();
	}
}
