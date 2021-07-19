<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ�����ѯ�����б�</title>
</head>

<script language="javascript">

// ���ӷ����ѯ����
function func_record_addRecord()
{
	var page = new pageDefine( "insert-share_service_condition.jsp", "���ӷ����ѯ����", "modal" );
	page.addRecord();
}

// �޸ķ����ѯ����
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn40210004.do", "�޸ķ����ѯ����", "modal" );
	page.addParameter( "record:condition_id", "primary-key:condition_id" );
	page.updateRecord();
}

// ɾ�������ѯ����
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn40210005.do", "ɾ�������ѯ����" );
	page.addParameter( "record:condition_id", "primary-key:condition_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ�����ѯ�����б�"/>
<freeze:errors/>

<freeze:form action="/txn40210001">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:text property="service_id" caption="����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="need_input" caption="�Ƿ���Ҫ����" datatype="string" maxlength="1" style="width:95%"/>
  </freeze:block>

  <freeze:grid property="record" caption="��ѯ�����ѯ�����б�" keylist="condition_id" width="95%" navbar="bottom" fixrow="true">
      <freeze:button name="record_addRecord" caption="���ӷ����ѯ����" txncode="40210003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸ķ����ѯ����" txncode="40210004" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ�������ѯ����" txncode="40210005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="condition_id" caption="�����ѯ����ID" style="width:10%" visible="false"/>
      <freeze:cell property="service_id" caption="����ID" style="width:12%" />
      <freeze:cell property="frist_connector" caption="���ӷ�1" style="width:12%" />
      <freeze:cell property="left_paren" caption="������" style="width:12%" />
      <freeze:cell property="table_name_en" caption="������" style="width:20%" />
      <freeze:cell property="table_name_cn" caption="����������" style="width:20%" />
      <freeze:cell property="column_name_en" caption="�ֶ�����" style="width:20%" />
      <freeze:cell property="column_name_cn" caption="�ֶ���������" style="width:20%" />
      <freeze:cell property="second_connector" caption="���ӷ�2" style="width:12%" />
      <freeze:cell property="param_value" caption="����ֵ" style="width:12%" />
      <freeze:cell property="param_type" caption="����ֵ����" style="width:12%" />
      <freeze:cell property="right_paren" caption="������" style="width:12%" />
      <freeze:cell property="show_order" caption="��ʾ˳��" style="width:10%" />
      <freeze:cell property="need_input" caption="�Ƿ���Ҫ����" style="width:10%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
