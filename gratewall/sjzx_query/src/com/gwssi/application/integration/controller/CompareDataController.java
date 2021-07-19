package com.gwssi.application.integration.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.application.integration.model.EntBo;
import com.gwssi.application.integration.service.CompareDataService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.controller.BaseController;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.util.UuidGenerator;

@Controller
@RequestMapping("/datas")
public class CompareDataController extends BaseController {

	public static final String EXTENSION_XLS = "xls";// excel 2003及以下
	public static final String EXTENSION_XLSX = "xlsx";// excel 2007及以上

	@Autowired
	private CompareDataService dataService;

	@SuppressWarnings({ "rawtypes", "unused", "deprecation" })
	@RequestMapping("/upload")
	public void show(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		HttpServletRequest request = req.getHttpRequest();
		String realPath = request.getRealPath("/");
		// 定义上传的目录
		String dirPath = realPath + "/upload";
		File dirFile = new File(dirPath);
		// 自动创建上传的目录
		if (!dirFile.exists())
			dirFile.mkdirs();
		// 上传操作
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		String fileName = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		List contents = null;
		List results = null;
		try {
			List items = upload.parseRequest(request); // 3name=null name=null
			if (null != items) {
				Iterator itr = items.iterator();
				while (itr.hasNext()) {
					FileItem item = (FileItem) itr.next();
					if (item.isFormField()) {
						continue;
					} else {
						String ext = getExt(item.getName());
						fileName = UUID.randomUUID().toString() + ext;
						// 上传文件的目录
						File savedFile = new File(dirPath, fileName);
						item.write(savedFile); // 保存文件到磁盘目录之中
						contents = this.readExcel(dirPath + "/" + fileName);
						String tid = dataService.save(contents);
						resp.addResponseBody(tid);
					}
				}
			}

		} catch (Exception e) {
		}

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("/getResults")
	public void toCompares(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String tid = req.getParameter("tid");
		if (StringUtils.isEmpty(tid)) {
			resp.addResponseBody("参数不能为空");
		}
		Map map = dataService.query(tid);
		resp.addAttr("count", map);
	}

	
	/**
	 * 默认为0的时候是无对比条件查询，仅根据用户上传的内容tid查询 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getList")
	public void getList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String tid = req.getParameter("tid");
		String select = req.getParameter("select");
		if(StringUtils.isEmpty(select)){
			select = "0";
		}
		int type = Integer.parseInt(select);
		if (StringUtils.isNotEmpty(tid)) {
			List lists = dataService.getList(tid,type);
			resp.addGrid("gridpanel", lists);
		}else{
			resp.addResponseBody("参数不能为空");
		}
	}

	/**
	 * 获取一个文件的后缀(带有点)
	 * 
	 * @param fileName
	 *            文件名
	 * @return 返回文件的后缀
	 */
	public static String getExt(String fileName) {
		int pos = fileName.lastIndexOf(".");
		if (pos == -1)
			return "";
		return fileName.substring(pos, fileName.length());
	}

	/**
	 * 获取一个文件的后缀(不带有点)
	 * 
	 * @param fileName
	 *            文件名
	 * @return 返回文件的后缀
	 */
	public static String getExtNoPoint(String fileName) {
		if (fileName.lastIndexOf(".") == -1)
			return "";
		int pos = fileName.lastIndexOf(".") + 1;
		return fileName.substring(pos, fileName.length());
	}

	/**
	 * 预读文件是否存在及是否excel
	 * 
	 * @param filePath
	 * @throws FileNotFoundException
	 * @throws FileFormatException
	 */
	public void preReadCheck(String filePath) {
		// 常规检查
		File file = new File(filePath);
		if (!file.exists()) {
			// throw new FileNotFoundException("传入的文件不存在：" + filePath);
		}
		if (!(filePath.endsWith(EXTENSION_XLS) || filePath.endsWith(EXTENSION_XLSX))) {
			// throw new FileFormatException("传入的文件不是excel");
		}
	}

	/**
	 * 
	 * @param columnNum
	 *            指定要匹配哪一列,默认下标从0，即第一列开始
	 * @param filePath
	 *            传入文件路径
	 * @throws FileNotFoundException
	 * @throws FileFormatException
	 */
	public List readExcel(String filePath) {
		// 检查
		this.preReadCheck(filePath);
		// 获取workbook对象
		Workbook workbook = null;
		List arry = new ArrayList();
		EntBo entBo = null;
		try {
			InputStream is = new FileInputStream(filePath);
			workbook = new XSSFWorkbook(is); // 只做 2003

			// 读文件 一个sheet一个sheet地读取
			String tid = UuidGenerator.getUUID();
			for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
				Sheet sheet = workbook.getSheetAt(numSheet);
				if (sheet == null) {
					continue;
				}
				int firstRowIndex = sheet.getFirstRowNum();
				int lastRowIndex = sheet.getLastRowNum();

				for (int rowIndex = firstRowIndex + 1; rowIndex <= lastRowIndex; rowIndex++) {
					Row currentRow = sheet.getRow(rowIndex);// 当前行
					entBo = new EntBo();
					entBo.setTid(tid);
					String regNo = this.getCellValue(currentRow.getCell(0), true);
					String entName = this.getCellValue(currentRow.getCell(1), true);
					String entType = this.getCellValue(currentRow.getCell(2), true);
					entBo.setRegno(regNo);
					entBo.setEntname(entName);
					entBo.setEnttype(entType);
					arry.add(entBo);
				}
			}
			return arry;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 取单元格的值
	 * 
	 * @param cell
	 *            单元格对象
	 * @param treatAsStr
	 *            为true时，当做文本来取值 (取到的是文本，不会把“1”取成“1.0”)
	 * @return
	 */
	public String getCellValue(Cell cell, boolean treatAsStr) {
		if (cell == null) {
			return "";
		}

		if (treatAsStr) {
			// 虽然excel中设置的都是文本，但是数字文本还被读错，如“1”取成“1.0”
			// 加上下面这句，临时把它当做文本来读取
			cell.setCellType(Cell.CELL_TYPE_STRING);
		}

		if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(cell.getBooleanCellValue());
		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			return String.valueOf(cell.getNumericCellValue());
		} else {
			return String.valueOf(cell.getStringCellValue());
		}
	}

}
