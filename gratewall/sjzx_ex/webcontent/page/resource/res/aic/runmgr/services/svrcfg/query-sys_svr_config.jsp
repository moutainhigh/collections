<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询服务配置列表</title>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery1.1.js"></script>
</head>
<%
	cn.gwssi.common.context.DataBus db = (cn.gwssi.common.context.DataBus)request.getAttribute("freeze-databus");
	String userId = db.getRecord("select-key").getValue("sys_svr_user_id");
	String svrId = db.getRecord("select-key").getValue("sys_svr_service_id");
%>
<script language="javascript">
window.onload = function(){
	
}

// 增加服务配置
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_svr_config.jsp", "增加服务配置");
	page.addParameter("record:sys_svr_config_id","record:sys_svr_config_id");
	page.addParameter("record:sys_svr_user_id","record:sys_svr_user_id");
	page.addParameter("record:sys_svr_service_id","record:sys_svr_service_id");
	page.addRecord();
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	var states = getFormAllFieldValues("record:state");
	var stateTds = document.getElementsByName("span_record:state");//"span_record:state");
	for(var i=0; i<states.length; i++){
		if(states[i] == '1'){
			// stateTds[i].setAttribute("align","right");
			stateTds[i].style.textAlign = 'right';
		}else{
			stateTds[i].style.textAlign = 'left';
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
			
function func_record_deleteRecord()
{
	var name = getFormFieldValues("record:svr_name");
	var uname = getFormFieldValues("record:user_name");
	var page = new pageDefine( "/txn50203005.do", "删除服务配置" );
	page.addParameter( "record:sys_svr_config_id", "select-key:sys_svr_config_id" );
	page.addValue( name, "select-key:svr_name" );
	page.addValue( uname, "select-key:user_name" );
	page.deleteRecord( "是否删除选中的记录" );
}

function locateOption(obj, v){
	if(v != "null"){
		var selObj = document.getElementById(obj);
		for(var i = 0; i < selObj.options.length; i++){
			var a = selObj.options[i].value;
			//alert(obj+": "+a+" == "+v+" ==>"+(a == v));
			if(a == v){
				selObj.options[i].selected = true;
				break;
			}else{
				selObj.options[i].selected = false;
			}
		}
	}
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
<freeze:title caption="配置列表"/>
<freeze:errors/>

<freeze:form action="/txn50203001">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:text property="login_name" caption="用户代码" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:select property="sys_svr_user_id" caption="用户名称"  style="width:95%"/>
      <freeze:text property="svr_code" caption="服务代码" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:select property="sys_svr_service_id" caption="服务名称" style="width:95%"/>
      <freeze:datebox property="create_date_from" caption="创建日期自"  numberformat="1" style="width:95%"/>
      <freeze:datebox property="create_date_to" caption="创建日期至"  numberformat="1" style="width:95%"/>
      <freeze:select property="state" caption="状态" valueset="共享服务用户状态GXFW" style="width:95%"/>
  </freeze:block>
  <br/>
  <freeze:grid property="record" caption="配置列表" keylist="sys_svr_config_id" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="配置" txncode="50203003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();" visible="false"/>
      <freeze:button name="record_deleteRecord" caption="删除" txncode="50203005" align="center" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:hidden property="sys_svr_config_id" caption="共享服务配置编码" style="width:15%" />
      <freeze:hidden property="sys_svr_user_id" caption="服务对象编码" style="width:15%" />
      <freeze:hidden property="sys_svr_service_id" caption="共享服务编码" style="width:15%" />
      <freeze:hidden property="state" caption="状态"/>
      <freeze:cell property="svr_code" caption="服务代码" style="width:15%"  />
      <freeze:cell property="svr_name" caption="服务名称" style="width:15%"  />
      <freeze:cell property="user_name" caption="用户名称" style="width:15%"  />
      <freeze:cell property="login_name" caption="用户代码" style="width:15%"  />
      <freeze:cell property="state" caption="用户状态" valueset="共享服务用户状态GXFW" style="width:5%"  />
      <freeze:cell property="create_date" caption="创建日期" style="width:10%"  />
      <freeze:cell property="create_by" caption="创建人" style="width:10%"  />
  </freeze:grid>

</freeze:form>
</freeze:body>
<script language="javascript">


</script>
</freeze:html>
