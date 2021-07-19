// define for cb
function init(_params) {
	var serviceName = "wcm6_MetaDataDef";
	var methodName = "queryViewsUsintTable";
	var oHelper = new com.trs.web2frame.BasicDataHelper();
	var aCombine = [];
	aCombine.push(oHelper.Combine('wcm6_MetaDataDef', 'findDBTableInfosByIds',{
		objectids	: _params.objectids,
		selectFields: 'tableinfoid,anotherName',
		fieldsToHTML: 'anotherName'
	}));
	var aIds = _params.objectids.split(",");
	for (var i = 0; i < aIds.length; i++){
		aCombine.push(oHelper.Combine(serviceName, methodName,{
			TableInfoId	: aIds[i],
			selectFields: 'viewInfoId,viewDesc',
			fieldsToHTML: 'viewDesc'
		}));
	}
	oHelper.MultiCall(aCombine, function(_trans, _json){
		var aTableInfos = $a(_json, "MULTIRESULT.METADBTABLEs.METADBTABLE") || [];
		var aMetaViews = $a(_json, "MULTIRESULT.METAVIEWS") || [];
		for (var i = 0; i < aMetaViews.length; i++){
			aMetaViews[i]["TABLEINFOID"] = $v(aTableInfos[i], "TABLEINFOID");
			aMetaViews[i]["TABLEDESC"] = $v(aTableInfos[i], "ANOTHERNAME");
		}
		//var sValue = TempEvaler.evaluateTemplater('txtEmploymentInfo', _json["MULTIRESULT"], null);
		var sValue = getDeleteInfo(aMetaViews);
		Element.update($('divEmploymentInfo'), sValue);
		
		//$('btnSubmit').focus();
		$('imClue').src = '../js/com.trs.crashboard/wcmcrashboard/resource/delete.gif';
	});        
}


function closeframe(_bResume){
	$('imClue').src = '../js/com.trs.dialog/img/7.gif';
	if (window.parent){
		window.parent.notifyParent2CloseMe(document.FRAME_NAME);
		if(_bResume) {
			window.parent.notifyParentOnFinished(document.FRAME_NAME);
		}
	}
}


//button init....
Event.observe(window, 'load', function(){
	(window.submitBtn = new $WCMButton({
		ButtonId	: 'btnSubmit',
		ButtonType	: $ButtonType.OK,
		Action		: 'closeframe(true)',
		Container	: 'ButtonContainer'
	})).loadButton();
	new $WCMButton({
		ButtonId	: 'btnCancel',
		ButtonType	: $ButtonType.CANCEL,
		Action		: 'closeframe(false)',
		Container	: 'ButtonContainer'
	}).loadButton();
});

function getDeleteInfo(metaViews){
	var aHTML = [];
	for (var i = 0; i < metaViews.length; i++){
		var tableInfoId = $v(metaViews[i], "TABLEINFOID");
		var tableDesc = $v(metaViews[i], "TABLEDESC");
		aHTML.push(
			'<div class="dataItem">',
				'<span style="padding:2px;">' + (i+1) + '</span>',
				'<span title="元数据-' + tableInfoId + '" style="color:green;">',
					tableDesc,
				'</span>',
				'<span>'
		);
		var aMetaView = $a(metaViews[i], "METAVIEW") || [];
		if(aMetaView.length == 0){
			aHTML.push('[<span style="color:red;">无视图引用</span>]');
		}else{
			aHTML.push('<span>被引用到视图</span>');
			for (var j = 0; j < aMetaView.length; j++){
				var viewInfoId = $v(aMetaView[j], "VIEWINFOID");
				var viewDesc = $v(aMetaView[j], "VIEWDESC");
				aHTML.push('[<span class="alertion_title" title="视图-' + viewInfoId + '">' + viewDesc + '</span>]');							
			}		
		}
		aHTML.push('</span>');
	}
	return aHTML.join("");
}