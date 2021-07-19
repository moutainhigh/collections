$import('com.trs.dialog.Dialog');
var SiteRecycleMgr = {
	serviceId : 'wcm6_website',
	getHelper : function(_sServceFlag){
		return new com.trs.web2frame.BasicDataHelper();
	},
	deleteall : function(_sIds, _params, _nRecNum){
		/*if(_nRecNum == null || _nRecNum <= 0) {
			$fail('没有任何要删除的站点！');
			return;
		}//*/
		if (confirm('确实要删除站点回收站中的所有站点吗？')){
			$beginSimplePB('正在删除站点..', 2);
			this.getHelper().call(this.serviceId, 'clearRecycle', _params, true, function(){
				$endSimplePB();
				$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', null);
			});
		}
	},
	restoreall : function(_sIds, _params, _nRecNum){
		/*if(_nRecNum == null || _nRecNum <= 0) {
			$fail('没有任何要还原的站点！');
			return;
		}//*/
		this.restore(_sIds, _params, true)
	},
	'delete' : function(_sIds, _params){
		_sIds = _sIds + '';
		var nCount = (_sIds.indexOf(',') == -1) ? 1 : _sIds.split(',').length;
		if (confirm('确实要删除' + (nCount == 1 ? '此' : '这 ' + nCount +' 个') + '站点吗？')){
			$beginSimplePB('正在删除站点..', 2);
			this.getHelper().call(this.serviceId, 'delete', {ObjectIds: _sIds, drop: true}, true, function(){
				$endSimplePB();
				$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', null);
			});
		}
	},
	restore : function(_sIds, _params, _bRestoreAll){
		var sClue = '';
		if(_bRestoreAll){
			sClue = '确实要还原站点回收站中的所有站点吗？';
		}else{
			_sIds = _sIds + '';
			var nCount = (_sIds.indexOf(',') == -1) ? 1 : _sIds.split(',').length;
			sClue = '确实要还原' + (nCount == 1 ? '此' : '这 ' + nCount +' 个') + '站点吗？'
		}
		if (confirm(sClue)){
			Object.extend(_params, {objectids: _sIds});
			if(_bRestoreAll) {
				_params.RestoreAll = true;
			}
			$beginSimplePB('正在还原站点..', 2);
			this.getHelper().call(this.serviceId, 'restoreSites', _params, true, function(){
				$endSimplePB();
				//ge gfc modify @ 2007-4-25 16:09
				var siteType = _params['sitetype'] || _params['SiteType'];
				if (siteType >= 0){
					$nav().refreshSiteType(siteType);
				}
				window.setTimeout(function(){
					$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext', null);
				}, 500);
			});
		}
	}
};

var $siteRecycleMgr = SiteRecycleMgr;
