<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="750" height="350">
<head>
<title>�鿴�ɼ����ݿ���Ϣ</title>
</head>

<script language="javascript">

// ���沢�ر�
function func_record_saveAndExit()
{
	saveAndExit( '', '����ɼ����ݿ��' );	// /txn30501001.do
	//var page = new pageDefine("/txn20202000.ajax", "������������Ƿ��Ѿ�ʹ��");	
	//page.addParameter("record:dataitem_name_en","primary-key:dataitem_name_en");
	//page.addParameter("record:collect_table_id","primary-key:collect_table_id");
	//page.callAjaxService('nameCheckCallback');
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
	
	alert("data_source_id="+data_source_id);
	if(data_source_id==null || data_source_id == ""){
		alert("����ѡ������Դ��");
	}else{
		var parameter = "input-data:data_source_id="+ getFormFieldValue('record:_tmp_data_source_id');
		return parameter;
	}
}

function getParameterForTableColumn(){
	//�ӵ�ǰҳ��ȡֵ�����key=value��ʽ�Ĵ�  CollectConstants.TYPE_CJLX_FILEUPLOADֵ01������Դ�����ݿ�����
	var data_source_id = getFormFieldValue("record:data_source_id");
	var source_collect_table = getFormFieldValue("record:source_collect_table");
	
	alert("source_collect_table="+source_collect_table);
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
<freeze:body>
<freeze:title caption="�鿴�ɼ����ݿ���Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn30501003">
  <freeze:block property="record" caption="�鿴�ɼ����ݿ���Ϣ" width="95%">
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="service_targets_id" caption="�����������"   style="width:95%"/>
      <freeze:hidden property="data_source_id" caption="����Դ"    style="width:95%"/>
      <freeze:cell property="task_name" caption="��������" datatype="string"  style="width:95%"/>
      <freeze:cell property="collect_mode" caption="�ɼ���ʽ"  show="name"  valueset="��Դ����_�ɼ���ʽ" style="width:95%"/>  
      <freeze:cell property="source_collect_table" caption="Դ�ɼ���"   style="width:95%"/>
      <freeze:cell property="collect_table" caption="Ŀ��ɼ���"  valueset="��Դ����_�ɼ���" style="width:95%"/>
      <freeze:cell property="source_collect_column" caption="�����ֶ�"  colspan="2" style="width:38.5%"/>
      <freeze:cell property="description" caption="˵��" colspan="2"  style="width:98%"/>
      <freeze:hidden property="created_time" caption="����ʱ��" datatype="string" maxlength="19"  style="width:95%"/>
      <freeze:hidden property="database_task_id" caption="�ɼ����ݿ�����ID" datatype="string" maxlength="32"  style="width:95%"/>
      <freeze:hidden property="collect_task_id" caption="�ɼ�����ID" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
