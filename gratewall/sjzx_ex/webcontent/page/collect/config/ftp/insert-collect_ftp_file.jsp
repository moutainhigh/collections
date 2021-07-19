<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="750" height="350">
<head>
<title>���Ӳɼ����ݿ���Ϣ</title>

<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery1.1.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>

</head>

<script language="javascript">

function checkInput(){
	var file_name_en=getFormFieldValue('record:file_name_en');
	if(!file_name_en){
		alert("�������ļ�Ӣ�����ơ�����������Ч����!");
		return false;
	}
	var collect_mode=getFormFieldValue('record:collect_mode');
	if(!collect_mode){
		alert("�����򡾲ɼ���ʽ������ѡ��һ����Ч����!");
		return false;
	}
	var nameType= getFormFieldValue('record:name_type');
	var check=/^((-\d+)|(0+))$/; //��������
	if(nameType=='D'){
		var day_num=getFormFieldValue('record:day_num');
		if(day_num.length>5){
			alert("����������ƫ�ơ��������Ƴ���");
			return false;
		}
		if(!check.test(day_num)){
			alert("����������ƫ�ơ�Ҫ������һ����������0");
			return false;
		}
		
	}else if(nameType=='M'){
		var month=getFormFieldValue('record:month');
		if(month.length>5){
			alert("�������·ݡ��������Ƴ���");
			return false;
		}
		if(!check.test(month)){
			alert("�������·ݡ�Ҫ������һ����������0");
			return false;
		}
		var day_month=getFormFieldValue('record:day_month');
		if(!day_month){
			alert("���������ڡ���ѡ��һ����Ч����");
			return false;
		}
	}else{
		alert("�����򡾺�׺������ʽ������");
		return false;
	}
	return true;
}

// ���沢�ر�
function func_record_saveAndExit()
{
	if(!checkInput()){
		return;
	}
	
	var page = new pageDefine( "/txn30101107.ajax","����");
	page.addParameter("record:collect_task_id","select-key:collect_task_id");
	page.addParameter("record:task_name","select-key:task_name");
	page.addParameter("record:flag","select-key:flag");
	page.addParameter("record:service_targets_id","select-key:service_targets_id");
	page.addParameter("record:ftp_task_id","select-key:ftp_task_id");
	page.addParameter("record:file_name_en","record:file_name_en");
	page.addParameter("record:file_name_cn","record:file_name_cn");
	page.addParameter("record:file_description","record:file_description");
	page.addParameter("record:collect_table","record:collect_table");
	page.addParameter("record:collect_mode","record:collect_mode");
	page.addParameter("record:file_sepeator","record:file_sepeator");
	page.addParameter("record:file_title_type","record:file_title_type");
	page.addParameter("record:month","record:month");
	page.addParameter("record:day_month","record:day_month");
	page.addParameter("record:name_type","record:name_type");
	page.addParameter("record:day_num","record:day_num");
	page.callAjaxService("checkTaskBack");
	
}

function checkTaskBack(errorCode, errDesc, xmlResults){
	if(errorCode!="000000"){
		alert(errDesc);
	}else{
		/* var service_targets_id=getFormFieldValue('record:service_targets_id');
		var collect_task_id=getFormFieldValue('record:collect_task_id');
		var task_name=getFormFieldValue('record:task_name');
		var flag=getFormFieldValue('record:flag');
		var href='/txn30101110.do?record:service_targets_id='+service_targets_id
				+'&record:collect_task_id='+collect_task_id
				+'&record:task_name='+task_name
				+'&record:flag='+flag; */
		//parent.window.location.href=href;
		window.dialogArguments.func_refresh();
		//saveAndExit("","",href);
		window.close();
		
		
			
	}
}

window.onbeforeunload = function() { 
	window.dialogArguments.func_refresh();
	return ; 
} 
// �� ��
function func_record_goBack()
{
	window.dialogArguments.func_refresh();
	window.close();
	//goBack();	// /txn30501001.do
}

function getParameter(){
	//�ӵ�ǰҳ��ȡֵ�����key=value��ʽ�Ĵ�
    var parameter='input-data:service_targets_id='+getFormFieldValue('record:service_targets_id');
	return parameter;
}

function setNameType(obj){
	var nameType=$(obj).val();
	if(nameType=='D'){
		$('#row_7').hide();
		$('#row_8').show();
		
	}else if(nameType=='M'){
		$('#row_8').hide();
		$('#row_7').show();
	}
}
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	$(".radioNew").hide();
	$(".radioNew").prev().show();
	var nameType= getFormFieldValue('record:name_type');
	if(nameType=='D'){
		$('#row_7').hide();
		$('#row_8').show();
		
	}else if(nameType=='M'){
		$('#row_8').hide();
		$('#row_7').show();
	}
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="FTP�ɼ��ļ���Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn30101107">

  <freeze:block property="record" caption="FTP�ɼ��ļ���Ϣ" width="95%">
      <freeze:button name="record_saveAndExit" caption="���沢�ر�" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:cell property="task_name" caption="�ɼ���������" colspan="2" style="width:95%"/>
      <freeze:text property="file_name_en" caption="�ļ�Ӣ������" colspan="2" notnull="true" datatype="string" maxlength="100"  style="width:95%"/>
      <freeze:text property="file_name_cn" caption="�ļ���������" colspan="2" datatype="string" maxlength="100"  style="width:95%"/>
      
      <freeze:textarea property="file_description" caption="�ļ�����" colspan="2" rows="2" maxlength="2000" style="width:95%"/>
      <freeze:text property="file_sepeator" caption="�зָ���" notnull="true" datatype="string" maxlength="10"  style="width:95%"/>
      <freeze:browsebox property="file_title_type" caption="����������" notnull="true" show="name"  valueset="����������" style="width:95%"/>
      
      <freeze:browsebox property="collect_table" caption="�ɼ���" align="center"  valueset="��Դ����_��������Ӧ�ɼ���" show="name" parameter="getParameter();" style="width:95%" />
      <freeze:browsebox property="collect_mode" caption="�ɼ���ʽ" align="center" notnull="true" show="name" valueset="��Դ����_�ɼ���ʽ"  show="name" style="width:95%"/>
      
      <freeze:radio property="name_type" caption="��׺������ʽ"  value="M" colspan="2" valueset="�ļ����ں�׺������ʽ"  style="width:95%" onclick="setNameType(this);" ></freeze:radio>
     
      <freeze:text property="month" caption="�·�"  notnull="false" title="������һ��������0������,0��ʾ����,-1��ʾ��һ���£���������"   style="width:95%;"  />
      <freeze:browsebox property="day_month" caption="����" align="center" notnull="false" show="name" valueset="��Դ����_����ѡ��"  show="name" style="width:95%"/>
      <freeze:text property="day_num" caption="����ƫ��"  notnull="false" title="������һ��������0������,0��ʾ����,-1��ʾǰһ�죬��������"   style="width:95%;"  />
      
      <freeze:hidden property="ftp_task_id" caption="����ID" datatype="string" maxlength="32" />
      <freeze:hidden property="collect_task_id" caption="�ɼ�����ID" style="width:95%"/>
      
      <freeze:hidden property="service_targets_id" caption="�������ID" datatype="string" maxlength="32"  style="width:80%"/>
	  <freeze:hidden property="flag" caption="������������Ǳ༭"  notnull="true" />	
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
