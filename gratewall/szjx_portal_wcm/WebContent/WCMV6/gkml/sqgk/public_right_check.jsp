<%@ page import="com.trs.infra.persistent.db.DBManager" %>

<%	
	String sHAS_SQGK_RIGHT = (String)session.getAttribute("HAS_SQGK_RIGHT");	
	if(sHAS_SQGK_RIGHT == null){
		if(loginUser.isAdministrator()){
			session.setAttribute("HAS_SQGK_RIGHT", "1");
		}else{
			StringBuffer sb = new StringBuffer("Select RoleId from WCMRoleUser where userId = ");
			sb.append(loginUser.getId());
			sb.append(" and RoleId in (Select RoleId from WCMRole where RoleName = 'SQGK')");
			int result = DBManager.getDBManager().sqlExecuteIntQuery(sb.toString());
			sHAS_SQGK_RIGHT = result > 0 ? "1" : "0";
			session.setAttribute("HAS_SQGK_RIGHT", sHAS_SQGK_RIGHT);
			}
	}	
	if("0".equals(sHAS_SQGK_RIGHT)){
		throw new Exception("没有访问依申请公开的权限!");
	}
%>