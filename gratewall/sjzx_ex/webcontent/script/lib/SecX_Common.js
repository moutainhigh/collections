/////const //////////////////////////
var CERT_SRC_BASE64	=					1;		//证书来自Base64字符串
var CERT_SRC_UNIQUEID =					2;		//证书来自唯一表示
var CERT_SRC_FILE =						3;		//证书来自der文件
var CERT_SRC_CONTAINER_UCA = 			4;		//证书来自UCA类型证书容器
var CERT_SRC_CONTAINER_SIGN	=			5;		//证书来自容器下签名证书
var CERT_SRC_CONTAINER_ENC =			6;		//证书来自容器下加密证书
var CERT_SRC_CONTAINER_BOTH	=			7;		//证书来自容器下签名加密证书
var CERT_SRC_PKCS12	=					8;		//证书来自PKCS12文件

var CERT_DST_BASE64	=					1;		//导出证书为Base64字符串
var CERT_DST_DERFILE =					2;		//导出证书为der文件
var CERT_DST_P12 =						3;		//到出证书为PKCS12文件

var CERT_XML_SUBJECT =					1;		//从XML配置文件取用户名
var CERT_XML_UNIQUEID =					2;		//从XML配置文件取用户唯一表识
var CERT_XML_DEPT =						3;		//从XML配置文件取用户所有者部门
var CERT_XML_ISSUE =					4;		//从XML配置文件取用户证书颁发者
var CERT_XML_STATE =					5;		//从XML配置文件取用户证书使用状态
var CERT_XML_TRADETYPE =				6;		//从XML配置文件取用户证书应用类型
var CERT_XML_PASSWORD =					7;		//从XML配置文件取用户证书私钥保护口令
var CERT_XML_DEVICETYPE =				8;		//从XML配置文件取用户证书介质类型
var CERT_XML_CATYPE	 =					9;		//从XML配置文件取用户证书CA类型
var CERT_XML_KEYTYPE =					10;		//从XML配置文件取用户证书密钥类型
var CERT_XML_SIGNSN	=					11;		//从XML配置文件取用户签名证书序列号
var CERT_XML_EXCHSN	=					12;		//从XML配置文件取用户加密证书序列号
var CERT_XML_DEVICENAME =				13;		//从XML配置文件取用户证书介质名称
var CERT_XML_DEVICEPROVIDER =			14;		//从XML配置文件取用户证书介质提供者
var CERT_XML_DEVICEAFFIX =				15;		//从XML配置文件取用户证书介质附加库
var CERT_XML_SIGNPATH =					16;		//从XML配置文件取用户签名证书路径
var CERT_XML_EXCHPATH =					17;		//从XML配置文件取用户加密证书路径
var CERT_XML_SIGNPFXPATH =				18;		//从XML配置文件取用户签名P12证书路径
var CERT_XML_EXCHPFXPATH =				19;		//从XML配置文件取用户加密P12证书路径
var CERT_XML_CHAINPATH =				20;		//从XML配置文件取用户证书链路径
var CERT_XML_CRLPATH =					21;		//从XML配置文件取用户证书作废列表路径
var CERT_XML_UNIQUEIDOID =				22;		//从XML配置文件取用户证书UniqueID的OID
var CERT_XML_VERIFYTYPE	=				23;		//从XML配置文件取用户证书验证类型
var CERT_XML_CACOUNTS =					24;		//从XML配置文件取用户证书根证书个数
var CERT_XML_CANUMTYPE =				25;		//从XML配置文件取用户证书跟证书类型

var CRYPT_CFGTYPE_UNSET =				0;		//用户应用类型未定义
var CRYPT_CFGTYPE_CSP =					1;		//用户应用类型CSP
var CRYPT_CFGTYPE_P11 =					2;		//用户应用类型P11
var CRYPT_CFGTYPE_P12 =					3;		//用户应用类型软算法

var ENVELOP_ENC =						1;		//加密P7数字信封
var ENVELOP_DEC =						0;		//解密P7数字信封
var CRYPT_ALG_HASH =					1;		//Hash标志位
var CRYPT_ALG_SYMM =					2;		//对称算法标志位
var CRYPT_ALG_MODE =					3;		//对称算法模式

////CUSTOM CERT OID////////////////////////////////
var CERT_OID_VERSION =					1;		//证书版本号
var CERT_OID_SN =						2;		//证书序列号
var CERT_OID_SIGNALG =					3;		//证书签名算法
var CERT_OID_ISSUERNAME =				4;		//证书颁发者
var CERT_OID_NOTBEFORE =				5;		//证书生效日期
var CERT_OID_NOTAFTER =					6;		//证书过期日期
var CERT_OID_PUBLICKEY =				7;		//证书公钥
var CERT_OID_UNIQUEID =					8;		//证书唯一标识
var g_xmluserlist;

