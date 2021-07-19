function getNextVerifyCode(event){
	$("verifycodeImg").src = "/wcm/verifycode.do?rand=" + new Date().getTime();
	var ev = event || window.event;
	if(ev.preventDefault) {
		ev.preventDefault();
	} else {
		ev.returnValue = false;
	}
}
function validate() {
	var frmAction = $('frmAction');
	var focusUserName = function(){
		frmAction.UserName.select();
//		frmAction.UserName.focus();
	}
	var focusPassWord = function(){
		frmAction.PassWord.focus();
	}
	var sUserName = frmAction.UserName.value;
	if(sUserName.byteLength() > 30){
		alert('用户名长度超过最大长度20！',focusUserName);
		return false;
	}
	if(sUserName.toLowerCase() == "system") {
		//Ext.Msg.$alert
		alert(String.format('{0}是系统保留帐号！', sUserName),
			focusUserName
		);
		return false;
	}
	if(sUserName.trim() == ''){
		alert('请输入用户名！', focusUserName);
		return false;
	}
	var sPassword = frmAction.PassWord.value;
	if(sPassword == ''){
		alert('请输入密码！', focusPassWord);
		return false;
	}
	//验证码不能为空
	if($("rand") && $F('rand').trim() == ""){
		alert("请输入验证码！");
		$('rand').focus();
		return false;
	}	
	setCookieUser(sUserName);
	if(/isdebug=1/i.test(location.href)){
		var sAction = frmAction.action;
		sAction +=  sAction.indexOf("?")==-1 ? "?isdebug=1":"&isdebug=1";
		frmAction.action = sAction;
	}
	return true;
}
//wenyh@20060419 添加简单的cookie操作,记住登录用户名
//cookie operation
var trsuser = "trswcmv6username";
//get cookie
function getCookieUser() {		
	var mycookie = document.cookie.split("; ");
	for(var i=0; i<mycookie.length; i++){
		var cookieUser = mycookie[i].split("=");
		if(cookieUser[0] == trsuser){
			return unescape(cookieUser[1]);
		}
	}
	return null;
}
//set cookie
function setCookieUser(userName, expires, path, domain, secure) {		
	var escapedValue = escape(userName);		
	var mycookie = [trsuser , "=" , escapedValue];		
	mycookie.push("; expires=");
	if(expires == null){
		expires = new Date();
		expires.setTime(expires.getTime() + (24 * 60 * 60 * 1000 * 30));
	}
	mycookie.push(expires.toGMTString());
	if(path != null){
		mycookie.push("; path=");
		mycookie.push(path);
	}
	if(domain != null){
		mycookie.push("; domain=");
		mycookie.push(domain);
	}
	if(secure != null){
		mycookie.push("; secure");			
	}
	document.cookie = mycookie.join("");		
}
function swapImg(_sId, _sSrc){
	document.getElementById(_sId).src = _sSrc;
}
Event.observe(window, 'load', function(){
	var frmAction = $('frmAction');
	if( window.location.href.indexOf("/app/") > 0){
		frmAction.action = "./login_dowith.jsp";
	}
	var sUserName = decodeURI(getParameter("UserName")) || getCookieUser();
	if(sUserName != null && sUserName != ''){
		frmAction.UserName.value = sUserName;
		frmAction.PassWord.focus();
	}else{
		frmAction.UserName.focus();
	}
	Event.observe('resetBtn', 'click', function(){
		frmAction.reset();
	});
	var func = function(_ev){
		swapImg(this.getAttribute('wcm_wrap', 2), this.getAttribute(_ev.type + '_wrap', 2));
	};
	//WCMVS-85 去掉加载的事件，否则报错 2013-03-22 lbm
	//Event.observe('linkRegNew', 'mouseout', func.bind($('linkRegNew')));
	//Event.observe('linkHelp', 'mouseout', func.bind($('linkHelp')));
	//Event.observe('linkRegNew', 'mouseover', func.bind($('linkRegNew')));
	//Event.observe('linkHelp', 'mouseover', func.bind($('linkHelp')));
	if($('linkCnLabel')){
		Event.observe('linkCnLabel', 'mouseout', func.bind($('linkCnLabel')));		
		Event.observe('linkCnLabel', 'mouseover', func.bind($('linkCnLabel')));
	}
	if($('linkEnLabel')){
		Event.observe('linkEnLabel', 'mouseout', func.bind($('linkEnLabel')));		
		Event.observe('linkEnLabel', 'mouseover', func.bind($('linkEnLabel')));
	}
});
