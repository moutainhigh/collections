package com.gwssi.dw.runmgr.webservices.localtax.out;

public interface QueryService
{
	/**
	 * 查询企业信息
	 * @param qymc
	 * @param yyzzh
	 * @return
	 */
	public ReturnMultiGSData queryQY_Info(String qymc, String yyzzh);

	/**
	 * 查询企业信息列表
	 * @param cxrqq
	 * @param cxrqz
	 * @param ksjls
	 * @param jsjls
	 * @return
	 */
	public ReturnMultiGSData queryQY_Info_List(String cxrqq, String cxrqz, String ksjls, String jsjls);
	
	/**
	 * 查询注销企业信息
	 * @param qymc
	 * @param yyzzh
	 * @return
	 */
	public ReturnMultiGSData queryZXQY_Info(String qymc, String yyzzh);
	
	/**
	 * 查询注销企业列表
	 * @param cxrqq
	 * @param cxrqz
	 * @param ksjls
	 * @param jsjls
	 * @return
	 */
	public ReturnMultiGSData queryZXQY_Info_List(String cxrqq, String cxrqz, String ksjls, String jsjls);
	
	/**
	 * 查询吊销企业信息
	 * @param qymc
	 * @param yyzzh
	 * @return
	 */
	public ReturnMultiGSData queryDXQY_Info(String qymc, String yyzzh);
	
	/**
	 * 查询吊销企业列表
	 * @param cxrqq
	 * @param cxrqz
	 * @param ksjls
	 * @param jsjls
	 * @return
	 */
	public ReturnMultiGSData queryDXQY_Info_List(String cxrqq, String cxrqz, String ksjls, String jsjls);
	
	/**
	 * 查询企业变更信息
	 * @param qymc
	 * @param yyzzh
	 * @return
	 */
	public ReturnMultiGSData queryBGQY_Info(String qymc, String yyzzh);
	
	/**
	 * 查询变更企业列表
	 * @param cxrqq
	 * @param cxrqz
	 * @param ksjls
	 * @param jsjls
	 * @return
	 */
	public ReturnMultiGSData queryBGQY_InfoList(String cxrqq, String cxrqz, String ksjls, String jsjls);
}
