<%--
/*
 *	cmsobject_locked.jsp
 *		对象锁定/解锁服务
 *
 *	Copyright	:	北京拓尔思(TRS)信息技术有限公司
 *	WebSite		:	www.trs.com.cn
 *
 *	History			Who			What
 *	2006-11-23		wenyh		created.
 *  2007-01-11      wenyh		修正解锁返回值错误的问题,并修改了一下提示信息
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" errorPage="../../include/error.jsp"%>

<%@ page import="com.trs.DreamFactory"%>
<%@ page import="com.trs.cms.auth.persistent.User"%>
<%@ page import="com.trs.cms.content.LockerMgr"%>
<%@ page import="com.trs.cms.content.Locker"%>

<%@include file="../../include/public_server.jsp"%>

<%
	int nObjId = Integer.parseInt(request.getParameter("ObjId"));
	int nObjType = Integer.parseInt(request.getParameter("ObjType"));
	boolean zLockAction = "true".equals(request.getParameter("ActionFlag"));

	out.clear();

	if(zLockAction){
		out.print(lockObj(loginUser,nObjId,nObjType));
	}else{
		out.print(unlockObj(loginUser,nObjId,nObjType));
	}

	if(true){
		return;//忽略后面可能的无意义输出
	}
%>

<%!
	private String lockObj(User _currUser,int _nObjId,int _nObjType){
		StringBuffer buff = new StringBuffer(64);
		buff.append("{");
		buff.append("Result:");
		if(_currUser == null){
			buff.append("'false'");
			buff.append(",");
			buff.append("Message:");
			buff.append("'不能锁定对象[User=null]!'");
			buff.append("}");
			return buff.toString();
		}

		LockerMgr lockerMgr = (LockerMgr) DreamFactory.createObjectById("LockerMgr");
		Locker locker = lockerMgr.getLocker(_nObjType, _nObjId, true);
		if(locker.isLocked() && !locker.lockUserIs(_currUser)){
			buff.append("'false'");
			buff.append(",");
			buff.append("Message:");
			buff.append("'对象已被").append(locker.getLockUser()).append("锁定!");
			buff.append("在锁定被解除前,您不能再锁定对象!'");
			buff.append("}");
			return buff.toString();
		}

		locker.lock(_currUser);
		buff.append("'true'");
		buff.append("}");
		return buff.toString();
	}

	private String unlockObj(User _currUser,int _nObjId,int _nObjType){
		StringBuffer buff = new StringBuffer(64);
		buff.append("{");
		buff.append("Result:");
		if(_currUser == null){
			buff.append("'false'");
			buff.append(",");
			buff.append("Message:");
			buff.append("'不能锁定对象[User=null]!'");
			buff.append("}");
			return buff.toString();
		}

		LockerMgr lockerMgr = (LockerMgr) DreamFactory.createObjectById("LockerMgr");
		Locker locker = lockerMgr.getLocker(_nObjType, _nObjId, false);
		if(locker == null || locker.lockUserIs(_currUser)){
			lockerMgr.removeLocker(_nObjType,_nObjId);
			buff.append("'true'");			
			buff.append("}");
			return buff.toString();
		}
		
		buff.append("'false'");
		buff.append(",");
		buff.append("Message:");
		buff.append("'对象已被").append(locker.getLockUser()).append("锁定!");
		buff.append("您不能解除这个锁定!'");
		buff.append("}");
		return buff.toString();
	}
%>