package com.gwssi.log.sharelog.dao;

public class ShareLogVo
{
	/** ������־ID */
	private String	log_id;

	/** ����ID */
	private String	service_id;

	/** ������ */
	private String	service_no;

	/** �������� */
	private String	service_name;

	/** �������ID */
	private String	service_targets_id;

	/** ����������� */
	private String	service_targets_name;

	/** ����������� */
	private String	targets_type;

	/** �������� */
	private String	service_type;

	/** ����IP */
	private String	access_ip;

	/** ���ʿ�ʼʱ�� */
	private String	service_start_time;

	/** ���ʽ���ʱ�� */
	private String	service_end_time;

	/** ���η�������ʱ�� */
	private String	consume_time;

	/** ��ʼ��¼�� */
	private String	record_start;

	/** ������¼�� */
	private String	record_end;

	/** ���η��������� */
	private String	record_amount;

	/** ��ڲ���ֵ */
	private String	patameter;

	/** ����״̬ */
	private String	service_state;

	/** ���񷵻��� */
	private String	return_codes;

	/** ��־���� */
	private String	log_type;

	/** ��ѯ�����ʱ */
	private String	sel_res_consume;

	/** ��ѯ��������ʱ */
	private String	sel_count_consume;

	/** ������ */
	private String	all_amount;

	/** �Ƿ���ʽ���� */
	private String	is_formal;

	public String getService_no()
	{
		return service_no;
	}

	public void setService_no(String service_no)
	{
		this.service_no = service_no;
	}

	public String getService_targets_name()
	{
		return service_targets_name;
	}

	public void setService_targets_name(String service_targets_name)
	{
		this.service_targets_name = service_targets_name;
	}

	public String getTargets_type()
	{
		return targets_type;
	}

	public void setTargets_type(String targets_type)
	{
		this.targets_type = targets_type;
	}

	public String getLog_type()
	{
		return log_type;
	}

	public void setLog_type(String log_type)
	{
		this.log_type = log_type;
	}

	public void setLog_id(String log_id)
	{
		this.log_id = log_id;
	}

	public String getLog_id()
	{
		return this.log_id;
	}

	public void setService_targets_id(String service_targets_id)
	{
		this.service_targets_id = service_targets_id;
	}

	public String getService_targets_id()
	{
		return this.service_targets_id;
	}

	public void setService_type(String service_type)
	{
		this.service_type = service_type;
	}

	public String getService_type()
	{
		return this.service_type;
	}

	public void setService_id(String service_id)
	{
		this.service_id = service_id;
	}

	public String getService_id()
	{
		return this.service_id;
	}

	public void setService_name(String service_name)
	{
		this.service_name = service_name;
	}

	public String getService_name()
	{
		return this.service_name;
	}

	public void setService_start_time(String service_start_time)
	{
		this.service_start_time = service_start_time;
	}

	public String getService_start_time()
	{
		return this.service_start_time;
	}

	public void setService_end_time(String service_end_time)
	{
		this.service_end_time = service_end_time;
	}

	public String getService_end_time()
	{
		return this.service_end_time;
	}

	public void setAccess_ip(String access_ip)
	{
		this.access_ip = access_ip;
	}

	public String getAccess_ip()
	{
		return this.access_ip;
	}

	public void setConsume_time(String consume_time)
	{
		this.consume_time = consume_time;
	}

	public String getConsume_time()
	{
		return this.consume_time;
	}

	public void setRecord_start(String record_start)
	{
		this.record_start = record_start;
	}

	public String getRecord_start()
	{
		return this.record_start;
	}

	public void setRecord_end(String record_end)
	{
		this.record_end = record_end;
	}

	public String getRecord_end()
	{
		return this.record_end;
	}

	public void setRecord_amount(String record_amount)
	{
		this.record_amount = record_amount;
	}

	public String getRecord_amount()
	{
		return this.record_amount;
	}

	public void setPatameter(String patameter)
	{
		this.patameter = patameter;
	}

	public String getPatameter()
	{
		return this.patameter;
	}

	public void setService_state(String service_state)
	{
		this.service_state = service_state;
	}

	public String getService_state()
	{
		return this.service_state;
	}

	public void setReturn_codes(String return_codes)
	{
		this.return_codes = return_codes;
	}

	public String getReturn_codes()
	{
		return this.return_codes;
	}

	public String getSel_res_consume()
	{
		return sel_res_consume;
	}

	public void setSel_res_consume(String sel_res_consume)
	{
		this.sel_res_consume = sel_res_consume;
	}

	public String getSel_count_consume()
	{
		return sel_count_consume;
	}

	public void setSel_count_consume(String sel_count_consume)
	{
		this.sel_count_consume = sel_count_consume;
	}

	public String getAll_amount()
	{
		return all_amount;
	}

	public void setAll_amount(String all_amount)
	{
		this.all_amount = all_amount;
	}

	public String getIs_formal()
	{
		return is_formal;
	}

	public void setIs_formal(String is_formal)
	{
		this.is_formal = is_formal;
	}
}
