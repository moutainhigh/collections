<%@ page contentType="text/javascript;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="java.io.File" %>
<%@ page import="java.util.List,java.util.ArrayList" %>
<%@ page import="com.trs.infra.util.CMyFile" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%
try{
	String sPath = request.getRequestURI().replaceAll("/[^/]*(/.*/)[^/]*.jsp", "$1");
	String realPath = application.getRealPath(sPath);
	String sToCombine = CMyString.showNull(request.getParameter("s")).toLowerCase();
	sToCombine = sToCombine;
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
					String sFileEncoding = com.trs.infra.util.CharsetDetector.detect(fn);
					out.append(CMyFile.readFile(fn, sFileEncoding));
					out.append("\n");
				}
			}else{
				if(CMyString.isEmpty(sIncPathes)){
					outContent(file.getAbsolutePath(), out);
				}
				String[] arrFileName = sIncPathes.split(",");
				String srcFile = file.getAbsolutePath() + File.separator;
				for(int i=0; i<arrFileName.length; i++){
					if(arrFileName[i].indexOf("\\") >= 0){
						String[] ChlFileNames = arrFileName[i].split("\\\\");
						String ChlFileName = "";
						for(int index = 0; index < ChlFileNames.length; index++){
							ChlFileName += ChlFileNames[index] + (index == ChlFileNames.length -1 ? "" : File.separator);
						}
						arrFileName[i] = ChlFileName;
					}
					outContent(srcFile + arrFileName[i], out);
				}
			}
		}
	}

//	private void outContent(String srcPath, StringBuffer out) throws Exception{
//		File file = new File(srcPath);
//		if(!file.exists()){
//			return;
//		}
//		if(file.isFile()){
//			String fn = file.getAbsolutePath();
//			if(fn.endsWith(".js")){
//				out.append("/**--");
//				out.append(fn.replaceAll("\\\\", "/").replaceAll(".*/([^/]*.js)", "$1"));
//				out.append("--**/\n");
//				out.append(CMyFile.readFile(fn, "UTF-8"));
//				out.append("\n");
//			}
//		}else{
//			File[] files = file.listFiles();
//			if(files != null && files.length > 0){
//				for(int i=0; i<files.length; i++){
//					outContent(files[i].getAbsolutePath(), out);
//				}
//			}
//		}
//	}

    private void outContent(String srcPath, StringBuffer out) throws Exception {
        File file = new File(srcPath);
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            String fn = file.getAbsolutePath();
            if (fn.endsWith(".js")) {
                out.append("/**--");
                out.append(fn.replaceAll("\\\\", "/").replaceAll(
                        ".*/([^/]*.js)", "$1"));
                out.append("--**/\n");
				String sFileEncoding = com.trs.infra.util.CharsetDetector.detect(fn);
                out.append(CMyFile.readFile(fn, sFileEncoding));
                out.append("\n");
            }
            return;
        }
        List dirs = new ArrayList();
        File[] files = file.listFiles();
        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    dirs.add(files[i]);
                    continue;
                }
                outContent(files[i].getAbsolutePath(), out);
            }
        }
        for (int i = 0, length = dirs.size(); i < length; i++) {
            File dir = (File) dirs.get(i);
            outContent(dir.getAbsolutePath(), out);
        }
    }

%>