package com.sender.mail.normalmail;

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

@Service
public class EmailServiceImpl implements EmailService {

	private static Logger logger = LogManager.getLogger(EmailServiceImpl.class);

	private String excelPath = "d://";

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private SimpleMailMessage simpleMailMessage;

	@Value("${normalEmailTitle}")
	private String emailTitle;

	@Value("${normalEmailDayTitle}")
	private String normalEmailDayTitle;

	@Value("${normalEmailWeekTitle}")
	private String normalEmailWeekTitle;

	@Override
	public void emailManage(String toEmails) {

		MailModel mail = new MailModel();
		// ����
		mail.setSubject(emailTitle);

		// ����
		Map<String, String> attachments = new HashMap<String, String>();
		attachments.put("�嵥.xlsx", excelPath + "�嵥.xlsx");
		// mail.setAttachments(attachments);

		// ����
		StringBuilder builder = new StringBuilder();
		builder.append("<html><body>֤ȯ��ر�<br />");
		builder.append("&nbsp&nbsp&nbsp��ע�⡿¶��úҵ������002128�����ﳭ�׼۸�8������ţɽ������000735    ������ȫ�����롣 <br />");
		builder.append("&nbsp&nbsp&nbsp&nbsp<br />" + Math.random());
		builder.append("</body></html>");
		String content = builder.toString();

		// mail.setToEmails(toEmails);
		// simpleMailMessage.setTo("1039288191@qq.com");
		simpleMailMessage.setTo(toEmails);
		mail.setContent(content);
		System.out.println(toEmails + " ���");
		sendEmail(mail);

		mail = null;
	}

