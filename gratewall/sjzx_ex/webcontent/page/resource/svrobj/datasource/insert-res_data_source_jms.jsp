<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="800" height="550">
<head>
<title>��������Դ��Ϣ</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/page/checkIP.js"></script>
<script language="javascript">

// ��������
function func_testConn()
{
	var data_source_ip = getFormFieldValue("record:data_source_ip");

	if(!isIP(data_source_ip)){
		alert("������Ϸ��ġ�����ԴIP��!");
		return;
	}
	
	var page = new pageDefine( "/txn20102030.ajax", "��������" );
	page.addParameter("record:access_url","record:access_url");
	page.addParameter("record:access_port","record:access_port");
	page.addParameter("record:data_source_ip","record:data_source_ip");
	_showProcessHintWindow("����������,���Ժ�...");
	page.callAjaxService("testConnCallBack");
}
function testConnCallBack(errorCode, errDesc, xmlResults){
	if(errorCode!="000000"){
		alert(errDesc);
	}else{
		var result = _getXmlNodeValue(xmlResults,"record:result");
		
		if(result=="1"){
			_showProcessHintWindow("<span style='color:green;'>���ӳɹ�!</span><br/><a href='javascript:_hideProcessHintWindow();'>�ر�</a>");
		}else{
			_showProcessHintWindow("<span style='color:red;'>����ʧ��!</span><br/><a href='javascript:_hideProcessHintWindow();'>�ر�</a>");
			}
	}
}

function _showProcessHintWindow( hint )
	{
		// ��ʾ��Ϣ
		if( hint == null || hint == '' ){
			hint = "����ִ��������ȴ�������";
		}
		var dt = new Date();
		if(hint.indexOf("����ʧ��")<0 && hint.indexOf("���ӳɹ�")<0){
			hint = '<div style="border:1px solid #A7DAED;background:#fff;">'+
					'<div style="width:100%;text-align:center;padding:2px 0px;filter:alpha(opacity=70);"><img src="/script/menu-images/onload.gif" width="80" height="80"/></div>'+
					'<div style="height:16px;width:100%;text-align:center;color:#336699;">'+ hint + '</div>'+
					'<div style="width:100%;text-align:center;color:#336699;"><span id="_HintTableCaptionMinute">00</span>:<span id="_HintTableCaptionSecond">00</span></div></div>';
		}else{
			hint = '<div style="border:1px solid #A7DAED;background:#fff;height:80px;">'+
			//'<div style="width:100%;text-align:center;padding:2px 0px;filter:alpha(opacity=70);"><img src="/script/menu-images/onload.gif" width="80" height="80"/></div>'+
			'<div style="height:16px;width:100%;text-align:center;color:#336699;margin-top:30px;">'+ hint + '</div></div>';
		}
		// ���
		var bHintLayer = document.all.hintWindowLayer;
		bHintLayer.innerHTML = hint;
		// ��ʾ����������
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
	    // ���û�����ؾ�����ʾ������ֱ�Ӻ���
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
			    		// document.getElementById("_HintTableCaption").innerHTML = "ִ�й��̽����������Լ����ȴ�Ҳ���Է��ػ����˳�";
			    		alert("ִ�й��̽����������Լ����ȴ�Ҳ���Է��ػ����˳�");
			    		promited = true;
			    	}*/
			    } , 1000); 
	    }
	}

// �� ��
function func_record_saveRecord()
{
	saveRecord( '', '��������Դ��' );
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '��������Դ��' );
}

