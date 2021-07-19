<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html title="设置角色的功能信息">
<freeze:include href="/script/gwssi-xtree.js"/>
<freeze:include href="/script/struts-coolmenus3.js"/>

<freeze:body>
<freeze:errors/>

<script language="javascript">
function func_record_saveAndExit()
{
  setFormFieldValue( 'record:funclist', 0, funcinfo.getCheckValue() );
  saveAndExit( "", "保存权限信息", "/txn980321.do" );
}

function func_record_goBack()
{
  goBack( "/txn980321.do" );
}

function funcClick( )
{
	//alert( funcinfo.selectedNode.id );
}
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	var funcList = getFormFieldValue( 'record:funclist' );
	if( funcList != null && funcList != '' ){
		funcinfo.setCheckValue( funcList );
	}
	var status = getFormFieldValue( 'record:status' );
	if(status != '1'){
	    document.getElementById("record_record_saveAndExit").disabled="true";
	}
	
}
_browse.execute( '__userInitPage()' );
</script>

<freeze:form action="/txn980327">
  <freeze:frame property="primary-key" width="95%">
    <freeze:hidden property="roleid" caption="角色编号" style="width:90%"/>
    <freeze:hidden property="rolename" caption="角色名称" style="width:90%"/>
  </freeze:frame>

  <freeze:frame property="record" width="100%" height="99%">
  <freeze:hidden property="roleid" caption="角色编号" style="width:90%"/>
  <freeze:hidden property="funclist" caption="功能列表" style="width:90%"/>
  <freeze:hidden property="status" caption="角色状态" style="width:90%"/>
  <table border="0" cellpadding="0" cellspacing="0" width="95%" height="100%" id="query" align="center">
  	<tr>
		<td align="right" height="9%" width="16%">角色名称：</td>
		<td width="34%"><freeze:text property="rolename" caption="角色名称" style="width:90%" datatype="string" maxlength="32" readonly="true"/></td>
		<td align="right" width="16%">角色描述：</td>
		<td width="34%"><freeze:text property="description" caption="角色描述" style="width:90%" datatype="string" maxlength="256" readonly="true"/></td>
	</tr>
  	<tr><td height="1" bgcolor="3366FF" width="100%" colspan="4"></td></tr>
  	
  	<tr height="82%"><td width="100%" colspan="4">
  		<freeze:xtree name='funcinfo' txnCode='980301' width="100%" height="100%" bgcolor="F7F7F7" aspect="check" nodeicon="scrollend.gif" nodename="record" pidname="parentcode" idname="funccode" textname="{funcname}" root="角色的功能维护" onclick="funcClick"/>
  	</td></tr>
  	
  	<tr><td height="1" bgcolor="3366FF" width="100%" colspan="4"></td></tr>
  	<tr><td colspan="4" height="9%" align="center">
	    <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE" onclick="func_record_saveAndExit();"/>
	    &nbsp;
	    <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
	</td></tr>
    </table>
  </freeze:frame>

</freeze:form>
</freeze:body>
</freeze:html>
