<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error.jsp"%>
<%@ page import="com.trs.cms.auth.persistent.Rights" %>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.config.OperationConfig" %>
<%@ page import="com.trs.cms.auth.config.OperationRelatedConfig" %>
<%@ page import="com.trs.cms.auth.config.RightConfigServer" %>
<%@ page import="com.trs.cms.auth.domain.IRightHost" %>
<%@ page import="com.trs.cms.auth.domain.IRightMgr" %>
<%@ page import="com.trs.cms.auth.domain.RightHostFactory" %>
<%@ page import="com.trs.cms.auth.persistent.Group" %>
<%@ page import="com.trs.cms.auth.persistent.Right" %>
<%@ page import="com.trs.cms.auth.persistent.Role" %>
<%@ page import="com.trs.cms.content.CMSObj" %>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.components.wcm.resource.Statuses" %>
<%@ page import="com.trs.infra.config.XMLConfigServer" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../../include/public_server.jsp"%>
<%
	int nOperId = currRequestHelper.getInt("OperId", 0);
	int nOperType = currRequestHelper.getInt("OperType", 0);
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="right_view_whole.jsp.title">TRS WCM操作者视图权限查看</TITLE>
<script src="../../../app/js/runtime/myext-debug.js"></script>
<script src="../../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../../app/js/data/locale/auth.js"></script>
<script src="../../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../../app/js/source/wcmlib/core/AuthServer.js"></script>
<script src="../../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>

<SCRIPT src="../../../app/js/source/wcmlib/Observable.js"></SCRIPT>
<SCRIPT src="../../../app/js/source/wcmlib/Component.js"></SCRIPT>
<SCRIPT src="../../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
<SCRIPT src="../../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT>
<link href="../../../app/js/resource/widget.css" rel="stylesheet" type="text/css" />


<script language="javascript" src="right_view_whole.js" type="text/javascript"></script>
<link href="right_tree.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	.mainTable{
		background-color:"#fff";
	}
    .TreeView div{/*资源名称列*/
        width:1px;
		overflow-x:visible;
        overflow-y:visible;
		height:23px;
    }
    .mainTable tr{         
		height:24px;
    }
	.mainTable td{
		border-right:1px solid gray;
		border-bottom:1px solid gray;
		text-align:center;
	}
 	.mainTable .grid_head{
		height:28px!important;
		height:29px;
		background-image:url(../../images/list/button-bg.png);
		background-repeat:repeat;
		padding:0px;
	}
	.mainTable .grid_head td{
		border:0px;
	}
	.mainTable tr.Selected{
        background-color:#aabbcc;
    }
	*{
		font-size:12px;
	}
	.fixTableHead{
		position: relative;
		top:expression(this.offsetParent.scrollTop-2);
	}
    .TreeView A{/*资源名称列*/
        width:180px;
		overflow:hidden;
        white-space:nowrap;
		text-overflow:ellipsis;
		height:23px;
		text-align:left;		
    }
	div{
		scrollbar-face-color : #EDEDED;
		scrollbar-darkshadow-color : #EDEDED;
	}
</style>
<script language="javascript">
<!--
	<%writeRightDefsScript(out);%>
	Event.observe(window, 'load', function(){
		setTimeout(function(){
			if(window.navigator.appVersion.indexOf("MSIE 7") >= 0){
				$('treeTitle').style.left = '0px';
			}
		},5);
	});
//-->
</script>
</HEAD>

