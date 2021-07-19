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
 *		wenyh@2006-03-17,如果是站点/频道判断是否已删除
 *		caohui@2006-9-1 减少不必要的依赖，改为Host自己Validate
 *		wenyh@2009-12-22 更加WCMRightTypes的引用方式
 *
 *  Parameters:
 *		see right_set.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="com.trs.cms.auth.persistent.Rights" %>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.domain.IRightHost" %>
<%@ page import="com.trs.cms.auth.domain.IRightMgr" %>
<%@ page import="com.trs.cms.auth.domain.RightHostFactory" %>
<%@ page import="com.trs.cms.auth.persistent.Group" %>
<%@ page import="com.trs.cms.auth.persistent.Right" %>
<%@ page import="com.trs.cms.auth.persistent.RightDef" %>
<%@ page import="com.trs.cms.auth.persistent.RightDefs" %>
<%@ page import="com.trs.cms.auth.persistent.Role" %>
<%@ page import="com.trs.cms.content.CMSObj" %>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.components.wcm.resource.Statuses" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%
//4.初始化(获取数据)
	int nObjType = currRequestHelper.getInt("ObjType", 0);
	int nObjId = currRequestHelper.getInt("ObjId", 0);
	
	if(nObjId <= 0)
		throw new WCMException("新建模式不允许设置权限！Type:["+nObjType+"] Id:["+nObjId+"]");

	
	IRightMgr currMgr = (IRightMgr) DreamFactory.createObjectById("IRightMgr");
	Rights	currRights = null;
	
	boolean bHasChildren = false;
	String sHostInfo = null;
	if(nObjType != 1){
		IRightHost rightHost = RightHostFactory.makeRightHostFrom(nObjType, nObjId);
		if(rightHost == null)
			throw new WCMException("没有找到指定的对象！Type:["+nObjType+"] Id:["+nObjId+"]");

		//权限校验
		rightHost.validateCanDoSetRight(loginUser);

		IRightHost[] children = rightHost.getChildren();
		bHasChildren = children != null && children.length>0;
			
		currRights = currMgr.getRights(rightHost.getSubstance());
		sHostInfo = rightHost.getInfo();
	}else{
		//权限校验
		if(!loginUser.isAdministrator())
			throw new WCMException("当前用户["+loginUser.getName()+"]没有系统属性权限进行设置！");

		//业务代码
		currRights = currMgr.getRights(1,nObjId);

		sHostInfo = "系统";
	}

	WCMFilter filter = new WCMFilter("", "", "RIGHTINDEX");
	RightDefs currRightDefs = RightDefs.openWCMObjs(loginUser, filter);

//7.结束
	out.clear();
%>
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 <%=LocaleServer.getString("auth.label.rightset", "权限设置")%>::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<base target="_self">
<LINK href="../style/style.css" rel="stylesheet" type="text/css">


<script src="../js/CTRSHTMLElement.js"></script>
<script src="../js/CTRSHTMLTr.js"></script>  

<script src="../js/CTRSSimpleTab.js"></script>

<script src="../js/CTRSArray.js"></script>

<SCRIPT LANGUAGE="JavaScript" src="../js/CWCMObj.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/CWCMObjHelper.js"></SCRIPT>

<SCRIPT LANGUAGE="JavaScript" src="../js/CTRSButton.js"></script>  

<script src="../js/CTRSHashtable.js"></script>
<script src="../js/CTRSRequestParam.js"></script>
<script src="../js/CTRSAction.js"></script>

<script src="../js/CWCMRightHelper.js"></script>  

<script>
var m_nObjType = <%=nObjType%>;
var m_nObjId = <%=nObjId%>;
var OBJ_WEBSITE = 103;
var OBJ_CHANNEL = 101;
var OBJ_DOCUMENT = 605;
var OBJ_SYSATTRIBUTE = 1;
var m_bExtended=<%=(nObjId==2)?true:false%>;

<%
//初始化权限索引
	RightDef currRightDef = null;
	for(int i=0; i<currRightDefs.size(); i++){
		currRightDef = (RightDef)currRightDefs.getAt(i);
%>
	WCMRightHelper.addRightDef(<%=currRightDef.getIndex()%>, "<%=CMyString.filterForJs(currRightDef.getName())%>", "<%=CMyString.filterForJs(CMyString.showNull(currRightDef.getDesc()))%>");
<%
	}
%>

