Ext.ns('wcm.domain.TemplateArgMgr');
(function(){
var m_oMgr = wcm.domain.TemplateArgMgr;
var m_sOperInputTemplate = ['<input name="argFeild" class="input_text" id="ArgValue" value="{0}"',
'validation="type:{1},no_desc:true"/>',
'<div class="text"><div class="{2}"><input type="checkbox" name="SyncSameName" value="0" />','同步修改子栏目下同名变量值','</div></div>'
].join("");
var m_sOperSelectTemplate = ['<div class="container"><SELECT id="ArgValue" _value={0} style="width:100px;height:20px;" validation="type:{2},no_desc:true">{1}</SELECT>',
'<div class="{3}"><input type="checkbox" name="SyncSameName" value="0" />','同步修改子栏目下同名变量值','</div></div>'].join("");
var m_sOperOptionTemplate = ['<option value="{0}">{1}</option>'].join('');
var m_sOperBooleanOptionTrue = [ '<option value="{0}">{1}</option>',
'<option value="0">{2}</option>'].join("");
var m_sOperBooleanOptionFalse = [ '<option value="1">{2}</option>',
'<option value="{0}">{1}</option>'].join("");
var m_sListOperComBoxOption = ['<span class="text_txt combo_cnt"><input name="ArgValue" id="ArgValue" value="{0}" class="kuang_as combo_input" validation="type:{2},no_desc:true"/>',
'<span class="combo_icon"><select id="selFieldInfo"><option>' + (wcm.LANG.TEMPLATEARG_31 || '请选择') + '</option>{1}</select></span>'].join("");
var m_sMultiTemplateFromListEditor = ['<input name="argFeild" class="input_text" id="ArgValue" onclick="Event.stop(window.event || arguments[0])" value="{0}" ',
'validation="type:{1},max_len:500,no_desc:true"/> ',
'<span id="multieditor" onclick=popupEditor(\"{2}\",\"{3}\",arguments[0]) title={4} class="text_param" style="padding-left:7px">&nbsp;&nbsp;&nbsp;</span>'].join("");
var m_sMultiTemplateFromOperEditor = ['<textarea id="ArgValue" rows="8" cols="29" style="width:100%;overflow-y:auto;overflow-x:hidden" validation="type:{1},no_desc:true,max_len:500,">{0}</textarea>',
'<div class="text"><div class="{2}"><input type="checkbox" name="SyncSameName" value="0" />','同步修改子栏目下同名变量值','</div></div>'].join("");
var m_sOperComBoxOption = ['<div class="container"><span class="text_txt combo_cntoper"><input name="ArgValue" id="ArgValue" value="{0}" class="kuang_as combo_input" validation="type:{2},no_desc:true"/>',
'<span class="combo_icon"><select id="selFieldInfo"><option>' + (wcm.LANG.TEMPLATEARG_31 || '请选择') + '</option>{1}</select></span>','</span><div class="{3}"><input type="checkbox" name="SyncSameName" value="0" />','同步修改子栏目下同名变量值','</div></div>'].join("");
function getHelper(){
return new com.trs.web2frame.BasicDataHelper();
}
function getValidationType(stype){
if(stype){
if(stype == "STRING" || stype == "MULTI")
return 'String';
else if(stype == "NUMBER")
return 'number';
else if(stype == "INTEGER")
return 'int';
else return 'String';
}
else return 'String';
}
function getHtml(obj, fromListEditor, bChannel){
var sArgName = obj.getPropertyAsString('argName');
var value = $transHtml(obj.getPropertyAsString('value'));
var type = obj.getPropertyAsString('type');
var templateid = obj.getProperty('templateId');
var canCustom = obj.getPropertyAsString('canCustom');
var validationType = getValidationType(type);
var sEditable = 'false';
if(canCustom =='true'){
sEditable = "editable";
}
else sEditable = "readonly";
var aHtml = [];
if(type == "BOOLEAN"){
var sBooleanOptions = null;
if(value=='是')sBooleanOptions = String.format(m_sOperBooleanOptionTrue, 1, value, '否');
else if(value.toUpperCase()=='TRUE')sBooleanOptions = String.format(m_sOperBooleanOptionTrue, 1, value, 'false');
else if(value=='否')sBooleanOptions = String.format(m_sOperBooleanOptionFalse, 0, value,'是');
else if(value=='false')sBooleanOptions = String.format(m_sOperBooleanOptionFalse, 0, value,'true');
return String.format(m_sOperSelectTemplate, (value=='是' || value.toUpperCase()=='TRUE') ? 1 : 0, sBooleanOptions, validationType, (bChannel ? "syncSameNameBooleanDiv" : "display-none"));
}
else if(type == "MULTI"){
if(fromListEditor==1) return String.format(m_sMultiTemplateFromListEditor, value==null ? "" : value, validationType, 'ArgValue', sArgName, (wcm.LANG['TEMPLATEARG_2'] || "点击转到大的编辑区域"));
return String.format(m_sMultiTemplateFromOperEditor, value==null ? "" : value, validationType, (bChannel ? "syncSameNameDiv" : "display-none"));
}
else if(m_oArgs[sArgName]){
for(var index = 0; index < m_oArgs[sArgName].length; index++){
aHtml.push(String.format(m_sOperOptionTemplate, m_oArgs[sArgName][index][0],m_oArgs[sArgName][index][1]));
}
if(canCustom == "false")return String.format(m_sOperSelectTemplate, value==null ? "" : value, aHtml.join(""), validationType, (bChannel ? "syncSameNameDiv" : "display-none"));
else return String.format(fromListEditor ? m_sListOperComBoxOption : m_sOperComBoxOption, value==null ? "" : value, aHtml.join(""), validationType, (bChannel ? "syncSameNameDiv" : "display-none"));
}
else 
return String.format(m_sOperInputTemplate, value==null ? "" : value, validationType, (bChannel ? "syncSameNameDiv" : "display-none"));
}
function makePhysicalFieldsSelect(){
Event.observe('selFieldInfo', 'change', function(){
var selFieldInfo = $('selFieldInfo');
var optionCurr = selFieldInfo.options[selFieldInfo.selectedIndex];
if(optionCurr==null)return;
$('ArgValue').value = selFieldInfo.value;
$('ArgValue').focus();
return false;
});
}
function submitData(_postData, ContextEvent, event){
var elEvent = window.event || event;
var dom = $('ArgValue');
if(dom){
var validInfo = ValidatorHelper.valid(dom);
if(validInfo && !validInfo.isValid()){
Ext.Msg.warn(validInfo.getWarning(), function(){
try{
dom.focus();
}catch(error){
}
});
return;
}
var sArgName = _postData['parameterName'];
if(sArgName.trim().length == 0) {
Ext.Msg.warn("变量名为空，请检查模板中定义的变量。");
CMSObj.afteredit(ContextEvent)();
return;
}
Object.extend(_postData, {ArgValue : dom.value});
BasicDataHelper.Call('wcm6_templateArg', 'saveArgument', _postData, true, function(){
CMSObj.afteredit(ContextEvent)();
}.bind(this));
return false;
}
}
Ext.apply(wcm.domain.TemplateArgMgr, {
edit : function(event){
if(event.length() == 0){
Ext.Msg.$fail(wcm.LANG['TEMPLATEARG_6'] || '没有选择任何模板变量');
return;
} 
var objs = event.getObjs();
if(objs.length() > 1){
Ext.Msg.$fail(wcm.LANG['TEMPLATEARG_7'] || '只能对一个模板变量进行修改');
return;
}
var obj = event.getObjs().getAt(0);
var type = obj.getPropertyAsString('type');
var nHostType = event.getHost().getIntType();
var _postData = {
TemplateId : obj.getPropertyAsInt('templateId'),
parameterName : obj.getPropertyAsString('argName'),
HostId : event.getHost().getId(),
HostType : nHostType,
PrexName : obj.getPropertyAsString('PrexName')
};
var bChannel = nHostType == 101 ? true : false;
var sHtml = getHtml(obj, 0, bChannel);
var sId = 'edit_template_arg';
cb = new wcm.CrashBoard({
baseCls : 'wcm-cbd mycbd',//修改重写后样式定义
getDragElId : function(){
return this.header;
},
id : sId,
title : wcm.LANG['TEMPLATEARG_8'] || '修改参数值',
html : sHtml,
renderTo : document.body,
width:'300px',
height : type == 'MULTI' ? '150px' : '50px',
maskable : true, 
btns : [
{
text : wcm.LANG['TEMPLATEARG_9'] || '确定', 
cmd : function(){
var dom = this.getElement('ArgValue');
var checkDom = this.getElement('SyncSameName');
var validInfo = ValidatorHelper.valid(dom);
if(validInfo && !validInfo.isValid()){
Ext.Msg.warn(validInfo.getWarning(), function(){
try{
dom.focus();
}catch(error){
}
});
return false;
}
var sArgName = _postData['parameterName'];
if(sArgName.trim().length == 0) {
Ext.Msg.warn("变量名为空，请检查模板中定义的变量。");
return;
}
var bSyncSameName = (checkDom && checkDom.checked) ? true : false;
Object.extend(_postData,{ArgValue : dom.value, SyncSameName : bSyncSameName});
BasicDataHelper.Call('wcm6_templateArg', 'saveArgument', _postData, true, function(){
CMSObj.afteredit(event)();
this.close();
}.bind(this));
this.hide();
return false;
}
},
{
text : wcm.LANG['TEMPLATEARG_10'] || '取消'
}
]
});
cb.on('show', function(){
var dom = $('ArgValue');
if($("selFieldInfo")) {
for(var j=0; j<$("selFieldInfo").options.length;j++){
if(dom.value == $("selFieldInfo").options[j].value){
$("selFieldInfo").selectedIndex = j;
break;
}
}
makePhysicalFieldsSelect();
}
if(dom){
if(dom.tagName != "SELECT") return;
dom.value = dom.getAttribute("_value");
}
})
cb.show();
},
listEdit : function(event){
var listEvent = event;
if(event.length() == 0){
Ext.Msg.$fail(wcm.LANG['TEMPLATEARG_6'] || '没有选择任何模板变量');
return;
} 
var objs = event.getObjs();
if(objs.length() > 1){
Ext.Msg.$fail(wcm.LANG['TEMPLATEARG_7'] || '只能对一个模板变量进行修改');
return;
}
var obj = event.getObjs().getAt(0);
var type = obj.getPropertyAsString('type');
var _postData = {
TemplateId : obj.getPropertyAsInt('templateId'),
parameterName : obj.getPropertyAsString('argName'),
HostId : event.getHost().getId(),
HostType : event.getHost().getIntType(),
PrexName : obj.getPropertyAsString('PrexName')
};
var sHtml = getHtml(obj, 1);
var valueEl = $('value_sp_' + obj.getId());
valueEl.innerHTML = sHtml;
var dom = $('ArgValue');
dom.focus();
if($("selFieldInfo")) {
for(var j=0; j<$("selFieldInfo").options.length;j++){
if(dom.value == $("selFieldInfo").options[j].value){
$("selFieldInfo").selectedIndex = j;
break;
}
}
makePhysicalFieldsSelect();
}
if(dom && dom.tagName == "SELECT"){
dom.value = dom.getAttribute("_value");
dom.onchange = submitData.bind(window, _postData, listEvent);
}
var config = {
excludes : [{'selFieldInfo':false}, {'multieditor':false}],
element : 'ArgValue',
blur : function(){
submitData.bind(window, _postData, listEvent)();
}
};
BlurMgr.add(config);
},
cancelArgVal : function(event){
if(event.length() == 0){
Ext.Msg.$fail(wcm.LANG['TEMPLATEARG_6'] || '没有选择任何模板变量！');
return;
} 
var checkedDocIds = event.getIds();
if(checkedDocIds.length == 0) {
return;
}
var objs = event.getObjs();
var nTemplateId = objs.getAt(0).getPropertyAsInt('templateId',0);
var sTempPrexName = objs.getAt(0).getPropertyAsString('PrexName');
for (var i = 0; i < objs.length(); i++){
var obj = objs.getAt(i);
var tempId = obj.getPropertyAsInt('templateId',0);
if(nTemplateId != tempId) {
Ext.Msg.$fail(String.format("无法同时取消两个模板[{0}]和[{1}]中的变量值！",nTemplateId,tempId));
return;
}
var tempPrexName = obj.getPropertyAsString('PrexName');
if(sTempPrexName != tempPrexName) {
Ext.Msg.$fail(String.format("无法同时取消两个模板标识[{0}]和[{1}]所对应模板的变量值！",sTempPrexName, tempPrexName));
return;
}
sTempPrexName = tempPrexName;
}
Ext.Msg.confirm(wcm.LANG['TEMPLATEARG_17'] || '确实要取消变量的赋值吗？',{
yes : function(){
var postData = {
templateId: nTemplateId,
parameterNames: objs.getPropertyAsString('argName')
};
if(sTempPrexName && (sTempPrexName = sTempPrexName.trim()) != ''
&& sTempPrexName.toLowerCase() != 'null') {
postData['prexName'] = sTempPrexName;
}
Object.extend(postData, PageContext.params);
BasicDataHelper.call('wcm6_templateArg', 'cancelArgVal', postData, true, function(){
CMSObj.afteredit(event)();
});
},
no: function(){
return;
}
});
}
});
})();
(function(){
var pageObjMgr = wcm.domain.TemplateArgMgr;
var reg = wcm.SysOpers.register;
reg({
key : 'delete',
type : 'templateArg',
desc : wcm.LANG['TEMPLATEARG_19'] || '取消赋值',
title : wcm.LANG['TEMPLATEARG_19'] || '取消赋值...',
rightIndex : -1,
order : 2,
fn : pageObjMgr['cancelArgVal']
});
reg({
key : 'edit',
type : 'templateArg',
desc : wcm.LANG['TEMPLATEARG_18'] || '修改模板变量',
title : wcm.LANG['TEMPLATEARG_18'] || '修改模板变量...',
rightIndex : -1,
order : 1,
fn : pageObjMgr['edit']
});
reg({
key : 'delete',
type : 'templateArgs',
desc : wcm.LANG['TEMPLATEARG_19'] || '取消赋值',
title : wcm.LANG['TEMPLATEARG_19'] || '取消赋值...',
rightIndex : -1,
order : 2,
fn : pageObjMgr['cancelArgVal']
});
})();

