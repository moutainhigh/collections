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
import cn.gwssi.dw.aic.bj.ecomm.vo.EbSiteIllegTrailContext;

public class TxnEbSiteIllegTrail extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnEbSiteIllegTrail.class, EbSiteIllegTrailContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "eb_site_illeg_trail";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select eb_site_illeg_trail list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one eb_site_illeg_trail";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one eb_site_illeg_trail";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one eb_site_illeg_trail";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one eb_site_illeg_trail";
	
	/**
	 * ���캯��
	 */
	public TxnEbSiteIllegTrail()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯΥ�������б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6026401( EbSiteIllegTrailContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoEbSiteIllegTrailSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoEbSiteIllegTrail result[] = context.getEbSiteIllegTrails( outputNode );
	}
	
	/** �޸�Υ��������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6026402( EbSiteIllegTrailContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoEbSiteIllegTrail eb_site_illeg_trail = context.getEbSiteIllegTrail( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ����Υ��������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6026403( EbSiteIllegTrailContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoEbSiteIllegTrail eb_site_illeg_trail = context.getEbSiteIllegTrail( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯΥ�����������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6026404( EbSiteIllegTrailContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoEbSiteIllegTrailPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoEbSiteIllegTrail result = context.getEbSiteIllegTrail( outputNode );
	}
	
	/** ɾ��Υ��������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6026405( EbSiteIllegTrailContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoEbSiteIllegTrailPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯΥ�������б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6026406( EbSiteIllegTrailContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoEbSiteIllegTrailSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( "queryIllegTrail", context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoEbSiteIllegTrail result[] = context.getEbSiteIllegTrails( outputNode );
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
		EbSiteIllegTrailContext appContext = new EbSiteIllegTrailContext( context );
		invoke( method, appContext );
	}
}
