package com.gwssi.socket.share.server;

import java.util.Map;

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
}
