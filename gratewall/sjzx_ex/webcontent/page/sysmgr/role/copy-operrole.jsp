<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="340">
<head>
<title>复制业务角色信息</title>
</head>

<freeze:body>
<freeze:title caption="复制业务角色信息"/>
<script language='javascript' src='<%=request.getContextPath()%>/script/lib/jquery.js'></script>
<script language='javascript' src='<%=request.getContextPath()%>/script/plugins/jquery.filterInput.js'></script>

<freeze:errors/>

<script language="javascript">
function func_record_saveAndExit(){

 saveAndExit( "", "复制业务角色信息", "/txn980328.do" );

}

function func_record_goBack(){
  goBack( "/txn980321.do" );
}
</script>

<freeze:form action="/txn980328">
  <freeze:frame property="primary-key" width="95%">
    <freeze:hidden property="roleid" caption="角色编号" style="width:90%"/>
    <freeze:hidden property="rolename" caption="角色名称" style="width:90%"/>
  </freeze:frame>

  <freeze:block property="record" caption="复制业务角色" width="95%" columns="1" captionWidth="0.25">
    <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE" onclick="func_record_saveAndExit();"/>
    <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
    <freeze:hidden property="roleid" caption="源角色编号" style="width:90%" readonly="true"/>
    <freeze:text property="rolename" caption="源角色名称" style="width:90%" datatype="string" maxlength="32" readonly="true"/>
    <freeze:text property="description" caption="源角色描述" style="width:90%" datatype="string" maxlength="256" readonly="true"/>

	<freeze:hidden property="roleid_target" caption="目标角色编号" style="width:90%"/>
    <freeze:text property="rolename_target" caption="目标角色名称" style="width:90%"  notnull="true" datatype="string" maxlength="32"/>
    <freeze:text property="description_target" caption="目标角色描述" style="width:90%" datatype="string" maxlength="256"/>
  </freeze:block>

</freeze:form>
</freeze:body>
<script language="javascript" type="text/javascript">
	//过滤特殊字符
	$(document).ready(
		function(){
			$(document.getElementById("record:rolename_target")).filterInput();
		}
	);
</script>
</freeze:html>
