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
<%@ page import="com.trs.components.wcm.content.persistent.Appendix"%>
<%@ page import="com.trs.components.wcm.content.persistent.Appendixes"%>
<%@ page import="com.trs.infra.persistent.WCMFilter"%>
<%@ page import="com.trs.infra.support.file.FilesMan"%>
<%@ page import="com.trs.infra.util.CMyFile"%>
<%@ page import="com.trs.infra.util.CMyString"%>
<!------- WCM IMPORTS END ------------>
<%@ page import="com.trs.components.gkml.sqgk.persistent.ApplyForm"%>
<%@ page import="com.trs.components.gkml.sqgk.persistent.ApplyForms"%>
<%@ page import="com.trs.components.gkml.sqgk.constant.ApplyFormConstants"%>
<%@ page import="com.trs.components.gkml.sqgk.SqgkFileUtil"%>


<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->

<%
    boolean isError = false;
    ApplyForm currApplyForm = null;
    String sMsg = null;
    Appendixes oAppendixes = null;
    try {
        String sQueryCode = request.getParameter("queryCode");
        if (sQueryCode == null) {
            throw new Exception("没有传入查询号！");
        }
        String sApplyerName = request.getParameter("applyerName");
        if (sApplyerName == null) {
            throw new Exception("没有传入用户名！");
        }
        String sApplyerType = request.getParameter("applyerType");
        if (sApplyerType == null) {
            throw new Exception("没有传入用户类型！");
        }
		int nApplyerType = 1;
		try{
			nApplyerType = Integer.parseInt(sApplyerType);
		}catch(Exception ex){
			//Ignore.
		}

        WCMFilter filter = new WCMFilter("",
                "queryCode=? and APPLYERNAME=? and APPLYERTYPE=?", "");
        filter.addSearchValues(0, sQueryCode);
        filter.addSearchValues(1, sApplyerName);
        filter.addSearchValues(2, nApplyerType);
        ApplyForms applyForms = ApplyForms.openWCMObjs(null, filter);

        for (int i = 0, nSize = applyForms.size(); i < nSize; i++) {
            currApplyForm = (ApplyForm) applyForms.getAt(i);
            if (currApplyForm == null)
                continue;
            break;
        }

        if (currApplyForm == null) {
            sMsg = "没有找到满足条件的记录！";
        } else {
            filter = new WCMFilter("", "AppDocId=?", "");
            filter.addSearchValues(0, -currApplyForm.getId());
            oAppendixes = Appendixes.openWCMObjs(null, filter);
        }
    } catch (Exception eOuter) {
        isError = true;
        eOuter.printStackTrace();
        sMsg = eOuter.getMessage();
    }
    out.clear();
%>

<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>政府公开目录---依申请公开</title>
<link id="indexStyle" href="css/add.css" rel="stylesheet"
	type="text/css" />
<script language="javascript" src="../js/com.trs.util/Common.js"
	type="text/javascript"></script>
<script language="javascript" src="applyform_query_dowith.js"
	type="text/javascript"></script>
<style type="text/css">
.personTable td {
	border-bottom: 1px solid silver;
	border-right: 1px solid silver;
}

.personTable {
	width: 780px;
}

.tableBorder {
	
}

.tableBorder td {
	padding-left: 8px;
	border-bottom: 1px solid silver;
	border-right: 1px solid silver;
}

.tdBorder,.personTable td {
	border-bottom: 1px solid silver;
	border-right: 1px solid silver;
}

.personTable td {
	height: 30px;
}

.personTable .label {
	text-align: right;
	padding-right: 8px;
	width: 120px;
	background: F8F8F8;
}

.personTable .normalText {
	padding: 2px 8px;
}

.textareaTag {
	width: 100%;
	height: 100%;
	overflow-y: auto;
}

table {
	font-size: 14px;
}

.errMsg {
	color: red;
}

.errorContainer {
	text-align: left;
	width: 50%;
	margin-top: 100px;
}

.errorContainer div {
	height: 25px;
	line-height: 25px;
}

.RowLabel {
	width: 30px;
	text-align: center;
	color: red;
	font-weight: bold;
	font-size: 14px;
}
</style>

</HEAD>

