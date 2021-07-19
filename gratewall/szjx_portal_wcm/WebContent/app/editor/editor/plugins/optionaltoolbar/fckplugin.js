//MyImage

var TRSOptionalToolbarCommand = function(_sName){
	this.ToolBarName = _sName ;
	this.Name = 'TRSOptionalToolbar' ;
}
TRSOptionalToolbarCommand.prototype.Execute = function(){
	FCK.ToolbarSet.Load(this.ToolBarName) ;
	ToggleToolbar(this.ToolBarName);
}
TRSOptionalToolbarCommand.prototype.GetState = function(){
	return FCK_TRISTATE_OFF ;
}
FCKCommands.RegisterCommand( 'WCM6Toolbar', new TRSOptionalToolbarCommand('WCM6')) ;
var oOptionalToolbarItem = new FCKToolbarButton( 'WCM6Toolbar', FCKLang.WCM6ToolbarBtn) ;
oOptionalToolbarItem.IconPath = FCKPlugins.Items['optionaltoolbar'].Path + 'icon1.gif' ;
FCKToolbarItems.RegisterItem( 'WCM6Toolbar', oOptionalToolbarItem ) ;

FCKCommands.RegisterCommand( 'WCM6AdvToolbar', new TRSOptionalToolbarCommand('WCM6_ADV')) ;
var oOptionalToolbarItem = new FCKToolbarButton( 'WCM6AdvToolbar', FCKLang.WCM6AdvToolbarBtn) ;
oOptionalToolbarItem.IconPath = FCKPlugins.Items['optionaltoolbar'].Path + 'icon2.gif' ;
FCKToolbarItems.RegisterItem( 'WCM6AdvToolbar', oOptionalToolbarItem ) ;
