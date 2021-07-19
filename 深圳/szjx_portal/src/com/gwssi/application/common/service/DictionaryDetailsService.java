package com.gwssi.application.common.service;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.util.StringUtil;

@Service(value = "dictionaryDetailsService")
public class DictionaryDetailsService extends BaseService{
	
	/**
	 * 通过代码表名，获取该代码表的结构
	 * @param codeTableEnName
	 * @return 代码表的表结构集合
	 * @throws OptimusException 
	 */
	public List queryStructList(String codeTableEnName) throws OptimusException{
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//编写sql语句
		String sql = "select c.column_name,b.comments,d.constraint_type,c.data_type,c.nullable,c.data_length "
				+ "from (SELECT a.column_name,a.comments FROM all_col_comments a WHERE a.table_name = ?) b,"
						+ "(SELECT t.column_name,t.data_type,t.nullable,t.data_length FROM user_tab_columns t WHERE t.table_name = ? ) c,"
								+ "(SELECT m.COLUMN_NAME,n.CONSTRAINT_TYPE FROM USER_CONS_COLUMNS m, USER_CONSTRAINTS n "
								+ "WHERE m.CONSTRAINT_NAME = n.CONSTRAINT_NAME and m.table_name = ?) d "
										+ "WHERE c.COLUMN_NAME = b.COLUMN_NAME (+) and c.COLUMN_NAME = d.COLUMN_NAME (+)";
		
		listParam.add(codeTableEnName);
		listParam.add(codeTableEnName);
		listParam.add(codeTableEnName);
		
		//封装结果集
		List list = dao.pageQueryForList(sql, listParam);
        return list;
	}
	
	/**
	 * 通过代码表中的代码、名称，查询记录
	 * @param params codeTableEnName
	 * @return 查询到的代码信息集合
	 * @throws OptimusException 
	 */
	public List queryValue(Map params, String codeTableEnName) throws OptimusException{
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//获取查询条件的值
		String code = StringUtil.getMapStr(params, "code").trim().toUpperCase();
		
		String name = StringUtil.getMapStr(params, "name").trim();
		
		//编写sql
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ").append(codeTableEnName).append(" where 1=1");
		
		
		//effectiveCode有值时
		if(StringUtils.isNotEmpty(code)){
			sql.append(" and CODE like ?");
			listParam.add("%"+code+"%");
			
			
		}
		
		//name有值时
		if(StringUtils.isNotEmpty(name)){
			sql.append(" and NAME like ?");
			listParam.add("%"+name+"%");
		}
		
		
		//封装结果集
		List list = dao.pageQueryForList(sql.toString(), listParam);
		return list;
	}
	
	/**
	 * 通过代码表值查询记录中是否有相同的记录
	 * @param effectiveCode codeTableEnName
	 * @throws OptimusException 
	 */
	public Boolean queryCodeTableByCode(String code,
			String codeTableEnName) throws OptimusException{
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//判断effectiveCode
		if(StringUtils.isBlank(code))
			return false;
		List<Map> lit = null;
		
		//编写sql
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ").append(codeTableEnName).append(" where CODE=?");
		listParam.add(code);
		
		//封装结果集
		List<Map> list=dao.queryForList(sql.toString(),listParam);
		//System.out.println("+++++++++++++++++++++"+list);
		
		if(list!=null&&!list.isEmpty())
			return true;
		return false;
		
	}
	
	/**
	 * 删除选中的代码值信息，即将其的EFFECTIVE_MARKER设为'N'
	 * @param effectiveCode codeTableEnName
	 * @throws OptimusException 
	 */
	public void deleteValue(String code, String codeTableEnName) throws OptimusException{
		 
		 IPersistenceDAO dao = getPersistenceDAO();
		 List listParam = new ArrayList();
		 
		 //编写sql,将删除的记录的EFFECTIVE_MARKER改为N
		 StringBuffer sql = new StringBuffer();
		 sql.append("delete from ").append(codeTableEnName).append("  where CODE=?");
		 listParam.add(code);
		 
		 //执行sql
		 dao.execute(sql.toString(), listParam);
		
	}
	
