FCKCommands.RegisterCommand( 'DocumentProps', 
new FCKDialogCommand( 'DocumentProps', FCKLang.DocumentPropsDlgTitle,
FCKPlugins.Items['documentprops'].Path +'fck_documentprops.html', 600, 430 ) ) ;
var oDocumentPropsItem = new FCKToolbarButton( 'DocumentProps', FCKLang.DocumentPropsLbl) ;
oDocumentPropsItem.IconPath = FCKPlugins.Items['documentprops'].Path + 'documentprops.gif' ;
FCKToolbarItems.RegisterItem( 'DocumentProps', oDocumentPropsItem ) ;

//获取自定义的样式表
FCK.GetMyStyleSheet = function(_sStyleTitle){
	for(var i=0; i<FCK.EditorDocument.styleSheets.length; i++){
		if(FCK.EditorDocument.styleSheets[i].title == (_sStyleTitle||"__mystyle__"))
			return FCK.EditorDocument.styleSheets[i];
	}
	return null;
}
FCK.TRS_AUTO_ADD = 'TRS_AUTOADD_';
FCK.EnableCustomStyle = false;
var oMyCookies = FCK.loadCookie();
if(oMyCookies){
	FCK.EnableCustomStyle = (oMyCookies['EditorEnableCustomStyle'] == '1');
}
FCK.GetHtmlWithCustomStyle = function(_sHtml){
	if(FCK.isBlankContent(_sHtml))return _sHtml;
	try{
		if(FCK.EnableCustomStyle&&FCK.JsonCustomStyle){
			var oStyleSheet = FCK.GetMyStyleSheet();
			if(oStyleSheet!=null){
				var sRandomId = oStyleSheet.owningElement.getAttribute('_rid',2);
				var sRootElementId = FCK.TRS_AUTO_ADD + sRandomId;
				var sCssText = oStyleSheet.owningElement.innerHTML;
//				sCssText = sCssText.replace(new RegExp('(#'+sRootElementId+')\\s*{','ig'),'$1 p,$1 td,$1 li,$1 div{');
				return "<DIV id='"+sRootElementId+"'>"
						+ "<style id=_Custom_V6_Style_>\n" + sCssText + "\n"
						+ "/**---JSON--\n"
						+ FCK.JsonCustomStyle
						+ "\n--**/"
						+ "</style>\n"
						+ _sHtml + "\n</DIV>";
			}
		}
	}catch(err){
//		Just skip it.
//		alert(err.message);
	}
	return _sHtml;
}
var FCKDocumentProps = {};
FCKDocumentProps.PreRender = function(sHTML){
	var oReOuter = new RegExp("^<div id='"+FCK.TRS_AUTO_ADD+"\\d+'>(.*)<\\/div>$",'img');
	sHTML = sHTML.replace(oReOuter,'$1');
	var nStartPos = sHTML.indexOf("<style id=_Custom_V6_Style_>"), nEndPos = -1;
	if( nStartPos >= 0 ){
		nEndPos = sHTML.indexOf("</style>", nStartPos);			
	}
	if( nEndPos > 0 ){
		var sStyleHTML = sHTML.substring(nStartPos + "<style id=_Custom_V6_Style_>".length, nEndPos);
		FCK.InitStyleSheet(sStyleHTML);
		sHTML = sHTML.substring(nEndPos + "</style>".length);
		return sHTML.substring(0,sHTML.length-'</DIV>'.length);	
	}
	if(!FCK.CustomStyle){
		var oMyCookies = FCK.loadCookie();
		if(oMyCookies){
			var sCustomStyle = FCK.JsonCustomStyle = oMyCookies['EditorCustomStyle'];
			eval('FCK.CustomStyle = '+oMyCookies['EditorCustomStyle']);
			if(FCK.CustomStyle==false)FCK.CustomStyle = null;
//			FCK.SetCustomStyle(FCK.CustomStyle,sCustomStyle);
		}
	}
	return sHTML;
}
FCKBeforeStartEditor.AppendNew(FCKDocumentProps.PreRender);
function FCKEditingArea_CompleteStart()
{
	// Of Firefox, the DOM takes a little to become available. So we must wait for it in a loop.
	if ( !this.document.body )
	{
		this.setTimeout( FCKEditingArea_CompleteStart, 50 ) ;
		return ;
	}

	var oEditorArea = this._FCKEditingArea ;
	oEditorArea.MakeEditable() ;

	// Fire the "OnLoad" event.
	FCKTools.RunFunction( oEditorArea.OnLoad ) ;
	if(FCK.EnableCustomStyle){
		FCKTools.RunFunction( function(){
			if(FCK.CustomStyle==null){
				FCK.CustomStyle = {
					"":{"line-height":"1.5","font-family":"宋体","font-size":"12pt","margin-top":"0","margin-bottom":"0"},
					"p":{"line-height":"1.5","font-family":"宋体","font-size":"12pt","margin-top":"0","margin-bottom":"0"},
					"td":{"line-height":"1.5","font-family":"宋体","font-size":"12pt","margin-top":"0","margin-bottom":"0"},
					"div":{"line-height":"1.5","font-family":"宋体","font-size":"12pt","margin-top":"0","margin-bottom":"0"},
					"li":{"line-height":"1.5","font-family":"宋体","font-size":"12pt","margin-top":"0","margin-bottom":"0"}
				};
				FCK.JsonCustomStyle = FCK._transObj2Str(FCK.CustomStyle);
			}
			FCK.SetCustomStyle(FCK.CustomStyle,FCK.JsonCustomStyle);
		});
	}
}