Ext.ns('wcm.TemplateAgrs', 'wcm.TemplateArg');
WCMConstants.OBJ_TYPE_TEMPLATEARG = 'TemplateArg';
wcm.Templates = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_TEMPLATE;
wcm.Templates.superclass.constructor.call(this, _config, _context);
}
Ext.extend(wcm.Templates, wcm.CMSObjs, {
});
wcm.Template = function(_config, _context){
this.objType = WCMConstants.OBJ_TYPE_TEMPLATE;
wcm.Template.superclass.constructor.call(this, _config, _context);
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
CMSObj.register(WCMConstants.OBJ_TYPE_TEMPLATE, 'wcm.Template');
Ext.extend(wcm.Template, wcm.CMSObj, {
});

(function(){
var sName = wcm.LANG['TEMPLATEARG'] || '模板变量';
var m_sNameTemplate = '<B>&nbsp;{1}:&nbsp;&nbsp;</B>{0}<br>';
var m_sTypeTemplate = '<B>&nbsp;{1}:&nbsp;&nbsp;</B>{0}<br>';
var m_sValueTemplate = '<span class="arg_detail_value" title="{0}"><B>{1}:&nbsp</B>{0}</span>'
function getEditInfo(obj){
var sArgName = obj.getProperty('argName');
var value = $transHtml(obj.getProperty('value'));
var type = obj.getProperty('type');
return String.format(m_sNameTemplate,sArgName,wcm.LANG['TEMPLATEARG_0'] || '名称') 
+ String.format(m_sTypeTemplate,type, wcm.LANG['TEMPLATEARG_20'] || '类型') 
+ String.format(m_sValueTemplate, value==null ? "" : value, wcm.LANG['TEMPLATEARG_1'] || '参数值');
}
wcm.PageOper.registerPanels({
templateInRoot : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INROOT'] || '库{0}操作任务', sName),
displayNum : 5
},
templateArgInSite : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INSITE'] || '站点{0}操作任务', sName),
displayNum : 5,
detail : function(){
return wcm.LANG['TEMPLATEARG_5'] || '模板变量信息';
}
},
templateArgInChannel : {
isHost : true,
title : String.format(wcm.LANG['OPER_TITLE_INCHANNEL'] || '栏目{0}操作任务', sName),
displayNum : 5
},
templateArg : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
displayNum : 7,
detail : function(cmsobjs, opt){
var obj = cmsobjs.getAt(0);
return getEditInfo(obj);
}
},
templateArgs : {
title : String.format(wcm.LANG['OPER_TITLE_OBJ'] || '{0}操作任务', sName),
displayNum : 7
}
});
})();

