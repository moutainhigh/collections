<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.components.stat.IStatResult"%>
<%@ page import="com.trs.components.stat.StatAnalysisTool"%>
<%@ page import="com.trs.components.stat.HitsCountHelper"%>
<%@ page import="com.trs.components.stat.IStatResultFilter"%>
<%@ page import="com.trs.components.stat.IChnlDeptMgr"%>
<%@ page import="com.trs.DreamFactory"%>
<%@ page import="com.trs.infra.persistent.db.DBManager"%>
 
 <%@ page import="com.trs.infra.persistent.IdFilterSqlUtil" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.components.stat.DocumentHitsCounts" %>
<%@ page import="com.trs.components.stat.DocumentHitsCount" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.cms.auth.domain.GroupMgr" %>
<%@ page import="com.trs.cms.auth.persistent.User" %>
<%@ page import="com.trs.cms.auth.persistent.Groups" %>
<%@ page import="com.trs.cms.auth.persistent.Group" %>
<%@ page import="com.trs.infra.util.CMyString"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.trs.infra.support.config.ConfigServer"%>

<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@include file="../../../include/public_server.jsp"%>
<%@include file="../../include/static_common.jsp"%>
<%		
	//构造创建时间的过滤条件
	String sDocCrtimeWhere = null;
	int nCrTimeItem = currRequestHelper.getInt("TimeItem", 0);

	if(nCrTimeItem != 0){
		sDocCrtimeWhere = "DOCCRTIME>=${STARTTIME} AND DOCCRTIME<=${ENDTIME}";
		sDocCrtimeWhere = formatTimeWhere(sDocCrtimeWhere, getDateTimeFromParame(currRequestHelper));
	}

	//1 构造sql
	String[] pDocHitsTimeStatSQL = new String[]{
		"Select sum(HITSCOUNT) TOTALCOUNT,DOCID from XWCMDOCUMENTHITSCOUNT "
					// 统计指定点击时间段的数据
					+ " Where HITSTIME>=${STARTTIME}"
					+ " And HITSTIME<=${ENDTIME}"
					+ (sDocCrtimeWhere == null ? "" : " And " + sDocCrtimeWhere)
					+ " Group by DOCID"
					+ " Order by TOTALCOUNT Desc"
		};

	//2 获取基本的时间类型参数

	//创建时间的统计方式
	//点击量时间的统计方式
	int nHitsTimeItem = currRequestHelper.getInt("HitsTimeItem", 0);

	//3 构造点击的时间段
	String[] sHitsTimeRands = new String[2];
	if(nHitsTimeItem == 0){
		sHitsTimeRands[0] = "2000-01-01 00:00:00";
		sHitsTimeRands[1] = CMyDateTime.now().toString();
	}else{
		sHitsTimeRands = HitsCountHelper.makeQueryHitsTimeRands(nHitsTimeItem);
	}

	//4 获取指定点击时间段内的统计数据
	StatAnalysisTool tool = new StatAnalysisTool(pDocHitsTimeStatSQL);
    IStatResult result = tool.stat(sHitsTimeRands[0], sHitsTimeRands[1]);

	//5 根据检索条件获取当前主体对象集合 需要兼容下子页面传入组织id和用户id的情况
	String sSearchKey = CMyString.showNull(currRequestHelper.getString("SelectItem"));
	String sSearchValue = CMyString.showNull(currRequestHelper.getString("SearchValue"));
	String sTitleInfo = "";
	boolean bShowReturnButton = false;
	WCMFilter filter = new WCMFilter("XWCMDOCUMENTHITSCOUNT","","","distinct docid");
	String sWhere = "";
	int nGroupId = currRequestHelper.getInt("GroupId", 0);
	int nCrUserId = currRequestHelper.getInt("UserId", 0);
	String sWhereGroup = "";
	String sWhereUser = "";
	//5.1 构造传递单个组织id的情况
	if(nGroupId > 0){
		Group oCurrGroup = Group.findById(nGroupId);
		// modify by ffx @2012-06-21需要包含子组的数据
		GroupMgr oGroupMgr = (GroupMgr) DreamFactory.createObjectById("GroupMgr");
		String sGroupIds = oGroupMgr.getOffspringGroupIds(Integer.toString(nGroupId), false);
		String sGroupIdSQL = " groupid in(?) ";
		if(!CMyString.isEmpty(sGroupIds)){
			sGroupIdSQL += " or " +IdFilterSqlUtil.makeAsString("GroupId", sGroupIds);
		}
		if(oCurrGroup != null)
			sTitleInfo = LocaleServer.getString("hitscount_data_include.department","部门")+"【<span class='detail-hostname'>" + oCurrGroup.getName() + "</span>】";
		sWhereGroup = "exists(select 1 from wcmuser where wcmuser.username = XWCMDOCUMENTHITSCOUNT.DocCrUser And userid in(SELECT userid FROM wcmGrpUser where "+sGroupIdSQL+" ))";
		bShowReturnButton = true;
	}
	//5.2 构造传递单个用户id的情况
	if(nCrUserId > 0){
		User oCurrUser = User.findById(nCrUserId);
		if(oCurrUser != null)
			sTitleInfo = LocaleServer.getString("hitscount_data_include.user","用户")+"【<span class='detail-hostname'>" + oCurrUser.getName() + "</span>】";
		sWhereUser = "exists(select 1 from wcmuser where wcmuser.username = XWCMDOCUMENTHITSCOUNT.DocCrUser And userid in(?))";
		bShowReturnButton = true;
	}
	//5.3 构造界面中的检索字段
	if(!CMyString.isEmpty(sSearchValue)){
		if("DocTitle".equals(sSearchKey)){
			sWhere = "DOCTitle like ?";
			filter.addSearchValues("%" + sSearchValue + "%");
			if(nCrUserId > 0){
				sWhere+= " And " + sWhereUser;
				filter.addSearchValues(nCrUserId);
			}else if(nGroupId > 0){
				sWhere+=" And " + sWhereGroup;
				filter.addSearchValues(nGroupId);
			}
		}else if("DocCrUser".equals(sSearchKey) && nCrUserId == 0){
			sWhere = "DocCrUser like ?";
			filter.addSearchValues("%" + sSearchValue + "%");
			if(nGroupId > 0){
				sWhere+=" And " + sWhereGroup;
				filter.addSearchValues(nGroupId);
			}
		}else {
			if(nGroupId > 0){
				sWhere = sWhereGroup;
				filter.addSearchValues(nGroupId);
			}else{
				sWhere = "exists(select 1 from wcmuser where wcmuser.username = XWCMDOCUMENTHITSCOUNT.DocCrUser And userid in(SELECT userid FROM wcmGrpUser where groupid in(select groupid from WCMGroup where GName like ?)))";
				filter.addSearchValues("%" + sSearchValue + "%");
			}
		}
	}else{
		if(nGroupId > 0){
			sWhere = sWhereGroup;
			filter.addSearchValues(nGroupId);
		}else if(nCrUserId > 0){
			sWhere = sWhereUser;
			filter.addSearchValues(nCrUserId);
		}
	}
	//5.5 开检索的集合
	boolean bSearch = false;
	int[] docIds = new int[0];//null;
	if(!CMyString.isEmpty(sWhere)){
		filter.setWhere(sWhere);
		docIds = DBManager.getDBManager().sqlExecuteIntsQuery(filter);
		bSearch = true;
	}
	final int[] hitsDocIds = docIds;

	//6 按照前面构造的条件,对统计结果集合进行过滤
	if(bSearch){
		result = result.filter(new IStatResultFilter() {
			public boolean accept(String sKey, IStatResult statResult) {
				int nDocId = Integer.parseInt(sKey);
				boolean bContains = false;
				for(int i=0; i<hitsDocIds.length;i++){
					int hitDocId = hitsDocIds[i];
					if(hitDocId == nDocId){
						bContains = true;
						break;
					}	
				}
				return bContains;
			}
		});
	}

	//7 排序
    List arDocIds = result.list();//result.sort(true);

	//8 设置分页信息
	int nNum = 0;
	if(arDocIds != null && (arDocIds.size() > 0)){
		nNum= arDocIds.size();
	}
	//构造分页参数
	int nPageSize = currRequestHelper.getInt("PageSize",20);
	int nPageIndex = currRequestHelper.getInt("CurrPage",1);
	
	CPager currPager = new CPager(nPageSize);
	currPager.setCurrentPageIndex(nPageIndex);
	currPager.setItemCount(nNum);
	
	// 设置分页信息到 header
	response.setHeader("CurrPage",String.valueOf(nPageIndex));
	response.setHeader("PageSize",String.valueOf(nPageSize));
	response.setHeader("Num",String.valueOf(nNum));
	//7.结束
	out.clear();
%>