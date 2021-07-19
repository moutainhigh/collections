<%@page import="cn.gwssi.common.context.DataBus"%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="cn.gwssi.common.context.TxnContext"%>
<%@ page import="cn.gwssi.common.context.vo.VoUser"%>
<freeze:html width="650" height="350">
<head>
<title>查询etl任务列表</title>
<%
DataBus context = (DataBus) request.getAttribute("freeze-databus");
String txnList=context.getRecord("oper-data").getValue("txnList");

%>
</head>

<script language="javascript">

// 增加etl任务
function func_record_addRecord()
{
	var page = new pageDefine( "insert-etl_subject.jsp", "增加etl任务", "modal" );
	page.addRecord();
}

// 修改etl任务
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn30300004.do", "修改etl任务", "modal" );
	page.addParameter( "record:etl_id", "primary-key:etl_id" );
	page.updateRecord();
}

// 删除etl任务
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn30300005.do", "删除etl任务" );
	page.addParameter( "record:etl_id", "primary-key:etl_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

function toDetail(wid){
	var url="txn30300006.do?select-key:workflow_id="+wid;
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
		html='<a href="javascript:void(\'\');" onclick="toDetail('+wids[i].value+');">'+names[i].innerHTML+'</a>';
		names[i].innerHTML=html;
		ids[i].innerHTML=(i+1);
		
	}
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询etl任务列表"/>
<freeze:errors/>

<freeze:form action="/txn30300001">
  <freeze:frame property="select-key" width="95%">
  </freeze:frame>


<%
 if(txnList.indexOf("30300003")!=-1){
%>
  <freeze:grid property="record" caption="查询etl任务列表" keylist="etl_id" width="95%"  fixrow="true">
      <freeze:button name="record_addRecord" caption="增加etl任务" txncode="30300003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改" txncode="30300004" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>&nsbp;&nsbp;&nsbp;&nsbp;
      <freeze:button name="record_deleteRecord" caption="删除" txncode="30300005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:hidden property="etl_id" caption="主键ID" style="width:5%" />
      <freeze:cell property="@rowid" caption="序号" style="width:5%" align="center"/>
      <freeze:cell property="subj_desc" caption="工作包描述" style="width:10%" />
      <freeze:cell property="workflow_desc" caption="任务描述" style="width:15%" />
      <freeze:cell property="workflow_remark" caption="任务备注" style="width:25%" />
      <freeze:cell property="inteval" caption="运行周期" style="width:15%" />
      <freeze:cell property="start_time" caption="开始执行时间" style="width:8%" align="center"/>
      <freeze:cell property="add_type" caption="数据处理类型" valueset="资源管理_采集方式" style="width:8%" align="center"/>
      <freeze:hidden property="workflow_id" caption="任务ID" style="width:10%" />
      <freeze:hidden property="workflow_name" caption="任务名称" />
  </freeze:grid>
<%}else{ %>

   <freeze:grid property="record" caption="查询etl任务列表" keylist="etl_id" width="95%" checkbox="false" rowselect="false" >
      <freeze:button name="record_addRecord" caption="增加etl任务" txncode="30300003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改" txncode="30300004" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>&nsbp;&nsbp;&nsbp;&nsbp;
      <freeze:button name="record_deleteRecord" caption="删除" txncode="30300005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:hidden property="etl_id" caption="主键ID" style="width:5%" />
      <freeze:cell property="@rowid" caption="序号" style="width:5%" align="center"/>
      <freeze:cell property="subj_desc" caption="工作包描述" style="width:10%" />
      <freeze:cell property="workflow_desc" caption="任务描述" style="width:15%" />
      <freeze:cell property="workflow_remark" caption="任务备注" style="width:25%" />
      <freeze:cell property="inteval" caption="运行周期" style="width:15%" />
      <freeze:cell property="start_time" caption="开始执行时间" style="width:8%" align="center"/>
      <freeze:cell property="add_type" caption="数据处理类型" valueset="资源管理_采集方式" style="width:8%" align="center"/>
      <freeze:hidden property="workflow_id" caption="任务ID" style="width:10%" />
      <freeze:hidden property="workflow_name" caption="任务名称" />
  </freeze:grid>

<%} %>

</freeze:form>
</freeze:body>
</freeze:html>