var g_objXML = new CXMLSignRule();

/////define object  /////////////////////////////////
try{
	
var oCert = new ActiveXObject("BJCASecCOM.Certificate");
var oCrypto = new ActiveXObject("BJCASecCOM.Crypto");
var oDevice = new ActiveXObject("BJCASecCOM.DeviceMgr");
var oUtil = new ActiveXObject("BJCASecCOM.Util");


document.writeln("<OBJECT classid=\"clsid:0CF5259B-A812-4B6E-9746-ACF7279FEF74\" height=1 id=USBKEY  style=\"HEIGHT: 1px; LEFT: 10px; TOP: 28px; WIDTH: 1px\" width=1 VIEWASTEXT>");
document.writeln("</OBJECT>");
USBKEY.getUserList();
}
catch(e)
{
	// alert("没有正确安装证书应用环境或者证书应用环境已经损坏！");
}

function addCookie(name,value,expireHours){
             var cookieString=name+"="+escape(value);
             //判断是否设置过期时间
             if(expireHours>0){
                    var date=new Date();
                    date.setTime(date.getTime+expireHours*3600*1000);
                    cookieString=cookieString+"; expire="+date.toGMTString();
             }
             document.cookie=cookieString;
}

function getCookie(name){
             var strCookie=document.cookie;
             var arrCookie=strCookie.split("; ");
             for(var i=0;i<arrCookie.length;i++){
                   var arr=arrCookie[i].split("=");
                   if(arr[0]==name)return arr[1];
             }
             return "";
}

function deleteCookie(name){
              var date=new Date();
              date.setTime(date.getTime()-10000);
              document.cookie=name+"=v; expire="+date.toGMTString();
}

/////组件接口转换为脚本接口////////////////////////

/////Certificate
function importCert(sCertSrc, SrcType, sPwd) {

	if (sPwd != null)
		return oCert.importCert(sCertSrc, SrcType, sPwd);
	else
		return oCert.importCert(sCertSrc, SrcType);
}

function exportCert(DstType, sCertPath){

	if (sCertPath != null)
		return oCert.exportCert(DstType, sCertPath);
	else
		return oCert.exportCert(DstType);
}

function getBasicCertInfoByOID(OID) {

	return oCert.getBasicCertInfoByOID(OID);
}

function getExtCertInfoByOID(sOID) {

	return oCert.getExtCertInfoByOID(sOID);
}

function checkValidaty(sDate) {

	if (sDate != null)
		return oCert.checkValidaty(sDate);
	else
		return oCert.checkValidaty();
}

function validateCert(sCertChain, sCRL) {
	
	if (sCRL != null)
		return oCert.validateCert(sCertChain, sCRL);
	else
		return oCert.validateCert(sCertChain);
}

function modifyPFXPwd(sPFXPath, sOldPwd, sNewPwd) {

	return oCert.modifyPFXPwd(sPFXPath, sOldPwd, sNewPwd);
}

/////Crypto

function setUserCfg(CfgFlag, sCfgValue, sExt1CfgValue, sExt2CfgValue) {

	return oCrypto.setUserCfg(CfgFlag, sCfgValue, sExt1CfgValue, sExt2CfgValue);
}

function setAlgFlag() {

	return oCrypto.setAlgFlag(AlgType, AlgFlag);
}

function signedDataByP7(sInData, sContainerName) {

	if (sContainerName != null)
		return oCrypto.signedDataByP7(sInData, sContainerName);
	else
		return oCrypto.signedDataByP7(sInData);
}

function verifySignedDataByP7(sInData) {

	return oCrypto.verifySignedDataByP7(sInData);
}

function signedData(sInData, sContainerName) {

	if (sContainerName != null)
		return oCrypto.signedData(sInData, sContainerName);
	else
		return oCrypto.signedData(sInData);
}

function verifySignedData(sInData, sCert, sOriData) {

	return oCrypto.verifySignedData(sInData, sCert, sOriData);
}

function envelopedData(sInData, flag, sContainerName) {

	if (sContainerName != null)
		return oCrypto.envelopedData(sInData, flag, sContainerName);
	else
		return oCrypto.envelopedData(sInData, flag);
}

function generateRandom(RandomLen) {

	return oCrypto.generateRandom(RandomLen);
}


function getCertBasicInfo(sCert, OID) {

	oCert.importCert(sCert, CERT_SRC_BASE64);
	
	return oCert.getBasicCertInfoByOID(OID);
	
}

/////Device
function changeUserPin(sCSPName, sExtLib, sOldPin, sNewPin) {

	return oDevice.changeUserPin(sCSPName, sExtLib, sOldPin, sNewPin);
}

