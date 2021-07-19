package com.gwssi.dw.runmgr.services.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import com.gwssi.common.util.Constants;
import com.gwssi.dw.component.quartz.QuartzInitService;
import com.gwssi.dw.component.quartz.webservice.IQuartzWebService;
import com.gwssi.dw.component.quartz.webservice.IQuartzWebServiceFactory;
import com.gwssi.dw.runmgr.services.vo.SysCltUserContext;
import com.gwssi.dw.runmgr.services.vo.VoSysCltUser;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

public class TxnSysCltUser extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap		txnMethods		= getAllMethod(
														TxnSysCltUser.class,
														SysCltUserContext.class);

	// 数据表名称
	private static final String	USER_TABLE_NAME	= "sys_clt_user";

	private static final String	ROWSET_FUNCTION	= "select sys_clt_user list";

	private static final String	SELECT_FUNCTION	= "select one sys_clt_user";

	private static final String	UPDATE_FUNCTION	= "update one sys_clt_user";

	protected void prepare(TxnContext arg0) throws TxnException
	{
	}

	public void txn50211001(SysCltUserContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				USER_TABLE_NAME);
		// 此方法在user的dao的配置文件中配置
		table.executeFunction(ROWSET_FUNCTION, context, inputNode, outputNode);
		Recordset rs = context.getRecordset(outputNode);
		for (int i = 0; rs != null && i < rs.size(); i++) {
			DataBus temp = rs.get(i);
			StringBuffer time = new StringBuffer();
			String stratrgy = temp.getString(VoSysCltUser.ITEM_STRATEGY);
			String stratrgydesc = temp
					.getString(VoSysCltUser.ITEM_STRATEGYDESC);
			String hours = temp.getString(VoSysCltUser.ITEM_HOURS);
			String minutes = temp.getString(VoSysCltUser.ITEM_MINUTES);
			String seconds = temp.getString(VoSysCltUser.ITEM_SECONDS);
			if ("1".equals(stratrgy)) {
				String week = transEngToChkOfWeek(stratrgydesc);
				time.append(week);
			}
			time.append(hours).append(":").append(minutes).append(":").append(
					seconds);
			temp.setValue(VoSysCltUser.ITEM_STRATEGYTIME, time.toString());

		}
	}

	public void txn50211002(SysCltUserContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				USER_TABLE_NAME);
		// 此方法在user的dao的配置文件中配置
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		context.getRecord(outputNode)
				.setValue(
						VoSysCltUser.ITEM_OLDSTATE,
						context.getRecord(outputNode).getValue(
								VoSysCltUser.ITEM_STATE));
	}

	public void txn50211003(SysCltUserContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				USER_TABLE_NAME);
		table.executeFunction(UPDATE_FUNCTION, context, inputNode, outputNode);
		DataBus db = context.getRecord(outputNode);
		String name = db.getValue(VoSysCltUser.ITEM_NAME);
		String state = db.getValue(VoSysCltUser.ITEM_STATE);
		String user_type = db.getValue(VoSysCltUser.ITEM_USERTYPE);
		String oldstate = db.getValue(VoSysCltUser.ITEM_OLDSTATE);
		// 如果状态没有改变
		if(state.equals(oldstate)){
			// 如果状态处于启用，那么可能更新了策略，因此需要先将调度卸载，然后根据新的调度策略重新启动
			if("0".equals(state)){
				try {
					if("0".equals(user_type)){
					    QuartzInitService.resetJob(db);
					}else if("1".equals(user_type)){
						IQuartzWebService serv = IQuartzWebServiceFactory.createIQuartzWebService();
						serv.resetJob(db.getValue(VoSysCltUser.ITEM_JOBNAME),
								db.getValue(VoSysCltUser.ITEM_GROUPNAME),
								db.getValue(VoSysCltUser.ITEM_CLASSNAME),
								db.getValue(VoSysCltUser.ITEM_HOURS),
								db.getValue(VoSysCltUser.ITEM_MINUTES),
								db.getValue(VoSysCltUser.ITEM_SECONDS),
								db.getValue(VoSysCltUser.ITEM_STRATEGY),
								db.getValue(VoSysCltUser.ITEM_STRATEGYDESC),
								db.getValue(VoSysCltUser.ITEM_STARTDATE),
								db.getValue(VoSysCltUser.ITEM_ENDDATE));
					}
					context.getRecord(Constants.BIZLOG_NAME).setValue(Constants.VALUE_NAME, "重新启用"+name+"的采集策略");
				} catch (Exception e) {
					e.printStackTrace();
					throw new TxnErrorException("50211003","重新启动采集策略失败！");
				}
			}
			// 如果状态处于停用，那么可能更新了策略，此时只需更新数据库中的调度信息。
		}
		// 如果状态发生改变了
		else{
            //如果状态处于启用，那么之前处于停用状态，此时只需将调度启动
			if("0".equals(state)){
				try {
					if("0".equals(user_type)){
					    QuartzInitService.startJob(db);
					}else if("1".equals(user_type)){
						IQuartzWebService serv = IQuartzWebServiceFactory.createIQuartzWebService();
						serv.startJob(db.getValue(VoSysCltUser.ITEM_JOBNAME),
								db.getValue(VoSysCltUser.ITEM_GROUPNAME),
								db.getValue(VoSysCltUser.ITEM_CLASSNAME),
								db.getValue(VoSysCltUser.ITEM_HOURS),
								db.getValue(VoSysCltUser.ITEM_MINUTES),
								db.getValue(VoSysCltUser.ITEM_SECONDS),
								db.getValue(VoSysCltUser.ITEM_STRATEGY),
								db.getValue(VoSysCltUser.ITEM_STRATEGYDESC),
								db.getValue(VoSysCltUser.ITEM_STARTDATE),
								db.getValue(VoSysCltUser.ITEM_ENDDATE));
					}					
					context.getRecord(Constants.BIZLOG_NAME).setValue(Constants.VALUE_NAME, "启用"+name+"的采集策略");
				} catch (Exception e) {
					e.printStackTrace();
					throw new TxnErrorException("50211003","启动采集策略失败！");
				}
			}
			//如果状态处于停用，那么之前处于启用状态，此时只需将调度卸载
			else{
				try {
					if("0".equals(user_type)){
					    QuartzInitService.stopJob(db);
					}else if("1".equals(user_type)){
						IQuartzWebService serv = IQuartzWebServiceFactory.createIQuartzWebService();
						serv.stopJob(db.getValue(VoSysCltUser.ITEM_JOBNAME),
								db.getValue(VoSysCltUser.ITEM_GROUPNAME),
								db.getValue(VoSysCltUser.ITEM_CLASSNAME),
								db.getValue(VoSysCltUser.ITEM_HOURS),
								db.getValue(VoSysCltUser.ITEM_MINUTES),
								db.getValue(VoSysCltUser.ITEM_SECONDS),
								db.getValue(VoSysCltUser.ITEM_STRATEGY),
								db.getValue(VoSysCltUser.ITEM_STRATEGYDESC),
								db.getValue(VoSysCltUser.ITEM_STARTDATE),
								db.getValue(VoSysCltUser.ITEM_ENDDATE));
					}	
					context.getRecord(Constants.BIZLOG_NAME).setValue(Constants.VALUE_NAME, "停用"+name+"的采集策略");
				} catch (Exception e) {
					e.printStackTrace();
					throw new TxnErrorException("50211003","停用采集策略失败！");
				}
			}
		}
	}	
	
	private String transEngToChkOfWeek(String engWeek)
	{
		StringBuffer chkWeek = new StringBuffer();
		String[] weeks = engWeek.split(",");
		for (int i = 0; weeks != null && i < weeks.length; i++) {
			if (chkWeek.length() > 0) {
				chkWeek.append(",");
			}
			chkWeek.append(Constants.weeks.get(weeks[i]));
		}
		return chkWeek.toString();
	}

	/**
	 * 重载父类的方法，用于替换交易接口的输入变量 调用函数
	 * 
	 * @param funcName
	 *            方法名称
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	protected void doService(String funcName, TxnContext context)
			throws TxnException
	{
		Method method = (Method) txnMethods.get(funcName);
		if (method == null) {
			funcName = this.getClass().getName() + "#" + funcName;
			throw new TxnErrorException(ErrorConstant.JAVA_METHOD_NOTFOUND,
					"没有找到交易码[" + txnCode + ":" + funcName + "]的处理函数");
		}

		// 执行
		SysCltUserContext appContext = new SysCltUserContext(context);
		invoke(method, appContext);
	}
}
