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
    		<span>���ӷ������ɹ�����ʼ��ȡ���ݿ���ֶ���Ϣ</span><br>
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
          
          //��������
          StringBuffer sbStr = new StringBuffer();

			// sbStr.append("����=(").append(queryStr).append(")");
			sbStr.append("����/10,����/5+=(").append("����").append(")");

			System.out.println("��������Ϊ�� " + sbStr.toString());
			%>
      		<span>��������Ϊ��  <%=sbStr.toString()%></span><br>
      		<% 
			// ���򲢲ü�����Ľ����¼����ͬʱ�Զ�����"�Ǿ�ȷ���е�"ģʽ����ҪSERVER6.10.3300���ϡ�
			conn.SetExtendOption("SORTPRUNE", "1000");
			// ��Ч��������¼�������������򣬰�������ʽ����Ľ��ֻ��֤ǰһ���ּ�¼������ģ���ҪSERVER6.10.3300���ϡ�
			conn.SetExtendOption("SORTVALID", "1000");
			// 2���ڽ������������ʱ����һ����¼�����дʵĵ�λ�������ȣ��Լ����дʵĴ�Ƶ����Ϊ��¼����ضȡ�
			// ����������¼���дʵĵ�λ�����������ʱ����Щ��¼���ٰ����дʵĴ�Ƶ�͵Ľ������У�
			conn.SetExtendOption("RELEVANTMODE", "2");
			// ���ֵΪ1�����ʾ��������ʽ�в����ڵ��ֶΣ����ֶ���ֵʱ�������(��¼�����ں���)�����򱨴���ҪSERVER6.50.4000���ϡ�
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
			System.out.println("��ѯ�����\n " + "�������Ϊ��" + totalSize + " \n"
					+ "��ҳ��Ϊ��" + totalPage + "��ǰҳ��Ϊ�� " + currentPage);
			%>
      		<span>�������Ϊ�� <%=totalSize%> ��ҳ��Ϊ�� <%=totalPage%> ��ǰҳ��Ϊ��  <%=currentPage%></span><br>
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
