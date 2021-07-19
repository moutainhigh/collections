// define for cb
function init(_params) {
	var postData = _params;
	Object.extend(postData, {
		PageSize: -1
	});
	BasicDataHelper.call('wcm6_viewdocument', 'getDocumentRefrences', postData, true, function(_trans, _json){
		var json = $a(_json, 'DocInfo');
		var bCannotOperate = false;
		if(_params['special']) { // 针对“还原”校验进行特殊处理
			var arNewJson = [];
			for (var i = 0; i < json.length; i++){
				var nModal = parseInt(json[i]['MODAL']);
				var nChnlId = parseInt(json[i]['CHANNELID']);
				if(nChnlId <0) {
					bCannotOperate = true;
					arNewJson.push(Object.extend(json[i], {RESTORE_FLAG: '_channel_deleted'}));
				}else if(nModal < 0) {
					bCannotOperate = true;
					arNewJson.push(Object.extend(json[i], {RESTORE_FLAG: '_entity_deleted'}));
				}
			}
			if(bCannotOperate) {
				json = arNewJson;
			}
		}//*/
		var sValue = TempEvaler.evaluateTemplater('txtDocInfo', json, null);
		//alert(sValue)
		Element.update($('divDocInfo'), sValue);
		
		if(bCannotOperate) {
			window.submitBtn.disable();
			$('btnCancel').focus();
			$('imClue').src = '../js/com.trs.crashboard/wcmcrashboard/resource/cannot_restore.gif';
			$('spOperation').innerHTML = getOperationDesc('_cannot_restore');
		}else{
			$('btnSubmit').focus();
			$('imClue').src = '../js/com.trs.crashboard/wcmcrashboard/resource/delete.gif';
			$('spOperation').innerHTML = getOperationDesc(_params['operation']);
		}
	});
}

function getOperationDesc(_sType){
	if(window.m_arrOptDescs == null) {
		window.m_arrOptDescs = {
			_delete : '您正准备删除如下图片：',
			_restore : '您正准备还原如下图片：',
			_trash : '您正准备删除如下图片：',
			_cannot_restore : '由于以下原因，您无法进行图片还原操作！',
			_channel_deleted : '图片所属分类尚在分类回收站内',
			_entity_deleted : '引用图片的实体尚在废稿箱内'
		}
	}
	var result = window.m_arrOptDescs[_sType] ||  '未知操作';
//*/
	return result;
}

function closeframe(_bResume){
	$('imClue').src = '../js/com.trs.dialog/img/7.gif';
	if (window.parent){
		window.parent.notifyParent2CloseMe(document.FRAME_NAME);
		if(_bResume) {
			window.parent.notifyParentOnFinished(document.FRAME_NAME);
		}else{						
			top.focus();
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