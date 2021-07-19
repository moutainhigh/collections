package com.gwssi.ebaic.common.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.gwssi.rodimus.config.ConfigUtil;

/**
 * 上传文件路径获取类。
 * 
 * @author lxb
 */
public class UploadFilePathUtil {

	private static final String UPLOAD_ROOT_PATH_KEY = "upload.uploadDir";
	private static final String CUT_ROOT_PATH_KEY = "upload.cutImage";
	private static final String separator = File.separator;
	
	/**
	 * 用于生成路径时 格式化日期。
	 */
	static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * <pre>
	 * 获取文件上传目录。
	 * 格式：[upload.rootDir][upload.uploadDir]/[year]/[month]/[date]/[gid]/。
	 * 日期取自上传当天的日期。
	 * 例如：/rootPath/upload4ebaic/2016/06/14/343alfjsdouosdfhzdfd/
	 * </pre>
	 * @return
	 */
	public static String getUploadPath(String gid){
		StringBuffer path = new StringBuffer();
		path.append(ConfigUtil.get(UPLOAD_ROOT_PATH_KEY));
		String date = df.format(new Date());
		if("\\".equals(separator)){
			date = date.replaceAll("-", "\\\\");
		}else{
			date = date.replaceAll("-", separator);
		}
		
		path.append(date).append(separator);
		path.append(gid).append(separator);
		return path.toString();
	}
	
	/**
	 * 获取剪切后的图片保存路径。
	 * 格式：/rootPath/cutImage/2016/06/14/343alfjsdouosdfhzdfd/
	 * @param gid
	 * @return
	 */
	public static String getCutImagePath(String gid){
		StringBuffer path = new StringBuffer();
		path.append(ConfigUtil.get(CUT_ROOT_PATH_KEY));
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date = df.format(new Date());
		path.append(date.replaceAll("-", separator)).append(separator);
		path.append(gid).append(separator);
		return path.toString();
		
	}
}
