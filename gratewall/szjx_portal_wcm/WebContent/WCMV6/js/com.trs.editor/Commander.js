$package("com.trs.editor");

com.trs.editor.Commander=Class.create('editor.Commander');
com.trs.editor.Commander.prototype={
	initialize:function(name,id,hint,cmd,type,other){
		this.name=name;
		this.id=id;
		this.hint=hint;
		this.command=cmd;
		this.type=type;//button,checkbox,select,upload-img
		this.other=other;//icon,options

		Event.observe(window, 'unload', function(){
			$destroy(this);
		}.bind(this),false);
		
	},
	toElement:function(_parentElement){
		var div=document.createElement('DIV');
		var caller=this;
		div.id=this.id;
		div.className='Command';
		_parentElement.appendChild(div);
		delete _parentElement;
		switch(this.type)
		{
			case 'button':
				var	img=document.createElement('IMG');
				img.src=com.trs.editor.Config.toolPath+this.other;
				img.title=this.hint;
				img.id='img_'+this.command;
				if(!_IE)img.style.border='1px solid transparent';
				Event.observe(img,'click',function(e){
					eval('com.trs.editor.Command.'+caller.command+'();');
				});
				div.appendChild(img);
				break;
			case 'select':
				var	img=document.createElement('IMG');
				img.src=com.trs.editor.Config.toolPath+this.other;
				img.title=this.hint;
				img.id='img_'+this.command;
				div.appendChild(img);
				if(!_IE)img.style.border='1px solid transparent';
				Event.observe(img,'click',function(e){
					if(!e){e=window.event};
					var x=e.clientX;
					var y=parseInt(e.clientY)+5;
					com.trs.editor.Editor.hideIframes();
					com.trs.editor.Editor.editor.contentWindow.focus();
					var selectedRange = com.trs.editor.Editor.editor.contentWindow.document.selection;
					if (selectedRange){
						var hideDiv=$('Editor_Set_'+this.id.replace(/img_/i,''));
						hideDiv.range = selectedRange.createRange();
						hideDiv.type=selectedRange.type;
						if($log().isDebugEnabled())
						{
							$log().debug("hideDiv.range="+hideDiv.range);
							$log().debug("hideDiv.range.text="+hideDiv.range.text);
							$log().debug("hideDiv.type="+hideDiv.type);
						}
					}
					eval('com.trs.editor.Command.'+caller.command+'(false,'+x+','+y+');');
				}.bind(img));
				var hideDiv=$('Editor_Set_'+this.command);
				if(!hideDiv)
				{
					hideDiv=document.createElement('DIV');
					document.body.appendChild(hideDiv);
					com.trs.editor.Config.iframes.push(hideDiv);
					hideDiv.id='Editor_Set_'+this.command;
					hideDiv.style.display='none';
					hideDiv.style.width=200;
					hideDiv.style.border='1px solid black';
					hideDiv.style.background='#fff';
					hideDiv.innerHTML='<div style="width:100%;background-color:#e4edf3;height:24px;cursor:move" id="Editor_Set_title"'+this.command+'>'+
						'<div style="float:right;padding:2px;cursor:pointer;display:inline;font-size:13px;" title="关闭" id="Editor_Set_close"'+this.command+'><font color="red">X</font></div>'+
						'</div><div style="padding:2px;width:100%" id="Editor_Set_body"'+this.command+'></div>';
					var divs=hideDiv.getElementsByTagName("DIV");
					var hideTitle=divs[0];
					var hideClose=divs[1];
					Event.observe(hideClose.firstChild,'click',function()
					{
						hideDiv.style.display='none';
					});
					new com.trs.drag.Dragger(hideTitle,hideDiv);
					var hideBody=divs[2];
					for(;hideBody.childNodes.length>0;){
						hideBody.removeChild(hideBody.childNodes[0]);
					}
					switch(this.command)
					{
						case 'Font':
							hideBody.innerHTML=com.trs.editor.Config.fontInnerHtml;
							var childs=hideBody.childNodes;
							for(var i=0;i<childs.length;i++)
							{
								if(_IE)childs[i].style.width='100%';
								Event.observe(childs[i],'click',function()
								{
									var font=this.style.fontFamily;
									eval('com.trs.editor.Command.'+caller.command+'(true,"'+font+'");');
								}.bind(childs[i]));
								Event.observe(childs[i],'mouseover',function(){
									this.setAttribute('lastBackgroundColor',this.style.backgroundColor);
									this.style.backgroundColor='#CCCCCC';
								}.bind(childs[i]));
								Event.observe(childs[i],'mouseout',function(){
									this.style.backgroundColor=this.getAttribute('lastBackgroundColor');
								}.bind(childs[i]));
							}
							break;
						case 'FontSize':
							hideBody.innerHTML=com.trs.editor.Config.fontSizeInnerHtml;
							var childs=hideBody.childNodes;
							for(var i=0;i<childs.length;i++)
							{
								if(_IE)childs[i].style.width='100%';
								Event.observe(childs[i],'click',function()
								{
									var index=this.id.replace(/fontsize_/i,'');
									eval('com.trs.editor.Command.'+caller.command+'(true,"'+index+'");');
								}.bind(childs[i]));
								Event.observe(childs[i],'mouseover',function(){
									this.setAttribute('lastBackgroundColor',this.style.backgroundColor);
									this.style.backgroundColor='#CCCCCC';
								}.bind(childs[i]));
								Event.observe(childs[i],'mouseout',function(){
									this.style.backgroundColor=this.getAttribute('lastBackgroundColor');
								}.bind(childs[i]));
							}
							break;
						case 'Style':
							hideBody.innerHTML=com.trs.editor.Config.styleInnerHtml;
							var childs=hideBody.childNodes;
							for(var i=0;i<childs.length;i++)
							{
								if(_IE)childs[i].style.width='100%';
								Event.observe(childs[i],'click',function()
								{
									var _id=this.id;
									_id=_id.replace(/style_/i,'');
									_id='<'+_id+'>';
									eval('com.trs.editor.Command.'+caller.command+'(true,"'+_id+'");');
								}.bind(childs[i]));
								Event.observe(childs[i],'mouseover',function(){
									this.setAttribute('lastBackgroundColor',this.style.backgroundColor);
									this.style.backgroundColor='#CCCCCC';
								}.bind(childs[i]));
								Event.observe(childs[i],'mouseout',function(){
									this.style.backgroundColor=this.getAttribute('lastBackgroundColor');
								}.bind(childs[i]));
							}
							break;
						case 'InsertFace':
							hideBody.innerHTML=com.trs.editor.Config.faceInnerHtml;
							var childs=hideBody.getElementsByTagName("AREA");
							for(var i=0;i<childs.length;i++)
							{
								Event.observe(childs[i],'click',function(){
									var _id=this.id;
									eval('com.trs.editor.Command.'+caller.command+'(true,"'+_id+'");');
								}.bind(childs[i]));
								Event.observe(childs[i],'mouseover',function(){
									this.setAttribute('lastBackgroundColor',this.style.backgroundColor);
									this.style.backgroundColor='#CCCCCC';
								}.bind(childs[i]));
								Event.observe(childs[i],'mouseout',function(){
									this.style.backgroundColor=this.getAttribute('lastBackgroundColor');
								}.bind(childs[i]));
							}
							break;
						case 'ForeColor':
							hideBody.innerHTML=com.trs.editor.Config.foreColorInnerHtml;
							var buttons=hideBody.getElementsByTagName("BUTTON");
							for(var i=0;i<buttons.length;i++)
							{
								Event.observe(buttons[i],'click',function(){
									var color=com.trs.util.CommonHelper.rgb2Color(com.trs.util.CommonHelper.returnColor(this));
									eval('com.trs.editor.Command.'+caller.command+'(true,"'+color+'");');
								}.bind(buttons[i]));
							}
							break;
						case 'BgColor':
							hideBody.innerHTML=com.trs.editor.Config.bgColorInnerHtml;
							var buttons=hideBody.getElementsByTagName("BUTTON");
							for(var i=0;i<buttons.length;i++)
							{
								Event.observe(buttons[i],'click',function(){
									var color=com.trs.util.CommonHelper.rgb2Color(com.trs.util.CommonHelper.returnColor(this));
									eval('com.trs.editor.Command.'+caller.command+'(true,"'+color+'");');
								}.bind(buttons[i]));
							}
							break;
						case 'InsertImg':
							hideBody.innerHTML=com.trs.editor.Config.insertImgInnerHtml;
							var buttons=hideBody.getElementsByTagName("BUTTON");
							for(var i=0;i<buttons.length;i++)
							{
								buttons[i].hideBody=hideBody;
							}
							Event.observe(buttons[0],'click',function()
							{
								var inputs=this.hideBody.getElementsByTagName("INPUT");
								var inputValue=inputs[0].value;
								inputs[0].value='http://';
								if(inputValue.indexOf('://')==-1)
								{
									inputValue='http://'+inputValue;
								}
								eval('com.trs.editor.Command.'+caller.command+'(true,"'+inputValue+'");');
							}.bind(buttons[0]));
							/*Event.observe(hideClose.firstChild,'click',function()
							{
								//var inputs=this.parentNode.parentNode.parentNode.getElementsByTagName("INPUT");
								//inputs[0].value='http://';
								//hideDiv.style.display='none';
							}.bind(hideClose.firstChild)); */
							break;
						case 'InsertTable':
							hideBody.innerHTML=com.trs.editor.Config.insertTableInnerHtml;
							var buttons=hideBody.getElementsByTagName("BUTTON");
							for(var i=0;i<buttons.length;i++)
							{
								buttons[i].hideBody=hideBody;
							}
							Event.observe(buttons[0],'click',function()
							{
								var inputs=this.hideBody.getElementsByTagName("INPUT");
								var selects=hideBody.getElementsByTagName("SELECT");
								var iRows=parseInt(inputs["wordEditer_Table_ROWS"].value);
								var iCols=parseInt(inputs["wordEditer_Table_COLUMNS"].value);
								if(iRows<1||iCols<1)
								{
									$alert('行列数不能小于1,系统设置为缺省值3');
									iRows=3;
									iCols=3;
								}
								else if(iRows*iCols>200)
								{
									$alert('行列数乘积过大将严重影响浏览器资源,系统设置为缺省值3');
									iRows=3;
									iCols=3;
								}
								var widthType = (selects["wordEditer_Table_WIDTHTYPE"].value == "pixels") ? "" : "%";
								var html = '<table border=' + inputs["wordEditer_Table_BORDER"].value + ' cellpadding=' + inputs["wordEditer_Table_PADDING"].value + ' ';
								
								html += 'cellspacing=' + inputs["wordEditer_Table_SPACING"].value + ' width=' + inputs["wordEditer_Table_WIDTH"].value + widthType + '>';
								for (var rows = 0; rows < iRows; rows++) {
									html += "<tr>";
									for (var cols = 0; cols < iCols; cols++) {
										html += "<td>&nbsp;</td>";
									}
									html+= "</tr>";
								}
								html += "</table>";
								eval('com.trs.editor.Command.'+caller.command+'(true,"'+html+'");');
							}.bind(buttons[0]));
							break;
						case 'InsertAnchor':
							hideBody.innerHTML=com.trs.editor.Config.insertAnchorInnerHtml;
							var buttons=hideBody.getElementsByTagName("BUTTON");
							for(var i=0;i<buttons.length;i++)
							{
								buttons[i].hideBody=hideBody;
							}
							Event.observe(buttons[0],'click',function()
							{
								var inputs=this.hideBody.getElementsByTagName("INPUT");
								var t = "_blank";
								var a = inputs["wordEditer_Link_ADS"].value;
								inputs["wordEditer_Link_ADS"].value='http://';
								var v = inputs["wordEditer_Link_TEXT"].value;
								//alert($getText(com.trs.editor.Editor.editor.contentWindow));
								inputs["wordEditer_Link_TEXT"].value='';//$getText(com.trs.editor.Editor.editor.contentWindow);//
								if(a == "") return;
								if(v == "") v = a;
								var html = "<a href='" + a + "' target='" + t + "'>" + v + "</a>";
								eval('com.trs.editor.Command.'+caller.command+'(true,"'+html+'");');
							}.bind(buttons[0]));
							/*Event.observe(hideClose.firstChild,'click',function()
							{
								var inputs=this.parentNode.parentNode.parentNode.getElementsByTagName("INPUT");
								inputs["wordEditer_Link_ADS"].value='http://';
								inputs["wordEditer_Link_TEXT"].value='';
								hideDiv.style.display='none'; 
							}.bind(hideClose.firstChild));*/
							break;
						case 'Config':
							hideBody.innerHTML=com.trs.editor.Config.configInnerHtml;
							var inputs=hideBody.getElementsByTagName("INPUT");
							for(var i=0;i<inputs.length;i++)
							{
								Event.observe(inputs[i],'click',function()
								{
									var checked=this.checked;
									var name=this.id.replace(/c_/i,'');
									eval('com.trs.editor.Command.'+caller.command+'(true,'+checked+',"'+name+'");');
								}.bind(inputs[i]));
							}
							break;
						default:
						
					}
				}
				break;
			case 'checkbox':
				var cb=document.createElement('INPUT');
				cb.type='checkbox';
				cb.id='checkbox_'+this.id;
				Event.observe(div,'click',function(){
					eval('com.trs.editor.Command.'+caller.command+'("'+cb.id+'");');
				});
				cb.checked=this.other;
				div.appendChild(cb);
				//div.innerHTML+=this.hint;
				new Insertion.Bottom(div,this.hint);
				return div;
			case 'upload-img':
				var html='<div style="width:150;padding:1;border:1px black solid;"><form id='+com.trs.editor.ImgUploader.formPre+this.id+' action='+com.trs.editor.ImgUploader.action+
                ' method=post enctype=multipart/form-data style="margin:0;" target='+com.trs.editor.ImgUploader.target+'>'+
                '<INPUT id='+com.trs.editor.ImgUploader.filePathPre+this.id+' value="" type=hidden>'+
				'<INPUT type=hidden value='+this.id+' name='+com.trs.editor.ImgUploader.params["fileKey"]+'>'+
				'<INPUT type=hidden value='+com.trs.editor.ImgUploader.values["uploadTime"]+' name='+com.trs.editor.ImgUploader.params["uploadTime"]+'>';
				html+='<DIV style="width:100%;height:20;padding-top:6px;font-size:13px;text-align:left;padding-left:10px"><button disabled onclick="com.trs.editor.ImgUploader.addArticle('+this.id+');" style="width:80px;font-size:12px" id="btnImg_' + this.id + '">插入图片</button>'+
				'<DIV style="float:right;cursor:pointer;display:inline;" onclick="com.trs.editor.ImgUploader.clearSelect('+this.id+');">'+
				'<IMG height=10 src="'+com.trs.editor.Config.toolPath+'del_btn.gif" width=10 border=0></DIV></DIV>'+
				'<DIV style="background:#ffffff;width:100%;font-size:9pt">'+
				'<center>'+
				'<table width="100%" cellpadding=0 cellspacing=0 border=0><tr><td align=center height=120 id='+com.trs.editor.ImgUploader.picShowPre+this.id+'>'+
				'<SPAN style="COLOR: #7f7f7f;font-size:9pt">图片剪切板</SPAN></td></tr></table></center></DIV>'+
				'<DIV style="height:24;width:100%;padding-left:5px">'+
				'<INPUT id=file_'+this.id+' type=file onchange="com.trs.editor.ImgUploader.initUpload(this,'+this.id+')"; size=8 name=fileContent>'+
				'</DIV></form></div>';
				new Insertion.Bottom(div,html);
				return div;
		}
		Event.observe(div,'mouseover',function(){
			var img=div.childNodes[0];
			img.setAttribute('lastBorder',img.style.border);
			img.setAttribute('lastBackgroundColor',img.style.backgroundColor);
			img.style.border='1px solid #000000';
			img.style.backgroundColor='#CCCCCC';
		});
		Event.observe(div,'mouseout',function(){
			var img=div.childNodes[0];
			img.style.border=img.getAttribute('lastBorder');
			img.style.backgroundColor=img.getAttribute('lastBackgroundColor');
		});
		return div;
	}
}

