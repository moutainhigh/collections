<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="350">
<head>
<title>新建用户信息</title>
</head>
<freeze:body onload="getOrgID()">
<freeze:title caption="新建用户信息"/>
<script language='javascript' src='<%=request.getContextPath()%>/script/lib/jquery.js'></script>
<script language='javascript' src='<%=request.getContextPath()%>/script/plugins/jquery.filterInput.js'></script>
<script language='javascript' src='<%=request.getContextPath()%>/script/selectTree.js'></script>

<script language="javascript">
// 全局变量用于记录当前机构id值
var org;

// 初始化函数设置当前机构id值
function getOrgID(){
 org = document.getElementById("record:jgid_fk").value;
 }
 
 // 保存信息 
function func_record_saveRecord(){
	var psw = document.getElementById("record:yhmm").value;
	if(checkPasswordRule(window.ruleXml,psw)&&checkUserName() && checkPassword() && checkCertStatus()){
	 	var param = "select-key:jgid_fk="+org;
		saveAndExit("", "保存用户信息", "/txn807011.do?"+param );
	}else{
		return false;
	}
}

// 返回列表
function func_record_goBack(){
  var param = "select-key:jgid_fk="+org;

  goBack( "/txn807011.do?"+param );
}

// 校验输入的用户账号以及用户编号为数字和英文字母格式
function checkUserName(){
	var yhzh = document.getElementById("record:yhzh").value;
	var yhbh = document.getElementById("record:yhbh").value;

	if(!validateWebPath(yhzh)){
		alert("用户账号输入非法字符！请输入数字及英文字母A-Z或a-z的组合。");
		return false;
	}
	return true;
}

// 检查密码的合法性
function checkPassword(){
	var psw = document.getElementById("record:yhmm").value;
	var psw1 = document.getElementById("record:yhmm1").value;
	/*
	if(psw.length<6){
		alert("您输入的密码小于6位！请输入一个大于6位的密码！");
		return false;
	}
	*/
    if(psw!=psw1){
		alert("两次密码输入不一致！");
		return false;
	}
	return true;
}

function checkCertStatus(){
	var isneedkey = getFormFieldValue("record:isneedkey");
	var keynumber = getFormFieldValue("record:keynumber");
	if (isneedkey == '1' && !keynumber){
		alert("请输入证书编号！");
		document.getElementById("record:keynumber").focus();
		return false;
	}
	return true;
}

// 检查email的合法性
function checkEmail(){
	var email = document.getElementById("record:dzyx");
	return isEMail(email);
}

// 检查身份证的合法性
function checkID(){
	var idcard = document.getElementById("record:sfz");
	var result = isWeb(idcard);
	if(!result){
		alert("输入的身份证号含有非法字符！");
	}
	return result;
}

// 选择机构
function chooseOrgs(){
	var jzjgids = document.getElementById("record:jzjgid").value;
	selectJG('check','record:jzjgid','record:jzjgname',jzjgids);
}
function checkPasswordRule(ruleXml,psw){
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
			//必须要有特殊字符
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
		document.getElementById("record:yhmm").hint = getHint(xml)||'';
	});
	$(document.getElementById("record:yhbh")).filterInput();
	$(document.getElementById("record:yhxm")).filterInput();
	
	$(".radioNew").each(function(index){
		$($(this).prev()[0]).css("display","");
		$($(this).prev()[0]).css("margin-left","-1000");
		if(index<2){
		$(this).click(function(){
			$($(".radioNew")[1-index]).css("background-position-y","bottom");
			$(this).prev()[0].click();
			if($(this).prev()[0].checked){
				$(this).css("background-position-y","top");
			}
		});
		$(this).next()[0].onclick=function(){
			$(this).prev()[0].click();
			if($(this).prev()[0].checked){
				$(this).css("background-position-y","top");
			}
		};}else{
		$(this).click(function(){
			$($(".radioNew")[5-index]).css("background-position-y","bottom");
			$(this).prev()[0].click();
			if($(this).prev()[0].checked){
				$(this).css("background-position-y","top");
			}
		});
		$(this).next()[0].onclick=function(){
			$(this).prev()[0].click();
			if($(this).prev()[0].checked){
				$(this).css("background-position-y","top");
			}
		};
		}
		
		if($(this).prev()[0].checked){
			$(this).css("background-position-y","top");
		}
	});
});

