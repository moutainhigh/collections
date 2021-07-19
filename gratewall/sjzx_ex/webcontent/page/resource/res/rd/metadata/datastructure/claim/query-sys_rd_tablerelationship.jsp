<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ�������ϵ</title>
</head>

<script language="javascript">


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
<freeze:title caption="��ѯ�������б�"/>
<freeze:errors/>

<freeze:form action="/txn8000801">
   <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:text property="db_name" caption="����Դ����" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="table_code" caption="������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="table_primary_key" caption="����" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="table_fk" caption="���" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="parent_table" caption="����" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="parent_pk" caption="��������" datatype="string" maxlength="32" style="width:95%"/>
      
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="��ѯ�������ϵ" keylist="sys_rd_table_id" width="95%" checkbox="false" navbar="bottom" fixrow="false">
      <freeze:cell property="operation" caption="���" style="width:5%" />
      <freeze:cell property="db_name" caption="����Դ����" style="width:15%" />
      <freeze:cell property="table_code" caption="������" style="width:20%" />
      <freeze:cell property="table_primary_key" caption="����" style="width:15%" />
      <freeze:cell property="table_fk" caption="���" style="width:15%" />
      <freeze:cell property="parent_table" caption="����" style="width:13%" />
      <freeze:cell property="parent_pk" caption="��������" style="width:15%" />
      
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
