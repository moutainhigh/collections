<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�鿴�ֶ���Ϣ</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '������������ֶα�' );	// /txn80002501.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn80002501.do
}

// �� ��
function func_record_goBackNoUpdate()
{
	goBackNoUpdate();	
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�鿴�ֶ���Ϣ"/>
<freeze:errors/>

<freeze:form action="/txn80002506">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_rd_column_id" caption="�ֶ�ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block  property="record" caption="�鿴�ֶ���Ϣ" width="95%">
      
      <freeze:hidden property="sys_rd_column_id" caption="�ֶ�ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="sys_rd_table_id" caption="�����ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="sys_rd_data_source_id" caption="����ԴID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="table_code" caption="�������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:cell property="column_code" caption="�ֶ���" datatype="string"  style="width:95%" />
      <freeze:cell property="column_name" caption="�ֶ�������" datatype="string"  style="width:95%" />
      <freeze:cell property="column_type" caption="�ֶ�����" valueset="�ֶ���������" datatype="string"  style="width:95%" />
      <freeze:cell property="column_length" caption="�ֶγ���" datatype="string"  style="width:95%" />
      <freeze:cell property="is_null" caption="�Ƿ�Ϊ��" valueset="�Ƿ���������" datatype="string" style="width:95%" />
      <freeze:cell property="default_value" caption="ȱʡֵ" datatype="string"  style="width:95%" />
      <freeze:cell property="standard_status" caption="�ϱ���" valueset="�ϱ���" datatype="string"  style="width:95%" />
      <freeze:cell property="jc_data_element" caption="��������Ԫ" valueset="����Ԫ��ʶ��" datatype="string"  style="width:95%" />
      <freeze:cell property="jc_data_index" caption="�������뼯" valueset="ȡ�������뼯" datatype="string"  style="width:95%" />
      
      <freeze:cell property="column_codeindex" caption="ϵͳ���뼯" valueset="ȡϵͳ���뼯" datatype="string"   style="width:98%" />
      <freeze:cell property="domain_value" caption="ֵ��" datatype="string"  style="width:95%" />
      <freeze:cell property="unit" caption="������λ" datatype="string"  style="width:95%" />
      <freeze:cell property="column_level" caption="�ֶμ���" valueset="�ֶμ���" datatype="string"  style="width:95%" />
      <freeze:cell property="basic_flag" caption="�����ֶα�ʶ" datatype="string"  style="width:95%" />
      <freeze:cell property="data_from" caption="������Դ" datatype="string"  style="width:95%" />
      <freeze:cell property="data_from_column" caption="������Դ�ֶ�" datatype="string"  style="width:95%" />
      <freeze:cell property="transform_desp" caption="����ת������" datatype="string"   style="width:98%" />
      <freeze:cell property="description" caption="˵��"   style="width:98%" />
      
      <freeze:hidden property="alia_name" caption="�ֶα���" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden property="code_name" caption="ϵͳ������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden property="is_extra_col" caption="�Ƿ�Ϊ�ز��ֶ�" datatype="string" maxlength="2" style="width:95%"/>
      <freeze:hidden property="sys_column_type" caption="�ֶ�����" datatype="string" maxlength="2" style="width:95%"/>
      <freeze:hidden property="claim_operator" caption="������" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="claim_date" caption="����ʱ��" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:hidden property="is_primary_key" caption="�Ƿ�����" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="is_index" caption="�Ƿ�����" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="changed_status" caption="�ֶα仯״̬" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="sync_sign" caption="ͬ����ʶ��" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="sort" caption="����" datatype="string" maxlength="" style="width:95%"/>
      <freeze:hidden property="timestamp" caption="ʱ���" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>
  
  <p align="center" class="print-menu">
  <!--  <input type="button" name="record_printDocument" value="�� ӡ" class="menu" onclick="func_record_printDocument();" style='width:60px' >-->
  <table cellspacing="0" cellpadding="0" class="button_table">
	<tr>
		<td class="btn_left"></td>
		<td>
  			<input type="button" name="record_goBackNoUpdate" value="�ر�" class="menu" onclick="func_record_goBackNoUpdate();" />
  		</td>
		<td class="btn_right"></td>
	</tr>
</table>
  </p>
</freeze:form>
</freeze:body>
</freeze:html>
