$(function(){
	if($('.leftFaceSpan') != null){
		$('.leftFaceSpan').click(function(e) {
			var yy =$(this).offset().top;
			var xx =$(this).offset().left;
			showFace();
			$("#face_container").css("top",yy-86);
			$("#face_container").css("left",xx+35);
			m_element = this;
		});
	}
	$(document).click(function(e){
		var e=e?e:window.event;
		var tar = e.srcElement||e.target;
		if($(tar).parents(".face").length ==0 && $(tar).attr("class")!="leftFaceSpan"&& $(tar).attr("class")!="leftFacePic" ){
			$("#face_container").hide();
		}
	});
});
function showFace(){
	$("#face_container").show();
}
function setFace(num){
	var sinaFaces = ["[呵呵]","[可爱]","[可怜]","[挖鼻屎]","[吃惊]","[害羞]","[挤眼]","[闭嘴]","[鄙视]","[爱你]","[泪]","[偷笑]","[亲亲]","[嘘]","[衰]","[委屈]","[吐]","[打哈欠]","[怒]","[疑问]","[拜拜]","[汗]","[困]","[失望]","[酷]","[鼓掌]"];
	insertAtCaret($(m_element).parent().parent().find("textarea:eq(0)")[0],sinaFaces[num]);
}
function insertAtCaret(obj,str){ 
	if (document.selection) {
		obj.focus(); 
		var sel = document.selection.createRange();
		sel.text = str;
	} else if (typeof obj.selectionStart === 'number' && typeof obj.selectionEnd === 'number') {
		var startPos = obj.selectionStart,
		endPos = obj.selectionEnd,
		cursorPos = startPos,
		tmpStr = obj.value;
		obj.value = tmpStr.substring(0, startPos) + str + tmpStr.substring(endPos, tmpStr.length);
		cursorPos += str.length;
		obj.selectionStart = obj.selectionEnd = cursorPos;
	} else {
		obj.value += str;
	}
}