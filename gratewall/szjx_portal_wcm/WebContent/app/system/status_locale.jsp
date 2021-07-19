<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.components.wcm.resource.Statuses" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="java.util.*" %>
<%@ page import="com.trs.presentation.locale.LocaleServer"%>

<%!
	private Map statusMapping = new HashMap();
	{
		statusMapping.put("新稿", "status.New");
		statusMapping.put("已编", "status.Edited");
		statusMapping.put("返工", "status.Redo");
		statusMapping.put("已发", "status.Published");
		statusMapping.put("已否", "status.Reject");
		statusMapping.put("已签", "status.Approved");
		statusMapping.put("正审", "status.Checking");
		statusMapping.put("未受理", "status.To.be.Accepted");
		statusMapping.put("已受理", "status.Accepted");
		statusMapping.put("草稿", "status.Draft");
		statusMapping.put("再审", "status.VerifyAgain");
		statusMapping.put("撤销审批中", "status.VerifyCancel");


		statusMapping.put("新稿状态", "status.New.STATUS");
		statusMapping.put("已编状态", "status.Edited.STATUS");
		statusMapping.put("返工状态", "status.Redo.STATUS");
		statusMapping.put("已发状态", "status.Published.STATUS");
		statusMapping.put("已否状态", "status.Reject.STATUS");
		statusMapping.put("已签状态", "status.Approved.STATUS");
		statusMapping.put("正审状态", "status.Checking.STATUS");
		statusMapping.put("再审状态", "status.VerifyAgain.STATUS");
		statusMapping.put("撤销审批中状态", "status.VerifyCancel.STATUSS");
		
		statusMapping.put("稿件最新采集但尚未编辑", "status.New.Desc");
		statusMapping.put("稿件已经编辑", "status.Edited.Desc");
		statusMapping.put("需要进一步修改", "status.Redo.Desc");
		statusMapping.put("文档已经发布", "status.Published.Desc");
		statusMapping.put("不合要求,被否决", "status.Reject.Desc");
		statusMapping.put("待发表", "status.Approved.Desc");
		statusMapping.put("已通过了正审,可以发表", "status.Checking.Desc");
		statusMapping.put("尚未指派部门", "status.To.be.Accepted.Desc");
		statusMapping.put("已经交给相关部门处理", "status.Accepted.Desc");
		statusMapping.put("未正式提交的原始稿件", "status.Draft.Desc");
	
	}

	private String getStatusLocale(String sStatus){
		String sKey = (String) statusMapping.get(sStatus);
		if(CMyString.isEmpty(sKey)){
			return sStatus;
		}
		return LocaleServer.getString(sKey, sStatus);
	}
%>