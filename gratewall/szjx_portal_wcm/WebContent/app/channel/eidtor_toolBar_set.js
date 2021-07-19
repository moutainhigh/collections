var PageContext = {};
var sResult = "";
var sAdvResult = "";
var bAdvEnable = true;
var bFirstLoad = true;
var bFirstAdvLoad = true;
var hiddenAdvInitForbid = false;
var hiddenAdvCancelForbid = false;
Object.extend(PageContext,{
	objId :	getParameter("ChannelId"),
	normalRowCss : 'row_normal',
	activeRowCss : 'row_active',
	lastRowId	: null,
	toggleStyle : function(element, newStyle, oldStyle){
		if(!$(element)) return;
		Element.removeClassName(element, oldStyle);
		Element.addClassName(element, newStyle);
	},
	toggleCheck : function(unCheckName,checkName){
		if(!$(unCheckName) || !$(checkName)) return;
		$(unCheckName).checked = false;
		$(checkName).checked = true;
	},
	loadCheckValue : function(toolbarName,sItemValue){
		var checkdItems = document.getElementsByName("ToolbarChecked");
		var sValueGroup = $(toolbarName).value;
		if(checkdItems.length > 0 ){
			$("ViewContainer").innerHTML = "";
			for(var i =0 ; i< checkdItems.length;i++){
				$(checkdItems[i]).checked = false;
			}
			if($("ToolbarButton").checked){
				if(! bFirstLoad) sValueGroup = sResult;
				this.loadCheck(sValueGroup,checkdItems,bFirstLoad);	
				if(bFirstLoad)bFirstLoad = false;
			}else{
				if(! bFirstAdvLoad) sValueGroup = sAdvResult;
				this.loadCheck(sValueGroup,checkdItems,bFirstAdvLoad);
				if(bFirstAdvLoad)bFirstAdvLoad = false;
			}
		}
	},
	loadCheck : function(sValueGroup,checkdItems,bFirst){
		var sElement = null;
		for(var k =0 ; k< sValueGroup.split(",").length;k++){
			if( sValueGroup.split(",")[k].length <=0) continue;
			sElement =  $("fck_" + sValueGroup.split(",")[k]);
			if(!sElement) continue;
			sElement.checked = true;	
			if($("ToolbarButton").checked && sResult.length > 0 && ! bFirstLoad){					this.onlyAttach(sElement.value,sElement.getAttribute("display"),sElement.getAttribute("scale"));
				continue;	
			}
			if($("advToolbarButton").checked && sAdvResult.length > 0 && ! bFirstAdvLoad){					this.onlyAttach(sElement.value,sElement.getAttribute("display"),sElement.getAttribute("scale"));
				continue;				
			}					this.attachRowToReal(sElement.value,sElement.getAttribute("display"),sElement.getAttribute("scale"));
		}
	},
	itemCheckConvert : function(itemName,displayName,scale){
		if(!$("fck_" + itemName)) return;
		$("fck_" + itemName).checked = ! $("fck_" + itemName).checked;
		this.CheckConvert(itemName,displayName,scale);
		
	},
	CheckConvert : function(itemName,displayName,scale){
		if(!$("fck_" + itemName)) return;
		if($("fck_" + itemName).checked){
			this.attachRowToReal(itemName,displayName,scale);
		}else{			
			this.detachRowToReal(itemName,displayName,scale);
		}
	},
	onlyAttach : function(itemName,displayName,scale){
		var aInput = $("ViewContainer").getElementsByTagName("span");
		var sValue = "<div class='row row_normal' id='" + itemName + "' item='true'><span style= 'display:inline-block;background-position:0px " + scale + "px;' class='editor_toolbar_button'>&nbsp;</span>&nbsp;<span class='tableAnotherName'>" + displayName + "</span></div>";
		if(aInput.length > 0){
			new Insertion.Bottom("ViewContainer", sValue);
		}else{
			Element.update("ViewContainer", sValue);
		}
	},
	attachRowToReal : function(itemName,displayName,scale){
		this.onlyAttach(itemName,displayName,scale);
		if(($("ToolbarButton").checked)){
			if(sResult == "e") sResult ="";
			sResult += (itemName + ",");
		}else{
			if(sAdvResult == "e") sAdvResult ="";
			sAdvResult += (itemName + ",");
		}
	},
	detachRowToReal : function(itemName,displayName){		
		var eRow = $(itemName);
		if(eRow){
			$("ViewContainer").removeChild(eRow);
			if(($("ToolbarButton").checked)){
				var index = sResult.indexOf(","+ itemName);
				sResult = sResult.substring(0,index) + sResult.substring(index + itemName.length + 1);
			}else{
				var index = sAdvResult.indexOf(","+itemName);
				sAdvResult = sAdvResult.substring(0,index) + sAdvResult.substring(index + itemName.length + 1);
			}
		}
	},
	clickToolbar : function(event){	
		this.toggleStyle("Toolbar", this.activeRowCss, this.normalRowCss);
		this.toggleStyle("advToolbar", this.normalRowCss, this.activeRowCss);
		this.toggleCheck("advToolbarButton","ToolbarButton");
		this.loadCheckValue("ToolbarValue",sResult);

	},
	clickAdvToolbar : function(event){	
		if(!bAdvEnable) return false;
		this.toggleStyle("advToolbar", this.activeRowCss, this.normalRowCss);
		this.toggleStyle("Toolbar", this.normalRowCss, this.activeRowCss);
		this.toggleCheck("ToolbarButton","advToolbarButton");
		this.loadCheckValue("AdvToolbarValue",sAdvResult);

	},
	fieldSelectAll : function(){
		var checkdItems = document.getElementsByName("ToolbarChecked");
		if(checkdItems.length > 0 ){
			$("ViewContainer").innerHTML = "";
			if(($("ToolbarButton").checked)){
				sResult = "";
			}else{
				sAdvResult = "";
			}
			for(var i =0 ; i< checkdItems.length;i++)
			{
				$(checkdItems[i]).checked = true;				this.attachRowToReal($(checkdItems[i]).value,$(checkdItems[i]).getAttribute("display"),$(checkdItems[i]).getAttribute("scale"));
			}
		}
	},
	fieldDeselectAll : function(){
		var checkdItems = document.getElementsByName("ToolbarChecked");
		if(checkdItems.length > 0 ){
			$("ViewContainer").innerHTML = "";
			if(($("ToolbarButton").checked)){
				sResult = "e";
			}else{
				sAdvResult = "e";
			}
			for(var i =0 ; i< checkdItems.length;i++)
			{
				$(checkdItems[i]).checked = false;
			}
		}
	},
	hiddenAdv : function(){
		if($("hiddenAdv").checked){
			$("advToolbarButton").disabled = true;
			bAdvEnable = false;
		}else{
			bAdvEnable = true;
			$("advToolbarButton").disabled = false;
		}
		//如果开始时为禁止高级工具栏，取消后则hiddenAdvCancelForbid=true，否则=false;
		if(hiddenAdvInitForbid && $("hiddenAdv").checked) {
			hiddenAdvCancelForbid = false;
		} else {
			hiddenAdvCancelForbid = true;
		}
	},
	deleteRow : function(){
		if(!this.lastRowId) return;
		var oLastRow = $(this.lastRowId);
		if(!oLastRow) return;
		var tempRow = Element.next(oLastRow);
		if($("fck_" + this.lastRowId)){
			$("fck_" + this.lastRowId).checked = false;
		}
		if(($("ToolbarButton").checked)){
			var index = sResult.indexOf(this.lastRowId);
			sResult = sResult.substring(0,index) + sResult.substring(index + this.lastRowId.length + 1);
		}else{
			var index = sAdvResult.indexOf(this.lastRowId);
			sAdvResult = sAdvResult.substring(0,index) + sAdvResult.substring(index + this.lastRowId.length + 1);
		}
		if(tempRow){
			this.toggleStyle(tempRow, this.activeRowCss, this.normalRowCss);
		}
		Element.remove(oLastRow);
		if(tempRow){
			this.lastRowId = tempRow.getAttribute("id");
		}
	},
	itemMouseDown : function(event){
		event = window.event || event;
		srcElement = Event.element(event);	
		if(!srcElement.parentNode.getAttribute("item")) return;
		if(this.lastRowId){
			this.toggleStyle(this.lastRowId, this.normalRowCss, this.activeRowCss);
		}
		this.lastRowId = srcElement.parentNode.getAttribute("id");
		this.toggleStyle(srcElement.parentNode.getAttribute("id"), this.activeRowCss,this.normalRowCss);
		//Event.observe("ViewContainer", 'mousemove', this.mmViewContainer);
		//Event.observe("ViewContainer", 'mouseup', this.muViewContainer);
	},
	moveControl : function(sDirection, event){
		if(!this.lastRowId) return;
		var oLastRow = $(this.lastRowId);
		if(!oLastRow) return;
		if(sDirection == 'up'){
			var oPrevious = Element.previous(oLastRow);
			if(!oPrevious) return;
			if(($("ToolbarButton").checked)){				
				sResult = this.moveup(sResult,oPrevious);
			}else{
				sAdvResult = this.moveup(sAdvResult,oPrevious);
			}
			$("ViewContainer").insertBefore(oLastRow, oPrevious);
		}else{
			var oNext = Element.next(oLastRow);
			if(!oNext) return;
			if(($("ToolbarButton").checked)){
				sResult = this.movedown(sResult,oNext);
			}else{
				sAdvResult = this.movedown(sAdvResult,oNext);
			}
			$("ViewContainer").insertBefore(oLastRow, Element.next(oNext));
		}
	},
	moveup : function(param,oPrevious){
		var sTempGroup = param.split(",");
		for(var i=0;i< sTempGroup.length;i++){
			if(sTempGroup[i].equals(oPrevious.getAttribute("id")) && sTempGroup[i+1].equals(this.lastRowId)){
				var stemp = sTempGroup[i];
				sTempGroup[i] = sTempGroup[i+1];
				sTempGroup[i+1] = stemp;
				break;
			}
		}
		return sTempGroup.join(",");
	},
	movedown : function(param,oNext){
		sTempGroup = param.split(",");
		for(var i=0;i< sTempGroup.length;i++){
			if(sTempGroup[i].equals(this.lastRowId) && sTempGroup[i+1].equals(oNext.getAttribute("id"))){
				var stemp = sTempGroup[i];
				sTempGroup[i] = sTempGroup[i+1];
				sTempGroup[i+1] = stemp;
				break;
			}
		}
		return sTempGroup.join(",");
	},
	returnToDefault : function(){
		if(($("ToolbarButton").checked)){
			bFirstLoad = true;
			sResult = "";
			this.loadCheckValue("ToolbarDefault",sResult);
		}else{
			bFirstAdvLoad = true;
			sAdvResult = "";
			this.loadCheckValue("AdvToolbarDefault",sAdvResult);
		}
	},
	initEvent : function(){
		Event.observe("Toolbar", 'click', this.clickToolbar.bind(this));
		Event.observe("advToolbar", 'click', this.clickAdvToolbar.bind(this));
		Event.observe("fieldSelectAllBtn", 'click', this.fieldSelectAll.bind(this));
		Event.observe("fieldDeselectAllBtn", 'click', this.fieldDeselectAll.bind(this));
		Event.observe("hiddenAdv", 'click', this.hiddenAdv.bind(this));
		Event.observe("ViewContainer", 'mousedown', this.itemMouseDown.bind(this));
		Event.observe("DeleteBtn", 'click', this.deleteRow.bind(this));
		Event.observe("UpBtn", 'click', this.moveControl.bind(this, 'up'));
		Event.observe("DownBtn", 'click', this.moveControl.bind(this, 'down'));
		Event.observe("returnToDefault", 'click', this.returnToDefault.bind(this));
		
		
	}
});

