<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ�����б�</title>
</head>

<script language="javascript">

// ����ϵͳ
function func_record_addRecord()
{
	var page = new pageDefine( "<%=request.getContextPath()%>/dw/metadata/datadict/ywxtdy/insert-sys_system_semantic.jsp", "��������", "modal" );
	page.addRecord();
}

// �޸�ϵͳ
function func_record_updateRecord(index)
{
    document.getElementsByName("record:_flag")[index].checked = false;
	var page = new pageDefine( "/txn30401004.do", "�޸�����", "modal" );	
	var sys_id = getFormFieldValue("record:sys_id", index);
	page.addValue( sys_id, "primary-key:sys_id" );
	page.updateRecord();
}

// ɾ��ϵͳ
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn30401005.do", "ɾ������" );
	page.addParameter( "record:sys_id", "primary-key:sys_id" );
	page.addParameter( "record:sys_name", "primary-key:sys_name" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var operationSpan = document.getElementsByName("span_record:operation");
	for (var i=0; i < operationSpan.length; i++){
		operationSpan[i].innerHTML = "<a onclick='func_record_updateRecord("+i+");' href='#'>�޸�</a>";
	}	
	
}

function funcQueryInfo(){    
	if (event.srcElement.tagName.toUpperCase() == 'INPUT'){
		return;
	}
	if (event.srcElement.tagName.toUpperCase() == 'A'){
		return;
	}	
	var index = getSelectedRowid("record");
	var sys_id = getFormFieldText("record:sys_id",index);  
	var page = new pageDefine("/txn30402001.do", "��ϸ��Ϣ");	
	page.addValue( sys_id, "select-key:sys_id" );
	page.goPage();	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ�����б�"/>
<freeze:errors/>

<freeze:form action="/txn30401001">
  <freeze:grid property="record" caption="�����б�" keylist="sys_id" width="95%" navbar="bottom" fixrow="false" rowselect="false" onclick="funcQueryInfo();" >
      <freeze:button name="record_addRecord" caption="����" txncode="30401003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ��" txncode="30401005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:hidden property="sys_id" caption="����ID" style="width:15%" />
      <freeze:cell property="sys_name" caption="��������" style="width:48%" />
      <freeze:cell property="sys_simple" caption="��ע" style="width:47%" />
      <freeze:hidden property="sys_order" caption="����˳��" style="width:20%" />
      <freeze:cell property="operation" caption="����" align="center" style="width:5%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
