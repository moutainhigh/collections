<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<script language='javascript' src='<%=request.getContextPath()%>/script/lib/jquery.js'></script>

<freeze:html width="600" height="160">
<head>
<title>用户密码维护</title>

</head>
<freeze:body>
<freeze:title caption="用户密码维护"/>
<freeze:errors/>

<script language="javascript">
function checkPassword(){
	var jmm=document.getElementById("input-data:jmm").value;
	var yhmm=document.getElementById("input-data:yhmm").value;
	var qrmm=document.getElementById("input-data:qrmm").value;
	if(jmm==''){
		alert("请输入旧密码！");
		return false;
	}
	if(yhmm==''){
		alert("请输入新密码！");
		return false;
	}
	if(qrmm==''){
		alert("请输入确认密码！");
		return false;
	}		
	if(jmm==yhmm){
		alert("新密码不能和旧密码一致！");
		return false;
	}
	if(yhmm!=qrmm){
		alert("确认密码和新密码不一致！");
		return false;
	}
	return true;
}
function func_input_data_saveRecord(){
var yhmm=document.getElementById("input-data:yhmm").value;
var jmm=document.getElementById("input-data:jmm").value;
var qrmm=document.getElementById("input-data:qrmm").value;
if(checkPassword()&&checkPasswordRule(window.ruleXml,yhmm)){
    $.ajax({
	  type: "post",
	  url: "<%=request.getContextPath()%>/txn807006.ajax?input-data:yhmm=" + yhmm+"&input-data:jmm="+jmm+"&input-data:qrmm="+qrmm,
	  async: false,
	  success: function(xmlResults){
	  	if (xmlResults.selectSingleNode("//context/error-code").text != "000000"){
	  		alert("原密码输入错误！");
	  	}else{
	  	    alert("密码修改成功！");
	  	    window.returnValue='success'; 
	  	    window.close(); 
	  	    //parent.window.location='/logout.jsp';
			//document.getElementById("input-data:yhmm").value="";
			//document.getElementById("input-data:jmm").value="";
			//document.getElementById("input-data:qrmm").value="";	  	    
	  	}
	  }
	});      	
}else{
  	return false;
}
}

function checkPasswordRule(ruleXml,psw){
	if(!ruleXml)return true;
	//密码长度要求
	var pwLength_enable;
	var minLength;
	var pwLengthElement;
	//密码复杂度要求
	var complication_enable;
	var lowercase;
	var specialCharacter;
	var uppercase;
	var ArabicNumerals;
	var complicationElement;
	
	pwLengthElement 	= $('CheckRule/pwLength',ruleXml)[0];
	complicationElement = $('CheckRule/complication',ruleXml)[0];
	
	pwLength_enable = pwLengthElement.getAttribute('enable');
	if(pwLength_enable=='true'){
		minLength = $('minLength',pwLengthElement).text();
		if(psw.length<minLength){
			alert("您输入的密码小于"+minLength+"位！请输入一个大于"+minLength+"位的密码！");
			return false;
		}
	}
	
	complication_enable = complicationElement.getAttribute('enable');
	if(complication_enable=='true'){
		lowercase 			= $('lowercase',complicationElement).text();
		specialCharacter 	= $('specialCharacter',complicationElement).text();
		uppercase 			= $('uppercase',complicationElement).text();
		ArabicNumerals 		= $('ArabicNumerals',complicationElement).text();
		if(lowercase=='true'){
			//必须要有小写字母
			if(!(/[a-z]/.test(psw))){
				alert('必须要有小写字母');
				return false;
			}
		}
		if(specialCharacter=='true'){
			//必须要有特殊字符!@#$%^&*()_+
			if(!(/\W/.test(psw))){
				alert('必须要有特殊字符');
				return false;
			}
		}
		if(uppercase=='true'){
			//必须要有大写字母
			if(!(/[A-Z]/.test(psw))){
				alert('必须要有大写字母');
				return false;
			}
		}
		if(ArabicNumerals=='true'){
			//必须要有数字
			if(!(/[0-9]/.test(psw))){
				alert('必须要有数字');
				return false;
			}
		}
	}
	
	return true;
}

function getHint(ruleXml){
	if(!ruleXml)return;
	var hintArray = [];
	//密码长度要求
	var pwLength_enable;
	var minLength;
	var pwLengthElement;
	//密码复杂度要求
	var complication_enable;
	var lowercase;
	var specialCharacter;
	var uppercase;
	var ArabicNumerals;
	var complicationElement;
	
	pwLengthElement 	= $('CheckRule/pwLength',ruleXml)[0];
	complicationElement = $('CheckRule/complication',ruleXml)[0];
	
	pwLength_enable = pwLengthElement.getAttribute('enable');
	if(pwLength_enable=='true'){
		minLength = $('minLength',pwLengthElement).text();
		hintArray.push("大于等于"+minLength+"位.");
	}
	
	complication_enable = complicationElement.getAttribute('enable');
	if(complication_enable=='true'){
		lowercase 			= $('lowercase',complicationElement).text();
		specialCharacter 	= $('specialCharacter',complicationElement).text();
		uppercase 			= $('uppercase',complicationElement).text();
		ArabicNumerals 		= $('ArabicNumerals',complicationElement).text();
		if(lowercase=='true'){
			hintArray.push('小写字母');
			hintArray.push('+');
		}
		if(specialCharacter=='true'){
			//必须要有特殊字符
			hintArray.push('特殊字符');
			hintArray.push('+');
		}
		if(uppercase=='true'){
			//必须要有大写字母
			hintArray.push('大写字母');
			hintArray.push('+');
		}
		if(ArabicNumerals=='true'){
			//必须要有数字
			hintArray.push('数字');
			hintArray.push('+');
		}
		hintArray.pop();
		hintArray.push('.');
	}
	
	return hintArray.join('');
}

$(document).ready(function(){
	var ruleSrc = '<%=request.getContextPath()%>/password.jsp';
	$.get(ruleSrc,function(xml){
		window.ruleXml = xml;
		$('span#passwordRule').html(getHint(xml));
	});
});
</script>

<freeze:form action="/txn807006">
  <freeze:block property="input-data" caption="用户密码维护" width="90%" columns="1" >
    <freeze:button name="record_saveAndExit" caption="修改密码"  hotkey="SAVE" onclick="func_input_data_saveRecord();"/>
    <freeze:password property="jmm" caption="旧密码" style="width:90%" minlength="1"  redisplay="false"/>
    <freeze:password property="yhmm" caption="新密码" style="width:90%" minlength="1"  redisplay="false"/>
    <freeze:password property="qrmm" caption="确认密码" style="width:90%" minlength="1" redisplay="false"/>
  </freeze:block>
</freeze:form>
</freeze:body>
</freeze:html>
