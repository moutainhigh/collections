<%@ page contentType="text/html; charset=GBK" %>
<%@page import="cn.gwssi.common.context.Recordset"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询共享服务列表</title>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery.ui/jquery.ui.core.js"></script>
</head>
<%
	cn.gwssi.common.context.DataBus db = (cn.gwssi.common.context.DataBus)request.getAttribute("freeze-databus");
	String svrId = db.getRecord("select-key").getValue("sys_svr_service_id");
	Recordset rs=db.getRecordset("record-all");
%>
<style>

#basic-modal-content {display:block;}

/* Overlay */
#simplemodal-overlay {background-color:#000; cursor:wait;}

/* Container */
#simplemodal-container {height:360px; width:600px; color:#bbb; background-color:#333; border:4px solid #444; padding:12px;}
#simplemodal-container .simplemodal-data {padding:8px;}
#simplemodal-container code {background:#141414; border-left:3px solid #65B43D; color:#bbb; display:block; font-size:12px; margin-bottom:12px; padding:4px 6px 6px;}
#simplemodal-container a {color:#ddd;}
#simplemodal-container a.modalCloseImg {background:url(../img/basic/x.png) no-repeat; width:25px; height:29px; display:inline; z-index:3200; position:absolute; top:-15px; right:-16px; cursor:pointer;}
#simplemodal-container h3 {color:#84b8d9;}


</style>
<script language="javascript">
var typeKey=new Array;
function showDialog(url,a,b){
				var iWidth=a;//窗口宽度
				var iHeight=b;//窗口高度
				var iTop=(window.screen.height-iHeight)/2;
				var iLeft=(window.screen.width-iWidth)/2;
				window.showModalDialog(url,window,"dialogHeight:"+ iHeight +"px;dialogWidth:"+ iWidth +"px;dialogTop:"+ iTop +";dialogLeft:"+ iLeft +";resizable:yes;status:no;scroll:yes;status:no;toolbar:no;menubar:no;");
			}

function changeButton(){}	//勿删有用

// 增加共享服务
function func_record_addRecord()
{ 
	//alert(window.screen.height + " ===  "+ window.screen.width)
	 var page = new pageDefine( "insert-sys_svr_service.jsp", "增加共享服务" );
	 page.addRecord();

	//  var src = "<%=request.getContextPath()%>/dw/runmgr/services/svrmgr/insert-sys_svr_service.jsp";
	// showDialog(src,window.screen.width,window.screen.height)
}

// 增加自定义服务
function func_record_selfRecord()
{
	var page = new pageDefine( "insert-sys_svr_self_service.jsp", "增加自定义服务", "modal" );
	page.addRecord();
}


// 修改共享服务
function func_record_updateRecord(idx)
{
	var svrId = getFormFieldValue("record:sys_svr_service_id", idx);
	$.get("<%=request.getContextPath()%>/txn50202016.ajax?record:sys_svr_service_id=" + svrId, function(xml){
		var errCode = xml.selectSingleNode("//context/error-code").text;
		if(errCode != '000000'){
			alert("服务已经配置给用户，禁止修改！");
			clickFlag = 0;
			checkAllMenuItem();
			return;
		}
		//执行服务目标
		var _target;
	    var service_type = getFormFieldText("record:svr_type",idx);
	    if(service_type == '一般服务'){
	    	_target = "<%=request.getContextPath()%>/dw/runmgr/services/svrmgr/update-sys_svr_service.jsp";
	    }else{
	    	_target = "/txn50202012.do";
	    }
    	var page = new pageDefine( _target, "修改共享服务");
		page.addValue( svrId, "record:sys_svr_service_id" );
		page.addParameter( "select-key:svr_code", "select-key:svr_code" );
		page.addParameter( "select-key:svr_name", "select-key:svr_name" );
		page.addParameter( "select-key:create_by", "select-key:create_by" );
		page.addParameter( "select-key:create_date", "select-key:create_date" );
		page.addValue( "update", "action" );
		page.goPage();
	});
}

