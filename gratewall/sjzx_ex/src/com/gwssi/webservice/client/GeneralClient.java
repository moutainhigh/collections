package com.gwssi.webservice.client;

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
public interface GeneralClient
{
	/**
	 * 
	 * collectData ���չ����ṩ�ı�׼��ʽ�ɼ�����
	 * 
	 * @param param
	 * @param collectLogVo
	 * @param countMap
	 * @return String
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	String collectData(Map param, CollectLogVo collectLogVo, Map countMap);

	/**
	 * 
	 * collectQualitySupervisionData �ɼ��ʼ������
	 * 
	 * @param param
	 * @param collectLogVo
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	Map collectQualitySupervisionData(Map param, CollectLogVo collectLogVo);

	/**
	 * 
	 * collectSocialSecurityData �ɼ������籣����
	 * 
	 * @param param
	 * @param collectLogVo
	 * @return String
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	String collectSocialSecurityData(Map param, CollectLogVo collectLogVo);
}
