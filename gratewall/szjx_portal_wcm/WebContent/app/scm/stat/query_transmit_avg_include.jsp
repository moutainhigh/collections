<%
String []pStatTimes = getDateTimes(nStatYear);
String[] pStatSQL = new String[] { "select sum(RETWEETCOUNT) DataCount, AccountId"
+ " from XWCMMICROCONTENTSTATDATA"
+ " where"
+ " MICROCONTENTCRTIME >=${StartTime}"
+ " and MICROCONTENTCRTIME <=${EndTime}"
+ " group by ACCOUNTID" ,
"select COUNT(*) MicroContentCount, AccountId"
+ " from XWCMMICROCONTENTSTATDATA"
+ " where"
+ " MICROCONTENTCRTIME >=${StartTime}"
+ " and MICROCONTENTCRTIME <=${EndTime}"
+ " group by ACCOUNTID" };
String sStartDate = pStatTimes[0];
String sEndDate =  pStatTimes[1];
StatAnalysisTool tool = new StatAnalysisTool(pStatSQL);
IStatResults oStatResults = tool.stat(sStartDate, sEndDate, TimeRange.STEP_MONTH);

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
	List<Integer> list = new ArrayList<Integer>();
	List<Integer> microContentCountList = new ArrayList<Integer>();
	List<Float> oShowList = new ArrayList<Float>();

	// 统计总量时需要做特殊处理
	//获取帐号为sKey的评论总数list
	list = oStatResults.getResult(1, sKey);

	//获取帐号为sKey的微博总数list
	microContentCountList = oStatResults.getResult(2, sKey);
	for(int j=0;j<list.size();j++){
		float nOneDate = 0;
		int nCommentCount = list.get(j).intValue();
		int nMicrocontentCount = microContentCountList.get(j).intValue();
		if(nCommentCount > 0 && nMicrocontentCount > 0){
			nOneDate = (float)nCommentCount/(float)nMicrocontentCount*100;
		}
		oShowList.add(j,new Float(nOneDate));
	}
	String sAccountDesc = getAccountDesc(Integer.parseInt(sKey));
	String sTempData =  getChartData(sAccountDesc, oShowList.toString());
	if(!CMyString.isEmpty(sAccountDesc)){
		oAccountList.add(sAccountDesc);
		oAccountDataMap.put(sAccountDesc, oShowList);
	}
	buffer.append("," +sTempData);
}
String sData = buffer.length() > 0 ? buffer.substring(1) : buffer.toString();
String sXLabels = oStatResults.getXlabels().toString();
String sTitle = null;
if(sPlatform.equals("all")){
	sTitle = nStatYear+ "年不同平台各帐号微博转发率";
}else{
	String sPlatChineseName = PlatformFactory.getPlatform(sPlatform).getChineseName();
	sTitle = nStatYear+ "年"+sPlatChineseName+"平台各帐号微博转发率";
}
%>