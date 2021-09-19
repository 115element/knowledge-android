package com.example.knowledge_android.comparator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;


/**
 * @author CStore
 */
public class NumberConfuser {

    private static int getTerminalNumber = 10;

    public NumberConfuser() {
    }

    /**
     * 传入字符串的打乱规则：
     * <br>1. 如果输入的字符串长度不足8位，前面用"0"补足凑够8位。
     * <br>2. 取这8个数字之和相加后的值模10取余，得到余数(可能为0～9之间的任何数字)。
     * <br>3. 根据得到的余数按照程序中的规则进行打乱。(case 余数，就执行其下面的打乱规则)
     * <br>4. 返回打乱后的字符串
     *
     * @param inputStr 需要打乱的字符串
     * @return 打乱以后的字符串
     */
    public static String confuse(String inputStr) {
        if (inputStr.length() < 8) {
            inputStr = "0000000" + inputStr;
        }

        if (getTerminalNumber > 9) { /**考虑两位机号**/
            inputStr = getTerminalNumber + inputStr.substring(inputStr.length() - 6);
            return getData(inputStr);
        } else {
            inputStr = getTerminalNumber + inputStr.substring(inputStr.length() - 7);
            return getData(inputStr);
        }
    }

    public static String confuse(String posNo, String inputStr) {


        if (inputStr.length() < 8) {
            inputStr = "0000000" + inputStr;
        }

        if (Integer.parseInt(posNo) > 9) {  /**考虑两位机号**/
            inputStr = posNo + inputStr.substring(inputStr.length() - 6);
            return getData(inputStr);
        } else {
            inputStr = posNo + inputStr.substring(inputStr.length() - 7);
            return getData(inputStr);
        }
    }


    public static String getData(String inputStr) {
        int sum = 0;
        StringBuffer target = new StringBuffer();
        for (int i = 0; i < inputStr.length(); i++) {
            sum += Integer.parseInt(inputStr.substring(i, i + 1));
        }
        sum = sum % 10;

        switch (sum) {
            case 0:
                target.append(inputStr.charAt(5));
                target.append(inputStr.charAt(6));
                target.append(inputStr.charAt(2));
                target.append(inputStr.charAt(0));
                target.append(inputStr.charAt(4));
                target.append(inputStr.charAt(1));
                target.append(inputStr.charAt(7));
                target.append(inputStr.charAt(3));

                break;
            case 1:
                target.append(inputStr.charAt(2));
                target.append(inputStr.charAt(0));
                target.append(inputStr.charAt(4));
                target.append(inputStr.charAt(1));
                target.append(inputStr.charAt(7));
                target.append(inputStr.charAt(3));
                target.append(inputStr.charAt(6));
                target.append(inputStr.charAt(5));
                break;
            case 2:
                target.append(inputStr.charAt(4));
                target.append(inputStr.charAt(1));
                target.append(inputStr.charAt(7));
                target.append(inputStr.charAt(3));
                target.append(inputStr.charAt(6));
                target.append(inputStr.charAt(5));
                target.append(inputStr.charAt(2));
                target.append(inputStr.charAt(0));
                break;
            case 3:
                target.append(inputStr.charAt(7));
                target.append(inputStr.charAt(3));
                target.append(inputStr.charAt(6));
                target.append(inputStr.charAt(5));
                target.append(inputStr.charAt(2));
                target.append(inputStr.charAt(0));
                target.append(inputStr.charAt(4));
                target.append(inputStr.charAt(1));
                break;
            case 4:
                target.append(inputStr.charAt(6));
                target.append(inputStr.charAt(5));
                target.append(inputStr.charAt(4));
                target.append(inputStr.charAt(1));
                target.append(inputStr.charAt(2));
                target.append(inputStr.charAt(0));
                target.append(inputStr.charAt(7));
                target.append(inputStr.charAt(3));
                break;
            case 5:
                target.append(inputStr.charAt(2));
                target.append(inputStr.charAt(0));
                target.append(inputStr.charAt(7));
                target.append(inputStr.charAt(3));
                target.append(inputStr.charAt(6));
                target.append(inputStr.charAt(5));
                target.append(inputStr.charAt(4));
                target.append(inputStr.charAt(1));
                break;
            case 6:
                target.append(inputStr.charAt(6));
                target.append(inputStr.charAt(5));
                target.append(inputStr.charAt(2));
                target.append(inputStr.charAt(0));
                target.append(inputStr.charAt(4));
                target.append(inputStr.charAt(1));
                target.append(inputStr.charAt(7));
                target.append(inputStr.charAt(3));
                break;
            case 7:
                target.append(inputStr.charAt(2));
                target.append(inputStr.charAt(0));
                target.append(inputStr.charAt(4));
                target.append(inputStr.charAt(1));
                target.append(inputStr.charAt(7));
                target.append(inputStr.charAt(3));
                target.append(inputStr.charAt(6));
                target.append(inputStr.charAt(5));
                break;
            case 8:
                target.append(inputStr.charAt(4));
                target.append(inputStr.charAt(1));
                target.append(inputStr.charAt(7));
                target.append(inputStr.charAt(3));
                target.append(inputStr.charAt(6));
                target.append(inputStr.charAt(5));
                target.append(inputStr.charAt(2));
                target.append(inputStr.charAt(0));
                break;
            case 9:
                target.append(inputStr.charAt(7));
                target.append(inputStr.charAt(3));
                target.append(inputStr.charAt(6));
                target.append(inputStr.charAt(5));
                target.append(inputStr.charAt(2));
                target.append(inputStr.charAt(0));
                target.append(inputStr.charAt(4));
                target.append(inputStr.charAt(1));
                break;
            default:
                target = null;
                break;
        }
        return target.toString();
    }

