var editorCfg = parent.editorCfg || {};
//ContentLink
(function(){
	FCKLang.ContentLinkBtn = FCKLang.ContentLinkBtn || "热词替换";
	FCKLang.ContentUnLinkBtn = FCKLang.ContentUnLinkBtn || "取消当前热词替换";
	FCKLang.ContentLinkDlgTitle = FCKLang.ContentLinkDlgTitle || '热词管理';
	FCKLang.ContentUnLinkDlgTitle = FCKLang.ContentUnLinkDlgTitle || '热词管理';
	FCKCommands.RegisterCommand( 'ContentLink', 
	new FCKDialogCommand( 'ContentLink', FCKLang.ContentLinkDlgTitle,
	editorCfg.basePath+'fck_contentlink.html?CurrDocId=' + m_nDocumentId + '&ChannelId=' + m_nChannelId, 568, 200 ) ) ;
	var oContentLinkItem = new FCKToolbarButton( 'ContentLink', FCKLang.ContentLinkBtn
		,null, null, null, null, 68) ;
	FCKToolbarItems.RegisterItem( 'ContentLink', oContentLinkItem ) ;

	FCKCommands.RegisterCommand( 'ContentUnLink', 
	new FCKDialogCommand( 'ContentUnLink', FCKLang.ContentUnLinkDlgTitle,
	editorCfg.basePath+'fck_contentunlink.html?CurrDocId=' + m_nDocumentId + '&ChannelId=' + m_nChannelId, 568, 200 ) ) ;
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