<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>修改共享数据项信息信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	saveAndExit( '', '保存共享数据项信息表' );	// /txn20301021.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn20301021.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="修改共享数据项信息信息"/>
<freeze:errors/>

<freeze:form action="/txn20301022">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="share_dataitem_id" caption="共享数据项ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改共享数据项信息信息" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="share_dataitem_id" caption="共享数据项ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="share_table_id" caption="共享表ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="dataitem_name_en" caption="数据项名称" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="dataitem_name_cn" caption="数据项中文名称" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="dataitem_type" caption="数据项类型" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="dataitem_long" caption="数据项长度" datatype="string" maxlength="4" style="width:95%"/>
      <freeze:text property="code_table_name" caption="系统代码名" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="code_table" caption="对应代码集" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="is_key" caption="是否主键" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:textarea property="dataitem_desc" caption="描述" colspan="2" rows="4" maxlength="2000" style="width:98%"/>
      <freeze:text property="show_order" caption="显示顺序" datatype="string" maxlength="" style="width:95%"/>
      <freeze:text property="is_markup" caption="有效标记" datatype="string" maxlength="1" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
