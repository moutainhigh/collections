function getNextVerifyCode(event){
	$("verifycodeImg").src = "/wcm/verifycode?rand=" + new Date().getTime();
	ev = event || window.event;
	if(ev.preventDefault) {
		ev.preventDefault();
	} else {
		ev.returnValue = false;
	}
}

function submitData(){
	// 验证注册码
	if($('license').value==""){
		alert(wcm.LANG.license_edit_1000 || "注册码不能为空");
		$('license').focus();
		return;
	}
	// 验证码不能为空
	if($F('rand').trim() == ""){
		alert(wcm.LANG.license_edit_2000 || "请输入验证码！");
		$('rand').focus();
		return;
	}
	// 输入的验证码不正确
	new Ajax.Request("check_verifycode.jsp", {
		method : 'get',
		parameters : "RAND=" + $F($('rand')),
		onComplete : function(transport){
			var result = transport.responseText.trim();
			if(result == 'false'){
				alert(wcm.LANG.license_edit_3000 || "输入的验证码错误！");
				$('rand').focus();
			}else{
				//var elements = Form.getElements("frmData");
				//for (var i = 0; i < elements.length; i++){
				//	if(elements[i].name){
				//		elements[i].name = elements[i].name.toUpperCase();
				//	}
				//}
				var frmData = $('frmData');
				var oTRSAction = new CTRSAction("license_edit_dowith.jsp");
				oTRSAction.setParameter("ORIGNLICENSE", frmData.orignlicense.value);
				oTRSAction.setParameter("LICENSE", frmData.license.value);
				var sResult = oTRSAction.doDialogAction(500, 300,'',(window.screen.availHeight - 560)/2+100,(window.screen.availWidth - 530)/2+100);
				alert(sResult[1]);
				if(sResult[0]=="false"){
					getNextVerifyCode();
					return;
				}
				//$('frmData').submit();
				var isGecko = navigator.userAgent.toLowerCase().indexOf("gecko") > -1;
				if(isGecko){
					window.opener=null;
					window.open('about:blank','_parent','');
					window.close();
				}else{
					window.close();
				}
			}
		}
	});
}