package com.sender.mail.ftl;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.sender.mail.ftl.model.UserMailModel;

import freemarker.template.Configuration;
import freemarker.template.Template;
//http://blog.csdn.net/u012560410/article/details/38018195
//http://blog.csdn.net/ajun_studio/article/details/7347644
//http://lvdong5830.iteye.com/blog/1763026
//https://www.oschina.net/code/snippet_615528_35332
//https://my.oschina.net/zhengweishan/blog/364578
//http://yingzhuo.iteye.com/blog/1879950
//http://blog.csdn.net/qq_23039605/article/details/52452806
///http://www.open-open.com/code/view/1420624421796
//http://blog.csdn.net/zdp072/article/details/32745335
//http://www.cnblogs.com/YuanSong/p/3877303.html
//@Service("templateSendEmail")


public class TemplateSendEmail {
	private static JavaMailSender sender = null;
	private static Configuration freemarkerConfiguration = null;
	private String fromMail = "";

	
	// 通过模板构造邮件内容，参数username将替换模板文件中的${username}标签。
	private String getMailText(UserMailModel user,String templateFtl) {
		String htmlText = "";
		try {
			// 通过指定模板名获取FreeMarker模板实例
//			Template tpl = freemarkerConfiguration.getTemplate("registerUser.ftl");
			Template tpl = freemarkerConfiguration.getTemplate(templateFtl);
			// FreeMarker通过Map传递动态数据
			Map map = new HashMap();
			map.put("userName", user.getUserName());
			map.put("loginName", user.getLoginName());
			map.put("userIpArea", user.getIpArea());
			// 解析模板并替换动态数据，最终username将替换模板文件中的${username}标签。
			htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(tpl,map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return htmlText;
	}

	// 发送模板邮件
	public boolean sendTemplateMail(UserMailModel user,String title,String templateFtl) throws MessagingException {
		MimeMessage msg = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, false, "utf8");// 由于是html邮件，不是mulitpart类型
		//helper.setFrom(user.getEmailAddress());
		helper.setFrom(fromMail);
		helper.setTo(user.getEmailAddress());
		helper.setSubject(title);
		String htmlText = getMailText(user,templateFtl);// 使用模板生成html邮件内容
		helper.setText(htmlText, true);
		sender.send(msg);
		return true;
	}

	
	
	
	public void setFreemarkerConfiguration(Configuration freemarkerConfiguration) {
		this.freemarkerConfiguration = freemarkerConfiguration;
	}

	public void setSender(JavaMailSender sender) {
		this.sender = sender;
	}

	public void setFromMail(String fromMail) {
		this.fromMail = fromMail;
	}
	
	
}