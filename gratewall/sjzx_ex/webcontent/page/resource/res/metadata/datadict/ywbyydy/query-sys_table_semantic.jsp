<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯҵ����б�</title>
</head>

<script language="javascript">

// ����ҵ���
function func_record_addRecord()
{
	var page = new pageDefine( "<%=request.getContextPath()%>/dw/metadata/datadict/ywbyydy/insert-sys_table_semantic.jsp", "����ҵ���", "modal" );
	page.addRecord();
}

// �޸�ҵ���
function func_record_updateRecord(index)
{
	
    document.getElementsByName("record:_flag")[index].checked = false;
	var table_no = getFormFieldValue("record:table_no", index);	
	var page = new pageDefine( "/txn30402004.do", "�޸�ҵ���", "modal" );
	page.addValue( table_no, "primary-key:table_no" );
	page.updateRecord();
			
}

// ɾ��ҵ���
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn30402005.do", "ɾ��ҵ���" );
	page.addParameter( "record:table_no", "primary-key:table_no" );
	page.addParameter( "record:table_name", "primary-key:table_name" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}
function getParameter()
{
	var sys_id = getFormFieldValue("select-key:sys_id");
	if (sys_id != "")
	{
		var parameter = 'sys_id=' + sys_id;
		return parameter;
	}
}
// ����������ӣ�ҳ�������ɺ���û���ʼ������ 
function __userInitPage()
{
	var operationSpan = document.getElementsByName("span_record:operation");
	for (var i=0; i < operationSpan.length; i++){
		operationSpan[i].innerHTML = "<a onclick='func_record_updateRecord("+i+");' href='#'>�޸�</a>";
	}
	
	var node = top.menu.lookupItem("30412000");
	if (node){
		top.menu.setSelectedNode(node);
	}
	node = null;
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
	var table_no = getFormFieldText("record:table_no",index);  
	var page = new pageDefine("/txn30403001.do", "��ϸ��Ϣ");	
	page.addValue( sys_id, "select-key:sys_id" );
	page.addValue( table_no, "select-key:table_no" );
	page.goPage();
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯҵ����б�"/>
<freeze:errors/>

<freeze:form action="/txn30402001">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:browsebox property="sys_id" caption="ҵ������" valueset="ҵ��ϵͳ" data="code" show="name" maxlength="2" style="width:95%"/>
      <freeze:browsebox property="table_name_cn" caption="ҵ���������" valueset="ҵ��������ձ�" show="name" data="name" style="width:95%" parameter="getParameter()"/>
      <freeze:text property="table_name" caption="ҵ�����" datatype="string" maxlength="100" style="width:95%"/>
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="ҵ����б�" keylist="table_no" width="95%" navbar="bottom" rowselect="false" fixrow="fasle"  onclick="funcQueryInfo();">
      <freeze:button name="record_addRecord" caption="����" txncode="30402003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ��" txncode="30402005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:hidden property="table_no" caption="ҵ������" style="width:15%" />
      <freeze:hidden property="sys_id" caption="ҵ������"/>
      <freeze:cell property="sys_id" caption="ҵ������" style="width:20%" valueset="ҵ��ϵͳ"/>
      <freeze:cell property="table_name_cn" caption="ҵ���������" style="width:17%" />
      <freeze:cell property="table_name" caption="ҵ�����" style="width:18%" />
      <freeze:cell property="demo" caption="��ע" style="width:15%" />   
      <freeze:hidden property="table_order" caption="����˳��" style="width:10%" />   
      <freeze:cell property="downloadflag" caption="�Ƿ������" valueset="��������" style="width:10%" />
      <freeze:cell property="operation" caption="����" align="center" style="width:5%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
