//package com.gwssi.rodimus.protocolstack;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.util.Enumeration;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.ServletInputStream;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.lang.StringUtils;
//
//import com.gwssi.rodimus.exception.RodimusException;
//import com.gwssi.rodimus.protocolstack.config.ProtocolListManager;
//import com.gwssi.rodimus.protocolstack.core.Protocol;
//import com.gwssi.rodimus.protocolstack.core.ProtocolStack;
//import com.gwssi.rodimus.protocolstack.core.Request;
//import com.gwssi.rodimus.protocolstack.core.Response;
//import com.gwssi.rodimus.util.StringUtil;
//
///**
// * 
// * @author liuhailong(liuhailong2008#foxmail.com)
// */
//public class ProtocolStackUtil {
//	/**
//	 * 执行 Servlet 请求。
//	 * @param httpRequest
//	 * @param httpResponse
//	 */
//	public static Response servletRun(HttpServletRequest httpRequest,HttpServletResponse httpResponse){
//		// 1 获得协议栈名称
//		// 1.1 先从header里找
//		String protocolStackName = httpRequest.getHeader(ProtocolStack.INPUT_PARAMS_NAME_PROTOCOL_STACK);
//		// 1.2 如果没有，再从 parameter 里找
//		if(StringUtil.isBlank(protocolStackName)){
//			protocolStackName = httpRequest.getParameter(ProtocolStack.INPUT_PARAMS_NAME_PROTOCOL_STACK);
//		}
//		// 1.3 如果没有，再从 attribute里找
//		if(StringUtil.isBlank(protocolStackName)){
//			protocolStackName = StringUtil.safe2String(httpRequest.getAttribute(ProtocolStack.INPUT_PARAMS_NAME_PROTOCOL_STACK));
//		}
//		// 1.4 如果还没找到，只能报错了
//		if(StringUtil.isBlank(protocolStackName)){
//			throw new RodimusException("协议栈名称为空。");
//		}
//		// 2 准备参数
//		// 2.1 准备header
//		Enumeration<String> it = httpRequest.getHeaderNames();
//		String headerName = "" , headerValue = "";
//		Map<String,String> header = new HashMap<String,String>();
//		while(it.hasMoreElements()){
//			headerName = it.nextElement();
//			if(StringUtil.isBlank(headerName)){
//				continue ;
//			}
//			headerValue = httpRequest.getHeader(headerName);
//			if(StringUtil.isBlank(headerValue)){
//				continue ;
//			}
//			header.put(headerName, headerValue);
//		}
//		// 2.2 准备body
//		//byte[] body = getServletRequestBody(httpRequest);
//		String body = getServletRequestBodyStr(httpRequest);
//		// 3 运行协议栈
//		Response response = run(protocolStackName,header, body);
//		// 4 返回结果
//		return response;
//	}
//	
//	protected static String getServletRequestBodyStr(HttpServletRequest request) {
//		String requsetBody = request.getParameter("r");
//		if(StringUtils.isBlank(requsetBody)){
//			throw new RodimusException("请求体为空。");
//		}
//		return requsetBody;
//	}
//
//	
//	/**
//	 * 从http servlet请求中读取二进制请求体。
//	 * @param request
//	 * @return
//	 */
//	protected static byte[] getServletRequestBodyByte(HttpServletRequest request)  {
//		byte[] ret = null;
//		ByteArrayOutputStream baos = null;
//		ServletInputStream sis = null;
//		try{
//	        final int BUFFER_SIZE = 8 * 1024;
//	        byte[] buffer = new byte[ BUFFER_SIZE];
//	        sis = request.getInputStream();
//	        baos = new ByteArrayOutputStream();
//	        int bLen=0;  
//			while((bLen= sis.read(buffer))>0){      
//				baos.write( buffer, 0, bLen);
//			} 
//			
//	        ret = baos.toByteArray();
//	        
//		}catch(IOException e){
//			return null;
//			//throw new RodimusException("从请求中读取数据时出错。");
//		}finally{
//			if(sis!=null){
//				try {
//					sis.close();
//				} catch (IOException e) {
//					return null;
//					//throw new RodimusException("关闭输入流时发生错误："+e.getMessage());
//				}
//			}
//			if(baos!=null){
//				try {
//					baos.close();
//				} catch (IOException e) {
//					return null;
//					//throw new RodimusException("保存输入流时发生错误："+e.getMessage());
//				}
//			}
//		}
//        return ret;
//    }
//	/**
//	 * 执行协议栈。
//	 * 
//	 * @param request
//	 * @return
//	 */
//	public static Response run(String protocolStackName,Map<String,String> header, String body){
////		try{
//		// 1 准备参数
//		// 1.1 准备待执行的协议列表
//		if(StringUtil.isBlank(protocolStackName)){
//			throw new RodimusException("协议栈名称为空。");
//		}
//		List<Protocol> protocols = ProtocolListManager.instance.getConfig(protocolStackName);
//		if(protocols==null || protocols.isEmpty()){
//			throw new RodimusException("协议栈名称不正确。");
//		}
//		
//		// 2. 创建本次请求的变量
//		ProtocolStack protocolStack = new ProtocolStack(protocols);
//		Request request = new Request(header,body);
//		
//		// 3. 执行入栈操作
//		protocolStack.inWay(request);
//		// 4. 执行出栈操作
//		protocolStack.outWay(request);
//		// 5. 返回
//		Response ret = new Response(request);
//		return ret;
////		}catch(Throwable e){
////			// 5. 处理异常消息返回
////			return new Response(e);
////		}
//	}
//
//}
