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
 * 图片添加多个水印logo
 *
 * @author JokeyFeng
 * date:2019/3/10
 * project:spring-boot
 * package:com.jokey.base.service.impl
 * comment:
 */
public class MoreWaterMarkService implements MarkService {
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

            String logoPath = realUploadPath + "/" + LOGO;
            File logo = new File(logoPath);

            Image logoImage = ImageIO.read(logo);
            int width1 = logoImage.getWidth(null);
            int height1 = logoImage.getHeight(null);


            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, ALPHA));
            //旋转30度
            g.rotate(Math.toRadians(30), bufferedImage.getWidth() / 2, bufferedImage.getHeight() / 2);

            int x = -width / 2;
            while (x < width * 1.5) {
                int y = -height / 2;
                while (y < height * 1.5) {
                    g.drawImage(logoImage, x, y, null);

                    y += height1 + 300;
                }
                x += width1 + 300;
            }
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