	/**
	 * �����ʼ�
	 * 
	 * @author chenyq
	 * @date 2016-5-9 ����11:18:21
	 * @throws Exception
	 */
	@Override
	public void sendEmail(MailModel mail) {
		// �����ʼ���Ϣ
		MimeMessage message = javaMailSender.createMimeMessage();

		MimeMessageHelper messageHelper;
		try {
			messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			// ���÷���������
			if (mail.getEmailFrom() != null) {
				messageHelper.setFrom(mail.getEmailFrom());
			} else {
				messageHelper.setFrom(simpleMailMessage.getFrom());
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
				messageHelper.setTo(simpleMailMessage.getTo());

			}

			// �ʼ�����
			if (mail.getSubject() != null) {
				messageHelper.setSubject(mail.getSubject());
			} else {

				messageHelper.setSubject(simpleMailMessage.getSubject());
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
			javaMailSender.send(message);
			logger.info("------------�����ʼ����----------");

		} catch (MessagingException | SimpleException e) {

			e.printStackTrace();
		}
	}

	// �ֱ�
	@SuppressWarnings("rawtypes")
	public void senderEmailByMinute(String toEmail, List<Stock> stocks, Map<String, String> tips) {
		MailModel mail = new MailModel();
		DecimalFormat df = new DecimalFormat("#0.00");// ��ʽ��С��
		// ����
		mail.setSubject(emailTitle);
		// ����
		Map<String, String> attachments = new HashMap<String, String>();
		// attachments.put("�嵥.xlsx",excelPath+"�嵥.xlsx");
		// mail.setAttachments(attachments);

		// ����
		// https://www.w3cways.com/1484.html
		StringBuilder builder = new StringBuilder();

		// �����б�
		builder.append("<table  style='width:100%; border-collapse:collapse; margin:0 0 10px' cellpadding='0' cellspacing='0' border='0'>");
		builder.append("<tbody>");

		//����1��ʼ
		builder.append("<tr>");
		builder.append("<td colspan='9'>");
		builder.append("<table width='100%' cellspacing='0' border='0' cellpadding='0'>");
		builder.append("<tbody>");
		builder.append("<tr>");
		builder.append("<td width='5' style='background:#3cbde5;'></td>");
		builder.append("<td width='10'></td>");
		builder.append("<td style='font-size:14px;font-family:'Microsoft YaHei',Helvetica,Arial,sans-serif;color:#222;'>��Ѱ�</td>");
		builder.append("<td align='right'>");
		builder.append(" <table cellspacing='0' border='0' cellpadding='5'>");
		builder.append(" </table>");
		builder.append("</td>");
		builder.append("</tr>");
		builder.append("</tbody>");
		builder.append("</table>");
		builder.append("</td>");
		builder.append("</tr>");
		//����1����
		// �ճ�������ص�td
		builder.append("<tr>");
		builder.append("<td height='5'>");
		builder.append("</td>");
		builder.append("</tr>");

		// ���1��ʼ
		builder.append("<tr>");
		builder.append("<td colspan='9'>");
		// ���ݱ��
		builder.append("<table width='100%' cellspacing='0' border='0' cellpadding='5' >");
		builder.append("<tbody>");
		builder.append("<tr style='background: burlywood;'>");
		builder.append("<td width='10'></td>");
		builder.append("<td>���</td>");
		builder.append("<td>����</td>");
		builder.append("<td>����</td>");
		builder.append("<td>��ǰ��</td>");
		builder.append("<td>����</td>");
		builder.append("<td>�����</td>");
		builder.append("<td>�������</td>");
		builder.append("<td>�������</td>");
		builder.append("</tr>");
		List<Stock> mostImp = new ArrayList<Stock>();
		for (Stock stock : stocks) {
			if(stock.getMostImp()!=0){
				mostImp.add(stock);
			}
		}
		
		for (int i = 0; i < mostImp.size(); i++) {
			if(i%2==0){
				builder.append("<tr style='background:#69758F;color:#fff'>");
				builder.append("<td><img src='http://120.78.225.98/pics/images/led_green.jpg'></td>");
				builder.append("<td>"+(i+1)+"</td>");
				builder.append("<td>"+mostImp.get(i).getStockCode()+"</td>");
				builder.append("<td>"+mostImp.get(i).getStockName()+"</td>");
				builder.append("<td>"+mostImp.get(i).getCurrentPrice()+"</td>");
				builder.append("<td>"+mostImp.get(i).getPrevClose()+"</td>");
				builder.append("<td>"+mostImp.get(i).getPointerPrice()+"</td>");
				builder.append("<td>��� "+mostImp.get(i).getTodayMinPrice()+"</td>");
				builder.append("<td>��� "+mostImp.get(i).getTodayMaxPrice()+"</td>");
				builder.append("</tr>");
			}else{
				builder.append("<tr style='background:#7C829C;color:#fff'>");
				builder.append("<td><img src='http://120.78.225.98/pics/images/led_green.jpg'></td>");
				builder.append("<td>"+(i+1)+"</td>");
				builder.append("<td>"+mostImp.get(i).getStockCode()+"</td>");
				builder.append("<td>"+mostImp.get(i).getStockName()+"</td>");
				builder.append("<td>"+mostImp.get(i).getCurrentPrice()+"</td>");
				builder.append("<td>"+mostImp.get(i).getPrevClose()+"</td>");
				builder.append("<td>"+mostImp.get(i).getPointerPrice()+"</td>");
				builder.append("<td>��� "+mostImp.get(i).getTodayMinPrice()+"</td>");
				builder.append("<td>��� "+mostImp.get(i).getTodayMaxPrice()+"</td>");
				builder.append("</tr>");
			}
		}
		
		builder.append("</tbody>");
		builder.append("</table>");
		// ���1����
		builder.append("</td>");
		builder.append("</tr>");
		
		builder.append("<tr>");
		builder.append("<td height='5'>");
		builder.append("</td>");
		builder.append("</tr>");
		// //����2��ʼ
		builder.append("<tr>");
		builder.append("<td colspan='9'>");
		builder.append("<table width='100%' cellspacing='0' border='0' cellpadding='0'>");
		builder.append("<tbody>");
		builder.append("<tr>");
		builder.append("<td width='5' style='background:#3cbde5;'></td>");
		builder.append("<td width='10'></td>");
		builder.append("<td style='font-size:14px;font-family:'Microsoft YaHei',Helvetica,Arial,sans-serif;color:#222;'>���ڹ�ע</td>");
		builder.append("<td align='right'>");
		builder.append(" <table cellspacing='0' border='0' cellpadding='5'>");
		builder.append(" </table>");
		builder.append("</td>");
		builder.append("</tr>");
		builder.append("</tbody>");
		builder.append("</table>");
		builder.append("</td>");
		builder.append("</tr>");
		//����2����
		builder.append("<tr>");
		builder.append("<td height='5'>");
		builder.append("</td>");
		builder.append("</tr>");

		// ���2��ʼ
		builder.append("<tr>");
		builder.append("<td colspan='9'>");
		// ���ݱ��
		builder.append("<table width='100%' cellspacing='0' border='0' cellpadding='5'>");
		builder.append("<tbody>");
		builder.append("<tr style='background:#F6F6F6'>");
		builder.append("<td width='10'></td>");
		builder.append("<td>���</td>");
		builder.append("<td>����</td>");
		builder.append("<td>����</td>");
		builder.append("<td>��ǰ��</td>");
		builder.append("<td>�����</td>");
		builder.append("<td>����</td>");
		builder.append("<td>�������</td>");
		builder.append("<td>�������</td>");
		builder.append("</tr>");
		List<Stock> recentWatch = new ArrayList<Stock>();
		for (Stock stock : stocks) {
			if(stock.getIsRecentImport()!=0){
				recentWatch.add(stock);
			}
		}
		
		for (int j = 0; j < recentWatch.size(); j++) {
			if(j%2==0){
				builder.append("<tr style='background:#F5FAFE;color:#444'>");
				builder.append("<td><img src='http://120.78.225.98/pics/images/green.png'></td>");
				builder.append("<td>"+(j+1)+"</td>");
				builder.append("<td>"+recentWatch.get(j).getStockCode()+"</td>");
				builder.append("<td>"+recentWatch.get(j).getStockName()+"</td>");
				builder.append("<td>"+recentWatch.get(j).getCurrentPrice()+"</td>");
				builder.append("<td>"+recentWatch.get(j).getPointerPrice()+"</td>");
				builder.append("<td>"+recentWatch.get(j).getPrevClose()+"</td>");
				builder.append("<td>��� "+recentWatch.get(j).getTodayMinPrice()+"</td>");
				builder.append("<td>��� "+recentWatch.get(j).getTodayMaxPrice()+"</td>");
				builder.append("</tr>");
			}else{
				builder.append("<tr style='background:#FCFFFF;color:#444'>");
				builder.append("<td><img src='http://120.78.225.98/pics/images/green.png'></td>");
				builder.append("<td>"+(j+1)+"</td>");
				builder.append("<td>"+recentWatch.get(j).getStockCode()+"</td>");
				builder.append("<td>"+recentWatch.get(j).getStockName()+"</td>");
				builder.append("<td>"+recentWatch.get(j).getCurrentPrice()+"</td>");
				builder.append("<td>"+recentWatch.get(j).getPointerPrice()+"</td>");
				builder.append("<td>"+recentWatch.get(j).getPrevClose()+"</td>");
				builder.append("<td>��� "+recentWatch.get(j).getTodayMinPrice()+"</td>");
				builder.append("<td>��� "+recentWatch.get(j).getTodayMaxPrice()+"</td>");
				builder.append("</tr>");
			}
		}
		builder.append("</tbody>");
		builder.append("</table>");
		builder.append("</td>");
		builder.append("</tr>");
		// ���2����

		builder.append("<tr>");
		builder.append("<td height='5'>");
		builder.append("</td>");
		builder.append("</tr>");
		/* ����3��ʼ */
		builder.append("<tr>");
		builder.append("<td colspan='9'>");
		builder.append("<table width='100%' cellspacing='0' border='0' cellpadding='0'>");
		builder.append("<tbody>");
		builder.append("<tr>");
		builder.append("<td width='5' style='background:#3cbde5;'></td>");
		builder.append("<td width='10'></td>");
		builder.append("<td style='font-size:14px;font-family:'Microsoft YaHei',Helvetica,Arial,sans-serif;color:#222;'>���ڴ�ѡ��ע</td>");
		builder.append("<td align='right'>");
		builder.append(" <table cellspacing='0' border='0' cellpadding='5'>");
		builder.append(" </table>");
		builder.append("</td>");
		builder.append("</tr>");
		builder.append("</tbody>");
		builder.append("</table>");
		builder.append("</td>");
		builder.append("</tr>");

		/* ����3���� */

		// ����2�ճ�һ��
		builder.append("<tr>");
		builder.append("<td height='5'>");
		builder.append("</td>");
		builder.append("</tr>");
		// ���ı��
		builder.append("<tr>");
		builder.append("<td colspan='9'>");
		// ���ʼ---���ݱ��
		builder.append("<table width='100%' cellspacing='0' border='0' cellpadding='5'>");
		builder.append("<tbody>");
		builder.append("<tr>");
		builder.append("<td style='background:#C4BC96'>");
		builder.append("���");
		builder.append("</td>");
		builder.append("<td style='background:#C4BC96'>");
		builder.append("����");
		builder.append("</td>");
		builder.append("<td style='background:#C4BC96' >");
		builder.append("����");
		builder.append("</td>");
		builder.append("<td style='background:#C4BC96' >");
		builder.append("��ǰ��");
		builder.append("</td>");
		builder.append("<td style='background:#C4BC96' >");
		builder.append("����");
		builder.append("</td>");
		builder.append("<td style='background:#C4BC96' >");
		builder.append("�ƽ��");
		builder.append("</td>");
		builder.append("<td style='background:#C4BC96' >");
		builder.append("���۲�");
		builder.append("</td>");
		builder.append("<td style='background:#C4BC96' >");
		builder.append("�Ƿ�");
		builder.append("</td>");
		builder.append("<td style='background:#C4BC96' >");
		builder.append("���۷���");
		builder.append("</td>");
		builder.append("<td style='background:#C4BC96' >");
		builder.append("��ʷ�ֲּ�");
		builder.append("</td>");
		builder.append("<td style='background:#C4BC96' >");
		builder.append("��λ");
		builder.append("</td>");
		builder.append("</tr>");
		List<Stock> waiterList = new ArrayList<Stock>();
		// ��ע�б�������
		for (int i = 0; i < stocks.size(); i++) {
			if (stocks.get(i).getWaitImp() != 0) {
				waiterList.add(stocks.get(i));
			}
		}

		for (int i = 0; i < waiterList.size(); i++) {
			String comp = df.format(waiterList.get(i).getCurrentPrice() - waiterList.get(i).getPointerPrice());
			String buyRate = df.format((waiterList.get(i).getCurrentPrice() - waiterList.get(i).getPointerPrice()) / waiterList.get(i).getCurrentPrice());
			double k = waiterList.get(i).getCurrentPrice() - waiterList.get(i).getPrevClose();
			double fuduTemp = waiterList.get(i).getFudu() * 100;
			String fuDu = df.format(fuduTemp) + "%";

			if (i % 2 == 0) {
				builder.append("<tr style='background:#FAFBFB'>");
				builder.append("<td>" + (i + 1) + "</td>");
				builder.append("<td>" + waiterList.get(i).getStockCode() + "</td>");
				builder.append("<td>" + waiterList.get(i).getStockName() + "</td>");
				if (k > 0) {
					builder.append("<td style='font-weight:bold;color:red'>" + waiterList.get(i).getCurrentPrice() + "</td>");
				} else {
					builder.append("<td style='font-weight:bold;color:#009900'>" + waiterList.get(i).getCurrentPrice() + "</td>");
				}
				builder.append("<td>" + waiterList.get(i).getPrevClose() + "</td>");
				builder.append("<td>" + waiterList.get(i).getPointerPrice() + "</td>");
				builder.append("<td>�۲� " + comp + "</td>");
				if (k > 0) {
					builder.append("<td style='color:red;font-weight:bold;'>" + fuDu + "</td>");
				} else if (k == 0) {
					builder.append("<td style='color:goldenrod;font-weight:bold;'>" + fuDu + "</td>");
				} else {
					builder.append("<td style='color:#009900;font-weight:bold;'>" + fuDu + "</td>");
				}
				builder.append("<td>" + buyRate + "</td>");
				builder.append("<td>" + waiterList.get(i).getBuyPrice() + "</td>");
				builder.append("<td>" + waiterList.get(i).getPositions() + "</td>");
				builder.append("</tr>");
			} else {
				builder.append("<tr style='background:#EFF6FA'>");
				builder.append("<td>" + (i + 1) + "</td>");
				builder.append("<td>" + waiterList.get(i).getStockCode() + "</td>");
				builder.append("<td>" + waiterList.get(i).getStockName() + "</td>");
				if (k > 0) {
					builder.append("<td style='color:red;font-weight:bold;'>" + waiterList.get(i).getCurrentPrice() + "</td>");
				} else if (k == 0) {
					builder.append("<td style='color:goldenrod;font-weight:bold;'>" + waiterList.get(i).getCurrentPrice() + "</td>");
				} else {
					builder.append("<td style='color:#009900;font-weight:bold;'>" + waiterList.get(i).getCurrentPrice() + "</td>");
				}
				builder.append("<td>" + waiterList.get(i).getPrevClose() + "</td>");
				builder.append("<td>" + waiterList.get(i).getPointerPrice() + "</td>");
				builder.append("<td>�� " + comp + "</td>");
				if (k > 0) {
					builder.append("<td style='color:red;font-weight:bold'>" + fuDu + "</td>");
				} else if (k == 0) {
					builder.append("<td style='color:goldenrod;font-weight:bold'>" + fuDu + "</td>");
				} else {
					builder.append("<td style='color:#009900;font-weight:bold'>" + fuDu + "</td>");
				}

				builder.append("<td>" + buyRate + "</td>");
				// builder.append("<td>"+(stocks.get(i).getCurrentPrice()-stocks.get(i).getPointerPrice())/stocks.get(i).getCurrentPrice()+"</td>");
				builder.append("<td>" + waiterList.get(i).getBuyPrice() + "</td>");
				builder.append("<td>" + waiterList.get(i).getPositions() + "</td>");
				builder.append("</tr>");
			}
		}

		builder.append("</tbody>");
		builder.append("</table>");
		
		builder.append("</td>");
		builder.append("</tr>");
		/* ���ݱ����� */
		
		
		
		
		
		//������ʼ
		builder.append("<tr>");
		builder.append("<td colspan='9'>");
		builder.append("<table width='100%' cellspacing='0' border='0' cellpadding='0'>");
		builder.append("<tbody>");
		builder.append("<tr>");
		builder.append("<td width='5' style='background:#3cbde5;'></td>");
		builder.append("<td width='10'></td>");
		builder.append("<td style='font-size:14px;font-family:'Microsoft YaHei',Helvetica,Arial,sans-serif;color:#222;'>������</td>");
		builder.append("<td align='right'>");
		builder.append(" <table cellspacing='0' border='0' cellpadding='5'>");
		builder.append(" </table>");
		builder.append("</td>");
		builder.append("</tr>");
		builder.append("</tbody>");
		builder.append("</table>");
		builder.append("</td>");
		builder.append("</tr>");
		//����1����
		// �ճ�������ص�td
		builder.append("<tr>");
		builder.append("<td height='5'>");
		builder.append("</td>");
		builder.append("</tr>");
		
		//���
		builder.append("<tr>");
		builder.append("<td colspan='9'>");
		// ���ʼ---���ݱ��ʼ
		builder.append("<table width='100%' cellspacing='0' border='0' cellpadding='5'>");
		builder.append("<tbody>");
		builder.append("<tr style='background: burlywood;'>");
		builder.append("<td width='10'></td>");
		builder.append("<td>���</td>");
		builder.append("<td>����</td>");
		builder.append("<td>����</td>");
		builder.append("<td>��ǰ��</td>");
		builder.append("<td>����</td>");
		builder.append("<td>�����</td>");
		builder.append("<td>�������</td>");
		builder.append("<td>�������</td>");
		builder.append("</tr>");
		
		List<Stock> longhuBan = new ArrayList<Stock>();
		for (Stock stock : stocks) {
			if(stock.getLonghuBang()!=0){
				longhuBan.add(stock);
			}
		}
		for (int i = 0; i < longhuBan.size(); i++) {
			if(i%2==0){
				builder.append("<tr style='background:#69758F;color:#fff'>");
				builder.append("<td><img src='http://120.78.225.98/pics/images/led_green.jpg'></td>");
				builder.append("<td>"+(i+1)+"</td>");
				builder.append("<td>"+longhuBan.get(i).getStockCode()+"</td>");
				builder.append("<td>"+longhuBan.get(i).getStockName()+"</td>");
				builder.append("<td>"+longhuBan.get(i).getCurrentPrice()+"</td>");
				builder.append("<td>"+longhuBan.get(i).getPrevClose()+"</td>");
				builder.append("<td>"+longhuBan.get(i).getPointerPrice()+"</td>");
				builder.append("<td>��� "+longhuBan.get(i).getTodayMinPrice()+"</td>");
				builder.append("<td>��� "+longhuBan.get(i).getTodayMaxPrice()+"</td>");
				builder.append("</tr>");
			}else{
				builder.append("<tr style='background:#7C829C;color:#fff'>");
				builder.append("<td><img src='http://120.78.225.98/pics/images/led_green.jpg'></td>");
				builder.append("<td>"+(i+1)+"</td>");
				builder.append("<td>"+longhuBan.get(i).getStockCode()+"</td>");
				builder.append("<td>"+longhuBan.get(i).getStockName()+"</td>");
				builder.append("<td>"+longhuBan.get(i).getCurrentPrice()+"</td>");
				builder.append("<td>"+longhuBan.get(i).getPrevClose()+"</td>");
				builder.append("<td>"+longhuBan.get(i).getPointerPrice()+"</td>");
				builder.append("<td>��� "+longhuBan.get(i).getTodayMinPrice()+"</td>");
				builder.append("<td>��� "+longhuBan.get(i).getTodayMaxPrice()+"</td>");
				builder.append("</tr>");
			}
		}
		builder.append("</tbody>");
		builder.append("</table>");
		builder.append("</td>");
		builder.append("</tr>");
		//���ݱ�����
		//���������
		
		
		
		// ���й�Ʊ��ϸ
		// �ճ�һ��
		builder.append("<tr>");
		builder.append("<td height='5'>");
		builder.append("</td>");
		builder.append("</tr>");
		builder.append("<tr><td colspan='9' style='text-align:center;font-size:14px;font-weight:bold;padding:5px 10px;border:1px solid #C1D9F3;background:#C1D9F3'>��ѡ���б�</td></tr>");
		builder.append("<tr>");
		builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>���</td>");
		builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>����</td>");
		builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3' >����</td>");
		builder.append("<td style='wont-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>��ǰ</td>");
		builder.append("<td style='wont-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>����</td>");
		builder.append("<td style='wont-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>��ǰ���</td>");
		builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3' >�����</td>");
		builder.append("<td style='wont-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>��ǰ���</td>");
		builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3' >����</td>");
		builder.append("</tr>");
		for (int i = 0; i < stocks.size(); i++) {
			double fudu = stocks.get(i).getFudu() * 100;
			String rates = df.format(fudu) + "%";
			int isRecentImp = stocks.get(i).getIsRecentImport();
			double k = stocks.get(i).getCurrentPrice() - stocks.get(i).getPrevClose();
			double cPrice = stocks.get(i).getCurrentPrice();
			double jieRuPrice = stocks.get(i).getPointerPrice();
			if (i % 2 == 0) {
				builder.append("<tr>");
				if (isRecentImp != 0) {
					builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3;color:#3300cc;font-weight:bold;'>" + (i + 1) + "</td>");
					builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3;color:#3300cc;font-weight:bold;'>" + stocks.get(i).getStockCode() + "</td>");
					builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3;color:#3300cc;font-weight:bold;'>" + stocks.get(i).getStockName() + "</td>");
				} else {
					builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>" + (i + 1) + "</td>");
					builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>" + stocks.get(i).getStockCode() + "</td>");
					builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>" + stocks.get(i).getStockName() + "</td>");
				}
				if (k > 0) {
					builder.append("<td style='color:red;font-weight:bold;background:#EFF5FB;font-size:14px;text-align:center;border:1px solid #C1D9F3'>" + cPrice + "</td>");
				} else if (k == 0) {
					builder.append("<td style='color:goldenrod;font-weight:bold;background:#EFF5FB;font-size:14px;text-align:center;border:1px solid #C1D9F3'>" + cPrice + "</td>");
				} else {
					builder.append("<td style='color:#009900;font-weight:bold;background:#EFF5FB;font-size:14px;text-align:center;border:1px solid #C1D9F3'>" + cPrice + "</td>");
				}
				builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>" + stocks.get(i).getPrevClose() + "</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>��� " + stocks.get(i).getTodayMinPrice() + "</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>" + jieRuPrice + "</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>���" + stocks.get(i).getTodayMaxPrice() + "</td>");
				if (k > 0) {
					builder.append("<td style='color:red;font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>" + rates + "</td>");
				} else if (k == 0) {
					builder.append("<td style='color:goldenrod;font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>" + rates + "</td>");
				} else {
					builder.append("<td style='color:#009900;font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>" + rates + "</td>");
				}
				builder.append("</tr>");
			} else {
				builder.append("<tr>");
				if (isRecentImp != 0) {
					builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3;color:#3300cc;font-weight:bold;'>" + (i + 1) + "</td>");
					builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3;color:#3300cc;font-weight:bold;'>" + stocks.get(i).getStockCode() + "</td>");
					builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3;color:#3300cc;font-weight:bold;'>" + stocks.get(i).getStockName() + "</td>");
				} else {
					builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>" + (i + 1) + "</td>");
					builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>" + stocks.get(i).getStockCode() + "</td>");
					builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>" + stocks.get(i).getStockName() + "</td>");
				}
				if (k > 0) {
					builder.append("<td style='color:red;font-weight:bold;background:#B2D7EA;font-size:14px;text-align:center;border:1px solid #C1D9F3'>" + cPrice + "</td>");
				} else if (k == 0) {
					builder.append("<td style='color:goldenrod;font-weight:bold;background:#B2D7EA;font-size:14px;text-align:center;border:1px solid #C1D9F3'>" + cPrice + "</td>");
				} else {
					builder.append("<td style='color:#009900;font-weight:bold;background:#B2D7EA;font-size:14px;text-align:center;border:1px solid #C1D9F3'>" + cPrice + "</td>");
				}
				builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>" + stocks.get(i).getPrevClose() + "</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>���" + stocks.get(i).getTodayMinPrice() + "</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>" + jieRuPrice + "</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>���" + stocks.get(i).getTodayMaxPrice() + "</td>");
				if (k > 0) {
					builder.append("<td style='color:red;font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>" + rates + "</td>");
				} else if (k == 0) {
					builder.append("<td style='color:goldenrod;font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>" + rates + "</td>");
				} else {
					builder.append("<td style='color:#009900;font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>" + rates + "</td>");
				}
				builder.append("</tr>");
			}
		}

		builder.append("<tr><td colspan='9'><table width='100%' cellspacing='0' border='0' cellpadding='0'>");
		builder.append("<tbody><tr><td height='20'></td></tr>");
		builder.append("<tr>");
		builder.append("<td width='130'><img src='http://120.78.225.98/pics/images/reprot_01.png'></td>");
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
		builder.append("<p><stong>����ʱ�������14��50�����ң����Ǳ����ĸ���С,������9��50��֮ǰ���̡�������ԭ�򣺲�Ҫ���𣬵ڶ��ǲ�Ҫ���𣬵������Ǽ�ס��һ������</stong></p>");
		if (!tips.isEmpty()) {
			for (Map.Entry entry : tips.entrySet()) {
				builder.append("<p style='color:red'>" + entry.getValue() + "</p>");
			}
		}

		String content = builder.toString();
		mail.setToEmails(toEmail);
		mail.setContent(content);
		try {
			sendEmail(mail);
			//logger.log(Level.WARN, "���͵��ʼ�����" + builder.toString());
			logger.log(Level.WARN, toEmail + "���ͳɹ�");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// �ձ�
	public void senderEmailByDay(String toEmail, List<Stock> stocks, Map<String, String> tips) {
		
	}

	// �ܱ�
	public void senderEmailByWeek(String toEmail, List<Stock> stocks, Map<String, String> tips) {
	
	}

}