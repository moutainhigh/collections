<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error.jsp" buffer="200kb"%>
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
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.persistent.BaseObj" %>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.components.wcm.resource.Statuses" %>
<%@ page import="com.trs.infra.config.XMLConfigServer" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>

<%@ page import="com.trs.DreamFactory" %>
<%@ page import="com.trs.components.wcm.content.domain.SiteMemberMgr" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSites" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.common.WCMTypes" %>
<%@ page import="com.trs.presentation.nav.TreeCreator" %>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%
	int nOperId = currRequestHelper.getInt("OperId", 0);
	int nOperType = currRequestHelper.getInt("OperType", 0);
	int nLoginOperId = loginUser.getId();
	int nLoginOperType = User.OBJ_TYPE;

	CMSObj operator = (CMSObj)BaseObj.findById(nOperType, nOperId);
	if(operator == null||(nOperType==User.OBJ_TYPE && ((User)operator).getName().indexOf("$")!=-1)){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("object.not.found", "没有找到指定的{1} [ID={0}]."), new String[] {String.valueOf(nOperId), WCMTypes.getLowerObjName(nOperType)}));
	}
	
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="right_edit.jsp.title">权限设置</TITLE>
<script src="../../../app/js/runtime/myext-debug.js"></script>
<script src="../../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../../app/js/data/locale/auth.js"></script>
<script src="../../../app/js/data/locale/system.js"></script>
<script src="../../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../../app/js/source/wcmlib/core/AuthServer.js"></script>
<script src="../../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>

<SCRIPT src="../../../app/js/source/wcmlib/Observable.js"></SCRIPT>
<SCRIPT src="../../../app/js/source/wcmlib/Component.js"></SCRIPT>
<SCRIPT src="../../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
<SCRIPT src="../../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT>
<link href="../../../app/js/resource/widget.css" rel="stylesheet" type="text/css" />


