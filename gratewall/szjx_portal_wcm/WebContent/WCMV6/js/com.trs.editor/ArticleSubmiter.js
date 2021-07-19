$package("com.trs.editor");

$define('com.trs.editor.ArticleSubmiter');

Object.extend(com.trs.editor.ArticleSubmiter,{
	lastSendTime:0,
	beforeSubmit:function(){
		// ge gfc add @ 2006-8-16 14:02
		if(com.trs.editor.Config.htmlOn) {
			com.trs.editor.Config.htmlOn = true;
			com.trs.editor.Editor.HTMLView();
		}

		var chkAutoAbstract = Form.$(this.params["auto_abstract"]);
		var bForcedAutoAbs = (chkAutoAbstract && chkAutoAbstract.checked);
		var txtAbstract = Form.$(this.params["article_abstract"])||{};
		if(bForcedAutoAbs || txtAbstract.value.trim().length == 0) {
			var sAbstract = com.trs.editor.Editor.getAbstract(com.trs.editor.Config.abstractLength);
			txtAbstract.value = sAbstract;
		}


		(Form.$(this.params["article"])||{}).value=com.trs.editor.Editor.getHtml();
		(Form.$(this.params["article_text"])||{}).value=com.trs.editor.Editor.getText();
	},
	preview:function(){
		com.trs.editor.Editor.hideIframes();
		var form=$(this.form);
		if (this.check())
		{
			(Form.$(this.params["act"])||{}).value = this.PREVIEW;
			if(this.method=='local')
			{
				form.method='GET';
				form.target=this.target;
				form.action=com.trs.editor.Config.basePath+'local/postArticle.htm';
				form.submit();
				form.method='POST';
			}
			else
			{
				form.action = this.previewAction;
				form.target = "_blank";
				this.beforeSubmit();
				form.submit();
				form.action = this.action;
				form.target = this.target;
			}
		}
	},
	submit:function(){
		if (this.check())
		{
			if(_IE)
			{
				//ge gfc comment @ 2006-8-22 16:31
				// this.save();
				$wait('<center><b>正在发表文章...</b></center><br/>　　<font color="green">提示：</font>文章内容已经暂存至系统剪贴板，如遇网络故障等原因发表失败可点击鼠标右键→选择“粘贴”重新发表。');
			}
			var _interval=this.interval();
			if(_interval<20)
			{
				$errorMsg('请勿在短时间内多次发送信息 :)<br/> 您需要耐心等待 ' + (20 - _interval) + ' 秒，就可以发表文章了.','',1);
				return false;
			}
			this.initSubmit();
		}
	},
	formSubmit:function(){
		var form=$(this.form);
		form.action = this.action;
		form.target = this.target;
		this.beforeSubmit();
		if(this.method=='local')
		{
			form.method='GET';
			form.action=com.trs.editor.Config.basePath+'local/postArticle.htm';
			form.submit();
			form.method='POST';
		}
		else if(this.method=='local-ajax')
		{
			(this.localAjax||Prototype.emptyFunction)();
		}
		else if(this.method=='ajax-frame'){
			this.$postArticle((Form.$(this.params["act"])||{}).value);
		}
		else
		{
			form.submit();
		}
	},
	save:function(){
		com.trs.util.CommonHelper.copy((Form.$(this.params["article"])||{}).value);
	},
	interval:function(){
		var sCurrTime = Math.floor((new Date() - this.lastSendTime)/1000);
		return sCurrTime < 0 ? 20 : sCurrTime;
	},
	initSubmit:function(){
		this.lastSendTime=new Date();
		com.trs.editor.ImgUploader.postFile(1);
	},
	publish:function(){
		com.trs.editor.Editor.hideIframes();
		(Form.$(this.params["act"])||{}).value = this.PUBLISH;
		this.submit();
	},
	draft:function(){
		com.trs.editor.Editor.hideIframes();
		(Form.$(this.params["act"])||{}).value = this.DRAFT;
		this.submit();
	},
	check:function(){
		var title=this.params["title"];
		var btitle = Form.$(title);
		var nTitleLen = 0;
		if (btitle&&btitle.value.trim() == ''){
			$alert('你忘记输入文章标题了.');
			return false;
		}else if((nTitleLen = btitle.value.getLength()) > 200){
			$alert('标题字符长度(每汉字相当于两个字符)[<b>' + nTitleLen + '</b>]超出最大限制[<b>200</b>]！');
			return false;
		}
		var sVal = btitle.value;
		if (this.__containsStr(sVal, /\$\&/)
			|| this.__containsStr(sVal, /\#INNERTHML/)) {
			$alert('标题中不能含有 "$&" 或者 "#INNERTHML"。');
			return false;
		}
		var keywords=this.params['keywords'];
		var sKeywords = $F(keywords);
		if(sKeywords.trim().getLength() > this.KEYWORDS_LENGTH) {
			$alert('关键字长度为[<b>' + sKeywords.trim().getLength() + '</b>]超过最大允许长度[<b>' + this.KEYWORDS_LENGTH + '</b>]', function(){
				$dialog().hide();
				try{
					$('txtKeywords').select();
					$('txtKeywords').focus();
				}catch (ex){
					//TODO
					//alert(ex.description);
				}		});
			return false;
		}
		$(keywords).value = sKeywords.trim();

		var	v=com.trs.editor.Editor.editor.contentWindow.document.body.innerHTML;
		var nContentLen = 0;
		if (v == ''){
			$alert('你忘记输入文章内容了.');
			return false;
		}
		return true;
	},
	__containsStr : function(_string, _reg){
		return _string.replace(_reg, '') != _string;
	},
	$postArticle:function(id){
		switch(parseInt(id))
		{
			case this.PREVIEW:
				this.$preview();
				break;
			case this.DRAFT:
				this.$draft();
				break;
			case this.PUBLISH:
				this.$publish();
				break;
		}
	},
	$preview:function(){//for extends
	},
	$draft:function(){//for extends
	},
	$publish:function(){//for extends
	}
});
ClassName(com.trs.editor.ArticleSubmiter,'editor.ArticleSubmiter');