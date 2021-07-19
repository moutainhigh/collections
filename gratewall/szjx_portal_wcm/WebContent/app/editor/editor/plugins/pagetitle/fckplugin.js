FCKCommands.RegisterCommand( 'InsertSepTitle', 
new FCKDialogCommand( 'InsertSepTitle', FCKLang.TrsInsertSepTitleBtn,
FCKPlugins.Items['InsertSepTitle'].Path +'setpagetitle.html', 800, 420 ) ) ;
var oInsertSepTitleItem = new FCKToolbarButton('InsertSepTitle',FCKLang.TrsInsertSepTitleBtn);
FCKToolbarItems.RegisterItem( 'InsertSepTitle', oInsertSepTitleItem ) ;