function userLogin(sCSPName, sUserPin) {

	return oDevice.userLogin(sCSPName, sUserPin);
}

function enumUserCertificates(sCSPName) {

	return oDevice.enumUserCertificates(sCSPName);
}

function getKeyRetrys(sExtLib) {
	
	 
	return oDevice.getKeyRetrys(sExtLib);
}
/////Util
function EnumUsbKey()
{
	 
	try
	{
		USBKEY.EnumUsbKey();
	}
	catch(e)
	{
		  
	}
   
}
function getUserList_pnp() {
	
	var list;
	try
	{
		list = USBKEY.getUserList();
		return list;
	}
	catch(e)
	{
		 
		return "";
	}
	
}
function getUserList() {

 try
{
	g_xmluserlist = oUtil.getUserList();
}
catch(e)
{
	g_xmluserlist="";
	
}
	return g_xmluserlist;
}

function getUserInfoByContainer_pnp(sContainerName, TypeID) {
	
 	return USBKEY.getUserInfoByContainer(sContainerName, TypeID);
}
function SetLoginState(sContainerName, sUserName) {
	
 	return USBKEY.SetLoginState(sContainerName, sUserName);
}

function getUserInfoByContainer(sContainerName, TypeID) {
	
	return oUtil.getUserInfoByContainer(sContainerName, TypeID);
}

function base64EncodeString(sInData) {

	return oUtil.base64EncodeString(sInData);
}

function base64EncodeFile(sFilePath) {

	return oUtil.base64EncodeFile(sFilePath);
}

function getUserCert(strContainerName)
{  
   var UserCert = getExchCert(strContainerName);
   return UserCert;
}

function getExchCert(strContainerName)
{  
	var strDeviceType = getUserInfoByContainer(strContainerName, CERT_XML_DEVICETYPE);
	var KeyType = getUserInfoByContainer(strContainerName, CERT_XML_KEYTYPE);
	if (strDeviceType == "BJSOFT") {
		//P12软算法
	   var Cert = getUserInfoByContainer(strContainerName,CERT_XML_EXCHPATH);
	   importCert(Cert,CERT_SRC_FILE);
	}
	else if (strDeviceType == "BJCSP0001"){
		//软CSP
	   var Cert = getUserInfoByContainer(strContainerName,CERT_XML_EXCHPATH);
	   importCert(Cert,CERT_SRC_FILE);
	}
	else {
		//智能卡
	 
		var strCSPName = getUserInfoByContainer_pnp(strContainerName, CERT_XML_DEVICEPROVIDER);
		importCert(strContainerName, CERT_SRC_CONTAINER_ENC, strCSPName);
	}
    var UserCert = exportCert(CERT_DST_BASE64);
    return UserCert;
}

function getSignCert(strContainerName)
{  
	var strDeviceType = getUserInfoByContainer(strContainerName, CERT_XML_DEVICETYPE);
	var KeyType = getUserInfoByContainer(strContainerName, CERT_XML_KEYTYPE);
	 
	if (strDeviceType == "BJSOFT") {
		//P12软算法
		if (KeyType == 1) {
			//单证书
		   var Cert = getUserInfoByContainer(strContainerName,CERT_XML_EXCHPATH);
		   importCert(Cert,CERT_SRC_FILE);
		}
		else if (KeyType == 2) {
			//双证书
		   var Cert = getUserInfoByContainer(strContainerName,CERT_XML_SIGNPATH);
		   importCert(Cert,CERT_SRC_FILE);
		}
		else {
			alert("配置文件错误1");
			return false;
		}
	}
	else if (strDeviceType == "BJCSP0001"){
		//软CSP
		if (KeyType == 1) {
			//单证书
		   var Cert = getUserInfoByContainer(strContainerName,CERT_XML_EXCHPATH);
		   importCert(Cert,CERT_SRC_FILE);
		}
		else if (KeyType == 2) {
			//双证书
		   var Cert = getUserInfoByContainer(strContainerName,CERT_XML_SIGNPATH);
		   importCert(Cert,CERT_SRC_FILE);
		}
		else {
			alert("配置文件错误2");
			return false;
		}

	}
	else {
		//智能卡
		 
		var strCSPName = getUserInfoByContainer_pnp(strContainerName, CERT_XML_DEVICEPROVIDER);
		KeyType = getUserInfoByContainer_pnp(strContainerName, CERT_XML_KEYTYPE);
		 
		if (KeyType == 1) {
			//单证书
			importCert(strContainerName, CERT_SRC_CONTAINER_ENC, strCSPName);
		}
		else if (KeyType == 2) {
			//双证书
			importCert(strContainerName, CERT_SRC_CONTAINER_SIGN, strCSPName);
		}
		else {
			alert("配置文件错误3,KeyType="+KeyType+"strContainerName="+strContainerName);
			return false;
		}
	}
	
    var UserCert = exportCert(CERT_DST_BASE64);
    return UserCert;
}

 /*
Function:	getCertDetail  
Parameter:	strContainerName容器名
			ItemNo		    细目类型
 * 0证书PEM编码
 * 1证书版本
 * 2证书序列号
 * 3证书签名算法
 * 4证书发放者国家名
 * 5证书发放者组织名
 * 6证书发放者部门名
 * 7证书发放者省州名
 * 8证书发放者通用名
 * 9证书发放者城市名
 * 10证书发放者EMAIL地址
 * 11证书有效期起始
 * 12证书有效期截止
 * 13用户国家名
 * 14用户组织名
 * 15用户部门名
 * 16用户省州名
 * 17用户通用名
 * 18用户城市名
 * 19用户EMAIL地址
 * 20用户DER公钥值
 * 21用户证书自定义级别	 
 * 22证书UniqueID
 * 23证书剩余有效期
 */
