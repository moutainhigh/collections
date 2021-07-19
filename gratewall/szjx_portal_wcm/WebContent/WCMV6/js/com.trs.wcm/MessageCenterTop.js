$package('com.trs.wcm');

com.trs.wcm.MessageCenter = Class.create('wcm.MessageCenter');
com.trs.wcm.MessageCenter.prototype = {
	initialize : function(){
		this.iframes = {};
	},
	destroy : function(){
		for(var i in this.iframes){
			this.iframes[i] = null;
		}
		delete this.iframes;
	},
	getIframes : function(){
		var iframes = [];
		for(var i in this.iframes){
			if(this.iframes[i])iframes.push(i);
		}
		return iframes.join(',');
	},
	getRegEntity : function(_sIframeId){
		var iframe = this.iframes[_sIframeId];
		if(iframe && iframe.contentWindow){
			return iframe.contentWindow;
		}
		// else
		return null;
	},
	getNav : function(){
		return this.getRegEntity('nav_tree');
	},
	getMain : function(){
		return this.getRegEntity('main');
	},
	getOAP : function(){
		return this.getRegEntity('oper_attr_panel');
	},
	getFooter : function(){
		return this.getRegEntity('footer');
	},
	register : function(_sIframeId , _eIframe){
		this.iframes[_sIframeId] = _eIframe;
	},
	unregister : function(_sIframeId){
		if(this.iframes&&this.iframes[_sIframeId]){
			delete this.iframes[_sIframeId];
		}
	},
	refresh : function(_sIframeId,_params){
		var iframe = this.iframes[_sIframeId];
		if(iframe.contentWindow&&iframe.contentWindow.PageContext.refresh){
			iframe.contentWindow.PageContext.refresh(_params);
			iframe.contentWindow.dealWithFresh(_params);
		}
		else{
			var oldSrc = iframe.getAttribute('src',2);
			iframe.src = _sSrc.replace(/\?.*/ig,'?'+_params);
		}
	},
	getDialogContent : function(tabType, sheetType, sheetArray){
		var tempValue = (top.$personalCon.noSheetDealType || {}).paramValue;
		var msg = '当前' + (tabType == 'system' ? '库节点' : (tabType == 'site' ? '站点' : '栏目')) 
				+ "下没有对应的[<b style='color:green;'>" +  this.getFooter().tabArray[tabType][sheetType]["desc"] + "</b>]标签,";
		msg += "请选择下一步处理方式：<br>";
		msg += "<font color='blue' style='width:350px;'>";
		msg += "<span style='width:20px;line-height:25px;'><input type='radio' id='_dealType_1' name='_dealType_' value='1' " + ((tempValue==1 || tempValue==undefined) ? "checked" : "") + "></span><label for='_dealType_1'>取消当前列表切换操作，返回原处</label><br>";
		msg += "<span style='width:20px;line-height:25px;'><input type='radio' id='_dealType_2'  name='_dealType_' value='2' "  + (tempValue==2 ? "checked" : "") +  "></span><label for='_dealType_2'>跳转到第一个存在的标签页</label><br>";
		msg += "<span style='width:20px;line-height:25px;'><input type='radio' id='_dealType_3'  name='_dealType_' value='3' "  + (tempValue==3 ? "checked" : "") + "></span><label for='_dealType_3'>跳转到指定的标签页:</label>"
		msg += "<select name='_toSheet_' id='_toSheet_' style='font-size:12px;'>";
		for (var i = 0; i < sheetArray.length; i++){
			msg += "<option value='" + sheetArray[i]["type"] + "'>" + sheetArray[i]["desc"] + "</option>";
		}
		msg += "</select>";
		msg += "<br><span style='width:20px;line-height:25px;'></span><label for='_dealType_3'>如果指定标签页不存在,则跳转到第一个存在的标签页</label></font>";
		msg += "<br><font color='red' style='line-height:25px;'><input type='checkbox' name='_notShowAgain_' id='_notShowAgain_'><label for='_notShowAgain_'>保存当前设置为默认值,以便下次不再出现这个提示框</label></font>";
		return msg;
	},
	saveParamsForSheet : function(){
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		var aCombine = [];
		//保存一些必要的参数
		if(top.$personalCon.noSheetNoShowAgain == null){
			top.$personalCon.noSheetNoShowAgain = {};
			top.$personalCon.noSheetNoShowAgain.objectId = 0;
			top.$personalCon.noSheetNoShowAgain.paramName = "noSheetNoShowAgain";
		}
		top.$personalCon.noSheetNoShowAgain.paramValue = top.$('_notShowAgain_').checked ? 1 : 0;
		aCombine.push(oHelper.Combine("wcm6_individuation", "save", top.$personalCon.noSheetNoShowAgain));
		var dealTypeArray = top.document.getElementsByName("_dealType_");
		for (var i = 0; i < dealTypeArray.length; i++){
			if(dealTypeArray[i].checked){
				//保存处理的方式
				if(top.$personalCon.noSheetDealType == null){
					top.$personalCon.noSheetDealType = {};
					top.$personalCon.noSheetDealType.objectId = 0;
					top.$personalCon.noSheetDealType.paramName = "noSheetDealType";
				}
				top.$personalCon.noSheetDealType.paramValue = dealTypeArray[i].value;
				aCombine.push(oHelper.Combine("wcm6_individuation", "save", top.$personalCon.noSheetDealType));				
				if(dealTypeArray[i].value == 3){
					//保存跳转的sheet类型
					top.PageContext.defaultSheetType = top.$('_toSheet_').value;
				}
				break;
			}
		}
		//保存至数据库
		oHelper.MultiCall(aCombine);		
	},
	changeSrc : function(_sIframeId , _sSrc, _oPostData, _bCheckPage404, _404From,_s404DefaultPage,_sRedirctSetting){
		//TODO
		if(_sSrc.indexOf('/document_list_redirect.jsp')==-1
				&&_sSrc.indexOf('disable_sheet=1')==-1){// && !checkExistSheet(_sSrc)){
			if(_bCheckPage404 && checkPage404(_sSrc)){
				var eImgTmp = document.createElement('IMG');
				var sTmpSrc = _sSrc.replace(/\?.*/,'');
				eImgTmp.src = sTmpSrc;
				sTmpSrc = eImgTmp.src;
				sTmpSrc = sTmpSrc.replace(/^http(s)?:\/+[^\/]*/ig,'');
				_404From = _404From || '系统中不存在此页面,请确认.';
				$alert(_404From+'<br><br><font color=blue>'+sTmpSrc+'</font>',(_s404DefaultPage)?function(){
					$MessageCenter.changeSrc(_sIframeId,_s404DefaultPage,_oPostData);
					$dialog().hide();
				}:null);
				return;
			}
			try{
				 if(checkExistSheet(_sSrc)){
					this._changeSrc(_sIframeId , _sSrc, _oPostData,_sRedirctSetting);
					return;
				 }
			}catch(error){
				//TODO logger
				//alert(error.description);
				return;
			}
			var params = _sSrc.toQueryParams();
			if(_sSrc.indexOf("?") > 0){
				params = _sSrc.match(/^.+\?(.+)$/)[1].toQueryParams();
			}	
			var tabType = getTabType(_sSrc).toLowerCase();
			var sheetType = getSheetType(_sSrc);
			var sheetArray = this.getFooter().getSheetsForTabType(tabType, params["RightValue"], params["ChannelType"]);
			if((top.$personalCon.noSheetNoShowAgain || {}).paramValue == 1){//已选择不再显示Dialog
				execSkeepForNotSheet((top.$personalCon.noSheetDealType || {}).paramValue || 1, sheetArray, _sSrc);
			}else{//显示Dialog
				var msg = this.getDialogContent(tabType, sheetType, sheetArray);
				TransformableDialog.show("系统提示信息", msg, function(){
					TransformableDialog.hide();
					this.saveParamsForSheet();
					execSkeepForNotSheet(top.$personalCon.noSheetDealType.paramValue, sheetArray, _sSrc);	
				}.bind(this), function(){
					TransformableDialog.hide();
					execSkeepForNotSheet('1', sheetArray, _sSrc);//取消当前操作，返回原视图
				});
			}
		}else{
			this._changeSrc(_sIframeId , _sSrc, _oPostData,_sRedirctSetting);
		}
	},
	_changeSrc : function(_sIframeId , _sSrc, _oPostData,_sRedirctSetting){
		try{
			$SheetChanger.setLocationSearch();
		}catch(error){
			//TODO logger
			//alert(error.message);
		}
		var iframe = this.iframes[_sIframeId];
		if(!iframe)iframe = $(_sIframeId);
		if(!iframe)return;
		try{
			var bIsSameUrl = this.lastRedirctSetting==_sRedirctSetting;
			if(_sRedirctSetting==null&&bIsSameUrl){//非文档列表切换
				var oldSrc = iframe.getAttribute('src',2);
				bIsSameUrl = oldSrc.replace(/\?.*/ig,'')==_sSrc.replace(/\?.*/ig,'');
			}
			if(bIsSameUrl
				&&_sSrc.indexOf('/document_list_redirect.jsp')==-1 
				&&_sSrc.indexOf('/viewfieldinfo_list.html')==-1 
				&&_sSrc.indexOf('disable_sheet=1')==-1){
				var search = _sSrc.replace(/[^?]*\?/ig,'');
				if(iframe.contentWindow&&iframe.contentWindow.PageContext&&iframe.contentWindow.PageContext.refresh){								
					try{
						iframe.contentWindow.dealWithFresh(search);
						iframe.contentWindow.PageContext.refresh(search);
					}catch(err){
						//TODO
						alert('需要同时在main对应的页面引入[MessageCenter]!\n@' + err.message);
					}
					return;
				}
			}
			//else
			if(iframe.contentWindow){
				iframe.contentWindow.closing = true;
			}
			//
			RotatingBar.start(iframe, '正在切换视图，请稍候..');
			window.setTimeout(function(){
				iframe.src = _sSrc;
			}, 10);
			if(_oPostData && typeof(_oPostData) == 'object') {
				this.sendMessage('main', 'PageContext.search', 'PageContext', [_oPostData, (top.actualTop || top).location_search], true, true);
			}
		}catch(err){
			iframe.src = _sSrc;
		}
		finally{
			this.lastRedirctSetting = _sRedirctSetting;
		}
	},
	canSendMessage : function(iframe , _sFunctionName , _oMaster , _arrArgs){
		var fFunc = eval('iframe.contentWindow.'+_sFunctionName);
		var oMaster = null;
		if(typeof _oMaster=="string"){
			oMaster = eval('iframe.contentWindow.' + _oMaster);
		}
		else if(_oMaster==null){
			oMaster = iframe.contentWindow;
		}
		else{
			oMaster = _oMaster;
		}
		if(fFunc&&oMaster){
			try{
				_arrArgs = _arrArgs != null ? _arrArgs : [];
				if(!Array.isArray(_arrArgs)){
					_arrArgs = [_arrArgs];
				}
				return fFunc.apply(oMaster,_arrArgs);
			}catch(err){
				throw (err.stack||err.message);
			}
		}
		else{
			throw '';
		}
	},
	sendMessage : function(_sIframeId , _sFunctionName , _oMaster , _arrArgs , _bMustDo , _bNeedDomReady){
		_arrArgs = Object.clone(_arrArgs);
		var iframe = this.iframes[_sIframeId];
		if(!iframe){
			iframe = $(_sIframeId);
		}
		if(!iframe){
			return;
		}
		var caller = this;
		if(iframe.contentWindow&&!iframe.contentWindow.closing){
			var myDoc = iframe.contentWindow.document;
			if(!_bNeedDomReady||(_IE&&myDoc&&myDoc.body&&myDoc.body.readyState=='complete')||(!_IE&&myDoc.body)){
				try{
					return this.canSendMessage(iframe,_sFunctionName,_oMaster,_arrArgs);
				}catch(err){
					return;
				}
			}
			else if(_IE&&myDoc){
				myDoc.onreadystatechange = function(){
					if(this.readyState=='complete'){
						try{
							caller.canSendMessage(iframe,_sFunctionName,_oMaster,_arrArgs);
							myDoc.onreadystatechange = null;
						}catch(err){
						}
					}
				};
				return;
			}
			else if(myDoc){
				if (myDoc.addEventListener){
					var caller = this;
					myDoc.addEventListener("DOMContentLoaded", function(){
						return caller.canSendMessage(iframe,_sFunctionName,_oMaster,_arrArgs);
					}, false);
					return;
				}
				else{
					try{
						return this.canSendMessage(iframe,_sFunctionName,_oMaster,_arrArgs);
					}catch(err){
					}
				}
			}
		}
		if(_bMustDo){
			var args = arguments;
			setTimeout(function(){
				try{
					$MessageCenter.sendMessage.apply($MessageCenter,args);
				}catch(err){alert('In MessageTop Line 66:'+(err.stack||err.message));}
			},10);
		}
	},
	call : function(fCall,_oMaster ,_arrArgs){
		if(!Array.isArray(_arrArgs)){
			_arrArgs = [_arrArgs];
		}
		fCall.apply(_oMaster,_arrArgs);
	}
}
Event.observe(window,'unload',function(){
	$MessageCenter.destroy();
	delete $MessageCenter;
});
$MessageCenter = new com.trs.wcm.MessageCenter();