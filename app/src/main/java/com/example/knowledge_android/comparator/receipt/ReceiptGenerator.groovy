package com.example.knowledge_android.comparator.receipt

import com.example.knowledge_android.comparator.CreamFormatter


class ReceiptGenerator {
    String prefixOfLine = ''
    //一行打印字符数
    int charSizeOfLine
//    StringWriter stringWriter
    List<String> receiptContent
    String enterStr = "\r\n"

    /**
     * 打印机类型
     * exe1=通过exe打印
     * server2=通过socket打印
     * jpos=通过jpos驱动打印
     */
    private String printerType

    ReceiptGenerator(int charSizeOfLine, String printerType, String prefixOfLine = '', String enterStr = "\r\n") {
        this.charSizeOfLine = charSizeOfLine
        this.printerType = printerType
        this.prefixOfLine = prefixOfLine
        this.enterStr = enterStr
//        stringWriter = new StringWriter()
        receiptContent = new ArrayList()

    }

    /**
     * 获取定长的字符串
     * @param str 原始字符串
     * @param maxLen 最终需要的长度
     * @param leftAppend 是否左填充
     * @return 固定长度的字符串
     */
    //给定字符串截取为指定长度（一个汉字两个字节） 如果位数不够，是否左边填充空格
    String getFixedLenString(String str, int maxLen, boolean leftAppend) {
        StringBuilder sb = new StringBuilder();
        int pre_len = 0
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i)
            if ((int) c > 255) { // Chinese char
                pre_len += 2
            } else {
                pre_len++
            }
            if (pre_len > maxLen) {
                break
            } else {
                sb.append(c)
            }
        }
        if (leftAppend) {
            leftFillString(sb.toString(), maxLen)
        } else {
            rightFillString(sb.toString(), maxLen)
        }
    }

    private int getPrintLength(String data) {
        int len = 0;
        for (int i = 0; i < data.length(); i++) {
            char c = data.charAt(i)
            if ((int) c > 255) { // Chinese char
                len += 2
            } else {
                len++
            }
        }
        return len
    }

    private List<String> parseLine(String data, int lineCharSize = charSizeOfLine) {
        if (!data)
            return null
        List<String> lines = []
        String line = ''
        int len = 0
        for (int i = 0; i < data.length(); i++) {
            int c_l = 1
            char c = data.charAt(i)
            if ((int) c > 255) { // Chinese char
                c_l = 2
                len += 2
            } else {
                len++
            }
            if (len <= lineCharSize) {
                line += c
            } else {
                lines << line
                line = c
                len = c_l
            }
        }
        if (line) {
            lines << line
        }
        lines
    }

    String centerLineWithFontSize(String data, int fontSize) {
        int lenSize = charSizeOfLine / fontSize
        String line = ''
        int padSize = (getPrintLength(data) % lenSize) / 2
        int fillSpace = 0  //填充几个空格
        if (padSize > 0) {
            fillSpace = lenSize / 2 - padSize
        }
        if (fillSpace > 0) {
            line = appendPad(data, true, fillSpace)
        }
        line
    }

    List<String> centerLine(String data, int lineCharSize = charSizeOfLine) {
        List<String> lines = parseLine(data, lineCharSize)
        if (lines) {
            if (lines.size() == 1) {
                String line = lines[0]
                int padSize = (getPrintLength(line) % lineCharSize) / 2
                int fillSpace = 0  //填充几个空格
                if (padSize > 0) {
                    fillSpace = lineCharSize / 2 - padSize
                }
                if (fillSpace > 0) {
                    line = appendPad(line, true, fillSpace)
                }
                lines[0] = line
            } else {
                String line = lines[lines.size() - 1]
                lines[lines.size() - 1] = rightFillString(line)
            }
        }
        lines
    }

    String rightFillString(String data, int maxLen = charSizeOfLine) {
        int padSize = maxLen - getPrintLength(data)
        appendPad(data, false, padSize)
    }

    String leftFillString(String data, int maxLen = charSizeOfLine) {
        int padSize = maxLen - getPrintLength(data)
        appendPad(data, true, padSize)
    }

    String format(Date date, String patten) {
        CreamFormatter formatter = new CreamFormatter()
        formatter.format(date, patten)
    }

    String format(int number, String patten) {
        CreamFormatter formatter = new CreamFormatter()
        formatter.format(number, patten)
    }

    String format(BigDecimal number, String patten) {
        CreamFormatter formatter = new CreamFormatter()
        formatter.format(number, patten)
    }

    String formatBigDecimal(BigDecimal number, String patten) {
        CreamFormatter formatter = new CreamFormatter()
        formatter.format(number, patten)
    }

    /**
     * 打印图片
     * @param arg 图片路径+文件名
     */
    void writeBmp(String arg) {
        String tag = ""
        if (printerType == 'exe1') {
            tag = "[bmpT]"
        } else {
            tag = "[bmp]"
        }
//        stringWriter.write(tag + arg + "\r\n")
        receiptContent.add(tag + arg + enterStr)
    }

    /**
     * 打印条码
     *
     * @param arg
     *      printerType=exe1/server2时 arg 图片路径+文件名
     *      printerType=jpos时 arg barcode值
     */
    void writeBarcode(String key, String arg) {
        if (printerType == 'exe1' || printerType == 'server2') {
            writeBmp(arg);
            return
        } else if (printerType == 'jpos') {
//            stringWriter.write("[barcode]" + arg + "\r\n")
            receiptContent.add("[barcode]" + arg)
        }
    }

    /**
     * 打印二维码
     *
     * @param arg
     *      printerType=exe1/server2时 arg 图片路径+文件名
     *      printerType=jpos时 arg qrcode值
     */
    void writeQrcode(String arg) {
        if (printerType == 'exe1' || printerType == 'server2') {
            writeBmp(arg);
            return
        } else if (printerType == 'jpos') {
//            stringWriter.write("[qr]" + arg + "\r\n")
            receiptContent.add("[qr]" + arg)
        }
    }

    void writeLR(String leftArg, String rightArg) {
        int leftSize = getPrintLength(leftArg)
        int rightSize = getPrintLength(rightArg)
        if (leftSize + rightSize == charSizeOfLine) {
            writeln leftArg + rightArg
        } else if (leftSize + rightSize < charSizeOfLine) {
            int padSize = charSizeOfLine - leftSize - rightSize
            writeln leftArg + appendPad(rightArg, true, padSize)
        } else {
            writeln leftArg + ' ' + rightArg
        }
    }

    void writeln(String... args) {
        String line = ''
        args.each { line += it }
        List<String> lines = parseLine(line)
        lines.each {
//            stringWriter.write(it + "\r\n")   // \r\n 代表回车 换行
            receiptContent.add(prefixOfLine + it + enterStr)
        }
    }

    /*1.不换行写入*/

