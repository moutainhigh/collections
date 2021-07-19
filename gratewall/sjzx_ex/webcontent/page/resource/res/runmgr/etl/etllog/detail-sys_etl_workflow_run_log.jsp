<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>抽取日志详细信息</title>
</head>

<script language="javascript">
// 返 回
function func_record_goBack()
{
	goBack();	
}
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="抽取日志详细信息"/>
<freeze:errors/>

<freeze:form action="/txn501030008">

  <freeze:block property="record" caption="抽取日志详细信息" width="95%">
      <freeze:button name="record_back" caption="返回" enablerule="0" align="right" onclick="func_record_goBack();"/>
      <freeze:hidden property="workflow_id" caption="WorkFlow_Id：" style="width:95%" />
      <freeze:cell property="mapping_name" caption="转换策略：" colspan="2" align="left" style="width:95%"/> 
      <freeze:cell property="start_time" caption="开始时间：" align="left" style="width:95%" />
      <freeze:cell property="end_time" caption="结束时间：" align="left" style="width:95%" />
      <freeze:cell property="src_success_rows" caption="源成功条数：" align="left" style="width:95%" />  
      <freeze:cell property="src_failed_rows" caption="源失败条数：" align="left" style="width:95%" />
      <freeze:cell property="targ_success_rows" caption="目标成功条数：" align="left" style="width:95%"/>
      <freeze:cell property="targ_failed_rows" caption="目标失败条数：" align="left" style="width:95%"/>
      <freeze:cell property="first_error_code" caption="错误编号：" colspan="2" align="left" style="width:95%" />
      <freeze:cell property="first_error_msg" caption="错误描述：" colspan="2" align="left" style="width:95%" />
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
