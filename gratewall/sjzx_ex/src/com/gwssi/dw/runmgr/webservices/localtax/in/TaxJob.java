package com.gwssi.dw.runmgr.webservices.localtax.in;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.TxnContext;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.database.DBOperation;
import com.gwssi.common.database.DBOperationFactory;
import com.gwssi.common.util.CalendarUtil;
import com.gwssi.common.util.ResourceBundleUtil;
import com.gwssi.dw.runmgr.webservices.common.ProcessResult;
import com.gwssi.dw.runmgr.webservices.localtax.LocalTaxWSException;
import com.gwssi.dw.runmgr.webservices.localtax.in.client.GsWebServiceFactory;
import com.gwssi.dw.runmgr.webservices.localtax.in.client.GsWebServicePort_PortType;
import com.gwssi.dw.runmgr.webservices.localtax.in.client.ReturnMultiGSData;
import com.gwssi.dw.runmgr.webservices.localtax.in.client.ReturnMultiTaxData;
/**
 * 
 * @author saga
 * @desc 地税数据采集的执行每天晚上20-22点每半小时执行一次,
 *       如果第一次执行成功,则后续两次无需执行.
 *       如果3次都执行失败,则当天不在继续执行
 */
public class TaxJob implements StatefulJob
{
	private static Log log =  LogFactory.getLog(TaxJob.class);
	
	private static final String SYS_CLT_USER_ID="001";
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		
		log.info("job execute start!");
		
		ProcessResult result = null;
		// 判断之前的执行是否成功,如果成功则无须继续执行
		try {
		    // 构造查询条件、执行日志信息
		    result = createProcess();
			log.info("构造ProcessResult成功!");
		} catch (LocalTaxWSException e1) {
			log.error("本次执行之前执行已经成功过!",e1);
			return;
		} catch (DBException e1) {
			log.error("构造ProcessResult对象时发生错误,程序bug!",e1);
			return;
		}catch (Exception e) {
			log.error("构造ProcessResult对象时发生其他错误,程序bug!",e);
			return;
		}
		

		try {
			GsWebServicePort_PortType services = GsWebServiceFactory.createGsWebService();
			ReturnMultiTaxData taxData = services.getLJ_QUERY();			
			String fhdm = taxData.getFHDM();
			if ("TAX0000".equalsIgnoreCase(fhdm)) {
				// 调用webservice接口，取得数据后，构造需要执行的sql语句
				process(result);
				log.info("调用地税webservice成功");
				// 具体执行sql保存地税数据及成功的日志信息
				executeSql(result);
				log.info("执行保存地税数据成功");
				setResultInfo(result, "1", result.getStrLog().toString());
			} else {
				setResultInfo(result,"0","连接地税接口失败！");
			}
		} catch (LocalTaxWSException e) {
			log.error("执行失败!",e);
			setResultInfo(result,"0",e.getMessage());
		} catch (ServiceException e) {
			setResultInfo(result,"0","无法连接到地税接口！");
		} catch (RemoteException e) {
			setResultInfo(result,"0","无法连接到地税接口！");
		}
		
		try {
			log.info("开始保存日志");
			// 保存失败的日志信息
			saveLog(result);
			log.info("保存日志成功");
		} catch (Exception ex) {
			log.error("保存日志失败,程序bug!",ex);
		}
		
