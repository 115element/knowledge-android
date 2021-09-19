package com.example.knowledge_android.comparator.sign;



import com.example.knowledge_android.comparator.sign.codec.Base64;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class SignUtil {

    /**
     * ʹ��1024˽Կǩ��
     *
     * @param content    ��ǩ������
     * @param privateKey 1024˽Կ
     * @return ǩ��
     */
    public static String rsaSign(String content, String privateKey) throws Exception {
        content = new String(Base64.encodeBase64(content.getBytes("utf-8")), "utf-8");

        PrivateKey priKey = getPrivateKeyFromPKCS8(new ByteArrayInputStream(privateKey.getBytes()));

        java.security.Signature signature = java.security.Signature.getInstance("SHA1WithRSA");

        signature.initSign(priKey);

        signature.update(content.getBytes("utf-8"));

        byte[] signed = signature.sign();

        return new String(Base64.encodeBase64(signed));
    }

    public static String rsa256Sign(String content, String privateKey, String charset) throws Exception {
        content = new String(Base64.encodeBase64(content.getBytes(charset)), charset);

        PrivateKey priKey = getPrivateKeyFromPKCS8(new ByteArrayInputStream(privateKey.getBytes()));

        java.security.Signature signature = java.security.Signature.getInstance("SHA256WithRSA");

        signature.initSign(priKey);

        signature.update(content.getBytes("utf-8"));

        byte[] signed = signature.sign();

        return new String(Base64.encodeBase64(signed));
    }

    /**
     * ��֤RSAǩ��
     *
     * @param content   ԭ��
     * @param sign      ǩ������
     * @param publicKey ��ǩ��Կ
     * @return true����ǩ�ɹ���false����ǩʧ��
     */
    public static boolean rsaCheck(String content, String sign, String publicKey) throws Exception {
        boolean result;

        content = new String(Base64.encodeBase64(content.getBytes("utf-8")), "utf-8");

        PublicKey pubKey = getPublicKeyFromX509(new ByteArrayInputStream(publicKey.getBytes()));

        java.security.Signature signature = java.security.Signature.getInstance("SHA1WithRSA");

        signature.initVerify(pubKey);

        signature.update(content.getBytes("utf-8"));

        result = signature.verify(Base64.decodeBase64(sign.getBytes()));
        return result;
    }

    public static boolean rsa256Check(String content, String sign, String publicKey) throws Exception {
        boolean result;

        content = new String(Base64.encodeBase64(content.getBytes("utf-8")), "utf-8");

        PublicKey pubKey = getPublicKeyFromX509(new ByteArrayInputStream(publicKey.getBytes()));

        java.security.Signature signature = java.security.Signature.getInstance("SHA256WithRSA");

        signature.initVerify(pubKey);

        signature.update(content.getBytes("utf-8"));

        result = signature.verify(Base64.decodeBase64(sign.getBytes()));
        return result;
    }

    /**
     * ��ȡ˽Կ
     *
     * @param ins inputstream
     * @return private key
     */
    private static PrivateKey getPrivateKeyFromPKCS8(InputStream ins) throws Exception {
        if (ins == null) {
            return null;
        }

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        byte[] encodedKey = StreamUtil.readText(ins).getBytes();

        encodedKey = Base64.decodeBase64(encodedKey);

        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
    }

    /**
     * ��ȡ��Կ
     *
     * @param ins inputstream
     * @return public key
     */
    private static PublicKey getPublicKeyFromX509(InputStream ins) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        StringWriter writer = new StringWriter();
        StreamUtil.io(new InputStreamReader(ins), writer);

        byte[] encodedKey = writer.toString().getBytes();

        encodedKey = Base64.decodeBase64(encodedKey);

        return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
    }

}
