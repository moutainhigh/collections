package com.gwssi.dw.runmgr.db.txn;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;

import com.gwssi.common.util.GenerateValue;
import com.gwssi.common.util.UuidGenerator;
import com.gwssi.dw.runmgr.db.TxnBaseDbMgr;
import com.gwssi.dw.runmgr.services.common.Constants;

public class TxnSysDbUser extends TxnBaseDbMgr
{
	
	// ���ݱ�����
	private static final String TABLE_NAME = "sys_db_user";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select sys_db_user list";
	
	// ��ѯ�б�
	private static final String EXIST_USER_ROWSET_FUNCTION = "select exist sys_db_user list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one sys_db_user";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one sys_db_user";

	// �޸�״̬
	private static final String UPDATE_STATE_FUNCTION = "update one sys_db_user state";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one sys_db_user";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one sys_db_user";
	
	// ��ѯ��¼
	private static final String SELECT_USERNAME_FUNCTION = "select one sys_db_user_name";
	
	// ��ѯ��¼
	private static final String SELECT_PASSWORD_FUNCTION = "select one sys_db_user_password";
	
	// ��ѯ��¼
	private static final String SELECT_ORDER_FUNCTION = "select user_order sys_db_user";
	
	//��ѯ��¼
	private static final String RESET_PWD_FUNCTION = "reset sys_db_user password";
	
	
	/**
	 * ���캯��
	 */
	public TxnSysDbUser()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ��������б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn52101001( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
//		if(context.getRecord(inputNode).getValue("showall") != null){
//			context.getRecord(inputNode).setValue("state", "0");
//			Attribute.setPageRow(context, outputNode, -1);
//		}
		// ��ѯ��¼������ VoSysSvrUserSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		
		Recordset rs = context.getRecordset(outputNode);
		
		for(int i=0;rs!=null&&i<rs.size();i++){
			DataBus temp = rs.get(i);
			String sys_db_user_id = temp.getValue("sys_db_user_id");			
			if (hasConfigView(table,sys_db_user_id,null)) {
				temp.setValue("hasConfig", "1");
			}else{
				temp.setValue("hasConfig", "0");
			}
		}
		// ��ѯ���ļ�¼�� VoSysSvrUser result[] = context.getSysSvrUsers( outputNode );
	}

	public void txn52101011( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		context.getRecord(inputNode).setValue("state", "0");
		Attribute.setPageRow(context, outputNode, -1);
		
		// ��ѯ��¼������ VoSysSvrUserSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoSysSvrUser result[] = context.getSysSvrUsers( outputNode );
	}	
	

