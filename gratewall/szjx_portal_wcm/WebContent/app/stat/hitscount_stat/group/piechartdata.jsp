<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@include file="group_hitscount_data_include.jsp"%>
<%	
	StringBuffer elements = new StringBuffer(500);
	StringBuffer sColor =  new StringBuffer(500);
	int y_max = 0,y_min = Integer.MAX_VALUE,y_step=10;
	// 生成用户数据
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {			
		try{
			int nGroupId = Integer.parseInt((String) arGroupIds.get(i-1));
			Group group = Group.findById(nGroupId);
			if(group == null)
				continue;
			String sGroupName = CMyString.transDisplay(group.getName());
 
			int num = result.getResult(1, String.valueOf(nGroupId));
			//取最大值和最小值
			if(y_max<num) y_max = num;
			if(y_min>num) y_min = num;
			// 构造元素信息
			if(num==0)continue;
			// 获取随机颜色
			sColor.append(","+getRandomColor());
			// 如果没有数据 就不显示该信息
			elements.append(",{\"value\":"+num+",\"label\":\""+sGroupName+"("+num+")\"}");
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}
	// 获取当前flash的描述信息
	String sFlashDesc = LocaleServer.getString("piechartdata.hitscount","部门点击量");
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