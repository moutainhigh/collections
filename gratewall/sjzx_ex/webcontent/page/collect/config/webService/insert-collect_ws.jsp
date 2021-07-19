<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<script
	src="<%=request.getContextPath()%> /script/common/js/validator.js"></script>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
	<title>新增webservice任务接口方法</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	//saveAndExit( '', '保存方法', );
 	var page = new pageDefine("/txn30102113.ajax");
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
	page.addParameter("record:web_name_space","record:web_name_space");
	page.addParameter("record:method_description","record:method_description");
	page.addParameter("record:method_status","record:method_status");
	page.callAjaxService("callBack()"); 
}

function callBack(errCode, errDesc, xmlResults){
	 if(errCode!='000000'){
		alert("保存失败！errCode="+errCode+"---errDesc="+errDesc);
		return;
	 }
	 var method_name_en = getFormFieldValue("record:method_name_en");
	 var method_name_cn = getFormFieldValue("record:method_name_cn");
	 var service_no = getFormFieldValue("record:service_no");
	 var wsId = _getXmlNodeValues(xmlResults,'record:webservice_task_id');
	 //alert(wsId);  
	 var obj={'title':"("+method_name_en+")"+method_name_cn,
			 'srvNo':service_no,
			 'key':wsId,
			 'tooltip':"("+method_name_en+")"+method_name_cn};
	 window.dialogArguments.reloadtree(obj);
	 window.close();
	 
}

// 返 回
function func_record_goBack()
{
	window.close();
	//goBack();
}

function getParameter(){
	//从当前页面取值，组成key=value格式的串
    var parameter = 'input-data:service_targets_id=<freeze:out  value="${record.service_targets_id}"/>';
	return parameter;
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
	<freeze:errors />

	<freeze:form action="/txn30102113">
		<freeze:frame property="primary-key" width="95%">
			<freeze:hidden property="webservice_task_id" caption="WEBSERVICE任务ID"
				style="width:95%" />
		</freeze:frame>
		<freeze:block property="record" caption="修改方法信息" width="95%">
			<freeze:button name="record_saveRecord" caption="保 存" hotkey="SAVE"
				onclick="func_record_saveAndExit();" />
			<freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE"
				onclick="func_record_goBack();" />
			<freeze:hidden property="webservice_task_id" caption="WEBSERVICE任务ID"
				datatype="string" maxlength="32" style="width:95%" />
			<freeze:hidden property="collect_task_id" caption="采集任务ID"
				datatype="string" maxlength="32" style="width:95%" />
			<freeze:hidden property="service_targets_id" caption="服务ID"
				datatype="string" maxlength="32" style="width:95%" />
			<freeze:cell property="service_no" caption="任务ID：" datatype="string"
				style="width:95%" />
			
			<freeze:text property="method_name_en" caption="方法名称：" notnull="true"
				datatype="string" style="width:95%" maxlength="100" />
			<freeze:text property="method_name_cn" caption="方法中文名称" maxlength="100"
				notnull="true" datatype="string" style="width:95%" />
			<freeze:browsebox property="collect_table" caption="对应采集表"
				show="name" notnull="true" valueset="资源管理_服务对象对应采集表"
				parameter="getParameter();" style="width:95%" />
			<freeze:select property="collect_mode" caption="采集方式" show="name"
				notnull="true" valueset="资源管理_采集方式" style="width:95%" />
			<freeze:hidden property="is_encryption" caption="是否加密"
				datatype="string" style="width:95%" />
			<freeze:select property="encrypt_mode" caption="解密方法" notnull="true"
				show="name" valueset="资源管理_解密方法" value="01" style="width:95%" />
			<freeze:text property="web_name_space" caption="命名空间" colspan="2"
				notnull="false" style="width:95%" />
			<freeze:textarea property="method_description" caption="方法描述"
				colspan="2" rows="2" style="width:98%" maxlength="2000" />
			<freeze:hidden property="method_status" caption="方法状态"
				datatype="string" style="width:95%" />

		</freeze:block>
	</freeze:form>
</freeze:body>
</freeze:html>
