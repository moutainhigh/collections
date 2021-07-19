<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ������б�</title>
</head>

<script language="javascript">

// ���ӹ����
function func_record_addRecord()
{
	var page = new pageDefine( "insert-res_share_table.jsp", "���ӹ����", "modal" );
	page.addRecord();
}

// �޸Ĺ����
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn20301004.do", "�޸Ĺ����", "modal" );
	page.addParameter( "record:share_table_id", "primary-key:share_table_id" );
	page.updateRecord();
}

// ɾ�������
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn20301005.do", "ɾ�������" );
	page.addParameter( "record:share_table_id", "primary-key:share_table_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ������б�"/>
<freeze:errors/>

<freeze:form action="/txn20301001">
  <freeze:frame property="select-key" width="95%">
  </freeze:frame>

  <freeze:grid property="record" caption="��ѯ������б�" keylist="share_table_id" width="95%" navbar="bottom" fixrow="true">
      <freeze:button name="record_addRecord" caption="���ӹ����" txncode="20301003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸Ĺ����" txncode="20301004" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ�������" txncode="20301005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="share_table_id" caption="�����ID" style="width:10%" visible="false"/>
      <freeze:cell property="business_topics_id" caption="ҵ������ID" style="width:11%" />
      <freeze:cell property="table_name_en" caption="������" style="width:18%" />
      <freeze:cell property="table_name_cn" caption="����������" style="width:18%" />
      <freeze:cell property="table_no" caption="����" style="width:11%" />
      <freeze:cell property="show_order" caption="��ʾ˳��" style="width:10%" />
      <freeze:cell property="time_" caption="ʱ���ֶ�" style="width:10%" />
      <freeze:cell property="table_type" caption="������" style="width:12%" />
      <freeze:cell property="table_index" caption="������" style="width:20%" visible="false" />
      <freeze:cell property="is_markup" caption="��Ч���" style="width:10%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
