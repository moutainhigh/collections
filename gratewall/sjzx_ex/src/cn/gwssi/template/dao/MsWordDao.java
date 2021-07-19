package cn.gwssi.template.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;

import oracle.sql.BLOB;

import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.Entry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
//import org.textmining.text.extraction.WordExtractor;

import weblogic.jdbc.vendor.oracle.OracleThinBlob;

import com.genersoft.frame.base.database.DbUtils;


/**
 * 动态模板数据库的相关操作方法,包括插入、读取等
 * @version 1.0
 * @author FlyFish 
 * modified by DC2-jufeng-20120730
 * 
 */
public class MsWordDao
{

	/**
	 * 将模板插入数据库
	 * @param subjectId 主题ID
	 * @param name 模板名称
	 * @param model 模板内容
	 * @param modelMemo 模板内容描述
	 * @param connType 数据库连接方式
	 * @throws Exception
	 */
	public void addMsWord(String sys_msword_id, String sys_msword_name, String sys_msword_template, String sys_msword_bookmarks,String sys_msword_desp) throws Exception
	{
		Connection conn = DbUtils.getConnection("2");
//		System.out.println(uuid);	
		conn.setAutoCommit(false); // 取消Connection对象的auto commit属性
		PreparedStatement ps = conn
				.prepareStatement("INSERT INTO  SYS_MSWORD (SYS_MSWORD_ID,SYS_MSWORD_NAME,SYS_MSWORD_BOOKMARKS,SYS_MSWORD_DESP,SYS_MSWORD_TEMPLATE) VALUES(?,?,?,?,?)");
		ps.setString(1, sys_msword_id);
		ps.setString(2, sys_msword_name);
		ps.setString(3, sys_msword_bookmarks);
		ps.setString(4, sys_msword_desp);
		ps.setBlob(5, BLOB.empty_lob());
		ps.executeUpdate();
		
		//再次对读出Blob句柄 
		ps = conn.prepareStatement(" SELECT SYS_MSWORD_TEMPLATE FROM SYS_MSWORD WHERE SYS_MSWORD_ID=? FOR UPDATE");
		ps.setString(1, sys_msword_id);
		ResultSet rs = ps.executeQuery();
		rs.next();
//		OracleThinBlob modelBLOB = (OracleThinBlob)rs.getBlob("TEMPLATE_FIEL");
		Object blobObj = rs.getBlob("SYS_MSWORD_TEMPLATE");
		InputStream is = new FileInputStream(new File(sys_msword_template) );
	//	getBookMarks(sys_msword_template);
		if (blobObj instanceof OracleThinBlob) {
			OracleThinBlob modelBlob = (OracleThinBlob)blobObj;
			OutputStream out = modelBlob.getBinaryOutputStream(); // 建立输出流
			copy(is, out);
		}
		else {
			BLOB modelBLOB = (BLOB)blobObj;
			OutputStream out = modelBLOB.getBinaryOutputStream(); // 建立输出流
			copy(is, out);
		}
		conn.commit();
		blobObj = null;
		rs.close();
		rs = null;
		conn.setAutoCommit(true);
		DbUtils.freeConnection(conn);
		conn = null;
	}
	
	private void copy(byte[] in, OutputStream out) throws IOException {
		//Assert.notNull(in, "No input byte array specified");
		//Assert.notNull(out, "No OutputStream specified");
		try {
			out.write(in);
		}
		finally {
			try {
				out.flush();
				out.close();
			}
			catch (IOException ex) {
				//logger.warn("Could not close OutputStream", ex);
			}
		}
	}
	
	private int copy(InputStream in, OutputStream out) throws IOException {
		//Assert.notNull(in, "No InputStream specified");
		//Assert.notNull(out, "No OutputStream specified");
		try {
			int byteCount = 0;
			byte[] buffer = new byte[1024];
			int bytesRead = -1;
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
				byteCount += bytesRead;
			}
			out.flush();
			return byteCount;
		}
		finally {
			try {
				in.close();
			}
			catch (IOException ex) {
				//logger.warn("Could not close InputStream", ex);
			}
			try {
				out.close();
			}
			catch (IOException ex) {
				//logger.warn("Could not close OutputStream", ex);
			}
		}
	}
	
	/*private String getBookMarks(String sys_msword_template) throws Exception{
		StringBuffer sb = new StringBuffer();
		WordExtractor we   =  new WordExtractor ();
	//	String str = we.extractText( new FileInputStream(new File(sys_msword_template) )  );
	//	System.out.println("word content is \n"+str);
		POIFSFileSystem pfs = new POIFSFileSystem(  new FileInputStream(new File(sys_msword_template) )  );
		DirectoryEntry de = pfs.getRoot();
		Iterator it = (Iterator)de.getEntries();
		int j=1;
		while(it.hasNext()){
			Entry obj = (Entry)it.next();
			
			if(obj.isDocumentEntry()){
				DocumentEntry obj2 = (DocumentEntry)obj;
				System.out.println(" ---->>>" +j+ " size is "+ obj2.getSize() +" name is "+ obj2.getName() +" str is "+ obj2.toString());
			}
			else if(obj.isDirectoryEntry()){
				DirectoryEntry obj2 = (DirectoryEntry)obj;
				System.out.println(" &&&&&&& " +j+ " size is "+ obj2.getEntryCount() +" name is "+ obj2.getName() +" str is "+ obj2.toString());
			}
			j++;
		}
	    System.out.println("--------->>>> "	+ de.getName());
		//String str = we.extractText( new FileInputStream(new File(sys_msword_template) )  );
		//System.out.println(str);
		return sb.toString();
	}*/

}
