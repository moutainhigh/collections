<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ���������б�</title>
</head>

<script language="javascript">

// ������������
function func_record_addRecord()
{
	var page = new pageDefine( "insert-view_sys_count_log.jsp", "������������", "modal" );
	page.addRecord();
}

// �޸���������
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn620100304.do", "�޸���������", "modal" );
	page.addParameter( "record:table_class_id", "primary-key:table_class_id" );
	page.updateRecord();
}

// ɾ����������
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn620100305.do", "ɾ����������" );
	page.addParameter( "record:table_class_id", "primary-key:table_class_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ���������б�"/>
<freeze:errors/>

<freeze:form action="/txn620100301">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:text property="sys_name" caption="��������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="table_name_cn" caption="��������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="count_date" caption="ͳ������" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="class_state" caption="����״̬" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

  <freeze:grid property="record" caption="��ѯ���������б�" keylist="table_class_id" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="������������" txncode="620100303" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸���������" txncode="620100304" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ����������" txncode="620100305" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="sys_name" caption="��������" style="width:20%" />
      <freeze:cell property="table_name" caption="����" style="width:20%" />
      <freeze:cell property="table_name_cn" caption="��������" style="width:20%" />
      <freeze:cell property="class_sort" caption="��������" style="width:12%" />
      <freeze:cell property="class_state" caption="����״̬" style="width:12%" />
      <freeze:cell property="count_date" caption="ͳ������" style="width:10%" />
      <freeze:cell property="count_full" caption="��ȫ��" style="width:10%" />
      <freeze:cell property="count_incre" caption="������" style="width:10%" />
      <freeze:cell property="table_class_id" caption="��������id" style="width:16%" />
      <freeze:cell property="sys_order" caption="���������ֶ�" style="width:10%" />
      <freeze:cell property="table_order" caption="�������ֶ�" style="width:10%" />
      <freeze:cell property="sort_order" caption="��������" style="width:10%" />
      <freeze:cell property="state_order" caption="״̬����" style="width:10%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
