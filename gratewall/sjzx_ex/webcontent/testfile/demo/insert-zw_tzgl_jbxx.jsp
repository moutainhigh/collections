<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>增加通知管理信息</title>
</head>
<style type="text/css">
.odd2_b a:hover,.odd1_b a:hover{background:green;color:white;}
</style>
<script language='javascript' src='<%=request.getContextPath()%>/script/uploadfile.js'></script>
<script language="javascript">

// 保 存
function func_record_saveRecord()
{
	saveRecord( '', '保存通知管理' );
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存通知管理' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存通知管理' );	// /txn315001001.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn315001001.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="增加通知管理信息"/>
<freeze:errors/>

<freeze:form action="/txn315001003" enctype="multipart/form-data">
  <freeze:block property="record" caption="增加通知管理信息" width="95%">
      <freeze:button name="record_saveAndExit" caption="保存并关闭" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="func_record_saveAndContinue" caption="保存并继续" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="func_record_saveRecord" caption="保存" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="jbxx_pk" caption="通知编号-主键" datatype="string" maxlength="32" style="width:95%"/>
	  <freeze:hidden property="fbrid" caption="发布人ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="fbrmc" caption="发布人名称" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="fbksid" caption="发布科室" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="fbksmc" caption="发布名称" datatype="string" maxlength="60" style="width:95%"/>
      <freeze:hidden property="fbsj" caption="发布时间" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="tznr" caption="通知内容"  maxlength="2000" style="width:98%"/>
      <freeze:hidden property="tzzt" caption="通知状态" datatype="string" maxlength="2" style="width:95%"/>
      <freeze:hidden property="jsrids" caption="接收人ids"  maxlength="4000" style="width:98%"/>
      <freeze:hidden property="jsrmcs" caption="接收人名称"  maxlength="2000" style="width:98%"/>
      <freeze:hidden property="fj_fk" caption="附件id"  maxlength="2000" style="width:98%"/>
      <freeze:hidden property="delID1" caption="sigle file id " style="width:90%"/>
    <freeze:hidden property="delNAME1" caption="single file name" style="width:90%"/>
    <freeze:hidden property="delIDs" caption="multi file id" style="width:90%"/>
    <freeze:hidden property="delNAMEs" caption="multi file name" style="width:90%"/>
      <freeze:text property="tzmc" caption="通知名称" datatype="string" maxlength="255" colspan="2" style="width:98%"/>
     
	<freeze:file property="fjmc1" caption="备案附件" style="width:80%" maxlength="100" colspan="2" />&nbsp;<span class="btn_add" onclick="addNewRow()" title="增加"></span><table id="moreFile" width="100%"  cellspacing="0" cellpadding="0"></table>
	
	
      
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