		log.info("job execute end!");
		
	}

	private ProcessResult createProcess() throws DBException, LocalTaxWSException, ParseException
	{
		
		DBOperation operation = DBOperationFactory.createOperation();
		// 查询日志表中是否存在今天采集成功的地税记录,如果有则今天不再采集
		String sql = "select max(CLT_ENDDATE) as enddate from SYS_CLT_LOG where SYS_CLT_USER_ID='"+SYS_CLT_USER_ID+"' and state='1'";

		Map maxEndDate = operation.selectOne(sql);
		String endDate = (String) maxEndDate.get("ENDDATE");
		
		String currentDate = CalendarUtil.getAfterDateBySecond(
				CalendarUtil.FORMAT11_1, CalendarUtil
						.getCalendarByFormat(CalendarUtil.FORMAT11_1), -1);
		if (currentDate.equals(endDate)) {
			throw new LocalTaxWSException("本次采集已经执行结束!");
		}		
		// 今天没有采集的话取出地税的采集参数，最早开始采集的数据日期、每次的最大记录数、每次的最大间隔天数
		sql = "select DATADATE,MAXCOUNT,MAXDAY from SYS_CLT_USER where SYS_CLT_USER_ID='"+SYS_CLT_USER_ID+"'";
		Map param = operation.selectOne(sql);
		// 构造result对象
		String dataDate = (String) param.get("DATADATE");
		String maxDay = (String) param.get("MAXDAY");
		String maxCount = (String) param.get("MAXCOUNT");
		
		ProcessResult result = new ProcessResult();
		result.setDataDate(dataDate);
		result.setMaxDay(maxDay);
		result.setEndDate(currentDate);
		result.setStartNum("1");
		result.setEndNum(maxCount);
		result.getCollectionLog().setSys_clt_user_id(SYS_CLT_USER_ID);
		result.getCollectionLog().setExc_starttime(CalendarUtil
				.getCalendarByFormat(CalendarUtil.FORMAT14));

		// 查看日志信息判断是否是第一次执行采集
		sql = "select rownum as row_num from SYS_CLT_LOG where SYS_CLT_USER_ID='"+SYS_CLT_USER_ID+"' and rownum=1";
		Map map1 = operation.selectOne(sql);
		
		// 如果有日志，则表示不是第一次执行
		if (map1 != null && map1.get("ROW_NUM") != null) {
			if(endDate!=null && !"".equals(endDate)){
				result.setStartDate(CalendarUtil.getAfterDateBySecond(
						CalendarUtil.FORMAT11_1, endDate, 1));
			} else {
				result.setStartDate(dataDate);
			}
		} 
		// 如果没有日志，则表示是第一次执行，则开始的数据日期为初始的数据日期
		else {
			result.setStartDate(dataDate);
		}
		log.info("采集数据开始日期："+result.getStartDate());
		log.info("采集数据结束日期："+result.getEndDate());
		return result;
	}

	private void process(ProcessResult result) throws LocalTaxWSException
	{
		
		Process process = new TaxRegInfoProcess(new TaxRegChgProcess(
				new TaxRegLockProcess(new TaxRegUnLockProcess(null))));

		process.handle(result);
	}

	private void executeSql(ProcessResult result) throws LocalTaxWSException
	{
		log.info("总共采集的数据量："+result.getResultSql().size());
		try {
			List sqls = result.getResultSql();
			/*
			for(int i =0;sqls!=null&&i<sqls.size();i++){
				log.info((String)sqls.get(i));
			}
			*/
			if (sqls != null&&sqls.size()>0) {
				DBOperation operation = DBOperationFactory.createOperation();
				operation.execute(sqls, true, "3");
			}
		} catch (DBException e) {
			e.printStackTrace();
			throw new LocalTaxWSException("保存采集地税数据失败!", e);
		}
	}

	private void saveLog(ProcessResult result)
			throws LocalTaxWSException
	{		
		try {
			DBOperation operation = DBOperationFactory.createOperation();
			operation.execute(result.getLog(), true, "2");
		} catch (DBException e) {
			throw new LocalTaxWSException("保存采集日志数据失败!", e);
		}
		
	}

	private void setResultInfo(ProcessResult result, String flag, String msg)
	{
		result.getCollectionLog().setState(flag);
		result.getCollectionLog().setLogdesc(msg);
		result.getCollectionLog().setExc_endtime(CalendarUtil
				.getCalendarByFormat(CalendarUtil.FORMAT14));
	}
}
