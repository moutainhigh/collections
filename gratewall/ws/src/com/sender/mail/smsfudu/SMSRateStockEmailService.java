package com.sender.mail.smsfudu;

import java.util.List;

import com.bo.domain.SMSStock;

public interface SMSRateStockEmailService {
	
	public void sendEmail(SMSRateMailModel mail);

	public void senderShortEmailByRate(String toEmail, List<SMSStock> stocks);
}