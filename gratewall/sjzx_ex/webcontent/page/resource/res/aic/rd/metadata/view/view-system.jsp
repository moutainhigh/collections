<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
<head>
<title>��ͼ��������б�</title>
</head>

<script language="javascript">
function func_export() 
{   
    var page = new pageDefine("/txn8000272.do", "����Excel");
    page.addParameter("select-key:object_type","select-key:object_type");
    page.addParameter("record:data_source_id","select-key:data_source_id");
	page.downFile();	
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

_browse.execute( __userInitPage );
</script>

<freeze:body>
<freeze:title caption="��ͼ��������б�"/>
<freeze:errors/>

<freeze:form action="/txn80003000">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%" columns="1">
      <freeze:hidden property="object_type" caption="���ݿ��������" datatype="string" style="width:95%"/>
      <freeze:text property="db_name" caption="����Դ����" datatype="string" maxlength="16" style="width:50%"/>
  </freeze:block>

  <freeze:grid property="record" caption="��ͼ�������"  keylist="sys_rd_data_source_id" width="95%" checkbox="false" navbar="bottom" fixrow="false">
      <freeze:cell property="operation" caption="���" style="width:3%"/>
      <freeze:cell property="db_name" caption="����Դ����" style="width:20%" />
      <freeze:cell property="total" caption="��ͼ����" style="width:18%" />
      <freeze:cell property="wrl" caption="δ������" style="width:18%" />
      <freeze:cell property="yrl" caption="��������" style="width:20%" />
      <freeze:hidden property="sys_svrname" caption="��������" value="����" style="width:14%" onclick="func_export();" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
