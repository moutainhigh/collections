<%
/** Title:				resource_style_addedit_for_ajax.jsp
 *  Description:
 *        在改变框架的时候，替换参数设置选项。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:				Archer
 *  Created:			日期
 *  Vesion:				1.0
 *  Last EditTime:	none
 *  Update Logs:	none
 *  Parameters:		see resource_style_addedit_for_ajax.xml
 */
%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../error_for_dialog.jsp"%>
<!-- WCM IMPORTS  @ BEGIN -->
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.components.common.publish.widget.ResourceStyle" %>
<%@ page import="com.trs.components.common.publish.widget.StyleItem" %>
<!-- WCM IMPORTS  @ END -->
<!--引用公共的登录控制页面 BEGIN-->
<%@include file="../../include/public_server.jsp"%>
<!--引用公共的登录控制页面 END-->
<%!boolean IS_DEBUG = false;%>
<%
	// 1、获取参数
	int nResourceStyleId = currRequestHelper.getInt("ResourceStyleId",0);
	int nPageStyleId = currRequestHelper.getInt("PageStyleId",0);
	String sTemFileName = CMyString.showNull(currRequestHelper.getString("TemFileName"),"");
	
	// 2、判断当前是编辑还是新建
	boolean bIsEdit = false;
	if(nResourceStyleId>0){
		bIsEdit = true;
	}

	// 3、获取对象
	ResourceStyle oResourceStyle = null;
	JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
	HashMap parameters = new HashMap();
	HashMap hmResStyleItemMap = new HashMap();
	if(bIsEdit){// 编辑
		// 获取 应用模式（Id:nAppModeId） 对象
		parameters.put("ObjectId",String.valueOf(nResourceStyleId));
		oResourceStyle = (ResourceStyle)processor.excute("wcm61_resourcestyle","findById",parameters);
		if(oResourceStyle == null){
			throw new WCMException(CMyString.format(LocaleServer.getString("resource_style_addedit_for_ajax.jsp.getresourcetypefailed","获取资源类型[Id:{0}]失败"),new int[]{nResourceStyleId}));
		}
		processor.reset();
		parameters = new HashMap();
		parameters.put("ObjectId",String.valueOf(nResourceStyleId));
		parameters.put("ObjectType",String.valueOf(ResourceStyle.OBJ_TYPE));
		hmResStyleItemMap = (HashMap)processor.excute("wcm61_styleitem","queryStyleItemMap",parameters);
	}else{// 新建 创建新的对象
		oResourceStyle = new ResourceStyle();
	}
	// 4、获取  相关信息
	
	String sResourceStyleName = CMyString.showNull(oResourceStyle.getStyleName(),"");
	String sCssFlag = CMyString.showNull(oResourceStyle.getCssFlag(),"");
	

	processor.reset();
	parameters = new HashMap();
	parameters.put("ObjectId",String.valueOf(nPageStyleId));
	String sImageUploadPath = (String)processor.excute("wcm61_pagestyle","findStyleImageDir",parameters);
	String sResStyleAddeditPage = "./resource_style_addedit_1.jsp";
	out.clear();
%>
<%
	if("resource_template_1.css".equals(sTemFileName)){
%>
<%@include file="./resource_style_addedit_1.jsp"%>
<%
}
%>
<%
if("resource_template_2.css".equals(sTemFileName)){
%>
<%@include file="./resource_style_addedit_2.jsp"%>
<%
}
%>
<%
if("resource_template_3.css".equals(sTemFileName)){
%>
<%@include file="./resource_style_addedit_3.jsp"%>
<%
}
%>
<%
if("resource_template_4.css".equals(sTemFileName)){
%>
<%@include file="./resource_style_addedit_4.jsp"%>
<%
}
%>
<%!
	String sOptions = "1px=1px~2px=2px~3px=3px~4px=4px~5px=5px~6px=6px";
	public String drawBorderWidthSelector(String _sSelectDomId,String _sSelectedValue,String _sOptions)throws WCMException {
		String sItemSplit = "~";//每一项分割符
		String sTextValueSplit = "=";//某一项中的key=value分割符
		String[] sItem = null;
		StringBuffer sDomHtmlBuffer = new StringBuffer();
		sDomHtmlBuffer.append("<input type='text' class='inputSelect_input' ");
		sDomHtmlBuffer.append("id='input_"+_sSelectDomId+"' ");
		sDomHtmlBuffer.append("name='"+_sSelectDomId+"' ");
		sDomHtmlBuffer.append("value='"+_sSelectedValue+"' ");
		
		sDomHtmlBuffer.append("isInputable='true' ParamType='StyleItem'/>");

		sDomHtmlBuffer.append("<span class='inputSelect_span'>");
		sDomHtmlBuffer.append("<select id='select_"+_sSelectDomId+"' class='inputSelect_select' onchange=document.getElementById('input_" + _sSelectDomId + "').value=this.value>");
		sItem = _sOptions.split(sItemSplit);
		for(int i=0;i<sItem.length;i++){
			String[] sItemContent = sItem[i].split(sTextValueSplit);
			String sItemName = sItemContent[0];
			String sItemValue = sItemContent[0];
			if(sItemContent.length>1){
				sItemValue = sItemContent[1];
			}
			if("".equals(sItemValue)) continue;
			String sIsSelected=sItemValue.equals(_sSelectedValue.trim())?"selected":"";
			sDomHtmlBuffer.append("<option id='option_"+i+"' value='"+sItemName+"' _value='"+sItemValue+"'"+sIsSelected+">"+sItemName+"</option> ");
		}

		sDomHtmlBuffer.append("</select>");
		sDomHtmlBuffer.append("</span>");
		sDomHtmlBuffer.append("<span id='msg_"+_sSelectDomId+"'></span>");
		return sDomHtmlBuffer.toString();
	}

	// 获取样式值
	private String getStyleItemValue(HashMap _pageStyleItemsMap,String _sName){
		if(_sName==null || _pageStyleItemsMap==null){
			return "";
		}
		
		StyleItem aStyleItem = (StyleItem)_pageStyleItemsMap.get(_sName);
		if(aStyleItem==null){
			return null;
		}
		return aStyleItem.getClassValue();
	}
%>