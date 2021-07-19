var ValidatorLang =
{
LOCALE : 'cn',
TEXT1 : '时间的time_format格式不对',
TEXT2 : '没有指定field域',
TEXT3 : '没有指定sub_type域',
TEXT4 : '域指定不合法',
TEXT5 : '不应包含\\/:*?\"<>|等字符',
TEXT6 : '普通字符，\\/:*?\"<>|等字符除外',
TEXT7 : "没有匹配日期格式:",
TEXT8 : "小时应该是0到23的整数",
TEXT9 : "分应该是0到59的整数",
TEXT10 : "秒应该是0到59的整数",
TEXT11 : "指定的",
TEXT12 : "不是分割的两个数字！",
TEXT13 : "不是个数字！",
TEXT14 : "月份应该为1到12的整数",
TEXT15 : "每个月的天数应该为1到31的整数",
TEXT16 : "该月不存在31号",
TEXT17 : "2月最多有29天",
TEXT18 : "闰年2月才有29天",
TEXT19 : "小时应该是0到23的整数",
TEXT20 : "分应该是0到59的整数",
TEXT21 : "秒应该是0到59的整数",
TEXT22 : "日期格式错误：",
TEXT23 : '{0}不能为空.',
TEXT24 : '{0}必须和{1}一致.',
TEXT25 : '{0}必须为数字.',
TEXT26 : '{0}必须为整数.',
TEXT27 : '{0}必须为小数.',
TEXT28 : '{0}必须为双精度.',
TEXT29 : '{0}最小长度为{1}.',
TEXT30 : '{0}最大长度为{1}.',
TEXT31 : '{0}最小值为 {1}.',
TEXT32 : '{0}最大值为{1}.',
TEXT33 : '{0}长度范围为{1}~{2}.',
TEXT34 : '{0}值范围为{1}~{2}.',
TEXT35 : '{0}期望格式为:http(s)://[站点](:[端口])/(子目录).',
TEXT36 : '{0}期望格式为:xxx.xxx.xxx.xxx..',
TEXT37 : '{0}符合IPV4格式，期望格式为: 192.9.200.114.',
TEXT38 : '{0}由字母,数字,下划线组成.',
TEXT39 : '{0}由字母开始的字母,数字,下划线组成.',
TEXT40 : '必须选择一个{0}',
TEXT41 : '{0}格式必须为\"hh:mm\"，如：09:03',
TEXT42 : '{0}格式必须为\"h:m\"，如：9:3' ,
TEXT43 : '{0}格式不合法.',
TEXT44 : '{0}小于最小长度{1}.',
TEXT45 : '{0}大于最大长度{1}.',
TEXT46 : '{0}小于最小值({1}).',
TEXT47 : '{0}大于最大值({1}).',
TEXT48 : '{0}长度超出范围{1}~{2}.',
TEXT49 : '{0}值超出范围{1}~{2}.',
TEXT50 : '{0}正确格式为：http(s)://[站点](:[端口])/(子目录).',
TEXT51 : '{0}不合法,正确格式为:xxx.xxx.xxx.xxx..',
TEXT52 : '{0}不符合IPV4格式！正确格式为: 192.9.200.114.',
TEXT53 : '{0}IPV4最高位不能为0！正确格式为: 192.9.200.114.',
TEXT54 : '{0}IPV4任一位数值不能超过255！正确格式为: 192.9.200.114.',
TEXT55 : '{0}不是由字母、数字、下划线组成.',
TEXT56 : '{0}不是由字母开始的字母,数字,下划线组成.',
TEXT57 : '小时必须在0~23之间',
TEXT58 : '分钟必须在0~59之间',
TEXT59 : '秒必须在0~59之间',
TEXT60 : '{0}由中文或字母开头的字符串组成.',
TEXT61 : '{0}不是由中文或字母开头的字符串组成.'
}

Ext.ns('com.trs.validator', 'ValidatorLang');
var $ValidatorConfigs = com.trs.validator.ValidatorConfigs = {
PREFIX_HINT_SPAN_ID : 'com_trs_wcm_ajax_hint_node_',
PREFIX_WARNING_SPAN_ID : 'com_trs_wcm_ajax_warning_node_',
PREFIX_MESSAGE_SPAN_ID : 'com_trs_wcm_ajax_message_node_',
WARNING_LOG_PATH : WCMConstants.WCM6_PATH+'com.trs.validator/images/error.gif',
MESSAGE_LOG_PATH : WCMConstants.WCM6_PATH+'com.trs.validator/images/info.gif',
WARNING_CLASSNAME_KEY : "warningClassKey",
MESSAGE_CLASSNAME_KEY : "messageClassKey",
WARNING_CLASSNAME_MOUSE : "warningClassMouse",
MESSAGE_CLASSNAME_MOUSE : "messageClassMouse",
REQUIREDCLASS : "requiredClass",
UserLanguage : 'auto',
DATE_FORMAT_DEFAULT : "yyyy-mm-dd",
TIME_FORMAT_DEFAULT : "hh:MM:ss",
MOUSE_MODE : false,
SHOW_ALL_MODE : false,
FOCUS_MODE : true,
REQUIRED_HINT : true,
DRAFT_MODE : false,
WARNING_BORDER : 'red 1px solid',
DESC : "desc" .toLowerCase(),
TYPE : "type" .toLowerCase(),
REQUIRED : "required" .toLowerCase(),
EQUALS_WITHS : "equals_with" .toLowerCase(),
MIN_LEN : "min_len" .toLowerCase(),
MAX_LEN : "max_len" .toLowerCase(),
LENGTH_RANGE : "length_range" .toLowerCase(),
MIN : "min" .toLowerCase(),
MAX : "max" .toLowerCase(), 
VALUE_RANGE : "value_range" .toLowerCase(),
DATE_FORMAT : "date_format" .toLowerCase(),
FORMAT : "format" .toLowerCase(),
MESSAGE : "message" .toLowerCase(),
WARNING : "warning" .toLowerCase(), 
METHODS : "methods" .toLowerCase(),
SHOWID : "showid" .toLowerCase(),
COMMON_CHAR : "common_char" .toLowerCase(),
COMMON_CHAR2 : "common_char2" .toLowerCase(),
NO_CLASS : "no_class" .toLowerCase(),
BLUR_SHOW : 'blur_show' .toLowerCase(),
RPC : "rpc" .toLowerCase(),
CLOSE : "close" .toLowerCase(),
TIME_FORMAT : "time_format" .toLowerCase(),
EXCLUDE : "exclude" .toLowerCase(),
SUBTYPE : "sub_type" .toLowerCase(),
RELATION : "relation" .toLowerCase(),
FIELD : "field" .toLowerCase(),
NO_DESC : "no_desc" .toLowerCase(),
REQUIRE_CONTAINER : "require_container".toLowerCase(),
NO_REQUIRE_HINT : "no_require_hint" .toLowerCase(),
MESSAGE_INFO : {
"zh-cn" : {
REQUIRED : ValidatorLang.TEXT23 || '{0}不能为空.',
EQUALS_WITHS : ValidatorLang.TEXT24 || '{0}必须和{1}一致.',
NUMBER : ValidatorLang.TEXT25 || '{0}必须为数字.',
INT : ValidatorLang.TEXT26 || '{0}必须为整数.',
FLOAT : ValidatorLang.TEXT27 || '{0}必须为小数.',
DOUBLE : ValidatorLang.TEXT28 || '{0}必须为双精度.',
MIN_LEN : ValidatorLang.TEXT29 || '{0}最小长度为{1}.',
MAX_LEN : ValidatorLang.TEXT30 || '{0}最大长度为{1}.',
MIN : ValidatorLang.TEXT31 || '{0}最小值为{1}.',
MAX : ValidatorLang.TEXT32 || '{0}最大值为{1}.',
LENGTH_RANGE : ValidatorLang.TEXT33 || '{0}长度范围为{1}~{2}.',
VALUE_RANGE : ValidatorLang.TEXT34 || '{0}值范围为{1}~{2}.',
URL : ValidatorLang.TEXT35 || '{0}期望格式为:http(s)://[站点](:[端口])/(子目录).',
LINK : ValidatorLang.TEXT36 || '{0}期望格式为:xxx.xxx.xxx.',
IPV4 : ValidatorLang.TEXT37 || '{0}符合IPV4格式，期望格式为: 192.9.200.114.',
COMMON_CHAR : ValidatorLang.TEXT38 || '{0}由字母,数字,下划线组成.',
COMMON_CHAR2 : ValidatorLang.TEXT39 || '{0}由字母开始的字母,数字,下划线组成.',
SELECT : ValidatorLang.TEXT40 || '必须选择一个{0}',
TIME_ONE : ValidatorLang.TEXT41 || '{0}格式必须为"hh:mm"，如：09:03',
TIME_TWO : ValidatorLang.TEXT42 || '{0}格式必须为"h:m"，如：9:3',
STRING2 : ValidatorLang.TEXT60 || '{0}由中文或字母开头的字符串组成.'
}
},
WARNING_INFO : {
"zh-cn" : {
DEFAULT : ValidatorLang.TEXT43 || '{0}格式不合法.',
REQUIRED : ValidatorLang.TEXT23 || '{0}不能为空.',
EQUALS_WITHS : ValidatorLang.TEXT24 || '{0}必须和{1}一致.',
NUMBER : ValidatorLang.TEXT25 || '{0}必须为数字.',
INT : ValidatorLang.TEXT26 || '{0}必须为整数.',
FLOAT : ValidatorLang.TEXT27 || '{0}必须为小数.',
DOUBLE : ValidatorLang.TEXT28 || '{0}必须为双精度.', 
MIN_LEN : ValidatorLang.TEXT44 || '{0}小于最小长度{1}.',
MAX_LEN : ValidatorLang.TEXT45 || '{0}大于最大长度{1}.',
MIN : ValidatorLang.TEXT46 || '{0}小于最小值({1}).',
MAX : ValidatorLang.TEXT47 || '{0}大于最大值({1}).',
LENGTH_RANGE : ValidatorLang.TEXT48 || '{0}长度不在范围{1}~{2}.',
VALUE_RANGE : ValidatorLang.TEXT49 || '{0}值不在范围{1}~{2}.',
URL : ValidatorLang.TEXT50 || '{0}正确格式为：http(s)://[站点](:[端口])/(子目录).',
LINK : ValidatorLang.TEXT51 || '{0}不合法,正确格式为:xxx.xxx.xxx.',
IPV4 : ValidatorLang.TEXT52 || '{0}不符合IPV4格式！正确格式为: 192.9.200.114.',
IPV4_HIGH_EQUAL_0 : ValidatorLang.TEXT53 || '{0}IPV4最高位不能为0！正确格式为: 192.9.200.114.',
IPV4_BEYOND_255 : ValidatorLang.TEXT54 || '{0}IPV4任一位数值不能超过255！正确格式为: 192.9.200.114.',
COMMON_CHAR : ValidatorLang.TEXT55 || '{0}不是由字母、数字、下划线组成.',
COMMON_CHAR2 : ValidatorLang.TEXT56 || '{0}不是由字母开始的字母,数字,下划线组成.',
SELECT : ValidatorLang.TEXT40 || '必须选择一个{0}',
TIME : ValidatorLang.TEXT41 || '{0}格式必须为"hh:mm"，如：09:03',
HOUR : ValidatorLang.TEXT57 || '小时必须在0~23之间',
MINUTE : ValidatorLang.TEXT58 || '分钟必须在0~59之间',
SECOND : ValidatorLang.TEXT59 || '秒必须在0~59之间',
STRING2 : ValidatorLang.TEXT61 || '{0}不是由中文或字母开头的字符串组成.'
}
},
setMouseMode : function(bool){
$ValidatorConfigs.MOUSE_MODE = bool;
},
getMouseMode : function(){
return $ValidatorConfigs.MOUSE_MODE;
},
setShowAllMode : function(bool){
$ValidatorConfigs.SHOW_ALL_MODE = bool;
},
getShowAllMode : function(){
return $ValidatorConfigs.SHOW_ALL_MODE;
},
setFocusMode : function(bool){
$ValidatorConfigs.FOCUS_MODE = bool;
},
getFocusMode : function(){
return $ValidatorConfigs.FOCUS_MODE;
},
setRequiredHint : function(bool){
$ValidatorConfigs.REQUIRED_HINT = bool;
},
getRequiredHint : function(){
return $ValidatorConfigs.REQUIRED_HINT;
},
setDraftMode : function(bool){
$ValidatorConfigs.DRAFT_MODE = bool;
},
getDraftMode : function(){
return $ValidatorConfigs.DRAFT_MODE;
}
};

