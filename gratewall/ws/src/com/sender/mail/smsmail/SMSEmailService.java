package com.sender.mail.smsmail;

import java.util.List;

import com.bo.domain.SMSStock;

public interface SMSEmailService {

	/**
	 * email配置
	 * 
	 * @return
	 */
	public void emailManage(String toEmails);

	/**
	 * 发送邮件
	 * 
	 * @param mail
	 */
	public void sendEmail(SMSMailModel mail);

	// 短信股
	public void senderShortEmailByMinute(String toEmail, List<SMSStock> stocks);

	// 按天
	public void senderShortEmailByDay(String toEmail, List<SMSStock> stocks);

	public void senderShortEmailByWeek(String toEmail, List<SMSStock> stocks);

}