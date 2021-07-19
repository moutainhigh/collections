<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="750" height="400">
<head>
<title>增加命名规范信息</title>
</head>
<script language='javascript' src='<%=request.getContextPath()%>/script/uploadfile.js'></script>

<script language="javascript">

// 保 存
function func_record_saveRecord()
{
	saveRecord( '', '保存规则标准' );
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存规则标准' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存规则标准' );	// /txn603001.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn603001.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="增加规则信息"/>
<freeze:errors/>

<freeze:form action="/txn603013"  enctype="multipart/form-data">
  <freeze:block property="record" caption="增加规则信息" width="95%"  >
    
      <freeze:button name="record_saveAndExit" caption="保存" hotkey="SAVE_CLOSE"  align="center" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="standard_id" caption="标准ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="standard_name" caption="命名规范名称"  notnull="true" datatype="string" maxlength="100" colspan="2" style="width:98%"/>
      <freeze:select property="standard_type" caption="规范类型"  valueset="规范类型"  notnull="true" style="width:95%"/>
      <freeze:hidden property="specificate_type" caption="规则类型" value="3" datatype="string" maxlength="20" style="width:95%"/>
     <freeze:hidden property="specificate_status" caption="规范状态" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="specificate_no" caption="类型分类号" datatype="string" maxlength="50" style="width:95%" />
      
      <freeze:select property="issuing_unit" caption="发布单位" valueset="规范发布单位"  notnull="true" style="width:95%"/>
      <freeze:datebox property="enable_time" caption="发布日期" datatype="string" maxlength="11" prefix="<table width='100%' border='0' cellpadding='0' cellspacing='0'>
      <tr><td width='75%'>" style="width:100%"/>
      </td><td width='5%'></td></tr></table>  
     
      <freeze:hidden property="is_markup" caption="代码集 无效 有效" value="Y" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="creator_id" caption="创建人ID" datatype="string" maxlength="32" style="width:95%"/>
       <freeze:hidden property="created_time" caption="创建时间" datatype="string" maxlength="19" style="width:95%"/>
       <freeze:hidden property="last_modify_id" caption="最后修改人ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="last_modify_time" caption="最后修改时间" datatype="string" maxlength="19" style="width:95%"/>
        <freeze:textarea property="specificate_desc" caption="备注" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
    
    <freeze:hidden property="fj_fk" caption="附件id"  maxlength="2000" style="width:98%"/>
      <freeze:hidden property="delID1" caption="sigle file id " style="width:90%"/>
    <freeze:hidden property="delNAME1" caption="single file name" style="width:90%"/>
    <freeze:hidden property="delIDs" caption="multi file id" style="width:90%"/>
    <freeze:hidden property="delNAMEs" caption="multi file name" style="width:90%"/>
     <freeze:hidden property="disable_time" caption="停用时间" datatype="string" maxlength="19" style="width:95%"/>
<freeze:file property="fjmc1" caption="上传文件" style="width:80%" maxlength="100" colspan="2" />&nbsp;<span class="btn_add" onclick="addNewRow()" title="增加"></span><table id="moreFile" width="100%"  cellspacing="0" cellpadding="0"></table> 
 

  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
