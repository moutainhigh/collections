
Event.observe(window, 'load', function(){
	FloatPanel.disableCommand("save",true);
});

//连接测试TRSServer的配置信息
function link(){
	if(!ValidationHelper.doValid('trsServerForm')){
		return false;
	}
	FloatPanel.disableCommand("save",true);
	if(!isValid())
		return false;
}
//保存TRSServer的配置信息
function save(){
	if(!ValidationHelper.doValid('trsServerForm')){
		return false;
	}
	var oHelper = new com.trs.web2frame.BasicDataHelper();
    var oPostData = {
		"host" : $("TargetServer").value,
		"port" : $("TargetPort").value, 
		"user" : $("LoginUser").value, 
		"pwd" : $("LoginPassword").value,
		"db"  :	$("TRSDatebase").value
	};
	try{
		oHelper.call("wcm61_viewDocument", 'saveTRSConfig', oPostData, true,
		function(_trans,_json){
			notifyFPCallback(_trans);
			FloatPanel.close();
		});
	}catch(err){
		Ext.Msg.$alert(err.message, function(){
		});
	}
	return false;
}

function isValid(){
	ProcessBar.start(wcm.LANG.PUBLISHDISTRUBUTION_VALID_44 || '连接服务器');
	var checkValidObj = {
			Server:$("TargetServer").value,
			Port:$("TargetPort").value,
			User:$("LoginUser").value,
			Password:$("LoginPassword").value
		};

    var url = "../include/validate_TRSServer.jsp";
	var request = new Ajax.Request(url, {
		contentType:'application/x-www-form-urlencoded',
		asynchronous:true, 
		method:'post',
		parameters:$toQueryStr(checkValidObj),
		onSuccess : function(){
			if(request.transport.responseText.trim() == ""){
				Ext.Msg.alert(wcm.LANG.RSSERVER_CONFIG_121 || "当前TRSServer上无任何数据源！");
				return false;
			}
			var regExp = /\[CDATA\[(.+)\]\]/m;
			var match = regExp.exec(request.transport.responseText);
			if(match[1].trim().equals("error")){
				Ext.Msg.alert(wcm.LANG.RSSERVER_CONFIG_131 || "无法连上TRSServer，请检查配置信息！");
				return false;
			}else{
				if(match[1].trim().equals("")){
					$("TRSDatebase").disabled = false;
					return true;
				}
				var sDBNames = match[1].split("-")[1];
				var sViewNames = match[1].split("-")[0];
				addOption(sDBNames,sViewNames);
				$("TRSDatebase").disabled = false;
			}
			return true;                
		},
		onFailure : function(){
			ProcessBar.close();
			FloatPanel.disableCommand("save",false);
			Ext.Msg.alert(wcm.LANG.PUBLISHDISTRUBUTION_CONFIRM_8 || '与服务器交互时出现错误!');
			return false;
		},
		onComplete : function(){
			ProcessBar.close();
			FloatPanel.disableCommand("save",false);
		}
	});  
}

function addOption(sDBNames,sViewNames){
	$("TRSDatebase").innerHTML = "";
	for(var i =0 ; i< sViewNames.split(",").length; i++){
		var element = document.createElement("option");
		element.innerHTML = sViewNames.split(",")[i];
		element.value = sViewNames.split(",")[i];
		element.setAttribute("DBName",sDBNames.split(",")[i]);
		$("TRSDatebase").appendChild(element);
	}
}