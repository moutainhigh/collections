<%@ page language="java" pageEncoding="GBK"%>

<%@ page import="com.trs.client.TRSConnection" %>
<%@ page import="com.trs.client.TRSDataBase" %>
<%@ page import="com.trs.client.TRSDataBaseColumn" %>
<%@ page import="com.trs.client.TRSException" %>
<%@ page import="com.trs.client.TRSIndex" %>
<%@ page import="com.trs.client.TRSResultSet" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'trstest.jsp' starting page</title>



  </head>
  
  <body>
  <%TRSConnection conn = null;
  try
  {
          conn = new TRSConnection();
          TRSResultSet trsrs = null;
          conn.connect("160.100.0.224", "8888", "system", "manager", "T10");            
          %>
    		<span>连接服务器成功，开始获取数据库和字段信息</span><br>
    		<% 
          TRSDataBase[] db = conn.getDataBases("*");
          for (int i = 0; i < db.length; i++){
          	TRSDataBase dbt = db[i];
          	System.out.println(db[i].getName());
          	%>
      		<span><%=db[i].getName()%></span><br>
      		<% 
          	TRSDataBaseColumn[] columns = dbt.getColumns();
          	for(int n=0; n<columns.length; n++){
          		TRSDataBaseColumn col = columns[n];
          		%>
          		<span>(<%=n+1%>) [column name] <%=col.getName()%>  [column type name]  <%=col.getColTypeName()%></span><br>
          		<% 
          		System.out.println("{"+(n+1)+"} [column name] "+col.getName()+"  [column type name]  "+col.getColTypeName()+"");
          	}
          	TRSIndex[] idex = dbt.getIndexes();
          	for(int j=0;j<idex.length; j++){
          		TRSIndex indext = idex[j];
          		%>
          		<span>(<%=j+1%>) [strColumn] <%=indext.strColumn%>  [iColType]  <%=indext.iColType%></span><br>
          		<% 
          		System.out.println("<"+(j+1)+">  [strColumn]  "+indext.strColumn+"  [iColType]  "+indext.iColType+"");
          	}
          	
          }
          
          //检索数据
          StringBuffer sbStr = new StringBuffer();

			// sbStr.append("内容=(").append(queryStr).append(")");
			sbStr.append("标题/10,内容/5+=(").append("长城").append(")");

			System.out.println("检索条件为： " + sbStr.toString());
			%>
      		<span>检索条件为：  <%=sbStr.toString()%></span><br>
      		<% 
			// 排序并裁减保存的结果记录数，同时自动设置"非精确命中点"模式，需要SERVER6.10.3300以上。
			conn.SetExtendOption("SORTPRUNE", "1000");
			// 有效排序结果记录数，即部分排序，按排序表达式排序的结果只保证前一部分记录是有序的，需要SERVER6.10.3300以上。
			conn.SetExtendOption("SORTVALID", "1000");
			// 2：在进行相关性排序时，用一个记录中命中词的单位向量长度，以及命中词的词频和作为记录的相关度。
			// 即当多条记录命中词的单位向量长度相等时，这些记录将再按命中词的词频和的降序排列；
			conn.SetExtendOption("RELEVANTMODE", "2");
			// 如果值为1，则表示对排序表达式中不存在的字段，按字段无值时情况处理(记录将排在后面)；否则报错。需要SERVER6.50.4000以上。
			conn.SetExtendOption("SORTMISCOL", "1");

			trsrs = conn
					.executeSelect("REG_BUS_ENT", sbStr.toString(), false);
			int countPerPage = 10;
			int currentPage = 1;
			int totalPage;
			trsrs.setPageSize(countPerPage);
			trsrs.setPage(currentPage);
			
			long totalSize = trsrs.getRecordCount();
			if (totalSize > 0) {
				if (totalSize % countPerPage == 0) {
					totalPage = (int) totalSize / countPerPage;
				} else {
					totalPage = (int) totalSize / countPerPage + 1;
				}
			} else {
				totalPage=0;
			}
			System.out.println("查询结果：\n " + "结果总数为：" + totalSize + " \n"
					+ "总页数为：" + totalPage + "当前页数为： " + currentPage);
			%>
      		<span>结果总数为： <%=totalSize%> 总页数为： <%=totalPage%> 当前页数为：  <%=currentPage%></span><br>
      		<% 
          
          
          
  }
  catch(TRSException e)
  {
          System.out.println("ErrorCode: " + e.getErrorCode());
          
          System.out.println("ErrorString: " + e.getErrorString());
          %>
    		<span>ErrorCode:   <%=e.getErrorCode()%></span><br>
    		<span>ErrorString:   <%=e.getErrorString()%></span><br>
    		<% 
  }
  finally
  {            
          if (conn != null) conn.close();         
          conn = null;
  } %>
     <br>
  </body>
</html>
