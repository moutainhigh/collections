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
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.ajaxservice.xmlconvertors.AppendixToXML" %>
<%@ page import="com.trs.components.wcm.content.persistent.Appendix" %>
<%@ page import="com.trs.components.wcm.content.persistent.Appendixes" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>

<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../../include/public_server.jsp"%>
<%--@include file="public_right_check.jsp"--%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)
	int nApplyFormId = currRequestHelper.getInt("ApplyFormId", 0);
	ApplyForm currApplyForm = null;
	if(nApplyFormId > 0){
		currApplyForm = ApplyForm.findById(nApplyFormId);
		if(currApplyForm == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取ID为["+nApplyFormId+"]的ApplyForm失败！");
		}
	}else{//nApplyFormId==0 create a new group
		currApplyForm = ApplyForm.createNewInstance();
	}
//5.权限校验

//6.业务代码
	
//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>政府公开目录---依申请公开</title>
<link id="indexStyle" href="css/add_manager.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="../../js/com.trs.util/Common.js" type="text/javascript"></script>
<script language="javascript" src="applyform_show.js" type="text/javascript"></script>
<script src="../../workflow/domain/WorkProcessor.js"></script>
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
        width:120px;
        background:F8F8F8;
    }
    .personTable .normalText{
		padding:2px 8px;
    }
    .textareaTag{
        width:100%;
        height:100%;
		overflow-y:auto;
    }
	table{
		font-size:14px;
	}
	.RowLabel{
		width:30px;
		text-align:center;
		color:red;
		font-weight:bold;
		font-size:14px;
	}
		
.function {
	font-size: 12px;
	color: #c65603;
	height:16px;
	line-height:16px;
	padding:2px 8px;
	border:1px solid gray;
}
A.function:link {
	COLOR: #c65603;
	text-decoration: none;
}
A.function:visited {
	COLOR: #c65603;
	text-decoration: none;
}
A.function:hover {
	COLOR: #000000;
	text-decoration: blink;
}
#tdDocTitle{
	font-weight:bold;
	font-family:华文仿宋,宋体,Arial;
	font-size:16pt;
}
.gunter_node_common{
		line-height: 26px;
		padding: 2px;
		border: 1px solid silver;
		font-family: georgia;
		background: #efefef;
		margin-right:2px;
		margin-bottom:2px;
		color: gray;
		height: 26px;
		width: 80px;
		float: left;
	}
	.gunter_node_title{
		font-weight: normal; 
		font-size: 12px; 
		text-align: center; 
		height: 20px;
		line-height: 20px;	
		white-space:nowrap;
	}
	.current_gunter_node{
		color: #010101; 
		font-weight: bold; 
	}
</style>
<script label="TagParser">
	$import('com.trs.web2frame.DataHelper');
	$import('com.trs.web2frame.TempEvaler');
	$import('com.trs.crashboard.CrashBoard');
	$import('com.trs.dialog.Dialog');
</script>

</HEAD>

<BODY>
<div class="container" style="padding-top:0;">
    <!--div class="header">
        <div class="headerLeft"></div>
        <div class="headerRight"></div>
    </div-->
    <!--div class="footer">
        版权所有&copy; 2002-2012 北京拓尔思（TRS）信息技术股份有限公司
    </div-->
    <div class="main" style="padding-top:10px;">
