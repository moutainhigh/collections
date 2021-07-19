<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询共享ftp服务列表</title>
</head>

<script language="javascript">

// 增加共享ftp服务
function func_record_addRecord()
{
	var page = new pageDefine( "insert-share_ftp_service.jsp", "增加共享ftp服务", "modal" );
	page.addRecord();
}

// 修改共享ftp服务
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn40401004.do", "修改共享ftp服务", "modal" );
	page.addParameter( "record:ftp_service_id", "primary-key:ftp_service_id" );
	page.updateRecord();
}

// 删除共享ftp服务
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn40401005.do", "删除共享ftp服务" );
	page.addParameter( "record:ftp_service_id", "primary-key:ftp_service_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询共享ftp服务列表"/>
<freeze:errors/>

<freeze:form action="/txn40401001">
  <freeze:frame property="select-key" width="95%">
  </freeze:frame>

  <freeze:grid property="record" caption="查询共享ftp服务列表" keylist="ftp_service_id" width="95%" navbar="bottom" fixrow="true">
      <freeze:button name="record_addRecord" caption="增加共享ftp服务" txncode="40401003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改共享ftp服务" txncode="40401004" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除共享ftp服务" txncode="40401005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="ftp_service_id" caption="FTP服务ID" style="width:10%" visible="false"/>
      <freeze:cell property="service_id" caption="服务ID" style="width:18%" />
      <freeze:cell property="datasource_id" caption="数据源ID" style="width:18%" />
      <freeze:cell property="srv_scheduling_id" caption="服务调度ID" style="width:17%" />
      <freeze:cell property="file_name" caption="文件名称" style="width:30%" />
      <freeze:cell property="file_type" caption="文件类型" style="width:17%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