<body style="margin:0px;padding:0px;overflow:hidden;">
	<table border=0 cellspacing=0 cellpadding=0 style="width:100%;height:100%;font-size:12px;padding:10px;">
		<tr>
			<td style="height:30px; padding-right:0px 20px;">
				<div style="float:left;">
					<%=getOperDesc(nOperId,nOperType)%>&nbsp;&nbsp;
				</div>
				<div style="float:right;">
					<span onclick="showType('site');" style="cursor:pointer;"><input type="radio" id="siteRadio" name="rightTypeTab" checked value=""><span WCMAnt:param="right_view_whole.jsp.siteright">站点类操作权限</span></span>
					<span onclick="showType('channel');" style="cursor:pointer;"><input type="radio" id="channelRadio" name="rightTypeTab" value=""><span WCMAnt:param="right_view_whole.jsp.channel">栏目类操作权限</span></span>
					<span onclick="showType('document');" style="cursor:pointer;"><input type="radio" id="documentRadio" name="rightTypeTab" value=""><span WCMAnt:param="right_view_whole.jsp.doc">文档类操作权限</span></span>
					<span onclick="showType('template');" style="cursor:pointer;"><input type="radio" id="templateRadio" name="rightTypeTab" value=""><span WCMAnt:param="right_view_whole.jsp.temp">模板类操作权限</span></span>
				<%--if(loginUser.isAdministrator()){--%>
					<span onclick="showType('flow');" style="cursor:pointer;"><input type="radio" id="flowRadio" name="rightTypeTab" value=""><span WCMAnt:param="right_view_whole.jsp.workflow">工作流类操作权限</span></span>
				<%--}--%>
				</div>
				<div id="imgDescription" style="font-size:12px;clear:all;display:none;">
					(<span WCMAnt:param="right_view_whole.jsp.explain">说明：</span>
					<span style="background:url('../../images/auth/enable.gif') no-repeat; padding-left:16px;padding-top:2px;font-size:12px;overflow:visible;" WCMAnt:param="right_view_whole.jsp.desc">表示权限来自用户，而非继承于用户组、角色或逻辑权限</span>&nbsp;
					)
					<span id="loading" style="background:url('../../images/auth/loading.gif') no-repeat center center;width:214;height:15px;display:none;"></span>
				</div>
			</td>
		</tr>

		<tr>
			<td>
				<div style="width:100%;height:100%;overflow:auto;">
				<table border=0 cellspacing=0 cellpadding=0 style="width:100%;height:100%;" class="mainTable"  onclick="dealWithClickTable(event);">
					<tr valign="top">
						<td style="width:20px;border:0px;display:none;" id="imgTdTable">
							<!-- 当前操作对象为用户时，显示这列表示权限的来源//-->
							<table border=0 cellspacing=0 cellpadding=0 style="width:100%;">
								<tr class="fixTableHead grid_head">
									<td style="text-align:left;padding-left:10px;">&nbsp;</td>
								</tr>
								<tr id="h_r_0">
									<td>&nbsp;</td>
								</tr>
								<tr id="h_r_1">
									<td>&nbsp;</td>
								</tr>
								<tr id="h_r_2">
									<td>&nbsp;</td>
								</tr>
								<tr id="h_r_4">
									<td>&nbsp;</td>
								</tr>
							</table>
							
						</td>
						<td style="width:200px;border:0px;">
						<!-- 左边的导航树//-->
							<table cellspacing=0 cellpadding=0 id="leftNavTree" class="TreeView" style="width:100%;border-left:1px soild gray;" >
								<tr class="fixTableHead grid_head" id="treeTitle">
									<td style="text-align:left;padding-left:10px;" WCMAnt:param="right_view_whole.jsp.sourcename">资源名称</td>
								</tr>
								<tr>
									<td style="text-align:left;padding-left:10px;padding-right:5px;">
										<DIV class="SiteType0Root" id="r_0" title="文字库" WCMAnt:paramattr="title:right_view_whole.jsp.wordsitetype" classPre="SiteType0">
											<A href="#" WCMAnt:param="right_view_whole.jsp.wordsitetype">文字库</A>
										</DIV>
									</td>
								</tr>
								<tr>
									<td style="text-align:left;padding-left:10px;padding-right:5px">
										<DIV class="SiteType1Root plugins" id="r_1" title="图片库" WCMAnt:paramattr="title:right_view_whole.jsp.imgsitetype" classPre="SiteType1">
											<A href="#" WCMAnt:param="right_view_whole.jsp.imgsitetype">图片库</A>
										</DIV>
									</td>
								</tr>
								<tr>
									<td style="text-align:left;padding-left:10px;padding-right:5px">
										<DIV class="SiteType2Root plugins" id="r_2" title="视频库" WCMAnt:paramattr="title:right_view_whole.jsp.videositetype" classPre="SiteType2">
											<A href="#" WCMAnt:param="right_view_whole.jsp.videositetype">视频库</A> 
										</DIV>
									</td>
								</tr>
								<tr>
									<td style="text-align:left;padding-left:10px;padding-right:5px">
										<DIV class="SiteType4Root plugins" id="r_4" title="资源库" WCMAnt:paramattr="title:right_view_whole.jsp.source" classPre="SiteType4">
											<A href="#" WCMAnt:param="right_view_whole.jsp.source">资源库</A> 
										</DIV>
									</td>
								</tr>
							</table>
						</td>

						<td style="border:0px;">
						<!-- 右边的列表 //-->
							<!-- 站点的权限列表 //-->
							<table border=0 cellspacing=0 cellpadding=0 id="siteRightTab" style="width:100%;">
								<tr class="fixTableHead grid_head">
									<script language="javascript">
									<!--
										//输出权限列表表头
										document.write(WCMRightHelper.getHeadHTMLForType('site'));
										for(var i = 0; i < 3; i++){
											document.write(WCMRightHelper.getTemplaterHTML('site', 'blank', 'site_r_'+i));
										}
										document.write(WCMRightHelper.getTemplaterHTML('site', 'blank', 'site_r_'+4));
									//-->
									</script>
								</tr>
							</table>

							<!-- 栏目的权限列表 //-->
							<table border=0 cellspacing=0 cellpadding=0 id="channelRightTab" style="width:100%;display:none;">
								<tr class="fixTableHead grid_head">
									<script language="javascript">
									<!--
										//输出权限列表表头
										document.write(WCMRightHelper.getHeadHTMLForType('channel'));
										for(var i = 0; i < 3; i++){
											document.write(WCMRightHelper.getTemplaterHTML('channel', 'blank', 'channel_r_'+i));
										}
										document.write(WCMRightHelper.getTemplaterHTML('channel', 'blank', 'channel_r_'+4));
									//-->
									</script>
								</tr>
							</table>

							<!-- 模板的权限列表 //-->
							<table border=0 cellspacing=0 cellpadding=0 id="templateRightTab" style="width:100%;display:none;">
								<tr class="fixTableHead grid_head">
									<script language="javascript">
									<!--
										//输出权限列表表头
										document.write(WCMRightHelper.getHeadHTMLForType('template'));
										for(var i = 0; i < 3; i++){
											document.write(WCMRightHelper.getTemplaterHTML('template', 'blank', 'template_r_'+i));
										}
										document.write(WCMRightHelper.getTemplaterHTML('template', 'blank', 'template_r_'+4));
									//-->
									</script>
								</tr>
							</table>

							<!-- 文档的权限列表 //-->
							<table border=0 cellspacing=0 cellpadding=0 id="documentRightTab" style="width:100%;display:none;">
								<tr class="fixTableHead grid_head">
									<script language="javascript">
									<!--
										//输出权限列表表头
										document.write(WCMRightHelper.getHeadHTMLForType('document'));
										for(var i = 0; i < 3; i++){
											document.write(WCMRightHelper.getTemplaterHTML('document', 'blank', 'document_r_'+i));
										}
										document.write(WCMRightHelper.getTemplaterHTML('document', 'blank', 'document_r_'+4));
									//-->
									</script>
								</tr>
							</table>

							<!-- 工作流的权限列表 //-->
							<table border=0 cellspacing=0 cellpadding=0 id="flowRightTab" style="width:100%;display:none;">
								<tr class="fixTableHead grid_head">
									<script language="javascript">
									<!--
										//输出权限列表表头
										document.write(WCMRightHelper.getHeadHTMLForType('flow'));
										for(var i = 0; i < 3; i++){
											document.write(WCMRightHelper.getTemplaterHTML('flow', 'blank', 'flow_r_'+i));
										}
										document.write(WCMRightHelper.getTemplaterHTML('flow', 'blank', 'flow_r_'+4));
									//-->
									</script>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				</div>
			</td>
		</tr>
	</table>

