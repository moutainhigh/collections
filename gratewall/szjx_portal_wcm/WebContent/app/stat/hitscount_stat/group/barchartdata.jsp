<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error.jsp"%>
<%@include file="group_hitscount_data_include.jsp"%>
<%	
	StringBuffer elements = new StringBuffer(500);
	StringBuffer groupNames = new StringBuffer(500);
	int y_max = 0,y_min = Integer.MAX_VALUE,y_step=10;
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
			elements.append("{\"top\":"+num+",\"tip\":\"部门:"+sGroupName+"<br>点击量:#top#\"}");
			groupNames.append("\""+sGroupName+"\"");
			
			if(i < currPager.getLastItemIndex()){
				elements.append(",");
				groupNames.append(",");
			}
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}
	// 设置最大值与最小值
	if(y_max > 0) y_step = y_max/10;
	y_max += y_step;
	response.setHeader("XlabelsNum",String.valueOf((CMyString.split(groupNames.toString(),",").length)));
	response.setHeader("ChartType","barchart");
	out.clear();
	out.print(getBarChartJson("部门点击量",elements.toString(),groupNames.toString(),y_max,y_step));
%>