package com.sender.mail.targetprice;

import java.util.List;
import java.util.Map;

import com.bo.domain.Stock;


public interface TargetEmailService {
    
    /**
     * �����ʼ�
     * @param mail
     */
    public void sendEmail(TargetMailModel mail) ;
    
    ///��������
    public void senderEmailByTargetPrice(String  toEmail,List<Stock> stocks,Map<String,String> tips);
    

}