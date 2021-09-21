package com.example.knowledge_android.comparator.bigdecimal;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

public final class BigDecimalConstructors {
	private BigDecimalConstructors() {
		// never create an instance
	}

    public static BigDecimal bd0 = BigDecimal.ZERO;

	public static BigDecimal bd(BigInteger val) {
		return new BigDecimal(val);
	}

	public static BigDecimal bd(BigInteger unscaledVal, int scale) {
		return new BigDecimal(unscaledVal, scale);
	}

	public static BigDecimal bd(BigInteger unscaledVal, int scale,
			MathContext mc) {
		return new BigDecimal(unscaledVal, scale, mc);
	}

	public static BigDecimal bd(BigInteger val, MathContext mc) {
		return new BigDecimal(val, mc);
	}

	public static BigDecimal bd(char[] in) {
		return new BigDecimal(in);
	}

	public static BigDecimal bd(char[] in, int offset, int len) {
		return new BigDecimal(in, offset, len);
	}

	public static BigDecimal bd(char[] in, int offset, int len, MathContext mc) {
		return new BigDecimal(in, offset, len, mc);
	}

	public static BigDecimal bd(char[] in, MathContext mc) {
		return new BigDecimal(in, mc);
	}

	public static BigDecimal bd(double val) {
		return BigDecimal.valueOf(val);
	}

	public static BigDecimal bd(double val, MathContext mc) {
		return new BigDecimal(val, mc);
	}

	public static BigDecimal bd(int val) {
		return new BigDecimal(val);
	}

	public static BigDecimal bd(int val, MathContext mc) {
		return new BigDecimal(val, mc);
	}

	public static BigDecimal bd(long val) {
		return BigDecimal.valueOf(val);
	}

	public static BigDecimal bd(long val, MathContext mc) {
		return new BigDecimal(val, mc);
	}

	public static BigDecimal bd(String val) {
		return new BigDecimal(val);
	}

	public static BigDecimal bd(CharSequence val) {
		return new BigDecimal(val.toString());
	}

	public static BigDecimal bd(String val, MathContext mc) {
		return new BigDecimal(val, mc);
	}

	public static BigDecimal bd(long unscaledVal, int scale) {
		return BigDecimal.valueOf(unscaledVal, scale);
	}

}
