//重载com.trs.menu.Menu.prototype的部分方法
Object.extend(com.trs.menu.Menu.prototype, {
	enableHotKey : false,
	executeItemCommand : function(jsonObj, itemNode){
		if(jsonObj === undefined){
			try{
				$alert("尚未实现<font color='red'><B>" + itemNode.innerText + "</B></font>操作");
			}catch(error){
				alert("尚未实现[" + itemNode.innerText + "]操作");
			}
		}else if(jsonObj){
			if(MenuOperates[jsonObj["execCommand"]]){
				MenuOperates[jsonObj["execCommand"]](jsonObj, itemNode);
			}else{
				try{
					$alert("执行<font color='red'><B>" + itemNode.innerText + "</B></font>操作");
				}catch(error){
					alert("执行[" + itemNode.innerText + "]操作");
				}
			}
		}
	},
	loadDynamicItem : function(itemNode){
		return ItemsSetter[itemNode.getAttribute("getItems") || "defaultGetItems"].call(this,itemNode);								
	},
	setItemStyle : function(itemNode){
		var descChild = this.getFirstHTMLChild(itemNode);
		var jsonObj = this.getItemJsonObj(descChild);
		if(jsonObj){
			if(jsonObj["identify"] && ExtraStyleDealer[jsonObj["identify"]]){//额外菜单样式处理,如：checkbox型菜单
				ExtraStyleDealer[jsonObj["identify"]](jsonObj, descChild);	
			}
			if(jsonObj["identify"] && DisableDecider[jsonObj["identify"]]){//是否disabled菜单处理
				if(DisableDecider[jsonObj["identify"]](jsonObj)){
					Element.addClassName(descChild, "disabled_item");
				}else{
					Element.removeClassName(descChild, "disabled_item");
				}
			}
		}			
	}
});

/*
*菜单项是否disabled的条件
*/
var DisableDecider = {
	newWebSite : function(jsonObj, descChild){
		if(isAdmin()){
			return false;
		}
		return true;
	}
	//...
};

/*
*菜单项是否需要添加而外样式的处理。如：在菜单前面添加checkbox选择框
*/
var ExtraStyleDealer = {
	attribute_bar : function(jsonObj, descChild){
		if(getAttrPanelStatus()){
			Element.addClassName(descChild, 'checkItem');
		}else{
			Element.removeClassName(descChild, 'checkItem');
		}
	}
	//...
};

/*
*设置菜单的菜单项
*/
var ItemsSetter = {
	getOperators : function(itemNode){//获得操作任务任务下面的根据对象改变的动态内容
		var operatorsStr = '';

		var type = $tab().getCurrentTabType();
		if(type == 'right' || (type=="document" && (top.actualTop||top).PageContext.mode=="read")){
			
		}else{
			var attrWin = $MessageCenter.iframes["oper_attr_panel"].contentWindow;	
			if(attrWin.ObjectOperatorPanel && attrWin.ObjectOperatorPanel.Operators
					&& attrWin.Element.visible(attrWin.ObjectOperatorPanel.panelId)){
				var currOperators = attrWin.ObjectOperatorPanel.Operators;
				for (var i = 0; i < currOperators.length; i++){
					if(currOperators[i]["operKey"] == "seperate"){
						if(currOperators.length != (i + 1)){
							operatorsStr += "<div class==\"subMenuItem\" dynamic><div class=\"separate\"></div></div>";
						}
					}else{
						operatorsStr += "<div class=\"subMenuItem\" dynamic>";
						operatorsStr +=		"<div class=\"subMenuItemDesc\" title=\"" + currOperators[i]["operDesc"] + "\" ";
						operatorsStr +=		"params=identify:'ww',execCommand:'execOperate',operKey:'" + currOperators[i]["operKey"] + "'>"
						operatorsStr +=			currOperators[i]["operName"];
						operatorsStr +=		"</div>";
						operatorsStr += "</div>";			
					}
				}
			}
		}
		//显示隐藏此菜单项前面的分割线
		if(operatorsStr ==""){
			this.getPreviousHTMLSibing(itemNode).style.display = 'none';
		}else{
			this.getPreviousHTMLSibing(itemNode).style.display = '';
		}
		return operatorsStr;
	},
	defaultGetItems : function(itemNode){//发送请求去获得菜单项
		alert("请实现一个从本地获得菜单项的方法或则实现一个从服务器端返回菜单项的方法");
		return '';
		/*********************************************************************************
		var url = "menu/dynamic.html";
		var r = new Ajax.Request(url, {asynchronous:false});
		if(r.responseIsSuccess()){
			this.setItems(itemNode, r.transport.responseText);
		}
		*********************************************************************************/
	}
};
/*
*菜单项执行的操作命令
*/
var MenuOperates = {
	viewMyRight : function(){
		var sFeatures = "status=yes,toolbar=no,menubar=no,location=no,resizable =yes";
		window.open('auth/operator/right_view.jsp?OperId=' + userId + '&OperType=' + userObjType, document.hostname + 'rightview', sFeatures);
	},
	backfeedOnline : function(jsonObj){
		window.open(jsonObj["link"], document.hostname + 'backfeedOnline');
	},
	contact : function(jsonObj){
		var mailToA = $("mailToA");
		mailToA.href = jsonObj["link"];
		mailToA.click();
	},
	aboutWCM : function(){
		if(window.showModalDialog){
			var sFeatures = "dialogHeight:350px;dialogWidth:410px;status:no;scroll:no;";
			showModalDialog("menu/about.html", null, sFeatures);   
		}else{
			$success("北京拓尔思信息技术有限公司<br>产品二部WCM");
		}
	}
};

