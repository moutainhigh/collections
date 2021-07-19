<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.cms.process.definition.Flow" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="com.trs.scm.persistent.SCMGroup" %>

<%!
		
	public boolean hasWorkFlow(int _nSCMGroupId, JSPRequestProcessor oProcessor) throws WCMException{
		if(_nSCMGroupId <= 0){
			throw new WCMException("传入的SCMGroupId不合法！SCMGroupID=[" + _nSCMGroupId + "]");
		}
		SCMGroup oCurrGroup = SCMGroup.findById(_nSCMGroupId);
		if(oCurrGroup == null){
			throw new WCMException("未找到您要查找的分组!SCMGroupID=[" + _nSCMGroupId + "]");
		}
		//获得分组设置的工作流ID
		int nWorkFlowId = oCurrGroup.getWorkFlow();
		HashMap oHashMap = new HashMap();
		oHashMap.put("GroupID",String.valueOf(_nSCMGroupId));

		
		//调用服务，获得分组定制的工作流
		oProcessor.reset();
		Flow oSetedFlow = null;
		try{
			oSetedFlow = (Flow)oProcessor.excute("wcm61_scmworkflow","queryWorkFlowForGroup", oHashMap);
		}catch(Exception e){
			throw new WCMException("非常抱歉，当前账号的审核工作流错误，管理员修正审核配置后，您方可提交微博审核！");
		}
		if(oSetedFlow != null){
			return true;
		}
		return false;
	}
%>