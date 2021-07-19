<%@ page import="java.io.File" %>
<%@ page import="com.trs.infra.util.CMyFile" %>
<%!
		
	public boolean isLegalPic(String picPath){
		if (!CMyFile.fileExists(picPath)) {
			return false;
		}
		File oPicFile = new File(picPath);
		if (!oPicFile.canRead()) {
			return false;
		}
		// 判断文件类型
		String sFileType = CMyFile.extractFileExt(picPath);
		if (!sFileType.equalsIgnoreCase("GIF")
				&& !sFileType.equalsIgnoreCase("PNG") && !sFileType.equalsIgnoreCase("JPG") && !sFileType.equalsIgnoreCase("JPEG")) {
		   return false;
		}
		
		long lFileSize = oPicFile.length();
		if (lFileSize / 1024 < 1 || lFileSize / 1024 / 1024 > 2) {
			return false;
		}
		
		return true;
	}
%>