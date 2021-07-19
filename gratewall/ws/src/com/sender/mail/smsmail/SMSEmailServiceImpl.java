package com.sender.mail.smsmail;

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

import com.bo.domain.SMSStock;

@Service
public class SMSEmailServiceImpl implements SMSEmailService {

	private static Logger logger = LogManager.getLogger(SMSEmailServiceImpl.class);

	private String excelPath = "d://";

	@Autowired
	private JavaMailSender javaSMSMailSender;

	@Autowired
	private SimpleMailMessage simpleSMSMailMessage;

	@Value("${smsEmailTitle}")
	private String shortStocks;

	@Override
	public void emailManage(String toEmails) {

		SMSMailModel mail = new SMSMailModel();
		
		// 主题
		mail.setSubject(shortStocks);

		// 附件
		Map<String, String> attachments = new HashMap<String, String>();
		attachments.put("清单.xlsx", excelPath + "清单.xlsx");
		// mail.setAttachments(attachments);

		// 内容
		StringBuilder builder = new StringBuilder();
		builder.append("<html><body>证券监控宝<br />");
		builder.append("&nbsp&nbsp&nbsp【注意】露天煤业，代码002128，到达抄底价格8，，罗牛山，代码000735    ，立刻全仓买入。 <br />");
		builder.append("&nbsp&nbsp&nbsp&nbsp<br />" + Math.random());
		builder.append("</body></html>");
		String content = builder.toString();

		// mail.setToEmails(toEmails);
		// simpleMailMessage.setTo("1039288191@qq.com");
		simpleSMSMailMessage.setTo(toEmails);
		mail.setContent(content);
		System.out.println(toEmails + " 完成");
		sendEmail(mail);

		mail = null;
	}

