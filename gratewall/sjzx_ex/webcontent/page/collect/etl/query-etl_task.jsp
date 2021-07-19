<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="350">
<head>
<title>查询采集数据库列表</title>
</head>

<script language="javascript">

function toDetail(wid){
	var url="txn30101081.do?select-key:workflow_id="+wid;
    var page = new pageDefine( url, "查询ETL任务详细","modal","800","500");
    page.goPage();
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	var ids=document.getElementsByName("span_record:@rowid");
	var names=document.getElementsByName("span_record:workflow_desc");
	var wids=document.getElementsByName("record:workflow_id");
	var html="";
	for(var i=0;i<ids.length;i++){
		ids[i].innerHTML=(i+1);
		html='<a href="javascript:void(\'\');" onclick="toDetail('+wids[i].value+');">'+names[i].innerHTML+'</a>';
		names[i].innerHTML=html;
	}
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="ETL采集任务列表"/>
<freeze:errors/>

<freeze:form action="/txn30101080">
  <freeze:frame property="select-key" width="95%">
  </freeze:frame>

  <freeze:grid property="record" caption="查询ETL采集任务" keylist="workflow_name" width="95%" checkbox="false" rowselect="false">
      <freeze:cell property="@rowid" caption="序号" style="width:5%" align="center"/>
      <freeze:cell property="subj_desc" caption="工作对象" style="width:12%" align="center"/>
      <freeze:cell property="workflow_desc" caption="任务描述" style="width:40%" />
      <freeze:cell property="inteval" caption="任务调度周期" style="width:16%" />
      <freeze:cell property="start_time" caption="开始时间" style="width:7%" align="center"/>
      <freeze:cell property="add_type" caption="更新方式" style="width:7%" align="center"/>
      <freeze:hidden property="workflow_id" caption="任务ID" />
  </freeze:grid>
<Br/>
</freeze:form>
</freeze:body>
</freeze:html>
