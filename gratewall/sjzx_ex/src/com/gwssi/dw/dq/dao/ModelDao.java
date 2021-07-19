package com.gwssi.dw.dq.dao;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import oracle.sql.BLOB;
import weblogic.jdbc.vendor.oracle.OracleThinBlob;

import com.genersoft.frame.base.database.DBException;
import com.genersoft.frame.base.database.DbUtils;
import com.gwssi.common.util.UuidGenerator;

/**
 * 动态模板数据库的相关操作方法,包括插入、读取等
 * @version 1.0
 * @author FlyFish
 */
public class ModelDao
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
	public void addModel(String subjectId, String name, String modelMemo,String model, String connType) throws Exception
	{
		Connection conn = DbUtils.getConnection(connType);
		String uuid = UuidGenerator.getUUID();
//		System.out.println(uuid);
		
		conn.setAutoCommit(false); // 取消Connection对象的auto commit属性
		PreparedStatement ps = conn
				.prepareStatement("insert into DQ_TEMPLATE(" +
						"TEMPLATE_ID, SUBJECT_ID, TEMPLATE_NAME, TARGET_MEMO,TEMPLATE_FIEL) " +
						"values(?,?,?,?,?) ");
		ps.setString(1, uuid);
		ps.setString(2, subjectId);
		ps.setString(3, name);
		ps.setString(4, modelMemo);
		ps.setBlob(5, BLOB.empty_lob());
		ps.executeUpdate();
		
		//再次对读出Blob句柄 
		ps = conn.prepareStatement("select TEMPLATE_FIEL from DQ_TEMPLATE where TEMPLATE_ID=? for update");
		ps.setString(1, uuid);
		ResultSet rs = ps.executeQuery();
		rs.next();
//		OracleThinBlob modelBLOB = (OracleThinBlob)rs.getBlob("TEMPLATE_FIEL");
		Object blobObj = rs.getBlob("TEMPLATE_FIEL");
		if (blobObj instanceof OracleThinBlob) {
			OracleThinBlob modelBlob = (OracleThinBlob)blobObj;
			OutputStream out = modelBlob.getBinaryOutputStream(); // 建立输出流
			copy(model.getBytes(), out);
		}
		else {
			BLOB modelBLOB = (BLOB)blobObj;
			OutputStream out = modelBLOB.getBinaryOutputStream(); // 建立输出流
			copy(model.getBytes(), out);
		}
		conn.commit();
		blobObj = null;
		rs.close();
		rs = null;
		conn.setAutoCommit(true);
		DbUtils.freeConnection(conn);
		conn = null;
	}
	
	/**
	 * 更新模板
	 * @param id
	 * @param name
	 * @param modelMemo
	 * @param model
	 * @param connType
	 * @throws Exception
	 */
	public void updateModel(String id, String name, String modelMemo,String model, String connType) throws Exception {
		Connection conn = DbUtils.getConnection(connType);
		conn.setAutoCommit(false); // 取消Connection对象的auto commit属性
		PreparedStatement ps = conn.prepareStatement("update DQ_TEMPLATE set TEMPLATE_FIEL=?,TEMPLATE_NAME=?,TARGET_MEMO=? where TEMPLATE_ID=?");
		ps.setBlob(1, BLOB.empty_lob());
		ps.setString(2, name);
		ps.setString(3, modelMemo);
		ps.setString(4, id);
		ps.executeUpdate();
		
		//再次对读出Blob句柄 
		ps = conn.prepareStatement("select TEMPLATE_FIEL from DQ_TEMPLATE where TEMPLATE_ID=? for update");
		ps.setString(1, id);
		ResultSet rs = ps.executeQuery();
		rs.next();
		Object blobObj = rs.getBlob("TEMPLATE_FIEL");
		if (blobObj instanceof OracleThinBlob) {
			OracleThinBlob modelBlob = (OracleThinBlob)blobObj;
			OutputStream out = modelBlob.getBinaryOutputStream(); // 建立输出流
			copy(model.getBytes(), out);
		}
		else {
			BLOB modelBLOB = (BLOB)blobObj;
			OutputStream out = modelBLOB.getBinaryOutputStream(); // 建立输出流
			copy(model.getBytes(), out);
		}
		conn.commit();
		blobObj = null;
		rs.close();
		rs = null;
		conn.setAutoCommit(true);
		DbUtils.freeConnection(conn);
		conn = null;
	}
	
