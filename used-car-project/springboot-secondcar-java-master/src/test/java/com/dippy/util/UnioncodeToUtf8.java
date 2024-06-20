package com.dippy.util;

public class UnioncodeToUtf8 {

    public static String unicodeToUtf8(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't') {
                        aChar = '\t';
                    }
                    if (aChar == 'r') {
                        aChar = '\r';
                    }
                    if (aChar == 'n') {
                        aChar = '\n';
                    }
                    if (aChar == 'f') {
                        aChar = '\f';
                    }
                    outBuffer.append(aChar);
                }
            } else {
                outBuffer.append(aChar);
            }
        }
        return outBuffer.toString();
    }

    public static String utf8ToUnicode(String inStr) {
        char[] myBuffer = inStr.toCharArray();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < inStr.length(); i++) {
            Character.UnicodeBlock ub = Character.UnicodeBlock.of(myBuffer[i]);
            if (ub == Character.UnicodeBlock.BASIC_LATIN) {
                //英文及数字等
                sb.append(myBuffer[i]);
            } else if (ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
                //全角半角字符
                int j = (int) myBuffer[i] - 65248;
                sb.append((char) j);
            } else {
                //汉字
                short s = (short) myBuffer[i];
                String hexS = Integer.toHexString(s);
                String unicode = "\\u" + hexS;
                sb.append(unicode.toLowerCase());
            }
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        String s = "\\u5c0a\\u656c\\u7684\\u5ba2\\u6237\\uff1a\\u60a8\\u597d\\uff0c\\u6839\\u636e\\u4e2d\\u56fd\\u7ed3\\u7b97\\u516c\\u53f8\\u5bf9\\u975e\\u73b0\\u573a\\u5f00\\u6237\\u7684\\u8981\\u6c42\\uff0c\\u4e5f\\u4e3a\\u4e86\\u6211\\u53f8\\u80fd\\u7ed9\\u60a8\\u66f4\\u597d\\u7684\\u670d\\u52a1\\uff0c\\u8bf7\\u60a8\\u5728\\u5f00\\u6237\\u524d\\u914d\\u5408\\u5b8c\\u6210\\u5982\\u4e0b\\u95ee\\u5377\\uff0c\\u611f\\u8c22\\u60a8\\u7684\\u652f\\u6301\\uff01";
        System.out.println(s);
        String utf8 = "尊敬的客户：您好，根据中国结算公司对非现场开户的要求，也为了我司能给您更好的服务，请您在开户前配合完成如下问卷，感谢您的支持！";
        String s2 = utf8ToUnicode(utf8);
        System.out.println(s2);
        String s1 = unicodeToUtf8(s);
        System.out.println(s1);
    }

}
