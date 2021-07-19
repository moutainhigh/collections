<%--
/** Title:			applyform_add.jsp
 *  Description:
 *		ApplyForm的添加修改页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			TRS WCM 6.0
 *  Created:		2008-02-20 20:18:05
 *  Vesion:			1.0
 *  Last EditTime:	2008-02-20 / 2008-02-20
 *	Update Logs:
 *		TRS WCM 6.0@2008-02-20 产生此文件
 *
 *  Parameters:
 *		see applyform_add.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	errorPage="../../../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.gkml.sqgk.persistent.ApplyForm" %>
<%@ page import="com.trs.components.gkml.sqgk.persistent.ApplyForms" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.components.gkml.sqgk.constant.ApplyFormConstants" %>

<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>政府公开目录---依申请公开</title>
<link id="indexStyle" href="css/add.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="../../js/com.trs.util/Common.js" type="text/javascript"></script>
<script language="javascript">
<!--
	$import("com.trs.validator.Validator");
//-->
</script>
<script language="javascript" src="applyform_query.js" type="text/javascript"></script>
<style type="text/css">
	.sqgktable{
		width:600px;
		height:250px;
		margin-top:130px;
		font-size:12px;
		background:white;
		border:1px solid #A5CAD3;
	}
	.sqgktable td{
		padding-top:10px;
		height:20px;
		padding-left:4px;
	}
    .sqgktable .label{
		width:90px;
		text-align:right;
		padding-right:4px;
		margin-top:3px;
		height:20px;
	}
    .sqgktable .normalText{
        width:200px;
        border:1px solid #9AABBB;
        background-color:#FEFEF1;
    }
	.leftPicture{
		width:220px;
		height:100%;
		background:url('images/query.jpg') center center no-repeat;
	}
</style>
</HEAD>

<BODY>
<FORM NAME="frmData" ID="frmData" action="applyform_query_dowith.jsp" method="post">
<div class="container">
    <div class="header"></div>
    <div class="rowOfCurrentPosition">当前位置：公开目录 &gt; 依申请公开 &gt; 查询申请</div>
    <div class="main" style="text-align:center;height:500px;">
		<div>
			<table cellspacing=0 cellpadding=0 class="sqgktable">
				<tr valign="middle">
					<td class="leftPicture" style="padding:0px;border-right:1px solid #A5CAD3;">&#160;</td>
					<td>
						<table border=0 cellspacing=0 cellpadding=0 style="width:100%;height:100%;">
							<tr valign="top">
								<td class="label">申请人类型：</td>
								<td style="padding-top:5px;">
									<input type="radio" name="applyerType" value="1" checked onclick="selectApplyerType(this.value);" id="personType">公民
									&nbsp;&nbsp;<input type="radio" name="applyerType" value="2" onclick="selectApplyerType(this.value);">法人/其他组织
								</td>
							</tr>
							<tr valign="top">
								<td class="label" id="nameTips">姓名：</td>
								<td>
									<input type="text" class="normalText" name="applyerName" value="">
								</td>
							</tr>
							<tr valign="top">
								<td class="label">查询号：</td>
								<td>
									<input type="text" class="normalText" name="queryCode" value="">
								</td>
							</tr>
							<tr valign="top">
								<td class="label">验证码：</td>
								<td>
									<input type="text" class="normalText" name="rand" value="" style="width:60px;">&nbsp;<img src="/wcm/verifycode" border=0 alt="" id="verifycodeImg" align="top">
									&nbsp; 
									<a href="" onclick="getNextVerifyCode();">看不清？换一张</a>  
								</td>
							</tr>
							<tr valign="middle">
								<td colspan="2" style="text-align:center;vertical-align:middle;">
									<button onclick="submitData();">查&nbsp;&nbsp;询</button>&nbsp;&nbsp;
									<input type="reset" value="重&nbsp;&nbsp;填">
								</td>
							</tr>
							<tr><td colspan="2">&#160;</td></tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
    </div>
    <div class="footer">
        <div class="footerLine"></div>
        <div>关于本站&nbsp;-&nbsp;使用帮助&nbsp;-&nbsp;联系我们</div>
        <div>技术支持：北京拓尔思信息技术股份有限公司</div>
        <div>Copyright&copy;2005-2012 All Rights Reserved 版权所有，未经授权，禁止转载</div>
        <div>ICP备案编号：京ICP备010164号</div>
        <div>建议使用IE5.5以上浏览器，分辨率1024*768</div>
    </div>
</div>

</FORM>
</BODY>
</HTML>