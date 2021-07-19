<%
/** Title:			infoview_resource_distribute.jsp
 *  Description:	分发表单发布资源文件
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			wenyh
 *  Created:		2006-08-28 14:36
 *  Vesion:			1.0
 *  Parameters:
 *		see infoview_resource_distribute.xml
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>

<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.infra.util.Reports" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSites" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CMyFile" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.infra.util.ExceptionNumber" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishFolder" %>
<%@ page import="com.trs.components.common.publish.persistent.element.PublishElementFactory" %>
<%@ page import="com.trs.components.common.publish.domain.distribute.FileDistributeShip" %>
<%@ page import="com.trs.components.common.publish.domain.publisher.PublishPathCompass" %>
<!------- WCM IMPORTS END ------------>

<%@ page import="java.io.File" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<%@include file="../include/public_server.jsp"%>
<%!
	private String m_sJsEncoding = "UTF-8";

	private char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'A', 'B', 'C', 'D', 'E', 'F' };

	public String char2hex(int j) {
		char[] buf = new char[6];
		for (int i = buf.length - 1; i >= 0; i--) {
			buf[i] = DIGITS[j & 0xF];
			j >>>= 4;
		}
		buf[0] = '\\';
		buf[1] = 'u';
		return new String(buf);
	}
	public String getEscapeStr(File file) {
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader reader = null;
		try {
			int c = 0;
			StringBuffer sb = new StringBuffer((int) file.length() * 2);
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis, m_sJsEncoding);
			reader = new BufferedReader(isr);
			int indexByte = -1;
			int firstByte = 0;
			boolean isAscii = false;
			while ((c = reader.read()) != -1) {
				/*
				indexByte++;
				if (indexByte == 0 && c == -17) {
					firstByte = -17;
					continue;
				}
				if (indexByte == 1 && firstByte == -17) {
					if (c == -69) {
						isAscii = true;
						continue;
					} else {
						sb.append((char) firstByte);
						sb.append((char) c);
					}
				}
				if (indexByte == 3 && isAscii) {
					continue;
				}
				*/
				if (c < 0x80) {
					sb.append((char) c);
				} else if (c != 0xFEFF) {
					sb.append(char2hex(c));
				}
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "";
	}
	private void outContent(String srcPath, StringBuffer out) throws Exception{
		File file = new File(srcPath);
		if(!file.exists() || !file.isFile())return;
		String fn = file.getAbsolutePath();
		if(!fn.endsWith(".js"))return;
		out.append("/**--");
		out.append(fn.replaceAll("\\\\", "/").replaceAll(".*/([^/]*.js)", "$1"));
		out.append("--**/\n");
		out.append(getEscapeStr(file));
		out.append("\n");
	}

	private String getCurrFilePath(HttpServletRequest oRequest, ServletContext application){
        String sRequestURI = oRequest.getRequestURI();
        String sContextPath = oRequest.getContextPath();
        String sServletPath = sRequestURI.substring(sContextPath.length());
        String sAbsolutePagePath = CMyFile.extractFilePath(application.getRealPath(sServletPath));
		return CMyString.setStrEndWith(sAbsolutePagePath, File.separatorChar);
	}


