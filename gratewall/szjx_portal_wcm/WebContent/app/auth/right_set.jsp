<%
/** Title:			right_set.jsp
 *  Description:
 *		WCM5.2 设置权限页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			wsw
 *  Created:		2004-12-29 13:54
 *  Vesion:			1.0
 *  Last EditTime:	2004-12-29 / 2004-12-29	
 *	Update Logs:
 *		CH@2004-12-29 产生
 *		wenyh@2006-03-17,如果是站点/栏目判断是否已删除
 *		caohui@2006-9-1 减少不必要的依赖，改为Host自己Validate
 *
 *  Parameters:
 *		see right_set.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/errorforAuth.jsp"%>
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
<%@ page import="com.trs.infra.common.WCMTypes" %>
<%@ page import="com.trs.infra.util.ExceptionNumber" %>
<%@ page import="com.trs.infra.util.CMyException" %>

<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%
//4.初始化(获取数据)
	int nObjType = currRequestHelper.getInt("ObjType", 0);
	String sObjIds = request.getParameter("ObjId");
	if(sObjIds == null || sObjIds.equals("0"))
		throw new WCMException(CMyString.format(LocaleServer.getString("auth.label.noviewperssion", "新建模式不允许查看/设置权限!Type:[{0}] Id:[{1}]"), new String[]{String.valueOf(nObjType), sObjIds}));

	String[] sObjIdArray = sObjIds.split(",");
	IRightMgr currMgr = (IRightMgr) DreamFactory.createObjectById("IRightMgr");
	Rights	currRights = null;
	IRightHost rightHost = null;
	
	boolean bHasChildren = false;
	String sHostInfo = null;
	int nObjId = 0;
	for(int i = 0; i < sObjIdArray.length; i++){//循环进行权限校验
		nObjId = Integer.parseInt(sObjIdArray[i]);
		if(nObjType != 1){
				rightHost = RightHostFactory.makeRightHostFrom(nObjType, nObjId);
				if(rightHost == null || rightHost.getId() == 0){
					throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("object.not.found_a", "没有找到指定ID为[{0}]的对象!"), new String[]{String.valueOf(nObjId),"document"}));
				}
				try{
				//权限校验
				//rightHost.validateCanDoSetRight(loginUser);

				IRightHost[] children = rightHost.getChildren();
				bHasChildren = children != null && children.length>0;
					
				currRights = currMgr.getRights(rightHost.getSubstance());
				sHostInfo = rightHost.getInfo();
			}catch(Throwable t){
				int number = 0;
				if(t instanceof CMyException){
					number = ((CMyException)t).getErrNo();
				}
%>	
				<script>
					try{
						top.FaultDialog.show({
							code		: '<%=number%>' || '',
							message		: '<%=t.getMessage()%>' || '',
							detail		: '<%=t.getMessage()%>' || ''
						}, '<%=LocaleServer.getString("auth.label.error", "与服务器交互时出现错误")%>', function(){
							/*
							top.$nav().refreshSiteType('0', null, function(){
								top.getFirstHTMLChild(top.$nav().$("r_0")).click();
							});
							*/
						});
					}catch(error){}
				</script>
<%
				return;
			}
		}else{
			//权限校验
			//if(!loginUser.isAdministrator())
			//	throw new WCMException("当前用户["+loginUser.getName()+"]没有系统属性权限进行设置！");

			//业务代码
			currRights = currMgr.getRights(1,nObjId);

			sHostInfo = LocaleServer.getString("auth.label.system", "系统");
		}
	}

	String[] pRightTypeIds = rightHost.getRightTypeIds();
	String[] pRightTypeNames = rightHost.getRightTypeNames();
	

//7.结束
	out.clear();
