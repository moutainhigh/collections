<%@include file="../include/public_server.jsp"%><%
/**
 *	Description:	WCM5.2 自定义表单，返回表单中图片的二进制内容。
	Author:			fcr
	Created:		2006.06.12 20:36:04
	Vesion:			1.0
	Parameters:
 		src			文件名
 		InfoViewId	表单ID
	Update Logs:
		2006.06.12	fcr created
 */

	String	sFileName	= request.getParameter("src");
	if (sFileName == null) {
	    return;
	}
	
	//安全性问题的处理 FilesMan.mapFilePath
	if (sFileName.indexOf("/") >= 0 || sFileName.indexOf("\\") >= 0) {
		return;
	}

 	int		iInfoViewId	= 0;
	String	sInfoViewId	= request.getParameter("InfoViewId");
	try {
	    iInfoViewId	= Integer.parseInt(sInfoViewId);
	} catch (Exception e) {
	    return;
	}

	com.trs.components.infoview.persistent.InfoView	aInfoView	= com.trs.components.infoview.persistent.InfoView.findById(iInfoViewId);
	if (aInfoView == null) {
	    return;
	}

	com.trs.components.infoview.InfoViewMgr	ivManager	= (com.trs.components.infoview.InfoViewMgr) com.trs.DreamFactory.createObjectById("InfoViewMgr");
	sFileName	= ivManager.getAbsolutePath(aInfoView, sFileName);
	if (sFileName == null) {
	    return;
	}

	//wenyh@2009-07-31 修正响应头的设置,原来的做法看上去在weblogic下有些问题
	response.reset();
	String sImageType = com.trs.infra.util.CMyFile.extractFileExt(sFileName).trim().toLowerCase();
	if(sImageType.equals("jpe") || sImageType.equals("jpg")) sImageType="jpeg";
	response.setContentType("image/"+ sImageType);

	javax.servlet.ServletOutputStream outx = response.getOutputStream();
	java.io.FileInputStream	fis = null;
	try {
	    fis = new java.io.FileInputStream(sFileName);
	    int		length	= 0;
	    byte[]	buffer	= new byte[4096];
		while ((length = fis.read(buffer)) != -1) {
		    outx.write(buffer, 0, length);
		}
		outx.close();
		fis.close();
		fis = null;
	} catch (Exception e) {
	    //Ignore
	} finally {
	    if (fis != null) {
	        try {
	            fis.close();
	        } catch (Exception e) {
	    	    //Ignore
	    	}
	    }
	}
%>