<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�����ļ��ϴ��ɼ�����Ϣ</title>
</head>
<script language='javascript' src='<%=request.getContextPath()%>/script/UUID.js'></script>
<script language="javascript">
var collect_joumal_id = UUID.prototype.createUUID ();
var save_flag = false;
// �� ��
//alert("parent="+window.parent);
function func_record_saveRecord()
{
	save_flag = true;
	setFormFieldValue('record:collect_joumal_id',collect_joumal_id);
	saveRecord( '', '�ļ��ϴ���������ʧ��!' );
	//saveAndExit( '�ɼ����ݳɹ�!', '�ɼ�����ʧ��!','/txn30101001.do' );	
	//confirm()
	//alert("����У����");
	//getResult(collect_joumal_id);
	
	//window.parent.func_record_goBack();
	//window.parent.location.href="/txn30101001.do";
	//setTimeout("window.parent.open('/txn30101001.do',window.parent.name)","5000");
	
	//window.parent.func_record_goBack();
}

//��ѯ������
function func_record_getResult()
{
	//alert("save_flag="+save_flag);
	if(save_flag==true){
		var page = new pageDefine( "/txn30101026.ajax", "�ɼ����" );
		page.addParameter("record:collect_joumal_id","record:collect_joumal_id");
		page.callAjaxService("collectResCallBack");
	}else{
		alert("����ִ�б��������");
	}
	
	
	
}
function collectResCallBack(errorCode, errDesc, xmlResults){
	
	if(errorCode!="000000"){
		alert(errDesc);
	}else{
		var collect_table_name = _getXmlNodeValue(xmlResults,"result:collect_table_name");
		var collect_data_amount = _getXmlNodeValue(xmlResults,"result:collect_data_amount");
		var task_consume_time = _getXmlNodeValue(xmlResults,"result:task_consume_time");
		
		var result = "�û����ã����βɼ����񣬲ɼ���["+collect_table_name+"]�ɼ�����["+collect_data_amount+"]��������ʱ"+task_consume_time+"��";
		alert(result);
		//parent.func_record_goBack();
	}
}

// ���沢����
function func_record_saveAndContinue()
{
	saveAndContinue( '', '�����ļ��ϴ��ɼ���' );
}

// ���沢�ر�
function func_record_saveAndExit()
{
	saveAndExit( '', '�����ļ��ϴ��ɼ���','txn30101001.do' );	// /txn30301001.do
}

// �� ��
function func_record_goBack()
{
	//goBack();	// /txn30301001.do  txn30101001
	parent.window.location.href="txn30101001.do";
}

function getParameter(){
	//�ӵ�ǰҳ��ȡֵ�����key=value��ʽ�Ĵ�
    var parameter='input-data:service_targets_id='+getFormFieldValue('record:service_targets_id');
	return parameter;
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�����ļ��ϴ��ɼ�����Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn30101021" enctype="multipart/form-data">
  <freeze:block property="record" caption="�����ļ��ϴ��ɼ�����Ϣ" width="95%">
      <freeze:button name="record_saveRecord" caption="�� ��" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_goBack" caption="�� ��"hotkey="CLOSE" onclick="func_record_goBack();"/>
      
      <freeze:hidden property="file_upload_task_id" caption="�ļ��ϴ�����ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:hidden property="collect_task_id" caption="�ɼ�����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="collect_joumal_id" caption="�ɼ���־ID" datatype="string" maxlength="32"  style="width:95%"/>
      
      <freeze:browsebox property="service_targets_id" caption="�����������" show="name" notnull="true" valueset="��Դ����_�����������"    style="width:95%"/>
      <freeze:text property="task_name" caption="��������" datatype="string" notnull="true" maxlength="100" style="width:95%"/>
      <freeze:browsebox property="collect_table" caption="�ɼ���"  notnull="true" maxlength="32" valueset="��Դ����_��������Ӧ�ɼ���" show="name" parameter="getParameter();" style="width:95%"/>
      <freeze:select property="collect_mode" caption="�ɼ���ʽ" show="name" notnull="true" valueset="��Դ����_�ɼ���ʽ" style="width:95%"/>
      <freeze:textarea property="task_description" caption="����˵��" colspan="2" rows="2" maxlength="2000" style="width:98%"/>
      <freeze:textarea property="record" caption="����˵��" colspan="2" rows="2" maxlength="2000" style="width:98%"/>
      
      <freeze:file property="fjmc1" caption="�ɼ��ļ�" style="width:80%" accept="*.xls,*.txt,*.mdb" maxlength="100" colspan="2"/>
      
      <freeze:hidden property="collect_file_name" caption="�ɼ��ļ�"  style="width:80%" maxlength="200" />
      <freeze:hidden property="collect_type" caption="�ɼ�����"  value="01"  style="width:95%" />
      <freeze:hidden property="data_source_id" caption="����Դ"  style="width:95%"/>
      <freeze:hidden property="fjmc" caption="�ļ��ϴ�" />
      <freeze:hidden property="fj_fk" caption="�ļ��ϴ�id" style="width:90%" />
      <freeze:hidden property="delIDs" caption="multi file id" style="width:90%"  />
      <freeze:hidden property="delNAMEs" caption="multi file name" style="width:90%" />
      <freeze:hidden property="task_status" caption="����״̬" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="is_markup" caption="��Ч���" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="creator_id" caption="������ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="created_time" caption="����ʱ��" datatype="string" maxlength="19" style="width:95%"/>
      <freeze:hidden property="last_modify_id" caption="����޸���ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="last_modify_time" caption="����޸�ʱ��" datatype="string" maxlength="19" style="width:95%"/>
      <freeze:hidden property="log_file_path" caption="��־�ļ����·��" datatype="string" maxlength="1000" style="width:95%"/>
      <freeze:hidden property="collect_status" caption="�ɼ�״̬" datatype="string" maxlength="2" style="width:95%"/>
      
      <freeze:hidden property="file_status" caption="�ļ�״̬" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="file_description" caption="�ļ�����"  maxlength="2000" style="width:98%"/>
      <freeze:hidden property="collect_file_id" caption="�ɼ��ļ�ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="check_result_file_name" caption="У�����ļ�����"  maxlength="300" style="width:98%"/>
      <freeze:hidden property="check_result_file_id" caption="У�����ļ�ID" datatype="string" maxlength="32" style="width:95%"/>
      
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