<textarea id="gunter_template" select="FLOWDOCS.FLOWDOC" style='display:none'>
	<for select=".">
		<div class="gunter_node_common" id="gunter_node_{#FlowDocId}" _flag="{#Flag.Id}">
			<div class="gunter_node_title" title="操作:{#Flag.Name}&#13;操作人:{#PostUser} &#13;时间:{#POSTTIME} &#13;意见:{#PostDesc}">
				<span class="icon_flag_{#Flag.Id}" title="{@getAdaptedFlagDesc(#Flag.Id)}"></span>{#Flag.Name}
			</div>
		</div>
	</for>
</textarea>
<table border=0 cellspacing=0 cellpadding=0 style="width:980px;">
<tr>
	<td colspan=3 id="tdDocTitle" align="center" height="30" valign="middle"><%=currApplyForm.toString()%></td>
</tr>
<tr height=30 id="tb_gunter" style="display:none">
<td width="70">流转轨迹：</td>
<td width="800">
		<div id="divGunter" style="font-family: georgia; margin-top: 8px; color: gray; display: none; overflow: auto; width: 100%;padding-bottom:5px;"></div>
</td>
<td align="right" id="td_top_nav" width="120">
		<span id="spReflow" style="display: none" >
			<a _flag="workflow" href="#" title="重新流转" _function="reflow" class="function flow_reflow">重新流转</a>
		</span>
		<span id="spStartFlow" style="display: none" >
			<a _flag="workflow" href="#" title="开始流转" _function="startFlow" class="function func_btn flow_start">开始流转</a>
		</span>
		<span id="spCease" style="display: none" >
			<a _flag="workflow" href="#" class="function func_btn flow_cease" title="结束流转" _function="ceaseFlow">结束流转</a>
		</span>
		<span width="60"> <a href="#" class="function close" title="关闭当前窗口" _function="close">关闭</a></span>
</td>
</tr>
</table>
<table border=0 cellspacing=0 cellpadding=0 style="border-left:1px solid silver;border-top:1px solid silver;width:980px;display:none;" id="applyerInfo_1">
    <tr>
        <td class="tdBorder RowLabel" style="width:100%;height:35px;"><span style="cursor:pointer;text-decoration:underline;" onclick="showContent('applyerInfo_1', 'applyerInfo_2');">申请人信息</span></td>
	</tr>
</table>
<table border=0 cellspacing=0 cellpadding=0 style="border-left:1px solid silver;border-top:1px solid silver; width:980px;" id="applyerInfo_2">
    <tr>
        <td class="tdBorder RowLabel" onclick="showContent('applyerInfo_2', 'applyerInfo_1');">申<br>请<br>人<br>信<br>息</td>
        <td>
            <table border=0 cellspacing=0 cellpadding=0 style="width:100%;height:100%;">
				<%if(currApplyForm.getPropertyAsInt("applyerType", 1) == 1){%>
                <tr>
                    <td> 
                        <table border=0 cellspacing=0 cellpadding=0 style="width:100%;height:100%;" id="dataContainer1">
                            <tr>
                                <td style="width:80px;text-align:center;" class="tdBorder">
									公民
								</td>
                                <td>
                                    <table class="personTable" border=0 cellspacing=0 cellpadding=0 style="width:100%;height:100%;">
                                        <tbody>
                                            <tr>
                                                <td class="label">姓名</td>
                                                <td>
													<div class="normalText">
														<%=showNull(currApplyForm.getPropertyAsString("applyerName"), "&nbsp;")%>
													</div>	
												</td>
                                                <td class="label">工作单位</td>
                                                <td>
													<div class="normalText">
														<%=showNull(currApplyForm.getPropertyAsString("department"), "&nbsp;")%>
													</div>	
												</td>
                                            </tr>
                                            <tr>
                                                <td class="label">证件名称</td>
                                                <td>
												<%
													String sCertificateName = null;
													switch(currApplyForm.getPropertyAsInt("certificateName", ApplyFormConstants.APPLY_CERTIFICATENAME_IDENTITY)){
														case ApplyFormConstants.APPLY_CERTIFICATENAME_IDENTITY:
															sCertificateName = "身份证";
															break;
														case ApplyFormConstants.APPLY_CERTIFICATENAME_STUDENT:
															sCertificateName = "学生证";
															break;
														case ApplyFormConstants.APPLY_CERTIFICATENAME_OFFICER:
															sCertificateName = "军官证";
															break;
														case ApplyFormConstants.APPLY_CERTIFICATENAME_WORKER:
															sCertificateName = "工作证";
															break;
													}
												%>
												<div class="normalText"><%=sCertificateName%></div>
												</td>
                                                <td class="label">证件号码</td>
                                                <td>
													<div class="normalText">
														<%=showNull(currApplyForm.getPropertyAsString("certificateCode"), "&nbsp;")%>
													</div>	
												</td>
                                            </tr>
                                            <tr>
                                                <td class="label">联系电话</td>
                                                <td>
													<div class="normalText">
														<%=showNull(currApplyForm.getPropertyAsString("telephone"), "&nbsp;")%>
													</div>	
												</td>
                                                <td class="label">邮政编码</td>
                                                <td>
													<div class="normalText">
														<%=showNull(currApplyForm.getPropertyAsString("postCode"), "&nbsp;")%>
													</div>	
												</td>
                                            </tr>
                                            <tr>
                                                <td class="label">联系地址</td>
                                                <td>
													<div class="normalText">
														<%=showNull(currApplyForm.getPropertyAsString("address"), "&nbsp;")%>
													</div>	
												</td>
                                                <td class="label">传真</td>
                                                <td>
													<div class="normalText">
														<%=showNull(currApplyForm.getPropertyAsString("fax"), "&nbsp;")%>
													</div>	
												</td>
                                            </tr>
                                            <tr>
                                                <td class="label">电子邮件</td>
                                                <td colspan="3">
													<div class="normalText">
														<%=showNull(currApplyForm.getPropertyAsString("email"), "&nbsp;")%>
													</div>	
												</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
				<%}else{%>
                <tr>
                    <td>
                        <table border=0 cellspacing=0 cellpadding=0 style="width:100%;height:100%;" id="dataContainer2">
                            <tr>
                                <td style="width:80px;text-align:center;" class="tdBorder">
									法人<br>/其他组织
								</td>
                                <td>
                                    <table class="personTable" border=0 cellspacing=0 cellpadding=0 style="width:100%;height:100%;">
                                        <tr>
                                            <td class="label">名称</td>
                                            <td>
												<div class="normalText">
													<%=showNull(currApplyForm.getPropertyAsString("applyerName"), "&nbsp;")%>
												</div>	
											</td>
                                            <td class="label">组织机构代码</td>
                                            <td>
												<div class="normalText">
													<%=showNull(currApplyForm.getPropertyAsString("organizeCode"), "&nbsp;")%>
												</div>	
											</td>
                                        </tr>
                                        <tr>
                                            <td class="label">法人代表</td>
                                            <td>
												<div class="normalText">
													<%=showNull(currApplyForm.getPropertyAsString("legalPerson"), "&nbsp;")%>
												</div>	
											</td>
                                            <td class="label">联系人姓名</td>
                                            <td>
												<div class="normalText">
													<%=showNull(currApplyForm.getPropertyAsString("associationPerson"), "&nbsp;")%>
												</div>	
											</td>
                                        </tr>
                                        <tr>
                                            <td class="label">联系人电话</td>
                                            <td>
												<div class="normalText">
													<%=showNull(currApplyForm.getPropertyAsString("telephone"), "&nbsp;")%>
												</div>	
											</td>
                                            <td class="label">传真</td>
                                            <td>
												<div class="normalText">
													<%=showNull(currApplyForm.getPropertyAsString("fax"), "&nbsp;")%>
												</div>	
											</td>
                                        </tr>
                                        <tr>
                                            <td class="label">联系地址</td>
                                            <td>
												<div class="normalText">
													<%=showNull(currApplyForm.getPropertyAsString("address"), "&nbsp;")%>
												</div>	
											</td>
                                            <td class="label">&#160;</td>
                                            <td>&#160;</td>
                                        </tr>
                                        <tr>
                                            <td class="label">电子邮件</td>
                                            <td colspan="3">
												<div class="normalText">
													<%=showNull(currApplyForm.getPropertyAsString("email"), "&nbsp;")%>
												</div>	
											</td>
                                        </tr>
                                    </table>                            
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
				<%}%>
            </table>
        </td>
    </tr>
</table>
<table border=0 cellspacing=0 cellpadding=0 style="border-left:1px solid silver;width:980px;display:none" id="needInfo_1">
    <tr>
        <td class="tdBorder RowLabel" style="width:100%;height:35px;"><span style="cursor:pointer;text-decoration:underline;" onclick="showContent('needInfo_1', 'needInfo_2');">所需信息情况</span></td>
	</tr>
</table>
<table border=0 cellspacing=0 cellpadding=0 style="border-left:1px solid silver;width:980px;" id="needInfo_2">
    <tr>
        <td class="tdBorder RowLabel" rowspan="2" onclick="showContent('needInfo_2', 'needInfo_1');">所<br>需<br>信<br>息<br>情<br>况</td>
        <td>
            <table border=0 cellspacing=0 cellpadding=0 style="width:100%;height:100%;" id="dataContainer3">
                <tr>
                    <td> 
                        <table border=0 cellspacing=0 cellpadding=0 style="width:100%;height:100%;">
                            <tr>
                                <td style="width:80px;height:30px;text-align:center;" class="tdBorder">索引号</td>
                                <td class="tdBorder">
									<div class="normalText" style="padding:2px 8px;">
										<%=showNull(currApplyForm.getPropertyAsString("indexCode"), "&nbsp;")%>
									</div>	
                                </td>
                            </tr>
							<tr>
                                <td style="width:80px;height:30px;text-align:center;" class="tdBorder">受理单位</td>
                                <td class="tdBorder">
                                    <div class="normalText" style="padding:2px 8px;">
										<%=showNull(currApplyForm.getPropertyAsString("DestDpt"), "&nbsp;")%>
									</div>                    
                                </td>
                            </tr>
                            <tr>
                                <td style="width:80px;height:80px;text-align:center;" class="tdBorder">所需信息的<br>内容描述</td>
                                <td class="tdBorder" style="padding:8px;">
                                    <div class="textareaTag"><%=showNull(currApplyForm.getPropertyAsString("applyDesc"), "&nbsp;")%></div>                    
                                </td>
                            </tr>
                            <tr>
                                <td style="width:80px;height:60px;text-align:center;" class="tdBorder">所需信息的<br>用途</td>
                                <td class="tdBorder" style="padding:8px;">
                                    <div class="textareaTag"><%=showNull(currApplyForm.getPropertyAsString("purpose"), "&nbsp;")%></div>                    
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
           <table border=0 cellspacing=0 cellpadding=0 class="tableBorder" style="width:100%;">
                <tr style="height:24px;">
                    <td>
						所需信息的指定提供方式:
						<%
							int[] aProviderType = {
								ApplyFormConstants.INFO_PROVIDER_TYPE_PAGE,
								ApplyFormConstants.INFO_PROVIDER_TYPE_EMAIL,
								ApplyFormConstants.INFO_PROVIDER_TYPE_CD,
								ApplyFormConstants.INFO_PROVIDER_TYPE_DISK
							};
							String[] aProviderTypeDesc = {
								"纸面",
								"电子邮件",
								"光盘",
								"磁盘"
							};
							String sProviderType = "";	
							for(int i = 0; i < aProviderType.length; i++){
								boolean isProviderType = currApplyForm.isInfoProviderType(aProviderType[i]);
								if(isProviderType){
									if(sProviderType.length() > 0){
										sProviderType += ",";
									}
									sProviderType += aProviderTypeDesc[i];
								}
							}
						%>
						<span style="font-size:12px;"><%=sProviderType%></span>
					</td>
                    <td>
						获取信息的方式:
						<%
							int[] aGetType = {
								ApplyFormConstants.INFO_GET_TYPE_POSTMAIL,
								ApplyFormConstants.INFO_GET_TYPE_QUICKPOST,
								ApplyFormConstants.INFO_GET_TYPE_EMAIL,
								ApplyFormConstants.INFO_GET_TYPE_FIXMAIL,
								ApplyFormConstants.INFO_GET_TYPE_BYSELF
							};
							String[] aGetTypeDesc = {
								"邮寄",
								"快递",
								"电子邮件",
								"传真",
								"自行领取"
							};
							String sGetType = "";	
							for(int i = 0; i < aGetType.length; i++){
								boolean isGetType = currApplyForm.isInfoGetType(aGetType[i]);
								if(isGetType){
									if(sGetType.length() > 0){
										sGetType += ",";
									}
									sGetType += aGetTypeDesc[i];
								}
							}
						%>
						<span style="font-size:12px;"><%=sGetType%></span>
					</td>
                </tr>
            </table>        
        </td>
    </tr>
</table>
<table border=0 cellspacing=0 cellpadding=0 style="border-left:1px solid silver;width:980px;" id="dataContainer4">
    <tr>
        <td class="tdBorder RowLabel">信<br>息<br>处<br>理<br>情<br>况</td>
        <td>
			<FORM NAME="frmData" ID="frmData" action="applyform_add_manager_dowith.jsp" method="post" style="width:100%;height:100%;">
			<input type="hidden" name="ApplyFormId" value="<%=currApplyForm.getId()%>">
            <table border=0 cellspacing=0 cellpadding=0 style="width:100%;height:100%;">
                <tr>
                    <td> 
                        <table border=0 cellspacing=0 cellpadding=0 style="width:100%;height:100%;">
                            <tr>
                                <td style="width:80px;height:30px;text-align:center;" class="tdBorder">处理状态</td>
                                <td class="tdBorder">
									<div class="normalText" style="padding:2px 8px;">
										<%/*
											String sDealType = null;
											switch(currApplyForm.getPropertyAsInt("DealType", 0)){
												case ApplyFormConstants.DEAL_TYPE_DEALING:
													sDealType = "正在处理";
													break;
												case ApplyFormConstants.DEAL_TYPE_DEALED:
													sDealType = "处理完成";
													break;
												case ApplyFormConstants.DEAL_TYPE_REJECT:
													sDealType = "拒绝处理";
													break;
											}*/
										%>
										<span style="margin-right:20px;"><%=currApplyForm.getStatusName()%></span>
									</div>	
                                </td>
                            </tr>
                            <tr>
                                <td style="width:80px;height:180px;text-align:center;" class="tdBorder">回复内容</td>
                                <td class="tdBorder" style="padding:8px;">
									<div class="textareaTag" style="border:1px solid gray;width:850px;height:200px;">
										<%=CMyString.showNull(currApplyForm.getMailBody())%>
									</div>
                                </td>
                            </tr>
							<tr>
                                <td style="width:80px;height:50px;text-align:center;" class="tdBorder">材料附件</td>
                                <td class="tdBorder" style="padding:8px;">
									<%=getAppendixs(currApplyForm)%>
									<div id="appendix_container">
									</div>
									<script>
										showAppendixs();
									</script>
									
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
			</FORM>
        </td>
    </tr>
</table>
</div>

</BODY>
</HTML>

<%!
	private String showNull(String sSrcStr, String sDefaultStr){
		if(sSrcStr == null || "".equals(sSrcStr.trim())){
			return sDefaultStr;
		}
		return sSrcStr;
	}
	private String getAppendixsXml(ApplyForm applyForm,int nAppendixType) throws WCMException{
		try{
			//根据负值ID获取applyForms
			Appendixes appendixes = Appendixes.createNewInstance(ContextHelper.getLoginUser());
			WCMFilter filter = new WCMFilter("", "AppDocId=? and AppFlag=?", "");
			filter.addSearchValues(0, 0 - applyForm.getId());
			filter.addSearchValues(1, nAppendixType);
			appendixes.open(filter);
            // 将附件转换成为XML
            AppendixToXML appendixToXML = new AppendixToXML();
			return PageViewUtil.toHtmlValue(appendixToXML.toXmlString(null, appendixes));
		}catch(Exception ex){
			throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION, "转换Appendixs集合对象为XML字符串失败！", ex);
		}
	}
	private String getAppendixs(ApplyForm applyForm) throws WCMException{
		StringBuffer sbResult = new StringBuffer();
		sbResult.append("<textarea style='display:none' id='appendix_" + Appendix.FLAG_DOCAPD + "'>\n");
		sbResult.append(getAppendixsXml(applyForm,Appendix.FLAG_DOCAPD));
		sbResult.append("</textarea>\n");
		sbResult.append("<textarea style='display:none' id='appendix_" + Appendix.FLAG_DOCPIC + "'>\n");
		sbResult.append(getAppendixsXml(applyForm,Appendix.FLAG_DOCPIC));
		sbResult.append("</textarea>\n");
		sbResult.append("<textarea style='display:none' id='appendix_" + Appendix.FLAG_LINK + "'>\n");
		sbResult.append(getAppendixsXml(applyForm,Appendix.FLAG_LINK));
		sbResult.append("</textarea>\n");
		sbResult.append("<script>\n");
		sbResult.append("var Appendixs = null;\n");
		sbResult.append("try{\n");
		sbResult.append("\tAppendixs = {\n");
		sbResult.append("\t\tType_" + Appendix.FLAG_DOCAPD + ":\n");
		sbResult.append("\t\t\tcom.trs.util.JSON.parseXml(\n");
		sbResult.append("\t\t\tcom.trs.util.XML.loadXML($('appendix_" + Appendix.FLAG_DOCAPD + "').value)),\n");
		sbResult.append("\t\tType_" + Appendix.FLAG_DOCPIC + ":\n");
		sbResult.append("\t\t\tcom.trs.util.JSON.parseXml(\n");
		sbResult.append("\t\t\tcom.trs.util.XML.loadXML($('appendix_" + Appendix.FLAG_DOCPIC + "').value)),\n");
		sbResult.append("\t\tType_" + Appendix.FLAG_LINK + ":\n");
		sbResult.append("\t\t\tcom.trs.util.JSON.parseXml(\n");
		sbResult.append("\t\t\tcom.trs.util.XML.loadXML($('appendix_" + Appendix.FLAG_LINK + "').value))\n");
		sbResult.append("\t}\n");
		sbResult.append("}catch(err){\n");
		sbResult.append("\tAppendixs = {\n");
		sbResult.append("\t\tType_" + Appendix.FLAG_DOCAPD + ":{},\n");
		sbResult.append("\t\tType_" + Appendix.FLAG_DOCPIC + ":{},\n");
		sbResult.append("\t\tType_" + Appendix.FLAG_LINK + ":{}\n");
		sbResult.append("\t}\n");
		sbResult.append("}\n");
		sbResult.append("</script>\n");
		return sbResult.toString();
	}
%>