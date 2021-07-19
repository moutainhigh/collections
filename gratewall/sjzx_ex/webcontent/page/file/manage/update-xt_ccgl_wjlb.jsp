<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>修改文件类别信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	saveAndExit( '', '保存文件类别' );	// /txn604050101.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn604050101.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="修改文件类别信息"/>
<freeze:errors/>

<freeze:form action="/txn604050102">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="cclbbh_pk" caption="文件类别编号" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改文件类别信息" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="cclbbh_pk" caption="文件类别编号" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="cclbmc" caption="处处类别名称" datatype="string" maxlength="60" minlength="1" style="width:95%"/>
      <freeze:text property="lbmcbb" caption="类别名称版本" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="ccgml" caption="存储根目录" datatype="string" maxlength="255" colspan="2" style="width:98%"/>
      <freeze:text property="ejmlgz" caption="二级目录规则" datatype="string" maxlength="1" minlength="1" style="width:95%"/>
      <freeze:text property="gzfzzd" caption="规则辅助字段" datatype="string" maxlength="255" colspan="2" style="width:98%"/>
      <freeze:text property="zt" caption="状态" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:text property="bz" caption="备注" datatype="string" maxlength="255" colspan="2" style="width:98%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
