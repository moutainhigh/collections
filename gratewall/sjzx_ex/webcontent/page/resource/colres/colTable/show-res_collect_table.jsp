<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="850" height="600">
<head>
<title>查看采集数据表信息</title>
</head>

<script type="text/javascript">

// 返 回
function func_record_goBack()
{
	goBackNoUpdate();	// /txn20201001.do
}
//查看采集数据项表信息
function func_record_viewRecord(idx)
{
	var page = new pageDefine( "/txn20202006.do", "查看采集数据项信息", "modal" );
	page.addValue(idx, "primary-key:collect_dataitem_id" );
	var collect_table_id=getFormFieldValue('record:collect_table_id');
	page.addValue(collect_table_id,"primary-key:collect_table_id");
	page.updateRecord();
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	/* var ids = getFormAllFieldValues("dataItem:collect_dataitem_id");
	for(var i=0; i<ids.length; i++){
	   var htm='<a href="#" title="查看" onclick="func_record_viewRecord(\''+ids[i]+'\');"><div class="detail"></div></a>&nbsp;';
	 
	   document.getElementsByName("span_dataItem:oper")[i].innerHTML +=htm;
	 } */
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="查看采集数据表信息"/>
<freeze:errors/>

<freeze:form action="/txn20201006">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="collect_table_id" caption="采集数据表ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="查看采集数据表信息" width="95%">
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="collect_table_id" caption="采集数据表ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="service_targets_id" caption="服务对象ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:cell property="service_targets_id" caption="服务对象名称"   show="name" valueset="资源管理_服务对象名称"  style="width:98%"/>
      <freeze:cell property="table_name_en" caption="表名称"  style="width:95%"/>
      <freeze:cell property="table_name_cn" caption="表中文名称"  style="width:95%"/>
      <freeze:cell property="table_type" caption="表类型"  show="name" valueset="资源管理_表类型"  style="width:95%"/>
      <freeze:cell property="created_time" caption="创建时间"  style="width:95%"/>
      <freeze:cell property="table_desc" caption="表描述" colspan="2"  style="width:98%"/>
       <freeze:cell property="crename" caption="创建人：" datatype="string"  style="width:95%"/>
      <freeze:cell property="cretime" caption="创建时间：" datatype="string"  style="width:95%"/>
      <freeze:cell property="modname" caption="最后修改人：" datatype="string"  style="width:95%"/>
      <freeze:cell property="modtime" caption="最后修改时间：" datatype="string"  style="width:95%"/>
      
      
      <freeze:hidden property="table_status" caption="表状态" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:hidden property="is_markup" caption="有效标记" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="creator_id" caption="创建人ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="last_modify_id" caption="最后修改人ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="last_modify_time" caption="最后修改时间" datatype="string" maxlength="19" style="width:95%"/>
  </freeze:block>
 <br>
   <freeze:grid property="dataItem" caption="采集数据项列表" keylist="collect_dataitem_id" multiselect="false" checkbox="false" width="95%" navbar="bottom" fixrow="false" >
      <freeze:hidden property="collect_dataitem_id" caption="采集数据项ID"  />
      <freeze:hidden property="collect_table_id" caption="采集数据表ID"  />
      <freeze:cell property="@rowid" caption="序号"  style="width:6%" align="center" />
      <freeze:cell property="dataitem_name_en" caption="数据项名称" style="width:12%" />
      <freeze:cell property="dataitem_name_cn" caption="中文名称" style="width:12%" />
      <freeze:cell property="dataitem_type" caption="数据项类型"  show="name" valueset="资源管理_数据项类型" style="width:12%" />
      <freeze:cell property="dataitem_long" caption="数据项长度" style="width:10%" />
      <freeze:cell property="is_key" caption="是否主键" valueset="资源管理_是否主键" style="width:10%" />
      <freeze:hidden property="is_code_table" caption="是否代码表" style="width:10%" />
      <freeze:cell property="code_table" caption="对应代码表" valueset="资源管理_对应代码表" style="width:12%" />
      <freeze:hidden property="oper" caption="操作" align="center" style="width:12%" />
      <freeze:hidden property="dataitem_long_desc" caption="数据项描述" style="width:20%"  />
      <freeze:hidden property="is_markup" caption="有效标记" style="width:10%" />
      <freeze:hidden property="creator_id" caption="创建人ID" style="width:12%" />
      <freeze:hidden property="created_time" caption="创建时间" style="width:12%" />
      <freeze:hidden property="last_modify_id" caption="最后修改人ID" style="width:12%" />
      <freeze:hidden property="last_modify_time" caption="最后修改时间" style="width:12%" />
  </freeze:grid>
</freeze:form>
</freeze:body>
</freeze:html>
