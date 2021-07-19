Event.observe(window, 'load', function(){
	new wcm.TabPanel({
		id : 'tabPanel',
		activeTab : 'tab-body-1'
	}).show();
	changeServer();
	changeGateWay();
	changeSource();
	changeSite();
	if($("taskName")) $("taskName").focus();
	wcmXCom.get('tabPanel').on('tabchange', function(sCurrTab){
		if(sCurrTab == "tab-body-1" && $("taskName")){
			$("taskName").focus();
		}
	});
});

function changeServer(){
	var ele = $("server");
	if(ele.value == 0){
		$("TargetServer").value = "";
		$("TargetPort").value = "";
		$("ServerUser").value = "";
		$("ServerPassword").value = "";
		$("ServerTableName").value = "";
		Element.hide($("deleteServer"));
		Element.show($("addServer"));
	}else{
		ele = ele.options[ele.selectedIndex];
		$("TargetServer").value = ele.getAttribute("ip",2);
		$("TargetPort").value = ele.getAttribute("port",2);
		$("ServerUser").value = ele.getAttribute("user",2);
		$("ServerPassword").value = ele.getAttribute("password",2);
		$("ServerTableName").value = ele.getAttribute("tablename",2);
		Element.show($("deleteServer"));
		Element.hide($("addServer"));
	}	
	disable($("TargetServer"));	
	disable($("TargetPort"));
	disable($("ServerUser"));	
	disable($("ServerPassword"));
	disable($("ServerTableName"));
}

function disable(ele){
	ele.disabled = true;
}

function undisable(ele){
	ele.disabled = false;
}

function testServer(){
	if(!ValidationHelper.doValid('trsServerForm')){
		return false;
	}
	FloatPanel.disableCommand("save",true);
	if(!isValid(true))
		return false;
}

function isValid(bAlert){
	if(bAlert) ProcessBar.start(wcm.LANG.SYSMENU_62 || '连接Server服务器...');
	var checkValidObj = {
			Server:$("TargetServer").value,
			Port:$("TargetPort").value,
			User:$("ServerUser").value,
			Password:$("ServerPassword").value
		};

    var url = "../include/validate_TRSServer.jsp";
	var request = new Ajax.Request(url, {
		contentType:'application/x-www-form-urlencoded',
		asynchronous:true, 
		method:'post',
		parameters:$toQueryStr(checkValidObj),
		onSuccess : function(){
			var regExp = /\[CDATA\[(.+)\]\]/m;
			var match = regExp.exec(request.transport.responseText);
			if(match && match[1].trim().equals("error")){
				Ext.Msg.alert(wcm.LANG.SYSMENU_55 || "无法连上TRSServer，请检查配置信息并确保Server已开启！");
				return false;
			}else{
				if(bAlert)
					Ext.Msg.alert(wcm.LANG.SYSMENU_56 || "配置正确！");
			}
			return true;                
		},
		onFailure : function(){
			if(bAlert) ProcessBar.close();
			FloatPanel.disableCommand("save",false);
			Ext.Msg.alert(wcm.LANG.PUBLISHDISTRUBUTION_CONFIRM_8 || '与服务器交互时出现错误!');
			return false;
		},
		onComplete : function(){
			if(bAlert) ProcessBar.close();
			FloatPanel.disableCommand("save",false);
		}
	});  
}

function changeGateWay(){
	var ele = $("gateway");
	if(ele.value == 0){
		$("GateWayServer").value = "";
		$("GateWayPort").value = "";
		$("GateWayUser").value = "";
		$("GateWayPassword").value = "";
		Element.hide($("deleteGateWay"));
		Element.show($("addGateWay"));
	}else{
		ele = ele.options[ele.selectedIndex];
		$("GateWayServer").value = ele.getAttribute("ip",2);
		$("GateWayPort").value = ele.getAttribute("port",2);
		$("GateWayUser").value = ele.getAttribute("user",2);
		$("GateWayPassword").value = ele.getAttribute("password",2);
		Element.hide($("addGateWay"));
		Element.show($("deleteGateWay"));
	}
	disable($("GateWayServer"));
	disable($("GateWayPort"));
	disable($("GateWayUser"));
	disable($("GateWayPassword"));
}

function testGateWay(){
	if(!ValidationHelper.doValid('trsGateWayForm')){
		return false;
	}
	FloatPanel.disableCommand("save",true);
	if(!isValidGateWay(true))
		return false;	
}