%>
<%
	String siteIds = currRequestHelper.getString("SiteIds");
	if(CMyString.isEmpty(siteIds)){
		return;
	}

	WebSites sites = WebSites.findByIds(loginUser,siteIds);
	if(sites.isEmpty()){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,  CMyString.format(LocaleServer.getString("resource_distribute.id.zero","没有找到指定的站点，Id[{0}]"),new String[]{siteIds}));
	}

	final String sufixPath = File.separator + "images" + File.separator + "infoview";
	//String srcPath = CMyFile.extractFilePath(application.getRealPath(request.getServletPath()));
	String srcPath = getCurrFilePath(request, application);
	srcPath += "infoview_pub";

	//合并2个js到infoview_pub目录
	String wcmPath = CMyString.setStrEndWith(application.getRealPath("/"), File.separatorChar);
	String sLibJsFile = srcPath + File.separator + "wcmlib.js";
	String sInfoViewJsFile = srcPath + File.separator + "infoview_pub.js";	
	String[] libJsPaths = {
		"app/js/easyversion/cssrender.js",
		"app/js/easyversion/lightbase.js",
		"app/js/easyversion/ajax.js",
		"app/js/easyversion/crashboard.js",
		"app/js/easyversion/bubblepanel.js",
		"app/js/easyversion/calendar3.js",
		"app/js/easyversion/position.js",
		"app/js/easyversion/xmlhelper.js",
		"app/js/easyversion/parsexml.js",
		"app/js/easyversion/processbar.js"
	};
	StringBuffer outx = new StringBuffer();
	for(int i=0;i<libJsPaths.length;i++){
		outContent(wcmPath + libJsPaths[i].replace('/', File.separatorChar), outx);
	}
	
	CMyFile.writeFile(sLibJsFile, outx.toString());

	String[] infoviewJsPaths = {
		"app/infoview/infoview/infoview_pub.js",
		"app/infoview/infoview/infoview_elehelper.js",
		"app/infoview/infoview/infoview_xmldatahelper.js",
		"app/infoview/infoview/infoview_expression.js"
	};
	outx = new StringBuffer();
	for(int i=0;i<infoviewJsPaths.length;i++){
		outContent(wcmPath + infoviewJsPaths[i].replace('/', File.separatorChar), outx);
	}
	CMyFile.writeFile(sInfoViewJsFile, outx.toString());

	FilesMan fileMan = FilesMan.getFilesMan();
	String previewPath = fileMan.getPathConfigValue(FilesMan.FLAG_LOCALPREVIEW,FilesMan.PATH_LOCAL);
	previewPath = CMyString.setStrEndWith(previewPath,File.separatorChar);
	
	String pubPath = fileMan.getPathConfigValue(FilesMan.FLAG_LOCALPUB,FilesMan.PATH_LOCAL);
	pubPath = CMyString.setStrEndWith(pubPath,File.separatorChar);
	String targetPath = null;
	
	List srcFiles = new ArrayList();
	listFiles(srcPath,srcFiles,srcPath.length());
	//加上neweditor目录下的文件
	List editorFiles = new ArrayList();
	String sEditorPath = wcmPath + "app/neweditor".replace('/', File.separatorChar);
	listFiles(sEditorPath, editorFiles, sEditorPath.length());

	WebSite site;
	IPublishFolder folder;
	String dataPath = null;
	java.util.Iterator it;

	FileDistributeShip fileDistributeShip = new FileDistributeShip(new PublishPathCompass());
	fileDistributeShip.setBatchMode(true);
	String prefixPath = "images/infoview";	
	
	for(int i=0;i<sites.size();i++){		
		site = (WebSite)sites.getAt(i);
		if(site == null || site.isDeleted()) continue;
		
		folder = (IPublishFolder)PublishElementFactory.makeElementFrom(site);
		dataPath = folder.getDataPath();
		if(CMyString.isEmpty(dataPath)) continue;
		
		it = srcFiles.iterator();
		String fn;
		String fullFileName;
		while(it.hasNext()){
			fn = (String)it.next();
			fullFileName = srcPath+fn;
			File fFromFile = new File(fullFileName);
			if ("Thumbs.db".equals(fFromFile.getName())) {
				continue;
			}
			//privew dir.
			targetPath = previewPath + dataPath + sufixPath;				
			CMyFile.copyFile(fullFileName,targetPath+fn);

			//pub dir.
			targetPath = pubPath + dataPath + sufixPath;
 
			CMyFile.copyFile(fullFileName,targetPath+fn);
			
			//distribute.
			fileDistributeShip.distributeFile(fullFileName,folder,prefixPath+extractFileName(fn),false);
		}
		it = editorFiles.iterator();
		while(it.hasNext()){
			fn = (String)it.next();
			fullFileName = sEditorPath+fn;
			File fFile = new File(fullFileName);
			if ("Thumbs.db".equals(fFile.getName())) {
				continue;
			}
			//privew dir.
			targetPath = previewPath + dataPath + sufixPath;				
			CMyFile.copyFile(fullFileName,targetPath+fn);

			//pub dir.
			targetPath = pubPath + dataPath + sufixPath;
			CMyFile.copyFile(fullFileName,targetPath+fn);
			
			//distribute.
			fileDistributeShip.distributeFile(fullFileName,folder,prefixPath+extractFileName(fn),false);
		}
	}


%>

<%!
	private void listFiles(String srcPath,List fileList,int headLen) throws Exception{
		File file = new File(srcPath);		
		if(file.exists()){
			if(file.isFile()){
				String fn = file.getAbsolutePath();
				fileList.add(fn.substring(headLen,fn.length()));
			}else{
				File[] files = file.listFiles();
				if(files != null && files.length > 0){
					for(int i=0; i<files.length; i++){
						listFiles(files[i].getAbsolutePath(),fileList,headLen);
					}
				}
			}
		}
	}

	private String extractFileName(String fn){
		int ix = fn.lastIndexOf(File.separator);
		return (ix > 0)?fn.substring(0,ix):"";
	}
%>