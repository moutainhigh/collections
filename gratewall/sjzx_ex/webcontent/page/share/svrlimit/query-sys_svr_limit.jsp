<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询用户服务限制列表</title>
</head>

<script language="javascript">

// 增加用户服务限制
function func_record_addRecord()
{
	var page = new pageDefine( "insert-sys_svr_limit.jsp", "增加用户服务限制", "modal" );
	page.addRecord();
}

// 修改用户服务限制
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn50011704.do", "修改用户服务限制", "modal" );
	page.addParameter( "record:sys_svr_limit_id", "primary-key:sys_svr_limit_id" );
	page.updateRecord();
}

// 删除用户服务限制
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn50011705.do", "删除用户服务限制" );
	page.addParameter( "record:sys_svr_limit_id", "primary-key:sys_svr_limit_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询用户服务限制列表"/>
<freeze:errors/>

<freeze:form action="/txn50011701">
  <freeze:block theme="query" property="select-key" caption="查询条件" width="95%">
      <freeze:text property="sys_svr_user_id" caption="用户ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="sys_svr_service_id" caption="服务ID" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

  <freeze:grid property="record" caption="查询用户服务限制列表" keylist="sys_svr_limit_id" width="95%" navbar="bottom" fixrow="true">
      <freeze:button name="record_addRecord" caption="增加用户服务限制" txncode="50011703" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改用户服务限制" txncode="50011704" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除用户服务限制" txncode="50011705" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="sys_svr_limit_id" caption="用户服务限制主键" style="width:12%" />
      <freeze:cell property="sys_svr_user_id" caption="用户ID" style="width:12%" />
      <freeze:cell property="sys_svr_service_id" caption="服务ID" style="width:12%" />
      <freeze:cell property="is_limit_week" caption="是否限制本日期" style="width:10%" />
      <freeze:cell property="is_limit_time" caption="是否限制时间" style="width:10%" />
      <freeze:cell property="is_limit_number" caption="是否限制次数" style="width:10%" />
      <freeze:cell property="is_limit_total" caption="是否限制总条数" style="width:10%" />
      <freeze:cell property="limit_week" caption="限制星期" style="width:10%" />
      <freeze:cell property="limit_time" caption="限制时间" style="width:12%" />
      <freeze:cell property="limit_start_time" caption="起止限制时间" style="width:12%" />
      <freeze:cell property="limit_end_time" caption="结束限制时间" style="width:12%" />
      <freeze:cell property="limit_number" caption="限制次数" style="width:12%" />
      <freeze:cell property="limit_total" caption="限制总条数" style="width:12%" />
      <freeze:cell property="limit_desp" caption="限制描述" style="width:20%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
