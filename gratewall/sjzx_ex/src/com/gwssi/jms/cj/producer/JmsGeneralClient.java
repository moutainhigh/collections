package com.gwssi.jms.cj.producer;

import java.util.Map;

import com.gwssi.log.collectlog.dao.CollectLogVo;

/**
 * 
 * 
 * ��Ŀ���ƣ�bjgs_exchange �����ƣ�GeneralClient ���������ͻ��˽ӿ� �����ˣ�lizheng ����ʱ�䣺Apr 2, 2013
 * 3:51:22 PM �޸��ˣ�lizheng �޸�ʱ�䣺Apr 2, 2013 3:51:22 PM �޸ı�ע��
 * 
 * @version
 * 
 */
public interface JmsGeneralClient
{
	/**
	 * 
	 * colGetDom �ɼ�����
	 * 
	 * @return String
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	String colGetDom(Map param, CollectLogVo collectLogVo);

	
}
