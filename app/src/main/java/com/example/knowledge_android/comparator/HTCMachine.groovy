//package com.example.knowledge_android.comparator
//
//
//import javax.comm.CommPortIdentifier;
//import javax.comm.PortInUseException;
//import javax.comm.SerialPort;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.util.Enumeration;
//
///**
// * Created with IntelliJ IDEA.
// * User: David
// * Date: 1/5/13
// * Time: 4:09 PM
// * To change this template use File | Settings | File Templates.
// */
//
//class HTCMachine {
//
//
//    public static String callHTC(List<Byte> tranType, List<Byte> posNum, List<Byte> transno, List<Byte> transAmount, List<Byte> transDatetime) {
//        List<Byte> sendFlag = [0x01,0x01,0x01]
//        List<Byte> command = [(int)'M',(int)'M']
//        List<Byte> subCashAmount = ['0','0','0','0','0','0','0']
//        List<Byte> preTransNo = ['0','0','0','0','0','0']
//
//        List<Byte> data = tranType + posNum + transno + transAmount + transDatetime + subCashAmount + preTransNo
//
//        List<Byte> all= sendFlag + command + data
//
//        for (int i = 0; i < all.size(); i++) {
//            if (all[i] instanceof String) {
//                all[i] = (int) all[i]
//            }
//        }
//
//        byte s = all[0]
//        for (int i=1; i<all.size(); i++) {
//            s = s ^ all[i]
//        }
//        byte checksum = s
//
//        all = (all + s)
//
////        println all
//
//
//        SerialPortUtil serialPortUtil = new SerialPortUtil("/dev/ttyS5", 9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
//        byte[] rst = null;
//        try {
//            byte[] byteAll= new byte[50]
//            for(int i=0; i < 50; i++) {
//                byteAll[i]=(byte)all[i]
//            }
//            CreamToolkit.logMessage("htc sendFlag:" + sendFlag)
//            CreamToolkit.logMessage("htc command:"+command)
//            CreamToolkit.logMessage("htc data:" + data)
//            CreamToolkit.logMessage("htc subCashAmount:" + subCashAmount)
//            CreamToolkit.logMessage("htc preTransNo:" + preTransNo)
//            CreamToolkit.logMessage("htc checksum:" + checksum)
//            CreamToolkit.logMessage("htc byteAll:" + byteAll)
//            rst = serialPortUtil.writeAndRead(byteAll, 20);
//            if (rst[3] == 'B' && rst[4] == 'B') {
////                println 'failed'
//                return "failed"
//            } else if (rst[3] == 'G' && rst[4] == 'G') {
////                println 'ok'
//                return "ok"
//            } else {
////                println 'others'
//                return "others"
//            }
//
//
//        } catch (Exception e) {
//            System.out.println("call HTCcatMachine process error!");
//            CreamToolkit.logMessage(e);
//            return "";
//        }
//        println rst
//
//    }
//
//    public static String cancelHTC() {
//
//        CreamToolkit.logMessage("cancel htc paying by user")
//        try {
//            Runtime.getRuntime().exec("/usr/bin/killall serialport").waitFor();
//        } catch (Exception e) {
//
//        }
//
//        Thread.sleep(1000);
//        List<Byte> sendFlag = [0x01,0x01,0x01]
//        List<Byte> command = [(int)'D',(int)'D']
//        List<Byte> all= sendFlag + command
//
//        for (int i = 0; i < all.size(); i++) {
//            if (all[i] instanceof String) {
//                all[i] = (int) all[i]
//            }
//        }
//
//        byte s = all[0]
//        for (int i=1; i<all.size(); i++) {
//            s = s ^ all[i]
//        }
//        byte checksum = s
//
//        all = (all + s)
//
////        println all
//
//
//        SerialPortUtil serialPortUtil = new SerialPortUtil("/dev/ttyS5", 9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
//        byte[] rst = null;
//        try {
//            byte[] byteAll= new byte[6]
//            for(int i=0; i < 6; i++) {
//                byteAll[i]=(byte)all[i]
//            }
//            CreamToolkit.logMessage("htc sendFlag:" + sendFlag)
//            CreamToolkit.logMessage("htc command:"+command)
//            CreamToolkit.logMessage("htc checksum:" + checksum)
//            CreamToolkit.logMessage("htc byteAll:" + byteAll)
//            rst = serialPortUtil.writeAndRead(byteAll, 5);
////            rst = serialPortUtil.writeCancel(byteAll);
//
//
////            if (rst[3] == 'B' && rst[4] == 'B') {
//////                println 'failed'
////                return "failed"
////            } else if (rst[3] == 'G' && rst[4] == 'G') {
//////                println 'ok'
////                return "ok"
////            } else {
//////                println 'others'
////                return "others"
////            }
//
//            return null;
//
//        } catch (Exception e) {
//            System.out.println("call HTCcatMachine process error!");
//            return "";
//        }
//
//
//    }
//
//    public static void main(String[] args) {
//
//        List<Byte> tranType = ['2','3']
//        List<Byte> posNum = ['0','1']
//        List<Byte> transno = ['0','0','0','0','0','2']
//        List<Byte> transAmount = ['0','0','0','0','1','2','3']
//        List<Byte> transDatetime = ['2','0','1','3','0','1','1','1','0','9','1','2','0','0']
//        String rst = callHTC(tranType, posNum, transno, transAmount, transDatetime)
//        if (rst.equals("ok")) {
//            println "ok"
//        } else {
//            println "failed"
//        }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
////        Enumeration portList = CommPortIdentifier.getPortIdentifiers();
////        CommPortIdentifier portId = null;
////        while (portList.hasMoreElements())
////        {
////            portId = (CommPortIdentifier)portList.nextElement();
////            System.out.println("Enum serial port: " + portId.getName());
////            if(portId.getPortType() == 1 && portId.getName().equals("/dev/ttyS4")) {
////                System.out.println("/dev/ttyS4 found");
////                try {
////                    SerialPort serialPort = (SerialPort)portId.open("SerialPortScannerTest", 15000);
////
////                    serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8,
////                            SerialPort.STOPBITS_1,
////                            SerialPort.PARITY_NONE);
////
////                    OutputStream outputStream = serialPort.getOutputStream();
////                    InputStream inputStream = serialPort.getInputStream();
//
//
////                    outputStream.write(0x01);
////                    outputStream.write(0x01);
////                    outputStream.write(0x01);
////
////                    outputStream.write(0x4d);
////                    outputStream.write(0x4d);
////
////
////                    String s = "23011234562222233201301051633002222233000000";
////                    String checkSum;
////                    for (int i = 0; i < s.length(); i++) {
////                        outputStream.write(s.charAt(i));
////                    }
////
////
////                    int x = (s.charAt(0)).toString().toInteger().intValue()
////                    for (int i = 1; i < s.length(); i++) {
////                        x = x ^ (s.charAt(i)).toString().toInteger().intValue()
////                    }
//
////                    byte[] x = [0x01, 0x01, 0x01, 0x4D, 0x4D, 0x32, 0x33, 0x30, 0x31, 0x30, 0x30, 0x30, 0x30, 0x30, 0x31, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x31, 0x32, 0x30, 0x31, 0x33, 0x30, 0x31, 0x31, 0x31, 0x30, 0x39, 0x31, 0x30, 0x32, 0x33, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x31, 0x30, 0x30, 0x30, 0x30, 0x30, 0x31, 0x09];
//
//
//
////                    byte[] x = [0x01,0x01,0x01,0x4D,0x4D,0x32,0x33,0x30,0x31,0x30,0x30,0x30,0x30,0x30,0x32,0x30,0x30,0x30,0x30,0x30,0x30,0x32,0x32,0x30,0x31,0x33,0x30,0x31,0x31,0x31,0x30,0x39,0x31,0x32,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x30,0x32,0x30,0x30,0x30,0x30,0x30,0x32,0x0A]
//
//
////                    byte[] x = [0x01,0x01,0x01,'M','M','2',
////                            '3','0','1','0','0','0','0','0',
////                            '2','0','0','0','0','0','0',
////                            '2','2','0','1','3',
////                            '0','1','1','1','0','9','1',
////                            '2','0',
////                            '0','0','0',
////                            '0','0','0','0','2','0','0','0',
////                            '0','0','2',0x0A]
//
////                    byte[] x= [0x01,0x01,0x01,(int)'M',(int)'M',(int)'2',
////                            (int)'3',(int)'0',(int)'1',(int)'0',(int)'0',(int)'0',(int)'0',(int)'0',
////                            (int)'2',(int)'0',(int)'0',(int)'0',(int)'0',(int)'0',(int)'0',
////                            (int)'2',(int)'2',(int)'0',(int)'1',(int)'3',
////                            (int)'0',(int)'1',(int)'1',(int)'1',(int)'0',(int)'9',(int)'1',
////                            (int)'2',(int)'0',
////                            (int)'0',(int)'0',(int)'0',
////                            (int)'0',(int)'0',(int)'0',(int)'0',(int)'2',(int)'0',(int)'0',(int)'0',
////                            (int)'0',(int)'0',(int)'2',0x0A]
//
//
//
////                    outputStream.write(x);
////                    outputStream.flush();
////                    System.out.println("write ok");
////                    byte[] readPort = new byte[100];
////                    inputStream.read(readPort);
////
////                    System.out.println("content size:" + readPort.length);
////                    println("content:" + readPort);
//
//                    //echo -ne '\x01\x01\x01\x4D\x4D\x32\x33\x30\x31\x30\x30\x30\x30\x30\x32\x30\x30\x30\x30\x30\x30\x32\x32\x30\x31\x33\x30\x31\x31\x31\x30\x39\x31\x32\x30\x30\x30\x30\x30\x30\x30\x30\x32\x30\x30\x30\x30\x30\x32\x0A' | /opt/pos/serialport /dev/ttyS4 9600 8 1 0 0 | hexdump -C
//
//                    //-------------------------------------------
////                    SerialPortUtil serialPortUtil = new SerialPortUtil("/dev/ttyS4", 9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
////                    byte[] rst = null;
////                    try {
////                        rst = serialPortUtil.writeAndRead(x);
////                    } catch (Exception e) {
////
////                    }
////
////                    println rst
////
////                    //-------------------------------------------
////
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
//
//
////            }
////        }
//    }
//}
//
