package com.zhang.file.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 图片处理类
 *
 * @author zhangsl 2018/10/7 1:41 PM
 */
public class ImageUtil {

    public static void main(String[] args) throws IOException {

        String src = "/Volumes/SL-Zhang/test/";
        String target = "/Volumes/SL-Zhang/down/";
        try {
            File[] files = new File(src).listFiles();
            for (File file : files) {
                String fileName = file.getName().replace(".png", "");
                if (!fileName.startsWith(".")) {
                    toJPG(src + fileName + ".png", target + fileName + ".jpg");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private static void toJPG(String src, String target) {
        try {
            //read image file      
            BufferedImage bufferedImage = ImageIO.read(new File(src));
            //create a blank, RGB, same width and height, and a white background
            BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
                    bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            //TYPE_INT_RGB:创建一个RBG图像，24位深度，成功将32位图转化成24位
            newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
            // write to jpeg file 
            ImageIO.write(newBufferedImage, "jpg", new File(target));
            System.out.println(target + "convert done !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}


