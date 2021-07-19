<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.individuation.Individuation" %>
<%@ page import="com.trs.components.wcm.individuation.Individuations" %>
<%@ page import="com.trs.components.wcm.individuation.CustomSiteCheckedInfo" %>
<%@include file="../include/list_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	Individuations objs = (Individuations)request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	out.clear();
%>
	window.allCustomSiteIds = [];
	window.customSites = [];
	window.checkedIds = [];
	function selectCustomSite(id, includeIds){
		BasicDataHelper.call("wcm6_individuation", 'setIsChecked', {
			excludeIds : allCustomSiteIds.join(","),
			includeIds : id,
			refreshCustomSiteSession : true
			}, true, function(){
				$('nav_tree').contentWindow.refreshTree(function(){$('nav_tree').contentWindow.refreshMain();});
		});
	}
<%	
	if(objs.size()>0){
%>
		customSites.push({
			desc : '<%=LocaleServer.getString("indivadual.query.cancelecustomsite", "取消选中的自定义站点")%>',
			oprKey : 'item0',
			cmd : function(){
				selectCustomSite(0, "");
			}
		});
<%
		for(int i=0; i<objs.size(); i++){
			Individuation obj = (Individuation)objs.getAt(i);
			int nId = obj.getId();
			String sTitle = obj.getPropertyAsString("title");
			String sParamValue = obj.getPropertyAsString("ParamValue");
			String oBjectIdsValue = obj.getPropertyAsString("OBJECTIDSVALUE");
%>
		customSites.push({
			desc : '<%=sParamValue%>',
			oprKey : 'item<%=nId%>',
			cmd : function(){
				var id = '<%=nId%>';
				var ids = '<%=oBjectIdsValue%>';
				selectCustomSite(id, ids);
			}
		});
		allCustomSiteIds.push('<%=nId%>');
<%	
			if(CustomSiteCheckedInfo.getCustomSiteCheckInfo(obj.getId(), loginUser) != null){
%>		
				checkedIds.push('<%=nId%>');
<%
			}
		}
	}else {	
%>
		customSites.push({
			desc : '<%=LocaleServer.getString("indivadual.query.nocustomsite","现在就进行站点个性化设置")%>',
			oprKey : 'setCustomSites',
			cmd : function(){
				var isAdmin = false;
				if(wcm.AuthServer.isAdmin())
					isAdmin = true;
				var sUrl = 'individuation/individual.html?path=customSite&isAdmin=' + isAdmin;
				if(window.showModalDialog){
					var sFeatures = "dialogHeight:450px;dialogWidth:560px;status:no;scroll:no";
					window.showModalDialog(sUrl, top, sFeatures);

				}else{
					var t	= window.screen.height/2 - 150, l = window.screen.width/2 - 280;
					var sFeatures = "width=560px,height=450px,status=no,scroll=no";
					sFeatures += ",top="+t+"px,"+"left="+l+"px";
					window.open(sUrl, document.hostname + 'individuate', sFeatures);
				}
				//MenuOperates.individuate('customSite');
			}
		});
<%}%>	