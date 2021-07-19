package com.gwssi.dw.runmgr.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.dw.runmgr.services.SelfServiceDefine;
import com.gwssi.dw.runmgr.services.ServiceDAO;
import com.gwssi.dw.runmgr.services.ServiceDAOImpl;
import com.gwssi.dw.runmgr.services.common.ColumnBean;
import com.gwssi.dw.runmgr.services.common.ConfigBean;
import com.gwssi.dw.runmgr.services.common.Constants;
import com.gwssi.dw.runmgr.services.common.ResultParser;
import com.gwssi.dw.runmgr.services.common.SQLHelper;
import com.gwssi.dw.runmgr.services.common.ServiceBean;
import com.gwssi.dw.runmgr.services.common.UserBean;

public class TestShareService implements SelfServiceDefine
{
	ServiceDAO dao = null;
	
	public TestShareService()
	{
		dao = new ServiceDAOImpl();
	}
	
	public List getParamColumns()
	{
		List list = new ArrayList();
		ColumnBean column = new ColumnBean("ENT_NAME","企业名称","string");
		list.add(column);
		
		return list;
	}

	public List getSharedColumns()
	{
		List list = new ArrayList();
		ColumnBean column = new ColumnBean("ENT_NAME","企业名称","string");
		list.add(column);
		
		column = new ColumnBean("REG_BUS_ENT_ID","企业ID","string");
		list.add(column);
		
		column = new ColumnBean("ENT_WORD","企业WORD","string");
		list.add(column);
		
		return list;
	}

	public String getVisitURL()
	{
		return "http://localhost:8080/test/test";
	}
	
	public String getQuerySQL(ConfigBean config, Map paramMap)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM (SELECT ROWNUM AS rn, ");
		ColumnBean[] columns = config.getPermit_column();
		for(int i = 0; i < columns.length; i++){
			sql.append(columns[i].getName());
			if(i == (columns.length - 1)){//最后一个参数不再加 "," 
				continue;
			}
			sql.append(", ");
		}
		sql.append(" FROM REG_BUS_ENT");
		
		List param_columns = this.getParamColumns();
		if(param_columns == null || param_columns.size() < 1){//如果无参数字段，则只添加 "行" 过滤条件
			sql.append(") WHERE rn BETWEEN ").append(paramMap.get(Constants.SERVICE_OUT_PARAM_KSJLS)).append(" AND ").append(Constants.SERVICE_OUT_PARAM_JSJLS);
			return sql.toString();
		}
		
		sql.append(" WHERE ");
		for(int i = 0; i < param_columns.size(); i++){
			ColumnBean column = (ColumnBean) param_columns.get(i);
			sql.append(column.getName());
			
			if(i == (param_columns.size() - 1)){//最后一个参数不再加 "AND" 关键字
				continue;
			}
			sql.append(" AND ");
		}
		sql.append(") WHERE rn BETWEEN ").append(paramMap.get(Constants.SERVICE_OUT_PARAM_KSJLS)).append(" AND ").append(paramMap.get(Constants.SERVICE_OUT_PARAM_JSJLS));
		return sql.toString();
	}

	public Map query(Map params) throws DBException
	{
//		Map userMap = dao.queryUser(SQLHelper.loginSQL(""+params.get(Constants.SERVICE_IN_PARAM_LOGIN_NAME), ""+params.get(Constants.SERVICE_IN_PARAM_LOGIN_PASSWORD)));
//		UserBean user = this.buildUser(userMap);
//		//检查是否成功登录
//		if(user == null){
//			System.out.println("错误！用户名，密码不正确！");
//			return ResultParser.createLoginFailMap(params);
//		}else if (Integer.parseInt(user.getState()) != 0){
//			System.out.println("错误！用户状态为停用！");
//			return ResultParser.createLoginFailMap(params);
//		}
//		System.out.println("user id === "+user.getId()+" serv id ==== "+service.getId());
//		//获得共享服务配置
//		ConfigBean config = this.getConfig(user, service);
//		if(config == null){
//			System.out.println("错误！服务未配置给用户！");
//			return ResultParser.createParamErrorMap(params);
//		}
//		List result = dao.query(getQuerySQL(config,params));//operation.select(getQuerySQL(config, params));
//		
//		return ResultParser.createResultMap(result, params);
		return null;
	}
	
	protected UserBean buildUser(Map result){
		UserBean user = null;
		if(result.size() > 0 ){
			user = new UserBean();
			user.setId(""+result.get("SYS_SVR_USER_ID"));
			user.setCreateBy(""+result.get("CREATE_BY"));
			user.setCreateDate(""+result.get("CREATE_DATE"));
			user.setDesc(""+result.get("USER_DESC"));
			user.setName(""+result.get("LOGIN_NAME"));
			user.setPwd(""+result.get("PASSWORD"));
			user.setState(""+result.get("STATE"));
			user.setType(""+result.get("USER_TYPE"));
		}
		
		return user;
	}
	
	protected ConfigBean getConfig(UserBean user, ServiceBean service){
		ConfigBean config = null;
		try{
			//查询共享服务配置
			String sql = SQLHelper.queryConfigSQL(service.getId(), user.getId());
			Map map = dao.queryConfig(sql);
			if(map.get("SYS_SVR_CONFIG_ID") == null){
				return null;
			}
			config = new ConfigBean();
			config.setId(""+map.get("SYS_SVR_CONFIG_ID"));
			config.setService(service);
			config.setUserId(user.getId());
			config.setDate(""+map.get("CREATE_DATE"));
			config.setBy(""+map.get("CREATE_BY"));
			if(map.get("PERMIT_COLUMN") == null){
				return null;
			}
			String[] permitColumns = String.valueOf(map.get("PERMIT_COLUMN")).split(Constants.SERVICE_COLUMN_SEPARATOR);
			ColumnBean[] columns = new ColumnBean[permitColumns.length];
			for(int i = 0; i < columns.length; i++){
				ColumnBean column = new ColumnBean();
				column.setName(permitColumns[i]);
				columns[i] = column;
			}
			config.setPermit_column(columns);
		}catch(Exception e){
			return null;
		}
		return config;
	}

	public static void main(String[] args)
	{}
}
