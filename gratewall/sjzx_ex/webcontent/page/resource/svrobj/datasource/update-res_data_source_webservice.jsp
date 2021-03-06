<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="800" height="450">
<head>
<title>修改数据源信息</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/page/checkIP.js"></script>
<script language="javascript">

// 测试连接
function func_testConn()
{

	var data_source_ip = getFormFieldValue("record:data_source_ip");
	var access_port = getFormFieldValue("record:access_port");
	var access_url = getFormFieldValue("record:access_url");
	if(!isIP(data_source_ip)){
		alert("请输入合法的【数据源IP】!");
		return;
	}
	if(!check_port(access_port)){
		alert("请输入合法的【访问端口】!");
		return;
	}
	if(access_url==null||access_url==""){
		alert("【访问URL】不能为空!");
		return;
	}
	var page = new pageDefine( "/txn20102017.ajax", "测试连接" );
	page.addParameter("record:access_url","record:access_url");
	page.addParameter("record:access_port","record:access_port");
	page.addParameter("record:data_source_ip","record:data_source_ip");
	_showProcessHintWindow("测试连接中,请稍候...");
	page.callAjaxService("testConnCallBack");
}
function testConnCallBack(errorCode, errDesc, xmlResults){
	if(errorCode!="000000"){
		alert(errDesc);
	}else{
		var result = _getXmlNodeValue(xmlResults,"record:result");
		
		if(result=="1"){
			_showProcessHintWindow("<span style='color:green;'>连接成功!</span><br/><a href='javascript:_hideProcessHintWindow();'>关闭</a>");
		}else{
			_showProcessHintWindow("<span style='color:red;'>连接失败!</span><br/><a href='javascript:_hideProcessHintWindow();'>关闭</a>");
			}
	}
}

function _showProcessHintWindow( hint )
	{
		// 提示信息
		if( hint == null || hint == '' ){
			hint = "正在执行请求，请等待处理结果";
		}
		var dt = new Date();
		if(hint.indexOf("连接失败")<0 && hint.indexOf("连接成功")<0){
			hint = '<div style="border:1px solid #A7DAED;background:#fff;">'+
					'<div style="width:100%;text-align:center;padding:2px 0px;filter:alpha(opacity=70);"><img src="/script/menu-images/onload.gif" width="80" height="80"/></div>'+
					'<div style="height:16px;width:100%;text-align:center;color:#336699;">'+ hint + '</div>'+
					'<div style="width:100%;text-align:center;color:#336699;"><span id="_HintTableCaptionMinute">00</span>:<span id="_HintTableCaptionSecond">00</span></div></div>';
		}else{
			hint = '<div style="border:1px solid #A7DAED;background:#fff;height:80px;">'+
			//'<div style="width:100%;text-align:center;padding:2px 0px;filter:alpha(opacity=70);"><img src="/script/menu-images/onload.gif" width="80" height="80"/></div>'+
			'<div style="height:16px;width:100%;text-align:center;color:#336699;margin-top:30px;">'+ hint + '</div></div>';
		}
		// 框架
		var bHintLayer = document.all.hintWindowLayer;
		bHintLayer.innerHTML = hint;
		// 显示并设置座标
		var o = bHintLayer.style;
		o.display = "";
	    var cw = 150, ch = 70;
	    if( openWindowType == 'modal' ){
	    	var dw = window.dialogWidth;
		    var dh = window.dialogHeight;
		    if( dw == null || dh == null ){
		    	return;
		    }
		    
		    var ptr = dw.indexOf('px');
		    if( ptr > 0 ){
		    	dw = dw.substring( 0, ptr );
		    }
		    
		    ptr = dh.indexOf('px');
		    if( ptr > 0 ){
		    	dh = dh.substring( 0, ptr );
		    }
	    }
	    else{
		    var dw = document.body.clientWidth;
		    var dh = document.body.clientHeight;
			if(dh==0){
				dh = 700;
			}
			if(dw ==0 ){
				dw = 1000;
			}
		}
	    
	    o.top = (dh - ch)/2;
	    o.left = (dw - cw)/2;
	    var hintWindowIFrame = document.getElementById("hintWindowIFrame");
	    hintWindowIFrame.style.top = o.top;
	    hintWindowIFrame.style.left = o.left;
	    hintWindowIFrame.style.height = bHintLayer.offsetHeight;
	    hintWindowIFrame.style.width = bHintLayer.offsetWidth;
	    var hintWindowIFrameStatus = hintWindowIFrame.style.display;
	    hintWindowIFrame.style.display = "block";
	    var promited = false;
	    // 如果没有隐藏就又显示，可以直接忽略
	    if (hintWindowIFrameStatus != "block"){
			  	window._checkWaitTime = setInterval( function(){
			    	var secondWithoutTransfer = (new Date - dt)/1000;
			    	var mins = Math.floor(secondWithoutTransfer / 60);
			    	var secs = Math.ceil(secondWithoutTransfer % 60);
			    	mins = mins < 10 ? "0" + mins : mins;
			    	secs = secs < 10 ? "0" + secs : secs;
			    	if(document.getElementById("_HintTableCaptionMinute")){
			    	  document.getElementById("_HintTableCaptionMinute").innerHTML = mins;
			    	}
			    	if(document.getElementById("_HintTableCaptionSecond")){
			    	   document.getElementById("_HintTableCaptionSecond").innerHTML = secs;
			    	}
			    	/*if (secondWithoutTransfer >= 60 && !promited){
			    		// document.getElementById("_HintTableCaption").innerHTML = "执行过程较慢，您可以继续等待也可以返回或者退出";
			    		alert("执行过程较慢，您可以继续等待也可以返回或者退出");
			    		promited = true;
			    	}*/
			    } , 1000); 
	    }
	}

