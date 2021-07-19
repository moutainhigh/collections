 <%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html>
<head>
<title>增加服务对象信息</title>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery1.1.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<style>
</style>
</head>

<script language="javascript">

function validate(){
	var login_name = getFormFieldValue("record:login_name");
	if(!login_name){
		alert("请填写【用户账号】!");
		return false;
	}
	var reg = new RegExp("[\u4e00-\u9fa5]+");
    if (reg.test(login_name)==true){
		alert("请正确填写【用户代码】!\r\n注意：只能是字母、数字或下划线的英文组合！");
	    document.getElementById("record:login_name").select();
		return false;
	}
	
	var regExp = /[~\\\?'"\^\*]/;
	if(regExp.test(login_name)){
		alert("【用户名称】中含有不合法的字符【" + login_name.match(regExp) + "】，请重新输入！");
		return false;
	}
	
	var userName = getFormFieldValue("record:user_name");
	if(!userName){
		alert("请填写【用户名称】!");
		return false;
	}
	var regExp = /[~\\\?'"\^\*]/;
	if(regExp.test(userName)){
		alert("【用户名称】中含有不合法的字符【" + userName.match(regExp) + "】，请重新输入！");
		return false;
	}
	
	var user_type = getFormFieldValue("record:user_type");
	var reg = new RegExp("^\s*$");
    if (reg.test(user_type)==true){
		alert("请选择【用户类型】!");
		return false;
	}
	
	var isBind = document.getElementById("isip_bind");
	if(isBind.checked == true){
		var ip1 = document.getElementById("ip1").value.trim();
		var ip2 = document.getElementById("ip2").value.trim();
		var ip3 = document.getElementById("ip3").value.trim();
		var ip4 = document.getElementById("ip4").value.trim();
		var ip = ip1 + '.' + ip2 + '.' + ip3 + '.' + ip4;
	if(ip.length < 7){
		alert("请填写完整IP");
		return false;
	}
//	var ip_bind = ip.split(";");
	
	var reg = /^([0,1]?\d{0,2}|2[0-4]\d|25[0-5])\.([0,1]?\d{0,2}|2[0-4]\d|25[0-5])\.([0,1]?\d{0,2}|2[0-4]\d|25[0-5])\.([0,1]?\d{0,2}|2[0-4]\d|25[0-5])$/;  
    var arr = reg.test(ip);  
     if(!arr){  
     	alert("绑定IP错误.");
     	for(var ii = 1;ii < 5; ii++)
			document.getElementById("ip"+ii).value = '';
         return false;
     }
	}
	return true;
}

// 保存并关闭
function func_record_saveAndExit()
{
	if(!validate()){
		return;
	}
	
	var isBind = document.getElementById("isip_bind");
	if(isBind.checked == true){
		var ip1 = document.getElementById("ip1").value.trim();
		var ip2 = document.getElementById("ip2").value.trim();
		var ip3 = document.getElementById("ip3").value.trim();
		var ip4 = document.getElementById("ip4").value.trim();
		var ipbind = ip1 + '.' + ip2 + '.' + ip3 + '.' + ip4;
		
		setFormFieldValue("record:ip_bind",ipbind);
		setFormFieldValue("record:is_ip_bind",'1');
	}else{
		setFormFieldValue("record:ip_bind" , " ");
		setFormFieldValue("record:is_ip_bind",'0');
	}
	
	saveAndExit( '', '保存服务对象管理' );	// /txn50201001.do
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn50201001.do
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	document.getElementById("record_record_saveAndExit").disabled = true;
	$("#padcool").next().next().remove();
	$("#padcool").next().remove();
	$(".radioNew").each(function(index){
		$(this).prev().css("display","");
		$(this).prev().css("margin-left","-1000");
		$(this).click(function(){
			$(".radioNew").css("background-position-y","bottom");
			$(this).prev()[0].click();
			$(this).css("background-position-y","top");
		});
		$($(this).next()[0]).click(function(){
			$(this).prev().prev()[0].click();
			if($(this).prev().prev()[0].checked){
				$($(this).prev()[0]).css("background-position-y","top");
			}
		});
	});
}

window.onload=function(){
	window.onunload = null;
}

function checkExist(){
	document.getElementById("record_record_saveAndExit").disabled = true;
	var loginName = getFormFieldValue("record:login_name");
	var userName = getFormFieldValue("record:user_name");
	if(loginName.trim() == "" ){
		return false;
	}
	var regExp = /[~\\\?'"\^\*]/;
	if(regExp.test(loginName)){
		alert("【用户名】中含有不合法的字符【" + loginName.match(regExp) + "】，请重新输入！");
		return false;
	}
	
	if(regExp.test(userName)){
		alert("【名称】中含有不合法的字符【" + userName.match(regExp) + "】，请重新输入！");
		return false;
	}
	
	var postStr = "<%=request.getContextPath()%>/txn50201009.ajax?";
	if(loginName){
		postStr += "select-key:login_name="+loginName;
		if(userName){
			postStr += "&select-key:user_name="+userName;
		}
	}else if(userName){
		postStr += "select-key:user_name="+userName;
	}
	
	$.get(postStr, function(xml){
		var errCode = _getXmlNodeValue( xml, "/context/error-code" );
		if(errCode != "000000"){
		    alert("处理错误：" + _getXmlNodeValue( xml, "/context/error-desc" ));
		    return false;
		}
		var existId = _getXmlNodeValue( xml, "/context/record/sys_svr_user_id" );
		var errorSpan = document.getElementById("frror");
		if(existId){
			errorSpan.innerHTML = "<center><font color='red'>【用户名】或【名称】已存在，请更换！</font><center>";
			document.getElementById("record_record_saveAndExit").disabled = true;
			errorSpan.style.display = "block";
			return false;
		}else{
			document.getElementById("record_record_saveAndExit").disabled = false;
			errorSpan.style.display = "none";
			return true;
		}
	});
}

function showIPRow(){
	var isBind = document.getElementById("isip_bind");
	if(isBind.checked == true){
		for(var ii = 1;ii < 5; ii++)
			document.getElementById("ip"+ii).disabled = false;
			document.getElementById("label_check").style.backgroundPositionY="top";
		document.getElementById("isip_bind").value = '1';
		}else{
			for(var ii = 1;ii < 5; ii++)
				document.getElementById("ip"+ii).disabled = true;
			document.getElementById("isip_bind").value = '0';
			document.getElementById("label_check").style.backgroundPositionY="bottom";
	}
}

function ip_onkeyup(index){
	if(document.getElementById("ip"+index).value.length == 3){
		index++;
		document.getElementById("ip"+index).focus();
	}
}


_browse.execute( '__userInitPage()' );
</script>

<freeze:body>
<freeze:title caption="增加服务对象信息"/>
<freeze:errors/>

<freeze:form action="/txn50201003">
  <freeze:block property="record" caption="增加服务对象信息" width="95%" columns='2' >
      <freeze:hidden property="ip_bind" caption="绑定IP" style="width:90%" />
      <freeze:hidden property="is_ip_bind" caption="是否绑定" style="width:90%" />
      <freeze:text property="login_name" caption="用户账号" onkeyup="checkExist();" datatype="string" maxlength="20" minlength="1" style="width:90%"/>
      <freeze:text property="user_name" caption="用户名称" onkeyup="checkExist();" datatype="string" maxlength="20" minlength="1" style="width:90%"/>
	  <freeze:select property="user_type" caption="用户类型" valueset="user_type" show="name" style="width:90%" notnull="true"/>
	  <td width="15%" align="center">绑定IP<input type="checkbox" style="margin-top:-1000px;" id="isip_bind" value="0" onclick="showIPRow();" />
	 	<label id="label_check" class="checkboxNew" for="isip_bind"></label>
	  </td>
	  <td id="padcool" colspan="0" width="35%"><input onkeyup="return ip_onkeyup(1)" disabled="true" style="width:18%;" name="ip1" value="" maxlength="3" type="text" title="绑定IP地址" >.
	  	<input onkeyup="return ip_onkeyup(2)" disabled="true" style="width:18%;" name="ip2" value="" maxlength="3" type="text" title="绑定IP地址" >.
	  	<input onkeyup="return ip_onkeyup(3)" disabled="true" style="width:18%;" name="ip3" value="" maxlength="3" type="text" title="绑定IP地址" >.
	  	<input  disabled="true" style="width:18%;" name="ip4" value="" maxlength="3" type="text" title="绑定IP地址" >
      <freeze:radio property="user_code" valueset="接口用户身份" caption="用户身份" colspan="2" value="05" style="width:100%"/>
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>  
 </freeze:block>
  <br/>
  <div>
  	<h3>使用说明：</h3>
  	<ul>
		<li>1、【用户代码】为webService客户端必须提供的，用于登录验证的用户代码；</li>
			<ul>
				<li style="color:red">此用户代码不能有中文字符；</li>
				<li style="color:red">此用户代码不可为空；</li>
				<li style="color:red">此用户代码不可重复；</li>
				<li style="color:red">只能输入字母、数字或下划线的英文组合；</li>
			</ul>
		<li>2、【用户名称】为此用户的书面名称，用于描述用户：
			<ul>
				<li style="color:red">此用户名称不可为空；</li>
				<li style="color:red">此用户名称不可重复</li>
			</ul>
		</li>
	</ul>
  </div>
</freeze:form>
</freeze:body>
</freeze:html>
