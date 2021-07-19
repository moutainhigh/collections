function getNextVerifyCode(){
	$("verifycodeImg").src = "/wcm/verifycode?rand=" + new Date().getTime();
	event.returnValue = false;
}

function submitData(){
	var applyerType = getApplyerType();

	//valid the controls.
	if(!ValidationHelper.doValid('dataContainer' + applyerType, function(warning, firstInValidControl){
		$alert(warning);
	})){
		return;
	}
	if(!ValidationHelper.doValid('dataContainer3', function(warning, firstInValidControl){
		$alert(warning);
	})){
		return;
	}
	var eMail = $('email_' + applyerType).value.trim();
	var index = eMail.indexOf('@');
	if(index <=0 || index >= eMail.length-1){
		$alert("邮件地址不正确！", function(){
			$dialog().hide();
			setTimeout(function(){
				$('email_' + applyerType).focus();
			}, 100);
		});
		return;
	}
	if($F('rand').trim() == ""){
		$alert("请输入验证码！", function(){
			$dialog().hide();
			setTimeout(function(){
				$('rand').focus();
			}, 100);
		});
		return;
	}

	Element.remove('dataContainer' + (applyerType == 1 ? "2" : "1"));
	var elements = Form.getElements("frmData");
	for (var i = 0; i < elements.length; i++){
		if(elements[i].name){
			elements[i].name = elements[i].name.toUpperCase();
		}
	}
	$('frmData').submit();
}


function getApplyerType(){
	var aApplyerType = document.getElementsByName('applyerType');
	for (var i = 0; i < aApplyerType.length; i++){
		if(aApplyerType[i].checked){
			return aApplyerType[i].value;
		}
	}
}

function selectApplyerType(applyerType){
	var container = $("dataContainer" + applyerType);
	Element.show(container);
	var container = $("dataContainer" + (applyerType==1?2:1));
	Element.hide(container);
	return;
	var elements = Form.getElements(container);
	for (var i = 0; i < elements.length; i++){
		elements[i].readOnly = false;
		elements[i].disabled = false;
	}

	var container = $("dataContainer" + (applyerType==1?2:1));
	var elements = Form.getElements(container);
	for (var i = 0; i < elements.length; i++){
		elements[i].readOnly = true;
		elements[i].disabled = true;
	}
}

function clickInfoType(event){
	event = window.event || event;
	var srcElement = Event.element(event);

	//单击电子邮件
	if(srcElement.name == 'emailProviderType' || srcElement.name == 'emailGetType'){
		$('emailProviderType').checked = $('emailGetType').checked = srcElement.checked;
	}
}