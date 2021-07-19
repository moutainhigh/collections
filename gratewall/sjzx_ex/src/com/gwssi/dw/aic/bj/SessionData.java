package com.gwssi.dw.aic.bj;

/**
 * ��¼Session��һЩ������Ϣ
 * @author BarryWei
 */
public class SessionData
{
	/**
	 * ��½IP
	 */
	private String ipAddress;
	
	/**
	 * �û���
	 */
	private String userName;

	/**
	 * ���캯��
	 * @param userName
	 * @param ipAddress
	 */
	SessionData(String userName, String ipAddress){
		this.userName  = userName;
		this.ipAddress = ipAddress;
	}
	
	public String getIpAddress()
	{
		return ipAddress;
	}

	public void setIpAddress(String ipAddress)
	{
		this.ipAddress = ipAddress;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
}