	/**
	 * 保存添加的新纪录
	 * @param list codeTableEnName
	 * @throws OptimusException 
	 * @throws ParseException 
	 */
	public void saveValue(List list, String codeTableEnName) throws OptimusException{
		 
		 IPersistenceDAO dao = getPersistenceDAO();
		 List listParam = new ArrayList();
		 
		 //获取填写的数据
		 String code = (String) list.get(0);
		 String name = (String) list.get(1);
		 String nameShort = (String) list.get(2);
		 String fCode = (String) list.get(3);
		 String chooseMark = (String) list.get(4);
		 String createrName = (String) list.get(5);
		 String userId = (String)list.get(6);
		
		 
		 //编写添加sql语句
		 StringBuffer sql = new StringBuffer();
		 sql.append("insert into ").append(codeTableEnName).append("(CODE,NAME,NAME_SHORT,F_CODE,CHOOSE_MARK,CREATER_ID,CREATER_NAME,CREATER_TIME,MODIFIER_ID,MODIFIER_NAME,MODIFIER_TIME) ")
		 	.append("values(?,?,?,?,?,?,?,?,?,?,?)");
		 
		 listParam.add(code);
		 listParam.add(name);
		 listParam.add(nameShort);
		 listParam.add(fCode);
		 listParam.add(chooseMark);
		 listParam.add(userId);
		 listParam.add(createrName);
		 listParam.add(list.get(7));
		 listParam.add(userId);
		 listParam.add(createrName);
		 listParam.add(list.get(7));
		 
		 //执行sql
		 dao.execute(sql.toString(), listParam);
		
	}
	
	/**
	 * 根据代码查询获取已有的信息
	 * @param codeTableEnName code
	 * @return 代码信息集合
	 * @throws OptimusException 
	 */
	public List getDisplayValue(String codeTableEnName, String code) throws OptimusException{
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//编写查询sql
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ").append(codeTableEnName).append(" where CODE=?");
		listParam.add(code);
		
		//封装结果集
		List list = dao.queryForList(sql.toString(), listParam);
        return list;
        
	}
	
	/**
	 * 保存修改的代码值信息
	 * @param list codeTableEnName
	 * @throws OptimusException 
	 */
	public void updateValue(List list, String codeTableEnName) throws OptimusException{
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();

		//获取填写的数据
		String code = (String) list.get(0);
		String name = (String) list.get(1);
		String nameShort = (String) list.get(2);
		String fCode = (String) list.get(3);
		String chooseMark = (String) list.get(4);
		String modifierId = (String) list.get(6);
		String modifierName = (String) list.get(8);
		
		//编写修改的sql语句
		StringBuffer sql = new StringBuffer();
		sql.append("update ").append(codeTableEnName).append(" set NAME=?,NAME_SHORT=?,"
				+ "F_CODE=?,CHOOSE_MARK=?,MODIFIER_ID=?,MODIFIER_NAME=?,MODIFIER_TIME=? ").append("where CODE=?");
		
		listParam.add(name);
		listParam.add(nameShort);
		listParam.add(fCode);
		listParam.add(chooseMark);
		listParam.add(modifierId);
		listParam.add(modifierName);
		listParam.add(list.get(7));
		listParam.add(code);
		
		//执行sql语句
		dao.execute(sql.toString(), listParam);
		
	}
	
	/**
	 * 判断代码表是否存在
	 * @param  codeTableEnName
	 * @throws OptimusException 
	 */
	public Boolean  queryTable(String codeTableEnName) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//编写sql语句
		StringBuffer sql = new StringBuffer();
		sql.append("select table_name from user_tables where table_name=?");
		listParam.add(codeTableEnName);
		
		//封装结果集
		List<Map> list = dao.queryForList(sql.toString(), listParam);
		
		//判断list
		if(list!=null&&!list.isEmpty())
			return true;
		return false;
		
	}
}
