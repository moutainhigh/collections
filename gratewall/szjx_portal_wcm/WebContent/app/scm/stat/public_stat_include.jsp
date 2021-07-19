<%@ include file="../include/register_check.jsp"%>
<%
	
	String path = request.getContextPath();
	String sServerName = request.getHeader("X-FORWARDED-HOST");
	if (sServerName == null || sServerName.length() < 1) {  
		sServerName = request.getServerName() + (request.getServerPort() == 80 ? "" : (":" + request.getServerPort()));  
	} else if (sServerName.contains(",")) {  
		sServerName = sServerName.substring(0, sServerName.indexOf(",")).trim();  
	}  
	String sBasePath = request.getScheme()+"://"+sServerName+path+"/";
	String sBaseHost = request.getScheme()+"://"+sServerName;
	String sBaseUrl = sBasePath+"app/scm/stat";
	if(false){
		System.out.println("sBasePath:" + sBasePath);
		System.out.println("sBaseUrl:" + sBaseUrl);
	}
	JSPRequestProcessor oProcessor = new JSPRequestProcessor(request,response);
	// SCMGroupId传入的值为-1时，表示获取所有分组
	int nSCMGroupId = currRequestHelper.getInt("SCMGroupId",-1);

	// 1 获取用户可以管理的所有分组。
	HashMap oUserIdParas = new HashMap();
	SCMGroups oGroups = null;
	if(SCMAuthServer.isAdminOfSCM(loginUser)){
		oProcessor.reset();
		oGroups = (SCMGroups) oProcessor.excute("wcm61_scmgroup", "query", oUserIdParas);
		for(int i=0; i<oGroups.size(); i++){
			SCMGroup oTempGroup = (SCMGroup)oGroups.getAt(i);
			if(oTempGroup == null){
				continue;
			}
		}
	}else{
		oProcessor.reset();
		oUserIdParas.put("UserId", String.valueOf(loginUser.getId()));
		oGroups = (SCMGroups) oProcessor.excute("wcm61_scmgroup", "getGroupsOfUser", oUserIdParas);
	}

	// 如果没有可以维护的分组，则跳转到主页面
	if(oGroups == null || oGroups.size()==0) {
	%>
	<div style="text-align: center;font-size:12px;padding-left:20px;padding-top:40px;"><font size="4" color="#AD251A">您在本系统还没有可以维护的分组！</font></div>
	<%
		return;
	}
	oProcessor.reset();

	// 2 获取统计的分组中所包含的帐号集合，以供统计数据过滤使用
	Accounts oAccounts = Accounts.createNewInstance(loginUser);
	if(nSCMGroupId != -1){
		HashMap oSCMGroupIdParams = new HashMap();
		oSCMGroupIdParams.put("SCMGroupId", String.valueOf(nSCMGroupId));
		oAccounts = (Accounts) oProcessor.excute("wcm61_scmaccount", "findAccountsOfGroup", oSCMGroupIdParams);
	}else{
		HashMap oSCMGroupIdParams = new HashMap();
		for(int i=0; i<oGroups.size(); i++){
			SCMGroup oTempGroup = (SCMGroup)oGroups.getAt(i);
			if(oTempGroup == null){
				continue;
			}
			oSCMGroupIdParams.put("SCMGroupId", String.valueOf(oTempGroup.getId()));
			Accounts oTempAccounts = (Accounts) oProcessor.excute("wcm61_scmaccount", "findAccountsOfGroup", oSCMGroupIdParams);
			oAccounts.addElements(oTempAccounts);
			oProcessor.reset();
			oSCMGroupIdParams.clear();
		}
	}

	// 3 构造数据
	CMyDateTime now = CMyDateTime.now();
	int nStatYear = currRequestHelper.getInt("StatYear", now.getYear());
	String sPlatform = currRequestHelper.getString("StatPlatform");
	if(CMyString.isEmpty(sPlatform)){
		sPlatform = "all";
	}
	
	// 4 设置IStatResultsFilter中要使用到的final类型的变量
	final boolean bStatAllPlatform = "all".equals(sPlatform) ? true : false;
	final String sFinalPlatform = sPlatform;
	final Accounts oFinalAccounts = oAccounts;

	// 5 调用服务获取用户第一次使用SCM的时间
	oProcessor.reset();
	HashMap oQueryFirstYear = new HashMap(); 
	Integer oFirstYear = (Integer) oProcessor.excute("wcm61_scmmicrocontent","queryFirstYear",oQueryFirstYear);
	int nFirstYear = oFirstYear.intValue();
%>