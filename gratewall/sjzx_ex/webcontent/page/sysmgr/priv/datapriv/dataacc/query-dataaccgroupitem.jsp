<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ����Ȩ�޷������б�</title>
</head>

<script language="javascript">

// ��������Ȩ�޷�����
function func_record_addRecord()
{
	var page = new pageDefine( "insert-dataaccgroupitem.jsp", "��������Ȩ�޷�����", "modal" );
	page.addRecord();
}

// �޸�����Ȩ�޷�����
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn103064.do", "�޸�����Ȩ�޷�����", "modal" );
	page.addParameter( "record:dataaccgrpid", "primary-key:dataaccgrpid" );
	page.addParameter( "record:dataaccid", "primary-key:dataaccid" );
	page.addParameter( "record:objectid", "primary-key:objectid" );
	page.updateRecord();
}

// ɾ������Ȩ�޷�����
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn103065.do", "ɾ������Ȩ�޷�����" );
	page.addParameter( "record:dataaccgrpid", "primary-key:dataaccgrpid" );
	page.addParameter( "record:dataaccid", "primary-key:dataaccid" );
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
<freeze:title caption="��ѯ����Ȩ�޷������б�"/>
<freeze:errors/>

<freeze:form action="/txn103061">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:text property="dataaccgrpid" caption="����Ȩ�޷���ID" datatype="string" maxlength="" style="width:95%"/>
  </freeze:block>

  <freeze:grid property="record" caption="��ѯ����Ȩ�޷������б�" keylist="dataaccgrpid,dataaccid,objectid" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="��������Ȩ�޷�����" txncode="103063" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸�����Ȩ�޷�����" txncode="103064" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ������Ȩ�޷�����" txncode="103065" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="dataaccgrpid" caption="����Ȩ�޷���ID" style="width:25%" />
      <freeze:cell property="objectid" caption="����Ȩ�����ʹ���" style="width:25%" />
      <freeze:cell property="dataaccid" caption="����Ȩ������" style="width:50%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
