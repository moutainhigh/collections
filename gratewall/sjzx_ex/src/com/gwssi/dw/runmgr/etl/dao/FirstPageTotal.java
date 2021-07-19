package com.gwssi.dw.runmgr.etl.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

import com.gwssi.dw.runmgr.etl.txn.ETLConstants;

public class FirstPageTotal extends BaseTable
{
   public FirstPageTotal()
   {
      
   }

   /**
    * ע��SQL���
    */
   protected void register()
   {
	   registerSQLFunction( "queryFirstPageTotal", DaoFunction.SQL_ROWSET, "��ҳ������" );
	   registerSQLFunction( "queryEntStatsDetail", DaoFunction.SQL_ROWSET, "ͳ����ϸ��Ϣ" );
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
	public SqlStatement queryFirstPageTotal( TxnContext request, DataBus inputData ){		
		SqlStatement stmt = new SqlStatement();
		String sql = "select first_page_total_id,first_page_total_cls,first_page_total_num from first_page_total_new";
		stmt.addSqlStmt(sql);
		return stmt;
	}
	
	public SqlStatement queryEntStatsDetail( TxnContext request, DataBus inputData ){		
		SqlStatement stmt = new SqlStatement();
		String entType = request.getRecord("select-key").getValue("ent_type");
		String entClass = request.getRecord("select-key").getValue("ent_class");
		if(entClass == null ){//Ĭ�ϰ���ҵ���ͳ��
			entClass = ETLConstants.ENT_STATS_SHOW_TYPE_ENT_TYPE;
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(t.ent_num) as hs, sum(t.ent_sum) as zbje, t.ent_stas_state, t.ent_state from first_page_ent_stat t where t.ent_class='")
		.append(entClass).append("' ");
		if(entType != null && !entType.equalsIgnoreCase(ETLConstants.ENT_STATS_QUERY_ALL)){
			sql.append(" and t.ent_type='").append(entType.toUpperCase()).append("'");
		}else{
			sql.append(" and t.ent_type<>'GT' and t.ent_type<>'FQ' and t.ent_type<>'JG' ");
		}
		sql.append(" group by t.ent_stas_state,t.ent_state,t.ent_stas_code ");
		if(entClass.equals(ETLConstants.ENT_STATS_SHOW_TYPE_YEAR)){
			sql.append(" order by t.ent_stas_state desc ");
		}else{
			sql.append(" order by t.ent_stas_code ");
		}
//		System.out.println("sql:"+sql);
		stmt.addSqlStmt(sql.toString());
		return stmt;
	}
}

