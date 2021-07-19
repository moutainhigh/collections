<%--
/** Title:          stat_list.jsp
 *  Description:
 *      统计列表页面
 *  Copyright:      www.trs.com.cn
 *  Company:        TRS Info. Ltd.
 *  Author:         CH
 *  Created:        2005-04-26 13:50:28
 *  Vesion:         1.0
 *  Last EditTime:  2005-04-26 / 2005-04-26
 *  Update Logs:
 *      CH@2005-04-26 产生此文件
 *
 *  Parameters:
 *      see stat_list.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.stat.StatFieldMap" %>
<%@ page import="com.trs.components.wcm.stat.StatFieldMaps" %>
<%@ page import="com.trs.components.wcm.stat.StatView" %>
<%@ page import="com.trs.components.wcm.stat.chart.BarChartProducer" %>
<%@ page import="com.trs.components.wcm.stat.chart.PieChartProducer" %>
<%@ page import="com.trs.infra.persistent.CMyResultSet" %>
<%@ page import="com.trs.infra.persistent.CMyResultSets" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.service.impl.StatService" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.infra.util.CMyFile" %>
<!------- WCM IMPORTS END ------------>
<%@ page import="java.util.StringTokenizer " %>
<%@ page import="jxl.write.WritableWorkbook" %>
<%@ page import="jxl.write.WritableSheet" %>
<%@ page import="jxl.write.Label" %>
<%@ page import="jxl.Workbook" %>
<%@ page import="java.io.BufferedOutputStream" %>
<%@ page import="java.io.FileOutputStream" %>


<!--- 页面状态设定,登录校验,参数获取,都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%!
private String getWhereSql(String sSearchKey, String sSearchValue) {
    StringTokenizer stKeys = new StringTokenizer(sSearchKey, ",");
    String sKey, sWhere = "";
    boolean bFirst = true;
    while (stKeys.hasMoreTokens()) {
        sKey = stKeys.nextToken();
        if (bFirst) {
            sWhere = sKey + " like '%" + CMyString.filterForSQL(sSearchValue) + "%'";
            bFirst = sWhere.length() == 0;
        } else {
            sWhere += " or " + sKey + " like '%" + CMyString.filterForSQL(sSearchValue) + "%'";
        }
    }
    return sWhere;
}
%>

<%
//4.初始化(获取数据)
    int nStatViewId = currRequestHelper.getInt("StatViewId", 0);
    StatView currStatView = StatView.findById(nStatViewId);
    if(currStatView == null) {
        throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("statresult_export_toexcel.view.not.found","没有找到指定ID为[{0}]的统计视图!"),new int[]{nStatViewId}));
    }

    String sViewDesc = currStatView.getViewDesc();

    int nTimePeriod = currRequestHelper.getInt("TimePeriod", 0);
    String sDtStart = currRequestHelper.getString("StartDate");
    String sDtEnd = currRequestHelper.getString("EndDate");
    
    CMyDateTime dtQueryStart = null;
    CMyDateTime dtQueryEnd = null;
    if(!CMyString.isEmpty(sDtStart)){
        dtQueryStart = new CMyDateTime();
        dtQueryStart.setDateTimeWithString(sDtStart,CMyDateTime.DEF_DATETIME_FORMAT_PRG);
    }
    
    if(!CMyString.isEmpty(sDtEnd)){
        dtQueryEnd = new CMyDateTime();
        dtQueryEnd.setDateTimeWithString(sDtEnd,CMyDateTime.DEF_DATETIME_FORMAT_PRG);
    }
    
    //检索字段
    String strSearchKey = CMyString.showNull(currRequestHelper.getSearchKey());
    //检索值
    String strSearchValue = CMyString.showNull(currRequestHelper.getSearchValue());
    //排序字段
    String sOrderField  = CMyString.showNull(currRequestHelper.getOrderField());
    //排序类型
    String sOrderType   = CMyString.showNull(currRequestHelper.getOrderType());

//5.权限校验
    if (!loginUser.isAdministrator() && !AuthServer.hasStatisticRight(loginUser)){
        throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, LocaleServer.getString("statresult_export_toexcel.data.statistic.noRight","您没有权限查看数据统计信息列表!"));
    }

    String strWhere = getWhereSql(strSearchKey, strSearchValue);

    //排序语句
    String strOrder = currRequestHelper.getOrderSQL();

    StatService currStatService = (StatService) DreamFactory.createObjectById("IStatService");
    StatFieldMaps currStatFieldMaps = currStatService.getStatFieldsByView(nStatViewId);
    StatFieldMaps statFieldMaps = currStatService.getStatFieldsByView(nStatViewId);
    CMyResultSets currResultSets = new CMyResultSets();
    boolean bTemplateView = (currStatView.getViewType() == StatView.VIEW_TYPE_TEMPLATE);
    
    if (bTemplateView){
        currResultSets = currStatService.getStatResult(nStatViewId, dtQueryStart, dtQueryEnd, strWhere, strOrder);
    } else if  (currStatView.getViewType() == StatView.VIEW_TYPE_QUERY || currStatView.getViewType() == StatView.VIEW_TYPE_FIELD){
        currResultSets = currStatService.getCurrentStatResult(nStatViewId, dtQueryStart, dtQueryEnd, strWhere);
    }

    if(dtQueryStart == null){
        dtQueryStart = dtQueryEnd = CMyDateTime.now();
    }
    
    int arraySize = currStatFieldMaps.size();
    String[] arFieldNames = new String[arraySize];
    String[] arFieldDescs = new String[arraySize];
    String[] arFieldTypes = new String[arraySize];
    int[] arFieldPoses = new int[arraySize];
    StatFieldMap currStatFieldMap = null;
    for(int j=0; j<arraySize; j++) {
        currStatFieldMap = (StatFieldMap)currStatFieldMaps.getAt(j);
        if(currStatFieldMap == null) continue;
        arFieldNames[j] = currStatFieldMap.getFieldName();
        arFieldDescs[j] = currStatFieldMap.getFieldDesc();
        arFieldTypes[j] = currStatFieldMap.getFieldType();
        arFieldPoses[j] = currStatFieldMap.getFieldPos()-1;
    }

    CPager currPager = new CPager(currRequestHelper.getInt("PageItemCount", 20));
    currPager.setItemCount( currResultSets.size() );    
    currPager.setCurrentPageIndex( currRequestHelper.getInt("PageIndex", 1) );

    int nFieldStart = 1;
    if (currStatView.getViewType() == StatView.VIEW_TYPE_FIELD){
        nFieldStart = 0;
    }   
    
    //Excel
    FilesMan fileManager = FilesMan.getFilesMan();
    String excelFile = fileManager.getNextFilePathName(FilesMan.FLAG_SYSTEMTEMP,"xls"); 
    BufferedOutputStream buffos = new BufferedOutputStream(new FileOutputStream(excelFile));
    WritableWorkbook excelBook = Workbook.createWorkbook(buffos);
    WritableSheet excelSheet = excelBook.createSheet(sViewDesc, 0);


    //表头
    int nExcelRow = 0;
    int nExcelCol = 0;
    String sCellContent = null;
    for (int j=nFieldStart; j<arraySize; j++) {
        sCellContent = arFieldDescs[j];
        Label lableCell = new Label(nExcelCol++,nExcelRow,sCellContent);
        excelSheet.addCell(lableCell);
    }

    //内容
    CMyResultSet currResultSet = null;
    nExcelRow = 1;  
    for(int i=currPager.getFirstItemIndex(); i<=currPager.getLastItemIndex(); i++){//begin for
        try{
            currResultSet = (CMyResultSet)currResultSets.getAt(i-1);
        } catch(Exception ex){
            throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,CMyString.format(LocaleServer.getString("statresult_export_toexcel.jsp.fail2get_data","获取第[{0}]个统计数据失败!"),new int[]{i}), ex);
        }
        if(currResultSet == null || !currResultSet.isValidInstance()){
            throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,CMyString.format(LocaleServer.getString("statresult_export_toexcel.jsp.thedata","没有找到第[{0}]个统计数据!"),new int[]{i}));
        }
        
        try{
            nExcelCol = 0;
            for (int j=nFieldStart; j<arraySize; j++) {
                sCellContent = currResultSet.getPropertyAsString(arFieldNames[j]);
                jxl.write.WritableCell contentCell = null;
                try{
                    long lContent = Long.parseLong(sCellContent);
                    contentCell = new jxl.write.Number(nExcelCol++,nExcelRow,lContent);
                }catch(Exception e){
                    contentCell = new Label(nExcelCol++,nExcelRow,sCellContent);
                }
                
                excelSheet.addCell(contentCell);
            }

            nExcelRow++;//下一行
        } catch(Exception ex){
            throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID, CMyString.format(LocaleServer.getString("statresult_export_toexcel.jsp.thedata_property","获取第[{0}]个统计数据的属性失败!"),new int[]{i}), ex);
        }
    }

    try{
        excelBook.write();
        excelBook.close();
    }finally{
        buffos.close();
    }

    String result = "<excelfile>"+CMyFile.extractFileName(excelFile)+"</excelfile>";
    out.clear();
    
    out.print(result);
%>