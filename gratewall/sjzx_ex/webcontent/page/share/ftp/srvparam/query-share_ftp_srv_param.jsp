<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询ftp服务参数列表</title>
</head>

<script language="javascript">

// 增加ftp服务参数
function func_record_addRecord()
{
	var page = new pageDefine( "insert-share_ftp_srv_param.jsp", "增加ftp服务参数", "modal" );
	page.addRecord();
}

// 修改ftp服务参数
function func_record_updateRecord()
{
	var page = new pageDefine( "/txn40402004.do", "修改ftp服务参数", "modal" );
	page.addParameter( "record:srv_param_id", "primary-key:srv_param_id" );
	page.updateRecord();
}

// 删除ftp服务参数
function func_record_deleteRecord()
{
	var page = new pageDefine( "/txn40402005.do", "删除ftp服务参数" );
	page.addParameter( "record:srv_param_id", "primary-key:srv_param_id" );
	page.deleteRecord( "是否删除选中的记录" );
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查询ftp服务参数列表"/>
<freeze:errors/>

<freeze:form action="/txn40402001">
  <freeze:frame property="select-key" width="95%">
  </freeze:frame>

  <freeze:grid property="record" caption="查询ftp服务参数列表" keylist="srv_param_id" width="95%" navbar="bottom" fixrow="true">
      <freeze:button name="record_addRecord" caption="增加ftp服务参数" txncode="40402003" enablerule="0" hotkey="ADD" align="right" onclick="func_record_addRecord();"/>
      <freeze:button name="record_updateRecord" caption="修改ftp服务参数" txncode="40402004" enablerule="1" hotkey="UPDATE" align="right" onclick="func_record_updateRecord();"/>
      <freeze:button name="record_deleteRecord" caption="删除ftp服务参数" txncode="40402005" enablerule="2" hotkey="DELETE" align="right" onclick="func_record_deleteRecord();"/>
      <freeze:cell property="srv_param_id" caption="参数值ID" style="width:14%" />
      <freeze:cell property="ftp_service_id" caption="FTP服务ID" style="width:14%" />
      <freeze:cell property="param_value_type" caption="代码表String INT BOOLEAN" style="width:14%" />
      <freeze:cell property="patameter_name" caption="参数名" style="width:14%" />
      <freeze:cell property="patameter_value" caption="参数值" style="width:20%" />
      <freeze:cell property="style" caption="格式" style="width:13%" />
      <freeze:cell property="showorder" caption="顺序字段" style="width:11%" />
  </freeze:grid>

</freeze:form>
</freeze:body>
</freeze:html>
