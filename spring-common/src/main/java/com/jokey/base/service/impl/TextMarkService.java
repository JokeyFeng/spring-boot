package com.jokey.base.service.impl;

import com.jokey.base.service.MarkService;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * 图片添加一个水印文字
 *
 * @author JokeyFeng
 * date:2019/3/10
 * project:spring-boot
 * package:com.jokey.base.service.impl
 * comment:
 */
public class TextMarkService implements MarkService {

    @Override
    public String waterMark(File image, String imageFileName, String uploadPath, String realUploadPath) {

        String logoFileName = "logo_" + imageFileName;
        OutputStream os = null;

        try {
            Image image2 = ImageIO.read(image);
            int width = image2.getWidth(null);
            int height = image2.getHeight(null);

            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImage.createGraphics();
            g.drawImage(image2, 0, 0, width, height, null);
            g.setFont(new Font(FONT_NAME, FONT_STYLE, FONT_SIZE));
            g.setColor(FONT_COLOR);

            //获取水印文本的高度和宽度值
            int width1 = FONT_SIZE * getTextLength(MARK_TEXT);
            int height1 = FONT_SIZE;

            int widthDiff = width - width1;
            int heightDiff = height - height1;

            int x = X > widthDiff ? widthDiff : X;
            int y = Y > heightDiff ? heightDiff : Y;

            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, ALPHA));
            //g.drawString(MARK_TEXT, x, y + FONT_SIZE)
            g.drawString(MARK_TEXT, x, y);
            g.dispose();

            os = new FileOutputStream(realUploadPath + "/" + logoFileName);
            JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(os);
            en.encode(bufferedImage);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
        }

        return uploadPath + "/" + logoFileName;
    }
}