<%
//初始化权限数据
	Right currRight = null;
	CMSObj oOperator = null;
	for(int i=0; i<currRights.size(); i++){
		currRight = (Right)currRights.getAt(i);
		if(currRight == null)continue;

		oOperator = currRight.getOperator();
		if(oOperator == null)continue;

		
%>
	WCMRightHelper.addRight(<%=currRight.getOperatorType()%>, <%=currRight.getOperatorId()%>, "<%=CMyString.filterForJs(getOperatorDesc(oOperator))%>", "<%=currRight.getValue()%>", <%=currRight.getId()%>);	
<%
	}
%>



	function drawSiteRightInfo(){
		WCMRightHelper.draw(<%=WCMRightTypes.SITE_MIN_INDEX%>, <%=WCMRightTypes.SITE_MAX_INDEX%>, document.all("id_TRSSimpleTab0"));
		WCMRightHelper.draw(<%=WCMRightTypes.CHNL_MIN_INDEX%>, <%=WCMRightTypes.CHNL_MAX_INDEX%>, document.all("id_TRSSimpleTab1"));
		WCMRightHelper.draw(<%=WCMRightTypes.TEMPLATE_MIN_INDEX %>, <%=WCMRightTypes.TEMPLATE_MAX_INDEX %>, document.all("id_TRSSimpleTab2"));
		WCMRightHelper.draw(<%=WCMRightTypes.DOC_MIN_INDEX %>, <%=WCMRightTypes.DOC_MAX_INDEX %>, document.all("id_TRSSimpleTab3"),"<%=getStatusIndexes(loginUser)%>");
	}

	function drawChannelRightInfo(){
		WCMRightHelper.draw(<%=WCMRightTypes.CHNL_MIN_INDEX%>, <%=WCMRightTypes.CHNL_MAX_INDEX%>, document.all("id_TRSSimpleTab0"));
		WCMRightHelper.draw(<%=WCMRightTypes.TEMPLATE_MIN_INDEX %>, <%=WCMRightTypes.TEMPLATE_MAX_INDEX %>, document.all("id_TRSSimpleTab1"));
		WCMRightHelper.draw(<%=WCMRightTypes.DOC_MIN_INDEX %>, <%=WCMRightTypes.DOC_MAX_INDEX %>, document.all("id_TRSSimpleTab2"),"<%=getStatusIndexes(loginUser)%>");
	}

	function drawDocumentRightInfo(){
		WCMRightHelper.draw(<%=WCMRightTypes.DOC_EDIT %>, <%=WCMRightTypes.DOC_MAX_INDEX %>, document.all("id_TRSSimpleTab0"),"<%=getStatusIndexes(loginUser)%>" );
	}

	function drawSysAttributeRightInfo(){
		WCMRightHelper.draw(<%=WCMRightTypes.getSysMinIndex()%>, <%=WCMRightTypes.getSysMaxIndex() %>,document.all("id_TRSSimpleTab0"));
	}

	function drawExtendedRightInfo(){
		WCMRightHelper.draw(<%=WCMRightTypes.getExtMinIndex() %>, <%=WCMRightTypes.getExtMaxIndex() %>,document.all("id_TRSSimpleTab0"));
	}

	function init(){
		if(m_nObjType == OBJ_WEBSITE){
			drawSiteRightInfo();
		}else if(m_nObjType == OBJ_CHANNEL){
			drawChannelRightInfo();
		}else if(m_nObjType == OBJ_DOCUMENT){
			drawDocumentRightInfo();
		}else if(m_nObjType == OBJ_SYSATTRIBUTE){
			if(!m_bExtended)
				drawSysAttributeRightInfo();
			else drawExtendedRightInfo();
		}	
	}

	window.onload = init;
</script>

<script>
//覆盖删除的通知函数，以便刷新页面
function CWCMRightHelper_notifyRemove(_elEvent){
	init();
}

function addOperator(_nType, sReturnValue){
	if(sReturnValue == null || sReturnValue.length == 0)return;

	var arDeleteIndex = WCMRightHelper.getIndexArray(_nType);
	var arDeleteId = WCMRightHelper.getIdArray(_nType);

	var arSelectId = sReturnValue[0];
	var arSelectName = sReturnValue[1]; 
	
	var bRemove = false;

	for(var i= arDeleteId.length - 1; i>=0; i--){
		bRemove = true;
		for(var j=0; j<arSelectId.length;j++){
			if(arDeleteId[i] == arSelectId[j]){
				arSelectId[j]=-1;
				bRemove = false;
			}
		}
		if(bRemove) WCMRightHelper.removeAt(arDeleteIndex[i]);
		
	}
	
	//遍历添加
	for(var i=0; i<arSelectId.length; i++){
		if (arSelectId[i] == -1) continue;
		WCMRightHelper.putOperator(_nType, arSelectId[i], arSelectName[i]);
	}
	if(!WCMRightHelper.bUpdate)return;

//刷新页面	
	init();
}

