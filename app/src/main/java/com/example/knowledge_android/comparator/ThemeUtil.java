package com.example.knowledge_android.comparator;

import android.graphics.Color;

import com.example.knowledge_android.knowledge.GUtil;

import java.util.Random;

public class ThemeUtil {
    public static int defaultTextColor = Color.WHITE;
    public static int disabledTextColor = Color.LTGRAY;
    public static float defaultTextSize = GUtil.sp2px(18);
    public static int defaultButtonColor = 0xff0d9f67;
    public static int defaultButtonTextColor = Color.WHITE;
    public static float defaultButtonTextSize = GUtil.sp2px(18);

    // DualLineItemList
    public static int pagingButtonBackground = color(255, 204, 51);
    public static float columnHeaderTextSize = GUtil.sp2px(24);
    public static int columnHeaderTextColor = Color.BLACK;
    public static int columnHeaderBackground = Color.WHITE;

    public static int historyLogTransactionLineColor = color(255, 0, 255);
    public static int darkGrayLineColor = color(169, 169, 169);

    public static int lineItemTextColor = Color.BLACK;
    public static int lineItemSelectedTextColor = Color.BLACK;
    public static int removedLineItemTextColor = Color.RED;
    public static float lineItemTextSize = GUtil.sp2px(14);
    public static int lineItemBackground = 0xfffffeee;
    public static int lineItemSelectedBackground = 0xF0C8C6C6;

    public static int unselectedLineItemBackground = Color.WHITE;
    public static int selectedLineItemBackground = 0xffdedede;

    public static String topCategoryButtonWidth = "84dp";

    public static String pagerTabStripHeight = "54dp";
    public static int pagerTabTextColor = 0xff555555;
    public static String pagerTabTextSize = "24sp";

    public static String takeOutPagerTabTextSize = "20sp";

    public static String pagerTabPadding = "18dp";
    public static int pagerTabDividerColor = 0xffd4d4cb;
    public static int pagerTabBackground = Color.rgb(241, 241, 241);

    public static int pluButtonPanelColumnCount = 3;
    public static String pluButtonPanelVerticalSpacing = "15dp";
    public static String pluButtonPanelHorizontalSpacing = "10dp";
    public static String pluButtonHeight = "300dp";
    public static float pluButtonNameTextSize = GUtil.sp2px(24);
    public static float pluButtonQtyTextSize = GUtil.sp2px(14);

    public static float qtyChangeViewFontSize = GUtil.sp2px(24);

    public static int salePanelTenderInfoBackfroundColor = Color.rgb(250, 240, 240);
    public static int tenderInfoPanelBackgroundColor = color(102, 169, 211);

    public static int adminPanelLeftButtonColor = Color.parseColor("#9370DB");
    public static int adminPanelRightButtonColor = Color.parseColor("#4169E1");

    public static int functionPanelLeftButtonColor = Color.parseColor("#9370DB");
    public static int functionPanelRightButtonColor = Color.parseColor("#4169E1");
    public static int functionButtonSelectedColor = Color.parseColor("#FF9977");

    public static int upInfoButtonOriginColor = Color.parseColor("#CD8500");
    public static int downInfoButtonOriginColor = Color.parseColor("#4FB991");
    public static int sotdownInfoButtonOriginColor = color(127, 69, 128);

    public static float settlementSize = GUtil.sp2px(10);
    //中文金钱符号
    public static String dough = "￥";

    //数字按钮键颜色
    public static String numberButtonsColor = "#966332";
    public static int numberButtonsColora = color(150, 99, 50);
    //交易金额区域背景颜色
    public static int tradingAreaColor = color(2, 56, 157);
    //功能键背景颜色
    public static String functionKeysColor = "#497C64";
    public static int functionKeysColora = color(72, 122, 93);

//    static Font lineItemPluNameStrikeThroughFont = createStrikeThroughFont(XXXFont)
//    static Font createStrikeThroughFont(Font font) {
//        Map<TextAttribute, Boolean> attributes = font.getAttributes() as Map<TextAttribute, Boolean>
//        attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON)
//        new Font(attributes)
//    }

    public static int color(int r, int g, int b) {
        return Color.rgb(r, g, b);
    }

    public static int color(int a, int r, int g, int b) {
        return Color.argb(a, r, g, b);
    }

    public static int getRandomColor() {
        float hue = new Random().nextFloat();
        return GUtil.convertFromHSBtoRGB(hue, 0.8f, 0.65f);
    }

    //字符串转颜色
    public static int color(String colorString) {
        try {
            int r = Integer.parseInt(colorString.substring(1, 3), 16);
            int g = Integer.parseInt(colorString.substring(3, 5), 16);
            int b = Integer.parseInt(colorString.substring(5, 7), 16);
            int a = 255;
            if (colorString.length() >= 9) {
                a = Integer.parseInt(colorString.substring(7, 9), 16);
            }
            return Color.argb(a, r, g, b);
        } catch (Exception e) {
            return Color.LTGRAY;
        }
    }
}
