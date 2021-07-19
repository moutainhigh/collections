$(function(){
	if($("#imgContainer")!=null){
		if($("#imgContainer").css("display")!="none"){
			$(".deletePicture").css("display","inline");
		}else{
			$(".deletePicture").css("display","none");
		}
	}
	$(window.document).live('click', function(e){
		//$(window.document).click(function(e){
		var e=e?e:window.event;
		var tar = e.srcElement||e.target;
		var yy =$(tar).offset().top;
		var xx =$(tar).offset().left;
		if($(tar).attr("id")=="showFace"){
			showFace();
			if($("#imgContainer").css("display")!="none"){
				$("#face_container").css("top",yy+22);
				$("#face_container").css("left",xx-8);
				$(".arrow-tips").css("top",-7);
				$(".arrow-tips-border").css("top",-6);
				$(".arrow-tips").css("left",25);
				$(".arrow-tips-border").css("left",25);
				$(".deletePicture").css("display","inline");
			}else{
				$("#face_container").css("top",yy-88);
				$("#face_container").css("left",xx+54);
				$(".arrow-tips").css("top",89);
				$(".arrow-tips-border").css("top",89);
				$(".arrow-tips").css("left",-7);
				$(".arrow-tips-border").css("left",-6);
				$(".deletePicture").css("display","none");
			}
		}
		if( $(tar).attr("id")=="faceText"){
			showFace();
			if($("#imgContainer").css("display")!="none"){
				$("#face_container").css("top",yy+22);
				$("#face_container").css("left",xx-30);
				$(".arrow-tips").css("top",-7);
				$(".arrow-tips-border").css("top",-6);
				$(".arrow-tips").css("left",25);
				$(".arrow-tips-border").css("left",25);
				$(".deletePicture").css("display","inline");
			}else{
				$("#face_container").css("top",yy-88);
				$("#face_container").css("left",xx+32);
				$(".arrow-tips").css("top",89);
				$(".arrow-tips-border").css("top",89);
				$(".arrow-tips").css("left",-7);
				$(".arrow-tips-border").css("left",-6);
				$(".deletePicture").css("display","none");
			}
		}
		if($(tar).parents("#face_container").length==0 && $(tar).attr("id")!="showFace" && $(tar).attr("id")!="faceText"){
			$("#face_container").hide();
		}
	});
});
function showFace(){
	$("#face_container").show();
}
function setFace(num){
	var sinaFaces =   ["[呵呵]","[可爱]","[可怜]","[挖鼻屎]","[吃惊]","[害羞]","[挤眼]","[闭嘴]","[鄙视]","[爱你]","[泪]","[偷笑]","[亲亲]","[嘘]","[衰]","[委屈]","[吐]","[打哈欠]","[怒]","[疑问]","[拜拜]","[汗]","[困]","[失望]","[酷]","[鼓掌]"];
	insertAtCaret($("#myExpress")[0], sinaFaces[num]);
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