package com.ly.email;

import java.io.File;
import java.io.FileNotFoundException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * 功能: 邮件发送实现.<br/>
 * date: 2015年8月24日 上午10:07:01 <br/>
 *
 * @author joseph
 * @version
 */
@Service("mailSenderSrv")
public class MailSenderSrvImpl implements MailSenderSrv {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * @see com.ly.email.temp.wisdombud.wisdomhr.common.srv.MailSenderSrv#sendEmail(java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String)
     */

    @Override
    public void sendEmail(String toAddress, String fromAddress, String subject, String msgBody) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromAddress);
        simpleMailMessage.setTo(toAddress);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(msgBody);
        mailSender.send(simpleMailMessage);
    }

    /**
     * @throws MessagingException
     * @see com.ly.email.temp.wisdombud.wisdomhr.common.srv.MailSenderSrv#sendHtmlEmail(java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String)
     */

    @Override
    public void sendHtmlEmail(String toAddress, String fromAddress, String subject, String htmlBody)
            throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        helper.setTo(toAddress);
        helper.setFrom(fromAddress);
        helper.setText(htmlBody, true);
        helper.setSubject(subject);

        mailSender.send(message);

    }

    /**
     * @throws MessagingException
     * @throws FileNotFoundException
     * @see com.ly.email.temp.wisdombud.wisdomhr.common.srv.MailSenderSrv#sendHtmlEmail(java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String)
     */

    @Override
    public void sendHtmlEmail(String toAddress, String fromAddress, String subject, String htmlBody, String filePath)
            throws MessagingException, FileNotFoundException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(toAddress);
        helper.setFrom(fromAddress);
        helper.setText(htmlBody, true);
        helper.setSubject(subject);
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("找不到附件：" + filePath);
        }
        helper.addAttachment(file.getName(), file);
        mailSender.send(message);
    }

    /**
     * @throws MessagingException
     * @throws FileNotFoundException
     * @see com.ly.email.temp.wisdombud.wisdomhr.common.srv.MailSenderSrv#sendHtmlEmail(java.lang.String,
     *      java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String)
     */

    @Override
    public void sendHtmlEmail(String toAddress, String fromAddress, String subject, String htmlBody, String filePath,
            String fileName) throws MessagingException, FileNotFoundException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(toAddress);
        helper.setFrom(fromAddress);
        helper.setText(htmlBody, true);
        helper.setSubject(subject);
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("找不到附件：" + filePath);
        }
        helper.addAttachment(fileName, file);
        mailSender.send(message);
    }
}

