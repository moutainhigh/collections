<% 
	if(nObjectId > 0){
		boolean hasRight = false;
		if(nFlowDocId > 0) {
			hasRight = WCMProcessServiceHelper.hasFlowingActionRight(loginUser, nFlowDocId, WCMRightTypes.DOC_EDIT);
		}else{
			hasRight = AuthServer.hasRight(loginUser, document, WCMRightTypes.DOC_EDIT);
		}
		if(!hasRight){
			if(nFlowDocId > 0){
				String sWorklistViewType = request.getParameter("WorklistViewType");
				int nWorklistViewType = 0;
				if(!CMyString.isEmpty(sWorklistViewType)){
					nWorklistViewType = Integer.parseInt(sWorklistViewType);
				}
				response.sendRedirect("./viewdata_detail.jsp?DocumentId=" + nObjectId + "&ChannelId=" + nChannelId + "&FlowDocId=" + nFlowDocId + "&WorklistViewType=" + nWorklistViewType);
			}else{
				throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, CMyString.format(LocaleServer.getString("metaviewdata_addedit_inflow_right_redirect.jsp.noright_change_doc", "您没有权限修改ID为[{0}]的文档！"), new int[]{nObjectId}));
			}
		}
	}
%>