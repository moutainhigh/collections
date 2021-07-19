<%@ page contentType="text/html; charset=GBK"%>
<%@page import="com.gwssi.common.database.DBOperation"%>
<%@page import="com.gwssi.common.database.DBOperationFactory"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<freeze:html width="900" height="450">
<head>
<title>新建用户信息</title>
<%
DataBus context = (DataBus) request.getSession().getAttribute("oper-data");
String roleIds=context.getValue("role-list");
//判断是否有system角色
boolean isSystem= context.getValue("role-list").indexOf("101")==-1 ? false : true; 
String[] ids=roleIds.trim().split(";");
String rids="";
for(int i=0;i<ids.length;i++){
	rids+= rids=="" ? "'"+ids[i]+"'" : ",'"+ids[i]+"'";
}
DBOperation operation = DBOperationFactory.createTimeOutOperation();
String sql="select ROLEID,ROLENAME from operrole_new where roleid in("+rids+")";
List list=operation.select(sql);
//System.out.println(RoleDAO.getInst().getRoleInfo("102").size());
%>
</head>
<freeze:body>
	<freeze:title caption="新建用户信息" />
	<script type="text/javascript" src='<%=request.getContextPath()%>/script/lib/jquery.js'></script>
	<script type="text/javascript" src='<%=request.getContextPath()%>/script/plugins/jquery.filterInput.js'></script>
	<script type="text/javascript" src='<%=request.getContextPath()%>/script/selectTree.js'></script>
	<script type="text/javascript">
// 保存信息
function func_record_saveRecord(){
//校验用户输入是否正确
	var psw = document.getElementById("record:yhmm").value;
	if(checkUserName()&&checkJg()&&checkCertStatus()){
		saveAndExit("","保存用户信息失败" ,"/txn807001.do");
	}else{
	  	return false;
	}
	
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

// 返回列表
function func_record_goBack(){
  goBack( "/txn807001.do" );
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

// 校验输入的密码是否为6位长度以及确认密码是否正确
function checkPassword(){
	var psw = document.getElementById("record:yhmm").value;
	var psw1 = document.getElementById("record:yhmm1").value;
	
	//if(psw.length<6){
	//	alert("您输入的密码小于6位！请输入一个大于6位的密码！");
	//	return false;
	//}
    if(psw!=psw1){
		alert("两次密码输入不一致！");
		return false;
	}
	return true;
}

// 校验输入的电子邮箱
function checkEmail(){
	var email = document.getElementById("record:dzyx");
	return isEMail(email);
}

// 校验输入的身份证
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
});

function checkJg(){

    var jgname = getFormFieldValue("record:jgname");
    if(jgname==""){
       alert("请选择所属机构！");
       return false;
    }else{
       return true;
    }
}

