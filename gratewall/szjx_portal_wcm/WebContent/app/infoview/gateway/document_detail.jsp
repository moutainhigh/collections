<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="expected_error.jsp"%>
<%@include file="../../include/public_server_nologin.jsp"%>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.infoview.InfoViewDataHelper" %>
<%@ page import="com.trs.components.common.publish.domain.publisher.PublishPathCompass" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishContent" %>
<%@ page import="com.trs.components.common.publish.persistent.element.PublishElementFactory" %>
<%@ page import="javax.servlet.RequestDispatcher"%>
<%
	// ��ʼ������ ���� �ĵ�
	// ��������ָ�����ĵ��Ƿ����
	int nDocumentId = currRequestHelper.getInt("DocumentId", 0);
	Document document = null;
	if(nDocumentId > 0){
		document  = Document.findById(nDocumentId);
	}
	if(document == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "��ȡIDΪ["+nDocumentId+"]���ĵ�ʧ�ܣ�");
	}
	if(document.getStatusId()==10){//�ѷ�
		String sPath = null;
		PublishPathCompass compass = new PublishPathCompass();
		try {
			IPublishContent element = (IPublishContent)PublishElementFactory
				.lookupElement(605, nDocumentId);
			sPath = compass.getHttpUrl(element, 0);
		}
		finally{
			compass.clear();
		}
		if(sPath!=null){
			RequestDispatcher dis = request.getRequestDispatcher(sPath);
			dis.forward(request,response);
			return;
		}
	}else{
		int nInfoViewId = document.getPropertyAsInt("DocFlag",0);
		String sRedirectPage = "./document_detail_" + nInfoViewId + ".jsp";
		sRedirectPage = sRedirectPage;
		RequestDispatcher dis = request.getRequestDispatcher(sRedirectPage);
		dis.forward(request,response);
		return;
	}
%>