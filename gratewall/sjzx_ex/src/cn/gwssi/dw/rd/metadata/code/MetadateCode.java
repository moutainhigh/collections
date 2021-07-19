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
		 * ��������Դȡ�����
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
				log.error( "ȡ���ݿ��ʱ����", e );
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
				log.error( "ȡ���ݿ��ʱ����", e );
				return null;
			}
			return getParamList(context.getRecordset("selected-code-listed"), "sys_name", "sys_rd_system_id");
		}
		
		/**
		 * ��������ȡ��������
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
			//System.out.println("��������ȡ��������::"+sql.toString());
			
			try{
				table = TableFactory.getInstance().getTableObject(this, "sys_rd_column");
				table.executeRowset(sql.toString(),context,"selected-code-listed");
			}
			catch( TxnException e ){
				log.error( "ȡ���ݿ��ʱ����", e );
				return null;
			}

			//System.out.println("��������ȡ��������sql��������:"+sql.toString());
			return getParamList(context.getRecordset("selected-code-listed"), "column_name", "column_code");

		}
		
		/**
		 * ���������ȡ���
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
			
			//System.out.println("���������ȡ���::"+sql.toString());
			
			try{
				table = TableFactory.getInstance().getTableObject(this, "sys_rd_unclaim_column");
				table.executeRowset(sql.toString(),context,"selected-code-listed");
			}
			catch( TxnException e ){
				log.error( "ȡ���ݿ��ʱ����", e );
				return null;
			}

			return getParamList(context.getRecordset("selected-code-listed"), "unclaim_column_name", "unclaim_column_code");

		}
		
		/**
		 * �������-�޸ĸ��������ȡ���
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
			
			//System.out.println("���������ȡ���::"+sql.toString());
			
			try{
				table = TableFactory.getInstance().getTableObject(this, "sys_rd_column");
				table.executeRowset(sql.toString(),context,"selected-code-listed");
			}
			catch( TxnException e ){
				log.error( "ȡ���ݿ��ʱ����", e );
				return null;
			}

			return getParamList(context.getRecordset("selected-code-listed"), "column_name", "column_code");

		}
		
		/**
		 * �������-���������������ȡ�������ֶ�
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
			
			//System.out.println("���������ȡ���::"+sql.toString());
			
			try{
				table = TableFactory.getInstance().getTableObject(this, "sys_rd_column");
				table.executeRowset(sql.toString(),context,"selected-code-listed");
			}
			catch( TxnException e ){
				log.error( "ȡ���ݿ��ʱ����", e );
				return null;
			}

			return getParamList(context.getRecordset("selected-code-listed"), "table_fk_name", "table_fk");

		}
		/**
		 * �������-��������ԴIDȡ��ID�ʹ���
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
				log.error( "ȡ���ݿ��ʱ����", e );
				return null;
			}

			return getParamList(context.getRecordset("selected-code-listed"), "relation_table_name", "relation_table_code");

		}
		
		/**
		 * ��ȡϵͳ���뼯
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
				log.error( "ȡ���ݿ��ʱ����", e );
				return null;
			}

			return getParamList(context.getRecordset("selected-code-listed"), "jc_dm_mc", "jc_dm_dm");

		}
		
		/**
		 * ��ȡϵͳ���뼯
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
					" t where cd.codetype = '��Դ����_�鵵����״̬' and cd.codevalue = " +
					"t.service_state(+) order by amount desc ) s where s.amount != 0 ");
			
			try{
				table = TableFactory.getInstance().getTableObject(this, "gz_dm_jcdm");
				table.executeRowset(sql.toString(),context,"selected-code-listed");
			}
			catch( TxnException e ){
				log.error( "ȡ���ݿ��ʱ����", e );
				return null;
			}

			return getParamList(context.getRecordset("selected-code-listed"), "jc_dm_mc", "jc_dm_dm");

		}
		
		/**
		 * ��ȡ�������뼯
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
				log.error( "ȡ���ݿ��ʱ����", e );
				return null;
			}

			return getParamList(context.getRecordset("selected-code-listed"), "codeindex_name", "sys_rd_standar_codeindex");

		}
		
		/**
		 * ���������ȡҵ������
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
					cond.append("���������") ;
				}else if(table_code.startsWith("AT_")){
					cond.append("12315����");
				}else if(table_code.startsWith("CASE_")){
					cond.append("������������");
				}else if(table_code.startsWith("CREDI_")){
					cond.append("���ö���");
				}else if(table_code.startsWith("ENTER_")){
					cond.append("���Ҿ�����%' or sys_name like'%�ֵܾǼ�����");
				}else if(table_code.startsWith("DQ_")){
					cond.append("��������");
				}else if(table_code.startsWith("ETL_")){
					cond.append("ETLת����������");
				}else if(table_code.startsWith("EXC_QUE_")){
					cond.append("���˿�����") ;
				}else if(table_code.startsWith("EXC_TAX_")){
					cond.append("��˰�Ǽ���Ϣ����");
				}else if(table_code.startsWith("FIRST_PAGE_")){
					cond.append("��ҳչʾ����");
				}else if(table_code.startsWith("FL_")){
					cond.append("ʳƷ���֤����");
				}else if(table_code.startsWith("FOOD_")){
					cond.append("ʳƷ�������");
				}else if(table_code.startsWith("HUR_")){
					cond.append("ס��ί");
				}else if(table_code.startsWith("JS_")){
					cond.append("��ҵ��������(��ʾ)����");
				}else if(table_code.startsWith("MAP_")){
					cond.append("���������Ϣ");
				}else if(table_code.startsWith("MDS_")){
					cond.append("��Ʒ�������");
				}else if(table_code.startsWith("MON_YC_")){
					cond.append("�������");
				}else if(table_code.startsWith("MON_")){
					cond.append("�������");
				}else if(table_code.startsWith("NM_")){
					cond.append("���ƵǼ�");
				}else if(table_code.startsWith("REG_BUS_")){
					cond.append("��ҵ�Ǽ�");
				}else if(table_code.startsWith("REG_INDIV_")){
					cond.append("����Ǽ�");
				}else if(table_code.startsWith("DL_")){
					cond.append("�ȶ������ϴ����ص���ʱ��");
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
				log.error( "ȡ���ݿ��ʱ����", e );
				return null;
			}
			
			return getParamList(context.getRecordset("selected-code-listed"), "sys_name", "sys_rd_system_id");

		}
}
