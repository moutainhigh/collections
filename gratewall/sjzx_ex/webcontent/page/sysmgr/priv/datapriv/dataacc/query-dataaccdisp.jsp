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
	var page = new pageDefine( "insert-dataaccdisp.jsp", "��������Ȩ�޷���", "modal" );
	page.addRecord();
}

// �޸�����Ȩ�޷���
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn103044.do", "�޸�����Ȩ�޷���", "modal" );
	page.addParameter( "record:objectid", "primary-key:objectid" );
	page.addParameter( "record:dataaccgrpid", "primary-key:dataaccgrpid" );
	page.updateRecord();
}

// ɾ������Ȩ�޷���
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn103045.do", "ɾ������Ȩ�޷���" );
	page.addParameter( "record:objectid", "primary-key:objectid" );
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

<freeze:form action="/txn103041">
  <freeze:frame property="select-key" width="95%">
  </freeze:frame>

  <freeze:grid property="record" caption="��ѯ����Ȩ�޷����б�" keylist="objectid,dataaccgrpid" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="��������Ȩ�޷���" txncode="103043" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸�����Ȩ�޷���" txncode="103044" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ������Ȩ�޷���" txncode="103045" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="objectid" caption="��������" style="width:34%" />
      <freeze:cell property="dataaccgrpid" caption="����Ȩ�޷�������" style="width:33%" />
      <freeze:cell property="dataaccdispobj" caption="�������" style="width:33%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
