<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="340">
<head>
<title>修改业务角色信息</title>
</head>

<freeze:body>
<freeze:title caption="修改业务角色信息"/>
<script language='javascript' src='<%=request.getContextPath()%>/script/lib/jquery.js'></script>
<script language='javascript' src='<%=request.getContextPath()%>/script/plugins/jquery.filterInput.js'></script>

<freeze:errors/>

<script language="javascript">
function func_record_saveAndExit(){
  saveAndExit( "", "修改业务角色信息", "/txn980322.do" );
}

function func_record_goBack(){
  goBack( "/txn980321.do" );
}
</script>

<freeze:form action="/txn980322">
  <freeze:frame property="primary-key" width="95%">
    <freeze:hidden property="roleid" caption="角色编号" style="width:90%"/>
    <freeze:hidden property="rolename" caption="角色名称" style="width:90%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改业务角色维护" width="95%" columns="1" captionWidth="0.25">
    <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE" onclick="func_record_saveAndExit();"/>
    <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
    <freeze:hidden property="roleid" caption="角色编号" style="width:90%"/>
    <freeze:text property="rolename" caption="角色名称" style="width:90%" datatype="string" maxlength="32" readonly="true"/>
    <freeze:text property="description" caption="角色描述" style="width:90%" datatype="string" maxlength="256"/>
    <freeze:text property="maxcount" caption="最大查询记录数" datatype="number" maxlength="10" minlength="1" value="0" style="width:90%"/>
   <freeze:textarea property="memo" caption="备注" style="width:90%" colspan="2" rows="4"/>
    <freeze:hidden property="rolescope" caption="角色使用范围" valueset="角色使用范围" style="width:90%" visible="false"/>
    <freeze:hidden property="groupname" caption="角色所属的功能组" style="width:90%" datatype="string" maxlength="32" visible="false"/>
    <freeze:hidden property="homepage" caption="主页面" style="width:90%" datatype="string" maxlength="128" visible="false"/>
    <freeze:hidden property="layout" caption="页面布局" valueset="页面的布局列表" hint="WEB页面的布局名称" style="width:90%" visible="false"/>
    <freeze:hidden property="roletype" caption="角色类型：保留" style="width:90%"/>
    <freeze:hidden property="roletype2" caption="角色类型：保留" style="width:90%"/>
    <freeze:hidden property="status" caption="有效标志" style="width:90%"/>
    
  </freeze:block>

</freeze:form>
</freeze:body>
<script language="javascript" type="text/javascript">
	//过滤特殊字符
	$(document).ready(
		function(){
			$(document.getElementById("record:rolename")).filterInput();
		}
	);
</script>
</freeze:html>
