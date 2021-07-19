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
 * 数据下载
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
	
	// 通过doPost方法生成文件
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
	
	// 下载文件
	private void downloadFile(HttpServletRequest request, HttpServletResponse response){
		
	}
	
	// 把毫秒时间转换成yyyy-mm-dd的形式
	public static final String parseMillionSecond(String id){
		long l = Long.parseLong(id);
		Date dt = new Date(l);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(dt);
	}
	
	// 生成xls文件
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
		// 如果没有传入sid，则生成一个，并在后面的xml中传回去
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
		// 转化码表
		result = ColumnCode.parseColumnCode(columnName, result, displayType);
		int maxRows = Integer.parseInt(strFileSize);
		if (maxRecord != null && Integer.parseInt(strFileSize) * Integer.parseInt(strCurrPage) > Integer.parseInt(maxRecord)){
			maxRows = Integer.parseInt(maxRecord) - (Integer.parseInt(strCurrPage) -1) * Integer.parseInt(strFileSize);
		}
		if (downloadCompare != null && downloadCompare.equals("1")){
			if ( filePrefix!= null && filePrefix.equals("1")){
				strCurrPage = "比对成功文件--" + strCurrPage;
			}else{
				strCurrPage = "比对失败文件--" + strCurrPage;
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
		// 得到应用的根路径
		String rootPath = getRootPath();
		File file = new File(rootPath + "/" + tempFilePath );
		// 如果临时文件夹不存在，就创建一个
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
		// 创建标题栏
		os.write((columnCnName + "\n").getBytes());
		
		// 循环写入数据
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
	

	// 把生成的xls打包
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
		// 如果是申请下载，直接把文件放在跟目录下，如果不是申请（也就是说是临时下载）, 把文件放在按照日期命名的文件夹
		if (isApply == null || !isApply.equals("1")){
			String dateStr = parseMillionSecond(sid);
			File t = new File(rootPath + "/" + tempFilePath + "/" + dateStr);
			if (!t.exists()){
				isNeedDelete = true;
				// 创建今天的文件夹
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
		
		// 生成压缩文件后，删除原先的文件
		for (int i = 0; i < fileArray.length; i++){
			String tempPath = rootPath + "/" + tempFilePath + "/" + sid + "/" + fileArray[i];
			File tempFile = new File( tempPath );
			tempFile.delete();
		}
		file.delete();
		
		// 如果是申请下载，生成完毕后，修改数据库记录
		if (isApply != null && isApply.equals("1")){
			String updateSql = "update download_status set has_gener='1' where filepath='" + sid + "'";
			DBOperation operation = DBOperationFactory.createTimeOutOperation();
			operation.execute(updateSql, false);
		}
		
		
		// 删除以往的临时文件夹
		if (isNeedDelete){
			// 删除除了今天以外的所有文件夹
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
	
	// 通过doGet方法下载文件
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
					// 对于不是重复下载的数据，下载完成后更新数据库记录, 改为已下载状态
					String updateSql = "update download_status set status='3' where status != '5' " +
							"and download_status_id = '" + download_status_id + 
							"' and is_mutil_download = '0'";
					DBOperation operation = DBOperationFactory.createTimeOutOperation();
					operation.execute(updateSql, false);
				}
			}else{
				response.setContentType("text/html;charset=GBK");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().println("您所下载的文件不存在！原因可能是您已经下载过该文件了");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 把当前列表的数据写到xls文件中去
	 * @param result
	 * @param columnCnName
	 * @return
	 * @throws IOException 
	 * @throws WriteException
	 */
	private File listToXls(List result, String columnCnName, String columnName, String filePath, String sheetName, String fileName, int maxRows) throws IOException, WriteException{
		// 得到应用的根路径
		String rootPath = getRootPath();
		File file = new File(rootPath + "/" + tempFilePath );
		// 如果临时文件夹不存在，就创建一个
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
		//创建标题栏
		for (int columnNum = 0; columnNum < columnCnNameArray.length; columnNum++){
			jxl.write.Label label = new jxl.write.Label(columnNum, 0, columnCnNameArray[columnNum]);
			ws.addCell(label);
		}
		// 循环写入数据
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
		
		// 得到应用的根路径
		String rootPath = getRootPath();
		File file = new File(rootPath + "/" + tempFilePath );
		// 如果临时文件夹不存在，就创建一个
		if (!file.exists()){
			file.mkdir();
		}
		file = new File(rootPath + "/" + tempFilePath + "/" + sid);
		if (!file.exists()){
			file.mkdir();
		}
		
		file = new File(rootPath + "/" + tempFilePath + "/" + sid + "/" + "报告.txt");
		if (!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		
		String reportText = "【比对条件】\r\n";
		reportText += compareCondition + "\r\n";
		reportText += "\r\n【备注】" + "\r\n";
		reportText += remark + "\r\n";
		reportText += "\r\n【比对结果】\r\n";
		reportText += "上传数据条数："  + totalRecord + "\r\n";
		reportText += "比对数据成功条数：" + successNumber + "；比对成功结果放置'比对成功文件'中\r\n";
		reportText += "比对数据失败条数：" + failedNumber + "；比对失败结果放置'比对失败文件'中\r\n";
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
	 * 删除指定文件夹下的所有文件夹(带排除)
	 * @param folderPrefix
	 */
	public void deleteTempDirs(String folderPathPrefix, String expFolderName){
		File file = new File(folderPathPrefix);
		// 文件夹存在的时候才操作
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
	 * 删除指定文件夹下的所有文件夹(不带排除)
	 * @param folderPathPrefix
	 */
	public void deleteTempDirs(String folderPathPrefix){
		File file = new File(folderPathPrefix);
		// 只有当file存在并且是文件夹的时候才执行
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
	 * 记录日志
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
        db.setValue("trdname", "数据下载");
        db.setValue("trdcode", "60310000");
        db.setValue("trdtype", "数据下载");
        db.setValue("errcode", "000000");
        db.setValue("errdesc", "");
        ActionUtil.callService(action, request, txnContext);
		return txnContext;
    }
}
