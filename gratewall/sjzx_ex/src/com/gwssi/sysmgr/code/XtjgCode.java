package com.gwssi.sysmgr.code;

import org.apache.commons.lang.StringUtils;

import com.gwssi.common.constant.CollectConstants;
import com.gwssi.common.constant.ExConstant;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.param.ParamHelp;

public class XtjgCode extends ParamHelp
{
	private static final String TABLE_NAME="xt_zzjg_jg";
	
	public Recordset getFjJgid (TxnContext data) throws TxnException{
		
		BaseTable table = TableFactory.getInstance().getTableObject(this, TABLE_NAME);
		String orgId = "";
		String sql = "select jgid_pk , jgmc from xt_zzjg_jg where sjjgid_fk is null and sjjgname is null ";
		try{
//			ȡ���ݹ����Ĳ�����Ĭ�Ϸ���input-data�ϣ������ж������
			orgId = data.getString("input-data:orgId");
		}
		catch(Exception e){
			log.error( e );
		}
		if(orgId!=null&&!orgId.equals("")){
			if(orgId.length()>=8){
				if(!(orgId.substring(0,8)).equals("00100100")){
				    sql+= " and ((jgid_pk = ( select sjjgid_fk from xt_zzjg_jg where jgid_pk='"+orgId+"')) or (( select sjjgid_fk from xt_zzjg_jg where jgid_pk='"+orgId+"') is null))";
				}
			}					
		}	
		sql+= " order by jgid_pk desc";
		//System.out.println("���־�ͳ�ƣ�"+sql);
        table.executeRowset(sql,data,"selected-view-listed");
		Recordset rs = getParamList(data.getRecordset("selected-view-listed"), "jgmc", "jgid_pk");	
		return rs;
		
		
	}
	public Recordset getKsJgid (TxnContext data) throws TxnException{
		
		BaseTable table = TableFactory.getInstance().getTableObject(this, TABLE_NAME);
		String orgId = "";
		String sql = "select jgid_pk , jgmc from xt_zzjg_jg where sfyx='0' ";
		try{
//			ȡ���ݹ����Ĳ�����Ĭ�Ϸ���input-data�ϣ������ж������
			orgId = data.getString("input-data:orgId");
		}
		catch(Exception e){
			log.error( e );
		}
		if(orgId!=null&&!orgId.equals("")){
			sql+= " and sjjgid_fk = '"+orgId+"'";			
		}	
		sql+= " order by jgid_pk desc";
        table.executeRowset(sql,data,"selected-view-listed");
		Recordset rs = getParamList(data.getRecordset("selected-view-listed"), "jgmc", "jgid_pk");	
		return rs;
		
		
	}	
	/**
	 * ��ȡ���ܴ���
	 * @param context
	 * @return
	 * @throws TxnException
	 */
	public Recordset getFunName(TxnContext context) throws TxnException {
		BaseTable table = null;
		
		//StringBuffer sql = new StringBuffer("select  SUBSTR(func_name, 1, 4) as codevalue, SUBSTR(func_name, 1, 4) as codename from view_sys_func_count");
		//
		StringBuffer sql = new StringBuffer("select  SUBSTR(func_name, 1, 4) as codevalue, SUBSTR(func_name, 1, 4) as codename from sys_func_count group by SUBSTR(func_name, 1, 4)");
		//System.out.println("��ȡ���ܴ��ࣺ"+sql.toString());
		
		try{
			table = TableFactory.getInstance().getTableObject(this, "view_sys_func_count");
			table.executeRowset(sql.toString(),context,"selected-code-listed");
		}
		catch( TxnException e ){
			log.error( "ȡ���ݿ��ʱ����", e );
			return null;
		}
		
		return getParamList(context.getRecordset("selected-code-listed"), "codename", "codevalue");

	}
	
