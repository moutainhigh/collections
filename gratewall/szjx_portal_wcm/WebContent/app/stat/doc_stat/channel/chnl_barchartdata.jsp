<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@include file="chnl_doc_stat_include.jsp"%>
<%
	// 获取sqlindex值
	int sSqlIndex = currRequestHelper.getInt("SqlIndex",1);
	// 获取需要的选项值
	int nValue = currRequestHelper.getInt("value",0);
	// 获取当前flash的描述信息
	String sFlashDesc = currRequestHelper.getString("desc");
	
	StringBuffer elements = new StringBuffer(500);
	StringBuffer sbChnlDescs = new StringBuffer(500);
	int y_max = 0,y_min = Integer.MAX_VALUE,y_step=10;

	int nCurrPageSize = currPager.getLastItemIndex();
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
			elements.append("{\"top\":"+num+",\"tip\":\""+CMyString.format(LocaleServer.getString("chnl_barchartdata.chnl","栏目:{0}<br>发稿量:"),new String[]{sChnlDesc})+"#top#\"}");
			// 构造水平下标显示发稿人
			if(sChnlDesc.getBytes().length<= 8){
				sbChnlDescs.append("\""+sChnlDesc+"\"");
			} else {
				if(nCurrPageSize <= 10){
					sbChnlDescs.append("\""+CMyString.truncateStr(sChnlDesc, 12) +"\"");
				} else if(nCurrPageSize >= 15){
					sbChnlDescs.append("\""+CMyString.truncateStr(sChnlDesc, 6) +"\"");
				} else {
					sbChnlDescs.append("\""+CMyString.truncateStr(sChnlDesc, 10) +"\"");
				}
			}
			
			if(i < currPager.getLastItemIndex()){
				elements.append(",");
				sbChnlDescs.append(",");
			}
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}
	// 设置最大值与最小值
	if(y_max > 0) y_step = y_max/10;
	y_max += y_step;
	out.clear();
	response.setHeader("XlabelsNum",String.valueOf((CMyString.split(sbChnlDescs.toString(),",").length)));
	response.setHeader("ChartType","barchart");
	out.print(getBarChartJson(sFlashDesc,elements.toString(),sbChnlDescs.toString(),y_max,y_step));
%>