function leave(){
	window.top.$("#frame_maincontent").get(0).contentWindow.leave();
}

function initData(res){
	var pkDcChange = res.getAttr("pkDcChange");
	var dataSourceName = res.getAttr("dataSourceName");
	var busiObjectName = res.getAttr("busiObjectName");
	setTimeout(function(){
		$("#dataSourceName").textfield("setValue",dataSourceName);
		$("#busiObjectName").textfield("setValue",busiObjectName);
	},300);
	
	$("#formpanel").formpanel("option","readonly",true);
	$("#formpanel").formpanel("option","dataurlparams",{"pkDcChange":pkDcChange});
	$("#formpanel").formpanel("option","dataurl",rootPath+"/changeManager/findChangeById.do?pkDcChange="+pkDcChange);
	$("#formpanel").formpanel("reload");
}

