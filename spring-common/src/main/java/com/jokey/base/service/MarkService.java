package com.jokey.base.service;

import java.awt.*;
import java.io.File;

/**
 * @author JokeyFeng
 * date:2019/3/10
 * project:spring-boot
 * package:com.jokey.base.service
 * comment:
 */
public interface MarkService {

    public static final String MARK_TEXT = "小冰狗";
    public static final String FONT_NAME = "微软雅黑";
    public static final int FONT_STYLE = Font.BOLD;
    public static final int FONT_SIZE = 120;
    public static final Color FONT_COLOR = Color.BLACK;

    public static final int X = 10;
    public static final int Y = 10;
    public static final float ALPHA = 0.3F;

    public static final String LOGO = "logo.png";

    /**
     * 文字水印
     *
     * @param image
     * @param imageFileName
     * @param uploadPath
     * @param realUploadPath
     * @return
     */
    String waterMark(File image, String imageFileName, String uploadPath, String realUploadPath);

    /**
     * 获取长度
     *
     * @param text
     * @return
     */
    default int getTextLength(String text) {
        int length = text.length();

        for (int i = 0; i < text.length(); i++) {
            String s = String.valueOf(text.charAt(i));
            if (s.getBytes().length > 1) {
                length++;
            }
        }

        return length % 2 == 0 ? length / 2 : length / 2 + 1;
    }

}
