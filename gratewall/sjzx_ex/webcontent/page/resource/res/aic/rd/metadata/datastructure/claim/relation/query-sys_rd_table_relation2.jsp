<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ���ϵ�б�</title>
</head>

<script language="javascript">


// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ���ϵ�б�"/>
<freeze:errors/>

<freeze:form action="/txn80002607">
 
  <freeze:grid property="record" caption="��ѯ���ϵ�б�" keylist="sys_rd_table_relation_id" width="95%" navbar="bottom" fixrow="false">
     
      <freeze:hidden property="sys_rd_table_relation_id" caption="����ID" style="width:12%" />
      <freeze:hidden property="sys_rd_table_id" caption="����ID" style="width:12%" />
      <freeze:hidden property="table_code" caption="�������" style="width:20%" />
      <freeze:cell property="table_name" caption="������������" style="width:20%" />
      <freeze:hidden property="table_fk" caption="�����ֶ�" style="width:20%" />
      <freeze:cell property="table_fk_name" caption="�����ֶ�����" style="width:20%" />
      <freeze:hidden property="relation_table_code" caption="���������" style="width:20%" />
      <freeze:cell property="relation_table_name" caption="��������������" style="width:20%" />
      <freeze:hidden property="relation_table_fk" caption="�������ֶ�" style="width:20%" />
      <freeze:cell property="ralation_table_fk_name" caption="�������ֶ���������" style="width:20%" />
      <freeze:hidden property="sys_rd_system_id" caption="������������ID" style="width:12%" />
      <freeze:hidden property="sys_rd_data_source_id" caption="������������ԴID" style="width:12%" />
       <freeze:hidden property="ref_sys_rd_system_id" caption="��������������ID" style="width:12%" />
      <freeze:hidden property="ref_sys_rd_data_source_id" caption="��������������ԴID" style="width:12%" />
      <freeze:cell property="table_relation_type" caption="������ϵ����" style="width:10%" />    
      <freeze:cell property="remarks" caption="ע��" style="width:20%" visible="false" />
      <freeze:hidden property="timestamp" caption="ʱ���" style="width:12%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
