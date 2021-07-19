<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询共享表列表</title>
</head>

<script language="javascript">

// 增加共享表
function func_record_addRecord()
{
	var page = new pageDefine( "insert-res_share_table.jsp", "增加共享表", "modal" );
	page.addRecord();
}

// 修改共享表
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn20301004.do", "修改共享表", "modal" );
	page.addParameter( "record:share_table_id", "primary-key:share_table_id" );
	page.updateRecord();
}

// 删除共享表
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn20301005.do", "删除共享表" );
	page.addParameter( "record:share_table_id", "primary-key:share_table_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询共享表列表"/>
<freeze:errors/>

<freeze:form action="/txn20301001">
  <freeze:frame property="select-key" width="95%">
  </freeze:frame>

  <freeze:grid property="record" caption="查询共享表列表" keylist="share_table_id" width="95%" navbar="bottom" fixrow="true">
      <freeze:button name="record_addRecord" caption="增加共享表" txncode="20301003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改共享表" txncode="20301004" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除共享表" txncode="20301005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="share_table_id" caption="共享表ID" style="width:10%" visible="false"/>
      <freeze:cell property="business_topics_id" caption="业务主题ID" style="width:11%" />
      <freeze:cell property="table_name_en" caption="表名称" style="width:18%" />
      <freeze:cell property="table_name_cn" caption="表中文名称" style="width:18%" />
      <freeze:cell property="table_no" caption="表编号" style="width:11%" />
      <freeze:cell property="show_order" caption="显示顺序" style="width:10%" />
      <freeze:cell property="time_" caption="时间字段" style="width:10%" />
      <freeze:cell property="table_type" caption="表类型" style="width:12%" />
      <freeze:cell property="table_index" caption="表索引" style="width:20%" visible="false" />
      <freeze:cell property="is_markup" caption="有效标记" style="width:10%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
