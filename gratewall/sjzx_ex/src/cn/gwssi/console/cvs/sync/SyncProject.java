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
	// ��־
	protected static Logger log = TxnLogger.getLogger( SyncProject.class.getName() );
	
	// ȡĿ¼
	private static String rootPath = null;
	private static String projPath = null;
	private static String srcPath = null;
	private static String classesPath = null;
	
	static{
		// ��Ŀ¼
		rootPath = Config.getRootPath();
		if( rootPath != null ){
			rootPath = rootPath.replace( '\\', '/' );
			if( rootPath.endsWith("/") == false ){
				rootPath = rootPath + "/";
			}
			
			classesPath = rootPath + "WEB-INF/classes/";
		}
		
		// ����Ŀ¼
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
		
		// Դ����Ŀ¼
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
	
	// �ļ��б�
	private boolean modifyFlag = false;
	private HashMap fileList = new HashMap();
	
	/**
	 * ͬ���ļ�:ͬ�������ļ� �� �����ļ�
	 */
	public void syncFile()
	{
		// ��Ŀ¼�����ܹ�ȡ��
		if( rootPath != null && projPath.compareTo(rootPath) != 0 ){
			// ͬ��ϵͳ�ļ�
			String srcPath = rootPath + "/WEB-INF/classes/modules/resource/new-project/runtime";
			syncRuntimeFile( srcPath, rootPath );
			
			// ͬ��java�ļ�
			compileJavaFile();
			
			// ͬ�������ļ���jsp�����������ļ�
			syncProjectFile();
		}
	}
	
	/**
	 * ͬ��java�ļ�
	 */
	protected void compileJavaFile()
	{
		File fp = new File( srcPath );
		if( fp.exists() == false ){
			return;
		}
		
		// ͬ���ļ�
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
			log.error( "ͬ��Ŀ¼[" + srcPath + "] ==> [" + rootPath + "]ʱ����", e );
		}
	}
	
	/**
	 * ͬ�������ļ���jsp�����������ļ�
	 *
	 */
	protected void syncProjectFile()
	{
		File fp = new File( projPath );
		if( fp.exists() == false ){
			return;
		}
		
		// ͬ���ļ�
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
			
			// ����ͬ���ļ�������
			String syncFile = Config.getInstance().getResourcePath() + "sync/file.list";
			// �ж��Ƿ���ɾ�����ļ�
			deleteFile( syncFile );
			
			// �ļ��Ƿ������޸�
			if( modifyFlag == false ){
				return;
			}
			// ����ͬ�����ļ�
			StringBuffer result = new StringBuffer();
			Iterator keys = fileList.keySet().iterator();
			while( keys.hasNext() ){
				result.append( (String)keys.next() );
				result.append( "\n" );
			}
			
			FileUtil.saveFile( syncFile, result.toString() );
		}
		catch( TxnException e ){
			log.error( "����Ŀ¼[" + projPath + "] ==> [" + rootPath + "]ʱ����", e );
		}
	}
	
	/**
	 * ����Ŀ¼
	 * @param path �����ļ�Ŀ¼ --> WEBӦ��Ŀ¼
	 * @throws TxnException
	 */
	private void syncWebFile( String path ) throws TxnException 
	{
		// ����ͬ�����ļ�����
		fileList.put( path, "1" );
		
		// �ж��ļ��Ƿ����
		String fromPath = projPath + path;
		File fp = new File( fromPath );
		
		// �ж���Ŀ¼�����ļ�
		if( fp.isDirectory() ){
			// ��ʽ��Ŀ¼
			path = path + "/";
			
			// �ļ��б�
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
			// Ŀ���ļ�/Ŀ¼
			String toPath = rootPath + path;
			File pfn = new File( toPath );
			
			// �����ļ�
			if( pfn.exists() == false || fp.lastModified() > pfn.lastModified() ){
				modifyFlag = true;
				log.info( "�����ļ� ==> " + path );
				FileUtil.copy( fromPath, toPath );
			}
		}
	}
	
	/**
	 * ɾ���ļ�
	 * @param fileList
	 * @throws TxnException
	 */
	private void deleteFile( String syncFile ) throws TxnException 
	{
		InputStream is = null;
		BufferedReader in = null;
		
		// ���ļ���ȡ�ϴ�ͬ�����ļ��б�
		try{
			is = FileUtil.getResourceAsStream( syncFile );
			in = new BufferedReader(new InputStreamReader(is) );
		}
		catch( Exception e ){
			return;
		}
		
		// ɾ���ļ�
		try{
			String name = in.readLine();
			while( name != null ){
				if( fileList.get(name) == null ){
					modifyFlag = true;
					log.info( "ɾ���ļ� ==> " + name );
					FileUtil.deleteFile( rootPath + name );
				}
				
				name = in.readLine();
			}
		}
		catch( Exception e ){
			log.warn( "ɾ���ļ�ʱ����", e );
		}
		finally{
			FileUtil.closeInputStream( is );
		}
	}
	
	/**
	 * ͬ��JAVAԴ����
	 * @param path
	 * @throws TxnException
	 */
	private void syncJavaFile( String path ) throws TxnException 
	{
		// �ж��ļ��Ƿ����
		String fromPath = srcPath + path;
		File fp = new File( fromPath );
		
		// �ж���Ŀ¼�����ļ�
		if( fp.isDirectory() ){
			// ��ʽ��Ŀ¼
			path = path + "/";
			
			// �ļ��б�
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

			// Ŀ���ļ�/Ŀ¼
			if( path.endsWith(".java") ){
				className = path.substring( 0, path.length()-5 );
				toPath = classesPath + className + ".class";
			}
			else{
				toPath = classesPath + path;
			}
			
			// �����ļ������߱����ļ�
			File pfn = new File( toPath );
			if( pfn.exists() == false || fp.lastModified() > pfn.lastModified() ){
				log.info( "�����ļ� ==> " + toPath );
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
	 * ��������ʱ�ļ�
	 * ����ļ��ͷ����İ��е��ļ���һ�£�����
	 */
	protected static void syncRuntimeFile( String srcPath, String destPath )
	{
		File fp = new File( srcPath );
		if( fp.exists() == false ){
			return;
		}
		
		if( fp.isFile() ){	// ͬ���ļ�
			MD5.syncFile( srcPath, destPath );
		}
		else if( fp.isDirectory() ){ // ͬ��Ŀ¼
			String[] fileList = fp.list();
			for( int ii=0; ii<fileList.length; ii++ ){
				String path = "/" + fileList[ii];
				syncRuntimeFile( srcPath + path, destPath + path );
			}
		}
	}
	
}
