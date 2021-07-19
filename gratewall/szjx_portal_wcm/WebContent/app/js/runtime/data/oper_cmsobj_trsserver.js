Ext.ns('wcm.domain.TRSServerMgr');
(function(){
var m_oMgr = wcm.domain.TRSServerMgr;
function getHelper(){
return new com.trs.web2frame.BasicDataHelper();
}
Ext.apply(wcm.domain.TRSServerMgr, {
"pick" : function(event){
var sIds = event.getObjs().getIds();
var args = {
IsRadio : 1,
CurrSiteType : 0,
ExcludeTop : 1,
ExcludeLink : 1,
ExcludeVirtual : 1,
ExcludeInfoView : 1,
ExcludeOnlySearch : 1,
ShowOneType : 1,
NotSelect : 1,
canEmpty : true
};
FloatPanel.open(
WCMConstants.WCM6_PATH + 'include/channel_select.html?close=1',
wcm.LANG.TRSSERVER_7 || '选择所属栏目',
function(selectIds, selectChnlDescs){
if(!selectIds||selectIds.length==0) {
Ext.Msg.$alert(wcm.LANG.DOCUMENT_PROCESS_32 ||'请选择当前文档要复制到的目标栏目!');
return false;
}
var oPostData = {
ServerIds :sIds,
ChannelId : selectIds[0]
}
var oHelper = new com.trs.web2frame.BasicDataHelper();
oHelper.Call('wcm6_document', 'trsSaveToWCM', oPostData, true, function(_oTrans, _json){
alert(wcm.LANG.DOCUMENT_PROCESS_237 || "提取成功！");
FloatPanel.close();
CMSObj.afteredit(event)();
},function(){
alert(wcm.LANG.DOCUMENT_PROCESS_238 || "提取失败！");
});
},
dialogArguments = args
);
}
});
})();
(function(){
var pageObjMgr = wcm.domain.TRSServerMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'pick',
type : 'trsserver',
desc : wcm.LANG.TRSSERVER_5 || '提取',
rightIndex : -1,
order : 1,
fn : pageObjMgr['pick'],
quickKey : 'P'
});
})();

Ext.ns('wcm.TRSServers', 'wcm.TRSServer');
WCMConstants.OBJ_TYPE_TRSSERVER = 'trsServer';
wcm.TRSServers = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_TRSSERVER;
wcm.TRSServers.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.TRSServers, wcm.CMSObjs, {
});
wcm.TRSServer = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_TRSSERVER;
wcm.TRSServer.superclass.constructor.call(this, _config, _context);
var arrRights = {
"view" : -1
};
for(rightName in arrRights){
var cameRightName = rightName.camelize();
var nRightIndex = parseInt(arrRights[rightName], 10);
this['can'+cameRightName] = function(){
return wcm.AuthServer.hasRight(this.right, nRightIndex);
}
}
}
CMSObj.register(WCMConstants.OBJ_TYPE_TRSSERVER, 'wcm.TRSServer');
Ext.extend(wcm.TRSServer, wcm.CMSObj, {
});

