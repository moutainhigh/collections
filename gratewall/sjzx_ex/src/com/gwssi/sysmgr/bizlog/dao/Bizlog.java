package com.gwssi.sysmgr.bizlog.dao;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.component.exception.TxnException;

public class Bizlog extends BaseTable
{
   public Bizlog()
   {
      
   }

   /**
    * 注册SQL语句
    */
   protected void register()
   {
	   registerSQLFunction("selectBizLogList", DaoFunction.SQL_ROWSET, "查询业务日志列表");
		
   }
   
   public SqlStatement selectBizLogList(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String opername = request.getRecord("select-key").getValue("opername");
		String username = request.getRecord("select-key").getValue("username");
		String orgid = request.getRecord("select-key").getValue("jgid_fk");
		String regdate_from = request.getRecord("select-key").getValue("regdate_from");
		String regdate_to = request.getRecord("select-key").getValue("regdate_to");
		StringBuffer sql = new StringBuffer(
				" select flowno, reqflowno, username, opername, orgid, orgname, regdate, regtime, " +
				"trdcode, trdname, trdtype, errcode, errdesc from biz_log where 1=1 ");
		if (StringUtils.isNotBlank(opername)) {
			sql.append(" and opername ='").append(opername).append("' ");
		}
		if (StringUtils.isNotBlank(username)) {
			sql.append(" and username like '%").append(username).append("%' ");
		}
		if (StringUtils.isNotBlank(orgid)) {
			sql.append(" and orgid in (select '").append(orgid).append("' jgid_pk from dual union all ")
			.append("select t.jgid_pk from xt_zzjg_jg t start with t.sjjgid_fk = '").append(orgid)
			.append("' connect by prior t.jgid_pk = t.sjjgid_fk) ");
			//System.out.println("========================\n"+sql.toString());
		}
		if (StringUtils.isNotBlank(regdate_from)) {
			regdate_from=regdate_from.replaceAll("-", "");
			sql.append(" and regdate >='").append(regdate_from).append("' ");
		}
		if (StringUtils.isNotBlank(regdate_to)) {
			regdate_to=regdate_to.replaceAll("-", "");
			sql.append(" and regdate <='").append(regdate_to).append("' ");
		}
		stmt.setCountStmt("select count(1) from (" + sql.toString() + ")");
        sql.append(" order by  regdate desc ,regtime desc");
		stmt.addSqlStmt(sql.toString());
		return stmt;
	}

   /**
    * 执行SQL语句前的处理
    */
   public void prepareExecuteStmt(DaoFunction func, TxnContext request, 
		   DataBus[] inputData, String outputNode) throws TxnException
   {
      
   }

   /**
    * 执行完SQL语句后的处理
    */
   public void afterExecuteStmt(DaoFunction func, TxnContext request, 
		   DataBus[] inputData, String outputNode) throws TxnException
   {
      
   }

}
