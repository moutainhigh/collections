<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
<head>
<title>设置角色的状态</title>
</head>

<freeze:body>
<freeze:title caption="设置角色的状态"/>
<freeze:errors/>

<script language="javascript">
function func_record_saveAndExit(){
  saveAndExit( "", "修改业务角色信息", "/txn980322.do" );
}

function func_record_goBack(){
  goBack( "/txn980321.do" );
}
</script>

<freeze:form action="/txn980326">
  <freeze:frame property="primary-key" width="95%">
    <freeze:hidden property="roleid" caption="角色编号" style="width:90%"/>
    <freeze:hidden property="rolename" caption="角色名称" style="width:90%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改业务角色维护" width="95%" columns="1" captionWidth="0.2">
    <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE" onclick="func_record_saveAndExit();"/>
    <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
    <freeze:hidden property="roleid" caption="角色编号" style="width:90%"/>
    <freeze:text property="rolename" caption="角色名称" style="width:90%" datatype="string" maxlength="32" readonly="true"/>
    <freeze:text property="description" caption="角色描述" style="width:90%" datatype="string" maxlength="256" readonly="false"/>
    <freeze:select property="rolescope" caption="角色使用范围" valueset="角色使用范围"  visible="false"/>
    <freeze:radio property="status" caption="有效标志" valueset="有效标志" style="width:30%"/>
    <freeze:text property="maxcount" caption="最大查询记录数" datatype="number" maxlength="5" minlength="1" value="0" style="width:90%"/>
    <freeze:textarea property="memo" caption="备注" style="width:90%" maxlength="256" cols="60" rows="5"/>
  </freeze:block>

</freeze:form>
<script language="javascript">
var flag = getFormFieldValue('record:status');
if( flag == '0' ){
	flag = '1';
}
else{
	flag = '0';
}

setFormFieldValue('record:status', 0, flag);
</script>
</freeze:body>
</freeze:html>
