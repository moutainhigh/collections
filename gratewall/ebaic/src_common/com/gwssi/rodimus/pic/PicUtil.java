package com.gwssi.rodimus.pic;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.gwssi.rodimus.pic.domain.ImageWidget;
import com.gwssi.rodimus.pic.domain.TextWidget;

/**
 * 图片处理工具类。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class PicUtil {
	
	/**  
     * 给图片添加水印、可设置水印图片旋转角度  
     * @param iconPath 水印图片路径  
     * @param srcImgPath 源图片路径  
     * @param targerPath 目标图片路径  
     * @param degree 水印图片旋转角度  
     */  
    public static void genCertPicture(File bg,List<TextWidget> strings,List<ImageWidget> images,String targerPath) {   
        OutputStream os = null;   
        try {   
        	
        	// 1、 准备原图像
            Image srcImg = ImageIO.read(bg);   
  
            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null), 
            		srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);   
  
            // 1.1、得到画笔对象   
            // Graphics g= buffImg.getGraphics();   
            Graphics2D g = buffImg.createGraphics();   
  
            // 1.2、设置对线段的锯齿状边缘处理   
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,    
            		RenderingHints.VALUE_INTERPOLATION_BILINEAR);   
  
            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg   
                    .getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);   
  
            // 2、写文字
            if(strings!=null){
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));  
            	g.setColor(Color.BLACK);
            	for(TextWidget text : strings){
            		g.setFont(text.getFont());
            		g.drawString(text.getText(), text.getX(), text.getY());
            	}
            }
            // 3、绘制图像，水印图象的路径 水印一般为gif或者png的，这样可设置透明度   
            if(images!=null){
            	for(ImageWidget image : images){
		            ImageIcon imgIcon = new ImageIcon(image.getSrc());   // 得到Image对象。   
		            Image img = imgIcon.getImage();   
		            float alpha = image.getAlpha(); // 透明度   
		            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,  alpha));   
		            // 表示水印图片的位置   
		            g.drawImage(img, image.getX(), image.getY(), null);   
            	}
            }
           
            // 4、结束处理
            g.dispose(); 
            os = new FileOutputStream(targerPath); 
            ImageIO.write(buffImg, "JPG", os); 
        } catch (Exception e) {   
            e.printStackTrace();   
        } finally {   
            try {   
                if (null != os)   
                    os.close();   
            } catch (Exception e) {   
                e.printStackTrace();   
            }   
        }   
    }  
}
