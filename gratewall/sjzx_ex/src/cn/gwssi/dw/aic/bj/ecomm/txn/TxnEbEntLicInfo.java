package cn.gwssi.dw.aic.bj.ecomm.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import cn.gwssi.dw.aic.bj.ecomm.vo.EbEntLicInfoContext;

public class TxnEbEntLicInfo extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnEbEntLicInfo.class, EbEntLicInfoContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "eb_ent_lic_info";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select eb_ent_lic_info list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one eb_ent_lic_info";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one eb_ent_lic_info";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one eb_ent_lic_info";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one eb_ent_lic_info";
	
	/**
	 * ���캯��
	 */
	public TxnEbEntLicInfo()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ��վ���֤��Ϣ�б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6026301( EbEntLicInfoContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoEbEntLicInfoSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoEbEntLicInfo result[] = context.getEbEntLicInfos( outputNode );
	}
	
	/** �޸���վ���֤��Ϣ��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6026302( EbEntLicInfoContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoEbEntLicInfo eb_ent_lic_info = context.getEbEntLicInfo( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ������վ���֤��Ϣ��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6026303( EbEntLicInfoContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoEbEntLicInfo eb_ent_lic_info = context.getEbEntLicInfo( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ��վ���֤��Ϣ�����޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6026304( EbEntLicInfoContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoEbEntLicInfoPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoEbEntLicInfo result = context.getEbEntLicInfo( outputNode );
	}
	
	/** ɾ����վ���֤��Ϣ��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6026305( EbEntLicInfoContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoEbEntLicInfoPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ��վ���֤��Ϣ�б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6026306( EbEntLicInfoContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoEbEntLicInfoSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( "queryWebLicInfo", context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoEbEntLicInfo result[] = context.getEbEntLicInfos( outputNode );
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
		EbEntLicInfoContext appContext = new EbEntLicInfoContext( context );
		invoke( method, appContext );
	}
}
