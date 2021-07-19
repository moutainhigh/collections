//FlashLib
var myActualTop = (top.actualTop||top);
FCKCommands.RegisterCommand( 'FlashLib', 
new FCKDialogCommand( 'FlashLib', FCKLang.FlashLibDlgTitle,
myActualTop.BasePath+'../video/fck_videolib.jsp', 850, 540 ) ) ;
var oFlashLibItem = new FCKToolbarButton( 'FlashLib', FCKLang.FlashLibBtn) ;
oFlashLibItem.IconPath = FCKPlugins.Items['flashlib'].Path + 'flashlib.gif' ;
FCKToolbarItems.RegisterItem( 'FlashLib', oFlashLibItem ) ;
