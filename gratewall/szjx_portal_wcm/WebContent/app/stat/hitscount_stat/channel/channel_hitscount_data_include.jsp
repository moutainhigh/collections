<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.components.stat.IStatResult"%>
<%@ page import="com.trs.components.stat.StatAnalysisTool"%>
<%@ page import="com.trs.components.stat.HitsCountHelper"%>
<%@ page import="com.trs.components.stat.IStatResultFilter"%>
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
<%@ page import="com.trs.infra.support.config.ConfigServer"%>

<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@include file="../../../../include/public_server.jsp"%>
<%@include file="../../include/static_common.jsp"%>
<%
	//1 构造sql
	String[] pChannelHitsTimeStatSQL = new String[]{
		"Select sum(HITSCOUNT) TotalCount,HostId from XWCMHOSTOBJHITSCOUNT "
				+ " Where HitsTime>=${STARTTIME}"
				+ " And HitsTime<=${ENDTIME}" 
				+ " And isSpecial=0"
				+ " And HostType=101"
				+ " Group by HostId"
				+ " Order by TOTALCOUNT Desc"
		};
	
	//2 获取基本的时间类型参数

	//创建时间的统计方式
	int nCrTimeItem = currRequestHelper.getInt("TimeItem", 0);
	//点击量时间的统计方式
	int nHitsTimeItem = currRequestHelper.getInt("HitsTimeItem", 0);

	//3 构造点击的时间段
	String[] sHitsTimeRands = new String[2];
	String[] sHitsTimeRandsForCompare = new String[2];
	if(nHitsTimeItem == 0){
		sHitsTimeRands[0] = "2000-01-01 00:00:00";
		sHitsTimeRands[1] = CMyDateTime.now().toString();
	}else{
		sHitsTimeRands = HitsCountHelper.makeQueryHitsTimeRands(nHitsTimeItem);
		sHitsTimeRandsForCompare = HitsCountHelper.makeQueryComparedHitsTimeRands(nHitsTimeItem);
	}

	//4 获取所有的统计数据
	StatAnalysisTool tool = new StatAnalysisTool(pChannelHitsTimeStatSQL, new HitsStatHandler4Channel());
    IStatResult result = tool.stat(sHitsTimeRands[0], sHitsTimeRands[1]);

	IStatResult resultForCompare = null;
	if(nHitsTimeItem > 0){
		resultForCompare = tool.stat(sHitsTimeRandsForCompare[0], sHitsTimeRandsForCompare[1]);
	}

	//5 根据检索条件获取当前主体对象集合
	String sSearchKey =  CMyString.showNull(currRequestHelper.getString("SelectItem"));
	String sSearchValue =  CMyString.showNull(currRequestHelper.getString("SearchValue"));
	Channels channels = new Channels(loginUser);
	WCMFilter filter = new WCMFilter("","","");
	String sWhere = "";
	boolean bSearch = false;
	if(!CMyString.isEmpty(sSearchValue)){
		if("siteName".equals(sSearchKey)){
			sWhere = "SiteId in (select WCMWebSite.SiteId from WCMWEBSITE where WCMWebSite.SiteDesc like ?)";
			filter.addSearchValues("%" + sSearchValue + "%");
		}else if("hostName".equals(sSearchKey)){
			sWhere = "CHNLDESC like ?";
			filter.addSearchValues("%" + sSearchValue + "%");
		}
	}

	//构造创建时间的开始和结束
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

	//6 按照前面构造的条件,对统计结果集合进行过滤
	if(bSearch){
		result = result.filter(new IStatResultFilter() {
			public boolean accept(String sKey, IStatResult statResult) {
				int nChannelId = Integer.parseInt(sKey);
				return chnls.indexOf(nChannelId) >= 0;
			}
		});
		if(resultForCompare!= null){
			resultForCompare = resultForCompare.filter(new IStatResultFilter() {
				public boolean accept(String sKey, IStatResult statResult) {
					int nChannelId = Integer.parseInt(sKey);
					return chnls.indexOf(nChannelId) >= 0;
				}
			});
		}
	}
	
	//7 排序
    List arHostIds = result.sort(true);
	List arHostIdsForCompare = null;
	if(resultForCompare != null){
		arHostIdsForCompare = resultForCompare.sort(true);
	}

	//8 设置分页信息
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
	//10.结束
	out.clear();
%>