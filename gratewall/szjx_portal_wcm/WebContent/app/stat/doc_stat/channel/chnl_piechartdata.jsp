<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error.jsp"%>
<%@include file="chnl_doc_stat_include.jsp"%>
<%
	String sType = currRequestHelper.getString("type");
	// 获取sqlindex值
	int sSqlIndex = currRequestHelper.getInt("SqlIndex",1);
	// 获取需要的选项值
	int nValue = currRequestHelper.getInt("value",0);
	
	StringBuffer elements = new StringBuffer(500);
	StringBuffer sbChnlDescs = new StringBuffer(500);
	StringBuffer sColor =  new StringBuffer(500);
	int y_max = 0,y_min = Integer.MAX_VALUE,y_step=10;
	// 生成用户数据
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {			
		try{
			String sKey = (String) ChnlKeys.get(i-1);
			int nChnlId = Integer.parseInt(sKey);
			Channel oChannel = Channel.findById(nChnlId);
			if(oChannel==null)continue;
			String sChnlDesc = oChannel.getDesc();
			int num =0;
			if(sSqlIndex == 1)
				num = result.getResult(1,nChnlId+"");
			else
				num = result.getResult(sSqlIndex,new String[]{nChnlId+"", nValue+""});
			//取最大值和最小值
			if(y_max<num) y_max = num;
			if(y_min>num) y_min = num;
			// 构造元素信息
			if(num==0)continue;
			// 获取随机颜色
			sColor.append(","+getRandomColor());
			elements.append(",{\"value\":"+num+",\"label\":\""+CMyString.truncateStr(sChnlDesc, 20)+"("+num+")\"}");
			
			//elements.append("{\"value\":"+num+",\"label\":\""+sUserName+"\"tip\":\"用户:"+sUserName+"<br>发稿量:#top#\"}");
			// 构造发稿人
			//userNames.append("\""+sUserName+"\"");
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}
	// 获取当前flash的描述信息
	String sFlashDesc = currRequestHelper.getString("desc");
	if(elements.length()==0){
		sFlashDesc+=LocaleServer.getString("piechartdata..null","(无数据)");
	}
	// 设置最大值与最小值
	if(y_max > 0) y_step = y_max/10;
	y_max += y_step;
	out.clear();
	String sColors = sColor.length()>0?sColor.substring(1):"";
	String sElements = elements.length()>0?elements.substring(1):"";
	out.println(getPieChartJson(sFlashDesc,sColors,sElements));
%>