<%
/** Title:              content_style_addedit_for_ajax.jsp
 *  Description:
 *        在改变框架的时候，替换参数设置选项。
 *  Copyright:      www.trs.com.cn
 *  Company:        TRS Info. Ltd.
 *  Author:             Archer
 *  Created:            日期
 *  Vesion:             1.0
 *  Last EditTime:  none
 *  Update Logs:    none
 *  Parameters:     see content_style_addedit_for_ajax.xml
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
<%@ page import="com.trs.components.common.publish.widget.ContentStyle" %>
<%@ page import="com.trs.components.common.publish.widget.StyleItem" %>
<!-- WCM IMPORTS  @ END -->
<!--引用公共的登录控制页面 BEGIN-->
<%@include file="../../include/public_server.jsp"%>
<!--引用公共的登录控制页面 END-->
<%!boolean IS_DEBUG = false;%>
<%
    // 1、获取参数
    int nContentStyleId = currRequestHelper.getInt("ContentStyleId",0);
    int nPageStyleId = currRequestHelper.getInt("PageStyleId",0);
    String sTemFileName = CMyString.showNull(currRequestHelper.getString("TemFileName"),"");
    
    // 2、判断当前是编辑还是新建
    boolean bIsEdit = false;
    if(nContentStyleId>0){
        bIsEdit = true;
    }

    // 3、获取对象
    ContentStyle oContentStyle = null;
    JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
    HashMap parameters = new HashMap();
    HashMap hmResStyleItemMap = new HashMap();
    if(bIsEdit){// 编辑
        // 获取 应用模式（Id:nAppModeId） 对象
        oContentStyle = ContentStyle.findById(nContentStyleId);
        if(oContentStyle == null){
            throw new WCMException(CMyString.format(LocaleServer.getString("content_style_addedit_for_ajax.jsp.fail2get_content_style", "获取内容可用风格[Id:{0}]失败"), new int[]{nContentStyleId}));
        }
        parameters.put("ObjectId",String.valueOf(nContentStyleId));
        parameters.put("ObjectType",String.valueOf(ContentStyle.OBJ_TYPE));
        hmResStyleItemMap = (HashMap)processor.excute("wcm61_styleitem","queryStyleItemMap",parameters);
    }else{// 新建 创建新的对象
        oContentStyle = new ContentStyle();
    }
    // 4、获取  相关信息
    
    String sContentStyleName = CMyString.showNull(oContentStyle.getStyleName(),"");
    String sCssFlag = CMyString.showNull(oContentStyle.getCssFlag(),"");
    

    processor.reset();
    parameters = new HashMap();
    parameters.put("ObjectId",String.valueOf(nPageStyleId));
    String sImageUploadPath = (String)processor.excute("wcm61_pagestyle","findStyleImageDir",parameters);
    String sResStyleAddeditPage = "./content_style_addedit_1.jsp";
    out.clear();
%>


<%
    if("CustomStyle".equals(sTemFileName)){
%>
<%@include file="./content_style_addedit_customstyle.jsp"%>
<%
}
%>
<%
    if("content_template_1.css".equals(sTemFileName)){
%>
<%@include file="./content_style_addedit_1.jsp"%>
<%
}
%>
<%
    if("content_template_2.css".equals(sTemFileName)){
%>
<%@include file="./content_style_addedit_2.jsp"%>
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
	//构造字体类型
	String fontFamily[] = {"宋体", "新宋体", "楷体", "仿宋", "黑体", "隶书", "微软雅黑", "Arial", "Comic Sans MS","Courier New", "Tahoma", "Times New Roman", "Verdana"};

	private String drawFontFamilySelected(String _fontFamily) throws WCMException {
		StringBuffer sFontFamilyHtmlBuffer = new StringBuffer();
		for (int i=0; i<fontFamily.length; i++) {
			String sIsSelected=fontFamily[i].equalsIgnoreCase(_fontFamily)?"selected":"";
			sFontFamilyHtmlBuffer.append("<option value='"+fontFamily[i]+"' "+sIsSelected+">"+fontFamily[i]+"</option> ");
		}
		return sFontFamilyHtmlBuffer.toString();
	}
%>