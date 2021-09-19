package com.example.knowledge_android.comparator;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Text formatter.
 *
 * @version      1.01 11/26/99
 * @author       Bruce Yo
*/
public class CreamFormatter {

    private static CreamFormatter instance = new CreamFormatter();
    private DecimalFormat df;
    private SimpleDateFormat datef;
    private String defaultPattern;

    private CreamFormatter() {
        //df = (DecimalFormat)DecimalFormat.getInstance();
        df = new DecimalFormat();
        datef = new SimpleDateFormat();
        defaultPattern = df.toLocalizedPattern();
    }

    public static CreamFormatter getInstance() {
        return instance;
    }

    /**
     * Format an integer to default format.
     */
    public String format(int number) {
        StringBuffer result = new StringBuffer();
        FieldPosition fieldPosition = new FieldPosition(0);
        df.applyLocalizedPattern(defaultPattern);
        df.format((long)number, result, fieldPosition);
        return result.toString();
    }

    /**
     * Format a double to default format.
     */
    public String format(double number) {
        StringBuffer result = new StringBuffer();
        FieldPosition fieldPosition = new FieldPosition(0);
        df.applyLocalizedPattern(defaultPattern);
        df.format(number, result, fieldPosition);
        return result.toString();
    }

    /**
     * Format an integer from given format.
     */
    public String format(int number, String pattern) {
        StringBuffer result = new StringBuffer();
        FieldPosition fieldPosition = new FieldPosition(0);
        df.applyPattern(pattern);
        df.format((long)number, result, fieldPosition);
        return result.toString();
    }

    /**
     * Format a double from given format.
     */
    public String format(double number, String pattern) {
        StringBuffer result = new StringBuffer();
        FieldPosition fieldPosition = new FieldPosition(0);
        df.applyPattern(pattern);
        df.format(number, result, fieldPosition);
        return result.toString();
    }

    public String format(BigDecimal number, String pattern) {
        StringBuffer result = new StringBuffer();
        FieldPosition fieldPosition = new FieldPosition(0);
        df.applyPattern(pattern);
        df.format(number, result, fieldPosition);
        return result.toString();
    }

    /**
     * Format an integer from given format.
     */
    public String format(Date date, String pattern) {
        StringBuffer result = new StringBuffer();
        FieldPosition fieldPosition = new FieldPosition(0);
        datef.applyPattern(pattern);
        datef.format(date, result, fieldPosition);
        return result.toString();
    }

    /**
     * Format a string to HTML format.
     */
    public String formatToHtml(String str) {
        StringTokenizer st = new StringTokenizer(str, "\n");
        String result = "<HTML><CENTER>";
        while (st.hasMoreTokens()) {
            result += st.nextToken();
            if (st.hasMoreTokens())
                result += "<BR>";
        }
        result += "</CENTER></HTML>";
        return result;
    }

    // For unit test.
    public static void main(String[] args) {
        CreamFormatter formatter = new CreamFormatter();

        System.out.println("formatToHtml(\"ABC DEF\") -> " + formatter.formatToHtml("ABC"));
        /*System.out.println("format(1234567) -> " + formatter.format(1234567));
        System.out.println("format(-1234567) -> " + formatter.format(-1234567));
        System.out.println("format(1234567, ##########) -> " + formatter.format(1234567, "##########"));
        System.out.println("format(-1234567, ##########) -> " + formatter.format(-1234567, "##########"));
        System.out.println("format(-1234567, ##########;(##########)) -> " + formatter.format(-1234567, "##########;(##########)"));
        System.out.println("format(-1234567, #,###,###,###;(#,###,###,###)) -> " + formatter.format(-1234567, "#,###,###,###;(#,###,###,###)"));
        System.out.println("format(1234567, #######000) -> " + formatter.format(1234567, "#######000"));
        System.out.println("format(-1234567, #######000) -> " + formatter.format(-1234567, "#######000"));
        System.out.println("format(1234567, 0000000000) -> " + formatter.format(1234567, "0000000000"));
        System.out.println("format(-1234567, 0000000000) -> " + formatter.format(-1234567, "0000000000"));
        System.out.println("format(12, #######000) -> " + formatter.format(12, "#######000"));
        System.out.println("format(-12, #######000) -> " + formatter.format(-12, "#######000"));
        System.out.println("format(1234.567) -> " + formatter.format(1234.567));
        System.out.println("format(-1234.567) -> " + formatter.format(-1234.567));
        System.out.println("format(0.12, ###%) -> " + formatter.format(0.12, "###%"));
        System.out.println("format(-0.12, ###%) -> " + formatter.format(-0.12, "###%"));
        System.out.println("format(0.12, 000%) -> " + formatter.format(0.12, "000%"));
        System.out.println("format(-0.12, 000%) -> " + formatter.format(-0.12, "000%"));
        System.out.println("formatToHtml(\"ABC DEF\") -> " + formatter.formatToHtml("ABC"));
        System.out.println("formatToHtml(\"today\") -> " + formatter.format(new Date(), "yyyy/MM/dd"));*/
    }
}

