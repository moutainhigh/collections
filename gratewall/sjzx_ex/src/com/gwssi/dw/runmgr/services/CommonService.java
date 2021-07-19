package com.gwssi.dw.runmgr.services;

import java.util.List;
import java.util.Map;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.dw.runmgr.services.common.Constants;
import com.gwssi.dw.runmgr.services.common.ResultParser;
import com.gwssi.dw.runmgr.services.common.SQLHelper;
import com.gwssi.dw.runmgr.services.common.UserParamAnalyzer;

public class CommonService extends AbsGeneralService
{
	ServiceDAO dao = null;
		
	public CommonService()
	{
		dao = new ServiceDAOImpl();
	}
	
	public Map query(Map params) throws DBException
	{
		int ksjls = Integer.parseInt(""+params.get(Constants.SERVICE_OUT_PARAM_KSJLS));
		int jsjls = Integer.parseInt(""+params.get(Constants.SERVICE_OUT_PARAM_JSJLS));
		String sql = "" + params.get("QUERY_SQL");
		System.out.println("sql----------------------------------->"+sql);
		UserParamAnalyzer up = new UserParamAnalyzer(params);
		sql = up.createSQL();
		try {
			if(params.get("IS_TEST") != null){
				String testSql = SQLHelper.getTestSQL(sql);
				System.out.println("getTestSQL--->"+testSql);
				List result = dao.query(testSql);
				System.out.println("result--->"+result);
				String countSql = SQLHelper.getCountSQL(sql);
				System.out.println("getCountSQL--->"+countSql);
				String total = dao.count(countSql).toString();
				System.out.println("total----->"+total);
				return ResultParser.createTestResultMap(result, total);
			}else{
				String querySql = SQLHelper.getQuerySQL(sql, ksjls, jsjls);
				System.out.println("querySql---->"+querySql);
				List result = dao.query(querySql);
				System.out.println("result.size()--->"+result.size());
				String total = "0";
				if(result != null){
					System.out.println("result size===>"+result.size());
					total = "" + result.size();
					if(result.size() > 1){//结果集大于一条记录时才进行count
						String countSql = SQLHelper.getCountSQL(sql);
						System.out.println("getCountSQL--->"+countSql);
						total = dao.count(countSql).toString();
						System.out.println("total----->"+total);
					}
				}
				
				System.out.println("sql==111111111111====>"+sql);
				
				return ResultParser.createResultMap(result, params, total);
			}
		} catch (DBException e) {
			return ResultParser.createUnknownFailMap(params);
		}
	}
}