	/**
	 * ��ȡ����С��
	 * @param context
	 * @return
	 * @throws TxnException
	 */
	public Recordset getSecondFunName(TxnContext context) throws TxnException {
		BaseTable table = null;
		
		StringBuffer sql = new StringBuffer("SELECT second_func_name as codevalue,second_func_name as codename from view_func_name_log");
		String first_func_name = null;
		
		
		try{
			first_func_name = context.getString("input-data:first_func_name");
			
			if(first_func_name!=null&&!"".equals(first_func_name)){
				sql.append(" where first_func_name='"+first_func_name+"'");
			}
		//System.out.println("��ȡ����С�ࣺ"+sql.toString());	
			table = TableFactory.getInstance().getTableObject(this, "view_func_name_log");
			table.executeRowset(sql.toString(),context,"selected-code-listed");
		}
		catch( TxnException e ){
			log.error( "ȡ���ݿ��ʱ����", e );
			return null;
		}
		
		return getParamList(context.getRecordset("selected-code-listed"), "codename", "codevalue");

	}
	/**
	 * ��ȡ��������Դ
	 * @param context��
	 * @return
	 * @throws TxnException
	 */
	public Recordset getAllDatasourceList(TxnContext context) throws TxnException {
		BaseTable table = null;
		
		StringBuffer sql = new StringBuffer("SELECT data_source_id as codevalue,data_source_name as codename from res_data_source");
		sql.append(" where is_markup = '"+ExConstant.IS_MARKUP_Y+"'");
		//System.out.println("��ȡ����Դ��"+sql.toString());	
		
		try{
			table = TableFactory.getInstance().getTableObject(this, "res_data_source");
			table.executeRowset(sql.toString(),context,"selected-code-listed");
		}
		catch( TxnException e ){
			log.error( "ȡ���ݿ��ʱ����", e );
			return null;
		}
		
		return getParamList(context.getRecordset("selected-code-listed"), "codename", "codevalue");

	}
	/**
	 * ��ȡ��������Ӧ������Դ
	 * @param context��
	 * @return
	 * @throws TxnException
	 */
	public Recordset getDatasourceList(TxnContext context) throws TxnException {
		BaseTable table = null;
		
		StringBuffer sql = new StringBuffer("SELECT data_source_id as codevalue,data_source_name as codename from res_data_source");
		sql.append(" where is_markup = '"+ExConstant.IS_MARKUP_Y+"'");
		String service_targets_id = null;
		String collectType=null;
		
		try{
			service_targets_id = context.getString("input-data:service_targets_id");
			collectType=context.getString("input-data:collectType");
			System.out.println("collectType=="+collectType);
			if(StringUtils.isNotBlank(service_targets_id)){
				sql.append(" and service_targets_id='"+service_targets_id+"' and data_source_type = '"+collectType+"'");
			}
			sql.append("  order by data_source_name");
			System.out.println("��ȡ����Դ��"+sql.toString());	
			table = TableFactory.getInstance().getTableObject(this, "res_data_source");
			table.executeRowset(sql.toString(),context,"selected-code-listed");
		}
		catch( TxnException e ){
			log.error( "ȡ���ݿ��ʱ����", e );
			return null;
		}
		
		return getParamList(context.getRecordset("selected-code-listed"), "codename", "codevalue");

	}
	
	/**
	 * ��ȡ��������Ӧ������Դ
	 * @param context��
	 * @return
	 * @throws TxnException
	 */
	public Recordset getDatasourceList1(TxnContext context) throws TxnException {
		BaseTable table = null;
		
		StringBuffer sql = new StringBuffer("SELECT data_source_id as codevalue,data_source_name as codename from res_data_source");
		sql.append(" where is_markup = '"+ExConstant.IS_MARKUP_Y+"'");
		String service_targets_id = null;
		String collectType=null;
		
		try{
			service_targets_id = context.getString("input-data:service_targets_id");
			//collectType=context.getString("input-data:collectType");
			if(!service_targets_id.equals("0000")){
				sql.append(" and service_targets_id='"+service_targets_id+"'");
			}
			
			/*if(StringUtils.isNotBlank(service_targets_id)){
				sql.append(" and service_targets_id='"+service_targets_id+"'");
			}*/
			System.out.println("����Դ���뼯sql::"+sql.toString());
			table = TableFactory.getInstance().getTableObject(this, "res_data_source");
			table.executeRowset(sql.toString(),context,"selected-code-listed");
		}
		catch( TxnException e ){
			log.error( "ȡ���ݿ��ʱ����", e );
			return null;
		}
		
		return getParamList(context.getRecordset("selected-code-listed"), "codename", "codevalue");

	}
	
