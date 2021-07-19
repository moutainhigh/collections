<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询共享主题列表</title>
</head>

<script language="javascript">

// 增加共享主题
function func_record_addRecord()
{
	var page = new pageDefine( "insert-res_business_topics.jsp", "增加共享主题", "modal" );
	page.addRecord();
}

// 修改共享主题
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn203014.do", "修改共享主题", "modal" );
	page.addParameter( "record:business_topics_id", "primary-key:business_topics_id" );
	page.updateRecord();
}

// 删除共享主题
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn203015.do", "删除共享主题" );
	page.addParameter( "record:business_topics_id", "primary-key:business_topics_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询共享主题列表"/>
<freeze:errors/>

<freeze:form action="/txn203011">
  <freeze:frame property="select-key" width="95%">
  </freeze:frame>

  <freeze:grid property="record" caption="查询共享主题列表" keylist="business_topics_id" width="95%" navbar="bottom" fixrow="true">
      <freeze:button name="record_addRecord" caption="增加共享主题" txncode="203013" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改共享主题" txncode="203014" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除共享主题" txncode="203015" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="business_topics_id" caption="业务主题ID" style="width:18%" />
      <freeze:cell property="service_targets_id" caption="服务对象ID" style="width:18%" />
      <freeze:cell property="topics_name" caption="主题名称" style="width:32%" />
      <freeze:cell property="topics_no" caption="主题编号" style="width:16%" />
      <freeze:cell property="topics_desc" caption="主题描述" style="width:20%" visible="false" />
      <freeze:cell property="show_order" caption="显示顺序" style="width:16%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
