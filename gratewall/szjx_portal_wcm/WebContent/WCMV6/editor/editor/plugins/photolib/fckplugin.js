//PhotoLib
var myActualTop = (top.actualTop||top);
FCKCommands.RegisterCommand( 'PhotoLib', 
new FCKDialogCommand( 'PhotoLib', FCKLang.PhotoLibDlgTitle,
myActualTop.BasePath+'fck_photolib.jsp', 850, 540 ) ) ;
var oPhotoLibItem = new FCKToolbarButton( 'PhotoLib', FCKLang.PhotoLibBtn) ;
oPhotoLibItem.IconPath = FCKPlugins.Items['photolib'].Path + 'photolib.gif' ;
FCKToolbarItems.RegisterItem( 'PhotoLib', oPhotoLibItem ) ;
