$import('com.trs.dialog.Dialog');
var ChnlRecycleMgr = {
	serviceId : 'wcm6_channel',
	getHelper : function(_sServceFlag){
		return new com.trs.web2frame.BasicDataHelper();
	},
	deleteall : function(_sIds, _params, _nRecNum){
		/*if(_nRecNum == null || _nRecNum <= 0) {
			$fail('没有任何要删除的栏目！');
			return;
		}//*/
		if (confirm('确实要删除栏目回收站中的所有栏目吗？')){
			$beginSimplePB('正在删除栏目..', 2);
			this.getHelper().call(this.serviceId, 'clearRecycle', _params, true, function(){
				$endSimplePB();
				$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', null);
			});
		}
	},
	restoreall : function(_sIds, _params, _nRecNum){
		/*if(_nRecNum == null || _nRecNum <= 0) {
			$fail('没有任何要还原的栏目！');
			return;
		}//*/
		this.restore(_sIds, _params, true)
	},
	'delete' : function(_sIds, _params){
		_sIds = _sIds + '';
		var nCount = (_sIds.indexOf(',') == -1) ? 1 : _sIds.split(',').length;
		if (confirm('确实要删除' + (nCount == 1 ? '此' : '这 ' + nCount +' 个') + '栏目吗？')){
			$beginSimplePB('正在删除栏目..', 2);
			this.getHelper().call(this.serviceId, 'delete', {ObjectIds: _sIds, drop: true}, true, function(){
				$endSimplePB();
				$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', null);
			});
		}
	},
	restore : function(_sIds, _params, _bRestoreAll){
		var sClue = '';
		if(_bRestoreAll){
			sClue = '确实要还原栏目回收站中的所有栏目吗？';
		}else{
			_sIds = _sIds + '';
			var nCount = (_sIds.indexOf(',') == -1) ? 1 : _sIds.split(',').length;
			sClue = '确实要还原' + (nCount == 1 ? '此' : '这 ' + nCount +' 个') + '栏目吗？'
		}
		if (confirm(sClue)){
			Object.extend(_params, {objectids: _sIds});
			if(_bRestoreAll) {
				_params.RestoreAll = true;
			}
			$beginSimplePB('正在还原栏目..', 2);
			this.getHelper().call(this.serviceId, 'restoreChannels', _params, true, function(){
				$endSimplePB();
				if (_params.siteid > 0){
					$nav().refreshSite(_params.siteid);
				}else if (_params.channelid > 0){
					$nav().refreshChannel(_params.channelid);
				}
				window.setTimeout(function(){
					$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', null);
				}, 500);
			});
		}
	}
};

var $chnlRecycleMgr = ChnlRecycleMgr;
