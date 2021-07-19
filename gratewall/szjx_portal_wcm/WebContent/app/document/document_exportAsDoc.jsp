<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_for_dialog.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection" %>
<%@ page import="com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter" %>
<%@ page import="com.artofsolving.jodconverter.DocumentConverter" %>
<%@ page import="com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection" %>
<%@ page import="java.io.File" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.cms.content.HTMLContent" %>
<%@ page import="com.trs.infra.util.CMyFile" %>
<%@ page import="com.trs.infra.util.html.HtmlElement" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="java.io.FileOutputStream" %>
<%@ page import="com.trs.infra.util.html.HtmlElement" %>
<%@ page import="com.trs.infra.util.html.HtmlElementFinder" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="java.awt.Image" %>
<%@ page import="com.sun.image.codec.jpeg.JPEGCodec" %>
<%@ page import="com.sun.image.codec.jpeg.JPEGEncodeParam" %>
<%@ page import="com.sun.image.codec.jpeg.JPEGImageEncoder" %>
<%@ page import="javax.imageio.ImageIO" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定,登录校验,参数获取,都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据) 
	int nDocumentId = currRequestHelper.getInt("DocumentId",0);
	Document document = Document.findById(nDocumentId);
	if(document == null){
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, CMyString.format(LocaleServer.getString("document_exportAsDoc.jsp.notfinddocid", "没有找到ID为{0}的文档！"), new int[]{nDocumentId}));
	}
//5.权限校验
//6.业务代码
	try{
		FilesMan filesMan = FilesMan.getFilesMan();
		String path = filesMan.getNextFileName(
				FilesMan.FLAG_SYSTEMTEMP, "html", null, true);

		//组装生成的html模板
		String sPreContent = "<HTML><HEAD><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /></HEAD><BODY>";
		
		String sRootPath = "http://" + request.getServerName() + ":" + request.getServerPort() + "/wcm/temp/";
		String sAblPath = ConfigServer.getServer().getInitProperty("WCM_PATH");
		sAblPath = CMyString.setStrEndWith(sAblPath,File.separatorChar);
		String sContent = getContent(document.getHtmlContent(),filesMan,sRootPath,sAblPath);
		String sPostContent = "</BODY></HTML>";
		sContent = sPreContent + sContent + sPostContent;
		FileOutputStream fileoutputstream = null;		
		fileoutputstream = new FileOutputStream(path);// 建立文件输出流
		byte tag_bytes[] = sContent.getBytes("UTF-8");
		fileoutputstream.write(tag_bytes);
		fileoutputstream.close();

		
		String sHost = ConfigServer.getServer().getSysConfigValue(
				"OCR_OPENOFFICE_HOST", "127.0.0.1");
		String sPort = ConfigServer.getServer().getSysConfigValue(
				"OCR_OPENOFFICE_PORT", "8100");
		File inputFile = new File(path);

		path = filesMan.getNextFileName(
				FilesMan.FLAG_SYSTEMTEMP, "doc", null, true);
		File outputFile = new File(path);
		
		OpenOfficeConnection conn = new SocketOpenOfficeConnection(sHost,
				Integer.parseInt(sPort));
		conn.connect();
		DocumentConverter converter = new OpenOfficeDocumentConverter(conn);
		converter.convert(inputFile, outputFile);
		conn.disconnect();
	//7.结束
		out.clear();
		out.println(outputFile.getName());
	}catch (java.lang.UnsupportedClassVersionError e) {
		throw new WCMException(
				LocaleServer.getString("document_exportAsDoc.jsp.jdkversiontoolow", "您的JDK版本过低，使用OpenOffice服务需要升级JDK版本到1.5及以上版本！"), e);
	} catch (Exception ex) {
		if (ex instanceof java.net.ConnectException) {
			throw new java.net.ConnectException(
					LocaleServer.getString("document_exportAsDoc.jsp.connectopoficeserverfailure","连接OpenOffice服务器失败，请检查OpenOffice是否已安装或者是否已经启动该服务！"));
		}
	} 
%>
<%!
	public String getContent(String strContent, FilesMan filesMan, String sRoot, String sPreString) throws Exception {
		// 处理正文图片,html文件转为word文件时需要遍历img元素，将图片暂时拷贝到wcm / temp目录下，目前openoffice只支持//http格式路径的图片转换
		HtmlElementFinder imgFinder = new HtmlElementFinder(strContent);
		HtmlElement element = null;
		String sImgSrc = null;
		String sImgPath = null;
		String sWidth=null,sHeight = null;
		File compressFile = null;
		Image srcFile = null;
		String sSourceUrl = null;
		while ((element = imgFinder.findNextElement("img", true)) != null) {
			int nWidth=0,nHeight = 0;
			sImgSrc = element.getAttributeValue("src");
			sWidth = element.getAttributeValue("width");
			sHeight = element.getAttributeValue("height");
			if(sWidth != null) nWidth = Integer.parseInt(sWidth);
			if(sHeight != null) nHeight = Integer.parseInt(sHeight);
			if(sImgSrc == null || ("").equals(sImgSrc.trim()) || ("null").equals(sImgSrc.trim())) continue;
			sImgPath = filesMan.mapFilePath(sImgSrc,FilesMan.PATH_LOCAL);
			if(nWidth >0 && nHeight > 0){
				/** 读取源文件 */
				compressFile = new File(sImgPath + sImgSrc);
				srcFile = ImageIO.read(compressFile);

				String path = filesMan.getNextFileName(
				FilesMan.FLAG_SYSTEMTEMP, "jpg", null, true);
				sSourceUrl = scale(srcFile,path,nWidth,nHeight);
			}else{
				sSourceUrl = sImgPath + sImgSrc;
			}
			CMyFile.copyFile(sSourceUrl,sPreString + "temp" + File.separatorChar + sImgSrc);
			element.setAttribute("src",sRoot + sImgSrc);
			imgFinder.putElement(element);
		}
		return imgFinder.getContent();
	}

	private String scale(Image srcFile,String newImage,int width, int height) throws Exception{
		/** 通过设定宽度和高度来压缩文件，Java二维作图采用直接颜色模型 */
		BufferedImage bImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		/** 编码对象渲染 */
		bImage.getGraphics().drawImage(srcFile, 0, 0, width, height, null);

		/** 定义输出流，指定输入到目标图片中 */
		FileOutputStream out = null;

		/** 输入到webpic出错次数为5，如果都失败，抛出异常；如果成功，进程睡眠30秒 */
		int n_maxRepeatTimes = 5;
		for (int i = 0; i < n_maxRepeatTimes; i++) {
			try {
				out = new FileOutputStream(newImage);
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				JPEGEncodeParam jep = JPEGCodec
						.getDefaultJPEGEncodeParam(bImage);
				encoder.encode(bImage, jep);

				// 判断图片信息是否有效
				File file = new File(newImage);
				if (file.length() > 20) {
					if (out != null)
						out.close();
					break;
				}
			} catch (Throwable e) {
				if (i == (n_maxRepeatTimes - 1)) {
					throw new WCMException("Compress image failed !", e);
				}
				Thread.sleep(30);
			} finally {
				if (out != null)
					out.close();
			}
		}

		return newImage;
	}
%>