	/**
	 * ��ȡ��������Ӧ�Ĳɼ���
	 * @param context��
	 * @return
	 * @throws TxnException
	 */
	public Recordset getCjbList(TxnContext context) throws TxnException {
		BaseTable table = null;
		
		StringBuffer sql = new StringBuffer("SELECT collect_table_id as codevalue,table_name_cn as codename from res_collect_table");
		String service_targets_id = null;
		
		
		
		try{
			sql.append(" where if_creat = '"+CollectConstants.TYPE_IF_CREAT_YES+"'");
			sql.append("  and is_markup = '"+ExConstant.IS_MARKUP_Y+"' ");
			sql.append(" and table_name_cn is not null ");
			service_targets_id = context.getString("input-data:service_targets_id");
			
			if(service_targets_id!=null&&!"".equals(service_targets_id)){
				sql.append(" and service_targets_id='"+service_targets_id+"'");
				
			}
		System.out.println("��ȡ�ɼ���"+sql.toString());	
			table = TableFactory.getInstance().getTableObject(this, "res_collect_table");
			table.executeRowset(sql.toString(),context,"selected-code-listed");
		}
		catch( TxnException e ){
			log.error( "ȡ���ݿ��ʱ����", e );
			return null;
		}
		
		return getParamList(context.getRecordset("selected-code-listed"), "codename", "codevalue");

	}
	
	/**
	 * ��ȡ���вɼ���
	 * @param context��
	 * @return
	 * @throws TxnException
	 */
	public Recordset getAllCjbList(TxnContext context) throws TxnException {
		BaseTable table = null;
		
		StringBuffer sql = new StringBuffer("SELECT collect_table_id as codevalue,table_name_cn as codename from res_collect_table");
		sql.append(" where is_markup = '"+ExConstant.IS_MARKUP_Y+"'");
		
		try{
			table = TableFactory.getInstance().getTableObject(this, "res_collect_table");
			table.executeRowset(sql.toString(),context,"selected-code-listed");
		}
		catch( TxnException e ){
			log.error( "ȡ���ݿ��ʱ����", e );
			return null;
		}
		return getParamList(context.getRecordset("selected-code-listed"), "codename", "codevalue");
	}
	
	/**
	 * ��ȡ��ض����б�
	 * @param context��
	 * @return
	 * @throws TxnException
	 */
	public Recordset getAllMonitorObjList(TxnContext context2) throws TxnException {
		BaseTable table = null;
		TxnContext context = new TxnContext();
		StringBuffer sql = new StringBuffer("select object_id as codevalue,object_name as codename  from monitor_object ");
		sql.append(" where is_markup = '"+ExConstant.IS_MARKUP_Y+"'");
		
		try{
			table = TableFactory.getInstance().getTableObject(this, "res_collect_table");
			table.executeRowset(sql.toString(),context,"selected-code-listed");
		}
		catch( TxnException e ){
			log.error( "ȡ���ݿ��ʱ����", e );
			return null;
		}
		return getParamList(context.getRecordset("selected-code-listed"), "codename", "codevalue");
	}
	
