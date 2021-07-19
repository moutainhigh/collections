var myActualTop = (top.actualTop||top);
//AdInTrsCommand
var AdInTrsCommand = function(){
	this.Name = 'AdInTrs' ;
}
AdInTrsCommand.prototype.Execute = function(){
	try{
		(top.actualTop||top).loadTRSAdOption();
	}catch(err){
		//Just Skip it.
	}
}
AdInTrsCommand.prototype.GetState = function(){
	return FCK_TRISTATE_OFF;
}
FCKCommands.RegisterCommand( 'AdInTrs', new AdInTrsCommand()) ;
var oAdInTrsItem = new FCKToolbarButton( 'AdInTrs', FCKLang.AdInTrsBtn) ;
oAdInTrsItem.IconPath = FCKPlugins.Items['adintrs'].Path + 'adintrs.gif' ;
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
AdInTrsProcessor.ProcessDocument = function( document )
{
	var aScripts = document.getElementsByTagName( 'TRSAD_SCRIPT' ) ;
	var i = aScripts.length - 1 ;
	while ( i >= 0 && ( eScript = aScripts[i--])){
//		if(eScript.getAttribute('fck_adintrs', 2)!='1')continue;
		var e = FCK.EditorDocument.createElement('SCRIPT') ;
		for(var j=0,n=eScript.attributes.length;j<n;j++){
			var oAttr = eScript.attributes[j];
			if(!_IE || oAttr.specified){
				e.setAttribute(oAttr.name,eScript.getAttribute(oAttr.name, 2), 0);
			}
		}
		var oFakeImage = FCKDocumentProcessor_CreateFakeImage( 'FCK__AdInTrs', e) ;
		eScript.parentNode.insertBefore( oFakeImage, eScript ) ;
		eScript.parentNode.removeChild( eScript ) ;
	}
}
