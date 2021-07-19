<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" errorPage="../../../include/error.jsp"%>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>

<%@include file="../../../include/public_server.jsp"%>
<%@include file="export_bonus_include.jsp"%>
<%@include file="../../include/static_common.jsp"%>
<%
BufferedOutputStream buffos = null;
String excelFile = null;
try{
	//1 初始化获取数据
	// TODO权限校验

	// 获得当前页面的参数
	String sUserName = currRequestHelper.getString("UserName");
	String sTotalBonus = currRequestHelper.getString("TotalBonus");
	CMyDateTime startTime = new CMyDateTime(),endTime=new CMyDateTime();
	//设置时间范围
	String[] arrTime = getDateTimeFromParame(currRequestHelper);
	String sStartTime = arrTime[0];
    String sEndTime = arrTime[1];
	
	startTime.setDateTimeWithString(sStartTime);
	endTime.setDateTimeWithString(sEndTime);

	// 获取文档标题的查询条件
	String sSelectItem = currRequestHelper.getString("SelectItem");
	String sSearchValue = currRequestHelper.getString("SearchValue");
	String sQuerySQL = "";
	if(!CMyString.isEmpty(sSearchValue)){
		sQuerySQL = " and DocTitle like '%" + CMyString.filterForSQL(sSearchValue)+ "%'";
	}
	// 构造统计的SQL语句
	String[] pStatSQL = new String[] {
	// 总的点击量
	"Select sum(HITSCOUNT) TotalCount,DocId from XWCMDOCUMENTHITSCOUNT "
			// 统计指定时间段的数据
			+ " Where HitsTime>=${StartTime}"
			+ " And HitsTime<=${EndTime}" + sQuerySQL + " Group by DocId" };

	StatBonusDataForUser state = new StatBonusDataForUser(pStatSQL);

	// 获取统计数据
    StatBonusResultForUser statResult = state.statHitsBonusByMonth(startTime, endTime, true);

	//after to do ...
	String[] saFieldName = new String[]{LocaleServer.getString("export_hits_bonus_stat.num","序号"),LocaleServer.getString("export_hits_bonus_stat.month","月份"), LocaleServer.getString("export_hits_bonus_stat.title","文档标题"), LocaleServer.getString("export_hits_bonus_stat.channel","所属栏目"), LocaleServer.getString("export_hits_bonus_stat.clicknum","点击量"), LocaleServer.getString("export_hits_bonus_stat.get","所获奖金(￥)")};
	//2 构造Excel基本文件信息
	FilesMan fileManager = FilesMan.getFilesMan();
	excelFile = fileManager.getNextFilePathName(FilesMan.FLAG_SYSTEMTEMP,"xls");	
	buffos = new BufferedOutputStream(new FileOutputStream(excelFile));
	WritableWorkbook excelBook = Workbook.createWorkbook(buffos);
	WritableSheet excelSheet = excelBook.createSheet(CMyString.format(LocaleServer.getString("export_hits_bonus_stat.info","【{0}】奖金统计表({0}—{1})"),new String[]{sUserName,startTime.getDateTimeAsString("yyyy年MM月"),endTime.getDateTimeAsString("yyyy年MM月")}), 0);
	 
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

		float fIssuedAmountBonus = 0f,fHitsBonus = 0f,fTotal = 0f;
		// 获取文档ID的List
		List docIds = statResult.getDocIds();
		if(docIds != null){
			int nCurrAmount = 0;
			int nDocId = 0;
			int nRowNo = 0;
			startTime.dateAdd(CMyDateTime.MONTH, -1);
			while (startTime.compareTo(endTime) < 0) {
				startTime.dateAdd(CMyDateTime.MONTH, 1);
				// 获取每月的点击量奖金统计数据
				%>
				<%// 根据文档Id获取该月该文档的点击量及所获奖金
				int nHitsCount=0;
				for (int i = 0; i < docIds.size(); i++) {
					int nCurrDocId = Integer.parseInt((String) docIds.get(i));
					
					// 获取文档
					Document document = Document.findById(nCurrDocId);
					if(document == null)
						continue;
					Channel channel = document.getChannel();
					ChnlDoc chnlDoc = ChnlDoc.findByDocument(document);
					// 获取文档点击量
					nHitsCount = statResult.getHitsCountOfMonth(sUserName, nCurrDocId, startTime);
					if(nHitsCount == 0)
						continue;
					nRowNo++;

					iExcelCol = 0;
					iExcelRow++;
					//往Excel中插入一行数据的各单元格
					String sFieldValue = "";
					numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, nRowNo);
					excelSheet.addCell(numberCell);

					contentCell = new Label(iExcelCol++, iExcelRow, startTime.toString("yyyy年MM月"));
					excelSheet.addCell(contentCell);

					contentCell = new Label(iExcelCol++, iExcelRow, document.getTitle());
					excelSheet.addCell(contentCell);

					contentCell = new Label(iExcelCol++, iExcelRow, channel.getName());
					excelSheet.addCell(contentCell);

					numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, nHitsCount);
					excelSheet.addCell(numberCell);

					numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, statResult.getHitsBonusOfMonth(sUserName, nCurrDocId, startTime), wcf);
					excelSheet.addCell(numberCell);
				}
			}
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