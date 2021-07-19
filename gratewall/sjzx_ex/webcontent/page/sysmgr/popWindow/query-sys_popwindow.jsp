<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ���������б�</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">

// ���ӵ�������
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_popwindow.jsp", "���ӵ�������", "modal" );
	page.addRecord();
}

// �޸ĵ�������
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn60800004.do", "�޸ĵ�������", "modal" );
	page.addParameter( "record:sys_popwindow_id", "primary-key:sys_popwindow_id" );
	page.updateRecord();
}

// ɾ����������
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn60800005.do", "ɾ����������" );
	page.addParameter( "record:sys_popwindow_id", "primary-key:sys_popwindow_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var operationSpan = document.getElementsByName("span_record:operation");
	for (var i=0; i < operationSpan.length; i++){
		 operationSpan[i].innerHTML = "<a onclick='setCurrentRowChecked(\"record\");func_record_updateRecord();' title='�޸�' href='#'><div class='edit'></div></a>"
		 +"<a onclick='setCurrentRowChecked(\"record\");func_record_deleteRecord();' title='ɾ��' href='#'><div class='delete'></div></a>";
	}
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ���������б�"/>
<freeze:errors/>

<freeze:form action="/txn60800001">
<div style="display:none">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:text property="content" caption="����" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:datebox property="expire_date" caption="����������" datatype="string" maxlength="10" style="width:95%"/>
  </freeze:block>
</div>  
  <freeze:grid property="record" caption="��ѯ���������б�" keylist="sys_popwindow_id" width="95%" navbar="bottom" fixrow="false" checkbox="false">
      <freeze:button name="record_addRecord" caption="����" txncode="60800003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ��" txncode="60800005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();" visible="false"/>
      <freeze:cell property="sys_popwindow_id" caption="ϵͳ����ID" visible="false" />
      <freeze:cell property="@rowid" caption="���" style="width:5%" align="center"/>
      <freeze:cell property="content" caption="����" style="width:15%" />
      <freeze:cell property="PUBLISH_DEPART" caption="��������" style="width:13%" />
      <freeze:cell property="publish_date" caption="��������" style="width:13%" datatype="date"/>
      <freeze:cell property="expire_date" caption="��������" style="width:13%" datatype="date"/>
      <freeze:cell property="AUTHOR" caption="������" style="width:13%" />
      <freeze:hidden property="roles" caption="��ɫ" style="width:13%"  />
      <freeze:cell property="operation" style="width:15%;" caption="����" align="center"/>
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
