<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>�����</title>
</head>

<script language="javascript">

// �� ��
function func_record_saveAndExit()
{
	saveAndExit( '', '����δ�����' );	// /txn80002101.do
}

// �� ��
function func_record_goBack()
{
	goBack();	// /txn80002101.do
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="�����"/>
<freeze:errors/>

<freeze:form action="/txn80002202">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_rd_unclaim_table_id" caption="δ�����ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="�����" width="95%">
      <freeze:button name="record_saveAndExit" caption="�� ��" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="�� ��" hotkey="CLOSE" onclick="func_record_goBack();"/>
      
      <freeze:hidden  property="sys_rd_unclaim_table_id" caption="�����" style="width:75%"/>
        <freeze:hidden property="unclaim_table_code" caption="�����" style="width:75%"/>
        <freeze:hidden property="object_schema" caption="ģʽ" style="width:75%"/>
        <freeze:hidden property="sys_rd_data_source_id" caption="����ԴID" style="width:75%"/>
        
        <freeze:cell property="unclaim_table_code" caption="�����"  colspan="2" style="width:75%"/>
        <freeze:text property="sys_rd_system_id" caption="��������"  style="width:95%"/>
        
        <!-- <freeze:select property="sys_rd_system_id" caption="ϵͳ����" notnull="true" onchange="fun_Changed(1);" valueset="ҵ��ϵͳ���뼯" style="width:95%"/>-->
        <freeze:text property="cur_record_count" caption="������" readonly="true" style="width:95%"/>
        <freeze:text property="tb_pk_columns" caption="����" readonly="true" style="width:95%"/> 
        <freeze:text property="tb_index_columns" caption="����" readonly="true" style="width:95%"/>
        <freeze:text property="table_type" caption="������" valueset="������" notnull="true" colspan="2" style="width:95%"/> 
        <!--<freeze:radio property="table_type" caption="������" valueset="������" notnull="true" colspan="2" onchange="funTBChanged();" value="1"/>-->
        <!--  4 -->
        <freeze:text property="table_name" caption="�����������" notnull="true" colspan="2" style="width:98%"/>
        <freeze:text property="parent_table" caption="����"  style="width:98%"/>
        <!--  5 -->
        <!-- <freeze:browsebox property="parent_table" caption="����" valueset="��������Դ�±�Ĵ���" style="width:98%" colspan="2" parameter="getParameter()" onchange="fun_Changed(2);"/>-->
        
        <freeze:text property="parent_pk" caption="��������"   style="width:95%"/>
        <freeze:text property="table_fk" caption="���"  style="width:95%" />
        <!--  6 -->
        <!-- <freeze:browsebox property="parent_pk" caption="��������" valueset="JC_COLUMN_CODELIST_BY_RLTAB" show="code" multiple="true"  parameter="getZBPKParameter()" style="width:95%"/>
        <freeze:browsebox property="table_fk" caption="���" show="code" valueset="���ݱ��������ֶ��б�" parameter="getZdParameter(100)" style="width:95%" /> -->     
        <!--  7
        <freeze:browsebox property="gen_code_column" show="code" valueset="���ݱ��������ֶ��б�" caption="�ִܾ����ֶ�" style="width:95%" parameter="getZdParameter(36)"/>
        <freeze:browsebox property="prov_code_column" show="code" valueset="���ݱ��������ֶ��б�" caption="ʡ�ִ����ֶ�" style="width:95%" parameter="getZdParameter(36)"/>-->
        <!--  8 
        <freeze:browsebox property="city_code_column" valueset="���ݱ��������ֶ��б�" show="code" caption="�оִ����ֶ�" style="width:95%" parameter="getZdParameter(36)"/>
        <freeze:browsebox property="content" valueset="���ݱ��������ֶ��б�" show="code" caption="���������ֶ�" style="width:95%" parameter="getZdParameter(2000)"/> -->
        <!-- 9 -->
   		<freeze:textarea property="table_use" caption="��;" colspan="2" maxlength="1000" style="width:98%"/>
     	<freeze:textarea property="remark" caption="��ע" colspan="2"  maxlength="2000" style="width:98%" />
      <!--  
      <freeze:hidden property="sys_rd_unclaim_table_id" caption="�����" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="sys_rd_data_source_id" caption="����ԴID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="unclaim_table_code" caption="δ�����" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="unclaim_table_name" caption="δ���������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="object_schema" caption="����ģʽ" datatype="string" maxlength="36" style="width:95%"/>
      <freeze:text property="tb_index_name" caption="��������" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:textarea property="tb_index_columns" caption="�����ֶ�" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
      <freeze:text property="tb_pk_name" caption="������" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:textarea property="tb_pk_columns" caption="�����ֶ�" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
      <freeze:text property="cur_record_count" caption="��ǰ��¼����" datatype="string" maxlength="" style="width:95%"/>
      <freeze:textarea property="remark" caption="��ע" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
      <freeze:text property="data_object_type" caption="���ݶ�������" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:text property="timestamp" caption="ʱ���" datatype="string" maxlength="32" style="width:95%"/>
      -->
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
