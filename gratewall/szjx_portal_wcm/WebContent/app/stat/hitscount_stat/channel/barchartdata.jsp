<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error.jsp"%>
<%@include file="channel_hitscount_data_include.jsp"%>
<%	
	StringBuffer elements = new StringBuffer(500);
	StringBuffer channelNames = new StringBuffer(500);
	int y_max = 0,y_min = Integer.MAX_VALUE,y_step=10;
	// 生成用户数据
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {			
		try{
			int nChannelId = Integer.parseInt((String) arHostIds.get(i-1));
			Channel channel = Channel.findById(nChannelId);
			if(channel == null)
				continue;
			String sChannelName = CMyString.transDisplay(channel.getDesc());
			int nResult = result.getResult(1, String.valueOf(nChannelId));
			int num = nResult;
			//取最大值和最小值
			if(y_max<num) y_max = num;
			if(y_min>num) y_min = num;
			// 构造元素信息
			elements.append("{\"top\":"+num+",\"tip\":\"栏目:"+sChannelName+"<br>点击量:#top#\"}");
			// 构造发稿人
			channelNames.append("\""+sChannelName+"\"");
			
			if(i < currPager.getLastItemIndex()){
				elements.append(",");
				channelNames.append(",");
			}
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}
	// 设置最大值与最小值
	if(y_max > 0) y_step = y_max/10;
	y_max += y_step;
	response.setHeader("XlabelsNum",String.valueOf((CMyString.split(channelNames.toString(),",").length)));
	response.setHeader("ChartType","barchart");
	out.clear();
	out.print(getBarChartJson("栏目点击量",elements.toString(),channelNames.toString(),y_max,y_step));
%>