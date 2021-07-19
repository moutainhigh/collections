package com.gwssi.ebaic.common.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * CA签名服务。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public interface BjcaService {
	
	
	
	/**
	 * <h3>绑定证书到系统用户</h3>
	 * 页面初始化：
	 * <ol>
	 * 	<li> 初始化绑定页面，向服务器请求初始化数据；</li>
	 * 	<li> 服务器端生成随机数R，保存在Session中；</li>
	 * 	<li> 使用服务器私钥非对称加密，得到密文S1；</li>
	 * 	<li> 服务器发送随机数R、服务器公钥和密文S1到客户端。</li>
	 * </ol>
	 */
	public abstract Map<String,Object> preBind(HttpServletRequest request);
	
	
	/**
	 * <h3>绑定证书到系统用户</h3>
	 * 
	 * 客户端操作：
	 * <ol>
	 * 	<li>客户端使用私钥对随机数R非对称加密，得到密文S2；</li>
	 * 	<li>客户端服务器公钥对userId非对称加密，得到密文S3。</li>
	 * </ol>
	 * 
	 * <ol>
	 * 	<li>服务器获得客户端提交的客户端证书（包含公钥）、密文S2、密文S3；</li>
	 * 	<li>服务器从Session中获取随机数R；</li>
	 * 	<li>服务器调用BJCA服务，验证客户端提交的证书的有效性；</li>
	 * 	<li>服务器通过客户端公钥，验证密文S2有效性，认定客户端身份；</li>
	 * 	<li>服务器通过自身私钥，对S3解密，得到userId；</li>
	 * 	<li>服务器从客户端证书中提取证书唯一标识、姓名、身份证号码等，查验证书是否和用户绑定过；</li>
	 *  <li>通过userId从用户表（sysmgr_user）获得信息；</li>
	 * 	<li>如果比对无误，更新用户表ca_cert_id字段；如果已经绑定过，首先将之前证书ID添加到CA_CERT_ID_HIS字段，然后更新CA_CERT_ID为当前证书唯一标识。</li>
	 * </ol>
	 * @param request
	 * @return
	 */
	public abstract Map<String,Object> bind(HttpServletRequest request);
	
	/**
	 * <h2>登录初始化</h2>
	 * <ol>
	 * 	<li>客户端初始化登录页面，向服务器请求初始化数据；</li>
	 * 	<li> 服务器端生成随机数R，保存在Session中；</li>
	 * 	<li> 使用服务器私钥非对称加密，得到密文S1；</li>
	 * 	<li> 服务器发送随机数R、服务器公钥和密文S1到客户端。</li>
	 * </ol>
	 * @param request
	 * @return
	 */
	public abstract Map<String,Object> preLogin(HttpServletRequest request);
	
	/**
	 * <h3>登录</h3>
	 * 客户端使用私钥对随机数R非对称加密，得到密文S2后，提交登录。
	 * <ol>
	 * 	<li>服务器获得客户端提交的客户端证书（包含公钥）、密文S2；</li>
	 * 	<li>服务器从Session中获取随机数R；</li>
	 * 	<li>服务器调用BJCA服务，验证客户端提交的证书的有效性；</li>
	 * 	<li>服务器通过客户端公钥，验证密文S2有效性，认定客户端身份；</li>
	 * 	<li>服务器从客户端证书中提取证书唯一标识，比对用户表（sysmgr_user），校验证书是否和用户绑定过；</li>
	 * 	<li>如果绑定过，保存证书唯一标识到Session中，完成登录。</li>
	 * </ol>
	 * @param request
	 * @return
	 */
	public abstract Map<String,Object> login(HttpServletRequest request);
	
	/**
	 *<h3>文件签名初始化</h3>
	 * <ol>
	 * 	<li>通过fileid找到物理文件；</li>
	 * 	<li>依据文件全文生成文件摘要D1；</li>
	 * 	<li>将D1返回。</li>
	 * </ol>
	 * @param fileId
	 * @return
	 */
	public abstract Map<String,Object> preSign(HttpServletRequest request);
	
	/**
	 *<h3>文件签名</h3>
	 * 客户端操作：
	 * <ol>
	 * 	<li>客户端获得fileid和文件摘要D1；</li>
	 * 	<li>客户端使用私钥对摘要D1进行签名，得到签名S1（私钥非对称加密）；</li>
	 * 	<li>客户端将客户端证书C1、fileid和文件摘要S1提交到服务器。</li>
	 * </ol>
	 * 服务器端操作：
	 * <ol>
	 * 	<li>服务器获得客户端证书C1、fileid和签名S1；</li>
	 * 	<li>读取证书信息，得到证书唯一标识，比对Session中的证书唯一标识，判断是否当前登录用户；</li>
	 * 	<li>使用客户端证书中的客户端公钥，对签名S1进行解密（公钥非对称解密），得到D2；</li>
	 * 	<li>通过fileid获得物理文件，并计算物流文件摘要D3；</li>
	 *  <li>比对D2和D3是否一致；</li>
	 *  <li>校验成功，保存客户端证书C1、签名S1和fileid到数据库。</li>
	 * </ol>
	 * @param request
	 * @return
	 */
	public abstract Map<String,Object> sign(HttpServletRequest request);
	
	/**
	 * <h3>文件验签</h3>
	 * <ol>
	 * 	<li>根据fileid查询be_wk_file_sign表，获得签名证书C、签名S；</li>
	 * 	<li>使用证书C对签名执行非对称解密，得到D1；</li>
	 * 	<li>通过fileid获得物理文件，对物理文件计算摘要得到D2；</li>
	 * 	<li>比对D1和D2，如果不一致，验签失败，抛出异常；</li>
	 *  <li>验签成功，从证书C1中取得身份信息（姓名、身份证号码、组织信息、联系方式等），从sysmgr_user表获取身份信息。</li>
	 * </ol>
	 * 
	 * @param fileid
	 * @return
	 */
	public abstract Map<String,Object> verSign(HttpServletRequest request);
	
	
	public Map<String,Object> service(String serviceName,HttpServletRequest request);
	
}
