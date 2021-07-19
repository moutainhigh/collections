<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ��������б�</title>
</head>

<script language="javascript">

// ���ӹ������
function func_record_addRecord()
{
	var page = new pageDefine( "insert-share_srv_scheduling.jsp", "���ӹ������", "modal" );
	page.addRecord();
}

// �޸Ĺ������
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn40400004.do", "�޸Ĺ������", "modal" );
	page.addParameter( "record:srv_scheduling_id", "primary-key:srv_scheduling_id" );
	page.updateRecord();
}

// ɾ���������
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn40400005.do", "ɾ���������" );
	page.addParameter( "record:srv_scheduling_id", "primary-key:srv_scheduling_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ��������б�"/>
<freeze:errors/>

<freeze:form action="/txn40400001">
  <freeze:frame property="select-key" width="95%">
  </freeze:frame>

  <freeze:grid property="record" caption="��ѯ��������б�" keylist="srv_scheduling_id" width="95%" navbar="bottom" fixrow="true">
      <freeze:button name="record_addRecord" caption="���ӹ������" txncode="40400003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸Ĺ������" txncode="40400004" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ���������" txncode="40400005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="srv_scheduling_id" caption="�������ID" style="width:10%" visible="false"/>
      <freeze:cell property="service_id" caption="����ID" style="width:12%" />
      <freeze:cell property="service_no" caption="������" style="width:12%" />
      <freeze:cell property="service_name" caption="��������" style="width:20%" />
      <freeze:cell property="job_class_name" caption="�������õ�����" style="width:20%" />
      <freeze:cell property="scheduling_type" caption="�ƻ���������" style="width:10%" />
      <freeze:cell property="scheduling_day" caption="�ƻ���������" style="width:10%" />
      <freeze:cell property="start_time" caption="�ƻ�����ʼʱ��" style="width:10%" />
      <freeze:cell property="end_time" caption="�ƻ��������ʱ��" style="width:10%" />
      <freeze:cell property="scheduling_count" caption="�ƻ�����ִ�д���" style="width:10%" />
      <freeze:cell property="interval_time" caption="ÿ�μ��ʱ��" style="width:10%" />
      <freeze:cell property="scheduling_week" caption="�ƻ���������" style="width:20%" />
      <freeze:cell property="scheduling_day1" caption="�ƻ�������������" style="width:20%" visible="false" />
      <freeze:cell property="task_expression" caption="���ʽ" style="width:20%" />
      <freeze:cell property="is_markup" caption="����� ��Ч ��Ч" style="width:10%" />
      <freeze:cell property="creator_id" caption="������ID" style="width:12%" />
      <freeze:cell property="created_time" caption="����ʱ��" style="width:12%" />
      <freeze:cell property="last_modify_id" caption="����޸���ID" style="width:12%" />
      <freeze:cell property="last_modify_time" caption="����޸�ʱ��" style="width:12%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
