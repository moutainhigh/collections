<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询数据增长列表</title>
</head>

<script language="javascript">

// 增加数据增长
function func_record_addRecord()
{
	var page = new pageDefine( "insert-view_sys_count_log.jsp", "增加数据增长", "modal" );
	page.addRecord();
}

// 修改数据增长
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn620100304.do", "修改数据增长", "modal" );
	page.addParameter( "record:table_class_id", "primary-key:table_class_id" );
	page.updateRecord();
}

// 删除数据增长
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn620100305.do", "删除数据增长" );
	page.addParameter( "record:table_class_id", "primary-key:table_class_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询数据增长列表"/>
<freeze:errors/>

<freeze:form action="/txn620100301">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:text property="sys_name" caption="主题名称" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="table_name_cn" caption="表中文名" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="count_date" caption="统计日期" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="class_state" caption="分类状态" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

  <freeze:grid property="record" caption="查询数据增长列表" keylist="table_class_id" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="增加数据增长" txncode="620100303" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改数据增长" txncode="620100304" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除数据增长" txncode="620100305" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="sys_name" caption="主题名称" style="width:20%" />
      <freeze:cell property="table_name" caption="表名" style="width:20%" />
      <freeze:cell property="table_name_cn" caption="表中文名" style="width:20%" />
      <freeze:cell property="class_sort" caption="分类排序" style="width:12%" />
      <freeze:cell property="class_state" caption="分类状态" style="width:12%" />
      <freeze:cell property="count_date" caption="统计日期" style="width:10%" />
      <freeze:cell property="count_full" caption="日全量" style="width:10%" />
      <freeze:cell property="count_incre" caption="日增量" style="width:10%" />
      <freeze:cell property="table_class_id" caption="分类主键id" style="width:16%" />
      <freeze:cell property="sys_order" caption="主体排序字段" style="width:10%" />
      <freeze:cell property="table_order" caption="表排序字段" style="width:10%" />
      <freeze:cell property="sort_order" caption="分类排序" style="width:10%" />
      <freeze:cell property="state_order" caption="状态排序" style="width:10%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
