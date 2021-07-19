<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@page import="cn.gwssi.common.context.Recordset"%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ���ϵ�б�</title>
</head>
<%
DataBus context = (DataBus) request.getAttribute("freeze-databus");
Recordset rs = context.getRecordset("record");
if(rs!=null&&rs.size()>0){
	for(int j=0; j<rs.size(); j++){
		DataBus db2 = rs.get(j);
		db2.setValue("table_name", db2.getValue("table_code")+"("+db2.getValue("table_name")+")");
		db2.setValue("table_fk_name", db2.getValue("table_fk")+"("+db2.getValue("table_fk_name")+")");
		db2.setValue("relation_table_name", db2.getValue("relation_table_code")+"("+db2.getValue("relation_table_name")+")");
		db2.setValue("relation_table_fk_name", db2.getValue("relation_table_fk")+"("+db2.getValue("relation_table_fk_name")+")");
	}
}

%>
<script language="javascript">

// ���ӱ��ϵ
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_rd_table_relation.jsp", "���ӱ��ϵ", "modal" );
	page.addRecord();
}

// �޸ı��ϵ
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn80002604.do", "�޸ı��ϵ", "modal" );
	page.addParameter( "record:sys_rd_table_relation_id", "primary-key:sys_rd_table_relation_id" );
	page.updateRecord();
}

// ɾ�����ϵ
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn80002605.do", "ɾ�����ϵ" );
	page.addParameter( "record:sys_rd_table_relation_id", "primary-key:sys_rd_table_relation_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ���ϵ�б�"/>
<freeze:errors/>

<freeze:form action="/txn80002601">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:text property="table_name" caption="��������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="table_code" caption="�������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="table_fk" caption="�����ֶδ���" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="table_fk_name" caption="�����ֶ�����" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="relation_table_name" caption="����������" datatype="string" maxlength="100" style="width:95%"/>
       <freeze:text property="relation_table_code" caption="���������" datatype="string" maxlength="100" style="width:95%"/>
       <freeze:text property="relation_table_fk" caption="�������ֶ�" datatype="string" maxlength="100" style="width:95%"/>
       <freeze:text property="relation_table_fk_name" caption="�������ֶ�����" datatype="string" maxlength="100" style="width:95%"/>
      
  </freeze:block>
<br/>
  <freeze:grid property="record" caption="��ѯ���ϵ�б�" checkbox="false"  width="95%" navbar="bottom" fixrow="false">
      <freeze:hidden property="sys_rd_table_relation_id" caption="����ID" style="width:12%" />
      <freeze:hidden property="sys_rd_table_id" caption="����ID" style="width:12%" />
      <freeze:hidden property="table_code" caption="�������" style="width:20%" />
      <freeze:cell property="@rowid" caption="���"  style="width:6%" align="center" /> 
      <freeze:cell property="table_name" caption="����" style="width:22%" />
      <freeze:hidden property="table_fk" caption="�����ֶ�" style="width:20%" />
      <freeze:cell property="table_fk_name" caption="�����ֶ�" style="width:22%" />
      <freeze:hidden property="relation_table_code" caption="���������" style="width:20%" />
      <freeze:cell property="relation_table_name" caption="������" style="width:22%" />
      <freeze:hidden property="relation_table_fk" caption="�������ֶ�" style="width:20%" />
      <freeze:cell property="relation_table_fk_name" caption="�������ֶ�" style="width:22%" />
      <freeze:hidden property="sys_rd_system_id" caption="������������ID" style="width:12%" />
      <freeze:hidden property="sys_rd_data_source_id" caption="������������ԴID" style="width:12%" />
      <freeze:hidden property="ref_sys_rd_system_id" caption="��������������ID" style="width:12%" />
      <freeze:hidden property="ref_sys_rd_data_source_id" caption="��������������ԴID" style="width:12%" />
      <freeze:hidden property="table_relation_type" caption="������ϵ����" style="width:10%" />    
      <freeze:hidden property="remarks" caption="ע��" style="width:20%" visible="false" />
      <freeze:hidden property="timestamp" caption="ʱ���" style="width:12%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
