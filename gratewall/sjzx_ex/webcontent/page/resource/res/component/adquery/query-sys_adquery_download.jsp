<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ�߼���ѯ�������б�</title>
<%
DataBus context = (DataBus) request.getAttribute("freeze-databus");
DataBus info=context.getRecord("select-key");
%>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">

// ���Ӹ߼���ѯ
function func_record_addRecord()
{
	//alert("���ã������û�ֻ�����ر����ص����ݣ�");	
	var page = new pageDefine( "/txn6025003.do", "���Ӳ�ѯ" );
	page.addRecord();
}

// ��������
function func_record_addDownload()
{
	//alert("���ã������û�ֻ�����ر����ص����ݣ�");
	var page = new pageDefine( "/txn6025009.do", "��������" );
	page.addRecord();
}

// �޸ĸ߼���ѯ������
function func_record_updateRecord(idx,queryType)
{
	var id = getFormFieldValue("record:sys_advanced_query_id", idx);
	var page = new pageDefine( "/txn6025006.do", "�޸ĸ߼���ѯ");
	page.addValue( id, "select-key:sys_advanced_query_id" );
	page.addValue( queryType, "queryType" );
	page.addValue( id, "attribute-node:record_primary-key");
	page.addValue("update","action");
	page.updateRecord();
}

function func_record_execRecord(idx)
{
	var id = getFormFieldValue("record:sys_advanced_query_id", idx);
	var querytype = getFormFieldValue("record:query_type", idx);
	querytype = (querytype=='��ѯ' ? 0 : 1);
   	var page = new pageDefine( "/txn6025004.do", "ִ�и߼���ѯ");
	page.addValue( id, "select-key:sys_advanced_query_id" );
	page.addValue( querytype, "select-key:queryType" );
   	page.goPage();
}

function func_record_detail(idx)
{
	var id = getFormFieldValue("record:sys_advanced_query_id", idx);
   	var page = new pageDefine( "/txn6025005.do", "�߼���ѯ����","modal");
	page.addValue( id, "select-key:sys_advanced_query_id" );
   	page.goPage();
}

// ɾ���߼���ѯ������
function func_record_deleteRecord(idx,qtype)
{
	var id = getFormFieldValue("record:sys_advanced_query_id", idx);
   	var page = new pageDefine( "/txn6025007.do", "ɾ����ѯ����","modal");
	page.addValue( id, "select-key:sys_advanced_query_id" );
	page.addValue( qtype, "queryType" );
   	page.goPage();
}

function resetForm(){
	setFormFieldValue("select-key:name", 0 ,"");
	setFormFieldValue("select-key:query_type", 0 ,"");
	setFormFieldValue("select-key:create_date_begin", 0 ,"");
	setFormFieldValue("select-key:create_date_end", 0 ,"");
}

function submitForm(){
  _querySubmit();
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	var query_type='<%=info.getValue("query_type")%>';
	setFormFieldValue("select-key:query_type", 0 ,query_type);
	var ids = getFormAllFieldValues("record:sys_advanced_query_id");
	var types = getFormAllFieldValues("record:query_type");
	for(var i=0;i<ids.length;i++){
	   if(types[i]=='��ѯ'){
	   document.getElementsByName("span_record:operation")[i].innerHTML +='<a href="#" title="����" onclick="func_record_detail('+i+');"><div class="detail"></div></a> <a href="#" title="�޸�" onclick="func_record_updateRecord('+i+',\'0\')"><div class="edit"></div></a> '+
	     '<a href="#" title="ִ�в�ѯ" onclick="func_record_execRecord('+i+');"><div class=\'run\'></div></a> '+
	     '<a href="#" title="ɾ��" onclick="func_record_deleteRecord('+i+',\'0\');"><div class=\'delete\'></div></a>';
	   }else{
	   document.getElementsByName("span_record:operation")[i].innerHTML +='<a href="#" title="����"  onclick="func_record_detail('+i+');"><div class="detail"></div></a> <a href="#" title="�޸�" onclick="func_record_updateRecord('+i+',\'1\')"><div class="edit"></div></a> '+
	     '<a href="#" title="ִ������" onclick="func_record_execRecord('+i+');"><div class=\'run\'></div></a></a> '
	     +'<a href="#" title="ɾ��" onclick="func_record_deleteRecord('+i+',\'1\');"><div class=\'delete\'></div></a>';
	   
	   }
	}
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ�߼���ѯ�������б�"/>
<freeze:errors/>

<freeze:form action="/txn6025001">
  <freeze:frame property="select-key" width="95%">
  </freeze:frame>
   <freeze:block property="select-key" caption="��ѯ����" width="95%">
      <freeze:text property="name" caption="����" datatype="string" maxlength="30" style="width:90%"/>
       <td id='label:select-key:query_type'>����</td>
      <td>
      <select name='select-key:query_type' id='select-key:query_type'  notnull='false' readOnly='false' style="width:90%" >
			<option value="">ȫ��</option>
			<option value="��ѯ">��ѯ</option>
			<option value="����">����</option>
        </select></td></tr>
      <freeze:datebox property="create_date_begin" caption="��������" prefix="<table width='90%' border='0' cellpadding='0' cellspacing='0'><tr><td width='45%'>" style="width:100%"/>
    </td><td width='5%'>��</td><td width='45%'>
    <freeze:datebox property="create_date_end" caption="��������" style="width:100%" colspan="0"/>
    </td></tr></table>    
      <td colspan="2" ></td></tr>
      <freeze:button name="queryButton" caption="�� ѯ" onclick="submitForm()"/>
      <freeze:button name="resetButton" caption="�� ��" onclick="resetForm()"/>
  </freeze:block>
  <br/>

  <freeze:grid property="record" caption="�߼���ѯ�������б�" width="95%" navbar="bottom" fixrow="false" checkbox="false">
      <freeze:button name="record_addRecord" caption="������ѯ" txncode="6025003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      &nbsp;&nbsp;&nbsp;&nbsp;
      <freeze:button name="record_updateRecord" caption="��������" txncode="6025009" enablerule="0" hotkey="UPDATE" align="right" onclick="func_record_addDownload();"/>
      
      <freeze:cell align="middle" property="@rowid" caption="���" style="width:5%"/>
      <freeze:cell property="name" caption="����" style="width:25%" />
      <freeze:cell property="query_type" caption="����" style="width:5%" />
      <freeze:cell property="create_date" caption="��������" style="width:13%" />
      <freeze:cell property="last_exec_date" caption="���ִ������" style="width:13%" />
      <freeze:cell property="last_down_date" caption="�������ʱ��" style="width:20%" />
      <freeze:cell property="operation" caption="����" align="center" style="" />
      
      <freeze:hidden property="sys_svr_user_id" caption="�û�id"  />
      <freeze:hidden property="is_mutil_download" caption="�Ƿ��ظ�����" />
      <freeze:hidden property="apply_count" caption="�������" />
      <freeze:hidden property="last_down_date" caption="�������ʱ��" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
