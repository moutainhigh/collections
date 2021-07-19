<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ���������б�</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">

// ���ӻ�������
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_rd_standard_codedata.jsp", "���ӻ�������", "modal" );
	page.addParameter( "select-key:sys_rd_standar_codeindex", "record:sys_rd_standar_codeindex" );
	page.addRecord();
}

// �޸Ļ�������
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn7000414.do", "�޸Ļ�������", "modal" );
	page.addParameter( "record:id", "primary-key:id" );
	page.updateRecord();
}

// ɾ����������
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn7000415.do", "ɾ����������" );
	page.addParameter( "record:id", "primary-key:id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// ���ش��뼯
function func_record_gobackRecord()
{
	window.location.href='/txn7000401.do';
	//goBack( "/txn7000401.do");
}
// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	//alert(window.location.pathname);
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ���������б�"/>
<freeze:errors/>

<freeze:form action="/txn7000411">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:text property="sys_rd_standar_codeindex" caption="���뼯��ʶ��" maxlength="10" datatype="string" readonly='true' style="width:95%"/>
      <freeze:text property="codeindex_name" caption="���뼯����" datatype="string" readonly='true' style="width:95%"/>
      <freeze:text property="sys_rd_standard_codevalue" caption="����ֵ" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="sys_rd_standard_codename" caption="��������" datatype="string" maxlength="10" style="width:95%"/>
     
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="��ѯ���������б�" keylist="id" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="���ӻ�������" txncode="7000413" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸Ļ�������" txncode="7000414" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ����������" txncode="7000415" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
       <freeze:button name="record_gobackRecord" caption="����"  enablerule="0" hotkey="" align="right" onclick="func_record_gobackRecord();"/>
      <freeze:hidden property="id" caption="����ֵID" style="width:34%" />
      <freeze:hidden property="sys_rd_standar_codeindex" caption="���뼯��ʶ��" style="width:30%" />
      <freeze:cell property="sys_rd_standard_codevalue" caption="����ֵ" style="width:30%" />
      <freeze:cell property="sys_rd_standard_codename" caption="��������" style="width:20%"  />
      <freeze:cell property="description" caption="˵��" style="width:20%"  />
      
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
