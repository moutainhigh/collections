package com.gwssi.common.upload;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.gwssi.common.constant.FileConstant;


/**
 * @desc �ļ��ϴ����ش�����
 * @author lifx
 * @version 1.0
 *
 */
public class FileManager
{
	/**
	 * �������������ļ���Ŀ¼
	 */
	private String root;
	
	private FileManager(){
		
	}
	
	private FileManager(String root){
		this.root = root;
	}
	
	/**
	 * �õ�һ���������������ļ��������
	 * @param root
	 * @return
	 */
	public static FileManager getInstance(String root){
		return new FileManager(root);
	} 
	
	/**
	 * ��������·��������͸�Ŀ¼��
	 * ��Ŀ¼�������򴴽�Ŀ¼
	 * @param filePath
	 */
	public boolean checkRootDir(){
		boolean result = false;
		String filePath = root;
		try{
			File rootDir = new File(filePath);	
			if(!rootDir.exists()){
				result = rootDir.mkdirs();
			}else{
				if(rootDir.isDirectory()){
					result = true;
				}
			}
		}catch(Exception e ){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * ��������·��ȡ�÷������������ļ�,�����ͽṹ
	 * @param folderName ����root·�������·�� 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List queryAllFiles(){
		String pathName = root;
		
		File path = new File(pathName);
		File[] files = path.listFiles();
		
		List listFiles = new ArrayList();
		for (int i = 0 ; i < files.length ; i++){
			if (files[i].exists() && files[i].isFile()){
				listFiles.add(files[i]);
			}
			if (files[i].exists() && files[i].isDirectory()){
				listFiles.addAll(queryAllFiles(pathName+FileConstant.PATH_SEPERATOR+files[i].getName()));
			}
		}
		
		return listFiles;
	} 
	
	public boolean copyAllFiles(String new_path){
		String origin_path = root;
		File o_path = new File(origin_path);
		
		File[] files = o_path.listFiles();

		for (int i = 0 ; i < files.length ; i++){
			if (files[i].exists() && files[i].isFile()){
				String fileName = new_path+FileConstant.PATH_SEPERATOR+files[i].getName();				
				copyFile(origin_path+FileConstant.PATH_SEPERATOR+files[i].getName(),fileName);
			}
			if (files[i].exists() && files[i].isDirectory()){
				copyAllFiles(origin_path+FileConstant.PATH_SEPERATOR+files[i].getName(),new_path+FileConstant.PATH_SEPERATOR+files[i].getName());
			}
		}		
		return true;
	}
	
	public void copyAllFiles(String originPrePath,String newPrePath){
		
		File originPath = new File(originPrePath);
		File[] files = originPath.listFiles();
		
		File newPath = new File(newPrePath);
		if(!newPath.exists()){
			newPath.mkdir();
		}
		
		for (int i = 0 ; i < files.length ; i++){
			if (files[i].exists() && files[i].isFile()){
				String fileName = newPrePath+FileConstant.PATH_SEPERATOR+files[i].getName();					
				//copyFile(files[i],fileName);	
				copyFile(originPrePath+FileConstant.PATH_SEPERATOR+files[i].getName(),fileName);
			}
			if (files[i].exists() && files[i].isDirectory()){
				copyAllFiles(originPrePath+FileConstant.PATH_SEPERATOR+files[i].getName(),newPrePath+FileConstant.PATH_SEPERATOR+files[i].getName());
			}
		}
	}
	

	/**
	 * ��������·��ȡ�÷������������ļ�,�����ͽṹ
	 * @param folderName ����root·�������·�� 
	 * @return
	 */
	public List queryAllFiles(String folderName){
		String pathName = "";
		if (folderName == null || folderName.equals("")){
			pathName = root;
		}else{
			pathName = root+FileConstant.PATH_SEPERATOR+folderName;
		}
		File path = new File(pathName);
		File[] files = path.listFiles();
		
		List listFiles = new ArrayList();
		for (int i = 0 ; i < files.length ; i++){
			if (files[i].exists() && files[i].isFile()){
				listFiles.add(files[i]);
			}
			if (files[i].exists() && files[i].isDirectory()){
				listFiles.addAll(queryAllFiles(folderName+FileConstant.PATH_SEPERATOR+files[i].getName()));
			}
		}
		
		return listFiles;
	} 
	
	/**
	 * ���������ļ������ƣ�ȡ�ĸ��ļ����µ�Ŀ¼���ƣ�ֻ�ṩһ����
	 * @param folderName ����root·�������·�� 
	 * @param type 0ȡ����Ŀ¼���ļ�����,1ȡĿ¼����,2ȡ�ļ�����
	 * @return
	 */
	public Map queryNameByFoledName(String folderName,int type){
		String pathName = "";
		if (folderName == null && folderName.equals("")){
			pathName = root;
		}else{
			pathName = root+FileConstant.PATH_SEPERATOR+folderName;
		}
		File path = new File(pathName);
		File[] files = path.listFiles();
		Map mapFiles = new HashMap();
		for (int i = 0 ; i < files[i].length() ; i++){
			if (files[i].exists() && files[i].isFile() && type != 1){
				mapFiles.put(folderName+FileConstant.PATH_SEPERATOR+files[i].getName(),getFileAttribute(files[i]));
			}
			if (files[i].exists() && files[i].isDirectory() && type != 2){
				mapFiles.put(folderName+FileConstant.PATH_SEPERATOR+files[i].getName(),getFileAttribute(files[i]));
			}
		}
		return mapFiles;
	}
	
	/**
	 * ��������·��ȡ�÷��������ļ�Ŀ¼�ṹ������Ŀ¼�µ������ļ�����
	 * @param folderName ����root·�������·�� 
	 * @return
	 */
	public Map queryAllFoledAndFileName(String folderName){
		List listFiles = queryAllFiles(folderName);
		
		Map mapFiles = new HashMap();
		
		for (Iterator iter = listFiles.iterator();iter.hasNext();){
			File file = (File)iter.next();
			mapFiles.put(folderName+FileConstant.PATH_SEPERATOR+file.getName(),getFileAttribute(file));
		}
		
		return mapFiles;
	}
	
	public Map getFileAttribute(File file){
		Map mapFile = new HashMap();
		mapFile.put(FileConstant.file_name,file.getName());
		if (file.isDirectory()){
			mapFile.put(FileConstant.file_type,FileConstant.folder);
		}else{
			mapFile.put(FileConstant.file_type,FileConstant.file);
		}
		mapFile.put(FileConstant.file_type,String.valueOf(file.length()));
		mapFile.put(FileConstant.file_lastModified,String.valueOf(file.lastModified()));
		return mapFile;
	}
	
	/**
	 * ȡ��file�ļ�
	 * @param folderName ����root·�������·��,�����ļ���
	 * @return
	 */
	public File getFile(String folderName){
		if (isExists(folderName,2)){
			return new File(root+FileConstant.PATH_SEPERATOR+folderName);
		}
		return null;
	}
	
	/**
	 * �ж�root�Ƿ����fileName����ļ�
	 * @param folderName ����root·�������·�������Դ��ļ���
	 * @param type 0ȡ����Ŀ¼���ļ�����,1ȡĿ¼����,2ȡ�ļ�����
	 * @return ���ڷ���true,�����ڷ���false
	 */
	public boolean isExists(String folderName,int type){
		String pathName = "";
		if (folderName == null && folderName.equals("")){
			return false;
		}else{
			pathName = root+FileConstant.PATH_SEPERATOR+folderName;
		}
		File file = new File(pathName);
		if (file.exists() && file.isFile() && type != 1){
			return true;
		}
		if (file.exists() && file.isDirectory() && type != 2){
			return true;
		}
		return false;
	}
	
	
	public void checkFolder(String FilePath){
		File fileDirecotry = new File(FilePath);
		if (!fileDirecotry.exists()) {
	      if (FilePath.indexOf(FileConstant.PATH_SEPERATOR) >= 0) {
	          int intseparatorPointer = FilePath.indexOf(FileConstant.PATH_SEPERATOR);
	          while (intseparatorPointer >= 0) {
	            if (intseparatorPointer == FilePath.length()) {
	              //throw new MISException("[�ļ�·���н�����·��·����û���ļ����ƣ�]");
	            }
	            String strFilePath = FilePath.substring(0, intseparatorPointer);
	            fileDirecotry = new File(strFilePath);
	            if (!fileDirecotry.exists()) {
	              fileDirecotry.mkdir();
	            }
	            //������һ��Ŀ¼
	            intseparatorPointer = FilePath.indexOf(FileConstant.PATH_SEPERATOR, intseparatorPointer + 1);
	          }
	       }
		}
	}
	
	private boolean copyFile(String originFileName,String fileName){
		FileInputStream stream = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		
		checkFolder(fileName);
		File newPath = new File(fileName);
		
		try {
			stream = new FileInputStream(new File(originFileName));
	        fos = new FileOutputStream(newPath);
	        bos = new BufferedOutputStream(fos);//����һ���ϴ��ļ��������
	        int bytesRead = 0;
	        byte[] buffer = new byte[1024];
	        while ( (bytesRead = stream.read(buffer, 0, 1024)) > 0) {
	            bos.write(buffer, 0, bytesRead);//���ļ�д�������
	        }
	        bos.flush();	        
        }catch(Exception e){
        	e.printStackTrace();
        	return false;
        }finally{
        	try{
        		fos.close();
        		bos.close();
        		stream.close();
        	}catch(IOException ioe){
        		ioe.fillInStackTrace();
        	}
        }
        return true;
	}
	
	/**
	 * ɾ��ָ��Ŀ¼�����е��������ݡ�
	 * @param dir Ҫɾ����Ŀ¼
	 * @return ɾ���ɹ�ʱ����true�����򷵻�false��
	 * @since  0.1
	 */
	 public static boolean deleteDirectory(File dir) {
         System.gc();
		 if ( (dir == null) || !dir.isDirectory()) {
		      throw new IllegalArgumentException("�����Ŀ¼���Ϸ� " + dir);
		   }

		 File[] entries = dir.listFiles();
		  int sz = entries.length;

		   for (int i = 0; i < sz; i++) {
		      if (entries[i].isDirectory()) {
		        if (!deleteDirectory(entries[i])) {
		          return false;
		        }
		      }
		      else {
		        if (!entries[i].delete()) {
		          return false;
		        }
		      }
		    }

		    if (!dir.delete()) {
		      return false;
		    }
		    return true;
		  }



	class DirFilter implements FilenameFilter{
		
		String afn = "";
		DirFilter(String afn) { 
			this.afn = afn; 
		}
		
		public boolean accept(File dir, String name) {
		    // Strip path information:
			String f = new File(name).getName();
			return afn.equals(f);
		}
	}

}
