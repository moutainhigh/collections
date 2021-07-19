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
	// 数据表名称
	private static final String TABLE_NAME = "biz_log";

	/**
	 * 写日志信息
	 */
	public void writeLogger(TxnDefine txnDefine, TxnContext context,
			String errCode, String errDesc) throws TxnException
	{
		// 控制信息
		VoCtrl ctrl = context.getConttrolData();
		
		// 没有流水号的交易不记录日志
		String flowid = ctrl.getFlowid();
		UuidGenerator ug=new UuidGenerator();
		if( flowid == null ){
			flowid=String.valueOf(ug.getLongId());
			//return;
		}
		
		// 生成日志数据
		VoBizlog logData = new VoBizlog();
		logData.setReqflowno( flowid );
		
		// 交易类型
		logData.setTrdtype( txnDefine.getSortName() );
		
		// 处理结果
		logData.setErrcode( errCode );
		logData.setErrdesc( errDesc );
		
		// 结果数据
		//logData.setResultdata( context.toString() );
		logData.setResultdata( "" );
		
//		logData.setResultdata( "1" );
		
		log.info("下面输出logData：");
		log.info(logData.toString());
		
		// 增加记录
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeUpdate( "insert one biz_log", context, logData );
	}
	
}
