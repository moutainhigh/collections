package cn.gwssi.dw.rd.metadata.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

public class SysRdUnclaimView extends BaseTable
{
	public SysRdUnclaimView()
	{

	}

	/**
	 * 注册SQL语句
	 */
	protected void register()
	{
		//
		registerSQLFunction("queryUnclaimViewList", DaoFunction.SQL_ROWSET,
				"查询未认领视图");
		registerSQLFunction("insertClaimView", DaoFunction.SQL_INSERT, "插入已认领视图");
	}

	protected void afterExecuteStmt(DaoFunction arg0, TxnContext arg1,
			DataBus[] arg2, String arg3) throws TxnException
	{
		// TODO Auto-generated method stub

	}

	protected void prepareExecuteStmt(DaoFunction arg0, TxnContext arg1,
			DataBus[] arg2, String arg3) throws TxnException
	{
		// TODO Auto-generated method stub

	}

	/**
	 * 查询未认领视图列表
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException
	 */
	public SqlStatement queryUnclaimViewList(TxnContext request, DataBus inputData)
			throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sb = new StringBuffer();
		String DataSourceID = request.getRecord("select-key").getValue("sys_rd_data_source_id");
		String object_schema = request.getRecord("select-key").getValue("object_schema");
		String view_name=request.getRecord("select-key").getValue("view_name");
		String data_object_type="V";
		//String hql="select a.SYS_RD_DATA_SOURCE_ID,a.UNCLAIM_TABLE_CODE,b.VIEW_NAME,b.VIEW_USE from sys_rd_unclaim_table a ,SYS_RD_CLAIM_VIEW b where a.DATA_OBJECT_TYPE='"+data_object_type+"'";
		String hql="select a.SYS_RD_DATA_SOURCE_ID,a.UNCLAIM_TABLE_CODE from sys_rd_unclaim_table a where a.DATA_OBJECT_TYPE='"+data_object_type+"'";
		sb.append(hql);
		if(DataSourceID!=null&&!"".equals(DataSourceID)){
			sb.append("and a.sys_rd_data_source_id =").append(DataSourceID.trim());
		}
		if(object_schema!=null&&!"".equals(object_schema)){
			sb.append("and a.object_schema =").append(object_schema.trim());
		}
		if(view_name!=null&&!"".equals(view_name)){
			sb.append("and b.view_name =").append(view_name.trim());
		}
		sb.append("and a.SYS_RD_UNCLAIM_TABLE_ID not in (select b.SYS_RD_UNCLAIM_TABLE_ID from sys_rd_unclaim_table c ,SYS_RD_CLAIM_VIEW d where c.sys_rd_data_source_id=d.SYS_RD_DATA_SOURCE_ID and c.object_schema=d.VIEW_NAME and c.unclaim_table_code = d.view_name) ");
		stmt.addSqlStmt(sb.toString());
		return stmt;

	}
	
	public SqlStatement queryUnclaimView(TxnContext request, DataBus inputData)throws TxnException{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sb = new StringBuffer();
		String data_object_type="V";
		String hql="";
		return stmt;
		
	}
	public SqlStatement insertClaimView(TxnContext request,DataBus inputData) throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		return stmt;
		
	}
	

}
