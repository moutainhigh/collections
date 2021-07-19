package com.ly.email;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ly.dao.impl.EmailRecevierDao;
import com.ly.dao.impl.EmailRecevierLogDao;
import com.ly.dao.impl.EmailSenderDao;
import com.ly.placeholder.DESUtils;
import com.ly.pojo.Email_Recevier;
import com.ly.pojo.Email_Recevier_Log;
import com.ly.pojo.Email_Sender;

/**
 * java实现邮箱发送邮件功能
 */
//https://www.cnblogs.com/ysocean/p/7666061.html
//https://blog.csdn.net/TangLingGuang/article/details/78419581
//https://www.cnblogs.com/luffyu/p/6146718.html
//https://www.cnblogs.com/x_wukong/p/4432420.html
//https://blog.csdn.net/u012187452/article/details/80557325
//https://www.baidu.com/s?ie=utf-8&f=3&rsv_bp=1&rsv_idx=1&tn=baidu&wd=javamail%20%20html%E6%A0%BC%E5%BC%8F&oq=javamail&rsv_pq=80670c3000075e92&rsv_t=b034dvWyplUCRtFu9m0BaSZjKuWCCbrFRhlMZmUnVn%2BLXm5%2FpFTLGGIGlB4&rqlang=cn&rsv_enter=1&sug=javamail&rsv_sug3=12&rsv_sug2=0&prefixsug=javamail%2520%2520html%25E6%25A0%25BC%25E5%25BC%258F&rsp=0&inputT=1103989&rsv_sug4=1103989&rsv_sug=1
@Component
public class MyJavaMailSenderUtil {
	
	
	private static Logger logger = Logger.getLogger("email");
	
	@Autowired
	private EmailSenderDao emailFormerDao;
	
	@Autowired
	private EmailRecevierDao emailDao;

	@Autowired
	private EmailRecevierLogDao emailSendLogDao;
	
	
	public void sendEmail(String title,String contents,boolean flag) throws Exception {
		String hql = "from Email_Sender ";
		
		if(flag){
			hql+= " where  flag  = 0 ";
		}else{
			hql+= " where  flag  > 0 ";
		}
		
		Email_Sender fromer = emailFormerDao.find(hql).get(0);
		
		String from = fromer.getAddress();
	//	String pwd =  DESUtils.getDecryptString(fromer.getPwd());
		String pwd = fromer.getPwd();
		String protocol  =fromer.getProtocol();
		String port = fromer.getPort();
		String host  = fromer.getHost();
		
		
		Properties props = new Properties();
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.transport.protocol", protocol);
		props.put("mail.smtp.host", host);// smtp服务器地址
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.smtp.auth", "true"); // 需要请求认证
		props.setProperty("mail.smtp.ssl.enable", "false");// 不开启ssl
		props.setProperty("mail.smtp.port", port);

		Session session = Session.getInstance(props);
		//session.setDebug(true);

		
		

		Transport transport = session.getTransport();
		List<Email_Recevier> emails = emailDao.find("from Email_Recevier where isSend > 0");
		Email_Recevier_Log email_recevier_log = null;
		
		if (emails != null && emails.size() > 0) {
			for (int j = 0; j < emails.size(); j++) {
				Email_Recevier toEmails = emails.get(j);
				String toAddres = toEmails.getAddress();
				try {
					logger.debug("总共需要发的用户邮件的用户数为【" + emails.size()+ " 】个,当前是第【 "+(j+1) +"】位,发送的邮件地址为===>"+ toAddres);
					Message msg = new MimeMessage(session);
					msg.setSubject(title);
					msg.setText(contents);
					msg.setFrom(new InternetAddress(from));// 发件人邮箱(我的163邮箱)
					msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toAddres)); // 收件人邮箱(我的QQ邮箱)
					msg.saveChanges();

					
					transport.connect(from, pwd);// 发件人邮箱,授权码(可以在邮箱设置中获取到授权码的信息)

					transport.sendMessage(msg, msg.getAllRecipients());
				} catch (Exception e) {
					logger.debug("第【 "+(j+1) +"】位发送失败，发送失败的用户邮件地址为===> "  + toAddres + " ,错误信息为 ===> " + e.getMessage());
					e.printStackTrace();
					
					email_recevier_log = new Email_Recevier_Log();
					email_recevier_log.setAddress(toAddres);
					email_recevier_log.setMsg(e.getMessage());
					email_recevier_log.setTime(new Date().toLocaleString());
					email_recevier_log.setFlag("0");
					emailSendLogDao.save(email_recevier_log);
				}finally {
					transport.close();
				}
				
				if(j>0){
					Thread.sleep(1000*1);
				}
			}
		}
		
	}
	
	
	public void sendHtmlEmail(String title,String contents,boolean flag) throws Exception {
		String hql = "from Email_Sender";
		if(flag){
			hql+= " where  flag  = 0 ";
		}else{
			hql+= " where  flag  > 0 ";
		}
		Email_Sender fromer = emailFormerDao.find(hql).get(0);
		
		String from = fromer.getAddress();
		//String pwd =  DESUtils.getDecryptString(fromer.getPwd());
		String pwd = fromer.getPwd();
		String protocol  =fromer.getProtocol();
		String port = fromer.getPort();
		String host  = fromer.getHost();
		
		
		Properties props = new Properties();
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.transport.protocol", protocol);
		props.put("mail.smtp.host", host);// smtp服务器地址
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.smtp.auth", "true"); // 需要请求认证
		props.setProperty("mail.smtp.ssl.enable", "false");// 不开启ssl
		props.setProperty("mail.smtp.port", port);

		Session session = Session.getInstance(props);
		//session.setDebug(true);

		
		

		Transport transport = session.getTransport();
		List<Email_Recevier> emails = emailDao.find("from Email_Recevier where isSend > 0");
		Email_Recevier_Log emailSendLog = null;
		
		if (emails != null && emails.size() > 0) {
			for (int j = 0; j < emails.size(); j++) {
				Email_Recevier toEmails = emails.get(j);
				String toAddres = toEmails.getAddress();
				try {
					logger.debug("总共需要发的用户邮件的用户数为【" + emails.size()+ " 】个,当前是第【 "+(j+1) +"】位,发送的邮件地址为===>"+ toAddres);
					Message msg = new MimeMessage(session);
					msg.setSubject(title);
					 MimeBodyPart text = new MimeBodyPart();
					 
					text.setContent(contents, "text/html;charset=UTF-8");
					MimeMultipart mm = new MimeMultipart();
					
					mm.addBodyPart(text);
					
					msg.setContent(mm);
					msg.setFrom(new InternetAddress(from));// 发件人邮箱(我的163邮箱)
					msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toAddres)); // 收件人邮箱(我的QQ邮箱)
					msg.saveChanges();

					
					transport.connect(from, pwd);// 发件人邮箱,授权码(可以在邮箱设置中获取到授权码的信息)

					transport.sendMessage(msg, msg.getAllRecipients());
				} catch (Exception e) {
					logger.debug("第【 "+(j+1) +"】位发送失败，发送失败的用户邮件地址为===> "  + toAddres + " ,错误信息为 ===> " + e.getMessage());
					e.printStackTrace();
					
					
					emailSendLog = new Email_Recevier_Log();
					emailSendLog.setAddress(toAddres);
					emailSendLog.setMsg(e.getMessage());
					emailSendLog.setTime(new Date().toLocaleString());
					emailSendLog.setFlag("0");
					emailSendLogDao.save(emailSendLog);
					
				}finally {
					transport.close();
				}
				
				if(j>0){
					Thread.sleep(1000*1);
				}
			}
		}
		
	}
	
	
}