<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="750" height="350">
<head>
<title>�޸�����Դ��Ϣ</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/page/checkIP.js"></script>
<script language="javascript">

// ��������
function func_testConn()
{
	var page = new pageDefine( "/txn20102009.ajax", "��������" );
	var url = getFormFieldValue("record:data_source_ip");
	var data_source_ip = getFormFieldValue("record:data_source_ip");
	var access_port = getFormFieldValue("record:access_port");
	var db_username = getFormFieldValue("record:db_username");
	var db_password = getFormFieldValue("record:db_password");
	if(!isIP(data_source_ip)){
		alert("������Ϸ��ġ�����ԴIP��!");
		
		return;
	}
	
	if(access_port==null || access_port==""){
		alert("�����ʶ˿ڡ�����Ϊ��!");
		return;
	}else{
		if(!check_port(access_port))
		{
			alert("������Ϸ��ġ����ʶ˿ڡ�!");
			return;
		}
	}
	if(db_username==null || db_username==""){
		alert("������ԭ�û���������Ϊ��!");
		return;
	}
	if(db_password==null || db_password==""){
		alert("������ԭ���롿����Ϊ��!");
		return;
	}
	
	page.addParameter("record:data_source_ip","record:url");
	page.addParameter("record:db_username","record:db_username");
	page.addParameter("record:db_password","record:db_password");
	page.addParameter("record:access_port","record:access_port");
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
function func_record_saveAndExit()
{
	var data_source_ip = getFormFieldValue("record:data_source_ip");
	var access_port = getFormFieldValue("record:access_port");
	var access_url = getFormFieldValue("record:access_url");
	if(!isIP(data_source_ip)){
		alert("������Ϸ��ġ�����ԴIP��!");
		return;
	}
	if(!check_port(access_port)){
		alert("������Ϸ��ġ����ʶ˿ڡ�!");
		return;
	}
	if(access_url==null||access_url==""){
		alert("���ļ�·��������Ϊ��!");
		return;
	}
	if(access_url=='//'){
		alert("���ļ�·������������Ч��·��!");
		return;
	}
	
	/* if(access_url!='/' && ( access_url.substr(0,1)!='/' || access_url.substr(access_url.length-1,1)!='/' ) ){
		alert("���ļ�·���������ԡ�/����ͷ�����ԡ�/����β");
		return ;
	} */
	saveAndExit( '', '��������Դ��' );	// /txn20102001.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn20102001.do
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
}else {
	typename = "";
}
	document.getElementById("record:data_source_name").value =name+"_"+typename ;
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�޸�����Դ��Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn20102012">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="data_source_id" caption="����ԴID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸�����Դ��Ϣ" width="95%">
      <freeze:button name="record_testRecord" caption="��������" hotkey="SAVE" onclick="func_testConn();"/>
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��"hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="data_source_id" caption="����ԴID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:browsebox property="service_targets_id" caption="�����������" show="name" valueset="��Դ����_�����������"  onchange="setName();" notnull="true" maxlength="32" style="width:95%"/>
      <freeze:select property="data_source_type" caption="����Դ����" show="name" valueset="��Դ����_����Դ����" readonly="true" style="width:95%"/>
      <freeze:text property="data_source_name" caption="����Դ����" notnull="true" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="db_username" caption="����Դ�û���" datatype="string" notnull="true" maxlength="50" style="width:95%"/>
      <freeze:text property="db_password" caption="����Դ����" maxlength="50" notnull="true" style="width:95%"/>
      <freeze:text property="data_source_ip" caption="����ԴIP" datatype="string" maxlength="50" notnull="true" style="width:95%"/>
      <freeze:text property="access_port" caption="���ʶ˿�" datatype="string" maxlength="8" colspan="2" notnull="true" style="width:98%"/>
      <freeze:text property="access_url" notnull="true" caption="�ļ�·��" colspan="2" title="�ļ�·��Ҫ�ԡ�/����ʼ�ͽ���" maxlength="1000" style="width:98%"/>
      
      <freeze:textarea property="db_desc" caption="����Դ����" colspan="2" rows="2" maxlength="2000" style="width:98%"/>
      <freeze:cell property="crename" caption="�����ˣ�" datatype="string"  style="width:95%"/>
      <freeze:cell property="cretime" caption="����ʱ�䣺" datatype="string"  style="width:95%"/>
      <freeze:cell property="modname" caption="����޸��ˣ�" datatype="string"  style="width:95%"/>
      <freeze:cell property="modtime" caption="����޸�ʱ�䣺" datatype="string"  style="width:95%"/>
      
      
      
      
      <freeze:hidden property="db_type" caption="���ݿ�����" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="db_instance" caption="����Դʵ��" datatype="string" maxlength="30" style="width:95%"/>
      <freeze:hidden property="req_que_name" caption="�����������" datatype="string" maxlength="100"  style="width:95%"/>
      <freeze:hidden property="res_que_name" caption="���ն�������" datatype="string" maxlength="100"  style="width:95%"/>
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
