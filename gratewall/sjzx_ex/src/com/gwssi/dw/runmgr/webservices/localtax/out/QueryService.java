package com.gwssi.dw.runmgr.webservices.localtax.out;

public interface QueryService
{
	/**
	 * ��ѯ��ҵ��Ϣ
	 * @param qymc
	 * @param yyzzh
	 * @return
	 */
	public ReturnMultiGSData queryQY_Info(String qymc, String yyzzh);

	/**
	 * ��ѯ��ҵ��Ϣ�б�
	 * @param cxrqq
	 * @param cxrqz
	 * @param ksjls
	 * @param jsjls
	 * @return
	 */
	public ReturnMultiGSData queryQY_Info_List(String cxrqq, String cxrqz, String ksjls, String jsjls);
	
	/**
	 * ��ѯע����ҵ��Ϣ
	 * @param qymc
	 * @param yyzzh
	 * @return
	 */
	public ReturnMultiGSData queryZXQY_Info(String qymc, String yyzzh);
	
	/**
	 * ��ѯע����ҵ�б�
	 * @param cxrqq
	 * @param cxrqz
	 * @param ksjls
	 * @param jsjls
	 * @return
	 */
	public ReturnMultiGSData queryZXQY_Info_List(String cxrqq, String cxrqz, String ksjls, String jsjls);
	
	/**
	 * ��ѯ������ҵ��Ϣ
	 * @param qymc
	 * @param yyzzh
	 * @return
	 */
	public ReturnMultiGSData queryDXQY_Info(String qymc, String yyzzh);
	
	/**
	 * ��ѯ������ҵ�б�
	 * @param cxrqq
	 * @param cxrqz
	 * @param ksjls
	 * @param jsjls
	 * @return
	 */
	public ReturnMultiGSData queryDXQY_Info_List(String cxrqq, String cxrqz, String ksjls, String jsjls);
	
	/**
	 * ��ѯ��ҵ�����Ϣ
	 * @param qymc
	 * @param yyzzh
	 * @return
	 */
	public ReturnMultiGSData queryBGQY_Info(String qymc, String yyzzh);
	
	/**
	 * ��ѯ�����ҵ�б�
	 * @param cxrqq
	 * @param cxrqz
	 * @param ksjls
	 * @param jsjls
	 * @return
	 */
	public ReturnMultiGSData queryBGQY_InfoList(String cxrqq, String cxrqz, String ksjls, String jsjls);
}
