<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询基础数据元列表</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
</head>
<script language="javascript">

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	$("#customPageRowSeleted option:gt(0)").remove();
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:errors/>

<freeze:form action="/txn7004401">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:hidden property="identifier" caption="标识符" datatype="string" maxlength="32" style="width:90%"/>
      <freeze:text property="column_nane" caption="字段名称" datatype="string" maxlength="32" style="width:90%"/>
      <freeze:text property="cn_name" caption="中文名称" datatype="string" maxlength="32" style="width:90%"/>
      <freeze:hidden property="en_name" caption="英文名称" datatype="string" maxlength="32" style="width:90%"/> 
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="查询基础数据元列表" keylist="sys_rd_standard_dataelement_id" width="95%" checkbox="false" rowselect="false" navbar="bottom" fixrow="false">
      <freeze:hidden property="sys_rd_standard_dataelement_id" caption="基础数据元ID" style="width:10%" />
      <freeze:hidden property="standard_category" caption="规范类型"  style="width:10%" />
      <freeze:hidden property="identifier" caption="标识符" style="width:10%" />
      <freeze:cell property="cn_name" caption="中文名称" style="width:30%" />
      <freeze:hidden property="en_name" caption="英文名称" style="width:10%" />
      <freeze:cell property="column_nane" caption="字段名称" style="" />
      <freeze:cell property="data_type" caption="数据类型" valueset="字段数据类型" style="width:20%" />
      <freeze:hidden property="data_length" caption="数据长度" style="width:10%" />
      <freeze:hidden property="data_format" caption="数据格式" style="width:10%" />
      <freeze:hidden property="value_domain" caption="值域" style="width:10%" />
      <freeze:hidden property="jc_standar_codeindex" caption="代码集标识符" style="width:10%" />
      <freeze:hidden property="representation" caption="表示" style="width:10%" />
      <freeze:hidden property="unit" caption="计量单位" style="width:10%" />
      <freeze:hidden property="synonyms" caption="同义词" style="width:10%" />
      <freeze:hidden property="version" caption="版本" style="width:10%" />
      <freeze:hidden property="memo" caption="备注" style="width:10%" />
      
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
