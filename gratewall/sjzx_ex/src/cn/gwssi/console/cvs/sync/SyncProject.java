package cn.gwssi.console.cvs.sync;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

import cn.gwssi.common.component.classloader.Compiler;
import cn.gwssi.common.component.Config;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.component.logger.TxnLogger;
import cn.gwssi.common.component.security.MD5;
import cn.gwssi.common.component.util.FileUtil;

public class SyncProject
{
	// 日志
	protected static Logger log = TxnLogger.getLogger( SyncProject.class.getName() );
	
	// 取目录
	private static String rootPath = null;
	private static String projPath = null;
	private static String srcPath = null;
	private static String classesPath = null;
	
	static{
		// 根目录
		rootPath = Config.getRootPath();
		if( rootPath != null ){
			rootPath = rootPath.replace( '\\', '/' );
			if( rootPath.endsWith("/") == false ){
				rootPath = rootPath + "/";
			}
			
			classesPath = rootPath + "WEB-INF/classes/";
		}
		
		// 工程目录
		projPath = Config.getInstance().getProjectPath();
		if( projPath == null || projPath.length() == 0 ){
			projPath = null;
		}
		else{
			projPath = projPath.replace( '\\', '/' );
			if( projPath.endsWith("/") == false ){
				projPath = projPath + "/";
			}
		}
		
		// 源代码目录
		srcPath = Config.getInstance().getSourcePath();
		if( srcPath == null || srcPath.length() == 0 ){
			srcPath = null;
		}
		else{
			srcPath = srcPath.replace( '\\', '/' );
			if( srcPath.endsWith("/") == false ){
				srcPath = srcPath + "/";
			}
		}
	}
	
	// 文件列表
	private boolean modifyFlag = false;
	private HashMap fileList = new HashMap();
	
	/**
	 * 同步文件:同步工程文件 和 运行文件
	 */
	public void syncFile()
	{
		// 根目录必须能够取到
		if( rootPath != null && projPath.compareTo(rootPath) != 0 ){
			// 同步系统文件
			String srcPath = rootPath + "/WEB-INF/classes/modules/resource/new-project/runtime";
			syncRuntimeFile( srcPath, rootPath );
			
			// 同步java文件
			compileJavaFile();
			
			// 同步工程文件：jsp、其它配置文件
			syncProjectFile();
		}
	}
	
	/**
	 * 同步java文件
	 */
	protected void compileJavaFile()
	{
		File fp = new File( srcPath );
		if( fp.exists() == false ){
			return;
		}
		
		// 同步文件
		try{
			String paths[] = fp.list();
			if( paths != null ){
				for( int ii=0; ii<paths.length; ii++ ){
					if( paths[ii].startsWith(".") == false &&
							paths[ii].compareTo("CVS") != 0 &&
							paths[ii].compareTo("svn-base") != 0 &&
							paths[ii].compareTo(".svn") != 0 &&
							paths[ii].compareTo("classes") != 0 &&
							paths[ii].compareTo("build.xml") != 0 )
					{
						syncJavaFile( paths[ii] );
					}
				}
			}
		}
		catch( TxnException e ){
			log.error( "同步目录[" + srcPath + "] ==> [" + rootPath + "]时错误", e );
		}
	}
	
	/**
	 * 同步工程文件：jsp、其它配置文件
	 *
	 */
	protected void syncProjectFile()
	{
		File fp = new File( projPath );
		if( fp.exists() == false ){
			return;
		}
		
		// 同步文件
		try{
			String paths[] = fp.list();
			if( paths != null ){
				for( int ii=0; ii<paths.length; ii++ ){
					if( paths[ii].startsWith(".") == false &&
							paths[ii].compareTo("CVS") != 0 &&
							paths[ii].compareTo("src") != 0 &&
							paths[ii].compareTo("svn-base") != 0 &&
							paths[ii].compareTo(".svn") != 0 &&
							paths[ii].compareTo("classes") != 0 &&
							paths[ii].compareTo("build.xml") != 0 )
					{
						syncWebFile( paths[ii] );
					}
				}
			}
			
			// 保存同步文件的名称
			String syncFile = Config.getInstance().getResourcePath() + "sync/file.list";
			// 判断是否有删除的文件
			deleteFile( syncFile );
			
			// 文件是否发生了修改
			if( modifyFlag == false ){
				return;
			}
			// 保存同步的文件
			StringBuffer result = new StringBuffer();
			Iterator keys = fileList.keySet().iterator();
			while( keys.hasNext() ){
				result.append( (String)keys.next() );
				result.append( "\n" );
			}
			
			FileUtil.saveFile( syncFile, result.toString() );
		}
		catch( TxnException e ){
			log.error( "复制目录[" + projPath + "] ==> [" + rootPath + "]时错误", e );
		}
	}
	
