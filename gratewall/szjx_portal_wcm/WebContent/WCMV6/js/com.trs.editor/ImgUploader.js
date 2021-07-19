$package("com.trs.editor");

$define('com.trs.editor.ImgUploader');

// ge gfc add @ 2006-8-28 15:08
_ATTR_FLAG_IMG_INSERTED = '点击"插入图片"将此图片插入到光标处';


Object.extend(com.trs.editor.ImgUploader,{
	used:[],
	addArticle:function(_sId)
	{
		var sInsert = $(this.picShowPre+ _sId).innerHTML;
		var src=$(this.filePathPre + _sId).value;
		if (sInsert.indexOf('.')!=-1&&src.length>0) {
			var hideDiv=$('Editor_Set_InsertImg');
			Element.insertHTML(com.trs.editor.Editor.editor,'<a href="' + src + '" target="_blank"><img border=0 src="'+src+'"></a>',hideDiv);
		}
	},
	clearSelect:function(_sId){
		var sOldPath=$(this.filePathPre + _sId).value;
		$(this.picShowPre + _sId).innerHTML ='<SPAN class=Img_ShowPre_Span>图片剪切板</SPAN>';
		$(this.formPre + _sId).title='';
		if (sOldPath.length >0&&this.seekUse(_sId)) {
			for(var i=0;i<this.used[_sId].length;i++)
			{
				var node=this.used[_sId][i].parentNode;
				if(node&&node.tagName=='A')
				{
					node.parentNode.removeChild(node);
					delete node;
				}
				else
				{
					var tmp=node.removeChild(this.used[_sId][i]);
					delete tmp;
				}
			}
		}
		var form=$(this.formPre + _sId);
		form.reset();

		$('btnImg_'+ _sId).disabled = true;
	},
	initUpload:function(_oObj,_sId){
		var sOldPath = $(this.filePathPre + _sId).value;
		$(this.formPre+ _sId).title	= _ATTR_FLAG_IMG_INSERTED;
		$('btnImg_'+ _sId).disabled = false;
		var nSrc='';
		if(!_IE)
		{
			nSrc="file:\\\\" + _oObj.value.replace(/ /ig,"%20");
		}
		else
		{
			nSrc="file:///" + _oObj.value.replace(/\\/ig, "/").replace(/ /ig,"%20");
		}
		var img=com.trs.util.CommonHelper.loadIMG(nSrc,{scale:true,width:120,height:120,border:0,title:'图片'+_sId,
							onerror:function()
							{
								$errorMsg('图片添加失败.<br>原因:<font color=red>添加的文件不是图片文件.</font>','',5,'');
								if(this.parentNode)
								{
									var parentNode=this.parentNode;
									parentNode.removeChild(this);
									parentNode.innerHTML='<SPAN class=Img_ShowPre_Span>图片剪切板</SPAN>';
								}
							}
			});
		this.replaceSrc(_sId,nSrc);
		$(this.filePathPre + _sId).value=nSrc;
		var container=$(this.picShowPre + _sId);
		if(!img.error)
		{
			container.innerHTML='';
			container.appendChild(img);
		}
	},
	replaceSrc:function(_sId,nSrc){
		var sOldPath=$(this.filePathPre+ _sId).value;
		if (sOldPath.length >0&&this.seekUse(_sId)) {
			for(var i=0;i<this.used[_sId].length;i++)
			{
				var node=this.used[_sId][i].parentNode;
				if(node&&node.tagName=='A'&&node.href==sOldPath)
				{
					node.href=nSrc;
				}
				this.used[_sId][i].src=nSrc;
			}
		}
		if (com.trs.editor.Config.htmlOn){
			var sHtml = com.trs.editor.Editor.getHtml();
			var reg	= new RegExp(sOldPath, 'ig');
			//$log().info(sHtml + ', ' + this.used[_sId][0].src + ', ' + nSrc);
			sHtml = sHtml.replace(reg, nSrc);
			com.trs.editor.Editor.editor.contentWindow.document.body.innerHTML = $transHtml(sHtml);
		}
	},
	seekUse:function(_sId){
		var editor = com.trs.editor.Editor.editor;
		this.used[_sId]=[];
		var imgs = [];//editor.contentWindow.document.getElementsByTagName("img");
		if (com.trs.editor.Config.htmlOn){	
			var divX = document.createElement("DIV");
			divX.id = 'xxx_form_imgupload_div';
			divX.innerHTML = com.trs.editor.Editor.getHtml();
			var nodes = divX.getElementsByTagName('img');
			for (var i = 0; i < nodes.length; i++){
				var oNode = nodes[i];
				if (oNode == null || (oNode.tagName && oNode.tagName.toLowerCase() != 'img'))
					continue;
				imgs.push(oNode);
			}
		}else{
			imgs = editor.contentWindow.document.getElementsByTagName("img");
		}
		if (imgs.length == 0) return false;

		var thisValue=$(this.filePathPre+ _sId).value;
		var img=$('img_for_compare_src');
		if(!img)
		{
			div=document.createElement("DIV");
			div.style.display='none';
			document.body.appendChild(div);
			img=document.createElement("IMG");
			img.id='img_for_compare_src';
			div.appendChild(img);
		}
		img.src=thisValue;
		for(var i = 0; i < imgs.length; i++) {
			if(imgs[i].src == img.src){
				this.used[_sId].push(imgs[i]);
			}
		}
		return this.used[_sId].length>0;
	},
	postFile:function(_sId){
		var sId = this.formPre + _sId;
		var form=$(sId);
		if (form){
			if (form.title == _ATTR_FLAG_IMG_INSERTED && this.seekUse(_sId)) {
				if(this.method=='local')
				{
					form.method='GET';
					form.action=com.trs.editor.Config.basePath+'local/postImage.htm';
				}
				$log().error('before submit');
				form.submit();
			} else {
				this.clearSelect(_sId);
				this.postFile( _sId+1 );
			}
		}
		else{
			com.trs.editor.ArticleSubmiter.formSubmit();
		}
	},
	report_upload_ok:function(_sId, pic_path){
		this.lastFormId = _sId;
		$(this.picShowPre + _sId).innerHTML = "<span class=Img_ShowPre_Span2>上传成功</span>";
		if(this.method!='local')
		{
			this.replaceSrc(_sId,com.trs.editor.Config.uploadPath+pic_path);
		}
		$(this.formPre + _sId).reset();
		var album=Form.$(com.trs.editor.ArticleSubmiter.params["album"]);
		if (album)
		{
			if(album.value == ''){
			var sp_char = '';
			}
			else{
				var sp_char = ',';
			}
			album.value = album.value + sp_char + pic_path;	
		}
		this.postFile(parseInt(_sId)+1);
	},
	report_upload_error:function(error_str, _sId){
		$errorMsg(error_str,'',5,'图片'+_sId+'上传失败');
		com.trs.editor.ImgUploader.lastFormId = parseInt(_sId);
	}
});
ClassName(com.trs.editor.ImgUploader,'editor.ImgUploader');