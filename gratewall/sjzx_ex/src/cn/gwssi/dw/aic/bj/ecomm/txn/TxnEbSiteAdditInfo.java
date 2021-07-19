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
import cn.gwssi.dw.aic.bj.ecomm.vo.EbSiteAdditInfoContext;

public class TxnEbSiteAdditInfo extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnEbSiteAdditInfo.class, EbSiteAdditInfoContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "eb_site_addit_info";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select eb_site_addit_info list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one eb_site_addit_info";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one eb_site_addit_info";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one eb_site_addit_info";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one eb_site_addit_info";
	
	/**
	 * ���캯��
	 */
	public TxnEbSiteAdditInfo()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ��վ��Ϣ�б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6026101( EbSiteAdditInfoContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoEbSiteAdditInfoSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoEbSiteAdditInfo result[] = context.getEbSiteAdditInfos( outputNode );
	}
	
	/** �޸Ľ�վ��Ϣ��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6026102( EbSiteAdditInfoContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoEbSiteAdditInfo eb_site_addit_info = context.getEbSiteAdditInfo( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ���ӽ�վ��Ϣ��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6026103( EbSiteAdditInfoContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoEbSiteAdditInfo eb_site_addit_info = context.getEbSiteAdditInfo( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ��վ��Ϣ�����޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6026104( EbSiteAdditInfoContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoEbSiteAdditInfoPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoEbSiteAdditInfo result = context.getEbSiteAdditInfo( outputNode );
	}
	
	/** ɾ����վ��Ϣ��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6026105( EbSiteAdditInfoContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoEbSiteAdditInfoPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** �鿴��վ��Ϣ��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6026106( EbSiteAdditInfoContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoEbSiteAdditInfo eb_site_addit_info = context.getEbSiteAdditInfo( inputNode );
		table.executeFunction( "viewSiteAdditInfo", context, inputNode, outputNode );
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
		EbSiteAdditInfoContext appContext = new EbSiteAdditInfoContext( context );
		invoke( method, appContext );
	}
}
