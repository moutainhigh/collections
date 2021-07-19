<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ�����б�</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">

// ��������
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_rd_standard_term.jsp", "��������", "modal" );
	page.addRecord();
}

// �޸�����
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn7000104.do", "�޸�����", "modal" );
	page.addParameter( "record:sys_rd_standar_term_id", "primary-key:sys_rd_standar_term_id" );
	page.updateRecord();
}

// ɾ������
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn7000105.do", "ɾ������" );
	page.addParameter( "record:sys_rd_standar_term_id", "primary-key:sys_rd_standar_term_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// ɾ������
function func_record_viewRecord()
{
	var page = new pageDefine( "/txn7000106.do", "�鿴����", "modal" );
	page.addParameter( "record:sys_rd_standar_term_id", "primary-key:sys_rd_standar_term_id" );
	page.viewRecord( );
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{

}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ�����б�"/>
<freeze:errors/>

<freeze:form action="/txn7000101">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:text property="standard_term_cn" caption="��������" datatype="string" maxlength="32" style="width:90%"/>
       <freeze:text property="standard_term_en" caption="����Ӣ��" datatype="string" maxlength="32" style="width:90%"/>
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="��ѯ�����б�" keylist="sys_rd_standar_term_id" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="��������" txncode="7000103" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸�����" txncode="7000104" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ������" txncode="7000105" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:button name="record_viewRecord" caption="�鿴����" txncode="7000106" enablerule="1" hotkey="" align="right" onclick="func_record_viewRecord();"/>
      <freeze:hidden property="sys_rd_standar_term_id" caption="����ID" style="width:20%" />
      <freeze:cell property="standard_term_cn" caption="��������" style="width:20%" />
      <freeze:cell property="standard_term_en" caption="Ӣ������" style="width:20%" />
      <freeze:cell property="standard_term_definition" caption="���ﶨ��" style="width:60%"  />
      <freeze:hidden property="memo" caption="��ע" style="width:15%"  />
      
      
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
