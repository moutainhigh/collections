<%@page import="org.apache.struts2.json.JSONUtil"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@page import="org.apache.commons.fileupload.FileItemFactory"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.FileNotFoundException"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.InputStream"%>
<%@page import="org.apache.poi.hssf.usermodel.HSSFWorkbook"%>
<%@page import="org.apache.poi.ss.usermodel.Cell"%>
<%@page import="org.apache.poi.ss.usermodel.Row"%>
<%@page import="org.apache.poi.ss.usermodel.Sheet"%>
<%@page import="org.apache.poi.ss.usermodel.Workbook"%>
<%@page import="org.apache.poi.xssf.usermodel.XSSFWorkbook"%>
<%@page import="org.apache.xmlbeans.impl.piccolo.io.FileFormatException"%>
<%@page import="java.text.DecimalFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");
	//获取文件的上传的具体目录
	String realPath = request.getRealPath("/");
	String username = request.getParameter("username");
	//定义上传的目录
	String dirPath = realPath + "/upload";
	File dirFile = new File(dirPath);
	//自动创建上传的目录
	if (!dirFile.exists())
		dirFile.mkdirs();
	//上传操作  
	FileItemFactory factory = new DiskFileItemFactory();
	//
	ServletFileUpload upload = new ServletFileUpload(factory);
	String fileName = null;
	HashMap<String, Object> map = new HashMap<String, Object>();
	try {
		List items = upload.parseRequest(request); //3name=null name=null
		if (null != items) {
			Iterator itr = items.iterator();
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				if (item.isFormField()) {
					continue;
				} else {
					String ext = getExt(item.getName());
					fileName = UUID.randomUUID().toString() + ext;
					//上传文件的目录
					File savedFile = new File(dirPath, fileName);
					item.write(savedFile); //保存文件到磁盘目录之中

					List<String> sb = this.readExcel(3, dirPath + "/" + fileName);
					//存入数据库内容中心
					System.out.println(sb);
					
					
					
					//数据对比功能
					String sql="";
					
					map.put("contents", sb);
					map.put("rows", sb.size());
					map.put("name", item.getName());//文件的原始名称
					map.put("newName", fileName);//文件的新名称
					map.put("ext", getExtNoPoint(fileName));//文件的后缀
					map.put("size", item.getSize());//文件的真实大小
					map.put("sizeString", countFileSize(item.getSize()));//获取文件转换以后的大写
					map.put("url", "upload/" + fileName);//获取文件的具体服务器的目录
				}
			}
		}
	} catch (Exception e) {

	}
	out.print(JSONUtil.serialize(map));
%>

<%!public static final String EXTENSION_XLS = "xls";// excel 2003及以下
	public static final String EXTENSION_XLSX = "xlsx";// excel 2007及以上

	public static String countFileSize(long fileSize) {
		String fileSizeString = "";
		try {
			DecimalFormat df = new DecimalFormat("#.00");
			long fileS = fileSize;
			if (fileS == 0) {
				fileSizeString = "0KB";
			} else if (fileS < 1024) {
				fileSizeString = df.format((double) fileS) + "B";
			} else if (fileS < 1048576) {
				fileSizeString = df.format((double) fileS / 1024) + "KB";
			} else if (fileS < 1073741824) {
				fileSizeString = df.format(((double) fileS / 1024 / 1024) - 0.01) + "MB";
			} else {
				fileSizeString = df.format((double) fileS / 1024 / 1024 / 1024) + "G";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileSizeString;
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
			//throw new FileNotFoundException("传入的文件不存在：" + filePath);
		}

		if (!(filePath.endsWith(EXTENSION_XLS) || filePath.endsWith(EXTENSION_XLSX))) {
			//throw new FileFormatException("传入的文件不是excel");
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
	public List<String> readExcel(int columnNo, String filePath) {
		List<String> array = new ArrayList<String>();
		// 检查
		this.preReadCheck(filePath);
		// 获取workbook对象
		Workbook workbook = null;
		try {
			InputStream is = new FileInputStream(filePath);
			workbook = new XSSFWorkbook(is); //只做 2003
			
			// 读文件 一个sheet一个sheet地读取
            for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
                Sheet sheet = workbook.getSheetAt(numSheet);
                if (sheet == null) {
                    continue;
                }
                System.out.println("=======================" + sheet.getSheetName() + "=========================");

                int firstRowIndex = sheet.getFirstRowNum();
                int lastRowIndex = sheet.getLastRowNum();

                // 读取首行 即,表头
                /* Row firstRow = sheet.getRow(firstRowIndex);
                for (int i = firstRow.getFirstCellNum(); i <= firstRow.getLastCellNum(); i++) {
                    Cell cell = firstRow.getCell(i);
                    String cellValue = this.getCellValue(cell, true);
                    //System.out.print(" " + cellValue + "\t");
                }
                System.out.println(""); */
                
               for (int rowIndex = firstRowIndex + 1; rowIndex <= lastRowIndex; rowIndex++) {
					Row currentRow = sheet.getRow(rowIndex);// 当前行
					for(int i=0;i<columnNo;i++){
						Cell currentCell = currentRow.getCell(i);// 当前单元格
						String currentCellValue = this.getCellValue(currentCell, true);// 当前单元格的值
						array.add(currentCellValue);
					}
				}
				return array; 

                // 读取数据行
                /* for (int rowIndex = firstRowIndex + 1; rowIndex <= lastRowIndex; rowIndex++) {
                    Row currentRow = sheet.getRow(rowIndex);// 当前行
                   // int firstColumnIndex = currentRow.getFirstCellNum(); // 首列
                    //int lastColumnIndex = currentRow.getLastCellNum();// 最后一列
                    for (int columnIndex = firstRowIndex; columnIndex <= 3; columnIndex++) {
                        Cell currentCell = currentRow.getCell(1);// 当前单元格
                        String currentCellValue = this.getCellValue(currentCell, true);// 当前单元格的值
                        System.out.print(currentCellValue);
                        
                        //String sql = "insert into t_uploadExcel(serid,name,main) values("+currentCellValue+")";
                        array.add(currentCellValue);
                    }
                } */
            }

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
		}
		return null;
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
	
	
	public boolean  getCompare() {
		boolean flag = false;
		//通过就成真
		String sql = "(select * from ENT_SELECT  minus select * from t_uploadExcel) union (select * from t_uploadExcel minus select * from ENT_SELECT)";
		
		
		
		return flag;
	}
	%>