package com.example.knowledge_android.pos.scanner;

public class ScannerFunctions {

	static {
		try {
			/**Android中的so文件是动态链接库，是二进制文件，即ELF文件。多用于NDK开发中。**/
			/**此文件在jniLibs目录里边**/
			System.loadLibrary("PosScannerDrv");
		} catch (UnsatisfiedLinkError ule) {
		}
	}

	//--------------------------Function
	public static native int OpenPort(String portName,int portSettings);
	public static native int OpenPort2(String portName,int portSettings);
	public static native int ClosePort(String portName);
	
	public static native int ReadData(String portName,int portSettings,byte[] data);
	public static native int ReadDataType(String portName,int portSettings);
	public static native int ReadStatus(String portName,int portSettings);
	public static native int ReadDataAndType(String portName,int portSettings,byte[] data,int[] type);
	
	public static native int Beep(String portName,int portSettings);
	public static native int StopReading(String portName,int portSettings);
	public static native int StartReading(String portName,int portSettings);
	//--------------------------
}
