FCKConfig.CustomConfigurationsPath = '' ;
FCKConfig.ToolbarComboPreviewCSS = '' ;
FCKConfig.DocType = '' ;
FCKConfig.BaseHref = '' ;
FCKConfig.FullPage = false ;
FCKConfig.Debug = false ;
FCKConfig.AllowQueryStringDebug = true ;
FCKConfig.SkinPath = FCKConfig.BasePath + 'skins/silver/' ;
FCKConfig.PreloadImages = [ FCKConfig.SkinPath + 'images/toolbar.start.gif', FCKConfig.SkinPath + 'images/toolbar.buttonarrow.gif' ] ;
FCKConfig.AutoGrowMax = 400 ;
FCKConfig.AutoDetectLanguage	= true ;
FCKConfig.ContentLangDirection	= 'ltr' ;
FCKConfig.ProcessHTMLEntities	= true ;
FCKConfig.IncludeLatinEntities	= true ;
FCKConfig.IncludeGreekEntities	= true ;
FCKConfig.ProcessNumericEntities = false ;
FCKConfig.AdditionalNumericEntities = ''  ;		// Single Quote: "'"
FCKConfig.FormatSource		= true ;
FCKConfig.FormatOutput		= true ;
FCKConfig.FormatIndentator	= '    ' ;
FCKConfig.GeckoUseSPAN	= false ;
FCKConfig.StartupFocus	= false ;
FCKConfig.ForcePasteAsPlainText	= false ;
FCKConfig.AutoDetectPasteFromWord = true ;	// IE only.
FCKConfig.ForceSimpleAmpersand	= false ;
FCKConfig.ShowBorders	= true ;
FCKConfig.SourcePopup	= false ;
FCKConfig.ToolbarStartExpanded	= true ;
FCKConfig.IgnoreEmptyParagraphValue = true ;
FCKConfig.PreserveSessionOnFileBrowser = false ;
FCKConfig.FloatingPanelsZIndex = 10000 ;
FCKConfig.ToolbarLocation = 'In' ;
FCKConfig.DefaultLanguage = 'zh-cn' ;
FCKConfig.PluginsPath = FCKConfig.BasePath + 'plugins/' ;
//FCKConfig.ToolbarLocation = 'Out:parent(xToolbar)' ;
FCKConfig.ToolbarCanCollapse = false;
FCKConfig.EditorAreaCSS = [
	FCKConfig.BasePath + 'css/fck_editorarea.css',
	FCKConfig.BasePath + 'css/fck_editorarea_standard.css',
	FCKConfig.BasePath + 'css/editor.css'
];
FCKConfig.ForceStrongEm = false;
FCKConfig.SupportExtract = true;
FCKConfig.FillEmptyBlocks	= true ;
FCKConfig.SingleSOAPApp = false;//wcm52
FCKConfig.RemoveScript = false;
FCKConfig.ToolbarSets["WCM52"] = [
	['Print','Style','FontName','FontSize','Bold','Italic','Underline','StrikeThrough','RemoveFormat'],
	['Cut','Copy','Paste','PasteText','-','Find','Replace', '-','Undo','Redo','-','SelectAll','-','TextColor','BGColor','Subscript','Superscript','-', 'JustifyLeft','JustifyCenter','JustifyRight','JustifyFull','-','Anchor'],
	'/',
	['Image','-',
	'Table',/*'InsertRow','InsertColumn','InsertCell','DeleteRows','DeleteColumns','DeleteCells','MergeCells','SplitCell',*/ '-',
	'OrderedList','UnorderedList','-','Outdent','Indent','-', 'Rule','SpecialChar','-', 'PageBreak'] 
] ;
FCKConfig.ToolbarSets["WCM6"] = [
	[
		'-',
		'Print',/*'SpellCheck',*/
		'Cut','Copy','Paste',
		'-',
		'Find','Replace',
		'-',
		'Undo','Redo',
		'-',
		'Image',
		'-',
		'Table','Comment',
		'-',
		'FitWindow','SpecialChar','Static'
	],
	'/',
	[
		'FontName','FontSize','LineHeight','Bold','Italic','Underline','TextColor','BGColor','RemoveFormat',
		'-',
		'JustifyLeft','JustifyFull','JustifyCenter','JustifyRight','OrderedList','UnorderedList',
		'-',
		'Outdent','Indent',
		'-'
	]
] ;
FCKConfig.ToolbarSets["WCM6_ADV"] = [
	[
		'Print',/*'SpellCheck',*/
		'-',
		'Cut','Copy','Paste','PasteText',,
		'-',
		'Find','Replace',
		'-',
		'Undo','Redo',
		'-',
		'SelectAll',
		'-',
		'Anchor',
		'-',
		'Image',
		'-',
		'Templates',
		'-',
		'Table','Comment',
		'-',
		'FitWindow',
		
	],
	'/',
	[
		'Style',/*'FontFormat',*/'FontName','FontSize','LineHeight',
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
		'-',
		'Rule','Smiley','SpecialChar'/*,'UniversalKey'*/,'Static',
		'-','PageBreak','InsertSepTitle','AdInTrs','CKMSpellCheck','AutoSaveHistory',
		'-'
	]
] ;
FCKConfig.EnterMode = 'p' ;			// p | div | br
FCKConfig.ShiftEnterMode = 'br' ;	// p | div | br
FCKConfig.Keystrokes = [
	[ CTRL + 65 /*A*/, true ],
	[ CTRL + 67 /*C*/, true ],
	[ CTRL + 70 /*F*/, true ],
	[ CTRL + 83 /*S*/, true ],
	[ CTRL + 88 /*X*/, true ],
	[ CTRL + 86 /*V*/, 'Paste' ],
	[ SHIFT + 45 /*INS*/, 'Paste' ],
	[ CTRL + 90 /*Z*/, 'Undo' ],
	[ CTRL + 89 /*Y*/, 'Redo' ],
	[ CTRL + SHIFT + 90 /*Z*/, 'Redo' ],
	[ CTRL + 76 /*L*/, 'Link' ],
	[ CTRL + 66 /*B*/, 'Bold' ],
	[ CTRL + 73 /*I*/, 'Italic' ],
	[ CTRL + 85 /*U*/, 'Underline' ],
	[ CTRL + SHIFT + 83 /*S*/, 'Save' ],
	[ CTRL + ALT + 13 /*ENTER*/, 'FitWindow' ],
	[ CTRL + 9 /*TAB*/, 'Source' ],
	[ 9 /*TAB*/, 'TabInsert' ],
	[ 46 /*Delete*/, 'Delete' ]
] ;
FCKConfig.BrowserContextMenuOnCtrl = false ;
FCKConfig.FontColors = '000000,993300,333300,003300,003366,000080,333399,333333,800000,FF6600,808000,808080,008080,0000FF,666699,808080,FF0000,FF9900,99CC00,339966,33CCCC,3366FF,800080,999999,FF00FF,FFCC00,FFFF00,00FF00,00FFFF,00CCFF,993366,C0C0C0,FF99CC,FFCC99,FFFF99,CCFFCC,CCFFFF,99CCFF,CC99FF,FFFFFF' ;
FCKConfig.FontFormats	= 'p;div;pre;address;h1;h2;h3;h4;h5;h6' ;
FCKConfig.MaxUndoLevels = 15 ;
FCKConfig.DisableObjectResizing = false ;
FCKConfig.DisableFFTableHandles = true ;
FCKConfig.ProtectedTags = '' ;
FCKConfig.BodyId = '' ;
FCKConfig.BodyClass = '' ;
FCKConfig.DefaultLinkTarget = '' ;
FCKConfig.CleanWordKeepsStructure = true ;
FCKConfig.ContextMenu = ['Generic','Link','Anchor','Image','Flash','Comment',/*'Select','Textarea','Checkbox','Radio','TextField','HiddenField','ImageButton','Button',*/'BulletedList','NumberedList','Table'/*,'Form'*/] ;
FCKConfig.LineHeights ='50%;100%;125%;150%;175%;200%';
FCKConfig.FontNames		= '宋体;新宋体;楷体,楷体_GB2312;仿宋;黑体;隶书;Arial;Comic Sans MS;Courier New;Tahoma;Times New Roman;Verdana' ;
if(Ext.isIE){
	FCKConfig.FontSizes		= '42pt/初号;36pt/小初;26pt/一号;24pt/小一;22pt/二号;18pt/小二;'
								+'16pt/三号;15pt/小三;14pt/四号;12pt/小四;10.5pt/五号;9pt/小五' ;
}else{
	FCKConfig.FontSizes		= '1/xx-small;2/x-small;3/small;4/medium;5/large;6/x-large;7/xx-large' ;
}
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
FCKConfig.DisableEnterKeyHandler = true;
FCKConfig.TabSpaces = 4 ;
FCKConfig.UseBROnCarriageReturn = true;
FCKConfig.AutoDetectPaste = true;
function RemoveToolbar(_sToolbarSetName, _sCmdName){
	var oToolbarSet = FCKConfig.ToolbarSets[_sToolbarSetName];
	if(!oToolbarSet)return;
	for(var i=0;i<oToolbarSet.length;i++){
		var oToolbar = oToolbarSet[i];
		if(!Array.isArray(oToolbar))continue;
		var result = [];
		for(var j=0;j<oToolbar.length;j++){
			if(oToolbar[j]!=_sCmdName){
				result.push(oToolbar[j]);
			}
		}
		oToolbarSet[i] = result;
	}
}
//放出这两个工具条，不知会有什么影响
//if(!Ext.isIE){
//	RemoveToolbar('WCM6','AutoSaveHistory');
//	RemoveToolbar('WCM6_ADV','AutoSaveHistory');
//	RemoveToolbar('WCM6','DocumentProps');
//	RemoveToolbar('WCM6_ADV','DocumentProps');
//}
//dym
(function(){
	var actualTop = parent.parent;
	var editorCfg = actualTop.editorCfg;
	if(!editorCfg) return;
	
	if(!editorCfg.enablePhotolib){
		//RemoveToolbar('WCM6','PhotoLib');
		//RemoveToolbar('WCM6_ADV','PhotoLib');
	}
	if(!editorCfg.enableFlashlib){
		//RemoveToolbar('WCM6','FlashLib');
		//RemoveToolbar('WCM6_ADV','FlashLib');
	}
	if(!editorCfg.enableAdintrs){
		RemoveToolbar('WCM6','AdInTrs');
		RemoveToolbar('WCM6_ADV','AdInTrs');
	}
	if(!editorCfg.enableCkmspellcheck){
		RemoveToolbar('WCM6','CKMSpellCheck');
		RemoveToolbar('WCM6_ADV','CKMSpellCheck');
	}
	//元数据中用复杂编辑器编辑时不希望显示的按钮。
	if(editorCfg.excludeBtns4MetaData){
		RemoveToolbar('WCM6','FitWindow');
		RemoveToolbar('WCM6_ADV','FitWindow');
	}
	FCKConfig.UseBROnCarriageReturn = editorCfg.enterAs != 'p';
	FCKConfig.TabSpaces = editorCfg.tabSpaces || 4 ;
	FCKConfig.AutoDetectPaste = editorCfg.enableAutoPaste==null || editorCfg.enableAutoPaste;
})();

