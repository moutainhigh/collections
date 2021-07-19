package com.gwssi.dw.aic.bj;

/**
 * 记录Session的一些附加信息
 * @author BarryWei
 */
public class SessionData
{
	/**
	 * 登陆IP
	 */
	private String ipAddress;
	
	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 构造函数
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
