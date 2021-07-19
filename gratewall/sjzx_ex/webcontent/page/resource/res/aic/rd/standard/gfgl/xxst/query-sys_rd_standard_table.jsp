<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯʵ����Ϣ�б�</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">

// ����ʵ����Ϣ
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_rd_standard_table.jsp", "������Ϣʵ��", "modal" );
	page.addParameter( "select-key:sys_rd_standard_id", "record:sys_rd_standard_id" );
	page.addParameter( "select-key:standard_name", "record:standard_name" );
	page.addRecord();
}

// �޸�ʵ����Ϣ
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn7000214.do", "�޸�ʵ����Ϣ", "modal" );
	page.addParameter( "record:sys_rd_standard_table_id", "primary-key:sys_rd_standard_table_id" );
	page.updateRecord();
}

// ɾ�����뼯
function func_record_deleteRecord()
{ 
     var page = new pageDefine( "/txn7000221.ajax", "��ѯָ����" ); 
     page.addParameter("record:sys_rd_standard_table_id", "select-key:sys_rd_standard_table_id"); 
	 page.callAjaxService('doCallback');
	 page.callAjaxService( null, true );
}

function doCallback(errorCode, errDesc, xmlResults) 
{ 
    //�����ú�������ȡʹ��Ajax�����õ�������
	if ( errorCode != '000000' ) {
		alert("��ѯ��¼ʧ�ܣ����������");
		return;
	} 
	var codeIndex = _getXmlNodeValues(xmlResults, 'record:sys_rd_standard_table_id');
	
	if(codeIndex != ''){
		alert("����ɾ�����뼯��Ĵ���ֵ��");
		return;
	}
	
	var page = new pageDefine( "/txn7000215.do", "ɾ��ʵ����Ϣ" );
	page.addParameter( "record:sys_rd_standard_table_id", "primary-key:sys_rd_standard_table_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
	
}

// �鿴ʵ����Ϣ
function func_record_viewRecord()
{
	var page = new pageDefine( "/txn7000216.do", "�鿴ʵ����Ϣ", "modal" );
	page.addParameter( "record:sys_rd_standard_table_id", "primary-key:sys_rd_standard_table_id" );
	page.viewRecord();
}

// �鿴ָ����
function func_record_cloumnRecord_button()
{
    var page = new pageDefine( "/txn7000221.do", "�鿴ָ����" );
	page.addParameter( "record:sys_rd_standard_table_id", "select-key:sys_rd_standard_table_id" );
	page.addParameter( "record:table_name", "select-key:table_name" );
	page.goPage();
}
// �鿴ָ����
function func_record_cloumnRecord()
{
	if (event.srcElement.tagName.toUpperCase() == 'LABEL'){
		return;
	}
	if (event.srcElement.tagName.toUpperCase() == 'INPUT'){
		return;
	}
	if (event.srcElement.tagName.toUpperCase() == 'A'){
		return;
	}
    var page = new pageDefine( "/txn7000221.do", "�鿴ָ����" );
	page.addParameter( "record:sys_rd_standard_table_id", "select-key:sys_rd_standard_table_id" );
	page.addParameter( "record:table_name", "select-key:table_name" );
	var urlkey = document.getElementById("select-key:standard_name");
	page.addParameter("select-key:standard_name","backkey");
	page.goPage();
}

// ����
function func_record_gobackRecord()
{
	window.location.href='/txn7000201.do';
	//goBack( "/txn7000201.do");
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯʵ����Ϣ�б�"/>
<freeze:errors/>

<freeze:form action="/txn7000211">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:hidden property="sys_rd_standard_id" caption="��׼ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="standard_name" caption="��׼����" datatype="string" maxlength="100" style="width:95%" readonly="true"/>
      <freeze:text property="table_name" caption="��Ϣʵ������" datatype="string" maxlength="100" style="width:95%"/>
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="��ѯʵ����Ϣ�б�" keylist="sys_rd_standard_table_id" width="95%" navbar="bottom" fixrow="false" onclick="func_record_cloumnRecord()">
      <freeze:button name="record_addRecord" caption="����" txncode="7000213" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸�" txncode="7000214" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ��" txncode="7000215" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:button name="record_viewRecord" caption="�鿴" txncode="7000216" enablerule="1" hotkey="" align="right" onclick="func_record_viewRecord();"/>
      <freeze:button name="record_columnRecord" caption="ָ����" txncode="7000221" enablerule="1" hotkey="" align="right" onclick="func_record_cloumnRecord_button()"/>
      <freeze:button name="record_gobackRecord" caption="����"  enablerule="0"  align="right" onclick="func_record_gobackRecord()"/>
      <freeze:hidden property="sys_rd_standard_table_id" caption="��Ϣʵ��ID" style="width:11%" />
      <freeze:hidden property="sys_rd_standard_id" caption="��׼ID" style="width:11%" />
      <freeze:hidden property="standard_name" caption="��׼����" style="width:18%" />
      <freeze:cell property="table_name" caption="��Ϣʵ������" style="width:15%" />
      <freeze:cell property="table_belongs" caption="������ϵ" style="width:20%" />
      <freeze:cell property="memo" caption="��ע" style="width:20%"  />
      <freeze:hidden property="sort" caption="�����" style="width:10%" />
      <freeze:hidden property="timestamp" caption="ʱ���" style="width:10%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