    /**
     * 把传过来的打乱过的字符串顺序还原
     * 1. 取这8个数字之和相加后的值模10取余，得到余数(可能为0～9之间的任何数字)。
     * 2. 根据得到的余数按照程序中的规则进行恢复。(case 余数，就执行其下面的恢复规则)
     * 3. 返回恢复后的字符串
     *
     * @param inputStr 打乱的字符串
     * @return 还原以后的字符串
     */
    public static String defuse(String inputStr) {
        int sum = 0;
        StringBuffer target = new StringBuffer();

        for (int i = 0; i < inputStr.length(); i++) {
            sum += Integer.parseInt(inputStr.substring(i, i + 1));
        }
        sum = sum % 10;
//		System.out.println(sum);
        switch (sum) {
            case 0:
                target.append(inputStr.charAt(3));
                target.append(inputStr.charAt(5));
                target.append(inputStr.charAt(2));
                target.append(inputStr.charAt(7));
                target.append(inputStr.charAt(4));
                target.append(inputStr.charAt(0));
                target.append(inputStr.charAt(1));
                target.append(inputStr.charAt(6));
                break;

            case 1:
                target.append(inputStr.charAt(1));
                target.append(inputStr.charAt(3));
                target.append(inputStr.charAt(0));
                target.append(inputStr.charAt(5));
                target.append(inputStr.charAt(2));
                target.append(inputStr.charAt(7));
                target.append(inputStr.charAt(6));
                target.append(inputStr.charAt(4));
                break;

            case 2:
                target.append(inputStr.charAt(7));
                target.append(inputStr.charAt(1));
                target.append(inputStr.charAt(6));
                target.append(inputStr.charAt(3));
                target.append(inputStr.charAt(0));
                target.append(inputStr.charAt(5));
                target.append(inputStr.charAt(4));
                target.append(inputStr.charAt(2));
                break;

            case 3:
                target.append(inputStr.charAt(5));
                target.append(inputStr.charAt(7));
                target.append(inputStr.charAt(4));
                target.append(inputStr.charAt(1));
                target.append(inputStr.charAt(6));
                target.append(inputStr.charAt(3));
                target.append(inputStr.charAt(2));
                target.append(inputStr.charAt(0));
                break;

            case 4:
                target.append(inputStr.charAt(5));
                target.append(inputStr.charAt(3));
                target.append(inputStr.charAt(4));
                target.append(inputStr.charAt(7));
                target.append(inputStr.charAt(2));
                target.append(inputStr.charAt(1));
                target.append(inputStr.charAt(0));
                target.append(inputStr.charAt(6));
                break;

            case 5:
                target.append(inputStr.charAt(1));
                target.append(inputStr.charAt(7));
                target.append(inputStr.charAt(0));
                target.append(inputStr.charAt(3));
                target.append(inputStr.charAt(6));
                target.append(inputStr.charAt(5));
                target.append(inputStr.charAt(4));
                target.append(inputStr.charAt(2));
                break;

            case 6:
                target.append(inputStr.charAt(3));
                target.append(inputStr.charAt(5));
                target.append(inputStr.charAt(2));
                target.append(inputStr.charAt(7));
                target.append(inputStr.charAt(4));
                target.append(inputStr.charAt(1));
                target.append(inputStr.charAt(0));
                target.append(inputStr.charAt(6));
                break;

            case 7:
                target.append(inputStr.charAt(1));
                target.append(inputStr.charAt(3));
                target.append(inputStr.charAt(0));
                target.append(inputStr.charAt(5));
                target.append(inputStr.charAt(2));
                target.append(inputStr.charAt(7));
                target.append(inputStr.charAt(6));
                target.append(inputStr.charAt(4));
                break;

            case 8:
                target.append(inputStr.charAt(7));
                target.append(inputStr.charAt(1));
                target.append(inputStr.charAt(6));
                target.append(inputStr.charAt(3));
                target.append(inputStr.charAt(0));
                target.append(inputStr.charAt(5));
                target.append(inputStr.charAt(4));
                target.append(inputStr.charAt(2));
                break;

            case 9:
                target.append(inputStr.charAt(5));
                target.append(inputStr.charAt(7));
                target.append(inputStr.charAt(4));
                target.append(inputStr.charAt(1));
                target.append(inputStr.charAt(6));
                target.append(inputStr.charAt(3));
                target.append(inputStr.charAt(2));
                target.append(inputStr.charAt(0));
                break;
        }
        return target.toString();
    }

