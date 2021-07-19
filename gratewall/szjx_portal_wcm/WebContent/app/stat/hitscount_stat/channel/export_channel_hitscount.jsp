<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" errorPage="../../../include/error.jsp"%>
<%@ page import="com.trs.cms.auth.persistent.Group" %>
<%@ page import="com.trs.cms.auth.persistent.Groups" %>
<%@ page import="com.trs.cms.auth.persistent.User" %>
<%@ page import="com.trs.cms.auth.persistent.Users" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>

<%@include file="../export_include.jsp"%>
<%@include file="channel_hitscount_data_include.jsp"%>
<%
String sGroupStatByChannel = ConfigServer.getServer().getSysConfigValue("GROUP_STAT_BY_CHANNEL", "false");
boolean bGroupStatByChannel = "true".equalsIgnoreCase(sGroupStatByChannel);
BufferedOutputStream buffos = null;
String excelFile = null;
try{
	//1 构造表头字段名
	String[] saFieldName = null;
	if(nHitsTimeItem > 0){
		if(bGroupStatByChannel)
			saFieldName = new String[]{LocaleServer.getString("export_channel_hitscount.num","序号"),LocaleServer.getString("export_channel_hitscount.chnlname","栏目名称"), LocaleServer.getString("export_channel_hitscount.clickMount","点击量"),LocaleServer.getString("export_channel_hitscount.increaseRate","增长指数"),LocaleServer.getString("export_channel_hitscount.departmentName","部门名称"),LocaleServer.getString("export_channel_hitscount.site","所属站点")};
		else{
			saFieldName = new String[]{LocaleServer.getString("export_channel_hitscount.num","序号"),LocaleServer.getString("export_channel_hitscount.chnlname","栏目名称"),LocaleServer.getString("export_channel_hitscount.clickMount","点击量"), LocaleServer.getString("export_channel_hitscount.increaseRate","增长指数"),LocaleServer.getString("export_channel_hitscount.site","所属站点")};
		}
	}else{
		if(bGroupStatByChannel){
			saFieldName = new String[]{LocaleServer.getString("export_channel_hitscount.chnlnum","序号"),LocaleServer.getString("export_channel_hitscount.chnlname","栏目名称"), LocaleServer.getString("export_channel_hitscount.clickMount","点击量"), LocaleServer.getString("export_channel_hitscount.departmentName","部门名称"), LocaleServer.getString("export_channel_hitscount.site","所属站点")};
		}else{
			saFieldName = new String[]{LocaleServer.getString("export_channel_hitscount.chnlnum","序号"),LocaleServer.getString("export_channel_hitscount.chnlname","栏目名称"), LocaleServer.getString("export_channel_hitscount.clickMount","点击量"), LocaleServer.getString("export_channel_hitscount.site","所属站点")};
		}
	}

	//2 构造Excel基本文件信息
	FilesMan fileManager = FilesMan.getFilesMan();
	excelFile = fileManager.getNextFilePathName(FilesMan.FLAG_SYSTEMTEMP,"xls");	
	buffos = new BufferedOutputStream(new FileOutputStream(excelFile));
	WritableWorkbook excelBook = Workbook.createWorkbook(buffos);
	WritableSheet excelSheet = excelBook.createSheet(LocaleServer.getString("export_channel_hitscount.statistic.chart","栏目点击量统计列表"), 0);
	 
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

	//循环
	if (nNum > 0) {  
		for (int i = 0; i < nNum; i++) {
			if(arHostIds.size() < i + 1) 
				break;
				
			int nChannelId = Integer.parseInt((String) arHostIds.get(i));
			Channel channel = Channel.findById(nChannelId);
			if(channel == null)
				continue;
			String sChannelName = CMyString.transDisplay(channel.getDesc());
			String sSiteName = "";
			WebSite oSite = channel.getSite();
			if(oSite != null)
				sSiteName = oSite.getDesc();
			int nResult = result.getResult(1, String.valueOf(nChannelId));
			int nCompare = 0;
			String sCompareResult = "";
			if(nHitsTimeItem > 0){
				int nResultForCompare = resultForCompare.getResult(1, String.valueOf(nChannelId));
				if(nResultForCompare == 0)
					sCompareResult = nResult + "";
				else{
					float fComparedCount = (float)nResultForCompare;
					nCompare = (int)(((nResult - fComparedCount) / fComparedCount) * 100);
					if(nCompare > 0){
						sCompareResult = "+" + nCompare + "%";
					}else{
						sCompareResult = nCompare + "%";
					}
				}

			}
			
			IChnlDeptMgr mgr = (IChnlDeptMgr) DreamFactory.createObjectById("IChnlDeptMgr");
			String sGroupName = CMyString.transDisplay(CMyString.showNull(mgr.getDepNamesByChannel(channel, channel.getCrUser())));
			
			iExcelCol = 0;
			iExcelRow++;
			//往Excel中插入一行数据的各单元格
			String sFieldValue = "";
			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, i+1);
			excelSheet.addCell(numberCell);
			
			contentCell = new Label(iExcelCol++, iExcelRow, sChannelName);
			excelSheet.addCell(contentCell);

			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, nResult);
			excelSheet.addCell(numberCell);
			
			if(nHitsTimeItem > 0){
			  contentCell = new Label(iExcelCol++, iExcelRow, sCompareResult);
			  excelSheet.addCell(contentCell);
			}
			if(bGroupStatByChannel){
				contentCell = new Label(iExcelCol++, iExcelRow, sGroupName);
				excelSheet.addCell(contentCell);
			}

			contentCell = new Label(iExcelCol++, iExcelRow, sSiteName);
			excelSheet.addCell(contentCell);
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
	String sResult = "";
	sResult = "<excelfile>"+ sXslFileName + "</excelfile>";
	out.clear();
	out.print(sResult);
	return;
}finally{
	if(buffos!=null){
		buffos.close();
	}
}
%>