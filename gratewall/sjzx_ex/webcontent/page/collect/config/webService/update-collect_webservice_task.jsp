<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<script src="<%=request.getContextPath() %> /script/common/js/validator.js"></script>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>修改webservice任务信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveRecord()
{
	      var item=getFormFieldValue('record:method_name_cn');
  		  if(!checkItemLength(item,"100","方法中文名称")){
  		    return false;
  		  }
	      item=getFormFieldValue('record:collect_table');
  		  if(!checkItem(item,"100","对应采集表")){
  		    return false;
  		  }
  		  item=getFormFieldValue('record:collect_mode');
  		  if(!checkItem(item,"100","采集方式")){
  		    return false;
  		  }
  		  item=getFormFieldValue('record:encrypt_mode');
  		  if(!checkItem(item,"100","解密方法")){
  		    return false;
  		  }
  		  item=getFormFieldValue('record:method_description');
  		  if(!checkItemLength(item,"2000","方法描述")){
  		    return false;
  		  }
	      var page = new pageDefine( "/txn30102002.do", "保存方法信息");
	    
	      page.addParameter("record:webservice_task_id","primary-key:webservice_task_id");
	      page.addParameter("record:webservice_task_id","record:webservice_task_id");
	      page.addParameter("record:collect_task_id","record:collect_task_id");
	      page.addParameter("record:service_targets_id","record:service_targets_id");
	      
	      page.addParameter("record:service_no","record:service_no");
	      page.addParameter("record:method_name_en","record:method_name_en");
	      page.addParameter("record:method_name_cn","record:method_name_cn");
	      page.addParameter("record:collect_table","record:collect_table");
	      page.addParameter("record:collect_mode","record:collect_mode");
	      page.addParameter("record:is_encryption","record:is_encryption");
	      page.addParameter("record:encrypt_mode","record:encrypt_mode");
	      page.addParameter("record:method_description","record:method_description");
	      page.addParameter("record:method_status","record:method_status");
	      //page.callAjaxService('updateTask');
	      page.updateRecord();
	       //saveRecord( '', '保存方法信息' );
}
function updateTask(errCode,errDesc,xmlResults){
		
		if(errCode != '000000'){
			alert('处理错误['+errCode+']==>'+errDesc);
			return;
		}else{
		    alert("保存成功!");
		}
}
// 保 存
function func_record_saveAndExit()
{
	saveAndExit( '', '保存webservice任务表' );	// /txn30102001.do
}

// 返 回
function func_record_goBack()
{
	//goBack();	// /txn30102001.do
	
	var page = new pageDefine( "/txn30101004.do", "查询采集服务");
	page.addParameter("record:webservice_task_id","primary-key:webservice_task_id");
	page.addParameter("record:collect_task_id","primary-key:collect_task_id");
	page.updateRecord();
}
function getParameter(){
	//从当前页面取值，组成key=value格式的串
    var parameter = 'input-data:service_targets_id=<freeze:out  value="${record.service_targets_id}"/>';
	return parameter;
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
	var ids = getFormAllFieldValues("dataItem:webservice_patameter_id");
	for(var i=0; i<ids.length; i++){
	  
	   var htm='<a href="#" title="修改" onclick="func_record_updateRecord(\''+ids[i]+'\');"><div class="edit"></div></a>&nbsp;';
	   document.getElementsByName("span_dataItem:oper")[i].innerHTML +=htm;
	 }
	 
	 var paramStyle = document.getElementById("label:dataItem:patameter_style");
	 if(paramStyle){
		 paramStyle.innerText += '(*)';
	 }
}
// 修改方法信息
function func_record_updateRecord(idx)
{
	var selVal = document.getElementById("dataItem:patameter_style");
	if(selVal){
		var index = selVal.selectedIndex;  
		var text = selVal.options[index].text; 
		if(text=='全部'){
			alert("请选择参数格式.");
			return;
		}
	}
	 
	var page = new pageDefine( "/txn30103004.do", "修改参数信息", "modal" ,1000, 800 );
	page.addValue(idx,"primary-key:webservice_patameter_id");
	var service_targets_id=getFormFieldValue("record:service_targets_id");
	page.addValue(service_targets_id,"primary-key:service_targets_id");
	var patameter_style= getFormFieldValue("dataItem:patameter_style");
	page.addValue(patameter_style,"record:patameter_style");
	page.updateRecord();
}
//查看采集数据项表信息
function func_record_viewRecord(idx)
{
	var page = new pageDefine( "/txn30103006.do", "查看参数信息", "modal" );
	page.addValue(idx,"primary-key:webservice_patameter_id");
	
	page.updateRecord();
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="修改方法信息"/>
<freeze:errors/>

<freeze:form action="/txn30102004">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="webservice_task_id" caption="WEBSERVICE任务ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改方法信息" width="95%">
      <freeze:button name="record_saveRecord" caption="保 存" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="webservice_task_id" caption="WEBSERVICE任务ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="collect_task_id" caption="采集任务ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="service_targets_id" caption="服务ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:cell property="service_no" caption="任务ID：" datatype="string"  style="width:95%"/>
      <freeze:cell property="method_name_en" caption="方法名称：" datatype="string"  style="width:95%"/>
      <freeze:text property="method_name_cn" caption="方法中文名称" datatype="string"  style="width:95%"/>
      <freeze:browsebox property="collect_table" caption="对应采集表" show="name" notnull="true" valueset="资源管理_服务对象对应采集表" parameter="getParameter();"  style="width:95%"/>
      <freeze:select property="collect_mode" caption="采集方式" show="name" notnull="true" valueset="资源管理_采集方式" style="width:95%"/>
      <freeze:hidden property="is_encryption" caption="是否加密"   datatype="string"  style="width:95%"/>
      <freeze:select property="encrypt_mode" caption="解密方法"  notnull="true" show="name" valueset="资源管理_解密方法" value="01" style="width:95%"/>
      <freeze:textarea property="method_description" caption="方法描述" colspan="2" rows="2"  style="width:98%"/>
      <freeze:cell property="web_name_space" caption="命名空间："  colspan="2" style="width:95%"/>
      <freeze:hidden property="method_status" caption="方法状态" datatype="string"  style="width:95%"/>
  </freeze:block>
<br>
   <freeze:grid property="dataItem" caption="参数列表" keylist="webservice_patameter_id" multiselect="false" checkbox="false" width="95%" navbar="bottom" fixrow="false" >
      <freeze:hidden property="webservice_patameter_id" caption="参数ID"  />
      <freeze:hidden property="webservice_task_id" caption="方法ID"  />
      <freeze:cell property="@rowid" caption="序号"  style="width:5%" align="center" />
      <freeze:cell property="patameter_type" caption="参数类型" style="width:10%" />
      <freeze:cell property="patameter_name" caption="参数名" style="width:10%" />
      <freeze:cell property="patameter_value" caption="参数值"   style="width:50%" />
      <freeze:select property="patameter_style" caption="参数格式" valueset="采集任务_参数格式" style="width:20%" />
      <freeze:cell property="oper" caption="操作" align="center" style="width:5%" />
  </freeze:grid>
</freeze:form>
</freeze:body>
</freeze:html>