	/**
	 * ��ȡ��ض����б�
	 * @param context��
	 * @return
	 * @throws TxnException
	 */
	public Recordset getAllMonitorPropList(TxnContext context) throws TxnException {
		BaseTable table = null;
		//System.out.println("context========"+context);
		TxnContext context2 = new TxnContext();
		//context2.remove("selected-code-list");
		//System.out.println("context2========"+context2);
		StringBuffer sql = new StringBuffer("select monitor_index_id as codevalue,index_name as codename  from monitor_index ");
		sql.append(" where is_markup = '"+ExConstant.IS_MARKUP_Y+"'");
		
		try{
			table = TableFactory.getInstance().getTableObject(this, "res_collect_table");
			table.executeRowset(sql.toString(),context2,"selected-code-listed");
		}
		catch( TxnException e ){
			log.error( "ȡ���ݿ��ʱ����", e );
			return null;
		}
		//System.out.println("���м��_���ָ��======"+getParamList(context2.getRecordset("selected-code-listed"), "codename", "codevalue"));
		return getParamList(context2.getRecordset("selected-code-listed"), "codename", "codevalue");
	}
	
	/**
	 * ��ȡFTP�ļ�����������
	 * @param context��
	 * @return
	 * @throws TxnException
	 */
	public Recordset getTitleType(TxnContext context) throws TxnException {
		BaseTable table = null;
		Recordset rs=new Recordset();
		DataBus db1=new DataBus();
		db1.put("codevalue", "EN");
		db1.put("codename", "Ӣ������");
		rs.add(db1);
		DataBus db2=new DataBus();
		db2.put("codevalue", "CN");
		db2.put("codename", "��������");
		rs.add(db2);
		//System.out.println("���м��_���ָ��======"+getParamList(context2.getRecordset("selected-code-listed"), "codename", "codevalue"));
		return getParamList(rs, "codename", "codevalue");
	}
	/**
	 * ��ȡ�·�
	 * @param context��
	 * @return
	 * @throws TxnException
	 */
	public Recordset getMonth(TxnContext context) throws TxnException {
		BaseTable table = null;
		Recordset rs=new Recordset();
		DataBus db1=new DataBus();
		db1.put("codevalue", "pre");
		db1.put("codename", "�ϸ���");
		rs.add(db1);
		DataBus db2=new DataBus();
		db2.put("codevalue", "cur");
		db2.put("codename", "����");
		rs.add(db2);
		for(int i=1;i<=12;i++){
			DataBus tmpdb= new DataBus();
			tmpdb.put("codevalue", i);
			tmpdb.put("codename", i+"��");
			rs.add(tmpdb);
		}
		return getParamList(rs, "codename", "codevalue");
	}
	/**
	 * ��ȡ�պ�
	 * @param context��
	 * @return
	 * @throws TxnException
	 */
	public Recordset getDayOfMonth(TxnContext context) throws TxnException {
		BaseTable table = null;
		Recordset rs=new Recordset();
		DataBus db1=new DataBus();
		db1.put("codevalue", "last");
		db1.put("codename", "���һ��");
		rs.add(db1);
		for(int i=1;i<=31;i++){
			DataBus tmpdb= new DataBus();
			tmpdb.put("codevalue", i);
			tmpdb.put("codename", i+"��");
			rs.add(tmpdb);
		}
		//System.out.println("���м��_���ָ��======"+getParamList(context2.getRecordset("selected-code-listed"), "codename", "codevalue"));
		return getParamList(rs, "codename", "codevalue");
	}
	/**
	 * �ļ������ں�׺���ɷ�ʽ
	 * @param context��
	 * @return
	 * @throws TxnException
	 */
	public Recordset getNameType(TxnContext context) throws TxnException {
		BaseTable table = null;
		Recordset rs=new Recordset();
		DataBus db1=new DataBus();
		db1.put("codevalue", "M");
		db1.put("codename", "����");
		rs.add(db1);
		DataBus db2=new DataBus();
		db2.put("codevalue", "D");
		db2.put("codename", "����");
		rs.add(db2);
		return getParamList(rs, "codename", "codevalue");
	}
}
