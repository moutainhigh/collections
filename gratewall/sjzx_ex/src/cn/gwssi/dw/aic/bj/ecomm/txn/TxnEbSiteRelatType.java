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
import cn.gwssi.dw.aic.bj.ecomm.vo.EbSiteRelatTypeContext;

public class TxnEbSiteRelatType extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnEbSiteRelatType.class, EbSiteRelatTypeContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "eb_site_relat_type";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select eb_site_relat_type list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one eb_site_relat_type";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one eb_site_relat_type";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one eb_site_relat_type";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one eb_site_relat_type";
	
	/**
	 * ���캯��
	 */
	public TxnEbSiteRelatType()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ���������Ϣ�б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6026201( EbSiteRelatTypeContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoEbSiteRelatTypeSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoEbSiteRelatType result[] = context.getEbSiteRelatTypes( outputNode );
	}
	
	/** �޸Ĳ��������Ϣ��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6026202( EbSiteRelatTypeContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoEbSiteRelatType eb_site_relat_type = context.getEbSiteRelatType( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ���Ӳ��������Ϣ��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6026203( EbSiteRelatTypeContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoEbSiteRelatType eb_site_relat_type = context.getEbSiteRelatType( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ���������Ϣ�����޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6026204( EbSiteRelatTypeContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoEbSiteRelatTypePrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoEbSiteRelatType result = context.getEbSiteRelatType( outputNode );
	}
	
	/** ɾ�����������Ϣ��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn6026205( EbSiteRelatTypeContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoEbSiteRelatTypePrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
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
		EbSiteRelatTypeContext appContext = new EbSiteRelatTypeContext( context );
		invoke( method, appContext );
	}
}
