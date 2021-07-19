package com.gwssi.common.servlets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.gwssi.common.component.logger.TxnLogger;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.constant.CollectConstants;
import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.database.DBOperation;
import com.gwssi.common.database.DBOperationFactory;

/**
 * 
 * 
 * ��Ŀ���ƣ�bjgs_exchange �����ƣ�DataExConfigServlet ���������ļ����·�� �����ˣ�lizheng ����ʱ�䣺Apr
 * 22, 2013 11:34:38 AM �޸��ˣ�lizheng �޸�ʱ�䣺Apr 22, 2013 11:34:38 AM �޸ı�ע��
 * 
 * @version
 * 
 */
@SuppressWarnings("serial")
public class DataExConfigServlet extends HttpServlet
{

	protected static Logger	logger		= TxnLogger
												.getLogger(DataExConfigServlet.class
														.getName());	// ��־

	DBOperation				operation	= null;

	public DataExConfigServlet()
	{
		operation = DBOperationFactory.createOperation();
	}

	/**
	 * ���������ļ�
	 */
	public void init() throws ServletException
	{
		InputStream is = null;
		try {
			if ("\\".equals(File.separator)) {
				// ϵͳΪwindows
				ExConstant.SERVER_SYSTEM_TYPE = "windows";
			} else if ("/".equals(File.separator)) {
				// ϵͳΪlinux
				ExConstant.SERVER_SYSTEM_TYPE = "linux";
			}

			is = this.getClass().getClassLoader().getResourceAsStream(
					"data.properties");
			Properties props = new Properties();
			props.load(is); // �����ļ�

			// ���Է��ʵ�ַ
			CollectConstants.WSDL_URL_TEST = props.getProperty("wsdl_url_test");

			// ����ӿڲ�ѯ��ʱ����
			ExConstant.INTERFACE_TIME = props.getProperty("interface_time");

			List fileDir = new ArrayList();

			if ("windows".equals(ExConstant.SERVER_SYSTEM_TYPE)) {
				// �ļ���Ŀ¼
				ExConstant.FILE_PATH_ROOT = props
						.getProperty("file_path_root_windows");
				// �������ļ����·��
				ExConstant.SHARE_RECORD = ExConstant.FILE_PATH_ROOT
						+ props.getProperty("share_record_windows");
				// �ɼ������ļ����·��
				ExConstant.COLLECT_RECORD = ExConstant.FILE_PATH_ROOT
						+ props.getProperty("collect_record_windows");
				// ������·��
				ExConstant.REPORT = ExConstant.FILE_PATH_ROOT
						+ props.getProperty("report_windows");
				// ����xml�ļ����·��
				ExConstant.SHARE_XML = ExConstant.FILE_PATH_ROOT
						+ props.getProperty("share_xml_windows");
				// ������������ļ����·��
				ExConstant.SHARE_CONFIG = ExConstant.FILE_PATH_ROOT
						+ props.getProperty("share_config_windows");
				// �ɼ�xml�ļ����·��
				ExConstant.COLLECT_XML = ExConstant.FILE_PATH_ROOT
						+ props.getProperty("collect_xml_windows");
				// ����ɼ���excel�ļ����·��
				ExConstant.RES_TBL_RECORD = ExConstant.FILE_PATH_ROOT
						+ props.getProperty("res_tbl_record_windows");
				// �ļ��ϴ��ļ����·��
				ExConstant.FILE_UPLOAD = ExConstant.FILE_PATH_ROOT
						+ props.getProperty("file_upload_windows");
				// FTP�ļ����·��
				ExConstant.FILE_FTP = ExConstant.FILE_PATH_ROOT
						+ props.getProperty("file_ftp_windows");
				// ���ݿ���־�ļ����·��
				ExConstant.FILE_DATABASE = ExConstant.FILE_PATH_ROOT
						+ props.getProperty("file_database_windows");
				// ȫ�ļ���ģ���ļ����·��
				ExConstant.TRS_TEMPLATE = ExConstant.FILE_PATH_ROOT
						+ props.getProperty("trs_template_windows");
				// ��������ļ����·��
				ExConstant.SERVICE_TARGET = ExConstant.FILE_PATH_ROOT
						+ props.getProperty("service_target_windows");
			} else if ("linux".equals(ExConstant.SERVER_SYSTEM_TYPE)) {
				// �ļ���Ŀ¼
				ExConstant.FILE_PATH_ROOT = props
						.getProperty("file_path_root_linux");
				// �������ļ����·��
				ExConstant.SHARE_RECORD = ExConstant.FILE_PATH_ROOT
						+ props.getProperty("share_record_linux");
				// �ɼ������ļ����·��
				ExConstant.COLLECT_RECORD = ExConstant.FILE_PATH_ROOT
						+ props.getProperty("collect_record_linux");
				// ������·��
				ExConstant.REPORT = ExConstant.FILE_PATH_ROOT
						+ props.getProperty("report_linux");
				// ����xml�ļ����·��
				ExConstant.SHARE_XML = ExConstant.FILE_PATH_ROOT
						+ props.getProperty("share_xml_linux");
				// ������������ļ����·��
				ExConstant.SHARE_CONFIG = ExConstant.FILE_PATH_ROOT
						+ props.getProperty("share_config_linux");
				// �ɼ�xml�ļ����·��
				ExConstant.COLLECT_XML = ExConstant.FILE_PATH_ROOT
						+ props.getProperty("collect_xml_linux");
				// ����ɼ���excel�ļ����·��
				ExConstant.RES_TBL_RECORD = ExConstant.FILE_PATH_ROOT
						+ props.getProperty("res_tbl_record_linux");
				// �ļ��ϴ��ļ����·��
				ExConstant.FILE_UPLOAD = ExConstant.FILE_PATH_ROOT
						+ props.getProperty("file_upload_linux");
				// FTP�ļ����·��
				ExConstant.FILE_FTP = ExConstant.FILE_PATH_ROOT
						+ props.getProperty("file_ftp_linux");
				// ���ݿ���־�ļ����·��
				ExConstant.FILE_DATABASE = ExConstant.FILE_PATH_ROOT
						+ props.getProperty("file_database_linux");
				// ȫ�ļ���ģ���ļ����·��
				ExConstant.TRS_TEMPLATE = ExConstant.FILE_PATH_ROOT
						+ props.getProperty("trs_template_linux");
				// ��������ļ����·��
				ExConstant.SERVICE_TARGET = ExConstant.FILE_PATH_ROOT
						+ props.getProperty("service_target_linux");
			}
			fileDir.add(ExConstant.SHARE_RECORD);
			fileDir.add(ExConstant.COLLECT_RECORD);
			fileDir.add(ExConstant.REPORT);
			fileDir.add(ExConstant.SHARE_XML);
			fileDir.add(ExConstant.SHARE_CONFIG);
			fileDir.add(ExConstant.COLLECT_XML);
			fileDir.add(ExConstant.RES_TBL_RECORD);
			fileDir.add(ExConstant.FILE_UPLOAD);
			fileDir.add(ExConstant.FILE_FTP);
			fileDir.add(ExConstant.FILE_DATABASE);
			fileDir.add(ExConstant.TRS_TEMPLATE);
			fileDir.add(ExConstant.SERVICE_TARGET);

			// System.out.println("ExConstant.SHARE_CONFIG is :"
			// + ExConstant.SHARE_CONFIG);
			// �����ļ�Ŀ¼
			for (int i = 0; i < fileDir.size(); i++) {
				if (!"".equals(fileDir.get(i))) {
					File tempDir = new File(fileDir.get(i).toString());
					if (!tempDir.exists()) {
						tempDir.mkdirs();
					}
				}
			}

//			this.delete("delete from XT_CCGL_WJLB");

			String[] inserts = new String[12];
			inserts[0] = "insert into XT_CCGL_WJLB (CCLBBH_PK, CCLBMC, LBMCBB, CCGML, EJMLGZ, GZFZZD, ZT, BZ)values ('01', 'share', '1', '"
					+ ExConstant.SHARE_RECORD + "', '1', null, '0', null)";
			inserts[1] = "insert into XT_CCGL_WJLB (CCLBBH_PK, CCLBMC, LBMCBB, CCGML, EJMLGZ, GZFZZD, ZT, BZ)values ('02', 'collect', '1', '"
					+ ExConstant.COLLECT_RECORD + "', '1', null, '0', null)";
			inserts[2] = "insert into XT_CCGL_WJLB (CCLBBH_PK, CCLBMC, LBMCBB, CCGML, EJMLGZ, GZFZZD, ZT, BZ)values ('03', 'report', '1', '"
					+ ExConstant.REPORT + "', '1', null, '0', null)";
			inserts[3] = "insert into XT_CCGL_WJLB (CCLBBH_PK, CCLBMC, LBMCBB, CCGML, EJMLGZ, GZFZZD, ZT, BZ)values ('04', 'share_xml', '1', '"
					+ ExConstant.SHARE_XML + "', '1', null, '0', null)";
			inserts[4] = "insert into XT_CCGL_WJLB (CCLBBH_PK, CCLBMC, LBMCBB, CCGML, EJMLGZ, GZFZZD, ZT, BZ)values ('05', 'collect_xml', '1', '"
					+ ExConstant.COLLECT_XML + "', '1', null, '0', null)";
			inserts[5] = "insert into XT_CCGL_WJLB (CCLBBH_PK, CCLBMC, LBMCBB, CCGML, EJMLGZ, GZFZZD, ZT, BZ)values ('06', 'res_tbl', '1', '"
					+ ExConstant.RES_TBL_RECORD + "', '1', null, '0', null)";
			inserts[6] = "insert into XT_CCGL_WJLB (CCLBBH_PK, CCLBMC, LBMCBB, CCGML, EJMLGZ, GZFZZD, ZT, BZ)values ('07', 'file_upload', '1', '"
					+ ExConstant.FILE_UPLOAD + "', '1', null, '0', null)";
			inserts[7] = "insert into XT_CCGL_WJLB (CCLBBH_PK, CCLBMC, LBMCBB, CCGML, EJMLGZ, GZFZZD, ZT, BZ)values ('08', 'file_ftp', '1', '"
					+ ExConstant.FILE_FTP + "', '1', null, '0', null)";
			inserts[8] = "insert into XT_CCGL_WJLB (CCLBBH_PK, CCLBMC, LBMCBB, CCGML, EJMLGZ, GZFZZD, ZT, BZ)values ('09', 'share_config', '1', '"
					+ ExConstant.SHARE_CONFIG + "', '1', null, '0', null)";
			inserts[9] = "insert into XT_CCGL_WJLB (CCLBBH_PK, CCLBMC, LBMCBB, CCGML, EJMLGZ, GZFZZD, ZT, BZ)values ('10', 'file_database', '1', '"
					+ ExConstant.FILE_DATABASE + "', '1', null, '0', null)";
			inserts[10] = "insert into XT_CCGL_WJLB (CCLBBH_PK, CCLBMC, LBMCBB, CCGML, EJMLGZ, GZFZZD, ZT, BZ)values ('11', 'trs_template', '1', '"
					+ ExConstant.TRS_TEMPLATE + "', '1', null, '0', null)";
			inserts[11] = "insert into XT_CCGL_WJLB (CCLBBH_PK, CCLBMC, LBMCBB, CCGML, EJMLGZ, GZFZZD, ZT, BZ)values ('12', 'service_target', '1', '"
					+ ExConstant.SERVICE_TARGET + "', '1', null, '0', null)";
			logger.debug("���ݼ������...");
			
			// for (int i = 0; i < inserts.length; i++) {
			// logger.debug(inserts[i]);
			// this.insert(inserts[i]);
			// }

		} catch (FileNotFoundException e) {
			logger.debug("δ�ҵ��ļ�..." + e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.debug("IOException..." + e);
			e.printStackTrace();
		} 
//		catch (DBException e) {
//			logger.debug("���ݿ��������..." + e);
//			e.printStackTrace();
//		} 
		finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		super.init();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
	}

	protected int delete(String sql) throws DBException
	{
		return operation.execute(sql, false);

	}

	protected int insert(String sql) throws DBException
	{
		return operation.execute(sql, false);
	}
}
