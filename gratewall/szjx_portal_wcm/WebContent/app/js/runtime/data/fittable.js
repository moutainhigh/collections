Ext.ns('WCMLANG', 'wcm.LANG');
WCMLANG = wcm.LANG;
Ext.apply(wcm.LANG, {
LOCALE : 'cn',
WCM_WELCOME : '欢迎您使用TRS WCM系统',
NOW_DOING_WAIT : '正在{0},请耐心等候.',
TIME_WAIT_DESC : '等待时间：',
TIME_UNIT_SECOND : '秒',
SYSTEM_ALERT : '系统提示',
WEBSITE : '站点',
CHANNEL : '栏目',
DOCUMENT : '文档',
SITERECYCLE : '站点回收站',
CHNLRECYCLE : '栏目回收站',
SYTEM_SUOYOU : '所有',
EASY_SERVER_OVER : '执行时间超出常规时间，',
EASY_SERVER_ERROR : '可能出现网络故障，请刷新后重新处理。',
LOGIN_ISSYSTEM_WARNNING : '{0}是系统保留帐号！',
LOGIN_NOUSERNAME_WARNNING : '请输入用户名！',
LOGIN_NOPASSWORD_WARNNING : '请输入密码！',
LOGIN_LARGERLENGTH_WARNING:'用户名长度超过最大长度20！',
LOGIN_DOWITH_FORCE_LOGIN: "用户[{0}]已在[{1}]登录!\n您希望强行登录吗?",
LOGIN_DOWITH_ERRMSG : "\n{0}！",
MSGCENTER_DOCUMENT_NAME : "文档",
MSGCENTER_CHANNEL_NAME : "栏目",
MSGCENTER_WEBSITE_NAME : "站点",
ABSLIST_OPER_LOADING : "正在加载...",
ABSLIST_OPER_MORE : "更多操作",
ABSLIST_OPER_DETAIL : "详细信息",
ABSLIST_OPER_DETAIL_OBJS : '当前共选中<span class="num">{0}</span>{1}. ',
OPER_TITLE_INROOT : '库{0}操作任务',
OPER_TITLE_INSITE_SHORT : '{0}操作任务',
OPER_TITLE_INSITE : '站点{0}操作任务',
OPER_TITLE_INCHANNEL : '栏目{0}操作任务',
OPER_TITLE_INCLASSINFO : '分类法{0}操作任务',
OPER_TITLE_INCHANNEL_SHORT : '{0}操作任务',
OPER_TITLE_INCHANNELMASTER : '子栏目操作任务',
OPER_TITLE_OBJ : '{0}操作任务',
LIST_QUERY_ALL_DESC : '全部',
LIST_QUERY_INPUT_DESC : '..输入',
LIST_QUERY_JSC_DESC : '检索词',
LIST_QUERY_FLOAT : '要求为小数！',
LIST_QUERY_INT_MIN : '要求为整数！',
LIST_QUERY_INT_MAX : '要求在-2147483648~2147483647(-2^31~2^31-1)之间的数字！', 
LIST_QUERY_MAX_LEN : '<span style="width:180px;overflow-y:auto;">当前检索字段限制为[<b>{0}</b>]个字符长度，当前为[<b>{1}</b>]！\<br><br><b>提示：</b>每个汉字长度为2。</span>',
LIST_QUERY_DEFAULTORDER : '默认排序',
LIST_QUERY_NOTDEFAULT : '非默认排序时不保存拖动排序结果',
PHOTO : '图片',
METAVIEWDATA : '资源',
INFOVIEWDOC : '文档',
WATERMARK : '水印',
TEMPLATEARG : '模板变量',
TEMPLATE : '模板',
REPLACE : '替换内容',
PUBLISHDISTRIBUTION : '站点分发',
DISTRIBUTE : '分发',
METAVIEW : '视图',
METARECDATA : '记录列表',
METADBTABLE : '元数据',
MESSAGE_tab_34 : '最新消息',
MESSAGE_tab_35 : '已收消息',
MESSAGE_tab_36 : '已发消息',
LOGO : '栏目Logo',
FLOWDOC_FORDEAL : '待处理',
FLOWDOC_DEALED : '已处理',
FLOWDOC_FROMME : '我发起',
FLOW : '工作流',
DOCRECYCLE : '废稿箱',
CONTENTEXTFIELD : '扩展字段',
CLASSINFO : '分类法',
DOCUMENTSYN : '文档同步',
CHANNELSYNCOL : '栏目汇总',
CHANNELSYN : '栏目分发',
CHANNELCONTENTLINK : '热词',
RIGHTSET : '权限',
PLUGIN_NOREG : '此选件未正确安装或您没有购买此选件！',
NORIGHT_TABCHANGE : '对不起，您没有权限处理此节点下的任何操作！',
SYSTEMINFO : '系统提示信息',
NAV_VIEW : '视图',
NAV_SEARCH : '检索',
NAV_TREE : '导航',
CHILDCHANNEL : '子栏目',
TREE_QUICKLOCATE : '快速定位',
TREE_INDIVIDUATE : '设置定制的站点',
TREE_REFRESH : '刷新',
TREE_NEWSITE : '新建站点',
TREE_QUICKNEWSITE : '智能创建站点',
TREE_IMPORTSITE : '从外部导入站点',
TREE_KEYWORDMGR : '管理关键词',
TREE_NEWCHNL : '新建栏目',
TREE_NEWTEMP : '新建模板',
TREE_IMPORTTEMP : '导入模板',
TREE_REPLACE_THISSITE : '这(?:个|些)站点(?:的)?',
TREE_REPLACE_THISDOC : '这(?:篇|些)文档(?:的)?',
TREE_REPLACE_THISTEMPLATE : '这(?:个|些)模板(?:的)?',
TREE_REPLACE_THISREC : '这(?:条|些)记录(?:的)?',
TREE_REPLACE_THISCHNL : '这个栏目',
TREE_NEWCHILDCHNL : '新建子栏目',
TREE_NEWDOC : '新建文档',
SURE : '确定',
TEXT_LIB : '文字库',
SEARCHTITLE :'文档检索的位置',
CRTIME : '创建时间设置',
PUBTIME :'发布时间设置',
ALERTPLACE : '请指定要搜索的位置！',
ALERTTIME :'：结束时间不应该小于开始时间！',
SYS_CHANNELSELECT_NOMODE : '没选中任何模式',
SYS_CHANNELSELECT_NONE : '请选择要设置的栏目！',
ALERTUNEQUAL : "所选的栏目非表单栏目，或与源栏目使用的表单不一致。",
CLOSE : '关闭',
UNDO : '暂未实现',
ABSLIST_OPER_DETAILMORE : '更多属性',
PHOTO_LIB : '图片库设置',
DIALOG_SYSTEM_ALERTION : "系统提示信息",
DIALOG_BTN_OK : "确定",
DIALOG_BTN_CANCEL : "取消",
DIALOG_BTN_YES : "是",
DIALOG_BTN_NO : "否",
DIALOG_SHOW_DETAIL : '显示详细信息',
DIALOG_COPY_DETAIL : '复制此信息',
DIALOG_CLOSE_FRAME : '关闭提示窗',
DIALOG_CLIPBOARD_COPYED : "已经复制到剪切板中！",
DIALOG_COPY_NOT_SUPPORTED : '您的浏览器不支持自动复制操作，请手工拷贝！',
DIALOG_SERVER_ERROR : "与服务器交互时出现错误",
DIALOG_DETAIL_INFO : '详细信息',
DIALOG_HIDE_INFO : '隐藏显示',
DIALOG_TIP1 : '本窗口在',
DIALOG_TIP2 : '秒内将自动消失',
PUBLISH_1 : '发布校验结果',
PUBLISH_2 : '已经将您的发布操作提交到后台了...',
PUBLISH_3 : '已发',
PUBLISH_4 : '已否',
PUBLISH_5 : '预览出错',
PUBLISH_6 : '文档',
PUBLISH_7 : '图片',
PUBLISH_8 : '视频',
PAGENAV1 : '条',
PAGENAV2 : '记录',
PAGENAV3 : "单击当前页可输入跳转页号",
PAGENAV4 : "首页",
PAGENAV5 : "更多",
PAGENAV6 : "尾页",
PAGENAV7 : "共",
PAGENAV8 : "页",
PAGENAV9 : "<span class=\"nav_pagesize\">{0}</span>{1}/页",
PAGENAV10 : "跳转到第",
PAGENAV11 : "页",
UPLOAD_1 : '\n原因：文件路径不存在或者非本地文件路径。',
UPLOAD_2 : "没有选择",
UPLOAD_3 : "只支持上传\"",
UPLOAD_4 : "\"格式的文件！",
UPLOAD_5 : '与服务器交互时出错啦！',
UPLOAD_6 : "文件！",
ALERTCANCEL : "未选中任何热词进行取消替换,您确认不进行取消替换?",
UNSELECTALL : '取消全选',
SELECTALL : '全选',
ANALYZE : '正在自动分析文档中命中的热词...',
NOHEAD : '无标题',
AUTO : '自动',
INVALID_PARAM : '无效的参数！',
TIPS : '提示：',
NOPICFOUNDED :"未选择任何要导入的图片",
SYSTEM_NOTUNIQUE : "不唯一",
ALERT_MESSAGE_1 : '您是初次访问系统，可能要设置一些系统的配置，',
ALERT_MESSAGE_2 : '设置入口',
ALERT_MESSAGE_3 : ' 您也可以以后再设置，设置入口是在菜单栏的配置管理下面的系统配置项。',
ALERT_MESSAGE_4 : "您在本系统中使用的密码为弱密码，为了确保您个人账号的安全，请点击<a href='#' onclick='onEditPassword();return false'>重设密码</a>进行重新设定！",
TRSSERVER_1 : '配置TRSServer信息',
TRSSERVER_2 : '刷新列表',
TRSSERVER_3 : 'TRSServer数据列表',
TRSSERVER_4 : '配置窗口',
TRSSERVER_5 : '提取',
TRSSERVER_6 : '提取为WCM数据',
TRSSERVER_7 : '选择所属栏目',
TRSSERVER_8 : '参数配置',
TRSSERVER_9 : '新建检索任务',
TRSSERVER_10 : '修改检索任务',
TRSSERVER_11 : "请选择需要删除的检索任务!",
TRSSERVER_12 : "您确定需要删除选定的检索任务吗?"
});

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


