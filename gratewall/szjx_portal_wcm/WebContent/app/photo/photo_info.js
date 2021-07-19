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
		Element.update($('divDocInfo'), sValue);
		if(bCannotOperate) {
			wcmXCom.get('btnSubmit').disable();
			wcmXCom.get('btnCancel').focus();
			$('imClue').src = '../images/include/cannot_restore.gif';
			$('spOperation').innerHTML = getOperationDesc('_cannot_restore');
		}else{
			wcmXCom.get('btnSubmit').focus();
			var op = _params['operation'];
			if(op == '_restore'){
				$('imClue').src = WCMConstants.WCM6_PATH + 'images/include/restore.gif';
			}else if(op == '_trash'){
				$('imClue').src = WCMConstants.WCM6_PATH + 'images/include/trash.gif';
			}else{
				$('imClue').src = WCMConstants.WCM6_PATH + 'images/include/delete.gif';
			}
			//$('imClue').src = '../images/include/delete.gif';
			$('spOperation').innerHTML = getOperationDesc(_params['operation']);
		}
	});
}

function getOperationDesc(_sType){
	if(window.m_arrOptDescs == null) {
		window.m_arrOptDescs = {
			_delete : wcm.LANG.PHOTO_CONFIRM_9 || '您正准备删除如下图片：',
			_forcedelete :  wcm.LANG.PHOTO_CONFIRM_145 || '您正准备强制删除如下图片：',
			_restore : wcm.LANG.PHOTO_CONFIRM_10 || '您正准备还原如下图片：',
			_trash : wcm.LANG.PHOTO_CONFIRM_11 || '您正准备将如下图片放入废稿箱：',
			_cannot_restore_all : wcm.LANG.PHOTO_CONFIRM_147 || '您正准备还原如下图片（含有不能还原的图片）：',
			_cannot_restore : wcm.LANG.PHOTO_CONFIRM_12 || '由于以下原因，您无法进行图片还原操作！',
			_channel_deleted : wcm.LANG.PHOTO_CONFIRM_13 || '图片所属分类尚在分类回收站内',
			_docchnl_deleted : wcm.LANG.PHOTO_CONFIRM_148 || '无法还原：图片所属分类尚在分类回收站内',
			_entity_deleted : wcm.LANG.PHOTO_CONFIRM_14 || '引用图片的实体尚在废稿箱内'
		}
	}
	var result = window.m_arrOptDescs[_sType] ||  (wcm.LANG.PHOTO_CONFIRM_15 || '未知操作');
//*/
	return result;
}

function closeframe(_bResume){
	$('imClue').src = '../images/include/7.gif';
	var cbr = wcm.CrashBoarder.get("photo_info_dialog");
	if(_bResume){
		cbr.notify();
	}
	cbr.hide();
}