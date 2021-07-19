package com.gwssi.ebaic.common.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
//import org.safehaus.uuid.UUID;
//import org.safehaus.uuid.UUIDGenerator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.gwssi.rodimus.config.ConfigUtil;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.UploadException;
import com.gwssi.rodimus.util.FileUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.rodimus.util.UUIDUtil;


/**
 * 文件上传工具类。
 * 
 * @author lxb
 */
public class UploadUtil {

	/**
	 * 将文件上传至指定目录。
	 * 
	 * TODO 当前处理方式是基于流的吗？ 是否有必要限制同时只传一个文件？
	 * 
	 * @param path 要保存的文件路径
	 * @param request 
	 * @param intoDB 是否保存至数据库 false为不保存，true是保存
	 * @return 文件fileid
	 * @throws Exception
	 */
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
//			MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
//			//MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
//			MultipartHttpServletRequest multiRequest = resolver.resolveMultipart(request);
//			Map<String, MultipartFile> fileMap = multiRequest.getFileMap();   
			
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			Map<String, MultipartFile> fileMap = multiRequest.getFileMap();   
			
			for (String key : fileMap.keySet()) {
				// 处理单个文件
				MultipartFile file = fileMap.get(key);
				Map<String,String> idMap = new HashMap<String,String>();
				
				String fileId= getUUID();//计算文件摘要值
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


//	/**
//	 * 插入新的文件记录
//	 * 用于裁剪图
//	 * @param fileId
//	 * @param suffix_name
//	 * @param dest
//	 */
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public static void buildFileInfo(String fileId,String suffix_name,String dest){
//		if(StringUtils.isBlank(fileId)){
//			throw new OptimusException("无效的文件标识："+fileId);
//		}
//		
//		String sql="insert into be_wk_file(file_id, suffix_name, timestamp, file_path) values (?,?,sysdate,?)";
//		List params = new ArrayList();
//		params.add(fileId);
//		params.add(suffix_name);
//		params.add(dest);
//		DaoUtil.getInstance().execute(sql, params);
//	}
	
	/**
	 * 单张图片关联业务数据
	 * 如果已经创建过业务数据，更新业务数据
	 * 否则创建一条新的业务数据
	 * @param gid
	 * @param fileId
	 * @param info
	 * @return
	 */
	public static String singleFileMatchBusiRecord(String gid, String fileId, Map<String,Object> info){
		if(StringUtils.isBlank(gid)||StringUtils.isBlank(fileId)
				|| info==null || info.isEmpty()){
			throw new RuntimeException("关联业务数据失败，无效的参数");
		}
		// info : {fId=cb8c99baab024d1586b95c00740b376f, thumbFileId=398f729810df4594bb0fa9ae40c3e263, 
		//	refText=乘车软件公司, dataType=, refId=36C79EEEFD773E27E055000000000001, categoryId=4}
		
		String categoryId = StringUtil.safe2String(info.get("categoryId")) ;
		if(StringUtil.isBlank(categoryId)){
			throw new RuntimeException("categoryId不能为空。");
		}
		String sql = "select c.* from sys_upload_category c where c.category_id = ?";
		Map<String,Object> categoryInfo = DaoUtil.getInstance().queryForRow(sql, categoryId);
		if(categoryInfo==null || categoryInfo.isEmpty()){
			throw new RuntimeException("根据categoryId找不到记录。");
		}
		String dataType = StringUtil.safe2String(info.get("dataType"));
		if(StringUtil.isBlank(dataType)){
			dataType = StringUtil.safe2String(categoryInfo.get("dataType"));
		}
		
		String fid = info.get("fId")==null?"":(String)info.get("fId");
		String sn = StringUtil.safe2String(info.get("sn"));
		if(StringUtils.isBlank(fid)){
			//创建新的业务数据
			fid=getUUID();
			StringBuffer insertSql = new StringBuffer();
			insertSql.append("insert into be_wk_upload_file(f_id, sn, ref_id, ref_text, ")
				.append("file_id,thumb_file_id, state, gid, src_id,timestamp, category_id,data_type) ")
				.append("values (?,?,?,?,?,?,'1',?,?,sysdate,?,?)");
			List<Object> param = new ArrayList<Object>();
			param.add(fid);
			param.add(sn);//序号 签字盖章页需要
			param.add(info.get("refId")==null?"":info.get("refId"));
			param.add(info.get("refText")==null?"":info.get("refText"));
			param.add(fileId);
			param.add(info.get("thumbFileId"));
			param.add(gid);
			param.add(info.get("srcId")==null?"":info.get("srcId"));
			param.add(info.get("categoryId")==null?"":info.get("categoryId"));
			param.add(dataType);
			DaoUtil.getInstance().execute(insertSql.toString(),param);
		}else{
			StringBuffer updateSql = new StringBuffer();
			updateSql.append("update be_wk_upload_file set sn=?,state='1',file_id=?,thumb_file_id=?,src_id=?,timestamp=sysdate,data_type=? ")
					.append("where f_id=? ");
			List<Object> param = new ArrayList<Object>();
			param.add(sn);//序号 签字盖章页需要
			param.add(fileId);
			param.add(info.get("thumbFileId"));
			param.add(info.get("srcId")==null?"":info.get("srcId"));
			param.add(dataType);
			param.add(fid);
			DaoUtil.getInstance().execute(updateSql.toString(),param);
			
		}
		
		return fid;
	}
	
	public static void delPic(String fid){
		if(StringUtils.isBlank(fid)){
			throw new RuntimeException("无效的文件业务标识");
		}
		StringBuffer sql = new StringBuffer();
		sql.append("update be_wk_upload_file set state='2' where f_id=? ");
		DaoUtil.getInstance().execute(sql.toString(), fid);
	}
	/**
	 * 
	 * @return 缩略图宽
	 */
	private static int getThumbPicWidth(){
		String width= 	ConfigUtil.get("thumb.pic.width");
		int ret = Integer.parseInt(width);		 	
		return ret;
	}
	/**
	 * 
	 * @return 缩略图高
	 */
	private static int getThumbPicHeight(){
		String height=	ConfigUtil.get("thumb.pic.height");
		int ret = Integer.parseInt(height);
		return ret;
	}
	
	/**
	 * 生成指定文件的缩略图片文件
	 * 并将新文件信息保存至文件表
	 * @param path
	 * @param fileId
	 * @param suffixName
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String buildThumb(String path,String fileId,String suffixName)throws IOException{
		String thumbId = getUUID();
		String srcFile=path+fileId+"."+suffixName;
		String destFile=path+thumbId+"."+suffixName;
		boolean isNew=true;
		suffixName = suffixName.toLowerCase();
		int width = getThumbPicWidth();
		int height = getThumbPicHeight();
		//生成缩略图文件
		if(suffixName.equals("jpg") || suffixName.equals("jpeg") 
				|| suffixName.equals("bmp") || suffixName.equals("png")){			
			isNew=ImageUtil.resizeImage(srcFile, destFile,width, height);
		}else if(suffixName.equals("gif")){
			isNew=ImageUtil.resizeGif(srcFile, destFile,width, height);
		}
		if(isNew){
			//生成了调整大小后的新图片文件
			//文件信息插入文件表
			String sql="insert into be_wk_file(file_id, suffix_name, timestamp, file_path) values (?,?,sysdate,?)";
			List params = new ArrayList();
			params.add(thumbId);
			params.add(suffixName);
			params.add(path);
			DaoUtil.getInstance().execute(sql, params);
			return thumbId;
		}else{
			//原图片大小没有超过配置，不用压缩
			return fileId;
		}
		
		
	}
	/**
	 * 图片合成，目前仅支持jpg格式的图片文件。
	 * 
	 * @param flag 表示横向还是纵向合成 目前无用 
	 * 
	 * @param srcPics 要合并的图片文件地址
	 * @param destDir
	 * @return 返回新图片的fileId
	 */
	public static String mergeImages(List<String> fileIds,String destDir,String flag){
		String newFileId = getUUID();
		String outName = newFileId+".jpg";
		
		if(fileIds ==null || fileIds.size()==0){
			//没有找要合并的文件信息
			throw new RuntimeException("获取文件信息失败，不能合成。");
		}
		//获取文件路径
		List<String> srcPics = new ArrayList<String>();
		Iterator<String> iterator = fileIds.iterator();
		while(iterator.hasNext()){
			
			Map<String, String> file = FileUtil.getFileInfo(iterator.next());
			String path = file.get("filePath");
			String name = file.get("srcName");
			if(StringUtils.isBlank(path)){
				System.out.println("文件路径获取失败："+file.get("fileId"));
				continue;
			}
			if(StringUtils.isBlank(name)){
				System.out.println("文件名获取失败："+file.get("fileId"));
				continue;
			}
			srcPics.add(path+name);
		}
		//合并图片
		ImageUtil.mergePic(srcPics, destDir, outName, flag);
		//生成数据库文件记录
		//FIXME contentType
		FileUtil.saveFile(newFileId, "jpg", destDir, "image/fsd", 0, newFileId+".jpg");
		return newFileId;
	}
    public static String getUUID(){
		String ret = UUIDUtil.getUUID(); 	
  	    return ret;
	}
    
    public static void main(String[] args) {
    	
    }
}
