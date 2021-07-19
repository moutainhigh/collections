<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ��ɫ����Ȩ���б�</title>
</head>

<script language="javascript">

// ���ӽ�ɫ����Ȩ��
function func_record_addRecord()
{
	var page = new pageDefine( "insert-operrolefun.jsp", "���ӽ�ɫ����Ȩ��", "modal" );
	page.addRecord();
}

// �޸Ľ�ɫ����Ȩ��
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn103034.do", "�޸Ľ�ɫ����Ȩ��", "modal" );
	page.addParameter( "record:roleaccid", "primary-key:roleaccid" );
	page.updateRecord();
}

// ɾ����ɫ����Ȩ��
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn103035.do", "ɾ����ɫ����Ȩ��" );
	page.addParameter( "record:roleaccid", "primary-key:roleaccid" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ��ɫ����Ȩ���б�"/>
<freeze:errors/>

<freeze:form action="/txn103031">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:text property="roleid" caption="��ɫ���" datatype="string" maxlength="" style="width:95%"/>
  </freeze:block>

  <freeze:grid property="record" caption="��ѯ��ɫ����Ȩ���б�" keylist="roleaccid" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="���ӽ�ɫ����Ȩ��" txncode="103033" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸Ľ�ɫ����Ȩ��" txncode="103034" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ����ɫ����Ȩ��" txncode="103035" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="roleaccid" caption="��ɫȨ�޴���" style="width:10%" visible="false"/>
      <freeze:cell property="roleid" caption="��ɫ���" style="width:33%" />
      <freeze:cell property="txncode" caption="���״���" style="width:35%" />
      <freeze:cell property="dataaccrule" caption="����Ȩ����֤����" style="width:32%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
