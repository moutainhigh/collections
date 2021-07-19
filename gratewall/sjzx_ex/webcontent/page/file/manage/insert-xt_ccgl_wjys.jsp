<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>增加文件映射信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveRecord()
{
	saveRecord( '', '保存文件映射' );
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存文件映射' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存文件映射' );	// /txn604060101.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn604060101.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="增加文件映射信息"/>
<freeze:errors/>

<freeze:form action="/txn604060103">
  <freeze:block property="record" caption="增加文件映射信息" width="95%">
      <freeze:button name="record_saveRecord" caption="保 存" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="保存并继续" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="保存并关闭" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="ysbh_pk" caption="映射编号" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="cclbbh_pk" caption="类别编号ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="wybs" caption="唯一标识" datatype="string" maxlength="255" minlength="1" colspan="2" style="width:98%"/>
      <freeze:text property="wjmc" caption="文件名称" datatype="string" maxlength="255" minlength="1" colspan="2" style="width:98%"/>
      <freeze:text property="wjzt" caption="文件状态" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:text property="cclj" caption="存储路径" datatype="string" maxlength="255" colspan="2" style="width:98%"/>
      <freeze:text property="cjsj" caption="创建时间" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="scxgsj" caption="上次修改时间" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="bz" caption="备注" datatype="string" maxlength="255" colspan="2" style="width:98%"/>
      <freeze:text property="ywbz" caption="业务备注" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="xm_fk" caption="项目fk" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
