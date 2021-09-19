package com.example.knowledge_android.comparator.jni;



public class CashDrawer7000Local {

	public native static char openCashDrawer(int no);

	public native static int getDrawerState(int no);

	static {
		try {
			System.loadLibrary("cashdrawer7000");
		} catch (Exception e) {
			//CreamToolkit.logErrorMessage("", e);
		}
	}
}
