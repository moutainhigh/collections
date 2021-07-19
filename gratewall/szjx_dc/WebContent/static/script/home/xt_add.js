$(function(){
	
	$("#systemCode").children(".jazz-form-text-label").prepend("<font color='red'>*</font>");
	$("#integratedUrl").children(".jazz-form-text-label").prepend("<font color='red'>*</font>");
	
	$("#formpanel .jazz-panel-content").css({"overflow":"none","overflow-y":"auto","overflow-x":"hidden"});

	$("#close-img").click(function(){
		$("#zhezhao").css("display","none");
	})
	
	$("#btn3").button("option","disabled",true);
	
	if(type==1 && !!pkId){
		$("#systemCode").textfield("option","disabled",true);
		$("#createrId").textfield("option","disabled",true);
		$("#createrTime").datefield("option","disabled",true);
		$("#formpanel").formpanel("option","dataurlparams",{"pkId":pkId});
		$("#formpanel").formpanel("option","dataurl",rootPath+"/integration/findByIdIntegration.do?type=1&pkId="+pkId);
		$("#formpanel").formpanel("reload");
		$("#btn3").button("option","disabled",false);
	}else if(type==2 && !!pkId){
		$("#remarks").height("100%");
		$("#btn3").button("hide");
		$("#formpanel").formpanel("option","readonly",true);
		$("#formpanel").formpanel("option","dataurlparams",{"pkId":pkId});
		$("#formpanel").formpanel("option","dataurl",rootPath+"/integration/findByIdIntegration.do?type=2&pkId="+pkId);
		$("#formpanel").formpanel("reload");
		setTimeout(function(){
			$(".jazz-att-theme1-close").css("display","none");
			$(".jazz-att-list-p").css("border","none");
		},300);
	}
});

function _close(e, data){
	e.currentClass.deleteFile(data);
	//$("#textfield_name8").attachment("deleteFile", data);
}

function _preview(e, data){
	var previewurl = data.previewurl;
	var width=document.documentElement.clientWidth;
	var height=document.documentElement.clientHeight; 
	$("#zhezhao").css({"display":"block","width":width,"height":height}); 
	$("#zhezhao-img").attr("src",previewurl);
	$("#zhezhao-img").attr("height",height-160); 
}

function save(){
	var params = {
		url:rootPath+"/integration/saveIntegration.do", 
		components: ['formpanel'],
		callback : function(data,obj,res) {
			query();
			leave();
		}
	};
	$.DataAdapter.submit(params);
}

function leave(){
	window.top.$("#frame_maincontent").get(0).contentWindow.leave();
}

function query(){
	window.top.$("#frame_maincontent").get(0).contentWindow.query();
}

function initData(res){
	var username = res.getAttr("username");
	var date = res.getAttr("currentDate");
	$("#createrId").textfield("setValue",username);
	$("#createrId").textfield("option","disabled",true);
	$("#createrTime").datefield("setValue",date);
	$("#createrTime").datefield("option","disabled",true);
}

function changeCode(){
	var result = {}; 
	var code = $("#systemCode").textfield("getValue");
	var regexp = /^[a-zA-Z]+$/;
    if(regexp.test(code)){
    	result["state"]=true; 
    	code = code.toUpperCase();
    	$("#systemCode").textfield("setValue",code);
    }else{
    	result["state"]=false;               
        result["msg"]="不能为空,且必须英文字母";
    }
    return result;
}

function changeUrl(){
	var result = {}; 
	var url = $("#integratedUrl").textfield("getValue");
	var strRegex = "^((https|http|ftp|rtsp|mms)?://)"
		+ "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?"
		+ "(([0-9]{1,3}.){3}[0-9]{1,3}" 
		+ "|"
		+ "([0-9a-z_!~*'()-]+.)*"
		+ "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]."
		+ "[a-z]{2,6})"
		+ "(:[0-9]{1,4})?"
		+ "((/?)|"
		+ "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
	var regexp = new RegExp(strRegex); 
    if(regexp.test(url)){
    	result["state"]=true; 
    }else{
    	result["state"]=false;               
        result["msg"]="不能为空,且必须是有效的url地址";
    }
    return result;
}

function selectFirm(event, ui){
	var firmid = ui.newValue;
	$("#pkSmLikeman").comboxfield("option","disabled",false);
	$("#pkSmLikeman").comboxfield("option","dataurl", rootPath+"/firmList/getLinkman.do?pkSmFirm="+firmid);
	$("#pkSmLikeman").comboxfield("reload");
}

function _uploadsuccess(){
	jazz.info("上传成功！");
	$("#btn3").button("option","disabled",false);
}