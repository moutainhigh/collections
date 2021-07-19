<%--
/** Title:			logo_delete.jsp
 *  Description:
 *		删除Logo的页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			TRS WCM 5.2
 *  Created:		2006-03-28 19:56:25
 *  Vesion:			1.0
 *  Last EditTime:	2006-03-28 / 2006-03-28
 *	Update Logs:
 *		TRS WCM 5.2@2006-03-28 产生此文件
 *
 *  Parameters:
 *		see logo_delete.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.wcm.trsserver.task.persistent.TRSServer" %>
<%@ page import="com.trs.infra.persistent.db.DBManager" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.database.DBTypes" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
	// 1 权限校验
	if(!loginUser.isAdministrator()){
		throw new WCMException("您没有权限操作检索配置！");
	}
//4.初始化(获取数据)
	String sServer = currRequestHelper.getString("TargetServer");
	int nPort = currRequestHelper.getInt("TargetPort",0);
	String sLoginUser = currRequestHelper.getString("LoginUser");
	String sLoginPassword = currRequestHelper.getString("LoginPassword");
	String sName = currRequestHelper.getString("sName");
	String sTableName = currRequestHelper.getString("LoginTableName");
//5.权限校验

	addFieldIfNeed();

//6.业务代码
	TRSServer server = TRSServer.createNewInstance();
	server.setName(sName);
	server.setIP(sServer);
	server.setPort(nPort);
	server.setUserName(sLoginUser);
	server.setPassword(sLoginPassword);
	server.setTableName(sTableName);
	server.save(loginUser);
//7.结束
	WCMFilter filter = new WCMFilter("XWCMTRSSERVER","ip = ? and sport = ? and tablename = ?","","trsserverid");
	filter.addSearchValues(0,sServer);
	filter.addSearchValues(1,nPort);
	filter.addSearchValues(2,sTableName);
	int nServerId = DBManager.getDBManager().sqlExecuteIntQuery(filter);
	out.clear();
	out.println(nServerId);
%>

<%!
	//weyh@2012-06-08 升级环境中少了TABLENAME字段，为了方便使用。在此做一个判断。
	private final static void addFieldIfNeed() throws WCMException {
		DBManager dbman = DBManager.getDBManager();
		if(dbman.getFieldInfo("XWCMTRSSERVER","TABLENAME") != null) return; //field exists.
		
		int nDbType = dbman.getDBTypeAsInt();
		String sTypeName = "nvarchar";
		switch(nDbType) {
			case DBTypes.ORACLE :
				sTypeName = "varchar2";
				break;
			case DBTypes.DB2UDB:
			case DBTypes.DB2UDB2:
			case DBTypes.DB2AS400:
				sTypeName = "varchar";
				break;
			default:break;
		}
		String sql = "alter table XWCMTRSSERVER add TABLENAME " + sTypeName +"(60) null";
		dbman.sqlExecuteUpdate(sql);
		dbman.reloadTableInfo("XWCMTRSSERVER");
	}
%>