//Parse Cert /////////////////////////////////////
function getCertDetail(strContainerName, ItemNo)
{
	//新版证书应用获取细目
	var sCert = getUserCert(strContainerName);
	switch(ItemNo)
	{
		case 0:
			return sCert;
			break;
		case 8:
			return getCertBasicInfo(sCert,4);
			break;
		case 11:
			return getCertBasicInfo(sCert,5);
			break;
		case 12:
			return getCertBasicInfo(sCert,6);
			break;
		case 13:
			return getCertBasicInfo(sCert,42);
			break;
		case 14:
			return getCertBasicInfo(sCert,45);
			break;
		case 15:
			return getCertBasicInfo(sCert,46);
			break;
		case 16:
			return getCertBasicInfo(sCert,44);
			break;
		case 17:
			return getCertBasicInfo(sCert,41);
			break;
		case 18:
			return getCertBasicInfo(sCert,43);
			break;
		case 20:
			return getCertBasicInfo(sCert,7);
			break;
		case 22:
			return getExtCertInfoByOID("2.16.840.1.113732.2");
			break;
		case 23:
			return checkValidaty();
			break;
		default:
			return getCertBasicInfo(sCert,ItemNo);
			break;
	}
}
 /*
Function:	getCertDetail_Key不需要安装注册从Key中获得证书详细信息
Parameter:	
			ItemNo		    细目类型
			strContainerName容器名
			keyType         key类型  
 * 0证书PEM编码
 * 1证书版本
 * 2证书序列号
 * 3证书签名算法
 * 4证书发放者国家名
 * 5证书发放者组织名
 * 6证书发放者部门名
 * 7证书发放者省州名
 * 8证书发放者通用名
 * 9证书发放者城市名
 * 10证书发放者EMAIL地址
 * 11证书有效期起始
 * 12证书有效期截止
 * 13用户国家名
 * 14用户组织名
 * 15用户部门名
 * 16用户省州名
 * 17用户通用名
 * 18用户城市名
 * 19用户EMAIL地址
 * 20用户DER公钥值
 * 21用户证书自定义级别	 
 * 22证书UniqueID
 * 23证书剩余有效期
 */
//Parse Cert /////////////////////////////////////
function getCertDetail_Key(ItemNo,strContainerName,keyType)
{
	//新版证书应用获取细目
	if(keyType==null || keyType=="")
	{
		keyType = "M&W eKey XCSP";
	}
	importCert(strContainerName, CERT_SRC_CONTAINER_ENC, keyType);
	switch(ItemNo)
	{
		case 0:
			var sCert = exportCert(CERT_DST_BASE64);
			return sCert;
			break;
		case 8:
			return getBasicCertInfoByOID(4);
			break;
		case 11:
			return getBasicCertInfoByOID(5);
			break;
		case 12:
			return getBasicCertInfoByOID(6);
			break;
		case 13:
			return getBasicCertInfoByOID(42);
			break;
		case 14:
			return getBasicCertInfoByOID(45);
			break;
		case 15:
			return getBasicCertInfoByOID(46);
			break;
		case 16:
			return getBasicCertInfoByOID(44);
			break;
		case 17:
			return getBasicCertInfoByOID(41);
			break;
		case 18:
			return getBasicCertInfoByOID(43);
			break;
		case 20:
			return getBasicCertInfoByOID(7);
			break;
		case 22:
			return getExtCertInfoByOID("2.16.840.1.113732.2");
			break;
		case 23:
			return checkValidaty();
			break;
		default:
			return getBasicCertInfoByOID(ItemNo);
			break;
	}

}
 /*
Function:	getCertDetail_Key不需要安装注册从Key中获得证书详细信息
Parameter:	ItemNo		    细目类型
			keyType         key类型
 * 0证书PEM编码
 * 1证书版本
 * 2证书序列号
 * 3证书签名算法
 * 4证书发放者国家名
 * 5证书发放者组织名
 * 6证书发放者部门名
 * 7证书发放者省州名
 * 8证书发放者通用名
 * 9证书发放者城市名
 * 10证书发放者EMAIL地址
 * 11证书有效期起始
 * 12证书有效期截止
 * 13用户国家名
 * 14用户组织名
 * 15用户部门名
 * 16用户省州名
 * 17用户通用名
 * 18用户城市名
 * 19用户EMAIL地址
 * 20用户DER公钥值
 * 21用户证书自定义级别	 
 * 22证书UniqueID
 * 23证书剩余有效期
 */
