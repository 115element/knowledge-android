//package com.example.knowledge_android.comparator;
//
//import com.google.zxing.BarcodeFormat;
//import com.google.zxing.MultiFormatWriter;
//import com.google.zxing.common.BitMatrix;
//
//import java.io.File;
//import java.io.IOException;
//
//
////这个类需要java awt支持
//public class BarcodeUtil {
//
//    public static void createBarcode(String orderId,String barcodeDestinationPath) throws Exception {
//
//        int width = 300;
//        int height = 50;
//        int BLACK = 0xFF000000;
//        int WHITE = 0xFFFFFFFF;
//        String type = "bmp";
//        //Hashtable hints= new Hashtable();
//        //hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
//
//        //TODO  可修改BarcodeFormat.CODE_128 寻找原因
//        BitMatrix bitMatrix = new MultiFormatWriter().encode(orderId, BarcodeFormat.CODE_128, width, height);
//
//        //TODO 可修改BufferedImage.TYPE_BYTE_INDEXED 寻找原因   --->  TYPE_BYTE_INDEXED
//        BufferedImage bufferImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY); /**256色*/
//        for (int x = 0; x < width; x++) {
//            for (int y = 0; y < height; y++) {
//                bufferImage.setRGB(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);
//            }
//        }
//        File barcodeFile = new File(barcodeDestinationPath)
//
//        //TODO 非常慢,为什么？
//        log.info('ImageIO write Begin!')
//        //log.info('一维码图片缓存目录:'+ImageIO.getCacheDirectory())
//        log.info('一维码图片是否使用缓存'+ImageIO.setUseCache(false))
//        if (!ImageIO.write(bufferImage,type,barcodeFile)) {
//            throw new IOException("Could not write an image1 of format " + type);
//        }
//        //TODO 非常慢,为什么？
//        log.info('ImageIO write End!')
//    }
//
//    static void main(String[] args){
//        String path = "C:\\pos\\lawson\\image\\" //云客券图片目录
//        File file = new File(path)
//        if (!file.exists()){
//            file.mkdir()
//        }
//        println(path)
//        createBarcode('1232342',path+'1234.bmp')
//    }
//}
