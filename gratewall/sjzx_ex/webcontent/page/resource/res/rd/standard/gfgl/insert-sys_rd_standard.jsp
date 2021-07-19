<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="750" height="350">
<head>
<title>增加规范信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveRecord()
{
	saveRecord( '', '保存信息实体管理' );
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存信息实体管理' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存信息实体管理' );	// /txn7000201.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn7000201.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="增加规范信息"/>
<freeze:errors/>

<freeze:form action="/txn7000203" enctype="multipart/form-data">
  <freeze:block property="record" caption="增加规范信息" width="95%" >
      <freeze:button name="record_saveRecord" caption="保 存" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="保存并继续" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="保存并关闭" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
  
      <freeze:text property="standard_name" caption="规范名称" datatype="string" maxlength="100" colspan="2" style="width:95%" notnull="true" />
      <freeze:select property="standard_category" caption="规范类型" valueset="规范类型" style="width:95%" notnull="true"/>
      <freeze:text property="standard_category_no" caption="规范分类号" datatype="string" maxlength="30" style="width:95%"/>
      <freeze:select property="standard_issued_unit" caption="规范发布单位"  valueset="规范发布单位" style="width:95%" notnull="true"/>
      <freeze:datebox property="standard_issued_time" caption="规范发布时间" datatype="string" maxlength="30" style="width:95%"/>
      <freeze:text property="file_name" caption="文件名称" datatype="string" maxlength="128" colspan="2" style="width:98%"/>
      <freeze:file property="file_path" caption="文件名" colspan="2" accept="*.doc,*.docx" style="width:98%"/>
      <freeze:textarea property="standard_range" caption="规范范围" colspan="2" rows="4" maxlength="4000" style="width:98%"/>
      <freeze:textarea property="memo" caption="备注" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
      <freeze:hidden property="sort" caption="排序号" datatype="string" maxlength="10" style="width:95%"/>
      
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
