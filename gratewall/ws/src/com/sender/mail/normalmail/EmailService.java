package com.sender.mail.normalmail;

import java.util.List;
import java.util.Map;

import com.bo.domain.Stock;


public interface EmailService {
    
    /**
     * email����
     * @return
     */
    public void emailManage(String toEmails);
    
    /**
     * �����ʼ�
     * @param mail
     */
    public void sendEmail(MailModel mail) ;
    
    ///��������
    public void senderEmailByMinute(String  toEmail,List<Stock> stocks,Map<String,String> tips);
    
    public void senderEmailByDay(String  toEmail,List<Stock> stocks,Map<String,String> tips);
    
    public void senderEmailByWeek(String  toEmail,List<Stock> stocks,Map<String,String> tips);

}