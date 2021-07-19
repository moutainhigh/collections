// define for cb
var m_cb = null;
function init(_params, cb) {
	m_cb = cb;
	var postData = _params;
	Object.extend(postData, {
		PageSize: -1
	});
	BasicDataHelper.call('wcm6_viewdocument', 'getDocumentRefrences', postData, true, function(_trans, _json){
		
		var json = $a(_json, 'DocInfo');
		if(!json || !(json.length > 0)) {
			//$fail('废稿箱内没有任何可操作的对象！');
			$('imClue').src = WCMConstants.WCM6_PATH + 'images/include/error.gif';
			Element.hide('divIfoContainer');
			Element.show('divNoObjectFound');
			//wcmXCom.get('btnSubmit').disable();
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
		if(json && json.length<10){
			var sValue = TempEvaler.evaluateTemplater('txtDocInfo', json, null);
			Element.update($('divDocInfo'), sValue);
		}else{
			var json2 = [];
			for(var j=0;j<10;j++){
				json2[j] = json[j];
			}
			var sValue = TempEvaler.evaluateTemplater('txtDocInfo', json2, null);
			var sText = sValue + wcm.LANG.INFOVIEW_DOCRECYLE_DESC_10 || "......文档较多，不一一列出！^-^";

			Element.update($('divDocInfo'), sText);
		}
		for (var i = 0; i < json.length && i < 10; i++){
			var aRefChannels = $v(json[i], 'REFCHANNELS');
			if(aRefChannels==null)continue;
			updateRefChannels(i+1, aRefChannels);
		}
		if(bCannotOperate) {
			//wcmXCom.get('btnSubmit').disable();
			//wcmXCom.get('btnCancel').focus();
			$('imClue').src = WCMConstants.WCM6_PATH + 'images/include/cannot_restore.gif';
			$('spOperation').innerHTML = getOperationDesc('_cannot_restore');
		}else{
			//wcmXCom.get('btnSubmit').focus();
			if(_params['operation'] == '_restore'){
				$('imClue').src = WCMConstants.WCM6_PATH + 'images/include/restore.gif';
			}else if(_params['operation'] == '_trash'){
				$('imClue').src = WCMConstants.WCM6_PATH + 'images/include/trash.gif';
			}else{
				$('imClue').src = WCMConstants.WCM6_PATH + 'images/include/delete.gif';
			}
			$('spOperation').innerHTML = getOperationDesc(bContainsSpecial ? '_cannot_restore_all' : _params['operation']);
		}
	}, function(_trans, _json){
		$render500Err(_trans, _json, false, function(){
			//window.parent.notifyParent2CloseMe(document.FRAME_NAME);
		});
	});
}

function getOperationDesc(_sType){
	if(window.m_arrOptDescs == null) {
		window.m_arrOptDescs = {
			_forcedelete : wcm.LANG.INFOVIEW_DOCRECYLE_DESC_1 || '您正准备强制删除如下文档：',
			_delete : wcm.LANG.INFOVIEW_DOCRECYLE_DESC_2 || '您正准备删除如下文档：',
			_restore : wcm.LANG.INFOVIEW_DOCRECYLE_DESC_3 || '您正准备还原如下文档：',
			_cannot_restore_all : wcm.LANG.INFOVIEW_DOCRECYLE_DESC_4 || '您正准备还原如下文档（含有不能还原的文档）：',
			_trash : wcm.LANG.INFOVIEW_DOCRECYLE_DESC_5 || '您正准备将如下文档放入废稿箱：',
			_cannot_restore : wcm.LANG.INFOVIEW_DOCRECYLE_DESC_6 || '由于以下原因，您无法进行文档还原操作！',
			_channel_deleted : wcm.LANG.INFOVIEW_DOCRECYLE_DESC_7 || '无法还原：文档所在栏目尚在栏目回收站内',
			_docchnl_deleted : wcm.LANG.INFOVIEW_DOCRECYLE_DESC_8 || '无法还原：文档所属栏目尚在栏目回收站内',
			_entity_deleted : wcm.LANG.INFOVIEW_DOCRECYLE_DESC_9 || '无法还原：引用文档的实体尚在废稿箱内'
		}
	}
	var result = window.m_arrOptDescs[_sType] ||  (wcm.LANG.INFOVIEW_DOCRECYLE_DESC_11 || '未知操作');
//*/
	return result;
}
Object.clone = function(_o,_bDeep){
	if(typeof _o!='object'){
		return _o;
	}
	if(!_bDeep){
		if(Array.isArray(_o)){
			return Array.apply(null,_o);
		}
		return Object.extend({},_o);
	}
	else{
		var oReturn = null;
		if(Array.isArray(_o)){
			oReturn = [];
			for(var i=0;i<_o.length;i++){
				oReturn.push(Object.clone(_o[i],true));
			}
		}
		else{
			oReturn = {};
			for(var prop in _o){
				oReturn[prop] = Object.clone(_o[prop],true);
			}
		}
		return oReturn;
	}
};
function onOk(){
	m_cb.hide();
}
function updateRefChannels(index, channels){
	if(channels.length == 1){
		Element.update('refChannels_'+ index, "[" + channels[0] + "]");
	}else if(channels.length > 2){
		var sHtml = "[" + channels.slice(0, channels.length - 1).join("], [") + (wcm.LANG.INFOVIEW_DOC_175||"] 和 [") + channels[channels.length-1] + "]";
		Element.update('refChannels_'+ index, sHtml);
	}else{
		var sHtml = "[" + channels[0] + (wcm.LANG.INFOVIEW_DOC_175||"] 和 [") + channels[channels.length-1] + "]";
		Element.update('refChannels_'+ index, sHtml);
	}
}
function getPoint(arg){
	return arg ? "" : ".";
}