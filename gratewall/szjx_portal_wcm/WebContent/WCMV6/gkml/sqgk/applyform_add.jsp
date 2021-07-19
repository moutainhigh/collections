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
<script language="javascript" src="applyform_add.js" type="text/javascript"></script>
<style type="text/css">
    .personTable td{
        border-bottom:1px solid silver;
        border-right:1px solid silver;
    }
    .personTable{
        width:780px;
    }
    .tableBorder{
    }
    .tableBorder td{
        padding-left:8px;
        border-bottom:1px solid silver;
        border-right:1px solid silver;
    }
    .tdBorder, .personTable td{
        border-bottom:1px solid silver;
        border-right:1px solid silver;
    }
    .personTable td{
        height:30px;
    }
    .personTable .label{
        text-align:right;
        padding-right:8px;
        width:100px;
        background:F8F8F8;
    }
    .personTable .normalText, .personTable  select{
        margin-left:8px;
        width:200px;
        border:1px solid #9AABBB;
        background-color:#FEFEF1;
    }
    textarea{
        margin-left:8px;
        width:700px;
        height:50px;
        border:1px solid #9AABBB;
        background-color:#FEFEF1;
    }
	table{
		font-size:14px;
	}
	.disableTable .normalText{
	}
	.requireStyle{
		margin-left:4px;
		color:red;
	}
	.RowLabel{
		width:30px;
		text-align:center;
		color:red;
		font-weight:bold;
		font-size:14px;
	}
</style>
</HEAD>

<BODY>
<FORM NAME="frmData" ID="frmData" action="applyform_add_dowith.jsp" method="post">
<div class="container">
    <div class="header"></div>
    <div class="rowOfCurrentPosition">当前位置：公开目录 &gt; 依申请公开 &gt; 提交申请</div>
    <div class="main" style="text-align:center;">

<table border=0 cellspacing=0 cellpadding=0 style="border-left:1px solid silver;border-top:1px solid silver; width:980px;">
    <tr>
        <td class="tdBorder RowLabel">申<br>请<br>人<br>信<br>息</td>
        <td>
			<table border=0 cellspacing=0 cellpadding=0 style="width:100%;height:100%;">
				<tr>
					<td class="tdBorder" style="height:30px;">
						<input type="radio" name="applyerType" value="1" checked onclick="selectApplyerType(this.value);" id="applyerType_1">
						<label for="applyerType_1">公民</label>  &nbsp;&nbsp;&nbsp;
						<input type="radio" name="applyerType" value="2" onclick="selectApplyerType(this.value);" id="applyerType_2">
						<label for="applyerType_2">法人/其他组织</label>
					</td>
				</tr>
				<tr>
					<td colspan="2" id="dataContainer1">
						<table class="personTable" border=0 cellspacing=0 cellpadding=0 style="width:100%;height:100%;">
							<tbody>
								<tr>
									<td class="label">姓名</td>
									<td><input class="normalText" type="text" name="applyerName" value="" validation="type:'string',desc:'姓名',required:'',max_len:'100'"><span class="requireStyle">*</span></td>
									<td class="label">工作单位</td>
									<td><input class="normalText" type="text" name="department" value="" validation="type:'string',desc:'工作单位',max_len:'200'"></td>
								</tr>
								<tr>
									<td class="label">证件名称</td>
									<td>
										<select name="certificateName">
											<option value="<%=ApplyFormConstants.APPLY_CERTIFICATENAME_IDENTITY%>">身份证</option>
											<option value="<%=ApplyFormConstants.APPLY_CERTIFICATENAME_IDENTITY%>">学生证</option>
											<option value="<%=ApplyFormConstants.APPLY_CERTIFICATENAME_OFFICER%>">军官证</option>
											<option value="<%=ApplyFormConstants.APPLY_CERTIFICATENAME_WORKER%>">工作证</option>
										</select>
									</td>
									<td class="label">证件号码</td>
									<td><input class="normalText" type="text" name="certificateCode" value="" validation="type:'string',desc:'证件号码',required:'',max_len:'50'"><span class="requireStyle">*</span></td>
								</tr>
								<tr>
									<td class="label">联系电话</td>
									<td><input class="normalText" type="text" name="telephone" value="" validation="type:'string',desc:'联系电话',required:'',max_len:'50'"><span class="requireStyle">*</span></td>
									<td class="label">邮政编码</td>
									<td><input class="normalText" type="text" name="postCode" value="" validation="type:'string',desc:'邮政编码',required:'',max_len:'50'"><span class="requireStyle">*</span></td>
								</tr>
								<tr>
									<td class="label">联系地址</td>
									<td><input class="normalText" type="text" name="address" value="" validation="type:'string',desc:'联系地址',required:'',max_len:'200'"><span class="requireStyle">*</span></td>
									<td class="label">传真</td>
									<td><input class="normalText" type="text" name="fax" value="" validation="type:'string',desc:'传真',max_len:'50'"></td>
								</tr>
								<tr>
									<td class="label">电子邮件</td>
									<td colspan="3"><input class="normalText" style="width:300px;" type="text" name="email" value="" validation="type:'string',desc:'电子邮件',required:'',max_len:'50'" id="email_1"><span class="requireStyle">*</span></td>
								</tr>
							</tbody>
						</table>
					</td>									
					<td colspan="2" style="display:none;" id="dataContainer2">
						<table class="personTable" border=0 cellspacing=0 cellpadding=0 style="width:100%;height:100%;">
							<tr>
								<td class="label">名称</td>
								<td><input class="normalText" type="text" name="applyerName" value="" validation="type:'string',desc:'名称',required:'',max_len:'100'"><span class="requireStyle">*</span></td>
								<td class="label">组织机构代码</td>
								<td><input class="normalText" type="text" name="organizeCode" value="" validation="type:'string',desc:'组织机构代码',max_len:'100'"></td>
							</tr>
							<tr>
								<td class="label">法人代表</td>
								<td><input class="normalText" type="text" name="legalPerson" value="" validation="type:'string',desc:'法人代表',required:'',max_len:'100'"><span class="requireStyle">*</span></td>
								<td class="label">联系人姓名</td>
								<td><input class="normalText" type="text" name="associationPerson" value="" validation="type:'string',desc:'联系人姓名',required:'',max_len:'100'"><span class="requireStyle">*</span></td>
							</tr>
							<tr>
								<td class="label">联系人电话</td>
								<td><input class="normalText" type="text" name="telephone" value="" validation="type:'string',desc:'联系人电话',required:'',max_len:'50'"><span class="requireStyle">*</span></td>
								<td class="label">传真</td>
								<td><input class="normalText" type="text" name="fax" value=""  validation="type:'string',desc:'传真',max_len:'50'"></td>
							</tr>
							<tr>
								<td class="label">联系地址</td>
								<td><input class="normalText" type="text" name="address" value="" validation="type:'string',desc:'联系地址',required:'',max_len:'200'"><span class="requireStyle">*</span></td>
								<td class="label">电子邮件</td>
								<td><input class="normalText" type="text" name="email" value="" validation="type:'string',desc:'电子邮件',required:'',max_len:'50'" id="email_2"><span class="requireStyle">*</span></td>
							</tr>
						</table>                            
					</td>
				</tr>
			</table>
        </td>
    </tr>