com.trs.editor.Command={
	Basic:function(command,option,hideDiv){
		if (!com.trs.editor.Config.htmlOn)
		{
			var editor = com.trs.editor.Editor.editor;
			if ((command == 'ForeColor') || (command == 'BackColor') || (command == 'FontName') || (command == 'FormatBlock') || (command == 'FontSize') || (command=='InsertImg'))
			{
				if(!(!_IE&&command=='BackColor'))
				{
					editor.contentWindow.focus();
					selectedRange = editor.contentWindow.document.selection;
					if (selectedRange){
						selectedRange = selectedRange.createRange();
						selectedRange=hideDiv.range;
						selectedRange.select();
					}
					editor.contentWindow.document.execCommand(command, false, option);
					editor.contentWindow.focus();
				}
			}
			else
			{
				editor.contentWindow.focus();
				editor.contentWindow.document.execCommand(command, false, option);
			}
		}
	},
	Basic2:function(command,command_zh){
		try{
			var editor = com.trs.editor.Editor.editor;
			editor.contentWindow.focus();
		editor.contentWindow.document.execCommand(command, false, null);
		}catch(err){
			switch(command){
					case 'cut': keyboard = 'x'; break;
					case 'copy': keyboard = 'c'; break;
					case 'paste': keyboard = 'v'; break;
			}
			$alert('您的浏览器不支持当前操作: \'' + command_zh + '\'. \n请使用快捷键  \(Windows用户:  Ctrl + ' + keyboard + ', Mac用户:  Apple + ' + keyboard + '\)')
		}
	},
	Undo:function(){
		try{
			this.Basic('undo');
		}catch (ex){
			//TODO
			alert(ex.description);
		}

	},
	Redo:function(){
		try{
			this.Basic('redo');
		}catch (ex){
			//TODO
			alert(ex.description);
		}
	},
	Cut:function(){
		this.Basic2('cut','剪切');
	},
	Copy:function(){
		this.Basic2('copy','复制');
	},
	Paste:function(){
		this.Basic2('paste','粘贴');
	},
	B:function(){
		this.Basic('bold');
	},
	I:function(){
		this.Basic('italic');
	},
	U:function(){
		this.Basic('underline');
	},
	Left:function(){
		this.Basic('justifyleft');
	},
	Center:function(){
		this.Basic('justifycenter');
	},
	Right:function(){
		this.Basic('justifyright');
	},
	NoAlign:function(){
		this.Basic('justifyfull');
	},
	InsertImg:function(){
		var hideDiv=$('Editor_Set_InsertImg');
		if(arguments[0])
		{
			var editor = com.trs.editor.Editor.editor;
			var src=arguments[1];
			Element.insertHTML(com.trs.editor.Editor.editor,'<a href="' + src + '" target="_blank"><img width=500 src="'+src+'" border=0 ignore="1"></a>',hideDiv);
			var imageUrl=$(com.trs.editor.ArticleSubmiter.params["image_url"]);
			if(imageUrl)
			{
				imageUrl.value=src;
			}
			hideDiv.style.display='none';
		}
		else
		{
			hideDiv.style.position='absolute';
			hideDiv.style.left=parseInt(arguments[1]);
			hideDiv.style.top=parseInt(arguments[2]);
			hideDiv.style.fontSize='9pt';
			hideDiv.style.width='370px';
			hideDiv.style.paddingBottom='10px';
			hideDiv.style.display='';
			com.trs.editor.Config.components['UploadImg'].toElement(hideDiv);
		}
	},
	InsertAnchor:function(){
		var hideDiv=$('Editor_Set_InsertAnchor');
		if(arguments[0])
		{
			var editor = com.trs.editor.Editor.editor;
			Element.insertHTML(editor,arguments[1],hideDiv);
			hideDiv.style.display='none';
		}
		else
		{
			hideDiv.style.position='absolute';
			hideDiv.style.left=parseInt(arguments[1]);
			hideDiv.style.top=parseInt(arguments[2]);
			hideDiv.style.fontSize='9pt';
			hideDiv.style.width='240px';
			hideDiv.style.display='';
		}
	},
	InsertTable:function(){
		var hideDiv=$('Editor_Set_InsertTable');
		if(arguments[0])
		{
			var editor = com.trs.editor.Editor.editor;
			Element.insertHTML(editor,arguments[1],hideDiv);
			hideDiv.style.display='none';
		}
		else
		{
			hideDiv.style.position='absolute';
			hideDiv.style.left=parseInt(arguments[1]);
			hideDiv.style.top=parseInt(arguments[2]);
			hideDiv.style.fontSize='9pt';
			hideDiv.style.width='240px';
			hideDiv.style.display='';
		}
	},
	InsertFace:function(){
		var hideDiv=$('Editor_Set_InsertFace');
		var args=arguments;
		if(!args[0])
		{
			hideDiv.style.position='absolute';
			hideDiv.style.left=parseInt(args[1]);
			hideDiv.style.top=parseInt(args[2]);
			hideDiv.style.width='280px';
			hideDiv.style.display='';
		}
		else
		{
			var editor = com.trs.editor.Editor.editor;
			Element.insertHTML(editor,'<img src="'+com.trs.editor.Config.facePath + args[1] +'.gif" ignore="1">',hideDiv);
			if(!hideDiv.getAttribute('hold'))
			{
				hideDiv.style.display='none';
			}
		}
	},
	InsertText:function(){
		var hideDiv=$('Editor_Set_InsertText');
		var _sVal;
		var rng = {};
		var oRTE = com.trs.editor.Editor.editor.contentWindow;
		if (document.all) {
			var selection = oRTE.document.selection; 
			if (selection != null) {
				rng = selection.createRange();
			}
		} else {
			var selection = oRTE.getSelection();
			rng = selection.getRangeAt(selection.rangeCount - 1).cloneRange();
			rng.text = rng.toString();
		}
		var text=(rng.text)?rng.text:''; 
		_sVal = text == "" ? "请在文本框输入文字" : text;
		var html = "<table style='border:1px solid #999;width:80%;font-size:12px;' align='center'><tr><td>"+ _sVal +"</td></tr></table>";
		Element.insertHTML(com.trs.editor.Editor.editor,html,hideDiv);
	},
	InsertHr:function(){
		this.Basic("InsertHorizontalRule");
	},
	ForeColor:function(){
		var hideDiv=$('Editor_Set_ForeColor');
		var args=arguments;
		if(!args[0])
		{
			hideDiv.style.position='absolute';
			hideDiv.style.left=parseInt(args[1]);
			hideDiv.style.top=parseInt(args[2]);
			hideDiv.style.width=140;
			hideDiv.style.display='';
		}
		else
		{
			this.Basic("ForeColor", args[1],hideDiv);
			$log().debug("hold="+hideDiv.getAttribute('hold'));
			if(!hideDiv.getAttribute('hold'))
			{
				hideDiv.style.display='none';			
			}
		}
	},
	BgColor:function(){
		var hideDiv=$('Editor_Set_BgColor');
		var args=arguments;
		if(!args[0])
		{
			hideDiv.style.position='absolute';
			hideDiv.style.left=parseInt(args[1]);
			hideDiv.style.top=parseInt(args[2]);
			hideDiv.style.width=140;
			hideDiv.style.display='';
		}
		else
		{
			this.Basic("BackColor", args[1],hideDiv);
			if(!hideDiv.getAttribute('hold'))
			{
				hideDiv.style.display='none';			
			}
		}
	},
	Style:function(){
		var hideDiv=$('Editor_Set_Style');
		var args=arguments;
		if(!args[0])
		{
			hideDiv.style.position='absolute';
			hideDiv.style.left=parseInt(args[1]);
			hideDiv.style.top=parseInt(args[2]);
			hideDiv.style.width=130;
			hideDiv.style.display='';
		}
		else
		{
			this.Basic("FormatBlock", args[1],hideDiv);
			if(!hideDiv.getAttribute('hold'))
			{
				hideDiv.style.display='none';
			}
		}
	},
	Font:function(){
		var hideDiv=$('Editor_Set_Font');
		var args=arguments;
		if(!args[0])
		{
			hideDiv.style.position='absolute';
			hideDiv.style.left=parseInt(args[1]);
			hideDiv.style.top=parseInt(args[2]);
			hideDiv.style.width=130;
			hideDiv.style.display='';
		}
		else
		{
			this.Basic("FontName", args[1],hideDiv);
			if(!hideDiv.getAttribute('hold'))
			{
				hideDiv.style.display='none';
			}
		}
	},
	FontSize:function(){
		var hideDiv=$('Editor_Set_FontSize');
		var args=arguments;
		if(!args[0])
		{
			hideDiv.style.position='absolute';
			hideDiv.style.left=parseInt(args[1]);
			hideDiv.style.top=parseInt(args[2]);
			hideDiv.style.width=130;
			hideDiv.style.display='';
		}
		else
		{
			this.Basic("FontSize", args[1],hideDiv);
			if(!hideDiv.getAttribute('hold'))
			{
				hideDiv.style.display='none';
			}
		}
	},
	OL:function(){
		this.Basic('InsertOrderedList');
	},
	UL:function(){
		this.Basic('InsertUnorderedList');
	},
	Outdent:function(){
		this.Basic('Outdent');
	},
	Indent:function(){
		this.Basic('indent');
	},
	Config:function(){
		var hideDiv=$('Editor_Set_Config');
		var args=arguments;
		if(!args[0])
		{
			hideDiv.style.position='absolute';
			hideDiv.style.left=parseInt(args[1]);
			hideDiv.style.top=parseInt(args[2]);
			hideDiv.style.width=152;
			hideDiv.style.display='';
		}
		else
		{
			if(args[1])
			{
				com.trs.editor.Config.toolbarComponents[args[2]]=args[2];
			}
			if(!args[1])
			{
				com.trs.editor.Config.toolbarComponents[args[2]]='';
			}
			com.trs.editor.Editor.replaceToolBar();
			hideDiv.style.display='none';			
		}
	},
	Source:function(cb_id){
		var cb=$(cb_id);
		com.trs.editor.Editor.hideIframes();
		com.trs.editor.Editor.HTMLView();
		cb.checked=com.trs.editor.Config.htmlOn;
	}
}


function $getText(_contentWindow){
	var oRTE = _contentWindow;
	if (window.pageXOffset) {
		var selection = oRTE.document.selection; 
		if (selection != null) {
			rng = selection.createRange();
		}
	} else {
		var selection = oRTE.getSelection();
		rng = selection.getRangeAt(selection.rangeCount - 1).cloneRange();
		rng.text = rng.toString();
	}

	return rng.text;
}