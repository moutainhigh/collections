<%--
/** Title:			keyword_delete.jsp
 *  Description:
 *		删除Keyword的页面
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
 *		see keyword_delete.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_for_dialog.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.resource.Keyword" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化(获取数据)
	int nKeywordId = currRequestHelper.getInt("KeywordId",0);

//5.权限校验

//6.业务代码
	/** TODO 建议改为向Service发出请求
	IKeywordService currKeywordService = (IKeywordService)DreamFactory.createObjectById("IKeywordService");
	currKeywordService.delete(sKeywordIds);
	**/
	Keyword currKeyword = Keyword.findById(nKeywordId);
	if(currKeyword==null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("object.not.found_a", "没有找到指定ID为[{0}]的对象!"), new int[]{nKeywordId}));
	}
	currKeyword.delete();
	

//7.结束
	out.clear();
%>