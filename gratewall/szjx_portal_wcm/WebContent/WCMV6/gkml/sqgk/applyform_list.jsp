<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	errorPage="error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.metadata.service.MailConfigsHelper"%>
<%@ page import="com.trs.components.gkml.sqgk.persistent.ApplyForm" %>
<%@ page import="com.trs.components.gkml.sqgk.persistent.ApplyForms" %>
<%@ page import="com.trs.infra.persistent.WCMFilter"%>
<%@ page import="com.trs.infra.util.CMyString"%>
<%@ page import="com.trs.infra.util.CPager"%>
<%@ page import="com.trs.presentation.util.PageHelper"%>
<%@ page import="com.trs.presentation.util.PageViewUtil"%>
<%@ page import="java.util.Map"%>
<%@ page import="com.trs.components.gkml.sqgk.constant.ApplyFormConstants" %>


<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../../include/public_server.jsp"%>
<%@include file="public_right_check.jsp"%>
<SCRIPT LANGUAGE="JavaScript" src="../../../js/CTRSHashtable.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../../../js/CTRSRequestParam.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../../../js/CTRSAction.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../../../js/CTRSHTMLTr.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../../../js/CTRSHTMLElement.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../../../js/CTRSButton.js"></SCRIPT>
<%=currRequestHelper.toTRSRequestParam()%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化（获取数据）
	String sOrderField	= CMyString.showNull(currRequestHelper.getOrderField());
	String sOrderType	= CMyString.showNull(currRequestHelper.getOrderType());

//5.权限校验

//6.业务代码
	String sSelectFields = "applyerType,applyerName,indexCode,dealTime,applyDesc,crtime,DealType,FlowStatusId";
	WCMFilter filter = new WCMFilter("", currRequestHelper.getWhereSQL(),
	currRequestHelper.getOrderSQL(), sSelectFields);

	int isDealed = currRequestHelper.getInt("isDealed", -1);
	
	if(isDealed != -1){
		String sWhere = filter.getWhere();
		if(CMyString.isEmpty(sWhere)){
			filter.setWhere("DealType=" + isDealed);
		}else{
			filter.setWhere(sWhere + " and DealType=" + isDealed);
		}
	}

	ApplyForms currApplyForms = ApplyForms.openWCMObjs(loginUser, filter);

	CPager currPager = new CPager(currRequestHelper.getInt("PageItemCount", 20));
	currPager.setItemCount( currApplyForms.size() );	
	currPager.setCurrentPageIndex( currRequestHelper.getInt("PageIndex", 1) );
	
	Map mailConfigs = MailConfigsHelper.getConfigs();
//7.结束
//	out.clear();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>政府公开目录---依申请公开</title>
<link id="indexStyle" href="css/list.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="../../js/com.trs.util/Common.js" type="text/javascript"></script>
<script language="javascript" src="applyform_list.js" type="text/javascript"></script>
</head>

