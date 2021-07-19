<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ��������Ԫ�б�</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">

// ���ӻ�������Ԫ
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_rd_standard_dataelement.jsp", "���ӻ�������Ԫ", "modal" );
	page.addRecord();
}

// �޸Ļ�������Ԫ
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn7000304.do", "�޸Ļ�������Ԫ", "modal" );
	page.addParameter( "record:sys_rd_standard_dataelement_id", "primary-key:sys_rd_standard_dataelement_id" );
	page.updateRecord();
}

// ɾ����������Ԫ
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn7000305.do", "ɾ����������Ԫ" );
	page.addParameter( "record:sys_rd_standard_dataelement_id", "primary-key:sys_rd_standard_dataelement_id" );
	page.deleteRecord( "�Ƿ�ɾ��ѡ�еļ�¼" );
}

// �鿴��������Ԫ
function func_record_viewRecord()
{
	var page = new pageDefine( "/txn7000306.do", "�鿴��������Ԫ", "modal" );
	page.addParameter( "record:sys_rd_standard_dataelement_id", "primary-key:sys_rd_standard_dataelement_id" );
	page.viewRecord();
}

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{

}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="��ѯ��������Ԫ�б�"/>
<freeze:errors/>

<freeze:form action="/txn7000301">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:text property="identifier" caption="��ʶ��" datatype="string" maxlength="32" style="width:90%"/>
      <freeze:text property="column_nane" caption="�ֶ�����" datatype="string" maxlength="32" style="width:90%"/>
      <freeze:text property="cn_name" caption="��������" datatype="string" maxlength="32" style="width:90%"/>
      <freeze:text property="en_name" caption="Ӣ������" datatype="string" maxlength="32" style="width:90%"/> 
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="��ѯ��������Ԫ�б�" keylist="sys_rd_standard_dataelement_id" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="���ӻ�������Ԫ" txncode="7000303" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="�޸Ļ�������Ԫ" txncode="7000304" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="ɾ����������Ԫ" txncode="7000305" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:button name="record_viewRecord" caption="�鿴����Ԫ" txncode="7000306" enablerule="1" hotkey="" align="right" onclick="func_record_viewRecord();"/>
      <freeze:hidden property="sys_rd_standard_dataelement_id" caption="��������ԪID" style="width:10%" />
      <freeze:hidden property="standard_category" caption="�淶����"  style="width:10%" />
      <freeze:cell property="identifier" caption="��ʶ��" style="width:5%" />
      <freeze:cell property="cn_name" caption="��������" style="width:10%" visible="false" />
      <freeze:cell property="en_name" caption="Ӣ������" style="width:10%" visible="false" />
      <freeze:cell property="column_nane" caption="�ֶ�����" style="width:10%" />
      <freeze:cell property="data_type" caption="��������" valueset="�ֶ���������" style="width:8%" />
      <freeze:hidden property="data_length" caption="���ݳ���" style="width:10%" />
      <freeze:cell property="data_format" caption="���ݸ�ʽ" style="width:10%" />
      <freeze:hidden property="value_domain" caption="ֵ��" style="width:10%" />
      <freeze:cell property="jc_standar_codeindex" caption="���뼯��ʶ��" style="width:10%" />
      <freeze:cell property="representation" caption="��ʾ" style="width:10%" visible="false" />
      <freeze:hidden property="unit" caption="������λ" style="width:10%" />
      <freeze:hidden property="synonyms" caption="ͬ���" style="width:10%" />
      <freeze:cell property="version" caption="�汾" style="width:10%" />
      <freeze:cell property="memo" caption="��ע" style="width:10%" visible="false" />
      
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
