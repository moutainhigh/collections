<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@include file="special_hitscount_data_include.jsp"%>
<%	
	StringBuffer elements = new StringBuffer(500);
	StringBuffer sColor =  new StringBuffer(500);
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
			if(num==0)continue;
			// 获取随机颜色
			sColor.append(","+getRandomColor());
			// 如果没有数据 就不显示该信息
			elements.append(",{\"value\":"+num+",\"label\":\""+sHostName+"("+num+")\"}");
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}
	// 获取当前flash的描述信息
	String sFlashDesc = LocaleServer.getString("piechartdata.hitmount","专题点击量");
	if(elements.length()==0){
		sFlashDesc+=LocaleServer.getString("piechartdata.nodata","(无数据)");
	}
	// 设置最大值与最小值
	if(y_max > 0) y_step = y_max/10;
	y_max += y_step;
	out.clear();
	String sColors=sColor.length()>0?sColor.substring(1):"";
	String sElements =elements.length()>0?elements.substring(1):"";
	out.println(getPieChartJson(sFlashDesc,sColors,sElements));
%>