<!-- 用于生成行的模板//-->
<table border=0 cellspacing=0 cellpadding=0 style="display:none;" id="templater">
	<tbody>
		<tr id="nav_templater"><!-- 导航树的模版 //-->
			<td style="text-align:left;padding-left:10px;padding-right:5px"></td>
		</tr>  
		<tr id="img_templater"><!-- 启用或禁用自身权限的模板 //-->
			<td>&nbsp;</td>
		</tr>  		
		<!-- 权限列表表头和表身的模板 //-->
		<script language="javascript">
		<!--
			document.write(WCMRightHelper.getTemplaterHTML('site', 'all'));
			document.write(WCMRightHelper.getTemplaterHTML('channel', 'all'));
			document.write(WCMRightHelper.getTemplaterHTML('template', 'all'));
			document.write(WCMRightHelper.getTemplaterHTML('document', 'all'));
			document.write(WCMRightHelper.getTemplaterHTML('flow', 'all'));
		//-->
		</script>
	</tbody>
</table>
<!-- 用于请求到达后数据的交换处理//-->
<div id="tempChangeArea" style="display:none;"></div>
</body>
</html>

<%!
	private void writeRightDefsScript(JspWriter out) throws Exception {
        XMLConfigServer oXMLConfigServer = XMLConfigServer.getInstance();
        List listOperation = oXMLConfigServer.getConfigObjects(OperationConfig.class);
        for (int i = 0, nSize = listOperation.size(); i < nSize; i++) {
            OperationConfig operationConfig = (OperationConfig) listOperation.get(i);
            if (operationConfig == null)
                continue;

            String sDepends = getDepends(operationConfig), sSimilar = getSimilar(operationConfig);
            

            out.println("WCMRightHelper.addRightDef("
                    + operationConfig.getIndex()
                    + ", \""
                    + CMyString.filterForJs(operationConfig.getDispName())
                    + "\", \""
                    + CMyString.filterForJs(CMyString.showNull(operationConfig
                            .getDesc())) + "\", \""
                    + CMyString.showNull(operationConfig.getType()) + "\", "+sDepends+", "+sSimilar+");");
        }
    }
    
    private String getSimilar(OperationConfig operationConfig){
        int[] pResult = RightConfigServer.getInstance().getSimilarIndexs(operationConfig.getIndex());
        if(pResult == null || pResult.length == 0)return null;
        
        int nSize = pResult.length;
        StringBuffer sbResult = new StringBuffer(nSize*4+2);
        sbResult.append("\"");
        boolean bFirst = true;
        for (int i = 0; i < nSize; i++) {            
            if (pResult[i] == 0)
                continue;
            
            if(bFirst){                
                bFirst = false;
            }else{
                sbResult.append(",");
            }
            sbResult.append(pResult[i]);
        }
        sbResult.append("\"");
        if(bFirst)return null;
        return sbResult.toString();
    }
    
    private String getDepends(OperationConfig operationConfig) {
        OperationRelatedConfig includes = operationConfig.getDepends();
        if (includes == null)
            return null;        
        
        ArrayList elements = includes.getOperations();
        int nSize = elements.size();
        StringBuffer sbResult = new StringBuffer(nSize*4);
        boolean bFirst = true;
        sbResult.append("\"");
        for (int i = 0; i < nSize; i++) {
            OperationConfig includeOperation = (OperationConfig) elements
                    .get(i);
            if (includeOperation == null)
                continue;
            
            if(bFirst){                
                bFirst = false;
            }else{
                sbResult.append(",");
            }
            sbResult.append(includeOperation.getIndex());
        }
        sbResult.append("\"");
        return sbResult.toString();
    }

	private String getOperDesc(int _nOperId,int _nOperType) throws WCMException{
		switch(_nOperType){
			case User.OBJ_TYPE : {
				User currUser = User.findById(_nOperId);
				if(currUser == null) throw new WCMException(CMyString.format(LocaleServer.getString("right_view_whole.jsp.usernotfoundexception", "没有找到[id={0}]的用户!"), new int[]{_nOperId}));
				return "<span style='background:url(\"../../images/wcm52/icon_user.gif\") no-repeat;padding-left:16px;'>"+currUser+"</span>";
			}
			case Group.OBJ_TYPE : {
				Group currGroup = Group.findById(_nOperId);
				if(currGroup == null) throw new WCMException(CMyString.format(LocaleServer.getString("right_view_whole.jsp.orgnotfoundexception", "没有找到[id={0}]的组织!"), new int[]{_nOperId}));
				return "<span style='background:url(\"../../images/wcm52/icon_group.gif\") no-repeat;padding-left:16px;'>"+currGroup+"</span>";
			}case Role.OBJ_TYPE : {
				Role currRole = Role.findById(_nOperId);
				if(currRole == null) throw new WCMException(CMyString.format(LocaleServer.getString("right_view_whole.jsp.rolenotfoundexception", "没有找到[id={0}]的角色!"), new int[]{_nOperId}));
				return "<span style='background:url(\"../../images/wcm52/icon_role.gif\") no-repeat;padding-left:16px;'>"+currRole+"</span>";
			}
			default : {
				throw new WCMException(LocaleServer.getString("right_view_whole.jsp.label.invalidoperator","无效的操作者类型!"));
			}
		}
	}
%>