package com.gwssi.jms.cj.consumer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import cn.gwssi.common.component.logger.TxnLogger;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.constant.CollectConstants;
import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.util.XmlToMapUtil;
import com.gwssi.log.collectlog.dao.CollectLogVo;
import com.gwssi.webservice.client.ClientDAO;
import com.gwssi.webservice.client.ClientDAOImpl;
import com.gwssi.webservice.client.TaskInfo;
import com.gwssi.webservice.client.WsClient;
import com.gwssi.webservice.client.collect.DataHandleFactory;
import com.gwssi.webservice.client.collect.DataHandleInterface;

public class JmsCjResListener implements MessageListener {
	protected static Logger	logger		= TxnLogger.getLogger(WsClient.class.getName());	// 日志
	
	
	
	
	public void onMessage(Message message)
	{

		TextMessage textMessage = (TextMessage) message;
		 TaskInfo			taskInfo	= new TaskInfo();		// 任务信息
		
		CollectLogVo collectLogVo = new CollectLogVo();
		try {
			System.out.println(textMessage.getText());

			String dom = textMessage.getText();
			
			Map param = XmlToMapUtil.dom2Map(dom);// 将参数转化成Map
			
			String wsTaskId=(String) param.get(CollectConstants.WEBSERVICE_TASK_ID);//任务ID
			
			//查询采集任务表
			StringBuffer sql = new StringBuffer();
			
			sql.append("select * from collect_webservice_task t ");
			sql.append("where t.webservice_task_id =  '"+wsTaskId+"'");
			ClientDAO	dao = new ClientDAOImpl();
			Map webserviceMap = new HashMap();
			try {
				webserviceMap = dao.queryWebTask(sql.toString());
			} catch (DBException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    String tableId=(String)webserviceMap.get("COLLECT_TABLE");
		    String  collectMode=(String)webserviceMap.get("COLLECT_MODE");
		    String svr_code=(String)webserviceMap.get("SERVICE_NO");
			
			// 日志ws任务
			collectLogVo.setTask_id(wsTaskId);
			collectLogVo.setService_no(svr_code);
			
			
			//插入数据
			
			System.out.println("客户端接收到服务器端返回结果为"+dom);
			
			logger.debug("开始检验获得的返回结果...");
			Map resultMap = analyzeResult(dom, collectLogVo);
			boolean flag = Boolean.valueOf(resultMap.get("status").toString());
			// 将返回结果入库入库
			if (flag) {
				Map domMap = (Map) resultMap.get("result");
				DataHandleInterface dataHandle = DataHandleFactory
						.getDataHandle();
				int count=-1;
				try {
					count = dataHandle.insertData(tableId, collectMode, domMap,
							collectLogVo);
				} catch (DBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				collectLogVo.setCollect_data_amount(String.valueOf(count));
			} else {
				collectLogVo.setCollect_data_amount("0");
			}

			

			// 生成文件
			try {
				inputFile(dom, svr_code);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// 记录日志
			logger.debug("开始记录日志...");
			taskInfo.insertLog(collectLogVo);
			logger.debug("记录日志成功...");
			
		
			

		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * 
	 * analyzeResult 分析获得的返回结果是否正确
	 * 
	 * @param dom
	 * @param collectLogVo
	 * @return boolean
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	private static Map analyzeResult(String dom, CollectLogVo collectLogVo)
	{
		Map tepMap = new HashMap();
		boolean flag = false;
		if (null != dom && !"".equals(dom)) {
			try {
				Document doc = DocumentHelper.parseText(dom);
				Map domMap = XmlToMapUtil.dom2Map(dom);
				tepMap.put("result", domMap);
				flag = true;
			} catch (DocumentException e) {
				collectLogVo
						.setReturn_codes(CollectConstants.CLIENT_FHDM_TODOM_ERROR);
				logger.debug("将数据转化成dom格式错误..." + e);
				e.printStackTrace();
			} finally {
			}
		} else {
			collectLogVo.setReturn_codes(CollectConstants.CLIENT_FHDM_FAIL);
		}
		tepMap.put("status", flag);
		return tepMap;
	}
	
	/**
	 * 
	 * inputFile 存放文件
	 * 
	 * @param dom
	 * @param serviceCode
	 * @throws IOException
	 *             void
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	private static void inputFile(String dom, String serviceCode)
			throws IOException
	{
		java.sql.Timestamp dateForFileName = new java.sql.Timestamp(new Date()
				.getTime());

		logger.debug("开始存储xml文件...");
		StringBuffer path = new StringBuffer();
		path.append(ExConstant.COLLECT_XML);
		path.append(File.separator);
		path.append(serviceCode);
		path.append("_");
		path.append(timeFormat(dateForFileName.toString()));
		path.append(".xml");

		OutputFormat format = OutputFormat.createPrettyPrint();
		XMLWriter output = null;
		try {
			Document doc = DocumentHelper.parseText(dom);
			output = new XMLWriter(new FileOutputStream(new File(path
					.toString())), format);
			output.write(doc);
			logger.debug("存储xml文件完毕...");
			logger.debug("xml文件存储路径为：" + path);

		} catch (DocumentException e) {
			if (null != output)
				output.close();
			logger.debug("DocumentException:" + e);
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			if (null != output)
				output.close();
			logger.debug("UnsupportedEncodingException:" + e);
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			if (null != output)
				output.close();
			logger.debug("FileNotFoundException:" + e);
			e.printStackTrace();
		} catch (IOException e1) {
			if (null != output)
				output.close();
			logger.debug("IOException:" + e1);
			e1.printStackTrace();
		} finally {
			if (null != output)
				output.close();
		}
	}
	
	/**
	 * 
	 * timeFormat 将系统时间改为特定格式
	 * 
	 * @param time
	 * @return String
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	private static String timeFormat(String time)
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

}
