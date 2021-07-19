<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ����Ȩ�޷����б�</title>
</head>

<script language="javascript">

// ��������Ȩ�޷���
function func_record_addRecord()
{
	var page = new pageDefine( "insert-dataaccgroup.jsp", "��������Ȩ�޷���", "modal" );
	page.addRecord();
}

// �޸�����Ȩ�޷���
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn103054.do", "�޸�����Ȩ�޷���", "modal" );
	page.addParameter( "record:dataaccgrpid", "primary-key:dataaccgrpid" );
	page.updateRecord();
}

// ɾ������Ȩ�޷���
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn103055.do", "ɾ������Ȩ�޷���" );
	page.addParameter( "record:dataaccgrpid", "primary-key:dataaccgrpid" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ����Ȩ�޷����б�"/>
<freeze:errors/>

<freeze:form action="/txn103051">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:text property="dataaccgrpname" caption="����Ȩ�޷�������" datatype="string" maxlength="40" style="width:95%"/>
      <freeze:text property="dataaccgrpdesc" caption="����Ȩ�޷�������" datatype="string" maxlength="200" style="width:95%"/>
  </freeze:block>

  <freeze:grid property="record" caption="��ѯ����Ȩ�޷����б�" keylist="dataaccgrpid" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="��������Ȩ�޷���" txncode="103053" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸�����Ȩ�޷���" txncode="103054" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ������Ȩ�޷���" txncode="103055" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="dataaccgrpid" caption="����Ȩ�޷���ID" style="width:10%" visible="false"/>
      <freeze:cell property="dataaccgrpname" caption="����Ȩ�޷�������" style="width:32%" />
      <freeze:cell property="dataaccrule" caption="��ɫȨ����֤����" style="width:17%" />
      <freeze:cell property="dataacctype" caption="����Ȩ�޷�������" style="width:17%" />
      <freeze:cell property="dataaccgrpdesc" caption="����Ȩ�޷�������" style="width:34%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
