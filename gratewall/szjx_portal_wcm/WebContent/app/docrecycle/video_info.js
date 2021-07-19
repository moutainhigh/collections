// define for cb
function init(_params) {
	var postData = _params;
	Object.extend(postData, {
		PageSize: -1
	});
	BasicDataHelper.call('wcm6_viewdocument', 'getDocumentRefrences', postData, true, function(_trans, _json){
		
		var json = $a(_json, 'DocInfo');
		if(!json || !(json.length > 0)) {
			//$fail('废稿箱内没有任何可操作的对象＄1�7');
			$('imClue').src = WCMConstants.WCM6_PATH + 'images/include/error.gif';
			Element.hide('divIfoContainer');
			Element.show('divNoObjectFound');
			wcmXCom.get('btnSubmit').disable();
			return;
		}
		var bCannotOperate = false;
		var bContainsSpecial = false;
		if(_params['special']) { // 针对“还原�1�7�校验进行特殊处琄1�7
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
		if(json && json.length<11){
			var sValue = TempEvaler.evaluateTemplater('txtDocInfo', json, null);
			Element.update($('divDocInfo'), sValue);
		}else{
			var json2 = [];
			for(var j=0;j<10;j++){
				json2[j] = json[j];
			}
			var sValue = TempEvaler.evaluateTemplater('txtDocInfo', json2, null);
			var sText = sValue +"......视频较多，不一一列出！^-^";

			Element.update($('divDocInfo'), sText);
		}
		for (var i = 0; i < json.length && i < 11; i++){
			var aRefChannels = $v(json[i], 'REFCHANNELS');
			if(aRefChannels==null)continue;
			updateRefChannels(i+1, aRefChannels);
		}
		if(bCannotOperate) {
			wcmXCom.get('btnSubmit').disable();
			wcmXCom.get('btnCancel').focus();
			$('imClue').src = WCMConstants.WCM6_PATH + 'images/include/cannot_restore.gif';
			$('spOperation').innerHTML = getOperationDesc('_cannot_restore');
		}else{
			wcmXCom.get('btnSubmit').focus();
			var op = _params['operation'];
			if(op == '_restore'){
				$('imClue').src = WCMConstants.WCM6_PATH + 'images/include/restore.gif';
			}else if(op == '_trash'){
				$('imClue').src = WCMConstants.WCM6_PATH + 'images/include/trash.gif';
			}else if(op == '_copy' || op == '_quote' || op == '_move'){
				$('imClue').src = WCMConstants.WCM6_PATH + 'images/include/7.gif';
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
			_copy : wcm.LANG.VIDEORECYLE_DESC_12 || '您正准备复制如下视频：',
			_move : wcm.LANG.VIDEORECYLE_DESC_13 || '您正准备移动如下视频：',
			_quote : wcm.LANG.VIDEORECYLE_DESC_14 || '您正准备引用如下视频：',
			_forcedelete : wcm.LANG.VIDEORECYLE_DESC_1 || '您正准备强制删除如下视频：',
			_delete : wcm.LANG.VIDEORECYLE_DESC_2 || '您正准备删除如下视频：',
			_restore : wcm.LANG.VIDEORECYLE_DESC_3 || '您正准备还原如下视频：',
			_cannot_restore_all : wcm.LANG.VIDEORECYLE_DESC_4 || '您正准备还原如下视频（含有不能还原的视频）：',
			_trash : wcm.LANG.VIDEORECYLE_DESC_5 || '您正准备将如下视频放入废稿箱：',
			_cannot_restore : wcm.LANG.VIDEORECYLE_DESC_6 || '由于以下原因，您无法进行视频还原操作!',
			_channel_deleted : wcm.LANG.VIDEORECYLE_DESC_7 || '无法还原：视频所在栏目尚在栏目回收站内',
			_docchnl_deleted : wcm.LANG.VIDEORECYLE_DESC_8 || '无法还原：视频所属栏目尚在栏目回收站内',
			_entity_deleted : wcm.LANG.VIDEORECYLE_DESC_9 || '无法还原：引用视频的实体尚在废稿箱内'
		}
	}
	var result = window.m_arrOptDescs[_sType] ||  (wcm.LANG.VIDEORECYLE_DESC_11 || '未知操作');
//*/
	return result;
}
function getPoint(arg){
	return arg ? "" : ".";
}
function closeframe(_bResume){
	//$('imClue').src = '../js/com.trs.dialog/img/7.gif';
	/*if (window.parent){
		window.parent.notifyParent2CloseMe(document.FRAME_NAME);
		if(_bResume) {
			window.parent.notifyParentOnFinished(document.FRAME_NAME);
		}
	}*/
	var cbr = wcm.CrashBoarder.get(window);
	if(_bResume){
		cbr.notify();
	}
	cbr.hide();
}

window.m_cbCfg = {
	btns : [
		{
			id : 'btnSubmit',
			text : wcm.LANG.TRUE||'确定',
			cmd : function(){
				closeframe(true);
				//return false;
			}
		},
		{
			id : 'btnCancel',
			text : wcm.LANG.CANCEL||'取消',
			cmd : function(){
				closeframe(false);
			}
		}
	]
}

function updateRefChannels(index, channels){
	if(channels.length == 1){
		Element.update('refChannels_'+ index, "[" + channels[0] + "]");
	}else if(channels.length > 2){
		var sHtml = "[" + channels.slice(0, channels.length - 1).join("], [") + "] and [" + channels[channels.length-1] + "]";
		Element.update('refChannels_'+ index, sHtml);
	}else{
		var sHtml = "[" + channels[0] + "] and [" + channels[channels.length-1] + "]";
		Element.update('refChannels_'+ index, sHtml);
	}
}