//	protected Object prepareLob(Connection con, Class lobClass) throws Exception {
//		/*
//		BLOB blob = BLOB.createTemporary(con, false, BLOB.DURATION_SESSION);
//		blob.open(BLOB.MODE_READWRITE);
//		return blob;
//		*/
//		Method createTemporary = lobClass.getMethod(
//				"createTemporary", new Class[] {Connection.class, boolean.class, int.class});
//		Object lob = createTemporary.invoke(
//				null, new Object[] {con, new Boolean(false), new Integer(BLOB.DURATION_SESSION)});
//		Method open = lobClass.getMethod("open", new Class[] {int.class});
//		open.invoke(lob, new Object[] {modeReadWriteConstants.get(lobClass)});
//		return lob;
//	}
	
	/**
	 * 通过ID从数据库中读出模板
	 * @param id 模板ID
	 * @param connType 数据库连接方式
	 * @return 模板内容的String
	 * @throws Exception
	 */
	public void getModel(String id, String connType, OutputStream out) {
		try {
			String sql = "select TEMPLATE_FIEL from DQ_TEMPLATE where TEMPLATE_ID=?";
			Connection conn = DbUtils.getConnection(connType);
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			InputStream in = null;
//			byte[] back = null;
//			StringBuffer backStr = new StringBuffer();
			if (rs.next()) {
				Object blobObj = rs.getBlob("TEMPLATE_FIEL");
				Blob modelBLOB = (Blob)blobObj;
				in = modelBLOB.getBinaryStream();
				copy(in, out);
//				Blob modelBLOB = rs.getBlob("TEMPLATE_FIEL");
//				in = modelBLOB.getBinaryStream();
//				back = new byte[2048];
//				int len = 0;
//				while ((len = in.read(back,0,back.length)) != -1) {
//				backStr.append(new String(back, 0, len));
//				}
//				in.close();
			}
//			if (back != null) {
//			return backStr.toString();
//			}
			DbUtils.freeConnection(conn);
			conn = null;
		} catch(DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		return "false";
	}
	
	/**
	 * 从数据库中读出所有模板,组成列表返回,暂时供测试用
	 * @param connType 数据库连接方式
	 * @return 列表组成的String,"id,name;id,name;"的格式
	 * @throws DBException
	 */
	public String getModelList(String connType) throws DBException {
		String sql = "select TEMPLATE_ID, TEMPLATE_NAME from DQ_TEMPLATE order by TEMPLATE_NAME asc";
		List tempList = DbUtils.select(sql, connType);
		StringBuffer back = new StringBuffer();
		for (int i=0;i < tempList.size();i++) {
			Map temp = (Map)tempList.get(i);
			back.append((String)temp.get("TEMPLATE_ID") + "," + (String)temp.get("TEMPLATE_NAME"));
			if (i != tempList.size() -1) {
				back.append(";");
			}
		}
		return back.toString();
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
	
	/**
	 * 将模板插入数据库
	 * @param subjectId 主题ID
	 * @param name 模板名称
	 * @param model 模板内容
	 * @param connType 数据库连接方式
	 * @throws Exception
	 */
	public void insertModel(String subjectId, String name, String model, String connType) throws DBException{
		Connection conn = DbUtils.getConnection(connType);
		String uuid = UuidGenerator.getUUID();
		String sql = 
			"insert into DQ_TEMPLATE (TEMPLATE_ID,SUBJECT_ID,TEMPLATE_NAME,TEMPLATE_FIEL) " +
			"values (?,?,?,?) ";
		PreparedStatement ps;
		BLOB blob = null;
		try {
			blob = BLOB.createTemporary(conn, false, BLOB.DURATION_SESSION);
			blob.open(BLOB.MODE_READWRITE);
			Method methodToInvoke = blob.getClass().getMethod("getBinaryOutputStream", new Class[0]);
			OutputStream out = (OutputStream) methodToInvoke.invoke(blob, (Object[]) null);
			try {
				copy(model.getBytes(), out);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ps = conn.prepareStatement(sql);
			ps.setString(1, uuid);
			ps.setString(2, subjectId);
			ps.setString(3, name);
			ps.setBlob(4, blob);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
//			System.out.println("taikewule");
			e.printStackTrace();
		}
		finally {
			if (blob != null)
				freeBlob(blob);
			DbUtils.freeConnection(conn);
			conn = null;
		}
		
	}
	
	private void freeBlob(BLOB lob) {
		Method freeTemporary;
		try {
			freeTemporary = lob.getClass().getMethod("freeTemporary", new Class[0]);
			freeTemporary.invoke(lob, new Object[0]);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
