/*在此处添加一些自定义js处理*/

function initFieldValue(){
	if(bAdd){
		com.trs.ui.ComponentMgr.get('organcat').setValue(""+nOrgancat+"");
		var oNode = $('organcat'+"-text");
		if(oNode){
			oNode.value = sPublisher;
			oNode.innerHTML = sPublisher;
		}
		com.trs.ui.ComponentMgr.get('organcat').setValue({
			value : nOrgancat, 
			desc : sPublisher
		});
		com.trs.ui.ComponentMgr.get('subcat').setValue({
			value : nSubcat, 
			desc : sShowSubcat
		});
		com.trs.ui.ComponentMgr.get('themecat').setValue({
			value : nThemecat, 
			desc : sShowThemecat
		});
		com.trs.ui.ComponentMgr.get('Publisher').setValue(sOrgancat);
	}
}

function initIndexField(){
	var indexDom = $('idxID');
	if(indexDom) {
		indexDom.disabled = "true";
		var newDom = document.createElement("span");
		newDom.innerHTML = '<font color=blue WCMAnt:param="metaviewdata_addedit.jsp.automatic">（系统自动产生）</font>';
		indexDom.parentNode.appendChild(newDom);
	}
}
Event.observe(window, 'load', function(event){
		
	var box = "tabs-1";
		
	var boxElement = $(box);
	var selectChannelElement = $('selectChannelDiv');
	if(selectChannelElement) {
		boxElement.insertBefore(selectChannelElement,boxElement.firstChild);
	}
});