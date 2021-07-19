
var WCMOBJ_PROPERTIES_NODE_NAME = "PROPERTIES";
var CDATA_REPLACER = "(TRSWCM_CDATA_END_HOLDER_TRSWCM)";

function isArray(a) {
    var bResult =  isObject(a) && a.constructor == Array;
	return bResult;
}

function isWCMObj(a) {	
    return isObject(a) && a.constructor == CWCMObj;
}

function isObject(a) {
    return (a && typeof a == 'object') || isFunction(a);
}

function isFunction(a) {
    return typeof a == 'function';
}

function getNodeText(_node){
	if(_node.childNodes.length==0){
		return _node.nodeValue;
	}

	return getNodeText(_node.childNodes[0]);
}

function CWCMObjHelper_fromXMLNode(_xmlNode){
	var nSize = _xmlNode.childNodes.length;
	for(var i=0; i<nSize; i++){
		if(_xmlNode.childNodes[i].nodeName.toUpperCase() == WCMOBJ_PROPERTIES_NODE_NAME){
			var oWCMObj = new CWCMObj();
			var nodeProperties = _xmlNode.childNodes[i];
			var nPropertySize = nodeProperties.childNodes.length;
			for(var j=0; j<nPropertySize; j++){
				var sPropName	= nodeProperties.childNodes[j].nodeName.toUpperCase();
				var sValue		= getNodeText(nodeProperties.childNodes[j]);
				if(sValue == null)sValue = "";
				if(sValue.indexOf(CDATA_REPLACER) >= 0) sValue = TRSString.decodeForCDATA(sValue);
				//oWCMObj.hProperty[sPropName] = sValue;
				oWCMObj.setProperty(sPropName, sValue);
			}
			return oWCMObj;
		}
	}
	return null;
}

function CWCMObjHelper_parseXMLStrToObjs(_sXML){
	var arWCMObjs = new Array();
	if(_sXML == null || _sXML.length==0)
		return arWCMObjs;

	var xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
	try{
		xmlDoc.loadXML(_sXML);
	}catch(e){
		delete xmlDoc;
		CTRSAction_alert("XML Data InValid!\n XML Data: \n"+_sXML);
		return arWCMObjs;
	}
	
	
	var nSize = xmlDoc.documentElement.childNodes.length;
	for(var i=0; i<nSize; i++)
		arWCMObjs[arWCMObjs.length] = this.fromXMLNode(xmlDoc.documentElement.childNodes[i]);

	delete xmlDoc;
	return arWCMObjs;
}

function CWCMObjHelper_parseXMLStrToObj(_sXML){	
	if(_sXML == null || _sXML.length==0)
		return null;

	var xmlDoc = new ActiveXObject("Microsoft.XMLDOM"); //初始化XMLHttp
	try{
		xmlDoc.loadXML(_sXML);
	}catch(e){
		delete xmlDoc;
		//CTRSAction_alert("XML Data InValid!\n XML Data: \n"+_sXML);
		return null;
	}
	
	//wenyh@2005-7-25 16:00:05 add comment:modify.
	try{
		var oWCMObj = this.fromXMLNode(xmlDoc.documentElement);
		delete xmlDoc;
		return oWCMObj;
	}catch(e){
		return null;
	}
	
}

function CWCMObjHelper_parseXMLURLToObj(_sXMLURL, _sPostData){
	//1.verify parameters
	var oHttp = new ActiveXObject("Microsoft.XMLHTTP"); 
	
	oHttp.open("POST", _sXMLURL, false);
	
	oHttp.send(_sPostData);

	delete oHttp;
	
	return this.parseXMLStrToObj(oHttp.responseText);	
}


function CWCMObjHelper_parseXMLURLToObjs(_sXMLURL, _sPostData){
	//1.verify parameters
	var oHttp = new ActiveXObject("Microsoft.XMLHTTP");
	
	oHttp.open("POST", _sXMLURL, false);
	
	oHttp.send(_sPostData);

	delete oHttp;

	return this.parseXMLStrToObjs(oHttp.responseText);	
}

