package cn.gwssi.dw.rd.metadata.code;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.param.ParamHelp;

public class MetadateCode extends ParamHelp{		
		/**
		 * 根据数据源取物理表
		 * @param context
		 * @return
		 * @throws TxnException
		 */
		public Recordset getTableCodeByDataSource(TxnContext context) throws TxnException {
			BaseTable table = null;
			String sys_rd_data_source_id = context.getString("input-data:sys_rd_data_source_id");
			String sys_name = context.getString("input-data:sys_name");
			StringBuffer sql = new StringBuffer("select table_code, table_name from sys_rd_table where sys_rd_data_source_id='" + sys_rd_data_source_id+"'");
			
			if(!"".equals(sys_name)){
				sql.append(" and sys_rd_system_id = '"+sys_name+"'");
			}
			
			try{
				table = TableFactory.getInstance().getTableObject(this, "sys_rd_table");
				table.executeRowset(sql.toString(),context,"selected-code-listed");
			}
			catch( TxnException e ){
				log.error( "取数据库表时错误", e );
				return null;
			}
			
			return getParamList(context.getRecordset("selected-code-listed"), "table_name", "table_code");

		}
		
		public Recordset getSysNameByType(TxnContext context) throws TxnException {
			BaseTable table = null;
			String sys_simple = context.getString("input-node:sys_simple");
			String sys_system_ids = context.getString("input-node:sys_system_ids");
			StringBuffer sql = new StringBuffer("select sys_rd_system_id, sys_name from sys_rd_system ");
			
			if(StringUtils.isNotBlank(sys_simple)){
				sql.append(" where sys_simple = '"+sys_simple+"'");
			}else{
				if(sys_system_ids!=null && sys_system_ids.indexOf(",")>0){
					String[] ids = sys_system_ids.split(",");
					for(int i=0;i<ids.length;i++){
						sys_system_ids += "'" + ids[i] + "',";
					}
					sys_system_ids = sys_system_ids.substring(0, sys_system_ids.length()-1);
					sql.append(" where sys_rd_system_id in ("+sys_system_ids+")");
				}
			}
			
			try{
				table = TableFactory.getInstance().getTableObject(this, "sys_rd_system");
				table.executeRowset(sql.toString(),context,"selected-code-listed");
			}
			catch( TxnException e ){
				log.error( "取数据库表时错误", e );
				return null;
			}
			return getParamList(context.getRecordset("selected-code-listed"), "sys_name", "sys_rd_system_id");
		}
		
		/**
		 * 根据主表取主表主键
		 * @param context
		 * @return
		 * @throws TxnException
		 */
		public Recordset getPrimaryKeyByTableCode(TxnContext context) throws TxnException {
			BaseTable table = null;
			String sys_rd_data_source_id = context.getString("input-data:sys_rd_data_source_id");
			String parent_table = context.getString("input-data:parent_table");
			String sys_name = context.getString("input-data:sys_name");
			
			StringBuffer sql = new StringBuffer("select column_code, column_name from sys_rd_column where sys_rd_table_id in (select sys_rd_table_id  from sys_rd_table where 1=1 ");
			
			if(!"".equals(sys_rd_data_source_id)){
				sql.append(" and sys_rd_data_source_id = '"+sys_rd_data_source_id+"'");
			}
			
			if(!"".equals(parent_table)){
				sql.append(" and table_code = '"+parent_table+"'");
			}
			
			if(!"".equals(sys_name)){
				sql.append(" and sys_rd_system_id = '"+sys_name+"'");
			}
			
			sql.append(")");
			//System.out.println("根据主表取主表主键::"+sql.toString());
			
			try{
				table = TableFactory.getInstance().getTableObject(this, "sys_rd_column");
				table.executeRowset(sql.toString(),context,"selected-code-listed");
			}
			catch( TxnException e ){
				log.error( "取数据库表时错误", e );
				return null;
			}

			//System.out.println("根据主表取主表主键sql》》》》:"+sql.toString());
			return getParamList(context.getRecordset("selected-code-listed"), "column_name", "column_code");

		}
		
		/**
		 * 根据物理表取外键
		 * @param context
		 * @return
		 * @throws TxnException
		 */
		public Recordset getForeignKeyByTableCode(TxnContext context) throws TxnException {
			BaseTable table = null;
			String sys_rd_unclaim_table_id = context.getString("input-data:sys_rd_unclaim_table_id");
			
			
			StringBuffer sql = new StringBuffer("select unclaim_column_code, unclaim_column_name from sys_rd_unclaim_column where 1=1 ");
			
			if(!"".equals(sys_rd_unclaim_table_id)){
				sql.append(" and sys_rd_unclaim_table_id = '"+sys_rd_unclaim_table_id+"'");
			}
			
			//System.out.println("根据物理表取外键::"+sql.toString());
			
			try{
				table = TableFactory.getInstance().getTableObject(this, "sys_rd_unclaim_column");
				table.executeRowset(sql.toString(),context,"selected-code-listed");
			}
			catch( TxnException e ){
				log.error( "取数据库表时错误", e );
				return null;
			}

			return getParamList(context.getRecordset("selected-code-listed"), "unclaim_column_name", "unclaim_column_code");

		}
		
