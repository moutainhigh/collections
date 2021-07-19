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
 * @desc ��˰���ݲɼ���ִ��ÿ������20-22��ÿ��Сʱִ��һ��,
 *       �����һ��ִ�гɹ�,�������������ִ��.
 *       ���3�ζ�ִ��ʧ��,���첻�ڼ���ִ��
 */
public class TaxJob implements StatefulJob
{
	private static Log log =  LogFactory.getLog(TaxJob.class);
	
	private static final String SYS_CLT_USER_ID="001";
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		
		log.info("job execute start!");
		
		ProcessResult result = null;
		// �ж�֮ǰ��ִ���Ƿ�ɹ�,����ɹ����������ִ��
		try {
		    // �����ѯ������ִ����־��Ϣ
		    result = createProcess();
			log.info("����ProcessResult�ɹ�!");
		} catch (LocalTaxWSException e1) {
			log.error("����ִ��֮ǰִ���Ѿ��ɹ���!",e1);
			return;
		} catch (DBException e1) {
			log.error("����ProcessResult����ʱ��������,����bug!",e1);
			return;
		}catch (Exception e) {
			log.error("����ProcessResult����ʱ������������,����bug!",e);
			return;
		}
		

		try {
			GsWebServicePort_PortType services = GsWebServiceFactory.createGsWebService();
			ReturnMultiTaxData taxData = services.getLJ_QUERY();			
			String fhdm = taxData.getFHDM();
			if ("TAX0000".equalsIgnoreCase(fhdm)) {
				// ����webservice�ӿڣ�ȡ�����ݺ󣬹�����Ҫִ�е�sql���
				process(result);
				log.info("���õ�˰webservice�ɹ�");
				// ����ִ��sql�����˰���ݼ��ɹ�����־��Ϣ
				executeSql(result);
				log.info("ִ�б����˰���ݳɹ�");
				setResultInfo(result, "1", result.getStrLog().toString());
			} else {
				setResultInfo(result,"0","���ӵ�˰�ӿ�ʧ�ܣ�");
			}
		} catch (LocalTaxWSException e) {
			log.error("ִ��ʧ��!",e);
			setResultInfo(result,"0",e.getMessage());
		} catch (ServiceException e) {
			setResultInfo(result,"0","�޷����ӵ���˰�ӿڣ�");
		} catch (RemoteException e) {
			setResultInfo(result,"0","�޷����ӵ���˰�ӿڣ�");
		}
		
		try {
			log.info("��ʼ������־");
			// ����ʧ�ܵ���־��Ϣ
			saveLog(result);
			log.info("������־�ɹ�");
		} catch (Exception ex) {
			log.error("������־ʧ��,����bug!",ex);
		}
		
		log.info("job execute end!");
		
	}

	private ProcessResult createProcess() throws DBException, LocalTaxWSException, ParseException
	{
		
		DBOperation operation = DBOperationFactory.createOperation();
		// ��ѯ��־�����Ƿ���ڽ���ɼ��ɹ��ĵ�˰��¼,���������첻�ٲɼ�
		String sql = "select max(CLT_ENDDATE) as enddate from SYS_CLT_LOG where SYS_CLT_USER_ID='"+SYS_CLT_USER_ID+"' and state='1'";

		Map maxEndDate = operation.selectOne(sql);
		String endDate = (String) maxEndDate.get("ENDDATE");
		
		String currentDate = CalendarUtil.getAfterDateBySecond(
				CalendarUtil.FORMAT11_1, CalendarUtil
						.getCalendarByFormat(CalendarUtil.FORMAT11_1), -1);
		if (currentDate.equals(endDate)) {
			throw new LocalTaxWSException("���βɼ��Ѿ�ִ�н���!");
		}		
		// ����û�вɼ��Ļ�ȡ����˰�Ĳɼ����������翪ʼ�ɼ����������ڡ�ÿ�ε�����¼����ÿ�ε����������
		sql = "select DATADATE,MAXCOUNT,MAXDAY from SYS_CLT_USER where SYS_CLT_USER_ID='"+SYS_CLT_USER_ID+"'";
		Map param = operation.selectOne(sql);
		// ����result����
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

		// �鿴��־��Ϣ�ж��Ƿ��ǵ�һ��ִ�вɼ�
		sql = "select rownum as row_num from SYS_CLT_LOG where SYS_CLT_USER_ID='"+SYS_CLT_USER_ID+"' and rownum=1";
		Map map1 = operation.selectOne(sql);
		
		// �������־�����ʾ���ǵ�һ��ִ��
		if (map1 != null && map1.get("ROW_NUM") != null) {
			if(endDate!=null && !"".equals(endDate)){
				result.setStartDate(CalendarUtil.getAfterDateBySecond(
						CalendarUtil.FORMAT11_1, endDate, 1));
			} else {
				result.setStartDate(dataDate);
			}
		} 
		// ���û����־�����ʾ�ǵ�һ��ִ�У���ʼ����������Ϊ��ʼ����������
		else {
			result.setStartDate(dataDate);
		}
		log.info("�ɼ����ݿ�ʼ���ڣ�"+result.getStartDate());
		log.info("�ɼ����ݽ������ڣ�"+result.getEndDate());
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
		log.info("�ܹ��ɼ�����������"+result.getResultSql().size());
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
			throw new LocalTaxWSException("����ɼ���˰����ʧ��!", e);
		}
	}

	private void saveLog(ProcessResult result)
			throws LocalTaxWSException
	{		
		try {
			DBOperation operation = DBOperationFactory.createOperation();
			operation.execute(result.getLog(), true, "2");
		} catch (DBException e) {
			throw new LocalTaxWSException("����ɼ���־����ʧ��!", e);
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
