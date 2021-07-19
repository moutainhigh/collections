<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询基础数据元列表</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script language="javascript">

// 增加基础数据元
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_rd_standard_dataelement.jsp", "增加基础数据元", "modal" );
	page.addRecord();
}

// 修改基础数据元
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn7000304.do", "修改基础数据元", "modal" );
	page.addParameter( "record:sys_rd_standard_dataelement_id", "primary-key:sys_rd_standard_dataelement_id" );
	page.updateRecord();
}

// 删除基础数据元
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn7000305.do", "删除基础数据元" );
	page.addParameter( "record:sys_rd_standard_dataelement_id", "primary-key:sys_rd_standard_dataelement_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 查看基础数据元
function func_record_viewRecord()
{
	var page = new pageDefine( "/txn7000306.do", "查看基础数据元", "modal" );
	page.addParameter( "record:sys_rd_standard_dataelement_id", "primary-key:sys_rd_standard_dataelement_id" );
	page.viewRecord();
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{

}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询基础数据元列表"/>
<freeze:errors/>

<freeze:form action="/txn7000301">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:text property="identifier" caption="标识符" datatype="string" maxlength="32" style="width:90%"/>
      <freeze:text property="column_nane" caption="字段名称" datatype="string" maxlength="32" style="width:90%"/>
      <freeze:text property="cn_name" caption="中文名称" datatype="string" maxlength="32" style="width:90%"/>
      <freeze:text property="en_name" caption="英文名称" datatype="string" maxlength="32" style="width:90%"/> 
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="查询基础数据元列表" keylist="sys_rd_standard_dataelement_id" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="增加基础数据元" txncode="7000303" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改基础数据元" txncode="7000304" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除基础数据元" txncode="7000305" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:button name="record_viewRecord" caption="查看数据元" txncode="7000306" enablerule="1" hotkey="" align="right" onclick="func_record_viewRecord();"/>
      <freeze:hidden property="sys_rd_standard_dataelement_id" caption="基础数据元ID" style="width:10%" />
      <freeze:hidden property="standard_category" caption="规范类型"  style="width:10%" />
      <freeze:cell property="identifier" caption="标识符" style="width:5%" />
      <freeze:cell property="cn_name" caption="中文名称" style="width:10%" visible="false" />
      <freeze:cell property="en_name" caption="英文名称" style="width:10%" visible="false" />
      <freeze:cell property="column_nane" caption="字段名称" style="width:10%" />
      <freeze:cell property="data_type" caption="数据类型" valueset="字段数据类型" style="width:8%" />
      <freeze:hidden property="data_length" caption="数据长度" style="width:10%" />
      <freeze:cell property="data_format" caption="数据格式" style="width:10%" />
      <freeze:hidden property="value_domain" caption="值域" style="width:10%" />
      <freeze:cell property="jc_standar_codeindex" caption="代码集标识符" style="width:10%" />
      <freeze:cell property="representation" caption="表示" style="width:10%" visible="false" />
      <freeze:hidden property="unit" caption="计量单位" style="width:10%" />
      <freeze:hidden property="synonyms" caption="同义词" style="width:10%" />
      <freeze:cell property="version" caption="版本" style="width:10%" />
      <freeze:cell property="memo" caption="备注" style="width:10%" visible="false" />
      
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
