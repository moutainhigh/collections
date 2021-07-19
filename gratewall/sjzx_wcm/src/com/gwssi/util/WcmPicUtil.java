package com.gwssi.util;
import com.trs.infra.support.file.FilesMan;
import com.trs.infra.util.CMyFile;
import com.trs.infra.common.WCMException;
import com.trs.infra.util.ExceptionNumber;
import com.trs.presentation.util.LoginHelper;
import com.trs.presentation.util.RequestHelper;
import com.trs.cms.auth.persistent.User;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.trs.cms.ContextHelper;

public class WcmPicUtil {

	private void picReader(HttpServletRequest request, HttpServletResponse response) throws IOException, WCMException {
		
		
		ServletContext application = request.getSession().getServletContext();
			//3.参数获取
		RequestHelper currRequestHelper = new RequestHelper(request, response, application);
		currRequestHelper.doValid();
	
		String sFileName = currRequestHelper.getString("FileName");
		if(sFileName == null || sFileName.trim().length()==0)
			throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, "传入文件名为空！");		
		try{
			//只能使用FilesMan获取WCMData目录下的文件.
			if(true){
				com.trs.infra.util.image.CMyImage imageTool = new com.trs.infra.util.image.CMyImage();
				FilesMan currFilesMan = FilesMan.getFilesMan();
				String sFilePath = currFilesMan.mapFilePath(sFileName, FilesMan.PATH_LOCAL);
	
				// 则需要判断是否有默认的缩略图，如果有则直接显示，如果没有则需要进行生成
				//HDG @2011-2-11 如果传入缩略宽度，则需要根据文件名取出缩略图
				int nScaleWidth = currRequestHelper.getInt("ScaleWidth",0);
				if("1".equals(currRequestHelper.getString("ScaleDefault")))
					nScaleWidth = imageTool.DEFAULT_WIDTH;
				//重新拷贝这个文件的原因是因为微博图片小于500kb时可能也需要裁减。
	
				int[] size = imageTool.getImageSize(sFilePath+sFileName);
			
	
				if(nScaleWidth>0 && nScaleWidth < size[0]){// && imageTool.ifNeedScale(sFileName)){
					String sTempFileName = imageTool.getThumbFileName(sFileName,nScaleWidth);
					if(!CMyFile.fileExists(sFilePath+sTempFileName))
						imageTool.scaleImg(sFileName,nScaleWidth);
					sFileName = sTempFileName;
				}
				sFileName = sFilePath + sFileName;
			}else{
				java.io.File f = new java.io.File(sFileName);
				if(f.exists()){
					sFileName = f.getAbsolutePath();
				}else{
					FilesMan currFilesMan = FilesMan.getFilesMan();
					sFileName = currFilesMan.mapFilePath(sFileName, FilesMan.PATH_LOCAL) + sFileName;
				}
			}
		}catch(Throwable ex){
			if(!sFileName.endsWith("introduce.gif")){
				throw new WCMException("写文件失败", ex);
			}
		}
		
	//8.输出
		response.reset();//wenyh@2007-07-23 reset the reponse first.
		String sImageType = CMyFile.extractFileExt(sFileName).trim();
		if(sImageType.equals("jpe") || sImageType.equals("jpg")) sImageType="jpeg";
		response.setContentType("image/"+ sImageType);
		//response.setHeader("Content-Disposition", "filename=\"" + CMyFile.extractFileName(sFileName) + "\"");
		
		
		// 打开指定文件的流信息 
		java.io.FileInputStream fileInputStream = null; 
		java.io.OutputStream os = null;
		try{
			fileInputStream = new java.io.FileInputStream( sFileName );
			// 写出流信息 
			byte buffer[] = new byte[65000];
			int i; 
			os = response.getOutputStream();
			while( (i=fileInputStream.read(buffer, 0, 65000))>0 ) { 
				os.write(buffer, 0, i);
			} 
			os.flush();
		}catch(Exception ex){
			throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION, "写文件失败", ex);
		}finally{
			if(fileInputStream!=null){
				try{
					fileInputStream.close(); 
					fileInputStream = null;
				}catch(Exception e){
					//Ignore.
				}
			}
			if(os != null){
				try{
					os.close();
					os = null;
				}catch(Exception e){
					//Ignore.
				}
			}
			//wenyh@2007-08-23 17:30 去掉out.clear(),不需要
			//out.clear();
		}
	}
	
}
