<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<script src="<%=request.getContextPath() %> /script/common/js/validator.js"></script>

<%-- template single/single-table-update.jsp --%>
<freeze:html width="750" height="350">
<head>
<title>修改采集数据项信息</title>
</head>

<script language="javascript">
var is_name_used = 1;
// 保 存
function func_record_saveRecord(){
	var en=getFormFieldValue('record:dataitem_name_en');
	if(!checkEnName(en,'数据项名称')){
		return false;
	}
	var dataitem_type=getFormFieldValue('record:dataitem_type');
	var dataitem_long=getFormFieldValue('record:dataitem_long');
	if(!checkItemTypeLength(dataitem_type,dataitem_long)){//校验数据项长度
		return false;
	}
	
	if(!check_int(dataitem_long)){//校验数据项长度
	return false;
	}
	var item=getFormFieldValue('record:dataitem_name_en');
	if(!checkItem(item,"1","数据项名称")){
	  	return false;
	 }
  	item=getFormFieldValue('record:dataitem_name_cn');
	if(!checkItem(item,"100","中文名称")){
	  return false;
	}
	item=getFormFieldValue('record:dataitem_type');
	if(!checkItem(item,"100","数据项类型")){
	  return false;
	}
	item=getFormFieldValue('record:dataitem_long');
	if(!checkItemLength(item,"4","数据项长度")){
	  return false;
	}
	item=getFormFieldValue('record:code_table');
	if(!checkItem(item,"100","对应代码表")){
	  return false;
	}
	item=getFormFieldValue('record:dataitem_long_desc');
  	if(!checkItemLength(item,"2000","数据项描述")){
  	  return false;
  	}
	var page = new pageDefine("/txn20202000.ajax", "检查数据项名是否已经使用");	
	page.addParameter("record:dataitem_name_en","primary-key:dataitem_name_en");
	page.addParameter("record:collect_table_id","primary-key:collect_table_id");
	page.addParameter("record:collect_dataitem_id","primary-key:collect_dataitem_id");
	page.callAjaxService('nameCheckCallback');
}
function nameCheckCallback(errCode,errDesc,xmlResults){
		is_name_used = 1;
		if(errCode != '000000'){
			alert('处理错误['+errCode+']==>'+errDesc);
			return;
		}
		is_name_used=_getXmlNodeValues(xmlResults,'record:name_nums');
		
		if(is_name_used>0){
  			alert("数据项已存在，请重新起名");
  		}else{
	  		  
  		       var collect_dataitem_id = getFormFieldValue('record:collect_dataitem_id');
  		       var collect_table_id = getFormFieldValue('record:collect_table_id');
			   var dataitem_name_en = getFormFieldValue('record:dataitem_name_en');
			   var dataitem_name_cn = getFormFieldValue('record:dataitem_name_cn');
			   var dataitem_type = getFormFieldValue('record:dataitem_type');
			   var dataitem_long = getFormFieldValue('record:dataitem_long');
			   var is_key = getFormFieldValue('record:is_key');
			   var code_table = getFormFieldValue('record:code_table');
			   var dataitem_long_desc = getFormFieldValue('record:dataitem_long_desc');
			  
			   var page = new pageDefine( "/txn20202009.ajax", "保存采集数据项信息");
			   page.addValue(collect_dataitem_id,"record:collect_dataitem_id");
			   page.addValue(collect_table_id,"record:collect_table_id");
			   page.addValue(dataitem_name_en,"record:dataitem_name_en");
			   page.addValue(dataitem_name_cn,"record:dataitem_name_cn");
			   page.addValue(dataitem_type,"record:dataitem_type");
			   page.addValue(dataitem_long,"record:dataitem_long");
			   page.addValue(is_key,"record:is_key");
			   page.addValue(code_table,"record:code_table");
			   page.addValue(dataitem_long_desc,"record:dataitem_long_desc");
			   page.callAjaxService('updateItem');
  		}
}
function updateItem(errCode,errDesc,xmlResults){
		is_name_used[0] = 1;
		if(errCode != '000000'){
			alert('处理错误['+errCode+']==>'+errDesc);
			return;
		}
		else{
		   alert("保存成功!");
		}
		
}

