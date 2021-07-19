<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.components.stat.StatAnalysisTool"%>
<%@ page import="com.trs.components.stat.IStatResult"%>
<%@ page import="com.trs.components.stat.IStatResultFilter"%>
<%@ page import="com.trs.components.stat.HitsStatHandler4User"%>
<%@ page import="com.trs.DreamFactory"%>
<%@ page import="com.trs.infra.persistent.db.DBManager"%>
 <%@ page import="com.trs.infra.persistent.IdFilterSqlUtil" %>

<%@ page import="com.trs.components.stat.HitsCountHelper"%>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.cms.auth.domain.GroupMgr" %>
<%@ page import="com.trs.cms.auth.persistent.User" %>
<%@ page import="com.trs.cms.auth.persistent.Users" %>
<%@ page import="com.trs.cms.auth.persistent.Groups" %>
<%@ page import="com.trs.cms.auth.persistent.Group" %>
<%@ page import="com.trs.components.wcm.content.persistent.Documents" %>
<%@ page import="com.trs.infra.util.CMyString"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>

<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@include file="../../../../include/public_server.jsp"%>
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
    // 根据文档的点击时间段来统计的sql
    String[] pSql = new String[] {
			"Select sum(HITSCOUNT) TOTALCOUNT,DOCCRUSER from XWCMDOCUMENTHITSCOUNT "
                    + " Where HITSTIME>=${STARTTIME}"
                    + " And HITSTIME<=${ENDTIME}"
					+ (sDocCrtimeWhere == null ? "" : " And " + sDocCrtimeWhere)
                    + " Group by DOCCRUSER " 
					+ " Order by TotalCount desc"
	};	
		
	//点击量时间的统计方式
	int nHitsTimeItem = currRequestHelper.getInt("HitsTimeItem", 0);

	//4 构造时间段
	String[] sHitsTimeRands = new String[2];
	if(nHitsTimeItem == 0){
		sHitsTimeRands[0] = "2000-01-01 00:00:00";
		sHitsTimeRands[1] = CMyDateTime.now().toString();
	}else{
		sHitsTimeRands = HitsCountHelper.makeQueryHitsTimeRands(nHitsTimeItem);
	}
	
	//5 获取所有的统计数据
	StatAnalysisTool tool = new StatAnalysisTool(pSql);
    IStatResult result = tool.stat(sHitsTimeRands[0], sHitsTimeRands[1]);

	//6 根据检索条件获取当前主体对象集合
	String sSearchKey = CMyString.showNull(currRequestHelper.getString("SelectItem"));
	String sSearchValue = CMyString.showNull(currRequestHelper.getString("SearchValue"));
	int nGroupId = currRequestHelper.getInt("GroupId", 0);
	
	boolean bSearchUserName = false;
	boolean bSearchGroupId = false;
	String sCurrGroupName = "";
	// modify by ffx @2012-06-21需要包含子组的数据
	String sGroupIdSQL = " groupid in(?) ";
	if(nGroupId > 0){
		bSearchGroupId = true;
		Group oCurrGroup = Group.findById(nGroupId);
		sCurrGroupName = oCurrGroup.getName();
		GroupMgr oGroupMgr = (GroupMgr) DreamFactory.createObjectById("GroupMgr");
		String sGroupIds = oGroupMgr.getOffspringGroupIds(Integer.toString(nGroupId), false);
		if(!CMyString.isEmpty(sGroupIds)){
			sGroupIdSQL += " or " +IdFilterSqlUtil.makeAsString("GroupId", sGroupIds);
		}
	}
	Users oUsers = new Users(loginUser);
	String sWhere = "";
	WCMFilter filter = new WCMFilter("","","");
	if("DocCrUser".equals(sSearchKey) && !CMyString.isEmpty(sSearchValue)){
		if(bSearchGroupId){
			sWhere = "username like ? And userid in(SELECT USERID FROM wcmGrpUser where "+sGroupIdSQL+" )";
			filter.addSearchValues("%" + sSearchValue + "%");
			filter.addSearchValues(nGroupId);
		}else{
			sWhere = "username like ? ";
			filter.addSearchValues("%" + sSearchValue + "%");
		}
	}else if("GroupName".equals(sSearchKey) && !CMyString.isEmpty(sSearchValue)){
		if(bSearchGroupId){
			sWhere = "userid in(SELECT USERID FROM wcmGrpUser where "+sGroupIdSQL+" )";
			filter.addSearchValues(nGroupId);
		}else{
			sWhere = "userid in(SELECT USERID FROM wcmGrpUser where groupid in(select groupId from WCMGroup where GName like ?))";
			filter.addSearchValues("%" + sSearchValue + "%");
		}
	}else {
		if(bSearchGroupId){
			sWhere = "userid in(SELECT USERID FROM wcmGrpUser where "+sGroupIdSQL+" )";
			filter.addSearchValues(nGroupId);
		}
	}

	boolean bSearch = false;
	if(!CMyString.isEmpty(sWhere)){
		filter.setWhere(sWhere);
		oUsers = Users.openWCMObjs(loginUser, filter);
		bSearch = true;
	}
	final Users users = oUsers;

	//7 按照前面构造的条件,对统计结果集合进行过滤
	if(bSearch){
		result = result.filter(new IStatResultFilter() {
			public boolean accept(String sKey, IStatResult statResult) {
				String sCurrUserName = sKey;
				for(int i=0; i<users.size();i++){
					User oUser = (User)users.getAt(i);
					if(oUser == null)
						continue;
					String sUserName = oUser.getName();
					if(sCurrUserName.equalsIgnoreCase(sUserName)){
						return true;
					}
				}
				return false;
			}
		});
	}

	//8 排序
    List arUserNames = result.list();//result.sort(true);

	//9 设置分页信息
	int nNum = 0;
	if(arUserNames != null && (arUserNames.size() > 0)){
		nNum= arUserNames.size();
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
	//10.结束
	out.clear();
%>