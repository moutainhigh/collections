<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
<head>
<title>��ѯ������洢�����б�</title>
</head>

<script language="javascript">
function func_go_view()
{
	var page = new pageDefine( "/txn80005201.do", "�洢���̹���" );
	page.addParameter("record:sys_rd_data_source_id","select-key:sys_rd_data_source_id");
	page.addParameter("record:db_name","select-key:db_name");
	page.addParameter("record:object_type","select-key:object_type");
	page.goPage();
}

function func_export() 
{   
    var page = new pageDefine("/txn8000276.do", "����Excel");
    page.addParameter("select-key:object_type","select-key:object_type");
    page.addParameter("record:jc_sys_name","select-key:jc_sys_name");
	page.downFile();	
}
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( __userInitPage );
</script>

<freeze:body>
<freeze:title caption="������洢�����б�"/>
<freeze:errors/>

<freeze:form action="/txn80005200">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%" columns="1">
      <freeze:hidden property="object_type" caption="���ݿ��������" datatype="string" style="width:95%"/>
      <freeze:text property="db_name" caption="����Դ����" datatype="string" maxlength="100" style="width:50%"/>
  </freeze:block>

  <freeze:grid property="record" caption="�洢������������б�" nowrap="true" keylist="sys_rd_data_source_id" width="95%" navbar="bottom" fixrow="false" multiselect="false">
  <freeze:button name="go_view" caption="�洢���̹���" txncode="80005201" enablerule="1" align="right" onclick="func_go_view();"/>
  	  <freeze:hidden property="sys_rd_data_source_id" caption="����ԴID" style="width:30%" />
  	  <freeze:hidden property="object_type" caption="���ݿ��������" style="width:5%" />
      <freeze:cell property="db_name" caption="����Դ����" style="width:30%" />
      <freeze:cell property="cnt" caption="����������" style="width:30%" />
      <!--freeze:link property="sys_svrname" caption="��������" value="����" style="width:30%" onclick="func_export();" /-->
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
