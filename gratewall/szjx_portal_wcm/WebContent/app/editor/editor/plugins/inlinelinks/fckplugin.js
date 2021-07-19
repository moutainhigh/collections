FCKCommands.RegisterCommand( 'InlineLink', 
new FCKDialogCommand( 'InlineLink', FCKLang.InlineLinkDlgTitle,
FCKPlugins.Items['inlinelinks'].Path +'document_list.html', 800, 420 ) ) ;
var oInlineLinkItem = new FCKToolbarButton('InlineLink',FCKLang.InlineLinkLbl);
oInlineLinkItem.IconPath = FCKPlugins.Items['inlinelinks'].Path + 'inlinelink.gif' ;
FCKToolbarItems.RegisterItem( 'InlineLink', oInlineLinkItem ) ;
