<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.components.metadata.center.IDocumentFieldsSyn" %>
<%@ page import="com.trs.components.metadata.center.DocumentFieldsSynFactory" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="java.util.List" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.components.metadata.MetaDataConstants" %>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%
	
	//获得视图ID
	String sViewId = null;
	String[] aViewId = {"viewid", "viewId", "ViewId"};
	for(int i = 0; i < aViewId.length; i++){
		sViewId = request.getParameter(aViewId[i]);
		if(sViewId != null){
			break;
		}
	}
	if(CMyString.isEmpty(sViewId)){
		throw new WCMException("没有指定要同步文档的视图ID[viewid]");
	}
	
	synDocumentFieldsValue(Integer.parseInt(sViewId.trim()));
	out.println("文档同步操作完成．<br>");
%>

<%!
    /**
     * 处理相关联的Document对象的同步
     */
    private void synDocumentFieldsValue(int nViewId) throws WCMException {
		MetaView oMetaView = MetaView.findById(nViewId);
        String sMainTableName = MetaDataConstants.makeTrueTableName(oMetaView.getMainTableName());
        List list = DocumentFieldsSynFactory.getDocumentFieldSyn(sMainTableName);
        if (list == null) {
            return;
        }

        for (int i = 0, length = list.size(); i < length; i++) {
            IDocumentFieldsSyn oDocumentFieldsSyn = (IDocumentFieldsSyn) list.get(i);
            oDocumentFieldsSyn.resetDocumentProperties(oMetaView);
        }
    }
%>