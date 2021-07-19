<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.context.vo.VoUser"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="cn.gwssi.common.context.TxnContext"%>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<%
  HttpSession usersession = request.getSession(false);
  VoUser voUser = (VoUser) usersession.getAttribute(TxnContext.OPER_DATA_NODE); 
  String user=voUser.getOperName();
%>
<title>增加弹窗任务信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveRecord()
{
	
	saveRecord( '', '保存弹窗设置' );
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存弹窗设置' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	var startDate=getFormFieldValue("record:publish_date");
	var endDate=getFormFieldValue("record:expire_date");
	startDate=startDate.replaceAll("-","");
	endDate=endDate.replaceAll("-","");
	if(parseInt(endDate)<=parseInt(startDate)){
		alert('到期日期不能早于或等于发布日期');
		return;
	}
	saveAndExit( '', '保存弹窗设置' );	// /txn60800001.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn60800001.do
}

function formatDateToString(date){
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	month = month.toString().length == 1 ? "0" + month : month; 
	var day = date.getDate();
	day = day.toString().length == 1 ? "0" + day : day;
	
	return year + "-" + month + "-" + day;
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	// 设置publish_date默认为今天，expire_date默认为10天后
	var today = new Date;
	var expire = Date.parse(today) + 10 * 24 * 3600 * 1000;
	var publish_date = formatDateToString(today);
	var expire_date = formatDateToString(new Date(expire));
	setFormFieldValue("record:publish_date", publish_date);
	setFormFieldValue("record:expire_date", expire_date);
	setFormFieldValue("record:AUTHOR", "<%=user%>");
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="增加弹窗任务信息"/>
<freeze:errors/>
<freeze:form action="/txn60800003">
  <freeze:block property="record" caption="增加弹窗任务信息" columns="1" width="95%" captionWidth="0.25">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="sys_popwindow_id" caption="系统任务ID" datatype="string" maxlength="32" style="width:75%"/>
      <freeze:hidden property="AUTHOR" caption="发布人" datatype="string" maxlength="10" style="width:75%"/>
      <freeze:textarea property="content" caption="内容" rows="4" minlength="1" maxlength="4000" style="width:75%"/>
      <freeze:text property="PUBLISH_DEPART" caption="发布部门"   maxlength="25" style="width:75%"/>
      <freeze:hidden property="roles" caption="可查看弹窗角色" style="width:75%"/>
      <freeze:datebox property="publish_date" caption="发布日期" datatype="string" maxlength="10" style="width:75%" notnull="true"/>
      <freeze:datebox property="expire_date" caption="到期日期" datatype="string" maxlength="10" style="width:75%" notnull="true"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
