package com.sender.mail.targetprice;

import java.util.List;
import java.util.Map;

import com.bo.domain.Stock;


public interface TargetEmailService {
    
    /**
     * 发送邮件
     * @param mail
     */
    public void sendEmail(TargetMailModel mail) ;
    
    ///正常短信
    public void senderEmailByTargetPrice(String  toEmail,List<Stock> stocks,Map<String,String> tips);
    

}