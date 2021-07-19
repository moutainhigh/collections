<%@ page contentType="text/javascript;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="java.io.File" %>
<%@ page import="com.trs.infra.util.CMyFile" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%
try{
	String sPath = request.getRequestURI().replaceAll("/[^/]*(/.*/)[^/]*.jsp", "$1");
	String realPath = application.getRealPath(sPath);
	String sToCombine = CMyString.showNull(request.getParameter("s")).toLowerCase();
	StringBuffer outx = new StringBuffer();
	outContentLev1(realPath, outx, sToCombine);
	out.clear();
	out.println(outx.toString());
	/*
	String sToFile = CMyString.isEmpty(sToCombine) ? "data.js"
							: (sToCombine.replaceAll(",", "-")+".js");
	CMyFile.writeFile(new File(realPath + File.separatorChar + sToFile).getAbsolutePath(),
		outx.toString(), m_sJsEncoding);
	//*/
}catch(Throwable t){
	out.clear();
	t.printStackTrace();
	return;
}
%>
<%!
	private String m_sJsEncoding = "UTF-8";
	private void outContentLev1(String srcPath, StringBuffer out, String sIncPathes) throws Exception{
		File file = new File(srcPath);
		if(file.exists()){
			if(file.isFile()){
				sIncPathes = ',' + sIncPathes + ',';
				if(sIncPathes.indexOf(",,")==-1){
					return;
				}
				String fn = file.getAbsolutePath();
				if(fn.endsWith(".js")){
					out.append("/**--");
					out.append(fn.replaceAll("\\\\", "/").replaceAll(".*/([^/]*.js)", "$1"));
					out.append("--**/\n");
					out.append(CMyFile.readFile(fn, m_sJsEncoding));
					out.append("\n");
				}
			}else{
				File[] files = file.listFiles();
				if(files != null && files.length > 0){
					for(int i=0; i<files.length; i++){
						String tmp = ','+files[i].getName().toLowerCase()+',';
						if(!CMyString.isEmpty(sIncPathes) &&
							(","+sIncPathes+",").indexOf(tmp)==-1){
							continue;
						}
						outContent(files[i].getAbsolutePath(), out);
					}
				}
			}
		}
	}
	private void outContent(String srcPath, StringBuffer out) throws Exception{
		File file = new File(srcPath);
		if(file.exists()){
			if(file.isFile()){
				String fn = file.getAbsolutePath();
				if(fn.endsWith(".js")){
					out.append("/**--");
					out.append(fn.replaceAll("\\\\", "/").replaceAll(".*/([^/]*.js)", "$1"));
					out.append("--**/\n");
					out.append(CMyFile.readFile(fn, "UTF-8"));
					out.append("\n");
				}
			}else{
				File[] files = file.listFiles();
				if(files != null && files.length > 0){
					for(int i=0; i<files.length; i++){
						outContent(files[i].getAbsolutePath(), out);
					}
				}
			}
		}
	}

%>