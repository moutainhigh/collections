<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-update.jsp --%>
<freeze:html>
<head>
<title>修改服务对象信息</title>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
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
		if(ip&&ip.length < 7){
			alert("请填写完整IP");
			return false;
	    }
	var reg = /^([0,1]?\d{0,2}|2[0-4]\d|25[0-5])\.([0,1]?\d{0,2}|2[0-4]\d|25[0-5])\.([0,1]?\d{0,2}|2[0-4]\d|25[0-5])\.([0,1]?\d{0,2}|2[0-4]\d|25[0-5])$/;  
    var arr = reg.test(ip);  
     if(!arr){  
     	alert("绑定IP错误.");
     	//for(var ii = 1;ii < 5; ii++)
		//	document.getElementById("ip"+ii).value = '';
         return false;
     }
     document.getElementById('record:is_ip_bind').value=1;
     document.getElementById('record:ip_bind').value=ip;
	}else{
	 document.getElementById('record:is_ip_bind').value=0;
     document.getElementById('record:ip_bind').value=' ';
	}
	
	return true;
}

// 保 存
function func_record_saveAndExit()
{
	if(!validate()){
		return;
	}
	
	saveAndExit( '', '保存服务对象管理' );	// /txn50201001.do
}

// 返 回
function func_record_goBack()
{
	_closeModalWindow(true)	// /txn50201001.do
}

// 删除服务对象
function func_record_deleteRecord()
{
	var id = getFormFieldValue("record:sys_svr_user_id");
	var name = getFormFieldValue("record:login_name");

	
	$.get("<%=request.getContextPath()%>/txn50201007.ajax?record:sys_svr_user_id="+id, function(xml){
		var errCode = _getXmlNodeValue( xml, "/context/error-code" );
		if(errCode != "000000"){
		    alert("处理错误：" + _getXmlNodeValue( xml, "/context/error-desc" ));
		}else{
    		var cfgid = _getXmlNodeValue( xml, "record:sys_svr_config_id" );
    		if(cfgid){
    			alert("用户已经被配置服务，禁止删除！");
    			return;
    		}else{
				if(!confirm("是否删除选中的记录?")){
					return;
				}
				$.get("<%=request.getContextPath()%>/txn50201005.ajax?record:sys_svr_user_id="+id, function(xml){
					var errCode = _getXmlNodeValue( xml, "/context/error-code" );
					if(errCode != "000000"){
					    alert("处理错误：" + _getXmlNodeValue( xml, "/context/error-desc" ));
					}
					func_record_goBack();
				});
			}
		}
	});
}

function checkExist(){
	var loginName = getFormFieldValue("record:login_name");
	var userName = getFormFieldValue("record:user_name");
	if(loginName.trim() == "" ){
		return;
	}
	$.get("<%=request.getContextPath()%>/txn50201009.ajax?select-key:login_name="+loginName+"&select-key:user_name="+userName, function(xml){
		var errCode = _getXmlNodeValue( xml, "/context/error-code" );
		if(errCode != "000000"){
		    alert("处理错误：" + _getXmlNodeValue( xml, "/context/error-desc" ));
		}
		var existId = _getXmlNodeValue( xml, "/context/record/sys_svr_user_id" );
		var errorSpan = document.getElementById("frror");
		if(existId && existId != getFormFieldValue("record:sys_svr_user_id")){
			errorSpan.innerHTML = "<center><font color='red'>【用户代码】或【用户名称】已存在，请更换！</font><center>";
			document.getElementById("record_record_saveAndExit").disabled = true;
			errorSpan.style.display = "block";
		}else{
			document.getElementById("record_record_saveAndExit").disabled = false;
			errorSpan.style.display = "none";
		}
	});
}

