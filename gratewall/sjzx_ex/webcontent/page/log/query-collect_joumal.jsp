<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询采集日志列表</title>
</head>

<script language="javascript">


// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
	var value=getFormAllFieldValues("record:collect_joumal_id");
	
	for(var i=0; i<value.length; i++){
		var val=value[i];
	
	document.getElementsByName("span_record:oper")[i].innerHTML='<a href="javascript:func_record_querycode(\''+val+'\');" title="" ><div class="detail"></div></a>';
	
    }
    /*
	var date_s = document.getElementsByName("span_record:task_start_time");
	for(var ii=0; ii<date_s.length; ii++){
		date_s[ii].innerHTML = date_s[ii].innerHTML.substr(0,10);
	}
	var date_s1 = document.getElementsByName("span_record:task_end_time");
	for(var ii=0; ii<date_s1.length; ii++){
		date_s1[ii].innerHTML = date_s1[ii].innerHTML.substr(0,10);
	}
	*/
}

function func_record_querycode(collect_joumal_id){
    var url="txn6011006.do?select-key:collect_joumal_id="+collect_joumal_id;
    var page = new pageDefine( url, "查询采集日志列表","modal", 750, 300);
    page.goPage();
}

function check(){
		var start = document.getElementById("select-key:created_time_start").value;	
		var end = document.getElementById("select-key:created_time_end").value;
		if(start&&end){
            if(start>end){
                alert("【时间范围】结束日期必须大于开始日期！");
                document.getElementById("select-key:created_time_end").select();
                return false;
            }		

		}
		return true;
}
function submitForm(){
	if(!check()){
		return false;
	}
}

// 归档采集日志
function func_record_addRecord()
{
	var page = new pageDefine( "update-collect_archive.jsp", "采集日志归档","modal");
	page.addRecord();
}

_browse.execute( '__userInitPage()' );
</script>
<!-- <freeze:button name="record_addRecord" caption="归档"  enablerule="0"  align="right" onclick=""/>
 <freeze:select property="return_codes" caption="执行状态"  valueset="日志查询_执行状态" style="width:95%"/>
 -->
<freeze:body>
<freeze:title caption="查询采集日志列表"/>
<freeze:errors/>

<freeze:form action="/txn6011011" onsubmit="return submitForm();">

  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:browsebox property="service_targets_name" caption="服务对象"  valueset="资源管理_服务对象名称"  show="name" style="width:95%"/>
      <freeze:text property="task_name" caption="任务名称" style="width:95%" />
      <freeze:datebox property="created_time_start" caption="时间范围"
				datatype="string" maxlength="30"
				prefix="<table width='100%' border='0' cellpadding='0' cellspacing='0'><tr><td width='45%'>"
				style="width:100%" />
			</td>
			<td width='5%'>&nbsp;至：</td>
			<td width='45%'><freeze:datebox property="created_time_end"
					caption="时间范围" datatype="string" maxlength="30" style="width:100%"
					colspan="0" /></td>
			<td width='5%'></td>
			</tr>
			</table>
  </freeze:block>
  
<br>
  <freeze:grid property="record"   checkbox="false" caption="查询采集日志列表" keylist="collect_joumal_id" width="95%" navbar="bottom" >
  	  <freeze:button name="record_backupRecord" caption="归档" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:hidden property="collect_joumal_id" caption="采集日志ID" style="width:10%" visible="false"/>
      <freeze:hidden property="collect_task_id" caption="采集任务ID" style="width:12%" />
      <freeze:hidden property="service_targets_id" caption="服务对象ID" style="width:12%" />
      <freeze:hidden property="task_id" caption="任务ID" style="width:12%" />
      <freeze:hidden property="service_no" caption="任务编号" style="width:95%" />
      <freeze:hidden property="return_codes" caption="返回代码" style="width:95%" />
      <freeze:hidden property="task_consume_time" caption="单位毫秒" style="width:10%" />
      
      <freeze:cell property="@rowid" caption="序号"  align="center"   style="width:50px;" />
      <freeze:cell property="service_targets_name" caption="服务对象"  align="center"  valueset="资源管理_服务对象名称" style="width:10%" />
      <freeze:cell property="task_name" caption="任务名称"  align="center"   style="" />
     <%--  <freeze:cell property="method_name_cn" caption="方法名称"  align="center"   style="width:12%" />
      <freeze:cell property="collect_table_name" caption="采集表"  align="center"   style="width:10%" />  
      <freeze:cell property="collect_type" caption="采集类型"  align="center" valueset="资源管理_数据源类型" style="width:10%" /> --%>
      <freeze:cell property="task_start_time" caption="开始时间"  align="center"  style="width:17%" />
      <freeze:cell property="task_end_time" caption="结束时间"    align="center"   style="width:17%" />
      <freeze:cell property="collect_data_amount" caption="采集条数"    align="center"   style="width:8%" />
      <freeze:cell property="return_codes" caption="返回结果"      align="center"      valueset="" style="width:8%" />
      <freeze:cell property="oper" caption="操作" align="center" style="width:50px" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
