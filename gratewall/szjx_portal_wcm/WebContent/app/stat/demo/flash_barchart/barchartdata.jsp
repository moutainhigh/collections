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
	// 生成用户数据
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {			
		try{
			String sUserName = (String) arUserNames.get(i-1);
			int num =0;
			if(sSqlIndex == 1)
				num = result.getResult(sUserName, 1);
			else
				num = result.getResult(sUserName, sSqlIndex,nValue);
			//取最大值和最小值
			if(y_max<num) y_max = num;
			if(y_min>num) y_min = num;
			// 构造元素信息
			elements.append("{\"top\":"+num+",\"tip\":\"用户:"+sUserName+"<br>发稿量:#top#\"}");
			// 构造发稿人
			userNames.append("\""+sUserName+"\"");
			
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
	out.clear();
%>
{
  "title":{"text":"<%=CMyString.filterForJs(sFlashDesc)%>","style":"{font-size:20px;color:#0000ff;font-family:微软雅黑;font-weight:bold;text-align:center;}"},
  "y_legend":{
    "text": " ",
    "style": "{color: #736AFF; font-size: 12px;}"
  },
  "elements":[{"type":"bar_3d",
  "alpha":1,
  "colour":"#27547f",
  "values" :[<%=elements%>]}],
  "x_axis":{"stroke":1,
    "tick-height":5,
    "colour":"#7c7c7c",
	"font-size":14,
    "labels":{"labels": [<%=userNames%>]
    },
    "3d":         5
   },
  "y_axis":{
    "stroke":      2,
    "tick-length": 10,
    "colour":      "#b53636",
    "offset":      0,
    "max":         <%=y_max%>,
	"steps":<%=y_step%>
  }
}