//Parse Cert /////////////////////////////////////
function getCertDetail_Key(ItemNo, keyType)
{
	//新版证书应用获取细目
	var strTemp;
	var strContainerName;
	var strExpireTime;
	var i;
	var len;
	var sCert;
	if(keyType==null || keyType=="")
	{
		keyType = "M&W eKey XCSP";
	}
	strTemp = enumUserCertificates(keyType);
	while (1) {
		i=strTemp.indexOf("&&&");
		if (i <= 0) {
			break;
		}
		var strContainerNameTmp = strTemp.substring(0,i);
		importCert(strContainerNameTmp, CERT_SRC_CONTAINER_ENC, keyType);
		sCert = exportCert(CERT_DST_BASE64);
		var strExpireTimeTmp = getCertBasicInfo(sCert,6);
		if(strExpireTime == null)
		{
			strExpireTime = strExpireTimeTmp;
			strContainerName = strContainerNameTmp;
		}
		else if(strExpireTime != null && strExpireTime < strExpireTimeTmp)
		{
			strExpireTime = strExpireTimeTmp;
			strContainerName = strContainerNameTmp;
		}
		else
		{
		}

		len = strTemp.length;
		strTemp = strTemp.substring(i+3,len);
	}
	importCert(strContainerName, CERT_SRC_CONTAINER_ENC, keyType);
	switch(ItemNo)
	{
		case 0:
			sCert = exportCert(CERT_DST_BASE64);
			return sCert;
			break;
		case 8:
			return getBasicCertInfoByOID(4);
			break;
		case 11:
			return getBasicCertInfoByOID(5);
			break;
		case 12:
			return getBasicCertInfoByOID(6);
			break;
		case 13:
			return getBasicCertInfoByOID(42);
			break;
		case 14:
			return getBasicCertInfoByOID(45);
			break;
		case 15:
			return getBasicCertInfoByOID(46);
			break;
		case 16:
			return getBasicCertInfoByOID(44);
			break;
		case 17:
			return getBasicCertInfoByOID(41);
			break;
		case 18:
			return getBasicCertInfoByOID(43);
			break;
		case 20:
			return getBasicCertInfoByOID(7);
			break;
		case 22:
			return getExtCertInfoByOID("2.16.840.1.113732.2");
			break;
		case 23:
			return checkValidaty();
			break;
		default:
			return getBasicCertInfoByOID(ItemNo);
			break;
	}
}

function envelopedDecodeData(sInData, sContainerName)
{  
	var decryptData="";
	var strDeviceType = getUserInfoByContainer(sContainerName, CERT_XML_DEVICETYPE);
	if (strDeviceType == "BJSOFT") {
		//P12软算法
	   var exchpfx = getUserInfoByContainer(sContainerName,CERT_XML_EXCHPFXPATH);
	   decryptData = envelopedData(sInData,ENVELOP_DEC,exchpfx);
	}
	else if (strDeviceType == "BJCSP0001"){
		//软CSP
	    decryptData = envelopedData(sInData,ENVELOP_DEC,sContainerName);
	}
	else {
		//智能卡
		decryptData = envelopedData(sInData,ENVELOP_DEC,sContainerName);
	}
    return decryptData;
}

function verifyCertByTrustPool(sCert) {

	//因接口设定原因，增加无用数据
	var sTemp = "temp";

	var sTrustType = null;
	var ret = null;

	importCert(sCert, 1);
	var TrustCount = getUserInfoByContainer(sTemp, CERT_XML_CACOUNTS);
	for (i=1; i<=TrustCount; i++) {
		sTrustType = getUserInfoByContainer(i, CERT_XML_CANUMTYPE);
		sTrustPath = getUserInfoByContainer(sTrustType, 20);
		ret = validateCert(sTrustPath);
		if (ret == 0)
		{
			break;
		}
	}

}

