package com.sender.mail.fudu;

import java.io.File;
import java.text.DecimalFormat;
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
import com.sender.mail.normalmail.EmailServiceImpl;
import com.sender.mail.normalmail.SimpleException;

@Service
public class RateEmailServiceImpl implements RateStockEmailService {
	private static Logger logger = LogManager.getLogger(EmailServiceImpl.class);
	@Autowired
	private JavaMailSender javaRateMailSender;

	@Autowired
	private SimpleMailMessage simpleRateMailMessage;
	
	@Value("${jinzhenEmailTitle}")   
	private String emailTitle ;
	
	
	public void senderEmailByRate(String toEmail, List<Stock> stocks,Map map) {
		RateMailModel mail = new RateMailModel();
		// ����
		mail.setSubject(emailTitle);
		// ����
		Map<String, String> attachments = new HashMap<String, String>();
		// attachments.put("�嵥.xlsx",excelPath+"�嵥.xlsx");
		// mail.setAttachments(attachments);

		// ����
		// https://www.w3cways.com/1484.html
		StringBuilder builder = new StringBuilder();
		// builder.append("<html><body><h1 style='color:red;font-weight:bold;text-align:center'>��ر�----����ߵĹ�Ʊ���ר��</h1>");
		// builder.append("<html><body><h2 style='color:red;font-weight:bold;text-align:center'>��Զ��Ҫ׷��ɱ������</h2><br/>");
		builder.append("<table  style='width:100%; border-collapse:collapse; margin:0 0 10px' cellpadding='0' cellspacing='0' border='0'>");
		builder.append("<tbody>");
		builder.append("<tr><td colspan='6' style='text-align:center;font-size:14px;font-weight:bold;padding:5px 10px;border:1px solid #C1D9F3;background:#C1D9F3'>��ر�</td></tr>");
		builder.append("<tr>");
		builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>���</td>");
		builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>����</td>");
		builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3' >����</td>");
		builder.append("<td style='wont-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>��ǰ��</td>");
		builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3' >��Ʊ��ǰ�������ǵ���</td>");
		builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3' >�Ƿ���ʷ�����</td>");
		builder.append("</tr>");
		for (int i = 0; i < stocks.size(); i++) {
			if (i % 2 == 0) {
				double cp = stocks.get(i).getCurrentPrice(); // ��ǰ��

				DecimalFormat df = new DecimalFormat("#0.00");

				String rates = df.format(stocks.get(i).getFudu());
				String isHisBuy = "��";
				int isHy = stocks.get(i).getIsHistoryBuy();
				if (isHy > 0) {
					isHisBuy = "��";
				}
				builder.append("<tr>");
				builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>" + (i + 1) + "</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>" + stocks.get(i).getStockCode() + "</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>" + stocks.get(i).getStockName() + "</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>" + cp + "Ԫ</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>" + rates + "</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>" + isHisBuy + "</td>");
				builder.append("</tr>");
			} else {
				double cp = stocks.get(i).getCurrentPrice(); // ��ǰ��

				DecimalFormat df = new DecimalFormat("#0.00");

				String rates = df.format(stocks.get(i).getFudu());
				String isHisBuy = "��";
				int isHy = stocks.get(i).getIsHistoryBuy();
				if (isHy > 0) {
					isHisBuy = "��";
				}
				builder.append("<tr>");
				builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>" + (i + 1) + "</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>" + stocks.get(i).getStockCode() + "</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>" + stocks.get(i).getStockName() + "</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>" + cp + "Ԫ</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>" + rates + "</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>" + isHisBuy + "</td>");
				builder.append("</tr>");
			}
		}

		builder.append("<tr><td colspan='6'><table width='100%' cellspacing='0' border='0' cellpadding='0'>");
		builder.append("<tbody><tr><td height='20'></td></tr>");
		builder.append("<tr>");
		builder.append("<td width='130'><img src='http://120.78.225.98/ws/statics/images/reprot_01.png'></td>");
		builder.append("<td width='730'>");
		builder.append("<table width='100%' cellspacing='0' border='0' cellpadding='0'>");
		builder.append("<tbody><tr>");
		builder.append("<td style='ffont-size:16px;font-family:'Microsoft YaHei',Helvetica,Arial,sans-serif;line-height:2.3;color:#333;'>Hi���װ����û���</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td style='font-size:14px;font-family:'Microsoft YaHei',Helvetica,Arial,sans-serif;line-height:1.5;color:#333;'>");
		builder.append("���Ǳ�����ԱPaul��<span style='color: red ;'><span>�����ǹ�ע�����顣</span></span><br>");
		builder.append("��������Ϊ������ļ�ظ�����������ͨ���������˽��Ʊ�Ŀ����ʣ���Ҳ��������ϸ�������˽�������Ϣ��</td>");
		builder.append("</tr>");
		builder.append("</tbody></table>");
		builder.append("</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td height='20'></td>");
		builder.append("</tr>");
		builder.append("</tbody></table>");
		builder.append("</td></tr>");
		builder.append("</tbody></table>");
		builder.append("<p><stong>Ӯ�ڳ���ʱ���ʱ����14��50�����ң����Ǳ����ĸ���С��������9��50��֮ǰ���̡�������ԭ����Զ��ס��Ҫ���𣬵ڶ��ǲ�Ҫ���𣬵������Ǽ�ס��һ������</stong></p>");
		builder.append("</body></html>");
		String content = builder.toString();
		mail.setToEmails(toEmail);
		logger.log(Level.WARN, builder.toString());
		logger.log(Level.WARN, toEmail + "���ͳɹ�");
		mail.setContent(content);
		sendEmail(mail);
	}
	
	
	public void sendEmail(RateMailModel mail) {
		// �����ʼ���Ϣ
		MimeMessage message = javaRateMailSender.createMimeMessage();

		MimeMessageHelper messageHelper;
		try {
			messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			// ���÷���������
			if (mail.getEmailFrom() != null) {
				messageHelper.setFrom(mail.getEmailFrom());
			} else {
				messageHelper.setFrom(simpleRateMailMessage.getFrom());
			}

			// �����ռ�������
			if (mail.getToEmails() != null) {
				String[] toEmailArray = mail.getToEmails().split(";");
				List<String> toEmailList = new ArrayList<String>();
				if (null == toEmailArray || toEmailArray.length <= 0) {
					throw new SimpleException("�ռ������䲻��Ϊ�գ�");
				} else {
					for (String s : toEmailArray) {
						if (s != null && !s.equals("")) {
							toEmailList.add(s);
						}
					}
					if (null == toEmailList || toEmailList.size() <= 0) {
						throw new SimpleException("�ռ������䲻��Ϊ�գ�");
					} else {
						toEmailArray = new String[toEmailList.size()];
						for (int i = 0; i < toEmailList.size(); i++) {
							toEmailArray[i] = toEmailList.get(i);
						}
					}
				}
				messageHelper.setTo(toEmailArray);
			} else {
				messageHelper.setTo(simpleRateMailMessage.getTo());

			}

			// �ʼ�����
			if (mail.getSubject() != null) {
				messageHelper.setSubject(mail.getSubject());
			} else {

				messageHelper.setSubject(simpleRateMailMessage.getSubject());
			}

			// true ��ʾ����HTML��ʽ���ʼ�
			messageHelper.setText(mail.getContent(), true);

			// ���ͼƬ
			if (null != mail.getPictures()) {
				for (Iterator<Map.Entry<String, String>> it = mail.getPictures().entrySet().iterator(); it.hasNext();) {
					Map.Entry<String, String> entry = it.next();
					String cid = entry.getKey();
					String filePath = entry.getValue();
					if (null == cid || null == filePath) {
						throw new RuntimeException("��ȷ��ÿ��ͼƬ��ID��ͼƬ��ַ�Ƿ���ȫ��");
					}

					File file = new File(filePath);
					if (!file.exists()) {
						throw new RuntimeException("ͼƬ" + filePath + "�����ڣ�");
					}

					FileSystemResource img = new FileSystemResource(file);
					messageHelper.addInline(cid, img);
				}
			}

			// ��Ӹ���
			if (null != mail.getAttachments()) {
				for (Iterator<Map.Entry<String, String>> it = mail.getAttachments().entrySet().iterator(); it.hasNext();) {
					Map.Entry<String, String> entry = it.next();
					String cid = entry.getKey();
					String filePath = entry.getValue();
					if (null == cid || null == filePath) {
						throw new RuntimeException("��ȷ��ÿ��������ID�͵�ַ�Ƿ���ȫ��");
					}

					File file = new File(filePath);
					if (!file.exists()) {
						throw new RuntimeException("����" + filePath + "�����ڣ�");
					}

					FileSystemResource fileResource = new FileSystemResource(file);
					messageHelper.addAttachment(cid, fileResource);
				}
			}
			messageHelper.setSentDate(new Date());
			// �����ʼ�
			javaRateMailSender.send(message);
			logger.info("------------�����ʼ����----------");

		} catch (MessagingException | SimpleException e) {

			e.printStackTrace();
		}
	}

}