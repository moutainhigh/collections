<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.components.stat.HitsStatHandler4ChnlDept"%>
<%@ page import="com.trs.components.stat.HitsStatHandler4UserDept"%>
<%@ page import="com.trs.components.stat.StatAnalysisTool"%>
<%@ page import="com.trs.components.stat.IStatResult"%>
<%@ page import="com.trs.components.stat.IStatResultFilter"%>
<%@ page import="com.trs.infra.support.config.ConfigServer"%>
<%@ page import="com.trs.infra.persistent.db.DBManager"%>

<%@ page import="com.trs.components.stat.HitsCountHelper"%>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.cms.auth.persistent.Group" %>
<%@ page import="com.trs.cms.auth.persistent.Groups" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channels" %>
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
	String sGroupStatByChannel = ConfigServer.getServer().getSysConfigValue("GROUP_STAT_BY_CHANNEL", "false");
	boolean bGroupStatByChannel = "true".equalsIgnoreCase(sGroupStatByChannel);

	//1 构造sql
	// 根据栏目的点击时间段来统计的sql
	String[] pGroupHitsTimeStatSQLByChannel = new String[]{
				"Select sum(HITSCOUNT) TotalCount,HostId,HostType from XWCMHOSTOBJHITSCOUNT "
					// 统计指定时间段的数据
					+ " Where HitsTime>=${STARTTIME}"
					+ " And HitsTime<=${ENDTIME}" 
					+ " And HostType=101"
					+ " Group by HostId,HostType" 
		};
    // 根据文档的点击时间段来统计的sql
    String[] pGroupHitsTimeStatSQLByUser = new String[] {
			"Select sum(HITSCOUNT) TOTALCOUNT,DOCID,DOCCRUSER from XWCMDOCUMENTHITSCOUNT "
                    + " Where HITSTIME>=${STARTTIME}"
                    + " And HITSTIME<=${ENDTIME}"
                    + " Group by DOCID,DOCCRUSER" };	
	
	//2 获取基本的时间类型参数

	//创建时间的统计方式
	int nCrTimeItem = currRequestHelper.getInt("TimeItem", 0);
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

	//4 获取所有的统计数据
	String[] sCrTimeRands = null;
	CMyDateTime dtCrTimeStart = new CMyDateTime(), dtCrTimeEnd = new CMyDateTime();
	Channels filterChannels = new Channels(loginUser);
	int[] filterDocIds = null;
	if(nCrTimeItem != 0){
		sCrTimeRands = getDateTimeFromParame(currRequestHelper);
		dtCrTimeStart.setDateTimeWithString(sCrTimeRands[0]);
		dtCrTimeEnd.setDateTimeWithString(sCrTimeRands[1]);
		if(bGroupStatByChannel){
			WCMFilter channelFilter = new WCMFilter("", "CRTIME>=? AND CRTIME<=?", "");
			channelFilter.addSearchValues(dtCrTimeStart);
			channelFilter.addSearchValues(dtCrTimeEnd);
			filterChannels = Channels.openWCMObjs(loginUser, channelFilter);
		}else{
			WCMFilter documentFilter = new WCMFilter("XWCMDOCUMENTHITSCOUNT","DOCCRTIME>=? AND DOCCRTIME<=?","","distinct docid");
			documentFilter.addSearchValues(dtCrTimeStart);
			documentFilter.addSearchValues(dtCrTimeEnd);
			filterDocIds = DBManager.getDBManager().sqlExecuteIntsQuery(documentFilter);
		}
	}

	String[] pSql = bGroupStatByChannel ? pGroupHitsTimeStatSQLByChannel : pGroupHitsTimeStatSQLByUser;
	StatAnalysisTool tool = null;
	if(bGroupStatByChannel){
		HitsStatHandler4ChnlDept oHitsStatHandler4ChnlDept = null;
		if(nCrTimeItem != 0){
			oHitsStatHandler4ChnlDept = new HitsStatHandler4ChnlDept(filterChannels);
		}else{
			oHitsStatHandler4ChnlDept = new HitsStatHandler4ChnlDept();
		}
		tool = new StatAnalysisTool(pSql, oHitsStatHandler4ChnlDept);
	}else{
		HitsStatHandler4UserDept oHitsStatHandler4UserDept = null;
		if(nCrTimeItem != 0){
			oHitsStatHandler4UserDept = new HitsStatHandler4UserDept(filterDocIds);
		}else{
			oHitsStatHandler4UserDept = new HitsStatHandler4UserDept();
		}
		tool = new StatAnalysisTool(pSql, oHitsStatHandler4UserDept);
	}

    IStatResult result = tool.stat(sHitsTimeRands[0], sHitsTimeRands[1]);
	

	//5 根据检索条件获取当前主体对象集合
	String sSearchKey =  CMyString.showNull(currRequestHelper.getString("SelectItem"));
	String sSearchValue =  CMyString.showNull(currRequestHelper.getString("SearchValue"));
	Groups oGroups = new Groups(loginUser);
	WCMFilter filter = new WCMFilter("","","");
	String sWhere = "";
	boolean bSearch = false;
	if(!CMyString.isEmpty(sSearchValue)){
		if("GroupName".equals(sSearchKey)){
			sWhere = "GNAME like ?";
			filter.addSearchValues("%" + sSearchValue + "%");
		}
		if(!CMyString.isEmpty(sWhere)){
			filter.setWhere(sWhere);
			oGroups = Groups.openWCMObjs(loginUser, filter);
			bSearch = true;
		}
	}

	final Groups groups = oGroups;

	//7 按照前面构造的条件,对统计结果集合进行过滤
	if(bSearch){
		result = result.filter(new IStatResultFilter() {
			public boolean accept(String sKey, IStatResult statResult) {
				int nGroupId = Integer.parseInt(sKey);
				return groups.indexOf(nGroupId) >= 0;
			}
		});
	}
	
	//7 排序
    List arGroupIds = result.sort(true);

	//8 设置分页信息
	int nNum = 0;
	if(arGroupIds != null && (arGroupIds.size() > 0)){
		nNum= arGroupIds.size();
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