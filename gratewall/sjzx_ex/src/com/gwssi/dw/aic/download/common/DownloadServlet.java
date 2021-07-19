package com.gwssi.dw.aic.download.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.web.channel.FreezeForm;
import cn.gwssi.common.web.config.ActionDefine;
import cn.gwssi.common.web.util.ActionUtil;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.database.DBOperation;
import com.gwssi.common.database.DBOperationFactory;
import com.gwssi.common.result.Page;
import com.gwssi.dw.aic.bj.ColumnCode;

/**
 * ��������
 * @author BarryWei
 * @data   2008-09-09 
 */
public class DownloadServlet extends HttpServlet
{
	public static final String DB_CONFIG = "app";
	private static final String DB_CONNECT_TYPE = "downloadFilePath";
	private static final long	serialVersionUID	= -8469828897873010577L;

	private static String tempFilePath = "download";
	private String rootPath;
	static final String ZIPXLS		= "ZIP";
	static final String GENXLS		= "XLS";
	static final String GENTXT		= "TXT";
	static final String GENDB		= "DATABASE";
	static final String DOWNLOAD	= "DOWNLOAD";
	static final String REPORT		= "REPORT";

	public void init(ServletConfig config) {
        String rootPath = java.util.ResourceBundle.getBundle(DB_CONFIG).getString(DB_CONNECT_TYPE);
        File file = new File(rootPath);
        if (!file.exists()){
        	file.mkdirs();
        }
        setRootPath(rootPath);
	}
	
	// ͨ��doPost���������ļ�
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String operate = request.getParameter("operate");
		try {
			if (operate.equalsIgnoreCase(DOWNLOAD)){
				downloadFile(request, response);
			}else if(operate.equalsIgnoreCase(REPORT)){
				genReport(request, response);
			}else if(operate.equalsIgnoreCase(GENXLS) || 
					operate.equalsIgnoreCase(GENTXT) || 
					operate.equalsIgnoreCase(GENDB)){
				genXlsFile(request, response);
			}else if(operate.equalsIgnoreCase(ZIPXLS)){
				zipXlsFile(request, response);
			}
		} catch (DBException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
	}
	
	// �����ļ�
	private void downloadFile(HttpServletRequest request, HttpServletResponse response){
		
	}
	
	// �Ѻ���ʱ��ת����yyyy-mm-dd����ʽ
	public static final String parseMillionSecond(String id){
		long l = Long.parseLong(id);
		Date dt = new Date(l);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(dt);
	}
	
	// ����xls�ļ�
	private void genXlsFile(HttpServletRequest request, HttpServletResponse response) throws DBException, WriteException, IOException{
		String sql = request.getParameter("record:query_sql");
		String columnName = request.getParameter("record:columnsEnArray");
		String columnCnName = request.getParameter("record:columnsCnArray");
		String defineFileName = request.getParameter("fileName");
		String strCurrPage = request.getParameter("currPage");
		String strFileSize = request.getParameter("fileSize");
		String maxRecord = request.getParameter("maxRecord");
		String sid = request.getParameter("sid");
		String operate = request.getParameter("operate");
		String downloadCompare = request.getParameter("downloadCompare");
		String filePrefix = request.getParameter("filePrefix");
		String displayType = request.getParameter("displayType");
		if (displayType == null || displayType.equals("")){
			displayType = "name";
		}
//		System.out.println("displayType:" + displayType);
//		
//		System.out.println("downloadCompare:" + downloadCompare);
//		System.out.println("filePrefix:" + filePrefix);
//		
		if (strCurrPage == null || strFileSize == null){
			return;
		}
		// ���û�д���sid��������һ�������ں����xml�д���ȥ
		if (sid == null){
			sid = String.valueOf(System.currentTimeMillis());
		}
		
		int currPage = new Integer(strCurrPage).intValue();
		int fileSize = new Integer(strFileSize).intValue();
//		
//		sql = java.net.URLDecoder.decode(sql, "UTF-8");
//		columnName = java.net.URLDecoder.decode(columnName, "UTF-8");
//		columnCnName = java.net.URLDecoder.decode(columnCnName, "UTF-8");
//		defineFileName = java.net.URLDecoder.decode(defineFileName, "UTF-8");
//		
		DBOperation operation = DBOperationFactory.createTimeOutOperation();
		Page newPage = new Page(currPage, fileSize);
		List result = operation.selectInOrder(sql, newPage);
		// ת�����
		result = ColumnCode.parseColumnCode(columnName, result, displayType);
		int maxRows = Integer.parseInt(strFileSize);
		if (maxRecord != null && Integer.parseInt(strFileSize) * Integer.parseInt(strCurrPage) > Integer.parseInt(maxRecord)){
			maxRows = Integer.parseInt(maxRecord) - (Integer.parseInt(strCurrPage) -1) * Integer.parseInt(strFileSize);
		}
		if (downloadCompare != null && downloadCompare.equals("1")){
			if ( filePrefix!= null && filePrefix.equals("1")){
				strCurrPage = "�ȶԳɹ��ļ�--" + strCurrPage;
			}else{
				strCurrPage = "�ȶ�ʧ���ļ�--" + strCurrPage;
			}
		}
		if (operate.equalsIgnoreCase(GENXLS)){
			listToXls(result, columnCnName, columnName, sid, defineFileName, strCurrPage, maxRows );
		}else if (operate.equalsIgnoreCase(GENTXT)){
			listToTxt(result, columnCnName, columnName, sid, defineFileName, strCurrPage, maxRows );
		}
		
		response.setContentType("text/xml;charset=GBK");
		response.setHeader("Cache-Control", "no-cache");
		StringBuffer bf = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?>");
		bf.append("<results>");
		bf.append("<sid>" + sid + "</sid>");
		bf.append("</results>");
		
		PrintWriter out = response.getWriter();
		out.println(bf.toString());
	}
	
