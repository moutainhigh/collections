package com.gwssi.dw.aic.bj;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.genersoft.frame.base.database.DBException;
import com.genersoft.frame.base.database.DbUtils;

import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.context.vo.VoUser;
import cn.org.bjca.security.SecurityEngineDeal;

public class CheckUserNumber extends
	cn.gwssi.common.web.channel.plugin.CheckSession
{

	public static final String DB_CONFIG = "app";
	private static final String DB_CONNECT_TYPE = "dbConnectionType";
	
	private static String getConnectType(){
		return java.util.ResourceBundle.getBundle(DB_CONFIG).getString(DB_CONNECT_TYPE);
	}
	
	public static String dbType = getConnectType();
	
	public CheckUserNumber(){
		
	}
	
	/**
	 * 前校验(仅限登陆)
	 */
	public void beforeAction(String txnCode, HttpServletRequest request)
		throws TxnException
	{
		// 如果交易号为空或者不是登陆交易，就不校验了
		if (txnCode == null || !txnCode.equals("999999")){
			return;
		}
		
		try{
//		String clientIP = request.getRemoteAddr();
		
		String clientIP = request.getHeader("x-forwarded-for");
		if (clientIP == null || clientIP.length() == 0 || "unknown".equalsIgnoreCase(clientIP)) {
			clientIP = request.getHeader("Proxy-Client-IP");
		}
		if (clientIP == null || clientIP.length() == 0 || "unknown".equalsIgnoreCase(clientIP)) {
			clientIP = request.getHeader("WL-Proxy-Client-IP");
		}
		if (clientIP == null || clientIP.length() == 0 || "unknown".equalsIgnoreCase(clientIP)) {
			clientIP = request.getRemoteAddr();
		}
		String userName = "";
		
		if(null!=request.getParameter(VoUser.OPER_USERNAME)&&!"".equals(request.getParameter(VoUser.OPER_USERNAME)))
			userName = request.getParameter(VoUser.OPER_USERNAME);
			if(null!=request.getAttribute(VoUser.OPER_USERNAME)&&!"".equals(request.getAttribute(VoUser.OPER_USERNAME).toString()))
				userName = request.getAttribute(VoUser.OPER_USERNAME).toString();
//		String password = (String)request.getParameter("password");
		
//		System.out.println("userName-0:" + userName);
//		System.out.println("password-0:" + password);
		
		String keypassword = request.getParameter("pwd1");
//		System.out.println(keypassword);
		// 如果certNumber不为空，证明是index.jsp提交过来的数据
		// 否则是 门户 提交过来的数据
		if (keypassword != null && !keypassword.equals("")){
			boolean checkPassed = varifyCert(request, userName);
			if (checkPassed){
//				return;
			}else{
				throw new TxnErrorException("999999", "证书验证错误，请重试!");
			}
		}else{
			boolean needKey = isNeedCertKey(userName);
			if (needKey){
				throw new TxnErrorException("999999", "请插入key，并输入密码！");
			}else{
//				return;
			}
		}
		
//		System.out.println("userName-1:" + userName);
//		System.out.println("password-1:" + password);
		
		/**
		 * --------------分割线------------------
		 */
		
			boolean hasPriv = OnlineUserInfo.hasLoginPriv(userName, clientIP);
			// 如果没有权限，则抛出异常。否则，添加session表记录
			if (!hasPriv){
				throw new TxnErrorException("999999", "当前登陆用户已满!请稍后再试！");
			}
		}catch (DBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	/***
	 * 后校验(仅限登陆)
	 */
	public boolean afterAction(String txnCode, HttpServletRequest request,
			TxnContext context) throws TxnException
	{
		// 如果交易号为空或者不是登陆交易，就不校验了
		if (txnCode == null || !txnCode.equals("999999")){
			return false;
		}
		
		HttpSession	session = request.getSession(false);
		// 如果session为空，直接退出
		if (session == null){
			return true;
		}
		// String clientIP = request.getRemoteAddr();
		String clientIP = request.getHeader("x-forwarded-for");
		if (clientIP == null || clientIP.length() == 0 || "unknown".equalsIgnoreCase(clientIP)) {
			clientIP = request.getHeader("Proxy-Client-IP");
		}
		if (clientIP == null || clientIP.length() == 0 || "unknown".equalsIgnoreCase(clientIP)) {
			clientIP = request.getHeader("WL-Proxy-Client-IP");
		}
		if (clientIP == null || clientIP.length() == 0 || "unknown".equalsIgnoreCase(clientIP)) {
			clientIP = request.getRemoteAddr();
		}
		String userName = (String)session.getAttribute( VoUser.OPER_USERNAME );
		String jgCode = context.getOperData().getOrgCode();
		String userId = context.getOperData().getValue("userID");
		try {
			OnlineUserInfo.addLoginUser(session.getId(), userName, clientIP, jgCode, userId);
		} catch (DBException e) {
			e.printStackTrace();
			throw new TxnErrorException("999999", "登陆出错，请稍后再试!");
		}
//		System.out.println("context：" + context);
		context.getRecord("oper-data").setValue("ipaddress", clientIP);
		return false;
	}
	
	/**
	 * 校验证书登陆是否正确
	 * @return
	 * @throws DBException 
	 */
	private boolean isCertCorrect(String userName, String certNo) throws DBException{
		String sql = "select * from xt_zzjg_yh_new t where upper(t.yhzh)='" + userName.toUpperCase() + "' and t.isneedkey = '1'";
//		System.out.println("sql:" + sql);
		List list = null;
		list = DbUtils.select(sql, dbType);
		// 如果找到记录，证明需要key才能登陆
		if (list.size() > 0){
			Map map = (Map) list.get(0);
			
			String keyNumber = map.get("KEYNUMBER") != null ? map.get("KEYNUMBER").toString() : "";
//			System.out.println("keyNumber:" + keyNumber);
//			System.out.println("certNo:" + certNo);
			// System.out.println("certNo.indexOf(keyNumber):" + certNo.indexOf(keyNumber));
//			System.out.println(certNo == null);
			
			// 没有找到，证书登陆错误
			if (certNo == null || ( !keyNumber.equals("") && certNo.indexOf(keyNumber) < 0 )){
				return false;
			}
			
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 是否需要
	 * @param userName
	 * @param certNo
	 * @return
	 * @throws DBException
	 */
	private boolean isNeedCertKey(String userName) throws DBException{
		String sql = "select * from xt_zzjg_yh_new t where upper(t.yhzh)='" + userName.toUpperCase() + "' and t.isneedkey = '1'";
//		System.out.println("sql:" + sql);
		List list = null;
		list = DbUtils.select(sql, dbType);
		// 如果找到记录，证明需要key才能登陆
		if (list.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 
	 * @param userName
	 * @param certNo
	 * @return
	 * @throws DBException
	 */
	private boolean varifyCert(HttpServletRequest request, String userName) throws DBException{
		SecurityEngineDeal sed = null;
	    sed = SecurityEngineDeal.getInstance();
		  
        //获得登陆用户cert
		String clientCert = request.getParameter("UserCert");
		String UserSignedData = request.getParameter("UserSignedData");
		String ContainerName = request.getParameter("ContainerName");
		String ranStr = (String) request.getSession().getAttribute("Random");
//		System.out.println("ranStr:" + ranStr);
		
		try{
			if(sed.verifySignedDataPkcs1(clientCert,ranStr,UserSignedData))		 
			{
				System.out.println("验证客户端签名正确！");	
			}
			else {
				System.out.println("验证客户端签名错误！");
				return false;
			}
		}catch (Exception e) {
			System.out.println("<p>验证客户端签名错误:"+e.getMessage()+"<p>");
		    return false;
		}
		  
		try{
			boolean retValue = sed.validateCert(clientCert);
			if (retValue) {
//				System.out.println("客户端证书验证成功！");
//				request.setAttribute("ContainerName", ContainerName);
//				System.out.println("ContainerName:" + ContainerName);
				//获得登陆用户ID
				String uniqueIdStr = sed.getCertInfo(clientCert,22);
//				request.setAttribute("UniqueID", uniqueIdStr);
//				System.out.println("uniqueIdStr:" + uniqueIdStr);
				//添加方法设置当前
//				System.out.println("<p>证书唯一标识：");
//				System.out.println(uniqueIdStr);
//				System.out.println("<p>");
				
				String sql = "select * from xt_zzjg_yh_new t where upper(t.yhzh)='" + userName.toUpperCase()
					+ "' and t.isneedkey = '1' and upper(t.keynumber)='" + uniqueIdStr.toUpperCase() + "'";
				System.out.println("sql:" + sql);
				List list = null;
				list = DbUtils.select(sql, dbType);
				// 如果找到记录，证明需要key才能登陆
				if (list.size() > 0){
					return true;
				}else{
					return false;
				}
			}else {
//				System.out.println("客户端证书验证失败！");
				return false;
			}
		} catch (Exception ex){
//				  System.out.println("<p>客户端证书验证失败:"+ex.getMessage()+"<p>");
				  return false;
		}
	}
}
