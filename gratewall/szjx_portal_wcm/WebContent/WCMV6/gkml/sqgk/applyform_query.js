function getNextVerifyCode(){
	$("verifycodeImg").src = "/wcm/verifycode?rand=" + new Date().getTime();
	event.returnValue = false;
}

function submitData(){
	var applyerTypeDesc = $('personType').checked ? "姓名" : "法人/其他组织";
	var aCheckArray = [['applyerName', applyerTypeDesc],['queryCode', "查询号"], ['rand', "验证码"]];
	for (var i = 0; i < aCheckArray.length; i++){
		if($F(aCheckArray[i][0]).trim() == ""){
			$alert("请输入" + aCheckArray[i][1] + "！", function(){
				$dialog().hide();
				setTimeout(function(){
					$(aCheckArray[i][0]).focus();
				}, 100);
			});
			return;
		}
	}
	$('frmData').submit();
}

function selectApplyerType(applyerType){
	Element.update('nameTips', applyerType == '1' ? "姓名：" : "法人/其他组织：");
}
