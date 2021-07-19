// define for cb
function init(_params) {
	var postData = _params;
	Object.extend(postData, {
		PageSize: -1
	});
	BasicDataHelper.call('wcm6_viewdocument', 'getDocumentRefrences', postData, true, function(_trans, _json){
		var json = $a(_json, 'DocInfo');
		if(!json || !(json.length > 0)) {
			//$fail('废稿箱内没有任何可操作的对象！');
			$('imClue').src = '../js/com.trs.dialog/img/error.gif';
			Element.hide('divIfoContainer');
			Element.show('divNoObjectFound');
			window.submitBtn.disable();
			return;
		}
		var bCannotOperate = false;
		var bContainsSpecial = false;
		if(_params['special']) { // 针对“还原”校验进行特殊处理
			var bCannotOperate = true;
			for (var i = 0; i < json.length; i++){
				var nModal = parseInt(json[i]['MODAL']);
				var nChannelId = parseInt(json[i]['CHANNELID']);
				var nDocChnlId = parseInt(json[i]['DOCCHNLID']);
				if(nDocChnlId <0) {
					bContainsSpecial = true;
					Object.extend(json[i], {RESTORE_FLAG: '_docchnl_deleted'});
				}else if(nChannelId <0) {
					bContainsSpecial = true;
					Object.extend(json[i], {RESTORE_FLAG: '_channel_deleted'});
				}else if(nModal < 0) {
					bContainsSpecial = true;
					Object.extend(json[i], {RESTORE_FLAG: '_entity_deleted'});
				}else{
					bCannotOperate = false;
				}
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
			if(_params['operation'] == '_restore'){
				$('imClue').src = '../js/com.trs.crashboard/wcmcrashboard/resource/restore.gif';
			}else if(_params['operation'] == '_trash'){
				$('imClue').src = '../js/com.trs.crashboard/wcmcrashboard/resource/trash.gif';
			}else{
				$('imClue').src = '../js/com.trs.crashboard/wcmcrashboard/resource/delete.gif';
			}
			$('spOperation').innerHTML = getOperationDesc(bContainsSpecial ? '_cannot_restore_all' : _params['operation']);
		}
	}, function(_trans, _json){
		$render500Err(_trans, _json, false, function(){
			window.parent.notifyParent2CloseMe(document.FRAME_NAME);
		});
	});
}

function getOperationDesc(_sType){
	if(window.m_arrOptDescs == null) {
		window.m_arrOptDescs = {
			_forcedelete : '您正准备强制删除如下文档：',
			_delete : '您正准备删除如下文档：',
			_restore : '您正准备还原如下文档：',
			_cannot_restore_all : '您正准备还原如下文档（含有不能还原的文档）：',
			_trash : '您正准备将如下文档放入废稿箱：',
			_cannot_restore : '由于以下原因，您无法进行文档还原操作！',
			_channel_deleted : '无法还原：文档所在栏目尚在栏目回收站内',
			_docchnl_deleted : '无法还原：文档所属栏目尚在栏目回收站内',
			_entity_deleted : '无法还原：引用文档的实体尚在废稿箱内'
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