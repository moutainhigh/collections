package com.gwssi.webservice.server;

import java.util.Map;

import cn.gwssi.common.component.exception.TxnException;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.log.sharelog.dao.ShareLogVo;

/**
 * 
 * 
 * ��Ŀ���ƣ�bjgs_exchange �����ƣ�GeneralService ������������ӿ� �����ˣ�lizheng ����ʱ�䣺Mar 28, 2013
 * 10:10:38 AM �޸��ˣ�lizheng �޸�ʱ�䣺Mar 28, 2013 10:10:38 AM �޸ı�ע��
 * 
 * @version
 * 
 */
public interface GeneralService
{
	/**
	 * 
	 * query ����ֵMap�ķ���
	 * 
	 * @param params
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	Map query(Map params, ShareLogVo shareLogVo) throws DBException;

	/**
	 * 
	 * queryData ����ֵdom�ķ���
	 * 
	 * @param params
	 * @return
	 * @throws DBException
	 *             String
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	String queryData(Map params, ShareLogVo shareLogVo) throws DBException;
	
	String queryTrsData(Map params, ShareLogVo shareLogVo) throws TxnException;
}
