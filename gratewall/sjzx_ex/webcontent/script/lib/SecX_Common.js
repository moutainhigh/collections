/////const //////////////////////////
var CERT_SRC_BASE64	=					1;		//֤������Base64�ַ���
var CERT_SRC_UNIQUEID =					2;		//֤������Ψһ��ʾ
var CERT_SRC_FILE =						3;		//֤������der�ļ�
var CERT_SRC_CONTAINER_UCA = 			4;		//֤������UCA����֤������
var CERT_SRC_CONTAINER_SIGN	=			5;		//֤������������ǩ��֤��
var CERT_SRC_CONTAINER_ENC =			6;		//֤�����������¼���֤��
var CERT_SRC_CONTAINER_BOTH	=			7;		//֤������������ǩ������֤��
var CERT_SRC_PKCS12	=					8;		//֤������PKCS12�ļ�

var CERT_DST_BASE64	=					1;		//����֤��ΪBase64�ַ���
var CERT_DST_DERFILE =					2;		//����֤��Ϊder�ļ�
var CERT_DST_P12 =						3;		//����֤��ΪPKCS12�ļ�

var CERT_XML_SUBJECT =					1;		//��XML�����ļ�ȡ�û���
var CERT_XML_UNIQUEID =					2;		//��XML�����ļ�ȡ�û�Ψһ��ʶ
var CERT_XML_DEPT =						3;		//��XML�����ļ�ȡ�û������߲���
var CERT_XML_ISSUE =					4;		//��XML�����ļ�ȡ�û�֤��䷢��
var CERT_XML_STATE =					5;		//��XML�����ļ�ȡ�û�֤��ʹ��״̬
var CERT_XML_TRADETYPE =				6;		//��XML�����ļ�ȡ�û�֤��Ӧ������
var CERT_XML_PASSWORD =					7;		//��XML�����ļ�ȡ�û�֤��˽Կ��������
var CERT_XML_DEVICETYPE =				8;		//��XML�����ļ�ȡ�û�֤���������
var CERT_XML_CATYPE	 =					9;		//��XML�����ļ�ȡ�û�֤��CA����
var CERT_XML_KEYTYPE =					10;		//��XML�����ļ�ȡ�û�֤����Կ����
var CERT_XML_SIGNSN	=					11;		//��XML�����ļ�ȡ�û�ǩ��֤�����к�
var CERT_XML_EXCHSN	=					12;		//��XML�����ļ�ȡ�û�����֤�����к�
var CERT_XML_DEVICENAME =				13;		//��XML�����ļ�ȡ�û�֤���������
var CERT_XML_DEVICEPROVIDER =			14;		//��XML�����ļ�ȡ�û�֤������ṩ��
var CERT_XML_DEVICEAFFIX =				15;		//��XML�����ļ�ȡ�û�֤����ʸ��ӿ�
var CERT_XML_SIGNPATH =					16;		//��XML�����ļ�ȡ�û�ǩ��֤��·��
var CERT_XML_EXCHPATH =					17;		//��XML�����ļ�ȡ�û�����֤��·��
var CERT_XML_SIGNPFXPATH =				18;		//��XML�����ļ�ȡ�û�ǩ��P12֤��·��
var CERT_XML_EXCHPFXPATH =				19;		//��XML�����ļ�ȡ�û�����P12֤��·��
var CERT_XML_CHAINPATH =				20;		//��XML�����ļ�ȡ�û�֤����·��
var CERT_XML_CRLPATH =					21;		//��XML�����ļ�ȡ�û�֤�������б�·��
var CERT_XML_UNIQUEIDOID =				22;		//��XML�����ļ�ȡ�û�֤��UniqueID��OID
var CERT_XML_VERIFYTYPE	=				23;		//��XML�����ļ�ȡ�û�֤����֤����
var CERT_XML_CACOUNTS =					24;		//��XML�����ļ�ȡ�û�֤���֤�����
var CERT_XML_CANUMTYPE =				25;		//��XML�����ļ�ȡ�û�֤���֤������

