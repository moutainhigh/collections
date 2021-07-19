package cn.gwssi.dw.aic.bj.ecomm.dao;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.component.exception.TxnException;

public class EbEntWebSiteInfo extends BaseTable
{
   public EbEntWebSiteInfo()
   {
      
   }

   /**
    * 注册SQL语句
    */
   protected void register()
   {
	   registerSQLFunction( "queryWebSite", DaoFunction.SQL_ROWSET, "查询网站信息" );
	   registerSQLFunction( "queryWebJudgeInfo", DaoFunction.SQL_ROWSET, "网站判定信息" );
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
   
   /**
    * 查询网站信息
    * */
   public SqlStatement queryWebSite(TxnContext request,DataBus inputData) throws TxnException
	{	   
	   SqlStatement stmt = new SqlStatement();
		
		//企业id
		String reg_bus_ent_id = request.getRecord("select-key").getValue("reg_bus_ent_id");
		
		StringBuffer querySql = new StringBuffer("select t.*,t1.user_name from eb_ent_web_site_info t,eb_sys_right_user t1  where t.judge_person = t1.eb_sys_right_user_id ");
		
		
		if(reg_bus_ent_id !=null && !"".equals(reg_bus_ent_id)){
			querySql.append(" and t.reg_bus_ent_id ='"+reg_bus_ent_id+"'");
		}
		
		querySql.append(" order by t.judge_sign, t.judge_date");
		System.out.println("查询网站信息："+querySql.toString());
			
		stmt.addSqlStmt(querySql.toString());
		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
		return stmt;
	}
   
   /**
    * 网站判定信息
    * */
   public SqlStatement queryWebJudgeInfo(TxnContext request,DataBus inputData) throws TxnException
	{	   
	   SqlStatement stmt = new SqlStatement();
		
		//企业id
		String reg_bus_ent_id = request.getRecord("select-key").getValue("reg_bus_ent_id");
		String ent_web_site_info_id = request.getRecord("select-key").getValue("ent_web_site_info_id");
		//reg_bus_ent_id="a1a1a1a028fd4b8b0129117cc0ff1a60";
		//ent_web_site_info_id="03bea3c80f3347bcb3df9740456da29f";
		
		//System.out.println("判定信息ent_web_site_info_id:"+ent_web_site_info_id);
		StringBuffer querySql = new StringBuffer(" select tt.*,tt1.o1 web_type,tt1.o2 buss_mode,tt1.o3 web_content,tt1.o4 cust_dispute  from eb_ent_web_site_info tt," +
												 " (select reg_bus_ent_id,ent_web_site_info_id, max(case when type=1 then ov end )o1, " +
												 " max(case when type=2 then ov end) o2," +
												 " max(case when type=3 then ov end) o3," +
												 " max(case when type=4 then ov end) o4 from ( " +
												 " select reg_bus_ent_id,ent_web_site_info_id,type ,wm_concat( distinct t3.option_value) ov from eb_site_relat_type t3 " +
												 " where 1=1 ");
		
		
		if(reg_bus_ent_id !=null && !"".equals(reg_bus_ent_id)){
			querySql.append(" and  t3.reg_bus_ent_id ='"+reg_bus_ent_id+"'");
		}
		
		if(ent_web_site_info_id !=null && !"".equals(ent_web_site_info_id)){
			querySql.append(" and  t3.ent_web_site_info_id ='"+ent_web_site_info_id+"'");
		}
		
		querySql.append(" group by reg_bus_ent_id,t3.ent_web_site_info_id,t3.type) ");
		querySql.append(" group by reg_bus_ent_id,ent_web_site_info_id)tt1 where  tt.chr_id = tt1.ent_web_site_info_id(+) ");
		
		if(reg_bus_ent_id !=null && !"".equals(reg_bus_ent_id)){
			querySql.append(" and  tt.reg_bus_ent_id= '"+reg_bus_ent_id+"'");
		}
		
		if(ent_web_site_info_id !=null && !"".equals(ent_web_site_info_id)){
			querySql.append(" and  tt.chr_id ='"+ent_web_site_info_id+"'");
		}
		
		System.out.println("网站判定信息："+querySql.toString());
			
		stmt.addSqlStmt(querySql.toString());
		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
		return stmt;
	}

}