function changeUserPassword(strContainerName,oldPwd,newPwd)
{
	var strP12Path = null;
	var rv = 0;
	var strDeviceType = getUserInfoByContainer(strContainerName, CERT_XML_DEVICETYPE);
	var KeyType = getUserInfoByContainer(strContainerName, CERT_XML_KEYTYPE);
	
	if (strDeviceType == "BJSOFT") {
		//P12软算法
		if (KeyType == 1) {
			//单证书
			strP12Path = getUserInfoByContainer(strContainerName, CERT_XML_EXCHPFXPATH);
			rv = modifyPFXPwd(strP12Path, oldPwd, newPwd);
		}
		else if (KeyType == 2) {
			//双证书
			strP12Path = getUserInfoByContainer(strContainerName, CERT_XML_SIGNPFXPATH);
			var strExchPath = getUserInfoByContainer(strContainerName, CERT_XML_EXCHPFXPATH);
			var rvtmp = modifyPFXPwd(strP12Path, oldPwd, newPwd);
			rv = modifyPFXPwd(strExchPath, oldPwd, newPwd);

		}
		else {
			alert("配置文件错误4");
			return -1;
		}
		setUserCfg(CRYPT_CFGTYPE_P12, strP12Path, newPwd, "");
	}
	else if (strDeviceType == "BJCSP0001"){
		//软CSP
		var strDevType = getUserInfoByContainer(strContainerName, CERT_XML_DEVICETYPE);
		var strCSPName = getUserInfoByContainer(strDevType, CERT_XML_DEVICEPROVIDER);

		if (KeyType == 1) {
			//单证书
			strP12Path = getUserInfoByContainer(strContainerName, CERT_XML_EXCHPFXPATH);
			rv = modifyPFXPwd(strP12Path, oldPwd, newPwd);
		}
		else if (KeyType == 2) {
			//双证书
			strP12Path = getUserInfoByContainer(strContainerName, CERT_XML_SIGNPFXPATH);
			var strExchPath = getUserInfoByContainer(strContainerName, CERT_XML_EXCHPFXPATH);
			var rvtmp = modifyPFXPwd(strP12Path, oldPwd, newPwd);
			rv = modifyPFXPwd(strExchPath, oldPwd, newPwd);
		}
		else {
			alert("配置文件错误5");
			return -1;
		}
		var strExtLib = strContainerName;
			
		setUserCfg(CRYPT_CFGTYPE_CSP, strCSPName, strExtLib, newPwd);
	}
	else {
		//智能卡
		
		var strCSPName = getUserInfoByContainer_pnp(strContainerName, CERT_XML_DEVICEPROVIDER);
		var strExtLib = getUserInfoByContainer_pnp(strContainerName, CERT_XML_DEVICEAFFIX);
	
		if (strExtLib == null)
			strExtLib = "Temp";
		rv = changeUserPin(strCSPName, strExtLib, oldPwd,newPwd);	
		setUserCfg(CRYPT_CFGTYPE_CSP, strCSPName, strExtLib, newPwd);
	}
	return rv;
}