FCK.InitStyleSheet = function(_sStyle){
	var nStartPos = _sStyle.indexOf("/**---JSON--"), nEndPos = -1;
	if( nStartPos >= 0 ){
		nEndPos = _sStyle.indexOf("--**/", nStartPos);			
	}
	if( nEndPos > 0 ){
		FCK.JsonCustomStyle = _sStyle.substring(nStartPos + "/**---JSON--".length, nEndPos);
		try{
			eval('FCK.CustomStyle = '+FCK.JsonCustomStyle);
		}catch(err){
			FCK.CustomStyle = null;
		}
	}
//	FCK.SetCustomStyle(FCK.CustomStyle,sCustomStyle);
}
FCK._transObj2Str = function(_obj){
	if(_obj){
		if(String.isString(_obj)){
			return '"' + _obj + '"';
		}
		var retVal = "{";
		var bFirst = true;
		for(var sName in _obj){
			if(_obj[sName]!=null){
				if(!bFirst)retVal += ',';
				retVal += '"' + sName + '":' + FCK._transObj2Str(_obj[sName]);
				bFirst = false;
			}
		}
		retVal += '}';
		return retVal;
	}
	return '""';
}

function _FCK_GetEditorAreaStyleTags(){
	var sTags = '' ;
	var aCSSs = FCKConfig.EditorAreaCSS ;

	for ( var i = 0 ; i < aCSSs.length ; i++ )
		sTags += '<link href="' + aCSSs[i] + '" rel="stylesheet" type="text/css" />' ;
	sTags += '<style title="__mystyle__" _rid="'+(new Date().getTime())+'"></style>';
	sTags += '<style title="__mystyle__curr__"></style>';
	return sTags ;
}

FCK.SetCustomStyle = function(_oStyles,strCurrStyle){
	try{
		var oStyleSheet = FCK.GetMyStyleSheet();
		var oCurrStyleSheet = FCK.GetMyStyleSheet('__mystyle__curr__');
		if(oStyleSheet!=null&&oCurrStyleSheet!=null&&_oStyles){
			FCK.CustomStyle = _oStyles;
			FCK.JsonCustomStyle = strCurrStyle||FCK._transObj2Str(_oStyles);
			var sRandomId = oStyleSheet.owningElement.getAttribute('_rid',2);
			var sRootElementId = FCK.TRS_AUTO_ADD + sRandomId;
			var sCssText = "";
			for(var n=oStyleSheet.rules.length-1;n>=0;n--){
				oStyleSheet.removeRule(n);
			}
			for(var n=oCurrStyleSheet.rules.length-1;n>=0;n--){
				oCurrStyleSheet.removeRule(n);
			}
			for(var sRuleName in _oStyles){
				var oStyle = _oStyles[sRuleName];
				sCssText = "";
				for(var sName in oStyle){
					sCssText += sName + ":" + oStyle[sName] + ";" ;
				}
				oStyleSheet.addRule("."+sRootElementId+" "+sRuleName,sCssText);
				oCurrStyleSheet.addRule("body " + sRuleName,sCssText);
			}
		}
	}catch(err){
	}
	finally{
		FCK.EnableCustomStyle = true;
	}
}
FCK.DisableCustomStyle = function(){
	try{
		var oStyleSheet = FCK.GetMyStyleSheet();
		var oCurrStyleSheet = FCK.GetMyStyleSheet('__mystyle__curr__');
		if(oStyleSheet!=null&&oCurrStyleSheet!=null){
			for(var n=oStyleSheet.rules.length-1;n>=0;n--){
				oStyleSheet.removeRule(n);
			}
			for(var n=oCurrStyleSheet.rules.length-1;n>=0;n--){
				oCurrStyleSheet.removeRule(n);
			}
		}
	}catch(err){
	}
	finally{
		FCK.EnableCustomStyle = false;
	}
}

FCK.GetHTML	= FCK.GetXHTML = function( format , _bGetAll){
	// We assume that if the user is in source editing, the editor value must
	// represent the exact contents of the source, as the user wanted it to be.
	if ( FCK.EditMode == FCK_EDITMODE_SOURCE )
			return FCK.EditingArea.Textarea.value ;

	this.FixBody() ;

	var sXHTML ;
	var oDoc = FCK.EditorDocument ;

	if ( !oDoc )
		return null ;

	if ( FCKConfig.FullPage )
	{
		sXHTML = FCKXHtml.GetXHTML( oDoc.getElementsByTagName( 'html' )[0], true, format ) ;

		if ( FCK.DocTypeDeclaration && FCK.DocTypeDeclaration.length > 0 )
			sXHTML = FCK.DocTypeDeclaration + '\n' + sXHTML ;

		if ( FCK.XmlDeclaration && FCK.XmlDeclaration.length > 0 )
			sXHTML = FCK.XmlDeclaration + '\n' + sXHTML ;
	}
	else
	{
		sXHTML = FCKXHtml.GetXHTML( oDoc.body, false, format ) ;

		if ( FCKConfig.IgnoreEmptyParagraphValue && FCKRegexLib.EmptyOutParagraph.test( sXHTML ) )
			sXHTML = '' ;
	}

	// Restore protected attributes.
	sXHTML = FCK.ProtectEventsRestore( sXHTML ) ;

	if ( FCKBrowserInfo.IsIE )
		sXHTML = sXHTML.replace( FCKRegexLib.ToReplace, '$1' ) ;

	sXHTML = FCKConfig.ProtectedSource.Revert( sXHTML ,true) ;
	if(_bGetAll=='both'){
		return [FCK.GetHtmlWithCustomStyle(sXHTML),sXHTML];
	}
	else if(_bGetAll)
		sXHTML = FCK.GetHtmlWithCustomStyle(sXHTML);
	return sXHTML;
}