//嵌入在CrashBoard中的入口
function init(params){
	PageContext.initEvent();
	//初始化选中简易编辑工具栏
	if($("AdvToolbarValue").value == "null"){
		hiddenAdvInitForbid = true;
		$("hiddenAdv").click();
	}else{
		PageContext.clickAdvToolbar();
	}
	PageContext.clickToolbar();
}

function saveData(){
	var sUserName = $("UserName").value;
	var sSourceToolbar = $("SourceToolbar").value;
	var sSourceAdvToolbar = $("SourceAdvToolbar").value;
	if($("hiddenAdv").checked)
		bAdvEnable = false;
	if(sResult == ""){sResult = "e";}
	else{
		sResult = explain(sSourceToolbar,sUserName,sResult);
	}
	if(sAdvResult == ""){sAdvResult = "e";}
	else{
		sAdvResult = explain(sSourceAdvToolbar,sUserName,sAdvResult);
	}
	//判断取消了“禁用高级工作栏”后，高级工具栏是否进行了设置，如果没有，则默认进行设置；处理方法2为进行提示！！！
	if(hiddenAdvInitForbid == true && hiddenAdvCancelForbid == true) {
		if(sAdvResult == "e") {
			//如果高级工具栏没有进行设置，则默认进行设置
			sAdvResult = document.getElementById("AdvToolbarDefault").value + ",";
			
			//方法2，也可以给用户进行提示，让用户自己手动进行配置。
			//if(!window.confirm("取消'禁用高级工具栏'后，'高级工具栏'选项为空,您确定不需要进行设置吗？")) {
				//return false;
			//}
		}
	}

	//获取同步方式值
	var synType = getType();
	var oHelper = new com.trs.web2frame.BasicDataHelper();
	var oPostData = {
		"SynType" : synType,
		"ObjectId" : PageContext.objId,
		"RelIds" : $("SelectedIds").value,
		"Toolbar" : sResult,
		"AdvToolbar" : bAdvEnable ? sAdvResult : "null"
	};
	oHelper.JspRequest(
		WCMConstants.WCM6_PATH + "channel/setToolbar.jsp",
		oPostData,  true,
		function(transport, json){
			window.requesting = false;
			var result = transport.responseText.trim();
			(fCallback || Ext.emptyFn)(result);
			if(result == 'false'){
				notifySaveChannel();
			}		
		}
	);
	return true;
}

