<%@page import="com.gwssi.common.util.DateUtil"%>
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ taglib uri="/WEB-INF/gwssi.tld" prefix="gwssi" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ����Դ�б�</title>
<script type="text/javascript" src="/script/lib/jquery171.js"></script>
<jsp:include page="/page/share/common/top_datepicker.html"></jsp:include>
<style type="text/css">
li.disabled {cursor: default;}
.pack-up .pack-list hr.hid{display: none;}
.modal_window .pack-list hr.hid{display:block;width: 600px;
.choose_date{width: 196px; height: 1.8em; display:block;font-size: 62.5%; }
}
</style>
<style type="text/css">
.modal_window {
	border: 1px solid #006699;
	color: #006699;
	position: absolute;
	width: 200px;
	height: 200px;
	left: 50%;
	top: 50%;
	margin-left: -100px;
	margin-top: -100px;
}
</style>
</head>

<script type="text/javascript" >
	// ��������Դ��
	function func_record_addRecord() {
		//var page = new pageDefine( "insert-res_data_source.jsp", "��������Դ��", "modal" );
		var page = new pageDefine("insert-res_data_source_tab.jsp", "��������Դ",
				"modal");

		page.updateRecord();
	}
	// �޸�����Դ��-ҳ�水ťչʾ
	function func_record_updateRecord1() {

		var type = getFormFieldValues("record:data_source_type");

		if (type == "FTP") {
			var page = new pageDefine("/txn20102014.do", "�޸�����Դ", "modal");
			page.addParameter("record:data_source_id",
					"primary-key:data_source_id");
			page.updateRecord();
		} else if (type == "���ݿ�") {
			var page = new pageDefine("/txn20102015.do", "�޸�����Դ", "modal");
			page.addParameter("record:data_source_id",
					"primary-key:data_source_id");
			page.updateRecord();
		} else {
			var page = new pageDefine("/txn20102004.do", "�޸�����Դ��", "modal");
			page.addParameter("record:data_source_id",
					"primary-key:data_source_id");
			page.updateRecord();
		}
	}
	// �鿴����Դ-ҳ�水ťչʾ
	function func_record_viewRecord1(index) {
		//var type = getFormFieldValues("record:data_source_type");

		var gridname = getGridDefine("record");
		var type = gridname.getAllFieldValues("data_source_type")[index];
		var id = gridname.getAllFieldValues("data_source_id")[index];

		// var param = "primary-key:jbxx_pk=" + gridname.getAllFieldValues( "jbxx_pk" )[index];

		if (type == "FTP") {
			var page = new pageDefine("/txn20102010.do", "�鿴����Դ", "modal",
					"600", "450");
			//page.addParameter( "record:data_source_id", "primary-key:data_source_id" );
			page.addValue(id, "primary-key:data_source_id");
			page.updateRecord();
		} else if (type == "���ݿ�") {
			var page = new pageDefine("/txn20102011.do", "�鿴����Դ", "modal",
					"600", "450");
			page.addValue(id, "primary-key:data_source_id");
			page.updateRecord();
		} else if (type == "SOCKET") {
			var page = new pageDefine("/txn20102026.do", "�鿴����Դ", "modal",
					"600", "450");
			page.addValue(id, "primary-key:data_source_id");
			page.updateRecord();
		} else if (type == "JMS��Ϣ") {
			var page = new pageDefine("/txn20102029.do", "�鿴����Դ", "modal",
					"600", "450");
			page.addValue(id, "primary-key:data_source_id");
			page.updateRecord();
		}else {
			var page = new pageDefine("/txn20102006.do", "�鿴����Դ", "modal",
					"600", "450");
			page.addValue(id, "primary-key:data_source_id");
			page.updateRecord();
		}

	}

	//��������-ҳ�水ť
	function func_test_Conn1() {

		var type = getFormFieldValues("record:data_source_type");
		//alert(type);
		if (type == "FTP") {
			var page = new pageDefine("/txn20102009.ajax", "��������");
			page.addParameter("record:data_source_ip", "record:url");
			page.addParameter("record:db_username", "record:db_username");
			page.addParameter("record:db_password", "record:db_password");
			page.addParameter("record:access_port", "record:access_port");
			page.callAjaxService("testConnCallBack");

		} else if (type == "���ݿ�") {
			var page = new pageDefine("/txn20102016.ajax", "��������");
			var db_type = getFormFieldValues("record:db_type");

			page.addValue(db_type, "record:db_type");
			page.addParameter("record:data_source_ip", "record:data_source_ip");
			page.addParameter("record:access_port", "record:access_port");
			page.addParameter("record:db_instance", "record:db_instance");
			page.addParameter("record:db_username", "record:db_username");
			page.addParameter("record:db_password", "record:db_password");
			page.callAjaxService("testConnCallBack");
		} else {
			//alert("��δ����");
			//_formSubmit(null,'���ڴ�����');
			var page = new pageDefine("/txn20102017.ajax", "��������");

			page.addParameter("record:access_url", "record:access_url");
			page.addParameter("record:access_port", "record:access_port");
			page.addParameter("record:data_source_ip", "record:data_source_ip");
			page.callAjaxService("testConnCallBack");
		}

	}
	// ɾ������Դ��--ҳ�水ť
	function func_record_deleteRecord1() {
		var page = new pageDefine("/txn20102005.do", "ɾ������Դ��");
		page
				.addParameter("record:data_source_id",
						"primary-key:data_source_id");
		page.deleteRecord("�Ƿ�ɾ��ѡ�еļ�¼");
	}

	// �޸�����Դ��
	function func_record_updateRecord(type, id) {

		//var type = getFormFieldValues("record:data_source_type",i);
		//var id = getFormFieldValues("record:data_source_id",i);

		if (type == "FTP") {
			var page = new pageDefine("/txn20102014.do", "�޸�����Դ","modal");
			page.addValue(id, "primary-key:data_source_id");

			page.updateRecord();
		} else if (type == "���ݿ�") {
			var page = new pageDefine("/txn20102015.do", "�޸�����Դ","modal");
			page.addValue(id, "primary-key:data_source_id");

			page.updateRecord();
		} else if (type == "SOCKET") {
			var page = new pageDefine("/txn20102024.do", "�޸�����Դ","modal");
			page.addValue(id, "primary-key:data_source_id");

			page.updateRecord();
		} else if (type == "JMS��Ϣ") {
			var page = new pageDefine("/txn20102031.do", "�޸�����Դ","modal");
			page.addValue(id, "primary-key:data_source_id");

			page.updateRecord();
		} else {
			var page = new pageDefine("/txn20102004.do", "�޸�����Դ��","modal");
			page.addValue(id, "primary-key:data_source_id");

			page.updateRecord();
		}
	}

	// �鿴����Դ
	function func_record_viewRecord(type, id) {

		//var type = getFormFieldValues("record:data_source_type",i);
		//var id = getFormFieldValues("record:data_source_id",i);

		if (type == "FTP") {
			var page = new pageDefine("/txn20102010.do", "�鿴����Դ", "modal",
					"600", "450");
			page.addValue(id, "primary-key:data_source_id");
			page.updateRecord();
		} else if (type == "���ݿ�") {
			var page = new pageDefine("/txn20102011.do", "�鿴����Դ", "modal",
					"600", "450");
			page.addValue(id, "primary-key:data_source_id");
			page.updateRecord();
		}else if (type == "SOCKET") {
			var page = new pageDefine("/txn20102026.do", "�鿴����Դ", "modal",
					"600", "450");
			page.addValue(id, "primary-key:data_source_id");
			page.updateRecord();
		} else {
			var page = new pageDefine("/txn20102006.do", "�鿴����Դ", "modal",
					"600", "450");
			page.addValue(id, "primary-key:data_source_id");
			page.updateRecord();
		}

	}

	// �鿴����Դ
	function func_record_viewRecord_link(index) {

		//var type = getFormFieldValues("record:data_source_type",i);
		//var id = getFormFieldValues("record:data_source_id",i);

		if (type == "FTP") {
			var page = new pageDefine("/txn20102010.do", "�鿴����Դ", "modal",
					"600", "450");
			page.addValue(id, "primary-key:data_source_id");
			page.updateRecord();
		} else if (type == "���ݿ�") {
			var page = new pageDefine("/txn20102011.do", "�鿴����Դ", "modal",
					"600", "450");
			page.addValue(id, "primary-key:data_source_id");
			page.updateRecord();
		} else if (type == "SOCKET") {
			var page = new pageDefine("/20102026.do", "�鿴����Դ", "modal",
					"600", "450");
			page.addValue(id, "primary-key:data_source_id");
			page.updateRecord();
		} else {
			var page = new pageDefine("/txn20102006.do", "�鿴����Դ", "modal",
					"600", "450");
			page.addValue(id, "primary-key:data_source_id");
			page.updateRecord();
		}

	}

	//�鿴�������
	function func_viewConfig(idx) {
		
		var svrId = getFormFieldValue("record:service_targets_id", idx);
		var page = new pageDefine( "/txn201009.do", "�鿴�������","modal" );
		page.addValue( svrId, "primary-key:service_targets_id" );
		page.updateRecord();
	}

	//��������
	function func_test_Conn(type, data_source_ip, db_username, db_password,
			access_port, db_type, db_instance, access_url) {
		//alert(type + ' === ' + access_url);
		if (type == "FTP") {
			var page = new pageDefine("/txn20102009.ajax", "��������");
			page.addValue(data_source_ip, "record:url");
			page.addValue(db_username, "record:db_username");
			page.addValue(unescape(db_password), "record:db_password");
			page.addValue(access_port, "record:access_port");
			_showProcessHintWindow("����������,���Ժ�...");
			page.callAjaxService("testConnCallBack");

		} else if (type == "���ݿ�") {
			var page = new pageDefine("/txn20102016.ajax", "��������");
			//var db_type=getFormFieldValues("record:db_type");

			page.addValue(db_type, "record:db_type");
			page.addValue(data_source_ip, "record:data_source_ip");
			page.addValue(access_port, "record:access_port");
			page.addValue(db_instance, "record:db_instance");
			page.addValue(db_username, "record:db_username");
			page.addValue(unescape(db_password), "record:db_password");
			_showProcessHintWindow("����������,���Ժ�...");
			page.callAjaxService("testConnCallBack");
		} else if (type == "WebService") {
			var page = new pageDefine("/txn20102017.ajax", "��������");
			page.addValue(access_url, "record:access_url");
			page.addValue(access_port, "record:access_port");
			page.addValue(data_source_ip, "record:data_source_ip");
			_showProcessHintWindow("����������,���Ժ�...");
			page.callAjaxService("testConnCallBack");
		}else if (type == "SOCKET") {
			var page = new pageDefine("/txn20102023.ajax", "��������");
			page.addValue(access_url, "record:access_url");
			page.addValue(access_port, "record:access_port");
			page.addValue(data_source_ip, "record:data_source_ip");
			_showProcessHintWindow("����������,���Ժ�...");
			page.callAjaxService("testConnCallBack");
		}else if (type == "JMS��Ϣ") {
			var page = new pageDefine("/txn20102030.ajax", "��������");
			page.addValue(access_url, "record:access_url");
			page.addValue(access_port, "record:access_port");
			page.addValue(data_source_ip, "record:data_source_ip");
			_showProcessHintWindow("����������,���Ժ�...");
			page.callAjaxService("testConnCallBack");
		} else {
			return;
		}

	}

	function testConnCallBack(errorCode, errDesc, xmlResults) {
		//alert(_getXmlNodeValue(xmlResults, "record:result"));
		if (errorCode != "000000") {
			alert(errDesc);
		} else {
			var result = _getXmlNodeValue(xmlResults, "record:result");
			if (result == "1") {
				//alert("���ӳɹ�!");
				_showProcessHintWindow("<span style='color:green;'>���ӳɹ�!</span><br/><a href='javascript:_hideProcessHintWindow();'>�ر�</a>");
			} else {
				//alert("����ʧ��!");
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

	// ɾ������Դ��
	function func_record_deleteRecord(id) {

		//checkDataSourceUse(id);

		var page = new pageDefine("/txn20102005.do", "ɾ������Դ��");
		//var id = getFormFieldValues("record:data_source_id",i);

		page.addValue(id, "primary-key:data_source_id");
		page.deleteRecord("�Ƿ�ɾ��ѡ�е�����Դ");
	}

	function checkDataSourceUse(id) {
		if (id == '') {
			alert("δѡ������Դ");
			return;
		}
		var page = new pageDefine("/txn20102019.ajax", "�������Դ�Ƿ�����");
		page.addValue(id, "primary-key:data_source_id");
		page.callAjaxService("checkCallBack");
	}

	function checkCallBack(errorCode, errDesc, xmlResult) {
		if (errorCode != "000000") {
			alert(errDesc);
		} else {

			var num = _getXmlNodeValue(xmlResult, "record:data_source_id");
			var id = _getXmlNodeValue(xmlResult, "primary-key:data_source_id");

			if (num == "" || num == null) {
				func_record_deleteRecord(id);
			} else {
				alert('������Դ����ʹ�ã�������ɾ��!');
				return;
			}
		}
	}

	function getParameter() {
		var id = getFormFieldValue('select-key:service_targets_id');
		//alert("id="+id);
		if (id == null || id == "") {
			id = "0000";
		}
		var parameter = 'service_targets_id=' + id;
		//var parameter='service_targets_id='+getFormFieldValue('select-key:service_targets_id');
		return parameter;
	}

	function setDataSource(){
	     setFormFieldValue('select-key:data_source_id','');
	 
	}

	// ����������ӣ�ҳ�������ɺ���û���ʼ������
	function __userInitPage() {
		var id = getFormAllFieldValues("record:data_source_id");
		var type = getFormAllFieldValues("record:data_source_type");
		var data_source_ip = getFormAllFieldValues("record:data_source_ip");
		var db_username = getFormAllFieldValues("record:db_username");
		var db_password = getFormAllFieldValues("record:db_password");
		
		var access_port = getFormAllFieldValues("record:access_port");
		var db_type = getFormAllFieldValues("record:db_type");
		var db_instance = getFormAllFieldValues("record:db_instance");
		var access_url = getFormAllFieldValues("record:access_url");

		for ( var i = 0; i < id.length; i++) {
			document.getElementsByName("span_record:operate")[i].innerHTML += '<a href="javascript:void(\'0\');" title="�޸�" onclick="func_record_updateRecord(\''
					+ type[i]
					+ '\',\''
					+ id[i]
					+ '\')"><div class="edit"></div></a>&nbsp;'
					+ '<a href="javascript:void(\'0\')" title="ɾ��" onclick="checkDataSourceUse(\''
					+ id[i]
					+ '\');"><div class="delete"></div></a>&nbsp;'
					+ '<a href="javascript:void(\'0\')" title="��������" onclick="func_test_Conn(\''
					+ type[i]
					+ '\',\''
					+ data_source_ip[i]
					+ '\',\''
					+ db_username[i]
					+ '\',\''
					+ escape(db_password[i])
					+ '\',\''
					+ access_port[i]
					+ '\',\''
					+ db_type[i]
					+ '\',\''
					+ db_instance[i]
					+ '\',\''
					+ access_url[i]
					+ '\');"><div class="config"></div></a>&nbsp;';
		}

		var names = getFormAllFieldValues("record:data_source_name");
		var targetnames = getFormAllFieldValues("record:service_targets_name");
		for ( var i = 0; i < names.length; i++) {
			htm = '<a href="#" title="����鿴��ϸ��Ϣ" onclick="func_record_viewRecord1(\''
					+ i + '\');">' + names[i] + '</a>';
			var htm2 = '<a href="#" title="����鿴��ϸ��Ϣ" onclick="func_viewConfig(\''
						+ i + '\');">' + targetnames[i] + '</a>';
			document.getElementsByName("span_record:data_source_name")[i].innerHTML = htm;
			document.getElementsByName("span_record:service_targets_name")[i].innerHTML = htm2;
		}
	}

	_browse.execute('__userInitPage()');
</script>
<freeze:body>
<freeze:title caption="��ѯ����Դ�б�"/>
<freeze:errors/>
 <gwssi:panel action="txn20102020" target="" parts="t1,t2,t3" styleClass="wrapper">
  <gwssi:cell id="t1" name="����Դ����" key="data_source_type" data="dsType" />
  <gwssi:cell id="t2" name="��&nbsp;��&nbsp;��&nbsp;��" key="service_targets_type,service_targets_id" isGroup="true" data="svrTarget"  maxsize="10" />
  <gwssi:cell id="t3" name="��&nbsp;��&nbsp;��&nbsp;��" key="created_time" data="svrInterface" date="true"/>
 </gwssi:panel>
	<freeze:form action="/txn20102020">
	  <freeze:frame property="select-key" >
     <freeze:hidden property="data_source_type" caption="����Դ����" />
     <freeze:hidden property="service_targets_id" caption="��&nbsp;��&nbsp;��&nbsp;��" />
     <freeze:hidden property="created_time" caption="��&nbsp;��&nbsp;��&nbsp;��" />
  </freeze:frame>
		<freeze:grid property="record" caption="��ѯ����Դ�б�" keylist="data_source_id" width="95%" multiselect="false" checkbox="false" navbar="bottom" fixrow="false">

			<freeze:button name="record_addRecord" caption="��������Դ" txncode="20102003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();" />
			<freeze:cell property="@rowid" caption="���" style="width:6%" align="center" />
			<freeze:hidden property="service_targets_id" caption="�������" onclick="func_viewConfig();" style="color:#FF0000;width:20%;" />
			<freeze:link property="service_targets_name" caption="�����������" style="width:19%;  text-align: center;" />
			<freeze:cell property="data_source_name" caption="����Դ����" style="" />
			<freeze:cell property="data_source_type" caption="����Դ����" valueset="��Դ����_����Դ����" style="width:12%" />
			<freeze:cell property="data_source_ip" caption="����ԴIP" style="width:14%" />
			
		  <freeze:cell property="name" align="center" caption="����޸���"  style="width:10%"/>
	      <freeze:cell property="time" align="center" caption="����޸�����"  style="width:10%"/>
			
			<freeze:cell property="operate" caption="����" style="width:90px" />

			<freeze:hidden property="data_source_id" caption="����ԴID" style="width:10%" />
			<freeze:hidden property="access_port" caption="���ʶ˿�" style="width:10%" />
			<freeze:hidden property="access_url" caption="����URL" style="width:20%" />
			<freeze:hidden property="db_type" caption="���ݿ�����" style="width:12%" />
			<freeze:hidden property="db_instance" caption="����Դʵ��" style="width:12%" />
			<freeze:hidden property="db_username" caption="����Դ�û���" style="width:16%" />
			<freeze:hidden property="db_password" caption="����Դ�û�����" style="width:16%" />
			<freeze:hidden property="db_desc" caption="����Դ����" style="width:20%" />
			<freeze:hidden property="db_status" caption="����Դ״̬" style="width:12%" />
			<freeze:hidden property="is_markup" caption="��Ч���" style="width:10%" />
			
		</freeze:grid>



	</freeze:form>
</freeze:body>
</freeze:html>