	/**
	 * List To Txt
	 * @param result
	 * @param columnCnName
	 * @param columnName
	 * @param filePath
	 * @param sheetName
	 * @param fileName
	 * @param maxRows
	 * @return
	 * @throws IOException
	 * @throws WriteException
	 */
	private File listToTxt(List result, String columnCnName, String columnName, String filePath, String sheetName, String fileName, int maxRows) throws IOException, WriteException{
		// �õ�Ӧ�õĸ�·��
		String rootPath = getRootPath();
		File file = new File(rootPath + "/" + tempFilePath );
		// �����ʱ�ļ��в����ڣ��ʹ���һ��
		if (!file.exists()){
			file.mkdir();
		}
		file = new File(rootPath + "/" + tempFilePath + "/" + filePath);
		if (!file.exists()){
			file.mkdir();
		}
		
		file = new File(rootPath + "/" + tempFilePath + "/" + filePath + "/" + fileName + ".txt");
		if (!file.exists()){
			file.createNewFile();
		}
		
		String[] columnCnNameArray = columnCnName.split(",");
		String[] columnNameArray = ColumnCode.getColumnNames(columnName); // columnName.split(",");
		
		OutputStream os = new FileOutputStream(file);
		// ����������
		os.write((columnCnName + "\n").getBytes());
		
		// ѭ��д������
		for (int rowNum = 1; rowNum <= maxRows && rowNum <= result.size(); rowNum++ ){
			Map map = (Map) result.get(rowNum - 1);
			String oneData = "";
			for (int columnNum = 0; columnNum < columnCnNameArray.length; columnNum++){
				String data = "";
				Object obj = map.get(columnNameArray[columnNum]);
				if (obj != null){
					data = obj.toString();
				}
				oneData += data;
				if (columnNum != columnCnNameArray.length - 1){
					oneData += ",";
				}
			}
			os.write((oneData + "\n").getBytes());
		}
		os.close();
		
		return file;
	}
	

	// �����ɵ�xls���
	private void zipXlsFile(HttpServletRequest request, HttpServletResponse response) throws IOException, DBException{
		String sid = request.getParameter("sid");
		String isApply = request.getParameter("isApply");
		String fileName = request.getParameter("fileName");
//		fileName = java.net.URLDecoder.decode(fileName, "UTF-8");
		
		String rootPath = getRootPath();
		File file = new File(rootPath + "/" + tempFilePath + "/" + sid);
		if (!file.isDirectory()){
			throw new IOException();
		}
		String[] fileArray = file.list();
		
		String newSid = sid;
		boolean isNeedDelete = false;
		// ������������أ�ֱ�Ӱ��ļ����ڸ�Ŀ¼�£�����������루Ҳ����˵����ʱ���أ�, ���ļ����ڰ��������������ļ���
		if (isApply == null || !isApply.equals("1")){
			String dateStr = parseMillionSecond(sid);
			File t = new File(rootPath + "/" + tempFilePath + "/" + dateStr);
			if (!t.exists()){
				isNeedDelete = true;
				// ����������ļ���
				t.mkdir();
			}
			newSid = dateStr + "/" + sid;
		}
		
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream( getRootPath() + "/" + tempFilePath + "/" + newSid + ".zip"));
		ZipEntry zipEntry = null;
		byte[] buf = new byte[1024];
		int readLen = 0;
		
		for (int i = 0; i < fileArray.length; i++){
			String tempPath = rootPath + "/" + tempFilePath + "/" + sid + "/" + fileArray[i];
			File tempFile = new File( tempPath );
			zipEntry = new ZipEntry( fileArray[i] );
			zipEntry.setSize(tempFile.length());
			zipEntry.setTime(tempFile.lastModified());
			zos.putNextEntry(zipEntry);
			InputStream is = new BufferedInputStream(new FileInputStream(tempFile));
			while ( (readLen = is.read(buf, 0, 1024)) != -1 ){
				zos.write(buf, 0, readLen);
			}
			is.close();
		}
		zos.close();
		
