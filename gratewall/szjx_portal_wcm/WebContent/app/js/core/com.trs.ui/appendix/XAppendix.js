Ext.ns('com.trs.ui');
/**
*附件字段
*/
(function(){
	//private 
	var Type="METAVIEWDATA_APPENDIX_SIZE_LIMIT";
	var appendixForm = "-frm";
	var appendixBrowserBtn = "-browser-btn";
	var appendixDeleteBtn = "-delete-btn";
	var appendixText = "-text";
	var template = [
		'<div class="XAppendix">',
			'<input type="hidden" name="{0}" id="{0}" value="{1}" />',
			'<form name="{0}-frm" id="{0}-frm" method="post" enctype="multipart/form-data">',
				'<div class="appendix-browser">',
					'<input type="file" ignore="true" name="{0}-browser-btn" id="{0}-browser-btn" />',
				'</div>',
				'<span id="{0}-text" class="appendix-text">{1}</span>',
				'<span class="appendix-delete" id="{0}-delete-btn" style="display:{2}"></span>',
			'</form>',
		'</div>'
	].join("");

	com.trs.ui.XAppendix = Ext.extend(com.trs.ui.BaseComponent, {
		uploadUrl : XConstants.BASE_PATH + 'com.trs.ui/appendix/file_upload_dowith.jsp',
		init : function(config){
			com.trs.ui.XAppendix.superclass.init.apply(this, arguments);
			com.trs.ui.XAppendixMgr.register(this);
			this.addEvents('beforeupload', 'upload', 'change', 'delete');
		},
		getHtml : function(){
			var config = this.initConfig;
			return String.format(template, config['name'], config["value"], !config["value"] ? "none" : "");			
		},
		afterRender : function(){	
			com.trs.ui.XAppendix.superclass.afterRender.apply(this, arguments);
			var config = this.initConfig;
			if(config['disabled']){
				$(config['name']+appendixBrowserBtn).disabled = true;
			}
		},
		initActions : function(){
			if(this.initConfig['disabled']) return;
			com.trs.ui.XAppendix.superclass.initActions.apply(this, arguments);
			var sName = this.initConfig['name'];
			var sForm = sName + appendixForm;
			var sBrowserBtn = sName + appendixBrowserBtn;
			var sDeleteBtn = sName + appendixDeleteBtn;
			var sTextId = sName + appendixText;		
			var caller = this;

			//bind event for browser button.
			Event.observe(sBrowserBtn, 'change', function(){
				var sValue = $(sBrowserBtn).value;
				var index = sValue.lastIndexOf("\\") + 1;
				var sFileName = sValue.substr(index);
				$(sTextId).innerHTML = sFileName;
				var sFileExt = "";
				var nDotIndex = sFileName.lastIndexOf(".");
				if(nDotIndex >= 0){
					sFileExt = sFileName.substr(nDotIndex);
				}
				$(sName).value = "temp"+sFileExt;
				Element.show(sDeleteBtn);
				caller.fireEvent('change');
			});

			//bind event for delete button.
			Event.observe(sDeleteBtn, 'click', function(){
				$(sForm).reset();
				$(sName).value = "";
				$(sTextId).innerHTML = "";	
				Element.hide(sDeleteBtn);
				caller.fireEvent('delete');
			});			
		},
		/**
		*返回当前附件是否需要上传，true表示不需要上传，否则反之
		*/
		isUploaded : function(){
			var sName = this.initConfig['name'];
			var dom = $(sName + appendixBrowserBtn);
			return dom.value.trim() == "";
		},
		upload : function(fSuccess, fFailure){
			if(this.fireEvent('beforeupload') === false) return;
			var sName = this.initConfig['name'];
			var sParams = "fileNameParam=" + $(sName + appendixBrowserBtn).name;
			sParams += "&fileNameValue=" + encodeURIComponent($(sName + appendixText).innerHTML);
			// 添加系统设置 检验上传文件大小
			sParams+= "&Type="+Type;
			YUIConnect.setForm(sName + appendixForm, true, Ext.isSecure);
			var caller = this;
			YUIConnect.asyncRequest('POST',
				this.uploadUrl + '?'+sParams, {
					"upload" : function(_transport){
						var sResponseText = _transport.responseText;
						eval("var result="+sResponseText);
						caller.fireEvent('upload', result);
						if(result["Error"]){
							if(fFailure) fFailure(result["Error"]);
							return;
						}
						$(sName).value = result["Message"];
						Element.update(sName + appendixText, result["Message"]);
						$(sName + appendixForm).reset();//清空form，防止上传出错时，下次再次上传
						if(fSuccess) fSuccess(result["Message"]);
					}
				}
			);
		}
	});
})();

com.trs.ui.XAppendixMgr = function(){
	//private
	var all = [];
	var index = 0;
	var fSuccessCallBack;
	var bRecored = false;
	/*
	*记录最后执行成功或失败时，执行的回调函数，并做一些数据的清除工作；
	*/
	var wrap = function(fSuccess, fFailure){
		bRecored = true;
		fSuccessCallBack = function(){
			index = 0;
			bRecored = false;
			if(fSuccess) fSuccess();
		};
		return function(){
			bRecored = false;
			if(fFailure){
				// 保存失败时让index减一，防止自动增加而忽略当前附件
				index--;
				fFailure(arguments[0]);
			}
		};
	};
	/**
	*获取下一个需要上传的附件，如果没有需要上传的附件，则返回空
	*/
	var getNextAvailableAppendix = function(){
		for (; index < all.length; index++){
			if(!all[index].isUploaded()) break;
		}
		return all[index++];
	}
	return {
		/**
		*对所有的附件字段进行上传，当所有附件都上传完成时执行fSuccess回调，
		*如果某个附件上传失败，则执行fFailure，同时不再继续上传剩余的附件
		*/
		upload : function(fSuccess, fFailure){
			if(!bRecored) fFailure = wrap(fSuccess, fFailure);
			var oAppendix = getNextAvailableAppendix();
			if(oAppendix == null){
				if(fSuccessCallBack) fSuccessCallBack();
				return;
			}
			oAppendix.upload(this.upload, fFailure);
		},
		register : function(c){
			all.push(c);
		},
		unregister : function(c){
			all.remove(c);
		},
		getAllComponents : function(){
			return all;	
		},
		get : function(id){
			return com.trs.ui.ComponentMgr.get(id);
		}
	};
}();