<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>视图信息</title>
</head>

<script language="javascript">

	var param = window.dialogArguments;
	function passValue(clickConfirm){
		if(clickConfirm){
		    var viewid = document.getElementById("record:view_id").value;
		    var code = document.getElementById("record:view_code").value;
		    var name = document.getElementById("record:view_name").value;
		    var desc = document.getElementById("record:view_desc").value;
		    if(name==null||name==''){
		        alert("【视图名称】不能为空!");
		        return;
		    }
		    if(param)
				window.returnValue = {code:code,name:name,desc:desc,viewid:viewid};
			else
				window.returnValue = {code:code,name:name,desc:desc};
			
			window.close();
		}else{
			window.close();
		}
		
	}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
    if(param){
		document.getElementById("record:view_id").value = param.viewid;
		document.getElementById("record:view_code").value = param.code;
		document.getElementById("record:view_name").value = param.name;
		document.getElementById("record:view_desc").value = param.desc;
	}else{
		var page = new pageDefine( "/txn52102010.ajax", "查询视图最新编号");
		page.callAjaxService('handleResponse');
	}	
}
	
function handleResponse(errCode, errDesc, xmlResults){
	if(errCode != '000000'){
	   alert("处理错误："+errDesc);
	   return;
	}
	var view_order = _getXmlNodeValue(xmlResults,'record:view_order');
    if(view_order==null||view_order=='')
	    view_order = 0;
	var newNum = "view"+(new Number(view_order)+1);
	document.getElementById("record:view_code").value = newNum;
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="视图基本信息"/>
<freeze:errors/>

<freeze:form action="/txn52101003">
  <freeze:block property="record" caption="视图基本信息" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="passValue(true);"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="passValue(false);"/>
      <freeze:hidden property="view_id" caption="视图id："/>
      <freeze:text property="view_code" caption="视图代码：" style="width:95%" readonly="true"/>
      <freeze:text property="view_name" caption="<font color='red'>视图名称*</font>" datatype="string" maxlength="20" minlength="1" style="width:95%"/>
      <freeze:textarea property="view_desc" caption="视图描述：" style="width:95%" colspan="2"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>