%>
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE><%=LocaleServer.getString("auth.label.rightset", "权限设置")%>::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<base target="_self">
<link href="style.css" rel="stylesheet" type="text/css" />
<link href="auth_index.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/resource/widget.css" rel="stylesheet" type="text/css" />
<style>
	#id_TRSSimpleTab{
		float:left;
		width:30px;
		height:100%;
		position:absolute;
		margin-left:-1px;
	}
	html, body{
		width:100%;
		height:100%;
	}
	.no_object_found{font-size:14px;color:gray;height:100px;line-height:100px;font-style:italic;padding-left:15px;margin-top:30px;
	background:white;
	text-align:center;}
	.headdesc{
		overflow:hidden;
		text-overflow:ellipsis;
		white-space:nowrap;
	}
	.ext-ie8 .wcm-btn{display:inline;}
</style>
<script type="text/javascript" src="../js/data/locale/wcm52.js"></script>
<script src="../js/wcm52/CTRSHTMLElement.js"></script>
<script src="../js/wcm52/CTRSHTMLTr.js"></script>  
<script src="../js/wcm52/CTRSSimpleTab.js"></script>
<script src="../js/wcm52/CTRSArray.js"></script>

<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CWCMObj.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CWCMObjHelper.js"></SCRIPT>

<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSButton.js"></script>  

<script src="../js/wcm52/CTRSHashtable.js"></script>
<script src="../js/wcm52/CTRSRequestParam.js"></script>
<script src="../js/wcm52/CTRSAction.js"></script>


<script>
//global variable definition.
var m_nObjType = <%=nObjType%>;
var m_nObjId = <%=nObjId%>;
var m_sObjIds = '<%=CMyString.filterForJs(sObjIds)%>';
var loginUserId = <%=loginUser.getId()%>;
var loginUserName = '<%=CMyString.filterForJs(loginUser.getName())%>';
var needRefresh = false;
var OBJ_WEBSITE = 103;
var OBJ_CHANNEL = 101;
var OBJ_DOCUMENT = 605;
var OBJ_SYSATTRIBUTE = 1;
var TYPE_WCMOBJ_USER	= 204;
var TYPE_WCMOBJ_GROUP	= 201;
var TYPE_WCMOBJ_ROLE	= 203;
var WEBSITE_SET_RIGHT	= 7;
var CHANNEL_SET_RIGHT	= 55;
var m_bExtended=<%=(nObjId==2)?true:false%>;
var m_bHasChildren = <%=bHasChildren%>;
var m_bRightChanged = false;
var m_aRightTypeIds = [];
<%
for (int i = 0; i < pRightTypeIds.length; i++) {
%>
	m_aRightTypeIds.push('<%=pRightTypeIds[i]%>');
	m_aRightTypeIds['<%=pRightTypeIds[i]%>'] = <%=i%>;
<%
}	
%>
</script>
</HEAD>

<BODY style="overflow:auto;background:transparent;">
<form name="frmAction" method="post">
	<INPUT TYPE="hidden" name="ObjType" id="ObjType" value="<%=nObjType%>">
	<INPUT TYPE="hidden" name="ObjId" id="ObjId" value="<%=nObjId%>">
	<INPUT TYPE="hidden" name="ResetChildrenRights" id="ResetChildrenRights" value="0">
	<INPUT TYPE="hidden" name="RightsXML" id="RightsXML" value="">
