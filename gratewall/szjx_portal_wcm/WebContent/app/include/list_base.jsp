<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.auth.persistent.RightValue" %>
<%@ page import="com.trs.cms.content.CMSObj" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.webframework.FrameworkConstants" %>
<%@ page import="com.trs.webframework.context.MethodContext" %>
<%@ page import="com.trs.DreamFactory" %>
<%@ page import="com.trs.cms.auth.domain.IObjectMemberMgr" %>
<%@ page import="com.trs.cms.content.CMSObj" %>
<%@ page import="com.trs.components.wcm.content.persistent.BaseChannel" %>
<%
//2. 获取业务数据
	MethodContext oMethodContext = (MethodContext)request.getAttribute(FrameworkConstants.ATTR_NAME_METHODCONTEXT);
	CPager currPager = (CPager)request.getAttribute(FrameworkConstants.ATTR_NAME_PAGER);

//3. 构造分页参数，这段逻辑应该都可以放到服务器端	TODO
	String strSelectedIds = "", sCurrOrderBy = "";
	String sOrigSelectedIds = CMyString.showNull(oMethodContext.getValue("SelectIds"));
	sOrigSelectedIds = CMyString.transDisplay(CMyString.filterForJs(sOrigSelectedIds));
	if (oMethodContext != null) {
		strSelectedIds = ","+sOrigSelectedIds+",";
		sCurrOrderBy = CMyString.showNull(oMethodContext.getValue("OrderBy"));
	}	
	out.clear();
%>
<%!
	public String getOrderFlag(String field, String currOrderBy){
		if(CMyString.isEmpty(currOrderBy))return "";
		String[] orderBy = currOrderBy.toLowerCase().split(" ");
		field = field.toLowerCase();
		if(!orderBy[0].equals(field))return "";
		return "&nbsp;" + ("asc".equals(CMyString.showEmpty(orderBy[1], "asc"))?"↑":"↓");
	}
	private String getPageAttributes(CPager _pager) {
		String sRetVal = "";
		sRetVal += "Num:"+String.valueOf(_pager.getItemCount());
		if (_pager.getPageSize() > 0){
			sRetVal += ",PageSize:"+String.valueOf(_pager.getPageSize());
			sRetVal += ",PageCount:"+String.valueOf(_pager.getPageCount());
			sRetVal += ",CurrPageIndex:"+String.valueOf(_pager.getCurrentPageIndex());
		}
		return sRetVal;
	}
    private RightValue getRightValue(CMSObj obj, User loginUser) throws WCMException {
		try {
			if (loginUser.isAdministrator()) {
				return RightValue.getAdministratorRightValue();
			}
			RightValue oRightValue;
			if(obj instanceof BaseChannel){
				IObjectMemberMgr oObjectMemberMgr = (IObjectMemberMgr) DreamFactory.createObjectById("IObjectMemberMgr");
				if(oObjectMemberMgr.canOperate(loginUser, obj.getWCMType(), obj.getId())){
					oRightValue = AuthServer.getRightValue(obj, loginUser);
				}else{
					oRightValue = new RightValue();
				}
			}else{
				oRightValue = AuthServer.getRightValue(obj, loginUser);
			}
			return oRightValue;
		} catch (Exception e) {
			throw new WCMException(CMyString.format(LocaleServer.getString("list_base.construct.failed","构造[{0}]权限信息失败!"),new Object[]{obj}), e);
		}
	}
	private boolean hasRight(User _currUser, CMSObj _objCurrent,int _nRightIndex) throws WCMException{
		return AuthServer.hasRight(_currUser,_objCurrent,_nRightIndex);
	}
	private String convertDateTimeValueToString(MethodContext _methodContext, CMyDateTime _dtValue) {
		String sDateTimeFormat = CMyDateTime.DEF_DATETIME_FORMAT_PRG;
		if (_methodContext != null) {
			sDateTimeFormat = _methodContext.getValue("DateTimeFormat");
			if (sDateTimeFormat == null) {
				sDateTimeFormat = CMyDateTime.DEF_DATETIME_FORMAT_PRG;
			}
		}
		String sDtValue = _dtValue.toString(sDateTimeFormat);
		return CMyString.showNull(sDtValue);
	}
%>