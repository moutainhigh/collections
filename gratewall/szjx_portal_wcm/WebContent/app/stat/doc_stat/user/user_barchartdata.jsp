<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error.jsp"%>
<%@include file="user_doc_stat_include.jsp"%>
<%
	String sType = currRequestHelper.getString("type");
	// 获取sqlindex值
	int sSqlIndex = currRequestHelper.getInt("SqlIndex",1);
	// 获取需要的选项值
	int nValue = currRequestHelper.getInt("value",0);
	// 获取当前flash的描述信息
	String sFlashDesc = currRequestHelper.getString("desc");
	
	StringBuffer elements = new StringBuffer(500);
	StringBuffer userNames = new StringBuffer(500);
	int y_max = 0,y_min = Integer.MAX_VALUE,y_step=10;

	int nCurrPageSize = currPager.getLastItemIndex();
	// 生成用户数据
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {			
		try{
			String sUserName = (String) arUserNames.get(i-1);
			int num =0;
			// 如果是查询总发稿量
			if(sSqlIndex == 1)
				num = result.getResult(sSqlIndex,sUserName);
			else if(sSqlIndex == 3){
				//实体型文档数目
				if(nValue==1){
					int nEntityNum = result.getResult(3,new String[]{sUserName,CMSConstants.CONTENT_MODAL_ENTITY + ""});
					int nCopyNum = result.getResult(4,sUserName);
					num = nEntityNum - nCopyNum;
				}else if(nValue==2){
					int nLinkNum = result.getResult(3,new String[]{sUserName,CMSConstants.CONTENT_MODAL_LINK + ""});
					int nMirrorNum = result.getResult(3,new String[]{sUserName,CMSConstants.CONTENT_MODAL_MIRROR + ""});
					num = nLinkNum + nMirrorNum;
				}else if(nValue==3){//如果是复制文档数
					num = result.getResult(4,sUserName);
					 
				}					
			}else{
				num = result.getResult(sSqlIndex, new String[]{sUserName,nValue + ""});
			}
			
			//取最大值和最小值
			if(y_max<num) y_max = num;
			if(y_min>num) y_min = num;
			// 构造元素信息
			elements.append("{\"top\":"+num+",\"tip\":\"用户:"+sUserName+"<br>发稿量:#top#\"}");
			// 构造水平下标显示发稿人
			if(sUserName.getBytes().length<= 8){
				userNames.append("\""+sUserName+"\"");
			} else {
				if(nCurrPageSize <= 10){
					userNames.append("\""+CMyString.truncateStr(sUserName, 12) +"\"");
				} else if(nCurrPageSize >= 15){
					userNames.append("\""+CMyString.truncateStr(sUserName, 6)+"\"");
				} else {
					userNames.append("\""+CMyString.truncateStr(sUserName, 10)+"\"");
				}
			}
			
			if(i < currPager.getLastItemIndex()){
				elements.append(",");
				userNames.append(",");
			}
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}
	// 设置最大值与最小值
	if(y_max > 0) y_step = y_max/10;
	y_max += y_step;
	response.setHeader("XlabelsNum",String.valueOf((CMyString.split(userNames.toString(),",").length)));
	response.setHeader("ChartType","barchart");
	out.clear();
	out.print(getBarChartJson(sFlashDesc,elements.toString(),userNames.toString(),y_max,y_step));
%>