		// ����ѹ���ļ���ɾ��ԭ�ȵ��ļ�
		for (int i = 0; i < fileArray.length; i++){
			String tempPath = rootPath + "/" + tempFilePath + "/" + sid + "/" + fileArray[i];
			File tempFile = new File( tempPath );
			tempFile.delete();
		}
		file.delete();
		
		// ������������أ�������Ϻ��޸����ݿ��¼
		if (isApply != null && isApply.equals("1")){
			String updateSql = "update download_status set has_gener='1' where filepath='" + sid + "'";
			DBOperation operation = DBOperationFactory.createTimeOutOperation();
			operation.execute(updateSql, false);
		}
		
		
		// ɾ����������ʱ�ļ���
		if (isNeedDelete){
			// ɾ�����˽�������������ļ���
			deleteTempDirs(rootPath + "/" + tempFilePath, parseMillionSecond(sid));
		}
		
		response.setContentType("text/xml;charset=GBK");
		response.setHeader("Cache-Control", "no-cache");
		StringBuffer bf = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?>");
		bf.append("<results>");
		bf.append("<fileName>" + fileName + "</fileName>");
		bf.append("<sid>" + newSid + "</sid>");
		bf.append("</results>");
		
		PrintWriter out = response.getWriter();
		out.println(bf.toString());
		
	}	
	
	// ͨ��doGet���������ļ�
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{ 
		String displayName = request.getParameter("displayName");
		request.setCharacterEncoding("GBK");
		String filePath = request.getParameter("filePath");
//		displayName = java.net.URLDecoder.decode(displayName, "UTF-8");
		String download_status_id = request.getParameter("download_status_id");
		
		try {
			File file = new File( getRootPath() + "/" + tempFilePath + "/" + filePath);
			if (file.exists()){
				response.setContentType("application/zip;charset=GBK");
				response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(displayName + ".zip", "UTF-8"));
				response.setContentLength((int) file.length());
				FileInputStream bis = new FileInputStream(file);
				OutputStream os = response.getOutputStream();
				byte[] buf = new byte[1024];
				int readLen = 0;
				while((readLen = bis.read(buf, 0, 1024)) != -1){
					os.write(buf, 0, readLen);
				}
				bis.close();
				os.close();
				if (download_status_id != null && !download_status_id.equals("") && !download_status_id.equals("null")){
					// ���ڲ����ظ����ص����ݣ�������ɺ�������ݿ��¼, ��Ϊ������״̬
					String updateSql = "update download_status set status='3' where status != '5' " +
							"and download_status_id = '" + download_status_id + 
							"' and is_mutil_download = '0'";
					DBOperation operation = DBOperationFactory.createTimeOutOperation();
					operation.execute(updateSql, false);
				}
			}else{
				response.setContentType("text/html;charset=GBK");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().println("�������ص��ļ������ڣ�ԭ����������Ѿ����ع����ļ���");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �ѵ�ǰ�б������д��xls�ļ���ȥ
	 * @param result
	 * @param columnCnName
	 * @return
	 * @throws IOException 
	 * @throws WriteException
	 */
	private File listToXls(List result, String columnCnName, String columnName, String filePath, String sheetName, String fileName, int maxRows) throws IOException, WriteException{
		// �õ�Ӧ�õĸ�·��
		String rootPath = getRootPath();
		File file = new File(rootPath + "/" + tempFilePath );
		// �����ʱ�ļ��в����ڣ��ʹ���һ��
		if (!file.exists()){
			file.mkdir();
		}
		file = new File(rootPath + "/" + tempFilePath + "/" + filePath);
		if (!file.exists()){
			file.mkdir();
		}
		
		file = new File(rootPath + "/" + tempFilePath + "/" + filePath + "/" + fileName + ".xls");
		if (!file.exists()){
			file.createNewFile();
		}
		
		String[] columnCnNameArray = columnCnName.split(",");
		String[] columnNameArray = ColumnCode.getColumnNames(columnName); // .split(",");
		WritableWorkbook wwb = Workbook.createWorkbook(file);
		WritableSheet ws = wwb.createSheet(sheetName, 0);
		//����������
		for (int columnNum = 0; columnNum < columnCnNameArray.length; columnNum++){
			jxl.write.Label label = new jxl.write.Label(columnNum, 0, columnCnNameArray[columnNum]);
			ws.addCell(label);
		}
		// ѭ��д������
		for (int rowNum = 1; rowNum <= maxRows && rowNum <= result.size(); rowNum++ ){
			Map map = (Map) result.get(rowNum - 1);
			for (int columnNum = 0; columnNum < columnCnNameArray.length; columnNum++){
				String data = "";
				Object obj = map.get(columnNameArray[columnNum]);
				if (obj != null){
					data = obj.toString();
				}
				jxl.write.Label label = new jxl.write.Label(columnNum, rowNum, data);
				ws.addCell(label);
			}
		}
		wwb.write();
		wwb.close();
		return file;
	}
	
	private boolean genReport(HttpServletRequest request, HttpServletResponse response){
		String sid = request.getParameter("sid");
		String successNumber = request.getParameter("successNumber");
		String failedNumber = request.getParameter("failedNumber");
		String compareCondition = request.getParameter("compareCondition");
		String remark = request.getParameter("remark");
		String totalRecord = request.getParameter("totalRecord");
		
//		try {
//			remark = java.net.URLDecoder.decode(remark, "UTF-8");
//			compareCondition = java.net.URLDecoder.decode(compareCondition, "UTF-8");
//		} catch (UnsupportedEncodingException e1) {
//			compareCondition = "";
//			remark = "";
//		}
		
		// �õ�Ӧ�õĸ�·��
		String rootPath = getRootPath();
		File file = new File(rootPath + "/" + tempFilePath );
		// �����ʱ�ļ��в����ڣ��ʹ���һ��
		if (!file.exists()){
			file.mkdir();
		}
		file = new File(rootPath + "/" + tempFilePath + "/" + sid);
		if (!file.exists()){
			file.mkdir();
		}
		
		file = new File(rootPath + "/" + tempFilePath + "/" + sid + "/" + "����.txt");
		if (!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		
		String reportText = "���ȶ�������\r\n";
		reportText += compareCondition + "\r\n";
		reportText += "\r\n����ע��" + "\r\n";
		reportText += remark + "\r\n";
		reportText += "\r\n���ȶԽ����\r\n";
		reportText += "�ϴ�����������"  + totalRecord + "\r\n";
		reportText += "�ȶ����ݳɹ�������" + successNumber + "���ȶԳɹ��������'�ȶԳɹ��ļ�'��\r\n";
		reportText += "�ȶ�����ʧ��������" + failedNumber + "���ȶ�ʧ�ܽ������'�ȶ�ʧ���ļ�'��\r\n";
		OutputStream os;
		try {
			os = new FileOutputStream(file);
			os.write(reportText.getBytes());
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	/**
	 * ɾ��ָ���ļ����µ������ļ���(���ų�)
	 * @param folderPrefix
	 */
	public void deleteTempDirs(String folderPathPrefix, String expFolderName){
		File file = new File(folderPathPrefix);
		// �ļ��д��ڵ�ʱ��Ų���
		if (file.exists() && file.isDirectory()){
			String[] fileName = file.list();
			for (int i = 0 ; i < fileName.length; i++){
				File t = new File(folderPathPrefix + "/" + fileName[i]);
				if (!fileName[i].equals(expFolderName) && t.isDirectory()){
					deleteTempDirs(folderPathPrefix + "/" + fileName[i]);
				}
			}
		}
	}
	
	/**
	 * ɾ��ָ���ļ����µ������ļ���(�����ų�)
	 * @param folderPathPrefix
	 */
	public void deleteTempDirs(String folderPathPrefix){
		File file = new File(folderPathPrefix);
		// ֻ�е�file���ڲ������ļ��е�ʱ���ִ��
		if (file.exists() && file.isDirectory()){
			String[] fileArray = file.list();
			for (int i=0; i < fileArray.length; i++){
				File t = new File(folderPathPrefix + "/" + fileArray[i]);
				if (t.exists() && t.isDirectory()){
					deleteTempDirs(folderPathPrefix + "/" + fileArray[i]);
				}else if (t.exists()){
					t.delete(); 
				}
			}
			file.delete();
		}
	}
	
	public String getRootPath()
	{
		return rootPath;
	}

	public void setRootPath(String rootPath)
	{
		this.rootPath = rootPath;
	}
	
	/**
	 * ��¼��־
	 * @param txnCode
	 * @param request
	 * @return
	 * @throws TxnException
	 */
	public static TxnContext callService(String txnCode, HttpServletRequest request, String msgStr) throws TxnException
    {
        ActionDefine action = ActionUtil.getActionDefine(txnCode);
        FreezeForm theForm = new FreezeForm();
        theForm.createDataForm(action, request);
        TxnContext txnContext = theForm.getContext();
        DataBus db = txnContext.getRecord("biz_log");
        db.setValue("desc", msgStr);
        db.setValue("trdname", "��������");
        db.setValue("trdcode", "60310000");
        db.setValue("trdtype", "��������");
        db.setValue("errcode", "000000");
        db.setValue("errdesc", "");
        ActionUtil.callService(action, request, txnContext);
		return txnContext;
    }
}
