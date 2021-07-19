<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="../../include/error.jsp"%>
<%@ page import="com.trs.scm.persistent.Account"%>
<%@ page import="com.trs.scm.persistent.Accounts"%>
<%@ page import="com.trs.scm.persistent.SCMGroup"%>
<%@ page import="com.trs.scm.persistent.SCMGroups"%>
<%@ page import="com.trs.cms.auth.persistent.User"%>
<%@ page import="com.trs.cms.auth.persistent.Users"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor"%>

<%@include file="../../include/public_server.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>帐号管理-个人信息</title>
<link rel="stylesheet" href="../css/public.css">
<link rel="stylesheet" href="change_account_info.css">
<script src="../js/jquery-1.7.2.min.js"></script>
<script src="../js/scm_common.js"></script>
<script src="../js/iframe_public.js"></script>
<script src="change_account_info.js"></script>
</head>
	<body class="bodyColor">
		<div id="userList" class="lineHeight63">
			<div class="changeInfoFont">
				修改帐号信息
			</div>
			<div class="returnListFontContainer" onclick="window.open('all_accounts_list.html','_self')">
				<img src="../images/back_pic.png" class="margin_bottom_5Px"></img>
				<span class="returnListFont">返回帐号列表</span>
			</div>
		</div>
		<div style="clearFloat"></div>
		<% 
			int nAccountId = currRequestHelper.getInt("ObjectId",1);			
			

			JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
			String sServiceId = "wcm61_scmaccount" , sMethodName = "findById";
			

			Account oAccount = (Account) processor.excute(sServiceId,sMethodName);
			
			
		%>
		<!--绑定的帐号-->
		<div class="personalCount padding_left_0Px">
			<div class="userHeadContent">
				<div class="contentHeadPic"></div>
				<span class="logoContent">
					<img src="../images/sina_log.png" />
				</span>
			</div>			
			 <div class="expressOperate">
				   <div class="fansInfo">
						<div class="express">关注 <span class="color_num">706</span> &nbsp;<span class="colorLine">|</span> &nbsp;粉丝 <span class="color_num">2739306</span> &nbsp;|&nbsp; 微博 <class style="color_num">7481</span></div>
						<div class="height31Px" style="margin-top:-5px;" onclick="alert('更改头像')">
							<img src="../images/changeHead.png"class="pointerHand" />
						</div>
				   </div>  
			 </div>
			<form name="fm" method="post">
				昵&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称：<input type="text" class="margin20Px" style="font-size:14px;" value="<%=CMyString.filterForHTMLValue(oAccount.getAccountName()) %>"></input>
				&nbsp;&nbsp;&nbsp;4-24个字符
				<div  class="margin20Px">
					性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：<input type="radio" value="1" name="sex" checked>男</input>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" value="2" name="sex">女</input>
				</div>
				<div  class="margin20Px">
					<div class="floatLeft">个人描述：</div>
					<div class="floatRight">
						<textarea type="textarea" id="myExpress" class="textareaExpress"></textarea>
						<div class="margin_top13Px">
							<input type="image" src="../images/submit_button.png" onclick="submitForm()" />
							<div class="alarmText">
								<span class="countTxt">还可以输入<em id="showNum">140</em>字</span>
							</div>
						</div>						
					</div>					
				</div>
		    </form>
		   </div>
		   <div style="clearFloat"></div>		  
	</body>
</html>