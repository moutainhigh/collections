<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯҵ���ֶ��б�</title>
</head>

<script language="javascript">

// ����ҵ���ֶ�
function func_record_addRecord()
{
	var page = new pageDefine( "<%=request.getContextPath()%>/dw/metadata/datadict/ywbzdyydy/insert-sys_column_semantic.jsp", "����ҵ���ֶ�", "modal" );
	page.addRecord();
}

// �޸�ҵ���ֶ�
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn30403004.do", "�޸�ҵ���ֶ�", "modal" );
	page.addParameter( "record:column_no", "primary-key:column_no" );
	page.updateRecord();
}

// ɾ��ҵ���ֶ�
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn30403005.do", "ɾ��ҵ���ֶ�" );
	page.addParameter( "record:column_no", "primary-key:column_no" );
	page.addParameter( "record:column_name", "primary-key:column_name" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

function getParameter()
{
	var sys_id = getFormFieldValue("select-key:sys_id");
	if (sys_id != "")
	{
		var parameter = 'sys_id=' + sys_id;
		return parameter;
	}else{
	    alert("��ѡ��ҵ������");
	    return;
	}
}

function resetTableNo()
{
	if (getFormFieldValue("select-key:table_no") == null || getFormFieldValue("select-key:table_no") == "")
	{
	}
	else{
		setFormFieldValue("select-key:table_no","")
	}
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var operationSpan = document.getElementsByName("span_record:operation");
	for (var i=0; i < operationSpan.length; i++){
		operationSpan[i].innerHTML = "<a onclick='setCurrentRowChecked(\"record\");func_record_updateRecord();' href='#'>�޸�</a>";
	}	
	var node = top.menu.lookupItem("30413000");
	if (node){
		top.menu.setSelectedNode(node);
	}
	node = null;	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯҵ���ֶ��б�"/>
<freeze:errors/>

<freeze:form action="/txn30403001">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:browsebox property="sys_id" caption="ҵ������" valueset="ҵ��ϵͳ" show="name" style="width:95%" data="code" onchange="resetTableNo()"/>
      <freeze:browsebox property="table_no" caption="��������" valueset="ҵ��������ձ�" show="name" data="code" style="width:95%" parameter="getParameter()"/>
      <freeze:text property="column_name" caption="ҵ���ֶ���" datatype="string" maxlength="60" style="width:95%"/>
      <freeze:text property="column_name_cn" caption="ҵ���ֶ�������" datatype="string" maxlength="100" style="width:95%"/>
  </freeze:block>
  <br> 
  <freeze:grid property="record" caption="ҵ���ֶ��б�" keylist="column_no" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="����" txncode="30403003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ��" txncode="30403005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:hidden property="column_no" caption="�ֶα���" style="width:8%" />
      <freeze:cell property="sys_name" caption="ҵ������" style="width:12%" />
      <freeze:cell property="table_name_cn" caption="ҵ���" style="width:22%" />
      <freeze:cell property="column_name_cn" caption="ҵ���ֶ�������" style="width:12%" />
      <freeze:cell property="column_name" caption="ҵ���ֶ���" style="width:23%" />      
      <freeze:cell property="edit_type" caption="�ֶ�����" valueset="�ֶ�����" style="width:10%" />
      <freeze:cell property="edit_content" caption="�ֶγ���" style="width:10%" />
      <freeze:cell property="demo" caption="��ע" style="width:10%" />
      <freeze:cell property="operation" caption="����" align="center" style="width:5%" />
      <freeze:hidden property="sys_order" caption="����˳��" style="width:20%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
