<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.context.vo.VoUser"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="cn.gwssi.common.context.TxnContext"%>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>增加意见反馈信息</title>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<style type="text/css">
.odd1_b {
	white-space: nowrap;
}
</style>
</head>
<%
  HttpSession usersession = request.getSession(false);
  VoUser voUser = (VoUser) usersession.getAttribute(TxnContext.OPER_DATA_NODE); 
  String user=voUser.getOperName();
  String ip = voUser.getValue("ipaddress");
  String orgName = voUser.getValue("org-name");
%>
<script language="javascript">
function formatDateToString(date){
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	month = month.toString().length == 1 ? "0" + month : month; 
	var day = date.getDate();
	day = day.toString().length == 1 ? "0" + day : day;
	
	var hour = date.getHours();
	hour = hour.toString().length == 1 ? "0" + hour : hour; 
	var minute = date.getMinutes();
	minute = minute.toString().length == 1 ? "0" + minute : minute; 
	var second = date.getSeconds();
	second = second.toString().length == 1 ? "0" + second : second; 
	
	return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
}
function getBrowser(){
	var browser = "other";
	if($.browser.msie) {
   		browser = "Internet Explorer ";
	}
	if($.browser.safari) {
   		browser = "safari ";
	}
	if($.browser.opera) {
   		browser = "opera ";
	}
	if($.browser.mozilla) {
   		browser = "mozilla ";
	}
	return browser + $.browser.version;
}

// 保 存
function func_record_saveRecord()
{
	saveRecord( '', '保存意见反馈' );
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存意见反馈' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存意见反馈' );	// /txn711001.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn711001.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{	
    var today = new Date;
    var publish_date = "2013-01-01 09:30:00";
	publish_date = formatDateToString(today);
	var dateArray = publish_date.split(" ")
	var browserInfo = getBrowser();
	setFormFieldValue("record:publish_date", dateArray[0]);
	setFormFieldValue("record:publish_time", dateArray[1]);
	setFormFieldValue("record:browser", browserInfo);
	setFormFieldValue("record:author", "<%=user%>");
	setFormFieldValue("record:ip", "<%=ip%>");
	setFormFieldValue("record:yhjg", "<%=orgName%>");
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="增加意见反馈信息"/>
<freeze:errors/>

<freeze:form action="/txn711003">
  <freeze:block property="record" caption="增加意见反馈信息" width="95%">
     <freeze:button name="record_saveAndExit" caption="保存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="sys_feedback_id" caption="意见反馈ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:text property="title" caption="标题" colspan="2" datatype="string" maxlength="100" style="width:45%"/>
      <freeze:textarea property="content" caption="意见反馈内容" colspan="2" rows="4" maxlength="4000" style="width:95%"/>
      <freeze:cell caption="联系电话" style="width:45%;" colspan="2" value="010-82690717" />
      <freeze:hidden property="publish_date" caption="发布日期" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:hidden property="author" caption="发布人" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden property="status" caption="有效标志" value="1" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="description" caption="处理结果" datatype="string" maxlength="200" style="width:95%"/>
      <freeze:hidden property="yhjg" caption="用户机构" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden property="ip" caption="用户ip" datatype="string" maxlength="128" style="width:95%"/>
      <freeze:hidden property="browser" caption="浏览器信息" datatype="string" maxlength="100" style="width:95%"/>
	  <freeze:hidden property="publish_time" caption="发布时间" datatype="string" maxlength="20" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