function __userInitPage(){
	var code = getFormFieldValue("record:user_code");
	if(code.length==2){
		code = code.substr(1,1);
		var radio_index = parseInt(code);
		$(".radioNew")[(radio_index-1)].style.backgroundPositionY='top';
	}
$(".radioNew").each(function(index){
		$(this).click(function(){
			$(".radioNew").css("background-position-y","bottom");
			$(this).css("background-position-y","top");
			$(this).prev()[0].click();
		});
		$($(this).next()[0]).click(function(){
			$(".radioNew").css("background-position-y","bottom");
			$(this).prev().prev()[0].click();
			if($(this).prev().prev()[0].checked){
				$($(this).prev()[0]).css("background-position-y","top");
			}
		});
		if($(this).prev()[0].checked){
			$(this).css("background-position-y","top");
		}
	});
	
  var is_ip_bind='<freeze:out value="${record.is_ip_bind}"/>';
  //var ip='<freeze:out value="${oper-data.ipaddress}"/>';
  var ip='<freeze:out value="${record.ip_bind}"/>';
   if(is_ip_bind=='1'){
    $("#isip_bind").attr("checked","true");
    $("#label_isip_bind").css("background-position-y","top")
    var ips=ip.split(".");
    for(var i=0;i<ips.length;i++){
      $("#ip"+(i+1)).attr("disabled",false);
      $("#ip"+(i+1)).val(ips[i]);
    }
  }
  	$("#padcool").next().next().remove();
	$("#padcool").next().remove();
}

function showIPRow(){
  if($("#label_isip_bind").css("background-position-y")=="bottom"){
  	$("#label_isip_bind").css("background-position-y","top");
    for(var i=1;i<=4;i++){
      $("#ip"+i).attr("disabled",false);
    }
   }else{
    for(var i=1;i<=4;i++){
      $("#ip"+i).attr("disabled",true);
      $("#label_isip_bind").css("background-position-y","bottom");
    }
  }
}

window.onload=function(){
	window.onunload = null;
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="修改服务对象信息"/>
<freeze:errors/>

<freeze:form action="/txn50201002">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="sys_svr_user_id" caption="服务对象编号" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改服务对象信息" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="sys_svr_user_id" caption="服务对象编号" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:text property="login_name" caption="用户账号" onblur="checkExist();" datatype="string" maxlength="20" minlength="1" style="width:95%"/>
      <freeze:text property="user_name" caption="用户名称" onblur="checkExist();" datatype="string" maxlength="20" minlength="1" style="width:95%"/>
      <freeze:select property="user_type" caption="用户类型" valueset="user_type" show="name" style="width:95%" notnull="true"/>
      <td width="15%" align="center"><input style="margin-left:-1000px;" type="checkbox" id="isip_bind" /><label for="isip_bind" style="width:60px;" class="checkboxNew" id="label_isip_bind" onclick="showIPRow();">绑定IP</label></td>
	  <td id="padcool" colspan="0" width="35%">
	  	<input disabled="true" style="width:19%;" id="ip1" name="ip1" value="" maxlength="3" type="text" title="绑定IP地址" >.
	 	 <input disabled="true" style="width:19%;" id="ip2" name="ip2" value="" maxlength="3" type="text" title="绑定IP地址" >.
	 	 <input  disabled="true" style="width:19%;" id="ip3" name="ip3" value="" maxlength="3" type="text" title="绑定IP地址" >.
	 	 <input  disabled="true" style="width:19%;" name="ip4" id="ip4" value="" maxlength="3" type="text" title="绑定IP地址" >
	  <freeze:radio property="user_code" valueset="接口用户身份" caption="用户身份" colspan="2"  style="width:100%"/>
      <freeze:hidden property="is_ip_bind" caption="服务对象编号" datatype="string" maxlength="32"  style="width:95%"/>
      <freeze:hidden property="ip_bind" caption="服务对象编号" datatype="string" maxlength="32"  style="width:95%"/>
      <input type="hidden" name="record:old_login_name" value="<freeze:out value="${record.login_name}"/>"/>
      <input type="hidden" name="record:old_user_name" value="<freeze:out value="${record.user_name}"/>"/>
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
