window.onload = function(){
	Element.update("dateInfo", getCurrentTime());

	var searchKey = document.getElementById("SearchKey");
	if(searchKey.selectedIndex == -1){
		searchKey.selectedIndex = 0;
	}

	Event.observe("listData", 'click', function(event){
		event = window.event || event;
		var srcElement = Event.element(event);
		var row = getClickRow(srcElement)
		if(!row) return;
		switch(srcElement.tagName.toUpperCase()){
			case 'INPUT':
				toggleRowStyle(row);
				break;
			case 'A':
				break;
			default:
				unselectRows();
				selectRow(row);
				break;
		}
	});
};

function getClickRow(srcElement){
	while(srcElement && srcElement.id != "listData"){
		if(Element.hasClassName(srcElement, "row")){
			return srcElement;
		}
		srcElement = srcElement.parentNode;
	}
	return null;
}

window.cacheOfRows = [];
function toggleRowStyle(row){
	var aInput = row.getElementsByTagName("input");
	if(aInput.length <= 0) return;
	
	if(aInput[0].checked){
		selectRow(row);
	}else{
		unselectRow(row);
	}
}

function selectRow(row){
	if(!window.cacheOfRows.include(row.id)){
		window.cacheOfRows.push(row.id);
	}
	Element['addClassName'](row, 'activeRow');
	var aInput = row.getElementsByTagName("input");
	if(aInput.length > 0){
		aInput[0].checked = true;
	}
}

function unselectRow(row){
	window.cacheOfRows = window.cacheOfRows.without(row.id);
	Element['removeClassName'](row, 'activeRow');
	var aInput = row.getElementsByTagName("input");
	if(aInput.length > 0){
		aInput[0].checked = false;
	}
}

function unselectRows(){
	for (var i = window.cacheOfRows.length-1; i >= 0; i--){
		unselectRow($(window.cacheOfRows[i]), false);
	}
	window.cacheOfRows = [];
}

function getCurrentTime(){
	var date = new Date();
	var result = date.getYear() + "年" + (date.getMonth() + 1) + "月" + date.getDate() + "日";
	var aWeekDesc = ['日', '一', '二', '三', '四', '五', '六'];
	return result + "&nbsp;&nbsp;星期" + aWeekDesc[date.getDay()];
}

function toggleAll(){
	var aRadio = document.getElementsByName('ApplyFormIds');
	var selectedAll = aRadio.length == getSelectedIds().length;
	for (var i = 0; i < aRadio.length; i++){
		aRadio[i].checked = !selectedAll;
		if(aRadio[i].checked){
			selectRow($("row_" + aRadio[i].value));
		}else{
			unselectRow($("row_" + aRadio[i].value));
		}
	}
}

function getSelectedIds(){
	var result = [];
	var aRadio = document.getElementsByName('ApplyFormIds');
	for (var i = 0; i < aRadio.length; i++){
		if(aRadio[i].checked){
			result.push(aRadio[i].value);
		}
	}
	return result;
}

function refreshApplyForm(){
	CTRSAction_refreshMe();
}

function deleteApplyForm(_sApplyFormIds){
	//参数校验
	if(_sApplyFormIds == null || _sApplyFormIds.length <= 0){
		alert("请选择需要删除的记录!");
		return;
	}
	if(!confirm("您确定需要删除这些记录吗?"))
		return;
	
	new Ajax.Request("applyform_delete.jsp", {
		method : 'post',
		parameters : "ApplyFormIds=" + _sApplyFormIds,
		contentType : 'application/x-www-form-urlencoded', 
		onSuccess : function(transport){
			var result = transport.responseText.trim();
			if(result == 'true'){
				CTRSAction_refreshMe();
			}else{
				alert(result);
			}
		},
		onFailure : function(){
			alert("删除失败！");
		}
	});
}

function changeTab(){
	var srcElement = window.event.srcElement;
	var beforeCall = srcElement.getAttribute("beforeCall");
	if(beforeCall && window[beforeCall]){
		window[beforeCall](srcElement);
		return;
	}
	var sKey = srcElement.getAttribute("key");
	if(sKey != null){
		setActiveTab(srcElement);
	}
}

function changeListType(activeTab){
	var isDealedValue = activeTab.getAttribute("isDealed");
	if(isDealedValue == null){
		return;
	}
	$("isDealed").value = isDealedValue;
	CTRSAction_doSearch(frmSearch);
}

function setActiveTab(activeTab){
	if(activeTab == null) return;

	//remove old active tab.
	var tabContainer = $('tabContainer');
	var tabs = tabContainer.childNodes;
	for (var i = 0; i < tabs.length; i++){
		if(tabs[i].nodeType != 1)
			continue;
		if(Element.hasClassName(tabs[i], "activeTab")){
			Element.removeClassName(tabs[i], "activeTab");
			toggleTabContent(tabs[i], 'hide');
			break;
		}
	}

	//set new active tab.
	Element.addClassName(activeTab, "activeTab");
	toggleTabContent(activeTab, 'show');
}

//show,hide the area of the old active tab.
function toggleTabContent(activeTab, showMethod){
	var sKey = activeTab.getAttribute("key");
	if(sKey){
		var mapContent = sKey + "_Area";
		if($(mapContent)){
			Element[showMethod](mapContent);
		}
	}
}

function saveMailConfig(){
	var sPostData = Form.serialize("mailConfigForm");
	var sUrl = "mailconfig_add.jsp";
	new Ajax.Request(sUrl, {
		method		: 'post',
		parameters	: sPostData,
		onSuccess	: function(transport){
			var result = transport.responseText.trim();
			if(result == 'true'){
				alert("保存成功！");
			}else{
				alert(result);
			}
		},
		onFailure	: function(transport){
			var result = transport.responseText;
			alert("保存失败！\n" + result);
		}
	});
}