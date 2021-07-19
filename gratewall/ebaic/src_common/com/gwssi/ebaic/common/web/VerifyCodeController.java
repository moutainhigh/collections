package com.gwssi.ebaic.common.web;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.rodimus.util.HttpSessionUtil;

/**
 * 图片验证码。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Controller
@RequestMapping("/system")
public class VerifyCodeController {
	
	@RequestMapping("/getVerifyCode")
	@ResponseBody
	public void getVerifyCode(HttpServletRequest request,HttpServletResponse response) throws IOException{
        //设置页面不缓存   
        response.setHeader("Pragma", "No-cache");  
        response.setHeader("Cache-Control", "no-cache"); 
        response.setDateHeader("Expires", 0);  
        //在内存中创建图像  
        int width = 60;  
        int height = 25;  
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);  
        //获取图形上下文  
        Graphics g = image.getGraphics();  
        //随机类  
        Random random = new Random();  
        //设定背景  
        g.setColor(getRandColor(200, 250));  
        g.fillRect(0, 0, width, height);  
        //设定字体  
        g.setFont(new Font("Times New Roman",Font.PLAIN,18));  
       //随机产生干扰线  
       g.setColor(getRandColor(160, 200));     
       for (int i = 0; i < 100; i++) {     
            int x = random.nextInt(width);     
            int y = random.nextInt(height);     
            int xl = random.nextInt(12);     
            int yl = random.nextInt(12);     
            g.drawLine(x, y, x + xl, y + yl);     
       }   
       //随机产生4位验证码  
       String[] codes = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","J","K","L","M","N","P","Q","R","S","T","U","V","W","X","Y","Z"};  
       String code = "";  
       for(int i=0;i<4;i++){  
           String str = codes[random.nextInt(codes.length)];  
           code += str;  
           // 将认证码显示到图象中  
           g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));  
           //调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成     
           g.drawString(str, 13 * i + 6, 18);     
       }  
        // 将认证码存入SESSION     
       //HttpSession session=WebContext.getHttpSession();
       HttpSession session=HttpSessionUtil.getSession();
       session.setAttribute("verify_code", code);  
       // 图象生效     
       g.dispose();     
       // 输出图象到页面     
       ImageIO.write(image, "JPEG", response.getOutputStream());  
       //加上下面代码,运行时才不会出现java.lang.IllegalStateException: getOutputStream() has already been called ..........等异常  
       response.getOutputStream().flush();    
       response.getOutputStream().close();    
       response.flushBuffer();    
//       out.clear();    
//       out = pageContext.pushBody();   
	}
	//获取随机颜色  
	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255){
			fc = 255;
		}
		if (bc > 255){
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	} 
	@RequestMapping("/getSecurityCode")
	@ResponseBody
	public void getSecurityCode(HttpServletRequest request,HttpServletResponse response) throws IOException{
		 //设置页面不缓存   
        response.setHeader("Pragma", "No-cache");  
        response.setHeader("Cache-Control", "no-cache"); 
        response.setDateHeader("Expires", 0);  
        //在内存中创建图像  
        int width = 90;  
        int height = 25;  
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);  
        //获取图形上下文  
        Graphics g = image.getGraphics();  
        //随机类  
        Random random = new Random();  
        //设定背景  
        g.setColor(getRandColor(200, 250));  
        g.fillRect(0, 0, width, height);  
        //设定字体  
        g.setFont(new Font("Times New Roman",Font.PLAIN,18));  
       //随机产生干扰线  
       g.setColor(getRandColor(160, 200));     
       for (int i = 0; i < 100; i++) {     
            int x = random.nextInt(width);     
            int y = random.nextInt(height);     
            int xl = random.nextInt(12);     
            int yl = random.nextInt(12);     
            g.drawLine(x, y, x + xl, y + yl);     
       }   
       //随机产生4位验证码  
       String[] codes = new String[5];  
       int param1=random.nextInt(90)+10;
       int param2=0;
       int value=0;
       codes[0]=String.valueOf(param1);
       if(random.nextBoolean()){
    	   codes[1]=" +";
    	   param2=random.nextInt(90)+10;
    	   codes[2]=String.valueOf(param2);
    	   value=param1+param2;
    	   
       }else{
    	   codes[1]=" -";
    	   param2=random.nextInt(param1);
    	   codes[2]=String.valueOf(param2);
    	   value=param1-param2;
       }
       codes[3]="=";
       codes[4]="?";
       for(int i=0;i<5;i++){ 
           // 将认证码显示到图象中  
           g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));  
           //调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成     
           g.drawString(codes[i], 18 * i +(i%2)+6, 18);  
       }  
        // 将认证码存入SESSION     
       HttpSession session=HttpSessionUtil.getSession();
       session.setAttribute("security_code", String.valueOf(value));  
       // 图象生效     
       g.dispose();     
       // 输出图象到页面     
       ImageIO.write(image, "JPEG", response.getOutputStream());  
       //加上下面代码,运行时才不会出现java.lang.IllegalStateException: getOutputStream() has already been called ..........等异常  
       response.getOutputStream().flush();    
       response.getOutputStream().close();    
       response.flushBuffer(); 
	}
}
