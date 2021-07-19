<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="800" height="350">
<head>
<title>查询业务日志</title>
</head>

<freeze:body>
<freeze:title caption="查询系统管理日志"/>
<freeze:errors/>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script language="javascript">
function func_record_goBack(){
  goBackNoUpdate( "/txn981211.do" );
}

function onloadFunction(){
	document.getElementById("span_record:resultdata").innerHTML = getFormFieldValue("record:resultdata");
	$(".tailrow:eq(0)").remove();
	$(".menu:eq(0)").remove();
	$(".menu:eq(1)").remove();
	$(".btn_query").hide();
	$(".btn_reset").hide();
}

_browse.execute("onloadFunction()");
</script>

<freeze:form action="/txn981214">
  <freeze:frame property="primary-key" width="95%">
    <freeze:hidden property="flowno" caption="流水号" width="90%"/>
  </freeze:frame>

  <freeze:block theme="query" property="record" caption="日志信息" width="95%">
    <freeze:hidden property="flowno" caption="流水号" style="width:90%"/>
    <freeze:hidden property="reqflowno" caption="请求流水号" style="width:90%"/>
    <freeze:cell property="regdate" caption="操作日期" datatype="date" style="width:90%"/>
    <freeze:cell property="regtime" caption="操作时间"  datatype="date" style="width:90%"/>
    <freeze:cell property="trdtype" caption="分类名称" style="width:90%"/>
    <freeze:cell property="username" caption="用户帐号" style="width:90%"/>
    <freeze:cell property="opername" caption="操作员姓名" style="width:90%"/>
    <freeze:cell property="orgid" caption="机构代码" style="width:90%" visible="false"/>
    <freeze:cell property="orgname" caption="机构名称" style="width:90%"/>
    <freeze:cell property="trdcode" caption="交易码" style="width:90%"/>
    <freeze:cell property="trdname" caption="交易名称" style="width:90%"/>
    <freeze:cell property="errcode" caption="错误代码" style="width:90%"/>
    <freeze:cell property="errdesc" caption="错误描述" colspan="2" style="width:36.5%"/>
  </freeze:block>
  <br />
  <freeze:block theme="query" property="record" caption="描述" captionWidth="0" columns="1" width="95%">
    <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
    <freeze:cell property="resultdata" caption="处理结果" style="width:100%" />
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