function isValidGateWay(bAlert){
	if(bAlert) ProcessBar.start(wcm.LANG.SYSMENU_63 || '连接GateWay服务器...');
	var checkValidObj = {
			Server:$("GateWayServer").value,
			Port:$("GateWayPort").value,
			User:$("GateWayUser").value,
			Password:$("GateWayPassword").value
		};

    var url = "../include/validate_GateWay.jsp";
	var request = new Ajax.Request(url, {
		contentType:'application/x-www-form-urlencoded',
		asynchronous:true, 
		method:'post',
		parameters:$toQueryStr(checkValidObj),
		onSuccess : function(){
			var regExp = /\[CDATA\[(.+)\]\]/m;
			var match = regExp.exec(request.transport.responseText);
			if(match && match[1].trim().equals("error")){
				Ext.Msg.alert(wcm.LANG.SYSMENU_57 || "无法连上GateWay，请检查配置信息并确保GateWay已开启！");
				return false;
			}else{
				if(bAlert)
					Ext.Msg.alert(wcm.LANG.SYSMENU_56 || "配置正确！");
			}
			return true;                
		},
		onFailure : function(){
			if(bAlert) ProcessBar.close();
			FloatPanel.disableCommand("save",false);
			Ext.Msg.alert(wcm.LANG.PUBLISHDISTRUBUTION_CONFIRM_8 || '与服务器交互时出现错误!');
			return false;
		},
		onComplete : function(){
			if(bAlert) ProcessBar.close();
			FloatPanel.disableCommand("save",false);
		}
	});
}

function doTransfer(sName,tName){
	if($("document_base").style.display != "none"){
		moveTo(sName + "1",tName + "1");
	}else if($("channel_base").style.display != "none"){
		moveTo(sName + "2",tName + "2");
	}else{
		moveTo(sName + "3",tName + "3");
	}
}

function moveTo(sName,tName){
	var eltoSel = $(tName);
	var elsBeSel = $(sName);
	var count = 0;
	var length = elsBeSel.options.length;
	for(var i=0;i<length;i++){
		if(elsBeSel.options[i]!=null && elsBeSel.options[i].selected){
			var sText = elsBeSel.options[i].text;
			var sValue = elsBeSel.options[i].value;
			var oOption = document.createElement("OPTION");
			eltoSel.options.add(oOption);
			oOption.innerText = sText;
			oOption.value = sValue;
		}
	}
	var index = elsBeSel.selectedIndex;
	while(index >=0){
		elsBeSel.remove(index);
		index = elsBeSel.selectedIndex;
	}
}

function changeSite(){
	if($("selAllSites").checked){
		var elsBeSel = $("baseSites");
		var eltoSel = $("synSites");
		elsBeSel.disabled = true;
		eltoSel.disabled = true;
	}else{
		var elsBeSel = $("synSites");
		var eltoSel = $("baseSites");
		elsBeSel.disabled = false;
		eltoSel.disabled = false;
	}
}

function save(){
	if(!ValidationHelper.doValid('addEditForm')){
		return false;
	}
	if($("server").value == 0){
		Ext.Msg.alert(wcm.LANG.SYSMENU_60 || "请配置Server信息！");
		return false;
	}
	if($("gateway").value == 0){
		Ext.Msg.alert(wcm.LANG.SYSMENU_61 || "请配置GateWay信息！");
		return false;
	}
	$("serverId").value = $("server").value;
	$("gatewayId").value = $("gateway").value;

	//获取同步字段
	var fields = null;
	var realEle = $("syn1");
	if($("channel_base").style.display == "")
		realEle = $("syn2");
	else if($("chnldoc_base").style.display == ""){
		realEle = $("syn3");
	}
	var nFieldLength = realEle.options.length;
	if(nFieldLength > 0){
		fields = "";
		for(var k=0; k < nFieldLength ; k++){
			fields += (realEle.options[k].value + ",");
		}
		if(fields.length > 0) fields = fields.substring(0,fields.length -1);
	}
	if(fields == null){
		Ext.Msg.alert(wcm.LANG.SYSMENU_65 || "请选择同步字段！");
		return false;
	}
	$("fields").value = fields;
	//获取检索站点ID	
	var ids = null;
	if(!$("selAllSites").checked){
		var nLength = $("synSites").options.length;
		if(nLength > 0){
			ids = "";
			for(var k=0; k < nLength ; k++){
				ids += ($("synSites").options[k].value + ",");
			}
			if(ids.length > 0) ids = ids.substring(0,ids.length -1);
		}
	}else{
		$("ignoreSite").value = "true";
	}
	if(ids == null && !$("selAllSites").checked){
		Ext.Msg.alert(wcm.LANG.SYSMENU_66 || "请选择检索站点！");
		return false;
	}
	$("siteIds").value = ids;

	//检查Server和 GateWay信息
	ProcessBar.start(wcm.LANG.SYSMENU_64 || "保存检索任务...");
	new Ajax.Request(WCMConstants.WCM6_PATH + "search/task_test.jsp?serverid=" + $("server").value + "&gatewayid=" + $("gateway").value, {'onSuccess' : 
		function(_trans, _json){
				if(_trans.responseText.indexOf("server") > 0){
					ProcessBar.close();
					Ext.Msg.alert(wcm.LANG.SYSMENU_55 || "无法连上TRSServer，请检查配置信息并确保Server已开启！");
					return false;
				}
				if(_trans.responseText.indexOf("gateway") > 0){
					ProcessBar.close();
					Ext.Msg.alert(wcm.LANG.SYSMENU_57 || "无法连上GateWay，请检查配置信息并确保GateWay已开启！");
					return false;
				}
				BasicDataHelper.JspRequest(
					WCMConstants.WCM6_PATH + "search/search_config_dowith.jsp",
					"addEditForm",  true,
					function(transport, json){
						ProcessBar.close();
						Ext.Msg.alert("任务配置完成，请到GateWay后台继续下一步配置！",function(){
							window.top.location.reload();
							return true;
						})						
					},function(transport, json){
						ProcessBar.close();
						Ext.Msg.alert(wcm.LANG.PUBLISHDISTRUBUTION_CONFIRM_8 || '与服务器交互时出现错误!');
						return false;
				});
		},
		'onFailure'	: function(){
			ProcessBar.close();
			return false;
		}
	});
	return false;
}

