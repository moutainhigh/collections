<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.gwssi.optimus.core.common.ConfigManager"%>
<%@ page import="java.io.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<title>文件预览</title>
</head>
<%
	out.clear();
	out = pageContext.pushBody();
	response.setContentType("image/jpeg");

	try {
		String rootDir = ConfigManager.getProperty("rootDir");
		String uploadTempDir = ConfigManager.getProperty("upload.tempDir");
		String uploadPath = rootDir + File.separator + uploadTempDir;
		
/* 		String rootDir = ConfigManager.getProperty("rootDir");
		String uploadTempDir = ConfigManager
				.getProperty("upload.tempDir"); */

		String imgPath1 = uploadPath + request.getParameter("fileName");
		String imgPath2 = "";
		//判断该路径下的文件是否存在
		File file = new File(imgPath1);
		if (file.exists() && !imgPath1.equals("")) {
			DataOutputStream temps = new DataOutputStream(
					response.getOutputStream());
			DataInputStream in = new DataInputStream(
					new FileInputStream(imgPath1));

			byte[] b = new byte[2048];
			while ((in.read(b)) != -1) {
				temps.write(b);
				temps.flush();
			}

			in.close();
			temps.close();
		} else {
			DataOutputStream temps = new DataOutputStream(
					response.getOutputStream());
			DataInputStream in = new DataInputStream(
					new FileInputStream(imgPath2));

			byte[] b = new byte[2048];
			while ((in.read(b)) != -1) {
				temps.write(b);
				temps.flush();
			}

			in.close();
			temps.close();
		}

	} catch (Exception e) {
		out.println(e.getMessage());
	}
%>
<body>
</body>
</html>