<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<script
	src="<%=request.getContextPath()%> /script/common/js/validator.js"></script>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="650" height="350">
<head>
	<title>修改webservice任务信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveAndExit()
{
	saveAndExit( '', '保存方法' );	
}

// 返 回
function func_record_goBack()
{
	goBack();
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
			<freeze:hidden property="service_no" caption="任务ID：" datatype="string"
				style="width:95%" />
			<freeze:cell property="method_name_en" caption="方法名称：" notnull="true"
				datatype="string" style="width:95%" />
			<freeze:hidden property="method_name_en" caption="方法名称：" notnull="true"
				datatype="string" style="width:95%" />
			<freeze:text property="method_name_cn" caption="方法中文名称"
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
				colspan="2" rows="2" style="width:98%" />
			<freeze:hidden property="method_status" caption="方法状态"
				datatype="string" style="width:95%" />
		</freeze:block>
	</freeze:form>
</freeze:body>
</freeze:html>
