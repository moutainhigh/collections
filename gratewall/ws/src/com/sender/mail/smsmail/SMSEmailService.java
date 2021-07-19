package com.sender.mail.smsmail;

import java.util.List;

import com.bo.domain.SMSStock;

public interface SMSEmailService {

	/**
	 * email����
	 * 
	 * @return
	 */
	public void emailManage(String toEmails);

	/**
	 * �����ʼ�
	 * 
	 * @param mail
	 */
	public void sendEmail(SMSMailModel mail);

	// ���Ź�
	public void senderShortEmailByMinute(String toEmail, List<SMSStock> stocks);

	// ����
	public void senderShortEmailByDay(String toEmail, List<SMSStock> stocks);

	public void senderShortEmailByWeek(String toEmail, List<SMSStock> stocks);

}