FCKConfig.SmileyPath	= FCKConfig.BasePath + 'images/smiley/msn/' ;
FCKConfig.SmileyImages	= ['regular_smile.gif','sad_smile.gif','wink_smile.gif','teeth_smile.gif','confused_smile.gif','tounge_smile.gif','embaressed_smile.gif','omg_smile.gif','whatchutalkingabout_smile.gif','angry_smile.gif','angel_smile.gif','shades_smile.gif','devil_smile.gif','cry_smile.gif','lightbulb.gif','thumbs_down.gif','thumbs_up.gif','heart.gif','broken_heart.gif','kiss.gif','envelope.gif'] ;
FCKConfig.SmileyColumns = 8 ;
FCKConfig.SmileyWindowWidth		= 320 ;
FCKConfig.SmileyWindowHeight	= 240 ;
if(FCKBrowserInfo.IsGecko){
	FCKConfig.ToolbarSets["WCM6"][0].remove("ContentLink");
	FCKConfig.ToolbarSets["WCM6_ADV"][0].remove("ContentLink");
	FCKConfig.ToolbarSets["WCM6"][0].remove("ContentUnLink");
	FCKConfig.ToolbarSets["WCM6_ADV"][0].remove("ContentUnLink");

}

//增加粘贴统一样式开关
FCKConfig.PasteToUnionStyle = false;
FCKConfig.UnionStyleName = "cas_content";

//增加自定义样式开关
FCKConfig.AutoAppendStyle = true;

/*系统配置设置区*/
//是否采用剪切板粘贴模式
FCKConfig.ClipBoardMode = false;


/*Word 粘贴设置区*/
//清理字体格式
FCKConfig.IgnoreFont = false;
//清理段落格式
FCKConfig.IgnoreBlock = false;
//去除空行
FCKConfig.RemoveEmpty = false;
//清理内联样式
FCKConfig.RemoveStyles = false;
//去除浮动
FCKConfig.RemovePosition = true;
//启用中文行距
FCKConfig.KeepLineHeight = false;
//移除页宽
FCKConfig.RemovePageWidth = true;
//设定回退步数
FCKUndo.MaxTypes=5;