function Login(strFormName,strContainerName,strPin) {
	var ret;
	var objForm = eval(strFormName);

	if (objForm == null) {
		alert("Form Error");
		return false;
	}
	if (strPin == null || strPin == "") {
		alert("请输入Key的保护口令");
		return false;
	}
	//Add a hidden item ...
	var strSignItem = "<input type=\"hidden\" name=\"UserSignedData\" value=\"\">";
	if (objForm.UserSignedData == null) {
		objForm.insertAdjacentHTML("BeforeEnd",strSignItem);
	}
	var strCertItem = "<input type=\"hidden\" name=\"UserCert\" value=\"\">";
	if (objForm.UserCert == null) {
		objForm.insertAdjacentHTML("BeforeEnd",strCertItem);
	}
	var strContainerItem = "<input type=\"hidden\" name=\"ContainerName\" value=\"\">";
	if (objForm.ContainerName == null) {
		objForm.insertAdjacentHTML("BeforeEnd",strContainerItem);
	}

	var strP12Path = null;
	
	var strDeviceType = getUserInfoByContainer(strContainerName, CERT_XML_DEVICETYPE);
	var KeyType = getUserInfoByContainer(strContainerName, CERT_XML_KEYTYPE);
  var strCAType = getUserInfoByContainer(strContainerName, CERT_XML_CATYPE);
 
	
	if (strDeviceType == "BJSOFT") {
		//P12软算法
		if (KeyType == 1) {
			//单证书
			strP12Path = getUserInfoByContainer(strContainerName, CERT_XML_EXCHPFXPATH);
		}
		else if (KeyType == 2) {
			//双证书
			strP12Path = getUserInfoByContainer(strContainerName, CERT_XML_SIGNPFXPATH);
		}
		else {
			alert("配置文件错误6");
			return false;
		}
		
		if (importCert(strP12Path, CERT_SRC_PKCS12, strPin) != 0) {
			alert("口令错误");
			return false;
		}
		setUserCfg(CRYPT_CFGTYPE_P12, strP12Path, strPin, "");
		var strClientSignedData = signedData(strServerRan,strContainerName);
	 
	}
	else if (strDeviceType == "BJCSP0001"){
		//软CSP
		var strDevType = getUserInfoByContainer(strContainerName, CERT_XML_DEVICETYPE);
		var strCSPName = getUserInfoByContainer(strDevType, CERT_XML_DEVICEPROVIDER);

		if (KeyType == 1) {
			//单证书
			strP12Path = getUserInfoByContainer(strContainerName, CERT_XML_EXCHPFXPATH);
		}
		else if (KeyType == 2) {
			//双证书
			strP12Path = getUserInfoByContainer(strContainerName, CERT_XML_SIGNPFXPATH);
		}
		else {
			alert("配置文件错误7");
			return false;
		}
		
		if (importCert(strP12Path, CERT_SRC_PKCS12, strPin) != 0) {
			alert("口令错误");
			return false;
		}
		
		var strExtLib = strContainerName;
			
		setUserCfg(CRYPT_CFGTYPE_CSP, strCSPName, strExtLib, strPin);
		var strClientSignedData = signedData(strServerRan, strContainerName);

	}
	else {
		//智能卡
		var strCSPName = getUserInfoByContainer_pnp(strContainerName, CERT_XML_DEVICEPROVIDER);
		var strExtLib = getUserInfoByContainer_pnp(strContainerName, CERT_XML_DEVICEAFFIX);
		var strUserName = getUserInfoByContainer_pnp(strContainerName, CERT_XML_SUBJECT);
	 KeyType = getUserInfoByContainer_pnp(strContainerName, CERT_XML_KEYTYPE);
		
		if (strExtLib == null)
			strExtLib = "Temp";
		ret = userLogin(strCSPName, strPin);

		if (ret != 0 ){
			var retryNum = getKeyRetrys(strExtLib);
			switch (retryNum) {
				case -1010:
					alert("未正确插入智能卡");
					return false;
					break;
				case -1011:
					alert("智能卡操作失败");
					return false;
					break;
				default:
					alert("口令错误,重试还剩下"+retryNum+"次");
					return false;
					break;
			}
		}
		
		if (KeyType == 1) {
			//单证书
			if(importCert(strContainerName, CERT_SRC_CONTAINER_ENC, strCSPName) != 0){
				alert("口令错误或未正确插入智能卡");
				return false;
			}
		}
		else if (KeyType == 2) {
			//双证书
			if(importCert(strContainerName, CERT_SRC_CONTAINER_SIGN, strCSPName)!= 0){
				alert("口令错误或未正确插入智能卡");
				return false;
			}
		}
		else {
			alert("配置文件错误8");
			return false;
		}
		 
	  
		setUserCfg(CRYPT_CFGTYPE_CSP, strCSPName, strExtLib, strPin);
		var strClientSignedData = signedData(strServerRan, strContainerName);
		 
	}
	 

	objForm.UserSignedData.value = strClientSignedData;
	objForm.UserCert.value = exportCert(CERT_DST_BASE64);
	objForm.ContainerName.value = strContainerName;
	SetLoginState(strContainerName,strUserName);
	addCookie("SecX_ContainerName", strContainerName, 24000);
	addCookie("SecX_Pwd", strPin, 24000);
	var rv = checkValidaty();
	return alertValidDay(rv);
	 

}

