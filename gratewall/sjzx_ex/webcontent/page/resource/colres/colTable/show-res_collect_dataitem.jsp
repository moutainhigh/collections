<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="750" height="350">
<head>
<title>查看采集数据项信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	saveAndExit( '', '保存采集数据项表' );	// /txn20202001.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn20202001.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查看采集数据项信息"/>
<freeze:errors/>

<freeze:form action="/txn20202002">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="collect_dataitem_id" caption="采集数据项ID" style="width:95%"/>
  </freeze:frame>

 <freeze:block property="tableinfo" caption="采集数据表信息" width="95%">
     <freeze:cell property="service_targets_id" caption="服务对象名称"   show="name" valueset="资源管理_服务对象名称"  style="width:95%"/>
     <freeze:cell property="table_name_en" caption="表名称"   show="name" valueset="资源管理_服务对象名称"  style="width:95%"/>
     <freeze:cell property="table_name_cn" caption="数据表中文名"   style="width:95%"/>
     <freeze:cell property="table_type" caption="表类型"  valueset="资源管理_表类型" style="width:95%"/>
     <freeze:cell property="table_desc" caption="表描述" colspan="2"  style="width:98%"/>
  </freeze:block>
   <br/>
  <freeze:block property="record" caption="采集数据项信息" width="95%">
      <freeze:button name="record_goBack" caption="返 回"hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="collect_dataitem_id" caption="采集数据项ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="collect_table_id" caption="采集数据表ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:cell property="dataitem_name_en" caption="数据项名称"   style="width:95%"/>
      <freeze:cell property="dataitem_name_cn" caption="中文名称"  style="width:95%"/>
      <freeze:cell property="dataitem_type" caption="数据项类型"  show="name" valueset="资源管理_数据项类型" style="width:95%"/>
      <freeze:cell property="dataitem_long" caption="数据项长度"  style="width:95%"/>
      <freeze:cell property="is_key" caption="是否主键" show="name" valueset="资源管理_是否主键" style="width:95%"/>
      <freeze:cell property="code_table" caption="对应代码表" show="name" valueset="资源管理_对应代码表" style="width:95%"/>
      <freeze:hidden property="dataitem_long_desc" caption="数据项描述" colspan="2"  style="width:98%"/>
      <freeze:hidden property="is_markup" caption="有效标记" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="creator_id" caption="创建人ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="created_time" caption="创建时间" datatype="string" maxlength="19" style="width:95%"/>
      <freeze:hidden property="last_modify_id" caption="最后修改人ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="last_modify_time" caption="最后修改时间" datatype="string" maxlength="19" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
