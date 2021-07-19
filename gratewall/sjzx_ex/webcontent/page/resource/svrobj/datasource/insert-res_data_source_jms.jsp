<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="800" height="550">
<head>
<title>增加数据源信息</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/page/checkIP.js"></script>
<script language="javascript">

// 测试连接
function func_testConn()
{
	var data_source_ip = getFormFieldValue("record:data_source_ip");

	if(!isIP(data_source_ip)){
		alert("请输入合法的【数据源IP】!");
		return;
	}
	
	var page = new pageDefine( "/txn20102030.ajax", "测试连接" );
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
function func_record_saveRecord()
{
	saveRecord( '', '保存数据源表' );
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存数据源表' );
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
}else if ('03'==type){
	typename = "JMS";
}else if ('04'==type){
	typename = "SOCKET";
}else {
	typename = "";
}
	document.getElementById("record:data_source_name").value =name+"_"+typename ;
}

// 保存并关闭
function func_record_saveAndExit()
{
	var data_source_ip = getFormFieldValue("record:data_source_ip");
	var access_port = getFormFieldValue("record:access_port");
	
	var data_source_type = getFormFieldValue("record:data_source_type");
	var service_targets_id = getFormFieldValue("record:service_targets_id");
	var data_source_name = getFormFieldValue("record:data_source_name");
	var db_username = getFormFieldValue("record:db_username");
	var db_password = getFormFieldValue("record:db_password");
	var access_url = getFormFieldValue("record:access_url");
	var req_que_name = getFormFieldValue("record:req_que_name");
	var res_que_name = getFormFieldValue("record:res_que_name");
	
	if(data_source_type==null||data_source_type==""){
		alert("【数据源类型】不能为空!");
		return;
	}
	if(data_source_name==null||data_source_name==""){
		alert("【数据源名称】不能为空!");
		return;
	}
	if(service_targets_id==null||service_targets_id==""){
		alert("【所属服务对象】不能为空!");
		return;
	}
	if(db_username==null||db_username==""){
		alert("【数据源用户名】不能为空!");
		return;
	}
	if(db_password==null||db_password==""){
		alert("【数据源密码】不能为空!");
		return;
	}
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
	if(req_que_name==null||req_que_name==""){
		alert("【请求队列名称】不能为空!");
		return;
	}
	//if(access_url==null||access_url==""){
		//alert("【访问URL】不能为空!");
		//return;
	//}
	
	
	
	
	//saveAndExit( '保存成功', '保存失败','/txn20102001.do' );	// /txn20102001.do
	var page = new pageDefine( "/txn20102003.ajax", "新增webservice数据源" );
	page.addParameter("record:data_source_type","record:data_source_type");
	page.addParameter("record:service_targets_id","record:service_targets_id");
	page.addParameter("record:data_source_name","record:data_source_name");
	page.addParameter("record:access_url","record:access_url");
	page.addParameter("record:db_desc","record:db_desc");
	page.addParameter("record:data_source_ip","record:data_source_ip");
	page.addParameter("record:db_username","record:db_username");
	page.addParameter("record:db_password","record:db_password");
	page.addParameter("record:access_port","record:access_port");
	page.addParameter("record:db_instance","record:db_instance");
	
	page.addParameter("record:db_type","record:db_type");
	page.addParameter("record:db_status","record:db_status");
	page.addParameter("record:is_markup","record:is_markup");
	page.addParameter("record:creator_id","record:creator_id");
	page.addParameter("record:created_time","record:created_time");
	page.addParameter("record:last_modify_id","record:last_modify_id");
	page.addParameter("record:last_modify_time","record:last_modify_time");
	page.addParameter("record:req_que_name","record:req_que_name");
	page.addParameter("record:res_que_name","record:res_que_name");
	page.callAjaxService("WebServiceCallBack");
}

function WebServiceCallBack(errorCode, errDesc, xmlResults){

	if(errorCode!="000000"){
		alert("保存失败!");
		alert(errDesc);
	}else{
	
		alert("保存成功!");
		parent.func_record_goBack();
		//window.close();
	}
}

// 返 回
function func_record_goBack()
{
	//goBack();
	//goBack("/txn20102001.do");	// /txn20102001.do
	//window.close();
	parent.func_record_goBack();
}

function funTBChanged(){
	var type = getFormFieldValue('record:data_source_type');
	
	if(type=='00'){
	//setDisplay("1",true);
	setDisplay("4",false);
	}else {
	//setDisplay("1",false);
	//setDisplay("2",true);
	}
}

function setDisplay(id,b){
	
	var preStr = "row_"+id;
	if(b=='true'||b==true){
		document.getElementById(preStr).style.display='block';
		var cells = document.getElementById(preStr).cells;
		for(var ii=0;ii<cells.length;ii++){
			cells[ii].className='odd1_b';
		}
	}else{
		document.getElementById(preStr).style.display='none';
	}
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body >
<freeze:title caption="增加数据源信息"/>
<freeze:errors/>

<freeze:form action="/txn20102027">
  <freeze:block property="record" caption="增加数据源信息" width="95%">
      <freeze:button name="record_testRecord" caption="测试连接" hotkey="SAVE" onclick="func_testConn();"/>
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回"hotkey="CLOSE" onclick="func_record_goBack();"/>
      
      <freeze:hidden property="data_source_id" caption="数据源ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="data_source_type" caption="数据源类型"  value="03"  style="width:95%" />
      <freeze:browsebox property="service_targets_id" caption="所属服务对象" show="name" valueset="资源管理_服务对象名称" onchange="setName();"  notnull="true" style="width:95%"/>
      <freeze:text property="data_source_name" caption="数据源名称" datatype="string" maxlength="100" notnull="true" style="width:95%"/>
      <freeze:text property="db_username" caption="数据源用户名" datatype="string" maxlength="50" notnull="true" style="width:95%"/>
      <freeze:text property="db_password" caption="数据源密码" maxlength="50" notnull="true" style="width:95%"/>
      <freeze:text property="data_source_ip" caption="数据源IP" datatype="string" maxlength="50" notnull="true" style="width:95%"/>
      <freeze:text property="access_port" caption="访问端口" datatype="string" maxlength="8" notnull="true" style="width:95%"/>
      <freeze:text property="req_que_name" caption="请求队列名称" datatype="string" maxlength="100" notnull="true" style="width:95%"/>
      <freeze:text property="res_que_name" caption="接收队列名称" datatype="string" maxlength="100" notnull="true" style="width:95%"/>
      
      
      
      <freeze:textarea property="access_url" caption="访问URL" colspan="2" rows="2" maxlength="2000" notnull="true" style="width:98%"/>
      
      <freeze:textarea property="db_desc" caption="数据源描述" colspan="2" rows="2" maxlength="2000" style="width:98%"/>
      
      <freeze:hidden property="db_type" caption="数据库类型" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="db_instance" caption="数据库实例" datatype="string" maxlength="30" style="width:95%"/>
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
