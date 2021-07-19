<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
<head>
<title>修改功能信息</title>
</head>

<freeze:body>
<freeze:title caption="修改功能信息"/>
<script language='javascript' src='<%=request.getContextPath()%>/script/lib/jquery.js'></script>
<script language='javascript' src='<%=request.getContextPath()%>/script/plugins/jquery.filterInput.js'></script>

<freeze:errors/>

<script language="javascript">
function func_record_saveAndExit(){
//如果是"/txn980301.do" 会出现404错误
  saveAndExit( "", "修改功能信息信息","txn980301.do");
}

function func_record_goBack(){
  goBack( "txn980301.do" );
}
</script>

<freeze:form action="/txn980302">
  <freeze:frame property="primary-key" width="95%">
    <freeze:hidden property="funccode" caption="功能代码" style="width:90%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改功能权限维护" width="95%">
    <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE" onclick="func_record_saveAndExit();"/>
    <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
    <freeze:text property="parentcode" caption="上级功能" style="width:100%" datatype="string" maxlength="36" readonly="true"/>
    <freeze:text property="funccode" caption="功能代码" newline="true" style="width:100%" datatype="string" maxlength="36" readonly="true"/>
    <freeze:text property="funcname" caption="功能名称" newline="true" style="width:100%" datatype="string" maxlength="256" minlength="1"/>
    <freeze:hidden property="groupname" caption="功能组名称" style="width:100%"/>
    <freeze:hidden property="status" caption="状态" style="width:100%"/>
    <freeze:textarea property="memo" caption="备注信息" style="width:95%" maxlength="256" colspan="2" rows="4"/>
  </freeze:block>

</freeze:form>
</freeze:body>
<script language="javascript" type="text/javascript">
	//过滤特殊字符
	$(document).ready(
		function(){
			$(document.getElementById("record:funccode")).filterInput();
			$(document.getElementById("record:funcname")).filterInput();
		}
	);
</script>
</freeze:html>
