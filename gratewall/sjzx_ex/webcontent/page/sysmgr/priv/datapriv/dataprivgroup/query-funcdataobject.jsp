<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ���ù��ܹ�ϵ�б�</title>
</head>

<script language="javascript">

// �������ù��ܹ�ϵ
function func_record_addRecord()
{
	var page = new pageDefine( "insert-funcdataobject.jsp", "�������ù��ܹ�ϵ", "modal" );
	page.addRecord();
}

// �޸����ù��ܹ�ϵ
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn103024.do", "�޸����ù��ܹ�ϵ", "modal" );
	page.addParameter( "record:funcode", "primary-key:funcode" );
	page.addParameter( "record:objectid", "primary-key:objectid" );
	page.updateRecord();
}

// ɾ�����ù��ܹ�ϵ
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn103025.do", "ɾ�����ù��ܹ�ϵ" );
	page.addParameter( "record:funcode", "primary-key:funcode" );
	page.addParameter( "record:objectid", "primary-key:objectid" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ���ù��ܹ�ϵ�б�"/>
<freeze:errors/>

<freeze:form action="/txn103021">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:text property="objectid" caption="���ݶ������" datatype="string" maxlength="" style="width:95%"/>
  </freeze:block>

  <freeze:grid property="record" caption="��ѯ���ù��ܹ�ϵ�б�" keylist="funcode,objectid" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="�������ù��ܹ�ϵ" txncode="103023" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸����ù��ܹ�ϵ" txncode="103024" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ�����ù��ܹ�ϵ" txncode="103025" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="funcode" caption="���ܴ���" style="width:66%" />
      <freeze:cell property="objectid" caption="���ݶ������" style="width:34%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
