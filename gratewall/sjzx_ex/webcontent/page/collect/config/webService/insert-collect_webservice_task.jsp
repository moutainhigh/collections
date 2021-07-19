<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>增加webservice任务信息</title>
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
	saveRecord( '', '保存webservice任务表' );
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存webservice任务表' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	saveAndExit( '', '保存webservice任务表' );	// /txn30102001.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn30102001.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="增加webservice任务信息"/>
<freeze:errors/>

<freeze:form action="/txn30102003">
  <freeze:block property="record" caption="增加webservice任务信息" width="95%">
      <freeze:button name="record_saveRecord" caption="保 存" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_saveAndContinue" caption="保存并继续" hotkey="SAVE_CONTINUE" onclick="func_record_saveAndContinue();"/>
      <freeze:button name="record_saveAndExit" caption="保存并关闭" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:text property="webservice_task_id" caption="WEBSERVICE任务ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
      <freeze:text property="collect_task_id" caption="采集任务ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="service_no" caption="任务编号" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="method_name_en" caption="方法名称" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="method_name_cn" caption="方法中文名称" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:text property="collect_table" caption="对应采集表" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="collect_mode" caption="采集方式" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="is_encryption" caption="是否加密" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:text property="encrypt_mode" caption="加密方式" datatype="string" maxlength="20" style="width:95%"/>
      <freeze:textarea property="method_description" caption="方法描述" colspan="2" rows="2" maxlength="500" style="width:98%"/>
      <freeze:text property="method_status" caption="方法状态" datatype="string" maxlength="20" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