function valYhzh(){
    var yhzh = document.getElementById("record:yhzh").value;
    if(yhzh==""){
      return;
    }
    $.ajax({
	  type: "post",
	  url: "<%=request.getContextPath()%>/txn807009.ajax?select-key:yhzh=" + yhzh,
	  async: false,
	  success: function(xmlResults){
	  	if (xmlResults.selectSingleNode("//context/error-code").text != "000000"){
	  		alert("用户账号【"+yhzh+"】已存在！");
	  		document.getElementById("record:yhzh").value = "";
	  		document.getElementById("record:yhzh").focus();
	  	}
	  }
	});      
}
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	$(".radioNew").hide();
	$(".radioNew").prev().show();
	/* $(".radioNew").each(function(index){
		$($(this).prev()[0]).css("display","");
		$($(this).prev()[0]).css("position","absolute");
		$($(this).prev()[0]).css("left","-1000");
		$($(this).prev()[0]).css("top","-1000");
		//$($(this).prev()[0]).css("margin-bottom","-1000");
		$(this).click(function(){
			$(".radioNew").css("background-position-y","bottom");
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
		if($(this).prev()[0].checked){
			$(this).css("background-position-y","top");
		}
	}); */
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:form action="/txn807003">
		<freeze:block property="record" caption="新建用户信息1" width="95%">
			<freeze:button name="record_saveRecord" caption="保 存"
				txncode="807003" hotkey="SAVE" onclick="func_record_saveRecord();" />
			<freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE"
				onclick="func_record_goBack();" />
			<freeze:hidden property="yhid_pk" caption="用户ID" style="width:90%" />
			<freeze:hidden property="yhbh" caption="用户编号" style="width:90%" />
			<freeze:text property="yhzh" caption="用户账号" style="width:90%"
				datatype="string" maxlength="20" minlength="1" onblur="valYhzh();"
				hint="输入值不可重复，且为数字或英文字母A-Z或a-z的组合" />
			<freeze:text property="yhxm" caption="用户姓名" style="width:90%"
				datatype="string" maxlength="20" minlength="1" />
			<freeze:hidden property="plxh" caption="排列序号" style="width:90%"
				value="0" datatype="string" maxlength="10" />
			<freeze:text property="jgname"
				caption="<div style='color:red;display:inline;'>*所属机构</div>" style="width:60%"
				datatype="string" readonly="true" />&nbsp;
			<a name="orgchoice" title="选择" style="cursor:pointer;text-decoration:underline;" href="#" onclick="selectJG('tree','record:jgid_fk','record:jgname')">选择</a>
			<freeze:hidden property="jgid_fk" caption="所属机构" style="width:90%"
				minlength="1" />
			<freeze:text property="maxline" caption="最大同时在线数" datatype="number"
				maxlength="5" value="1" minlength="1" style="width:90%" />
			<freeze:hidden property="yhmm"
				caption="<span style='color:red'>*用户密码</span>"
				value="E3CEB5881A0A1FDAAD01296D7554868D" style="width:90%" />
			<freeze:hidden property="yhmm1"
				caption="<span style='color:red'>*确认密码</span>"
				value="E3CEB5881A0A1FDAAD01296D7554868D" style="width:90%" />
			<freeze:hidden property="mainrole" caption="主要角色" />
			<freeze:hidden property="mainrolename" caption="主要角色名称" />
			<freeze:browsebox property="roleids" caption="角色" multiple="true"
				valueset="组织机构角色" show="name" namebox="rolenames" notnull="true"
				colspan="2" style="width:96%" />
			<freeze:radio property="isneedkey" caption="证书登陆" valueset="布尔型数"
				notnull="true" style="width:90%" value="0"></freeze:radio>
			<freeze:text property="keynumber" caption="证书编号" style="width:90%"></freeze:text>
			<freeze:hidden property="jzjgname" caption="兼职机构" style="width:80%" />
			<freeze:hidden property="jzjgid" caption="兼职机构id" style="width:80%" />
			<freeze:hidden property="sfyx" caption="是否有效" style="width:90%"
				value="0" />
			<freeze:hidden property="sfz" caption="身份证号" style="width:90%"
				datatype="idcard" maxlength="20" />
			<freeze:hidden property="zw" caption="职位" style="width:90%"
				datatype="string" maxlength="64" />
			<freeze:hidden property="zyzz" caption="主要职责" style="width:96%"
				maxlength="255" />
			<freeze:hidden property="gzdh" caption="工作电话" style="width:90%"
				datatype="phone" maxlength="20" />
			<freeze:hidden property="lxdh" caption="联系电话" style="width:90%"
				datatype="phone" maxlength="20" />
			<freeze:hidden property="dzyx" caption="电子邮件" style="width:96%"
				datatype="mail" maxlength="64" />
			<freeze:hidden property="qtlxfs" caption="其他联系方式" style="width:96%"
				datatype="string" maxlength="64" />
			<freeze:textarea property="bz" caption="备注" style="width:96%"
				maxlength="255" colspan="3" rows="5" valign="center" />
		</freeze:block>

	</freeze:form>
</freeze:body>
</freeze:html>