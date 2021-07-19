package com.ly.email;

import java.io.FileNotFoundException;

import javax.mail.MessagingException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ly.task.getweekline.choose.byhand.ChooseStockConditionByHandTask;


//本类见https://www.cnblogs.com/lzxianren/p/4754168.html
//https://www.cnblogs.com/netsa/p/7923028.html
//https://www.cnblogs.com/leijiangsheng/p/7831214.html
public class MailSrvTest {
    private MailSenderSrv srv;
    
    
    public static void main(String[] args)  {
		 ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:app.xml");
		 
		/* MailSenderSrv srv =  ac.getBean(MailSenderSrv.class);
		   String from = "1039288191@qq.com";
	        String to = "1039288191@qq.com";
	        String subject = "测试主题";
	        String text = "测试内容";
	        srv.sendEmail(to, from, subject, text);*/
		 
		 ChooseStockConditionByHandTask t =  ac.getBean(ChooseStockConditionByHandTask.class);
		 
		t.getStockCodeAndNameByTxtFile();
		 
		 
	}

    public void testTextMail() {
        String from = "test@163.com";
        String to = "test@163.com";
        String subject = "测试主题";
        String text = "测试内容";
        this.srv.sendEmail(to, from, subject, text);
    }

    public void testHtmlMail() {
        String from = "test@163.com";
        String to = "test@163.com";
        String subject = "测试主题";
        String text = "<html><a href=\"www.baidu.com\">百度</a></html>";
        try {
            this.srv.sendHtmlEmail(to, from, subject, text);
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void testHtmlMailWithAttach() {
        String from = "test@163.com";
        String to = "test@163.com";
        String subject = "测试主题";
        String text = "<html><a href=\"www.baidu.com\">百度</a></html>";
        String filePath = "d://1.sql";
        try {
            this.srv.sendHtmlEmail(to, from, subject, text, filePath);
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void testHtmlMailWithAttachAndName() {
        String from = "test@163.com";
        String to = "test@163.com";
        String subject = "测试主题";
        String text = "<html><a href=\"www.baidu.com\">百度</a></html>";
        String filePath = "d://1.sql";
        String fileName = "haha.sql";
        try {
            this.srv.sendHtmlEmail(to, from, subject, text, filePath, fileName);
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
