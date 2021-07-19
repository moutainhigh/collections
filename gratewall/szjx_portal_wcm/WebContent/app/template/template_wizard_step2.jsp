<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.StringTokenizer" %>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.infra.util.CMyXMLElement" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.publish.template.CMyTemplateWizard" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)
	int nType = currRequestHelper.getInt("TagType", 0);

//5.权限校验

//6.业务代码
	List parameters = CMyTemplateWizard.getParameters(nType);
	if(parameters == null)
		throw new WCMException(ExceptionNumber.ERR_XMLELEMENT_NOTFOUND, CMyString.format(LocaleServer.getString("template_addedit_label_6","没有找到Type为[{0}]模板参数信息!"),new int[]{nType}));

//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="template_wizard_step2.jsp.title">TRS WCM 置标向导</TITLE>
<BASE TARGET="_self">

<style type="text/css">
	body{
		margin: 0px;
		padding: 0px;
		font-size: 12px;
		background: gray
	}
	table, td, b{
		font-size: 12px;
		font-family: arials;
	}
	.tdHead{
		background: #D8E4F8;
	}
	.tdAlter1{
		background: #ffffff;
	}
	.tdAlter2{
		background: #efefef;
	}
	.field_input{
		margin-left: 3px;
	}
	.ext-ie7 .inputselect,.ext-gecko .inputselect,.ext-strict .inputselect{
		position:relative;
		margin-left:3px;
	}
	.ext-ie7 .inputselect input,.ext-gecko .inputselect input,.ext-strict .inputselect input{
		position:absolute;
        top:0px;
        left:0px;
		z-index:2;
        width: 150px; 
        height:21px;
        border-right:0px;
		border:1px solid gray;
		border-bottom:0px;
	}
 	.ext-ie7 .inputselect select,.ext-gecko .inputselect select, .ext-strict .inputselect select{
		position:absolute;
        top:0px;
        left:0px;
		z-index:1;
		width:168px;
        height:22px;
	}
	.ext-ie6 .inputselect{
		margin-left:3px;
	}
    .ext-ie6 .inputselect input{
        width: 150px; 
        height:22px;
        border-right:0px;
		border:1px solid gray;
    }
 	.ext-ie6 .inputselect select{
		width:168px;
        height:22px;
        margin-left:-150px;
	}
	.ext-ie6 .inputselect span{
		width:18px;
	}
	html,body{margin:0px;padding:0px;}
</style>
</HEAD>

<BODY onload="init();">
<form NAME="frmAction" ID="frmAction" METHOD=get onSubmit="return false" ACTION="../template/template_wizard_step3.jsp" style="margin-top:0;display:none;">
	<input TYPE="hidden" name="TagType" value="<%=nType%>">
	<input TYPE="hidden" NAME="ObjectXML" Value="">
</form>
<form NAME="frmData" ID="frmData" onSubmit="return false" action="../template/template_wizard_step3.jsp">
	<table width="100%" border="0" cellspacing="0" cellpadding="3" align="center" style="background:#fefefe; border: 0px solid gray; height: 100%; margin-top: 0px;">
	<tr>
	<td valign="top">
		<div style="height: 235px; overflow: auto;">
		<table id="tbHead" width="100%" border="0" cellspacing="1" cellpadding="2" style="background: silver;">
		<tr>
			<td class="tdHead" height="25" align="left" width="30%"><FONT SIZE="3"><B WCMAnt:param="template_wizard_step2.jsp.paramdesc">参数描述</B></FONT></td>
			<td class="tdHead" align="left" width="50%"><FONT SIZE="3"><B WCMAnt:param="template_wizard_step2.jsp.param">参数值</B></FONT></td>
			<td class="tdHead" align="left" width="20%"><FONT SIZE="3"><B WCMAnt:param="template_wizard_step2.jsp.param_contain">包含此参数</B></FONT></td>
		</tr>
