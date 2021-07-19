<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�޸Ĳɼ����ݿ���Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	//saveAndExit( '', '����ɼ����ݿ��' );	// /txn30501001.do
	var collect_mode = getFormFieldValue("record:collect_mode");
	var source_collect_table = getFormFieldValue("record:source_collect_table");
	var collect_table = getFormFieldValue("record:collect_table");
	var source_collect_column = getFormFieldValue("record:source_collect_column");
	
	if(collect_mode==null || collect_mode==""){
		alert("���ɼ���ʽ������Ϊ��!");
		return;
	}
	
	if(collect_table==null || collect_table==""){
		alert("��Ŀ��ɼ�������Ϊ��!");
		return;
	}
	if(source_collect_table==null || source_collect_table==""){
		alert("��Դ�ɼ�������Ϊ��!");
		return;
	}
	
	if(collect_mode=="1"){//����
		if(source_collect_column==null || source_collect_column==""){
			alert("�������ֶΡ�����Ϊ��!");
			return;
		}	
	}else if (collect_mode=="2"){//ȫ��
		setFormFieldValue("record:source_collect_column",0,"");
	}
	
	
	var page = new pageDefine( "/txn30501006.ajax", "�������ݿ������Ƿ��ظ�" );
	page.addParameter("record:collect_task_id","record:collect_task_id");
	page.addParameter("record:source_collect_table","record:source_collect_table");
	page.addParameter("record:collect_table","record:collect_table");
	page.callAjaxService("checkTaskBack");
	
}

function checkTaskBack(errorCode, errDesc, xmlResults){
	if(errorCode!="000000"){
		alert(errDesc);
	}else{
		var task_num = _getXmlNodeValue(xmlResults,"record:task_num");
		if(task_num>0){
			alert("�����ظ���");
			return;
		}else{
			saveAndExit( '', '����ɼ����ݿ��' );	// /txn30501001.do
		}
	}
}


// �� ��
function func_record_goBack()
{
	goBack();	// /txn30501001.do
}

function getParameter(){
	//�ӵ�ǰҳ��ȡֵ�����key=value��ʽ�Ĵ�
    var parameter='input-data:service_targets_id='+getFormFieldValue('record:service_targets_id');
	return parameter;
}

function getParameterForTable(){
	//�ӵ�ǰҳ��ȡֵ�����key=value��ʽ�Ĵ�  CollectConstants.TYPE_CJLX_FILEUPLOADֵ01������Դ�����ݿ�����
	var data_source_id = getFormFieldValue("record:data_source_id");
	
	if(data_source_id==null || data_source_id == ""){
		alert("����ѡ������Դ��");
	}else{
		var parameter = "input-data:data_source_id="+ getFormFieldValue('record:data_source_id');
		return parameter;
	}
}

function getParameterForTableColumn(){
	//�ӵ�ǰҳ��ȡֵ�����key=value��ʽ�Ĵ�  CollectConstants.TYPE_CJLX_FILEUPLOADֵ01������Դ�����ݿ�����
	var data_source_id = getFormFieldValue("record:data_source_id");
	var source_collect_table = getFormFieldValue("record:source_collect_table");
	
	if(source_collect_table==null || source_collect_table == ""){
		alert("����ѡ��Դ�ɼ�����");
	}else{
		var parameter = "input-data:data_source_id="+ getFormFieldValue('record:data_source_id')+"&input-data:source_collect_table="+ getFormFieldValue('record:source_collect_table');
		return parameter;
	}
}

function funTBChanged(){
	var collect_mode = getFormFieldValue('record:collect_mode');
	
	if(collect_mode=='1'){//����
	setDisplay("2",true);
	
	}else if(collect_mode=='2'){//ȫ��
	setDisplay("2",false);
	
	}
}

function setDisplay(id,b){
	
	var preStr = "row_"+id;
	if(b=='true'||b==true){
		document.getElementById(preStr).style.display='block';
		var cells = document.getElementById(preStr).cells;
		for(var ii=0;ii<cells.length;ii++){
			cells[ii].className='odd12';
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
<freeze:body onload="funTBChanged();">
<freeze:title caption="�޸Ĳɼ����ݿ���Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn30501002">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="database_task_id" caption="�ɼ����ݿ�����ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�޸Ĳɼ����ݿ���Ϣ" width="95%">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="service_targets_id" caption="�����������"   style="width:95%"/>
      <freeze:hidden property="data_source_id" caption="����Դ"    style="width:95%"/>
      <freeze:cell property="task_name" caption="��������" datatype="string"  style="width:95%"/>
      <freeze:select property="collect_mode" caption="�ɼ���ʽ" onchange="funTBChanged();" show="name" notnull="true" valueset="��Դ����_�ɼ���ʽ" style="width:95%"/>  
      <freeze:browsebox property="source_collect_table" caption="Դ�ɼ���"  valueset="�ɼ�����_��������Դȡ�Է����е����б�" notnull="true" parameter="getParameterForTable();"  style="width:95%"/>
      <freeze:browsebox property="collect_table" caption="Ŀ��ɼ���"  notnull="true" maxlength="32" valueset="��Դ����_��������Ӧ�ɼ���" show="name" parameter="getParameter();" style="width:95%"/>
      <freeze:browsebox property="source_collect_column" caption="�����ֶ�" valueset="�ɼ�����_��������Դȡ�Է����б���ֶ���Ϣ"  parameter="getParameterForTableColumn();" colspan="2"  maxlength="100"  style="width:38.5%"/>
      <freeze:textarea property="description" caption="˵��" colspan="2" rows="2" maxlength="2000" style="width:98%"/>
      <freeze:hidden property="created_time" caption="����ʱ��" datatype="string" maxlength="19"  style="width:95%"/>
      <freeze:hidden property="database_task_id" caption="�ɼ����ݿ�����ID" datatype="string" maxlength="32"  style="width:95%"/>
      <freeze:hidden property="collect_task_id" caption="�ɼ�����ID" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
