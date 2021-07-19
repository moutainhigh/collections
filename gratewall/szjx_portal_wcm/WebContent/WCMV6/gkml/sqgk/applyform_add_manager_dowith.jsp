<%--
/** Title:			applyform_addedit_dowith.jsp
 *  Description:
 *		处理ApplyForm的添加修改页面
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
 *		see applyform_addedit_dowith.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../../app/include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.gkml.sqgk.persistent.ApplyForm" %>
<%@ page import="com.trs.components.gkml.sqgk.persistent.ApplyFormDealLog" %>
<%@ page import="com.trs.components.gkml.sqgk.constant.ApplyFormConstants" %>
<%@ page import="com.trs.cms.content.WCMObjHelper" %>
<%@ page import="com.trs.infra.util.database.TableInfo" %>
<%@ page import="com.trs.infra.persistent.db.DBManager" %>
<%@ page import="com.trs.components.govinfo.GovInfoViewFinder" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="com.trs.infra.util.CMyBitsValue" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.infra.util.email.TRSMailer" %>
<%@ page import="com.trs.infra.util.email.CMyEmail" %>
<%@ page import="com.trs.infra.util.email.CMySMTPServer" %>
<%@ page import="com.trs.components.metadata.service.MailConfigsHelper" %>
<%@ page import="com.trs.components.metadata.center.MetaViewDatas" %>
<%@ page import="com.trs.components.metadata.center.MetaViewData" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Hashtable" %>
<%@ page import="com.trs.ajaxservice.WCMProcessServiceHelper" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.util.CMyException" %>

<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../../include/public_server.jsp"%>
<%--@include file="public_right_check.jsp"--%>
<%

	int nFlowDocId  = currRequestHelper.getInt("FlowDocId", 0);

	int nApplyFormId = currRequestHelper.getInt("ApplyFormId", 0);
	if(nApplyFormId == 0){
		throw new Exception("没有传入参数ApplyFormId");
	}

	ApplyForm currApplyForm = ApplyForm.findById(nApplyFormId);
	if(currApplyForm == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取ID为["+nApplyFormId+"]的ApplyForm失败！");
	}
	
	boolean hasRight = false;
	if(nFlowDocId > 0) {
		hasRight = WCMProcessServiceHelper.hasFlowingActionRight(loginUser, nFlowDocId, WCMRightTypes.DOC_EDIT);
	}else{
		hasRight = AuthServer.hasRight(loginUser, currApplyForm, WCMRightTypes.DOC_EDIT);
	}
	String sFlowDocPostXMLData = currRequestHelper.getString("FlowDocPostXMLData");
	boolean bFlowDocInFlow = !CMyString.isEmpty(sFlowDocPostXMLData);
	boolean bSkipDocSaving = currRequestHelper.getBoolean("SkipDocSaving", false);
	if(nApplyFormId>0 && !bSkipDocSaving){
		if(!hasRight){
			throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, "您没有权限修改ID为["+nApplyFormId+"]的依申请公开文档！");
		}

//		currApplyForm.setDealType(ApplyFormConstants.DEAL_TYPE_DEALED);
		currApplyForm.setProperty("DealTime", CMyDateTime.now());

		String sMailBody = CMyString.getStr(CMyString.showNull(request.getParameter("mailBody")));	
		currApplyForm.setMailBody(sMailBody);
		String sIndexCode = CMyString.getStr(CMyString.showNull(request.getParameter("indexCode")));	
		currApplyForm.setIndexCode(sIndexCode);
		String sDestDpt = CMyString.getStr(CMyString.showNull(request.getParameter("DestDpt")));	
		currApplyForm.setProperty("DestDpt", sDestDpt);
		
		currApplyForm.save(loginUser);

		//save the log.
		ApplyFormDealLog currApplyFormDealLog = ApplyFormDealLog.createNewInstance();
		currApplyFormDealLog.setApplyerName(currApplyForm.getApplyerName());
		currApplyFormDealLog.setApplyDesc(currApplyForm.getApplyDesc());
//		currApplyFormDealLog.setDealContent(sMailBody);
		currApplyFormDealLog.save(loginUser);

		if(!bFlowDocInFlow){
			//send the mail.
			Map mailConfigs = MailConfigsHelper.getConfigs();
			sendMail(currApplyForm, mailConfigs);
		}
	}
%>

<%!
    private void sendMail(ApplyForm currApplyForm, Map mailConfigs)throws Exception{
        try {
            // 构造邮件正文内容参数
            CMyEmail myEmail = new CMyEmail();
            myEmail.setFrom((String)mailConfigs.get("USERNAME"));
            myEmail.setTo(currApplyForm.getPropertyAsString("email"));

			Map properties = new Hashtable();
			properties.put("TO_USER", currApplyForm.getPropertyAsString("applyerName"));
			properties.put("FROM_USER", mailConfigs.get("USERNAME"));
			String sMailTitle = "";
			String sMailBody = currApplyForm.getMailBody();
			properties.put("CONTENT", sMailBody);

			MetaViewData metaViewData = getMetaViewData(currApplyForm);

			if(metaViewData != null){
				properties.putAll(metaViewData.getProperties());
				properties.put("CONTENT", sMailBody);

				String sMailTitleTemplate = (String)mailConfigs.get("MAILTITLE");
				sMailTitle = CMyString.parsePageVariables(sMailTitleTemplate, properties);
			}
			String sMailBodyTemplate = (String)mailConfigs.get("RESULTMAILBODY");
			sMailBody = CMyString.parsePageVariables(sMailBodyTemplate, properties);

			myEmail.setSubject(sMailTitle);

            try {
                myEmail.setBody(sMailBody);
            } catch (Exception e) {
                throw new Exception("构造邮件内容失败!", e);
            }
            myEmail.setMailFormat(CMyEmail.FORMAT_HTML);

            TRSMailer currMailer = new TRSMailer();

            if (!currMailer.send(myEmail, getCMySMTPServer(mailConfigs))) {
                throw new WCMException("邮件发送失败!"
                        + currMailer.getMailLogString());
            }

        } catch (Exception ex) {
             throw new CMyException("数据已经保存，但发送邮件出现异常,请检查邮件配置信息！",ex);
        }
    } // END of testSendMail

    private CMySMTPServer getCMySMTPServer(Map mailConfigs) {
        CMySMTPServer smtpServer = new CMySMTPServer();
        smtpServer.setAuth(true);

        smtpServer.setUserName((String)mailConfigs.get("USERNAME"));
        smtpServer.setPassword((String)mailConfigs.get("PASSWORD"));
        smtpServer.setServerName((String)mailConfigs.get("SERVERNAME"));
		String sPort = (String)mailConfigs.get("SERVERPORT");
        smtpServer.setServerPort(Integer.parseInt(sPort));

        return smtpServer;
    }

	private MetaViewData getMetaViewData(ApplyForm currApplyForm)throws Exception{
		int defaultViewId = 39;
		MetaView oMetaView = GovInfoViewFinder.findMetaViewOfGovInfo(null);
		if(oMetaView != null){
			defaultViewId = oMetaView.getId();
		}
		MetaView metaView = MetaView.findById(defaultViewId);
		if(metaView == null){
			return null;
		}else{
			MetaViewDatas metaViewDatas = new MetaViewDatas(metaView);
			WCMFilter filter = new WCMFilter("", "idxId=?", "");
			filter.addSearchValues(currApplyForm.getIndexCode());
			metaViewDatas.open(filter);
			
			for (int i = 0, nSize = metaViewDatas.size(); i < nSize; i++) {
				MetaViewData metaViewData = (MetaViewData) metaViewDatas.getAt(i);
				if (metaViewData == null)
					continue;
				return metaViewData;
			}
			return null;
		}
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title> new document </title>
<body>
<%
	if(bFlowDocInFlow) {
%>
	<script src="../../js/com.trs.util/Common.js"></script>
	<script label="TagParser">
		$import('com.trs.web2frame.DataHelper');
		$import('com.trs.wcm.Web2frameDefault');
	</script>
	<table id="tblProcessing" border="0" cellspacing="0" cellpadding="0" width="100%" height="100%">
		<tr>
			<td valign="middle" align="center">
				<div style="padding: 15px; border: 1px solid silver; width: 400px; background: aliceblue; font-family: arial">
					依申请公开已保存，正在处理依申请公开流转....
				</div>
			</td>
		</tr>
	</table>
	<%
		}// end of 判断是否需要处理文档流转
	%>
<script>
	<%
		if(bFlowDocInFlow){
	%>
		var m_sFlowDocPostXMLData = '<%=CMyString.filterForJs(sFlowDocPostXMLData)%>';
			var doc = com.trs.util.XML.loadXML(m_sFlowDocPostXMLData);
			var sUrl = BasicDataHelper['WebService'];
			var aOptions = {
				'postBody': doc,
				'method': 'post',
				'onSuccess': function(_trans, _json){
					if(window.opener && window.opener.CTRSAction_refreshMe){
						window.opener.CTRSAction_refreshMe();
					}
					if(window.opener && window.opener.PageContext && window.opener.PageContext.RefreshList){
						window.opener.PageContext.RefreshList();
					}
					window.close();
				},
				'on500': function(_trans, _json){
					Element.hide('tblProcessing');
					$render500Err(_trans, _json, false, function(){
						window.close();
					});
				}
			};
			var request = new com.trs.web2frame.AjaxRequest(sUrl, aOptions);
	<%
		}
		else{
	%>
			if(window.opener && window.opener.CTRSAction_refreshMe){
				window.opener.CTRSAction_refreshMe();
			}
			window.close();
	<%
		}
	%>
//-->
</script>
</body>
</html>