function alertValidDay(ret)
{
 	var ValidDay;
	ValidDay = parseInt(ret);
    if(parseInt(ret) > 365){
       // alert("证书还未生效!");
	   //return false;
	}
	
	if (parseInt(ret) <= 60 && parseInt(ret) > 0) {
		alert("您的证书还有" + ValidDay + "天过期，\n请您尽快到北京数字证书认证中心办理证书更新手续，\n否则会影响您的正常使用，造成不必要的麻烦和损失。\n证书用户注意查看告知事项，具体更新手续请登录:\nhttp://www.bjca.org.cn；咨询电话：82031677-8686。");
	}
	
	if(parseInt(ret) <= -45)
	{
	    alert("您的证书已过期 "+ -parseInt(ret) +" 天，超过了最后使用期限！\n请到北京数字证书认证中心办理证书更新手续！\n\n证书用户注意查看告知事项，具体更新手续请登录:\nhttp://www.bjca.org.cn；咨询电话：82031677-8686。");
	    return false;
	}
		
	if(parseInt(ret) <= 0){
	    alert("您的证书已过期 "+ -parseInt(ret) +" 天，\n请尽快到北京数字证书认证中心办理证书更新手续，\n否则会影响您的正常使用，造成不必要的麻烦和损失。\n证书用户注意查看告知事项，具体更新手续请登录:\nhttp://www.bjca.org.cn；咨询电话：82031677-8686。");
	}
	return true;
}
function getKeySN(strContainerName) 
 {
 	 //var strDevType = getUserInfoByContainer(strContainerName, CERT_XML_DEVICETYPE);
 	 var strExtLib = getUserInfoByContainer_pnp(strContainerName, CERT_XML_DEVICEAFFIX);
   var sn = oDevice.getKeySN(strExtLib);
    
   return sn;
 }
 
 
 function ReadPfxToBca(sContainerName, KeyUsage, sPassword, sPfxFile) {

	return oCrypto.ReadPfxToBca(sContainerName, KeyUsage, sPassword, sPfxFile)
}
function importpfxtobca(sContainerName, KeyType, sPassword) {

	if (KeyType == 1) {
		var sPfxFile = getUserInfoByContainer(sContainerName, CERT_XML_EXCHPFXPATH);
		var ret = ReadPfxToBca(sContainerName, 1, sPassword, sPfxFile);
		return ret;
	}//单证书
	else if (KeyType == 2) {
			//双证书
			var sPfxFile = getUserInfoByContainer(sContainerName, CERT_XML_EXCHPFXPATH);
		var ret = ReadPfxToBca(sContainerName, 1, sPassword, sPfxFile);
		if(ret != 0)
			return ret;
		sPfxFile = getUserInfoByContainer(sContainerName, CERT_XML_SIGNPFXPATH);
		return ret = ReadPfxToBca(sContainerName, 2, sPassword, sPfxFile);
		}
}
 
 
//文件签名 返回签名数据
function SignFile(sFileName,sContainerName)
{
	 return oCrypto.signFile(sFileName,sContainerName);
}

function verifySignFile(sFileName,sCert,SignData)
{
	 return oCrypto.verifySignFile(sFileName,sCert,SignData);
}
 function GetSavedPass(strContainerName)
{
	var strUserName = getUserInfoByContainer_pnp(strContainerName,CERT_XML_SUBJECT);
	var pass = USBKEY.GetSavedPass(strUserName);
	return pass;
}
 function SaveUSKKeyPass(strContainerName,strPass)
{
	var strUserName = getUserInfoByContainer_pnp(strContainerName,CERT_XML_SUBJECT);
	return USBKEY.SaveUserPass(strUserName,strPass);
}
function signedDataXML(signdata,ContainerName)
{
	return oCrypto.signedDataXML(signdata,ContainerName);
}
function verifySignXML(signxml)
{
	return oCrypto.verifySignedDataXML(signxml);
}

function getxmldata(signxml,i)
{
	return oCrypto.getXMLSignatureInfo(signxml,i);
}
function CXMLSignRule()
{
	 
	this.XMLHeader = "<?xml version=\"1.0\" encoding=\"GB2312\"?> ";
	this.XMLDoc = new ActiveXObject("Microsoft.XMLDOM");
	this.XMLDoc.async = false;
	this.XMLDoc.loadXML("<SecXMSG></SecXMSG>");
}
function gFuncURLEncode(str)
{
	//return str;
	var str1 = str;
	var i, c;
	var ret = ""
	var strSpecial = "!\"#$%&'()*+,/:;<=>?@[\]^`{|}~%";
	for(i = 0; i < str1.length ;i++ ){
			c=str1.charAt(i);
			if(c==" ")
				str1=str1.replace(" ","+");
			else if(strSpecial.indexOf(c)!=-1)
			{
				var temp = "%"+str1.charCodeAt(i).toString(16);
				str1 =str1.replace(c,temp);
				i=i+temp.length - 1;
			}

	}
	return str1;
}
function gFuncFormItem2XML(strTag, strType, strValue)
{
	if (strTag == "")
	{
		 return false;
	}
	
	strTag = gFuncURLEncode(strTag);
	var strPath = "/SecXMSG";
	var objNode = g_objXML.XMLDoc.selectSingleNode(strPath);

	var objTemp = g_objXML.XMLDoc.createElement(strTag);

	objNode.appendChild(objTemp);

	objTemp.setAttribute("Type", strType);
	
	var objNode = g_objXML.XMLDoc.selectSingleNode("/SecXMSG/"+ strTag);
	objNode.text = strValue;
	
	//var objCDATA = g_objXML.XMLDoc.createCDATASection(strValue);
	//objTemp.appendChild(objCDATA);
	return true;
}
 



