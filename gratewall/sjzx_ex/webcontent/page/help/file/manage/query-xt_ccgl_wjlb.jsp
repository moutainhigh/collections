<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ�ļ�����б�</title>
</head>

<script language="javascript">

// �����ļ����
function func_record_addRecord()
{
	var page = new pageDefine( "insert-xt_ccgl_wjlb.jsp", "�����ļ����", "modal" );
	page.addRecord();
}

// �޸��ļ����
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn604050104.do", "�޸��ļ����", "modal" );
	page.addParameter( "record:cclbbh_pk", "primary-key:cclbbh_pk" );
	page.updateRecord();
}

// ɾ���ļ����
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn604050105.do", "ɾ���ļ����" );
	page.addParameter( "record:cclbbh_pk", "primary-key:cclbbh_pk" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ�ļ�����б�"/>
<freeze:errors/>

<freeze:form action="/txn604050101">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:text property="cclbmc" caption="�����������" datatype="string" maxlength="60" style="width:95%"/>
      <freeze:text property="lbmcbb" caption="������ư汾" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="zt" caption="״̬" datatype="string" maxlength="1" style="width:95%"/>
  </freeze:block>

  <freeze:grid property="record" caption="��ѯ�ļ�����б�" keylist="cclbbh_pk" width="95%" navbar="bottom" fixrow="true">
      <freeze:button name="record_addRecord" caption="�����ļ����" txncode="604050103" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸��ļ����" txncode="604050104" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ���ļ����" txncode="604050105" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="cclbbh_pk" caption="�ļ������" style="width:10%" visible="false"/>
      <freeze:cell property="cclbmc" caption="�����������" style="width:14%" />
      <freeze:cell property="lbmcbb" caption="������ư汾" style="width:9%" />
      <freeze:cell property="ccgml" caption="�洢��Ŀ¼" style="width:18%" />
      <freeze:cell property="ejmlgz" caption="����Ŀ¼����" style="width:9%" />
      <freeze:cell property="gzfzzd" caption="�������ֶ�" style="width:20%" />
      <freeze:cell property="zt" caption="״̬" style="width:10%" />
      <freeze:cell property="bz" caption="��ע" style="width:20%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
