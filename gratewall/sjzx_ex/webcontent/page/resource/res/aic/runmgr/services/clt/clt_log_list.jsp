<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>采集日志列表</title>
<style type="text/css">
.activerow {
	cursor:auto;
}
</style>
</head>

<script language="javascript">

// 修改服务对象
function func_record_updateRecord(index)
{
	var page = new pageDefine( "/txn50212002.do", "详细日志" );
	page.addValue( getFormFieldValue("record:sys_clt_log_id",index), "select-key:sys_clt_log_id" );
	page.goPage();
}


// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
    var state = getFormAllFieldValues("record:state"); 
    var operationSpan = document.getElementsByName("span_record:oper");
	for (var i=0; i < operationSpan.length; i++){
	    if(state[i]=="1"){
	    	operationSpan[i].innerHTML += "<a onclick='func_record_updateRecord(" + i + ");' href='#'>查看日志</a>";
	    }
	}
}


_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="采集日志列表"/>
<freeze:errors/>

<freeze:form action="/txn50212001">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:select property="sys_clt_user_id" caption="采集来源"  valueset="采集来源代码" show="name" data="value" style="width:60%"/> 
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="采集日志列表" keylist="sys_clt_log_id" width="95%" navbar="bottom" checkbox="false" multiselect="false" fixrow="false" rowselect="false">
      <freeze:button name="record_updateRecord" caption="查看日志"  enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();" visible="false"/>
      <freeze:hidden property="sys_clt_log_id" caption="采集策略编号"/>
      <freeze:hidden property="state" caption="状态"/>
      <freeze:cell property="@rowid" caption="序号" style="width:6%" align="center"/>
      <freeze:cell property="sys_clt_user_id" caption="采集来源" valueset="采集来源代码" style="width:10%" align="left"/>
      <freeze:cell property="clt_startdate" caption="采集开始日期" style="width:10%" align="left" />
      <freeze:cell property="clt_enddate" caption="采集结束日期" style="width:10%" align="left" />
      <freeze:cell property="exc_starttime" caption="执行开始时间" style="width:15%" align="left" />
      <freeze:cell property="exc_endtime" caption="执行结束时间" style="width:15%" align="left" />      
      <freeze:cell property="state" caption="状态" style="width:8%" align="left" valueset="采集执行状态" />
      <freeze:cell property="logdesc" caption="错误信息" style="width:18%" align="left" />
      <freeze:cell property="oper" caption="操作" style="width:8%" align="center" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
