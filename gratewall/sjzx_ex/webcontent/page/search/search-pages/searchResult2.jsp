<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.io.InputStreamReader" %>
<%@page import="java.io.BufferedReader" %>
<%@page import="java.net.URL" %>
<%@page import="java.util.zip.GZIPInputStream" %>
<%@page import="java.net.HttpURLConnection" %>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.net.URLDecoder"%>
<%!

    public String unescape(String src) {
	  StringBuffer tmp = new StringBuffer();
	  tmp.ensureCapacity(src.length());
	  int lastPos = 0, pos = 0;
	  char ch;
	  while (lastPos < src.length()) {
	   pos = src.indexOf("%", lastPos);
	   if (pos == lastPos) {
	    if (src.charAt(pos + 1) == 'u') {
	     ch = (char) Integer.parseInt(src
	       .substring(pos + 2, pos + 6), 16);
	     tmp.append(ch);
	     lastPos = pos + 6;
	    } else {
	     ch = (char) Integer.parseInt(src
	       .substring(pos + 1, pos + 3), 16);
	     tmp.append(ch);
	     lastPos = pos + 3;
	    }
	   } else {
	    if (pos == -1) {
	     tmp.append(src.substring(lastPos));
	     lastPos = src.length();
	    } else {
	     tmp.append(src.substring(lastPos, pos));
	     lastPos = pos;
	    }
	   }
	  }
	  return tmp.toString();
	 }
%>
<%
	String url =request.getParameter("url");

	if(url != null && !"".equals(url))
	{
		StringBuffer sb = new StringBuffer();
		HttpURLConnection connection = null;
		try
		{
			//System.out.println(url);
			url=unescape(url);
			System.out.println(url);
			if(url.indexOf("?") > -1)
			{
				String[] arr = url.split("\\?");
				url = arr[0] + "?"+ URLEncoder.encode(arr[1], "UTF-8");
				url = url.replaceAll("%3D","=");
				url = url.replaceAll("%26","&");
			}
			System.out.println(url);
			connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setDoOutput(true);
 			connection.setRequestProperty("Accept-Encoding", "deflate");
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String line = null;
			
			while((line = reader.readLine()) != null)
			{
				
			    sb.append(line);
			}
			System.out.println(sb.toString());
			reader.close();
			//response.setContentType("application/xml;charset=utf-8");  
			//response.setContentType("text/xml");
			response.getWriter().write(sb.toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(connection != null)
			{
				connection.disconnect();
			}
		}
	}

%>

