<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>

<%@ page import="com.trs.components.metadata.definition.MetaDBTables" %>
<%@ page import="com.trs.components.metadata.definition.MetaDBTable" %>
<%@ page import="com.trs.components.metadata.definition.MetaViews" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.components.metadata.definition.IMetaDataDefMgr" %>
<%@ page import="com.trs.DreamFactory" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@include file="../include/public_processor.jsp"%>

<HTML>
<HEAD>
<TITLE WCMAnt:param="tableinfo_delete_info.jsp.title">系统提示信息</TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<SCRIPT src="../../app/js/runtime/myext-debug.js"></SCRIPT>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/data/locale/metadbtable.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<!-- CarshBoard Inner Start -->
<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></SCRIPT>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/crashboard/resource/crashboard.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<!-- CarshBoard Inner End -->
<script language="javascript">
<!--
    window.m_cbCfg = {
        btns : [
            {
                text : wcm.LANG.METADBTABLE_27 || '确定',
                cmd : function(){                     
                    this.notify();
					this.hide();
					return false;
                }
            },
			{
				text : wcm.LANG.METADBTABLE_28 || '取消'
			}
        ]
    };    
//-->
</script>

<style type="text/css">
	*{margin:0;padding:0}
	.alertion_title{
		color: navy;
	}
	html, body{
		width:100%;
		height:100%;
		overflow:hidden;
		font-family: Georgia;
	}
	.dataItem{
		line-height: 18px;
		list-style-type: decimal;
		margin-top: 5px;
		border-bottom: solid 1px #efefef;
	}
</style>
</HEAD>

<body>
<%
	MetaDBTables metadbTables = (MetaDBTables)processor.excute("wcm61_metadbtable","findDBTableInfosByIds");	
%>
<div style="height:100%;width:100%;">
	<table border=0 align="center" cellspacing=1 cellpadding=3 style="font-size:12px; width: 100%;height:100%; background: silver;">
	<tbody>
		<tr>
			<td style="width: 100px; height: 200px; background: #ffffff" align="center">
				<img id="imClue" src="../images/include/delete.gif">
			</td>
			<td style="background: #ffffff;" align="left" valign="top">
				<div style="width: 100%; height: 30px; line-height: 30px; overflow: visible; border-bottom: 1px solid aliceblue; font-size: 14px; padding-left: 10px; font-weight: normal;">
					<div id="divOptionsDesc">
						<span class="alertion_title" style="color: red" id="spOperation" WCMAnt:param="tableinfo_delete_info.jsp.deleteConfirm">
							您正准备删除如下元数据，确定要删除吗?
						</span>
					</div>
				</div>
				<div style="height: 160px; overflow: auto">
					<div style="width: 100%;" id="divEmploymentInfo">
				<%
					MetaDBTable metaDBTable = null;
					MetaViews metaViews = null;
					MetaView metaView = null;
					IMetaDataDefMgr metaDataDefMgr = (IMetaDataDefMgr) DreamFactory.createObjectById("IMetaDataDefMgr");
					for(int i=0,size=metadbTables.size();i<size;i++){
						metaDBTable = (MetaDBTable)metadbTables.getAt(i);
						if(metaDBTable == null) continue;
						metaViews = metaDataDefMgr.getViewsUsingTable(metaDBTable);
				%>					
					<div class="dataItem">
						<span style="padding:2px;"><%=(i+1)%>.</span>
						<span title="<%=LocaleServer.getString("metadbtable.label.metadbtable", "元数据")%>-<%=metaDBTable.getId()%>" style='color:green;'>
							<%=CMyString.filterForHTMLValue(metaDBTable.getAnotherName())%>
						</span>
						<span>
						<%if(metaViews.isEmpty()){%>
							[<span style="color:red;" WCMAnt:param="tableinfo_delete_info.jsp.noViewQuote">无视图引用</span>]
						<%}else{%>						
							<span WCMAnt:param="tableinfo_delete_info.jsp.viewQuoted">被引用到视图</span>
							<%
								for(int j=0,viewSize=metaViews.size();j<viewSize;j++){
									metaView = (MetaView)metaViews.getAt(j);
									if(metaView == null) continue;
							%>
								[<span class="alertion_title" title="<%=LocaleServer.getString("metadbtable.label.metaview", "视图")%>-<%=metaView.getId()%>"><%=metaView.getDesc()%></span>]
							<%}%>
						<%}%>
						</span>
					</div>					
				<%}%></div>
				</div>
			</td>
		</tr>
	</tbody>
	</table>	
</div>
</BODY>
</HTML>