function explain(Toolbar,sUserName,sResult){
	var result = "";
	if(Toolbar == null || Toolbar.trim().length == 0){
		if(sUserName != "admin") result = (sUserName + "=" + sResult);
		else result = sResult;
		return result;
	}
	var index = Toolbar.indexOf(sUserName);
	var configGroup = Toolbar.split("&");
	if(index < 0 && sUserName != "admin"){
		result = Toolbar + ("&" + sUserName + "=" + sResult);
	}else if(index < 0){
		var hasFound = false;
		for(var i=0; i < configGroup.length;i++){
			if(configGroup[i].indexOf("=") < 0){
				hasFound = true;
				result = linkString(configGroup,i,sResult);
				break;
			}				
		}
		if(!hasFound) result = Toolbar + ("&" + sResult);
	}else{
		for(var i=0; i < configGroup.length;i++){
			if(configGroup[i].indexOf(sUserName) >=0){
				result = linkString2(configGroup,i,sResult,sUserName);
				break;
			}
		}
	}
	return result;
}

function linkString(configGroup,i,sResult){
	var result = "";
	for(var j=0; j < configGroup.length;j++){
		if(j != i){
			if(j == configGroup.length-1) result += configGroup[j];
			else result += (configGroup[j] + "&");
		}else{
			if(i == configGroup.length-1) result += sResult;
			else result += (sResult + "&");
		}
	}
	return result;
}

