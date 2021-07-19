<%
String []pStatTimes = getDateTimes(nStatYear);
String[] pStatSQL = new String[] { "select sum(COMMENTCOUNT) DataCount, AccountId"
+ " from XWCMMICROCONTENTSTATDATA"
+ " where"
+ " MICROCONTENTCRTIME >=${StartTime}"
+ " and MICROCONTENTCRTIME <=${EndTime}"
+ " group by ACCOUNTID" };
String sStartDate = pStatTimes[0];
String sEndDate =  pStatTimes[1];
StatAnalysisTool tool = new StatAnalysisTool(pStatSQL);
IStatResults oStatResults = tool.stat(sStartDate, sEndDate,TimeRange.STEP_MONTH);

oStatResults = oStatResults.filter(new IStatResultsFilter() {
public boolean accept(String sKey, IStatResults statResults) throws WCMException {
	int nFilterAccountId = Integer.parseInt(sKey);
	// 获取当前记录对应的帐号，如果帐号为null或者已经被删除，则过滤掉
	Account oTempAccount = Account.findById(nFilterAccountId);
	if(oTempAccount==null) {
		return false;
	}
	if(oTempAccount.getStatus() !=1)
			return false;

	// 先判断帐号是否在统计的分组下，再判断平台
	String sTempPlatform = oTempAccount.getPlatform();
	// 先判断帐号是否在统计的分组下
	if(oFinalAccounts == null || oFinalAccounts.size() == 0 || (oFinalAccounts.indexOf(nFilterAccountId) < 0)){
		return false;
	}

	if(bStatAllPlatform){
		return true;
	}
	return sTempPlatform.equals(sFinalPlatform);
}
});
List mainObjList = oStatResults.list();
int nNum = 0;
if (mainObjList != null && (mainObjList.size() > 0)) {
	nNum = mainObjList.size();
}

StringBuffer buffer = new StringBuffer(2000);
// 列表数据
List oAccountList = new ArrayList();
Map oAccountDataMap = new HashMap();
for (int i = 0; i < nNum; i++) {
	String sKey = String.valueOf(mainObjList.get(i));
	List list = new ArrayList();
	// 统计总量时需要做特殊处理
	list = oStatResults.getResult(1, sKey);

	String sAccountDesc = getAccountDesc(Integer.parseInt(sKey));
	String sTempData =  getChartData(sAccountDesc, list.toString());
	if(!CMyString.isEmpty(sAccountDesc)){
		oAccountList.add(sAccountDesc);
		oAccountDataMap.put(sAccountDesc, list);
	}
	buffer.append("," +sTempData);
}
String sData = buffer.length() > 0 ? buffer.substring(1) : buffer.toString();
String sXLabels = oStatResults.getXlabels().toString();
String sTitle = null;
if(sPlatform.equals("all")){
	sTitle = nStatYear+ "年不同平台各帐号微博被评论数";
}else{
	String sPlatChineseName = PlatformFactory.getPlatform(sPlatform).getChineseName();
	sTitle = nStatYear+ "年"+sPlatChineseName+"平台各帐号微博被评论数";
}
%>