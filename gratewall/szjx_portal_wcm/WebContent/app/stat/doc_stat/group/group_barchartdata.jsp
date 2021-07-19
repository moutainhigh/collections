<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error.jsp"%>
<%@include file="group_doc_stat_include.jsp"%>
<%
	String sType = currRequestHelper.getString("type");
	// 获取sqlindex值
	int sSqlIndex = currRequestHelper.getInt("SqlIndex",1);
	// 获取需要的选项值
	int nValue = currRequestHelper.getInt("value",0);
	// 获取当前flash的描述信息
	String sFlashDesc = currRequestHelper.getString("desc");
	
	StringBuffer elements = new StringBuffer(500);
	StringBuffer groupNames = new StringBuffer(500);
	int y_max = 0,y_min = Integer.MAX_VALUE,y_step=10;
	
	int nCurrPageSize = currPager.getLastItemIndex();
	// 生成用户数据
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {			
		try{
			//反解出构造的key数组
			DocStatHandler4UserDept temp = new DocStatHandler4UserDept();
			String[] sKeyGroup = temp.makeFields((String)arGroupKeys.get(i-1));	
			String sGroupKey = sKeyGroup[0];

			int nGroupId = Integer.parseInt(sGroupKey);			
			Group group = Group.findById(nGroupId);
			String sGroupName = group.getName();
			int num =0;
			if(sSqlIndex == 1)
				num = result.getResult(1,sGroupKey);
			else
				num = result.getResult(sSqlIndex, new String[]{sGroupKey,nValue + ""});
			//取最大值和最小值
			if(y_max<num) y_max = num;
			if(y_min>num) y_min = num;
			// 构造元素信息
			elements.append("{\"top\":"+num+",\"tip\":\"部门:"+sGroupName+"<br>发稿量:#top#\"}");
			// 构造水平下标显示发稿人
			if(sGroupName.getBytes().length<= 8){
				groupNames.append("\""+sGroupName+"\"");
			} else {
				if(nCurrPageSize <= 10){
					groupNames.append("\""+CMyString.truncateStr(sGroupName, 12) +"\"");
				} else if(nCurrPageSize >= 15){
					groupNames.append("\""+CMyString.truncateStr(sGroupName, 6) +"\"");
				} else {
					groupNames.append("\""+CMyString.truncateStr(sGroupName, 10) +"\"");
				}
			}
			
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
	out.print(getBarChartJson(sFlashDesc,elements.toString(),groupNames.toString(),y_max,y_step));
%>