		/**
		 * 已认领表-修改根据物理表取外键
		 * @param context
		 * @return
		 * @throws TxnException
		 */
		public Recordset getForeignKeyByTableCodeForClaim(TxnContext context) throws TxnException {
			BaseTable table = null;
			String sys_rd_table_id = context.getString("input-data:sys_rd_table_id");
			
			
			StringBuffer sql = new StringBuffer("select column_code, column_name from sys_rd_column where 1=1 ");
			
			if(!"".equals(sys_rd_table_id)){
				sql.append(" and sys_rd_table_id = '"+sys_rd_table_id+"'");
			}
			
			//System.out.println("根据物理表取外键::"+sql.toString());
			
			try{
				table = TableFactory.getInstance().getTableObject(this, "sys_rd_column");
				table.executeRowset(sql.toString(),context,"selected-code-listed");
			}
			catch( TxnException e ){
				log.error( "取数据库表时错误", e );
				return null;
			}

			return getParamList(context.getRecordset("selected-code-listed"), "column_name", "column_code");

		}
		
		/**
		 * 已认领表-根据已认领表主键取表所有字段
		 * @param context
		 * @return
		 * @throws TxnException
		 */
		public Recordset getColumnsByTableId(TxnContext context) throws TxnException {
			BaseTable table = null;
			String sys_rd_table_id = context.getString("input-data:sys_rd_table_id");
			
			
			StringBuffer sql = new StringBuffer("select column_code as table_fk, column_name as table_fk_name from sys_rd_column where 1=1 ");
			
			if(!"".equals(sys_rd_table_id)){
				sql.append(" and sys_rd_table_id = '"+sys_rd_table_id+"'");
			}
			
			//System.out.println("根据物理表取外键::"+sql.toString());
			
			try{
				table = TableFactory.getInstance().getTableObject(this, "sys_rd_column");
				table.executeRowset(sql.toString(),context,"selected-code-listed");
			}
			catch( TxnException e ){
				log.error( "取数据库表时错误", e );
				return null;
			}

			return getParamList(context.getRecordset("selected-code-listed"), "table_fk_name", "table_fk");

		}
		/**
		 * 已认领表-根据数据源ID取表ID和代码
		 * @param context
		 * @return
		 * @throws TxnException
		 */
		public Recordset getTablesByDataSource(TxnContext context) throws TxnException {
			BaseTable table = null;
			String sys_rd_data_source_id = context.getString("input-data:sys_rd_data_source_id");
			String sys_rd_table_id = context.getString("input-data:sys_rd_table_id");

			StringBuffer sql = new StringBuffer("select table_code as relation_table_code , table_name as relation_table_name from sys_rd_table   where 1=1 ");
			
			if(!"".equals(sys_rd_data_source_id)){
				sql.append(" and sys_rd_data_source_id = '"+sys_rd_data_source_id+"'");
			}
			if(!"".equals(sys_rd_table_id)){
				sql.append(" and sys_rd_table_id <> '"+sys_rd_table_id+"'");
			}
			try{
				table = TableFactory.getInstance().getTableObject(this, "sys_rd_table");
				table.executeRowset(sql.toString(),context,"selected-code-listed");
				System.out.println("getTablesByDataSource \n"+context);
			}
			catch( TxnException e ){
				log.error( "取数据库表时错误", e );
				return null;
			}

			return getParamList(context.getRecordset("selected-code-listed"), "relation_table_name", "relation_table_code");

		}
		
		/**
		 * 获取系统代码集
		 * @param context
		 * @return
		 * @throws TxnException
		 */
		public Recordset getSystemCode(TxnContext context) throws TxnException {
			BaseTable table = null;
			
			StringBuffer sql = new StringBuffer("select jc_dm_dm,jc_dm_mc from gz_dm_jcdm");
			
			
			
			try{
				table = TableFactory.getInstance().getTableObject(this, "gz_dm_jcdm");
				table.executeRowset(sql.toString(),context,"selected-code-listed");
			}
			catch( TxnException e ){
				log.error( "取数据库表时错误", e );
				return null;
			}

			return getParamList(context.getRecordset("selected-code-listed"), "jc_dm_mc", "jc_dm_dm");

		}
		
