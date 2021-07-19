package com.gwssi.ebaic.common.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwssi.ebaic.apply.util.SessionConst;
import com.gwssi.ebaic.approve.util.ApproveUserUtil;
import com.gwssi.ebaic.domain.SysmgrUser;
import com.gwssi.optimus.util.MD5Util;
import com.gwssi.rodimus.bjca.BjcaEngine;
import com.gwssi.rodimus.dao.ApproveDaoUtil;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.RodimusException;
import com.gwssi.rodimus.util.DateUtil;
import com.gwssi.rodimus.util.FileUtil;
import com.gwssi.rodimus.util.ParamUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.rodimus.util.UUIDUtil;

import bjca.org.apache.log4j.Logger;


/**
 * 北京CA。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Service("bjcaService")
public class BjcaServiceImpl implements BjcaService{
	protected final static Logger logger = Logger.getLogger(BjcaServiceImpl.class);

	@Autowired
	private BjcaEngine bjcaEngine = null ;
	


	/* (non-Javadoc)
	 * @see com.gwssi.rodimus.bjca.core.SecurityService#preBind(javax.servlet.http.HttpServletRequest)
	 */
	public Map<String, Object> preBind(HttpServletRequest request) {
		try {
			
			// 获得服务器端证书
			String strServerCert = bjcaEngine.getServerCertificate();
			// 产生随机数
			String strRandom = bjcaEngine.genRandom(24);
			// 对随机数签名
			String strSignedData = bjcaEngine.signData(strRandom.getBytes());
			// 将随机数放入Session
			HttpSession session = request.getSession();
			session.setAttribute("Random", strRandom);
			
			Map<String,Object> ret = new HashMap<String,Object>();
			ret.put("strSignedData", strSignedData);
			ret.put("strRandom", strRandom);
			ret.put("strServerCert", strServerCert);
			
			return ret;
		} catch (Exception e) {
			throw new RodimusException(e.getMessage(),e);
		}
	}
	/* (non-Javadoc)
	 * @see com.gwssi.rodimus.bjca.core.SecurityService#bind(javax.servlet.http.HttpServletRequest)
	 */
	public Map<String,Object> bind(HttpServletRequest request) {
		Map<String,Object> ret = new HashMap<String,Object>();
		HttpSession session = request.getSession();
		//获得登录用户cert
		String clientCert = request.getParameter("UserCert");
		String UserSignedData = request.getParameter("UserSignedData");
		String ContainerName = request.getParameter("ContainerName");
		
		String UserSignedData2 = request.getParameter("UserSignedData2");//userId
		
		try {
//			String certPub = bjcaEngine.getCertInfo(clientCert, 8);
			String certType = bjcaEngine.getCertInfo(clientCert, 31);
			session.setAttribute("UserCertType", certType);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		
		//验证客户端签名
		byte[] signedByte = bjcaEngine.base64Decode(UserSignedData);
		try {
			// 取出随机数
			String ranStr = (String) session.getAttribute(SessionConst.BJCA_RANDOM);
			if(StringUtil.isBlank(ranStr)){
				ret.put("error", "登录失败:请刷新页面后重试。");
				return ret;
//				throw new RuntimeException("登录失败:请刷新页面后重试。");
			}
			if (!bjcaEngine.verifySignedData(clientCert, ranStr.getBytes(), signedByte)) {
				ret.put("error", "绑定失败:验证客户端签名失败。");
				return ret;
				//throw new RuntimeException("绑定失败:验证客户端签名失败。");
			}
		} catch (Exception e) {
			ret.put("error", "绑定失败:验证客户端签名失败。");
			return ret;
//			throw new RuntimeException("绑定失败:验证客户端签名失败。");
		}
		
		String userId = null;
		try {
			userId = bjcaEngine.priKeyDecrypt(UserSignedData2);
		} catch (Exception e) {
			ret.put("error", "绑定失败:验证客户端签名失败。");
			return ret;
//			throw new RuntimeException("绑定失败:验证客户端签名失败。");
		}
		if(StringUtil.isBlank(userId)){
			
		}
		
		
		//验证客户端证书
		try {

			int retValue = bjcaEngine.validateCert(clientCert);
			if(retValue!=1){
				if (retValue == -1) {
					throw new RuntimeException("绑定失败:证书的根不被信任。");
				} else if (retValue == -2) {
					throw new RuntimeException("绑定失败:证书超过有效期。");
				} else if (retValue == -3) {
					throw new RuntimeException("绑定失败:证书为作废证书。");
				} else if (retValue == -4) {
					throw new RuntimeException("绑定失败:证书被临时冻结。");
				}else{
//					throw new RuntimeException("绑定失败:证书无效。");
				}
			}
			
			session.setAttribute("ContainerName", ContainerName);
			
			// TODO 唯一标识是否会变，如：证书重做的时候
			String uniqueId = "";
			try {
				//获得绑定用户ID
				uniqueId = bjcaEngine.getCertInfoByOid(clientCert,"2.16.840.1.113732.2");
			} catch (Exception e) {
				uniqueId = "";
			}
													
			if(uniqueId == null || "".equals(uniqueId.trim())) {
				try {
					//获得绑定用户ID
					uniqueId = bjcaEngine.getCertInfoByOid(clientCert,"1.2.156.112562.2.1.1.1");
				} catch (Exception e) {
					throw new RuntimeException("绑定失败:证书无效。");
				}
			}
			//
			if(StringUtil.isBlank(uniqueId)) {
				//请在错误页面上提示。
				throw new RuntimeException("绑定失败:证书无效。");
			}
			session.setAttribute("ca_cert_uniqueId", uniqueId);
			//请处理您的业务绑定。
/*			
			String sql = "select u.user_id from sysmgr_user u where u.CA_CERT_UNIQUEID = ? and u.flag='1'";
			String userId = ApproveDaoUtil.getInstance().queryForOneString(sql, uniqueId);
			if(StringUtil.isBlank(userId)){
				throw new RuntimeException("绑定失败:证书没有关联系统用户，请联系分局管理员。");
			}
			doLogin(userId);
*/	
//				out.println("<h3>欢迎您使用本系统!</h3>");
//				out.println("<h3>主题通用名：");
//				out.println(uniqueIdStr);
//				out.println("<br/>证书颁发者(颁发者通用名): ");
//				out.println(certPub);
//				out.println("<br/>证书唯一标识(备用主题通用名)：");
//				out.println(uniqueId);
//				out.println("<font color='red'>(实际集成时,会将唯一标识与数据库比对,判断是否为合法用户)</font>");
//				out.println("</h3><br/>");
			 
		} catch (Exception ex) {
			throw new RuntimeException("绑定失败:证书无效。");
		}
		session.removeAttribute("Random");
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.gwssi.rodimus.bjca.core.SecurityService#preLogin(javax.servlet.http.HttpServletRequest)
	 */
	public Map<String, Object> preLogin(HttpServletRequest request) {
		try {
			
			// 获得服务器端证书
			String strServerCert = bjcaEngine.getServerCertificate();
			// 产生随机数
			String strRandom = bjcaEngine.genRandom(24);
			// 对随机数签名
			String strSignedData = bjcaEngine.signData(strRandom.getBytes());
			// 将随机数放入Session
			HttpSession session = request.getSession();
			session.setAttribute("Random", strRandom);
			
			Map<String,Object> ret = new HashMap<String,Object>();
			ret.put("strSignedData", strSignedData);
			ret.put("strRandom", strRandom);
			ret.put("strServerCert", strServerCert);
			
			return ret;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}

	/* (non-Javadoc)
	 * @see com.gwssi.rodimus.bjca.core.SecurityService#login(javax.servlet.http.HttpServletRequest)
	 */
	public Map<String,Object> login(HttpServletRequest request) {
		Map<String,Object> ret = new HashMap<String,Object>();
		HttpSession session = request.getSession();
		//获得登录用户cert
		String clientCert = request.getParameter("UserCert");
		String UserSignedData = request.getParameter("UserSignedData");
		String ContainerName = request.getParameter("ContainerName");
		
		try {
			String certType = bjcaEngine.getCertInfo(clientCert, 31);
			session.setAttribute("UserCertType", certType);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		
		//验证客户端签名
		byte[] signedByte = bjcaEngine.base64Decode(UserSignedData);
		try {
			// 取出随机数
			String ranStr = (String) session.getAttribute(SessionConst.BJCA_RANDOM);
			if(StringUtil.isBlank(ranStr)){
				ret.put("error", "登录失败:请刷新页面后重试。");
				return ret;
			}
			if (!bjcaEngine.verifySignedData(clientCert, ranStr.getBytes(), signedByte)) {
				ret.put("error", "登录失败:验证客户端签名失败。");
				return ret;
			}
		} catch (Exception e) {
			ret.put("error", "登录失败:验证客户端签名失败。");
			return ret;
		}
		
		//验证客户端证书
		String uniqueId = "";
		try {

			int retValue = bjcaEngine.validateCert(clientCert);
			if(retValue!=1){
				if (retValue == -1) {
					ret.put("error", "登录失败:证书的根不被信任。");
					return ret;
				} else if (retValue == -2) {
					ret.put("error", "登录失败:证书超过有效期。");
					return ret;
				} else if (retValue == -3) {
					ret.put("error", "登录失败:证书为作废证书。");
					return ret;
				} else if (retValue == -4) {
					ret.put("error", "登录失败:证书被临时冻结。");
					return ret;
				}else{
					ret.put("error", "登录失败:证书无效。");
					return ret;
				}
			}
			
			session.setAttribute("ContainerName", ContainerName);
			
			
			try {
				//获得登录用户ID
				uniqueId = bjcaEngine.getCertInfoByOid(clientCert,"2.16.840.1.113732.2");
			} catch (Exception e) {
				uniqueId = "";
			}
													
			if(uniqueId == null || "".equals(uniqueId.trim())) {
				try {
					//获得登录用户ID
					uniqueId = bjcaEngine.getCertInfoByOid(clientCert,"1.2.156.112562.2.1.1.1");
				} catch (Exception e) {

					ret.put("error", "登录失败:证书无效。");
					return ret;
				}
			}
			
			
	

		} catch (Exception ex) {
			ret.put("error", "登录失败:证书无效。"+ex.getMessage());
			return ret;
		}
		//
		if(StringUtil.isBlank(uniqueId)) {
			ret.put("error", "登录失败:未获取有效的证书uniqueId。");
			return ret;
		}
		session.setAttribute("ca_cert_uniqueId", uniqueId);
		//请处理您的业务登录。			
		String sql = "select u.user_id from sysmgr_user u where u.CA_CERT_ID = ? and u.flag='1'";
		String userId = ApproveDaoUtil.getInstance().queryForOneString(sql, uniqueId);
		if(StringUtil.isBlank(userId)){
			ret.put("error", "登录失败:证书没有关联系统用户，请联系分局管理员。");
			return ret;
		}
//		doLogin(userId);
		
		session.removeAttribute("Random");
		ret.put("result", "success");
		return ret;
	}

	/* 
	 * 对文件计算摘要，将摘要发到客户端。
	 * 客户端对摘要签名，将fileid、公钥、签名发回服务器端。
	 * (non-Javadoc)
	 * @see com.gwssi.rodimus.bjca.core.SecurityService#preSign(javax.servlet.http.HttpServletRequest)
	 
	public Map<String, Object> preSign(HttpServletRequest request) {
		String fileid = request.getParameter("fileid");
		Map<String,Object> ret = new HashMap<String,Object>();
		try {
			String filePath = "H:\\t\\ArchiveElecFile.pdf";//FileUtil.getFilePath(fileid);
			byte[] fileContent = FileUtil.getFileBytes(filePath);
			byte[] hashByte = bjcaEngine.hashData(fileContent);
			String fileHashCode = bjcaEngine.base64Encode(hashByte);
			ret.put("fileId", fileid);
			ret.put("hashCode", fileHashCode);
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			ret.put("error", e.getMessage());
			return ret;
		}
		
	}*/
	
	
	/* 
	 * 对文件计算摘要，将摘要发到客户端。
	 * 客户端对摘要签名，将fileid、公钥、签名发回服务器端。
	 * (non-Javadoc)
	 * @see com.gwssi.rodimus.bjca.core.SecurityService#preSign(javax.servlet.http.HttpServletRequest)
	 */
	public Map<String, Object> preSign(HttpServletRequest request) {
		String filePath = StringUtil.safe2String(request.getAttribute(SessionConst.BJCA_PATH));
		System.out.println("预签名路径："+filePath);
		
		HttpSession session = request.getSession();
		session.setAttribute(SessionConst.BJCA_PATH, filePath);
		Map<String,Object> ret = new HashMap<String,Object>();
		try {
			byte[] fileContent = FileUtil.getFileBytes(filePath);
			System.out.println("fileContent："+fileContent);
			
			byte[] hashByte = bjcaEngine.hashData(fileContent);
			System.out.println("hashByte："+hashByte);
			
			String fileHashCode = bjcaEngine.base64Encode(hashByte);
			System.out.println("fileHashCode："+fileHashCode);
			
			ret.put("hashCode", fileHashCode);
			return ret;
		} catch (Exception e) {
			System.out.println("ERROR："+e.getMessage());
			e.printStackTrace();
			ret.put("error", e.getMessage());
			return ret;
		}
		
	}
	

	/* 
	 * 接收服务器端fileid、公钥、签名，验签后保存到数据库。
	 * (non-Javadoc)
	 * @see com.gwssi.rodimus.bjca.core.SecurityService#sign(javax.servlet.http.HttpServletRequest)
	 */
	public Map<String, Object> sign(HttpServletRequest request){
		Map<String,Object> ret = new HashMap<String,Object>();
		HttpSession session = request.getSession();
		String gid = ParamUtil.get("gid");
		System.out.println("gid:" + gid);
		if(StringUtil.isBlank(gid)){
			ret.put("error", "gid不能为空。");
			return ret;
		}
		// 当前登录用户
        SysmgrUser currentUser = null;
        try{
            currentUser = ApproveUserUtil.getLoginUser();
        }catch(Exception e){
            e.printStackTrace();
        }
        //获取客户端提交的 P7 签名
        String UserSignedData = request.getParameter("UserSignedData");
        System.out.println("UserSignedData:" + UserSignedData);
        String currentLoginCaCertId = currentUser.getCaCertId();
        //客户端证书
        String userCert = bjcaEngine.getP7SignatureInfo(UserSignedData, 2);//不是byte[]吗？
        int retValue = bjcaEngine.validateCert(userCert);
        System.out.println("证书状态："+retValue);
        String uniqueId = bjcaEngine.getCertInfoByOid(userCert,"2.16.840.1.113732.2");
        if(StringUtil.isBlank(uniqueId)){
            uniqueId = bjcaEngine.getCertInfoByOid(userCert,"1.2.156.112562.2.1.1.1");
        }
        System.out.println("uniqueId："+uniqueId);
        if(StringUtil.isBlank(uniqueId)){
            ret.put("error", "签名失败:证书无效。");
            return ret;
        }
		//这里需要从session中获取登录用户，先注释
		if(StringUtil.isBlank(currentLoginCaCertId)){
			ret.put("error", "签名失败:请注销后，通过电子证书登录系统。");
			return ret;
		}
		if(!uniqueId.equals(currentLoginCaCertId)){
			ret.put("error", "签名失败:当前电子证书与系统登录用户不一致。");
			return ret;
		}
		// 文件物理路径
		String filePath = StringUtil.safe2String(session.getAttribute(SessionConst.BJCA_PATH));
		System.out.println("filePath:" + filePath);
		try {
			// 计算文件摘要
			byte[] fileContent = FileUtil.getFileBytes(filePath);
			System.out.println("fileContent:" + fileContent);
			byte[] hashByte = bjcaEngine.hashData(fileContent);
			System.out.println("hashByte:" + hashByte);
			String serverHashCode = bjcaEngine.base64Encode(hashByte);
			System.out.println("serverHashCode:" + serverHashCode);
			// 原文
			String clientHashCode = bjcaEngine.getP7SignatureInfo(UserSignedData, 1);//不是byte[]吗？
			if(!serverHashCode.equals(clientHashCode)){
				System.out.println("签名失败:签名数据与服务器端数据不一致。");
				ret.put("error", "签名失败:签名数据与服务器端数据不一致。");
				return ret;
			}
			
			boolean isVerified =  bjcaEngine.verifySignedDataByP7Attach(UserSignedData);
			ret.put("isVerified", isVerified);
			System.out.println("isVerified："+isVerified);
			if(!isVerified){
				System.out.println("服务器端校验签名失败。");
				ret.put("error", "服务器端校验签名失败。");
				return ret ;
			}
			if(retValue!=1){
				if (retValue == -1) {
					ret.put("error", "签名失败:证书的根不被信任。");
					return ret;
				} else if (retValue == -2) {
					ret.put("error", "签名失败:证书超过有效期。");
					return ret;
				} else if (retValue == -3) {
					ret.put("error", "签名失败:证书为作废证书。");
					return ret;
				} else if (retValue == -4) {
					ret.put("error", "签名失败:证书被临时冻结。");
					return ret;
				} else {
					ret.put("error", "签名失败:证书无效。");
					return ret;
				}
			}
//			
//			String certVersion = bjcaEngine.getCertInfo(userCert, 1);
//			("1:"+certVersion);
//			String certSn = bjcaEngine.getCertInfo(userCert, 2);
//			("2:"+certSn);
//			String certPublisher = bjcaEngine.getCertInfo(userCert, 8);
//			("8:"+certPublisher);
//			for(int i=11;i<32;++i){
//				String t = bjcaEngine.getCertInfo(userCert, i);
//				(i+":"+t);
//			}
			// 客户端签名
			String userSign = bjcaEngine.getP7SignatureInfo(UserSignedData, 3);//不是byte[]吗？
			System.out.println("userSign："+userSign);
//			("证书号:\n"+userCert);
//			("证书:\n"+userCert);
//			("签名:\n"+userSign);
			
			String fileId = MD5Util.MD5Encode(serverHashCode);

			System.out.println("fileId："+fileId);
			// 插入签名记录到 be_wk_file_sign表		
			String sql = "select count(1) as cnt from be_wk_file_sign t where t.gid = ?";
			long cnt = DaoUtil.getInstance().queryForOneLong(sql, gid);
			if(cnt>0){
				
				sql = "delete from be_wk_file_sign t where t.gid = ?";
				DaoUtil.getInstance().execute(sql, gid);
				
				/*sql = "update be_wk_file_sign t set t.approve_user_id=?,t.approve_user_name=?,t.user_cert=?,t.user_sign=?,t.gid=?,t.timestamp=?,t.reg_org=? where t.file_id = ?";
				DaoUtil.getInstance().execute(sql, currentUser.getUserId(),currentUser.getUserName(),userCert,userSign,gid,DateUtil.getCurrentTime(),currentUser.getOrgCodeFk(),fileId);*/
			}
			String currentUserId = "";
			String currentName = "";
			String currentRegOrg = "";
			if(currentUser!=null){
				currentUserId = currentUser.getUserId();
				currentName =  currentUser.getUserName();
				currentRegOrg = currentUser.getOrgCodeFk();
			}
			sql = "insert into be_wk_file_sign(sign_id,file_id,approve_user_id,approve_user_name,user_cert,user_sign,gid,timestamp,reg_org) values (?,?,?,?,?,?,?,?,?)";

			System.out.println("sql："+sql);
			String signId = UUIDUtil.getUUID();
			DaoUtil.getInstance().execute(sql, signId,fileId,currentUserId,currentName,userCert,userSign,gid,DateUtil.getCurrentTime(),currentRegOrg);

			System.out.println("OK");
			// 返回
			ret.put("result", "success");
			ret.put(SessionConst.BJCA_PATH, filePath);
			return ret;
			
		} catch (Exception e) {
			ret.put("error", e.getMessage());
			e.printStackTrace();
			return ret;
		}
	}
	/**
	 * 
	 * 	<li></li>
	 * 	<li>通过fileid获得物理文件，对物理文件计算摘要得到D2；</li>
	 * 	<li>比对D1和D2，如果不一致，验签失败，抛出异常；</li>
	 *  <li>验签成功，从证书C1中取得身份信息（姓名、身份证号码、组织信息、联系方式等），从sysmgr_user表获取身份信息。</li>
	 */
	public Map<String, Object> verSign(HttpServletRequest request) {

		Map<String,Object> ret = new HashMap<String,Object>();
		ret.put("error", "未实现。");
		return ret;
//		String fileId = request.getParameter("fileid");
//		//根据fileid查询be_wk_file_sign表，获得签名证书C、签名S
//		String sql = "select t.file_id,t.approve_user_id,t.approve_user_name,t.cert,t.sign from be_wk_file_sign t where t.file_id = ?";
//		Map<String, Object> signRow = DaoUtil.getInstance().queryForRow(sql, fileId);
//		if(signRow==null || signRow.isEmpty()){
//			throw new RuntimeException("签名校验失败：没有签名记录。");
//		}
//		String certInDb = StringUtil.safe2String(signRow.get("cert"));
//		String signInDb = StringUtil.safe2String(signRow.get("sign"));
		
//		String filePath = "H:\\t\\ArchiveElecFile.pdf";//FileUtil.getFilePath(fileid);
//		byte[] fileContent = FileUtil.getFileBytes(filePath);
//		byte[] hashByte = bjcaEngine.hashData(fileContent);
//		String hashCode = bjcaEngine.base64Encode(hashByte);
//		
//		String certInDb = "MIIFfzCCBGegAwIBAgIKLDAAAAAAAACAZjANBgkqhkiG9w0BAQUFADBSMQswCQYDVQQGEwJDTjENMAsGA1UECgwEQkpDQTEYMBYGA1UECwwPUHVibGljIFRydXN0IENBMRowGAYDVQQDDBFQdWJsaWMgVHJ1c3QgQ0EtMTAeFw0xNjA2MjcxNjAwMDBaFw0xNjA5MjgxNTU5NTlaMCwxCzAJBgNVBAYTAkNOMR0wGwYDVQQDDBTmtYvor5Xor4HkuaYo5rWL6K+VKTCBnzANBgkqhkiG9w0BAQEFAAOBjQAwgYkCgYEAyrrGSyyU2bFI9S0Q0pDE/oM5V6z+I3cbC+WEPxA82bM6qZtSbpX3rp2u+E93OHwFYLENx3CGCWCfxR9UvQqWYJ+VT10Tr5UJsDCRCwqAMQDKGf3n6wgBwY+G+Xi5eGHx/ro2V1UqxOm1wJGQ+C8SC1nzSI2uwbsNW/ao4fDmwnsCAwEAAaOCAv8wggL7MB8GA1UdIwQYMBaAFKw77K8Mo1AO76+vtE9sO9vRV9KJMB0GA1UdDgQWBBQyii4ADBCj5zQA80aDl3/F16h4QjALBgNVHQ8EBAMCBsAwga0GA1UdHwSBpTCBojBsoGqgaKRmMGQxCzAJBgNVBAYTAkNOMQ0wCwYDVQQKDARCSkNBMRgwFgYDVQQLDA9QdWJsaWMgVHJ1c3QgQ0ExGjAYBgNVBAMMEVB1YmxpYyBUcnVzdCBDQS0xMRAwDgYDVQQDEwdjYTNjcmwxMDKgMKAuhixodHRwOi8vbGRhcC5iamNhLm9yZy5jbi9jcmwvcHRjYS9jYTNjcmwxLmNybDAJBgNVHRMEAjAAMBEGCWCGSAGG+EIBAQQEAwIA/zAdBgUqVgsHAQQUU0YzMjExMDExOTgwMDUxNDAwMTUwHQYFKlYLBwgEFFNGMzIxMTAxMTk4MDA1MTQwMDE1MCAGCGCGSAGG+EQCBBRTRjMyMTEwMTE5ODAwNTE0MDAxNTAbBggqVoZIAYEwAQQPOTk5MDAwMTAwMTAyOTQ0MCUGCiqBHIbvMgIBBAEEFzFDQFNGMzIxMTAxMTk4MDA1MTQwMDE1MCoGC2CGSAFlAwIBMAkKBBtodHRwOi8vYmpjYS5vcmcuY24vYmpjYS5jcnQwDwYFKlYVAQEEBjEwMDAxMDCB5wYDVR0gBIHfMIHcMDUGCSqBHAHFOIEVATAoMCYGCCsGAQUFBwIBFhpodHRwOi8vd3d3LmJqY2Eub3JnLmNuL2NwczA1BgkqgRwBxTiBFQIwKDAmBggrBgEFBQcCARYaaHR0cDovL3d3dy5iamNhLm9yZy5jbi9jcHMwNQYJKoEcAcU4gRUDMCgwJgYIKwYBBQUHAgEWGmh0dHA6Ly93d3cuYmpjYS5vcmcuY24vY3BzMDUGCSqBHAHFOIEVBDAoMCYGCCsGAQUFBwIBFhpodHRwOi8vd3d3LmJqY2Eub3JnLmNuL2NwczATBgoqgRyG7zICAQEeBAUMAzUzNjANBgkqhkiG9w0BAQUFAAOCAQEAWCXYbKUgoWc64YmQEpmLLZo/WuPSKT0vw3+mTOYwictx75/d5hqRStVc8+lWCMBMDR25nvoedwUt7yCWJzEAxRni7tyFgJ+Wml5eZf8/FU6C1MmBRIv8qXs4YCBQP/v8BSJJqZYKBSTxGnK7MyKNDu16++OP3uvpOHyuwh4lTb1N1w3Xdgqy9Nadh3EWDNITPZlZYS+4ocx0xIcAMTgMN8mn5cYzSi+WIYBMD9m+Q4N5ILr6+PE/bcBGrPZkGWxI36frjYaL5C3A/SSEpJT3lWcmXLS3Rph7KWiFfwM1mNAKo6wKFuS5P4ZbPT+uNqBBFkfAUSOuFURKm/1ssWq+3w==";
//		String signInDb = "FW59gV4+2Zyl4MwX5/ztJQslFf6J4QIi0ad+wuuBSEiCDRemNhLx5P3Pyjk6zhAoAZB5Fspd1RUBUbtoov/LLto/zn3PThVY0BiT6PckCvZk0V8Aur2f48eZuaywnzgelhMNBdsssdlrjZ1Tw1ueXJR9ckaNvKx+fu/OpNTz7pE=";
//		
//		if(StringUtil.isBlank(certInDb)){
//			throw new RuntimeException("签名校验失败：证书记录为空。");
//		}
//		if(StringUtil.isBlank(signInDb)){
//			throw new RuntimeException("签名校验失败：签名记录为空。");
//		}
//		// 使用证书C对签名执行非对称解密，得到D1；
//		try {
//			int retValue = bjcaEngine.verifySignatureByCertOrSN(certInDb, hashCode, signInDb, null);
//			if(retValue==1 || retValue==0){
//				ret.put("result", "success");
//				return ret;
//			}
//			if (retValue == -1) {
//				ret.put("error", "验证失败:证书的根不被信任。");
//				return ret;
//			} else if (retValue == -2) {
//				ret.put("error", "验证失败:证书超过有效期。");
//				return ret;
//			} else if (retValue == -3) {
//				ret.put("error", "验证失败:证书为作废证书。");
//				return ret;
//			} else if (retValue == -4) {
//				ret.put("error", "验证失败:证书被临时冻结。");
//				return ret;
//			} else if (retValue == -5) {
//				ret.put("error", "验证失败:证书未生效。");
//				return ret;
//			} else if (retValue == -6) {
//				ret.put("error", "验证失败:验证签名失败。");
//				return ret;
//			} else if (retValue == -7) {
//				ret.put("error", "验证失败:证书不存在。");
//				return ret;
//			} else {
//				ret.put("error", "验证失败。");
//				return ret;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			ret.put("error", e.getMessage());
//			return ret;
//		}
		
	}
	/**
	 * 入口。
	 */
	public Map<String,Object> service(String serviceName,HttpServletRequest request){
		
		if(StringUtil.isBlank(serviceName)){
			throw new RuntimeException("serviceName不能为空。");
		}
		
		Map<String,Object> ret = null;
		
		if("preBind".equalsIgnoreCase(serviceName)){
			ret = preBind(request);
			return ret ;
		}
		if("bind".equalsIgnoreCase(serviceName)){
			ret = bind(request);
			return ret ;
		}
		
		if("preLogin".equalsIgnoreCase(serviceName)){
			ret = preLogin(request);
			return ret ;
		}
		if("login".equalsIgnoreCase(serviceName)){
			ret = login(request);
			return ret ;
		}
		
		if("preSign".equalsIgnoreCase(serviceName)){
			ret = preSign(request);
			return ret ;
		}
		if("sign".equalsIgnoreCase(serviceName)){
			ret = sign(request);
			return ret ;
		}
		
		if("verSign".equalsIgnoreCase(serviceName)){
			ret = verSign(request);
			return ret ;
		}
		
		throw new RuntimeException("serviceName不正确。");
	}

}
