package cn.gwssi.template.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;
import cn.gwssi.template.dao.MsWordDao;
import cn.gwssi.template.vo.SysMswordContext;

import com.gwssi.common.util.UuidGenerator;

public class TxnMsWord extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnMsWord.class, SysMswordContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "sys_msword";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select sys_msword list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one sys_msword";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one sys_msword";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one sys_msword";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one sys_msword";
	
	/**
	 * ���캯��
	 */
	public TxnMsWord()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯwordģ���б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30200101( SysMswordContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysMswordSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoSysMsword result[] = context.getSysMswords( outputNode );
	}
	
	/** �޸�wordģ����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30200102( SysMswordContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoSysMsword sys_msword = context.getSysMsword( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ����wordģ����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30200103( SysMswordContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		String sys_msword_id =  UuidGenerator.getUUID();
		DataBus db = context.getRecord(inputNode);
		String sys_msword_name = db.getValue("sys_msword_name");
		String sys_msword_template = db.getValue("sys_msword_template");
		String sys_msword_desp = db.getValue("sys_msword_desp");
		String sys_msword_bookmarks = db.getValue("sys_msword_bookmarks");
		MsWordDao dao = new MsWordDao();
		
		try {
			dao.addMsWord(sys_msword_id, sys_msword_name, sys_msword_template, sys_msword_bookmarks, sys_msword_desp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println("����wordģ���ļ�  \n"+context);		
		// ���Ӽ�¼������ VoSysMsword sys_msword = context.getSysMsword( inputNode );
		//table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯwordģ�������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30200104( SysMswordContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysMswordPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoSysMsword result = context.getSysMsword( outputNode );
	}
	
	/** ɾ��wordģ����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30200105( SysMswordContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoSysMswordPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
		
	/**
	 * ���ظ���ķ����������滻���׽ӿڵ��������
	 * ���ú���
	 * @param funcName ��������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void doService( String funcName,
			TxnContext context ) throws TxnException
	{
		Method method = (Method)txnMethods.get( funcName );
		if( method == null ){
			funcName = this.getClass().getName() + "#" + funcName;
			throw new TxnErrorException( ErrorConstant.JAVA_METHOD_NOTFOUND,
					"û���ҵ�������[" + txnCode + ":" + funcName + "]�Ĵ�����"
			);
		}
		
		// ִ��
		SysMswordContext appContext = new SysMswordContext( context );
		invoke( method, appContext );
	}
}
