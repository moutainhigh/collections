(function(){
var oTabItem = {
type : 'advisor',
desc : '顾问',
url : WCMConstants.WCM6_PATH + 'advisor/advisor_list.jsp',
fittable : false,
rightIndex : '13',
order : '9',
renderUrl : function(search){
var params = search.parseQuery();
Ext.apply(params, {
HostId : PageContext.hostId,
HostType : PageContext.intHostType
});
return params;
}
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
items : [Ext.applyIf({order : '9'}, oTabItem)]
});
})();

(function(){
var oTabItem = {
type : 'right',
desc : wcm.LANG['RIGHTSET'] || '权限',
fittable : false,
url : WCMConstants.WCM6_PATH + 'auth/right_set.jsp',
rightIndex : -1,
order : '4.5',
renderUrl : function(search){
var params = search.parseQuery();
Ext.apply(params, {
ObjId : PageContext.hostId,
ObjType : PageContext.intHostType
});
return params;
}
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
items : [oTabItem]
});
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
items :[oTabItem]
});
})();

(function(){
var oTabItem = {
type : 'channel',
desc : function(context){
if(context && context.host && (context.host.objType==WCMConstants.OBJ_TYPE_CHANNELMASTER 
|| context.host.objType==WCMConstants.OBJ_TYPE_CHANNEL)){
return wcm.LANG['CHILDCHANNEL'] || '子栏目';
}
return wcm.LANG['CHANNEL'] || '栏目';
},
url : WCMConstants.WCM6_PATH + 'channel/channel_thumb.html',
rightIndex : '-1',
order : '2',
isVisible : function(context){
var host = context.host;
if(Ext.isTrue(host.isVirtual) && context.params['CHANNELTYPE'] != 0){
return false;
}
return true;
}
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
items : [Ext.applyIf({}, oTabItem)]
});
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
items :[Ext.applyIf({isdefault:true}, oTabItem)]
});
})();
(function(){
var tabType = 'channel';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'channel/channel_classic_thumb.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'channelcontentlink',
desc : wcm.LANG['CHANNELCONTENTLINK'] || '热词',
url : WCMConstants.WCM6_PATH + 'channelcontentlink/channelcontentlink_list.html',
rightIndex : '13',
order : '10',
isVisible : function(context){
var host = context.host;
if(Ext.isTrue(host.isVirtual) || (context.params.SITETYPE != 0 && context.params.SITETYPE != 4)){
return false;
}
return true;
}
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
items : [Ext.applyIf({order : '6',rightIndex:'1'}, oTabItem)]
});
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
items : [Ext.applyIf({order : '10'}, oTabItem)]
});
})();
(function(){
var tabType = 'channelcontentlink';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'channelcontentlink/channelcontentlink_classic_list.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'channelsyn',
desc : wcm.LANG['DOCUMENTSYN'] || '文档同步',
url : WCMConstants.WCM6_PATH + ((getParameter['CONTAINSRIGHT'])? 'channelsyn/channelsyn_list.html' : 'channelsyn/channelsyn_list.html'),
rightIndex : '13',
order : '8',
isVisible : function(context){
var host = context.host;
if((Ext.isTrue(host.isVirtual) && context.params['CHANNELTYPE']==0) || (context.params.SITETYPE != 0 && context.params.SITETYPE != 4)){//检索栏目
return false;
}
return true;
}
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
items : [Ext.applyIf({order : '8'}, oTabItem)]
});
})();
(function(){
var tabType = 'channelsyn';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'channelsyn/channelsyn_classic_list.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'channelsyncol',
desc : wcm.LANG['DOCUMENTSYN'] || '文档同步',
url : WCMConstants.WCM6_PATH + 'channelsyn/channelsyncol_list.html',
rightIndex : '13',
order : '8'
}
})();
(function(){
var tabType = 'channelsyncol';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'channelsyn/channelsyncol_classic_list.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'chnlrecycle',
desc : wcm.LANG['CHNLRECYCLE'] || '栏目回收站',
url : WCMConstants.WCM6_PATH + 'chnlrecycle/chnlrecycle_list.html',
rightIndex : '-1,12',
order : '98'
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
items : [Ext.applyIf({order : '98'}, oTabItem)]
});
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
items :[Ext.applyIf({order : '97'}, oTabItem)]
});
})();
(function(){
var tabType = 'chnlrecycle';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'chnlrecycle/chnlrecycle_classic_list.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'classinfo',
desc : wcm.LANG['CLASSINFO'] || '分类法',
url : WCMConstants.WCM6_PATH + 'classinfo/classinfo_list.html',
rightIndex : '-2',
order : '6',
isVisible : function(context){
return context.params['SITETYPE'] == '4';
}
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITEROOT,
items : [Ext.applyIf({order : '6'}, oTabItem)]
});
})();
(function(){
var tabType = 'classinfo';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'classinfo/classinfo_classic_list.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITEROOT, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'contentextfield',
desc : wcm.LANG['CONTENTEXTFIELD'] || '扩展字段',
url : WCMConstants.WCM6_PATH + 'contentextfield/contentextfield_list.html',
rightIndex : 19,
order : '7',
isVisible : function(context){
var host = context.host;
if(Ext.isTrue(host.isVirtual) || Ext.isTrue(context.params.CHANNELTYPE == 13)){
return false;
}
return true;
}
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
items : [Ext.applyIf({}, oTabItem)]
});
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
items :[Ext.applyIf({order : '6'}, oTabItem)]
});
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITEROOT,
items :[Ext.applyIf({order : '3'}, oTabItem)]
});
})();
(function(){
var tabType = 'contentextfield';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'contentextfield/contentextfield_classic_list.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITEROOT, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'docrecycle',
desc : wcm.LANG['DOCRECYCLE'] || '废稿箱',
url : WCMConstants.WCM6_PATH + 'docrecycle/docrecycle_list.html',
rightIndex : '18',
order : '9',
isVisible : function(context){
var host = context.host;
if(context.params['SITETYPE'] == '1' || context.params['SITETYPE'] == '2'){
return false;
}
if(Ext.isTrue(host.isVirtual) && context.params['CHANNELTYPE']==0){
return false;
}
return true;
}
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
items : [Ext.applyIf({order : '9'}, oTabItem)]
});
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
items :[Ext.applyIf({order : '9'}, oTabItem)]
});
})();
(function(){
var tabType = 'docrecycle';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'docrecycle/docrecycle_classic_list.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'document',
desc : wcm.LANG.DOCUMENT || '文档',
url : WCMConstants.WCM6_PATH + 'document/document_list_redirect.jsp',
rightIndex : '30-34,38-39',
order : 1
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
items : [Ext.applyIf({isdefault:true}, oTabItem)]
});
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
items : [oTabItem]
});
})();
(function(){
var tabType = 'document';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'document/document_classic_list.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'filter',
desc : '筛选器',
url : WCMConstants.WCM6_PATH + 'filter/filter_list.jsp',
fittable : false,
rightIndex : '13',
order : '10',
renderUrl : function(search){
var params = search.parseQuery();
Ext.apply(params, {
HostId : PageContext.hostId,
HostType : PageContext.intHostType
});
return params;
}
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
items : [Ext.applyIf({order : '10'}, oTabItem)]
});
})();

