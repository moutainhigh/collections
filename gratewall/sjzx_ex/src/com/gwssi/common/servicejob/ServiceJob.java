package com.gwssi.common.servicejob;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.StatefulJob;

import cn.gwssi.common.component.logger.TxnLogger;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.constant.ShareConstants;
import com.gwssi.common.util.AnalyCollectFile;
import com.gwssi.common.util.ParamUtil;
import com.gwssi.ftp.FtpUtil;
import com.gwssi.resource.svrobj.vo.VoResDataSource;
import com.gwssi.share.ftp.vo.VoShareFtpService;
import com.gwssi.webservice.server.CheckService;
import com.gwssi.webservice.server.GSGeneralWebService;
import com.gwssi.webservice.server.ServerInfo;

public class ServiceJob implements StatefulJob
{
	protected static Logger	logger		= TxnLogger.getLogger(ServiceJob.class
												.getName());	// ��־

	ServerInfo				serverInfo	= new ServerInfo();

	/**
	 * �������ʱ����
	 */
	public void execute(JobExecutionContext jobCtx)
	{
		// ҵ���߼�
		// 1.�����������еĹ�������Ǳ����ĵȴ��û������ʽ���ƽ̨�ӿ�
		// 2.ֻ��FTP����Ҫ��������ָ��ʱ���ڽ��ӿڵ����������ļ���ŵ�FTP�������Ϲ��û���ȡ
		// 3.��ָ��ʱ�����doTask������ȡ����
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String nowTime = sDateFormat.format(new java.util.Date()); // ϵͳ��ǰ������ʱ����
		logger.debug("��ǰʱ��Ϊ��" + nowTime + " ��ʼ�����������ServiceJob_execute...");
		String taskId = jobCtx.getTrigger().getName(); // ����ID������service id��

		String startStr = ""; // ��ʼʱ��
		String endStr = ""; // ����ʱ��
		JobDataMap dataMap = jobCtx.getJobDetail().getJobDataMap();
		// System.out.println("taskId is " + taskId);
		logger.debug("srvId is " + dataMap.get("srvId"));
		// System.out.println("startTime is " + dataMap.get("startTime"));
		// System.out.println("endTime is " + dataMap.get("endTime"));
		if (null != dataMap && !dataMap.isEmpty()) {
			if (null != dataMap.get("startTime"))
				startStr = dataMap.get("startTime").toString();
			if (null != dataMap.get("endTime"))
				endStr = dataMap.get("endTime").toString();
		} else {
			logger.debug("�����������ʱ��������...");
		}
		if (!"".equals(startStr) && !"".equals(endStr)) {
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			String calNow = sd.format(new java.util.Date());
			String startTime = calNow + " " + startStr + ":00";
			String endTime = calNow + " " + endStr + ":00";

			// �Ƚ�ϵͳ��ǰʱ���������ȿ�ʼʱ�䡢����ʱ��
			CheckService check = new CheckService();
			boolean isIn = check.isInDatesForJob(nowTime.toString(), startTime,
					endTime);

			if (isIn) {
				try {
					this.doTask(taskId);
				} catch (FileNotFoundException e) {
					logger.info("ִ�й������FTP����ʧ��");
					e.printStackTrace();
				}
			} else {
				logger.debug("���������ʱ�䷶Χ��...");
			}
		} else {
			logger.debug("�����������ʱ��������...");
		}
	}

	/**
	 * 
	 * doTask ִ�й��������������
	 * 
	 * @param taskId
	 *            void
	 * @throws FileNotFoundException 
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	private void doTask(String taskId) throws FileNotFoundException
	{
		// 1.����taskID����service id��ѯFTP�������Ϣ
		// 2.��ȡ�ӿڲ�����Ϣ
		// 3.���������и�ʽ�������ڵ��÷���
		logger.info("����FTP���� id="+taskId+" ��ʼִ��");
		
		/**��ȡ��ǰ������Ϊ�ļ������ں�׺**/
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String date= df.format(calendar.getTime());
		
		/**��ȡ���������Ϣ**/
		Map ftpService = serverInfo.queryFtpService(taskId);
		VoShareFtpService ftpServiceVo = new VoShareFtpService();
		ParamUtil.mapToBean(ftpService, ftpServiceVo, false);

		// ��ȡ�ļ���������
		String fileName = ftpServiceVo.getFile_name();
		String fileType = ftpServiceVo.getFile_type();
		if ("01".equals(fileType)) {
			//���ļ���������Ϊ��׺			
			fileName = fileName +"_"+ date +".txt";
		}
		
		/**��ȡ����ʽ���������**/
		String ftpServiceId = ftpServiceVo.getFtp_service_id();
		List paramList = serverInfo.queryFtpParam(ftpServiceId); // ��ȡ����
		Map param = serverInfo.formatParam(paramList); // ��ʽ������		
		param.put("USER_TYPE", "SHARE_FTP"); //���ù���FTP����ר���û����ͱ�ʶ
		
