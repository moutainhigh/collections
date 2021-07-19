<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="850" height="550">
<head>
	<title>系统日志</title>
</head>

<script language="javascript">





// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	

}
function func_record_goBack()
{
	window.close();	// /txn201001.do
}




_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
	<freeze:title caption="查询系统日志列表" />
	<freeze:errors />

	<freeze:form action="/txn601016">
	<freeze:frame property="select-key" width="95%">
      <freeze:hidden property="collect_joumal_id" caption="主键" style="width:95%"/>
  </freeze:frame>
		<freeze:block theme="view" property="record" caption="查看系统日志"
			width="95%">
			      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
			
			 <freeze:hidden property="first_page_query_id" caption="主键" style="width:10%" visible="false"/>
      <freeze:hidden property="orgid" caption="ID" style="width:95%" />
      <freeze:cell property="opername" caption="操作对象" style="width:95%" />
      <freeze:cell property="username" caption="操作人" style="width:95%" />
      <freeze:cell property="orgname" caption="操作类型" style="width:95%" />
      <freeze:cell property="query_time" caption="操作时间"   style="width:95%" />
      <freeze:cell property="state" caption="用户状态" style="width:95%" />
      <freeze:cell property="count" caption="数量" style="width:95%" />
      <freeze:cell property="num" caption="总数" style="width:95% " />
      <freeze:cell property="orgname" caption="操作名称" style="width:95%" />
      <freeze:cell property="ipaddress" caption="ip地址" style="width:95%" />
     
		</freeze:block>



	</freeze:form>
</freeze:body>
</freeze:html>