<%
	int nSize = parameters.size();
	if(nSize == 0){
%>
		<tr><td colspan=3 style="padding: 8px; background-color: #f6f6f6" WCMAnt:param="template_wizard_step2.jsp.param_fail">当前置标无参数, 请直接进行下一步</td></tr>
<%
	} else {
		CMyXMLElement aParam = null;
		for(int i=0; i<parameters.size(); i++){
			aParam = (CMyXMLElement) parameters.get(i);
			if(aParam == null) continue; 

			String sCssClz = (i%2 == 0) ? "tdAlter1" : "tdAlter2";
%>
		<tr>
			<td class="<%=sCssClz%>" align="left" valign="top" style="height: 22px; line-height: 22px;" width="30%"><%=PageViewUtil.toHtml(aParam.getProperty("PName"))%></td>
			<td class="<%=sCssClz%>" align="left" valign="top" width="50%"><%=toParamHtml(aParam)%></td>
			<td class="<%=sCssClz%>" align="left" valign="top" width="20%"><input type="checkbox" name="ckb<%=PageViewUtil.toHtml(aParam.getName())%>" _checked="<%=CMyString.showNull(aParam.getProperty("Checked"))%>"><%=PageViewUtil.toHtml(aParam.getName()).toUpperCase()%></td>
		</tr>
<%
		}
	}
%>
		</table>
		</div>
	</td>
	</tr>
	</table>
</form>
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../js/data/locale/template.js"></script>
<SCRIPT TYPE="text/javascript" src="CTRSSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
function doWithSelect(_oEl){
	if(!_oEl) return;
	var sHandleName = _oEl.getAttribute("HandleName");
	var sSelectName = _oEl.getAttribute("name");
	var sParamName = _oEl.getAttribute("ParamName");
	var sCmd = sHandleName + " = new CTRSSelect(\""+sSelectName+"\", \""+sHandleName+"\", \""+sParamName+"\");"
	sCmd += sHandleName + ".doSelectIdx(0);";
	eval(sCmd);
}
var m_oPreParameters = null;
function init(){	
	var arEls = document.getElementsByTagName("SELECT");
	if(!arEls) return;
	if(!arEls.length){
		//if(!arEls.getAttribute("ISTRSSelect")) {
			needSetChecked(arEls);
		//}else{
		//	doWithSelect(arEls);
		//}
	} else {
		for(var i=0; i<arEls.length; i++){
			var oEl = arEls[i];
			if(!oEl || !oEl.getAttribute("ISTRSSelect")) {
				var sName = oEl.name;
				var sCkbName = "ckb" + sName;
				var oCkb = document.getElementsByName(sCkbName)[0];
				var sChecked = oCkb.getAttribute('_checked', 2);
				if((oEl.value && oEl.value.length > 0)
					|| (sChecked == '1' || sChecked == 'true'))
					oCkb.checked = true;
				
				/*if(sChecked && (sChecked == '1' || sChecked.toLowerCase() == 'true')){
					oCkb.checked = true;
				}//*/
			}else{
				doWithSelect(oEl);
			}
		}
	}
	preCheckSet();

	// 如果是回退，需要重新赋值
	var sObjectXML = getParameter("ObjectXML");
	if(sObjectXML != null && sObjectXML.length>0){
		m_oPreParameters = Ext.Json.parseXml(Ext.Xml.loadXML(sObjectXML));		
	}
	if(m_oPreParameters != null){
		// 将所有的Checkbox都设置为uncheck
		var arEls = document.getElementsByTagName("INPUT");
		for(var i=0; i<arEls.length; i++){
			var oEl = arEls[i];
			if(oEl.type != "checkbox")continue;
			
			oEl.checked = false;		
		}
		
		// 将传入的值设置上
		var oProperties = m_oPreParameters.WCMOBJ.PROPERTIES;
		for(var sKey in oProperties){
			var sPropertyName = sKey.toLowerCase();
			var sCkbName = "ckb" + sPropertyName;
			var oCkb = document.getElementsByName(sCkbName)[0];
			if(!oCkb)continue;
			oCkb.checked = true;
			var oEl = null;
			if(sPropertyName=="default"){//好像default是保留字，有问题，先这样处理一下
				oEl = eval($(sPropertyName));
			}else{
				oEl = eval("frmData." + sPropertyName);
			}
			oEl.value = oProperties[sKey].NODEVALUE;
		}
	}

	if(window.top && window.top.$winzard) {
		window.top.$winzard.doAfterSteped();
	}
}

