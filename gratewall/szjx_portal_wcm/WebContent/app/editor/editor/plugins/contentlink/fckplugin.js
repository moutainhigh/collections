//ContentLink
/*
var TRSContentLinkCommand = function(){
	this.Name = 'TRSContentLink' ;
}
TRSContentLinkCommand.prototype.Execute = function(){
	(top.actualTop||top).ManageHotWord();
}
TRSContentLinkCommand.prototype.GetState = function(){
	return FCK_TRISTATE_OFF ;
}
FCKCommands.RegisterCommand( 'ContentLink', new TRSContentLinkCommand()) ;
*/
var myActualTop = (top.actualTop||top);
FCKCommands.RegisterCommand( 'ContentLink', 
new FCKDialogCommand( 'ContentLink', FCKLang.ContentLinkDlgTitle,
myActualTop.BasePath+'fck_contentlink.html', 568, 200 ) ) ;
var oContentLinkItem = new FCKToolbarButton( 'ContentLink', FCKLang.ContentLinkBtn) ;
oContentLinkItem.IconPath = FCKPlugins.Items['contentlink'].Path + 'contentlink.gif' ;
FCKToolbarItems.RegisterItem( 'ContentLink', oContentLinkItem ) ;
/*
//ContentUnLink
var TRSContentUnLinkCommand = function(){
	this.Name = 'TRSContentUnLink' ;
}
TRSContentUnLinkCommand.prototype.Execute = function(){
//	(top.actualTop||top).clearAllHotWord();
	try{
		var eAncestor = FCKSelection.MoveToAncestorNode('A');
		if(eAncestor&&eAncestor.name=='AnchorAddByWCM'){
			FCK.ExecuteNamedCommand( 'Unlink' );
		}
	}catch(err){
	}
}
TRSContentUnLinkCommand.prototype.GetState = function(){
//	return FCK_TRISTATE_OFF;
	try{
		var eAncestor = FCKSelection.MoveToAncestorNode('A');
		if(eAncestor&&eAncestor.name=='AnchorAddByWCM'){
			return FCK_TRISTATE_OFF;
		}
	}catch(err){
	}
	return FCK_TRISTATE_DISABLED ;
}
*/
FCKCommands.RegisterCommand( 'ContentUnLink', 
new FCKDialogCommand( 'ContentUnLink', FCKLang.ContentUnLinkDlgTitle,
myActualTop.BasePath+'fck_contentunlink.html', 568, 200 ) ) ;
var oContentUnLinkItem = new FCKToolbarButton( 'ContentUnLink', FCKLang.ContentUnLinkBtn) ;
oContentUnLinkItem.IconPath = FCKPlugins.Items['contentlink'].Path + 'contentunlink.gif' ;
FCKToolbarItems.RegisterItem( 'ContentUnLink', oContentUnLinkItem ) ;
/*

FCKCommands.RegisterCommand( 'ContentUnLink', new TRSContentUnLinkCommand()) ;
var oContentUnLinkItem = new FCKToolbarButton( 'ContentUnLink', FCKLang.ContentUnLinkBtn) ;
oContentUnLinkItem.IconPath = FCKPlugins.Items['contentlink'].Path + 'contentunlink.gif' ;
oContentUnLinkItem.ContextSensitive = true;
FCKToolbarItems.RegisterItem( 'ContentUnLink', oContentUnLinkItem ) ;
*/
//Implements it in editor_ex.js
var TRSContentUnLinkCommandProcessor = window.FCKAnchorsProcessor ;
if(!TRSContentUnLinkCommandProcessor){
	var TRSContentUnLinkCommandProcessor = FCKDocumentProcessor.AppendNew() ;
	TRSContentUnLinkCommandProcessor.ProcessDocument = function( document )
	{
		var aLinks = document.getElementsByTagName( 'A' ) ;

		var eLink ;
		var i = aLinks.length - 1 ;
		while ( i >= 0 && ( eLink = aLinks[i--])){
			try{
				if(eLink.getAttribute('name')=='AnchorAddByWCM'){
					var sClassName = (eLink.className||'').trim();
					if(sClassName!=''){
						eLink.className = sClassName + ' ContentLink';
					}
					else{
						eLink.className = 'ContentLink';
					}
				}
			}catch(err){}
		}
	}
}
/*
FCKXHtml.TagProcessors['a'] = function( node, htmlNode )
{
	var sSavedUrl = htmlNode.getAttribute( '_fcksavedurl' ) ;
	if ( sSavedUrl != null )
		FCKXHtml._AppendAttribute( node, 'href', sSavedUrl ) ;

	FCKXHtml._AppendChildNodes( node, htmlNode, false ) ;

	// Firefox may create empty tags when deleting the selection in some special cases (SF-BUG 1556878).
	if ( node.childNodes.length == 0 && !node.getAttribute( 'name' ) )
		return false ;

	if(node.getAttribute( 'name' )=='AnchorAddByWCM'){
		var sClassName = (node.getAttribute( 'class' )||'').replace(/(^(\s)*ContentLink\s|\sContentLink\s|\sContentLink(\s)*$|^(\s)*ContentLink(\s)*$)/,' ').trim();
		if(sClassName==''){
			node.removeAttribute( 'class' );
		}
		else{
			node.setAttribute( 'class' ,sClassName);
		}
	}
	
	return node ;
}
*/