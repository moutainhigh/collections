package com.ly.email;

import java.io.FileNotFoundException;

import javax.mail.MessagingException;

/**
 * 功能: 邮件发送接口.<br/>
 * @version
 */
public interface MailSenderSrv {
    /**
     * 功能: 发普通邮件，msgBody是普通的文本.<br/>
     * date: 2015年8月24日 上午9:57:19 <br/>
     *
     * @author joseph
     * @param toAddress
     * @param fromAddress
     * @param subject
     * @param msgBody
     */
    void sendEmail(String toAddress, String fromAddress, String subject, String msgBody);

    /**
     * 功能: 发html邮件或者普通邮件，msgBody是html文本或者普通文本.<br/>
     * MimeMessage 消息发送.<br/>
     * date: 2015年8月24日 上午9:57:19 <br/>
     *
     * @author joseph
     * @param toAddress
     * @param fromAddress
     * @param subject
     * @param msgBody
     * @throws MessagingException
     */
    void sendHtmlEmail(String toAddress, String fromAddress, String subject, String htmlBody) throws MessagingException;

    /**
     * 功能: 发html邮件或者普通邮件，msgBody是html文本或者普通文本，带附件.<br/>
     * MimeMessage 消息发送.<br/>
     * date: 2015年8月24日 上午9:57:19 <br/>
     *
     * @author joseph
     * @param toAddress
     * @param fromAddress
     * @param subject
     * @param msgBody
     * @throws MessagingException
     * @throws FileNotFoundException
     */
    void sendHtmlEmail(String toAddress, String fromAddress, String subject, String htmlBody, String filePath)
            throws MessagingException, FileNotFoundException;

    /**
     * 功能: 发html邮件或者普通邮件，msgBody是html文本或者普通文本，带附件.<br/>
     * MimeMessage 消息发送.<br/>
     * date: 2015年8月24日 上午9:57:19 <br/>
     *
     * @author joseph
     * @param toAddress
     * @param fromAddress
     * @param subject
     * @param htmlBody
     * @param filePath
     * @param fileName
     * @throws MessagingException
     * @throws FileNotFoundException
     */
    void sendHtmlEmail(String toAddress, String fromAddress, String subject, String htmlBody, String filePath,
            String fileName) throws MessagingException, FileNotFoundException;
}