// 保 存并关闭
function func_record_saveAndExit()
{
	var en=getFormFieldValue('record:dataitem_name_en');
	if(!checkEnName(en,'数据项名称')){
		return false;
	}
	var dataitem_type=getFormFieldValue('record:dataitem_type');
	var dataitem_long=getFormFieldValue('record:dataitem_long');
	if(!checkItemTypeLength(dataitem_type,dataitem_long)){//校验数据项长度
	return false;
	}
	if(!check_int(dataitem_long)){//校验数据项长度
	return false;
	}
	var page = new pageDefine("/txn20202000.ajax", "检查数据项名是否已经使用");	
	page.addParameter("record:dataitem_name_en","primary-key:dataitem_name_en");
	page.addParameter("record:collect_table_id","primary-key:collect_table_id");
	page.addParameter("record:collect_dataitem_id","primary-key:collect_dataitem_id");
	page.callAjaxService('nameCheckCallback2');
}
function nameCheckCallback2(errCode,errDesc,xmlResults){
		is_name_used = 1;
		if(errCode != '000000'){
			alert('处理错误['+errCode+']==>'+errDesc);
			return;
		}
		is_name_used=_getXmlNodeValues(xmlResults,'record:name_nums');
		if(is_name_used>0){
  			alert("数据项名称已存在，请重新起名");
  		}else{
  		   saveAndExit( '', '保存采集数据项表' );
  		}
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
<freeze:title caption="修改采集数据项信息"/>
<freeze:errors/>

<freeze:form action="/txn20202002">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="collect_dataitem_id" caption="采集数据项ID" style="width:95%"/>
  </freeze:frame>
  
  <freeze:block property="tableinfo" caption="采集数据表信息" width="95%">
     <freeze:cell property="service_targets_id" caption="服务对象名称："   show="name" valueset="资源管理_服务对象名称"  style="width:95%"/>
     <freeze:cell property="table_name_en" caption="表名称："   show="name" valueset="资源管理_服务对象名称"  style="width:95%"/>
     <freeze:cell property="table_name_cn" caption="数据表中文名："   style="width:95%"/>
     <freeze:cell property="table_type" caption="表类型："  valueset="资源管理_表类型" style="width:95%"/>
     <freeze:cell property="table_desc" caption="表描述：" colspan="2"  style="width:98%"/>
  </freeze:block>
  <br/>
  <freeze:block property="record" caption="修改采集数据项信息" width="95%">
  	  <freeze:button name="record_saveAndExit" caption="保存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="collect_dataitem_id" caption="采集数据项ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="collect_table_id" caption="采集数据表ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="dataitem_name_en" caption="数据项名称" datatype="string" maxlength="100" notnull="true"  style="width:95%"/>
      <freeze:text property="dataitem_name_cn" caption="中文名称" datatype="string" maxlength="100" notnull="true" style="width:95%"/>
      <freeze:select property="dataitem_type" caption="数据项类型" show="name" valueset="资源管理_数据项类型" notnull="true" style="width:95%"/>
      <freeze:text property="dataitem_long" caption="数据项长度"   style="width:95%"/>
      <freeze:checkbox property="is_key" caption="是否主键" valueset="资源管理_主键标识" value="0" style="width:95%"/>
      <freeze:select property="code_table" caption="对应代码表" valueset="资源管理_对应代码表" value="00" notnull="true"  style="width:95%"/>
      <freeze:textarea property="dataitem_long_desc" caption="数据项描述" colspan="2" rows="2" maxlength="2000" style="width:98%"/>
      <freeze:hidden property="is_markup" caption="有效标记" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="creator_id" caption="创建人ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="created_time" caption="创建时间" datatype="string" maxlength="19" style="width:95%"/>
      <freeze:hidden property="last_modify_id" caption="最后修改人ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="last_modify_time" caption="最后修改时间" datatype="string" maxlength="19" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