/*function doResize(_sHandleName){
	var sCmd = "if("+_sHandleName+"!=null){"+_sHandleName+".doResize();}";
	eval(sCmd);
}*/

function doChange(_sHandleName){
	var sCmd = "if("+_sHandleName+"!=null){"+_sHandleName+".doChange();}";
	eval(sCmd);
}

function needSetChecked(_oEl){
	if(!_oEl || _oEl.type != "text") return;
	var sName = _oEl.name;
	var sCkbName = "ckb"+sName;
	var oCkb = document.getElementsByName(sCkbName)[0];
	var sChecked = oCkb.getAttribute('_checked', 2);
	if((_oEl.value && _oEl.value.length > 0)
		|| (sChecked == '1' || sChecked == 'true'))
		oCkb.checked = true;
	
	/*if(sChecked && (sChecked == '1' || sChecked.toLowerCase() == 'true')){
		oCkb.checked = true;
	}//*/
}

function preCheckSet(){
	var arEls = document.getElementsByTagName("INPUT");
	if(arEls && arEls.length && arEls.length > 1){
		for(var i=0; i<arEls.length; i++){
			needSetChecked(arEls[i]);
		}
	} else {
		needSetChecked(arEls);
	}
}

function getPostData(){
	var elements = Form.getElements($('frmData'));
	var sObjXml = '<WCMOBJ><PROPERTIES>';
	for(var i = 0; i < elements.length; i++){
		var param = elements[i];
		//var bIgnore = param.getAttribute('ignore', 2);
		var sCkbName = "ckb" + param.name;
		var oCkb = document.getElementsByName(sCkbName)[0];
		if(!oCkb || !oCkb.checked)
			continue;
		if(param.name && param.tagName){
			var sName = param.name.toUpperCase();
			var sValue = $F(param);
			if(sValue != null){
				sObjXml += '\n\t<' + sName + '><![CDATA[' + sValue + ']]></' + sName + '>';
			}
		}
	}
	//BasicDataHelper('wcm6_template', 'makeTemplateCode', {}, );
	//wenyh@2007-03-02 构造XML数据错误,需要PROPERTIES节点.
	sObjXml += '</PROPERTIES></WCMOBJ>';
	return sObjXml;
	
}//*/

function doSubmit(){
	$('frmAction').ObjectXML.value = getPostData();
	$('frmAction').submit();
}
function getPageParams(){
	return 'Type=<%=nType%>';
}
</SCRIPT>
</BODY>
</HTML>

