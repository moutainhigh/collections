<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="340">
<head>
<title>增加业务角色信息</title>
</head>

<freeze:body>
<freeze:title caption="增加业务角色信息"/>
<script language='javascript' src='<%=request.getContextPath()%>/script/lib/jquery.js'></script>
<script language='javascript' src='<%=request.getContextPath()%>/script/plugins/jquery.filterInput.js'></script>

<freeze:errors/>

<script language="javascript">

function func_record_saveRecord(){
  var jsmc = document.getElementById("record:rolename");
  if(isNotChar(jsmc.value,"角色名称")){
	  saveRecord( "", "保存业务角色信息" );
  }else{
  	return false;
  }
}

function func_record_saveAndContinue(){
  var jsmc = document.getElementById("record:rolename");
  if(isNotChar(jsmc.value,"角色名称")){
	  saveAndContinue( "", "保存业务角色信息并解析" );
  }else{
  	return false;
  }
}

function func_record_saveAndExit(){
  var jsmc = document.getElementById("record:rolename");
  if(isNotChar(jsmc.value,"角色名称")){
	  saveAndExit( "", "保存业务角色信息并返回" );
  }else{
  	return false;
  }
}

function func_record_goBack(){
  goBack( "/txn980321.do" );
}
</script>

<freeze:form action="/txn980323">
  <freeze:block property="record" caption="增加业务角色维护" width="95%" columns="1">
    <freeze:button name="record_saveRecord" caption="保 存" hotkey="SAVE" onclick="func_record_saveAndExit();"/>
    <%--   
    <freeze:button name="record_saveAndContinue" caption="保存并继续" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
    <freeze:button name="record_saveAndExit" caption="保存并关闭" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
    --%>
    <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
    <freeze:text property="rolename" caption="角色名称" style="width:90%" datatype="string" maxlength="32" minlength="1"/>
    <freeze:text property="description" caption="角色描述" style="width:90%" datatype="string" maxlength="256"/>
    <freeze:text property="maxcount" caption="最大查询记录数" datatype="number" maxlength="10" minlength="1" value="0" style="width:90%"/>
       <freeze:textarea property="memo" caption="备注" style="width:90%" colspan="2" rows="4"/>
    
    <freeze:hidden property="rolescope" caption="使用范围" valueset="角色使用范围" style="width:90%" visible="false"/>
    <freeze:hidden property="groupname" caption="角色所属的功能组" style="width:90%" datatype="string" maxlength="32" visible="false"/>
    <freeze:hidden property="homepage" caption="主页面" style="width:90%" datatype="string" maxlength="128" visible="false"/>
    <freeze:hidden property="layout" caption="页面布局" valueset="页面的布局列表" hint="WEB页面的布局名称" style="width:90%" visible="false"/>
    <freeze:hidden property="roletype" caption="角色类型：保留" style="width:90%"/>
    <freeze:hidden property="roletype2" caption="角色类型：保留" style="width:90%"/>
   
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
