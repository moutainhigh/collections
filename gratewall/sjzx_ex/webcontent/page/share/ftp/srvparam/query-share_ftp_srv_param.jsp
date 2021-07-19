<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯftp��������б�</title>
</head>

<script language="javascript">

// ����ftp�������
function func_record_addRecord()
{
	var page = new pageDefine( "insert-share_ftp_srv_param.jsp", "����ftp�������", "modal" );
	page.addRecord();
}

// �޸�ftp�������
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn40402004.do", "�޸�ftp�������", "modal" );
	page.addParameter( "record:srv_param_id", "primary-key:srv_param_id" );
	page.updateRecord();
}

// ɾ��ftp�������
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn40402005.do", "ɾ��ftp�������" );
	page.addParameter( "record:srv_param_id", "primary-key:srv_param_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯftp��������б�"/>
<freeze:errors/>

<freeze:form action="/txn40402001">
  <freeze:frame property="select-key" width="95%">
  </freeze:frame>

  <freeze:grid property="record" caption="��ѯftp��������б�" keylist="srv_param_id" width="95%" navbar="bottom" fixrow="true">
      <freeze:button name="record_addRecord" caption="����ftp�������" txncode="40402003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸�ftp�������" txncode="40402004" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ��ftp�������" txncode="40402005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="srv_param_id" caption="����ֵID" style="width:14%" />
      <freeze:cell property="ftp_service_id" caption="FTP����ID" style="width:14%" />
      <freeze:cell property="param_value_type" caption="�����String INT BOOLEAN" style="width:14%" />
      <freeze:cell property="patameter_name" caption="������" style="width:14%" />
      <freeze:cell property="patameter_value" caption="����ֵ" style="width:20%" />
      <freeze:cell property="style" caption="��ʽ" style="width:13%" />
      <freeze:cell property="showorder" caption="˳���ֶ�" style="width:11%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
