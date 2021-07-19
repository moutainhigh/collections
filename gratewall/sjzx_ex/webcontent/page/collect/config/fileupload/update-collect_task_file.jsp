<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�޸��ļ��ϴ��ɼ�����Ϣ</title>
</head>
<script language='javascript' src='<%=request.getContextPath()%>/script/UUID.js'></script>
<script language="javascript">
var collect_joumal_id = UUID.prototype.createUUID ();


// ���沢�ر�
function func_record_saveAndExit()
{
	setFormFieldValue('record:collect_joumal_id',collect_joumal_id);
	saveAndExit( '', '�޸Ĳɼ�����ʧ��!'  );	// /txn30301001.do
}

// �� ��
function func_record_goBack()
{
	
	goBack();	// /txn30301001.do  txn30101001
	//parent.window.location.href="txn30101001.do";
}

function getParameter(){
	//�ӵ�ǰҳ��ȡֵ�����key=value��ʽ�Ĵ�
    var parameter='input-data:service_targets_id='+getFormFieldValue('record:service_targets_id');
	return parameter;
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	//var filepath = getFormFieldValue("record:log_file_path");
	//var num = filepath.lastIndexOf("/");
	//var filename = filepath.substring(num+1);
	//var url = "/downloadFile?file="+filepath+"&&fileName="+filename;	
	//document.getElementById("logfile").href= url;
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�޸��ļ��ϴ��ɼ�����Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn30101023" enctype="multipart/form-data">
  <freeze:block property="record" caption="�޸��ļ��ϴ��ɼ�����Ϣ" width="95%">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      
      <freeze:hidden property="file_upload_task_id" caption="�ļ��ϴ�����ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:hidden property="collect_task_id" caption="�ɼ�����ID" datatype="string"  style="width:95%"/>
      <freeze:hidden property="collect_joumal_id" caption="�ɼ���־ID" datatype="string" maxlength="32"  style="width:95%"/>
      
      <freeze:browsebox property="service_targets_id" caption="�����������" show="name" notnull="true" valueset="��Դ����_�����������"    style="width:95%"/>
      <freeze:text property="task_name" caption="��������" datatype="string" notnull="true" maxlength="100" style="width:95%"/>
      <freeze:browsebox property="collect_table" caption="�ɼ���"  notnull="true" maxlength="32" valueset="��Դ����_��������Ӧ�ɼ���" show="name" parameter="getParameter();" style="width:95%"/>
      <freeze:select property="collect_mode" caption="�ɼ���ʽ" show="name" notnull="true" valueset="��Դ����_�ɼ���ʽ" style="width:95%"/>
      <freeze:textarea property="task_description" caption="����˵��" colspan="2" rows="2" maxlength="2000" style="width:98%"/>
      <freeze:textarea property="record" caption="����˵��" colspan="2" rows="2" maxlength="2000" style="width:98%"/>
      <freeze:file property="collect_file_name" caption="�ɼ��ļ�" accept="*.xls,*.txt,*.mdb" style="width:80%" maxlength="200" colspan="2"/>
      <freeze:hidden property="collect_status" caption="�ɼ�״̬" datatype="string" maxlength="2" style="width:95%"/>
      
      <freeze:hidden property="collect_type" caption="�ɼ�����" valueset="�ɼ�����_�ɼ�����" value="01"  style="width:95%" />
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
      <freeze:hidden property="file_status" caption="�ļ�״̬" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="file_description" caption="�ļ�����"  maxlength="2000" style="width:98%"/>
      <freeze:hidden property="collect_filse_id" caption="�ɼ��ļ�ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="check_result_file_name" caption="У�����ļ�����"   style="width:98%"/>
      <freeze:hidden property="log_file_path" caption="��־�ļ����·��" datatype="string" maxlength="1000" style="width:95%"/>
      <freeze:hidden property="check_result_file_id" caption="У�����ļ�ID" datatype="string" maxlength="32" style="width:95%"/>
      
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