/*
function CWCMObjHelper_toXMLString(_toObject, _sRootTagName, _sObjTagName){
	if(_sRootTagName == null || _sRootTagName.length==0){
		CTRSAction_alert("Invalid Root Tag Name!");
		return null;
	}

	var sResult = "<"+_sRootTagName.toUpperCase()+">\n";
	if(isArray(_toObject))
	{//WCMObjs				
		for(var i=0; i<_toObject.length; i++){
			sResult += this.toXMLString(_toObject[i], _sObjTagName) + "\n";
		}
	}
	else if(isWCMObj(_toObject))
	{//WCMObj
		var xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
		xmlDoc.loadXML("<"+WCMOBJ_PROPERTIES_NODE_NAME+"></"+WCMOBJ_PROPERTIES_NODE_NAME+">");
		var arKeys = _toObject.getKeys(), sValue, sKey;
		for(var i=0; i<arKeys.length; i++){
			sKey	= arKeys[i];
			sValue	= _toObject.getProperty(sKey);
			
			var newNode = xmlDoc.createElement(sKey.toUpperCase()), newNodeValue;
			if(isNaN(sValue)){
				newNodeValue = xmlDoc.createCDATASection(sValue);
			}else{
				newNodeValue = xmlDoc.createTextNode(sValue);
			}
			newNode.appendChild(newNodeValue);

			xmlDoc.documentElement.appendChild(newNode);
		}
		
		sResult += xmlDoc.xml;
	}
	else
	{//Invalid Object
		CTRSAction_alert("Invalid Object!");
		return null;
	}
	sResult += "</"+_sRootTagName.toUpperCase()+">";
	return sResult;
}
*/


function CWCMObjHelper_toXMLString(_toObject, _sRootTagName, _sObjTagName){
	if(_sRootTagName == null || _sRootTagName.length==0){
		CTRSAction_alert("Invalid Root Tag Name!");
		return null;
	}

	var sResult = "<"+_sRootTagName.toUpperCase()+">\n";
	if(isArray(_toObject))
	{//WCMObjs				
		for(var i=0; i<_toObject.length; i++){
			sResult += this.toXMLString(_toObject[i], _sObjTagName) + "\n";
		}
	}
	else if(isWCMObj(_toObject))
	{//WCMObj
		sResult += "<"+WCMOBJ_PROPERTIES_NODE_NAME+">";
		var arKeys = _toObject.getKeys(), sValue, sKey;
		for(var i=0; i<arKeys.length; i++){
			sKey	= arKeys[i];
			sValue	= _toObject.getProperty(sKey);
			//需要处理CDATA关键字转义
			if(sValue && sValue.indexOf && sValue.indexOf("]]") >= 0){
				sValue = "`" + sValue + "`";
				sValue = TRSString.encodeForCDATA(sValue);
			}
			
			sResult += "<"+sKey.toUpperCase()+">";
			if(isNaN(sValue)){
				sResult += "<![CDATA["+sValue+"]]>";
			}else{
				sResult += sValue;
			}

			sResult += "</"+sKey.toUpperCase()+">";
		}
		
		sResult += "</"+WCMOBJ_PROPERTIES_NODE_NAME+">";
	}
	else
	{//Invalid Object
		CTRSAction_alert("Invalid Object!");
		return null;
	}
	sResult += "</"+_sRootTagName.toUpperCase()+">";
	return sResult;
}

function CWCMObjHelper(){
//Define Properties
	
//Define Methods
	this.fromXMLNode		= CWCMObjHelper_fromXMLNode;
	this.fromXMLString		= CWCMObjHelper_parseXMLStrToObjs;

	this.parseXMLStrToObjs	= CWCMObjHelper_parseXMLStrToObjs;
	this.parseXMLStrToObj	= CWCMObjHelper_parseXMLStrToObj;

	this.parseXMLURLToObj	= CWCMObjHelper_parseXMLURLToObj;		
	this.parseXMLURLToObjs	= CWCMObjHelper_parseXMLURLToObjs;		

	this.toXMLString	= CWCMObjHelper_toXMLString;
}

var WCMObjHelper = new CWCMObjHelper();