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

<%@ page contentType="text/xml;charset=UTF-8" pageEncoding="utf-8"
	errorPage="../../../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.metadata.service.MailConfigsHelper"%>
<%@ page import="com.trs.components.gkml.sqgk.persistent.ApplyForm" %>
<%@ page import="com.trs.components.gkml.sqgk.persistent.ApplyForms" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.components.gkml.sqgk.constant.ApplyFormConstants" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.components.govinfo.GovInfoViewFinder" %>
<%@ page import="com.trs.components.metadata.center.MetaViewDatas" %>
<%@ page import="com.trs.components.metadata.center.MetaViewData" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>

<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
	int defaultViewId = 39;
	//获取政府信息公开视图的Id
	MetaView oMetaView = GovInfoViewFinder.findMetaViewOfGovInfo(loginUser);
	if(oMetaView != null){
		defaultViewId = oMetaView.getId();
	}

//4.初始化(获取数据)
	int nApplyFormId = currRequestHelper.getInt("ApplyFormId", 0);
	if(nApplyFormId == 0){
		throw new Exception("没有传入参数ApplyFormId");
	}

	ApplyForm currApplyForm = ApplyForm.findById(nApplyFormId);
	if(currApplyForm == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取ID为["+nApplyFormId+"]的ApplyForm失败！");
	}
//5.权限校验

//6.业务代码
	
//7.结束
	out.clear();

	//get the mail content.
	Map result = new HashMap();
	String sIndexCode = currApplyForm.getPropertyAsString("indexCode");

	if(sIndexCode == null || "".equals(sIndexCode.trim())){
		setDefaultMap(result);
	}else{
        MetaView metaView = MetaView.findById(defaultViewId);
		if(metaView == null){
			setDefaultMap(result);
		}else{
			MetaViewDatas metaViewDatas = new MetaViewDatas(metaView);
			WCMFilter filter = new WCMFilter("", "idxId=?", "");
			filter.addSearchValues(sIndexCode);
			metaViewDatas.open(filter);
			
			boolean isFound = false;
			for (int i = 0, nSize = metaViewDatas.size(); i < nSize; i++) {
				MetaViewData metaViewData = (MetaViewData) metaViewDatas.getAt(i);
				if (metaViewData == null)
					continue;
/*
				Map mailConfigs = MailConfigsHelper.getConfigs();

				String sMailTitleTemplate = (String)mailConfigs.get("MAILTITLE");
				String sMailTitle = CMyString.parsePageVariables(sMailTitleTemplate, metaViewData.getProperties());
				result.put("mailTitle", sMailTitle);

				String sMailBodyTemplate = (String)mailConfigs.get("MAILBODY");
				String sMailBody = CMyString.parsePageVariables(sMailBodyTemplate, metaViewData.getProperties());
				result.put("mailBody", sMailBody);
*/
				result.put("mailBody", metaViewData.getPropertyAsString("content"));

				isFound = true;
				break;
			}

			if(!isFound){
				setDefaultMap(result);
			}
		}
	}
%>

<Datas>
	<mailBody><![CDATA[<%=result.get("mailBody")%>]]></mailBody>
</Datas>

<%!
	private void setDefaultMap(Map result){
		result.put("mailTitle", "");
		result.put("mailBody", "");
	}
%>