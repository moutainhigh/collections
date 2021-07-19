<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="../include/error_scm.jsp"%>
<%@ include file="../../include/public_server.jsp"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.trs.infra.util.CMyException"%>
<%@ page import="com.trs.scm.persistent.Account"%>
<%@ page import="com.trs.scm.persistent.Accounts"%>
<%@ page import="com.trs.scm.persistent.SCMGroup"%>
<%@ page import="com.trs.scm.persistent.SCMGroups"%>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor"%>

<%	
	//判断分组里面是否还有帐号
	String sSCMGroupIds = currRequestHelper.getString("SCMGroupIds");	
	String[] aSCMGroupId = sSCMGroupIds.split(",");
	
	JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
	String sServiceId = "wcm61_scmgroup",sMethodName="isHasAccounts";
	HashMap parameters = new HashMap();
	
	String sSCMGroupNames = "";
	try{
		for(int i = 0;i < aSCMGroupId.length;i++){
			String sSCMGroupId = aSCMGroupId[i];
			
			int nSCMGroupId = Integer.parseInt(sSCMGroupId);
			parameters.put("SCMGroupId",sSCMGroupId);

			SCMGroup oSCMGroup = SCMGroup.findById(nSCMGroupId);
			if(oSCMGroup == null){
				out.print(1);
				return;
			}
			Boolean bHasAccounts = (Boolean)processor.excute(sServiceId,sMethodName,parameters);
			if(bHasAccounts){
				if(sSCMGroupNames == ""){
					sSCMGroupNames = oSCMGroup.getGroupName();
				}else{
					sSCMGroupNames = sSCMGroupNames + "," + oSCMGroup.getGroupName();
				}
			}
		}
	}catch(Exception e){
		out.print(e.getMessage());
		return;
	}
	if(sSCMGroupNames != ""){
		out.print("【"+sSCMGroupNames+"】分组中还存在帐号，请解除绑定帐号后再删除分组");
		return;
	}
	out.print(0);
%>


