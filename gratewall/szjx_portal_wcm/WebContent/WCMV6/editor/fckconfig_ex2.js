FCKConfig.SkinPath = FCKConfig.BasePath + 'skins/silver/' ;
FCKConfig.DefaultLanguage		= 'zh-cn' ;
//FCKConfig.ToolbarLocation = 'Out:parent(xToolbar)' ;
FCKConfig.ToolbarCanCollapse = false;
FCKConfig.EditorAreaCSS = [
	FCKConfig.BasePath + 'css/fck_editorarea.css',
	FCKConfig.BasePath + 'css/fck_editorarea_standard.css',
];
FCKConfig.EditorAreaCSSEx =  FCKConfig.BasePath + 'css/fck_editorarea_ex.css';
FCKConfig.ForceStrongEm = false;
FCKConfig.SupportExtract = true;
FCKConfig.FillEmptyBlocks	= true ;
FCKConfig.SingleSOAPApp = false;//wcm52
FCKConfig.RemoveScript = false;
FCKConfig.FullScreenEditor = true;
FCKConfig.ToolbarSets["Default"] = [
	['Source','DocProps','-','Save','NewPage','Preview','-','Templates'],
	['Cut','Copy','Paste','PasteText','PasteWord','-','Print','SpellCheck'],
	['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat'],
	['Form','Checkbox','Radio','TextField','Textarea','Select','Button','ImageButton','HiddenField'],
	'/',
	['Bold','Italic','Underline','StrikeThrough','-','Subscript','Superscript'],
	['OrderedList','UnorderedList','-','Outdent','Indent'],
	['JustifyLeft','JustifyCenter','JustifyRight','JustifyFull'],
	['Link','Unlink','Anchor'],
	['Image','Flash','Video','Table','Rule','Smiley','SpecialChar','PageBreak','UniversalKey'],
	'/',
	['Style','FontFormat','FontName','FontSize'],
	['TextColor','BGColor'],
	['FitWindow','-','About']
] ;