var CRYPT_CFGTYPE_UNSET =				0;		//�û�Ӧ������δ����
var CRYPT_CFGTYPE_CSP =					1;		//�û�Ӧ������CSP
var CRYPT_CFGTYPE_P11 =					2;		//�û�Ӧ������P11
var CRYPT_CFGTYPE_P12 =					3;		//�û�Ӧ���������㷨

var ENVELOP_ENC =						1;		//����P7�����ŷ�
var ENVELOP_DEC =						0;		//����P7�����ŷ�
var CRYPT_ALG_HASH =					1;		//Hash��־λ
var CRYPT_ALG_SYMM =					2;		//�Գ��㷨��־λ
var CRYPT_ALG_MODE =					3;		//�Գ��㷨ģʽ

////CUSTOM CERT OID////////////////////////////////
var CERT_OID_VERSION =					1;		//֤��汾��
var CERT_OID_SN =						2;		//֤�����к�
var CERT_OID_SIGNALG =					3;		//֤��ǩ���㷨
var CERT_OID_ISSUERNAME =				4;		//֤��䷢��
var CERT_OID_NOTBEFORE =				5;		//֤����Ч����
var CERT_OID_NOTAFTER =					6;		//֤���������
var CERT_OID_PUBLICKEY =				7;		//֤�鹫Կ
var CERT_OID_UNIQUEID =					8;		//֤��Ψһ��ʶ
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
	// alert("û����ȷ��װ֤��Ӧ�û�������֤��Ӧ�û����Ѿ��𻵣�");
}

function addCookie(name,value,expireHours){
             var cookieString=name+"="+escape(value);
             //�ж��Ƿ����ù���ʱ��
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

/////����ӿ�ת��Ϊ�ű��ӿ�////////////////////////

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
		//P12���㷨
	   var Cert = getUserInfoByContainer(strContainerName,CERT_XML_EXCHPATH);
	   importCert(Cert,CERT_SRC_FILE);
	}
	else if (strDeviceType == "BJCSP0001"){
		//��CSP
	   var Cert = getUserInfoByContainer(strContainerName,CERT_XML_EXCHPATH);
	   importCert(Cert,CERT_SRC_FILE);
	}
	else {
		//���ܿ�
	 
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
		//P12���㷨
		if (KeyType == 1) {
			//��֤��
		   var Cert = getUserInfoByContainer(strContainerName,CERT_XML_EXCHPATH);
		   importCert(Cert,CERT_SRC_FILE);
		}
		else if (KeyType == 2) {
			//˫֤��
		   var Cert = getUserInfoByContainer(strContainerName,CERT_XML_SIGNPATH);
		   importCert(Cert,CERT_SRC_FILE);
		}
		else {
			alert("�����ļ�����1");
			return false;
		}
	}
	else if (strDeviceType == "BJCSP0001"){
		//��CSP
		if (KeyType == 1) {
			//��֤��
		   var Cert = getUserInfoByContainer(strContainerName,CERT_XML_EXCHPATH);
		   importCert(Cert,CERT_SRC_FILE);
		}
		else if (KeyType == 2) {
			//˫֤��
		   var Cert = getUserInfoByContainer(strContainerName,CERT_XML_SIGNPATH);
		   importCert(Cert,CERT_SRC_FILE);
		}
		else {
			alert("�����ļ�����2");
			return false;
		}

	}
	else {
		//���ܿ�
		 
		var strCSPName = getUserInfoByContainer_pnp(strContainerName, CERT_XML_DEVICEPROVIDER);
		KeyType = getUserInfoByContainer_pnp(strContainerName, CERT_XML_KEYTYPE);
		 
		if (KeyType == 1) {
			//��֤��
			importCert(strContainerName, CERT_SRC_CONTAINER_ENC, strCSPName);
		}
		else if (KeyType == 2) {
			//˫֤��
			importCert(strContainerName, CERT_SRC_CONTAINER_SIGN, strCSPName);
		}
		else {
			alert("�����ļ�����3,KeyType="+KeyType+"strContainerName="+strContainerName);
			return false;
		}
	}
	
    var UserCert = exportCert(CERT_DST_BASE64);
    return UserCert;
}

 /*
Function:	getCertDetail  
Parameter:	strContainerName������
			ItemNo		    ϸĿ����
 * 0֤��PEM����
 * 1֤��汾
 * 2֤�����к�
 * 3֤��ǩ���㷨
 * 4֤�鷢���߹�����
 * 5֤�鷢������֯��
 * 6֤�鷢���߲�����
 * 7֤�鷢����ʡ����
 * 8֤�鷢����ͨ����
 * 9֤�鷢���߳�����
 * 10֤�鷢����EMAIL��ַ
 * 11֤����Ч����ʼ
 * 12֤����Ч�ڽ�ֹ
 * 13�û�������
 * 14�û���֯��
 * 15�û�������
 * 16�û�ʡ����
 * 17�û�ͨ����
 * 18�û�������
 * 19�û�EMAIL��ַ
 * 20�û�DER��Կֵ
 * 21�û�֤���Զ��弶��	 
 * 22֤��UniqueID
 * 23֤��ʣ����Ч��
 */