//��������Դ����
function setName(){

var name = getFormFieldValue("record:_tmp_service_targets_id");
var type = getFormFieldValue("record:data_source_type");

var typename = "";
if('00'==type){
	typename = "WEBSERVICE";
}else if('01'==type){
	typename = "���ݿ�";
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

// ���沢�ر�
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
		alert("������Դ���͡�����Ϊ��!");
		return;
	}
	if(data_source_name==null||data_source_name==""){
		alert("������Դ���ơ�����Ϊ��!");
		return;
	}
	if(service_targets_id==null||service_targets_id==""){
		alert("������������󡿲���Ϊ��!");
		return;
	}
	if(db_username==null||db_username==""){
		alert("������Դ�û���������Ϊ��!");
		return;
	}
	if(db_password==null||db_password==""){
		alert("������Դ���롿����Ϊ��!");
		return;
	}
	if(!isIP(data_source_ip)){
		alert("������Ϸ��ġ�����ԴIP��!");
		return;
	}
	if(!check_port(access_port)){
		alert("������Ϸ��ġ����ʶ˿ڡ�!");
		return;
	}
	if(access_url==null||access_url==""){
		alert("������URL������Ϊ��!");
		return;
	}
	if(req_que_name==null||req_que_name==""){
		alert("������������ơ�����Ϊ��!");
		return;
	}
	//if(access_url==null||access_url==""){
		//alert("������URL������Ϊ��!");
		//return;
	//}
	
	
	
	
	//saveAndExit( '����ɹ�', '����ʧ��','/txn20102001.do' );	// /txn20102001.do
	var page = new pageDefine( "/txn20102003.ajax", "����webservice����Դ" );
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
		alert("����ʧ��!");
		alert(errDesc);
	}else{
	
		alert("����ɹ�!");
		parent.func_record_goBack();
		//window.close();
	}
}

// �� ��
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

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body >
<freeze:title caption="��������Դ��Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn20102027">
  <freeze:block property="record" caption="��������Դ��Ϣ" width="95%">
      <freeze:button name="record_testRecord" caption="��������" hotkey="SAVE" onclick="func_testConn();"/>
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��"hotkey="CLOSE" onclick="func_record_goBack();"/>
      
      <freeze:hidden property="data_source_id" caption="����ԴID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="data_source_type" caption="����Դ����"  value="03"  style="width:95%" />
      <freeze:browsebox property="service_targets_id" caption="�����������" show="name" valueset="��Դ����_�����������" onchange="setName();"  notnull="true" style="width:95%"/>
      <freeze:text property="data_source_name" caption="����Դ����" datatype="string" maxlength="100" notnull="true" style="width:95%"/>
      <freeze:text property="db_username" caption="����Դ�û���" datatype="string" maxlength="50" notnull="true" style="width:95%"/>
      <freeze:text property="db_password" caption="����Դ����" maxlength="50" notnull="true" style="width:95%"/>
      <freeze:text property="data_source_ip" caption="����ԴIP" datatype="string" maxlength="50" notnull="true" style="width:95%"/>
      <freeze:text property="access_port" caption="���ʶ˿�" datatype="string" maxlength="8" notnull="true" style="width:95%"/>
      <freeze:text property="req_que_name" caption="�����������" datatype="string" maxlength="100" notnull="true" style="width:95%"/>
      <freeze:text property="res_que_name" caption="���ն�������" datatype="string" maxlength="100" notnull="true" style="width:95%"/>
      
      
      
      <freeze:textarea property="access_url" caption="����URL" colspan="2" rows="2" maxlength="2000" notnull="true" style="width:98%"/>
      
      <freeze:textarea property="db_desc" caption="����Դ����" colspan="2" rows="2" maxlength="2000" style="width:98%"/>
      
      <freeze:hidden property="db_type" caption="���ݿ�����" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="db_instance" caption="���ݿ�ʵ��" datatype="string" maxlength="30" style="width:95%"/>
      <freeze:hidden property="db_status" caption="����Դ״̬" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="is_markup" caption="��Ч���" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="creator_id" caption="������ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="created_time" caption="����ʱ��" datatype="string" maxlength="19" style="width:95%"/>
      <freeze:hidden property="last_modify_id" caption="����޸���ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="last_modify_time" caption="����޸�ʱ��" datatype="string" maxlength="19" style="width:95%"/>  
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