// 删除共享服务
function func_record_deleteRecord()
{
	var id = getFormFieldValues("record:sys_svr_service_id");
	var name = getFormFieldValues("record:svr_name");
	$.get("<%=request.getContextPath()%>/txn50202016.ajax?record:sys_svr_service_id=" + id + "&record:svr_name="+name, function(xml){
		var errCode = xml.selectSingleNode("//context/error-code").text;
		if(errCode != '000000'){
			alert("服务已经配置给用户，禁止删除！");
			clickFlag = 0;
			checkAllMenuItem();
			return;
		}
		var page = new pageDefine( "/txn50202005.do", "删除共享服务" );
		page.addParameter( "record:sys_svr_service_id", "record:sys_svr_service_id" );
		page.addValue( name, "record:svr_name" );
		page.deleteRecord( "是否删除选中的记录" );
		document.SysSvrServiceForm.submit();
	});
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	var ids = getFormAllFieldValues("record:sys_svr_service_id");
	for(var i=0; i<ids.length; i++){
	   var htm='<a href="#" title="修改" onclick="func_record_updateRecord('+i+')"><div class="edit"></div></a>&nbsp;';
	   htm+='<a href="#" title="查看" onclick="func_viewConfig(\''+ids[i]+'\');"><div class="detail"></div></a>&nbsp;';
	   htm+='<a href="#" title="查看用户" onclick="func_userConfig(\''+ids[i]+'\');"><div class="query"></div></a>';
	   document.getElementsByName("span_record:oper")[i].innerHTML +=htm;
	 }
  <freeze:loop property="record-all">
       typeKey.push({'svr_name':'<freeze:out value="${record-all.svr_name}"/>','sid':'<freeze:out value="${record-all.sys_svr_service_id}"/>'});
  </freeze:loop>
	
	//alert(typeKey.length);
	var svrSelect = document.getElementById("select-key:sys_svr_service_id");
	for(var i = 0; i < typeKey.length; i++){
	   var sId = typeKey[i].sid;
	   var sName = typeKey[i].svr_name;
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
	
	//查询所有服务
	/*$.get("<%= request.getContextPath()%>/txn50202001.ajax?select-key:showall=true", function(xml){
		var errCode = xml.selectSingleNode("//context/error-code").text;
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
	});*/
}

function func_viewConfig(sid){
	var page = new pageDefine( "show-sys_svr_service.jsp", "查询服务配置" );
	page.addValue(sid, "record:sys_svr_service_id");
	page.goPage();
}

function func_userConfig(sid){
	var page = new pageDefine( "/txn50202018.do", "查询服务配置" );
	page.addValue(sid, "select-key:sys_svr_service_id");
	page.goPage();
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
<freeze:title caption="共享服务列表"/>
<freeze:errors/>

<freeze:form action="/txn50202001">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:text property="svr_code" caption="服务代码" datatype="string" maxlength="20" style="width:90%"/>
      <freeze:select property="sys_svr_service_id" caption="服务名称" style="width:90%"/>
      <freeze:datebox property="create_date_from" caption="创建日期" prefix="<table width='90%' border='0' cellpadding='0' cellspacing='0'><tr><td width='45%'>" style="width:100%"/>
    </td><td width='5%'>至</td><td width='45%'>
    <freeze:datebox property="create_date_to" caption="创建日期" style="width:100%" colspan="0"/>
    </td></tr></table>
      <freeze:text property="create_by" caption="创&nbsp;建&nbsp;人" datatype="string" maxlength="32" style="width:90%"/>
  </freeze:block>
  <br/>
  <freeze:grid property="record" caption="共享服务列表" keylist="sys_svr_service_id" width="95%" checkbox="true" navbar="bottom" fixrow="false" align="center">
      <freeze:button name="record_addRecord" caption="增加" txncode="50202003" align="center" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除" txncode="50202005" align="center" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="svr_code" caption="服务代码"  style="width:10%" />
      <freeze:cell property="svr_name" caption="服务名称"  style="width:20%" />
      <freeze:cell property="create_date" caption="创建日期"  style="width:10%" />
      <freeze:cell property="create_by" caption="创&nbsp;建&nbsp;人"  style="width:10%" />
      <freeze:cell property="svr_type" caption="服务类型"  style="width:10%" />
      <freeze:cell property="svr_desc" caption="描&nbsp;&nbsp;&nbsp;&nbsp;述" style="width:10%" />
      <freeze:hidden property="hasConfig" caption="是否配置服务"/>
      <freeze:cell property="oper" caption="操作" align="center" style="width:12%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
