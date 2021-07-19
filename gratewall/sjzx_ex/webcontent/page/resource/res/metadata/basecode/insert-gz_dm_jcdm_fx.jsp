<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>增加代码分项信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveRecord()
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
   saveAndExit( '', '增加索引目录分项' );
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存基础代码分项表(隐藏)' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存基础代码分项表(隐藏)' );	// /txn3010101.do
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
<freeze:title caption="增加代码分项信息"/>
<freeze:errors/>

<freeze:form action="/txn3010103">
  <freeze:block property="record" caption="增加代码分项信息" captionWidth="0.5" width="95%" nowrap="true">
      <freeze:button name="record_saveRecord" caption="保 存" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="jcsjfx_id" caption="数据分项ID" datatype="string" maxlength="36" style="width:95%"/>
      <freeze:hidden property="jc_dm_id" caption="代码ID" datatype="string" maxlength="36" minlength="1" style="width:95%"/>
      <freeze:text property="jcsjfx_dm" caption="数据分项代码" datatype="string" maxlength="36" minlength="1" colspan="2" style="width:95%"/>
      <freeze:text property="jcsjfx_mc" caption="数据分项名称" datatype="string" maxlength="150" minlength="1" colspan="2" style="width:95%"/>
      <freeze:hidden property="jcsjfx_fjd" caption="数据分项父节点代码" datatype="string" maxlength="36" style="width:95%"/>
      <freeze:hidden property="szcc" caption="所在层次" datatype="string" maxlength="2" value="1" style="width:95%"/>
      <freeze:hidden property="xssx" caption="显示顺序" datatype="string" maxlength="" style="width:95%"/>
      <freeze:hidden property="sfmx" caption="是否明细" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:textarea property="fx_ms" caption="分项描述" colspan="2" rows="2" maxlength="32700" style="width:98%"/>
      <freeze:hidden property="sy_zt" caption="使用状态" datatype="string" maxlength="1" value="1" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
