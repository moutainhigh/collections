package cn.gwssi.app.bizlog.plugin;

import cn.gwssi.app.bizlog.vo.VoBizlog;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.context.vo.VoCtrl;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.proxy.config.TxnDefine;
import cn.gwssi.common.txn.logger.LoggerService;
import com.gwssi.common.util.UuidGenerator;

public class BizLogger extends LoggerService
{
	// ���ݱ�����
	private static final String TABLE_NAME = "biz_log";

	/**
	 * д��־��Ϣ
	 */
	public void writeLogger(TxnDefine txnDefine, TxnContext context,
			String errCode, String errDesc) throws TxnException
	{
		// ������Ϣ
		VoCtrl ctrl = context.getConttrolData();
		
		// û����ˮ�ŵĽ��ײ���¼��־
		String flowid = ctrl.getFlowid();
		UuidGenerator ug=new UuidGenerator();
		if( flowid == null ){
			flowid=String.valueOf(ug.getLongId());
			//return;
		}
		
		// ������־����
		VoBizlog logData = new VoBizlog();
		logData.setReqflowno( flowid );
		
		// ��������
		logData.setTrdtype( txnDefine.getSortName() );
		
		// ������
		logData.setErrcode( errCode );
		logData.setErrdesc( errDesc );
		
		// �������
		//logData.setResultdata( context.toString() );
		logData.setResultdata( "" );
		
//		logData.setResultdata( "1" );
		
		log.info("�������logData��");
		log.info(logData.toString());
		
		// ���Ӽ�¼
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeUpdate( "insert one biz_log", context, logData );
	}
	
}
