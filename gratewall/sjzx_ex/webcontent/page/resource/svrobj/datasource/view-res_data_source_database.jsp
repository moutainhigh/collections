<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="750" height="350">
<head>
<title>查看数据源信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	saveAndExit( '', '保存数据源表' );	// /txn20102001.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn20102001.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查看数据源信息"/>
<freeze:errors/>

<freeze:form action="/txn20102006">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="data_source_id" caption="数据源ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="查看数据源信息" width="95%">
      
      <freeze:button name="record_goBack" caption="返 回"hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="data_source_id" caption="数据源ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:cell property="service_targets_id" caption="所属服务对象" datatype="string" show="name" valueset="资源管理_服务对象名称" style="width:95%"/>
      <freeze:cell property="data_source_type" caption="数据源类型" valueset="资源管理_数据源类型" datatype="string"  style="width:95%"/>
      <freeze:cell property="data_source_name" caption="数据源名称" datatype="string"  style="width:95%"/>
      <freeze:cell property="db_type" caption="数据库类型" valueset="资源管理_数据库类型" datatype="string"  style="width:95%"/>
      <freeze:cell property="data_source_ip" caption="数据源IP" datatype="string"  style="width:95%"/>
      <freeze:cell property="access_port" caption="访问端口" datatype="string"  style="width:95%"/>
      <freeze:cell property="db_instance" caption="数据源实例" datatype="string"  style="width:95%"/>
      <freeze:cell property="db_username" caption="数据源用户名" datatype="string"  style="width:95%"/>
      <freeze:cell property="db_password" caption="数据源密码"   colspan="2" style="width:95%"/>
    
      <freeze:cell property="db_desc" caption="数据源描述" colspan="2"  style="width:98%"/>
       <freeze:cell property="crename" caption="创建人" datatype="string"  style="width:95%"/>
      <freeze:cell property="cretime" caption="创建时间" datatype="string"  style="width:95%"/>
      <freeze:cell property="modname" caption="最后修改人" datatype="string"  style="width:95%"/>
      <freeze:cell property="modtime" caption="最后修改时间" datatype="string"  style="width:95%"/>
      
      <freeze:hidden property="access_url" caption="访问URL" datatype="string"   style="width:98%"/>
      <freeze:hidden property="db_status" caption="数据源状态" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="is_markup" caption="有效标记" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="creator_id" caption="创建人ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="created_time" caption="创建时间" datatype="string" maxlength="19" style="width:95%"/>
      <freeze:hidden property="last_modify_id" caption="最后修改人ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="last_modify_time" caption="最后修改时间" datatype="string" maxlength="19" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
