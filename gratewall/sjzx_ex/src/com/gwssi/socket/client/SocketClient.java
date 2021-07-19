package com.gwssi.socket.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import cn.gwssi.common.component.logger.TxnLogger;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.collect.webservice.vo.VoCollectTask;
import com.gwssi.collect.webservice.vo.VoCollectWebserviceTask;
import com.gwssi.common.constant.CollectConstants;
import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.util.ParamUtil;
import com.gwssi.common.util.UuidGenerator;
import com.gwssi.common.util.XmlToMapUtil;
import com.gwssi.log.collectlog.dao.CollectLogVo;
import com.gwssi.resource.svrobj.vo.VoResDataSource;
import com.gwssi.resource.svrobj.vo.VoResServiceTargets;
import com.gwssi.socket.share.client.TestClient;
import com.gwssi.webservice.client.GSGeneralClient;
import com.gwssi.webservice.client.TaskInfo;
import com.gwssi.webservice.client.WsClient;
import com.gwssi.webservice.client.collect.DataHandleFactory;
import com.gwssi.webservice.client.collect.DataHandleInterface;

public class SocketClient
{

	public static void main(String[] args) throws DBException, IOException
	{
		SocketClient client = new SocketClient();
		String ipaddr = "172.30.18.50";
		//client.createSocketClient(ipaddr, 4700);
		doCollectTask("1dfd421167bc44b1bcd0ea71d75e9680");
	}
	
