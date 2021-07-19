<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询下载设置列表</title>
</head>

<script language="javascript">

// 增加下载设置
function func_record_addRecord()
{
	var page = new pageDefine( "insert-download_purv.jsp", "增加下载设置", "modal" );
	page.addRecord();
}

// 修改下载设置
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn105004.do", "修改下载设置", "modal" );
	page.addParameter( "record:download_purv_id", "primary-key:download_purv_id" );
	page.updateRecord();
}

// 删除下载设置
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn105005.do", "删除下载设置" );
	page.addParameter( "record:download_purv_id", "primary-key:download_purv_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询下载设置列表"/>
<freeze:errors/>

<freeze:form action="/txn105001">
  <freeze:block property="select-key" width="95%" caption="查询条件" theme="query">
  	<freeze:text property="jgmc" caption="机构名称" style="width:95%" />
  </freeze:block>
  <br>
  <freeze:grid property="record" caption="查询下载设置列表" keylist="download_purv_id" width="95%" navbar="bottom" fixrow="false">
      <freeze:button name="record_addRecord" caption="增加下载设置" txncode="105003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改下载设置" txncode="105004" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除下载设置" txncode="105005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="download_purv_id" caption="主键" style="width:10%" visible="false"/>
      <freeze:cell property="agency_id" caption="机构编号" style="width:22%" />
      <freeze:cell property="has_purv" caption="是否允许下载" style="width:19%" />
      <freeze:cell property="max_result" caption="允许下载条数" style="width:19%" />
      <freeze:cell property="last_modi_user" caption="最后修改者" style="width:21%" />
      <freeze:cell property="last_modi_date" caption="最后修改日期" style="width:19%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
