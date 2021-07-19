Ext.apply(wcm.LANG, {
REPLACE_UNIT : '个',
REPLACE : '替换内容',
REPLACE_NEW : '新建替换内容',
REPLACE_EDIT : '修改这个替换内容',
REPLACE_DELETE : '删除这个替换内容',
REPLACES_DELETE : '删除这些替换内容',
REPLACE_CONFIRM : '确实要将这{0}个替换内容删除吗?',
TRUE : '确定',
REPLACE_ALERT : '标题已经存在',
REPLACENAME : '标题',
REPLACECONTENT :'内容',
REPLACE_1 :'新建',
REPLACE_2 :'新建一个替换内容',
REPLACE_3 :'删除',
REPLACE_4 :'删除这个/些替换内容',
REPLACE_5 :'刷新',
REPLACE_6 :'刷新列表',
REPLACE_7 :'替换内容列表管理',
REPLACE_8 :'新建替换内容',
REPLACE_EDIT_2 : '修改替换内容'
});

Ext.ns('wcm.domain.ReplaceMgr');
(function(){
var m_oMgr = wcm.domain.ReplaceMgr={
serviceId : 'wcm6_replace',
getHelper : function(_sServceFlag){
return new com.trs.web2frame.BasicDataHelper();
}
};
Ext.apply(wcm.domain.ReplaceMgr, {
add : function(event){
var host = event.getHost();
var hostId = host.getId();
var oparameters = "ChannelId="+hostId;
oparameters += "&ObjectId=0" 
FloatPanel.open(WCMConstants.WCM6_PATH + 'replace/replace_add_edit.jsp?' + oparameters, 
wcm.LANG.REPLACE_8||'新建替换内容',
CMSObj.afteradd(event)
);
},
edit : function(event){
var sId = event.getIds();
var host = event.getHost();
var hostId = host.getId();
var oparameters = "ChannelId="+hostId;
oparameters += "&ObjectId=" + sId;
FloatPanel.open(WCMConstants.WCM6_PATH + 'replace/replace_add_edit.jsp?' + oparameters, 
wcm.LANG.REPLACE_EDIT_2||'修改替换内容',
CMSObj.afteredit(event)
);
},
"delete" : function(event){
var sIds = event.getIds() + '';
var hostId = event.getHost().getId();
var oPageParams = {
ChannelId : hostId
};
var nCount = (sIds.indexOf(',') == -1) ? 1:sIds.split(',').length;
var sHint = (nCount==1)?'':' '+nCount+' ';
if (confirm(String.format(wcm.LANG.REPLACE_CONFIRM||('确实要将这{0}个替换内容删除吗?'),sHint))){
m_oMgr.getHelper().call(m_oMgr.serviceId, 
'delete', //远端方法名 
Object.extend(oPageParams, {"ObjectIds": sIds}), //传入的参数
false, 
function(){
event.getObjs().afterdelete();
}
);
}
}
});
})();
(function(){
var pageObjMgr = wcm.domain.ReplaceMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'edit',
type : 'replace',
desc : wcm.LANG.REPLACE_EDIT||'修改这个替换内容',
title:'修改这个替换内容',
rightIndex : 13,
order : 1,
fn : pageObjMgr['edit'],
quickKey : 'M'
});
reg({
key : 'delete',
type : 'replace',
desc : wcm.LANG.REPLACE_DELETE||'删除这个替换内容',
title:'删除这个替换内容',
rightIndex : 13,
order : 2,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
reg({
key : 'add',
type : 'replaceInChannel',
desc : wcm.LANG.REPLACE_NEW||'新建替换内容',
title:'新建替换内容',
rightIndex : 13,
order : 3,
fn : pageObjMgr['add'],
quickKey : 'N'
});
reg({
key : 'delete',
type : 'replaces',
desc : wcm.LANG.REPLACES_DELETE||'删除这些替换内容',
title:'删除这些替换内容',
rightIndex : 13,
order : 4,
fn : pageObjMgr['delete'],
quickKey : ['Delete', 'ShiftDelete']
});
})();

Ext.ns('wcm.Replaces', 'wcm.Replace');
WCMConstants.OBJ_TYPE_REPLACE = 'Replace';
wcm.Replaces = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_REPLACE;
wcm.Replaces.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.Replaces, wcm.CMSObjs, {
});
wcm.Replace = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_REPLACE;
wcm.Replace.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_REPLACE, 'wcm.Replace');
Ext.extend(wcm.Replace, wcm.CMSObj, {
getIntType : function(){
return 105;
}
});

(function(){
var sName = wcm.LANG['REPLACE'] || '替换内容';
wcm.PageOper.registerPanels({
replaceInRoot : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INROOT'] || '库{0}操作任务', sName),
displayNum : 5
},
replaceInSite : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INSITE'] || '站点{0}操作任务', sName),
displayNum : 5
},
replaceInChannel : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INCHANNEL'] || '栏目{0}操作任务', sName),
displayNum : 5
},
replace : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
displayNum : 7,
url : '?serviceid=wcm61_replace&methodname=jFindbyid'
},
replaces : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
displayNum : 7
}
});
})();
ValidationHelper.bindValidations([
{
renderTo : 'ReplaceName',
type :'string',
required :'',
max_len :'50',
desc :wcm.LANG.REPLACENAME||'标题',
methods : PageContext.validExistProperty()
},
{
renderTo : 'ReplaceContent',
type:'string',
required:'',
max_len:'500',
desc:wcm.LANG.REPLACECONTENT||'内容'
}
]);