<BODY>
<div class="container">
<div class="header">
<div class="headerLeft"></div>
<div class="headerRight"></div>
</div>
<div class="rowOfCurrentPosition">当前位置：公开目录 &gt; 依申请公开 &gt; 申请结果</div>
<%
    if (sMsg != null) {
%>
<div class="main" style="text-align: center;">
<div class="errorContainer">
<%
    if (isError) {
%>
<div style="font-weight: bold;">申请信息提交失败</div>
<div>失败信息如下：</div>
<%
    }
%>
<div class="errMsg"><%=sMsg%></div>
<div style="text-align: center;">
<button onclick="window.history.back();">返回查询页面</button>
</div>
</div>
</div>
<%
    } else {
%>
<div class="main">
<table border=0 cellspacing=0 cellpadding=0
	style="border-left: 1px solid silver; border-top: 1px solid silver; width: 980px; display: none;"
	id="applyerInfo_1">
	<tr>
		<td class="tdBorder RowLabel" style="width: 100%; height: 35px;"><span
			style="cursor: pointer; text-decoration: underline;"
			onclick="showContent('applyerInfo_1', 'applyerInfo_2');">申请人信息</span></td>
	</tr>
</table>
<table border=0 cellspacing=0 cellpadding=0
	style="border-left: 1px solid silver; border-top: 1px solid silver; width: 980px;"
	id="applyerInfo_2">
	<tr>
		<td class="tdBorder RowLabel"
			onclick="showContent('applyerInfo_2', 'applyerInfo_1');"
			style="cursor: pointer;">申<br>
		请<br>
		人<br>
		信<br>
		息</td>
		<td>
		<table border=0 cellspacing=0 cellpadding=0
			style="width: 100%; height: 100%;">
			<%
			    if (currApplyForm.getPropertyAsInt("applyerType", 1) == 1) {
			%>
			<tr>
				<td>
				<table border=0 cellspacing=0 cellpadding=0
					style="width: 100%; height: 100%;" id="dataContainer1">
					<tr>
						<td style="width: 80px; text-align: center;" class="tdBorder">
						公民</td>
						<td>
						<table class="personTable" border=0 cellspacing=0 cellpadding=0
							style="width: 100%; height: 100%;">
							<tbody>
								<tr>
									<td class="label">姓名</td>
									<td>
									<div class="normalText"><%=showNull(currApplyForm
                                    .getPropertyAsString("applyerName"),
                                    "&nbsp;")%></div>
									</td>
									<td class="label">工作单位</td>
									<td>
									<div class="normalText"><%=showNull(currApplyForm
                                    .getPropertyAsString("department"),
                                    "&nbsp;")%></div>
									</td>
								</tr>
								<tr>
									<td class="label">证件名称</td>
									<td>
									<div class="normalText">
									<%
									    String sCertificateName = null;
									            switch (currApplyForm.getPropertyAsInt("certificateName",
									                    ApplyFormConstants.APPLY_CERTIFICATENAME_IDENTITY)) {
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
									            out.print(sCertificateName);
									%>
									</div>
									</td>
									<td class="label">证件号码</td>
									<td>
									<div class="normalText"><%=showNull(currApplyForm
                                    .getPropertyAsString("certificateCode"),
                                    "&nbsp;")%></div>
									</td>
								</tr>
								<tr>
									<td class="label">联系电话</td>
									<td>
									<div class="normalText"><%=showNull(currApplyForm
                                            .getPropertyAsString("telephone"),
                                            "&nbsp;")%></div>
									</td>
									<td class="label">邮政编码</td>
									<td>
									<div class="normalText"><%=showNull(currApplyForm
                                            .getPropertyAsString("postCode"),
                                            "&nbsp;")%></div>
									</td>
								</tr>
								<tr>
									<td class="label">联系地址</td>
									<td>
									<div class="normalText"><%=showNull(currApplyForm
                                    .getPropertyAsString("address"), "&nbsp;")%>
									</div>
									</td>
									<td class="label">传真</td>
									<td>
									<div class="normalText"><%=showNull(
                                    currApplyForm.getPropertyAsString("fax"),
                                    "&nbsp;")%></div>
									</td>
								</tr>
								<tr>
									<td class="label">电子邮件</td>
									<td colspan="3">
									<div class="normalText"><%=showNull(currApplyForm
                                    .getPropertyAsString("email"), "&nbsp;")%>
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
			<%
			    } else {
			%>
			<tr>
				<td>
				<table border=0 cellspacing=0 cellpadding=0
					style="width: 100%; height: 100%;" id="dataContainer2">
					<tr>
						<td style="width: 80px; text-align: center;" class="tdBorder">
						法人<br>
						/其他组织</td>
						<td>
						<table class="personTable" border=0 cellspacing=0 cellpadding=0
							style="width: 100%; height: 100%;">
							<tr>
								<td class="label">名称</td>
								<td>
								<div class="normalText"><%=showNull(currApplyForm
                                    .getPropertyAsString("applyerName"),
                                    "&nbsp;")%></div>
								</td>
								<td class="label">组织机构代码</td>
								<td>
								<div class="normalText"><%=showNull(currApplyForm
                                    .getPropertyAsString("organizeCode"),
                                    "&nbsp;")%></div>
								</td>
							</tr>
							<tr>
								<td class="label">法人代表</td>
								<td>
								<div class="normalText"><%=showNull(currApplyForm
                                    .getPropertyAsString("legalPerson"),
                                    "&nbsp;")%></div>
								</td>
								<td class="label">联系人姓名</td>
								<td>
								<div class="normalText"><%=showNull(currApplyForm
                                    .getPropertyAsString("associationPerson"),
                                    "&nbsp;")%></div>
								</td>
							</tr>
							<tr>
								<td class="label">联系人电话</td>
								<td>
								<div class="normalText"><%=showNull(currApplyForm
                                            .getPropertyAsString("telephone"),
                                            "&nbsp;")%></div>
								</td>
								<td class="label">传真</td>
								<td>
								<div class="normalText"><%=showNull(
                                    currApplyForm.getPropertyAsString("fax"),
                                    "&nbsp;")%></div>
								</td>
							</tr>
							<tr>
								<td class="label">联系地址</td>
								<td>
								<div class="normalText"><%=showNull(currApplyForm
                                    .getPropertyAsString("address"), "&nbsp;")%>
								</div>
								</td>
								<td class="label">&#160;</td>
								<td>&#160;</td>
							</tr>
							<tr>
								<td class="label">电子邮件</td>
								<td colspan="3">
								<div class="normalText"><%=showNull(currApplyForm
                                    .getPropertyAsString("email"), "&nbsp;")%>
								</div>
								</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<%
			    }
			%>
		</table>
		</td>
	</tr>
</table>
<table border=0 cellspacing=0 cellpadding=0
	style="border-left: 1px solid silver; width: 980px;" id="needInfo_1">
	<tr>
		<td class="tdBorder RowLabel" style="width: 100%; height: 35px;"><span
			style="cursor: pointer; text-decoration: underline;"
			onclick="showContent('needInfo_1', 'needInfo_2');">所需信息情况</span></td>
	</tr>
</table>
<table border=0 cellspacing=0 cellpadding=0
	style="border-left: 1px solid silver; width: 980px; display: none;"
	id="needInfo_2">
	<tr>
		<td class="tdBorder RowLabel" rowspan="2"
			onclick="showContent('needInfo_2', 'needInfo_1');"
			style="cursor: pointer;">所<br>
		需<br>
		信<br>
		息<br>
		情<br>
		况</td>
		<td>
		<table border=0 cellspacing=0 cellpadding=0
			style="width: 100%; height: 100%;" id="dataContainer3">
			<tr>
				<td>
				<table border=0 cellspacing=0 cellpadding=0
					style="width: 100%; height: 100%;">
					<tr>
						<td style="width: 80px; height: 30px; text-align: center;"
							class="tdBorder">索引号</td>
						<td class="tdBorder">
						<div class="normalText" style="padding: 2px 8px;"><%=showNull(currApplyForm
                                .getPropertyAsString("indexCode"), "&nbsp;")%>
						</div>
						</td>
					</tr>
					<tr>
						<td style="width: 80px; height: 130px; text-align: center;"
							class="tdBorder">所需信息的内容描述</td>
						<td class="tdBorder" style="padding: 8px;">
						<div class="textareaTag"><%=showNull(currApplyForm
                                .getPropertyAsString("applyDesc"), "&nbsp;")%></div>
						</td>
					</tr>
					<tr>
						<td style="width: 80px; height: 60px; text-align: center;"
							class="tdBorder">所需信息的用途</td>
						<td class="tdBorder" style="padding: 8px;">
						<div class="textareaTag"><%=showNull(
                                currApplyForm.getPropertyAsString("purpose"),
                                "&nbsp;")%></div>
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
		<table border=0 cellspacing=0 cellpadding=0 class="tableBorder"
			style="width: 100%;">
			<tr style="height: 24px;">
				<td>所需信息的指定提供方式: <%
				    int[] aProviderType = {
				                ApplyFormConstants.INFO_PROVIDER_TYPE_PAGE,
				                ApplyFormConstants.INFO_PROVIDER_TYPE_EMAIL,
				                ApplyFormConstants.INFO_PROVIDER_TYPE_CD,
				                ApplyFormConstants.INFO_PROVIDER_TYPE_DISK };
				        String[] aProviderTypeDesc = { "纸面", "电子邮件", "光盘", "磁盘" };
				        String sProviderType = "";
				        for (int i = 0; i < aProviderType.length; i++) {
				            boolean isProviderType = currApplyForm
				                    .isInfoProviderType(aProviderType[i]);
				            if (isProviderType) {
				                if (sProviderType.length() > 0) {
				                    sProviderType += ",";
				                }
				                sProviderType += aProviderTypeDesc[i];
				            }
				        }
				%> <span style="font-size: 12px;"><%=sProviderType%></span></td>
				<td>获取信息的方式: <%
				    int[] aGetType = { ApplyFormConstants.INFO_GET_TYPE_POSTMAIL,
				                ApplyFormConstants.INFO_GET_TYPE_QUICKPOST,
				                ApplyFormConstants.INFO_GET_TYPE_EMAIL,
				                ApplyFormConstants.INFO_GET_TYPE_FIXMAIL,
				                ApplyFormConstants.INFO_GET_TYPE_BYSELF };
				        String[] aGetTypeDesc = { "邮寄", "快递", "电子邮件", "传真", "自行领取" };
				        String sGetType = "";
				        for (int i = 0; i < aGetType.length; i++) {
				            boolean isGetType = currApplyForm
				                    .isInfoGetType(aGetType[i]);
				            if (isGetType) {
				                if (sGetType.length() > 0) {
				                    sGetType += ",";
				                }
				                sGetType += aGetTypeDesc[i];
				            }
				        }
				%> <span style="font-size: 12px;"><%=sGetType%></span></td>
			</tr>
			<tr style="height: 24px;">
				<td>所需信息的受理机关单位: <span style="font-size: 12px;"><%=currApplyForm.getPropertyAsString("DestDpt")%></span>
				</td>
				<td>&nbsp;</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<table border=0 cellspacing=0 cellpadding=0
	style="border-left: 1px solid silver; width: 980px;"
	id="dataContainer4">
	<tr>
		<td class="tdBorder  RowLabel">信<br>
		息<br>
		处<br>
		理<br>
		情<br>
		况</td>
		<td>
		<FORM NAME="frmData" ID="frmData"
			action="applyform_add_manager_dowith.jsp" method="post"
			style="width: 100%; height: 100%;"><input type="hidden"
			name="ApplyFormId" value="<%=currApplyForm.getId()%>">
		<table border=0 cellspacing=0 cellpadding=0
			style="width: 100%; height: 100%;">
			<tr>
				<td>
				<table border=0 cellspacing=0 cellpadding=0
					style="width: 100%; height: 100%;">
					<tr>
						<td style="width: 80px; height: 30px; text-align: center;"
							class="tdBorder">处理状态</td>
						<td class="tdBorder">
						<div class="normalText" style="padding: 2px 8px;">
						<%
						    int nDealType = currApplyForm.getPropertyAsInt("DEALTYPE", 0);
						%> <span style="margin-right: 20px;"> <%=currApplyForm.getStatusName()%>
						<%
						    /*
						                                                                                                                                                                                                                                                                                                                                                                        	switch(nDealType){
						                                                                                                                                                                                                                                                                                                                                                                        		case ApplyFormConstants.DEAL_TYPE_DEALED:
						                                                                                                                                                                                                                                                                                                                                                                        			out.print("处理完成");
						                                                                                                                                                                                                                                                                                                                                                                        			break;
						                                                                                                                                                                                                                                                                                                                                                                        		case ApplyFormConstants.DEAL_TYPE_REJECT:
						                                                                                                                                                                                                                                                                                                                                                                        			out.print("拒绝处理");
						                                                                                                                                                                                                                                                                                                                                                                        			break;
						                                                                                                                                                                                                                                                                                                                                                                        		default:
						                                                                                                                                                                                                                                                                                                                                                                        			out.print("正在处理");
						                                                                                                                                                                                                                                                                                                                                                                        	}
						         */
						%> </span></div>
						</td>
					</tr>
					<tr>
						<td style="width: 80px; height: 300px; text-align: center;"
							class="tdBorder">回复内容</td>
						<td class="tdBorder" style="padding: 8px;">
						<div class="textareaTag"
							style="border: 1px solid gray; width: 850px;"><%=CMyString.showNull(currApplyForm.getMailBody())%>
						</div>
						</td>
					</tr>
					<%
					    if (oAppendixes != null && !oAppendixes.isEmpty()) {
					%>
					<tr>
						<td style="width: 80px; text-align: center;" class="tdBorder">回复附件</td>
						<td style="padding: 8px;">
						<div style="border: 1px solid gray; width: 850px;">
						<OL style="margin-left: 50px">
							<%
							    for (int i = 0; i < oAppendixes.size(); i++) {
							                Appendix oAppendix = (Appendix) oAppendixes.getAt(i);
							                if (oAppendix.getFlag() == Appendix.FLAG_LINK) {
												out.print("<li><a href='");
												out.print(oAppendix.getFile());
												out.print("'>");
												out.print(CMyString.showNull(oAppendix.getSrcFile(),oAppendix.getFile()));
												out.print("</a></li>");
							                    continue;
											}

							                String sFileName = oAppendix.getFile();
							                sFileName = CMyFile.extractFileName(sFileName);
							                String sSrcFilePathName = FilesMan.getFilesMan()
							                        .mapFilePath(sFileName, FilesMan.PATH_LOCAL)
							                        + sFileName;

							                if (!CMyFile.fileExists(sSrcFilePathName)) {
							                    // 文件不存在，作为警告信息发送给用户
							                    System.err.println("在磁盘上没有找到申请("
							                            + currApplyForm.getId() + ")的附件["
							                            + sFileName + "]");
							                    continue;
							                }

							                String sSrcFileName = oAppendix.getSrcFile();											
											String sFileParams = "FileName=" + sFileName;
											sFileParams += "&DownName=" + java.net.URLEncoder.encode(sSrcFileName,"UTF-8");
											sFileParams += "&query=" + SqgkFileUtil.makeQuery(currApplyForm.getId(), sFileName);
							                out.println("<li><a href='read_file.jsp?" + sFileParams + "'>" + sSrcFileName + "</a></li>");
							            }//END FOR
							%>
						</OL>
						</div>
						</td>
					</tr>

					<%
					    }// END IF oAppendixes IS EMPTY
					%>
				</table>
				</td>
			</tr>
		</table>
		</FORM>
		</td>
	</tr>
</table>
</div>
<div style="margin-top: 10px; text-align: center;">
<button onclick="window.close();">关闭</button>
</div>
<%
    }
%>
<div class="footer">
<div class="footerLine"></div>
<div>关于本站&nbsp;-&nbsp;使用帮助&nbsp;-&nbsp;联系我们</div>
<div>技术支持：北京拓尔思信息技术股份有限公司</div>
<div>Copyright&copy;2005-2012 All Rights Reserved 版权所有，未经授权，禁止转载</div>
<div>ICP备案编号：京ICP备010164号</div>
<div>建议使用IE5.5以上浏览器，分辨率1024*768</div>
</div>
</div>

</BODY>
</HTML>

<%!private String showNull(String sSrcStr, String sDefaultStr) {
        if (sSrcStr == null || "".equals(sSrcStr.trim())) {
            return sDefaultStr;
        }
        return sSrcStr;
    }%>