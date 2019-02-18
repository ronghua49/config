package com.haohua.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author L-heng
 */
public class ChartGraphics {
    private BufferedImage image;
    private int imageWidth = 500; //图片的宽度
    private int imageHeight = 100; //图片的高度

    //生成图片文件
    public String createImage(String fileLocation) {
        BufferedOutputStream bos = null;
        if (image != null) {
            try {
                FileOutputStream fos = new FileOutputStream(fileLocation);
                bos = new BufferedOutputStream(fos);
                //保存新图片
                ImageIO.write(image, "JPG", bos);
                bos.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bos != null) {//关闭输出流
                    try {
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return fileLocation;
    }

    public String graphicsGeneration(String name, String imgurl) {
        image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        //设置图片的背景色
        Graphics2D main = image.createGraphics();
        main.setColor(Color.white);
        main.fillRect(0, 0, imageWidth, imageHeight);

        Graphics2D tip = image.createGraphics();

        //设置区域颜色
        tip.setColor(Color.WHITE);
        //填充区域并确定区域大小位置
        tip.fillRect(0, 0, imageWidth, imageHeight);
        //设置字体颜色，先设置颜色，再填充内容
        tip.setColor(Color.RED);
        //设置字体
        Font tipFont = new Font("/simsun.ttf", Font.BOLD, 24);
        tip.setStroke(new BasicStroke(1));
        tip.setFont(tipFont);
        tip.drawString(name, (imageWidth - (name.length() * 24)) / 2 + 0, (imageHeight - 24) / 2 + 10);
        tip.drawString("声明有效", (imageWidth - (4 * 24)) / 2 + 0, (imageHeight - 24) / 2 + 40);
        tip.setStroke(new BasicStroke(8));
//        tip.setColor(Color.RED);
        tip.drawRoundRect(0, 0, imageWidth - 1, imageHeight - 1, 40, 40);
        return createImage(imgurl);
    }

/*
    public static void main(String[] args) {
        ChartGraphics g = new ChartGraphics();
        g.graphicsGeneration("中科白城科技（北京）股份有限公司", "/file-path/112.jpg");
    }*/
}
