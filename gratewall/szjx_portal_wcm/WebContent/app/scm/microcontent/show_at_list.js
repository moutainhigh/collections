function changeAtType(typeId,url){
	if(typeId=="#showComment"){
		$("#showMicro").removeClass("notSelected");
		$("#showMicro").addClass("currentSelected");
		$("#sentComment").removeClass("currentSelected");
		$("#sentComment").addClass("notSelected");
		window.open(url,"_self");
	}else{
		$("#showMicro").removeClass("currentSelected");
		$("#showMicro").addClass("notSelected");
		$("#sentComment").removeClass("notSelected");
		$("#sentComment").addClass("currentSelected");
		window.open(url,"_self");
	}
}