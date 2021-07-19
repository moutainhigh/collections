<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>修改代码分项信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
   var jcsjfx_dm=getFormFieldValue("record:jcsjfx_dm");
   var jcsjfx_mc=getFormFieldValue("record:jcsjfx_mc");  
   if(jcsjfx_dm==""||jcsjfx_mc==""){
       window.alert("请正确填写必输项...");
       return;
   }
   if(/[\'@$!`~/?#,;^&\*\.\\]+/.test(jcsjfx_mc))  {
    　alert("基础数据分项名称中不应含有特殊字符"); 
    　return;
  　}
	if(/[\'@$!`~/?#,;^&\*\.\\]+/.test(jcsjfx_dm))  {
    　alert("基础数据分项代码中不应含有特殊字符"); 
    　return;
  　}  
    saveAndExit( '', '保存计量单位表' );   
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn3010101.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="修改代码分项信息"/>
<freeze:errors/>

<freeze:form action="/txn3010102">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="jcsjfx_id" caption="数据分项ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改代码分项信息" captionWidth="0.5" width="95%" nowrap="true">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:hidden property="jcsjfx_id" caption="数据分项ID" datatype="string" maxlength="36" style="width:95%"/>
      <freeze:hidden property="jc_dm_id" caption="代码ID" datatype="string" maxlength="36" minlength="1" style="width:95%"/>
      <freeze:text property="jcsjfx_dm" caption="数据分项代码" datatype="string" maxlength="36" minlength="1" colspan="2" style="width:95%"/>
      <freeze:text property="jcsjfx_mc" caption="数据分项名称" datatype="string" maxlength="150" minlength="1" colspan="2" style="width:95%"/>
      <freeze:hidden property="jcsjfx_cjm" caption="数据分项层级码" datatype="string" maxlength="36" style="width:95%"/>
      <freeze:hidden property="jcsjfx_fjd" caption="数据分项父节点代码" datatype="string" maxlength="36" style="width:95%"/>
      <freeze:hidden property="szcc" caption="所在层次" datatype="string" maxlength="2" style="width:95%"/>
      <freeze:hidden property="xssx" caption="显示顺序" datatype="string" maxlength="" style="width:95%"/>
      <freeze:hidden property="sfmx" caption="是否明细" datatype="string" maxlength="1" style="width:95%"/>
      <!--<freeze:select property="sy_zt" caption="使用状态" valueset="基础数据分项使用状态" notnull="true" style="width:95%"/>-->
      <freeze:textarea property="fx_ms" caption="分项描述" colspan="2" rows="2" maxlength="32700" style="width:98%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
