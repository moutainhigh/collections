<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@include file="site_doc_stat_include.jsp"%>
<%
	// 获取sqlindex值
	int sSqlIndex = currRequestHelper.getInt("SqlIndex",1);
	// 获取需要的选项值
	int nValue = currRequestHelper.getInt("value",0);
	// 获取当前flash的描述信息
	String sFlashDesc = currRequestHelper.getString("desc");
	
	StringBuffer elements = new StringBuffer(500);
	StringBuffer sbSiteDescs = new StringBuffer(500);
	int y_max = 0,y_min = Integer.MAX_VALUE,y_step=10;

	int nCurrPageSize = currPager.getLastItemIndex();
	// 生成用户数据
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {			
		try{
			String sKey = (String) SiteKeys.get(i-1);
			int nSiteId = Integer.parseInt(sKey);
			WebSite oWebSite = WebSite.findById(nSiteId);
			if(oWebSite==null)continue;
			String sSiteDesc = oWebSite.getDesc();
			int num =0;
			if(sSqlIndex == 1)
				num = result.getResult(1,nSiteId+"");
			else
				num = result.getResult(sSqlIndex,new String[]{nSiteId+"", nValue+""});
			//取最大值和最小值
			if(y_max<num) y_max = num;
			if(y_min>num) y_min = num;
			// 构造元素信息
			elements.append("{\"top\":"+num+",\"tip\":\"站点:"+sSiteDesc+"<br>发稿量:#top#\"}");
			// 构造水平下标显示发稿人
			if(sSiteDesc.getBytes().length<= 8){
				sbSiteDescs.append("\""+sSiteDesc+"\"");
			} else {
				if(nCurrPageSize <= 10){
					sbSiteDescs.append("\""+CMyString.truncateStr(sSiteDesc, 12) +"\"");
				} else if(nCurrPageSize >= 15){
					sbSiteDescs.append("\""+CMyString.truncateStr(sSiteDesc, 6) +"\"");
				} else {
					sbSiteDescs.append("\""+CMyString.truncateStr(sSiteDesc, 10) +"\"");
				}
			}

			if(i < currPager.getLastItemIndex()){
				elements.append(",");
				sbSiteDescs.append(",");
			}
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}
	// 设置最大值与最小值
	if(y_max > 0) y_step = y_max/10;
	y_max += y_step;
	out.clear();
	out.print(getBarChartJson(sFlashDesc,elements.toString(),sbSiteDescs.toString(),y_max,y_step));
%>