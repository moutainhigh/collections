<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>��ѯ��������Ԫ�б�</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
</head>
<script language="javascript">

// ����������ӣ�ҳ�������ɺ���û���ʼ������
function __userInitPage()
{
	$("#customPageRowSeleted option:gt(0)").remove();
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:errors/>

<freeze:form action="/txn7004401">
  <freeze:block theme="query" property="select-key" caption="��ѯ����" width="95%">
      <freeze:hidden property="identifier" caption="��ʶ��" datatype="string" maxlength="32" style="width:90%"/>
      <freeze:text property="column_nane" caption="�ֶ�����" datatype="string" maxlength="32" style="width:90%"/>
      <freeze:text property="cn_name" caption="��������" datatype="string" maxlength="32" style="width:90%"/>
      <freeze:hidden property="en_name" caption="Ӣ������" datatype="string" maxlength="32" style="width:90%"/> 
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="��ѯ��������Ԫ�б�" keylist="sys_rd_standard_dataelement_id" width="95%" checkbox="false" rowselect="false" navbar="bottom" fixrow="false">
      <freeze:hidden property="sys_rd_standard_dataelement_id" caption="��������ԪID" style="width:10%" />
      <freeze:hidden property="standard_category" caption="�淶����"  style="width:10%" />
      <freeze:hidden property="identifier" caption="��ʶ��" style="width:10%" />
      <freeze:cell property="cn_name" caption="��������" style="width:30%" />
      <freeze:hidden property="en_name" caption="Ӣ������" style="width:10%" />
      <freeze:cell property="column_nane" caption="�ֶ�����" style="" />
      <freeze:cell property="data_type" caption="��������" valueset="�ֶ���������" style="width:20%" />
      <freeze:hidden property="data_length" caption="���ݳ���" style="width:10%" />
      <freeze:hidden property="data_format" caption="���ݸ�ʽ" style="width:10%" />
      <freeze:hidden property="value_domain" caption="ֵ��" style="width:10%" />
      <freeze:hidden property="jc_standar_codeindex" caption="���뼯��ʶ��" style="width:10%" />
      <freeze:hidden property="representation" caption="��ʾ" style="width:10%" />
      <freeze:hidden property="unit" caption="������λ" style="width:10%" />
      <freeze:hidden property="synonyms" caption="ͬ���" style="width:10%" />
      <freeze:hidden property="version" caption="�汾" style="width:10%" />
      <freeze:hidden property="memo" caption="��ע" style="width:10%" />
      
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
