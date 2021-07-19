//中文
function importScriptSrc(_src){
	document.write("<script src=\""+_src+"\"><\/script>");
}

try{
	CWCMObjHelper;
}catch(ex){
	importScriptSrc("../js/wcm52/CWCMObj.js");
	importScriptSrc("../js/wcm52/CWCMObjHelper.js");	
}

try{
	CTRSTree;
}catch(ex){	
	importScriptSrc("../js/wcm52/CTRSTree_res_default.js");
	importScriptSrc("../js/wcm52/CTRSTree.js");
}

try{
	CTRSAction;
}catch(ex){
	importScriptSrc("../js/wcm52/CTRSHashtable.js");
	importScriptSrc("../js/wcm52/CTRSRequestParam.js");
	importScriptSrc("../js/wcm52/CTRSAction.js");	
}

try{
	CWCMConstants;
}catch(ex){
	importScriptSrc("../js/wcm52/CWCMConstants.js");	
}