		//��δ���ÿ�ʼ��¼���ͽ�����¶������������Ϊ��������������Ĭ��ֵ
		if(!param.containsKey(ShareConstants.SERVICE_OUT_PARAM_KSJLS)){
			param.put(ShareConstants.SERVICE_OUT_PARAM_KSJLS, "1");
		}
		int interval=5000;//ÿ��ȡ���ݵ�������� Ĭ��Ϊ5000��
		if(param.get(ShareConstants.SERVICE_OUT_PARAM_JSJLS)==null
				|| param.get(ShareConstants.SERVICE_OUT_PARAM_JSJLS)==""){
			param.put(ShareConstants.SERVICE_OUT_PARAM_JSJLS, "5000");
			
		}else{
			//�����õĽ�����¼����Ϊÿ��ȡ���ݵ���
			interval= Integer.parseInt((String)param.get(ShareConstants.SERVICE_OUT_PARAM_JSJLS));
		}
		/**���ݻ�ȡ���ļ�����**/
		try {
			GSGeneralWebService gs = new GSGeneralWebService();
			
			int zts=0;//��¼�Ѳɼ���������������
			boolean hasMore=false;
			StringBuffer filecontext = new StringBuffer();//�ļ�����
			String[] colNameEnArray=null;//Ӣ������
			long start = System.currentTimeMillis();
			do{
				System.out.println("����Ϊ��"+param);
				/**���÷����ȡ����**/
				Map result = gs.query(param); // ����query�ӿڻ�ȡ���
				if(result.get(ShareConstants.SERVICE_OUT_PARAM_FHDM)!=null){
					String returnCode=result.get(ShareConstants.SERVICE_OUT_PARAM_FHDM).toString();
					if(returnCode!=ShareConstants.SERVICE_FHDM_SUCCESS && returnCode!=ShareConstants.SERVICE_FHDM_NO_RESULT){
						//��ѯ���������ļ�
						logger.info("��ȡ����ʧ��δ�����ļ� ����ID="+taskId+"  ������="+returnCode);
						return;
					}else if(returnCode==ShareConstants.SERVICE_FHDM_NO_RESULT)
					{
						//������
						logger.info("�����ݣ����ɿ��ļ� ����ID="+taskId+"  ������="+returnCode);
						break;
					}
				}
				
				/**������ݴ���**/
				//long s=System.currentTimeMillis();
				if(!hasMore){
					/**ֻ�е�һ��ִ��ʱ��ӱ�����**/
					Map tepMap = gs.getOhterColumn(taskId);
					String columnStrEn = tepMap.get("column_name_en").toString();
					//String columnStrCn = tepMap.get("column_name_cn").toString();
					colNameEnArray = columnStrEn.split(",");
					for (int i = 0; i < colNameEnArray.length; i++) {
						if (i == (colNameEnArray.length - 1)) {
							filecontext.append(colNameEnArray[i] + "\r\n");
						} else {
							filecontext.append(colNameEnArray[i] + "@#");
						}
					}					
				}
				
				/**�������ж�**/
				String ztsCount = (String)result.get("ZTS");//����������
				//������¼��
				String jsjls= (String)result.get(ShareConstants.SERVICE_OUT_PARAM_JSJLS);
				if(ztsCount!=null && jsjls!=null ){
					zts=Integer.parseInt(ztsCount);
					int jsjlsInt=Integer.parseInt(jsjls);
					if(jsjlsInt<zts){
						logger.debug("������������Ҫ�ֶ�ζ�ȡ");
						hasMore=true;//��ʶλ						
						param.put(ShareConstants.SERVICE_OUT_PARAM_KSJLS,String.valueOf(jsjlsInt+1) );
						if(zts-interval>jsjlsInt){
							//ÿ��ȡinterval��
							param.put(ShareConstants.SERVICE_OUT_PARAM_JSJLS,String.valueOf(jsjlsInt+interval) );
						}else {
							//ʣ�����ݲ���interval��
							param.put(ShareConstants.SERVICE_OUT_PARAM_JSJLS,String.valueOf(zts) );
						}						
					}else{
						//û��������
						hasMore=false;
					}
					
				}
				
				HashMap[] filemapArray = (HashMap[]) result.get("GSDJ_INFO_ARRAY");				
				if (filemapArray != null && filemapArray.length != 0) {
					
					//��������
					for (int i = 0; filemapArray != null && i < filemapArray.length; i++) {
						for (int j = 0; j < colNameEnArray.length; j++) {
							HashMap tmpMap1 = (HashMap) filemapArray[i];
							String tmpStr = "";
							if (tmpMap1.get(colNameEnArray[j].toUpperCase()) != null) {
								tmpStr = tmpMap1.get(
										colNameEnArray[j].toUpperCase()).toString();
							}
							if (j == (colNameEnArray.length - 1)) {
								filecontext.append(replaceBlank(tmpStr.trim())
										+ "\r\n");
							} else {
								filecontext.append(replaceBlank(tmpStr.trim())
										+ "@#");
							}
						}
					}
				}
				//long e=System.currentTimeMillis();
				//logger.debug("���������ʱ��"+(e-s)/1000f+"��");
			}while(hasMore);
			long end = System.currentTimeMillis();
			logger.debug("���ݻ�ȡ��ʱ��"+(end-start)/1000f+"��");
			/**���ݻ�ȡ�����������������ļ�**/
			long start1 = System.currentTimeMillis();
			// ������������ļ����ļ���
			StringBuffer dataFileName = new StringBuffer();
			dataFileName.append(ExConstant.FILE_FTP);
			dataFileName.append(File.separator);
			dataFileName.append(fileName);
			//dataFileName.append(".txt");
			System.out.println("dataFileName is " + dataFileName);
			//��������У���ļ�
			StringBuffer checkFileContext=new StringBuffer();
			checkFileContext.append(ftpServiceVo.getFile_name())
				.append("_").append(date).append("@#").append(String.valueOf(zts)).append("\r\n");
			StringBuffer checkFileName=new StringBuffer();
			checkFileName.append(ExConstant.FILE_FTP);
			checkFileName.append(File.separator);
			String loginName="";
			if(param.get("LOGIN_NAME")!=null){				
				loginName=param.get("LOGIN_NAME").toString();
			}			
			if(StringUtils.isNotBlank(loginName)){
				//Ϊ����У���ļ����������ɵ�У���ļ��ڱ����Ȱ���¼�����ļ��б���
				checkFileName.append(loginName);
				checkFileName.append(File.separator);
			}			
			String checkName="CHECK_"+date+".txt";
			checkFileName.append(checkName);
			// �����ļ�
			AnalyCollectFile ac = new AnalyCollectFile();
			ac.writeFile(dataFileName.toString(), filecontext.toString());
			logger.info("�����ļ���"+dataFileName);
			//����׷�ӷ�ʽ���⸲��
			ac.appendContext(checkFileName.toString(), checkFileContext.toString());
			logger.info("����У���ļ���"+checkFileName);
			long end1 = System.currentTimeMillis();
			logger.debug("�ļ����ɺ�ʱ��"+(end1-start1)/1000f+"��");
			/**�ϴ��ļ�**/
			String datasourceId = ftpServiceVo.getDatasource_id();// ��ȡFTP����ԴID
			// ��ѯFTP����Դ��Ϣ
			Map datasourceMap = serverInfo.queryFTPDatasource(datasourceId);
			//System.out.println("datasourceMap is " + datasourceMap);
			VoResDataSource vo = new VoResDataSource();
			ParamUtil.mapToBean(datasourceMap, vo, false);

			// ��ȡFTP������IP �˿�
			String ftpIp = vo.getData_source_ip();
			String port = vo.getAccess_port();
			String username = vo.getDb_username();
			String password = vo.getDb_password();
			String path = vo.getAccess_url();
			
			FileInputStream in;
			in = new FileInputStream(new File(dataFileName.toString()));
			boolean rs=FtpUtil.uploadFile(ftpIp, Integer.parseInt(port), username, password,
					path, fileName, in);
			if (rs) {
				logger.info("�����ļ�"+fileName+"�ϴ��ɹ�");
			}else{
				logger.info("�����ļ�"+fileName+"�ϴ�ʧ��");
			}
			
			in = new FileInputStream(new File(checkFileName.toString()));			
			rs=FtpUtil.uploadFile(ftpIp, Integer.parseInt(port), username, password,
					path, checkName, in);
			if (rs) {
				logger.info("У���ļ�"+checkName+"�ϴ��ɹ�");
			}else{
				logger.info("У���ļ�"+checkName+"�ϴ�ʧ��");
			}

		} catch (DBException e) {
			e.printStackTrace();
		}finally{
			logger.info("����FTP���� id="+taskId+" ִ�н���");
		}
// } catch (FileNotFoundException e) {
// e.printStackTrace();
// }
	}

	public String replaceBlank(String str)
	{
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			str = m.replaceAll("");
		}
		return str;
	}

	private String timeFormat(String time)
	{
		String currenttime = "";
		if (time != null && !"".equals(time)) {
			String temp[] = time.split(" ");
			String temp1[] = temp[0].split("-");
			String temp2[] = temp[1].split(":");
			for (int i = 0; i < temp1.length; i++) {
				currenttime = currenttime + temp1[i];
			}
			for (int j = 0; j < temp2.length - 1; j++) {
				currenttime = currenttime + temp2[j];
			}
		}
		return currenttime;
	}

	private String timeFormat1(String time)
	{
		Calendar cal = Calendar.getInstance();
		int yearInt = cal.get(Calendar.YEAR);
		String theYear = String.valueOf(yearInt);

		int monthInt = cal.get(Calendar.MONTH) + 1;
		String theMonth = String.valueOf(monthInt);
		// ����·�Ϊ:1�Ļ����ӦΪ��һ��
		if (monthInt == 1) {
			theYear = String.valueOf(yearInt - 1);
			theMonth = "12";
		} else {
			theMonth = String.valueOf(monthInt - 1);
			if (theMonth.length() == 1) {
				theMonth = "0" + theMonth;
			}
		}
		return theYear + theMonth;
	}

	public static void main(String[] arags)
	{
		

	}
}
