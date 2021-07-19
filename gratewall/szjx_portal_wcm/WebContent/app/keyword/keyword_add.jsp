<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_for_dialog.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.resource.Keyword" %>
<%@ page import="com.trs.cms.content.WCMObjHelper" %>
<%@ page import="com.trs.infra.persistent.WCMFilter"%>
<%@ page import="com.trs.infra.persistent.db.DBManager"%>
<%@ page import="com.trs.infra.util.CMyString"%>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
	response.addHeader("Cache-Control","no-store"); //Firefox
	response.setHeader("Pragma","no-cache"); //HTTP 1.0
	response.setDateHeader ("Expires", -1);
	response.setDateHeader("max-age", 0);
%>
<%
//4.初始化(获取数据)
	String sKName = currRequestHelper.getString("KName");
	int nSiteId = currRequestHelper.getInt("siteId",0);
	int nSiteType = currRequestHelper.getInt("siteType",0);
	Keyword currKeyword = null;
	currKeyword = Keyword.createNewInstance();
	
//5.权限校验

//6.业务代码
	try{
		//保存时先判断是否KName名称重名
		WCMFilter existFilter = new WCMFilter("XWCMKEYWORD", "siteId in(0,?) and KName=? and siteType=?", "", "keywordId");
		existFilter.addSearchValues(nSiteId);
		existFilter.addSearchValues(sKName);
		existFilter.addSearchValues(nSiteType);
		boolean bexists = (DBManager.getDBManager().sqlExecuteIntQuery(existFilter) > 0)?true:false;
		if(bexists){
			out.println("保存关键词时,因关键词[" + sKName + "]发生重名而失败中止！");
			return;
		}
		currKeyword.setKNAME(sKName);
		currKeyword.setSITEID(nSiteId);
		currKeyword.setSITETYPE(nSiteType);
	} catch(Exception ex){
		throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID,LocaleServer.getString("keyword_add.attribute.wrong", "保存关键词时,因关键词[" + sKName + "]不正确而失败中止！"), ex);
	}
	/**
		<span WCMAnt:param="keyword_add.jsp.reques">TODO 建议改为向Service发出请求</span>
	IKeywordService currKeywordService = (IKeywordService)DreamFactory.createObjectById("IKeywordService");
	currKeywordService.save(currKeyword);
		**/
	currKeyword.save(loginUser);

//7.结束
	out.clear();
%>