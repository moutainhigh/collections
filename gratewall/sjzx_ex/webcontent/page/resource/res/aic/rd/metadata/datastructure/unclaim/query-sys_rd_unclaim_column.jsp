<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯδ������ֶ��б�</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/FusionCharts/jquery-1.4.2.min.js"></script>
<script language="javascript">

// ����δ������ֶ�
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_rd_unclaim_column.jsp", "����δ������ֶ�", "modal" );
	page.addRecord();
}

// �޸�δ������ֶ�
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn80002404.do", "�޸�δ������ֶ�", "modal" );
	page.addParameter( "record:sys_rd_unclaim_column_id", "primary-key:sys_rd_unclaim_column_id" );
	page.updateRecord();
}

// ɾ��δ������ֶ�
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn80002405.do", "ɾ��δ������ֶ�" );
	page.addParameter( "record:sys_rd_unclaim_column_id", "primary-key:sys_rd_unclaim_column_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// �� ��
function func_record_goBack()
{
	goBack();	// 
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var operationSpan = document.getElementsByName("span_record:operation");
	var number=1;
	for (var i=0; i < operationSpan.length; i++){
		operationSpan[i].style.textAlign = "center";
		operationSpan[i].innerHTML = number++;
	}
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯδ������ֶ��б�"/>
<freeze:errors/>

<freeze:form action="/txn80002401">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:hidden property="sys_rd_unclaim_table_id" caption="�����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="unclaim_tab_code" caption="�����" datatype="string" maxlength="32" style="width:39%" readonly="true" colspan="2"/>
      <freeze:text property="unclaim_column_code" caption="�ֶ�����" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:select property="unclaim_column_type" caption="�ֶ�����" valueset="�ֶ���������"  style="width:95%"/>
  </freeze:block>
<BR>
  <freeze:grid property="record" caption="��ѯδ������ֶ��б�" keylist="sys_rd_unclaim_column_id" width="95%" rowselect="false" checkbox="false" navbar="bottom" fixrow="false">
      <freeze:button name="record_goBack" caption="�� ��"  hotkey="CLOSE" align="right" onclick="func_record_goBack();"/>
      <freeze:button name="record_addRecord" caption="����δ������ֶ�" txncode="80002403" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸�δ������ֶ�" txncode="80002404" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ��δ������ֶ�" txncode="80002405" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:hidden property="sys_rd_unclaim_column_id" caption="������" style="width:10%" />
      <freeze:hidden property="sys_rd_unclaim_table_id" caption="�����ID" style="width:12%" />
      <freeze:hidden property="sys_rd_data_source_id" caption="����ԴID" style="width:12%" />
      <freeze:hidden property="unclaim_tab_code" caption="δ�������" style="width:20%" />
      <freeze:cell property="@rowid" caption="���" align="center" style="width:5%" />
      <freeze:cell property="unclaim_column_code" caption="�ֶ�����" style="width:20%" />
      <freeze:cell property="unclaim_column_name" caption="�ֶ�������" style="width:20%" />
      <freeze:cell property="unclaim_column_type" caption="�ֶ�����" valueset="�ֶ���������" style="width:10%" />
      <freeze:cell property="unclaim_column_length" caption="�ֶγ���" style="width:10%" />
      <freeze:hidden property="is_primary_key" caption="�Ƿ�����" style="width:10%" />
      <freeze:hidden property="is_index" caption="�Ƿ�����" style="width:10%" />
      <freeze:hidden property="is_null" caption="�Ƿ�����Ϊ��" style="width:10%" />
      <freeze:hidden property="default_value" caption="Ĭ��ֵ" style="width:20%" />
      <freeze:hidden property="remarks" caption="��ע" style="width:20%"  />
      <freeze:hidden property="timestamp" caption="ʱ���" style="width:12%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
