package com.gwssi.sysmgr.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * ���ݱ�[view_sys_func_count]�Ĵ�����
 * @author Administrator
 *
 */
public class ViewSysFuncCount extends BaseTable
{
	public ViewSysFuncCount()
	{
		
	}
	
	/**
	 * ע���û��Զ����SQL���
	 */
	protected void register( )
	{
		registerSQLFunction( "queryDataByDate", DaoFunction.SQL_ROWSET, "��ȡ����ʹ��ͳ���б�" );
		registerSQLFunction( "querySub1DataByDate", DaoFunction.SQL_ROWSET, "��ȡ�ӹ���ʹ��ͳ���б�" );
		registerSQLFunction( "querySub2DataByDate", DaoFunction.SQL_ROWSET, "��ȡ���ӹ���ʹ��ͳ���б�" );
		registerSQLFunction( "queryLogStatistics", DaoFunction.SQL_ROWSET, "��ȡ�û�ʹ����־ͳ���б�" );
	}
	
	/**
	 * ִ��SQL���ǰ�Ĵ���
	 */
	public void prepareExecuteStmt(DaoFunction func, TxnContext request,
			DataBus[] inputData, String outputNode) throws TxnException
	{
		
	}
	
	/**
	 * ִ����SQL����Ĵ���
	 */
	public void afterExecuteStmt(DaoFunction func, TxnContext request,
			DataBus[] inputData, String outputNode) throws TxnException
	{
		
	}
	
