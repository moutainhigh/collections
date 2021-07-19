/**
 * Create by xuchengfei
 * Since 2013-11-21上午11:51:07
 */
package com.tz.util.email;

import java.util.Date;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * 邮件发送
 * 
 * @author xuchengfei
 * @date 2013-11-21 上午11:51:07
 * @modifyNote
 * @version 1.0
 */
public class TmSendEmailUtil {

	/**
	 * 发送带有html格式的邮件
	 * 
	 * @Description
	 * @author xuchengfei
	 * @date 2013-11-21 下午12:22:34
	 * @modifyNote
	 * @param emailInfo
	 * @return
	 */
    public static boolean sendEmail(EmailInfo emailInfo) {
        MailAuthTool authTool = null;
        if (emailInfo.isValidate()) {
            authTool = new MailAuthTool(emailInfo.getUserName(),
                    emailInfo.getPassword());
        }
        Session session = Session.getInstance(emailInfo.getProperties(),
                authTool);
        session.setDebug(false);
        try {
            Message message = new MimeMessage(session);
            Address from = new InternetAddress(emailInfo.getSendAddress());
            message.setSubject(emailInfo.getSubject());
            message.setFrom(from);// 设置邮件消息发送的地址
            Address to = new InternetAddress(emailInfo.getReceivAddress());
            message.setRecipient(RecipientType.TO, to);
            message.setSentDate(new Date());
            Multipart mainPart = new MimeMultipart();
            BodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(emailInfo.getContent(), "text/html;charset=gbk");
            mainPart.addBodyPart(bodyPart);
            message.setContent(mainPart);
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return false;
    }

	/**
	 * 发送带有html格式的邮件
	 * 
	 * @Description
	 * @author xuchengfei
	 * @date 2013-11-21 下午12:22:34
	 * @modifyNote
	 * @param emailInfo
	 * @return
	 */
	public static boolean sendEmails(EmailInfo emailInfo) {
		MailAuthTool authTool = null;
		if (emailInfo.isValidate()) {
			authTool = new MailAuthTool(emailInfo.getUserName(),
					emailInfo.getPassword());
		}
		Session session = Session.getInstance(emailInfo.getProperties(),
				authTool);
		session.setDebug(true);
		try {
			Message message = new MimeMessage(session);
			Address from = new InternetAddress(emailInfo.getSendAddress());
			message.setFrom(from);// 设置邮件消息发送的地址
			message.setSubject(emailInfo.getSubject());
			Address[] ads = null;
			String[] receivers = emailInfo.getRec();
			if (receivers != null) {
				ads = new InternetAddress[receivers.length];
				for (int i = 0; i < receivers.length; i++) {
					ads[i] = new InternetAddress(receivers[i]);
				}
			} else {
				ads = new InternetAddress[1];
				ads[0] = new InternetAddress(emailInfo.getReceivAddress());
			}
			message.setRecipients(RecipientType.TO, ads);
			message.setSentDate(new Date());
			Multipart mainPart = new MimeMultipart();
			BodyPart bodyPart = new MimeBodyPart();
			bodyPart.setContent(emailInfo.getContent(), "text/html;charset=gbk");
			mainPart.addBodyPart(bodyPart);
			message.setContent(mainPart);
			Transport.send(message);
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) {
		EmailInfo emailInfo = new EmailInfo();
		/* 新浪 */
		 emailInfo.setHost("smtp.sina.com.cn");
		 emailInfo.setPort("25");
		 emailInfo.setValidate(true);
		 emailInfo.setUserName("xuchengfeifei");
		 emailInfo.setPassword("xiaoer1986");
		 emailInfo.setSendAddress("xuchengfeifei@sina.com");
		 emailInfo.setReceivAddress("10958767358@qq.com");
		// 163
//		 emailInfo.setHost("mail.tmfood.com");
//		 emailInfo.setPort("8383");
//		 emailInfo.setValidate(false);
//		 emailInfo.setUserName("xuchengfei");
//		 emailInfo.setPassword("xiaoer");
//		 emailInfo.setSendAddress("xuchengfei@tmfood.cc");
//		 emailInfo.setReceivAddress("153398644@qq.com");
		 
//		 apache common-email.jar
//		 百度：apache email
		 
//		 emailInfo.setHost("smtp.exmail.qq.com");
//		 emailInfo.setPort("23");
//		 emailInfo.setValidate(false);
//		 emailInfo.setUserName("keke@mooncollege.cn");
//		 emailInfo.setPassword("xiaoer1986");
//		 emailInfo.setSendAddress("keke@mooncollege.cn");
//		 emailInfo.setReceivAddress("761008250@qq.com");
//
//		 emailInfo.setSubject("<b>邮件测试2222</b>");
//		 emailInfo.setContent("我的邮件测试用例<b>dddddd3333333</b>");
		//String[] recs = new String[]{"xuchengfeifei@163.com","153398644@qq.com"};
		//emailInfo.setRec(recs);
		//sendEmails(emailInfo);
		sendEmail(emailInfo);

	}

}
