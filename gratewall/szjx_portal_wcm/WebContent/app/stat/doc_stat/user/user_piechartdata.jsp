<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@include file="user_doc_stat_include.jsp"%>
<%
	String sType = currRequestHelper.getString("type");
	// 获取sqlindex值
	int sSqlIndex = currRequestHelper.getInt("SqlIndex",1);
	// 获取需要的选项值
	int nValue = currRequestHelper.getInt("value",0);
	
	StringBuffer elements = new StringBuffer(500);
	StringBuffer colors = new StringBuffer(500);
	int y_max = 0,y_min = Integer.MAX_VALUE,y_step=10;
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
			if(num==0)continue;
			// 如果没有数据 就不显示该信息
			elements.append(",{\"value\":"+num+",\"label\":\""+CMyString.truncateStr(sUserName, 20)+"("+num+")\"}");
			colors.append(","+getRandomColor());
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}
	// 获取当前flash的描述信息
	String sFlashDesc = currRequestHelper.getString("desc");
	if(elements.length()==0){
		sFlashDesc+= LocaleServer.getString("user_piechartdata.jsp.have_no_data","(无数据)");
	}
	// 设置最大值与最小值
	if(y_max > 0) y_step = y_max/10>0?y_max/10:1;
	y_max += y_step;
	out.clear();
	String sColors = colors.length()>0?colors.substring(1):"";
	String sElements = elements.length()>0?elements.substring(1):"";
	out.println(getPieChartJson(sFlashDesc,sColors,sElements));
%>