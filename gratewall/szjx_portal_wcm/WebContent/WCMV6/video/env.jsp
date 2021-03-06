<%--
/** 
 *  Description:	查看基于Java Servlet的Web程序的运行环境信息.
 *	Author:			Liu Shen(Islands)
 */
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.io.File"%>
<%@ page import="java.io.FilenameFilter"%>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.addHeader("Cache-Control","no-store"); //Firefox
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", -1);
response.setDateHeader("max-age", 0);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<title>IDS服务器环境信息</title>
<style type=text/css>
body {
	FONT-SIZE: 10px;
}
td {
	FONT-SIZE: 12px;
}
</style>
</head>

<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="30" style="border-bottom: 1px solid #1F4880;border-top: 1px solid #92B2E1;">
	<table width="100%" border="1" cellspacing="0" cellpadding="0">
		  <tr>
			<td width=150 align="center"><b>当前时间</b></td>
			<td>&nbsp;<%= CMyString.transDisplay(new java.sql.Timestamp(System.currentTimeMillis())) %></td>
		  </tr>
		  <tr>
			<td width=150 align="center"><b>应用目录</b></td>
			<td>&nbsp;<%= CMyString.transDisplay(application.getRealPath("/")) %></td>
		  </tr>
<%
	File dirWebInf = new File(application.getRealPath("/WEB-INF"));    
	File dirLib = new File(dirWebInf, "lib");
	String[] libJars = null;
	if (dirLib != null && dirLib.isDirectory()) {
		libJars = dirLib.list(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".jar") || name.endsWith(".zip");
			}
				});
	}
%>
		  <tr>
			<td width=150 valign="middle" align="center"><b>应用lib目录</b></td>
			<td>&nbsp;<%= CMyString.transDisplay(extractFiles(libJars)) %></td>
		  </tr>
		  <tr>
			<td width=150 align="center"><b>程序运行目录</b></td>
			<td>&nbsp;<%= CMyString.transDisplay(System.getProperty("user.dir")) %></td>
		  </tr>
		  <tr>
			<td width=150 align="center"><b>程序运行用户</b></td>
			<td>&nbsp;<%= CMyString.transDisplay(System.getProperty("user.name")) %></td>
		  </tr>
		  <tr>
			<td width=150 align="center"><b>JavaHome目录</b></td>
			<td>&nbsp;<%= CMyString.transDisplay(System.getProperty("java.home")) %></td>
		  </tr>
          <tr>
            <td align="center"><B>JDK信息</B></td>
            <td>&nbsp;[Vendor]: <%= CMyString.transDisplay(System.getProperty("java.vendor")) %>
            &nbsp;[Version]: <%= CMyString.transDisplay(System.getProperty("java.version")) %>
            &nbsp;[VM]: <%= CMyString.transDisplay(System.getProperty("java.vm.name")) %>, <%= CMyString.transDisplay(System.getProperty("java.vm.info")) %>
            </td>
          </tr>
		  <tr>
			<td width=150 align="center"><b>临时目录</b></td>
			<td>&nbsp;<%= CMyString.transDisplay(System.getProperty("java.io.tmpdir")) %></td>
		  </tr>
          <tr>
            <td width=150 align="center"><B>应用服务器</B></td>
            <td>&nbsp;<%= CMyString.transDisplay(application.getServerInfo()) %></td>
          </tr>
          <tr>
            <td width=150 align="center"><B>应用服务器支持规范</B></td>
<%
    String jspSpecVer;
	try {
		jspSpecVer = javax.servlet.jsp.JspFactory.getDefaultFactory().getEngineInfo().getSpecificationVersion(); 
	} catch (Throwable t) {
		jspSpecVer = "Unknown!";
	}
%>
            <td>&nbsp;Servlet规范版本: <%= CMyString.transDisplay(application.getMajorVersion()) %>.<%= CMyString.transDisplay(application.getMinorVersion()) %>&nbsp;JSP规范版本: <%= CMyString.transDisplay(jspSpecVer) %></td>
          </tr>
          <tr>
            <td width=150 align="center"><B>Session超时时间</B></td>
            <td>&nbsp;<%= CMyString.transDisplay(session.getMaxInactiveInterval() / 60) %>分钟</td>
          </tr>
          <tr>
            <td width=150 align="center"><b>应用服务器内存信息</b></td>
			<td>&nbsp;已分配内存: <b><%=CMyString.transDisplay(Runtime.getRuntime().totalMemory()/1048576)%></b> MB
			&nbsp;空闲内存: <b><%=CMyString.transDisplay((Runtime.getRuntime().freeMemory()/1048576))%></b> MB 
			&nbsp;最大可分配内存: <b><%=CMyString.transDisplay((Runtime.getRuntime().maxMemory()/1048576))%></b> MB 
			</td>
		  </tr>
		  <tr>
			<td width=150 align="center"><b>CPU个数</b></td>
			<td>&nbsp;<%=CMyString.transDisplay(Runtime.getRuntime().availableProcessors())%>个</td>
		  </tr>
		  <tr>
<% String osPatchInfo = System.getProperty("sun.os.patch.level"); %>
			<td width=150 align="center"><b>操作系统</b></td>
			<td>&nbsp;<%= CMyString.transDisplay(System.getProperty("os.name")) %>&nbsp;
			(<%= CMyString.transDisplay(System.getProperty("os.version")) %>;&nbsp;<%= CMyString.transDisplay(System.getProperty("os.arch")) %>).&nbsp;
			<%= CMyString.transDisplay((osPatchInfo == null) ? "" : osPatchInfo )%>
			</td>
		  </tr>
		  <tr>
			<td width=150 align="center"><b>字符编码</b></td>
			<td>&nbsp;<%=CMyString.transDisplay(System.getProperty("file.encoding")) %></td>
		  </tr>
		  <tr>
			<td width=150 align="center"><b>主机/IP</b></td>
			<td>&nbsp;<%= CMyString.transDisplay(java.net.InetAddress.getLocalHost()) %></td>
		  </tr>
    </table></td>
  </tr>
</table>

<%-- JVM系统属性 --%>
<table width="100%" border="1" cellspacing="0" cellpadding="0">
  <tr>
	<td colspan="2"><b>JVM系统属性</b></td>
  </tr>
<%
   	String key = null;
   	for (Iterator iter = jvmSysProps.keySet().iterator(); iter.hasNext(); ) {
   	    key = (String) iter.next();
%>
  <tr>
	<td width="150"><%= CMyString.transDisplay(key) %></td>
	<td><%= CMyString.transDisplay(jvmSysProps.getProperty(key)) %></td>
  </tr>
<%
	}
%>
</table>

</body>
</html>

<%!
	private java.util.Properties jvmSysProps = System.getProperties(); 

	private StringBuffer extractFiles(String[] fileNames) {
		StringBuffer sb = new StringBuffer(256);
		if (fileNames == null) {
			return sb;
		}
		int nFiles = fileNames.length;
		for (int i = 0; i < nFiles; i++) {
			sb.append(fileNames[i]).append(File.pathSeparatorChar);
		}
		return sb;
	}

//	private String jdkInfo = 
%>