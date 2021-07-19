var myActualTop = parent.parent;
var editorCfg = myActualTop.editorCfg || {};
//AdInTrs
(function(){
	FCKLang.AdInTrsBtn = FCKLang.AdInTrsBtn || "插入广告位";
	//AdInTrsCommand
	var AdInTrsCommand = function(){
		this.Name = 'AdInTrs' ;
	}
	AdInTrsCommand.prototype.Execute = function(){
		try{
			myActualTop.loadTRSAdOption();
		}catch(err){
			//Just Skip it.
		}
	}
	AdInTrsCommand.prototype.GetState = function(){
		return FCK_TRISTATE_OFF;
	}
	FCKCommands.RegisterCommand( 'AdInTrs', new AdInTrsCommand()) ;
	var oAdInTrsItem = new FCKToolbarButton( 'AdInTrs', FCKLang.AdInTrsBtn, 
		null, null, null, null, 70 ) ;
	FCKToolbarItems.RegisterItem( 'AdInTrs', oAdInTrsItem ) ;

	FCK.doAdInTrs = function(_oAttrs){
		var e = FCK.EditorDocument.createElement('SCRIPT') ;
		e.setAttribute('fck_adintrs', '1');
		for(var name in _oAttrs){
			e.setAttribute(name, _oAttrs[name], 0);
		}
		var oFakeImage = FCKDocumentProcessor_CreateFakeImage( 'FCK__AdInTrs', e ) ;
		oFakeImage	= FCK.InsertElement( oFakeImage ) ;
	}
	FCKFormatEditedHtml.AppendNew(function(_sHTML){
		_sHTML = _sHTML.replace(/<SCRIPT((?:[^>]*)fck_adintrs=(?:[^>]*))>((.|\n|\r)*?)<\/SCRIPT>/ig,
			'<TRSAD_SCRIPT$1>$2</TRSAD_SCRIPT>');
		return _sHTML;
	});
	var AdInTrsProcessor = FCKDocumentProcessor.AppendNew() ;
	AdInTrsProcessor.ProcessDocument = function( document ){
		var aScripts = document.getElementsByTagName( 'TRSAD_SCRIPT' ) ;
		var i = aScripts.length - 1 ;
		while ( i >= 0 && ( eScript = aScripts[i--])){
			var e = FCK.EditorDocument.createElement('SCRIPT') ;
			for(var j=0,n=eScript.attributes.length;j<n;j++){
				var oAttr = eScript.attributes[j];
				if(!Ext.isIE || oAttr.specified){
					e.setAttribute(oAttr.name,eScript.getAttribute(oAttr.name, 2), 0);
				}
			}
			var oFakeImage = FCKDocumentProcessor_CreateFakeImage( 'FCK__AdInTrs', e) ;
			eScript.parentNode.insertBefore( oFakeImage, eScript ) ;
			eScript.parentNode.removeChild( eScript ) ;
		}
	}
})();
//History
(function(){
	//if(!Ext.isIE)return;
	function fmt2Digit(n){
		return n>=10 ? n : '0' + n;
	}
	Date.prototype.format = function(fm){
		if(!fm)return "";
		var dt=this;
		fm = fm.replace(/yyyy/ig,dt.getFullYear());
		var y = "" + dt.getFullYear();
		y = y.substring(y.length-2);
		return fm.replace(/yy/ig, y)
			.replace(/mm/g,fmt2Digit(dt.getMonth()+1))
			.replace(/dd/ig,fmt2Digit(dt.getDate()))
			.replace(/hh/ig,fmt2Digit(dt.getHours()))
			.replace(/MM/g,fmt2Digit(dt.getMinutes()))
			.replace(/ss/ig,fmt2Digit(dt.getSeconds()));
	}
	FCKLang.AutoSaveHistoryBtn = FCKLang.AutoSaveHistoryBtn || "恢复历史记录";
	FCKLang.AutoSaveHistoryDlgTitle = FCKLang.AutoSaveHistoryDlgTitle ||  '选择历史记录进行恢复';
	FCKLang.AutoSaveHistoryCRM = FCKLang.AutoSaveHistoryCRM || '您最近的一篇文章尚未发表成功,是否确认恢复?';
	FCKLang.AutoSaveHistoryALERT = FCKLang.AutoSaveHistoryALERT || '文档提交失败,系统已自动备份当前版本,重新刷新页面可恢复该版本.';
	//AutoSaveHistory
	FCKCommands.RegisterCommand( 'AutoSaveHistory', 
		new FCKDialogCommand( 'AutoSaveHistory', FCKLang.AutoSaveHistoryDlgTitle,
			editorCfg.basePath + 'fck_autosave_history.html', 500, 320 ) ) ;
	var oAutoSaveHistoryItem = new FCKToolbarButton( 'AutoSaveHistory', FCKLang.AutoSaveHistoryBtn
		,null, null, null, null, 83) ;
	FCKToolbarItems.RegisterItem( 'AutoSaveHistory', oAutoSaveHistoryItem ) ;
	FCK.saveUserData = function(_element,_oData,_nVersion){
		if(_element.addBehavior) _element.addBehavior("#default#userData");		
		if(window.sessionStorage){//for firefox 2.0+
			try{
				for(var sName in _oData){
					sessionStorage.setItem(sName + "_" + _nVersion,_oData[sName]);
				}
			}catch (ex){
			}
		}else{			
			for(var sName in _oData){
				_element.setAttribute(sName,_oData[sName]);
			}
			_element.save("TRSEditorAutoSave_"+_nVersion);
		}
	}
	FCK.loadUserData = function(_element,_oData,_nVersion){
		if(_element.addBehavior) _element.addBehavior("#default#userData");
		if(window.sessionStorage){//for firefox 2.0+
			try{
				for(var sName in _oData){
					_oData[sName] = sessionStorage.getItem(sName + "_" + _nVersion);
				}
				return _oData;
			}catch (ex){
			}
		}else{
			_element.load("TRSEditorAutoSave_"+_nVersion);
			for(var sName in _oData){
				_oData[sName] = _element.getAttribute(sName);
			}
			return _oData;
		}
	}
	FCK.clearUserData = function(_element,_oData,_nVersion){
		try{
			if(_element.addBehavior) _element.addBehavior("#default#userData");
			if(window.sessionStorage){//for firefox 2.0+
				try{
					for(var sName in _oData){
						sessionStorage.removeItem(sName + "_" + _nVersion);
					}
				}catch (ex){
				}
			}else{
				var oTimeNow = new Date();
				oTimeNow.setMinutes(oTimeNow.getMinutes() - 1);
				var sExpirationDate = oTimeNow.toString();
				_element.expires = sExpirationDate;
				for(var sName in _oData){
					_element.setAttribute(sName,"");
				}
				_element.save("TRSEditorAutoSave_"+_nVersion);
			}
		}catch(err){}
	}
	FCK.AttachSaveUserData = function(){
		var oTop = myActualTop;
		var oEditFrame = FCK.EditorWindow.frameElement;
		var oCookies = FCK.loadCookie();
		var nCurrVersion = oCookies["LastVersion"]||0;
		if(oCookies["LastFailure"]!=null&&oCookies["LastFailure"]!='false'
			&&!editorCfg.editMode){//保存失败
			try{
				var nVersion = oCookies["FailureVersion"];
				var oData = {"Title":"","Content":""};
				FCK.loadUserData(oEditFrame,oData,nVersion);
				if(!(oData["Title"]==null&&oData["Content"]==null)
					&&confirm(FCKLang.AutoSaveHistoryCRM)){
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
		if(!editorCfg.enableAutoSave)return;
		oTop.autosave = setTimeout(function(){
			try{
				var oEditFrame = FCK.EditorWindow.frameElement;
				var aContents = FCK.EditorDocument.body.innerHTML;
				if(Ext.isString(aContents)){
					aContents = [aContents,aContents];
				}
				var oData = {
					"Title":parent.GetTitleElement().value,
					"Content": aContents[0],
					"Date":new Date().format('yyyy-mm-dd HH:MM:ss')
				};
				if(!FCK.isBlankContent(aContents[1])){
					var oLastData = {"Title":"","Content":""}
					FCK.loadUserData(oEditFrame,oLastData,nCurrVersion);
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
							if(editorCfg.onAutoSave){
								editorCfg.onAutoSave();
							}
						}catch(err){
							//Just Skip it.
						}
					}
				}
			}catch(err2){
				//Just Skip it.
			}
			oTop.autosave = setTimeout(arguments.callee,10000);
		},10000);
		oTop.whenSaveFailure = function(){
			var oEditFrame = FCK.EditorWindow.frameElement;
			clearTimeout(oTop.autosave);
			var oData = {
				"Title" : parent.GetTitleElement().value,
				"Content" : FCK.GetHTML(true,true),
				"Date" : new Date().toString(0)
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
})();
//Comment
(function(){
	FCKLang.CommentBtn = FCKLang.CommentBtn || "批注";
	FCKLang.CommentDlgTitle = FCKLang.CommentDlgTitle || '编辑批注属性';
	FCKLang.CommentDel = FCKLang.CommentDel || "删除批注";
	FCKLang.CommentAddIntoDoc = FCKLang.CommentAddIntoDoc || "将批注加入文档";
	FCKLang.CommentSetBg1 = FCKLang.CommentSetBg1 || "黄色";
	FCKLang.CommentSetBg2 = FCKLang.CommentSetBg2 || "橙色";
	FCKLang.CommentSetBg3 = FCKLang.CommentSetBg3 || "粉色";
	FCKLang.CommentSetBg4 = FCKLang.CommentSetBg4 || "绿色";
	FCKLang.CommentSetBg5 = FCKLang.CommentSetBg5 || "蓝色";
	FCKLang.CommentSetBg6 = FCKLang.CommentSetBg6 || "紫色";
	FCKLang.CommentSet1 = FCKLang.CommentSet1 || "请在此输入批注";
	FCKLang.CommentSet2 = FCKLang.CommentSet2 || '是否在注释中包含指定文本?';
	FCKLang.CommentSet3 = FCKLang.CommentSet3 || '是否将此批注加入到文档中?';
	FCKLang.CommentSet4 = FCKLang.CommentSet4 || "一旦点击\"确定\"就无法恢复";
	var FCKCommentCommand = function(_sName,_sBgColorIndex){
		this.Name = _sName ;
		this.BgColorIndex = _sBgColorIndex || 1;
	}
	var FCKCommentCommandBgColors = ['#FFFFD7','#FFFFD7','#FFE3C0',
		'#FFD7FF','#D7FFD7','#D7FFFF','#EED7FF'];
	FCKCommentCommand.prototype.Execute = function(){
		switch(this.Name){
			case 'Comment':
				var oCookies = FCK.loadCookie();
				var sValue = sDefault = FCKLang.CommentSet1,sBgColor = FCKCommentCommandBgColors[1];
				if(oCookies['EditorCommentBgColor']){
					sBgColor = oCookies['EditorCommentBgColor'];
				}
				var selRange = GetWYSIWYGSelectionRange();
				var MSG_TOO_COMPLEX = 'MSG_TOO_COMPLEX';
				var MSG_INCLUDE_SEL_TEXT = FCKLang.CommentSet2;
				if (IsDefined(selRange, "collapsed")) { // non-IE browsers
					if (!selRange.collapsed) {
						if (WY_isRangeComplex_(selRange)) {
							if(confirm(MSG_TOO_COMPLEX)) {//TODO
								selRange.collapse(false);
							}
						} else if (!confirm(MSG_INCLUDE_SEL_TEXT)) {
							selRange.collapse(false);
						} else {
						}
					}
				} else if (IsDefined(selRange, "htmlText")) { // IE browsers
					if (selRange.htmlText.length > 0 && !FCK.isBlankContent(selRange.htmlText)) {
						if (confirm(MSG_INCLUDE_SEL_TEXT)) {
							sValue = selRange.htmlText;
						}
					}
				}
				var oSpan = FCKComments.Add( sValue, sBgColor, sDefault );
				SelectNode(oSpan.getElementsByTagName("SPAN")[0]);
				break;
			case 'CommentDel':
				FCKComments.GetCommentSpan();
				FCKComments.AddIntoDoc(false);
				break;
			case 'CommentAddIntoDoc':
				FCKComments.GetCommentSpan();
				FCKComments.AddIntoDoc(true);
				break;
			case 'CommentSetBg':
				FCKComments.GetCommentSpan();
				FCKComments.SetBgColor(
					FCKCommentCommandBgColors[this.BgColorIndex] || FCKCommentCommandBgColors[1],true);
				break;
		}
	}
	FCKCommentCommand.prototype.GetState = function(){
		return FCK_TRISTATE_OFF ;
	}
	FCKCommands.RegisterCommand( 'Comment', 
		new FCKCommentCommand('Comment')) ;
	FCKCommands.RegisterCommand( 'CommentDel', 
		new FCKCommentCommand('CommentDel')) ;
	FCKCommands.RegisterCommand( 'CommentAddIntoDoc', 
		new FCKCommentCommand('CommentAddIntoDoc')) ;
	FCKCommands.RegisterCommand( 'CommentSetBg1', 
		new FCKCommentCommand('CommentSetBg',1)) ;
	FCKCommands.RegisterCommand( 'CommentSetBg2', 
		new FCKCommentCommand('CommentSetBg',2)) ;
	FCKCommands.RegisterCommand( 'CommentSetBg3', 
		new FCKCommentCommand('CommentSetBg',3)) ;
	FCKCommands.RegisterCommand( 'CommentSetBg4', 
		new FCKCommentCommand('CommentSetBg',4)) ;
	FCKCommands.RegisterCommand( 'CommentSetBg5', 
		new FCKCommentCommand('CommentSetBg',5)) ;
	FCKCommands.RegisterCommand( 'CommentSetBg6', 
		new FCKCommentCommand('CommentSetBg',6)) ;
	var oCommentItem = new FCKToolbarButton( 'Comment', FCKLang.CommentBtn
		,null, null, null, null, 69) ;
	FCKToolbarItems.RegisterItem( 'Comment', oCommentItem ) ;
	var FCKComments = new Object() ;
	var WY_commentSpan_,WY_newCommentColor_;
	FCKComments.Add = function( _sComment, _sBgColor, _sDefault ){
		var oSpan = FCK.CreateElement( 'SPAN' ) ;
		try{
			this.SetupSpan( oSpan, _sComment, _sBgColor ) ;
		}catch(err){
			try{
				var eDiv = FCK.EditorDocument.createElement('DIV');
				eDiv.innerHTML = _sComment;
				this.SetupSpan( oSpan, eDiv.innerText, _sBgColor ) ;
			}catch(err2){
				this.SetupSpan( oSpan, _sDefault, _sBgColor ) ;
			}
		}
		return oSpan;
	}
	var toColorPart = function(num){
		if(num < 10) return "0"+num;
		return num;
	}
	FCKComments.SetupSpan = function( span, _sComment, _sBgColor ){
		var currDate = new Date();
		span.innerHTML = '<span contentEditable="true">'
			+_sComment+'<\/span> -'
			+(myActualTop.UserName||'')
			+' ' + (Ext.isIE ?  currDate.getYear() :  currDate.getYear() + 1900) + "-" + toColorPart(currDate.getMonth()+1)
			+ '-' + toColorPart(currDate.getDate()) + " " + toColorPart(currDate.getHours())
			+ ":" + toColorPart(currDate.getMinutes()) + ":" + toColorPart(currDate.getSeconds());

		span.style.backgroundColor = _sBgColor ;
		if ( FCKBrowserInfo.IsGecko )
			span.style.cursor = 'default' ;
		span.setAttribute('_trscomment','true');
		span.id = 'fckcomment_'+(myActualTop.UserName||'');
		span.className = 'fck_comment';
		span.contentEditable = false ;
		span.onresizestart = function(){
			FCK.EditorWindow.event.returnValue = false ;
			return false ;
		}
	}
	FCKComments.GetCommentSpan = function(){
		var eSelected = FCKSelection.GetSelectedElement() ;
		WY_commentSpan_ = null;
		if ( eSelected.tagName == 'SPAN' && eSelected.getAttribute('_trscomment',2) ){
			WY_commentSpan_ = eSelected ;
		}
		return WY_commentSpan_;
	}
	FCKComments.AddIntoDoc = function(addToDoc) {
		try {
			if (!WY_commentSpan_) {
				return;
			}
			var ask = addToDoc ? FCKLang.CommentSet3 : FCKLang.CommentDel;
			if (!confirm(ask + " " + FCKLang.CommentSet4)) {
				return;
			}
			var textToAdd = null;
			if (addToDoc) {
				textToAdd = RemoveHTMLMarkup(WY_commentSpan_.innerHTML);
				var newNode = FCK.EditorDocument.createTextNode(textToAdd);
				WY_commentSpan_.parentNode.replaceChild(newNode, WY_commentSpan_);
			} else {
				WY_commentSpan_.parentNode.removeChild(WY_commentSpan_);
			}

		} catch (ex) {
		}
	}
	FCKComments.SetBgColor = function(_sBgColor,_bAll) {
		var sSpanId = 'fckcomment_'+(myActualTop.UserName||'');
		if (!_bAll){
			if (WY_commentSpan_) {
				WY_commentSpan_.style.backgroundColor = _sBgColor;
			}
		}
		else{
			var spans = FCK.EditorDocument.getElementsByTagName("span");
			for (var index = 0; index < spans.length; index++) {
				var oSpan = spans[index];
				if (oSpan.getAttribute('_trscomment',2)&&oSpan.id == sSpanId) {
					oSpan.style.backgroundColor = _sBgColor;
				}
			}
		}
		FCK.setCookie('EditorCommentBgColor',_sBgColor);
	}
	FCKComments.FormatHTML = function(_sHTML){
		return _sHTML.replace(/<TRS_COMMENT([^>]*)>((.|\n|\r)*?)<\/TRS_COMMENT>/ig,'<SPAN class="fck_comment" _trscomment="true" contentEditable="false" onresizestart="return false"$1>$2<\/SPAN>');
	}
	FCKFormatEditedHtml.AppendNew(FCKComments.FormatHTML);
	FCKXHtml.TagProcessors['span'] = function( node, htmlNode ){
		if ( htmlNode.getAttribute('_trscomment',2) ){
			node = FCKXHtml.XML.createElement('TRS_COMMENT') ;
			FCKXHtml._AppendAttribute( node, 'id', htmlNode.id ) ;
			FCKXHtml._AppendAttribute( node, 'style', 'background-color:'+htmlNode.style.backgroundColor ) ;
			node = FCKXHtml._AppendChildNodes( node, htmlNode, false ) ;
		}
		else{
			if (htmlNode.innerHTML.length==0) return false;
			FCKXHtml._AppendChildNodes( node, htmlNode, false ) ;
		}
		return node ;
	}
})();
//ContentLink
(function(){
	FCKLang.ContentLinkBtn = FCKLang.ContentLinkBtn || "热词替换";
	FCKLang.ContentUnLinkBtn = FCKLang.ContentUnLinkBtn || "取消当前热词替换";
	FCKLang.ContentLinkDlgTitle = FCKLang.ContentLinkDlgTitle || '热词管理';
	FCKLang.ContentUnLinkDlgTitle = FCKLang.ContentUnLinkDlgTitle || '热词管理';
	FCKCommands.RegisterCommand( 'ContentLink', 
	new FCKDialogCommand( 'ContentLink', FCKLang.ContentLinkDlgTitle,
	editorCfg.basePath+'fck_contentlink.html', 568, 200 ) ) ;
	var oContentLinkItem = new FCKToolbarButton( 'ContentLink', FCKLang.ContentLinkBtn
		,null, null, null, null, 68) ;
	FCKToolbarItems.RegisterItem( 'ContentLink', oContentLinkItem ) ;

	FCKCommands.RegisterCommand( 'ContentUnLink', 
	new FCKDialogCommand( 'ContentUnLink', FCKLang.ContentUnLinkDlgTitle,
	editorCfg.basePath+'fck_contentunlink.html', 568, 200 ) ) ;
	var oContentUnLinkItem = new FCKToolbarButton( 'ContentUnLink', FCKLang.ContentUnLinkBtn
		,null, null, null, null, 67) ;
	FCKToolbarItems.RegisterItem( 'ContentUnLink', oContentUnLinkItem ) ;
	var TRSContentUnLinkCommandProcessor = window.FCKAnchorsProcessor ;
	if(TRSContentUnLinkCommandProcessor)return;
	var TRSContentUnLinkCommandProcessor = FCKDocumentProcessor.AppendNew() ;
	TRSContentUnLinkCommandProcessor.ProcessDocument = function( document ){
		var aLinks = document.getElementsByTagName( 'A' ) ;
		var eLink ,i = aLinks.length - 1 ;
		while ( i >= 0 && ( eLink = aLinks[i--])){
			try{
				if(eLink.getAttribute('name')!='AnchorAddByWCM')continue;
				var sClassName = (eLink.className||'').trim();
				eLink.className = ((sClassName||'') + ' ContentLink').trim();
			}catch(err){}
		}
	}
})();
//DocumentProps
(function(){
	var FCKDocumentProps = window.FCKDocumentProps = {};
	//if(!Ext.isIE)return;
	FCKLang.DocumentPropsLbl = FCKLang.DocumentPropsLbl || "页面属性";
	FCKLang.DocumentPropsDlgTitle = FCKLang.DocumentPropsDlgTitle || '设置页面属性';
	FCKCommands.RegisterCommand( 'DocumentProps', 
	new FCKDialogCommand( 'DocumentProps', FCKLang.DocumentPropsDlgTitle,
	'plugins/documentprops/fck_documentprops.html', 650, 300 ) ) ;
	var oDocumentPropsItem = new FCKToolbarButton( 'DocumentProps', FCKLang.DocumentPropsLbl
		,null, null, null, null, 73) ;
	FCKToolbarItems.RegisterItem( 'DocumentProps', oDocumentPropsItem ) ;
	FCK.GetMyStyleSheet = function(_sStyleTitle){
		for(var i=0; i<FCK.EditorDocument.styleSheets.length; i++){
			if(FCK.EditorDocument.styleSheets[i].title == (_sStyleTitle||"__mystyle__"))
				return FCK.EditorDocument.styleSheets[i];
		}
		return null;
	}
	FCK.TRS_AUTO_ADD = 'TRS_Editor';
	FCK.EnableCustomStyle = false;
	var oMyCookies = FCK.loadCookie() || {};
	FCK.EnableCustomStyle = (oMyCookies['EditorEnableCustomStyle'] == '1');
	FCK.GetHtmlWithCustomStyle = function(_sHtml){
		if(FCK.isBlankContent(_sHtml))return _sHtml;
		//initStyleSelect=3表示不选择样式（关闭样式）
		if(FCK.loadCookie().recordStyleChoice=="1" && FCK.loadCookie().initStyleSelect=="3" || FCK.loadCookie().recordStyleChoice=="0"&& FCK.loadCookie().tempSelect=="3") return _sHtml;
		try{
			var osst = FCK.GetMyStyleSheet();
			if(osst==null)return _sHtml;
			var sRootElementId = FCK.TRS_AUTO_ADD;
			var sCssText = "";
			FCK.JsonCustomStyle = "";

			//对于word粘贴数据保持不变
			var reg = /^<div class=\"TRS_PreAppend\"[\s\S]*?width-trs/img;
			if(reg.test(_sHtml)) _sHtml = _sHtml.replace(/width-trs/ig,"width");
			if(FCK.IsWordFile) {
				return _sHtml;
			}
			if(top && top.getEditorCss){
				sCssText = top.getEditorCss();
				if(sCssText && sCssText != ""){
					return "<DIV class='"+sRootElementId+"'>"
					+ "<style id=_Custom_V6_Style_>\n" + sCssText + "\n"
					+ "</style>\n"
					+ _sHtml + "\n</DIV>";
				}
			}
			return _sHtml;
		}catch(err){
	//		Just skip it.
		}
	}
	//新增编辑器自定义样式
	var FCKEditorCss = window.FCKEditorCss = {};
	//在数据库表中的DOCHTMLDOC如果使用了TRS_Editor样式,则会保存为两层DIV到数据库中；则这里在编辑时，会首先过滤掉第一层div
	FCKEditorCss.PreRender = function(sHTML){
		var oReOuter = new RegExp("^<div class=TRS_Editor>([\s\S]*)",'img');
		if(FCKConfig.AutoAppendStyle && oReOuter.test(sHTML)){
			sHTML = sHTML.replace(oReOuter,'$1');
			sHTML = sHTML.replace(/(.*)<\/div>$/i,'$1');
		}
		return sHTML;
	}
	FCKBeforeStartEditor.AppendNew(FCKEditorCss.PreRender);
	
	//这里会过滤掉第二层div，其中会将DOCHTMLCON内容的TRS_Editor样式保存到sStyleHTML
	var sStyleHTML = "";
	FCKDocumentProps.PreRender = function(sHTML){
		var oReOuter = new RegExp("^<div id='"+FCK.TRS_AUTO_ADD+"'>(.*)<\\/div>$",'img');
		sHTML = sHTML.replace(oReOuter,'$1');
		var nStartPos = sHTML.indexOf("<style id=_Custom_V6_Style_>"), nEndPos = -1;
		if( nStartPos >= 0 ){
			nEndPos = sHTML.indexOf("</style>", nStartPos);			
		}
		if( nEndPos > 0 ){
			//在这里获取到了TRS_Editor样式，添加到样式文件中
			sStyleHTML = sHTML.substring(nStartPos + "<style id=_Custom_V6_Style_>".length, nEndPos);
			sHTML = sHTML.substring(nEndPos + "</style>".length);
			return sHTML.substring(0,sHTML.length-'</DIV>'.length);	
		}
		return sHTML;
	}
	FCKBeforeStartEditor.AppendNew(FCKDocumentProps.PreRender);
	
	//自动隐藏style标记
	var FCKWordStyle = window.FCKWordStyle = {};
	FCKWordStyle.PreRender = function(sHTML){
		//只试用与word粘贴
		var oReOuter = new RegExp("^<div class=TRS_PreAppend>([\s\S]*)",'img');
		if(!(oReOuter.test(sHTML) && sHTML.indexOf("TRS_PreExcel") < 0) ){
			return SHTML;
		}
		var oReOuter = /<style [^>]*>([\s\S]*?)<\/style>/img;		
		var sWordStyle = oReOuter.exec(sHTML);
		if(sWordStyle != null){
			FCK.WordStyle = sWordStyle[0].replace(oReOuter,"$1");
		}
		sHTML = sHTML.replace(oReOuter,'');
		return sHTML;
	}
	FCKBeforeStartEditor.AppendNew(FCKWordStyle.PreRender);
	var item = {
		"line-height":"1.4",
		"font-family":"宋体",
		"font-size":"10.5pt",
		"margin-top":"1em",
		"margin-bottom":"0"
	};
	FCK.BasicCustomStyle = {
		"" : Ext.apply({}, item),
		"p" :Ext.apply({}, item),
		"td" : Ext.apply({}, item),
		"div" :Ext.apply({}, item),
		"li" : Ext.apply({}, item)
	};
	window.FCKEditingArea_CompleteStart = function (){
		if ( !this.document.body ){
			this.setTimeout( FCKEditingArea_CompleteStart, 50 ) ;
			return ;
		}
		var oEditorArea = this._FCKEditingArea ;
		oEditorArea.MakeEditable() ;
		FCKTools.RunFunction( oEditorArea.OnLoad ) ;
		//新增设置word样式
		FCKTools.RunFunction( function(){
			FCK.SetWordStyle(FCK.WordStyle);
			FCK.SetFormatStyle(FCK.sFormatStyle);
		});		
	}
	FCK.InitStyleSheet = function(_sStyle){
		var nStartPos = _sStyle.indexOf("/**---JSON--"), nEndPos = -1;
		if( nStartPos >= 0 ){
			nEndPos = _sStyle.indexOf("--**/", nStartPos);			
		}
		if( nEndPos <= 0 )return;
		FCK.JsonCustomStyle = _sStyle.substring(nStartPos + "/**---JSON--".length, nEndPos);
		try{
			eval('FCK.CustomStyle = '+FCK.JsonCustomStyle);
		}catch(err){
			FCK.CustomStyle = null;
		}
	}
	FCK._transObj2Str = function(_obj){
		if(!_obj)return '""';
		if(Ext.isString(_obj)){
			return '"' + _obj + '"';
		}
		var retVal = "{";
		var bFirst = true;
		for(var sName in _obj){
			if(_obj[sName]==null)continue;
			if(!bFirst)retVal += ',';
			retVal += '"' + sName + '":' + FCK._transObj2Str(_obj[sName]);
			bFirst = false;
		}
		retVal += '}';
		return retVal;
	}
	window._FCK_GetEditorAreaStyleTags = function(){
		var sTags = '' ;
		var aCSSs = FCKConfig.EditorAreaCSS ;
		//修正新建页面404错误
		for ( var i = 0 ; i < aCSSs.length ; i++ )
		{
			if(aCSSs[i] == undefined) continue;
			sTags += '<link href="' + aCSSs[i] + '" rel="stylesheet" type="text/css">' ;
		}
		sTags += '<style title="__mystyle__" type="text/css" _rid="'+(new Date().getTime())+'"></style>';
		sTags += '<style title="__mystyle__curr__" type="text/css"></style>';
		sTags += '<style title="__wordstyle__" type="text/css" _rid="'+(new Date().getTime())+'"></style>';
		sTags += '<style title="__formatstyle__" type="text/css" _rid="'+(new Date().getTime())+'"></style>';
		return sTags ;
	}

	FCK.SetWordStyle = function(style){	
		try{
			for(var i=0; i<FCK.EditorDocument.styleSheets.length; i++){
				if(FCK.EditorDocument.styleSheets[i].title == "__wordstyle__"){
					FCK.EditorDocument.styleSheets[i].cssText = style;
					break;
				}
			}
		}catch(ex){
		}
	}
	FCK.SetFormatStyle = function(style){
		try{
			for(var i=0; i<FCK.EditorDocument.styleSheets.length; i++){
				if(FCK.EditorDocument.styleSheets[i].title == "__formatstyle__"){
					FCK.EditorDocument.styleSheets[i].cssText = style;
					break;
				}
			}
		}catch(ex){
		}
	}

	FCK.SetCustomStyleWithString= function(strStyle){	
		if(!strStyle) return;
		try{
			var osst = FCK.GetMyStyleSheet();//__mystyle__
			osst.cssText = strStyle;

			var ocrsst = FCK.GetMyStyleSheet('__mystyle__curr__');
			strStyle = strStyle.replace(/\.TRS_Editor/ig,"BODY");
			ocrsst.cssText = strStyle;
		}catch(ex){
		}
	}

	FCK.SetCustomStyle = function(_oStyles,strCurrStyle){
		try{
			var osst = FCK.GetMyStyleSheet();
			var ocrsst = FCK.GetMyStyleSheet('__mystyle__curr__');
			if(!(osst!=null&&ocrsst!=null&&_oStyles))return;
			FCK.CustomStyle = _oStyles;
			FCK.JsonCustomStyle = strCurrStyle||FCK._transObj2Str(_oStyles);
			var ownEle = osst.owningElement || osst.ownerNode;
			var sRandomId = ownEle.getAttribute('_rid',2);
			var sRootElementId = FCK.TRS_AUTO_ADD;
			var sCssText = "";
			for(var n=(osst.rules||osst.cssRules).length-1;n>=0;n--){
				if(Ext.isIE)osst.removeRule(n);
				else osst.deleteRule(n);
			}
			for(var n=(ocrsst.rules||ocrsst.cssRules).length-1;n>=0;n--){
				if(Ext.isIE)ocrsst.removeRule(n);
				else ocrsst.deleteRule(n);
			}
			var idx = 0;
			for(var sRuleName in _oStyles){
				var oStyle = _oStyles[sRuleName];
				sCssText = "";
				for(var sName in oStyle){
					sCssText += sName + ":" + oStyle[sName] + ";" ;
				}
				if(Ext.isIE){
					osst.addRule("."+sRootElementId+" "+sRuleName,sCssText);
					ocrsst.addRule("body " + sRuleName,sCssText);
				}
				else{
					osst.insertRule("."+sRootElementId+" "+sRuleName + "{" + sCssText+"}", idx);
					ocrsst.insertRule("body " + sRuleName + "{" + sCssText+"}", idx++);
				}
			}
		}finally{
			FCK.EnableCustomStyle = true;
		}
	}
	FCK.DisableCustomStyle = function(){
		try{
			var osst = FCK.GetMyStyleSheet();
			var ocrsst = FCK.GetMyStyleSheet('__mystyle__curr__');
			if(!(osst!=null&&ocrsst!=null))return;
			for(var n=osst.rules.length-1;n>=0;n--){
				osst.removeRule(n);
			}
			for(var n=ocrsst.rules.length-1;n>=0;n--){
				ocrsst.removeRule(n);
			}
		}finally{
			FCK.EnableCustomStyle = false;
		}
	}
	FCK.GetHTML	= FCK.GetXHTML = function( format , _bGetAll){
		return FCK.GetHTML_ENTITY( format , _bGetAll,true);
	}
	FCK.GetHTML_ENTITY = function( format , _bGetAll,_bClear){
		// We assume that if the user is in source editing, the editor value must
		// represent the exact contents of the source, as the user wanted it to be.
		if ( FCK.EditMode == FCK_EDITMODE_SOURCE )
				return FCK.EditingArea.Textarea.value ;
		this.FixBody() ;
		var sXHTML ;
		var oDoc = FCK.EditorDocument ;
		if ( !oDoc )
			return null ;
		if ( FCKConfig.FullPage ){
			sXHTML = FCKXHtml.GetXHTML( oDoc.getElementsByTagName( 'html' )[0], true, format ) ;
			if ( FCK.DocTypeDeclaration && FCK.DocTypeDeclaration.length > 0 )
				sXHTML = FCK.DocTypeDeclaration + '\n' + sXHTML ;
			if ( FCK.XmlDeclaration && FCK.XmlDeclaration.length > 0 )
				sXHTML = FCK.XmlDeclaration + '\n' + sXHTML ;
		}
		else{
			//修正插入分页符时的多余p问题
			sXHTML = FCKXHtml.GetXHTML( oDoc.body, false, format ) ;
			//插入wordStyle样式
			if(!FCK.isBlankContent(sXHTML) && _bGetAll){
				try{
					for(var i=0; i<FCK.EditorDocument.styleSheets.length; i++){
						if(FCK.EditorDocument.styleSheets[i].title == "__wordstyle__" && FCK.EditorDocument.styleSheets[i].cssText.trim() != "" && !FCK.UnitStyle){
							sXHTML = "<style type=\"text/css\">\n" + FCK.EditorDocument.styleSheets[i].cssText + "\n</style>\n" + sXHTML;
							break;
						}
					}
					for(var i=0; i<FCK.EditorDocument.styleSheets.length; i++){
						if(FCK.EditorDocument.styleSheets[i].title == "__formatstyle__" && FCK.EditorDocument.styleSheets[i].cssText.trim() != "" && !FCK.UnitStyle){
							sXHTML = "<style type=\"text/css\">\n" + FCK.EditorDocument.styleSheets[i].cssText + "\n</style>\n" + sXHTML;
							break;
						}
					}

					
				}catch(err){
					//skip It
				}
			}
			if ( FCKConfig.IgnoreEmptyParagraphValue && FCKRegexLib.EmptyOutParagraph.test( sXHTML ) )
				sXHTML = '' ;
		}
		sXHTML = sXHTML.replace(/<p>(\s)*<trs_page_separator([^>]*)>((.|\n|\r)*?)<\/trs_page_separator>(\s)*<\/p>/ig,"<trs_page_separator><\/trs_page_separator>");
		// Restore protected attributes.
		sXHTML = FCK.ProtectEventsRestore( sXHTML ) ;
		if ( FCKBrowserInfo.IsIE )
			sXHTML = sXHTML.replace( FCKRegexLib.ToReplace, '$1' ) ;
		sXHTML = FCKConfig.ProtectedSource.Revert( sXHTML ,_bClear) ;
		if(_bGetAll=='both'){
			return [FCK.GetHtmlWithCustomStyle(sXHTML),sXHTML];
		}
		else if(_bGetAll)
			sXHTML = FCK.GetHtmlWithCustomStyle(sXHTML);
		return sXHTML;
	}
	FCK.GetHTML_FORCHECK = function( format , _bGetAll){
		return FCK.GetHTML_ENTITY( format , _bGetAll,false);
	}
})();
//FlashLib
(function(){
	FCKLang.FlashLibBtn = FCKLang.FlashLibBtn || "插入视频库视频";
	FCKLang.FlashLibDlgTitle = FCKLang.FlashLibDlgTitle || '从视频库选择视频插入';
	//FlashLib
	FCKCommands.RegisterCommand( 'FlashLib', 
	new FCKDialogCommand( 'FlashLib', FCKLang.FlashLibDlgTitle,
	editorCfg.basePath+'../video/fck_videolib.jsp',  950, 660) ) ;
	var oFlashLibItem = new FCKToolbarButton( 'FlashLib', FCKLang.FlashLibBtn
		,null, null, null, null, 75) ;
	FCKToolbarItems.RegisterItem( 'FlashLib', oFlashLibItem ) ;
})();
//InlineLinks
(function(){
	// 修改插入内联文档，当在A元素内时不可用
	FCKLang.InlineLinkDlgTitle = FCKLang.InlineLinkDlgTitle || "选择内联文档";
	FCKLang.InlineLinkLbl = FCKLang.InlineLinkLbl || '插入内联文档链接';
	var FCKInlineLinkCommand = function(){
		this.name ="InlineLink"; 
	}
	FCKInlineLinkCommand.prototype.Execute=function(){
		FCKDialog.OpenDialog('FCKDialog_InlineLink',FCKLang.InlineLinkDlgTitle,'plugins/inlinelinks/document_list.html',880, 400 );
	}
	FCKInlineLinkCommand.prototype.GetState =function(){
		var A=FCK.GetNamedCommandState(this.name);
		var B=null;
		try{
			if(Ext.isIE)
				B=FCK.EditorDocument.selection.createRange().htmlText;
			else 
				B=FCK.EditorWindow.getSelection()
		}catch(ex){
		}
		if(!(B && B.length )) return 0;
		if (A==0&&FCK.EditMode==0){if(FCKSelection.HasAncestorNode('A')) A=-1;else A=0;}return A;
	}
	FCKCommands.RegisterCommand( 'InlineLink', new FCKInlineLinkCommand()) ;
	var oInlineLinkItem = new FCKToolbarButton('InlineLink',FCKLang.InlineLinkLbl
		,null, null, false, true, 80) ;
	FCKToolbarItems.RegisterItem( 'InlineLink', oInlineLinkItem ) ;
})();
//OfficeDoc
(function(){
	FCKLang.OfficeDocLbl = FCKLang.OfficeDocLbl || "从WORD文件中抽取";
	FCKLang.OfficeDocDlgTitle = FCKLang.OfficeDocDlgTitle ||  "从WORD文件中抽取内容";
	FCKCommands.RegisterCommand( 'OfficeDoc', 
	new FCKDialogCommand( 'OfficeDoc', FCKLang.OfficeDocDlgTitle,
	'plugins/officedoc/fck_officedoc.html', 400, 120 ) ) ;
	var oOfficeDocItem = new FCKToolbarButton('OfficeDoc',FCKLang.OfficeDocLbl, 
	null,null,false,true,11);
	FCKToolbarItems.RegisterItem( 'OfficeDoc', oOfficeDocItem ) ;
})();
//OptionalToolbar
(function(){
	FCKLang.WCM6ToolbarBtn = FCKLang.WCM6ToolbarBtn || "切换到简易工具栏";
	FCKLang.WCM6AdvToolbarBtn = FCKLang.WCM6AdvToolbarBtn || "切换到高级工具栏";
	var TRSOptionalToolbarCommand = function(_sName){
		this.ToolBarName = _sName ;
		this.Name = 'TRSOptionalToolbar' ;
	}
	TRSOptionalToolbarCommand.prototype.Execute = function(){
		FCK.ToolbarSet.Load(this.ToolBarName) ;
		var bIsAdvanced = 'WCM6_ADV'.equalsI(this.ToolBarName);
		$('xToolbarRow').className = (bIsAdvanced) ? 'layout_north editor_toolbar_adv'
					: 'layout_north editor_toolbar';
		$('editor_body').className = (bIsAdvanced) ? 'layout_center_container layout_center_container2'
					: 'layout_center_container';
		$('container').className = (bIsAdvanced) ? 'layout_container layout_container2'
					: 'layout_container';
		$('xToolbar').className = (bIsAdvanced) ? 'TB_ToolbarSet xToolbar_adv'
										: 'TB_ToolbarSet xToolbar';
	}
	TRSOptionalToolbarCommand.prototype.GetState = function(){
		return FCK_TRISTATE_OFF ;
	}
	FCKCommands.RegisterCommand( 'WCM6Toolbar', new TRSOptionalToolbarCommand('WCM6')) ;
	var oOptionalToolbarItem = new FCKToolbarButton( 'WCM6Toolbar', FCKLang.WCM6ToolbarBtn
		,null, null, null, null, 82) ;
	FCKToolbarItems.RegisterItem( 'WCM6Toolbar', oOptionalToolbarItem ) ;

	FCKCommands.RegisterCommand( 'WCM6AdvToolbar', new TRSOptionalToolbarCommand('WCM6_ADV')) ;
	var oOptionalToolbarItem = new FCKToolbarButton( 'WCM6AdvToolbar', FCKLang.WCM6AdvToolbarBtn
		,null, null, null, null, 81) ;
	FCKToolbarItems.RegisterItem( 'WCM6AdvToolbar', oOptionalToolbarItem ) ;
})();
//ImportFlash
(function(){
	FCKLang.ImportFlash = FCKLang.ImportFlash || "源码方式引入外部视频";
	//PhotoLib
	FCKCommands.RegisterCommand( 'ImportFlash', 
	new FCKDialogCommand( 'ImportFlash', FCKLang.ImportFlash,
	'../editor/dialog/fck_importFlash.html',400, 330 ) ) ;
	var oPhotoLibItem = new FCKToolbarButton( 'ImportFlash', FCKLang.ImportFlash
		,null, null, null, null, 38) ;
	FCKToolbarItems.RegisterItem( 'ImportFlash', oPhotoLibItem ) ;
})();
//ImageLib
(function(){
	FCKLang.PhotoLibBtn = FCKLang.PhotoLibBtn || "插入图片库图片";
	FCKLang.PhotoLibDlgTitle = FCKLang.PhotoLibDlgTitle || '从图片库选择图片插入';
	//PhotoLib
	FCKCommands.RegisterCommand( 'PhotoLib', 
	new FCKDialogCommand( 'PhotoLib', FCKLang.PhotoLibDlgTitle,
	editorCfg.basePath+'fck_photolib.jsp', 950, 660 ) ) ;
	var oPhotoLibItem = new FCKToolbarButton( 'PhotoLib', FCKLang.PhotoLibBtn
		,null, null, null, null, 79) ;
	FCKToolbarItems.RegisterItem( 'PhotoLib', oPhotoLibItem ) ;
})();
//CKMSpell
(function(){
	FCKLang.TrsCKMBtn =FCKLang.TrsCKMBtn ||  "CKM拼写检查";
	var CKMSpellCheckCommand = function(){
		this.Name = 'CKMSpellCheck' ;
	}
	CKMSpellCheckCommand.prototype.Execute = function(){
		try{
			myActualTop.checkWordSpell('SpellCheck');
		}catch(err){
			//Just Skip it.
		}
	}
	CKMSpellCheckCommand.prototype.GetState = function(){
		return FCK_TRISTATE_OFF;
	}
	FCKCommands.RegisterCommand( 'CKMSpellCheck', new CKMSpellCheckCommand()) ;
	var oTrsCKMItem = new FCKToolbarButton( 'CKMSpellCheck', FCKLang.TrsCKMBtn
		,null, null, null, null, 76) ;
	FCKToolbarItems.RegisterItem( 'CKMSpellCheck', oTrsCKMItem ) ;
})();
//InsertSepTitle
(function(){
	FCKLang.TrsInsertSepTitleBtn =FCKLang.TrsInsertSepTitleBtn ||  "设置分页标题";
	var InsertSepTitleCommand = function(){
		this.Name = "InsertSepTitle";
	}	
	FCKCommands.RegisterCommand( "InsertSepTitle", new FCKDialogCommand( 'InsertSepTitle', FCKLang.TrsInsertSepTitleBtn,
	'plugins/pagetitle/setpagetitle.html', 500, 300 ));

	var oInsertSepTitle = new FCKToolbarButton( 'InsertSepTitle', FCKLang.TrsInsertSepTitleBtn
		,null, null, null, null, 51) ;
	FCKToolbarItems.RegisterItem( 'InsertSepTitle', oInsertSepTitle ) ;

})();
//系统设置
(function(){
	FCKLang.SysConfigTitle = FCKLang.SysConfigTitle || '系统配置';
	//SysConfig
	FCKCommands.RegisterCommand( 'SysConfig', 
	new FCKDialogCommand( 'SysConfig', FCKLang.SysConfigTitle,
	'plugins/sysconfig/fck_sysconfig.html',  450, 80) ) ;
	var oSysConfigItem = new FCKToolbarButton( 'SysConfig', FCKLang.SysConfigTitle
		,null, null, null, null, null) ;
	FCKToolbarItems.RegisterItem( 'SysConfig', oSysConfigItem ) ;
})();

//修正对特殊快替换时增加一个空行问题
FCKXHtml.GetXHTML=function(A,B,C){
	FCKXHtmlEntities.Initialize();
	this._NbspEntity=(FCKConfig.ProcessHTMLEntities?'nbsp':'#160');
	var D=FCK.IsDirty();this._CreateNode=FCKConfig.ForceStrongEm?FCKXHtml_CreateNode_StrongEm:FCKXHtml_CreateNode_Normal;FCKXHtml.SpecialBlocks=[];
	this.XML=FCKTools.CreateXmlObject('DOMDocument');
	this.MainNode=this.XML.appendChild(this.XML.createElement('xhtml'));
	FCKXHtml.CurrentJobNum++;
	if (B) this._AppendNode(this.MainNode,A);
	else this._AppendChildNodes(this.MainNode,A,false);
	var E=this._GetMainXmlString();
	this.XML=null;E=E.substr(7,E.length-15).Trim();
	if (FCKBrowserInfo.IsGecko) E=E.replace(/<br\/>$/,'');E=E.replace(FCKRegexLib.SpaceNoClose,' />');
	if (FCKConfig.ForceSimpleAmpersand) E=E.replace(FCKRegexLib.ForceSimpleAmpersand,'&');
	if (C) E=FCKCodeFormatter.Format(E);
	for (var i=0;i<FCKXHtml.SpecialBlocks.length;i++){
		var F=new RegExp('___FCKsi___'+i);
		E=E.replace(F,FCKXHtml.SpecialBlocks[i].replace(/^\s*/img,"\n"));
	};
	E=E.replace(FCKRegexLib.GeckoEntitiesMarker,'&');if (!D) FCK.ResetIsDirty();
	return E;
};

//字数统计
(function(){
	FCKLang.LETTERSTATSTIC = FCKLang.LETTERSTATSTIC || '字数统计';
	FCKCommands.RegisterCommand( 'Static', 
	new FCKDialogCommand( 'Static', FCKLang.LETTERSTATSTIC,
	'plugins/static/fck_static.html',  200, 200) ) ;
	var oSysConfigItem = new FCKToolbarButton( 'Static', FCKLang.LETTERSTATSTIC
		,null, null, null, null, null) ;
	FCKToolbarItems.RegisterItem( 'Static', oSysConfigItem ) ;
})();

//纯文本粘贴，含图
(function(){
	var PasteTextPicCommand = function(){
		this.Name='PasteTextPic';
	}
    //实际执行体，切忌不要放在上面声明的空对象中。
	PasteTextPicCommand.prototype.Execute = function(){
		PasteAsPlainText();
	}
	PasteTextPicCommand.prototype.GetState = function(){
		return FCK_TRISTATE_OFF;
	}
    //工具栏项绑定函数体
	FCKCommands.RegisterCommand( 'PasteTextPic', new PasteTextPicCommand());
	//下面绑定方式和上例一致
	var oPasteTextPicItem = new FCKToolbarButton( 'PasteTextPic', FCKLang.TIP10
		,null, null, null, null, null) ;
	FCKToolbarItems.RegisterItem( 'PasteTextPic', oPasteTextPicItem ) ;
})();
(function(){
	if (FCK.EditorDocument){
		Event.observe(FCK.EditorDocument.body, 'keydown', function(e){
			if(FCK.EditorWindow.event){
				e= FCK.EditorWindow.event;
				keynum = e.keyCode
			}
			else if(e.which){
				keynum = e.which
			}
			if(keynum==8 || keynum==46){
				FCKUndo.SaveUndoStep();
			}
		});
	}
	else 
		setTimeout(arguments.callee,100);
})();