</script>

<freeze:form action="/txn807013">
  <freeze:block property="record" caption="新建用户信息" width="95%" >
    <freeze:button name="record_saveRecord" caption="保 存" txncode="807013" hotkey="SAVE" onclick="func_record_saveRecord();"/>
    <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
    <freeze:hidden property="yhid_pk" caption="用户ID" style="width:100%"/>
    <freeze:text property="yhbh" caption="用户编号" style="width:90%" datatype="number" maxlength="20" minlength="1"/>
    <freeze:text property="yhxm" caption="用户姓名" style="width:90%" datatype="string" maxlength="20" minlength="1"/>
    <freeze:text property="yhzh" caption="用户账号" style="width:90%" datatype="string" maxlength="20" minlength="1" hint="输入值不可重复，且为数字或英文字母A-Z或a-z的组合"/>
	
	<freeze:text property="jgname" caption="<font color='red'>*所属机构</font>" style="width:60%" minlength="1" readonly="true"/>&nbsp;<input type="button" name="orgchoice" value="选择" onclick="selectJG('tree','record:jgid_fk','record:jgname')" class="menu"/>
    <freeze:hidden property="jgid_fk" caption="所属机构" style="width:90%" minlength="1"/>
   
    <freeze:password property="yhmm" caption="<font color='red'>*用户密码</font>" style="width:90%" />
    <freeze:password property="yhmm1" caption="<font color='red'>*确认密码</font>" style="width:90%" />    
    <freeze:browsebox property="mainrole" caption="主要角色" valueset="组织机构角色" show="name" style="width:90%" namebox="mainrolename" notnull="true"/>    
    <freeze:text property="plxh" caption="排列序号" value="0" style="width:90%" datatype="string" maxlength="10"/>
      
    <freeze:browsebox property="roleids" caption="其他角色" valueset="组织机构角色" show="name" style="width:96%"  multiple="true" namebox="rolenames" colspan="3"/> 
	<!--  
    <freeze:text property="jzjgname" caption="兼职机构" style="width:83%" colspan="3" readonly="true"/>&nbsp;&nbsp;<input type="button" name="orgchoice" value="选择" onclick="chooseOrgs()" class="menu"/>
    -->
    <freeze:hidden property="jzjgname" caption="兼职机构" style="width:80%" />   
    <freeze:hidden property="jzjgid" caption="兼职机构id" style="width:80%" />   

    <freeze:hidden property="sfyx" caption="是否有效" style="width:90%" value="0"/>
    <freeze:text property="sfz" caption="身份证号" style="width:90%" datatype="idcard" maxlength="20"/>
    <freeze:text property="zw" caption="职位" style="width:90%" datatype="string" maxlength="64"/>
    <freeze:textarea property="zyzz" caption="主要职责" style="width:96%" maxlength="255" colspan="3" rows="5" valign="center"/>
    <freeze:text property="gzdh" caption="工作电话" style="width:90%" datatype="phone" maxlength="20"/>
    <freeze:text property="lxdh" caption="联系电话" style="width:90%" datatype="phone" maxlength="20"/>
    <freeze:text property="dzyx" caption="电子邮件" style="width:96%" datatype="mail" maxlength="64" colspan="3" />
    <freeze:text property="qtlxfs" caption="其他联系方式" style="width:96%" datatype="string" maxlength="64" colspan="3"/>
    <freeze:radio property="isneedkey" caption="证书登陆" valueset="布尔型数" style="width:90%" notnull="true"></freeze:radio>
    <freeze:text  property="keynumber" caption="证书编号" style="width:90%"></freeze:text>
    <freeze:textarea property="bz" caption="备注" style="width:96%" maxlength="255" colspan="3" rows="5" valign="center"/>
     </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>