function deleteServer(){
	var _serverId = $("server").value;
	if(_serverId == 0) return;
	new Ajax.Request(WCMConstants.WCM6_PATH + "search/server_delete.jsp?serverIds=" + _serverId, {'onSuccess' : 
		function(_trans, _json){
			for(var i=0; i < $("server").options.length; i++){
				if($("server").options[i].value == _serverId){
					$("server").options[i] = null;
					$("server").selectedIndex = 0;
					changeServer();
					break;
				}
			}
		}
	});
}

function addServer(){
	var _serverId = $("server").value;
	if(_serverId != 0) return;
	var sTitle = '<font style="font-size:12px;font-weight:normal;">' + (wcm.LANG.SYSMENU_58 || '新增Server') + '</font>';
	wcm.CrashBoarder.get('addServerCon').show({
		title : sTitle,
		src : "server_add.html",
		width: '400px',
		height: '230px',
		params : {},
		reloadable : true,
		maskable : true,
		callback : function(_args){
			var ele = document.createElement("option");
			ele.innerText = _args.name;
			ele.setAttribute("ip",_args.server);
			ele.setAttribute("port",_args.port);
			ele.setAttribute("user",_args.user);
			ele.setAttribute("password",_args.password);
			ele.setAttribute("tablename",_args.tablename);
			ele.setAttribute("value",_args.id);
			$("server").appendChild(ele);
			setTimeout(function(){
				$("server").options[$("server").options.length -1].selected = true;
				changeServer();
			},400);
			
		}
	});
}

function addGateWay(){
	var _gatewayId = $("gateway").value;
	if(_gatewayId != 0) return;
	var sTitle = '<font style="font-size:12px;font-weight:normal;">' + (wcm.LANG.SYSMENU_59 || '新增GateWay') + '</font>';
	wcm.CrashBoarder.get('addGateWayCon').show({
		title : sTitle,
		src : "gateway_add.html",
		width: '400px',
		height: '230px',
		params : {},
		reloadable : true,
		maskable : true,
		callback : function(_args){
			var ele = document.createElement("option");
			ele.innerText = _args.name;
			ele.setAttribute("ip",_args.server);
			ele.setAttribute("port",_args.port);
			ele.setAttribute("user",_args.user);
			ele.setAttribute("password",_args.password);
			ele.setAttribute("value",_args.id);
			$("gateway").appendChild(ele);
			setTimeout(function(){
				$("gateway").options[$("gateway").options.length -1].selected = true;
				changeGateWay();
			},400);
			
		}
	});
}

function deleteGateWay(){
	var _gatewayId = $("gateway").value;
	if(_gatewayId == 0) return;
	new Ajax.Request(WCMConstants.WCM6_PATH + "search/gateway_delete.jsp?gatewayIds=" + _gatewayId, {'onSuccess' : 
		function(_trans, _json){
			for(var i=0; i < $("gateway").options.length; i++){
				if($("gateway").options[i].value == _gatewayId){
					$("gateway").options[i] = null;
					$("gateway").selectedIndex = 0;
					changeGateWay();
					break;
				}
			}
		}
	});
}

function changeSource(){
	if($("taskSource").value == "WCMCHANNEL"){
		Element.hide("document_base");
		Element.hide("chnldoc_base");
		Element.show("channel_base");
		Element.hide("document_syn");
		Element.hide("chnldoc_syn");
		Element.show("channel_syn");
	}else if(($("taskSource").value == "WCMDOCUMENT")){
		Element.hide("channel_base");
		Element.hide("chnldoc_base");
		Element.show("document_base");
		Element.hide("channel_syn");
		Element.hide("chnldoc_syn");
		Element.show("document_syn");
	}else{
		Element.hide("channel_base");
		Element.hide("document_base");
		Element.show("chnldoc_base");
		Element.hide("channel_syn");
		Element.hide("document_syn");
		Element.show("chnldoc_syn");
	}
}