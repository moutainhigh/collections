<%
	response.setContentType("text/xml; charset=utf-8");
	String filePath = application.getRealPath("")+"/WEB-INF/password.xml";
	java.io.File file = new java.io.File(filePath);
	org.dom4j.io.SAXReader reader = new org.dom4j.io.SAXReader();
	org.dom4j.Document document = reader.read(file);
	out.write(document.asXML());
%>