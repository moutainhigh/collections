function edit(nObjectid){
	FloatPanel.open(WCMConstants.WCM6_PATH +
			'search/search_config.jsp?taskId=' + nObjectid, nObjectid == 0 ? (wcm.LANG.TRSSERVER_9 || '新建检索任务') : (wcm.LANG.TRSSERVER_10 || '修改检索任务')
	);
}

function getIds(){
	return TRSHTMLElement.getElementValueByName('TaskIds');
}

function deleteTasks(_taskIds){
	//参数校验
	if(_taskIds == null || _taskIds.length <= 0){
		Ext.Msg.alert(wcm.LANG.TRSSERVER_11 ||"请选择需要删除的检索任务!");
		return;
	}
	var sMsg = wcm.LANG.TRSSERVER_12 ||"您确定需要删除选定的检索任务吗?";
	Ext.Msg.confirm(sMsg, {
		ok : function(){
			new Ajax.Request(WCMConstants.WCM6_PATH + "search/task_delete.jsp?TaskIds=" + _taskIds, {'onSuccess' : 
				function(_trans, _json){
					CTRSAction_refreshMe();
				}
			});
		},
		cancel : function(){
			return;
		}
	});
}

//全选按钮事件绑定
Event.observe(window, 'load', function(){
	document.onkeydown = function(evt){
		var event = evt || window.event;
		var element = event.srcElement || event.target;
		if(event.keyCode== 65 && element.tagName.toUpperCase() != "INPUT"){
			$("selectAll").click();
		}
	}

	var eles = document.getElementsByName("select");
	for(var i=0; i < eles.length; i++){
		Event.observe(eles[i], 'mousemove', function(){
			Ext.get(eles[i]).addClass('wcm_attr_value_withborder');
	});
	}
});

function setDisplay(valueDom,sourceId){
	if(!$(sourceId)) return;
	var spanEl = valueDom.getElementsByTagName('SPAN')[0];
	if(!spanEl){
		Element.update(valueDom, '');
		spanEl = document.createElement('SPAN');
		spanEl.appendChild($(sourceId));
		valueDom.appendChild(spanEl);
	}else{
		Element.show(spanEl);
		var pre = valueDom.getElementsByTagName('SPAN')[1];
		if(pre) Element.hide(pre);
	}
	valueDom.onclick = "return false;";
}

function changeServer(_taskId){
	var ele = $("server");
	setTimeout(function(){
		if(ele.value == 0){
			var sTitle = '<font style="font-size:12px;font-weight:normal;">' + (wcm.LANG.SYSMENU_58 || '新增Server') + '</font>';
			wcm.CrashBoarder.get('ServerCon').show({
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
					ele.setAttribute("value",_args.id);
					$("server").appendChild(ele);
					setTimeout(function(){
						$("server").options[$("server").options.length -1].selected = true;
					},400);
					var param = {taskId:_taskId,serverId:_args.id};
					addServer(param);
				}
			});
		}else{
			var param = "taskId=" + _taskId + "&serverId=" + ele.value;
			addServer(param);
		}	 
	},400);
}

function addServer(param){
	ProcessBar.start(wcm.LANG.SYSMENU_64 || "保存检索任务...");
	new Ajax.Request(WCMConstants.WCM6_PATH + "search/search_server_dowith.jsp", {
		contentType:'application/x-www-form-urlencoded',
		asynchronous:true, 
		method:'post',
		parameters:param,
		onSuccess : function(response,json){
			ProcessBar.close();
			if(response.responseText.indexOf("false") >=0){
				Ext.Msg.alert(wcm.LANG.SYSMENU_55 || "无法连上TRSServer，请检查配置信息并确保Server已开启！",function(){location.href = location.href});
			}
		},
		onFailure : function(){
			ProcessBar.close();
		}
	}); 
}

function changeSource(source,id){
	var desc = source.options[source.selectedIndex].getAttribute("desc");
	var ele = $("setContainer_" + id);
	if(!ele) return;
	var spanEl = ele.getElementsByTagName('SPAN')[1];
	var pre = ele.getElementsByTagName('SPAN')[0];
	if(pre) Element.hide(pre);
	if(!spanEl){
		spanEl = document.createElement('SPAN');
		Element.update(spanEl, desc);
		ele.appendChild(spanEl);
	}else{
		Element.show(spanEl);
	}

	setTimeout(function(){
		Event.observe(ele, 'click', function(){
			setDisplay(ele,"taskSource_" + id);
		});}
	,400)
}

function changeGateWay(_taskId){
	var ele = $("gateway");
	if(ele.value == 0){
		var sTitle = '<font style="font-size:12px;font-weight:normal;">' + (wcm.LANG.SYSMENU_59 || '新增GateWay') + '</font>';
		wcm.CrashBoarder.get('GateWayCon').show({
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
				var param = {taskId:_taskId,gatewayId:_args.id};
				addGateWay(param);
			}
		});
	}else{
		var param = {taskId:_taskId,gatewayId:ele.value};
		addGateWay(param);
	}
}

function addGateWay(param){
	ProcessBar.start(wcm.LANG.SYSMENU_64 || "保存检索任务...");
	BasicDataHelper.JspRequest(
		WCMConstants.WCM6_PATH + "search/search_gateway_dowith.jsp",
		param,  true,
		function(transport, json){
			ProcessBar.close();
			if(transport.responseText.indexOf("false") >=0){
				Ext.Msg.alert(wcm.LANG.SYSMENU_57 || "无法连上GateWay，请检查配置信息并确保GateWay已开启！",function(){location.href = location.href;});
			}
		},function(transport, json){
			ProcessBar.close();
	});
}