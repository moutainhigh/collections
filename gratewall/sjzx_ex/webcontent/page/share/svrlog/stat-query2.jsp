<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
<head>
<title>数据交换统计</title>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery1.1.js"></script>
<style type="text/css">
.activerow {
	cursor:auto;
}
</style>
</head>

<freeze:body>
<freeze:title caption="数据交换统计"/>
<%
	cn.gwssi.common.context.DataBus db = (cn.gwssi.common.context.DataBus)request.getAttribute("freeze-databus");
	String execute_type = "";
	if(db != null){
		execute_type = db.getRecord("select-key").getValue("execute_type");
	}
%>
<freeze:errors/>
<script language="javascript">

function createOption(optionValue,optionText){
	var oOption = document.createElement("option");
	oOption.value = optionValue;
	oOption.text = optionText;
	oOption.title = optionText;
	return oOption;
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
	var execute_typeObj = document.getElementById("select-key:execute_type");
	if(execute_typeObj){
		execute_typeObj.options.add(createOption("提供","提供"));
		execute_typeObj.options.add(createOption("采集","采集"));
	}
	
	var execute_type = "<%= execute_type == null ? "" : execute_type%>";
   	if(execute_type){
 		if(execute_typeObj.options.length != 0){
   			for(var i = 0; i< execute_typeObj.options.length; i++){
   				if(execute_typeObj.options[i].value == execute_type){
   					execute_typeObj.options[i].selected = true;
   				}
   			}
   		}
   	}
}

function func_doQuery(){
	var startObj = document.getElementById("select-key:execute_start_time");	
	var endObj = document.getElementById("select-key:execute_end_time");
	if(!checkInput(startObj,'【起始时间】','3',false)||!checkInput(endObj,'【结束时间】','3',false)){
	    return;
	}
	var start = startObj.value;
	var end = endObj.value;
	if(start){
		var s_date = new Date();
		var currYear = s_date.getYear();
		var currMonth = s_date.getMonth() + 1;
		var currDay = s_date.getDate();
		var today = currYear+'-';
		if(currMonth>9){
	    today += currMonth+'-';
		}else{
	    today += '0'+currMonth+'-';
		}
		if(currDay>9){
	    today += currDay;
		}else{
	    today += '0'+currDay;
		}
		if(start>today){
            alert("【起始时间】开始日期不能超过今天！");
            document.getElementById("select-key:execute_start_time").select();
            return false;
		}		
	}
	
	if(start&&end){
        if(start>end){
            alert("【结束时间】结束日期必须大于起始时间！");
            document.getElementById("select-key:execute_end_time").select();
            return false;
        }		
	}
	
	
  var page = new pageDefine( "/txn50207004.do", "数据交换统计");
  page.addValue(getFormFieldText("select-key:user_id"),"select-key:user_id");
  page.addParameter("select-key:execute_start_time","select-key:execute_start_time");
  page.addParameter("select-key:execute_end_time","select-key:execute_end_time");
  page.addValue(getFormFieldText("select-key:sys_svr_service_id"),"select-key:sys_svr_service_id");
  page.addParameter("select-key:execute_type","select-key:execute_type");
  page.goPage( );
}

function reset(){
	setFormFieldValue("select-key:user_id", 0, "");
	setFormFieldValue("select-key:execute_start_time", 0, "");
	setFormFieldValue("select-key:execute_end_time", 0, "");
	setFormFieldValue("select-key:_tmp_sys_svr_service_id", 0, "");
	setFormFieldValue("select-key:execute_type", 0, " ");
}

function queryUserServices(){
	var user_id = getFormFieldValue("select-key:user_id");
	if(!user_id){
		return;
	}else{
		var param = 'userbianma='+user_id;
		return param;
	}
}

function __clear(){
	setFormFieldValue("select-key:sys_svr_service_id", 0, "");
}

_browse.execute( '__userInitPage()' );
</script>

<freeze:form action="/txn50207004">
  <freeze:block property="select-key" caption="查询条件" width="95%">
      <freeze:button name="record_addRecord" caption=" 查 询 " enablerule="0" hotkey="ADD" align="right" onclick="func_doQuery();"/>
      <freeze:button name="record_resetRecord" caption=" 重 填 " enablerule="0" hotkey="ADD" align="right" onclick="reset();"/>
      <freeze:browsebox property="user_id" caption="用户名称" style="width:90%" valueset="数据交换对象" show="name" onchange="__clear();" />
      <freeze:browsebox property="sys_svr_service_id" caption="服务名称" style="width:90%" valueset="数据交换服务" parameter="queryUserServices()" show="name" />
      <freeze:datebox property="execute_start_time"  caption="起始时间" prefix="<table width='90%' border='0' cellpadding='0' cellspacing='0'><tr><td width='45%'>" style="width:100%" notnull="true"/>
        </td><td width='5%'>至</td><td width='45%'>
      <freeze:datebox property="execute_end_time" caption="起始时间" style="width:100%" colspan="0" notnull="true"/>
        </td></tr></table>
      <freeze:select property="execute_type" caption="交换类型" style="width:90%" />
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="数据交换统计列表" width="95%" keylist="sys_svr_user_name" rowselect="false" checkbox="false" navbar="bottom" fixrow="false">
    <freeze:cell property="@rowid" caption="序号" align="middle" style="width:5%"/>
    <freeze:cell property="sys_svr_user_name" caption="用户名称" width="30%" />
    <freeze:cell property="sys_svr_service_name" caption="服务名称" width="30%" />
    <freeze:cell property="records_mount" caption="返回记录数"  align="middle" width="10%"/>
    <freeze:cell property="query_times" caption="查询次数" width="10%"/>
    <freeze:cell property="execute_type" caption="交换类型" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
