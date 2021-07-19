<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="750" height="350">
<head>
<title>修改用户信息</title>
<style>
.odd2_b {
	white-space: nowrap;
}
.odd2_b span,.odd1_b span{
	width: auto !important;
}
</style>
</head>
<freeze:body onload="getOrgID()">
<freeze:title caption="修改用户信息"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script language='javascript' src='<%=request.getContextPath()%>/script/lib/jquery.js'></script>
<script language='javascript' src='<%=request.getContextPath()%>/script/plugins/jquery.filterInput.js'></script>
<script language='javascript' src='<%=request.getContextPath()%>/script/selectTree.js'></script>

<script language="javascript">
// 全局变量用于记录当前机构id值
var org;

// 初始化函数设置当前机构id值
function getOrgID(){
 org = document.getElementById("primary-key:jgid_fk").value;
 }

// 保存记录
function func_record_save_record(){
 	// 校验用户输入是否正确
	if( checkCertStatus() ){	
 		var param = "select-key:jgid_fk="+org;
 		saveAndExit("", "修改用户信息", "/txn807011.do?"+param );
 		// saveAndExit("","保存用户信息失败" ,"/txn807001.do");
 	}else{	
 		return false;
 	}
}

// 返回列表
function func_record_goBack(){
var param = "select-key:jgid_fk="+org;
  goBack( "/txn807011.do?"+param );
}

// 判断两次密码是否一致
function checkPassword(){
	var psw = document.getElementById("record:yhmm").value;
	var psw1 = document.getElementById("record:yhmm1").value;
	
	if(psw==null || psw==""){
		return true;
	}else{
		if(!checkPasswordRule(window.ruleXml,psw)){//
			return false;
		}
	}
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

// 判断Email格式是否合法
function checkEmail(){
	var email = document.getElementById("record:dzyx");
	return isEMail(email);
}

// 判断用户身份证是否合法
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
	hintArray.push("用户密码为空时不修改原密码。否则必须满足：");
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

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{	
	var ruleSrc = '<%=request.getContextPath()%>/password.jsp';
	$.get(ruleSrc,function(xml){
		window.ruleXml = xml;
		document.getElementById("record:yhmm").hint = getHint(xml)||'';
	});
	$(document.getElementById("record:yhbh")).filterInput();
	$(document.getElementById("record:yhxm")).filterInput();
	
	$(".radioNew").each(function(index){
			$(this).click(function(){
				if(index<2){
					$(".radioNew")[(1-index)].style.backgroundPositionY='bottom';
					$(".radioNew")[(index)].style.backgroundPositionY='top';
					$(this).prev()[0].click();
				}else{
					$(".radioNew")[(5-index)].style.backgroundPositionY='bottom';
					$(".radioNew")[(index)].style.backgroundPositionY='top';
					$(this).prev()[0].click();
				}
			});
			$(this).next().click(function(){
				if(index<2){
					$(".radioNew")[(1-index)].style.backgroundPositionY='bottom';
					$(".radioNew")[(index)].style.backgroundPositionY='top';
					$(this).prev()[0].click();
				}else{
					$(".radioNew")[(5-index)].style.backgroundPositionY='bottom';
					$(".radioNew")[(index)].style.backgroundPositionY='top';
					$(this).prev()[0].click();
				}
			});
			if($(this).prev()[0].checked){
				$(".radioNew")[(index)].style.backgroundPositionY='top';
			}
		});
}
_browse.execute( '__userInitPage()' );
</script>

<freeze:form action="/txn807012">
  <freeze:frame property="primary-key" width="100%">
    <freeze:hidden property="yhid_pk" caption="用户ID" style="width:90%"/>
    <freeze:hidden property="jgid_fk" caption="机构ID" style="width:90%"/>
  </freeze:frame>

  <freeze:block property="record" caption="修改用户信息" width="100%" >
    <freeze:button name="record_saveAndExit" caption="保 存" txncode="807002" hotkey="SAVE" onclick="func_record_save_record();"/>
    <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
    <freeze:hidden property="yhid_pk" caption="用户ID" style="width:90%"/>
    <freeze:hidden property="yhbh" caption="用户编号" style="width:90%"/>
    <freeze:text property="yhzh" caption="用户账号" style="width:90%" datatype="string" minlength="1" readonly="true" hint="用户账号不能修改"/>    
    <freeze:text property="yhxm" caption="用户姓名" style="width:90%" datatype="string" maxlength="20" minlength="1"/>
    <freeze:hidden property="plxh" caption="排列序号" style="width:90%" datatype="string" maxlength="10"/>
    <freeze:text property="jgname" caption="<font color='red'>*所属机构</font>" style="width:60%"  readonly="true" minlength="1"/>&nbsp;&nbsp;
    <a href="#" name="orgchoice" value="选择" onclick="selectJG('tree','record:jgid_fk','record:jgname')">选择</a>   
    <freeze:radio property="sfyx" caption="使用状态"  valueset="syzt" style="width:90%"/>
    <freeze:text property="maxline" caption="最大同时在线数" datatype="number" maxlength="5" minlength="1" style="width:90%"/>
    <freeze:hidden property="jgid_fk" caption="所属机构" style="width:90%" />
    <freeze:hidden property="yhmm" caption="用户密码" style="width:90%"   />    
    <freeze:hidden property="yhmm1" caption="确认密码" style="width:90%"  />    
	<freeze:hidden property="mainrole" caption="主要角色"style="width:90%"/>  
	<freeze:hidden property="mainrolename" caption="主要角色名称"style="width:90%"/>      
	<freeze:browsebox property="roleids" caption="角色" multiple="true" valueset="组织机构角色" show="name" notnull="true" namebox="rolenames"  colspan="2" style="width:96%"/>
    <freeze:radio property="isneedkey" caption="证书登陆" valueset="布尔型数" style="width:90%"></freeze:radio>
    <freeze:text  property="keynumber" caption="证书编号" style="width:90%"></freeze:text>
    <freeze:hidden property="jzjgname" caption="兼职机构" style="width:80%" />   
    <freeze:hidden property="jzjgid" caption="兼职机构id" style="width:80%" />    
    <freeze:hidden property="sfz" caption="身份证号" style="width:90%" datatype="idcard" maxlength="20" />
    <freeze:hidden property="zw" caption="职位" style="width:90%" datatype="string" maxlength="64"/>
    <freeze:hidden property="zyzz" caption="主要职责" style="width:96%" maxlength="255"/>
    <freeze:hidden property="gzdh" caption="工作电话" style="width:90%" datatype="phone" maxlength="20"/>
    <freeze:hidden property="lxdh" caption="联系电话" style="width:90%" datatype="phone" maxlength="20"/>
    <freeze:hidden property="dzyx" caption="电子邮件" style="width:96%" datatype="mail" maxlength="64"/>
    <freeze:hidden property="qtlxfs" caption="其他联系方式" style="width:96%" datatype="string" maxlength="64"/>
    <freeze:textarea property="bz" caption="备注" style="width:96%" maxlength="255" colspan="3" rows="5" valign="center"/>
  </freeze:block>
</freeze:form>
</freeze:body>
</freeze:html>