	/**
	 * �������ڷ�Χ����ͳ��
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement queryDataByDate( TxnContext request, DataBus inputData )
	{
		DataBus db = request.getRecord("select-key");
		String query_date_from = db.getValue("query_date_from");
		String query_date_to = db.getValue("query_date_to");
		
		SqlStatement stmt = new SqlStatement( );
		String sql = "SELECT MIN (func_index) as func_index, SUBSTR (func_name, 1, 5) as func_name, SUM (querytimes) as querytimes FROM VIEW_SYS_FUNC_COUNT WHERE 1=1 ";
		if(query_date_from != null && !query_date_from.equals("")){
			sql += " and query_date >= '" + query_date_from + "'";
		}
		if(query_date_to != null && !query_date_to.equals("")){
			sql += " and query_date <= '" + query_date_to + "'";
		}
		sql += " GROUP BY SUBSTR (func_name, 1, 5) ORDER BY func_index";
		System.out.println(sql);
		stmt.addSqlStmt( sql );
		// stmt.setCountStmt( "select count(*) from view_sys_func_count" );
		return stmt;
	}
	
	public SqlStatement querySub1DataByDate( TxnContext request, DataBus inputData )
	{
		DataBus db = request.getRecord("select-key");
		String query_date_from = db.getValue("query_date_from");
		String query_date_to = db.getValue("query_date_to");
		String func_name = db.getValue("func_name");
		
		SqlStatement stmt = new SqlStatement( );
		String sql = "SELECT MIN (func_index) as func_index, func_name, SUM (querytimes) as querytimes FROM VIEW_SYS_FUNC_COUNT WHERE SUBSTR (func_name, 1, 4)='"+func_name+"' ";
		if(query_date_from != null && !query_date_from.equals("")){
			sql += " and query_date >= '" + query_date_from + "'";
		}
		if(query_date_to != null && !query_date_to.equals("")){
			sql += " and query_date <= '" + query_date_to + "'";
		}
		sql += " GROUP BY func_name ORDER BY func_index";
		System.out.println(sql);
		stmt.addSqlStmt( sql );
		// stmt.setCountStmt( "select count(*) from view_sys_func_count" );
		return stmt;
	}
	
	public SqlStatement querySub2DataByDate( TxnContext request, DataBus inputData )
	{
		DataBus db = request.getRecord("select-key");
		String query_date_from = db.getValue("query_date_from");
		String query_date_to = db.getValue("query_date_to");
		String func_name = db.getValue("func_name");
		
		SqlStatement stmt = new SqlStatement( );
		String sql = "SELECT sjjgNAME,sjjgid_fk,SUM(querytimes) as querytimes FROM VIEW_SYS_FUNC_COUNT where func_name='"+func_name+"' ";
		if(query_date_from != null && !query_date_from.equals("")){
			sql += " and query_date >= '" + query_date_from + "'";
		}
		if(query_date_to != null && !query_date_to.equals("")){
			sql += " and query_date <= '" + query_date_to + "'";
		}
		sql += " GROUP BY sjjgNAME,sjjgid_fk ORDER BY sjjgNAME";
		System.out.println(sql);
		stmt.addSqlStmt( sql );
		// stmt.setCountStmt( "select count(*) from view_sys_func_count" );
		return stmt;
	}
	
	public SqlStatement queryLogStatistics( TxnContext request, DataBus inputData )
	{
		
		DataBus db = request.getRecord("select-key");
		String query_date_from = db.getValue("query_date_from");
		String query_date_to = db.getValue("query_date_to");
		String count_type = db.getValue("count_type");
		String first_func_name = db.getValue("first_func_name");
		String second_func_name = db.getValue("second_func_name");
		String sjjgid_fk = db.getValue("sjjgid_fk");//�����־�
		
		SqlStatement stmt = new SqlStatement( );
		//StringBuffer sql = new StringBuffer("SELECT MIN(func_index) as func_index,SUBSTR(func_name, 1, 4) as first_func_name,func_name, sjjgNAME,SUM(querytimes) as querytimes FROM VIEW_SYS_FUNC_COUNT where 1=1 ");
		StringBuffer sql = new StringBuffer("select t.* from (SELECT MIN(func_index) as func_index,SUBSTR(func_name, 1, 4) as first_func_name," +
				"case when instr(func_name,'>',1,1)=0 then '' else case when instr(func_name,'>',1,2)=0 then substr(func_name,instr(func_name,'>',1,1)+1) "+
				" else  substr(func_name,instr(func_name,'>',1,1)+2,instr(func_name,'>',1,2)-instr(func_name,'>',1,1)-3) end end second_func_name, "+
				"func_name, " +
				"sjjgNAME,SUM(querytimes) as querytimes FROM VIEW_SYS_FUNC_COUNT where 1=1 ");
		
		if(count_type != null && !count_type.equals("")){
			if(count_type.equals("1")){//������ͳ��
				
				if(first_func_name != null && !first_func_name.equals("")){
					sql.append( " and SUBSTR(func_name, 1, 4) = '" + first_func_name + "'");
				}
			}else if (count_type.equals("2")){//���־�ͳ��
			
				if(sjjgid_fk != null && !sjjgid_fk.equals("")){
					
					sql.append( " and sjjgid_fk = '" + sjjgid_fk + "'");
				}
			}
		}
		
		if(query_date_from != null && !query_date_from.equals("")){
			sql.append( " and query_date >= '" + query_date_from + "'");
		}
		if(query_date_to != null && !query_date_to.equals("")){
			sql.append(" and query_date <= '" + query_date_to + "'");
		}
		
		if(count_type != null && !count_type.equals("")){
			if ("2".equals(count_type)){
				sql.append("GROUP BY func_name,sjjgname ORDER BY sjjgname,func_index ) t");
			}
			if("1".equals(count_type)){
				sql.append( " GROUP BY func_name,sjjgname ORDER BY func_index) t ");
			}
			
		}else{
			sql.append( " GROUP BY func_name,sjjgname ORDER BY func_index ) t");
		}
		
		//sql.append("select t.* from ("+sql.toString()+") t ");
		if(second_func_name != null && !second_func_name.equals("")){
			if(count_type!=null && "2".equals(count_type)){
				//�־�����£�����С�������Զ�ʧЧ
			}else {
				sql.append(" where t.second_func_name like'%"+second_func_name+"%'");
			}
			
		}
		
		//System.out.println("��־ͳ��sql��"+sql.toString());
		stmt.addSqlStmt( sql.toString() );
		stmt.setCountStmt( "select count(*) from ("+sql.toString()+")" );
		return stmt;
	}
	
}

