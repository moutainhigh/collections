package com.sender.mail.targetprice;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.bo.domain.Stock;
import com.sender.mail.smsmail.SMSMailModel;

@Service
public class TargetEmailServiceImpl implements TargetEmailService {

	private static Logger logger = LogManager.getLogger(TargetEmailServiceImpl.class);

	private String excelPath = "d://";

	@Autowired
	private JavaMailSender targetMailSender;

	@Autowired
	private SimpleMailMessage targetMailMessage;

	@Value("${targetPriceEmailTitle}")
	private String emailTitle;


	/**
	 * 发送邮件
	 * 
	 * @author chenyq
	 * @date 2016-5-9 上午11:18:21
	 * @throws Exception
	 */
	@Override
	public void sendEmail(TargetMailModel mail) {
		// 建立邮件消息
		MimeMessage message = targetMailSender.createMimeMessage();

		MimeMessageHelper messageHelper;
		try {
			messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			// 设置发件人邮箱
			if (mail.getEmailFrom() != null) {
				messageHelper.setFrom(mail.getEmailFrom());
			} else {
				messageHelper.setFrom(targetMailMessage.getFrom());
			}

			// 设置收件人邮箱
			if (mail.getToEmails() != null) {
				String[] toEmailArray = mail.getToEmails().split(";");
				List<String> toEmailList = new ArrayList<String>();
				if (null == toEmailArray || toEmailArray.length <= 0) {
					throw new TargetSimpleException("收件人邮箱不得为空！");
				} else {
					for (String s : toEmailArray) {
						if (s != null && !s.equals("")) {
							toEmailList.add(s);
						}
					}
					if (null == toEmailList || toEmailList.size() <= 0) {
						throw new TargetSimpleException("收件人邮箱不得为空！");
					} else {
						toEmailArray = new String[toEmailList.size()];
						for (int i = 0; i < toEmailList.size(); i++) {
							toEmailArray[i] = toEmailList.get(i);
						}
					}
				}
				messageHelper.setTo(toEmailArray);
			} else {
				messageHelper.setTo(targetMailMessage.getTo());

			}

			// 邮件主题
			if (mail.getSubject() != null) {
				messageHelper.setSubject(mail.getSubject());
			} else {

				messageHelper.setSubject(targetMailMessage.getSubject());
			}

			// true 表示启动HTML格式的邮件
			messageHelper.setText(mail.getContent(), true);

			// 添加图片
			if (null != mail.getPictures()) {
				for (Iterator<Map.Entry<String, String>> it = mail.getPictures().entrySet().iterator(); it.hasNext();) {
					Map.Entry<String, String> entry = it.next();
					String cid = entry.getKey();
					String filePath = entry.getValue();
					if (null == cid || null == filePath) {
						throw new RuntimeException("请确认每张图片的ID和图片地址是否齐全！");
					}

					File file = new File(filePath);
					if (!file.exists()) {
						throw new RuntimeException("图片" + filePath + "不存在！");
					}

					FileSystemResource img = new FileSystemResource(file);
					messageHelper.addInline(cid, img);
				}
			}

			// 添加附件
			if (null != mail.getAttachments()) {
				for (Iterator<Map.Entry<String, String>> it = mail.getAttachments().entrySet().iterator(); it.hasNext();) {
					Map.Entry<String, String> entry = it.next();
					String cid = entry.getKey();
					String filePath = entry.getValue();
					if (null == cid || null == filePath) {
						throw new RuntimeException("请确认每个附件的ID和地址是否齐全！");
					}

					File file = new File(filePath);
					if (!file.exists()) {
						throw new RuntimeException("附件" + filePath + "不存在！");
					}

					FileSystemResource fileResource = new FileSystemResource(file);
					messageHelper.addAttachment(cid, fileResource);
				}
			}
			messageHelper.setSentDate(new Date());
			// 发送邮件
			targetMailSender.send(message);
			logger.info("------------发送邮件完成----------");

		} catch (MessagingException | TargetSimpleException e) {

			e.printStackTrace();
		}
	}

	// 到达预设价列表
	@SuppressWarnings("rawtypes")
	public void senderEmailByTargetPrice(String toEmail, List<Stock> stocks, Map<String, String> tips) {
		TargetMailModel mail = new TargetMailModel();
		DecimalFormat df = new DecimalFormat("#0.000");// 格式化小数
		SimpleDateFormat dateformate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 主题
		mail.setSubject(emailTitle);
		
		StringBuffer sb = new StringBuffer();
		
		
		
		
		
		mail.setToEmails(toEmail);
	}

}