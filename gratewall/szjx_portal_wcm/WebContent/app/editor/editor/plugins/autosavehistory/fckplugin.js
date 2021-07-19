//AutoSaveHistory
var myActualTop = (top.actualTop||top);
FCKCommands.RegisterCommand( 'AutoSaveHistory', 
new FCKDialogCommand( 'AutoSaveHistory', FCKLang.AutoSaveHistoryDlgTitle,
myActualTop.BasePath+'fck_autosave_history.html', 500, 320 ) ) ;
var oAutoSaveHistoryItem = new FCKToolbarButton( 'AutoSaveHistory', FCKLang.AutoSaveHistoryBtn) ;
oAutoSaveHistoryItem.IconPath = FCKPlugins.Items['autosavehistory'].Path + 'history.gif' ;
FCKToolbarItems.RegisterItem( 'AutoSaveHistory', oAutoSaveHistoryItem ) ;
if(true//_IE){

	FCK.saveUserData = function(_element,_oData,_nVersion){
		_element.addBehavior("#default#userData");
		for(var sName in _oData){
			_element.setAttribute(sName,_oData[sName]);
		}
		_element.save("TRSEditorAutoSave_"+_nVersion);
	}
	FCK.loadUserData = function(_element,_oData,_nVersion){
		_element.addBehavior("#default#userData");
		_element.load("TRSEditorAutoSave_"+_nVersion);
		for(var sName in _oData){
			_oData[sName] = _element.getAttribute(sName);
		}
		return _oData;
	}
	FCK.clearUserData = function(_element,_oData,_nVersion){
		try{
			_element.addBehavior("#default#userData");
			var oTimeNow = new Date();
			oTimeNow.setMinutes(oTimeNow.getMinutes() - 1);
			var sExpirationDate = oTimeNow.toString();
			_element.expires = sExpirationDate;
			for(var sName in _oData){
				_element.setAttribute(sName,"");
			}
			_element.save("TRSEditorAutoSave_"+_nVersion);
		}catch(err){}
	}
	FCK.AttachSaveUserData = function(){
		var oTop = top.actualTop||top;
		var oEditFrame = FCK.EditorWindow.frameElement;
		var oCookies = FCK.loadCookie();
		var nCurrVersion = oCookies["LastVersion"]||0;
		if(oCookies["LastFailure"]!=null&&oCookies["LastFailure"]!='false'){//保存失败
			try{
				var nVersion = oCookies["FailureVersion"];
				var oData = {"Title":"","Content":""};
				FCK.loadUserData(oEditFrame,oData,nVersion);
				if(!(oData["Title"]==null&&oData["Content"]==null)&&confirm(FCKLang.AutoSaveHistoryCRM)){
					parent.GetTitleElement().value = oData["Title"]||'';
					FCK.SetHTML(oData["Content"]||'');
				}
				else{
					FCK.clearUserData(oEditFrame,oData,nVersion);
				}
				FCK.clearCookie('LastFailure');
				FCK.clearCookie('FailureVersion');
			}catch(err){}
		}
		if(oTop.enableAutoSave){
			oTop.autosave = setTimeout(function(){
				try{
					var oEditFrame = FCK.EditorWindow.frameElement;
//					var aContents = FCK.GetHTML(true,"both");
					var aContents = FCK.EditorDocument.body.innerHTML;
					if(String.isString(aContents)){
						aContents = [aContents,aContents];
					}
					var oData = {
						"Title":parent.GetTitleElement().value,
						"Content": aContents[0],
						"Date":new Date().toString(0)
					};
					if(!FCK.isBlankContent(aContents[1])){
						var oLastData = {"Title":"","Content":""}
						FCK.loadUserData(oEditFrame,oLastData,nCurrVersion);
//						if(FCKDocumentProps.PreRender){
//							oLastData["Content"] = FCKDocumentProps.PreRender(oLastData["Content"]||'');
//						}
						oLastData["Title"] = oLastData["Title"] || '';
						//FIX IT 和上次没改变导致不再监听
						var bContentChanged = (oLastData["Title"].trim()!=oData["Title"].trim()
							|| oLastData["Content"].trim()!=aContents[1].trim());
						if(bContentChanged){
							nCurrVersion++;
							if(nCurrVersion>10){
								nCurrVersion = 1;
							}
							FCK.setCookie("LastVersion",nCurrVersion);
							try{
								FCK.saveUserData(oEditFrame,oData,nCurrVersion);
								if(oTop.showAutoSaveMessage){
									oTop.showAutoSaveMessage();
								}
							}catch(err){
								//Just Skip it.
								//alert("err"+err.message);
							}
						}
					}
				}catch(err2){
					//Just Skip it.
					//alert("err2"+err2.message);
				}
				oTop.autosave = setTimeout(arguments.callee,10000);
			},10000);
		}
		oTop.whenSaveFailure = function(){
			var oEditFrame = FCK.EditorWindow.frameElement;
			clearTimeout(oTop.autosave);
			var oData = {
				"Title":parent.GetTitleElement().value,
				"Content":FCK.GetHTML(true,true),
				"Date":new Date().toString(0)
			};
			if(!FCK.isBlankContent(oData["Content"])){
				nCurrVersion++;
				if(nCurrVersion>10){
					nCurrVersion = 1;
				}
				FCK.setCookie("LastVersion",nCurrVersion);
				FCK.setCookie("LastFailure",new Date().toString());
				FCK.setCookie("FailureVersion",nCurrVersion);
				try{
					FCK.saveUserData(oEditFrame,oData,nCurrVersion);
					alert(FCKLang.AutoSaveHistoryALERT);
				}catch(err){}
			}
		}
	}
}