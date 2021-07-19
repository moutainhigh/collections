package com.gwssi.rodimus.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.gwssi.ebaic.common.util.UploadUtil;
import com.gwssi.rodimus.config.ConfigUtil;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.FileException;
import com.gwssi.rodimus.exception.UploadException;

/**
 * 手机文件处理。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class MobileFileUtil {
	
	/**
	 * 手机文件上传。
	 * 
	 * @param request
	 * @return
	 */
	public static List<Map<String,String>> upload(HttpServletRequest request){
		// 保存文件
		String datePath = sdf.format( new java.util.Date() );
		datePath = datePath.replace("-", File.separator);
		String path = "mobile" + File.separator + datePath ;
		try {
			// 执行上传
			List<Map<String,String>> files = upload(path, request, true);
			// 生成缩略图
			for(Map<String,String> file : files ){
				String fileId = file.get("fileId");
				//System.out.println("上传文件："+fileId);
				String thumbFileId = UploadUtil.buildThumb(path, fileId, "jpg");
				//System.out.println("缩略图："+thumbFileId);
				file.put("thumbFileId", thumbFileId);
			}
			return files;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new FileException(e.getMessage());
		}
	}
	
	
	public static List<Map<String,String>> upload(String path, HttpServletRequest request,boolean intoDB)throws Exception{
		String fileRootPath = ConfigUtil.get("file.rootPath");
		String tempDirPath = ConfigUtil.get("upload.tmp");
		File tmpDir = new File(fileRootPath + tempDirPath);
		
		if(!tmpDir.exists()){
			tmpDir.mkdirs();
		}
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		multipartResolver.setDefaultEncoding("utf-8");
		//设置上传缓存目录
		multipartResolver.setUploadTempDir(new FileSystemResource(tmpDir));
		// 检查form是否有enctype="multipart/form-data"
		if (!multipartResolver.isMultipart(request)) {			
			throw new UploadException("上传失败：没有获取到文件数据。");
		}
		// 定义返回值（上传文件的fileid）
		List<Map<String,String>> ret = new ArrayList<Map<String,String>>();

		try {
			MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			//MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			MultipartHttpServletRequest multiRequest = resolver.resolveMultipart(request);
			Map<String, MultipartFile> fileMap = multiRequest.getFileMap();   
			
			for (String key : fileMap.keySet()) {
				// 处理单个文件
				MultipartFile file = fileMap.get(key);
				Map<String,String> idMap = new HashMap<String,String>();
				
				String fileId= UUIDUtil.getUUID();//计算文件摘要值
				idMap.put("fileId", fileId);
				
				String name = file.getOriginalFilename();//文件名
				String suffix = name.substring(name.lastIndexOf(".")+1, name.length());//后缀名
				
				//创建保存目录			
				File uploadDir = new File(FileUtil.getRootPath()+path);
				//System.out.println("文件上传目录："+uploadDir.getAbsolutePath());
				if(!uploadDir.exists()){
					uploadDir.mkdirs();
				}
				File newFile = new File(uploadDir, fileId+"."+suffix);//fileid作为文件名，
				//上传文件
				file.transferTo(newFile);
				//System.out.println("上传文件："+newFile.getAbsolutePath());
				if(intoDB){
					String contentType = file.getContentType();
					long size = file.getSize();
					String fileName = file.getOriginalFilename();
					FileUtil.saveFile(fileId,suffix,path,contentType,size,fileName);
				}
				//生成缩略图
				//String thumbId=buildThumb(path, fileId, suffix);
				//idMap.put("thumbId", thumbId);
				ret.add(idMap);
			}
			

	    } catch (Exception e) { 
	    	e.printStackTrace();
	    	throw new RuntimeException("上传失败："+e.getMessage());
	    }
		return ret;
	}

	
	/**
	 * 手机文件下载。
	 * 
	 * @param response
	 */
	public static void download(String fileId , HttpServletResponse response){
		String phyFilePath = FileUtil.getFilePhyPath(fileId);
		String contentType = "";
		String fileName = "";

		String sql = "select f.* from be_wk_file f where f.file_id = ?";
		Map<String,Object> fileInfo = DaoUtil.getInstance().queryForRow(sql, fileId);
		if(fileInfo!=null){
			contentType = StringUtil.safe2String(fileInfo.get("contentType"));
			fileName = fileId ;
			
			String suffixName = StringUtil.safe2String(fileInfo.get("suffixName"));
			if(!StringUtil.isBlank(suffixName)){
				fileName = fileName + "." + StringUtil.safe2String(fileInfo.get("suffixName"));
			}
		}
		downloadFile(contentType, fileName, phyFilePath, response);
	}
	
	private static void downloadFile(String contentType, String fileName,String phyFilePath, HttpServletResponse response){
		if(StringUtils.isBlank(phyFilePath)){
			throw new FileException("文件不存在。");
		}
		
		FileInputStream fis = null;
		OutputStream os = null;
//		if(response.isCommitted()){}
		try {
			File file = new File(phyFilePath);
			fis = new FileInputStream(file);
			os = response.getOutputStream();
			//设置头信息，采用文件形式下载
			if(StringUtil.isNotBlank(contentType)){
				response.setContentType(contentType);
			}
			if(StringUtil.isNotBlank(fileName)){
				response.addHeader("Content-Disposition", "attachment;filename="+fileName);
			}
		    response.addHeader("Content-Length", "" + file.length());
		    
			byte[] data = new byte[10240];
			int len = 0;
			while ((len = fis.read(data)) > 0) {
				os.write(data, 0, len);
			}
			os.flush();
		} catch (IOException e) {
			throw new FileException("获取文件失败："+e.getMessage());
		}finally{
			IOUtils.closeQuietly(fis);
			IOUtils.closeQuietly(os);
		}
	}
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-");
	
	
	public static void main(String[] args){
		String datePath = sdf.format( new java.util.Date() );
		datePath = datePath.replace("-", File.separator);
		System.out.println(datePath);
	}
}

