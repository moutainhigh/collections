<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" errorPage="../../../include/error.jsp"%>

<%@ page import="com.trs.cms.auth.persistent.Group" %>
<%@ page import="com.trs.cms.auth.persistent.Groups" %>
<%@ page import="com.trs.cms.auth.persistent.User" %>
<%@ page import="com.trs.cms.auth.persistent.Users" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>

<%@include file="../../../include/public_server.jsp"%>
<%@include file="../../include/static_common.jsp"%>
<%@include file="export_bonus_include.jsp"%>
<%
BufferedOutputStream buffos = null;
String excelFile = null;
try{
	//1 初始化获取数据
	// 构造用户或组织名称的查询条件
	String sSelectItem = currRequestHelper.getString("SelectItem");
	String sSearchValue = currRequestHelper.getString("SearchValue");

	String sQuerySQL = "";
	if(!CMyString.isEmpty(sSearchValue)){
		if(sSelectItem.equalsIgnoreCase("userName")){
			sQuerySQL = " and CrUser like '%" + CMyString.filterForSQL(sSearchValue) + "%'";
		}else{
			// 获取组织下的发稿人
			String sUserNames = getUserNames(sSearchValue, loginUser);
			// 如果符合检索条件的用户为空，则附件一个逻辑值为真的条件
			if(CMyString.isEmpty(sUserNames)){
				sQuerySQL = " and 1=0";
			}else{
				sQuerySQL = " and CrUser in (" 
					+ getUserNames(sSearchValue, loginUser)
					+")";
			}
		}
	}

	// 获取当前页面用户的统计数据
	// 构造统计的SQL语句
	String[] pStatSQL = new String[] {
	// 总的发稿量
	"Select count(*) DataCount, CrUser from WCMChnlDoc "
			// 统计指定时间段的数据
			+ " Where CrTime>=${StartTime}" + " And CrTime<=${EndTime}"
			+ " and DocStatus=10 and CHNLID>0" + sQuerySQL + " Group by CrUser",
	"Select sum(HITSCOUNT) TotalCount,DocId from XWCMDOCUMENTHITSCOUNT "
			// 统计指定时间段的数据
			+ " Where HitsTime>=${StartTime}"
			+ " And HitsTime<=${EndTime}" + sQuerySQL + " Group by DocId"};

	StatBonusDataForUser state = new StatBonusDataForUser(pStatSQL);
	
	CMyDateTime dtQueryStart = new CMyDateTime(),dtQueryEnd=new CMyDateTime();
	//设置时间范围
	String[] arrTime = getDateTimeFromParame(currRequestHelper);
	String sStartTime = arrTime[0];
    String sEndTime = arrTime[1];
	
	dtQueryStart.setDateTimeWithString(sStartTime);
	dtQueryEnd.setDateTimeWithString(sEndTime);

	// 获取统计数据
	StatBonusResultForUser statResult = state.stat(dtQueryStart, dtQueryEnd);
	List arUserNames = null;
	if (statResult != null) {
		arUserNames = statResult.getUserNames();
	}

	//after to do ...
	String[] saFieldName = new String[]{LocaleServer.getString("export_bonus_stat.num","序号"),LocaleServer.getString("export_bonus_stat.user","发稿人"),LocaleServer.getString("export_bonus_stat.partment", "所属部门"), LocaleServer.getString("export_bonus_stat.send","已发文档所获奖金(￥)"),LocaleServer.getString("export_bonus_stat.get","点击量所获奖金(￥)"),LocaleServer.getString("export_bonus_stat.totle","合计(￥)")};
	//2 构造Excel基本文件信息
	FilesMan fileManager = FilesMan.getFilesMan();
	excelFile = fileManager.getNextFilePathName(FilesMan.FLAG_SYSTEMTEMP,"xls");	
	buffos = new BufferedOutputStream(new FileOutputStream(excelFile));
	WritableWorkbook excelBook = Workbook.createWorkbook(buffos);
	WritableSheet excelSheet = excelBook.createSheet(CMyString.format(LocaleServer.getString("export_bonus_stat.info","用户奖励情况统计表({0}——{1})"),new String[]{dtQueryStart.getDateTimeAsString("yyyy年MM月"),dtQueryEnd.getDateTimeAsString("yyyy年MM月")}), 0);
	 
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
		for (int i = 0; i < arUserNames.size(); i++) {
			String sName = (String) arUserNames.get(i);
			User currUser = User.findByName(sName);
			int ncurrUserId = currUser.getId();

			// 获取用户所属组织
			Groups groups = currUser.getGroups();
			String sGroupName = LocaleServer.getString("export_bonus_stat.null","无");
			Group temp = null;
			if(groups.size() > 0){
				sGroupName = "";
				for(int j=0,nsize= groups.size(); j< nsize; j++){
					temp = (Group) groups.getAt(j);
					if(temp == null) continue;
					if(j != nsize-1){
						sGroupName += temp.getName() + " , ";
					}else{
						sGroupName += temp.getName();
					}
				}
			}
			// 获取奖金数据
			fIssuedAmountBonus = statResult.getBonus(sName, BonusRule.ISSUEDAMOUNT_BONUS);
			fHitsBonus = statResult.getBonus(sName, BonusRule.HITS_BONUS);
			fTotal = fIssuedAmountBonus + fHitsBonus;
			iExcelCol = 0;
			iExcelRow++;
			//往Excel中插入一行数据的各单元格
			String sFieldValue = "";
			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, i+1);
			excelSheet.addCell(numberCell);

			contentCell = new Label(iExcelCol++, iExcelRow, sName);
			excelSheet.addCell(contentCell);

			contentCell = new Label(iExcelCol++, iExcelRow, sGroupName);
			excelSheet.addCell(contentCell);

			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, fIssuedAmountBonus, wcf);
			excelSheet.addCell(numberCell);

			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, fHitsBonus, wcf);
			excelSheet.addCell(numberCell);

			numberCell = new jxl.write.Number(iExcelCol++, iExcelRow, fTotal, wcf);
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
%><%!
	// 获取根据组织名称查找的组织中的所有用户的发稿人
	public String getUserNames(String _sSearchValue, User _loginUser) throws Exception{
		// 获取所有组织
		String sWhere = "GName like '%"+ CMyString.filterForSQL(_sSearchValue)+"%'";
		WCMFilter filter = new WCMFilter("", sWhere, "");
		Groups groups = Groups.openWCMObjs(_loginUser, filter);

		// 获取这些组织中的所有用户
		Users users = Users.createNewInstance(_loginUser);
		if(groups == null){
			return "";
		}

		for(int i= 0; i< groups.size(); i++){
			Group currGroup = (Group) groups.getAt(i);
			if(currGroup == null)
				continue;
			users.addElements(currGroup.getUsers(_loginUser));
		}
		
		// 得到发稿人
		String sUserNames = "";
		for(int i= 0; i < users.size(); i++){
			User currUser = (User)users.getAt(i);
			if(currUser == null)
				continue;
			if(CMyString.isEmpty(sUserNames)){
				sUserNames = "'"+currUser.getName()+"'";
			}else{
				sUserNames += ",'"+currUser.getName()+"'";
			}
		}
		return sUserNames;
	}
%>