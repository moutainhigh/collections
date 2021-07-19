<%@ page import="com.trs.components.stat.StatDataCountForUser"%>
<%@ page import="com.trs.components.stat.StatDataResultForUser"%>
<%@ page import="com.trs.infra.util.CMyString"%>
<%@ page import="com.trs.components.wcm.content.persistent.Document"%>
<%@ page import="com.trs.infra.util.CMyDateTime"%>
<%@include file="../../include/public_server.jsp"%>
<%@include file="../include/static_common.jsp"%>
<%
	// 构造统计的SQL语句
	String[] pStatSQL = new String[] {
		// 获取某一段时间创建的不同文档类型的总数
		"Select count(*) DataCount, DocForm from WCMChnlDoc "
				// 统计指定时间段的数据
				+ " Where CrTime>= ?"
				+ " And CrTime<= ?"
				+ " and DocStatus>0 and CHNLID>0"
				+ " and DocStatus != " + Status.STATUS_ID_DRAFT
				//排除被删除文档的引用
				+ " and Modal>0"
				+ " Group by DocForm",
		// 获取一段时间内不同文档状态的总数
		"Select count(*) DataCount, DocStatus from WCMChnlDoc "
				// 统计指定时间段的数据
				+ " Where CrTime>= ?"
				+ " And CrTime<= ?"
				+ " and DocStatus>0 and CHNLID>0"
				+ " and DocStatus != " + Status.STATUS_ID_DRAFT
				//排除被删除文档的引用
				+ " and Modal>0"
				+ " Group by DocStatus"
		};
	//对应的参数列表
	String[] arrTime=getDateTimeFromParame(currRequestHelper);
	String[][] pStatParam = new String[][] {
		{arrTime[0],arrTime[1]},
		{arrTime[0],arrTime[1]}
	};
	StatDataCountForUser stater = new StatDataCountForUser(pStatSQL);
	StatDataResultForUser result = stater.stat(pStatParam);
%>