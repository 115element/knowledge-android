//package com.example.knowledge_android.comparator;
//
//import java.io.*;
//import java.util.*;
//
///**
// * User: eric
// * Date: 10/2/12
// * Time: 3:16 PM
// */
//public class SerialPortUtil {
//    private static final File BIN_FILE = new File("serialport");
//    private Process process;
//    private OutputStream output;
//    private String portName;
//    private int baudRate;
//    private int dataBits;
//    private int stopBits;
//    private int parity;
////    private MessageHandler messageHandler;
////    private DataHandler dataHandler;
//    private DataBuffer buffer;
//    private boolean isRunning = true;
//
//    synchronized public static boolean getFlag() {
//        return flag;
//    }
//
//    synchronized public static void setFlag(boolean flag) {
//        SerialPortUtil.flag = flag;
//    }
//
//    static boolean flag = false;
//
//
//    public SerialPortUtil(String portName, int baudRate, int dataBits, int stopBits, int parity) {
//        this.portName = portName;
//        this.baudRate = baudRate;
//        this.dataBits = dataBits;
//        this.stopBits = stopBits;
//        this.parity = parity;
//        buffer = new DataBuffer();
//        buildProcess();
//    }
//
//    public void write(byte[] data) throws IOException {
//        if (process == null)
//            buildProcess();
//
//        try {
//            output.write(data);
//            output.flush();
//        } catch (IOException e) {
//        	CreamToolkit.logErrorMessage("", e)
//;
//        	System.out.println("Close()   1");
//            close();
//            throw e;
//        }
//    }
//
//    public void writeCancel(byte[] data) throws IOException {
//        CreamToolkit.logMessage("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
//        write(data);
//    }
//
//    public byte[] writeAndRead(byte[] outData, int timeout) throws IOException {
//        synchronized (buffer) {
//            buffer.clearData();
//            write(outData);
//            setFlag(true);
//            try {
//                CreamToolkit.logMessage("等待" + portName + "回应资料");
//
//                Date beginTime = Calendar.getInstance().getTime();
//                CreamToolkit.logMessage("getflag():" + getFlag());
//                while (getFlag()) {
//
//                    buffer.wait(500);
//                    CreamToolkit.logMessage("取得" + portName + "回应资料");
//                    byte[] inData = buffer.readData();
//                    if (buffer.length >= 6 && inData[0] == 0x01) {
//                        String s = "[";
//                        for (int i = 0; i < buffer.length; i++) {
//                            s = s + inData[i] + ",";
//                        }
//                        s = s.substring(0, s.length() - 1);
//                        CreamToolkit.logMessage("htc return data:" + s + "]");
//
//                        return inData;
//                    }
//                    Date endTime = Calendar.getInstance().getTime();
//                    if ((endTime.getTime() - beginTime.getTime())/1000 >= timeout) {
//                        CreamToolkit.logMessage("timeout break" + "(" + (endTime.getTime() - beginTime.getTime()) + ")");
//                        break;
//                    }
//                }
//
//                return null;
//
////                ArrayList list = new ArrayList();
////                if (inData.length == 0) {
////                    logMessage(portName + "回应资料错误");
////                    return null;
////                } else {
////                    for (int i = 0; i < inData.length; i++) {
////                        list.add("" + inData[i]);
////                    }
////                    buffer = new DataBuffer();
////
////                    return list;
////                    return inData;
////                }
//            } catch (Exception e) {
//                CreamToolkit.logMessage(portName + "回应超时超時");
//                e.printStackTrace();
//                return null;
//            } finally {
//                CreamToolkit.logMessage("begin process destroy");
//                try {
//                    setFlag(false);
//                    if (!CreamToolkit.getOsName().equals("Windows"))
//                        Runtime.getRuntime().exec("/usr/bin/killall serialport").waitFor();
//                } catch (InterruptedException e) {
//
//                }
//                process = null;
//                CreamToolkit.logMessage("end process destroy");
//            }
//        }
//
//    }
//
//    public void close() {
//        if (process == null)
//            return;
//
//        try {
//            CreamToolkit.logMessage("通知serialport(" + portName + ")程式结束");
//            output.write("\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000".getBytes());
//            output.flush();
////            process.getInputStream().close();
//        } catch (Exception e) {
//            CreamToolkit.logMessage("通知serialport(" + portName + ")程式结束失败");
//            e.printStackTrace();
//        }
//
//        try {
//            CreamToolkit.logMessage("等待serialport(" + portName + ")程式结束");
//            //process.waitFor();
//            CreamToolkit.logMessage("serialport(" + portName + ")程式结束");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        process = null;
//    }
//
//    private void buildProcess() {
//        String[] cmd = {
//                BIN_FILE.getAbsolutePath(),
//                portName,
//                baudRate + "",
//                dataBits + "",
//                stopBits + "",
//                parity + "",
//                "0"
//        };
//        try {
//            StringBuffer buf = new StringBuffer();
//            for (int i = 0; i < cmd.length; i++) {
//                if (i > 0)
//                    buf.append(" ");
//                buf.append(cmd[i]);
//            }
//            CreamToolkit.logMessage("启动程式:" + buf.toString());
//            Runtime.getRuntime().exec("/bin/chmod +x " + BIN_FILE.getAbsolutePath());
//            process = Runtime.getRuntime().exec(cmd);
//            MessageHandler messageHandler = new MessageHandler(process.getErrorStream());
//            CreamToolkit.logMessage("开始" + portName + "的信息处理");
//            new Thread(messageHandler, "MessageHandler").start();
//
//            DataHandler dataHandler = new DataHandler(process.getInputStream());
//            new Thread(dataHandler, "DataHandler").start();
//            output = process.getOutputStream();
//        } catch (IOException e) {
//            e.printStackTrace();
//            process = null;
//        }
//    }
//
////    private void logMessage(String message) {
////        CreamToolkit.logMessage("[" + getClass().getSimpleName() + "] " + message);
////    }
//
//    class DataHandler implements Runnable {
//        private InputStream in;
//
//        public DataHandler(InputStream in) {
//            this.in = in;
//        }
//
//        public void run() {
//            CreamToolkit.logMessage("开始" + portName + "的回应资料处理");
//            try {
//                int c;
//                while(true) {
//                    c = in.read();
//
//                    if (c == -1)
//                        break;
//
//                    buffer.writeData(c);
//                }
//                buffer.writeData(-1);
//            } catch (Exception e) {
//            	CreamToolkit.logErrorMessage("", e)
//;
//            	System.out.println("Close()   2");
//                close();
//            }
//            CreamToolkit.logMessage("结束" + portName + "的回应资料处理");
//        }
//    }
//
//    class MessageHandler implements Runnable {
//        private InputStream in;
//
//        public MessageHandler(InputStream in) {
//            this.in = in;
//        }
//
//        public void run() {
//            BufferedReader reader = null;
//            try {
//                reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
//                String line;
//                while((line = reader.readLine()) != null) {
//                    CreamToolkit.logMessage(line);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    if (reader != null)
//                        reader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                CreamToolkit.logMessage("结束" + portName + "的信息处理");
//            }
//        }
//    }
//
//    class DataBuffer {
//        private byte[] buffer = new byte[255];
//        private int length;
//
//        synchronized public void writeData(int data) {
//        	if (data == -1) {
//        		notifyAll();
//        		return;
//        	}
//            if (length == buffer.length) {
//                CreamToolkit.logMessage("buffer已满,清空buffer的资料");
//                length = 0;
//            }
//            buffer[length++] = (byte) data;
//
//        }
//
//        synchronized public byte[] readData() {
////            try {
////                Thread.sleep(20000);
////            } catch (InterruptedException e) {
////                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
////            }
////            byte[] ret = new byte[buffer.length];
////            System.arraycopy(buffer, 0, ret, 0, buffer.length);
////            length = 0;
////            return ret;
//            return buffer;
//        }
//
//        synchronized public void clearData() {
//            if (length > 0) {
//                CreamToolkit.logMessage("有残留资料" + length + " bytes");
//                StringBuffer buf = new StringBuffer();
//                for (int i = 0; i < length; i++) {
//                    if (i > 0)
//                        buf.append(" ");
//                    String s = Integer.toHexString(buffer[i] & '\u0011');
//                    if (s.length() == 1)
//                        s = "0" + s;
//                    buf.append(s);
//                }
//                CreamToolkit.logMessage(buf.toString());
//            }
//            length = 0;
//        }
//    }
//}
