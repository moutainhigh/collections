var myActualTop = (top.actualTop||top);
//AdInTrsCommand
var CKMSpellCheckCommand = function(){
	this.Name = 'CKMSpellCheck' ;
}
CKMSpellCheckCommand.prototype.Execute = function(){
	try{
		(top.actualTop||top).doCKM('SpellCheck');
	}catch(err){
		//Just Skip it.
	}
}
CKMSpellCheckCommand.prototype.GetState = function(){
	return FCK_TRISTATE_OFF;
}
FCKCommands.RegisterCommand( 'CKMSpellCheck', new CKMSpellCheckCommand()) ;
var oTrsCKMItem = new FCKToolbarButton( 'CKMSpellCheck', FCKLang.TrsCKMBtn) ;
oTrsCKMItem.IconPath = FCKPlugins.Items['trsckm'].Path + 'trsckm.gif' ;
FCKToolbarItems.RegisterItem( 'CKMSpellCheck', oTrsCKMItem ) ;
