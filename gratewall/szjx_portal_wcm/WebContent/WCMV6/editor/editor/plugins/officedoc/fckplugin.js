FCKCommands.RegisterCommand( 'OfficeDoc', 
new FCKDialogCommand( 'OfficeDoc', FCKLang.OfficeDocDlgTitle,
FCKPlugins.Items['officedoc'].Path +'fck_officedoc.html', 400, 120 ) ) ;
var oOfficeDocItem = new FCKToolbarButton('OfficeDoc',FCKLang.OfficeDocLbl, 
null,null,false,true,11);
FCKToolbarItems.RegisterItem( 'OfficeDoc', oOfficeDocItem ) ;