<script language="javascript" src="right_edit.js" type="text/javascript"></script>
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
		height:26px;
    }
	.mainTable td{
		border-right:1px solid gray;
		border-bottom:1px solid gray;
		text-align:center;
	}
 	.mainTable .grid_head{
		height:28px!important;
		height:29px;
		background:url(../../images/list/button-bg.png) center center repeat-x;
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
		top:expression(this.offsetParent.scrollTop);
	}
	.updateRow{
		background-color:#FFE4B5;
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
	.authbtn1{
		background:url('../../images/auth/save_one.png') no-repeat; padding-left:14px;padding-top:1px;cursor:pointer;
		background-color:#cecfde;
	}
	.authbtn2{
		background:url('../../images/auth/save_all.png') no-repeat; padding-left:14px;padding-top:1px;cursor:pointer;
		background-color:#cecfde;
	}
 	.mainTable .grid_head td{
		border-right:1px solid gray!important;
		border-bottom:1px solid gray!important;
		border-top:1px solid gray!important;
	}
 	.mainTable .grid_head{
		background:transparent;
	}
 	.mainTable .grid_head td{
		cursor:default;
	}
 	.mainTable .grid_head td{
		background:url(../../images/list/button-bg.png) center center repeat-x;
	}
	.datarow table{
		table-layout:fixed;
	}
	.datarow td{
		white-space:nowrap;
		overflow:hidden;
		text-overflow:ellipsis;
	}
</style>
<script language="javascript">
<!--
	<%writeRightDefsScript(out);%>
	Event.observe(window, 'load', function(){
		setTimeout(function(){
			if(window.navigator.appVersion.indexOf("MSIE") >= 0){
				$('treeTitle').style.left = '0px';
			}
		},5);
	});
//-->
</script>
</HEAD>
<!--先注释一下，调整了显示样式，cvs比较一直覆盖不下来 -->
<body style="margin:0px;padding:0px;">
	<table border=0 cellspacing=0 cellpadding=0 style="width:100%;height:100%;font-size:12px;padding:10px;">
		<tr>
			<td style="height:24px; padding:0px 20px;">
				<div style="float:left;">
					<%=getOperDesc(nOperId,nOperType)%>&nbsp;&nbsp;
					<button type="button" class="authbtn1" id="saveCurrRowId" WCMAnt:param="right_edit.jsp.save">保存选定行</button>
					(<span style="padding:0px 3px;background-color:#aabbcc;">&nbsp;</span><span WCMAnt:param="right_edit.jsp.exp">标注</span>)&nbsp;&nbsp;
					<button type="button" class="authbtn2" id="saveAllRowsId" WCMAnt:param="right_edit.jsp.saveall">保存全部</button>
					(<span style="padding:0px 3px;" class="updateRow">&nbsp;</span><span WCMAnt:param="right_edit.jsp.exp">标注</span>)&nbsp;&nbsp;				
				</div>
				<div id="imgDescription" style="font-size:12px;float:right;margin-top:5px;margin-left:3px;">
					(<span WCMAnt:param="right_edit.jsp.explain">说明：</span>
					<span style="background:url('../../images/auth/enable.gif') no-repeat;width:16px;margin-right:4px;padding-left:16px;padding-bottom:2px;"></span>
					<span style="padding-top:2px;font-size:12px;overflow:visible;" WCMAnt:paramattr="title:right_edit.jsp.titledesc" WCMAnt:param="right_edit.jsp.desc" title="单击此类图标将删除本用户自身的权限&#13;此时用户权限将继承自用户组、角色或逻辑权限">表示权限来自用户，而非继承于用户组、角色或逻辑权限.</span>&nbsp;
					)
					<span id="loading" style="background:url('../../images/auth/loading.gif') no-repeat center center;width:214;height:15px;display:none;"></span>
				</div>
			</td>
		</tr>
		<tr>
			<td style="height:24px; padding:0px 20px;">
				<div>
					<span onclick="showType('site');" style="cursor:pointer;"><input type="radio" id="siteRadio" name="rightTypeTab" checked value="site"><span WCMAnt:param="right_edit.jsp.siteright">站点类操作权限</span></span>
					<span onclick="showType('channel');" style="cursor:pointer;"><input type="radio" id="channelRadio" name="rightTypeTab" value="channel"><span WCMAnt:param="right_edit.jsp.channel">栏目类操作权限</span></span>
					<span onclick="showType('document');" style="cursor:pointer;"><input type="radio" id="documentRadio" name="rightTypeTab" value="document"><span WCMAnt:param="right_edit.jsp.doc">文档类操作权限</span></span>
					<span onclick="showType('template');" style="cursor:pointer;"><input type="radio" id="templateRadio" name="rightTypeTab" value="template"><span WCMAnt:param="right_edit.jsp.temp">模板类操作权限</span></span>
				<%--if(loginUser.isAdministrator()){--%>
					<span onclick="showType('flow');" style="cursor:pointer;"><input type="radio" id="flowRadio" name="rightTypeTab" value="flow"><span WCMAnt:param="right_edit.jsp.workflow">工作流类操作权限</span></span>
				<%--}--%>
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
							<table border=0 cellspacing=0 cellpadding=0 style="width:100%;" id="imgTable">
								<tr class="fixTableHead grid_head" id="h_r_-1">
									<td style="text-align:left;padding-left:10px;border-left:1px solid gray;">&nbsp;</td>
								</tr>
							</table>
							
						</td>
						<td style="width:200px;border:0px;">
						<!-- 左边的导航树//-->
							<table cellspacing=0 cellpadding=0 id="leftNavTree" class="TreeView" style="width:100%;border-left:1px soild gray;" >
								<tr class="fixTableHead grid_head" id="treeTitle">
									<td style="text-align:left;padding-left:10px;"><%=LocaleServer.getString("right_edit.label.site", "站点:")%>
									<div id="r_-1" style="display:inline;">
									<select onchange="onChange(this);" id="siteSel" style="width:150px;">
										<%
											//复用treecreator中取站点的过程，
											//保持right_edit.js(SitesLoadMgr.loadSites();)right_edit.jsp中所取站点逻辑的一致性
											TreeCreator creator = new TreeCreator(loginUser, currRequestHelper, out);
											WebSites sites = creator.getWebSites(-1);//-1为sitetype

											for (int i = 0, len = sites.size(); i < len; i++) {
												WebSite site = (WebSite) sites.getAt(i);
												if (site == null)continue;
												
										%>
											<option value="<%=site.getId()%>"><%=site.getDispDesc()%></option>												
										<%}%>
									</select>
									</div>
									</td>
								</tr>
							</table>
						</td>

						<td style="border:0px;" class="datarow">
						<!-- 右边的列表 //-->
							<!-- 站点的权限列表 //-->
							<table border=0 cellspacing=0 cellpadding=0 id="siteRightTab" style="width:100%;">
								<tr class="fixTableHead grid_head" id="site_r_-1">
									<script language="javascript">
									<!--
										//输出权限列表表头
										document.write(WCMRightHelper.getHeadHTMLForType('site'));
									//-->
									</script>
								</tr>
							</table>

							<!-- 栏目的权限列表 //-->
							<table border=0 cellspacing=0 cellpadding=0 id="channelRightTab" style="width:100%;display:none;">
								<tr class="fixTableHead grid_head" id="channel_r_-1">
									<script language="javascript">
									<!--
										//输出权限列表表头
										document.write(WCMRightHelper.getHeadHTMLForType('channel'));
									//-->
									</script>
								</tr>
							</table>

							<!-- 模板的权限列表 //-->
							<table border=0 cellspacing=0 cellpadding=0 id="templateRightTab" style="width:100%;display:none;">
								<tr class="fixTableHead grid_head" id="template_r_-1">
									<script language="javascript">
									<!--
										//输出权限列表表头
										document.write(WCMRightHelper.getHeadHTMLForType('template'));
									//-->
									</script>
								</tr>
							</table>

							<!-- 文档的权限列表 //-->
							<table border=0 cellspacing=0 cellpadding=0 id="documentRightTab" style="width:100%;display:none;">
								<tr class="fixTableHead grid_head" id="document_r_-1">
									<script language="javascript">
									<!--
										//输出权限列表表头
										document.write(WCMRightHelper.getHeadHTMLForType('document'));
									//-->
									</script>
								</tr>
							</table>

							<!-- 工作流的权限列表 //-->
							<table border=0 cellspacing=0 cellpadding=0 id="flowRightTab" style="width:100%;display:none;">
								<tr class="fixTableHead grid_head" id="flow_r_-1">
									<script language="javascript">
									<!--
										//输出权限列表表头
										document.write(WCMRightHelper.getHeadHTMLForType('flow'));
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
			<td style="border-left:1px solid gray;">&nbsp;</td>
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
				return "<span style='background:url(\"../../images/auth/icon_user.gif\") no-repeat;padding-left:16px;'>"+currUser+"</span>";
			}
			case Group.OBJ_TYPE : {
				Group currGroup = Group.findById(_nOperId);
				return "<span style='background:url(\"../../images/auth/icon_group.gif\") no-repeat;padding-left:16px;'>"+currGroup+"</span>";
			}case Role.OBJ_TYPE : {
				Role currRole = Role.findById(_nOperId);
				return "<span style='background:url(\"../../images/auth/icon_role.gif\") no-repeat;padding-left:16px;'>"+currRole+"</span>";
			}
			default : {
				throw new WCMException(LocaleServer.getString("right_edit.jsp.label.erroperoperator","无效的操作者类型!"));
			}
		}
	}

	private boolean isAdmin(int _objType, int _objId) throws WCMException {
		switch (_objType) {
		case Role.OBJ_TYPE:
			return _objId == Role.ADMINISTRATORS_ID;
		case User.OBJ_TYPE:
			User user = User.findById(_objId);
			return user != null && user.isAdministrator();
		}
		return false;
	}
%>