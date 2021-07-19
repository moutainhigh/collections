<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="java.io.BufferedInputStream" %>
<%@ page import="java.io.BufferedOutputStream" %>
<%@ page import="java.io.FileOutputStream" %>
<%@include file="../include/public_server.jsp"%>
<%
	//加载文件
	uploadFile(request, application);
	out.print("welcome");
%>
<%!
	private void uploadFile(HttpServletRequest request, ServletContext application) throws Exception {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            bis = new BufferedInputStream(request.getInputStream());           
			//bos = new BufferedOutputStream(new FileOutputStream(application.getRealPath("/WCMV6/images/login/background/temp.jpg")));
            byte[] buffer = new byte[1024];
			String bufferString = "";
            int len = 0;
            while ((len = bis.read(buffer, 0, buffer.length)) > 0) {
                //bos.write(buffer, 0, len);
				bufferString += new String(buffer);
            }
        } catch (Exception e) {
			e.printStackTrace();
            throw e;
        } finally {
            if(bis != null){
                bis.close();
            }
            if(bos != null){
                bos.close();
            }
        }
    }
%>