if(!window.Class){
var Class = {};
}
Ext.apply(Class, {
create: function() {
return function() {
this.initialize.apply(this, arguments);
}
}
});
Ext.applyIf(Array.prototype, {
last : function(){
return this[this.length - 1];
}
});
Ext.applyIf(Event, {
pointerX: function(event) {
return event.pageX || (event.clientX +
(document.documentElement.scrollLeft || document.body.scrollLeft));
},
pointerY: function(event) {
return event.pageY || (event.clientY +
(document.documentElement.scrollTop || document.body.scrollTop));
}
});
var AttributeHelper = {
prefix : 'attr_',
autoId : 0,
cache : {},
setAttribute : function(element, sAttrName, sAttrValue){
element = $(element);
if(!element) return false;
var _id = this.getId(element);
var cache = this.cache;
var attributes = cache[_id];
if(!attributes){
attributes = cache[_id] = {};
}
attributes[sAttrName.toUpperCase()] = sAttrValue;
},
getAttribute : function(element, sAttrName){
element = $(element);
if(!element) return null;
var _id = this.getId(element);
var cache = this.cache;
var attributes = cache[_id];
if(!attributes){
return null;
}
return attributes[sAttrName.toUpperCase()];
},
removeAttribute : function(element, sAttrName){
element = $(element);
if(!element) return;
var _id = this.getId(element);
var cache = this.cache;
var attributes = cache[_id];
if(!attributes){
return;
}
delete attributes[sAttrName.toUpperCase()];
},
getId : function(element){
var _id = element.getAttribute("_id");
if(_id != undefined) return _id;
_id = element.name || element.id || (this.prefix + (++this.autoId));
_id = _id.toUpperCase();
element.setAttribute("_id", _id);
return _id;
}
};
AbstractValidator = Class.create();
AbstractValidator.prototype = {
initialize : function(_field) {
this.field = $(_field);
this.validateObj = AttributeHelper.getAttribute(this.field, "_VALIDATEOBJ_");
this.warning = "";
},
execute : function(){
this.warning = "";
if(this.validateObj[$ValidatorConfigs.CLOSE] != undefined){
try{
if(eval(this.validateObj[$ValidatorConfigs.CLOSE]))
return true;
}catch(error){
alert("AbstractValidator.prototype:execute:" + error.message);
return false;
}
}
if($F(this.field).trim() == ''){
if(ValidationHelper.hasRequired(this.validateObj)){
this.warning += this.getReplaceInfoContent("REQUIRED", "WARNING_INFO");
return false;
}else{
return true;
}
}
if(this.validateObj[$ValidatorConfigs.FORMAT]){
eval("this.outerformat = " + this.validateObj[$ValidatorConfigs.FORMAT] + ";");
if($F(this.field).trim() != '' && !this.outerformat.test($F(this.field))){
this.warning += this.validateObj[$ValidatorConfigs.WARNING] || this.getWarning();
return false;
}
}
if($F(this.field).trim() != '' && this.format && !this.format.test($F(this.field))){
this.warning += this.getWarning();
return false;
}
var arrayMethod = [];
arrayMethod.push(this.method || Ext.emptyFn);
var strMethods = this.validateObj[$ValidatorConfigs.METHODS];
if(strMethods){
if(String.isString(strMethods)){
Array.prototype.push.apply(arrayMethod, strMethods.split(","));
}else{
if(!Array.isArray(strMethods)){
strMethods = [strMethods];
}
Array.prototype.push.apply(arrayMethod, strMethods);
}
}
var result = arrayMethod[0].call(this);
if(!result || arrayMethod.length <= 1) return result;
result = true;
for (var i = 1; i < arrayMethod.length; i++){
if(Ext.isFunction(arrayMethod[i])){
var temp = arrayMethod[i].call(this);
if(temp == null)result = null;
if(temp === false)result = false;
continue;
}
arrayMethod[i] = arrayMethod[i].trim();
if(arrayMethod[i] != ""){
eval("var temp = " + arrayMethod[i]);
if(temp == null)result = null;
if(temp === false)result = false;
}
}
return result;
},
getDesc : function(){
var sDesc = this.field.getAttribute("validation_desc");
if(sDesc != null) return sDesc;
return this.validateObj[$ValidatorConfigs.DESC];
},
getWarning : function(){
return ValidationHelper.getReplaceInfoContent("DEFAULT", "WARNING_INFO", this.validateObj[$ValidatorConfigs.DESC] || this.field.name || this.field.id);
},
getReplaceInfoContent : function(info, infoType, args, _desc){
var desc = '';
if(this.validateObj[$ValidatorConfigs.NO_DESC] == undefined){
desc = this.getDesc() || _desc || this.validateObj[$ValidatorConfigs.DESC] || this.field.name || this.field.id;
}
return ValidationHelper.getReplaceInfoContent(info, infoType, desc, args);
},
checkSyntaxValidation:function(){
return true;
},
getMessage : function(){
return this.validateObj[$ValidatorConfigs.MESSAGE] || '';
}
};
if(ValidatorLang.LOCALE == 'en'){
Ext.apply(AbstractValidator.prototype, {
getDesc : function(_desc){
return "It ";
},
getReplaceInfoContent : function(info, infoType, args, _desc){
var desc = this.getDesc() || _desc;
return ValidationHelper.getReplaceInfoContent(info, infoType, desc, args);
}
});
}
var NumberValidator = Class.create();
Object.extend(NumberValidator.prototype, AbstractValidator.prototype);
Object.extend(NumberValidator.prototype, {
initialize : function(_field) {
AbstractValidator.prototype.initialize.call(this, _field);
this.type = this.validateObj[$ValidatorConfigs.TYPE].trim().toUpperCase();
if(this.validateObj[$ValidatorConfigs.TYPE].trim().toLowerCase() == 'int'){
this.parseMethod = parseInt;
this.format = new RegExp('^-?\\d+(e[+-]?\\d+)?$', "i");
}else{
this.parseMethod = parseFloat;
this.format = new RegExp('^-?\\d+(\\.\\d+)?(e[+-]?\\d+)?$', "i");
}
if(!this.checkValidationSyntax()) return false;
},
method : function(){
if(this.validateObj[$ValidatorConfigs.VALUE_RANGE]){
if(this.minValue > this.parseMethod($F(this.field)) || this.parseMethod($F(this.field)) > this.maxValue){
this.warning += this.getReplaceInfoContent("VALUE_RANGE", "WARNING_INFO", [this.minValue, this.maxValue]);
return false;
}
return true;
}
var flag = true;
if(this.validateObj[$ValidatorConfigs.MIN]){
if(this.parseMethod($F(this.field)) < this.minValue){
flag = false;
this.warning += this.getReplaceInfoContent("MIN", "WARNING_INFO", this.minValue);
}
}
if(this.validateObj[$ValidatorConfigs.MAX]){
if(this.parseMethod($F(this.field)) > this.maxValue){
flag = false;
this.warning += this.getReplaceInfoContent("MAX", "WARNING_INFO", this.maxValue);
}
}
return flag;
},
getMessage:function (){
if(this.validateObj[$ValidatorConfigs.MESSAGE])return this.validateObj[$ValidatorConfigs.MESSAGE];
var message = "";
if(this.validateObj[$ValidatorConfigs.VALUE_RANGE]){
message += this.getReplaceInfoContent("VALUE_RANGE", "MESSAGE_INFO", [this.minValue, this.maxValue]);
return message;
}
if(this.validateObj[$ValidatorConfigs.MIN]){
message += this.getReplaceInfoContent("MIN", "MESSAGE_INFO", this.minValue);
}
if(this.validateObj[$ValidatorConfigs.MAX]){
message += this.getReplaceInfoContent("MAX", "MESSAGE_INFO", this.maxValue);
}
return message;
},
getWarning : function(){
return this.getReplaceInfoContent(this.type, "WARNING_INFO", null, this.validateObj[$ValidatorConfigs.DESC] || this.field.name || this.field.id);
},
checkValidationSyntax : function(){
if(this.validateObj[$ValidatorConfigs.VALUE_RANGE]){
var valueRange = this.validateObj[$ValidatorConfigs.VALUE_RANGE].split(",");
for (var i = 0; i < valueRange.length; i++){
valueRange[i] = (valueRange[i] + "").trim();
}
if((valueRange.length != 2) ||
(valueRange[0] != "" && isNaN(this.parseMethod(valueRange[0]))) || 
(valueRange[1] != "" && isNaN(this.parseMethod(valueRange[1])))){
alert('NumberValidator.prototype:checkValidationSyntax:' + String.format("指定的{0}不是分割的两个数字！",this.validateObj[$ValidatorConfigs.VALUE_RANGE]));
return false;
}
this.minValue = (valueRange[0] === "" ? Number.NEGATIVE_INFINITY : this.parseMethod(valueRange[0]));
this.maxValue = (valueRange[1] === "" ? Number.POSITIVE_INFINITY : this.parseMethod(valueRange[1]));
}
if(this.validateObj[$ValidatorConfigs.MIN]){
this.minValue = this.parseMethod(this.validateObj[$ValidatorConfigs.MIN].trim());
if(isNaN(this.minValue)){
alert('NumberValidator.prototype:checkValidationSyntax:' + String.format("指定的{0}不是个数字！",this.validateObj[$ValidatorConfigs.MIN]));
return false;
}
}
if(this.validateObj[$ValidatorConfigs.MAX]){
this.maxValue = this.parseMethod(this.validateObj[$ValidatorConfigs.MAX.trim()]);
if(isNaN(this.maxValue)){
alert('NumberValidator.prototype:checkValidationSyntax:' + String.format("指定的{0}不是个数字！",this.validateObj[$ValidatorConfigs.MIN]));
return false;
}
}
return true;
}
});
var StringValidator = Class.create();
Object.extend(StringValidator.prototype, AbstractValidator.prototype);
Object.extend(StringValidator.prototype, {
initialize : function(_field) {
AbstractValidator.prototype.initialize.call(this, _field);
if(!this.checkValidationSyntax()) return false;
},
method : function(){
this.getLength();
if(this.validateObj[$ValidatorConfigs.LENGTH_RANGE]){
if(this.minLen > this.length || this.length > this.maxLen){
this.warning += this.getReplaceInfoContent("LENGTH_RANGE", "WARNING_INFO", [this.minLen, this.maxLen]);
return false;
}
return true;
}
var flag = true;
if(this.validateObj[$ValidatorConfigs.MIN_LEN]){
if(this.length < this.minLen){
flag = false;
this.warning += this.getReplaceInfoContent("MIN_LEN", "WARNING_INFO", [this.minLen]);
}
}
if(this.validateObj[$ValidatorConfigs.MAX_LEN]){
if(this.length > this.maxLen){
flag = false;
this.warning += this.getReplaceInfoContent("MAX_LEN", "WARNING_INFO", [this.maxLen]);
}
}
return flag;
},
getLength : function(){
this.length = ($F(this.field)||'').byteLength();
},
getMessage : function(){
if(this.validateObj[$ValidatorConfigs.MESSAGE])return this.validateObj[$ValidatorConfigs.MESSAGE];
var message = "";
if(this.validateObj[$ValidatorConfigs.LENGTH_RANGE]){
message += this.getReplaceInfoContent("LENGTH_RANGE", "MESSAGE_INFO", [this.minLen, this.maxLen]);
return message;
}
if(this.validateObj[$ValidatorConfigs.MIN_LEN]){
message += this.getReplaceInfoContent("MIN_LEN", "MESSAGE_INFO", this.minLen);
}
if(this.validateObj[$ValidatorConfigs.MAX_LEN]){
message += this.getReplaceInfoContent("MAX_LEN", "MESSAGE_INFO", this.maxLen);
}
if(message == '' && ValidationHelper.hasRequired(this.validateObj)){
message = this.getReplaceInfoContent("REQUIRED", "MESSAGE_INFO");
}
return message;
},
checkValidationSyntax : function(){
if(this.validateObj[$ValidatorConfigs.LENGTH_RANGE]){
var lengthRange = this.validateObj[$ValidatorConfigs.LENGTH_RANGE].split(",");
for (var i = 0; i < lengthRange.length; i++){
lengthRange[i] = lengthRange[i].trim();
}
if((lengthRange.length != 2) ||
(lengthRange[0] != "" && isNaN(parseInt(lengthRange[0]))) || 
(lengthRange[1] != "" && isNaN(parseInt(lengthRange[1])))){
alert('StringValidator.prototype:checkValidationSyntax:' + String.format("指定的{0}不是分割的两个数字！",this.validateObj[$ValidatorConfigs.LENGTH_RANGE]));
return false;
}
this.minLen = (lengthRange[0] === "" ? 0 : parseInt(lengthRange[0]));
this.maxLen = (lengthRange[1] === "" ? Number.POSITIVE_INFINITY : parseInt(lengthRange[1]));
}
if(this.validateObj[$ValidatorConfigs.MIN_LEN]){
this.minLen = parseInt((""+this.validateObj[$ValidatorConfigs.MIN_LEN]).trim());
if(isNaN(this.minLen)){
alert('StringValidator.prototype:checkValidationSyntax:' + String.format("指定的{0}不是个数字！",this.validateObj[$ValidatorConfigs.MIN_LEN]));
return false;
}
}
if(this.validateObj[$ValidatorConfigs.MAX_LEN]){
this.maxLen = parseInt((""+this.validateObj[$ValidatorConfigs.MAX_LEN]).trim());
if(isNaN(this.maxLen)){
alert('StringValidator.prototype:checkValidationSyntax:' + String.format("指定的{0}不是个数字！",this.validateObj[$ValidatorConfigs.MAX_LEN]));
return false;
}
}
return true;
}
});
var CommonCharValidator = Class.create();
Object.extend(CommonCharValidator.prototype, StringValidator.prototype);
Object.extend(CommonCharValidator.prototype, {
format : /^\w*$/,
getMessage : function(){
if(this.validateObj[$ValidatorConfigs.MESSAGE])return this.validateObj[$ValidatorConfigs.MESSAGE];
var message = StringValidator.prototype.getMessage.call(this);
if(message != "")
message += this.getReplaceInfoContent("COMMON_CHAR", "MESSAGE_INFO", null, ' ');
else
message = this.getReplaceInfoContent("COMMON_CHAR", "MESSAGE_INFO");
return message;
},
getWarning : function(){
return this.getReplaceInfoContent("COMMON_CHAR", "WARNING_INFO");
}
});
var URLValidator = Class.create();
Object.extend(URLValidator.prototype, AbstractValidator.prototype);
Object.extend(URLValidator.prototype, {
format : /^(http|https):\/\/(?:(([A-Z0-9][A-Z0-9_-]*)(\.[A-Z0-9][A-Z0-9_-]*)+)|localhost)(:(\d+))?(\/)?/i, 
getMessage : function(){
if(this.validateObj[$ValidatorConfigs.MESSAGE])return this.validateObj[$ValidatorConfigs.MESSAGE];
return this.getReplaceInfoContent("URL", "MESSAGE_INFO");
},
method : function(){
var StringVal = new StringValidator(this.field);
var result = StringVal.execute();
this.warning = StringVal.warning;
return result;
}, 
getWarning:function(){
return this.validateObj[$ValidatorConfigs.WARNING] || this.getReplaceInfoContent("URL", "WARNING_INFO");
}
});
var IPV4Validator = Class.create();
Object.extend(IPV4Validator.prototype, AbstractValidator.prototype);
Object.extend(IPV4Validator.prototype, {
getMessage : function(){
if(this.validateObj[$ValidatorConfigs.MESSAGE])return this.validateObj[$ValidatorConfigs.MESSAGE];
return this.getReplaceInfoContent("IPV4", "MESSAGE_INFO");
},
getWarning : function(){
},
method : function(){
var format = /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/;
if(format.exec($F(this.field)) == null){
this.warning += this.getReplaceInfoContent("IPV4", 'WARNING_INFO');
return false;
}
if (RegExp.$1 == 0){
this.warning += this.getReplaceInfoContent("IPV4_HIGH_EQUAL_0", 'WARNING_INFO');
return false;
}
if (Math.max(RegExp.$1, RegExp.$2, RegExp.$3, RegExp.$4) > 255){
this.warning += this.getReplaceInfoContent('IPV4_BEYOND_255', "WARNING_INFO");
return false;
}
return true;
}
});
var DateValidator = Class.create();
Object.extend(DateValidator.prototype, AbstractValidator.prototype);
Object.extend(DateValidator.prototype, {
formatRegExp : /^(yy|yyyy)(-|\/)(m{1,2})(\2)(d{1,2})(\W+(h{1,2})(:)(M{1,2})((\8)(s{0,2}))?)?$/,
dateRegExp : /^(\d{2}|\d{4})(-|\/)(\d{1,2})(\2)(\d{1,2})(\W+(\d{1,2})(:)(\d{1,2})((\8)(\d{0,2}))?)?$/,
initialize : function(_field) {
AbstractValidator.prototype.initialize.call(this, _field);
if(!this.checkValidationSyntax()) return false;
},
method : function(){
var sFormat = this.validateObj[$ValidatorConfigs.DATE_FORMAT];
var sTemp = sFormat.replace(/yy|mm|dd|hh|ss/gi, "t").replace(/m|d|h|s/ig, '\\d{1,2}').replace(/t/ig, '\\d{2}');
var oRegExp = new RegExp("^"+sTemp+"$");
var sValue = $F(this.field);
if(!oRegExp.test(sValue)){
this.warning = ((ValidatorLang.TEXT7 || "没有匹配日期格式:") + this.validateObj[$ValidatorConfigs.DATE_FORMAT].toLowerCase());
if(!this.validateObj["showDefault"]){
this.field.value = new Date().format(sFormat || "yyyy-mm-dd HH:MM");
this.field.select();
this.field.focus();
}
return false;
}
var matchs = $F(this.field).match(this.dateRegExp);
if(!matchs){
this.warning = ((ValidatorLang.TEXT7 || "没有匹配日期格式:") + this.validateObj[$ValidatorConfigs.DATE_FORMAT].toLowerCase());
this.field.value = new Date().format(sFormat || "yyyy-mm-dd HH:MM");
this.field.select();
this.field.focus();
return false;
}
var year = parseInt(matchs[1], 10);
var month = parseInt(matchs[3], 10);
var day = parseInt(matchs[5], 10);
var hour = parseInt(matchs[7], 10);
var minute = parseInt(matchs[9], 10);
var second = parseInt(matchs[12], 10);
if(matchs[1].length != this.formatMatchs[1].length
|| matchs[2] != this.formatMatchs[2]){
this.warning = ((ValidatorLang.TEXT7 || "没有匹配日期格式:") + this.validateObj[$ValidatorConfigs.DATE_FORMAT].toLowerCase());
this.field.value = new Date().format(sFormat || "yyyy-mm-dd HH:MM");
this.field.select();
this.field.focus();
return false;
}
if(month < 1 || month > 12){
this.warning = ValidatorLang.TEXT14 || "月份应该为1到12的整数";
return false;
}
if (day < 1 || day > 31){
this.warning = (ValidatorLang.TEXT15 || "每个月的天数应该为1到31的整数");
return false;
}
if ((month==4 || month==6 || month==9 || month==11) && day==31){
this.warning = ValidatorLang.TEXT16 || "该月不存在31号";
return false;
} 
if (month==2){
var isleap=(year % 4==0 && (year % 100 !=0 || year % 400==0));
if (day>29){
this.warning = ValidatorLang.TEXT17 || "2月最多有29天";
return false;
} 
if ((day==29) && (!isleap)){
this.warning = ValidatorLang.TEXT18 || "闰年2月才有29天";
return false;
}
}
if(hour && hour<0 || hour>23){
this.warning = ValidatorLang.TEXT19 || "小时应该是0到23的整数";
return false;
} 
if(minute && minute<0 || minute>59){
this.warning = ValidatorLang.TEXT20 || "分应该是0到59的整数";
return false;
} 
if(second && second<0 || second>59){
this.warning = ValidatorLang.TEXT21 || "秒应该是0到59的整数";
return false;
} 
return true;
},
checkValidationSyntax : function(){
var timeFormat = this.validateObj[$ValidatorConfigs.DATE_FORMAT]
= this.validateObj[$ValidatorConfigs.DATE_FORMAT] || $ValidatorConfigs.DATE_FORMAT_DEFAULT;
this.formatMatchs = timeFormat.match(this.formatRegExp);
if(!this.formatMatchs){
alert((ValidatorLang.TEXT22 || "日期格式错误：") + timeFormat.toLowerCase());
return false;
}
return true;
}
});