<%!
	private String toParamHtml(CMyXMLElement _aParam) throws WCMException {
		if(_aParam == null) return "";
		String sName = _aParam.getName();
		int nType = Integer.parseInt(_aParam.getProperty("PType"));
		String sValue = _aParam.getProperty("PValue");
		String sValueDesc = _aParam.getProperty("PValueDesc");
		StringBuffer sbHtml = new StringBuffer();
		StringTokenizer st;
		StringTokenizer stDesc;
		String sTemp = "";
		String sTempDesc = "";
		switch(nType){
		case CMyTemplateWizard.PARAM_TYPE_ENUMER:
			st = new StringTokenizer(sValue, ":");
			stDesc = new StringTokenizer(sValue, ":");
			if(sValueDesc != null){
				stDesc = new StringTokenizer(sValueDesc, ":");
				if(st.countTokens() != stDesc.countTokens())
					throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, LocaleServer.getString("template_addedit_label_8","枚举类型的参数值与描述数量不匹配!"));
			}
			sbHtml.append("<SELECT NAME=\"");
			sbHtml.append(sName);
			sbHtml.append("\" style=\"width:150px\" class=\"field_input\">");
			while (st.hasMoreTokens()) {
				sTemp = st.nextToken();
				sTempDesc = stDesc.nextToken();
				sbHtml.append("<option value=\"");
				sbHtml.append(PageViewUtil.toHtmlValue(sTemp));
				sbHtml.append("\">");
				sbHtml.append(PageViewUtil.toHtml(sTempDesc));
				sbHtml.append("</option>");
			}
			sbHtml.append("</SELECT>");
			break;
		case CMyTemplateWizard.PARAM_TYPE_ENUMER_EXT:
			String sSelectName = "sel" + sName;
			String sHandleName = "hdl" + sName;
			sbHtml.append("<div class='inputselect'>");
			sbHtml.append("<span><SELECT ISTRSSelect=1 NAME=\"");
			sbHtml.append(sSelectName);
			sbHtml.append("\" HandleName=\"");
			sbHtml.append(sHandleName);
			sbHtml.append("\" ParamName=\"");
			sbHtml.append(sName);
			//sbHtml.append("\" onResize=\"");
			//sbHtml.append("doResize('"+sHandleName+"');");
			sbHtml.append("\" onChange=\"");
			sbHtml.append("doChange('"+sHandleName+"');");
			sbHtml.append("\">");
			if(sValue == null) 
				throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, LocaleServer.getString("template_addedit_label_9","枚举类型的参数必须给出默认值!"));
			st = new StringTokenizer(sValue, ":");
			stDesc = new StringTokenizer(sValue, ":");
			if(sValueDesc != null){
				stDesc = new StringTokenizer(sValueDesc, ":");
				if(st.countTokens() != stDesc.countTokens())
					throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, LocaleServer.getString("template_addedit_label_8","枚举类型的参数值与描述数量不匹配!"));
			}
			while (st.hasMoreTokens()) {
				sTemp = st.nextToken();
				sTempDesc = stDesc.nextToken();
				sbHtml.append("<option value=\"");
				sbHtml.append(PageViewUtil.toHtmlValue(sTemp));
				sbHtml.append("\">");
				sbHtml.append(PageViewUtil.toHtml(sTempDesc));
				sbHtml.append("</option>");
			}
			sbHtml.append("</SELECT></span></div>");
			sbHtml.append("<SCRIPT>");
			sbHtml.append("var ");
			sbHtml.append(sHandleName);
			sbHtml.append(";</SCRIPT>");
			break;
		case CMyTemplateWizard.PARAM_TYPE_BOOLEAN:
			sbHtml.append("<SELECT NAME=\"");
			sbHtml.append(sName);
			sbHtml.append("\" class=\"field_input\" style=\"width:80px\">");
			sbHtml.append("<option value=\"FALSE\">FALSE</option>");
			sbHtml.append("<option value=\"TRUE\"");
			if(sValue != null && sValue.equals("1")){
				sbHtml.append(" selected ");
			}
			sbHtml.append(">TRUE</option>");
			sbHtml.append("</SELECT>");
			break;
		case CMyTemplateWizard.PARAM_TYPE_INT:
			sbHtml.append("<input TYPE=\"text\" class=\"field_input\" style=\"width:50px\" NAME=\"");
			sbHtml.append(sName);
			sbHtml.append("\" ");
			if(sValue != null){
				sbHtml.append(" VALUE=\"");
				sbHtml.append(sValue);
				sbHtml.append("\" ");
			}
			sbHtml.append(">");
			break;
		case CMyTemplateWizard.PARAM_TYPE_TEXT:
			sbHtml.append("<input class=\"field_input\" TYPE=\"text\" style=\"width:150px\" NAME=\"");
			sbHtml.append(sName);
			sbHtml.append("\" ");
			if(sValue != null){
				sbHtml.append(" VALUE=\"");
				sbHtml.append(sValue);
				sbHtml.append("\" ");
			}
			sbHtml.append(">");
			break;
		case CMyTemplateWizard.PARAM_TYPE_LONGTEXT:
			sbHtml.append("<TEXTAREA class=\"field_input\" ROWS=\"5\" COLS=\"25\" NAME=\"");
			sbHtml.append(sName);
			sbHtml.append("\">");
			if(sValue != null){
				sbHtml.append(sValue);
			}
			sbHtml.append("</TEXTAREA>");
			break;
		default:
			throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, CMyString.format(LocaleServer.getString("template_addedit_label_10","参数类型无效[{0}]!"),new int[]{nType}));
		}
		return sbHtml.toString();
	}
%>