<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.infoview.InfoViewDataHelper" %>
<%@ page import="com.trs.components.common.publish.domain.publisher.PublishPathCompass" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishContent" %>
<%@ page import="com.trs.components.common.publish.persistent.element.PublishElementFactory" %>
<%@ page import="com.trs.infra.util.CMyString"%>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%
    // 初始化参数 —— 文档
    // 仅仅测试指定的文档是否存在
    int nDocumentId = currRequestHelper.getInt("DocumentId", 0);
    Document document = null;
    if(nDocumentId > 0){
        document  = Document.findById(nDocumentId);
    }
    if(document == null){
        throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("fetch_document.id.zero","获取ID为[{0}]的文档失败！"),new int[]{nDocumentId}));
    }
    if(document.getStatusId()==10){//已发
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
            response.sendRedirect(sPath);
            return;
        }
    }
%>