(function(){
var oTabItem = {
type : 'flow',
desc : wcm.LANG['FLOW'] || '工作流',
url : WCMConstants.WCM6_PATH + 'flow/flow_list.html',
rightIndex : '41-45',
order : '5',
isVisible : function(context){
var host = context.host;
if(Ext.isTrue(host.isVirtual)){
return false;
}
return true;
}
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
items : [Ext.applyIf({url : WCMConstants.WCM6_PATH + 'flow/flow_chnl_list.html',
rightIndex : '13,41-45'
}, oTabItem)]
});
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
items :[Ext.applyIf({}, oTabItem)]
});
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITEROOT,
items :[Ext.applyIf({order : '3'}, oTabItem)]
});
})();
(function(){
var tabType = 'flow';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'flow/flow_classic_list.html'
}, tabItem);
}
var wrapper1 = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'flow/flow_classic_chnl_list.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITEROOT, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper1);
})();

(function(){
var oTabItem1 = {
type : 'flowdoc1',
desc : wcm.LANG['FLOWDOC_FORDEAL'] || '待处理',
url : WCMConstants.WCM6_PATH + 'flowdoc/iflowcontent_list.html?ViewType=1',
rightIndex : '-1',
order : 1,
isdefault : true
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_MYFLOWDOCLIST,
items :[oTabItem1]
});
oTabItem2 = {
type : 'flowdoc2',
desc : wcm.LANG['FLOWDOC_DEALED'] || '已处理',
url : WCMConstants.WCM6_PATH + 'flowdoc/iflowcontent_list.html?ViewType=2',
rightIndex : '-1',
order : 2
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_MYFLOWDOCLIST,
items :[oTabItem2]
});
oTabItem3 = {
type : 'flowdoc3',
desc : wcm.LANG['FLOWDOC_FROMME'] || '我发起',
url : WCMConstants.WCM6_PATH + 'flowdoc/iflowcontent_list.html?ViewType=3',
rightIndex : '-1',
order : 3
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_MYFLOWDOCLIST,
items :[oTabItem3]
});
})();
(function(){
var tabType = 'flowdoc1';
var wrapper = function(context, tabItem){
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'flowdoc/iflowcontent_classic_list.html?ViewType=1'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_MYFLOWDOCLIST, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();
(function(){
var tabType = 'flowdoc2';
var wrapper = function(context, tabItem){
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'flowdoc/iflowcontent_classic_list.html?ViewType=2'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_MYFLOWDOCLIST, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();
(function(){
var tabType = 'flowdoc3';
var wrapper = function(context, tabItem){
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'flowdoc/iflowcontent_classic_list.html?ViewType=3'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_MYFLOWDOCLIST, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'logo',
desc : wcm.LANG['LOGO'] || '栏目Logo',
url : WCMConstants.WCM6_PATH + 'logo/logo_list.jsp',
fittable : false,
rightIndex : '13',
order : '8',
extraCls : 'logoTabCls',
renderUrl : function(search){
var params = search.parseQuery();
Ext.apply(params, {
HostId : PageContext.hostId,
HostType : PageContext.intHostType
});
return params;
}
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
items : [Ext.applyIf({order : '8'}, oTabItem)]
});
})();

(function(){
var oTabItem1 = {
type : 'message0',
desc : wcm.LANG['MESSAGE_tab_34'] || '最新消息',
url : WCMConstants.WCM6_PATH + 'message/message_list.html?ReadFlag=0',
rightIndex : '-1',
order : '1',
isdefault : true
}
oTabItem2 = {
type : 'message2',
desc : wcm.LANG['MESSAGE_tab_35'] || '已收消息',
url : WCMConstants.WCM6_PATH + 'message/message_list.html?ReadFlag=2',
rightIndex : '-1',
order : 2
}
oTabItem3 = {
type : 'message1',
desc : wcm.LANG['MESSAGE_tab_36'] || '已发消息',
url : WCMConstants.WCM6_PATH + 'message/message_list.html?ReadFlag=1',
rightIndex : '-1',
order : 3
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_MYMSGLIST,
items : [Ext.applyIf({order : '1'}, oTabItem1)]
});
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_MYMSGLIST,
items : [Ext.applyIf({order : '2'}, oTabItem2)]
});
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_MYMSGLIST,
items :[Ext.applyIf({order : '3'}, oTabItem3)]
});
})();
(function(){
var tabType = 'message0';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'message/message_classic_list.html?ReadFlag=0'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_MYMSGLIST, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();
(function(){
var tabType = 'message2';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'message/message_classic_list.html?ReadFlag=2'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_MYMSGLIST, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();
(function(){
var tabType = 'message1';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'message/message_classic_list.html?ReadFlag=1'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_MYMSGLIST, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'metadbtable',
desc : wcm.LANG['METADBTABLE'] || '元数据',
url : WCMConstants.WCM6_PATH + 'metadbtable/metadbtable_thumb.html',
rightIndex : '-2',
order : '4',
isVisible : function(context){
return context.params['SITETYPE'] == '4';
}
} 
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITEROOT,
items : [Ext.applyIf({order : '4'}, oTabItem)]
});
})();
(function(){
var tabType = 'metadbtable';
var wrapper = function(context, tabItem){
if($MsgCenter.getActualTop().m_bClassicList)return tabItem;
if(!context.params['TABLEINFOID'])return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'metadbfield/metadbfield_list.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITEROOT, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();
(function(){
var tabType = 'metadbtable';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
if(context.params['TABLEINFOID'])return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'metadbtable/metadbtable_classic_thumb.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITEROOT, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();
(function(){
var tabType = 'metadbtable';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
if(!context.params['TABLEINFOID'])return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'metadbfield/metadbfield_classic_list.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITEROOT, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'metarecdata',
desc : wcm.LANG['METARECDATA'] || '记录列表',
url : WCMConstants.WCM6_PATH + 'metarecdata/metarecdata_list.html',
rightIndex : '-1',
order : '8'
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_CLASSINFO,
items : [Ext.applyIf({order : '8'}, oTabItem)]
});
})();
(function(){
var tabType = 'metarecdata';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'metarecdata/metarecdata_classic_list.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CLASSINFO, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();
(function(){
var tabType = 'metarecdata';
var wrapper = function(context, tabItem){
if(context.params.VIEWID == "0" || context.params.FILTERTYPE){
var sUrl = 'viewclassinfo/classinfo_document_list.html';
if(context.params.VIEWID != "0"){
sUrl = 'viewclassinfo/metarecdata_list.html';
}
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + sUrl
}, tabItem);
}
return tabItem;
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CLASSINFO, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'metaview',
desc : wcm.LANG['METAVIEW'] || '视图',
url : WCMConstants.WCM6_PATH + 'metaview/metaview_thumb.html',
rightIndex : '-2',
order : '4',
isVisible : function(context){
if(context.params['SITETYPE'] == '4' && (context.params['CHANNELTYPE'] == '0' || !context.params['CHANNELTYPE'] )){
return true;
}
return false;
}
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITEROOT,
items : [Ext.applyIf({order : '5'}, oTabItem)]
});
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
items : [Ext.applyIf({order : '1.2',rightIndex : '13'}, oTabItem)]
});
})();
(function(){
var tabType = 'metaview';
var wrapper = function(context, tabItem){
if(context.params['VIEWID'] || context.params['CHANNELID']){
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'metaviewfield/metaviewfield_list.html'
}, tabItem);
}
return tabItem;
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITEROOT, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();
(function(){
var tabType = 'metaview';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
if(context.params['VIEWID'] || context.params['CHANNELID'])return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'metaview/metaview_classic_thumb.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITEROOT, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();
(function(){
var tabType = 'metaview';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
if(!context.params['VIEWID'] && !context.params['CHANNELID'])return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'metaviewfield/metaviewfield_classic_list.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITEROOT, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'publishdistribution',
desc : wcm.LANG['PUBLISHDISTRIBUTION'] || '站点分发',
url : WCMConstants.WCM6_PATH + 'publishdistribution/publishdistribution_list.html',
rightIndex : '1',
order : '7'
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
items :[Ext.applyIf({order : '7'}, oTabItem)]
});
})();
(function(){
var tabType = 'publishdistribution';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'publishdistribution/publishdistribution_classic_list.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'region',
desc : wcm.LANG.region_2345 || '导读',
url : WCMConstants.WCM6_PATH + 'region/region_list.html',
rightIndex : '-1',
order : '6',
isVisible : function(context){
var host = context.host;
if(Ext.isTrue(host.isVirtual)){
return false;
}
if(context.params['SITETYPE']==0 || context.params['SITETYPE']==4){
return true;
}
return false;
}
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
items :[Ext.applyIf({rightIndex:48}, oTabItem)]
});
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
items :[Ext.applyIf({rightIndex:48}, oTabItem)]
});
})();
(function(){
var tabType = 'region';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'region/region_classic_list.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'replace',
desc : wcm.LANG['REPLACE'] || '替换内容',
url : WCMConstants.WCM6_PATH + 'replace/replace_list.html',
rightIndex : '13',
order : '6',
isVisible : function(context){
var host = context.host;
if(Ext.isTrue(host.isVirtual) && context.params['CHANNELTYPE'] != 0){
return false;
}
return true;
}
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
items : [Ext.applyIf({order : '6'}, oTabItem)]
});
})();
(function(){
var tabType = 'replace';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'replace/replace_classic_list.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'siterecycle',
desc : wcm.LANG['SITERECYCLE'] || '站点回收站',
url : WCMConstants.WCM6_PATH + 'siterecycle/siterecycle_list.html',
rightIndex : '-1,2',
order : '96',
isVisible : function(context){
var host = context.host;
if(Ext.isTrue(host.isVirtual)){
return false;
}
return true;
}
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITEROOT,
items : [Ext.applyIf({order : '96'}, oTabItem)]
});
})();
(function(){
var tabType = 'siterecycle';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'siterecycle/siterecycle_classic_list.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITEROOT, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'template',
desc : wcm.LANG['TEMPLATE'] || '模板',
url : WCMConstants.WCM6_PATH + 'template/template_list.html',
rightIndex : '21-25,28',
order : '3',
isVisible : function(context){
var host = context.host;
if(Ext.isTrue(host.isVirtual) && context.params['CHANNELTYPE'] != 0){
return false;
}
return true;
}
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
items : [Ext.applyIf({}, oTabItem)]
});
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
items :[Ext.applyIf({}, oTabItem)]
});
})();
(function(){
var tabType = 'template';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'template/template_classic_list.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'templateArg',
desc : wcm.LANG['TEMPLATEARG'] || '模板变量',
url : WCMConstants.WCM6_PATH + 'template/template_arg_list.html',
rightIndex : '53',
order : '100'
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
items : [Ext.applyIf({order : '100'}, oTabItem)]
});
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
items :[Ext.applyIf({order : '100'}, oTabItem)]
});
})();
(function(){
var tabType = 'templateArg';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'template/template_arg_classic_list.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'trsserver',
desc : wcm.LANG['DOCUMENT'] || '文档',
url : WCMConstants.WCM6_PATH + 'trsserver/trsserver_classic_list.html?ViewType=1', //'trsserver/trsserver_classic_list.html?ViewType=1',
rightIndex : '-1',
order : 1,
isdefault : true
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_TRSSERVERLIST,
items :[oTabItem]
});
})();

