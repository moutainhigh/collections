<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ�ļ��ϴ��ɼ����б�</title>
</head>

<script language="javascript">

// �����ļ��ϴ��ɼ���
function func_record_addRecord()
{
	var page = new pageDefine( "insert-collect_file_upload_task.jsp", "�����ļ��ϴ��ɼ���", "modal" );
	page.addRecord();
}

// �޸��ļ��ϴ��ɼ���
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn30301004.do", "�޸��ļ��ϴ��ɼ���", "modal" );
	page.addParameter( "record:file_upload_task_id", "primary-key:file_upload_task_id" );
	page.updateRecord();
}

// ɾ���ļ��ϴ��ɼ���
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn30301005.do", "ɾ���ļ��ϴ��ɼ���" );
	page.addParameter( "record:file_upload_task_id", "primary-key:file_upload_task_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ�ļ��ϴ��ɼ����б�"/>
<freeze:errors/>

<freeze:form action="/txn30301001">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:text property="collect_table" caption="�ɼ���" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="collect_mode" caption="�ɼ�ģʽ" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="file_status" caption="�ļ�״̬" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="file_description" caption="�ļ�����" datatype="string" maxlength="2000" style="width:95%"/>
      <freeze:text property="collect_file_name" caption="�ɼ��ļ�����" datatype="string" maxlength="200" style="width:95%"/>
      <freeze:text property="collect_file_id" caption="�ɼ��ļ�ID" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

  <freeze:grid property="record" caption="��ѯ�ļ��ϴ��ɼ����б�" keylist="file_upload_task_id" width="95%" navbar="bottom" fixrow="true">
      <freeze:button name="record_addRecord" caption="�����ļ��ϴ��ɼ���" txncode="30301003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸��ļ��ϴ��ɼ���" txncode="30301004" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ���ļ��ϴ��ɼ���" txncode="30301005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="file_upload_task_id" caption="�ļ��ϴ�����ID" style="width:11%" />
      <freeze:cell property="collect_task_id" caption="�ɼ�����ID" style="width:11%" />
      <freeze:cell property="collect_table" caption="�ɼ���" style="width:11%" />
      <freeze:cell property="collect_mode" caption="�ɼ�ģʽ" style="width:11%" />
      <freeze:cell property="file_status" caption="�ļ�״̬" style="width:12%" />
      <freeze:cell property="file_description" caption="�ļ�����" style="width:20%" visible="false" />
      <freeze:cell property="collect_file_name" caption="�ɼ��ļ�����" style="width:20%" />
      <freeze:cell property="collect_file_id" caption="�ɼ��ļ�ID" style="width:12%" />
      <freeze:cell property="check_result_file_name" caption="У�����ļ�����" style="width:20%" visible="false" />
      <freeze:cell property="check_result_file_id" caption="У�����ļ�ID" style="width:12%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