function linkString2(configGroup,i,sResult,sUserName){
	var result = "";
	for(var j=0; j < configGroup.length;j++){
		if(j != i){
			if(j == configGroup.length-1) result += configGroup[j];
			else result += (configGroup[j] + "&");
		}else{
			if(i == configGroup.length-1) result += (sUserName + "=" + sResult);
			else result += (sUserName + "=" + sResult + "&");
		}
	}
	return result;
}

//是否显示"选择栏目"的链接
function showLink(){
	var oChkbox = $("synToCertain");
	var oLink = $("ChnlSlt");
	if(oChkbox.checked){
		if(oLink.style.display == "none"){
			oLink.style.display = "inline";
		}
	}else{
		oLink.style.display = "none";
	}
}

//选择需要同步到的栏目
function selectChnl(event){
	Event.stop(window.event || event);
	var selectedChannelIds = $("SelectedIds").value;
	var chnlId = PageContext.objId;
	wcm.CrashBoarder.get("chnlSltCB").show({
		title : "选择要同步的栏目",
		src : WCMConstants.WCM6_PATH+'include/channel_select_forCB.html',
		width: '400px',
		height: '400px',
		maskable : true,			
		params:{isRadio:0,currChannelId:chnlId,ExcludeSelf:1,SELECTEDCHANNELIDS:selectedChannelIds,ExcludeTop:1,ExcludeVirtual:1,MultiSiteType:0,SiteTypes:'0,4'},
		callback : function(params){
			$("SelectedIds").value = params[0];
		}

	});
}

function getType(){
	var eles = document.getElementsByName("syn");
	for(var i=0; i < eles.length; i++){
		if(eles[i].checked) return eles[i].value;
	}
}