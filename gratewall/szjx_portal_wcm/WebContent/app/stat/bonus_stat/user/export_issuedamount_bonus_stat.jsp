<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" errorPage="../../../include/error.jsp"%>

<%@include file="../../../include/public_server.jsp"%>
<%@include file="export_bonus_include.jsp"%>
<%@include file="../../include/static_common.jsp"%>
<%
BufferedOutputStream buffos = null;
String excelFile = null;
try{
	//1 初始化获取数据
	// 构造用户或组织名称的查询条件
	String sUserName = currRequestHelper.getString("UserName");
	CMyDateTime startTime = new CMyDateTime(),endTime=new CMyDateTime();
	//设置时间范围
	String[] arrTime = getDateTimeFromParame(currRequestHelper);
	String sStartTime = arrTime[0];
    String sEndTime = arrTime[1];
	
	startTime.setDateTimeWithString(sStartTime);
	endTime.setDateTimeWithString(sEndTime);

	// 记录总发稿量
	int nIssuedAmount = 0;
	float fTotalBonus = 0f;

	// 构造统计的SQL语句
	String[] pStatSQL = new String[] {
	// 总的发稿量
	"Select count(*) DataCount, CrUser from WCMChnlDoc "
			// 统计指定时间段的数据
			+ " Where CrTime>=${StartTime}" + " And CrTime<=${EndTime}"
			+ " and DocStatus=10 and CHNLID>0" + " Group by CrUser"};

	StatBonusDataForUser state = new StatBonusDataForUser(pStatSQL);

	// 获取统计数据
    StatBonusResultForUser statResult = state.statIssuedAmountBonusByMonth(startTime, endTime, true);

	//after to do ...
	String[] saFieldName = new String[]{LocaleServer.getString("bonus_stat.month","月份"),LocaleServer.getString("bonus_stat.sent","已发文档数量"),LocaleServer.getString("bonus_stat.get","所获奖金(￥)")};
	//2 构造Excel基本文件信息
	FilesMan fileManager = FilesMan.getFilesMan();
	excelFile = fileManager.getNextFilePathName(FilesMan.FLAG_SYSTEMTEMP,"xls");	
	buffos = new BufferedOutputStream(new FileOutputStream(excelFile));
	WritableWorkbook excelBook = Workbook.createWorkbook(buffos);
	WritableSheet excelSheet = excelBook.createSheet(CMyString.format(LocaleServer.getString("issuedamount_bonus_stat.info","用户【{0}】的奖金统计表(从时间{0}—至{1})"),new String[]{sUserName,startTime.getDateTimeAsString("yyyy年MM月"),endTime.getDateTimeAsString("yyyy年MM月")}), 0);
	 
	//3 设置表头
	int iExcelCol = 0;
	int iExcelRow = 0;
	Label contentCell = null;
	jxl.write.Number numberCell = null; 
	WritableFont wf = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false);
	WritableCellFormat wcfF = new WritableCellFormat(wf);
	for (int column = 0; column < saFieldName.length; column++) {
		contentCell = new Label(iExcelCol++,iExcelRow,saFieldName[column], wcfF);
		excelSheet.addCell(contentCell); 
	}

	//4 构造各个单元格

	//在每个循环中做的事情
	if (statResult != null) {
		
		/* 
		* 定义对于显示金额的公共格式 
		* jxl会自动实现四舍五入 
		* 例如 2.456会被格式化为2.46,2.454会被格式化为2.45 
		* */   
		jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#.##");
		jxl.write.WritableCellFormat wcf = new jxl.write.WritableCellFormat(nf);

		int nCurrAmount = 0;
		float fCurrBonus = 0f;
		startTime.dateAdd(CMyDateTime.MONTH, -1);
		while (startTime.compareTo(endTime) < 0) {
			
			startTime.dateAdd(CMyDateTime.MONTH, 1);
			// 获取奖金数据
			nCurrAmount = statResult.getIssuedAmountOfMonth(sUserName, startTime);
			fCurrBonus = statResult.getIssuesAmountBonusOfMonth(sUserName, startTime);
			nIssuedAmount += nCurrAmount;
			fTotalBonus += fCurrBonus;
			if(nCurrAmount == 0)
				continue;

			iExcelCol = 0;
			iExcelRow++;
			//往Excel中插入一行数据的各单元格
			String sFieldValue = "";

			contentCell = new Label(iExcelCol++, iExcelRow, startTime.toString("yyyy年MM月"));
			excelSheet.addCell(contentCell);

			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, nCurrAmount);
			excelSheet.addCell(numberCell);

			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, fCurrBonus, wcf);
			excelSheet.addCell(numberCell);
		}
		// 插入合计的一行
		if(nIssuedAmount >0){
			iExcelCol = 0;
			iExcelRow++;
			contentCell = new Label(iExcelCol++, iExcelRow, LocaleServer.getString("issuedamount_bonus_stat.total","合计"));
			excelSheet.addCell(contentCell);

			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, nIssuedAmount);
			excelSheet.addCell(numberCell);

			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, fTotalBonus, wcf);
			excelSheet.addCell(numberCell);
		}
	}

	
	
	//5.资源释放
	excelBook.write();
	excelBook.close();
	if(buffos!=null){
		buffos.close();
	}
	
	//6.返回值的构造
	String sXslFileName = CMyFile.extractFileName(excelFile);
	String result = "";
	result = "<excelfile>"+ sXslFileName + "</excelfile>";
	out.clear();
	out.print(result);
	return;
}finally{
	if(buffos!=null){
		buffos.close();
	}
}
%>