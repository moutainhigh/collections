<%@ page import="java.io.IOException" %><%@ page import="java.io.OutputStreamWriter" %><%@ page import="java.io.StringReader" %><%@ page import="javax.servlet.ServletException" %><%@ page import="javax.servlet.ServletOutputStream" %><%@ page import="javax.servlet.http.HttpServlet" %><%@ page import="javax.servlet.http.HttpServletRequest" %><%@ page import="javax.servlet.http.HttpServletResponse" %><%@ page import="org.apache.batik.transcoder.Transcoder" %><%@ page import="org.apache.batik.transcoder.TranscoderException" %><%@ page import="org.apache.batik.transcoder.TranscoderInput" %><%@ page import="org.apache.batik.transcoder.TranscoderOutput" %><%@ page import="org.apache.batik.transcoder.image.JPEGTranscoder" %><%@ page import="org.apache.batik.transcoder.image.PNGTranscoder" %><%@ include file="../../include/public_server.jsp"%><%
	response.reset();//wenyh@2008-04-17 reset the reponse first.

	//设置编码，解决乱码问题。
	request.setCharacterEncoding("utf-8");
	
	//获取参数。
	String type = request.getParameter("type");
	String svg = new String(request.getParameter("svg").getBytes("ISO-8859-1"),"utf-8");
	String filename = request.getParameter("filename");
	//当文件名称为空时，使用默认chart为文件名称。
	filename = filename == null ? "chart" : filename;
	
	ServletOutputStream outPic = response.getOutputStream();
	
	if (null != type && null != svg) {
		svg = svg.replaceAll(":rect", "rect");
		String ext = "";
		Transcoder t = null;
		if (type.equals("image/png")) {
			ext = "png";
			t = new PNGTranscoder();
		} else if (type.equals("image/jpeg")) {
			ext = "jpg";
			t = new JPEGTranscoder();
		} else if (type.equals("image/svg+xml"))
			ext = "svg";
		response.addHeader("Content-Disposition", "attachment; filename="
				+ filename + "." + ext);
		response.addHeader("Content-Type", type);

		if (null != t) {
			TranscoderInput input = new TranscoderInput(new StringReader(
					svg));
			TranscoderOutput output = new TranscoderOutput(outPic);

			try {
				t.transcode(input, output);
			} catch (TranscoderException e) {
				outPic.print("转码失败,查看网络日志获取更多信息。");
				e.printStackTrace();
			}
		} else if (ext.equals("svg")) {
			OutputStreamWriter writer = new OutputStreamWriter(outPic, "utf-8");
			writer.append(svg);
			writer.close();
		} else
			outPic.print("非可下载类型: " + type);
	} else {
		response.addHeader("Content-Type", "text/html");
		outPic.println("参数错误！");
	}
	//outPic.flush();
	if(outPic != null){
		outPic.close();
	}
%>