		/**
		 * 获取系统代码集
		 * @param context
		 * @return
		 * @throws TxnException
		 */
		public Recordset getShareSvrState(TxnContext context) throws TxnException {
			BaseTable table = null;
			
			StringBuffer sql = new StringBuffer("select s.key as jc_dm_dm, s.title as jc_dm_mc from ( select cd.codename as title, " +
					"cd.codevalue as key, NVL(t.mt, 0) as amount from codedata cd," +
					" (select svr.service_state, count(svr.service_id) as mt from " +
					"share_service svr where svr.is_markup = 'Y' group by svr.service_state)" +
					" t where cd.codetype = '资源管理_归档服务状态' and cd.codevalue = " +
					"t.service_state(+) order by amount desc ) s where s.amount != 0 ");
			
			try{
				table = TableFactory.getInstance().getTableObject(this, "gz_dm_jcdm");
				table.executeRowset(sql.toString(),context,"selected-code-listed");
			}
			catch( TxnException e ){
				log.error( "取数据库表时错误", e );
				return null;
			}

			return getParamList(context.getRecordset("selected-code-listed"), "jc_dm_mc", "jc_dm_dm");

		}
		
		/**
		 * 获取基础代码集
		 * @param context
		 * @return
		 * @throws TxnException
		 */
		public Recordset getCodeIndex(TxnContext context) throws TxnException {
			BaseTable table = null;
			
			StringBuffer sql = new StringBuffer("select sys_rd_standar_codeindex,codeindex_name from sys_rd_standard_codeindex");
			
			
			
			try{
				table = TableFactory.getInstance().getTableObject(this, "sys_rd_standard_codeindex");
				table.executeRowset(sql.toString(),context,"selected-code-listed");
			}
			catch( TxnException e ){
				log.error( "取数据库表时错误", e );
				return null;
			}

			return getParamList(context.getRecordset("selected-code-listed"), "codeindex_name", "sys_rd_standar_codeindex");

		}
		
		/**
		 * 根据物理表取业务主题
		 * @param context
		 * @return
		 * @throws TxnException
		 */
		public Recordset getSysNameByTableCode(TxnContext context) throws TxnException {
			BaseTable table = null;
			String table_code = context.getString("input-data:table_code");
			StringBuffer cond= new StringBuffer("");
			if(table_code!=null && !"".equals(table_code)){

				if(table_code.startsWith("AD_")){
					cond.append("广告监管主题") ;
				}else if(table_code.startsWith("AT_")){
					cond.append("12315主题");
				}else if(table_code.startsWith("CASE_")){
					cond.append("案件管理主题");
				}else if(table_code.startsWith("CREDI_")){
					cond.append("信用二期");
				}else if(table_code.startsWith("ENTER_")){
					cond.append("国家局主体%' or sys_name like'%总局登记主题");
				}else if(table_code.startsWith("DQ_")){
					cond.append("灵活报表主题");
				}else if(table_code.startsWith("ETL_")){
					cond.append("ETL转换过程主题");
				}else if(table_code.startsWith("EXC_QUE_")){
					cond.append("法人库主题") ;
				}else if(table_code.startsWith("EXC_TAX_")){
					cond.append("地税登记信息主题");
				}else if(table_code.startsWith("FIRST_PAGE_")){
					cond.append("首页展示主题");
				}else if(table_code.startsWith("FL_")){
					cond.append("食品许可证主题");
				}else if(table_code.startsWith("FOOD_")){
					cond.append("食品监管主题");
				}else if(table_code.startsWith("HUR_")){
					cond.append("住建委");
				}else if(table_code.startsWith("JS_")){
					cond.append("企业限制锁定(警示)主题");
				}else if(table_code.startsWith("MAP_")){
					cond.append("网格地理信息");
				}else if(table_code.startsWith("MDS_")){
					cond.append("商品监管主题");
				}else if(table_code.startsWith("MON_YC_")){
					cond.append("年检验照");
				}else if(table_code.startsWith("MON_")){
					cond.append("网格管理");
				}else if(table_code.startsWith("NM_")){
					cond.append("名称登记");
				}else if(table_code.startsWith("REG_BUS_")){
					cond.append("企业登记");
				}else if(table_code.startsWith("REG_INDIV_")){
					cond.append("个体登记");
				}else if(table_code.startsWith("DL_")){
					cond.append("比对下载上传下载的临时表");
				}else{
					
				};
			}
			
			StringBuffer sql = new StringBuffer("select sys_rd_system_id, sys_name from sys_rd_system ");
			
			if(!"".equals(cond.toString())){
				sql.append(" where sys_name like '%"+cond+"%'");
			}
	
			try{
				table = TableFactory.getInstance().getTableObject(this, "sys_rd_table");
				table.executeRowset(sql.toString(),context,"selected-code-listed");
			}
			catch( TxnException e ){
				log.error( "取数据库表时错误", e );
				return null;
			}
			
			return getParamList(context.getRecordset("selected-code-listed"), "sys_name", "sys_rd_system_id");

		}
}
