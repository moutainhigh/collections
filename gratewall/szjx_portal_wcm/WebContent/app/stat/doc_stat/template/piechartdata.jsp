<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error.jsp"%>
<%@include file="sitesource_stat_include.jsp"%>
<%
	String sType = currRequestHelper.getString("type");
	// 获取sqlindex值
	int sqlIndex = currRequestHelper.getInt("SqlIndex",1);
	// 获取需要的选项值
	int nValue = currRequestHelper.getInt("value",0);
	
	StringBuffer elements = new StringBuffer(500);
	StringBuffer sColor =  new StringBuffer(500);
	int y_max = 0,y_min = Integer.MAX_VALUE,y_step=10;
	// 生成用户数据
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {			
		try{
			String sKey = (String) StatKeys.get(i-1);
			if(sKey == null) continue;
			int num = result.getResult(sqlIndex,sKey);
			
			//取最大值和最小值
			if(y_max<num) y_max = num;
			if(y_min>num) y_min = num;

			// 构造元素信息
			if(num==0)continue;

			// 获取随机颜色
			sColor.append(","+getRandomColor());
			elements.append(",{\"value\":"+num+",\"label\":\""+CMyString.truncateStr(sKey, 20)+"("+num+")\"}");
			
		}catch(Exception ex){
			throw ex;
		}
	}
	// 获取当前flash的描述信息
	String sFlashDesc = currRequestHelper.getString("desc");
	if(elements.length()==0){
		sFlashDesc = "(无数据)";
	}
	// 设置最大值与最小值
	if(y_max > 0) y_step = y_max/10;
	y_max += y_step;
	out.clear();
	String sColors = sColor.length()>0?sColor.substring(1):"";
	String sElements = elements.length()>0?elements.substring(1):"";
	out.println(getPieChartJson(sFlashDesc,sColors,sElements));
%>