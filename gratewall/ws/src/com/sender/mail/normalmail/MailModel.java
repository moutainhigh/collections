package com.sender.mail.normalmail;
import java.util.Map;
//�������http://www.jb51.net/article/111497.htm
//http://cuisuqiang.iteye.com/blog/1750866
//http://lpf.iteye.com/blog/1495546
//http://www.jb51.net/article/81415.htm
public class MailModel {
    
    /**
     * ���������������
     */
    private String emailHost;
    /**
     * ����������
     */
    private String emailFrom;

    /**
     * �������û���
     */
    private String emailUserName;

    /**
     * ����������
     */
    private String emailPassword;

    /**
     * �ռ������䣬��������ԡ�;���ָ�
     */
    private String toEmails;
    /**
     * �ʼ�����
     */
    private String subject;
    /**
     * �ʼ�����
     */
    private String content;
    /**
     * �ʼ��е�ͼƬ��Ϊ��ʱ��ͼƬ��map�е�keyΪͼƬID��valueΪͼƬ��ַ
     */
    private Map<String, String> pictures;
    /**
     * �ʼ��еĸ�����Ϊ��ʱ�޸�����map�е�keyΪ����ID��valueΪ������ַ
     */
    private Map<String, String> attachments;
    
    
    private String fromAddress;//�����˵�ַ1��
    
    private String toAddresses;//�����˵�ַ,����Ϊ�ܶ����ÿ����ַ֮����";"�ָ����ȷ�˵450065208@qq.com;lpf@sina.com
    
    private String[] attachFileNames;//���� 

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddresses() {
        return toAddresses;
    }

    public void setToAddresses(String toAddresses) {
        this.toAddresses = toAddresses;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getAttachFileNames() {
        return attachFileNames;
    }

    public void setAttachFileNames(String[] attachFileNames) {
        this.attachFileNames = attachFileNames;
    }

    public String getEmailHost() {
        return emailHost;
    }

    public void setEmailHost(String emailHost) {
        this.emailHost = emailHost;
    }

    public String getEmailFrom() {
        return emailFrom;
    }

    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }

    public String getEmailUserName() {
        return emailUserName;
    }

    public void setEmailUserName(String emailUserName) {
        this.emailUserName = emailUserName;
    }

    public String getEmailPassword() {
        return emailPassword;
    }

    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }

    public String getToEmails() {
        return toEmails;
    }

    public void setToEmails(String toEmails) {
        this.toEmails = toEmails;
    }

    public Map<String, String> getPictures() {
        return pictures;
    }

    public void setPictures(Map<String, String> pictures) {
        this.pictures = pictures;
    }

    public Map<String, String> getAttachments() {
        return attachments;
    }

    public void setAttachments(Map<String, String> attachments) {
        this.attachments = attachments;
    }
    
    
}