</form>
<table id="table_body" class="wcm_table_layout" border="0" cellpadding="0" cellspacing="0">
<tr>
	<td valign="top" bgcolor="#fff" style="height:29px;">
		<div class="pageinfo">
			<div class="pageinfo_left"></div>
			<div class="pageinfo_right"></div>
			<div style="float:right" id="toggleModeBox"></div>	
			<div style="float:right; display:none;" id="btnBox">
			<script>		
				oButtons = new CTRSButtons("bt_table_noborder");
				oButtons.addTRSButton("<%=LocaleServer.getString("auth.button.adduser", "添加用户")%>", "addUser();", "../images/auth/icon_user.gif","<%=LocaleServer.getString("auth.tip.adduser", "添加用户")%>");
				oButtons.addTRSButton("<%=LocaleServer.getString("auth.button.addgroup", "添加用户组")%>", "addGroup();", "../images/auth/icon_group.gif","<%=LocaleServer.getString("auth.tip.addgroup", "添加用户组")%>");
				oButtons.addTRSButton("<%=LocaleServer.getString("auth.button.addrole", "添加角色")%>", "addRole();", "../images/auth/icon_role.gif","<%=LocaleServer.getString("auth.tip.addrole", "添加角色")%>");
				oButtons.addTRSButton("<%=LocaleServer.getString("auth.button.dropall", "全部删除")%>", "removeAll();", "../images/auth/button_delete.gif","<%=LocaleServer.getString("auth.tip.dropall", "删除全部设置")%>");
				oButtons.addTRSButton("<%=LocaleServer.getString("auth.button.saveright", "保存修改")%>", "onSave();", "../images/auth/save.png","<%=LocaleServer.getString("auth.tip.saveright", "保存修改")%>");
				oButtons.draw();
				delete oButtons;
			</script>	
			</div>
			<TABLE style="float:left;height:29px;"><TR VALIGN='MIDDLE'><TD id="hostInfoTd">&nbsp;<%=CMyString.transDisplay(sHostInfo)%></TD></TR></TABLE>
		</div>	
	</td>
	<td></td>
</tr>
<tr>
	<td valign="top">
		<div style="width:100%; height:100%; overflow-y:auto;">
		<TABLE width="100%" height="100%" style="border:0px;border-left:1px solid #474747;" cellpadding="0" cellspacing="0">
			<TR>		  
			  <TD bgcolor="whitesmoke" align=center valign="top" id="tabContentTd">
				<div id="id_TRSSimpleTab0" style="display:inline;"></div>				
				<div id="id_TRSSimpleTab1" style="display:none"></div>
				<div id="id_TRSSimpleTab2" style="display:none"></div>			
				<div id="id_TRSSimpleTab3" style="display:none"></div>		
				<div id="id_TRSSimpleTab4" style="display:none"></div>
			  </TD>
			</TR>	
		</TABLE>
		</div>
	</td>
	<td valign="top" style="width:50px;" id="tabTd" class="hideChild">
		<script>		
			TRSSimpleTab.nCurrIndex = 0;	
<%
String sRightType = null;
for (int i = 0; i < pRightTypeNames.length; i++) {
	sRightType = "auth.label." + pRightTypeIds[i];
	sRightType = LocaleServer.getString(sRightType, "");
	sRightType = CMyString.showEmpty(sRightType, pRightTypeNames[i]) + LocaleServer.getString("auth.label.doright", "");
	out.println("TRSSimpleTab.addItem(\""+sRightType+"\");");
}	
%>
			if(m_nObjType != OBJ_DOCUMENT){
				TRSSimpleTab.draw();
			}
		</script>
	</td>
</tr>
</table>
<div id="list_navigator" style="position:absolute;bottom:0px;right:60px;"></div>
<div id="errorInfo" style="display:none;"></div>
<script src="../../app/js/easyversion/lightbase.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/easyversion/extrender.js"></script>
<script src="../js/data/locale/auth.js"></script>
<script src="../js/data/locale/system.js"></script>
<script src="../../app/js/easyversion/ajax.js"></script>
<script src="../../app/js/easyversion/basicdatahelper.js"></script>
<script src="../../app/js/easyversion/web2frameadapter.js"></script>
<script src="../../app/js/easyversion/elementmore.js"></script>
<script src="../../app/js/easyversion/list.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanel.js"></script>
<script src="../../app/js/source/wcmlib/Observable.js"></script>
<script src="../../app/js/source/wcmlib/pagecontext/PageNav.js"></script>

<script src="../../app/js/source/wcmlib/dragdrop/dd.js"></script>
<script src="../../app/js/source/wcmlib/dragdrop/wcm-dd.js"></script>
<script src="../../app/js/source/wcmlib/Component.js"></script>
<script src="../../app/js/source/wcmlib/dialog/Dialog.js"></script>
<script src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></script>
<script src="auth.js"></script>
<script language="javascript">
<!--
	if(m_nObjType == OBJ_DOCUMENT) document.getElementById('list_navigator').style.right = '10px';