<body>
<div class="container">
    <div class="header">
        <div class="headerLeft"></div>
        <div class="headerRight"></div>
    </div>
    <div class="footer">
        版权所有&copy; 2002-2012 北京拓尔思（TRS）信息技术股份有限公司
    </div>
    <div class="main">
        <div class="topNav">
            <div class="userInfo">
                当前在线：<%=loginUser.getName()%>&nbsp;&nbsp;|&nbsp;&nbsp;<span class="exitSystem" onclick="window.close();">关闭</span>
            </div>
            <div class="dateInfo" id="dateInfo"></div>
            <div class="tabContainer" id="tabContainer" onclick="changeTab()">
                <div key="listData" beforeCall="changeListType" class="tab <%=isDealed==-1 ? "activeTab" :""%>" isDealed="-1">
                    所有文档
                </div>
				<!--
                <div key="listData" beforeCall="changeListType" class="tab <%=isDealed==1 ? "activeTab" :""%>" isDealed="<%=ApplyFormConstants.DEAL_TYPE_DEALED%>">
                    已处理文档
                </div>
                <div key="listData" beforeCall="changeListType" class="tab <%=isDealed==0 ? "activeTab" :""%>" isDealed="<%=ApplyFormConstants.DEAL_TYPE_DEALING%>">
                    待处理文档
                </div>
				-->
                <div class="tab" key="mailConfig">
                    邮件配置
                </div>
            </div>
        </div>
        <div class="content" id="mailConfig_Area" style="display:none;">
            <div class="contentTop"></div>
            <div style="position:relative;width:100%;height:100%;padding:30px 0px 0px;border:1px solid #7C9DBE;background:white;">
                <div class="headInfo">配置邮件服务器</div>     
				<div style="height:100%;text-align:center;padding-top:50px;overflow-y:auto;">
					<table border=0 cellspacing=0 cellpadding=0 class="mailConfigForm" id="mailConfigForm">
						<tr style="height:30px;">
							<td>名称:</td>
							<td style="padding-left:15px;">
								<input type="text" name="serverName" value="<%=CMyString.showObjNull(mailConfigs.get("SERVERNAME"), "mail.trs.com.cn")%>" style="width:200px;">
							</td>
							<td>端口号:</td>
							<td style="padding-left:15px;">
								<input type="text" name="serverPort" value="<%=CMyString.showObjNull(mailConfigs.get("SERVERPORT"), "25")%>" style="width:200px;">
							</td>
						</tr>
						<tr style="height:30px;">
							<td>用户名:</td>
							<td style="padding-left:15px;">
								<input type="text" name="userName" value="<%=CMyString.showObjNull(mailConfigs.get("USERNAME"), "trs")%>" style="width:200px;">
							</td>
							<td>密码:</td>
							<td style="padding-left:15px;">
								<input type="password" name="passWord" value="<%=CMyString.showObjNull(mailConfigs.get("PASSWORD"), "trs")%>" style="width:200px;">
							</td>
						</tr>
						<tr style="height:30px;">
							<td>邮件标题:</td>
							<td colspan="3" style="padding-left:15px;">
								<input type="text" name="mailTitle" value="<%=CMyString.showObjNull(mailConfigs.get("MAILTITLE"), "")%>" style="width:494px;">
							</td>
						</tr>
						<tr style="height:30px;">
							<td>邮件正文:</td>
							<td colspan="3" style="padding-left:15px;">
								<textarea name="mailBody" rows="" cols="" style="width:494px;height:200px;"><%=CMyString.showObjNull(mailConfigs.get("RESULTMAILBODY"), "")%></textarea>
							</td>
						</tr>
						<tr style="height:40px;">
							<td colspan="4" style="vertical-align:bottom;text-align:center;">
								<button onclick="saveMailConfig();">保&nbsp;&nbsp;存&nbsp;&nbsp;配&nbsp;&nbsp;置</button>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
        <div class="content" id="listData_Area">
            <div class="contentTop"></div>
            <div style="position:relative;width:100%;height:100%;padding:90px 0px 0px;border:1px solid #7C9DBE;background:white;">
                <div class="headInfo"></div>             
                <div class="operatorContainer">
                    <div class="operator">
                        <span class="refreshButton" onclick="refreshApplyForm();">刷新</span>
                    </div>
                    <div class="operator" onclick="deleteApplyForm(getSelectedIds());">
                        <span class="deleteButton">删除</span>
                    </div>
					<form class="frmSearch" name="frmSearch" onsubmit="CTRSAction_doSearch(this);return false;">
						<input class="queryTxt" type="text" name="SearchValue" size=10 value="<%=PageViewUtil.toHtmlValue(currRequestHelper.getSearchValue())%>">
						<select name="SearchKey" id="SearchKey">
							<option value="applyerName,indexCode">全部</option>							
							<option value="applyerName">申请人</option>							
							<option value="indexCode">索引号</option>							
						</select> 
						<input type="image" src="images/search.gif">
						<input type="hidden" name="isDealed" id="isDealed" value="<%=isDealed%>">
						<script>
							document.frmSearch.SearchKey.value = "<%=CMyString.filterForJs(currRequestHelper.getSearchKey())%>";
						</script>
					</form>
                </div>   
                <div class="listHead">
                    <div class="row">
                        <li style="float:left;width:40px;" onclick="toggleAll();">全选</li>
                        <li style="float:left;width:120px;overflow:hidden;">
							<%=PageViewUtil.getHeadTitle("applyerType", "申请人", sOrderField, sOrderType)%>
						</li>
                        <li style="float:left;width:120px;">类型</li>
                        <li style="float:left;width:150px;overflow:hidden;">
							<%=PageViewUtil.getHeadTitle("indexCode", "索引号", sOrderField, sOrderType)%>
						</li>
						<%if(isDealed != ApplyFormConstants.DEAL_TYPE_DEALING){%>
                        <li style="float:right;width:120px;">
							<%=PageViewUtil.getHeadTitle("dealTime", "处理时间", sOrderField, sOrderType)%>
						</li>
						<%}%>
                        <li style="float:right;width:120px;">
							<%=PageViewUtil.getHeadTitle("crTime", "申请时间", sOrderField, sOrderType)%>
						</li>
						<%if(isDealed == -1){%>
	                    <li style="float:right;width:120px;">处理状态</li>
						<%}%>
                        <li class="autoColumn">内容</li>
                    </div>
                </div>
                <div class="listData" id="listData">
		<%
			ApplyForm currApplyForm = null;
			for(int i=currPager.getFirstItemIndex(); i<=currPager.getLastItemIndex(); i++)
			{//begin for
				try{
					currApplyForm = (ApplyForm)currApplyForms.getAt(i-1);
				} catch(Exception ex){
					throw new WCMException("获取第["+i+"]篇申请信息失败！", ex);
				}
				if(currApplyForm == null){
					throw new WCMException("没有找到第["+i+"]篇申请信息！");
				}

				try{
		%>
                    <div class="row" id="row_<%=currApplyForm.getId()%>" + >
                        <li style="float:left;width:40px;">
							<INPUT title="<%=currApplyForm.getId()%>" TYPE="checkbox" NAME="ApplyFormIds" VALUE="<%=currApplyForm.getId()%>"><%=i%>
						</li>
                        <li style="float:left;width:120px;overflow:hidden;"><%=currApplyForm.getApplyerName()%></li>
                        <li style="float:left;width:120px;"><%=currApplyForm.getApplyerType()==ApplyFormConstants.APPLYER_TYPE_PERSON?"公民":"法人/其他组织"%></li>
                        <li style="float:left;width:150px;overflow:hidden;"><%=currApplyForm.getIndexCode()%></li>
						<%if(isDealed != ApplyFormConstants.DEAL_TYPE_DEALING){%>
                        <li style="float:right;width:120px;"><%=currApplyForm.getPropertyAsString("dealTime", "")%></li>
						<%}%>
                        <li class="lastColumn" style="float:right;width:120px;"><%=currApplyForm.getCrTime()%></li>
						<%if(isDealed == -1){%>
	                    <li style="float:right;width:120px;">
							<%

								String sDealType = currApplyForm.getStatusName();/*null;
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
								out.print(sDealType);
							%>
						</li>							
						<%}%>
                        <li class="autoColumn">
							<a target="_blank" href="applyform_add_manager.jsp?ApplyFormId=<%=currApplyForm.getId()%>"><%=currApplyForm.getApplyDesc()%></a>						
						</li>
                    </div>
		<%
				} catch(Exception ex){
					throw new WCMException("获取第["+i+"]篇申请信息的属性失败！", ex);
				}
			}//end for	
		%>
               </div>
			   <div class="listNavigator">
					<%=PageHelper.PagerHtmlGenerator(currPager, currRequestHelper.getInt("PageItemCount", 20), "文档", "篇")%>
			   </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>