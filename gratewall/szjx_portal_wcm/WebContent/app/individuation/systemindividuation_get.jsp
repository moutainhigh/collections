<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>

<%@ page import="com.trs.components.wcm.individuation.Individuation" %>
<%@ page import="com.trs.components.wcm.individuation.IndividuationMgr" %>
<%@ page import="com.trs.components.wcm.individuation.Individuations" %>
<%@ page import="com.trs.infra.cluster.member.MemberManagerImpl" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.trs.ajaxservice.JSONHelper" %>

<%@include file="../include/public_server.jsp"%>
		

<%		
		//修改编码
		response.setHeader("ReturnJson", "true");

		HashMap ohashmap = new HashMap();
		
		IndividuationMgr mgr = new IndividuationMgr();
		Individuations individuations = mgr.getIndividuations4System();
			for (int i = 0, nSize = individuations.size(); i < nSize; i++) {
				Individuation element = (Individuation) individuations.getAt(i);
				if (element == null)
					continue;
			
			ohashmap.put(element.getParamName(),element.getParamValue());
			}

			Iterator iter = ohashmap.entrySet().iterator();
		while (iter.hasNext()) {
           Map.Entry entry = (Map.Entry) iter.next();
           String key = (String) entry.getKey();
           String val = (String) entry.getValue();
		  
		}
		out.println(JSONHelper.toSimpleJSON2(ohashmap,0));
 %>
