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
			//3.������ȡ
		RequestHelper currRequestHelper = new RequestHelper(request, response, application);
		currRequestHelper.doValid();
	
		String sFileName = currRequestHelper.getString("FileName");
		if(sFileName == null || sFileName.trim().length()==0)
			throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, "�����ļ���Ϊ�գ�");		
		try{
			//ֻ��ʹ��FilesMan��ȡWCMDataĿ¼�µ��ļ�.
			if(true){
				com.trs.infra.util.image.CMyImage imageTool = new com.trs.infra.util.image.CMyImage();
				FilesMan currFilesMan = FilesMan.getFilesMan();
				String sFilePath = currFilesMan.mapFilePath(sFileName, FilesMan.PATH_LOCAL);
	
				// ����Ҫ�ж��Ƿ���Ĭ�ϵ�����ͼ���������ֱ����ʾ�����û������Ҫ��������
				//HDG @2011-2-11 ����������Կ�ȣ�����Ҫ�����ļ���ȡ������ͼ
				int nScaleWidth = currRequestHelper.getInt("ScaleWidth",0);
				if("1".equals(currRequestHelper.getString("ScaleDefault")))
					nScaleWidth = imageTool.DEFAULT_WIDTH;
				//���¿�������ļ���ԭ������Ϊ΢��ͼƬС��500kbʱ����Ҳ��Ҫ�ü���
	
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
				throw new WCMException("д�ļ�ʧ��", ex);
			}
		}
		
	//8.���
		response.reset();//wenyh@2007-07-23 reset the reponse first.
		String sImageType = CMyFile.extractFileExt(sFileName).trim();
		if(sImageType.equals("jpe") || sImageType.equals("jpg")) sImageType="jpeg";
		response.setContentType("image/"+ sImageType);
		//response.setHeader("Content-Disposition", "filename=\"" + CMyFile.extractFileName(sFileName) + "\"");
		
		
		// ��ָ���ļ�������Ϣ 
		java.io.FileInputStream fileInputStream = null; 
		java.io.OutputStream os = null;
		try{
			fileInputStream = new java.io.FileInputStream( sFileName );
			// д������Ϣ 
			byte buffer[] = new byte[65000];
			int i; 
			os = response.getOutputStream();
			while( (i=fileInputStream.read(buffer, 0, 65000))>0 ) { 
				os.write(buffer, 0, i);
			} 
			os.flush();
		}catch(Exception ex){
			throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION, "д�ļ�ʧ��", ex);
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
			//wenyh@2007-08-23 17:30 ȥ��out.clear(),����Ҫ
			//out.clear();
		}
	}
	
}