function addUser(){
	var _nType = TYPE_WCMOBJ_USER;
	//获取用户ID
	var nWidth = 700, nHeight = 500, oArgs = [WCMRightHelper.getIdArray(_nType), WCMRightHelper.getNameArray(_nType)];
	var oTRSAction = new CTRSAction(getOperatorSelectURL(_nType));
	oTRSAction.setParameter("TransferName", 1);
	var sReturnValue = oTRSAction.doDialogAction(nWidth, nHeight, oArgs);
	addOperator(_nType, sReturnValue);
}

function addGroup(){
	var _nType = TYPE_WCMOBJ_GROUP;
	//获取用户ID
	var nWidth = 700, nHeight = 500;
	var oTRSAction = new CTRSAction(getOperatorSelectURL(_nType));
	oTRSAction.setParameter("TransferName", 1);
	oTRSAction.setParameter("GroupIds", WCMRightHelper.getIdArray(_nType));
	oTRSAction.setParameter("TreeType", 1);
	var sReturnValue = oTRSAction.doDialogAction(nWidth, nHeight);
	addOperator(_nType, sReturnValue);
}

function addRole(){
	var _nType = TYPE_WCMOBJ_ROLE;
	//获取用户ID
	var nWidth = 700, nHeight = 500;
	var oTRSAction = new CTRSAction(getOperatorSelectURL(_nType));
	oTRSAction.setParameter("TransferName", 1);
	oTRSAction.setParameter("RoleIds", WCMRightHelper.getIdArray(_nType));
	var sReturnValue = oTRSAction.doDialogAction(nWidth, nHeight);
	addOperator(_nType, sReturnValue);
}

function getOperatorSelectURL(_nType){
	switch(_nType){
		case TYPE_WCMOBJ_USER:
			return "../auth/user_select_index.jsp";
		case TYPE_WCMOBJ_GROUP:
			return "../auth/group_select_index.jsp";
		case TYPE_WCMOBJ_ROLE:
			return "../auth/role_select_index.jsp";
		default:
			return "../auth/user_select_index.jsp";
	}
}

function onOK(){
	var frmAction = document.frmAction;
	if(frmAction == null){
		CTRSAction_alert("没有定义");
	}
	frmAction.RightsXML.value = WCMRightHelper.toXMLInfo();
	var oResetChildren = document.getElementById("resetChildren");
	if(oResetChildren != null)
		frmAction.ResetChildrenRights.value = (oResetChildren.checked ? "1" : "0");
	
	frmAction.submit();
}

</script>
</HEAD>

<BODY>
<form name="frmAction" method="post" action="right_set_dowith.jsp">
	<INPUT TYPE="hidden" name="ObjType" value="<%=nObjType%>">
	<INPUT TYPE="hidden" name="ObjId" value="<%=nObjId%>">
	<INPUT TYPE="hidden" name="ResetChildrenRights" value="0">
	<INPUT TYPE="hidden" name="RightsXML" value="">
</form>

<!--~== Dialog Head TABLE ==~-->
	<script src="../js/CWCMDialogHead.js"></script>
	<script>WCMDialogHead.draw("设置[<%=sHostInfo%>]权限");</script>
<!--~- END Dialog Head TABLE -~-->

