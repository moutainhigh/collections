<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>修改数据增长信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	saveAndExit( '', '保存分类视图统计' );	// /txn620100301.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn620100301.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="修改数据增长信息"/>
<freeze:errors/>

<freeze:form action="/txn620100302">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="table_class_id" caption="分类主键id" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改数据增长信息" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:text property="sys_name" caption="主题名称" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="table_name" caption="表名" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="table_name_cn" caption="表中文名" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="class_sort" caption="分类排序" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="class_state" caption="分类状态" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="count_date" caption="统计日期" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:text property="count_full" caption="日全量" datatype="string" maxlength="" style="width:95%"/>
      <freeze:text property="count_incre" caption="日增量" datatype="string" maxlength="" style="width:95%"/>
      <freeze:hidden property="table_class_id" caption="分类主键id" datatype="string" maxlength="40" style="width:95%"/>
      <freeze:text property="sys_order" caption="主体排序字段" datatype="string" maxlength="" style="width:95%"/>
      <freeze:text property="table_order" caption="表排序字段" datatype="string" maxlength="" style="width:95%"/>
      <freeze:text property="sort_order" caption="分类排序" datatype="string" maxlength="" style="width:95%"/>
      <freeze:text property="state_order" caption="状态排序" datatype="string" maxlength="" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
