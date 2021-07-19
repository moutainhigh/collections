<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.components.stat.IStatResult"%>
<%@ page import="com.trs.components.stat.IStatResults"%>
<%@ page import="com.trs.components.stat.StatAnalysisTool"%>
<%@ page import="com.trs.components.stat.HitsCountHelper"%>
<%@ page import="com.trs.components.stat.IStatResultsFilter"%>

<%@ page import="com.trs.components.stat.HitsStatHandler4Channel"%>
 
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channels" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.cms.auth.persistent.User" %>
<%@ page import="com.trs.cms.auth.persistent.Groups" %>
<%@ page import="com.trs.cms.auth.persistent.Group" %>
<%@ page import="com.trs.infra.util.CMyString"%>
<%@ page import="com.trs.components.stat.IChnlDeptMgr"%>
<%@ page import="com.trs.DreamFactory"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>

<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@include file="../../../../include/public_server.jsp"%>
<%@include file="../../include/static_common.jsp"%>
<%	
	// 1 构造sql
	// 统计指定点击时间段的数据
	String[] pChannelHitsTimeStatSQL = new String[]{
		"Select sum(HITSCOUNT) TotalCount,HostId from XWCMHOSTOBJHITSCOUNT "
				+ " Where HitsTime>=${STARTTIME}"
				+ " And HitsTime<=${ENDTIME}" 
				+ " And isSpecial=0"
				+ " And HostType=101"
				+ " Group by HostId"
		};
	// 2 构造时间范围

	//创建时间的统计方式
	int nCrTimeItem = currRequestHelper.getInt("TimeItem", 0);
	//点击量时间的统计方式
	int nHitsTimeItem = currRequestHelper.getInt("HitsTimeItem", 0);

	String[] sHitsTimeRands = new String[2];
	if(nHitsTimeItem == 0){
		sHitsTimeRands[0] = "2000-01-01 00:00:00";
		sHitsTimeRands[1] = CMyDateTime.now().toString();
	}else{
		sHitsTimeRands = HitsCountHelper.makeQueryHitsTimeRands(nHitsTimeItem);
	}

	//3 统计所有结果
	int nTimeStep = currRequestHelper.getInt("TimeStep",1); 
	StatAnalysisTool tool = new StatAnalysisTool(pChannelHitsTimeStatSQL, new HitsStatHandler4Channel());
	IStatResults result = tool.stat(sHitsTimeRands[0], sHitsTimeRands[1], nTimeStep);
	
	//4 根据检索条件获取当前主体对象集合
	String sSearchKey =  CMyString.showNull(currRequestHelper.getString("SelectItem"));
	String sSearchValue =  CMyString.showNull(currRequestHelper.getString("SearchValue"));
	Channels channels = new Channels(loginUser);
	WCMFilter filter = new WCMFilter("","","");
	String sWhere = "";
	boolean bSearch = false;
	if(!CMyString.isEmpty(sSearchValue)){
		if("siteName".equals(sSearchKey)){
			sWhere = "exists(select 1 from WCMWebSite where WCMWebSite.SITEID = SITEID AND WCMWebSite.SITEDESC like ?)";
			filter.addSearchValues("%" + sSearchValue + "%");
		}else if("hostName".equals(sSearchKey)){
			sWhere = "CHNLDESC like ?";
			filter.addSearchValues("%" + sSearchValue + "%");
		}
	}

	// 构造创建时间的开始和结束
	String[] sCrTimeRands = null;
	CMyDateTime dtCrTimeStart = new CMyDateTime(), dtCrTimeEnd = new CMyDateTime();
	if(nCrTimeItem != 0){
		sCrTimeRands = getDateTimeFromParame(currRequestHelper);
		dtCrTimeStart.setDateTimeWithString(sCrTimeRands[0]);
		dtCrTimeEnd.setDateTimeWithString(sCrTimeRands[1]);
		if(!CMyString.isEmpty(sWhere)){
			sWhere+=" AND CRTIME >= ? AND CRTIME <= ?";
		}else{
			sWhere = "CRTIME >= ? AND CRTIME <= ?";
		}
		filter.addSearchValues(dtCrTimeStart);
		filter.addSearchValues(dtCrTimeEnd);
	}
	// 开检索的集合
	if(!CMyString.isEmpty(sWhere)){
		filter.setWhere(sWhere);
		channels = Channels.openWCMObjs(loginUser, filter);
		bSearch = true;
	}
	
	//按照组织的检索比较特殊，最后再过滤处理
	Channels channelsByGroup = new Channels(loginUser);
	if("GroupName".equals(sSearchKey) && !CMyString.isEmpty(sSearchValue)){
		Groups groups = new Groups(loginUser);
		WCMFilter oGroupFilter = new WCMFilter("","GNAME LIKE ?","");
		oGroupFilter.addSearchValues("%" + sSearchValue + "%");
		groups = Groups.openWCMObjs(loginUser, oGroupFilter);
		if(groups != null && groups.size() > 0){
			IChnlDeptMgr mgr = (IChnlDeptMgr) DreamFactory.createObjectById("IChnlDeptMgr");
			String sChannelIds = mgr.getChnlIdsByDepts(groups);
			channelsByGroup = Channels.findByIds(loginUser,sChannelIds);
		}
		if(bSearch){
			//说明之前已经有其他的检索条件检索，在此需要与部门的检索做合并处理
			for(int k=0; k<channelsByGroup.size(); k++){
				Channel filterChannel = (Channel)channelsByGroup.getAt(k);
				if(filterChannel == null)
					continue;
				int nfilterChannelId = filterChannel.getId();
				if(channels.indexOf(nfilterChannelId) < 0){
					channelsByGroup.remove(filterChannel, false);
				}
			}
		}else{
			//说明之前没有检索，只有根据部门的检索这种情况
			bSearch = true;
		}
		//不管是什么方式的合并，最后都是以channelsByGroup为终
		channels = channelsByGroup;
	}
	final Channels chnls = channels;

	//5 按照前面构造的条件,对统计结果集合进行过滤
	if(bSearch){
		result = result.filter(new IStatResultsFilter() {
			public boolean accept(String sKey, IStatResults statResults) {
				int nChnlId = Integer.parseInt(sKey);
				return chnls.indexOf(nChnlId) >= 0;
			}
		});
	}


	//6 排序
    List arHostIds = result.list();
	 
	//7 设置分页信息
	int nNum = 0;
	if(arHostIds != null && (arHostIds.size() > 0)){
		nNum= arHostIds.size();
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

	StringBuffer elements = new StringBuffer(2000);
	int y_max = 0,y_min = Integer.MAX_VALUE,y_step=10;
	//for (int i = 0; i < arHostIds.size(); i++){
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		String sKey = String.valueOf(arHostIds.get(i-1));
		List list = result.getResult(1, sKey);
		for (int j = 0; j < list.size(); j++){
			int num = ((Integer)list.get(j)).intValue();
			if(y_max<num) y_max = num;
			if(y_min>num) y_min = num;
		}
		Channel currChannel = Channel.findById(Integer.parseInt(sKey));
		if(currChannel==null)continue;
		String sChannelName = currChannel.getDesc();
		elements.append(","+getTrendChartValue("栏目",sChannelName,list.toString()));
	}
	// 获取当前flash的描述信息
	String sFlashDesc = currRequestHelper.getString("desc");

	// 设置最大值与最小值
	if(y_max > 0) y_step = y_max/10>0?y_max/10:1;
	y_max += y_step;
	out.clear();
	response.setHeader("XlabelsNum",String.valueOf(result.getXlabels().size()));
	response.setHeader("ChartType","trendchart");
	String x_labels = result.getXlabels().toString();
	String sElements = elements.length()>0?elements.substring(1):"";
	out.print(getTrendChartJson(sFlashDesc,sElements,x_labels,y_max,y_step));
%>