<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:include href="/script/lib/jquery162.js"/>
<freeze:html width="650" height="350">
<head>
<title>��ѯ�������б�</title>
<style>
.odd1_b{
	white-space: nowrap;
}
</style>
</head>

<script language="javascript">
// �ֶι���
function func_record_columnRecord()
{
	if (event.srcElement.tagName.toUpperCase() == 'INPUT'){
		return;
	}
	if (event.srcElement.tagName.toUpperCase() == 'LABEL'){
		return;
	}
	if (event.srcElement.tagName.toUpperCase() == 'A'){
		return;
	}
	var page = new pageDefine( "/txn80003501.do", "�鿴������" );
	page.addParameter( "record:sys_rd_table_id", "select-key:sys_rd_table_id" );
	page.addParameter( "record:table_code", "select-key:table_code" );
	page.addParameter( "record:table_name", "select-key:table_name" );
	page.updateRecord();
}

// �� ��
function func_record_goBack()
{
	goBack();	// 
}
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	$("#customPageRowSeleted option:gt(0)").remove();
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:errors/>
<freeze:form action="/txn80004301">

  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:select property="sys_rd_system_id" valueset="ҵ���������" caption="��������" style="width:90%"/>
      <freeze:text property="table_code" caption="�����" datatype="string" maxlength="100" style="width:90%"/>
      <freeze:text property="table_name" caption="�����������" datatype="string" maxlength="100" style="width:90%"/>
      <freeze:select property="table_type" caption="������" valueset="������" style="width:90%"/>
      <freeze:hidden property="query_type" value="1" caption="��ѯ��ʽ" style="width:90%"/>
      <freeze:hidden property="sys_simple" caption="��ѯ��ʽ" style="width:90%"/>
      <freeze:hidden property="sys_system_ids" caption="��ѯ�����б�" style="width:90%"/>
  </freeze:block>
<br />
  <freeze:grid property="record" checkbox="false" caption="��ѯ�������б�" keylist="sys_rd_table_id" width="95%" navbar="bottom"  fixrow="false" onclick="func_record_columnRecord();" >
      <freeze:cell property="table_code" caption="�����" style="width:30%" ondblclick="func_record_columnRecord();"  />
      <freeze:cell property="table_name" caption="�����������" style="" ondblclick="func_record_columnRecord();"/>
      <freeze:cell property="table_type" caption="������" valueset="������" style="width:15%" ondblclick="func_record_columnRecord();" />
      <freeze:cell property="sys_rd_system_id" caption="��������" valueset="ҵ������" style="width:25%" ondblclick="func_record_columnRecord();" />

      <freeze:hidden property="claim_operator" caption="������" style="width:15%" />
      <freeze:hidden property="claim_date" caption="��������" style="width:10%" />
      <freeze:hidden property="table_no" caption="���ݱ���" style="width:12%" />
      <freeze:hidden property="table_sql" caption="���ݱ�sql" style="width:20%"  />
      <freeze:hidden property="table_sort" caption="�����ֶ�" style="width:20%" />
      <freeze:hidden property="table_dist" caption="�����ֶ�" style="width:20%" />
      <freeze:hidden property="table_time" caption="ʱ���ֶ�" style="width:20%" />
      <freeze:hidden property="parent_table" caption="������" style="width:20%" />
      <freeze:hidden property="parent_pk" caption="����������" style="width:20%" />
      <freeze:hidden property="table_fk" caption="�븸���������" style="width:20%" />
      <freeze:hidden property="first_record_count" caption="����������" style="width:10%" />
      <freeze:hidden property="last_record_count" caption="���һ��ͬ��������" style="width:10%" />
      <freeze:hidden property="sys_rd_table_id" caption="���ݱ�ID" style="width:10%" />
      <freeze:hidden property="sys_rd_system_id" caption="ҵ������ID" style="width:12%" />
      <freeze:hidden property="sys_no" caption="ҵ��������" style="width:12%" />
      <freeze:hidden property="sys_rd_data_source_id" caption="����ԴID" style="width:12%" />
      <freeze:hidden property="table_primary_key" caption="������" style="width:20%" />
      <freeze:hidden property="table_index" caption="������" style="width:20%" />
      <freeze:hidden property="table_use" caption="��;" style="width:20%" />
      <freeze:hidden property="gen_code_column" caption="�ִܾ����ֶ�" style="width:12%" />
      <freeze:hidden property="prov_code_column" caption="ʡ�ִ����ֶ�" style="width:12%" />
      <freeze:hidden property="city_code_column" caption="�оִ����ֶ�" style="width:12%" />
      <freeze:hidden property="content" caption="�����ֶ�����" style="width:12%" />
      <freeze:hidden property="changed_status" caption="�仯״̬" style="width:10%" />
      <freeze:hidden property="object_schema" caption="��ģʽ" style="width:16%" />
      <freeze:hidden property="memo" caption="��ע" style="width:20%"  />
      <freeze:hidden property="is_query" caption="�Ƿ�ɲ�ѯ" style="width:10%" />
      <freeze:hidden property="is_trans" caption="�Ƿ�ɹ���" style="width:10%" />
      <freeze:hidden property="is_download" caption="�Ƿ������" style="width:10%" />
      <freeze:hidden property="sort" caption="����" style="width:10%" />
      <freeze:hidden property="timestamp" caption="ʱ���" style="width:12%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