<TABLE width="100%" height="90%" border="0" cellpadding="0" cellspacing="0">
<!--~--- ROW5 ---~-->
<TR>
  <TD align="center" valign="top" class="tanchu_content_td">
  <!--~== TABLE4 ==~-->
  <TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
  <!--~--- ROW6 ---~-->
  <TR>
    <TD>
		
    </TD>
  </TR>
  <!--~- END ROW6 -~-->
  <!--~-- ROW10 --~-->
  <TR>
    <TD>
		<!--~== TABLE1 ==~-->
		<TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
		<!--~--- ROW1 ---~-->
		<TR>
		  <TD height="25">
				<!--开始标签列表-->
				<script>					
					TRSSimpleTab.nCurrIndex = 0;
					
					if(m_nObjType !=1){

						if(m_nObjType == OBJ_WEBSITE){
							TRSSimpleTab.addItem("<%=LocaleServer.getString("auth.label.operatesite", "站点类操作权限")%>");
						}
						
						if(m_nObjType == OBJ_WEBSITE || m_nObjType == OBJ_CHANNEL){
							TRSSimpleTab.addItem("<%=LocaleServer.getString("auth.label.operatechannel", "频道类操作权限")%>");
							TRSSimpleTab.addItem("<%=LocaleServer.getString("auth.label.operatetemplate", "模板类操作权限")%>");
						}
						
						
						TRSSimpleTab.addItem("<%=LocaleServer.getString("auth.label.operatedocument", "文档类操作权限")%>");	
					}else{

						TRSSimpleTab.addItem("<%=LocaleServer.getString("auth.label.operatesysattr", "系统属性类操作权限")%>");
					}

					TRSSimpleTab.draw();
				</script>
				<!--结束标签列表-->	
				
		  </TD>
		</TR>
		<!--~- END ROW1 -~-->
		<TR>
		  <TD  bgcolor="#FFFFFF" style="BORDER-left: #a6a6a6 1px solid; BORDER-right: #a6a6a6 1px solid; BORDER-bottom: #a6a6a6 1px solid" align=left valign="middle">
			<script>
				var oButtons = new CTRSButtons("bt_table_noborder");

				oButtons.addTRSButton("<%=LocaleServer.getString("auth.button.adduser", "添加用户")%>", "addUser();", "../images/icon_user.gif","<%=LocaleServer.getString("auth.tip.adduser", "添加用户")%>");
				oButtons.addTRSButton("<%=LocaleServer.getString("auth.button.addgroup", "添加用户组")%>", "addGroup();", "../images/icon_group.gif","<%=LocaleServer.getString("auth.tip.addgroup", "添加用户组")%>");
				oButtons.addTRSButton("<%=LocaleServer.getString("auth.button.addrole", "添加角色")%>", "addRole();", "../images/icon_role.gif","<%=LocaleServer.getString("auth.tip.addrole", "添加角色")%>");

				oButtons.addTRSButton("<%=LocaleServer.getString("auth.button.dropall", "全部删除")%>", "WCMRightHelper.removeAll();", "../images/button_delete.gif","<%=LocaleServer.getString("auth.tip.dropall", "删除全部设置")%>");

				//oButtons.addTRSButton("测试Put", "WCMRightHelper.putOperator(TYPE_WCMOBJ_USER, 23);init();", "../images/button_default.gif","测试Put");
				//oButtons.addTRSButton("测试GetIdArray", "CTRSAction_alert(WCMRightHelper.getIdArray(TYPE_WCMOBJ_USER));", "../images/button_default.gif","测试GetIdArray");
				//oButtons.addTRSButton("测试GetNameArray", "CTRSAction_alert(WCMRightHelper.getNameArray(TYPE_WCMOBJ_USER));", "../images/button_default.gif","测试GetNameArray");
				//oButtons.addTRSButton("测试ToXML", "CTRSAction_alert(WCMRightHelper.toXMLInfo());", "../images/button_default.gif","测试ToXML");

	
				oButtons.draw();
			
			</script>

			<%
				if(bHasChildren){
			%>
					<input type="checkbox" name="resetChildren" id="resetChildren" value=1>重新设置子对象的权限
			<%
				}	
			%>
			</TD>
		</TR>
		<!--~-- ROW10 --~-->
		<TR>
		  <TD  bgcolor="#FFFFFF" style="BORDER-left: #a6a6a6 1px solid; BORDER-right: #a6a6a6 1px solid; BORDER-bottom: #a6a6a6 1px solid" align=center valign="top">
			
			<BR>		  
				<div id="id_TRSSimpleTab0" style="display:inline;">
					
				</div>				
				<div id="id_TRSSimpleTab1" style="display:none">
					
				</div>
				<div id="id_TRSSimpleTab2" style="display:none">
					
				</div>			
				<div id="id_TRSSimpleTab3" style="display:none">
					
				</div>			
						
				<BR>
		  </TD>
		</TR>
		<!--~ END ROW10 ~-->
		</TABLE>
		<!--~ END TABLE1 ~-->
		
    </TD>
  </TR>
  <!--~ END ROW10 ~-->
  <!--~-- ROW18 --~-->
  <TR>
    <TD align=center height="10">&nbsp;</TD>
  </TR>
  <TR>
    <TD align=center>
		<SCRIPT SRC="../js/CTRSButton.js"></SCRIPT>
		<script>
			//定义一个TYPE_ROMANTIC_BUTTON按钮
			var oTRSButtons = new CTRSButtons();
			
			oTRSButtons.cellSpacing = "0";
			oTRSButtons.nType = TYPE_ROMANTIC_BUTTON;

			oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.confirm", "确认")%>", "onOK()");
			oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.cancel", "取消")%>", "window.close();");
			
			oTRSButtons.draw();
		</script>
    </TD>
  </TR>
  <!--~ END ROW18 ~-->
  </TABLE>
  <!--~ END TABLE4 ~-->
  </TD>
</TR>
<!--~- END ROW5 -~-->
</TABLE>
<!--~ END TABLE1 ~-->
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
%>