	/**
	 * 发送邮件
	 * 
	 * @author chenyq
	 * @date 2016-5-9 上午11:18:21
	 * @throws Exception
	 */
	@Override
	public void sendEmail(SMSMailModel mail) {
		// 建立邮件消息
		MimeMessage message = javaSMSMailSender.createMimeMessage();

		MimeMessageHelper messageHelper;
		try {
			messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			// 设置发件人邮箱
			if (mail.getEmailFrom() != null) {
				messageHelper.setFrom(mail.getEmailFrom());
			} else {
				messageHelper.setFrom(simpleSMSMailMessage.getFrom());
			}

			// 设置收件人邮箱
			if (mail.getToEmails() != null) {
				String[] toEmailArray = mail.getToEmails().split(";");
				List<String> toEmailList = new ArrayList<String>();
				if (null == toEmailArray || toEmailArray.length <= 0) {
					throw new SMSSimpleException("收件人邮箱不得为空！");
				} else {
					for (String s : toEmailArray) {
						if (s != null && !s.equals("")) {
							toEmailList.add(s);
						}
					}
					if (null == toEmailList || toEmailList.size() <= 0) {
						throw new SMSSimpleException("收件人邮箱不得为空！");
					} else {
						toEmailArray = new String[toEmailList.size()];
						for (int i = 0; i < toEmailList.size(); i++) {
							toEmailArray[i] = toEmailList.get(i);
						}
					}
				}
				messageHelper.setTo(toEmailArray);
			} else {
				messageHelper.setTo(simpleSMSMailMessage.getTo());

			}

			// 邮件主题
			if (mail.getSubject() != null) {
				messageHelper.setSubject(mail.getSubject());
			} else {

				messageHelper.setSubject(simpleSMSMailMessage.getSubject());
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
			javaSMSMailSender.send(message);
			logger.info("------------发送邮件完成----------");

		} catch (MessagingException | SMSSimpleException e) {

			e.printStackTrace();
		}
	}

	// 短信股开始
	// /////////////////////////////////////////////////////////////////////////////////////
	public void senderShortEmailByMinute(String toEmail, List<SMSStock> stocks) {
		SMSMailModel mail = new SMSMailModel();
		DecimalFormat df = new DecimalFormat("#0.000");// 格式化小数
		SimpleDateFormat dateformate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 主题
		mail.setSubject(shortStocks);
		// 附件
		Map<String, String> attachments = new HashMap<String, String>();
		// attachments.put("清单.xlsx",excelPath+"清单.xlsx");
		// mail.setAttachments(attachments);

		// 内容
		// https://www.w3cways.com/1484.html
		StringBuilder builder = new StringBuilder();
		builder.append("<table  style='width:100%; border-collapse:collapse; margin:0 0 10px' cellpadding='0' cellspacing='0' border='0'>");
		builder.append("<tbody>");
		builder.append("<tr><td colspan='8' style='text-align:center;font-size:14px;font-weight:bold;padding:5px 10px;border:1px solid #C1D9F3;background:#C1D9F3'>监控宝</td></tr>");
		builder.append("<tr>");
		builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>序号</td>");
		builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>代码</td>");
		builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3' >名称</td>");
		builder.append("<td style='wont-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>当前价</td>");
		builder.append("<td style='wont-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>昨收</td>");
		builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>股票当前较昨日涨跌幅</td>");
		builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>类型</td>");
		builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>添加日期</td>");
		builder.append("</tr>");
		for (int i = 0; i < stocks.size(); i++) {
			double cp = stocks.get(i).getCurrentPrice(); // 当前价
			String rates = df.format(stocks.get(i).getRates());
			if (i % 2 == 0) {
				builder.append("<tr>");
				builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB; border:1px solid #C1D9F3'>" + (i + 1) + "</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB; border:1px solid #C1D9F3'>" + stocks.get(i).getStockCode() + "</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB; border:1px solid #C1D9F3'>" + stocks.get(i).getStockName() + "</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB; border:1px solid #C1D9F3'>" + cp + "元</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB; border:1px solid #C1D9F3'>" + stocks.get(i).getPrevClose() + "元</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB; border:1px solid #C1D9F3'>" + rates + "</td>");
				String types = "早盘";
				if(stocks.get(i).getTypes()!=0){
					types = "午盘";
				}
				builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB; border:1px solid #C1D9F3'>"+types+"</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>"+dateformate.format(stocks.get(i).getAddDate())+"</td>");
				builder.append("</tr>");
			} else {
				builder.append("<tr>");
				builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>" + (i + 1) + "</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>" + stocks.get(i).getStockCode() + "</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>" + stocks.get(i).getStockName() + "</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>" + cp + "元</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>" + stocks.get(i).getPrevClose() + "元</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>" + rates + "</td>");
				String types = "早盘";
				if(stocks.get(i).getTypes()!=0){
					types = "午盘";
				}
				builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA; border:1px solid #C1D9F3'>"+types+"</td>");
				
				builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>"+dateformate.format(stocks.get(i).getAddDate())+"</td>");
				builder.append("</tr>");
			}
		}

		builder.append("<tr><td colspan='7'><table width='100%' cellspacing='0' border='0' cellpadding='0'>");
		builder.append("<tbody><tr><td height='20'></td></tr>");
		builder.append("<tr>");
		builder.append("<td width='130'><img src='http://120.78.225.98/ws/statics/images/reprot_01.png'></td>");
		builder.append("<td width='730'>");
		builder.append("<table width='100%' cellspacing='0' border='0' cellpadding='0'>");
		builder.append("<tbody><tr>");
		builder.append("<td style='ffont-size:16px;font-family:'Microsoft YaHei',Helvetica,Arial,sans-serif;line-height:2.3;color:#333;'>Hi，亲爱的用户：</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td style='font-size:14px;font-family:'Microsoft YaHei',Helvetica,Arial,sans-serif;line-height:1.5;color:#333;'>");
		builder.append("我是报告快递员Paul，<span style='color: red ;'><span>以上是关注的详情。</span></span><br>");
		builder.append("以上是我为您整理的监控概述，您可以通过它快速了解股票的可买率，您也可以在详细报告中了解更多的信息。</td>");
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
		builder.append("<p><stong>赢在抄底时最佳时间是14：50分左右，暴涨暴跌的概率小。尽量在9：50分之前清盘。炒股三原则：永远记住不要亏损，第二是不要亏损，第三条是记住第一条！！</stong></p>");
		String content = builder.toString();
		mail.setToEmails(toEmail);
		//logger.log(Level.WARN, builder.toString());
		
		try {
			logger.log(Level.WARN, toEmail + "发送成功");
			mail.setContent(content);
			sendEmail(mail);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void senderShortEmailByDay(String toEmail, List<SMSStock> stocks) {
		SMSMailModel mail = new SMSMailModel();
		DecimalFormat df = new DecimalFormat("#0.000");// 格式化小数
		SimpleDateFormat dateformate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 主题
		mail.setSubject(shortStocks+"日报");
		// 附件
		Map<String, String> attachments = new HashMap<String, String>();
		// attachments.put("清单.xlsx",excelPath+"清单.xlsx");
		// mail.setAttachments(attachments);

		// 内容
		// https://www.w3cways.com/1484.html
		StringBuilder builder = new StringBuilder();
		builder.append("<table  style='width:100%; border-collapse:collapse; margin:0 0 10px' cellpadding='0' cellspacing='0' border='0'>");
		builder.append("<tbody>");
		builder.append("<tr><td colspan='8' style='text-align:center;font-size:14px;font-weight:bold;padding:5px 10px;border:1px solid #C1D9F3;background:#C1D9F3'>监控宝</td></tr>");
		builder.append("<tr>");
		builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>序号</td>");
		builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>代码</td>");
		builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3' >名称</td>");
		builder.append("<td style='wont-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>当前价</td>");
		builder.append("<td style='wont-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>昨收</td>");
		builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>股票当前较昨日涨跌幅</td>");
		builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>类型</td>");
		builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>添加时间</td>");
		builder.append("</tr>");
		for (int i = 0; i < stocks.size(); i++) {
			double cp = stocks.get(i).getCurrentPrice(); // 当前价
			String rates = df.format(stocks.get(i).getRates());
			if (i % 2 == 0) {
				builder.append("<tr>");
				builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB; border:1px solid #C1D9F3'>" + (i + 1) + "</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB; border:1px solid #C1D9F3'>" + stocks.get(i).getStockCode() + "</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB; border:1px solid #C1D9F3'>" + stocks.get(i).getStockName() + "</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB; border:1px solid #C1D9F3'>" + cp + "元</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB; border:1px solid #C1D9F3'>" + stocks.get(i).getPrevClose() + "元</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB; border:1px solid #C1D9F3'>" + rates + "</td>");
				String types = "早盘";
				if(stocks.get(i).getTypes()!=0){
					types = "午盘";
				}
				builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB; border:1px solid #C1D9F3'>"+types+"</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>"+dateformate.format(stocks.get(i).getAddDate())+"</td>");
				builder.append("</tr>");
			} else {
				builder.append("<tr>");
				builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>" + (i + 1) + "</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>" + stocks.get(i).getStockCode() + "</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>" + stocks.get(i).getStockName() + "</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>" + cp + "元</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>" + stocks.get(i).getPrevClose() + "元</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>" + rates + "</td>");
				String types = "早盘";
				if(stocks.get(i).getTypes()!=0){
					types = "午盘";
				}
				builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA; border:1px solid #C1D9F3'>"+types+"</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>"+dateformate.format(stocks.get(i).getAddDate())+"</td>");
				builder.append("</tr>");
			}
		}

		builder.append("<tr><td colspan='7'><table width='100%' cellspacing='0' border='0' cellpadding='0'>");
		builder.append("<tbody><tr><td height='20'></td></tr>");
		builder.append("<tr>");
		builder.append("<td width='130'><img src='http://120.78.225.98/ws/statics/images/reprot_01.png'></td>");
		builder.append("<td width='730'>");
		builder.append("<table width='100%' cellspacing='0' border='0' cellpadding='0'>");
		builder.append("<tbody><tr>");
		builder.append("<td style='ffont-size:16px;font-family:'Microsoft YaHei',Helvetica,Arial,sans-serif;line-height:2.3;color:#333;'>Hi，亲爱的用户：</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td style='font-size:14px;font-family:'Microsoft YaHei',Helvetica,Arial,sans-serif;line-height:1.5;color:#333;'>");
		builder.append("我是报告快递员Paul，<span style='color: red ;'><span>以上是关注的详情。</span></span><br>");
		builder.append("以上是我为您整理的监控概述，您可以通过它快速了解股票的可买率，您也可以在详细报告中了解更多的信息。</td>");
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
		builder.append("<p><stong>赢在抄底时最佳时间是14：50分左右，暴涨暴跌的概率小。尽量在9：50分之前清盘。炒股三原则：永远记住不要亏损，第二是不要亏损，第三条是记住第一条！！</stong></p>");
		//builder.append("</body></html>");
		String content = builder.toString();
		mail.setToEmails(toEmail);
		try {
			//logger.log(Level.WARN, builder.toString());
			logger.log(Level.WARN, toEmail + "发送成功");
			mail.setContent(content);
			sendEmail(mail);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void senderShortEmailByWeek(String toEmail, List<SMSStock> stocks) {
		SMSMailModel mail = new SMSMailModel();
		DecimalFormat df = new DecimalFormat("#0.000");// 格式化小数
		SimpleDateFormat dateformate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 主题
		mail.setSubject(shortStocks+"周报");
		// 附件
		Map<String, String> attachments = new HashMap<String, String>();
		// attachments.put("清单.xlsx",excelPath+"清单.xlsx");
		// mail.setAttachments(attachments);

		// 内容
		// https://www.w3cways.com/1484.html
		StringBuilder builder = new StringBuilder();
		builder.append("<table  style='width:100%; border-collapse:collapse; margin:0 0 10px' cellpadding='0' cellspacing='0' border='0'>");
		builder.append("<tbody>");
		builder.append("<tr><td colspan='8' style='text-align:center;font-size:14px;font-weight:bold;padding:5px 10px;border:1px solid #C1D9F3;background:#C1D9F3'>监控宝</td></tr>");
		builder.append("<tr>");
		builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>序号</td>");
		builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>代码</td>");
		builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3' >名称</td>");
		builder.append("<td style='wont-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>当前价</td>");
		builder.append("<td style='wont-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>昨收</td>");
		builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>股票当前较昨日涨跌幅</td>");
		builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>类型</td>");
		builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>添加时间</td>");
		builder.append("</tr>");
		for (int i = 0; i < stocks.size(); i++) {
			double cp = stocks.get(i).getCurrentPrice(); // 当前价
			String rates = df.format(stocks.get(i).getRates());
			if (i % 2 == 0) {
				builder.append("<tr>");
				builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB; border:1px solid #C1D9F3'>" + (i + 1) + "</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB; border:1px solid #C1D9F3'>" + stocks.get(i).getStockCode() + "</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB; border:1px solid #C1D9F3'>" + stocks.get(i).getStockName() + "</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB; border:1px solid #C1D9F3'>" + cp + "元</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB; border:1px solid #C1D9F3'>" + stocks.get(i).getPrevClose() + "元</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB; border:1px solid #C1D9F3'>" + rates + "</td>");
				String types = "早盘";
				if(stocks.get(i).getTypes()!=0){
					types = "午盘";
				}
				builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB; border:1px solid #C1D9F3'>"+types+"</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#EFF5FB;border:1px solid #C1D9F3'>"+dateformate.format(stocks.get(i).getAddDate())+"</td>");
				builder.append("</tr>");
			} else {
				builder.append("<tr>");
				builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>" + (i + 1) + "</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>" + stocks.get(i).getStockCode() + "</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>" + stocks.get(i).getStockName() + "</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>" + cp + "元</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>" + stocks.get(i).getPrevClose() + "元</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>" + rates + "</td>");
				String types = "早盘";
				if(stocks.get(i).getTypes()!=0){
					types = "午盘";
				}
				builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA; border:1px solid #C1D9F3'>"+types+"</td>");
				builder.append("<td style='font-size:14px;text-align:center;background:#B2D7EA;border:1px solid #C1D9F3'>"+dateformate.format(stocks.get(i).getAddDate())+"</td>");
				builder.append("</tr>");
			}
		}

		builder.append("<tr><td colspan='7'><table width='100%' cellspacing='0' border='0' cellpadding='0'>");
		builder.append("<tbody><tr><td height='20'></td></tr>");
		builder.append("<tr>");
		builder.append("<td width='130'><img src='http://120.78.225.98/ws/statics/images/reprot_01.png'></td>");
		builder.append("<td width='730'>");
		builder.append("<table width='100%' cellspacing='0' border='0' cellpadding='0'>");
		builder.append("<tbody><tr>");
		builder.append("<td style='ffont-size:16px;font-family:'Microsoft YaHei',Helvetica,Arial,sans-serif;line-height:2.3;color:#333;'>Hi，亲爱的用户：</td>");
		builder.append("</tr>");
		builder.append("<tr>");
		builder.append("<td style='font-size:14px;font-family:'Microsoft YaHei',Helvetica,Arial,sans-serif;line-height:1.5;color:#333;'>");
		builder.append("我是报告快递员Paul，<span style='color: red ;'><span>以上是关注的详情。</span></span><br>");
		builder.append("以上是我为您整理的监控概述，您可以通过它快速了解股票的可买率，您也可以在详细报告中了解更多的信息。</td>");
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
		builder.append("<p><stong>赢在抄底时最佳时间是14：50分左右，暴涨暴跌的概率小。尽量在9：50分之前清盘。炒股三原则：永远记住不要亏损，第二是不要亏损，第三条是记住第一条！！</stong></p>");
		String content = builder.toString();
		mail.setToEmails(toEmail);
		
		try {
			//logger.log(Level.WARN, builder.toString());
			logger.log(Level.WARN, toEmail + "发送成功");
			mail.setContent(content);
			sendEmail(mail);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}