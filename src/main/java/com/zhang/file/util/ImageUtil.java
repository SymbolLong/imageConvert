package com.zhang.file.util;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 图片处理类
 *
 * @author zhangsl 2018/10/7 1:41 PM
 */
public class ImageUtil {

    public static void main(String[] args) throws IOException {

//        String src = "/Volumes/SL-Zhang/test/";
//        String target = "/Volumes/SL-Zhang/down/";
//        try {
//            File[] files = new File(src).listFiles();
//            for (File file : files) {
//                String fileName = file.getName().replace(".png", "");
//                if (!fileName.startsWith(".")) {
//                    toJPG(src + fileName + ".png", target + fileName + ".jpg");
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
// --------pdf to png--------
        String src = "/Users/zhangsl/Downloads/卡尔卡西古典吉他教程/卡尔卡西古典吉他教程.pdf";
        String target = "/Users/zhangsl/Downloads/卡尔卡西古典吉他教程/";
        for (int i = 1; i < 178; i++) {
            pdf2png(src, target + i + ".png", i);
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

    private static void pdf2png(String src, String target, int pageNumber) {
        File file = new File(src);
        PDFFile pdffile = null;
        // set up the PDF reading
        try {
            RandomAccessFile raf = new RandomAccessFile(file, "r");
            FileChannel channel = raf.getChannel();
            ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0,
                    channel.size());
            pdffile = new PDFFile(buf);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (pageNumber > pdffile.getNumPages()) {
            return;
        }

        // print 出该 pdf 文档的页数
        System.out.println(pdffile.getNumPages());

        // 设置将第 pagen 页生成 png 图片
        PDFPage page = pdffile.getPage(pageNumber);

        // create and configure a graphics object
        int width = (int) page.getBBox().getWidth();
        int height = (int) page.getBBox().getHeight();
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        //g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // do the actual drawing
        PDFRenderer renderer = new PDFRenderer(page, g2, new Rectangle(0, 0, width, height), null, Color.WHITE);
        try {
            page.waitForFinish();
        } catch (Exception e) {
            e.printStackTrace();
        }
        renderer.run();
        g2.dispose();

        try {
            ImageIO.write(img, "png", new File(target));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}