var Custom_radio_Validator = Class.create();
Object.extend(Custom_radio_Validator.prototype, AbstractValidator.prototype);
Object.extend(Custom_radio_Validator.prototype, {
method : function(){
var radioArray = this.field.name ? document.getElementsByName(this.field.name) : [this.field];
for (var i = 0; i < radioArray.length; i++){
if(radioArray[i].checked)
return true;
}
return false;
}
});
var Custom_select_Validator = Class.create();
Object.extend(Custom_select_Validator.prototype, AbstractValidator.prototype);
Object.extend(Custom_select_Validator.prototype, {
method : function(){
var excludeValue = this.validateObj[$ValidatorConfigs.EXCLUDE];
if(excludeValue == undefined){
excludeValue = this.field.options[0] ? this.field.options[0].value : "";
}
var selectValue = $F(this.field);
if(excludeValue == selectValue){
this.warning += this.getReplaceInfoContent("SELECT", "WARNING_INFO");
return false;
}
return true;
},
getMessage : function(){
if(this.validateObj[$ValidatorConfigs.MESSAGE])return this.validateObj[$ValidatorConfigs.MESSAGE];
return this.getReplaceInfoContent("SELECT", "MESSAGE_INFO");
}
});
var Custom_time_Validator = Class.create();
Object.extend(Custom_time_Validator.prototype, AbstractValidator.prototype);
Object.extend(Custom_time_Validator.prototype, {
_format : [/^(\d\d):(\d\d)(:(\d\d))?$/, /^(\d{1,2}):(\d{1,2})(:(\d{1,2}))?$/],
initialize : function(_field) {
AbstractValidator.prototype.initialize.call(this, _field);
if(!this.checkValidationSyntax()) return false;
},
method : function(){
if($F(this.field).match(this._format[this._type])){
if(parseInt(RegExp.$1) > 23){
this.warning += this.getReplaceInfoContent("HOUR", "WARNING_INFO");
return false;
}
if(parseInt(RegExp.$2) > 59){
this.warning += this.getReplaceInfoContent("MINUTE", "WARNING_INFO");
return false;
}
if(RegExp.$4 && parseInt(RegExp.$4) > 59){
this.warning += this.getReplaceInfoContent("SECOND", "WARNING_INFO");
return false;
}
return true;
}else{
this.warning += this.getReplaceInfoContent("TIME", "WARNING_INFO");
return false;
}
},
getMessage : function(){
if(this.validateObj[$ValidatorConfigs.MESSAGE])return this.validateObj[$ValidatorConfigs.MESSAGE];
if(this._type == 0){
return this.getReplaceInfoContent("TIME_ONE", "MESSAGE_INFO");
}else if(this._type == 1){
return this.getReplaceInfoContent("TIME_TWO", "MESSAGE_INFO");
}
},
checkValidationSyntax : function(){
var timeFormat = this.validateObj[$ValidatorConfigs.TIME_FORMAT];
if(timeFormat != undefined){
timeFormat = timeFormat.trim();
if(timeFormat.replace(/h|m|s|:/ig,'') != ""){
alert("Custom_time_Validator.prototype:checkValidationSyntax:" + ValidatorLang.TEXT1 || '时间的time_format格式不对');
return false;
}
var timeArray = timeFormat.split(":");
if(timeArray[0].length == 2){
this._type = 0;
}else{
this._type = 1;
}
}else{
if($ValidatorConfigs.TIME_FORMAT_DEFAULT == 'hh:mm:ss'){
this._type = 0;
}else{
this._type = 1;
}
}
return true;
}
});
var Custom_combin_Validator = Class.create();
Object.extend(Custom_combin_Validator.prototype, AbstractValidator.prototype);
Object.extend(Custom_combin_Validator.prototype, {
initialize : function(_field) {
AbstractValidator.prototype.initialize.call(this, _field);
if(!this.checkValidationSyntax()) return false;
},
method : function(){
var innerValidationIns = ValidationFactory._makeValidator(this._subtype, this.field);
return true;
},
getMessage : function(){
if(this.validateObj[$ValidatorConfigs.MESSAGE])return this.validateObj[$ValidatorConfigs.MESSAGE];
return "";
},
checkValidationSyntax : function(){
this._field = this.validateObj[$ValidatorConfigs.FIELD];
this._subtype = this.validateObj[$ValidatorConfigs.SUBTYPE];
if(this._field == undefined){
alert("Custom_combin_Validator.prototype:checkValidationSyntax:" + ValidatorLang.TEXT2 || '没有指定field域');
return false;
}
if(this._subtype == undefined){
alert("Custom_combin_Validator.prototype:checkValidationSyntax:" + ValidatorLang.TEXT3 || '没有指定sub_type域');
return false;
}
this._relation = this.validateObj[$ValidatorConfigs.RELATION];
if(this._relation == undefined){
this._relation = "=";
}else{
this._relation = this._relation.trim();
}
if(this._relation.replace(/>|=|</g, "") != ''){
alert("Custom_combin_Validator.prototype:checkValidationSyntax:relation" + ValidatorLang.TEXT4 || '域指定不合法');
return false;
}
}
});
var Custom_link_Validator = Class.create();
Object.extend(Custom_link_Validator.prototype, AbstractValidator.prototype);
Object.extend(Custom_link_Validator.prototype, {
format:/^([^.]+\.){1,}[^.]+$/,
getWarning:function (){
return this.getReplaceInfoContent("LINK", "WARNING_INFO");
},
method : function(){
var StringVal = new StringValidator(this.field);
var result = StringVal.execute();
this.warning = StringVal.warning;
return result;
}, 
getMessage:function (){
if(this.validateObj[$ValidatorConfigs.MESSAGE])return this.validateObj[$ValidatorConfigs.MESSAGE];
return this.getReplaceInfoContent("LINK", "MESSAGE_INFO");
}
});
var Custom_common_char2_Validator = Class.create();
Object.extend(Custom_common_char2_Validator.prototype, AbstractValidator.prototype);
Object.extend(Custom_common_char2_Validator.prototype, {
method : function(){
if(!$F(this.field).match(/^[A-Za-z]\w*$/)){
this.warning += this.getReplaceInfoContent("COMMON_CHAR2", "WARNING_INFO");
return false;
}
var comCharVal = new CommonCharValidator(this.field);
var result = comCharVal.execute();
this.warning = comCharVal.warning;
return result;
},
getMessage:function (){
if(this.validateObj[$ValidatorConfigs.MESSAGE])return this.validateObj[$ValidatorConfigs.MESSAGE];
return this.getReplaceInfoContent("COMMON_CHAR2", "MESSAGE_INFO");
}
});
var Custom_string2_Validator = Class.create();
Object.extend(Custom_string2_Validator.prototype, AbstractValidator.prototype);
Object.extend(Custom_string2_Validator.prototype, {
method : function(){
if($F(this.field).match(/^[^A-Za-z\u4E00-\u9FA5]/)){
this.warning += this.getReplaceInfoContent("STRING2", "WARNING_INFO");
return false;
}
var stringVal = new StringValidator(this.field);
var result = stringVal.execute();
this.warning = stringVal.warning;
return result;
},
getMessage:function (){
if(this.validateObj[$ValidatorConfigs.MESSAGE])return this.validateObj[$ValidatorConfigs.MESSAGE];
return this.getReplaceInfoContent("STRING2", "MESSAGE_INFO");
}
});
var Custom_filename_Validator = Class.create();
Object.extend(Custom_filename_Validator.prototype, AbstractValidator.prototype);
Object.extend(Custom_filename_Validator.prototype, {
method : function(){
if($F(this.field).match(/[\\\/\:\*\?\"\<\>\|]/)){
this.warning += ValidatorLang.TEXT5 || '不应包含\\/:*?"<>|等字符'; //TODO改为从配置中取
return false;
}
var StringVal = new StringValidator(this.field);
var result = StringVal.execute();
this.warning = StringVal.warning;
return result;
},
getMessage:function (){
return ValidatorLang.TEXT6 || '普通字符，\\/:*?"<>|等字符除外'; //TODO改为从配置中取
}
});
var Custom_uri_Validator = Class.create();
Object.extend(Custom_uri_Validator.prototype, AbstractValidator.prototype);
Object.extend(Custom_uri_Validator.prototype, {
format : /^(((\w+):\/\/)|(mailto:))([^/:]+)(:\d*)?([^# ]*)/i, 
getMessage : function(){
if(this.validateObj[$ValidatorConfigs.MESSAGE])return this.validateObj[$ValidatorConfigs.MESSAGE];
return this.getReplaceInfoContent("URL", "MESSAGE_INFO");
},
method : function(){
var StringVal = new StringValidator(this.field);
var result = StringVal.execute();
this.warning = StringVal.warning;
return result;
}, 
getWarning:function(){
return this.validateObj[$ValidatorConfigs.WARNING] || this.getReplaceInfoContent("URL", "WARNING_INFO");
}
});
var Custom_time_Validator = Class.create();
Object.extend(Custom_time_Validator.prototype, AbstractValidator.prototype);
Object.extend(Custom_time_Validator.prototype, {
initialize : function(_field) {
AbstractValidator.prototype.initialize.call(this, _field);
if(!this.checkValidationSyntax()) return false;
},
method : function(){
var sValue= $F(this.field);
var aInfo = sValue.split(":");
if(this.aTimeFormat.length != aInfo.length || !/^(\d+(:\d+)?)?$/.test(sValue)){
this.warning = (ValidatorLang.TEXT7 || "没有匹配日期格式:") + this.validateObj[$ValidatorConfigs.DATE_FORMAT].toLowerCase();
return false;
}
var hour = parseInt(aInfo[0], 10);
var minute = parseInt(aInfo[1], 10);
var second = parseInt(aInfo[2], 10);
if(hour && hour<0 || hour>23){
this.warning = ValidatorLang.TEXT8 || "小时应该是0到23的整数";
return false;
} 
if(minute && minute<0 || minute>59){
this.warning = ValidatorLang.TEXT9 || "分应该是0到59的整数";
return false;
} 
if(second && second<0 || second>59){
this.warning = ValidatorLang.TEXT10 || "秒应该是0到59的整数";
return false;
} 
return true;
},
checkValidationSyntax : function(){
var timeFormat = this.validateObj[$ValidatorConfigs.DATE_FORMAT]
= this.validateObj[$ValidatorConfigs.DATE_FORMAT] || $ValidatorConfigs.TIME_FORMAT_DEFAULT;
this.aTimeFormat = timeFormat.split(":");
return true;
}
});

Ext.ns('com.trs.validator', 'ValidatorLang');
var ValidationFactory = Class.create();
Ext.apply(ValidationFactory, {
getValidateObj : function(_eField){
_eField = $(_eField);
var myValidateObj = ValidationHelper.getRegisterValidations(_eField.name || _eField.id);
var sValidation = _eField.getAttribute("validation");
if(!sValidation){
return Ext.apply({}, myValidateObj);
}
var cSplit = ";";
if(sValidation.indexOf(cSplit) < 0){
cSplit = ",";
}
var oValidateObj = {};
var sRegExp = "([^\\s:]+)\\s*:\\s*(?:\\'(.*?)\\'|([^,\\']*?))\\s*(?:,|$)";
var regExp = new RegExp(sRegExp.replace(/,/g, cSplit), "g");
while(regExp.exec(sValidation)){
var $1 = RegExp.$1;
var $2 = RegExp.$2 || RegExp.$3;
oValidateObj[$1.trim()] = ($2 || "").trim();
}
return Ext.apply(oValidateObj, myValidateObj);
},
makeValidator : function(_field){
var eField = $(_field);
var validateObj = this.getValidateObj(eField);
AttributeHelper.setAttribute(eField, "_VALIDATEOBJ_", validateObj);
var validatorIns = null;
var sType = validateObj[$ValidatorConfigs.TYPE];
if(!sType){
validatorIns = new AbstractValidator(eField);
}else{
validatorIns = this._makeValidator(sType, eField);
}
AttributeHelper.setAttribute(eField, "_VALIDATORINS_", validatorIns);
return validatorIns;
},
_makeValidator : function(type, _field){
var validatorIns = null;
switch(type.toLowerCase()){
case "int":
case "float":
case "double":
case "number":
validatorIns = new NumberValidator(_field);
break;
case "date":
validatorIns = new DateValidator(_field);
break;
case "url":
validatorIns = new URLValidator(_field);
break;
case "string":
validatorIns = new StringValidator(_field);
break;
case "ip":
validatorIns = new IPV4Validator(_field);
break;
case "common_char":
validatorIns = new CommonCharValidator(_field);
break;
default:
eval("validatorIns = new Custom_" + type.toLowerCase() + "_Validator(_field)");
}
return validatorIns;
}
});
var ValidationHelper = Class.create();
ValidationHelper.refreshValid = function(_field){
var eField = $(_field);
var sValidation = eField.getAttribute("validation");
ValidationHelper.popElements(eField);
changeBorderStyle(eField);
if(sValidation){
ValidationFactory.makeValidator(_field);
ValidationHelper.pushElements(eField);
ValidationHelper.forceValid(eField);
}
};
ValidationHelper.doAlert = function(_sMsg, _func){
try{
Ext.Msg.alert(_sMsg, function(){
ValidationHelper.exec(_func);
});
}catch (ex){
alert(_sMsg);
ValidationHelper.exec(_func);
}
};
ValidationHelper.exec = function(_func){
_func = _func || Ext.emptyFn;
try{
_func();
}catch(error){
}
}
ValidationHelper.validFuns = {};
ValidationHelper.addValidListener = function(callBackFun, validationId){
validationId = validationId || '$NoValidationId';
if(!ValidationHelper.validFuns[validationId]){
ValidationHelper.validFuns[validationId] = [callBackFun];
return;
}
if(!ValidationHelper.validFuns[validationId].include(callBackFun)){
ValidationHelper.validFuns[validationId].push(callBackFun);
}
};
ValidationHelper.invalidFuns = {};
ValidationHelper.addInvalidListener = function(callBackFun, validationId){
validationId = validationId || '$NoValidationId';
if(!ValidationHelper.invalidFuns[validationId]){
ValidationHelper.invalidFuns[validationId] = [callBackFun];
return;
}
if(!ValidationHelper.invalidFuns[validationId].include(callBackFun)){
ValidationHelper.invalidFuns[validationId].push(callBackFun);
}
};
ValidationHelper.doSubmit = function(_field){
var sameValidationIdControls = ValidationHelper.getSameValidationIdControls(_field);
for (var i = 0; i < sameValidationIdControls.length; i++){
if(AttributeHelper.getAttribute(sameValidationIdControls[i], "isValid") == false){
ValidationHelper.execInvalidFuns(_field);
return false;
}
}
ValidationHelper.execValidFuns(_field);
return true;
};
ValidationHelper.doSubmitAll = function(){
var validateControlsSimpleClone = ValidationHelper.getCloneControls(ValidationHelper.validateControls);
validateControlsSimpleClone.sort(ValidationHelper.sortFun);
while(validateControlsSimpleClone.length > 0){
var topObj = validateControlsSimpleClone.pop();
if(topObj == null) continue;
if(!ValidationHelper.doSubmit(topObj)){
while(validateControlsSimpleClone.length > 0){
if(ValidationHelper.getControlValidationId(topObj) == 
ValidationHelper.getControlValidationId(validateControlsSimpleClone.last()))
validateControlsSimpleClone.pop();
else break;
}
}
}
};
ValidationHelper.sortFun = function(_field1, _field2){
if(!_field1 || !_field2) return -1;
var id1 = ValidationHelper.getControlValidationId(_field1);
var id2 = ValidationHelper.getControlValidationId(_field2);
if(id1 < id2)return -1;
if(id1 > id2)return 1;
return 0;
};
ValidationHelper.getCloneControls = function(arrayObj){
var cloneArrayObj = [];
for (var i = 0; i < arrayObj.length; i++){
cloneArrayObj[i] = arrayObj[i];
}
return cloneArrayObj;
};
ValidationHelper.execInvalidFuns = function(_field){
var validationId = ValidationHelper.getControlValidationId(_field);
if(ValidationHelper.invalidFuns[validationId]){
ValidationHelper.invalidFuns[validationId].each(function(invalidFun){invalidFun();});
}
return false;
};
ValidationHelper.execValidFuns = function(_field){
var validationId = ValidationHelper.getControlValidationId(_field);
if(ValidationHelper.validFuns[validationId]){
ValidationHelper.validFuns[validationId].each(function(validFun){validFun();});
}
return false;
};
ValidationHelper.getSameValidationIdControls = function(_field){
var sameValidationIdControls = [];
var validationId = ValidationHelper.getControlValidationId(_field);
ValidationHelper.validateControls.each(function(element){
if(element == null) return;
var tempValidationId = ValidationHelper.getControlValidationId(element)
if(validationId == tempValidationId){
sameValidationIdControls.push(element);
}
});
return sameValidationIdControls;
};
ValidationHelper.getControlValidationId = function(_field){
_field = $(_field);
if(_field == null) return;
var validationId = _field.getAttribute("validationId");
if(!validationId && _field.form){
validationId = _field.form.getAttribute("validationId") || _field.form.id || _field.form.name;
}
return validationId ? validationId : '$NoValidationId';
};
ValidationHelper.getReplaceInfoContent = function(info, infoType, desc, _args){
var hintString = $ValidatorConfigs[infoType]["zh-cn"][info];
if(ValidatorLang.LOCALE == 'en' && !hintString.startsWith("{0}")){
desc = (desc||"").toLowerCase();
}
hintString = hintString.replace('{0}', desc);
if (_args == null) return hintString;
if (typeof _args != 'object'){
return hintString.replace('{1}', _args);
}
for (var i = 0; i < _args.length; i++){
hintString = hintString.replace('{' + (i+1) + '}', _args[i]);
} 
return hintString;
};
ValidationHelper.initIsValid = function(element){
var isValid = AttributeHelper.getAttribute(element, "_VALIDATORINS_").execute();
AttributeHelper.setAttribute(element, "isValid", isValid);
if(isValid) {
ValidationHelper.addBoardToDomTree(element, "MESSAGE");
}else{
ValidationHelper.addBoardToDomTree(element, "WARNING");
}
};
ValidationHelper.hasRequired = function(validateObj){
var required = validateObj[$ValidatorConfigs.REQUIRED];
if($ValidatorConfigs.getDraftMode()){
return false;
}
return !(required=='0' || required=='false' || (required==null&&required!=''));
};
ValidationHelper.makeRequiredHint = function(_field){
try{
var validateObj = AttributeHelper.getAttribute(_field, "_VALIDATEOBJ_");
if(validateObj[$ValidatorConfigs.REQUIRED] != undefined){
if(validateObj[$ValidatorConfigs.NO_REQUIRE_HINT]){
return;
}
if(!ValidationHelper.hasRequired(validateObj)){
return;
}
var index = AttributeHelper.getAttribute(_field, "_VALID_INDEX_");
var requireContainer = validateObj[$ValidatorConfigs.REQUIRE_CONTAINER];
if(requireContainer){
var sHTML = "<span class='" + $ValidatorConfigs.REQUIREDCLASS + "' id='" + $ValidatorConfigs.PREFIX_HINT_SPAN_ID + index + "'>*</span>";
new Insertion.Bottom($(requireContainer), sHTML);
return;
}
if(_field.style.position != 'absolute'){
afterObj = "<span class='" + $ValidatorConfigs.REQUIREDCLASS + "' id='" + $ValidatorConfigs.PREFIX_HINT_SPAN_ID + index + "'>*</span>";
new Insertion.After(_field, afterObj);
}else{
var requiredObj = document.createElement("span");
requiredObj.id = $ValidatorConfigs.PREFIX_HINT_SPAN_ID + index;
requiredObj.innerHTML = "*";
requiredObj.className = $ValidatorConfigs.REQUIREDCLASS;
var offsets = Position.cumulativeOffset(_field);
requiredObj.style.top = offsets[1] + "px";
requiredObj.style.left = (offsets[0] + _field.offsetWidth + 1) + 'px';
requiredObj.style.position = "absolute";
document.body.appendChild(requiredObj);
}
}
}catch(error){
alert("ValidationHelper.makeRequiredHint:" + error.message);
}
};
ValidationHelper.makeInfoBoard = function(_field, _option){
var sContent = _option.content;
sContent = 
'<span id="sp_' + _option.id.trim() + '">' + sContent + '</span>';
var oInsertionNode = document.createElement('span');
oInsertionNode.style.textAlgin = 'justify';
oInsertionNode.id = _option.id;
oInsertionNode.className = _option.className;
oInsertionNode.innerHTML = sContent;
oInsertionNode.style.position = 'absolute';
oInsertionNode.style.display = 'none';
oInsertionNode.style.opacity = '0.85';
oInsertionNode.style.filter = 'alpha(opacity=85)';
return oInsertionNode;
};
ValidationHelper.getOption = function(element, _infoType, _eventType){
var validatorIns = AttributeHelper.getAttribute(element, "_VALIDATORINS_");
var index = AttributeHelper.getAttribute(element, "_VALID_INDEX_");
return {
id: $ValidatorConfigs["PREFIX_" + _infoType + "_SPAN_ID"] + _eventType + "_" + index, 
content: _infoType == "MESSAGE" ? validatorIns.getMessage() : validatorIns.warning, 
logoSrc: $ValidatorConfigs[_infoType + "_LOG_PATH"], 
className: $ValidatorConfigs[_infoType + "_CLASSNAME_" + _eventType]
};
};
ValidationHelper.addBoardToDomTree = function(element, infoType){
var validateObj = AttributeHelper.getAttribute(element, "_VALIDATEOBJ_");
var showControlId = validateObj[$ValidatorConfigs.SHOWID];
if(!showControlId && element.form){
showControlId = element.form.getAttribute($ValidatorConfigs.SHOWID);
}
var option = ValidationHelper.getOption(element, infoType, "KEY");
boardObj = ValidationHelper.makeInfoBoard(element, option);
if(showControlId){
if(typeof(showControlId) == 'function'){
showControlId = showControlId();
}else if(typeof(showControlId) == 'string'){
showControlId = $(showControlId);
}
showControlId.innerHTML = '';
showControlId.appendChild(boardObj);
boardObj.style.position = 'static';
var noClass = validateObj[$ValidatorConfigs.NO_CLASS];
if(noClass == undefined && element.form){
noClass = element.form.getAttribute($ValidatorConfigs.NO_CLASS);
}
if(noClass != undefined){
boardObj.className = '';
}
}else{
if(element.style.position=='absolute'){
var offsets = Position.cumulativeOffset(element);
boardObj.style.top = offsets[1] + "px";
boardObj.style.left = (offsets[0] + element.offsetWidth + 5) + 'px';
document.body.appendChild(boardObj);
}else{
var afterElement = element;
if(ValidationHelper.hasRequired(validateObj))
afterElement = element.nextSibling;
new Insertion.After(afterElement, boardObj.outerHTML);
afterElement.nextSibling.style.position = 'static';
return afterElement.nextSibling;
}
}
return boardObj;
};
ValidationHelper.showAllTime = function(element){
var validatorIns = AttributeHelper.getAttribute(element, "_VALIDATORINS_");
var boardObjMessage = ValidationHelper.addBoardToDomTree(element, "MESSAGE");
Element.show(boardObjMessage);
};
ValidationHelper.keyDownEvent = function(element, event){
if(event.keyCode == Event.KEY_TAB || event.keyCode == Event.KEY_RETURN){
if(element.releaseCapture){
element.releaseCapture();
}
}else{
if(element.setCapture){
element.setCapture();
}
}
};
ValidationHelper.keyUpEvent = function(element){
if(element.releaseCapture){
element.releaseCapture();
}
var validatorIns = AttributeHelper.getAttribute(element, "_VALIDATORINS_");
var index = AttributeHelper.getAttribute(element, "_VALID_INDEX_");
var boardObjWarning = $($ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "KEY_" + index);
var boardObjMessage = $($ValidatorConfigs.PREFIX_MESSAGE_SPAN_ID + "KEY_" + index);
var isValid = validatorIns.execute();
AttributeHelper.setAttribute(element, "isValid", isValid);
if(isValid) {
if(!boardObjMessage){
boardObjMessage = ValidationHelper.addBoardToDomTree(element, "MESSAGE");
}
if($ValidatorConfigs.getShowAllMode() || $ValidatorConfigs.getFocusMode()){
if(boardObjWarning) Element.hide(boardObjWarning);
Element.show(boardObjMessage);
}
changeBorderStyle(element);
return ValidationHelper.doSubmit(element);
ValidationHelper.doSubmit(element);
}else{
if(!boardObjWarning){
boardObjWarning = ValidationHelper.addBoardToDomTree(element, "WARNING");
}
$('sp_' + $ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "KEY_" + index).innerHTML = validatorIns.warning;
if($ValidatorConfigs.getShowAllMode() || $ValidatorConfigs.getFocusMode()){
if(boardObjMessage) Element.hide(boardObjMessage);
Element.show(boardObjWarning);
}
changeBorderStyle(element, $ValidatorConfigs["WARNING_BORDER"]);
return ValidationHelper.execInvalidFuns(element);
ValidationHelper.execInvalidFuns(element);
}
};
ValidationHelper.changeEvent = function(element){
element = $(element);
var validatorIns = AttributeHelper.getAttribute(element, "_VALIDATORINS_");
var isValid = validatorIns.execute();
var index = AttributeHelper.getAttribute(element, "_VALID_INDEX_");
var boardObjWarning = $($ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "KEY_" + index);
var boardObjMessage = $($ValidatorConfigs.PREFIX_MESSAGE_SPAN_ID + "KEY_" + index);
if(isValid){
var validateObj = AttributeHelper.getAttribute(element, "_VALIDATEOBJ_");
if(validateObj[$ValidatorConfigs.RPC]){
ValidationHelper.currRPC = new Object();
ValidationHelper.currRPC.element = element;
eval("(" + validateObj[$ValidatorConfigs.RPC] + ")();");
}else{
changeBorderStyle(element);
if(!boardObjMessage){
boardObjMessage = ValidationHelper.addBoardToDomTree(element, "MESSAGE");
}
if($ValidatorConfigs.getShowAllMode() || $ValidatorConfigs.getFocusMode()){
if(boardObjWarning) Element.hide(boardObjWarning);
Element.show(boardObjMessage);
}
AttributeHelper.setAttribute(element, 'isValid', true);
ValidationHelper.doSubmit(element);
}
}else{
changeBorderStyle(element, $ValidatorConfigs["WARNING_BORDER"]);
if(!boardObjWarning){
boardObjWarning = ValidationHelper.addBoardToDomTree(element, "WARNING");
}
$('sp_' + $ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "KEY_" + index).innerHTML = validatorIns.warning;
if($ValidatorConfigs.getShowAllMode() || $ValidatorConfigs.getFocusMode()){
if(boardObjMessage) Element.hide(boardObjMessage);
Element.show(boardObjWarning);
}
AttributeHelper.setAttribute(element, 'isValid', false);
ValidationHelper.execInvalidFuns(element);
}
};
ValidationHelper.successRPCCallBack = function(message){
changeBorderStyle(ValidationHelper.currRPC.element);
AttributeHelper.setAttribute(ValidationHelper.currRPC.element, "isValid", true);
var index = AttributeHelper.getAttribute(ValidationHelper.currRPC.element, "_VALID_INDEX_");
var validateObj = AttributeHelper.getAttribute(ValidationHelper.currRPC.element, "_VALIDATEOBJ_");
var boardObjMessage = $($ValidatorConfigs.PREFIX_MESSAGE_SPAN_ID + "KEY_" + index);
if(boardObjMessage && validateObj[$ValidatorConfigs["BLUR_SHOW"]] == undefined){
Element.hide(boardObjMessage);
}
var boardObjWarning = $($ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "KEY_" + index);
if(boardObjWarning){
Element.hide(boardObjWarning);
}
return true;
};
ValidationHelper.failureRPCCallBack = function(warning){
try{
changeBorderStyle(ValidationHelper.currRPC.element, $ValidatorConfigs["WARNING_BORDER"]);
var index = AttributeHelper.getAttribute(ValidationHelper.currRPC.element, "_VALID_INDEX_");
var boardObjWarning = $($ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "KEY_" + index);
var boardObjMessage = $($ValidatorConfigs.PREFIX_MESSAGE_SPAN_ID + "KEY_" + index);
AttributeHelper.setAttribute(ValidationHelper.currRPC.element, "isValid", false);
AttributeHelper.getAttribute(ValidationHelper.currRPC.element,"_VALIDATORINS_").warning = warning;
if(!boardObjWarning){
boardObjWarning = ValidationHelper.addBoardToDomTree(ValidationHelper.currRPC.element, "WARNING");
}
var index = AttributeHelper.getAttribute(ValidationHelper.currRPC.element, "_VALID_INDEX_");
$('sp_' + $ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "KEY_" + index).innerHTML = warning;
if($ValidatorConfigs.getShowAllMode() || $ValidatorConfigs.getFocusMode()){
if(boardObjMessage) Element.hide(boardObjMessage);
Element.show(boardObjWarning);
}
ValidationHelper.currRPC.element.focus();
return ValidationHelper.execInvalidFuns(ValidationHelper.currRPC.element);
}catch(error){
}
};
ValidationHelper.focusEvent = function(element){
var validatorIns = AttributeHelper.getAttribute(element, "_VALIDATORINS_");
var index = AttributeHelper.getAttribute(element, "_VALID_INDEX_");
var boardObjWarning = $($ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "KEY_" + index);
var boardObjMessage = $($ValidatorConfigs.PREFIX_MESSAGE_SPAN_ID + "KEY_" + index);
var warningBorderstyle = $ValidatorConfigs["WARNING_BORDER"].split(" ");
if(AttributeHelper.getAttribute(element, "isValid") == false && element.style.borderBottomColor == warningBorderstyle[0] 
&& element.style.borderBottomWidth == warningBorderstyle[1] && element.style.borderBottomStyle == warningBorderstyle[2]){
if(boardObjMessage) Element.hide(boardObjMessage);
if(!boardObjWarning){
boardObjWarning = ValidationHelper.addBoardToDomTree(element, "WARNING");
}
Element.show(boardObjWarning);
return;
}
if(boardObjWarning) Element.hide(boardObjWarning);
if(!boardObjMessage){
boardObjMessage = ValidationHelper.addBoardToDomTree(element, "MESSAGE");
}
Element.show(boardObjMessage);
};
ValidationHelper.blurEvent = function(element){
element = $(element);
if(element.releaseCapture){
element.releaseCapture();
}
var validatorIns =AttributeHelper.getAttribute( element, "_VALIDATORINS_");
var isValid = validatorIns.execute();
AttributeHelper.setAttribute(element, "isValid", isValid);
var validateObj = AttributeHelper.getAttribute(element, "_VALIDATEOBJ_");
var index = AttributeHelper.getAttribute(element, "_VALID_INDEX_");
var boardObjWarning = $($ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "KEY_" + index);
var boardObjMessage = $($ValidatorConfigs.PREFIX_MESSAGE_SPAN_ID + "KEY_" + index);
if(AttributeHelper.getAttribute(element, "isValid") == false){
if(boardObjMessage) Element.hide(boardObjMessage);
if(!boardObjWarning){
boardObjWarning = ValidationHelper.addBoardToDomTree(element, "WARNING");
}
$('sp_' + $ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "KEY_" + index).innerHTML = validatorIns.warning;
Element.show(boardObjWarning);
changeBorderStyle(element, $ValidatorConfigs["WARNING_BORDER"]);
}else{
if(boardObjWarning) Element.hide(boardObjWarning);
changeBorderStyle(element);
if(boardObjMessage && validateObj[$ValidatorConfigs["BLUR_SHOW"]] == undefined){
Element.hide(boardObjMessage);
} 
ValidationHelper.doSubmit(element);
if(validateObj[$ValidatorConfigs.RPC]){
ValidationHelper.currRPC = new Object();
ValidationHelper.currRPC.element = element;
eval("(" + validateObj[$ValidatorConfigs.RPC] + ")();");
}else{
}
}
};
ValidationHelper.mouseOverEvent = function(element, event){
var validatorIns = AttributeHelper.getAttribute(element, "_VALIDATORINS_");
var index = AttributeHelper.getAttribute(element, "_VALID_INDEX_");
var boardObjMessage = $($ValidatorConfigs.PREFIX_MESSAGE_SPAN_ID + "MOUSE_" + index);
var boardObjWarning = $($ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "MOUSE_" + index);
if(AttributeHelper.getAttribute(element, "isValid") == false){
if(!boardObjWarning){
var option = ValidationHelper.getOption(element, "WARNING", "MOUSE");
boardObjWarning = ValidationHelper.makeInfoBoard(element, option);
document.body.appendChild(boardObjWarning);
boardObjWarning.style.left = Event.pointerX(window.event || event);
boardObjWarning.style.top = Event.pointerY(window.event || event);
} 
if(boardObjMessage){
Element.hide(boardObjMessage);
}
Element.show(boardObjWarning);
}else{
if(!boardObjMessage){
var option = ValidationHelper.getOption(element, "MESSAGE", "MOUSE");
boardObjMessage = ValidationHelper.makeInfoBoard(element, option);
document.body.appendChild(boardObjMessage);
boardObjMessage.style.left = Event.pointerX(window.event || event);
boardObjMessage.style.top = Event.pointerY(window.event || event);
}
if(boardObjWarning){
Element.hide(boardObjWarning);
} 
Element.show(boardObjMessage);
}
};
ValidationHelper.mouseOutEvent = function(element){
var validatorIns = AttributeHelper.getAttribute(element, "_VALIDATORINS_");
var index = AttributeHelper.getAttribute(element, "_VALID_INDEX_");
var boardObjMessage = $($ValidatorConfigs.PREFIX_MESSAGE_SPAN_ID + "MOUSE_" + index);
var boardObjWarning = $($ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "MOUSE_" + index);
if(boardObjMessage){
Element.hide(boardObjMessage);
}
if(boardObjWarning){
Element.hide(boardObjWarning);
}
};
ValidatorHelper = ValidationHelper;
ValidationHelper.valid = function(){
var arrRetVal = [];
for(var i=0;i<arguments.length;i++){
var element = arguments[i];
element = $(element);
if(element.getAttribute("forceValid") != "true" && (element.disabled || element.style.display == 'none')) continue;
if(element.getAttribute("validation") == undefined) continue;
var validatorIns = ValidationFactory.makeValidator(element);
var isValid = validatorIns.execute();
AttributeHelper.setAttribute(element, "isValid", isValid);
if(isValid){
changeBorderStyle(element);
}else{
changeBorderStyle(element, $ValidatorConfigs["WARNING_BORDER"]);
}
var validInfo = new Object();
validInfo.getMessage = function(){
return this.getMessage() || '';
}.bind(validatorIns);
validInfo.getWarning = function(){
return this.warning || '';
}.bind(validatorIns);
validInfo.isValid = function(){
return this.isValid;
}.bind({isValid:isValid});
validInfo['id'] = element.id || element.name;
arrRetVal.push(validInfo);
}
return (arrRetVal.length==0)?null:(arrRetVal.length==1)?arrRetVal[0]:arrRetVal;
};
ValidatorHelper.validAndDisplay = function(){
var validInfo = ValidationHelper.valid.apply(ValidationHelper, arguments);
if(!Array.isArray(validInfo)){
validInfo = [validInfo];
}
var result = true;
var warning = '';
var firstInvalid = null;
validInfo.each(function(element){
if(!element.isValid()){
result = false;
warning += element.getWarning() + "<br>";
if(firstInvalid == null) {
firstInvalid = element;
}
}
});
if(!result){
ValidationHelper.doAlert(warning, function(){
if(firstInvalid != null) {
try{
$(firstInvalid['id']).select();
$(firstInvalid['id']).focus();
}catch(err){
}
}
});
}
return result;
};
ValidatorHelper.asynValid = function(element, options){
element = $(element);
AttributeHelper.setAttribute(element, "asynOptions", options);
var validatorIns = ValidationFactory.makeValidator(element);
var isValid = validatorIns.execute();
if(isValid != undefined){
AttributeHelper.setAttribute(element, "isValid", isValid);
changeBorderStyle(element, isValid ? null : $ValidatorConfigs["WARNING_BORDER"]);
(options[isValid ? "success" : "fail"] || Ext.emptyFn)(validatorIns.warning || "");
}
};
ValidatorHelper.execCallBack = function(element, warning){
var isValid = !warning;
AttributeHelper.setAttribute(element, "isValid", isValid);
changeBorderStyle(element, isValid ? null : $ValidatorConfigs["WARNING_BORDER"]);
var options = AttributeHelper.getAttribute(element, "asynOptions");
if(!options)return;
(options[isValid ? "success" : "fail"] || Ext.emptyFn)(warning || "");
};
ValidatorHelper.forceValid = function(element){
element = $(element);
if(!ValidationHelper.hasValid(element)) return;
var validatorIns = AttributeHelper.getAttribute(element,"_VALIDATORINS_");
if(validatorIns == undefined){
ValidationHelper.registerValidation(element);
}
ValidationHelper.changeEvent(element);
};
ValidationHelper.doValid = function(parentId, _fDoAfterValidate, isExcludeHide){
var parentControl = $(parentId);
var inputArray = $A(parentControl.getElementsByTagName("INPUT"));
var textAreaArray = $A(parentControl.getElementsByTagName("TEXTAREA"));
var selectArray = $A(parentControl.getElementsByTagName("SELECT"));
var warning = "";
var flag = false;
var allControls = inputArray.concat(textAreaArray, selectArray);
if(isExcludeHide){
var _allControls = [];
for (var i = 0; i < allControls.length; i++){
if(Element.visible(allControls[i])){
_allControls.push(allControls[i]);
}
}
allControls = _allControls;
}
var firstInValidControl = null;
for (var i = 0; i < allControls.length; i++){
var validInfo = ValidationHelper.valid(allControls[i]);
if(!validInfo)continue;
if(!validInfo.isValid()){
if(flag == false) firstInValidControl = allControls[i];
flag = true;
warning += validInfo.getWarning() + "<br>";
}
}
if(flag){
if(_fDoAfterValidate && typeof(_fDoAfterValidate) == 'function') {
_fDoAfterValidate(warning, firstInValidControl);
return;
}
ValidationHelper.doAlert(warning, function(){
try{
$dialog().hide();
}catch(err){};
firstInValidControl.focus();
if(firstInValidControl.select){
firstInValidControl.select();
}
return false;
});
}
return !flag;
};
ValidationHelper.validateControls = [];
ValidationHelper.initValidation = function(){
var inputArray = $A(document.getElementsByTagName("INPUT"));
var textAreaArray = $A(document.getElementsByTagName("TEXTAREA"));
var selectArray = $A(document.getElementsByTagName("SELECT"));
inputArray.concat(textAreaArray, selectArray).each(function(element){
if(element.disabled || element.style.display == 'none' || element.offsetWidth == 0)
return;
var sType = element.type;
if(!sType) {
return;
}
if(sType.toLowerCase() == "hidden")
return;
ValidationHelper.registerValidation(element);
});
ValidationHelper.doSubmitAll();
};
ValidationHelper._registeredValidations_ = {
};
ValidationHelper.getRegisterValidations = function(sId){
sId = sId.toUpperCase();
return ValidationHelper._registeredValidations_[sId];
};
ValidationHelper.setRegisterValidations = function(sId, oJson){
sId = sId.toUpperCase();
ValidationHelper._registeredValidations_[sId] = oJson;
};
ValidationHelper.bindValidations = function(_arr){
_arr = Ext.isArray(_arr) ? _arr : [_arr];
for (var i = 0; i < _arr.length; i++){
var item = _arr[i];
ValidationHelper.setRegisterValidations(item.renderTo, Ext.apply({}, item));
}
};
ValidationHelper.hasValid = function(element, sId){
element = $(element);
if(element.getAttribute("validation") != null){
return true;
}
return ValidationHelper.getRegisterValidations(sId || element.name || element.id) != null;
};
ValidationHelper.registerValidations = function(_arr){
_arr = Ext.isArray(_arr) ? _arr : [_arr];
for (var i = 0; i < _arr.length; i++){
var item = _arr[i];
var element = $(item.renderTo);
if(element==null)continue;
ValidationHelper.setRegisterValidations(element.name || element.id, Ext.apply({}, item));
if(element.getAttribute("validation") == window.undefined){
element.setAttribute("validation", "");
}
ValidationHelper.registerValidation(element);
}
};
ValidationHelper.registerValidation = function(element){
element = $(element);
if(!ValidationHelper.hasValid(element)) return;
if(ValidationHelper.validateControls.include(element)) return;
var index = ValidationHelper.validateControls.length;
AttributeHelper.setAttribute(element, "_VALID_INDEX_", index);
ValidationHelper.validateControls.push(element);
var validatorIns = ValidationFactory.makeValidator(element);
if($ValidatorConfigs.getRequiredHint()){
ValidationHelper.makeRequiredHint(element);
}
ValidationHelper.initIsValid(element);
var bindMethod = {};
AttributeHelper.setAttribute(element, "bindMethod", bindMethod);
bindMethod.keyUpEvent = ValidationHelper.keyUpEvent.bind(ValidationHelper, element);
bindMethod.keyDownEvent = ValidationHelper.keyDownEvent.bind(ValidationHelper, element);
Event.observe(element, 'keydown', bindMethod.keyDownEvent, false);
Event.observe(element, 'keyup', bindMethod.keyUpEvent, false);
if($ValidatorConfigs.getShowAllMode()){
ValidationHelper.showAllTime(element);
}else if($ValidatorConfigs.getFocusMode()){
bindMethod.blurEvent = ValidationHelper.blurEvent.bind(ValidationHelper, element);
Event.observe(element, 'blur', bindMethod.blurEvent, false);
if(element.tagName.toUpperCase() == "SELECT"){
bindMethod.changeEvent = ValidationHelper.changeEvent.bind(ValidationHelper, element);
Event.observe(element, 'change', bindMethod.changeEvent, false);
}else{
bindMethod.focusEvent = ValidationHelper.focusEvent.bind(ValidationHelper, element);
Event.observe(element, 'focus', bindMethod.focusEvent, false);
}
}
if($ValidatorConfigs.getMouseMode()){
bindMethod.mouseOverEvent = ValidationHelper.mouseOverEvent.bind(ValidationHelper, element, event);
bindMethod.mouseOutEvent = ValidationHelper.mouseOutEvent.bind(ValidationHelper, element, event);
Event.observe(element, 'mouseover', bindMethod.mouseOverEvent, false);
Event.observe(element, 'mouseout', bindMethod.mouseOutEvent, false);
}
return true;
};
ValidationHelper.pushElements = function(){
var elementArray = $.apply($, arguments);
if(elementArray.constructor != Array) elementArray = [elementArray];
for (var i = 0; i < elementArray.length; i++){
element = elementArray[i];
var registerStatus = ValidationHelper.registerValidation(element);
if(registerStatus){
if(element.value != "" && AttributeHelper.getAttribute(element, "isValid")){
var validateObj = AttributeHelper.getAttribute(element, "_VALIDATEOBJ_");
if(validateObj[$ValidatorConfigs.RPC]){
ValidationHelper.currRPC = new Object();
ValidationHelper.currRPC.element = element;
eval("(" + validateObj[$ValidatorConfigs.RPC] + ")();");
}
}else{
ValidationHelper.doSubmit(element);
}
}
}
};
ValidationHelper.popElements = function(){
var elementArray = $.apply($, arguments);
if(elementArray.constructor != Array) elementArray = [elementArray];
var doms = ValidationHelper.validateControls;
for (var i = 0, length = doms.length; i < length; i++){
if(!elementArray.include(doms[i])){
continue;
}
ValidationHelper.unregisterElement(doms[i]);
if(AttributeHelper.getAttribute(doms[i], "isValid") == false){
AttributeHelper.removeAttribute(doms[i], "isValid");
ValidationHelper.doSubmit(doms[i]);
} 
doms[i] = null;
}
};
ValidationHelper.notify = function(){
var elementArray = $.apply($, arguments);
if(elementArray.constructor != Array) elementArray = [elementArray];
for (var i = 0; i < elementArray.length; i++){
var element = elementArray[i];
if(element.disabled || element.style.display == 'none'){
ValidationHelper.popElements(element);
}else{
ValidationHelper.pushElements(element);
}
}
};
ValidationHelper.unregisterElement = function(element){
if(!element) return;
var index = AttributeHelper.getAttribute(element, "_VALID_INDEX_");
var aIdInfo = [
$ValidatorConfigs.PREFIX_MESSAGE_SPAN_ID + "KEY_" + index,
$ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "KEY_" + index,
$ValidatorConfigs.PREFIX_MESSAGE_SPAN_ID + "MOUSE_" + index,
$ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "MOUSE_" + index,
$ValidatorConfigs.PREFIX_HINT_SPAN_ID + index
];
for (var i = 0; i < aIdInfo.length; i++){
var dom = $(aIdInfo[i]);
if(dom && dom.parentNode){
dom.parentNode.removeChild(dom);
}
}
var bindMethod = AttributeHelper.getAttribute(element, "bindMethod");
for(var tempEvent in bindMethod){
Event.stopObserving(element, tempEvent.replace("Event", '').toLowerCase(), bindMethod[tempEvent], false);
delete bindMethod[tempEvent];
}
var _VALIDATORINS_ = AttributeHelper.getAttribute(element, "_VALIDATORINS_");
delete _VALIDATORINS_["field"];
AttributeHelper.removeAttribute(element, "bindMethod");
AttributeHelper.removeAttribute(element, "_VALIDATORINS_");
AttributeHelper.removeAttribute(element, "_VALIDATEOBJ_");
AttributeHelper.removeAttribute(element, "asynOptions");
};
ValidationHelper.isValid = function(element){
return AttributeHelper.getAttribute(element, "isValid");
};
Event.observe(window, 'unload', function(){
var doms = ValidationHelper.validateControls;
var length = doms.length;
for (var i = 0; i < length; i++){
ValidationHelper.unregisterElement(doms[i]);
doms[i] = null;
}
ValidationHelper.validateControls = [];
});
function changeBorderStyle(element, newBorder){
if(AttributeHelper.getAttribute(element, "_oldBorderStyle_") == undefined){
var oldBorderWidth = Element.getStyle(element, "borderWidth") || '';
var oldBorderStyle = Element.getStyle(element, "borderStyle") || '';
var oldBorderColor = Element.getStyle(element, "borderColor") || '';
AttributeHelper.setAttribute(element, "_oldBorderWidth_", oldBorderWidth);
AttributeHelper.setAttribute(element, "_oldBorderStyle_", oldBorderStyle);
AttributeHelper.setAttribute(element, "_oldBorderColor_", oldBorderColor);
}
if(newBorder){
element.style.border = newBorder;
}else{
if(element.tagName!='TEXTAREA' && AttributeHelper.getAttribute(element, "_oldBorderStyle_") == 'none'){
element.style.borderWidth = '';
element.style.borderStyle = 'none';
element.style.borderColor = '';
}else{
var borderWidth = AttributeHelper.getAttribute(element, "_oldBorderWidth_");
if(borderWidth.startsWith("0")){
element.style.borderWidth = '';
}else{
element.style.borderWidth = borderWidth;
}
var borderColor = AttributeHelper.getAttribute(element, "_oldBorderColor_");
var borderStyle = AttributeHelper.getAttribute(element, "_oldBorderStyle_");
if(borderColor=="#000000" && borderStyle.toLowerCase()=="inset"){
element.style.borderColor = "#ffffff";
}else{
element.style.borderColor = AttributeHelper.getAttribute(element, "_oldBorderColor_");
}
element.style.borderStyle = borderStyle;
}
}
}

