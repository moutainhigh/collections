<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ��������ֶα��б�</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script language="javascript">
// �� ��
function func_record_goBack()
{
	goBack();	// /txn80002501.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	$("#customPageRowSeleted option:gt(0)").remove();
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ��������ֶα��б�"/>
<freeze:errors/>
<br />
<freeze:form action="/txn80003501">
  <freeze:grid property="record" caption="�ֶ��б�" checkbox="false" rowselect="false" keylist="sys_rd_column_id" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" align="right" onclick="func_record_goBack();"/>
     
      <freeze:cell property="column_code" caption="�ֶ���" style="width:15%" />
      <freeze:cell property="column_name" caption="�ֶ�������" style="width:15%" />
      <freeze:cell property="column_type" caption="�ֶ�����" valueset="�ֶ���������" style="width:10%" />
      <freeze:hidden property="column_length" caption="�ֶγ���" style="width:8%" />
      <freeze:cell property="column_codeindex" caption="ϵͳ���뼯����" style="width:15%" />
      
      <freeze:hidden property="use_type" caption="�Ƿ���Ч" valueset="��������" style="width:8%" />
      <freeze:hidden property="sys_rd_column_id" caption="�ֶ�ID" style="width:10%" />
      <freeze:hidden property="sys_rd_table_id" caption="�����ID" style="width:12%" />
      <freeze:hidden property="sys_rd_data_source_id" caption="����ԴID" style="width:12%" />
      <freeze:hidden property="table_code"  caption="�������" style="width:20%" />
      <freeze:hidden property="alia_name" caption="�ֶα���" style="width:20%" />
      <freeze:hidden property="code_name" caption="ϵͳ������" style="width:20%" />
      <freeze:hidden property="standard_status" caption="�ϱ�״̬" style="width:10%" />
      <freeze:hidden property="is_extra_col" caption="�Ƿ�Ϊ�ز��ֶ�" style="width:10%" />
      <freeze:hidden property="sys_column_type" caption="�ֶ�����" style="width:10%" />
      <freeze:hidden property="jc_data_element" caption="��������Ԫ" style="width:10%" />
      <freeze:hidden property="jc_data_index" caption="�������뼯" style="width:10%" />
      <freeze:hidden property="domain_value" caption="ֵ��" style="width:10%" />
      <freeze:hidden property="unit" caption="������λ" style="width:12%" />
      <freeze:hidden property="claim_operator" caption="������" style="width:12%" />
      <freeze:hidden property="claim_date" caption="����ʱ��" style="width:10%" />
      <freeze:hidden property="default_value" caption="ȱʡֵ" style="width:16%" />
      <freeze:hidden property="is_null" caption="�Ƿ�Ϊ��" style="width:10%" />
      <freeze:hidden property="is_primary_key" caption="�Ƿ�����" style="width:10%" />
      <freeze:hidden property="is_index" caption="�Ƿ�����" style="width:10%" />
      <freeze:hidden property="changed_status" caption="�ֶα仯״̬" style="width:10%" />
      <freeze:hidden property="sync_sign" caption="ͬ����ʶ��" style="width:12%" />
      <freeze:hidden property="description" caption="˵��" style="width:20%" visible="false" />
      <freeze:hidden property="column_level" caption="�ֶμ���" style="width:10%" />
      <freeze:hidden property="basic_flag" caption="�����ֶα�ʶ" style="width:16%" />
      <freeze:hidden property="data_from" caption="������Դ" style="width:16%" />
      <freeze:hidden property="data_from_column" caption="������Դ�ֶ�" style="width:16%" />
      <freeze:hidden property="transform_desp" caption="����ת������" style="width:20%" />
      <freeze:hidden property="sort" caption="����" style="width:10%" />
      <freeze:hidden property="timestamp" caption="ʱ���" style="width:12%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
