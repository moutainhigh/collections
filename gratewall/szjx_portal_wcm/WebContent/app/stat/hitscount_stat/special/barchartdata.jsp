<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error.jsp"%>
<%@include file="special_hitscount_data_include.jsp"%>
<%	
	StringBuffer elements = new StringBuffer(500);
	StringBuffer hostNames = new StringBuffer(500);
	int y_max = 0,y_min = Integer.MAX_VALUE,y_step=10;
	// 生成用户数据
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {			
		try{
			int nHostId = Integer.parseInt((String) arHostIds.get(i-1));
			BaseChannel baseChannel = WebSite.findById(nHostId);
			if(baseChannel == null)
				baseChannel = Channel.findById(nHostId);
			if(baseChannel == null)
				continue;
			String sHostName = CMyString.transDisplay(baseChannel.getDesc());
			int nResult = result.getResult(1, String.valueOf(nHostId));
 
			int num = nResult;
			//取最大值和最小值
			if(y_max<num) y_max = num;
			if(y_min>num) y_min = num;
			// 构造元素信息
			elements.append("{\"top\":"+num+",\"tip\":\"专题:"+sHostName+"<br>点击量:#top#\"}");
			// 构造发稿人
			hostNames.append("\""+sHostName+"\"");
			
			if(i < currPager.getLastItemIndex()){
				elements.append(",");
				hostNames.append(",");
			}
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}
	// 设置最大值与最小值
	if(y_max > 0) y_step = y_max/10;
	y_max += y_step;
	response.setHeader("XlabelsNum",String.valueOf((CMyString.split(hostNames.toString(),",").length)));
	response.setHeader("ChartType","barchart");
	out.clear();
	out.print(getBarChartJson("专题点击量",elements.toString(),hostNames.toString(),y_max,y_step));
%>