	public void createSocketClient(String host, int port)
	{
		Socket socket = null;
		PrintWriter pw = null;
		BufferedReader br = null;
		try {
			String request = "我是客户端，请求连接!!";
			socket = new Socket(host, port);
			pw = new PrintWriter(new OutputStreamWriter(socket
					.getOutputStream()));
			pw.println(request);
			pw.flush();
			br = new BufferedReader(new InputStreamReader(socket
					.getInputStream()));
			System.out.println(br.readLine());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

	protected static Logger	logger		= TxnLogger.getLogger(WsClient.class.getName());	// 日志

	static TaskInfo			taskInfo	= new TaskInfo();		// 任务信息

	/**
	 * 
	 * doCollectTask 执行 socket 采集任务
	 * 
	 * @param taskId
	 * @return boolean
	 * @throws DBException
	 * @throws IOException
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public static void doCollectTask(String taskId) throws DBException,IOException
	{
		// 业务逻辑
		// 由于不同委办局的接口都不一致(包括：入参个数、入参格式、出参格式等)，数据交换平台对外提供一个统一的标准
		// 如果按照标准做的话就可以使用一个统一的方法，要是有例外就根据不同的单位使用的接口不同建立不同的采集方法
		// 此业务流程只适用于按照标准格式webservice采集
		// 1.获取传入参数任务ID
		// 2.根据任务ID查询采集服务对象NO
		// 3.根据不同的采集对象走不同的采集方法（如果是按照标准格式的则走统一方法如：地税）
		// 4.根据任务ID查询任务详细信息并记录日志
		// 5.根据任务ID查询服务对象详细信息并记录日志
		// 6.根据任务ID查询数据源信息
		// 6a.确定此任务要调用的webservice的URL
		// 7.根据任务ID查询服务提供者共提供了多少个服务方法,并获得每个服务方法的Id
		// 8.根据服务方法ID查询具体的每个方法的详细信息
		// 8.根据服务方法ID，获取需要传入的参数
		// 9.封装参数
		// 10.调用服务，并获取结果集
		// 11.根据服务方法ID查询调用此方法得到的结果集要插入的采集表
		// 12.封装插入的SQL语句
		// 13.执行SQL插入(这里要注意是增量还是全量)
		// 13a.如果总条数比本次取的结束记录数大，表明还有数据尚未取完，那么重复10直到数据全部取完为止
		// 14.记录日志

		int c = CollectConstants.COUNT;
		CollectConstants.COUNT++;
		System.out.println("第" + c + "次");
		CollectLogVo collectLogVo = new CollectLogVo(); // 采集日志记录
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String startTime = sDateFormat.format(new java.util.Date()); // 系统当前年月日时分秒
		logger.debug("当前时间为：" + startTime + "开始采集数据...");
		logger.debug("获得的参数任务ID为：[" + taskId + "]...");

		//logger.debug("开始查询采集任务对象...");
		//String targetNo = taskInfo.queryTargetNo(taskId);
		//logger.debug("查询采集任务对象完毕...");
		//logger.debug("任务对象为：" + targetNo);
		
		String batch = UuidGenerator.getUUID(); //批次号
		collectLogVo.setBatch_num(batch);
		
		Map beforeMap = collectBefore(taskId, collectLogVo); // 采集数据准备工作

		//doGetData(taskId, beforeMap, collectLogVo); // 用socket 采集方法
		collecting(beforeMap, collectLogVo); // 地税采集方法

	}
	
	
	/**
	 * 
	 * collectBefore 采集数据准备方法
	 * 
	 * @param taskId
	 * @param collectLogVo
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	protected static Map collectBefore(String taskId, CollectLogVo collectLogVo)
			throws DBException
	{
		Long start = System.currentTimeMillis(); // 开始时间用于计算耗时
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String startTime = sDateFormat.format(new java.util.Date()); // 系统当前年月日时分秒
		collectLogVo.setTask_start_time(startTime); // 采集开始时间

		// 根据taskId查询服务对象
		logger.debug("开始查询任务...");
		Map taskMap = taskInfo.queryTask(taskId);
		VoCollectTask taskVo = new VoCollectTask();
		ParamUtil.mapToBean(taskMap, taskVo, false);
		String srvTargetId = taskVo.getService_targets_id();
		logger.debug("查询任务完毕...");
		logger.debug("采集对象ID为：" + srvTargetId);

		collectLogVo.setCollect_task_id(taskVo.getCollect_task_id());
		collectLogVo.setCollect_type(taskVo.getCollect_type());
		collectLogVo.setTask_name(taskVo.getTask_name());

		logger.debug("开始查询采集对象...");
		VoResServiceTargets srvTargetVo = new VoResServiceTargets();
		Map srvTargetMap = taskInfo.querySrvTager(srvTargetId);
		ParamUtil.mapToBean(srvTargetMap, srvTargetVo, false);
		logger.debug("查询采集对象完毕...");

		// 日志服务对象
		collectLogVo.setService_targets_id(srvTargetVo.getService_targets_id());
		collectLogVo.setService_targets_name(srvTargetVo
				.getService_targets_name());

		// 根据taskId查询数据源信息
		logger.debug("开始查询数据源...");
		Map dataSourceMap = taskInfo.queryDataSource(taskId);
		VoResDataSource dataSourceVo = new VoResDataSource();
		ParamUtil.mapToBean(dataSourceMap, dataSourceVo, false);
		logger.debug("查询数据源完毕...");

		String accessUrl = dataSourceVo.getAccess_url();
		logger.debug("服务访问路径为：" + accessUrl);
		
		String ip=dataSourceVo.getData_source_ip();
		logger.debug("服务访问IP为：" + ip);
		
		String port=dataSourceVo.getAccess_port();
		logger.debug("服务访问端口为：" + port);

		// 根据taskId查询webservice方法
		logger.debug("开始查询服务方法...");
		List webserviceTask = taskInfo.queryMethod(taskId);
		logger.debug("查询服务方法完毕...");
		logger.debug("该服务共有[" + webserviceTask.size() + "]个方法...");

		// 返回值封装
		Map tepMap = new HashMap();
		tepMap.put(CollectConstants.WSDL_URL, accessUrl);
		tepMap.put(CollectConstants.WSDL_IP, ip);
		tepMap.put(CollectConstants.WSDL_PORT, port);
		tepMap.put("METHOD_LIST", webserviceTask);

		// 计算服务耗时
		Long end = System.currentTimeMillis(); // 结束时间用于计算耗时
		String consumeTime = String.valueOf(((end - start) / 1000f));
		logger.debug("采集数据准备共耗时：" + consumeTime + "秒");

		return tepMap;
	}
	
	/**
	 * 
	 * collecting 采集数据中
	 * 
	 * @param beforeMap
	 * @param collectLogVo
	 * @throws DBException
	 * @throws IOException
	 *             void
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	protected static void collecting(Map beforeMap, CollectLogVo collectLogVo)
			throws DBException, IOException
	{
		List webserviceTask = (List) beforeMap.get("METHOD_LIST"); // 获取采集方法列表
		beforeMap.remove("METHOD_LIST");
		for (int i = 0; i < webserviceTask.size(); i++) {
			Map wsTask = (Map) webserviceTask.get(i);
			doGetData(beforeMap, wsTask, collectLogVo); // 执行采集方法
		}
	}
	
	/**
	 * 
	 * doGetData 采集数据
	 * 
	 * @param taskId
	 * @param beforeMap
	 * @param collectLogVo
	 * @throws DBException
	 * @throws IOException
	 *             void
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	protected static void doGetData(Map beforeMap,Map webserviceTask,
			CollectLogVo collectLogVo) throws DBException, IOException
	{
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Long start = System.currentTimeMillis(); // 开始时间用于计算耗时
		
		String ip = beforeMap.get(CollectConstants.WSDL_IP).toString();
		
		String port = beforeMap.get(CollectConstants.WSDL_PORT).toString();
		
		Map wsTask = webserviceTask;
		VoCollectWebserviceTask wsTaskVo = new VoCollectWebserviceTask();
		ParamUtil.mapToBean(wsTask, wsTaskVo, false);
		
		String wsTaskId = wsTaskVo.getWebservice_task_id();
		String qName = wsTaskVo.getMethod_name_en();
		String qNameCn = wsTaskVo.getMethod_name_cn();
		String tableId = wsTaskVo.getCollect_table();
		String collectMode = wsTaskVo.getCollect_mode();
		String srvCode = wsTaskVo.getService_no();
		logger.debug("数据采集方式为：" + collectMode);
		logger.debug("当前调用服务方法为：" + qName);

		// 日志ws任务
		collectLogVo.setTask_id(wsTaskId);
		collectLogVo.setService_no(wsTaskVo.getService_no());
		collectLogVo.setMethod_name_en(qName);
		collectLogVo.setMethod_name_cn(qNameCn);
		collectLogVo.setCollect_mode(collectMode);
		collectLogVo.setCollect_table(tableId);
		
		try {
			// 根据方法ID查询方法的参数
			logger.debug("开始查询服务方法参数...");
			List paramList = taskInfo.queryParam(wsTaskId);
			logger.debug("查询服务方法参数完毕...");

			logger.debug("开始格式化参数...");
			Map paramValueMap = taskInfo.fromatParamValue(paramList);
			logger.debug("valueList is " + paramValueMap);
			Map paraMap = new HashMap();
			paraMap.put(CollectConstants.QNAME, qName); // 方法名称
			paraMap.put(CollectConstants.WSDL_IP, ip); //ip
			paraMap.put(CollectConstants.WSDL_PORT, port); // 端口
			paraMap.put(CollectConstants.PARAM_LIST, paramValueMap
					.get(CollectConstants.PARAM_LIST)); // 参数名
			paraMap.put(CollectConstants.PARAM_VALUE, paramValueMap
					.get(CollectConstants.PARAM_VALUE)); // 参数值
			logger.debug("beforeMap is " + beforeMap);

			logger.debug("格式化参数完毕,参数为..." + paraMap);
			// 计算服务耗时
			Long end = System.currentTimeMillis(); // 结束时间用于计算耗时
			String consumeTime = String.valueOf(((end - start) / 1000f));
			logger.debug("此时开始调用服务方法在此之前共耗时：" + consumeTime + "秒");

			Long star2 = System.currentTimeMillis(); 
			// 调用接口
			
			// 调用接口
			SocketGSGeneralClient socketGSGeneralClient = new SocketGSGeneralClient();
			String dom = socketGSGeneralClient.colGetDom(paraMap, collectLogVo);
			System.out.println("客户端接收到服务器端返回结果为"+dom);
			
			Long end2 = System.currentTimeMillis(); 
			String consumeTime2 = String.valueOf(((end2 - star2) / 1000f));
			collectLogVo.setInvoke_consume_time(consumeTime2);

			logger.debug("开始检验获得的返回结果...");
			Map resultMap = analyzeResult(dom, collectLogVo);
			boolean flag = Boolean.valueOf(resultMap.get("status").toString());
			// 将返回结果入库入库
			if (flag) {
				Map domMap = (Map) resultMap.get("result");
				DataHandleInterface dataHandle = DataHandleFactory
						.getDataHandle();
				int count = dataHandle.insertData(tableId, collectMode, domMap,
						collectLogVo);
				collectLogVo.setCollect_data_amount(String.valueOf(count));
			} else {
				collectLogVo.setCollect_data_amount("0");
			}
			String endTime = sDateFormat.format(new java.util.Date());// 系统当前年月日时分秒
			collectLogVo.setTask_end_time(endTime);
			
			inputFile(dom, srvCode); // 生成文件
		} catch (DBException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			Long end1 = System.currentTimeMillis(); // 结束时间用于计算耗时
			String consumeTime1 = String.valueOf(((end1 - start) / 1000f));
			collectLogVo.setTask_consume_time(consumeTime1);
			logger.debug("采集数据共耗时：" + consumeTime1 + "秒");
			
			logger.debug("开始记录日志...");
			taskInfo.insertLog(collectLogVo);
			logger.debug("记录日志成功...");
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
