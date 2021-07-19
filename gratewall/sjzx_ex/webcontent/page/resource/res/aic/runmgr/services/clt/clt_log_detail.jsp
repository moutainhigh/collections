<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>采集日志详细</title>
<style type="text/css">
#navbar{
	display:none;
}
</style>
</head>

<script language="javascript">

// 修改服务对象
function func_record_updateRecord()
{

}

function func_record_goBack()
{
	goBack();
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
}


_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="采集日志详细"/>
<freeze:errors/>

<freeze:form action="/txn50212002">
  <freeze:grid property="record" caption="采集日志详细" keylist="sys_clt_log_detail_id" width="95%" navbar="bottom" checkbox="false" fixrow="false">
      <freeze:cell property="@rowid" caption="序号" style="width:6%" align="center"/>
      <freeze:cell property="inf_name" caption="接口名称" style="width:24%" align="center" />
      <freeze:cell property="inf_desc" caption="接口描述" style="width:50%" align="center" />
      <freeze:cell property="clt_num" caption="采集数据量(条)" style="width:20%" align="center" />
  </freeze:grid>
  <freeze:block property="record" width="95%" border="0">
    <freeze:button name="record_goBack" caption="返 回" onclick="func_record_goBack();"/>    
  </freeze:block>
</freeze:form>
</freeze:body>
</freeze:html>
