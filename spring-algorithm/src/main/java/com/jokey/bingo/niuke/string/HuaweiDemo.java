package com.jokey.bingo.niuke.string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author Zhengjingfeng
 * @created 2020/7/26 14:21
 * @comment
 */
public class HuaweiDemo {

    private static Map<String, String> CHAR_MAP = new HashMap<>(26);

    static {
        CHAR_MAP.put("A", "Q");
        CHAR_MAP.put("B", "W");
        CHAR_MAP.put("C", "E");
        CHAR_MAP.put("D", "R");
        CHAR_MAP.put("E", "T");
        CHAR_MAP.put("F", "Y");
        CHAR_MAP.put("G", "U");
        CHAR_MAP.put("H", "I");
        CHAR_MAP.put("I", "O");
        CHAR_MAP.put("J", "P");
        CHAR_MAP.put("K", "A");
        CHAR_MAP.put("L", "S");
        CHAR_MAP.put("M", "D");
        CHAR_MAP.put("N", "F");
        CHAR_MAP.put("O", "G");
        CHAR_MAP.put("P", "H");
        CHAR_MAP.put("Q", "J");
        CHAR_MAP.put("R", "K");
        CHAR_MAP.put("S", "L");
        CHAR_MAP.put("T", "Z");
        CHAR_MAP.put("U", "X");
        CHAR_MAP.put("V", "C");
        CHAR_MAP.put("W", "V");
        CHAR_MAP.put("X", "B");
        CHAR_MAP.put("Y", "N");
        CHAR_MAP.put("Z", "M");
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String str;
        while ((str = reader.readLine()) != null) {
            String[] strArr = str.split(" ");
            StringBuilder sb = new StringBuilder();
            String c = ".";
            for (int i = 0; i < strArr.length; i++) {
                char[] chars = strArr[i].toUpperCase().toCharArray();
                for (int j = 0; j < chars.length; j++) {
                    if (chars[j] >= 'A' && chars[j] <= 'Z') {
                        if (Objects.equals(c, ".")) {
                            sb.append(CHAR_MAP.get(String.valueOf(chars[j])));
                        } else {
                            sb.append(CHAR_MAP.get(String.valueOf(chars[j])).toLowerCase());
                        }
                    }
                    if (Objects.equals(String.valueOf(chars[j]), ",") || Objects.equals(String.valueOf(chars[j]), ".")) {
                        sb.append(chars[j]);
                    }
                    c = chars[j] + "";
                }
                sb.append(" ");
            }

            System.out.println(sb.toString().trim());
        }
    }
}
