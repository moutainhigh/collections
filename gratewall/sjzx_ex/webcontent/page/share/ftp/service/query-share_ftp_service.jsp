<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ����ftp�����б�</title>
</head>

<script language="javascript">

// ���ӹ���ftp����
function func_record_addRecord()
{
	var page = new pageDefine( "insert-share_ftp_service.jsp", "���ӹ���ftp����", "modal" );
	page.addRecord();
}

// �޸Ĺ���ftp����
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn40401004.do", "�޸Ĺ���ftp����", "modal" );
	page.addParameter( "record:ftp_service_id", "primary-key:ftp_service_id" );
	page.updateRecord();
}

// ɾ������ftp����
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn40401005.do", "ɾ������ftp����" );
	page.addParameter( "record:ftp_service_id", "primary-key:ftp_service_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ����ftp�����б�"/>
<freeze:errors/>

<freeze:form action="/txn40401001">
  <freeze:frame property="select-key" width="95%">
  </freeze:frame>

  <freeze:grid property="record" caption="��ѯ����ftp�����б�" keylist="ftp_service_id" width="95%" navbar="bottom" fixrow="true">
      <freeze:button name="record_addRecord" caption="���ӹ���ftp����" txncode="40401003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸Ĺ���ftp����" txncode="40401004" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ������ftp����" txncode="40401005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="ftp_service_id" caption="FTP����ID" style="width:10%" visible="false"/>
      <freeze:cell property="service_id" caption="����ID" style="width:18%" />
      <freeze:cell property="datasource_id" caption="����ԴID" style="width:18%" />
      <freeze:cell property="srv_scheduling_id" caption="�������ID" style="width:17%" />
      <freeze:cell property="file_name" caption="�ļ�����" style="width:30%" />
      <freeze:cell property="file_type" caption="�ļ�����" style="width:17%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
