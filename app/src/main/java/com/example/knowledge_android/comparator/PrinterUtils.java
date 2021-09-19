package com.example.knowledge_android.comparator;
/*104年03-04月
UL-20275903
2015/04/22 09:54:12


隨機碼 0455
總計 50
賣方 97380574

10404UL202759030455
UL202759031040422045500000000000000320000000097380574LCyi/QyEIfKcluYC0Lnb4A==:**********:5:5:1      
**:商品0002:1:1000:商品0003:1:1000:商品0004:1:1000:商品0005:1:1000:商品0006:1:1000                  
序號 000622
退貨請持電子發票證明聯正本
0

*/


///////////////////


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author eric
 */
public class PrinterUtils {
    public static final byte ESC = 0x1B;
    public static final byte FS = 0x1C;
    public static final byte GS = 0x1D;
    public static final byte CR = 0x0D; // CR \r 0x0d.
    public static final byte LF = 0x0A; // LF \n 0x0a.
    public static final byte[] HL = "--------------------------------".getBytes();
    public static final int QRCODE_LENGTH = 134;

    private static Map<String, String> printerErrorMessages;

    public static byte[] createDefaultInvoice(String[] fields) throws IOException {
        byte[] result;
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();

            //Select justification
            //ESC a n(0:left, 1:center, 2:right)
            out.write(ESC);
            out.write(97);
            out.write(1);

            //Print bitmap
            out.write(FS);
            out.write(112);
            out.write(1);
            out.write(0);

            //outputStream.write(new byte[] { 27, 51, 200 });

            //Select character size
            //GS ! n
            out.write(GS);
            out.write(33);
            out.write(0x11);

            //Set line spacing
            //ESC 3 n
            out.write(ESC);
            out.write(51);
            out.write(20);

            out.write(LF);

            //Select default line spacing
            //ESC 2
            out.write(new byte[] { 27, 50 });
            //

            out.write(toBytes(fields[0]));
            out.write(LF);

            //Set line spacing
            //ESC 3 n
            out.write(ESC);
            out.write(51);
            out.write(20);

            out.write(LF);

            //Select default line spacing
            //ESC 2
            out.write(new byte[] { 27, 50 });

            out.write(toBytes(fields[1]));
            out.write(LF);

            //Set line spacing
            //ESC 3 n
            out.write(ESC);
            out.write(51);
            out.write(20);

            out.write(LF);

            //Select print mode(s)
            //ESC ! n
            out.write(ESC);
            out.write(33);
            out.write(0x38);

            //Select default line spacing
            //ESC 2
            out.write(new byte[] { 27, 50 });

            out.write(toBytes(fields[2]));
            out.write(LF);

            //Select character size
            //GS ! n
            out.write(GS);
            out.write(33);
            out.write(0x00);

            //Set line spacing
            //ESC 3 n
            out.write(ESC);
            out.write(51);
            out.write(20);

            out.write(LF);

            //Select default line spacing
            //ESC 2
            out.write(new byte[] { 27, 50 });

            //Select print mode(s)
            //ESC ! n
            out.write(ESC);
            out.write(33);
            out.write(0x00);

            //Select justification
            //ESC a n(0:left, 1:center, 2:right)
            out.write(ESC);
            out.write(97);
            out.write(0);

            out.write(toBytes(fields[3]));
            out.write(' ');
            out.write(toBytes(fields[4]));
            out.write(' ');
            out.write(toBytes(fields[5]));
            out.write(LF);


            byte[] data = toBytes(fields[6]);
            out.write(data);
            for (int i = 0; i < 17 - data.length; i++) {
                out.write(' ');
            }

            out.write(toBytes(fields[7]));
            out.write(LF);

            data = toBytes(fields[8]);
            out.write(data);
            for (int i = 0; i < 17 - data.length; i++) {
                out.write(' ');
            }

            out.write(toBytes(fields[9]));
            out.write(LF);

            //Set line spacing
            //ESC 3 n
            out.write(ESC);
            out.write(51);
            out.write(30);

            out.write(LF);

            //Select default line spacing
            //ESC 2
            out.write(new byte[] { 27, 50 });

            out.write(createCode39(fields[10]));

            //select page mode
            //ESC L
            out.write(ESC);
            out.write(76);

            int x = 0;
            int y = 0;
            int width = 416;
            int height = 360;
            //set print area in page mode
            //ESC W xL xH yL yH dxL dxH dyL dyH
            out.write(ESC);
            out.write(87);
            out.write(x % 256);
            out.write(x / 256);
            out.write(y % 256);
            out.write(y / 256);
            out.write(width % 256);
            out.write(width / 256);
            out.write(height % 256);
            out.write(height / 256);

            x = 30;
            y = 0;
            //移動x到列印qr code位置
            out.write(ESC);
            out.write(36);
            out.write(x % 256);
            out.write(x / 256);
            //移動y到列印qr code位置
            out.write(GS);
            out.write(36);
            out.write(y % 256);
            out.write(y / 256);

            out.write(createQRCode(fields[11]));

            x = 220;
            y = 0;
            //移動x到列印qr code位置
            out.write(ESC);
            out.write(36);
            out.write(x % 256);
            out.write(x / 256);
            //移動y到列印qr code位置
            out.write(GS);
            out.write(36);
            out.write(y % 256);
            out.write(y / 256);

            out.write(createQRCode(fields[12]));

            //print data in page mode
            //ESC FF
            //in.add(new byte[] { 27, 12 });
            //Print and return to standard mode
            out.write(12);

            out.write(toBytes(fields[13]));
            out.write(LF);

            //Select justification
            //ESC a n(0:left, 1:center, 2:right)
            out.write(ESC);
            out.write(97);
            out.write(1);

            out.write(toBytes(fields[14]));
            out.write(LF);

            //Select justification
            //ESC a n(0:left, 1:center, 2:right)
            out.write(ESC);
            out.write(97);
            out.write(0);

            int blankLines = 0;
            if (fields.length >= 16)
                blankLines = Integer.parseInt(fields[15]);

            if (blankLines == 0)
                blankLines = 3;

            //Print and feed n lines
            //ESC d n
            out.write(ESC);
            out.write(100);
            out.write(blankLines);

            //Cut paper
            out.write(ESC);
            out.write('m');

            result = out.toByteArray();
        } finally {
            if (out != null)
                out.close();
        }
        return result;
    }

    public static byte[] createDTP220Invoice(String[] fields) throws IOException {
        byte[] result;
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();

            //Select justification
            //ESC a n(0:left, 1:center, 2:right)
            out.write(ESC);
            out.write(97);
            out.write(1);

            //Print bitmap
            out.write(FS);
            out.write(112);
            out.write(1);
            out.write(0);

            //outputStream.write(new byte[] { 27, 51, 200 });

            //Select character size
            //GS ! n
            out.write(GS);
            out.write(33);
            out.write(0x11);

            //Select print mode(s)
            //ESC ! n
            out.write(ESC);
            out.write(33);
            out.write(0x38);

            //Set line spacing
            //ESC 3 n
            out.write(ESC);
            out.write(51);
            out.write(10);

            out.write(LF);

            //Select default line spacing
            //ESC 2
            out.write(new byte[] { 27, 50 });
            //

            out.write(toBytes(fields[0]));
            out.write(LF);

            //Set line spacing
            //ESC 3 n
            out.write(ESC);
            out.write(51);
            out.write(10);

            out.write(LF);

            //Select default line spacing
            //ESC 2
            out.write(new byte[] { 27, 50 });

            out.write(toBytes(fields[1]));
            out.write(LF);

            //Set line spacing
            //ESC 3 n
            out.write(ESC);
            out.write(51);
            out.write(10);

            out.write(LF);

            //Select default line spacing
            //ESC 2
            out.write(new byte[] { 27, 50 });


            //Select character size
            //GS ! n
            out.write(GS);
            out.write(33);
            out.write(0x21);

            out.write(toBytes(fields[2]));
            out.write(LF);

            //Select character size
            //GS ! n
            out.write(GS);
            out.write(33);
            out.write(0x00);

            //Set line spacing
            //ESC 3 n
            out.write(ESC);
            out.write(51);
            out.write(10);

            out.write(LF);

            //Select default line spacing
            //ESC 2
            out.write(new byte[] { 27, 50 });

            //Select print mode(s)
            //ESC ! n
            out.write(ESC);
            out.write(33);
            out.write(0x00);

            //Select justification
            //ESC a n(0:left, 1:center, 2:right)
            out.write(ESC);
            out.write(97);
            out.write(0);

            out.write(toBytes(fields[3]));
            out.write(' ');
            out.write(toBytes(fields[4]));
            out.write(' ');
            out.write(toBytes(fields[5]));
            out.write(LF);


            byte[] data = toBytes(fields[6]);
            out.write(data);
            for (int i = 0; i < 17 - data.length; i++) {
                out.write(' ');
            }

            out.write(toBytes(fields[7]));
            out.write(LF);

            data = toBytes(fields[8]);
            out.write(data);
            for (int i = 0; i < 17 - data.length; i++) {
                out.write(' ');
            }

            out.write(toBytes(fields[9]));
            out.write(LF);

            //Set line spacing
            //ESC 3 n
            out.write(ESC);
            out.write(51);
            out.write(10);

            out.write(LF);

            //Select default line spacing
            //ESC 2
            out.write(new byte[] { 27, 50 });

            out.write(createCode39(fields[10]));

            //Set line spacing
            //ESC 3 n
            out.write(ESC);
            out.write(51);
            out.write(30);

            out.write(LF);

            //Select default line spacing
            //ESC 2
            out.write(new byte[] { 27, 50 });

            //select page mode
            //ESC L
            out.write(ESC);
            out.write(76);

            int x = 0;
            int y = 0;
            int width = 416;
            int height = 340;
            //set print area in page mode
            //ESC W xL xH yL yH dxL dxH dyL dyH
            out.write(ESC);
            out.write(87);
            out.write(x % 256);
            out.write(x / 256);
            out.write(y % 256);
            out.write(y / 256);
            out.write(width % 256);
            out.write(width / 256);
            out.write(height % 256);
            out.write(height / 256);

            x = 20;
            y = 20;
            //移動x到列印qr code位置
            out.write(ESC);
            out.write(36);
            out.write(x % 256);
            out.write(x / 256);
            //移動y到列印qr code位置
            out.write(GS);
            out.write(36);
            out.write(y % 256);
            out.write(y / 256);

            out.write(createQRCode(fields[11]));

            x = 260;
            y = 20;
            //移動x到列印qr code位置
            out.write(ESC);
            out.write(36);
            out.write(x % 256);
            out.write(x / 256);
            //移動y到列印qr code位置
            out.write(GS);
            out.write(36);
            out.write(y % 256);
            out.write(y / 256);

            out.write(createQRCode(fields[12]));

            //print data in page mode
            //ESC FF
            //in.add(new byte[] { 27, 12 });
            //Print and return to standard mode
            out.write(12);

            out.write(toBytes(fields[13]));
            out.write(LF);

            //Select justification
            //ESC a n(0:left, 1:center, 2:right)
            out.write(ESC);
            out.write(97);
            out.write(1);

            out.write(toBytes(fields[14]));
            out.write(LF);

            //Select justification
            //ESC a n(0:left, 1:center, 2:right)
            out.write(ESC);
            out.write(97);
            out.write(0);

            int blankLines = 0;
            if (fields.length >= 16)
                blankLines = Integer.parseInt(fields[15]);

            if (blankLines == 0)
                blankLines = 4;

            //Print and feed n lines
            //ESC d n
            out.write(ESC);
            out.write(100);
            out.write(blankLines);

            //Cut paper
            out.write(ESC);
            out.write('m');

            result = out.toByteArray();
        } finally {
            if (out != null)
                out.close();
        }
        return result;
    }

    public static byte[] createDTP805Invoice(String[] fields) throws IOException {
        byte[] result;
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();

            //Select justification
            //ESC a n(0:left, 1:center, 2:right)
            out.write(ESC);
            out.write(97);
            out.write(1);

            //Print bitmap
            out.write(FS);
            out.write(112);
            out.write(1);
            out.write(0);

            //outputStream.write(new byte[] { 27, 51, 200 });

            //Select character size
            //GS ! n
            out.write(GS);
            out.write(33);
            out.write(0x11);

            //Set line spacing
            //ESC 3 n
//                out.write(ESC);
//                out.write(51);
//                out.write(20);
//
//                out.write(LF);
//
//                //Select default line spacing
//                //ESC 2
//                out.write(new byte[] { 27, 50 });
            //
            //電子發票證明聯
            out.write(toBytes(fields[0]));
            out.write(LF);

//                //Set line spacing
//                //ESC 3 n
//                out.write(ESC);
//                out.write(51);
//                out.write(20);
//
//                out.write(LF);
//
//                //Select default line spacing
//                //ESC 2
//                out.write(new byte[] { 27, 50 });

            out.write(toBytes(fields[1]));
            out.write(LF);

            //Select print mode(s)
            //ESC ! n
            out.write(ESC);
            out.write(33);
            out.write(0x38);

//                //Set line spacing
//                //ESC 3 n
//                out.write(ESC);
//                out.write(51);
//                out.write(20);
//
//                out.write(LF);
//
//                //Select default line spacing
//                //ESC 2
//                out.write(new byte[] { 27, 50 });

            out.write(toBytes(fields[2]));
            out.write(LF);

            //Select character size
            //GS ! n
            out.write(GS);
            out.write(33);
            out.write(0x00);

//                //Set line spacing
//                //ESC 3 n
//                out.write(ESC);
//                out.write(51);
//                out.write(20);
//
//                out.write(LF);
//
//                //Select default line spacing
//                //ESC 2
//                out.write(new byte[] { 27, 50 });

            //Select print mode(s)
            //ESC ! n
            out.write(ESC);
            out.write(33);
            out.write(0x00);

            //Select justification
            //ESC a n(0:left, 1:center, 2:right)
            out.write(ESC);
            out.write(97);
            out.write(0);

            out.write(toBytes(fields[3]));
            out.write(' ');
            out.write(toBytes(fields[4]));
            out.write(' ');
            out.write(toBytes(fields[5]));
            out.write(LF);


            byte[] data = toBytes(fields[6]);
            out.write(data);
            for (int i = 0; i < 17 - data.length; i++) {
                out.write(' ');
            }

            out.write(toBytes(fields[7]));
            out.write(LF);

            data = toBytes(fields[8]);
            out.write(data);
            for (int i = 0; i < 17 - data.length; i++) {
                out.write(' ');
            }

            out.write(toBytes(fields[9]));
            out.write(LF);

            out.write(createCode39(fields[10]));

            //Set line spacing
            //ESC 3 n
            out.write(ESC);
            out.write(51);
            out.write(50);

            out.write(LF);

            //Select default line spacing
            //ESC 2
            out.write(new byte[] { 27, 50 });


            //select page mode
            //ESC L
            out.write(ESC);
            out.write(76);

            int x = 0;
            int y = 0;
            int width = 416;
            int height = 340;
            //set print area in page mode
            //ESC W xL xH yL yH dxL dxH dyL dyH
            out.write(ESC);
            out.write(87);
            out.write(x % 256);
            out.write(x / 256);
            out.write(y % 256);
            out.write(y / 256);
            out.write(width % 256);
            out.write(width / 256);
            out.write(height % 256);
            out.write(height / 256);

            x = 30;
            y = 0;
            //移動x到列印qr code位置
            out.write(ESC);
            out.write(36);
            out.write(x % 256);
            out.write(x / 256);
            //移動y到列印qr code位置
            out.write(GS);
            out.write(36);
            out.write(y % 256);
            out.write(y / 256);

            out.write(createQRCode(fields[11]));

            x = 220;
            y = 0;
            //移動x到列印qr code位置
            out.write(ESC);
            out.write(36);
            out.write(x % 256);
            out.write(x / 256);
            //移動y到列印qr code位置
            out.write(GS);
            out.write(36);
            out.write(y % 256);
            out.write(y / 256);

            out.write(createQRCode(fields[12]));

            //print data in page mode
            //ESC FF
            //in.add(new byte[] { 27, 12 });
            //Print and return to standard mode
            out.write(12);

            out.write(toBytes(fields[13]));
            out.write(LF);

            //Select justification
            //ESC a n(0:left, 1:center, 2:right)
            out.write(ESC);
            out.write(97);
            out.write(1);

            out.write(toBytes(fields[14]));
            out.write(LF);

            //Select justification
            //ESC a n(0:left, 1:center, 2:right)
            out.write(ESC);
            out.write(97);
            out.write(0);

            int blankLines = 0;
            if (fields.length >= 16)
                blankLines = Integer.parseInt(fields[15]);

            if (blankLines == 0)
                blankLines = 4;

            //Print and feed n lines
            //ESC d n
            out.write(ESC);
            out.write(100);
            out.write(blankLines);

            //Cut paper
            out.write(ESC);
            out.write('m');

            result = out.toByteArray();
        } finally {
            if (out != null)
                out.close();
        }
        return result;
    }

    public static byte[] createCode39(String code) throws IOException {
        byte[] result;
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            //條碼寬度
            //GS w n
            out.write(GS);
            out.write(119);
            out.write(1);
            //條碼高度
            //GS h n
            out.write(GS);
            out.write(104);
            out.write(48);
            //不顯示條碼文字
            //GS H n
            out.write(GS);
            out.write(72);
            out.write(0);
            //置中
            out.write(ESC);
            out.write(97);
            out.write(1);

            byte[] data = toBytes(code);
            //列印條碼
            //GS k E n
            out.write(GS);
            out.write(107);
            out.write(69);
            out.write(data.length);
            out.write(data);

            //還原靠左
            out.write(ESC);
            out.write(97);
            out.write(0);
            result = out.toByteArray();
        } finally {
            if (out != null)
                out.close();
        }
        return result;
    }

    public static byte[] createQRCode(String data) throws IOException {
        byte[] result;
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();

            //selects the model for qr code
            //ESC ( k pL pH cn fn n1 n2
            out.write(GS);
            out.write(40);
            out.write(107);
            out.write(4);
            out.write(0);
            out.write(49);
            out.write(65);
            out.write(50);
            out.write(0);

            //sets the size of qr code to n dots
            //ESC ( k pL pH cn fn n
            out.write(GS);
            out.write(40);
            out.write(107);
            out.write(3);
            out.write(0);
            out.write(49);
            out.write(67);
            out.write(5);

            //selects the error correction level for qr code
            //ESC ( k pL pH cn fn n
            out.write(GS);
            out.write(40);
            out.write(107);
            out.write(3);
            out.write(0);
            out.write(49);
            out.write(69);
            out.write(48);

            //store the data in the symbol storage area
            //ESC ( k pL pH cn fn m d1...dk
            String s = data;
            byte[] qrcode = toBytes(s);
            //限制長度
            while (qrcode.length > QRCODE_LENGTH) {
                s = s.substring(0, s.length() - 1);
                qrcode = toBytes(s);
            }
            //補足長度
            byte[] buf = new byte[QRCODE_LENGTH];
            System.arraycopy(qrcode, 0, buf, 0, qrcode.length);
            if (qrcode.length < buf.length)
                Arrays.fill(buf, qrcode.length, buf.length, (byte) ' ');

            int len = buf.length + 3;
            out.write(GS);
            out.write(40);
            out.write(107);
            out.write(len % 256);
            out.write(len / 256);
            out.write(49);
            out.write(80);
            out.write(48);
            out.write(buf);

            //print the data in the symble storage area
            //ESC ( k pL pH cn fn m
            out.write(GS);
            out.write(40);
            out.write(107);
            out.write(3);
            out.write(0);
            out.write(49);
            out.write(81);
            out.write(48);
            result = out.toByteArray();
        } finally {
            if (out != null)
                out.close();
        }
        return result;
    }

    public static boolean printQRcode(String data,OutputStream out) {

        if (data.startsWith("http://") && data.length() > 100) {
            try {

                if (out == null) {
                    out = new ByteArrayOutputStream();
                }
                //select page mode
                //ESC L
                out.write(ESC);
                out.write(76);
                int x = 0;
                int y = 0;
                int width = 450;
                int height = 450;
                //set print area in page mode
                //ESC W xL xH yL yH dxL dxH dyL dyH
                out.write(ESC);
                out.write(87);
                out.write(x % 256);
                out.write(x / 256);
                out.write(y % 256);
                out.write(y / 256);
                out.write(width % 256);
                out.write(width / 256);
                out.write(height % 256);
                out.write(height / 256);

                x = 80;
                y = 0;
                //移動x到列印qr code位置
                out.write(ESC);
                out.write(36);
                out.write(x % 256);
                out.write(x / 256);
                //移動y到列印qr code位置
                out.write(GS);
                out.write(36);
                out.write(y % 256);
                out.write(y / 256);
//                data = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
                out.write(createQRCodeStar(data));
                out.write(12);
                out.flush();

                //CreamToolkit.logMessage("****" + createQRCode(data) + "******");




            } catch (IOException e) {
                e.printStackTrace();
                //CreamToolkit.logMessage(e);
            }
            return true;
        }
//        if (data != null && data.endsWith("扫描二维码开具发票")) {
//            isQRcodePrint = true;
//        }
        return false;
    }

    public static boolean printQRcodeEpson(String data,OutputStream out) {

        if (data.startsWith("http://") && data.length() > 100) {
            try {

                if (out == null) {
                    out = new ByteArrayOutputStream();
                }
                //select page mode
                //ESC L
                out.write(ESC);
                out.write(76);
                int x = 0;
                int y = 0;
                int width = 450;
                int height = 450;
                //set print area in page mode
                //ESC W xL xH yL yH dxL dxH dyL dyH
                out.write(ESC);
                out.write(87);
                out.write(x % 256);
                out.write(x / 256);
                out.write(y % 256);
                out.write(y / 256);
                out.write(width % 256);
                out.write(width / 256);
                out.write(height % 256);
                out.write(height / 256);

                x = 100;
                y = 0;
                //移動x到列印qr code位置
                out.write(ESC);
                out.write(36);
                out.write(x % 256);
                out.write(x / 256);
                //移動y到列印qr code位置
                out.write(GS);
                out.write(36);
                out.write(y % 256);
                out.write(y / 256);
//                data = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
                out.write(createQRCode(data));
                out.write(12);
                out.flush();

                //CreamToolkit.logMessage("****" + createQRCode(data) + "******");




            } catch (IOException e) {
                e.printStackTrace();
                //CreamToolkit.logMessage(e);
            }
            return true;
        }
//        if (data != null && data.endsWith("扫描二维码开具发票")) {
//            isQRcodePrint = true;
//        }
        return false;
    }

    public static byte[] createQRCodeStar(String data) throws IOException {
        byte[] result;
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();

            //init printer
            /*out.write(ESC);
            out.write(64);*/

            //make center  居中
            /*out.write(ESC);
            out.write(97);
            out.write(1);*/

            //set model
            out.write(ESC);
            out.write(GS);
            out.write(121);
            out.write(83);
            out.write(48);
            out.write(2);

            //Sets mistake correction level
            out.write(ESC);
            out.write(GS);
            out.write(121);
            out.write(83);
            out.write(49);
            out.write(0);

            //Set QR code cell size
            out.write(ESC);
            out.write(GS);
            out.write(121);
            out.write(83);
            out.write(50);
            out.write(5);

            //set data
            String s = data;
            byte[] qrcode = toBytes(s);
            //限制長度
            while (qrcode.length > QRCODE_LENGTH) {
                s = s.substring(0, s.length() - 1);
                qrcode = toBytes(s);
            }
            //補足長度
            byte[] buf = new byte[QRCODE_LENGTH];
            System.arraycopy(qrcode, 0, buf, 0, qrcode.length);
            if (qrcode.length < buf.length)
                Arrays.fill(buf, qrcode.length, buf.length, (byte) ' ');

//            int len = buf.length + 3;
            int len = buf.length;
            out.write(ESC);
            out.write(GS);
            out.write(121);
            out.write(68);
            out.write(49);
            out.write(0);
            out.write(len % 256);
            out.write(len / 256);

            out.write(buf);

            //print the data in the symble storage area
            //ESC ( k pL pH cn fn m
            out.write(ESC);
            out.write(GS);
            out.write(121);
            out.write(80);

            //check
            /*out.write(ESC);
            out.write(GS);
            out.write(121);
            out.write(80);*/

            //make left
            /*out.write(ESC);
            out.write(97);
            out.write(0);*/



            result = out.toByteArray();
        } finally {
            if (out != null)
                out.close();
        }
        return result;
    }

    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        try {
            byte[] arr = createQRCodeStar("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            System.out.println(bytesToHexString(arr));
            /*for (int i = 0; i < arr.length; i++) {
                System.out.println(bytesToHexString(arr));
            }*/

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static byte[] toBytes(String s) {
        try {
//            return s.getBytes("BIG5");
            return s.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    /**
     * V-R7000 device error code convert to V-R100 device error code
     *
     * 要回應的代碼
     * RESULT_SUCCESS = 0;
     * RESULT_ERROR = 1;
     * RESULT_UNOPENED = 2;
     * RESULT_PAPERJAM = 3;
     * RESULT_PAPEREMPTY = 4;
     * RESULT_HEADOPEN = 5;
     * RESULT_OVERHEAT = 6;
     * RESULT_VPERROR = 7;
     * RESULT_OFFLINE = 8;
     * RESULT_CONNECTERR = 9;
     * RESULT_SECURITYERR = 10;
     * RESULT_BUSY = 12;
     * RESULT_CUTTERERR = 13;
     * RESULT_POWERDOWN = 14;
     * RESULT_BUFFERFULL = 15;
     * RESULT_CANCEL = 16;
     *
     * 來源代碼
     * SUCCESS = 0;
     * ERR_PARAMETER = -1;
     * ERR_NOTOPEN = -2;
     * ERR_OPENCONFLICT = -3;
     * ERR_SERVICE_NOTRUNNING = -4;
     * ERR_SERVICE_CONNECTION = -5;
     * ERR_BOARD_NOTRUNNING = -6;
     * ERR_BOARD_CONNECTION = -7;
     * ERR_POWER_FAILURE = -8;
     * ERR_TIMEOUT = -9;
     * ERR_PAPEREMPTY = -104;
     * ERR_PAPERJAM = -103;
     * ERR_HEADOPEN = -105;
     * ERR_OVERHEAT = -106;
     * ERR_OFFLINE = -108;
     * ERR_PRINTER_CONNECTION = -109;
     * ERR_BUSY = -112;
     * ERR_BUFFEROVER = -115;
     * ERR_UNRECOVERABLE = -116;
     * ERR_UNDEFINED = -117;
     * CANCELED = -118;
     *
     * @param code V-R7000 error code
     * @return V-R100 error code
     */
//    public static int convertErrCode(int code) {
//        int result = 1;
//        switch (code) {
//            case LinePrinter.Response.SUCCESS:        //0
//                result = 0;
//                break;
//            case LinePrinter.Response.ERR_PARAMETER:
//                result = 1;
//                break;
//            case LinePrinter.Response.ERR_PAPEREMPTY:
//                result = 4;
//                break;
//            case LinePrinter.Response.ERR_PAPERJAM:
//                result = 3;
//                break;
//            case LinePrinter.Response.ERR_HEADOPEN:
//                result = 5;
//                break;
//            case LinePrinter.Response.ERR_OVERHEAT:
//                result = 6;
//                break;
//            case LinePrinter.Response.ERR_OFFLINE:
//                result = 8;
//                break;
//            case LinePrinter.Response.ERR_PRINTER_CONNECTION:
//                result = 9;
//                break;
//            case LinePrinter.Response.ERR_BUSY:
//                result = 12;
//                break;
//            case LinePrinter.Response.ERR_BUFFEROVER:
//                result = 15;
//                break;
//            case LinePrinter.Response.CANCELED:
//                result = 16;
//                break;
//            case 202:
//            case 203:
//            case 204:
//                result = code;
//                break;
//            case LinePrinter.Response.ERR_NOTOPEN:
//            case LinePrinter.Response.ERR_OPENCONFLICT:
//            case LinePrinter.Response.ERR_SERVICE_NOTRUNNING:
//            case LinePrinter.Response.ERR_SERVICE_CONNECTION:
//            case LinePrinter.Response.ERR_BOARD_NOTRUNNING:
//            case LinePrinter.Response.ERR_BOARD_CONNECTION:
//            case LinePrinter.Response.ERR_POWER_FAILURE:
//            case LinePrinter.Response.ERR_TIMEOUT:
//            case LinePrinter.Response.ERR_UNRECOVERABLE:
//            case LinePrinter.Response.ERR_UNDEFINED:
//            default:
//                result = 2;
//        }
//        return result;
//    }

//    public static String getPrinterErrorMessage(Context context, int errorCode) {
//        if (printerErrorMessages == null) {
//            printerErrorMessages = new HashMap<String, String>();
//            String[] keys = context.getResources().getStringArray(R.array.lineprinter_error_list);
//            String[] values = context.getResources().getStringArray(R.array.printer_error_list_value);
//            for (int i = 0; i < keys.length; i++) {
//                printerErrorMessages.put(keys[i], values[i]);
//            }
//        }
//
//        String err = errorCode + "";
//        if (printerErrorMessages.containsKey(err)) {
//            return printerErrorMessages.get(err);
//        } else {
//            return MessageFormat.format(printerErrorMessages.get("common"), err);
//        }
//    }

}