// 保 存
function func_record_saveAndExit()
{
	var data_source_ip = getFormFieldValue("record:data_source_ip");
	var access_port = getFormFieldValue("record:access_port");
	
	if(!isIP(data_source_ip)){
		alert("请输入合法的【数据源IP】!");
		return;
	}
	if(!check_port(access_port)){
		alert("请输入合法的【访问端口】!");
		return;
	}
	saveAndExit( '', '保存数据源表' );	// /txn20102001.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn20102001.do
}

//设置数据源名称
function setName(){

var name = getFormFieldValue("record:_tmp_service_targets_id");
var type = getFormFieldValue("record:data_source_type");

var typename = "";
if('00'==type){
	typename = "WEBSERVICE";
}else if('01'==type){
	typename = "数据库";
}else if ('02'==type){
	typename = "FTP";
}else {
	typename = "";
}
	document.getElementById("record:data_source_name").value =name+"_"+typename ;
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="修改数据源信息"/>
<freeze:errors/>

<freeze:form action="/txn20102002" enctype="multipart/form-data">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="data_source_id" caption="数据源ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改数据源信息" width="95%">
  <freeze:button name="record_testRecord" caption="测试连接" hotkey="SAVE" onclick="func_testConn();"/>
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回"hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="data_source_id" caption="数据源ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:browsebox property="service_targets_id" caption="所属服务对象" show="name" valueset="资源管理_服务对象名称" onchange="setName();" notnull="true"  style="width:95%"/>
      <freeze:select property="data_source_type" caption="数据源类型" show="name" valueset="资源管理_数据源类型" readonly="true" notnull="true" style="width:95%"/>
      <freeze:text property="data_source_name" caption="数据源名称" datatype="string" maxlength="100" notnull="true" style="width:95%"/>
      <freeze:text property="db_username" caption="数据源用户名" datatype="string" maxlength="50" notnull="true" style="width:95%"/>
      <freeze:text property="db_password" caption="数据源密码" maxlength="50" notnull="true"  style="width:95%"/>
      
      
      <freeze:text property="data_source_ip" caption="数据源IP" datatype="string" maxlength="50" notnull="true" style="width:95%"/>
      <freeze:text property="access_port" caption="访问端口" datatype="string" maxlength="8" colspan="2" notnull="true" style="width:98%"/>
      <freeze:textarea property="access_url" caption="访问URL" colspan="2" rows="2" notnull="true" style="width:98%"/>
      <freeze:textarea property="db_desc" caption="数据源描述" colspan="2" rows="2" maxlength="2000" style="width:98%"/>
     <freeze:cell property="crename" caption="创建人" datatype="string"  style="width:95%"/>
      <freeze:cell property="cretime" caption="创建时间" datatype="string"  style="width:95%"/>
      <freeze:cell property="modname" caption="最后修改人" datatype="string"  style="width:95%"/>
      <freeze:cell property="modtime" caption="最后修改时间" datatype="string"  style="width:95%"/>
     
      <freeze:hidden property="req_que_name" caption="请求队列名称" datatype="string" maxlength="100"  style="width:95%"/>
      <freeze:hidden property="res_que_name" caption="接收队列名称" datatype="string" maxlength="100"  style="width:95%"/>
      <freeze:hidden property="db_type" caption="数据库类型" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="db_instance" caption="数据源实例" datatype="string" maxlength="30" style="width:95%"/>
      
      
      <freeze:hidden property="db_status" caption="数据源状态" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="is_markup" caption="有效标记" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="creator_id" caption="创建人ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="created_time" caption="创建时间" datatype="string" maxlength="19" style="width:95%"/>
      <freeze:hidden property="last_modify_id" caption="最后修改人ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="last_modify_time" caption="最后修改时间" datatype="string" maxlength="19" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
