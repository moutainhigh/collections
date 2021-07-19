<%--
/** Title:			keyword_addedit_dowith.jsp
 *  Description:
 *		处理Keyword的添加修改页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			TRS WCM 5.2
 *  Created:		2009-06-23 13:44:49
 *  Vesion:			1.0
 *  Last EditTime:	2009-06-23 / 2009-06-23
 *	Update Logs:
 *		TRS WCM 5.2@2009-06-23 产生此文件
 *
 *  Parameters:
 *		see keyword_addedit_dowith.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.resource.Keyword" %>
<%@ page import="com.trs.cms.content.WCMObjHelper" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化(获取数据)
	int nKeywordId = currRequestHelper.getInt("KeywordId", 0);
	String sKName = currRequestHelper.getString("KName");
	Keyword currKeyword = null;
	if(nKeywordId > 0){
		currKeyword = Keyword.findById(nKeywordId);
		if(currKeyword == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,CMyString.format(LocaleServer.getString("addedit_dowith.obj.not.found", "没有找到指定ID为[{0}]的Keyword！"),new int[]{nKeywordId}));
		}
	}else{//nKeywordId==0 create a new group
		currKeyword = Keyword.createNewInstance();
	}
	
//5.权限校验
	if(nKeywordId > 0){
		if(!currKeyword.canEdit(loginUser)){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTLOCKED,CMyString.format(LocaleServer.getString("addedit_dowith.locked", "Keyword被用户[{0}]锁定！"),new String[]{currKeyword.getLockerUserName()}));
		}
	}

//6.业务代码
	try{
		//currKeyword = (Keyword)WCMObjHelper.toWCMObj(currRequestHelper.getString("ObjectXML"), loginUser, nKeywordId, Keyword.class);
		currKeyword.setKNAME(sKName);
	} catch(Exception ex){
		throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID, LocaleServer.getString("addedit_dowith.failed.for.wrongAttri","保存Keyword时因属性值不正确而失败中止！"), ex);
	}
	/**
		<span WCMAnt:param="keyword_addedit_dowith.jsp.reques">TODO 建议改为向Service发出请求</span>
	IKeywordService currKeywordService = (IKeywordService)DreamFactory.createObjectById("IKeywordService");
	currKeywordService.save(currKeyword);
		**/
	currKeyword.save(loginUser);

//7.结束
	out.clear();
%>