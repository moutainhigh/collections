$(function(){
	$('#forwardFace').click(function(e) {
		var yy =$(this).offset().top;
		var xx =$(this).offset().left;
		showFace();
		$("#face_container").css("top",yy-87);
		$("#face_container").css("left",xx+24);
	});
	$(document).click(function(e){
		var e=e?e:window.event;
		var tar = e.srcElement||e.target;
		var x = e.pageX;
		var y = e.pageY;
		//判断元素的是否存在id为face_forward的父类，或者是否为表情图片点击按钮，然后判断是否执行隐藏动作。
		if($("#face_container").has(tar).html()==null && $(tar).attr("id")!="forwardFace"){
			$("#face_container").hide();
		}
	});
});
function showFace(){
	$("#face_container").show();
}
function setFace(num){
	var sinaFaces =["[呵呵]","[可爱]","[可怜]","[挖鼻屎]","[吃惊]","[害羞]","[挤眼]","[闭嘴]","[鄙视]","[爱你]","[泪]","[偷笑]","[亲亲]","[嘘]","[衰]","[委屈]","[吐]","[打哈欠]","[怒]","[疑问]","[拜拜]","[汗]","[困]","[失望]","[酷]","[鼓掌]"];	
	var value = sinaFaces[num];
	insertAtCaret($("#myExpress")[0],value);
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