    public void parseNumber () {
        System.out.println("测试:" + defuse("44620160"));
    }

    public void parseNumber2 () {
        System.out.println("测试:" + confuse("1","100020"));
    }

    public static void main(String[] args) {

        System.out.println("测试:" + confuse("41790260"));
        System.out.println("测试:" + confuse("1", "205037"));

        System.out.println(defuse("01452947"));
        System.out.println(confuse("493"));

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String inStr = "";
        System.out.println("To get help, pls type ?");
        while (true) {
            try {
                inStr = reader.readLine();
                if (inStr == null || inStr.equalsIgnoreCase(""))
                    continue;
                if (inStr.trim().substring(0, 1).equalsIgnoreCase("c"))
                    System.out.println("original tranno: " + NumberConfuser.defuse(inStr.substring(1, inStr.length())));
                if (inStr.trim().substring(0, 1).equalsIgnoreCase("r"))
                    System.out.println("confused tranno" + NumberConfuser.confuse(inStr.substring(1, inStr.length())));
                else if (inStr.equalsIgnoreCase("?")) {
                    System.out.println("1. If u wanna get a real tranno, pls type c+tranno, e.g. c231231 then app will give u a reply: original tranno: 100001.");
                    System.out.println("2. If u wanna get a confused tranno, pls type r+tranno, e.g. r100001 then app will give u a reply: confused tranno: 231231.");
                    System.out.println("3. To quit, pls type quit.");
                } else if (inStr.equalsIgnoreCase("quit"))
                    System.exit(0);
            } catch (IOException e) {
                System.out.println("input error!");
            }


        }


//    	String a = "682860";
//<<<<<<< .mine
//        a = "180763";
//        String confuseredNumber = NumberConfuser.confuse(a);
//        System.out.println(confuseredNumber);
//        System.out.println(NumberConfuser.confuse(a));

//        System.out.println("xx:"+NumberConfuser.defuse("53901006"));
        //System.out.println("123".substring(1, 2));
//    	System.out.println("原始数据为:"+ a);
        //      System.out.println("打乱数据为:"+ NumberConfuser.confuse(a));
//        confuseredNumber = "51243610";
//        confuseredNumber = "34721051";
//        confuseredNumber = "83105144";
//        confuseredNumber = "83165144";
//        confuseredNumber = "38105184";
//        System.out.println("回复数据为:"+ NumberConfuser.defuse(confuseredNumber));
//        Random rand = new Random();
//=======
//    	String confuseredNumber = NumberConfuser.confuse(a);
//    	//System.out.println("123".substring(1, 2));
//    	System.out.println("原始数据为:"+ a);
//        System.out.println("打乱数据为:"+ NumberConfuser.confuse(a));
//
//        confuseredNumber = "10808640";
//        System.out.println("打乱数据为:"+ confuseredNumber);
//        System.out.println("回复数据为:"+ NumberConfuser.defuse(confuseredNumber));
////        Random rand = new Random();
//>>>>>>> .r107
//        for (int i = 0; i < 20; i++) {
//            Integer ig = new Integer(Math.abs(rand.nextInt()));
//            String tmp = ig.toString();
//            if(tmp.length() < 8) {
//                tmp = "0000000" + tmp;
//            }
//            tmp = tmp.substring(tmp.length()-8);
//            System.out.println("input: " + tmp);
//            String tmp1 = NumberConfuser.confuse(tmp);
//            String tmp2 = NumberConfuser.defuse(tmp1);
//            System.out.println("tmp2: " + tmp2 + " --> tmp1:  " + tmp1);
//            
//        }

    }
}
