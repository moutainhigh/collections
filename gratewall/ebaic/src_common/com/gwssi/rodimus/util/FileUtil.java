package com.gwssi.rodimus.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.gwssi.optimus.core.cache.CacheBlock;
import com.gwssi.optimus.core.cache.CacheElement;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.rodimus.cache.CacheUtil;
import com.gwssi.rodimus.config.ConfigUtil;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.exception.FileException;
import com.gwssi.rodimus.exception.RodimusException;

import sun.misc.BASE64Decoder;

/**
 * 文件工具类。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class FileUtil {
	public static String getFilePhyPath(String fileId){
		if(StringUtil.isBlank(fileId)||"undefined".equals(fileId)){
			throw new FileException("无效的fileId");
		}
		String cacheKey = "file_"+fileId;
		String ret = StringUtil.safe2String(CacheUtil.get(cacheKey));
		//ret = "D:/upload/userSign/110108000/shigzh/"+fileId+".jpg";
		if(StringUtil.isBlank(ret)){
			Map<String,String> rsMap = FileUtil.getFileInfo(fileId);
			String path = rsMap.get("filePath");
			String fname = rsMap.get("srcName");
			
			ret = ConfigUtil.get("file.rootPath")+path+fname ;
			
			CacheBlock cb = CacheUtil.getCacheBlock();
	    	CacheElement ce = new CacheElement(cacheKey,ret);
	    	ce.setTimeToIdleSeconds(6000);
	    	cb.put(ce);
		}
		return ret ;
	}
	/**
	 * 文件下载。
	 * 
	 * FIXME 方法加固。
	 * 
	 * @param contentType
	 * @param fileName
	 * @param phyFilePath
	 * @param response
	 */
	public static void download(String contentType, String fileName,String suffix,String phyFilePath, HttpServletResponse response){
		
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
				if(StringUtil.isBlank(suffix)){
					suffix=".pdf";
				}
				response.addHeader("Content-Disposition", "attachment;filename="+fileName+suffix);
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
	/**
	 * 得到文件根路径。
	 * @return
	 */
	public static String getRootPath(){
		String ret = ConfigUtil.get("file.rootPath");
		if(StringUtil.isBlank(ret)){
			throw new FileException("请配置file.rootPath。");
		}
		return ret;
	}
	/**
	 * 文件信息保存至be_wk_file表 生成对应的业务数据
	 * @param fileId
	 * @param suffix
	 * @param path
	 * @param file
	 */
	public static String saveFile(String fileid,String suffix, String path,String contentType,long size,String fileName){
		Calendar now = DateUtil.getCurrentTime();
		String sql ="insert into be_wk_file(file_id, suffix_name, content_type, \"SIZE\", timestamp, file_path, file_name) values (?,?,?,?,?,?,?)";
		List<Object> params = new ArrayList<Object>();
		params.add(fileid);
		params.add(suffix);		
		params.add(contentType);
		params.add(size);
		params.add(now);
		params.add(path);
		params.add(fileName);
		DaoUtil.getInstance().execute(sql, params);
		return fileid;
	}

	
	/**
	 * 根据fileid获得路径。
	 * 
	 * @param fileid
	 * @return
	 */
	public static String getFilePath(String fileid){
		if(StringUtil.isBlank(fileid)){
			throw new FileException("无效的fileId。");
		}
		Map<String,Object> row = DaoUtil.getInstance().queryForRow("select * from be_wk_file u where u.file_id = ?", fileid);
		if(row==null){
			throw new FileException("无效的fileId。");
		}
		
		String rootPath = ConfigUtil.get("file.rootPath") ;
		String filePath = StringUtil.safe2String(row.get("filePath"));
		String suffixName = StringUtil.safe2String(row.get("suffixName"));
		
		String ret = String.format("%s%s%s.%s",rootPath,filePath,fileid,suffixName);
		return ret;
	}
	
	/**
	 * 获取指定的文件信息
	 * 文件名、拓展名、文件路径、上传时文件名
	 * @param fileId
	 * @return
	 * @throws OptimusException
	 */
	public static Map<String,String> getFileInfo(String fileid){
		if(StringUtil.isBlank(fileid)){
			throw new FileException("无效的fileId。");
		}
		String sql ="select file_id||'.'||suffix_name as src_name,suffix_name,file_path,file_name from be_wk_file where file_id=?";
		Map<String,Object> rsMap = DaoUtil.getInstance().queryForRow(sql, fileid);
		if(rsMap==null || rsMap.isEmpty()){
			throw new FileException("无效的fileId。");
		}
		Map<String,String> ret = new HashMap<String,String>();
		for(Map.Entry<String, Object> entry : rsMap.entrySet()){
			ret.put(entry.getKey(), StringUtil.safe2String(entry.getValue()));
		}
		return ret;
	}
	/**
	 * 批量获取文件
	 * @param fileIds 要获取的文件fileId列表
	 * @return
	 * @throws OptimusException
	 */
	public static List<Map<String,Object>> getFileInfos (List<String>fileIds){
		if(fileIds==null || fileIds.size()==0){
			throw new FileException("批量获取文件信息失败，参数不能为空。");
		}
		int len = fileIds.size();
		StringBuffer sql = new StringBuffer("select file_id||'.'||suffix_name as src_name,suffix_name,file_path,file_name from be_wk_file where file_id in ("); 
		for (int i = 0; i <len; i++) {
			if(i==len-1){
				sql.append("?");
			}else{
				sql.append("?,");
			}
		}
		sql.append(")");
		
		return DaoUtil.getInstance().queryForList(sql.toString(), fileIds);
	}
	
	/**
	 * 读取文件内容，以byte数字格式返回。
	 * 
	 * @param filePath
	 * @return
	 */
	public static byte[] getFileBytes(String filePath) {
		byte[] buffer = null;  
        try {  
            File file = new File(filePath);  
            FileInputStream fis = new FileInputStream(file);  
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);  
            byte[] b = new byte[10000];  
            int n;  
            while ((n = fis.read(b)) != -1) {  
                bos.write(b, 0, n);  
            }  
            fis.close();  
            bos.close();  
            buffer = bos.toByteArray();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return buffer; 
	}
	
	/**
	 * 获取目标文件的文本内容
	 * 
	 * @param targetFile
	 * @return
	 */
	public static String getFileText(File targetFile) {
		StringBuffer ret = new StringBuffer("");
		BufferedReader br = null;
		try{
			if(targetFile!=null && targetFile.isFile() && targetFile.exists()){
				FileInputStream fileInputStream = new FileInputStream(targetFile);
				InputStreamReader reader = new InputStreamReader(fileInputStream,"UTF-8");
				br = new BufferedReader(reader);
				String line = null;
				while ((line = br.readLine()) != null) {
					ret.append(line);
				}
			}else{
				throw new FileException("文件不存在。");
			}
		}catch(FileException e){
			
		} catch (FileNotFoundException e) {
			throw new FileException("文件不存在："+e.getMessage()+"。",e);
		} catch (UnsupportedEncodingException e) {
			throw new FileException("文件不支持UTF-8编码："+e.getMessage()+"。",e);
		} catch (IOException e) {
			throw new FileException("读入文件时发生IO异常："+e.getMessage()+"。",e);
		}finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ret.toString();
	}

	/**
	 * 保存文本到文件。
	 * 
	 * @param targetFilePath
	 * @param content
	 */
	public static void saveString2File(String targetFilePath, String content) {
		try {
			File targetDocFile = new File(targetFilePath);
			//创建文件夹
			if(!targetDocFile.exists() || !targetDocFile.isDirectory()){
				targetDocFile.mkdirs();
			}
			File targetFile = new File(targetFilePath);
			//创建文件
			if(!targetFile.exists()){
				targetFile.createNewFile();
			}
			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile), "UTF-8"));
			writer.write(content);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			throw new FileException(e.getMessage(),e);
		}
	}
	
	/**
	 * <h3>生成文件<h3> 
	 * 
	 * @param path 完整路径包括文件名
	 * @param content 文件内容
	 */
	public static void createFile(String path,String content){
		if(StringUtils.isBlank(path)){
			throw new FileException("Create filePath 不能为空。");
		}
		try {
			File file = new File(path);
			//创建文件
			if(!file.exists()){
				file.createNewFile();
			}
			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			writer.write(content);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			throw new FileException(e.getMessage()+path);
		}
	}
	
	/**
	 * <h3>读取文件内容</h3>
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static String readFile(String path) {
		if(StringUtils.isBlank(path)){
			throw new FileException("Read filePath 不能为空。");
		}
		File file = new File(path);
		//创建文件
		if(!file.exists()){
			throw new FileException(path+"文件不存在。");
		}
		StringBuffer result = new StringBuffer();
		try{
			FileReader fr;
			fr = new FileReader(path);
			BufferedReader bReader = new BufferedReader(fr);
			String str = null;
			while((str = bReader.readLine())!=null){
				result.append(str);
			}
			fr.close();
			bReader.close();
		}catch(Exception e){
			throw new RodimusException(e.getMessage(),e);
		}
		return result.toString();
	}

	public static boolean saveBase64ToFile(String base64Data, String phyFilePath) {

		if (base64Data == null) { //文件数据为空
			return false;
		}
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(base64Data);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			// 生成文件
			OutputStream out = new FileOutputStream(phyFilePath);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 清空文件夹
	 * 
	 * @param file
	 * @param suffix 指定删除的文件类型 如：png,jpg
	 */
	public static void cleanDir(File file,String... suffix){
		if(file.exists()){
			//如果是目录需要递归删除
			if(file.isDirectory()){
				String[] children = file.list();
				//递归删除目录中的子目录下
	            for (int i=0; i<children.length; i++) {
	                cleanDir(new File(file, children[i]),suffix);
	            }
			}
			if(file.isFile()){
				//没有指定删除的文件类型，则直接删除
				if(null==suffix || suffix.length==0){
					file.delete();
				}else{
					String fileName = file.getName();
					String[] name = fileName.split("\\.");
					String type = "";
					if(name.length==2){
						type = name[1];
						for (String suf : suffix) {
							if(suf.equals(type)){
								file.delete();
							}
						}
					}
				}
			}
		}
	}
	
	/** 
     * 复制单个文件 
     *  
     * @param srcFileName 
     *            待复制的文件全路径
     * @param descFileName 
     *            目标文件全路径
     * @param overlay 
     *            如果目标文件存在，是否覆盖 
     * @return 如果复制成功返回true，否则返回false 
     */  
    public static void copyFile(String srcPath, String destPath,  
            boolean overlay) {  
        File srcFile = new File(srcPath);  
        // 判断源文件是否存在  
        if (!srcFile.exists()) {  
            throw new EBaicException("源文件：" + srcPath + "不存在！");  
        } else if (!srcFile.isFile()) {  
        	throw new EBaicException("复制文件失败，源文件：" + srcPath + "不是一个文件！");  
        }  
  
        // 判断目标文件是否存在  
        File destFile = new File(destPath);  
        if (destFile.exists()) {  
            // 如果目标文件存在并允许覆盖  
            if (overlay) {  
                // 删除已经存在的目标文件，无论目标文件是目录还是单个文件  
                new File(destPath).delete();  
            }else{
            	return;
            }
        } else {  
            // 如果目标文件所在目录不存在，则创建目录  
            if (!destFile.getParentFile().exists()) {  
                // 目标文件所在目录不存在  
                if (!destFile.getParentFile().mkdirs()) {  
                    throw new EBaicException("复制文件失败：创建目标文件所在目录失败  ");
                }  
            }  
        }  
  
        // 复制文件  
        int byteread = 0; // 读取的字节数  
        InputStream in = null;  
        OutputStream out = null;  
  
        try {  
            in = new FileInputStream(srcFile);  
            out = new FileOutputStream(destFile);  
            byte[] buffer = new byte[1024];  
  
            while ((byteread = in.read(buffer)) != -1) {  
                out.write(buffer, 0, byteread);  
            }  
        } catch (FileNotFoundException e) {  
            throw new EBaicException(e.getMessage());
        } catch (IOException e) {  
        	throw new EBaicException(e.getMessage());
        } finally {  
            try {  
                if (out != null)  
                    out.close();  
                if (in != null)  
                    in.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }
}