//    void write(String... args) {
//        String line = ''
//        args.each { line += it }
//        List<String> lines = parseLine(line)
//        lines.each {
//            stringWriter.write(it)
//        }
//    }

    /**2.写入换行**/
    void writeNewLine() {
//        stringWriter.write("  " + "\r\n")
        receiptContent.add(prefixOfLine + "  " + enterStr)
    }

    /*3.写入中间*/

    void writeCenter(String center) {
        List<String> s = centerLine(center)
        println(s)
        s.each {
            writeln(it)
        }
    }

    void writeCenter(String format, String center, int lineCharSize) {
        List<String> s = centerLine(center, lineCharSize)
        println(s)
        s.each {
            writeln(format + it)
        }
    }

    /**4.写入几个换行**/
    void writeCountNewLine(int count) {
        for (int i = 0; i < count; i++) {
//            stringWriter.write("  " + "\r\n")
            receiptContent.add(prefixOfLine + "  " + enterStr)
        }
    }

    /**5.写入一条实横线**/
    void writeSolidLine() {
        StringBuilder builder = new StringBuilder()
        for (int i = 0; i < charSizeOfLine; i++) {
            builder.append("-")
//            stringWriter.write("━")
        }
//        stringWriter.write(builder.toString() + "━" + "\r\n")
        receiptContent.add(prefixOfLine + builder.toString() + "━" + enterStr)
    }

    /**6.写入一条虚横线**/
    void writeDashedLine() {
        StringBuilder builder = new StringBuilder()
        for (int i = 0; i < charSizeOfLine; i++) {
            builder.append("-")
//            stringWriter.write("━ ")
        }
//        stringWriter.write("━" + "\r\n")
//        stringWriter.write(builder.toString() + "━" + "\r\n")
        receiptContent.add(prefixOfLine + builder.toString() + enterStr)
    }

    /**7.写入一条*线**/
    void writeFiveStarsLine() {
        StringBuilder builder = new StringBuilder()
        for (int i = 0; i < charSizeOfLine; i++) {
            builder.append("*")
//            stringWriter.write("*")
        }
//        stringWriter.write("*" + "\r\n")
//        stringWriter.write(builder.toString() + "━" + "\r\n")
        receiptContent.add(prefixOfLine + builder.toString() + "━" + enterStr)
    }

    /**8.获取固定长度位数的金额**/
    String getFixedLengthBigDecimal(BigDecimal bigDecimal, int length) {
        int q = bigDecimal.toString().length()
        if (q == length) {
            return bigDecimal.toString()
        }

        int w = length - q

        if (w > 0) {
            StringBuilder s1 = new StringBuilder(bigDecimal.toString())
            for (int i = 0; i < Math.abs(w); i++) {
                s1.insert(0, ' ')
            }
            return s1.toString()
        } else {
            StringBuilder s2 = new StringBuilder(bigDecimal.toString())
            for (int i = 0; i < Math.abs(w); i++) {
                s2.append(' ')
            }
            return s2.toString()
        }

    }

    /*9.获取 合计 优惠 应收 三个中长度最长的*/

    int getMaxLength(BigDecimal bigDecimal1, BigDecimal bigDecimal2, BigDecimal bigDecimal3) {
        int x = 0

        int a = bigDecimal1.toString().length()
        int b = bigDecimal2.toString().length()
        int c = bigDecimal3.toString().length()

        if (a > b) {
            x = a
        } else if (a < b) {
            x = b
        } else {
            x = a
        }

        if (x > c) {
            return x
        } else if (x < c) {
            return c
        } else {
            return c
        }
    }

    /*9.获取 合计 优惠 应收 三个中长度最长的[字符串]*/

    int getMaxStringLength(String str1, String str2, String str3) {
        int x = 0

        int a = str1.length()
        int b = str2.length()
        int c = str3.length()

        if (a > b) {
            x = a
        } else if (a < b) {
            x = b
        } else {
            x = a
        }

        if (x > c) {
            return x
        } else if (x < c) {
            return c
        } else {
            return c
        }
    }


    private String appendPad(String data, boolean left, int size) {
        if (data == null) {
            return ''
        }
        if (size == 0) {
            return data
        }
        (1..size).each {
            if (left) {
                data = ' ' + data
            } else {
                data += ' '
            }
        }
        data
    }

//    /**
//     * 查看图片文件是否存在
//     * @param path
//     * @param fileName
//     * @return
//     */
//    abstract boolean fileExist(String path, String fileName)
//
//    /**
//     * 图片存放路径
//     * @return
//     */
//    abstract File getImageRootPath()
//
//    /**
//     * 云客券图片路径
//     * @return
//     */
//    abstract File getTicketGrantsImagePath()
//
//    /**
//     * 二维码图片存放路径
//     * @return
//     */
//    abstract File getQrImageRootPath()

//    abstract String generatorBmpLine(String arg)
//
//    abstract String generatorBarcodeLine(String key, String qrcode)
//
//    abstract String generatorQrcodeLine(String key, String qrcode)

    static void main(String[] args) {
//        ReceiptGenerator r = new ReceiptGenerator(34, null)
//        // String s = receiptGenerator.getFixedLenString('上好佳芝士条', 16, false)
//        //println r.getFixedLengthBigDecimal(new BigDecimal('0.00'),1)
//        println r.getMaxLength(new BigDecimal('0.90'), new BigDecimal('3.999'), new BigDecimal('6'))
    }

}
