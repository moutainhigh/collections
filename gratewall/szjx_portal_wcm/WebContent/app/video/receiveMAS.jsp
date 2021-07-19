
<%@page import="java.io.IOException"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.trs.webframework.controler.JSPRequestProcessor"%>
<%@page import="com.trs.cms.auth.persistent.User"%>
<%@page import="com.trs.cms.ContextHelper"%><%@ page
	contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.video.VSConfig"%>
<%@ page import="com.trs.dl.util.ConfigFileModifier"%>
<%@ page import="com.trs.components.wcm.content.persistent.Document"%>
<%@ page import="com.trs.components.wcm.content.domain.DocumentMgr"%>
<%@ page import="com.trs.DreamFactory"%>
<%@ page import="com.trs.components.video.persistent.XVideo"%>
<%@ page import="com.trs.components.video.domain.XVideoMgr"%>
<%@ page import="com.trs.components.video.domain.WCMContextHelper"%>
<%@ page import="com.trs.components.video.util.SimpleConsoleLogger"%>
<%@ page import="org.dom4j.Element"%>
<%@ page import="java.util.Properties"%>
<%@ page import="com.trs.dl.util.PropertyUtil"%>

<%
	ContextHelper.initContext(User.findById(1));
response.setHeader("x-mas",VSConfig.getVersion().getReleaseInfo());
	//获取推送过来的信息
	String string = request.getParameter("pushInfo");
	System.out.println("333333333333333" + request.getRemoteAddr());
	//获取IP，如果IP和配置的IP不同则不进行处理
	String ip = request.getRemoteAddr();
	//System.out
	//		.println("string is -----------------------------------------------------------------------------------:"
	//				+ string);
	//System.out.println("2222222222222222222");
	
	if (string != null) {
		
		Element root = SimpleConsoleLogger.parserXml(string);
		if(root.element("time")!=null ||"time".equals(root.element("time"))){
		try {
			response.getWriter().append(root.element("time").getText());
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
			return;
		}
	}

	if (!ip.trim().equals(VSConfig.getMASIP().trim())) {
		//System.out.println("dddddddddddddddddddddffffffffffffffffffffffff");
		try {
			response
					.getWriter()
					.append(
							"<pushResponse><responseType>Received _Forbidden</responseType><responseInfo>非法IP</responseInfo></pushResponse>");
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}

	if (VSConfig.getChnId() == null) {
		System.out.println("ddddddddddddddddddddddd");
		try {
			response
					.getWriter()
					.append(
							"<pushResponse><responseType>Received_Failed</responseType><responseInfo>没有设定接收栏目</responseInfo></pushResponse>");
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}

	//如果接收的信息不为空，则开始解析

	if (string != null) {
		System.out.println("33332222");
		Element root = SimpleConsoleLogger.parserXml(string);
		XVideo xvideo = new XVideo();
		Document document = new Document();
		XVideoMgr xvideoMgr = WCMContextHelper.getXVideoMgr();

		DocumentMgr m_oDocumentMgr = (DocumentMgr) DreamFactory
				.createObjectById("DocumentMgr");

		JSPRequestProcessor processor = new JSPRequestProcessor(
				request, response);
		HashMap parameters = new HashMap();
		parameters.put("objectid", "0");

		String value = String.valueOf(VSConfig.getChnId());
		parameters.put("channelid", value);
		//
		parameters.put("docTitle", root.element("sorVideoName")
				.getText());
		parameters.put("docContent", "111");
		Integer nDocId = (Integer) processor.excute("wcm6_document",
				"save", parameters);
		document = Document.findById(nDocId.intValue());
		//document.setAttribute("parentvideo");
		m_oDocumentMgr.save(document);

		System.out.println("33332222");
		xvideo.setDocId(document.getId());
		System.out.println("DOCID:" + document.getId());
		//todo
		xvideo.setSrcFileName(root.element("sorVideoName").getText());
		xvideo.setThumb(root.element("thumbSubPath").getText());

		xvideo.setDuration(Integer.parseInt(root.element("duration")
				.getText()));

		xvideo.setWidth(Integer.parseInt(root.element("width")
				.getText()));

		xvideo.setHeight(Integer.parseInt(root.element("height")
				.getText()));
		xvideo.setFPS(Integer.parseInt(root.element("fps").getText()));
		xvideo.setBitrate(Integer.parseInt(root.element("bitrate")
				.getText()));
		xvideo.setCreateType(50);
		VSConfig
				.setThumbRootUrl(root.element("thumbRootUrl").getText());
		VSConfig
				.setVideoRootUrl(root.element("videoRootUrl").getText()); //需要更改
		xvideo.setFileName(root.element("videoSubPath").getText());

		xvideo.setConvertStatus(XVideo.CONVERT_SUCCESS);
		xvideo.setQuality(XVideo.QUALITY_LOW);
		xvideoMgr.insert(xvideo);
		ContextHelper.clear();
		try {
			response
					.getWriter()
					.append(
							"<pushResponse><responseType>Received_Successful</responseType><responseInfo></responseInfo></pushResponse>");
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} else {
		try {
			response
					.getWriter()
					.append(
							"<pushResponse><responseType>Received_Failed</responseType><responseInfo>save failed</responseInfo></pushResponse>");
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
%>