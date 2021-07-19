package com.gwssi.common.delivery.remote.ems.invoke;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;



import com.gwssi.common.delivery.remote.ems.domain.MessageHeaderRequest;
import com.gwssi.common.delivery.remote.ems.domain.MessageRequest;
import com.gwssi.common.delivery.remote.ems.domain.MessageResponse;
import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.DAOManager;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.torch.util.StringUtil;

/**
 * EMS 远程接口调用工具类。
 * 
 * 发送报文前：构造报文  压缩  加密  签名
 * 接收报文后：校验签名  解密  解压缩  解析报文
 * 
 * @author 海龙
 */
public class EmsInvoker {

	protected final static Logger logger = Logger.getLogger(EmsInvoker.class);

	public static final String VERSION ;
	
	
	
	
	static{
		Properties props = ConfigManager.getProperties("ems");
		//REMOTE_URL = props.getProperty("ems.remoteUrl");
		//SYS_CODE = props.getProperty("ems.sysCode");
		//USERNAME = props.getProperty("ems.username");
		//PASSWORD = props.getProperty("ems.password");
		VERSION  = props.getProperty("ems.version"); 
	}
	/**
	 * EMS 远程接口调用。
	 * 
	 * 发送报文前：构造报文  压缩  加密  签名
	 * 接收报文后：校验签名  解密  解压缩  解析报文
	 * 
	 * @param request
	 * @return
	 * @throws OptimusException 
	 * @throws IOException 
	 */
	@SuppressWarnings("rawtypes")
	public static MessageResponse invoke(MessageRequest request,String csdm) throws OptimusException{
		String REMOTE_URL="";
		String USERNAME="";
		String PASSWORD=""; 
		String SYS_CODE="";
		MessageResponse response = null;
		try{
			MessageHeaderRequest head = request.getRequestHeader();
			
			IPersistenceDAO dao = DAOManager.getPersistenceDAO();
			if(!StringUtils.isBlank(csdm)){
				String sql = "select * from ems_config c where c.csdm=?";
				List<String> param = new ArrayList<String>();
				param.add(csdm);
				List<Map> configList = dao.queryForList(sql, param);
				if(configList!=null && configList.size()>0){
					Map config = configList.get(0);
					REMOTE_URL = StringUtil.safe2String(config.get("remoteUrl")).trim();
					USERNAME   = StringUtil.safe2String(config.get("username")).trim();
					PASSWORD   = StringUtil.safe2String(config.get("password")).trim();
					SYS_CODE   = StringUtil.safe2String(config.get("sysCode")).trim();
				}else{
					throw new Exception("未找到该地区EMS配置信息。");
				}
			}else{
				throw new Exception("未找到行政区划。");
			}
			head.setSYS_CODE(SYS_CODE);
			head.setUSERNAME(USERNAME);
			head.setPASSWORD(PASSWORD);	
			head.setVERSION(VERSION);
			
			String requestMsgString = request.toJSonString();
			if(logger.isDebugEnabled()){
				logger.debug("请求:"+requestMsgString);
			}
			
			//String respText = ClientUtils.getFileText("statusData.json");
			String respText = remoteInvoke(requestMsgString,REMOTE_URL);
			
			response = new MessageResponse(respText);
			
			if(logger.isDebugEnabled()){
				logger.debug("MessageResponse:"+response.toJSonString());
			}
		}catch(Exception e){
			throw new OptimusException("调用EMS接口出错。",e);
		}
		return response;
	}
	/**
	 * 
	 * 输出和输出都是字符串，需要自行构建请求并解析响应结果。
	 * 
	 * @param requestMsgString
	 * @return
	 * @throws OptimusException
	 */
	public static String remoteInvoke(String requestMsgString,String REMOTE_URL) throws OptimusException{
		
		if(logger.isInfoEnabled()){
			logger.info(String.format("请求：“%s”",requestMsgString));
		}
		
		if(StringUtils.isEmpty(requestMsgString)){
			return "";
		}
		// 1 发送请求
		// 1.1 压缩
		String respText = "";
		try {
			byte[] data = EmsUtils.compress(requestMsgString);
		
			// 1.2 加密
			data = EmsUtils.encrypt(data);
			// 1.3 签名
			String sign = EmsUtils.sign(data);
			
			// 2 调用
			byte[] respBytes = remoteInvoke(REMOTE_URL,sign,data);
			
			// 3 处理返回结果
			// 3.1 解密
			data = EmsUtils.encrypt(respBytes);
			// 3.2 解压缩
			respText = EmsUtils.uncompress(data);
		} catch (IOException e) {
			throw new OptimusException(e.getMessage(),e);
		}
		if(logger.isInfoEnabled()){
			logger.info(String.format("响应：“%s”",respText));
		}
		return respText;
	}
	/**
	 * 执行远程调用。
	 * 底层接口，不能直接使用。
	 * @param sign
	 * @param data
	 * @return
	 * @throws OptimusException 
	 * @throws IOException
	 */
	protected static byte[] remoteInvoke(String REMOTE_URL,String sign,byte[] data) throws OptimusException {
		byte[] ret = null;
		PostMethod filePost = null;
		try{
			String targetURL = REMOTE_URL + sign;
			logger.debug("REMOTE_URL:"+targetURL);
			filePost = new PostMethod(targetURL);
			//增加http请求输入流
			filePost.setRequestEntity(new ByteArrayRequestEntity(data, "text/plain; charset=utf-8"));
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
			int status = client.executeMethod(filePost);
			if (status == HttpStatus.SC_OK) {
				// 响应回文数组
				ret = filePost.getResponseBody();
			}else{
				logger.debug("请求出错，状态："+status+","+filePost.getStatusText());
				logger.debug("请求出错，响应："+filePost.getResponseBodyAsString());
				throw new OptimusException("调用EMS接口失败，"+filePost.getStatusText());
			}
		}catch(IOException e){
			throw new OptimusException("调用EMS接口失败，"+e.getMessage(),e);
		}finally{
			if(filePost!=null){
				filePost.releaseConnection();
			}
		}
		if(logger.isDebugEnabled()){
			//logger.debug(String.format("请求：“%s”", EmsUtils.toString(data)));
			//logger.debug(String.format("响应：“%s”", EmsUtils.toString(ret)));
		}
		return ret;
	}
	
}
