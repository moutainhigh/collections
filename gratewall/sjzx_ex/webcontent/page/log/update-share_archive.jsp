<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<freeze:html width="650" height="200">
<head>
	<title>归档共享日志</title>
</head>

<script language="javascript">


// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	
}

function func_record_goBack()
{
	goBack();	
}

function func_record_saveAndExit()
{
	//saveAndExit( '', '归档共享日志' );
	if(check()){
		
		var page = new pageDefine( "/txn6030014.ajax", "归档共享日志" );
		page.addParameter("record:created_time_start","record:created_time_start");
		page.addParameter("record:created_time_end","record:created_time_end");
		page.callAjaxService("checkBack");
	}
}

function checkBack(errorCode, errDesc, xmlResults){
	var count = _getXmlNodeValue(xmlResults,"record:count");
	alert("本次共归档共享日志 【"+count+"】 条");
	//goBackWithUpdate();
	//window.close();
	saveAndExit( '', '归档共享日志' );
	
}
function check(){
		var start = document.getElementById("record:created_time_start").value;	
		var end = document.getElementById("record:created_time_end").value;

		if(start&&end){
            if(start>end){
                alert("结束日期必须大于开始日期！");
                document.getElementById("record:created_time_end").select();
                return false;
            }		

		}else{
			alert("开始日期和结束日期都是必填项！");
			return false
		}
		return true;
}
_browse.execute( '__userInitPage()' );
</script>

<freeze:body>
	<freeze:title caption="归档共享日志" />
	<freeze:errors />

	<freeze:form action="/txn6030014">

		<freeze:block property="record" caption="修改功能大小类对应信息" width="95%">
			<freeze:button name="record_saveAndExit" caption="保 存"
				hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();" />
			<freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE"
				onclick="func_record_goBack();" />

			<freeze:datebox property="created_time_start" caption="开始时间"
				datatype="string" notnull="true" maxlength="30" style="width:95%" />
			<freeze:datebox property="created_time_end" caption="结束时间"
				datatype="string" notnull="true" maxlength="30" style="width:95%" />
		</freeze:block>

	</freeze:form>
</freeze:body>
</freeze:html>
