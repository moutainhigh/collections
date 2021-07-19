<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@include file="#PREFIX#_stat_include.jsp"%>
<%
	// 获取sqlindex值
	int sqlIndex = currRequestHelper.getInt("SqlIndex",1);
	// 获取需要的选项值
	int nValue = currRequestHelper.getInt("value",0);
	// 获取当前flash的描述信息
	String sFlashDesc = currRequestHelper.getString("desc");
	
	StringBuffer elements = new StringBuffer(500);
	StringBuffer descBuff = new StringBuffer(500);
	int y_max = 0,y_min = Integer.MAX_VALUE,y_step=10;

	int nCurrPageSize = currPager.getLastItemIndex();
	// 生成用户数据
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {			
		try{
			// TODO 数据处理逻辑
			String sKey = (String) StatKeys.get(i-1);
			if(sKey == null) continue;

			int num = result.getResult(sqlIndex,sKey);

			//取最大值和最小值
			if(y_max<num) y_max = num;
			if(y_min>num) y_min = num;

			// 构造元素信息
			elements.append("{\"top\":"+num+",\"tip\":\"#PREFIX_DESC#:"+sKey+"<br>发稿量:#top#\"}");
			
			// 构造水平下标显示发稿人
			if(sKey.getBytes().length<= 8){
				descBuff.append("\""+sKey+"\"");
			} else {
				if(nCurrPageSize <= 10){
					descBuff.append("\""+CMyString.truncateStr(sKey, 12) +"\"");
				} else if(nCurrPageSize >= 15){
					descBuff.append("\""+CMyString.truncateStr(sKey, 6) +"\"");
				} else {
					descBuff.append("\""+CMyString.truncateStr(sKey, 10)+"\"");
				}
			}

			if(i < currPager.getLastItemIndex()){
				elements.append(",");
				descBuff.append(",");
			}
		}catch(Exception ex){
			throw ex;
		}
	}
	// 设置最大值与最小值
	if(y_max > 0) y_step = y_max/10;
	y_max += y_step;
	out.clear();
	out.print(getBarChartJson(sFlashDesc,elements.toString(),descBuff.toString(),y_max,y_step));
%>