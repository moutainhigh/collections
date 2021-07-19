<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>采集策略列表</title>
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
	var page = new pageDefine( "/txn50211002.do", "策略配置", "modal", 600,400 );
	page.addValue( getFormFieldValue("record:sys_clt_user_id",index), "record:sys_clt_user_id" );
	page.updateRecord();
}


// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	var stateTds = document.getElementsByName("span_record:state");
	var states = getFormAllFieldValues("record:state");
    var operationSpan = document.getElementsByName("span_record:oper");
	for (var i=0; i < operationSpan.length; i++){
		if(states[i] == '1'){
			stateTds[i].style.textAlign = 'right';
		}
	    operationSpan[i].innerHTML += "<a onclick='func_record_updateRecord(" + i + ");' href='#'>配置</a>";
	}
}


_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="采集策略列表"/>
<freeze:errors/>

<freeze:form action="/txn50211001">

  <freeze:grid property="record" caption="采集策略列表" keylist="sys_clt_user_id" width="95%" navbar="bottom" checkbox="false" multiselect="false" fixrow="false" rowselect="false">
      <freeze:button name="record_updateRecord" caption="配置"  enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();" visible="false"/>
      <freeze:hidden property="sys_clt_user_id" caption="采集策略编号"/>
      <freeze:hidden property="state" caption="状态"/>
      <freeze:cell property="@rowid" caption="序号" style="width:6%" align="center"/>
      <freeze:cell property="name" caption="采集来源" style="width:20%" align="left"/>
      <freeze:cell property="user_type" caption="用户类型" style="width:10%" align="left" valueset="user_type"/>
      <freeze:cell property="strategy" caption="采集周期" style="width:8%" align="left" valueset="时间周期" />
      <freeze:cell property="strategytime" caption="采集时间" style="width:20%" align="left" />
      <freeze:cell property="state" caption="状态" style="width:8%" align="left" valueset="syzt" />
      <freeze:cell property="startdate" caption="开始日期" style="width:12%" align="left" />
      <freeze:cell property="enddate" caption="结束日期" style="width:12%" align="left" />
      <freeze:cell property="oper" caption="操作" style="width:4%" align="center" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
