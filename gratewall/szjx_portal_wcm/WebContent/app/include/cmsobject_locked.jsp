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

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>

<%@ page import="com.trs.DreamFactory"%>
<%@ page import="com.trs.cms.auth.persistent.User"%>
<%@ page import="com.trs.cms.content.LockerMgr"%>
<%@ page import="com.trs.cms.content.Locker"%>
<%
try{
%>
<%@include file="../../include/public_server.jsp"%>

<%
	int nObjId = Integer.parseInt(request.getParameter("ObjId"));
	int nObjType = Integer.parseInt(request.getParameter("ObjType"));
	boolean zLockAction = "true".equals(request.getParameter("ActionFlag"));

	if(zLockAction){
		out.println(lockObj(loginUser,nObjId,nObjType));
	}else{
		out.println(unlockObj(loginUser,nObjId,nObjType));
	}

	if(true){
		return;//忽略后面可能的无意义输出
	}
%>
<%
}catch(Throwable tx){
	out.clear();
	out.println(notLogin());
}
%>
<%!
	private String notLogin(){
		return "LockerUtil.notLogin();";
	}
	private String lockObj(User _currUser,int _nObjId,int _nObjType){
		StringBuffer buff = new StringBuffer(64);
		buff.append("LockerUtil.lockCallback({");
		buff.append("Result:");
		if(_currUser == null){
			buff.append("'false'");
			buff.append(",");
			buff.append("Message:");
			buff.append("'").append(LocaleServer.getString("lock.label.canotLock", "不能锁定对象")).append("[User=null]!'");
			buff.append("});");
			return buff.toString();
		}

		LockerMgr lockerMgr = (LockerMgr) DreamFactory.createObjectById("LockerMgr");
		Locker locker = lockerMgr.getLocker(_nObjType, _nObjId, true);
		if(locker == null || locker.isLocked() && !locker.lockUserIs(_currUser)){
			buff.append("'false'");
			buff.append(",");
			buff.append("Message:");
			buff.append("'").append(LocaleServer.getString("lock.label.objHas", "对象已被"));
			if(locker == null){
				buff.append("System");//wenyh@2009-10-27 添加为空判断(避免NullPointer异常)
			}else{
				buff.append(locker.getLockUser());
			}
			buff.append(LocaleServer.getString("lock.label.lock", "锁定")).append(",");
			buff.append(LocaleServer.getString("lock.label.canotLockTwice", "在锁定被解除前,您不能再锁定对象!")).append("'");
			buff.append("});");
			return buff.toString();
		}

		locker.lock(_currUser);
		buff.append("'true'");
		buff.append("});");
		return buff.toString();
	}

	private String unlockObj(User _currUser,int _nObjId,int _nObjType){
		StringBuffer buff = new StringBuffer(64);
		buff.append("LockerUtil.unlockCallback({");
		buff.append("Result:");
		if(_currUser == null){
			buff.append("'false'");
			buff.append(",");
			buff.append("Message:");
			buff.append("'").append(LocaleServer.getString("lock.label.canotLock", "不能锁定对象")).append("[User=null]!'");
			buff.append("});");
			return buff.toString();
		}

		LockerMgr lockerMgr = (LockerMgr) DreamFactory.createObjectById("LockerMgr");
		Locker locker = lockerMgr.getLocker(_nObjType, _nObjId, false);
		if(locker == null || locker.lockUserIs(_currUser)){
			lockerMgr.removeLocker(_nObjType,_nObjId);
			buff.append("'true'");			
			buff.append("});");
			return buff.toString();
		}
		
		buff.append("'false'");
		buff.append(",");
		buff.append("Message:");
		buff.append("'").append(LocaleServer.getString("lock.label.objHas", "对象已被")).append(locker.getLockUser()).append(LocaleServer.getString("lock.label.lock", "锁定")).append(",");
		buff.append(LocaleServer.getString("lock.label.canotLockTwice", "在锁定被解除前,您不能再锁定对象!")).append("'");
		buff.append("});");
		return buff.toString();
	}
%>