<%--
/** Title:			Contentextfield_addedit.jsp
 *  Description:
 *		扩展字段位置设置页面
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	errorPage="../include/error_for_dialog.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentExtField" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentExtFields" %>
<%@ page import="com.trs.components.wcm.content.domain.auth.DocumentAuthServer" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.infra.common.WCMTypes"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.persistent.db.DBManager" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)
	int nObjectId = currRequestHelper.getInt("ObjectId", 0);
	ContentExtField currContentExtField = ContentExtField.findById(nObjectId);
	if(currContentExtField == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("object.not.found", "没有找到指定的{1} [ID={0}]."), new String[]{String.valueOf(nObjectId),WCMTypes.getLowerObjName(ContentExtField.OBJ_TYPE)}));
	}

	int nChannelId = currRequestHelper.getInt("ChannelId", 0);
	Channel currChannel = Channel.findById(nChannelId);
	if(currChannel == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,CMyString.format(LocaleServer.getString("contentextfield_position_set_dowith.jsp.noidchnl","没有找到ID为[{0}]的栏目！"),new int[]{nChannelId}));
	}
	int nCurrExtOrder = currContentExtField.getPropertyAsInt("EXTORDER",0);
	int nNewExtOrder = currRequestHelper.getInt("DocOrder", 0);
//5.权限校验

//6.业务代码
	String sWhere = "objType = 101 and objid = " + nChannelId;
	WCMFilter filter = new WCMFilter("", sWhere, "extorder desc", "");
	ContentExtFields contentExtFields = ContentExtFields.openWCMObjs(ContextHelper.getLoginUser(),filter);
	int nSize = contentExtFields.size();
	int nExtOrder = contentExtFields.indexOf(nObjectId);
	System.out.println(nSize +"&&&&" + nExtOrder);
	//计算拖动模式和目标Id
	ContentExtField toContentExtField = null;
	int nTargetExtId = 0;
	int nPosition = 1;
	if(nNewExtOrder<0 || nNewExtOrder >= nSize){//移动到最后面
		nNewExtOrder = nSize;
		nPosition = 0;
		nTargetExtId = contentExtFields.getIdAt(nNewExtOrder-1);
	}else if( nNewExtOrder == 0 ){//移动到最前面
		nTargetExtId  = contentExtFields.getIdAt(0);
	}
	else if( nNewExtOrder > 0 ){//指定位置
		nTargetExtId  = contentExtFields.getIdAt(nNewExtOrder-1);
		toContentExtField = ContentExtField.findById(nTargetExtId);
		int nToExtOrder = toContentExtField.getPropertyAsInt("EXTORDER",0);
		
		if(nCurrExtOrder > nToExtOrder){
			nPosition = 0;
		}
	}
	System.out.println(nTargetExtId + "****" + nObjectId);
	if(nTargetExtId != nObjectId){
		//判断是否能够改变扩展字段顺序
		if (!AuthServer.hasRight(loginUser, currContentExtField ,WCMRightTypes.CHNL_EXTEND)) {
			throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT,CMyString.format(LocaleServer.getString("contentextfield_position_set_dowith.jsp.label.norights", "没有权限在当前栏目[{0}][ID={1}]下改变扩展字段顺序！"), new String[]{currChannel.getName(), String.valueOf(currChannel.getId())}));
		}

		// 判断是否需要移动
        int nNewOrder = nCurrExtOrder;
		toContentExtField = ContentExtField.findById(nTargetExtId);
		if(toContentExtField == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("contentextfield_position_set_dowith.jsp.noidextendfield","没有找到ID为[{0}]的扩展字段！"),new int[]{nTargetExtId}));
		}
		int nToExtOrder = toContentExtField.getPropertyAsInt("EXTORDER",0);
		System.out.println(nToExtOrder);
        int nOldOffset = nCurrExtOrder - nToExtOrder;
        int nMoveBeginAt = 0; // 移动区域的开始位置
        int nMoveEndAt = 0; // 移动区域的结束位置
        int nMoveDirection = 0; // 移动方向，1为区域前移，-1为区域后移
        switch (nPosition) {
        case 1:
            // 本来就在前一个位置
            if (nOldOffset == 1) {
                return;
            }
            // 之前的某几个位置
            else if (nOldOffset > 0) {
                nMoveBeginAt = nToExtOrder + 1;
                nMoveEndAt = nCurrExtOrder - 1;
                nNewOrder = nMoveBeginAt;
                nMoveDirection = 1;
            }
            // 之后的某几个位置
            else if (nOldOffset < 0) {
                nMoveBeginAt = nCurrExtOrder + 1;
                nMoveEndAt = nToExtOrder;
                nNewOrder = nMoveEndAt;
                nMoveDirection = -1;
            }
            break;
        case 0:
            // 本来就在后一个位置
            if (nOldOffset == -1) {
                return;
            }
            // 之前的某几个位置
            else if (nOldOffset > 0) {
                nMoveBeginAt = nToExtOrder;
                nMoveEndAt = nCurrExtOrder - 1;
                nNewOrder = nMoveBeginAt;
                nMoveDirection = 1;
            }
            // 之后的某几个位置
            else if (nOldOffset < 0) {
                nMoveBeginAt = nCurrExtOrder + 1;
                nMoveEndAt = nToExtOrder - 1;
                nNewOrder = nMoveEndAt;
                nMoveDirection = -1;
            }
            break;
        }

        // 2.移动区域
        final String sRegionWhere = "ExtOrder>=? AND ExtOrder <=? and ObjType = 101 and ObjId=?";
        String sMoveSql = "UPDATE wcmcontentextfield SET ExtOrder = ExtOrder+("
                + nMoveDirection + ") WHERE " + sRegionWhere;
        DBManager.getDBManager().sqlExecuteUpdate(new String[] { sMoveSql },
                new int[] { nMoveBeginAt, nMoveEndAt, nChannelId });

        // 3.改变本身的order
        currContentExtField.setProperty("EXTORDER", nNewOrder);
        currContentExtField.save();
	}
	
//7.结束
	out.clear();
%>