<%
/** Title:			group_tree.jsp
 *  Description:
 *		标准WCM5.2 页面，用于“用户组导航结构”。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WCM
 *  Created:		2004-12-19 16:12
 *  Vesion:			1.0
 *  Last EditTime:	2004-12-19/2004-12-19
 *	Update Logs:
 *		CH@2004-12-19 created the file 
 *
 *  Parameters:
 *		see group_tree.jsp
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.auth.persistent.Group" %>
<%@ page import="com.trs.cms.auth.persistent.Groups" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.DebugTimer" %>
<%@ page import="com.trs.presentation.common.PageViewConstants" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.service.IGroupService" %>
<!------- WCM IMPORTS END ------------>
<%!boolean IS_DEBUG = false;
  IGroupService currGroupService = (IGroupService)DreamFactory.createObjectById("IGroupService");
%>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%
    DebugTimer aTimer = new DebugTimer();
	aTimer.start();
%>

<%
//TODO 权限校验

//4.初始化（获取数据）	
	int nOperType		= currRequestHelper.getInt("OperType", PageViewConstants.TREE_OPERATION_DOCUMENT);
	int nTreeType		= currRequestHelper.getInt("TreeType", PageViewConstants.TREE_TYPE_NORMAL);
	String sObjTreeName = CMyString.filterForJs(currRequestHelper.getString("ObjTreeName"));

	int	nGroupId		= currRequestHelper.getInt("GroupId", 0);			
	Group currGroup		= null;
	if(nGroupId>0){
		currGroup = Group.findById(nGroupId);
		if(currGroup == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("object.not.found_a", "没有找到指定ID为[{0}]的对象!"), new int[]{nGroupId}));
		}
	}
	

	boolean isShowAll = currRequestHelper.getBoolean("ShowAll", false);

	boolean bFilterRight = currRequestHelper.getBoolean("FilterRight", false);
	

//5.权限校验

//6.业务代码
	WCMFilter filter = new WCMFilter("", "", "");
	
	IGroupService currGroupService = (IGroupService)DreamFactory.createObjectById("IGroupService");
	//Groups currGroups	= currGroupService.getChildren(Group);

//7.结束
	out.clear();
%>

<%
	StringBuffer sbDisabledGroupIds = new StringBuffer();
%>
	<%=CMyString.transDisplay(toTreeNavJavascript(currGroupService, currGroup, sObjTreeName,loginUser,currGroup.getId(), isShowAll, sbDisabledGroupIds, bFilterRight))%>
<%=CMyString.transDisplay(sObjTreeName)%>.arDisabledId = <%=CMyString.transDisplay(sObjTreeName)%>.arDisabledId.concat([<%=CMyString.filterForJs(sbDisabledGroupIds.toString())%>]);

<%
aTimer.stop();
if(IS_DEBUG)System.out.println(CMyString.format(LocaleServer.getString("group_tree.jsp.createtime", "构造用户[{0}]用户组树所用时间为[{1}]毫秒！"), new Object[]{loginUser.getName(),String.valueOf(aTimer.getTime())}));
%>

<%!
	public String toTreeNavJavascript(IGroupService _service,
			Group _parentGroup, String _sObjTreeName, User _loginUser, int _parentId, boolean _isShowAll, StringBuffer _sbDisabledGroupIds, boolean _bFilterRight)throws WCMException {
		Groups currGroups = _service.getChildren(_parentGroup);
		Group currGroup = null;
		String sResult = "";
		Groups userGroups = _loginUser.getGroups();
		Groups ancestorGroups = getGroups(_loginUser);		
		for (int i = 0; i < currGroups.size(); i++) {
			currGroup = (Group) currGroups.getAt(i);
			if (currGroup == null)	continue;
			boolean bHasRightView = true;
			//如果需要过滤权限，则判断是否有权限查看
			if(_bFilterRight){
				bHasRightView =  ancestorGroups.indexOf(currGroup)!=-1 || AuthServer.hasRight(_loginUser, currGroup);
			}
			if (_isShowAll || bHasRightView){
				sResult += _sObjTreeName + ".addItem(\"" + CMyString.filterForJs(currGroup.getName())
						+ "\", " + currGroup.getId() + ", null, getNodeHref("
						+ currGroup.getId() + ", nTreeType), \""+_parentId+"\", true);\n";
				if(_bFilterRight && !hasRight(_loginUser, currGroup, userGroups)){
					if(_sbDisabledGroupIds.length()>0){
						_sbDisabledGroupIds.append(",");
					}
					_sbDisabledGroupIds.append(currGroup.getId());
				}
			}			
		}
		return sResult;
	}

	
	private boolean hasRight(User _loginUser, Group currGroup, Groups _userGroups) throws WCMException{	 
		if(currGroup == null) return false;
		if(AuthServer.hasRight(_loginUser, currGroup))
			return true;
		if(_userGroups!=null && _userGroups.indexOf(currGroup)!=-1)
			return true;
		return false;
	}
	

	/**
	*获取当前用户_loginUser所在的用户组（包含用户组的祖先组）
	*/
	private Groups getGroups(User _loginUser)throws WCMException{
		Groups result = new Groups(_loginUser);
		Groups userGroups = _loginUser.getGroups();
		if(userGroups == null || userGroups.isEmpty()){
			return result;
		}

		Group currGroup = null;
		for(int i=0,size=userGroups.size();i<size;i++){
			currGroup = (Group) userGroups.getAt(i);
			while(currGroup != null){
				if(result.indexOf(currGroup.getId()) == -1){
					result.addElement(currGroup);
				}
				currGroup = currGroup.getParent();
			}
		}
		return result;
	}
%>