FCKConfig.ToolbarSets["WCM52"] = [
	['Print','Style','FontName','FontSize', /*'LineHeight',*/ /*'Zoom',*/ 'Bold','Italic','Underline','StrikeThrough','RemoveFormat'],
	['Cut','Copy',/*'Delete',*/ 'Paste','PasteText','PasteWord','-','Find','Replace', /*'SpellCheck',*/ '-','Undo','Redo','-','SelectAll','-','TextColor','BGColor','Subscript','Superscript','-', 'JustifyLeft','JustifyCenter','JustifyRight','JustifyFull','-','Link','Unlink','Anchor'/*'RemoveStyle','RefreshResult'*/],
	'/',
	['Image','Flash',/*'Sound',*/'Video','Templates','-',
	'Table',/*'InsertRow','InsertColumn','InsertCell','DeleteRows','DeleteColumns','DeleteCells','MergeCells','SplitCell',*/ '-',
	'OrderedList','UnorderedList','-','Outdent','Indent','-', 'Rule','SpecialChar','Smiley','-', /*'ShowTableBorders','ShowDetails',*/ 'PageBreak'] 
] ;
FCKConfig.ToolbarSets["WCM6"] = [
	[
		'DocumentProps',
		'-',
		'Print',
		'-',
		'Cut','Copy','Paste','PasteText',/*'OfficeDoc',*/'PasteWord',
		'-',
		'Find','Replace',
		'-',
		'Undo','Redo',
		'-',
		'SelectAll',
		'-',
		/*'InlineLink',*/'Link','Unlink',
		'-',
		'Image','PhotoLib','Flash','FlashLib',
		'-',
		'Templates',
		'-',
		'Table','Comment',
		'-',
		/*'ContentLink','ContentUnLink',
		'-',*/
		'Smiley','SpecialChar'
	],
	'/',
	[
		/*'Style',*//*'FontFormat',*/'FontName','FontSize','Bold','Italic','Underline','TextColor','BGColor','RemoveFormat',
		'-',
		'JustifyFull','JustifyCenter','OrderedList','UnorderedList',
		'-',
		'Outdent','Indent',
		'-','PageBreak',/*'AdInTrs',*/'CKMSpellCheck',
		'-',
		'AutoSaveHistory',/*'ClearBlank','OutdentPara','Db2Sb','UnionStyle',*/
		'-',
		'WCM6AdvToolbar'
	]
] ;
FCKConfig.ToolbarSets["WCM6_ADV"] = [
	[
		'DocumentProps',
		'-',
		'Print',/*'SpellCheck',*/
		'-',
		'Cut','Copy','Paste','PasteText',/*'OfficeDoc',*/'PasteWord',
		'-',
		'Find','Replace',
		'-',
		'Undo','Redo',
		'-',
		'SelectAll',
		'-',
		/*'InlineLink',*/'Link','Unlink','Anchor',
		'-',
		'Image','PhotoLib','Flash','FlashLib',
		'-',
		'Templates',
		'-',
		'Table','Comment',
		'-',
		/*'ContentLink','ContentUnLink',
		'-',*/
		'Rule','Smiley','SpecialChar'/*,'UniversalKey'*/
	],
	'/',
	[
		'Style',/*'FontFormat',*/'FontName','FontSize',
		'-',
		'Bold','Italic','Underline','StrikeThrough',
		'-',
		'Subscript','Superscript',
		'-',
		'TextColor','BGColor','RemoveFormat'
	],
	'/',
	[
		'JustifyLeft','JustifyCenter','JustifyRight','JustifyFull',
		'-',
		'OrderedList','UnorderedList',
		'-',
		'Outdent','Indent',
		'-','PageBreak',/*'AdInTrs',*/'CKMSpellCheck',
		'-',
		'AutoSaveHistory',/*'ClearBlank','OutdentPara','Db2Sb','UnionStyle',*/
		'-',
		'WCM6Toolbar'
	]
] ;
FCKConfig.ToolbarSets["Source"] = [
	//['EditSource']
] ;

FCKConfig.ToolbarSets["Accessibility"] = [
	['EditSource','-','Delete','Cut','Copy','Paste','-','SelectAll','RemoveFormat','-','Link','RemoveLink','-','Image','PhotoLib','Flash','FlashLib','Rule','-','About'] ,
	['FontStyle','-','Bold','Italic','Underline','-','InsertOrderedList','InsertUnorderedList','-','Undo','Redo']
] ;

FCKConfig.ContextMenu = ['Generic','Link','Anchor','Image','Flash','Comment',/*'Select','Textarea','Checkbox','Radio','TextField','HiddenField','ImageButton','Button',*/'BulletedList','NumberedList','Table'/*,'Form'*/] ;

FCKConfig.FontNames		= '宋体;新宋体;楷体_GB2312;仿宋_GB2312;黑体;隶书;Arial;Comic Sans MS;Courier New;Tahoma;Times New Roman;Verdana' ;
FCKConfig.FontSizes		= '42pt/初号;36pt/小初;26pt/一号;24pt/小一;22pt/二号;18pt/小二;'
							+'16pt/三号;15pt/小三;14pt/四号;12pt/小四;10.5pt/五号;9pt/小五' ;
FCKConfig.StylesXmlPath		= FCKConfig.EditorPath + 'fckstyles.xml' ;
FCKConfig.TemplatesXmlPath	= FCKConfig.EditorPath + 'fcktemplates.xml' ;
FCKConfig.TemplateReplaceAll = false;
FCKConfig.TemplateReplaceCheckbox = false;

FCKConfig.LinkBrowser = false ;

FCKConfig.ImageBrowser = false ;

FCKConfig.FlashBrowser = false ;

FCKConfig.LinkUpload = false ;

FCKConfig.LinkDlgHideAdvanced = true;
FCKConfig.LinkDlgHideTarget = true;

