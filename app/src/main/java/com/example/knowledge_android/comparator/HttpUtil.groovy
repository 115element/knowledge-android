package com.example.knowledge_android.comparator


class HttpUtil {

    public static String getHttpPost(String urlStr, String data) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置http连接方式
            conn.setRequestMethod("POST");
            //设置超时时间
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            //设置http的返回文本类型
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            conn.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            conn.setRequestProperty("Accept", "*/*");
            conn.connect();
            OutputStream output = conn.getOutputStream();
            output.write(data.getBytes("utf-8"));
            output.flush();
            //接收数据
            InputStream input = conn.getInputStream();
            ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int n = 0;
            while (-1 != (n = input.read(buffer))) {
                byteOutput.write(buffer, 0, n);
            }
            conn.disconnect();
            return byteOutput.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