//Parse Cert /////////////////////////////////////
function getCertDetail(strContainerName, ItemNo)
{
	//�°�֤��Ӧ�û�ȡϸĿ
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
Function:	getCertDetail_Key����Ҫ��װע���Key�л��֤����ϸ��Ϣ
Parameter:	
			ItemNo		    ϸĿ����
			strContainerName������
			keyType         key����  
 * 0֤��PEM����
 * 1֤��汾
 * 2֤�����к�
 * 3֤��ǩ���㷨
 * 4֤�鷢���߹�����
 * 5֤�鷢������֯��
 * 6֤�鷢���߲�����
 * 7֤�鷢����ʡ����
 * 8֤�鷢����ͨ����
 * 9֤�鷢���߳�����
 * 10֤�鷢����EMAIL��ַ
 * 11֤����Ч����ʼ
 * 12֤����Ч�ڽ�ֹ
 * 13�û�������
 * 14�û���֯��
 * 15�û�������
 * 16�û�ʡ����
 * 17�û�ͨ����
 * 18�û�������
 * 19�û�EMAIL��ַ
 * 20�û�DER��Կֵ
 * 21�û�֤���Զ��弶��	 
 * 22֤��UniqueID
 * 23֤��ʣ����Ч��
 */
//Parse Cert /////////////////////////////////////
function getCertDetail_Key(ItemNo,strContainerName,keyType)
{
	//�°�֤��Ӧ�û�ȡϸĿ
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
Function:	getCertDetail_Key����Ҫ��װע���Key�л��֤����ϸ��Ϣ
Parameter:	ItemNo		    ϸĿ����
			keyType         key����
 * 0֤��PEM����
 * 1֤��汾
 * 2֤�����к�
 * 3֤��ǩ���㷨
 * 4֤�鷢���߹�����
 * 5֤�鷢������֯��
 * 6֤�鷢���߲�����
 * 7֤�鷢����ʡ����
 * 8֤�鷢����ͨ����
 * 9֤�鷢���߳�����
 * 10֤�鷢����EMAIL��ַ
 * 11֤����Ч����ʼ
 * 12֤����Ч�ڽ�ֹ
 * 13�û�������
 * 14�û���֯��
 * 15�û�������
 * 16�û�ʡ����
 * 17�û�ͨ����
 * 18�û�������
 * 19�û�EMAIL��ַ
 * 20�û�DER��Կֵ
 * 21�û�֤���Զ��弶��	 
 * 22֤��UniqueID
 * 23֤��ʣ����Ч��
 */
//Parse Cert /////////////////////////////////////
function getCertDetail_Key(ItemNo, keyType)
{
	//�°�֤��Ӧ�û�ȡϸĿ
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
		//P12���㷨
	   var exchpfx = getUserInfoByContainer(sContainerName,CERT_XML_EXCHPFXPATH);
	   decryptData = envelopedData(sInData,ENVELOP_DEC,exchpfx);
	}
	else if (strDeviceType == "BJCSP0001"){
		//��CSP
	    decryptData = envelopedData(sInData,ENVELOP_DEC,sContainerName);
	}
	else {
		//���ܿ�
		decryptData = envelopedData(sInData,ENVELOP_DEC,sContainerName);
	}
    return decryptData;
}

function verifyCertByTrustPool(sCert) {

	//��ӿ��趨ԭ��������������
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
		//P12���㷨
		if (KeyType == 1) {
			//��֤��
			strP12Path = getUserInfoByContainer(strContainerName, CERT_XML_EXCHPFXPATH);
			rv = modifyPFXPwd(strP12Path, oldPwd, newPwd);
		}
		else if (KeyType == 2) {
			//˫֤��
			strP12Path = getUserInfoByContainer(strContainerName, CERT_XML_SIGNPFXPATH);
			var strExchPath = getUserInfoByContainer(strContainerName, CERT_XML_EXCHPFXPATH);
			var rvtmp = modifyPFXPwd(strP12Path, oldPwd, newPwd);
			rv = modifyPFXPwd(strExchPath, oldPwd, newPwd);

		}
		else {
			alert("�����ļ�����4");
			return -1;
		}
		setUserCfg(CRYPT_CFGTYPE_P12, strP12Path, newPwd, "");
	}
	else if (strDeviceType == "BJCSP0001"){
		//��CSP
		var strDevType = getUserInfoByContainer(strContainerName, CERT_XML_DEVICETYPE);
		var strCSPName = getUserInfoByContainer(strDevType, CERT_XML_DEVICEPROVIDER);

		if (KeyType == 1) {
			//��֤��
			strP12Path = getUserInfoByContainer(strContainerName, CERT_XML_EXCHPFXPATH);
			rv = modifyPFXPwd(strP12Path, oldPwd, newPwd);
		}
		else if (KeyType == 2) {
			//˫֤��
			strP12Path = getUserInfoByContainer(strContainerName, CERT_XML_SIGNPFXPATH);
			var strExchPath = getUserInfoByContainer(strContainerName, CERT_XML_EXCHPFXPATH);
			var rvtmp = modifyPFXPwd(strP12Path, oldPwd, newPwd);
			rv = modifyPFXPwd(strExchPath, oldPwd, newPwd);
		}
		else {
			alert("�����ļ�����5");
			return -1;
		}
		var strExtLib = strContainerName;
			
		setUserCfg(CRYPT_CFGTYPE_CSP, strCSPName, strExtLib, newPwd);
	}
	else {
		//���ܿ�
		
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
		alert("������Key�ı�������");
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
		//P12���㷨
		if (KeyType == 1) {
			//��֤��
			strP12Path = getUserInfoByContainer(strContainerName, CERT_XML_EXCHPFXPATH);
		}
		else if (KeyType == 2) {
			//˫֤��
			strP12Path = getUserInfoByContainer(strContainerName, CERT_XML_SIGNPFXPATH);
		}
		else {
			alert("�����ļ�����6");
			return false;
		}
		
		if (importCert(strP12Path, CERT_SRC_PKCS12, strPin) != 0) {
			alert("�������");
			return false;
		}
		setUserCfg(CRYPT_CFGTYPE_P12, strP12Path, strPin, "");
		var strClientSignedData = signedData(strServerRan,strContainerName);
	 
	}
	else if (strDeviceType == "BJCSP0001"){
		//��CSP
		var strDevType = getUserInfoByContainer(strContainerName, CERT_XML_DEVICETYPE);
		var strCSPName = getUserInfoByContainer(strDevType, CERT_XML_DEVICEPROVIDER);

		if (KeyType == 1) {
			//��֤��
			strP12Path = getUserInfoByContainer(strContainerName, CERT_XML_EXCHPFXPATH);
		}
		else if (KeyType == 2) {
			//˫֤��
			strP12Path = getUserInfoByContainer(strContainerName, CERT_XML_SIGNPFXPATH);
		}
		else {
			alert("�����ļ�����7");
			return false;
		}
		
		if (importCert(strP12Path, CERT_SRC_PKCS12, strPin) != 0) {
			alert("�������");
			return false;
		}
		
		var strExtLib = strContainerName;
			
		setUserCfg(CRYPT_CFGTYPE_CSP, strCSPName, strExtLib, strPin);
		var strClientSignedData = signedData(strServerRan, strContainerName);

	}
	else {
		//���ܿ�
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
					alert("δ��ȷ�������ܿ�");
					return false;
					break;
				case -1011:
					alert("���ܿ�����ʧ��");
					return false;
					break;
				default:
					alert("�������,���Ի�ʣ��"+retryNum+"��");
					return false;
					break;
			}
		}
		
		if (KeyType == 1) {
			//��֤��
			if(importCert(strContainerName, CERT_SRC_CONTAINER_ENC, strCSPName) != 0){
				alert("��������δ��ȷ�������ܿ�");
				return false;
			}
		}
		else if (KeyType == 2) {
			//˫֤��
			if(importCert(strContainerName, CERT_SRC_CONTAINER_SIGN, strCSPName)!= 0){
				alert("��������δ��ȷ�������ܿ�");
				return false;
			}
		}
		else {
			alert("�����ļ�����8");
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
       // alert("֤�黹δ��Ч!");
	   //return false;
	}
	
	if (parseInt(ret) <= 60 && parseInt(ret) > 0) {
		alert("����֤�黹��" + ValidDay + "����ڣ�\n�������쵽��������֤����֤���İ���֤�����������\n�����Ӱ����������ʹ�ã���ɲ���Ҫ���鷳����ʧ��\n֤���û�ע��鿴��֪�����������������¼:\nhttp://www.bjca.org.cn����ѯ�绰��82031677-8686��");
	}
	
	if(parseInt(ret) <= -45)
	{
	    alert("����֤���ѹ��� "+ -parseInt(ret) +" �죬���������ʹ�����ޣ�\n�뵽��������֤����֤���İ���֤�����������\n\n֤���û�ע��鿴��֪�����������������¼:\nhttp://www.bjca.org.cn����ѯ�绰��82031677-8686��");
	    return false;
	}
		
	if(parseInt(ret) <= 0){
	    alert("����֤���ѹ��� "+ -parseInt(ret) +" �죬\n�뾡�쵽��������֤����֤���İ���֤�����������\n�����Ӱ����������ʹ�ã���ɲ���Ҫ���鷳����ʧ��\n֤���û�ע��鿴��֪�����������������¼:\nhttp://www.bjca.org.cn����ѯ�绰��82031677-8686��");
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
	}//��֤��
	else if (KeyType == 2) {
			//˫֤��
			var sPfxFile = getUserInfoByContainer(sContainerName, CERT_XML_EXCHPFXPATH);
		var ret = ReadPfxToBca(sContainerName, 1, sPassword, sPfxFile);
		if(ret != 0)
			return ret;
		sPfxFile = getUserInfoByContainer(sContainerName, CERT_XML_SIGNPFXPATH);
		return ret = ReadPfxToBca(sContainerName, 2, sPassword, sPfxFile);
		}
}
 
 
//�ļ�ǩ�� ����ǩ������
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
 