</table>
<table border=0 cellspacing=0 cellpadding=0 style="border-left:1px solid silver;width:980px;" id="dataContainer3">
    <tr>
        <td class="tdBorder RowLabel" rowspan="2">所<br>需<br>信<br>息<br>情<br>况</td>
		<td> 
			<table border=0 cellspacing=0 cellpadding=0 style="width:100%;height:100%;" class="personTable">
				<tr>
					<td style="background:transparent;" class="label">索引号</td>
					<td class="tdBorder">
						<input class="normalText" type="text" name="indexCode" value="" validation="type:'string',desc:'索引号',required:'',max_len:'50'"><span class="requireStyle">*</span>
					</td>
				</tr>
				<tr>
					<td style="width:100px;height:130px;text-align:center;" class="tdBorder">所需信息的<br>内容描述</td>
					<td class="tdBorder">
						<textarea name="applyDesc" style="height:110px;" validation="type:'string',desc:'所需信息的内容描述',required:'',max_len:'2000'"></textarea><span class="requireStyle">*</span>                    
					</td>
				</tr>
				<tr>
					<td style="width:100px;height:60px;text-align:center;" class="tdBorder">所需信息的<br>用途</td>
					<td class="tdBorder">
						<textarea name="purpose" validation="type:'string',desc:'所需信息的用途',required:'',max_len:'2000'"></textarea><span class="requireStyle">*</span>                    
					</td>
				</tr>
			</table>
		</td>
    </tr>
    <tr>
        <td>
            <table border=0 cellspacing=0 cellpadding=0 class="tableBorder" style="width:100%;">
                <tr style="height:30px;">
                    <td>所需信息的指定提供方式（可选）</td>
                    <td>获取信息的方式（可选）</td>
                </tr>
                <tr style="font-size:12px;" onclick="clickInfoType();">
					<td>
                        <input type="checkbox" value="<%=ApplyFormConstants.INFO_PROVIDER_TYPE_PAGE%>" name="pageProviderType"/> 纸面<br>
                        <input type="checkbox" value="<%=ApplyFormConstants.INFO_PROVIDER_TYPE_EMAIL%>" name="emailProviderType" checked/> 电子邮件<br>
                        <input type="checkbox" value="<%=ApplyFormConstants.INFO_PROVIDER_TYPE_CD%>" name="cdProviderType"/> 光盘<br>
                        <input type="checkbox" value="<%=ApplyFormConstants.INFO_PROVIDER_TYPE_DISK%>" name="diskProviderType"/> 磁盘<br>
                    </td>
                    <td>
                        <input type="checkbox" value="<%=ApplyFormConstants.INFO_GET_TYPE_POSTMAIL%>" name="postMailGetType"/> 邮寄<br>
                        <input type="checkbox" value="<%=ApplyFormConstants.INFO_GET_TYPE_QUICKPOST%>" name="quickPostGetType"/> 快递<br>
                        <input type="checkbox" value="<%=ApplyFormConstants.INFO_GET_TYPE_EMAIL%>" name="emailGetType" checked/> 电子邮件<br>
                        <input type="checkbox" value="<%=ApplyFormConstants.INFO_GET_TYPE_FIXMAIL%>" name="fixMailGetType"/> 传真<br>
                        <input type="checkbox" value="<%=ApplyFormConstants.INFO_GET_TYPE_BYSELF%>" name="bySelfGetType"/> 自行领取<br>    
                    </td>
                </tr>
            </table>        
        </td>
    </tr>
</table>
    
    <div class="verifyCode" style="text-align:left;padding-left:2px;">
        输入图片中的字符：
        <input type="text" name="rand" value="" style="border:1px solid silver;width:60px;">
        请输入验证码 <img src="/wcm/verifycode" border=0 alt="" id="verifycodeImg"> 
        <a href="" onclick="getNextVerifyCode();">看不清？换一张</a>         
    </div>
    <div style="margin-top:10px;">
        <button onclick="submitData();">提&nbsp;&nbsp;交&nbsp;&nbsp;申&nbsp;&nbsp;请</button>
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