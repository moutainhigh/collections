<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.stat.StatDataCountForSite"%>
<%@ page import="com.trs.components.stat.StatDataResultForSite "%>

<%@ page import="com.trs.components.stat.StatDataResultForSites"%>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite"%>
<%@ page import="com.trs.components.wcm.resource.Status"%>
<%@ page import="com.trs.cms.CMSConstants"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.trs.infra.util.CMyDateTime"%>
<%@ page import="com.trs.infra.util.CMyString"%>
<%@include file="../../../../include/public_server.jsp"%>
<%@include file="../../include/static_common.jsp"%>
<%


	StringBuffer sSiteName = new StringBuffer(500);
	StringBuffer elements = new StringBuffer(2000);
	//int[][] nValues = new int[hResults.length][];
	int y_max = 0,y_min = Integer.MAX_VALUE,y_step=10;
	//StringBuffer sSiteName = new StringBuffer(500);
	//StringBuffer[i] sValues = new StringBuffer[];
	/*HashMap resultMap = new HashMap();
	for (int j = 0; hResults.length < 1; j++) {
		StringBuffer sValues = new StringBuffer(500);
		if(hResults[j].isEmpty())continue;
		Iterator itResult = hResults[j].keySet().iterator();
		while(itResult.hasNext()){//此 hashmap 里只有一个
			int nTimeCount = ((Integer)itResult.next()).intValue();
			StatDataResultForSite result = (StatDataResultForSite)hResults[j].get(new Integer(nTimeCount));
			int nStatSQLIndex = 1;
			List arSiteIds = result.sort(2, CMSConstants.CONTENT_MODAL_ENTITY);
			if(arSiteIds==null)continue;
			if(arSiteIds.size()==0){
				//resultMap.get()
			}
			for (int i = 0, nSize = arSiteIds.size(); i < nSize; i++) {
				int nSiteId = (Integer.parseInt((String)arSiteIds.get(i)));
				WebSite oWebSite = WebSite.findById(nSiteId);
				if(oWebSite==null)continue;
				sSiteName.append(",\""+oWebSite.getName()+"\"");
				System.out.println("  "+oWebSite.getName());
				System.out.println("------------");
				// 输出发稿量的统计结果
				nStatSQLIndex = 1;
				//System.out.println("----------------------");
				int nValue = result.getResult(nSiteId, nStatSQLIndex);
				if(y_max<nValue)y_max = nValue;
				if(y_min>nValue)y_min = nValue;
				sValues.append(","+nValue);
				//System.out.print(result.getResult(nSiteId, nStatSQLIndex));
				//System.out.print(" ");

				// 输出状态——新稿的统计结果
				nStatSQLIndex = 2;
				System.out.print(result.getResult(nSiteId, nStatSQLIndex,
						Status.STATUS_ID_NEW));
				System.out.print(" ");

				// 输出状态——已编的统计结果
				System.out.print(result.getResult(nSiteId, nStatSQLIndex,
						Status.STATUS_ID_EDITED));
				System.out.print(" ");

				// 输出状态——已发的统计结果
				System.out.print(result.getResult(nSiteId, nStatSQLIndex,
						Status.STATUS_ID_PUBLISHED));
				System.out.print(" ");

				// 输出原创的统计结果
				nStatSQLIndex = 3;
				System.out.print(result.getResult(nSiteId, nStatSQLIndex,
						CMSConstants.CONTENT_MODAL_ENTITY));
				System.out.print(" ");

				// 输出引用的统计结果
				System.out.print(result.getResult(nSiteId, nStatSQLIndex,
						new int[] { CMSConstants.CONTENT_MODAL_LINK,
								CMSConstants.CONTENT_MODAL_MIRROR }));
				System.out.print(" ");

				System.out.print("\n");
			
			}
		}
		*/
	StringBuffer sValues = new StringBuffer(500);
	sValues.append(",7,12,5,8,9,3,2,4,7,5,6,32,4,2,1,32,6,54,54,25,2,4,2,1,32,6");
	elements.append(",{\"type\":\"line\",\"text\":\"line2\",\"width\":1,\"colour\":\""+getRandomColor()+"\",\"values\":["+sValues.substring(1)+"]}");

	StringBuffer sValues1 = new StringBuffer(500);
	sValues1.append(",1,7,12,5,8,9,3,2,4,7,5,6,32,4,2,1,32,6,4,15,25,2,4,2,1,32");
	elements.append(",{\"type\":\"line\",\"text\":\"line1\",\"width\":1,\"colour\":\""+getRandomColor()+"\",\"values\":["+sValues1.substring(1)+"]}");
		
	System.out.println("------"+elements);
	// 获取sqlindex值
	int sSqlIndex = currRequestHelper.getInt("SqlIndex",1);
	// 获取需要的选项值
	int nValue = currRequestHelper.getInt("value",0);
	// 获取当前flash的描述信息
	String sFlashDesc = currRequestHelper.getString("desc");
	// 获取当前的天数
	int nToday = CMyDateTime.now().getDay();

	String[] arrDay =new String[nToday];
	for(int i=0;i<nToday;i++){
		arrDay[i] = "\""+(i+1)+"\"";
	}
	// 设置最大值与最小值
	y_max = 54;
	if(y_max > 0) y_step = y_max/10>0?y_max/10:1;
	y_max += y_step;
	String x_labels = CMyString.join(arrDay,",");
	out.clear();
%>
{
	"title":{
		"text":"<%=CMyString.filterForJs(sFlashDesc)%>",
		"style":"{font-size:20px;color:#0000ff;font-family:微软雅黑;font-weight:bold;text-align:center;}"
	},
	"elements":[<%=elements.substring(1)%>],
	"x_axis":{
    "labels": {
      "rotate": "vertical",
      "labels":[<%=x_labels%>]
    }
  },
  "y_axis":{
    "max":   <%=y_max%>,
	"steps":<%=y_step%>
  }
}