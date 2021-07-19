package com.sender.mail.fudu;

import java.util.List;
import java.util.Map;

import com.bo.domain.Stock;

public interface RateStockEmailService {
	
	public void sendEmail(RateMailModel mail);

	public void senderEmailByRate(String toEmail, List<Stock> stocks, Map map);
}