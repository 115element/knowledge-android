package com.example.knowledge_android.comparator;

public class NumberConfuser2 {

    public NumberConfuser2() {
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


}
