//MyImage
/*
FCKCommands.RegisterCommand('MyImage',new FCKDialogCommand('MyImage',FCKLang.DlgMyImageTitle,
FCKPlugins.Items['myimage'].Path + 'myimage.html',450,400)) ;
var oImageItem = new FCKToolbarButton('MyImage',FCKLang.InsertMyImageLbl, FCKLang.InsertMyImage,null,false,true,37);
FCKToolbarItems.RegisterItem('MyImage',oImageItem) ;
*/
var TRSMyImageCommand = function(){
	this.Name = 'TRSMyImage' ;
}
TRSMyImageCommand.prototype.Execute = function(){
	(top.actualTop||top).InsertImage();
}
TRSMyImageCommand.prototype.GetState = function(){
	return FCK_TRISTATE_OFF ;
}
FCKCommands.RegisterCommand( 'MyImage', new TRSMyImageCommand()) ;
var oImageItem = new FCKToolbarButton('MyImage',FCKLang.InsertMyImageLbl, FCKLang.InsertMyImage,null,false,true,37);
FCKToolbarItems.RegisterItem( 'MyImage', oImageItem ) ;

