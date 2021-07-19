<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_scm.jsp"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.trs.cms.process.engine.FlowDoc" %>
<%@ page import="com.trs.cms.process.definition.FlowNode" %>
<%@ page import="com.trs.cms.process.definition.FlowNodes" %>
<%@ page import="com.trs.scm.persistent.SCMMicroContent" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ include file="../../include/public_server.jsp"%>
<%! static final boolean IS_DEBUG = false;%>
<%
	// 2 获取参数
	int nIsPass = currRequestHelper.getInt("IsPass",0);
	int nFlowDocId = currRequestHelper.getInt("FlowDocId",0);
	String sPostDesc = currRequestHelper.getString("PostDesc");
	int nContentId = currRequestHelper.getInt("ContentId",0);
	JSPRequestProcessor oProcessor = new JSPRequestProcessor(request,response);

	if(sPostDesc.length() > 250){
		out.print("审核意见长度超过250个字，请您修改后提交！");
		return;
	}

	// 3 调用服务
	String sServiceId = "wcm6_process";
	String sMethodNameForPass = "submitTo";
	String sMethodNameForRework = "backTo";
	FlowDoc oCurrFlowDoc = FlowDoc.findById(nFlowDocId);
	FlowNodes oNextNodes = oCurrFlowDoc.getNode().getNextNodes(loginUser);
	for(int i = 0; i < oNextNodes.size(); i++){
		HashMap oHashMap = new HashMap();
		oHashMap.put("ObjectIds",String.valueOf(nFlowDocId));
		oHashMap.put("PostDesc",sPostDesc);
		oHashMap.put("ContentId",String.valueOf(nContentId));
		oHashMap.put("ContentType",String.valueOf(SCMMicroContent.OBJ_TYPE));
		oHashMap.put("NextNodeId",String.valueOf(oNextNodes.getAt(i).getId()));
		oHashMap.put("NotifyTypes","message");
		try{
			if(nIsPass == 1){
				//submitTo
				oProcessor.excute(sServiceId,sMethodNameForPass,oHashMap);
			}else{
				//backTo
				oProcessor.excute(sServiceId,sMethodNameForRework,oHashMap);
			}
		}catch(Exception e){
			String sMsg = e.getMessage();
			out.print(sMsg.substring(sMsg.indexOf("[ERR-"),sMsg.indexOf("<--")-1));
			return;
		}
		out.print(1);
	}
%>