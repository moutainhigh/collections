<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html width="650" height="350"><head>
	<title>深圳市市场和质量监督管理委员会</title>
	<style type="text/css">
		.body-div{
			overflow:hidden !important;
		}
		
		.tableLogin {
			margin-top: 38px;
			margin-left: 110px;
		}
		.input01 {
			font-family: Helvetica, sans-serif, Arial;
			font-size: 16px;
			color: #5F707C;
		}
		.tdLogin {
			font-family: "宋体";
			font-size: 14px;
			color: #00000;
			line-height: 36px;
		}
		.index_bg2 {
			background-image: url(module/img/login-images/index_bg2.jpg);
			background-repeat: no-repeat;
			background-position: 0px 0px;
		}
	</style>
</head>
<script language="javaScript">

	if(openWindowType == 'modal'){
		_top.navigate( _top.loginPage );
		window.close();
	}
	function checkForm(){
		var username = document.getElementById("username");
		
		var password = document.getElementById("password");
		if(username.value==""){
			alert("请输入用户名");
			username.focus();
			return false;
		}
		if(password.value==""){
			alert("请输入密码");
			password.focus();
			return false;
		}
		return true;
	}
	function submitForm(){
		
		if(!checkForm()) return false;
		
		//var password = document.getElementById("password");
		//password.value = calcMD5(password.value);
		//document.indexform.action="txn999999.do";
		//document.indexform.submit();
	}
	
	function resetForm(){
	
		var username = document.getElementById("username");
		var password = document.getElementById("password");
		username.value="";
		password.value="";
		document.getElementById('username').focus();
		/*var ruleSrc = '<%=request.getContextPath()%>/password.jsp?timeStamp='+new Date().getTime();
		$.get(ruleSrc,function(xml){
			if($('validatecode',xml).length==0)return false;
			validatecodeEnable = $('validatecode',xml)[0].getAttribute('enable');
			if(validatecodeEnable=='true'){
				document.getElementById("add-code").value="";
			}
		});*/
	}
	window.onload = function(){
		document.getElementById('username').focus();
	}
</script>
<freeze:body>
<div style="width:410px; height:300; position:absolute; left:50%; top: 50%; margin-left: -205px; margin-top: -150px; ">
<form name="indexform" method="post" action="/txn999999.do" onsubmit="return submitForm()">
<input type='hidden' name='login-page' value='/login.jsp'>
<input type='hidden' name='logintype' id='logintype' value='000001'>
<table id="mainTable" width="100%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0" style="background:url(module/img/login-images/login-bj_sz.png) 50% 50% no-repeat;">
  <tr>
    <td><table border="0" cellpadding="0" cellspacing="0" align="center">
      <tr height="50"><td>&nbsp;</td></tr>
	  <tr>
        <td width="60" class="tdLogin">用户名:</td>
        <td width="170" style="background:url(module/img/login-images/username.png) left 50% no-repeat;">
        	<input  id="username" name="username" type="text"style="margin-left: 30px; height: 30px; line-height: 30px; border: 1px solid #ccc;" class="input01" size="25" maxlength="15" />
        </td>
<!--         <td width="68">
        	<input name="imageField" type="image" src="module/img/login-images/ico1.jpg" width="68" height="20" border="0"  onClick="return submitForm();" /></td> -->
      </tr>
      <tr>
        <td class="tdLogin">密&nbsp;&nbsp;码:</td>
        <td style="background:url(module/img/login-images/passwd.png) left 50% no-repeat;">
        	<input  name="password" id="password" type="password" style="margin-left: 30px; height: 30px; line-height:30px; border: 1px solid #ccc;" class="input01" size="25" maxlength="15" />
        </td>
        <!-- <td><input name="imageField2" type="image" src="module/img/login-images/ico2.jpg" width="68" height="20" border="0" onClick="resetForm();return false;"/></td> -->
      </tr>
      <tr>
      	<td colspan="2" height="30" align="center"><freeze:errors/></td>
      </tr>
      <tr>
        <td align="center" colspan="2" height="40" align="center" valign="middle">
        	<input type="submit" value="" style="border-width: 0px; background:url(module/img/login-images/login.png) 50% 50% no-repeat; width:170px; height:30px;" />&nbsp;&nbsp; <a href="javascript: resetForm();" >重置</a>
        </td>
      </tr>
    </table></td>
  </tr>
</table>
</form>
</div>
<script type="text/javascript">
	if (screen.width <= 800){
		var mainTable = document.getElementById("mainTable");
		mainTable.background="module/img/login-images/index_bg3.jpg";
		mainTable.rows[0].style.display = "none";
		mainTable.rows[1].style.display = "none";
		mainTable.rows[2].style.display = "block";
		mainTable.rows[3].style.display = "block";
		mainTable = null;
	}
</script>
<SCRIPT language=JavaScript src="<%=request.getContextPath()%>/script/lib/SecX_Common.js"></SCRIPT>
</freeze:body>
</freeze:html>