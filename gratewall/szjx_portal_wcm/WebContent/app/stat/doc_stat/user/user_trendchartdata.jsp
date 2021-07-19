<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@include file="user_trendchartdata_include.jsp"%>
<%	
	// 获取文档状态值或者文档类型值
	String sValue = currRequestHelper.getString("value"); 
	int nSqlIndex = currRequestHelper.getInt("SqlIndex",1);
	StringBuffer elements = new StringBuffer(2000);
	int y_max = 0,y_min = Integer.MAX_VALUE,y_step=10;
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		String sKey = String.valueOf(mainObjList.get(i-1));
		List list = new ArrayList();
		// 统计总量时需要做特殊处理
		if(nSqlIndex==1)
			 list = result.getResult(nSqlIndex,sKey);
		else
			list = result.getResult(nSqlIndex,new String[]{sKey,sValue});
		for (int j = 0; j < list.size(); j++){
			int num = ((Integer)list.get(j)).intValue();
			if(y_max<num) y_max = num;
			if(y_min>num) y_min = num;
		}
		elements.append(","+getTrendChartValue("用户",sKey,list.toString()));
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