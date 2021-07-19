<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯָ�����б�</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">

// ����ָ����
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_rd_standard_column.jsp", "����ָ����", "modal" );
	page.addParameter( "select-key:sys_rd_standard_table_id", "record:sys_rd_standard_table_id" );
	page.addRecord();
}

// �޸�ָ����
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn7000224.do", "�޸�ָ����", "modal" );
	page.addParameter( "record:sys_rd_standard_column_id", "primary-key:sys_rd_standard_column_id" );
	page.updateRecord();
}

// ɾ��ָ����
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn7000225.do", "ɾ��ָ����" );
	page.addParameter( "record:sys_rd_standard_column_id", "primary-key:sys_rd_standard_column_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// �鿴ָ����
function func_record_viewRecord()
{
	var page = new pageDefine( "/txn7000226.do", "�鿴ָ����", "modal" );
	page.addParameter( "record:sys_rd_standard_column_id", "primary-key:sys_rd_standard_column_id" );
	page.viewRecord();
}

//����
function func_record_gobackRecord()
{
	window.location.href='/txn7000211.do?select-key:standard_name='+getvl("backkey");
	//goBack('/txn7000211.do');
}

//��ȡurl�е�ֵ
function getvl(name) {
	var reg = new RegExp("(^|\\?|&)"+ name +"=([^&]*)(\\s|&|$)", "i");
	if (reg.test(location.href)) return unescape(RegExp.$2.replace(/\+/g, " "));
		return "";
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯָ�����б�"/>
<freeze:errors/>

<freeze:form action="/txn7000221">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:hidden property="sys_rd_standard_table_id" caption="��Ϣʵ��ID" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="table_name" caption="��Ϣʵ������" datatype="string" maxlength="100" style="width:95%" readonly="true"/>
      <freeze:text property="cn_name" caption="ָ��������" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="column_name" caption="�ֶ�����" datatype="string" maxlength="100" style="width:95%"/>
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="��ѯָ�����б�" keylist="sys_rd_standard_column_id" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="����ָ����" txncode="7000223" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸�ָ����" txncode="7000224" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ��ָ����" txncode="7000225" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
       <freeze:button name="record_viewRecord" caption="�鿴ָ����" txncode="7000226" enablerule="1"  align="right" onclick="func_record_viewRecord();"/>
      <freeze:button name="record_gobackRecord" caption="����"  enablerule="0"  align="right" onclick="func_record_gobackRecord();"/>
      <freeze:hidden property="sys_rd_standard_column_id" caption="ָ����ID" style="width:11%" />
      <freeze:hidden property="sys_rd_standard_table_id" caption="��Ϣʵ��ID" style="width:11%" />
      <freeze:cell property="data_element_identifier" caption="����Ԫ��ʶ��" valueset="����Ԫ��ʶ��" style="width:9%" />
      <freeze:cell property="cn_name" caption="ָ��������" style="width:15%" />
      <freeze:cell property="column_name" caption="�ֶ�����" style="width:19%" />
      <freeze:cell property="column_type" caption="��������" valueset="�ֶ���������" style="width:10%" />
      <freeze:cell property="column_format" caption="��ʽ" style="width:10%" />
      <freeze:cell property="code_identifier" caption="�����ʶ��" style="width:10%" />
      <freeze:hidden property="memo" caption="��ע" style="width:20%" visible="false" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