(function(){
var oTabItem = {
type : 'viewoper',
desc : wcm.LANG['VIEWOPER'] || '访问控制',
fittable : false,
url : WCMConstants.WCM6_PATH + 'auth/view_oper_set.jsp',
rightIndex : -1,
order : 4,
renderUrl : function(search){
var params = search.parseQuery();
Ext.apply(params, {
ObjId : PageContext.hostId,
ObjType : PageContext.intHostType,
FilterRight : 0
});
return params;
}
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_CHANNEL,
items : [oTabItem]
});
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
items :[ oTabItem]
});
})();

(function(){
var oTabItem = {
type : 'watermark',
desc : wcm.LANG['WATERMARK'] || '水印',
url : WCMConstants.WCM6_PATH + 'watermark/watermark_thumb.html',
rightIndex : '-1',
order : '8',
isVisible : function(context){
if(context.params['SITETYPE']!=1){
return false;
}
return true;
}
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITE,
items :[Ext.applyIf({order : '8'}, oTabItem)]
});
})();
(function(){
var tabType = 'watermark';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'watermark/watermark_classic_thumb.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var oTabItem = {
type : 'website',
desc : wcm.LANG['WEBSITE'] || '站点',
url : WCMConstants.WCM6_PATH + 'website/website_thumb.html',
rightIndex : '-1',
order : '1'
}
wcm.TabManager.register({
hostType : WCMConstants.TAB_HOST_TYPE_WEBSITEROOT,
items : [Ext.applyIf({isdefault:true}, oTabItem)]
});
})();
(function(){
var tabType = 'website';
var wrapper = function(context, tabItem){
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'website/website_classic_thumb.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITEROOT, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var tabType = 'docrecycle';
var wrapper = function(context, tabItem){
if(context.params['SITETYPE']!=1)return tabItem;
return Ext.applyIf({
desc : wcm.LANG['DOCRECYCLE'] || '废稿箱',
url : WCMConstants.WCM6_PATH + 'photorecycle/recycle_thumb.html',
rightIndex : '18',
order : '9',
isVisible : function(context){
return true;
}
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();
(function(){
var tabType = 'docrecycle';
var wrapper = function(context, tabItem){
if(context.params['SITETYPE']!=1)return tabItem;
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'photorecycle/recycle_classic_thumb.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var tabType = 'docrecycle';
var wrapper = function(context, tabItem){
if(context.params['SITETYPE']!=2)return tabItem;
return Ext.applyIf({
desc : wcm.LANG.videorecycle_101 || '废稿箱',
url : WCMConstants.WCM6_PATH + 'video/videorecycle_thumb.html',
rightIndex : '18',
order : '9',
isVisible : function(context){
return true;
}
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();
(function(){
var tabType = 'docrecycle';
var wrapper = function(context, tabItem){
if(context.params['SITETYPE']!=2)return tabItem;
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'video/videorecycle_thumb.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var tabType = 'document';
var wrapper = function(context, tabItem){
if(context.params['CHANNELTYPE']!=13)return tabItem;
return Ext.applyIf({
desc : wcm.LANG['INFOVIEWDOC'] || '表单',
url : WCMConstants.WCM6_PATH + 'document/document_list_redirect.jsp',
isdefault : context.tabHostType==WCMConstants.TAB_HOST_TYPE_CHANNEL
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();
(function(){
var tabType = 'document';
var wrapper = function(context, tabItem){
if(context.params['CHANNELTYPE']!=13)return tabItem;
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'infoview/infoviewdoc_classic_list.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var tabType = 'document';
var wrapper = function(context, tabItem){
var bClassicList = 0;
if(context.params['SITETYPE'] != 4)return tabItem;
if($MsgCenter.getActualTop().m_bClassicList) bClassicList = 0;
return Ext.applyIf({
desc : wcm.LANG['METAVIEWDATA'] || '璧勬簮',
url : WCMConstants.WCM6_PATH + 'metaviewdata/metaviewdata_list_redirect.jsp?bClassicList=' + bClassicList ,
isdefault : context.tabHostType==WCMConstants.TAB_HOST_TYPE_CHANNEL
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var tabType = 'document';
var wrapper = function(context, tabItem){
if(context.params['SITETYPE']!=1)return tabItem;
return Ext.applyIf({
desc : wcm.LANG.PHOTO || '图片',
url : WCMConstants.WCM6_PATH + 'document/document_list_redirect.jsp',
rightIndex : '30-34,38-39',
order : '1',
isdefault : context.tabHostType==WCMConstants.TAB_HOST_TYPE_CHANNEL
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();
(function(){
var tabType = 'document';
var wrapper = function(context, tabItem){
if(context.params['SITETYPE']!=1)return tabItem;
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'photo/photo_classic_thumb.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();

(function(){
var tabType = 'document';
var wrapper = function(context, tabItem){
if(context.params['SITETYPE']!=2)return tabItem;
return Ext.applyIf({
desc : wcm.LANG.video_1201 || '视频',
url : WCMConstants.WCM6_PATH + 'video/video_thumb.html',
rightIndex : '30-34,38-39',
order : '1',
isdefault : context.tabHostType==WCMConstants.TAB_HOST_TYPE_CHANNEL
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();
(function(){
var tabType = 'document';
var wrapper = function(context, tabItem){
if(context.params['SITETYPE']!=2)return tabItem;
if(!$MsgCenter.getActualTop().m_bClassicList)return tabItem;
return Ext.applyIf({
url : WCMConstants.WCM6_PATH + 'video/video_thumb.html'
}, tabItem);
}
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_WEBSITE, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
var oTabItem = wcm.TabManager.getTab(WCMConstants.TAB_HOST_TYPE_CHANNEL, tabType);
wcm.TabManager.createWrapper(oTabItem, wrapper);
})();


