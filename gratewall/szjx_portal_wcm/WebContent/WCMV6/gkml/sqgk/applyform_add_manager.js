function submitData(){
	$('frmData').submit();
}

function setMailContent(ApplyFormId){
	event.returnValue = false;
	new Ajax.Request("default_mailcontent_get.jsp", {
		method : 'post',
		parameters : "ApplyFormId=" + ApplyFormId,
		contentType : 'application/x-www-form-urlencoded', 
		onSuccess : function(transport, json){
			var xmlDoc = transport.responseXML;
			$("mailBody").value = xmlDoc.getElementsByTagName("mailBody")[0].text;
		},
		onFailure : function(){
			alert("获取邮件默认信息失败！");
		}
	});
}

function showContent(srcElement, targetElement){
	Element.hide(srcElement);
	Element.show(targetElement);
}