	/**
	 * 拷贝目录
	 * @param path 工程文件目录 --> WEB应用目录
	 * @throws TxnException
	 */
	private void syncWebFile( String path ) throws TxnException 
	{
		// 增加同步的文件名称
		fileList.put( path, "1" );
		
		// 判断文件是否存在
		String fromPath = projPath + path;
		File fp = new File( fromPath );
		
		// 判断是目录还是文件
		if( fp.isDirectory() ){
			// 格式化目录
			path = path + "/";
			
			// 文件列表
			String[] files = fp.list();
			for( int ii=0; ii<files.length; ii++ ){
				if( files[ii].compareTo("CVS") != 0 &&
						files[ii].compareTo(".svn") != 0 &&
						files[ii].compareTo("svn-base") != 0 &&
						files[ii].compareTo("Thumbs.db") != 0 )
				{
					syncWebFile( path + files[ii] );
				}
			}
		}
		else{
			// 目标文件/目录
			String toPath = rootPath + path;
			File pfn = new File( toPath );
			
			// 拷贝文件
			if( pfn.exists() == false || fp.lastModified() > pfn.lastModified() ){
				modifyFlag = true;
				log.info( "更新文件 ==> " + path );
				FileUtil.copy( fromPath, toPath );
			}
		}
	}
	
	/**
	 * 删除文件
	 * @param fileList
	 * @throws TxnException
	 */
	private void deleteFile( String syncFile ) throws TxnException 
	{
		InputStream is = null;
		BufferedReader in = null;
		
		// 打开文件，取上次同步的文件列表
		try{
			is = FileUtil.getResourceAsStream( syncFile );
			in = new BufferedReader(new InputStreamReader(is) );
		}
		catch( Exception e ){
			return;
		}
		
		// 删除文件
		try{
			String name = in.readLine();
			while( name != null ){
				if( fileList.get(name) == null ){
					modifyFlag = true;
					log.info( "删除文件 ==> " + name );
					FileUtil.deleteFile( rootPath + name );
				}
				
				name = in.readLine();
			}
		}
		catch( Exception e ){
			log.warn( "删除文件时错误", e );
		}
		finally{
			FileUtil.closeInputStream( is );
		}
	}
	
	/**
	 * 同步JAVA源代码
	 * @param path
	 * @throws TxnException
	 */
	private void syncJavaFile( String path ) throws TxnException 
	{
		// 判断文件是否存在
		String fromPath = srcPath + path;
		File fp = new File( fromPath );
		
		// 判断是目录还是文件
		if( fp.isDirectory() ){
			// 格式化目录
			path = path + "/";
			
			// 文件列表
			String[] files = fp.list();
			for( int ii=0; ii<files.length; ii++ ){
				if( files[ii].compareTo("CVS") != 0 &&
						files[ii].compareTo(".svn") != 0 &&
						files[ii].compareTo("svn-base") != 0 &&
						files[ii].compareTo("Thumbs.db") != 0 )
				{
					syncJavaFile( path + files[ii] );
				}
			}
		}
		else{
			String toPath = null;
			String className = null;

			// 目标文件/目录
			if( path.endsWith(".java") ){
				className = path.substring( 0, path.length()-5 );
				toPath = classesPath + className + ".class";
			}
			else{
				toPath = classesPath + path;
			}
			
			// 拷贝文件，或者编译文件
			File pfn = new File( toPath );
			if( pfn.exists() == false || fp.lastModified() > pfn.lastModified() ){
				log.info( "更新文件 ==> " + toPath );
				if( className != null ){
					className = className.replace( '/', '.' );
					Compiler.getInstance().compileJavaBean( className );
				}
				else{
					FileUtil.copy( fromPath, toPath );
				}
			}
		}
	}
	

	/**
	 * 创建运行时文件
	 * 如果文件和发布的包中的文件不一致，覆盖
	 */
	protected static void syncRuntimeFile( String srcPath, String destPath )
	{
		File fp = new File( srcPath );
		if( fp.exists() == false ){
			return;
		}
		
		if( fp.isFile() ){	// 同步文件
			MD5.syncFile( srcPath, destPath );
		}
		else if( fp.isDirectory() ){ // 同步目录
			String[] fileList = fp.list();
			for( int ii=0; ii<fileList.length; ii++ ){
				String path = "/" + fileList[ii];
				syncRuntimeFile( srcPath + path, destPath + path );
			}
		}
	}
	
}
