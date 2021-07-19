<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询服务日志列表</title>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery1.1.js"></script>
<style type="text/css">
.activerow {
	cursor:auto;
}
</style>
</head>
<%
	cn.gwssi.common.context.DataBus db = (cn.gwssi.common.context.DataBus)request.getAttribute("freeze-databus");
	String userId = db.getRecord("select-key").getValue("sys_svr_user_id");
	String svrId = db.getRecord("select-key").getValue("sys_svr_service_id");
	String state = db.getRecord("select-key").getValue("state");
%>
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
	var stateObj = document.getElementById("select-key:state");
	if(stateObj){
		stateObj.options.add(createOption("成功","成功"));
		stateObj.options.add(createOption("失败","失败"));
	}
	
	var state = "<%= state == null ? "" : state%>";
   	if(state){
 		if(stateObj.options.length != 0){
   			for(var i = 0; i< stateObj.options.length; i++){
   				if(stateObj.options[i].value == state){
   					stateObj.options[i].selected = true;
   				}
   			}
   		}
   	} 
	
	$.get("<%= request.getContextPath()%>/txn50201001.ajax?select-key:showall=true&select-key:state=0", function(xml){
		var errCode = xml.selectSingleNode("//context/error-code").text;
		if(errCode != '000000'){
	      alert("处理错误："+errDesc);
	      return;
	    }else{
	    	var userSelect = document.getElementById("select-key:sys_svr_user_id");
	    	var users = xml.selectNodes("//record");
	    	for(var i = 0; i < users.length; i++){
	    		var uId = users[i].selectSingleNode("sys_svr_user_id").text;
	    		var uName = users[i].selectSingleNode("user_name").text;
	    		userSelect.options.add(createOption(uId, uName));
	    	}
	    	var userId = "<%= userId == null ? "" : userId%>";
	    	if(userId){
	    		if(userSelect.options.length != 0){
	    			for(var i = 0; i< userSelect.options.length; i++){
	    				if(userSelect.options[i].value == userId){
	    					userSelect.options[i].selected = true;
	    				}
	    			}
	    		}
	    	} 
	    	//查询所有服务
	    	$.get("<%= request.getContextPath()%>/txn50202001.ajax?select-key:showall=true", function(xml){
				if(errCode != '000000'){
			      alert("处理错误："+errDesc);
			      return;
			    }else{
			    	var svrSelect = document.getElementById("select-key:sys_svr_service_id");
			    	var svrs = xml.selectNodes("//record");
			    	for(var i = 0; i < svrs.length; i++){
			    		var sId = svrs[i].selectSingleNode("sys_svr_service_id").text;
	    				var sName = svrs[i].selectSingleNode("svr_name").text;
			    		svrSelect.options.add(createOption(sId, sName));
			    	}
			    	
			    	var svrId = "<%= svrId == null ? "" : svrId%>";
			    	if(svrId){
			    		if(svrSelect.options.length != 0){
			    			for(var i = 0; i< svrSelect.options.length; i++){
			    				if(svrSelect.options[i].value == svrId){
			    					svrSelect.options[i].selected = true;
			    				}
			    			}
			    		}
			    	}
				}
			});
	    }
	});
}

function createOption(v,t){
  var oOption = document.createElement("option");
  oOption.value = v;
  oOption.text = t;
  oOption.title = t;
  return oOption;
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询服务日志列表"/>
<freeze:errors/>

<freeze:form action="/txn50207001">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:select property="sys_svr_user_id" caption="用户名称" style="width:95%"/>
      <freeze:select property="sys_svr_service_id" caption="服务名称" style="width:95%"/>
      <freeze:datebox property="execute_start_time" caption="起始时间" datatype="string" numberformat="1" style="width:95%"/>
      <freeze:datebox property="execute_end_time" caption="结束时间" datatype="string"  numberformat="1" style="width:95%"/>
      <freeze:select property="state" caption="查询状态" style="width:95%"/>
  </freeze:block>
  <br/>
  <freeze:grid property="record" caption="服务日志列表" keylist="sys_svr_log_id" width="95%" checkbox="false" rowselect="false" navbar="bottom" fixrow="false">
      <freeze:cell align="middle" property="@rowid" caption="序号" style="width:5%"/>
      <freeze:hidden property="sys_svr_log_id" caption="服务日志编号" style="width:10%" />
      <freeze:cell property="sys_svr_user_name" caption="服务对象" style="width:15%" />
      <freeze:cell property="sys_svr_service_name" caption="服务名称" style="width:15%" />
      <freeze:cell property="execute_start_time" caption="执行开始时间" datatype="date" style="width:15%" />
      <freeze:cell property="execute_end_time" caption="执行结束时间" datatype="date" style="width:15%" />
      <freeze:cell property="state" caption="执行结果" style="width:5%" />
      <freeze:cell property="records_mount" caption="返回记录数" style="width:10%" />
      <freeze:cell property="error_msg" caption="错误描述" style="width:10%" />
      <freeze:cell property="client_ip" caption="客户端IP" style="width:10%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
