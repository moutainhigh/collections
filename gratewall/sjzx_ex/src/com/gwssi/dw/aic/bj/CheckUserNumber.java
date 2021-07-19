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
	 * ǰУ��(���޵�½)
	 */
	public void beforeAction(String txnCode, HttpServletRequest request)
		throws TxnException
	{
		// ������׺�Ϊ�ջ��߲��ǵ�½���ף��Ͳ�У����
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
		// ���certNumber��Ϊ�գ�֤����index.jsp�ύ����������
		// ������ �Ż� �ύ����������
		if (keypassword != null && !keypassword.equals("")){
			boolean checkPassed = varifyCert(request, userName);
			if (checkPassed){
//				return;
			}else{
				throw new TxnErrorException("999999", "֤����֤����������!");
			}
		}else{
			boolean needKey = isNeedCertKey(userName);
			if (needKey){
				throw new TxnErrorException("999999", "�����key�����������룡");
			}else{
//				return;
			}
		}
		
//		System.out.println("userName-1:" + userName);
//		System.out.println("password-1:" + password);
		
		/**
		 * --------------�ָ���------------------
		 */
		
			boolean hasPriv = OnlineUserInfo.hasLoginPriv(userName, clientIP);
			// ���û��Ȩ�ޣ����׳��쳣���������session���¼
			if (!hasPriv){
				throw new TxnErrorException("999999", "��ǰ��½�û�����!���Ժ����ԣ�");
			}
		}catch (DBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	/***
	 * ��У��(���޵�½)
	 */
	public boolean afterAction(String txnCode, HttpServletRequest request,
			TxnContext context) throws TxnException
	{
		// ������׺�Ϊ�ջ��߲��ǵ�½���ף��Ͳ�У����
		if (txnCode == null || !txnCode.equals("999999")){
			return false;
		}
		
		HttpSession	session = request.getSession(false);
		// ���sessionΪ�գ�ֱ���˳�
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
			throw new TxnErrorException("999999", "��½�������Ժ�����!");
		}
//		System.out.println("context��" + context);
		context.getRecord("oper-data").setValue("ipaddress", clientIP);
		return false;
	}
	
	/**
	 * У��֤���½�Ƿ���ȷ
	 * @return
	 * @throws DBException 
	 */
	private boolean isCertCorrect(String userName, String certNo) throws DBException{
		String sql = "select * from xt_zzjg_yh_new t where upper(t.yhzh)='" + userName.toUpperCase() + "' and t.isneedkey = '1'";
//		System.out.println("sql:" + sql);
		List list = null;
		list = DbUtils.select(sql, dbType);
		// ����ҵ���¼��֤����Ҫkey���ܵ�½
		if (list.size() > 0){
			Map map = (Map) list.get(0);
			
			String keyNumber = map.get("KEYNUMBER") != null ? map.get("KEYNUMBER").toString() : "";
//			System.out.println("keyNumber:" + keyNumber);
//			System.out.println("certNo:" + certNo);
			// System.out.println("certNo.indexOf(keyNumber):" + certNo.indexOf(keyNumber));
//			System.out.println(certNo == null);
			
			// û���ҵ���֤���½����
			if (certNo == null || ( !keyNumber.equals("") && certNo.indexOf(keyNumber) < 0 )){
				return false;
			}
			
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * �Ƿ���Ҫ
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
		// ����ҵ���¼��֤����Ҫkey���ܵ�½
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
		  
        //��õ�½�û�cert
		String clientCert = request.getParameter("UserCert");
		String UserSignedData = request.getParameter("UserSignedData");
		String ContainerName = request.getParameter("ContainerName");
		String ranStr = (String) request.getSession().getAttribute("Random");
//		System.out.println("ranStr:" + ranStr);
		
		try{
			if(sed.verifySignedDataPkcs1(clientCert,ranStr,UserSignedData))		 
			{
				System.out.println("��֤�ͻ���ǩ����ȷ��");	
			}
			else {
				System.out.println("��֤�ͻ���ǩ������");
				return false;
			}
		}catch (Exception e) {
			System.out.println("<p>��֤�ͻ���ǩ������:"+e.getMessage()+"<p>");
		    return false;
		}
		  
		try{
			boolean retValue = sed.validateCert(clientCert);
			if (retValue) {
//				System.out.println("�ͻ���֤����֤�ɹ���");
//				request.setAttribute("ContainerName", ContainerName);
//				System.out.println("ContainerName:" + ContainerName);
				//��õ�½�û�ID
				String uniqueIdStr = sed.getCertInfo(clientCert,22);
//				request.setAttribute("UniqueID", uniqueIdStr);
//				System.out.println("uniqueIdStr:" + uniqueIdStr);
				//��ӷ������õ�ǰ
//				System.out.println("<p>֤��Ψһ��ʶ��");
//				System.out.println(uniqueIdStr);
//				System.out.println("<p>");
				
				String sql = "select * from xt_zzjg_yh_new t where upper(t.yhzh)='" + userName.toUpperCase()
					+ "' and t.isneedkey = '1' and upper(t.keynumber)='" + uniqueIdStr.toUpperCase() + "'";
				System.out.println("sql:" + sql);
				List list = null;
				list = DbUtils.select(sql, dbType);
				// ����ҵ���¼��֤����Ҫkey���ܵ�½
				if (list.size() > 0){
					return true;
				}else{
					return false;
				}
			}else {
//				System.out.println("�ͻ���֤����֤ʧ�ܣ�");
				return false;
			}
		} catch (Exception ex){
//				  System.out.println("<p>�ͻ���֤����֤ʧ��:"+ex.getMessage()+"<p>");
				  return false;
		}
	}
}
