package com.gwssi.log.collectlog.dao;

/**
 * 
 * 
 * ��Ŀ���ƣ�bjgs_exchange �����ƣ�CollectLogVo ���������ɼ���־ �����ˣ�lizheng ����ʱ�䣺May 6, 2013
 * 2:16:05 PM �޸��ˣ�lizheng �޸�ʱ�䣺May 6, 2013 2:16:05 PM �޸ı�ע��
 * 
 * @version
 * 
 */
public class CollectLogVo
{
	/** �ɼ���־ID */
	private String	collect_joumal_id;

	/** �ɼ�����ID */
	private String	collect_task_id;

	/** �ɼ��������� */
	private String	task_name;

	/** �������ID */
	private String	service_targets_id;

	/** ����������� */
	private String	service_targets_name;

	/** �ɼ����� */
	private String	collect_type;

	/** �ɼ���ʽ */
	private String	collect_mode;

	/** ����ID */
	private String	task_id;

	/** ������ */
	private String	service_no;

	/** ����ʼʱ�� */
	private String	task_start_time;

	/** �������ʱ�� */
	private String	task_end_time;

	/** ��������ʱ�� */
	private String	task_consume_time;

	/** ���βɼ������� */
	private String	collect_data_amount;

	/** �ɼ�״̬ */
	private String	task_status;

	/** �ɼ���ڲ��� */
	private String	patameter;

	/** �ɼ������� */
	private String	return_codes;

	/** �ɼ���ID */
	private String	collect_table;

	/** �ɼ������� */
	private String	collect_table_name;

	/** �ɼ��������� */
	private String	method_name_cn;

	/** �ɼ��������� */
	private String	method_name_en;

	/** �ɼ��ֶ����� */
	private String	collect_column_name;

	/** ���ýӿ�����ʱ�� */
	private String	invoke_consume_time;

	/** ��ɼ����ʱ */
	private String	insert_consume_time;

	/** ���� */
	private String	batch_num;
	
	/** �Ƿ���ʽ���� */
	private String	is_formal;

	public void setCollect_joumal_id(String collect_joumal_id)
	{
		this.collect_joumal_id = collect_joumal_id;
	}

	public String getCollect_joumal_id()
	{
		return this.collect_joumal_id;
	}

	public void setCollect_task_id(String collect_task_id)
	{
		this.collect_task_id = collect_task_id;
	}

	public String getCollect_task_id()
	{
		return this.collect_task_id;
	}

	public void setTask_name(String task_name)
	{
		this.task_name = task_name;
	}

	public String getTask_name()
	{
		return this.task_name;
	}

	public void setService_targets_id(String service_targets_id)
	{
		this.service_targets_id = service_targets_id;
	}

	public String getService_targets_id()
	{
		return this.service_targets_id;
	}

	public void setService_targets_name(String service_targets_name)
	{
		this.service_targets_name = service_targets_name;
	}

	public String getService_targets_name()
	{
		return this.service_targets_name;
	}

	public void setCollect_type(String collect_type)
	{
		this.collect_type = collect_type;
	}

	public String getCollect_type()
	{
		return this.collect_type;
	}

	public void setTask_start_time(String task_start_time)
	{
		this.task_start_time = task_start_time;
	}

	public String getTask_start_time()
	{
		return this.task_start_time;
	}

	public void setTask_end_time(String task_end_time)
	{
		this.task_end_time = task_end_time;
	}

	public String getTask_end_time()
	{
		return this.task_end_time;
	}

	public void setTask_consume_time(String task_consume_time)
	{
		this.task_consume_time = task_consume_time;
	}

	public String getTask_consume_time()
	{
		return this.task_consume_time;
	}

	public void setCollect_data_amount(String collect_data_amount)
	{
		this.collect_data_amount = collect_data_amount;
	}

	public String getCollect_data_amount()
	{
		return this.collect_data_amount;
	}

	public void setTask_status(String task_status)
	{
		this.task_status = task_status;
	}

	public String getTask_status()
	{
		return this.task_status;
	}

	public void setPatameter(String patameter)
	{
		this.patameter = patameter;
	}

	public String getPatameter()
	{
		return this.patameter;
	}

	public String getTask_id()
	{
		return task_id;
	}

	public void setTask_id(String task_id)
	{
		this.task_id = task_id;
	}

	public String getService_no()
	{
		return service_no;
	}

	public void setService_no(String service_no)
	{
		this.service_no = service_no;
	}

	public String getReturn_codes()
	{
		return return_codes;
	}

	public void setReturn_codes(String return_codes)
	{
		this.return_codes = return_codes;
	}

	public String getCollect_mode()
	{
		return collect_mode;
	}

	public void setCollect_mode(String collect_mode)
	{
		this.collect_mode = collect_mode;
	}

	public String getCollect_table()
	{
		return collect_table;
	}

	public void setCollect_table(String collect_table)
	{
		this.collect_table = collect_table;
	}

	public String getCollect_table_name()
	{
		return collect_table_name;
	}

	public void setCollect_table_name(String collect_table_name)
	{
		this.collect_table_name = collect_table_name;
	}

	public String getMethod_name_cn()
	{
		return method_name_cn;
	}

	public void setMethod_name_cn(String method_name_cn)
	{
		this.method_name_cn = method_name_cn;
	}

	public String getMethod_name_en()
	{
		return method_name_en;
	}

	public void setMethod_name_en(String method_name_en)
	{
		this.method_name_en = method_name_en;
	}

	public String getCollect_column_name()
	{
		return collect_column_name;
	}

	public void setCollect_column_name(String collect_column_name)
	{
		this.collect_column_name = collect_column_name;
	}

	public String getInvoke_consume_time()
	{
		return invoke_consume_time;
	}

	public void setInvoke_consume_time(String invoke_consume_time)
	{
		this.invoke_consume_time = invoke_consume_time;
	}

	public String getInsert_consume_time()
	{
		return insert_consume_time;
	}

	public void setInsert_consume_time(String insert_consume_time)
	{
		this.insert_consume_time = insert_consume_time;
	}

	public String getBatch_num()
	{
		return batch_num;
	}

	public void setBatch_num(String batch_num)
	{
		this.batch_num = batch_num;
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
