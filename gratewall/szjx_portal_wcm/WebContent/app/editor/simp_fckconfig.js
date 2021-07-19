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
];
FCKConfig.FillEmptyBlocks	= true ;
FCKConfig.RemoveScript = false;
FCKConfig.ToolbarCanCollapse = false;
FCKConfig.ForceStrongEm = false;
FCKConfig.FormatSource = false ;
FCKConfig.ToolbarSets["Default"] =　FCKConfig.ToolbarSets["Title"] = [
	['Bold','Italic','Underline','-','FontName','FontSize','-','TextColor']
] ;
FCKConfig.ToolbarSets["Abstract"] = [
	['Bold','Italic','Underline','-','FontName','FontSize',
	'-','TextColor','BGColor','-','OrderedList','UnorderedList',
	'-','Link','Unlink']
] ;
FCKConfig.ToolbarSets["MetaData"] = [
	['Bold','Italic','Underline','-','FontName','FontSize','-','TextColor','BGColor',
	'-','OrderedList','UnorderedList',
	'-','JustifyLeft','JustifyCenter','JustifyRight','JustifyFull',
	'-','Image','Table','Rule','Smiley','SpecialChar',
	'-','Link','Unlink',
	'ContentLink','ContentUnLink']
] ;
if((FCKURLParams['Toolbar'] || 'Title')=='Title'){
	FCKConfig.ContextMenu = [];
}
else{
	FCKConfig.ContextMenu = ['Generic','Link','BulletedList','NumberedList'] ;
}
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
FCKConfig.CleanWordKeepsStructure = false ;
FCKConfig.FontNames		= '宋体;新宋体;楷体;仿宋;黑体;隶书;Arial;Comic Sans MS;Courier New;Tahoma;Times New Roman;Verdana' ;
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
FCKConfig.SmileyPath	= FCKConfig.BasePath + 'images/smiley/msn/' ;
FCKConfig.SmileyImages	= ['regular_smile.gif','sad_smile.gif','wink_smile.gif','teeth_smile.gif','confused_smile.gif','tounge_smile.gif','embaressed_smile.gif','omg_smile.gif','whatchutalkingabout_smile.gif','angry_smile.gif','angel_smile.gif','shades_smile.gif','devil_smile.gif','cry_smile.gif','lightbulb.gif','thumbs_down.gif','thumbs_up.gif','heart.gif','broken_heart.gif','kiss.gif','envelope.gif'] ;
FCKConfig.SmileyColumns = 8 ;
FCKConfig.SmileyWindowWidth		= 320 ;
FCKConfig.SmileyWindowHeight	= 240 ;