FCKConfig.ImageUpload = false ;
FCKConfig.ImageDlgHideLink = true;
FCKConfig.ImageDlgHideAdvanced = true;

FCKConfig.FlashUpload = false ;
FCKConfig.FlashDlgHideAdvanced = true;

var actualTop = top.actualTop||top;
var bEnablePicLib = actualTop.bEnablePicLib;
var bEnableFlashLib = actualTop.bEnableFlashLib;
function RemoveToolbar(_sToolbarSetName, _sCmdName){
	var oToolbarSet = FCKConfig.ToolbarSets[_sToolbarSetName];
	if(oToolbarSet){
		for(var i=0;i<oToolbarSet.length;i++){
			var oToolbar = oToolbarSet[i];
			if(oToolbar.constructor == Array){
				for(var j=0;j<oToolbar.length;j++){
					if(oToolbar[j]==_sCmdName){
						oToolbar[j] = null;
					}
				}
			}
		}
	}
}
var sOtherPluginPath = FCKConfig.BasePath.substr(0, FCKConfig.BasePath.length - 7) + 'editor/plugins/' ;
FCKConfig.Plugins.Add( 'contentlink', 'zh-cn', sOtherPluginPath ) ;
FCKConfig.Plugins.Add( 'myimage', 'zh-cn', sOtherPluginPath ) ;
if(bEnablePicLib){
	FCKConfig.Plugins.Add( 'photolib', 'zh-cn', sOtherPluginPath ) ;
}
else{
	RemoveToolbar('WCM6','PhotoLib');
	RemoveToolbar('WCM6_ADV','PhotoLib');
}
if(FCKBrowserInfo.IsIE){
	FCKConfig.Plugins.Add( 'autosavehistory', 'zh-cn', sOtherPluginPath ) ;
}
if(bEnableFlashLib){
	FCKConfig.Plugins.Add( 'flashlib', 'zh-cn', sOtherPluginPath ) ;
}
else{
	RemoveToolbar('WCM6','FlashLib');
	RemoveToolbar('WCM6_ADV','FlashLib');
}
FCKConfig.Plugins.Add( 'optionaltoolbar', 'zh-cn', sOtherPluginPath ) ;
FCKConfig.Plugins.Add( 'documentprops', 'zh-cn', sOtherPluginPath ) ;
FCKConfig.Plugins.Add( 'comment', 'zh-cn', sOtherPluginPath ) ;
FCKConfig.Plugins.Add( 'inlinelinks', 'zh-cn', sOtherPluginPath ) ;
FCKConfig.Plugins.Add( 'officedoc', 'zh-cn', sOtherPluginPath ) ;
//FCKConfig.Plugins.Add( 'trscommands', 'zh-cn', sOtherPluginPath ) ;
var bEnableAdInTrs = actualTop.bEnableAdInTrs || false;
if(bEnableAdInTrs){
	FCKConfig.Plugins.Add( 'adintrs', 'zh-cn', sOtherPluginPath ) ;
}
else{
	RemoveToolbar('WCM6','AdInTrs');
	RemoveToolbar('WCM6_ADV','AdInTrs');
}
var bEnableCKMSpellCheckInTrs = (actualTop.CKMConfig)?(actualTop.CKMConfig['bCKMSpellCheck'] || false):false;
if(bEnableCKMSpellCheckInTrs){
	FCKConfig.Plugins.Add( 'trsckm', 'zh-cn', sOtherPluginPath ) ;
}
else{
	RemoveToolbar('WCM6','CKMSpellCheck');
	RemoveToolbar('WCM6_ADV','CKMSpellCheck');
}
FCKConfig.UseBROnCarriageReturn = (actualTop.window.EnterAs||'br')!='p';
FCKConfig.TabSpaces = actualTop.window.TabSpaces || 4 ;
FCKConfig.AutoDetectPaste = (actualTop.window.enableAutoPaste==null)?true:
actualTop.window.enableAutoPaste;
FCKConfig.DisableEnterKeyHandler = true;