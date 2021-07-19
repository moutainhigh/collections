package com.gwssi.validate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;


@Controller
@RequestMapping("codes")
public class CodeValidate {

	private Color getRandColor(int fc, int bc) {
		   Random random = new Random();
	        if (fc > 255)
	            fc = 255;
	        if (bc > 255)
	            bc = 255;
	        int r = fc + random.nextInt(bc - fc);
	        int g = fc + random.nextInt(bc - fc);
	        int b = fc + random.nextInt(bc - fc);
	        return new Color(r, g, b);
		
	}
	
	@RequestMapping(value = "getCode")
	public void getCode1(HttpServletRequest req, HttpServletResponse response)
			throws IOException {
		// 设置页面不缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		// 在内存中创建图象
		int width = 120, height = 30;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		// 获取图形上下文
		Graphics2D g = (Graphics2D) image.getGraphics();

		// 生成随机类
		Random random = new Random();

		// 设定背景色
		g.setColor(getRandColor(240, 250));
		g.fillRect(0, 0, width, height);

		/*
		 * GraphicsEnvironment graph =
		 * GraphicsEnvironment.getLocalGraphicsEnvironment(); String[]
		 * fontFamilies = graph.getAvailableFontFamilyNames(); for (String s :
		 * fontFamilies) { System.out.println(s); }
		 */

		// 这里很重要 你的环境里所拥有的汉字字体 不然汉字会乱码
		Font font = new Font("宋体", Font.PLAIN, 25);
		// //设定字体
		g.setFont(font);

		// 随机产生90条干扰线，更改getRandColor（）方法中的参数可以改变干扰线的粗细程度
		g.setColor(getRandColor(90, 230));
		for (int i = 0; i < 90; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(1200);
			int yl = random.nextInt(1200);
			g.drawLine(x, y, x + xl, y + yl);
		}

		// 验证码，由2个一位数的加减乘三种运算法构成
		int num1 = (int) (Math.random() * 10) + 1;
		int num2 = (int) (Math.random() * 10) + 1;
		int funNo = random.nextInt(3); // 产生[0,2]之间的随机整数
		String funMethod = "";
		int result = 0;
		switch (funNo) {
		case 0:
			funMethod = "加";
			result = num1 + num2;
			break;
		case 1:
			funMethod = "减";
			result = (num1 - num2) > 0 ? (num1 - num2) : (num2 - num1);
			break;
		case 2:
			funMethod = "乘";
			result = num1 * num2;
			break;
		}

		String calc = funMethod == "减" ? ((num1 - num2) > 0 ? (num1 + funMethod
				+ num2 + "=?") : (num2 + funMethod + num1 + "=?")) : (num1
				+ funMethod + num2 + "=?");
		g.setColor(new Color(20 + random.nextInt(110),
				20 + random.nextInt(110), 20 + random.nextInt(110)));

		FontRenderContext context = g.getFontRenderContext();
		Rectangle2D bounds = (font).getStringBounds(calc, context);
		double x = (width - bounds.getWidth()) / 2;
		double y = (height - bounds.getHeight()) / 2;
		double ascent = -bounds.getY();
		double baseY = y + ascent;

		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.drawString(calc, (int) x, (int) baseY);

		// 将生成的验证码存入SESSION
		HttpSession session = req.getSession();
		// System.out.println(("生成验证码[{}] ,结果为:[{}]",calc,result);
		System.out.println("生成验证码[{"+calc+"}] ,结果为:[{"+ result+"}]");
		session.setAttribute("certCode", String.valueOf(result));
		// 图象生效
		g.dispose();
		// 输出图象到页面
		ImageIO.write(image, "JPEG", response.getOutputStream());
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}

	

	/**
	 * 验证码校验
	 * 
	 * @param code
	 * @return
	 * @throws OptimusException 
	 */
	@RequestMapping(value = "checkCode")
	public void getCode1(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String code = req.getParameter("code");
		String sessionCode = (String) req.getHttpRequest().getSession().getAttribute("certCode");
		if(code!=null){
			if(code.equals(sessionCode)){
				resp.addAttr("msg", "success");
			}else{
				resp.addAttr("msg", "fail");
			}
		}else{
			resp.addAttr("msg", "fail");
		}
	}
	
	
}
