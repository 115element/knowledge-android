package com.example.knowledge_android.comparator;

import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 2009-5-15
 * Time: 11:31:15
 * To change this template use File | Settings | File Templates.
 */
public class Hex {

    private static HexTranslator encoder = new HexTranslator();

    public static byte[] encode(byte[] array) {
        return encode(array, 0, array.length);
    }

    public static byte[] encode(byte[] array,
                                int off,
                                int length) {
        byte[] enc = new byte[length * 2];

        encoder.encode(array, off, length, enc, 0);

        return enc;
    }

    public static byte[] decode(String string) {
        byte[] bytes = new byte[string.length() / 2];
        String buf = string.toLowerCase();

        for (int i = 0; i < buf.length(); i += 2) {
            char left = buf.charAt(i);
            char right = buf.charAt(i + 1);
            int index = i / 2;

            if (left < 'a') {
                bytes[index] = (byte) ((left - '0') << 4);
            } else {
                bytes[index] = (byte) ((left - 'a' + 10) << 4);
            }
            if (right < 'a') {
                bytes[index] += (byte) (right - '0');
            } else {
                bytes[index] += (byte) (right - 'a' + 10);
            }
        }

        return bytes;
    }

    public static byte[] decode(byte[] array) {
        byte[] bytes = new byte[array.length / 2];

        encoder.decode(array, 0, array.length, bytes, 0);

        return bytes;
    }

    public static String getRandom(int length){
        String str = "0123456789ABCDEF";
        StringBuffer sb = new StringBuffer();
        Random random = new Random(System.currentTimeMillis());
        int tmp = 0;
        for (int i=0;i<length;i++) {
            tmp = random.nextInt(15);
            sb.append(str.substring(tmp,tmp+1));
        }
        return sb.toString();
    }
}
