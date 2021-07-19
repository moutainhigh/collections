/*!
 * File:flowemployee_view.js,栏目[工件流]标签:当前栏目使用的流程显示
 *
 *	History			Who			What
 *	2007-03-28		wenyh		created.
 *
 */

Object.extend(PageContext,{
	init : function(_params){
		this.params = _params || {};
		Object.extend(this.params, {
			ObjectId:  channelid || 0,
			ContainsRight: true
		});			
	},
	loadFlow : function(){
		var aCombine = [];
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		aCombine.push(BasicDataHelper.Combine('wcm6_channel', 'findbyid', this.params));
		aCombine.push(BasicDataHelper.Combine('wcm6_process', 'getFlowUsedByChannel', this.params));
		oHelper.multiCall(aCombine, function(_transport,_json){
			_json = $v(_json, 'MULTIRESULT');
			//获取栏目所属站点的信息
			var nSiteId = $v(_json, 'Channel.Site.Id');
			if(!nSiteId || isNaN(nSiteId)) {
				$fail(String.format("无法获取当前栏目[ID={0}]所属站点的信息！",this.params['ObjectId']));
				return;
			}
			if(rightValue)
			var sFolderRight = rightValue; 
			if(sFolderRight == '111111111111111111111111111111111111111111111111111111111111111') {
				sFolderRight = '11111111100000000000';
			}
			var sSiteRight = $v(_json, 'Channel.Site.Right');
			if(sSiteRight) {
				sFolderRight = PageContext.__mergeRights(sFolderRight, sSiteRight);
			}

			//else
			Object.extend(PageContext.params, {
				siteid: nSiteId
			});

			nFlowId = $v(_json, 'Flow.FlowId') || 0;
			var sRight  = $v(_json, 'Flow.Right') + '';
			if(!sRight) {
				sRight = getParameter('RightValue');
			}else{
				if(sRight == '111111111111111111111111111111111111111111111111111111111111111') {
					sRight = '111111100000000000000000000000000000000000000000';
				}
				sRight = PageContext.__mergeRights(sRight, sFolderRight);
			}	
			
			//alert($v(_json, 'Flow.Right') + '\n' + sFolderRight + '\n' + sRight)
			var flow = {FlowId:nFlowId};
			
			//判断是否有修改权限
			var bCanEdit = isAccessable4WcmObject(sRight, 42);//工作流编辑权限
			//如果不是admin用户，但当前编辑的工作流属于站点类型，则判定为不具有修改权限
			if(bCanEdit && getParameter('RightValue') != '111111111111111111111111111111111111111111111111111111111111111') {
				if($v(_json, 'Flow.IsRoot') == 'true') {
					bCanEdit = false;
				}
			}

			if(nFlowId > 0){
				var nLoadView = bCanEdit ? 2 : 1;
				var sUrl = WCMConstants.WCM6_PATH + 'flow/flow_addedit.jsp';
				var sParams = 'LoadView=' + nLoadView + '&FlowId=' + nFlowId + '&rand=' + Math.random();
				$('flowviewer').src = sUrl + '?' + sParams;

				Element.hide($('divNoObjectFound'));	
				Element.show($('flowshowarea'));		
				
				Object.extend(flow,{OwnerType:$v(_json,'Flow.OwnerType',false),OwnerId:$v(_json,'Flow.OwnerId',false)});
			}else{				
				Element.show($('divNoObjectFound'));				
			}			
			
			Object.extend(PageContext.params,flow);
			Object.extend(PageContext.params,{
				'objecttype': 'flowemployee',
				'objectrights': sRight,
				'ChannelId': PageContext.params.ObjectId
			});

			//alert($toQueryStr(PageContext.params))
			$MessageCenter.sendMessage('oper_attr_panel','PageContext.response','PageContext',PageContext.params,true,true);
		});
	},
	refreshCurrRows : function(){
		PageContext.loadFlow();
	},
	updateCurrRows : function(){
		PageContext.refreshCurrRows();
	},
	__mergeRights : function(_r1, _r2){
		if(_r1 == null || _r2 == null) {
			return null;
		}
		_r1 += '', _r2 += '';
		if((_r1 = _r1.trim()) == '' || (_r2 = _r2.trim()) == '') {
			return;
		}//*/
		var result = [];
		if(_r1.length != _r2.length) {
			if(_r1.length < _r2.length) {
				var temp = _r2;
				_r2 = _r1;
				_r1 = temp;
			}
			var sTemp = '';
			for (var i = 0; i < (_r1.length - _r2.length); i++){
				sTemp += '0';
			}
			_r2 = sTemp + _r2;
		}
		for (var i = 0; i < _r2.length; i++){
			result.push((_r2.charAt(i) + _r1.charAt(i) > 0) ? '1' : '0');
		}
		return result.join('');
	}
});

/*
PageContext.init();
Event.onDOMReady(function(){
	//PageContext.drawLiterator('literator_path');
	PageContext.loadFlow();

	// ge gfc add @ 2007-5-9 10:52 如果有RotatingBar显示，先隐藏之
	if((top.actualTop || top).RotatingBar) {
		(top.actualTop || top).RotatingBar.stop();
	}
});
*/
function trace(_sChannelId, _bIsSite, _sRights, _channelType){
	var oParams = {
		RightValue : _sRights || '0',
		ChannelType : _channelType || '0'
	};
	var sUrl = '';
	if(_bIsSite) {
		oParams.siteid = _sChannelId;
		sUrl = './workflow/workflow_list.html';
	}else{
		oParams.channelid = _sChannelId;
		sUrl = './workflow/flowemployee_view.html';
	}
	//(top.actualTop||top).location_search = '?' + $toQueryStr(oParams);
	//document.location.href = sUrl + '?' + $toQueryStr(oParams);
	$MessageCenter.changeSrc('main', sUrl + '?' + $toQueryStr(oParams));

	// ge gfc add @ 2007-4-3 17:15 加入页面切换的过度页面
	if((top.actualTop || top).RotatingBar) {
		(top.actualTop || top).RotatingBar.start();
	}
}