	/** �޸ķ��������Ϣ
	 * ע���������⣬����DDL����Զ�commit����˽��޸ķֳ���������
	 * �޸����ݿ��¼update�����������а���һ��alter�û�������
	 * ��alter�ɹ���update����ż��ύ������executeDBSql�������׳��쳣�Ƶ�update�ع���
	 * �������ӷ����Ĵ���Ҳ��һ���ġ�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn52101002( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );

		
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
		String state = context.getRecord(inputNode).getValue("state");
		String user_type = context.getRecord(inputNode).getValue("user_type");
		String login_name = context.getRecord(inputNode).getValue("login_name");
        List sqls = new ArrayList();
		if("0".equals(state)){
			sqls.add("alter user "+login_name+" account unlock ");
			executeDBSql(user_type, sqls, "����");
		}else if("1".equals(state)){
			sqls.add("alter user "+login_name+" account lock ");
			executeDBSql(user_type, sqls, "ͣ��");
		}
		context.getRecord(com.gwssi.common.util.Constants.BIZLOG_NAME).setValue(com.gwssi.common.util.Constants.VALUE_NAME, "�޸����ݿ⹲������û�����"+login_name);
	}
	
	/** ���ӷ��������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn52101003( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		String login_name = context.getRecord(inputNode).getValue("login_name");
		String user_type = context.getRecord(inputNode).getValue("user_type");

		TxnContext cont = new TxnContext();
		DataBus db = new DataBus();
		db.setValue("login_name", login_name);
		cont.addRecord("select-key", db);
		callService("52101009", cont);
		if(cont.getRecordset("record").hasNext()){
			throw new TxnDataException("999999","���û����Ѵ��ڣ�");
		}
		String pwd = UuidGenerator.getUUID().substring(0, 8);
	
		table.executeSelect("SELECT MAX(user_order) as user_order FROM sys_db_user", cont, "max-order");
		String v = cont.getRecord("max-order").getValue("user_order");
		int max = Integer.parseInt(v.equalsIgnoreCase("") ? "0" : v);
		context.getRecord("record").setValue("create_date", new SimpleDateFormat(Constants.DB_DATE_FORMAT).format(new Date()));
		String uId = context.getOperData().getValue("oper-name");
		context.getRecord("record").setValue("create_by", uId);
		context.getRecord("record").setValue("state", "0");
		context.getRecord("record").setValue("password", pwd);
		context.getRecord("record").setValue("user_order", ""+(max + 1));

		// ���Ӽ�¼������ VoSysSvrUser sys_svr_user = context.getSysSvrUser( inputNode );
		
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
		
		List sqls = new ArrayList();
		sqls.add("create user "+login_name+" identified by \""+pwd+"\"");
		sqls.add("grant connect to "+login_name);
		executeDBSql(user_type, sqls,"����");
		context.getRecord(com.gwssi.common.util.Constants.BIZLOG_NAME).setValue(com.gwssi.common.util.Constants.VALUE_NAME, "�������ݿ⹲������û�����"+login_name);
	}

	/** ��ѯ������������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn52101004( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysSvrUserPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoSysSvrUser result = context.getSysSvrUser( outputNode );
	}
	
	/** ɾ�����������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn52101005( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
//		int j = table.executeFunction( "checkConfig", context, inputNode, outputNode );
//		if (j > 0) {
//			if (context.getRecord(outputNode).getValue("sys_db_config_id") != null
//					&& !"".equals(context.getRecord(outputNode).getValue(
//							"sys_db_config_id")))
//				throw new TxnDataException("999999", "���û��Ѿ�����������ͼ������ɾ����");
//		}
		
		Recordset rs = context.getRecordset("record");
		List nbsqls = new ArrayList();
		List wbsqls = new ArrayList();
		StringBuffer wbNames = new StringBuffer();
		StringBuffer nbNames = new StringBuffer();
		StringBuffer logStr = new StringBuffer();
		for(int i=0;rs!=null&&i<rs.size();i++){
			String login_name = rs.get(i).getValue("login_name");
			String user_type = rs.get(i).getValue("user_type");
			if("0".equals(user_type)){
			    nbsqls.add("drop user "+login_name +" cascade");
			    if(nbNames.length()>0){
			    	nbNames.append(",");
			    }
			    nbNames.append("'").append(login_name.toUpperCase()).append("'");
			}
			if("1".equals(user_type)){
				wbsqls.add("drop user "+login_name +" cascade");
			    if(wbNames.length()>0){
			    	wbNames.append(",");
			    }
			    wbNames.append("'").append(login_name.toUpperCase()).append("'");
			}
			if(logStr.length()>0){
				logStr.append(",");
			}
			logStr.append(login_name);
		}		
		
		if (nbNames.length() > 0) {
			Map map = executeDBSql("0",
					"select username from v$session where username in ("
							+ nbNames + ")", "��ѯ��Ҫɾ�����ڲ��û��Ƿ��������ݿ�ʧ��,������������ԭ��!");
			if (map != null && (String)map.get("USERNAME") !=null&&!"".equals((String)map.get("USERNAME"))) {
				throw new TxnErrorException("99999", "��Ҫɾ�����ڲ��û��Ѿ����ӵ����ݿ⣡");
			}
		}
		if (wbNames.length() > 0) {
			Map map = executeDBSql("1",
					"select username from v$session where username in ("
							+ wbNames + ")", "��ѯ��Ҫɾ�����ⲿ�û��Ƿ��������ݿ�ʧ��,������������ԭ��!");
			if (map != null && (String)map.get("USERNAME") !=null&&!"".equals((String)map.get("USERNAME"))) {
				throw new TxnErrorException("99999", "��Ҫɾ�����ⲿ�û��Ѿ����ӵ����ݿ⣡");
			}
		}	
		
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
		
		if(nbsqls.size()>0){
			executeDBSql("0", nbsqls ,"ɾ��");
		}
		if(wbsqls.size()>0){
			executeDBSql("1", wbsqls ,"ɾ��");
		}
		context.remove(outputNode);
		context.getRecord(com.gwssi.common.util.Constants.BIZLOG_NAME).setValue(com.gwssi.common.util.Constants.VALUE_NAME, "ɾ�����ݿ⹲������û�����"+logStr);
	}
	

	/** ���÷������
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn52101006(TxnContext context ) throws TxnException{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Recordset rs = context.getRecordset("record");
		List nbsqls = new ArrayList();
		List wbsqls = new ArrayList();
		StringBuffer wbNames = new StringBuffer();
		StringBuffer nbNames = new StringBuffer();
		StringBuffer logStr = new StringBuffer();
		for(int i=0;i<rs.size();i++){
			rs.get(i).setValue("state","0");
			String login_name = rs.get(i).getValue("login_name");
			String user_type = rs.get(i).getValue("user_type");
			if("0".equals(user_type)){
			    nbsqls.add("alter user "+login_name+" account unlock ");
			    if(nbNames.length()>0){
			    	nbNames.append(",");
			    }
			    nbNames.append("'").append(login_name.toUpperCase()).append("'");
			}
			if("1".equals(user_type)){
				wbsqls.add("alter user "+login_name+" account unlock ");
			    if(wbNames.length()>0){
			    	wbNames.append(",");
			    }
			    wbNames.append("'").append(login_name.toUpperCase()).append("'");
			}
			if(logStr.length()>0){
				logStr.append(",");
			}
			logStr.append(login_name);
		}
		
		if (nbNames.length() > 0) {
			Map map = executeDBSql("0",
					"select username from v$session where username in ("
							+ nbNames + ")", "��ѯ��Ҫ���õ��ڲ��û��Ƿ��������ݿ�ʧ��,������������ԭ��!");
			if (map != null && (String)map.get("USERNAME") !=null&&!"".equals((String)map.get("USERNAME"))) {
				throw new TxnErrorException("99999", "��Ҫ���õ��ڲ��û��Ѿ����ӵ����ݿ⣡");
			}
		}
		if (wbNames.length() > 0) {
			Map map = executeDBSql("1",
					"select username from v$session where username in ("
							+ wbNames + ")", "��ѯ��Ҫ���õ��ⲿ�û��Ƿ��������ݿ�ʧ��,������������ԭ��!");
			if (map != null && (String)map.get("USERNAME") !=null&&!"".equals((String)map.get("USERNAME"))) {
				throw new TxnErrorException("99999", "��Ҫ���õ��ⲿ�û��Ѿ����ӵ����ݿ⣡");
			}
		}			
		
		table.executeFunction( UPDATE_STATE_FUNCTION, context, inputNode, outputNode );
		context.remove(outputNode);
		
		if(nbsqls.size()>0){
			executeDBSql("0", nbsqls, "����");
		}
		if(wbsqls.size()>0){
			executeDBSql("1", wbsqls, "����");
		}
		context.getRecord(com.gwssi.common.util.Constants.BIZLOG_NAME).setValue(com.gwssi.common.util.Constants.VALUE_NAME, "�������ݿ⹲������û�����"+logStr);
		
	}
	
	/** ͣ�÷������
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn52101007(TxnContext context ) throws TxnException{

		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Recordset rs = context.getRecordset("record");
		List nbsqls = new ArrayList();
		List wbsqls = new ArrayList();
		StringBuffer wbNames = new StringBuffer();
		StringBuffer nbNames = new StringBuffer();
		StringBuffer logStr = new StringBuffer();
		for(int i=0;i<rs.size();i++){
			rs.get(i).setValue("state","1");
			String login_name = rs.get(i).getValue("login_name");
			String user_type = rs.get(i).getValue("user_type");
			if("0".equals(user_type)){
			    nbsqls.add("alter user "+login_name+" account lock ");
			    if(nbNames.length()>0){
			    	nbNames.append(",");
			    }
			    nbNames.append("'").append(login_name.toUpperCase()).append("'");
			}
			if("1".equals(user_type)){
				wbsqls.add("alter user "+login_name+" account lock ");
			    if(wbNames.length()>0){
			    	wbNames.append(",");
			    }
			    wbNames.append("'").append(login_name.toUpperCase()).append("'");
			}
			if(logStr.length()>0){
				logStr.append(",");
			}
			logStr.append(login_name);
		}		

		if (nbNames.length() > 0) {
			Map map = executeDBSql("0",
					"select username from v$session where username in ("
							+ nbNames + ")", "��ѯ��Ҫͣ�õ��ڲ��û��Ƿ��������ݿ�ʧ��,������������ԭ��!");
			if (map != null && (String)map.get("USERNAME") !=null&&!"".equals((String)map.get("USERNAME"))) {
				throw new TxnErrorException("99999", "��Ҫͣ�õ��ڲ��û��Ѿ����ӵ����ݿ⣡");
			}
		}
		if (wbNames.length() > 0) {
			Map map = executeDBSql("1",
					"select username from v$session where username in ("
							+ wbNames + ")", "��ѯ��Ҫͣ�õ��ⲿ�û��Ƿ��������ݿ�ʧ��,������������ԭ��!");
			if (map != null && (String)map.get("USERNAME") !=null&&!"".equals((String)map.get("USERNAME"))) {
				throw new TxnErrorException("99999", "��Ҫͣ�õ��ⲿ�û��Ѿ����ӵ����ݿ⣡");
			}
		}			
		
		table.executeFunction( UPDATE_STATE_FUNCTION, context, inputNode, outputNode );
		context.remove(outputNode);
		
		if(nbsqls.size()>0){
			executeDBSql("0", nbsqls, "ͣ��");
		}
		if(wbsqls.size()>0){
			executeDBSql("1", wbsqls, "ͣ��");
		}
		context.getRecord(com.gwssi.common.util.Constants.BIZLOG_NAME).setValue(com.gwssi.common.util.Constants.VALUE_NAME, "ͣ�����ݿ⹲������û�����"+logStr);
	}
	
	/** ��ԃ����������
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn52101008(TxnContext context ) throws TxnException{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( SELECT_ORDER_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ԃ�������
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn52101009(TxnContext context ) throws TxnException{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( EXIST_USER_ROWSET_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ʼ�������������
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn52101010(TxnContext context ) throws TxnException{

		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Recordset rs = context.getRecordset("record");
		List nbsqls = new ArrayList();
		List wbsqls = new ArrayList();
		StringBuffer wbNames = new StringBuffer();
		StringBuffer nbNames = new StringBuffer();
		StringBuffer logStr = new StringBuffer();
		for(int i=0;i<rs.size();i++){
			String pwd = UuidGenerator.getUUID().substring(0, 8);
			rs.get(i).setValue("password",pwd);
			String login_name = rs.get(i).getValue("login_name");
			String user_type = rs.get(i).getValue("user_type");
			if("0".equals(user_type)){
			    nbsqls.add("alter user "+login_name+" identified by \""+pwd+"\"");
			    if(nbNames.length()>0){
			    	nbNames.append(",");
			    }
			    nbNames.append("'").append(login_name.toUpperCase()).append("'");
			}
			if("1".equals(user_type)){
				wbsqls.add("alter user "+login_name+" identified by \""+pwd+"\"");
			    if(wbNames.length()>0){
			    	wbNames.append(",");
			    }
			    wbNames.append("'").append(login_name.toUpperCase()).append("'");
			}
			if(logStr.length()>0){
				logStr.append(",");
			}
			logStr.append(login_name);
		}
		
		if (nbNames.length() > 0) {
			Map map = executeDBSql("0",
					"select username from v$session where username in ("
							+ nbNames + ")", "��ѯ��Ҫ��ʼ��������ڲ��û��Ƿ��������ݿ�ʧ��,������������ԭ��!");
			if (map != null && (String)map.get("USERNAME") !=null&&!"".equals((String)map.get("USERNAME"))) {
				throw new TxnErrorException("99999", "��Ҫ��ʼ��������ڲ��û��Ѿ����ӵ����ݿ⣡");
			}
		}
		if (wbNames.length() > 0) {
			Map map = executeDBSql("1",
					"select username from v$session where username in ("
							+ wbNames + ")", "��ѯ��Ҫ��ʼ��������ⲿ�û��Ƿ��������ݿ�ʧ��,������������ԭ��!");
			if (map != null && (String)map.get("USERNAME") !=null&&!"".equals((String)map.get("USERNAME"))) {
				throw new TxnErrorException("99999", "��Ҫ��ʼ��������ⲿ�û��Ѿ����ӵ����ݿ⣡");
			}
		}
		
		table.executeFunction( RESET_PWD_FUNCTION, context, inputNode, outputNode );
				
		if(nbsqls.size()>0){
			executeDBSql("0", nbsqls,"�޸�����");
		}
		if(wbsqls.size()>0){
			executeDBSql("1", wbsqls,"�޸�����");
		}	
		context.getRecord(com.gwssi.common.util.Constants.BIZLOG_NAME).setValue(com.gwssi.common.util.Constants.VALUE_NAME, "��ʼ�����ݿ⹲��������룬�û�����"+logStr);
	}
		
}
