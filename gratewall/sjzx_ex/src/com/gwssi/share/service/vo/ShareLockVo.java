package com.gwssi.share.service.vo;

public class ShareLockVo
{
	/** ��������ID */
	private String	service_lock_id;
	
	/** �������ID */
	private String	service_targets_id;
	
	/** ����ID */
	private String	service_id;
	
	/** ���������� */
	private String	lock_code;
	
	/** ����������� */
	private String	lock_desc;
	
	/** ����ʱ�� */
	private String	lock_time;

	public String getService_lock_id()
	{
		return service_lock_id;
	}

	public void setService_lock_id(String service_lock_id)
	{
		this.service_lock_id = service_lock_id;
	}

	public String getService_targets_id()
	{
		return service_targets_id;
	}

	public void setService_targets_id(String service_targets_id)
	{
		this.service_targets_id = service_targets_id;
	}

	public String getService_id()
	{
		return service_id;
	}

	public void setService_id(String service_id)
	{
		this.service_id = service_id;
	}

	public String getLock_code()
	{
		return lock_code;
	}

	public void setLock_code(String lock_code)
	{
		this.lock_code = lock_code;
	}

	public String getLock_desc()
	{
		return lock_desc;
	}

	public void setLock_desc(String lock_desc)
	{
		this.lock_desc = lock_desc;
	}

	public String getLock_time()
	{
		return lock_time;
	}

	public void setLock_time(String lock_time)
	{
		this.lock_time = lock_time;
	}
	
}