//-->
</script>


<script>
<%
	//初始化权限结构信息
	writeRightDefsScript(out, nObjType, request.getParameter("IsVirtual"));
%>

<%
	//初始化权限数据
	Right currRight = null;
	CMSObj oOperator = null;
	boolean ifBreak = false;
	if(sObjIdArray.length <= 1){
		for(int i=0; i<currRights.size(); i++){
			currRight = (Right)currRights.getAt(i);
			if(currRight == null)continue;

			oOperator = currRight.getOperator();
			if(oOperator == null)continue;
%>
wcm.Rights.add({
	'OPRTYPE' : <%=currRight.getOperatorType()%>,
	'OPRID' : <%=currRight.getOperatorId()%>, 
	'NAME' : "<%=CMyString.filterForJs(getOperatorDesc(oOperator))%>", 
	'RIGHTVALUE' : "<%=currRight.getValue()%>", 
	'RIGHTID' : <%=currRight.getId()%>
});	
<%
		}
	}
%>
</script>
</BODY>
</HTML>
<%!		
	public String getOperatorDesc(CMSObj _rightHost)throws WCMException{
		switch(_rightHost.getWCMType()){
			case User.OBJ_TYPE:
				return ((User)_rightHost).getName() ;
			case Group.OBJ_TYPE:
				return ((Group)_rightHost).getName();
			case Role.OBJ_TYPE:
				return ((Role)_rightHost).getName();
			default:
				return "";
		}
	}

	public String getStatusIndexes(User _loginUser)throws WCMException{
		Statuses currStatuses = Statuses.openWCMObjs(_loginUser, null);
        Status currStatus=null;
        
        //动态获取Status的权限值
       String rightIndexes="";
        for (int i = 0; i < currStatuses.size(); i++) {
            currStatus = (Status)currStatuses.getAt(i);
    		if(currStatus==null || currStatus.getRightIndex()==0 || !currStatus.isUsed())
    			continue;
    		else rightIndexes += (rightIndexes==""?"":",")+currStatus.getRightIndex();
            
        }
		return rightIndexes;
	}

	private void writeRightDefsScript(JspWriter out, int nObjType, String isVirtual) throws Exception {
        XMLConfigServer oXMLConfigServer = XMLConfigServer.getInstance();
        List listOperation = oXMLConfigServer
                .getConfigObjects(OperationConfig.class);
        for (int i = 0, nSize = listOperation.size(); i < nSize; i++) {
            OperationConfig operationConfig = (OperationConfig) listOperation
                    .get(i);
            if (operationConfig == null)
                continue;
			//filter start ................
			if(nObjType == WCMTypes.OBJ_DOCUMENT){
				if(operationConfig.getName().equalsIgnoreCase("Document_outline")
						|| operationConfig.getName().equalsIgnoreCase("Document_add")){
					continue;
				}
			}
			if(isVirtual != null && isVirtual.equals("1")){
				if(nObjType == WCMTypes.OBJ_CHANNEL){
					
				}
			}
			//filter end ...................
			String sDepends = getDepends(operationConfig), sSimilar = getSimilar(operationConfig);
            
			out.println("wcm.RightDef.add({"
				+ "\n\tIndex:" + operationConfig.getIndex()
				+ ",\n\tName:'" + CMyString.filterForJs(operationConfig.getDispName())
				+ "',\n\tDesc:'" + CMyString.filterForJs(CMyString.showNull(operationConfig.getDesc()))
				+ "',\n\tType:'" + CMyString.showNull(operationConfig.getType())
				+ "',\n\tDepends:" + sDepends
				+ ",\n\tSimilars:" + sSimilar
			+"\n});");
        }
    }
    
    private String getSimilar(OperationConfig operationConfig){
        int[] pResult = RightConfigServer.getInstance().getSimilarIndexs(
                operationConfig.getIndex());
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
        OperationRelatedConfig includes = operationConfig
                .getDepends();
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
%>