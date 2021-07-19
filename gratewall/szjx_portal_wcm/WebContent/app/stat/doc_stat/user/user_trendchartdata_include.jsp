<%@ page import="com.trs.components.stat.StatAnalysisTool" %>
<%@ page import="com.trs.components.stat.IStatResults" %>
<%@ page import="com.trs.components.stat.IStatResultsFilter" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.cms.CMSConstants" %>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDocs" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.cms.auth.persistent.User" %>
<%@ page import="com.trs.cms.auth.persistent.Groups" %>
<%@ page import="com.trs.cms.auth.persistent.Group" %>
<%@ page import="com.trs.infra.persistent.db.DBManager"%>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@include file="../../../../include/public_server.jsp"%>
<%@include file="../../include/static_common.jsp"%>
<%
	// 获取页面传入的检索参数(检索项/值,检索时间范围)
	String sSelectItem = currRequestHelper.getString("SelectItem");
	String sSearchValue = currRequestHelper.getString("SearchValue");

	String[] arrTime = getDateTimeFromParame(currRequestHelper);
	String sStartTime = arrTime[0];
    String sEndTime = arrTime[1];

	//确定具体检索项
	boolean bSearchUser = false, bSearchDept = false;
	if(!CMyString.isEmpty(sSearchValue)){
		if("UserName".equalsIgnoreCase(sSelectItem)){
			bSearchUser = true;
		}
		if("GName".equalsIgnoreCase(sSelectItem)){
			bSearchDept = true;
		}
	}

	// 构造统计的SQL语句
	String[] pStatSQL = new String[] {
		// 总的发稿量
		"Select count(*) DataCount, CrUser from WCMChnlDoc "
				// 统计指定时间段的数据
				+ " Where CrTime>= ${StartTime}"
				+ " And CrTime<= ${EndTime}"
				+ " and DocStatus>0 and ChnlId>0"
				+ " and DocStatus != " + Status.STATUS_ID_DRAFT
				//排除被删除文档的引用
				+ " and Modal>0"
				+ " Group by CrUser",

		// 按照状态统计
		"Select count(*) DataCount, CrUser, DocStatus from WCMChnlDoc "
				// 统计指定时间段的数据
				+ " Where CrTime>=${StartTime}"
				+ " And CrTime<=${EndTime}"
				+ " and DocStatus>0 and ChnlId>0"
				+ " and DocStatus != " + Status.STATUS_ID_DRAFT
				//排除被删除文档的引用
				+ " and Modal>0"
				+ " Group by CrUser, DocStatus",

		// 按照创作方式(实体/引用)统计
		"Select count(*) DataCount, CrUser, abs(Modal) from WCMChnlDoc "
				// 统计指定时间段的数据
				+ " Where CrTime>=${StartTime}"
				+ " And CrTime<=${EndTime}"
				+ " and DocStatus>0 and ChnlId>0"
				+ " and DocStatus != " + Status.STATUS_ID_DRAFT
				//排除被删除文档的引用
				+ " and Modal>0"
				+ " Group by CrUser, Modal",

		// 对复制型创作方式的统计
		"Select count(*) DataCount, CrUser from WCMChnlDoc "
				// 统计指定时间段的数据
				+ " Where CrTime>=${StartTime}"
				+ " And CrTime<=${EndTime}"
				+ " And DocOutUpId > 0"
				+ " and DocStatus>0 and ChnlId>0"
				+ " and DocStatus != " + Status.STATUS_ID_DRAFT
				//排除被删除文档的引用
				+ " and Modal>0"
				+ " Group by CrUser",

		// 按照文档所属类型统计
		"Select count(*) DataCount, CrUser, DocForm from WCMChnlDoc "
				// 统计指定时间段的数据
				+ " Where CrTime>=${StartTime}"
				+ " And CrTime<=${EndTime}"
				+ " and DocStatus>0 and ChnlId>0"
				+ " and DocStatus != " + Status.STATUS_ID_DRAFT
				//排除被删除文档的引用
				+ " and Modal>0"
				+ " Group by CrUser, DocForm"
		};

	// 传入时间步长单位
	int nTimeStep = currRequestHelper.getInt("TimeStep",1); 

	//调用统计类进行统计
	StatAnalysisTool tool = new StatAnalysisTool(pStatSQL);
    IStatResults result = tool.stat(sStartTime, sEndTime, nTimeStep);

	if(bSearchUser || bSearchDept){
		//构造各种检索条件Filter
		String sWhere = formatTimeWhere("CrTime>= ${StartTime} And CrTime<= ${EndTime}", new String[]{sStartTime, sEndTime});
		WCMFilter filter = new WCMFilter("WCMCHNLDOC", "", "", "distinct(CrUser)");
		if(bSearchUser){
			sWhere += " And CrUser like ? ";
			filter.addSearchValues("%" + sSearchValue + "%");
		}
		if(bSearchDept){
			sWhere += " And CrUser in( select userName from wcmuser where userid in ("
					+ "SELECT userid FROM wcmGrpUser where groupid in("
					+ "select groupid from WCMGroup where GName like ?))) ";
			filter.addSearchValues("%" + sSearchValue + "%");
		}
		filter.setWhere(sWhere);
		if(logger.isDebugEnabled()){
			logger.info(LocaleServer.getString("user_trendchartdata_include.start","开始执行用户或部门的检索统计查询..."));
			logger.info(filter);
			timer.start();
		}
		final String[] aCrUsers = DBManager.getDBManager().sqlExecuteStringsQuery(filter);
		if(logger.isDebugEnabled()){
			timer.stop();
			logger.info("query use[" + timer.getTime() + "]ms");
			timer.start();
		}

		// 按照前面构造的条件,对集合进行过滤
		result = result.filter(new IStatResultsFilter() {
			public boolean accept(String sKey, IStatResults statResult) {
				String sUserName = sKey;
				for(int i=0,nSize=aCrUsers.length; i<nSize; i++){
					String CrUser = aCrUsers[i];
					if(CrUser == null) continue;
					if(sUserName.equals(CrUser)){
						return true;
					}
				}
				return false;
			}
		});	
		if(logger.isDebugEnabled()){
			timer.stop();
			logger.info("filter data use[" + timer.getTime() + "]ms");
		}
	}

	// 获取主体，做分页需要以这个主体做为总数
	List mainObjList = result.list();
	int nNum = 0;
	if(mainObjList != null && (mainObjList.size() > 0)){
		nNum= mainObjList.size();
	}

	//构造分页参数
	int nPageSize = currRequestHelper.getInt("PageSize",20);
	int nCurrPage = currRequestHelper.getInt("CurrPage",1);
	CPager currPager = new CPager(nPageSize);
	currPager.setCurrentPageIndex(nCurrPage);
	currPager.setItemCount(nNum);
	
	// 设置分页信息到 header
	response.setHeader("CurrPage",String.valueOf(nCurrPage));
	response.setHeader("PageSize",String.valueOf(nPageSize));
	response.setHeader("Num",String.valueOf(nNum));
	//7.结束
	out.clear();
%>