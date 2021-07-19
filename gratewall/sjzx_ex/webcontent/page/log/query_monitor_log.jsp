<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@page import="cn.gwssi.common.context.TxnContext"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Calendar"%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询系统日志列表</title>
</head>
<%
	Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String start_date = df.format(calendar.getTime());
		String end_date = df.format(new Date());
		TxnContext context = (TxnContext) request
		.getAttribute("freeze-databus");
		if (context.getRecord("select-key").isEmpty()) {
			if (null == context.getRecord("select-key").getValue(
					"startTime")
					&& null == context.getRecord("select-key")
							.getValue("endTime")) {
				context.getRecord("select-key").setValue("startTime",
						start_date);
				context.getRecord("select-key").setValue("endTime",
						end_date);
			}
		}
%>
<script language="javascript">



// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	var value=getFormAllFieldValues("record:first_page_query_id");
	
	var date_s1 = document.getElementsByName("span_record:query_time");
	for(var ii=0; ii<date_s1.length; ii++){
		date_s1[ii].innerHTML = date_s1[ii].innerHTML.substr(0,10);
	}
	
}
function func_record_querycode(first_page_query_id){
    var url="txn601016.do?select-key:first_page_query_id="+first_page_query_id;
    var page = new pageDefine( url, "查询系统日志列表", "系统日志" );
	page.addRecord();
}

function check(){
	var start = document.getElementById("select-key:startTime").value;	
	var end = document.getElementById("select-key:endTime").value;
	if(start&&end){
        if(start>end){
            alert("[时间范围]结束日期必须大于开始日期！");
            document.getElementById("select-key:endTime").select();
            return false;
        }		

	}
	//else if(!end){
		//alert("输入域[时间范围]不能为空！");
		//return false;
	//}
	return true;
}
function checkForm(){
	if(!check()){
		return false;
	}
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询运行监控日志列表"/>
<freeze:errors/>
<freeze:form action="/txn6010007"  onsubmit="return checkForm();">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:browsebox property="objectId" caption="监控对象" valueset="运行监控_监控对象" show="name" data="name" style="width:95%"/>
      <%-- <freeze:browsebox property="propId"  caption="监控指标"  valueset="运行监控_监控指标" show="name" data="name" style="width:95%"/>
       --%><freeze:datebox property="startTime"  caption="时间范围" datatype="string" maxlength="30" prefix="<table width='100%' border='0' cellpadding='0' cellspacing='0'><tr><td width='45%'>" style="width:100%"/>
      </td><td width='5%'>&nbsp;至：</td><td width='45%'>
      <freeze:datebox property="endTime"  caption="时间范围" datatype="string" maxlength="30" style="width:100%" colspan="0"/>
      </td><td width='5%'></td></tr></table>
  </freeze:block>
<br/>
  <freeze:grid property="record"   checkbox="false" caption="查询系统日志列表" keylist="object_id" width="95%" navbar="bottom" >
     
      <freeze:hidden property="object_id" caption="主键" style="width:10%" />
      <freeze:cell property="@rowid" caption="序号"  align="center"  style="width:50px; " />
      <freeze:cell property="object_id" caption="监控对象" valueset="运行监控_监控对象"  align="center"  style="width:22%" />
      <%-- <freeze:cell property="index_name" caption="监控指标"  align="center"  style="width:15%" /> --%>
      <%-- <freeze:cell property="monitor_task" caption="监控任务名称"  align="center"   style="width:15%" /> --%>
      <freeze:cell property="monitor_value" caption="实际值"  align="center"    style="width:15%" />
      <freeze:cell property="threshold" caption="阀值"   align="center"     style="width:10%" />
      <freeze:cell property="monitor_msg" caption="监控信息"   align="left"     style=" " />
      <%-- <freeze:cell property="task_start_time" caption="开始时间"    align="center"        style="width: 130px;" /> --%>
      <freeze:cell property="task_end_time" caption="监控时间"    align="center" style="width:135px;" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
