<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.metadata.definition.ClassInfo" %>
<%@ page import="com.trs.components.metadata.definition.ClassInfos" %>
<%@ page import="com.trs.infra.persistent.db.DBManager" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@include file="../../include/public_processor.jsp"%>
<%
	ClassInfos objs = null;
	String nParentType = processor.getParam("ParentType");
	if("v".equalsIgnoreCase(nParentType)){
		processor.setEscapeParameters(new String[]{"ParentId", "ViewId"});
		objs = (ClassInfos)processor.excute("MetaDataDef", "queryClassInfosUsingMetaView");
	}else{
		objs = (ClassInfos)processor.excute("ClassInfo", "queryClassInfos");
	}
	out.clear();
	StringBuffer sbHtml = new StringBuffer();
	makeClassInfoHTML(sbHtml, objs);
	out.println(sbHtml);
%>

<%!

    private void makeClassInfoHTML(StringBuffer sb, ClassInfos classInfos) throws WCMException {
        for (int i = 0, nSize = classInfos.size(); i < nSize; i++) {
            this.makeClassInfoHTML(sb, (ClassInfo) classInfos.getAt(i));
        }
    }

    private void makeClassInfoHTML(StringBuffer sb, ClassInfo classInfo) throws WCMException {
        if (classInfo == null)
            return;
        // 添加自己
        appendOneClassInfoHTML(sb, classInfo);
        // 判断是否需要添加一个空的子
        final String SQL_QUERY_CHILD = "select CLASSINFOID from XWCMCLASSINFO where ParentId=?";
        int nId = DBManager.getDBManager().sqlExecuteIntQuery(SQL_QUERY_CHILD,
                new int[] { classInfo.getId() });
        if (nId > 0)
            sb.append("<ul></ul>\n");
    }

    private void appendOneClassInfoHTML(StringBuffer sb, ClassInfo classInfo) throws WCMException {
		sb.append("<div");
		sb.append(" title='");
		sb.append(LocaleServer.getString("viewclassinfo.label.objId", "编号"))	.append("：").append(classInfo.getId()).append("\n");
		sb.append(LocaleServer.getString("viewclassinfo.label.desc", "名称"))
		.append("：").append(classInfo.getName()).append("\n");
		sb.append(LocaleServer.getString("viewclassinfo.label.cruser", "创建者"))	.append("：").append(classInfo.getCrUserName()).append("\n");
		sb.append(LocaleServer.getString("viewclassinfo.label.crtime", "创建时间"))
		.append("：").append(classInfo.getCrTime());
		sb.append("'");
		sb.append(" id='cls_");
        sb.append(classInfo.getId());
        sb.append("'");
		sb.append(" classPre='classInfo'");
        sb.append(">");
        sb.append("<a href='#'");
		sb.append(" name='acls_");
        sb.append(classInfo.getId());
        sb.append("'>");
        sb.append(classInfo.getName());
        sb.append("</a>");
        sb.append("</div>");
    }
%>