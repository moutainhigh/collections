Ext.ns('com.trs.ui');
/**
*附件字段扩展
*/
(function(){
	//extend for image appendix
	var imgCropBtn = '-img-crop-btn';
	var imgPreview = '-img-preview';
	var appendixImgBox = '-appendix-img-box';
	var template = [
		'<span id=\"{0}', appendixImgBox, '\" style=\"display:none\">',
			'<span class="appendix-img-crop" id=\"{0}', imgCropBtn, '\" title=\"', '编辑图片', '\"></span>',
			'<br/><a href=\"\" target=\"_blank\"><img id=\"{0}', imgPreview , '\" border=\"0\" alt=\"\" onload=\"resizeIfNeed(this);\" /></a>',
		'</span>'
	].join("");

	//确保图片编辑按钮及图片预览区的存在
	function ensureImgElementsExists(comp){
		var sName = comp.initConfig['name'];
		var cropBtnId = sName + imgCropBtn;
		if($(cropBtnId)) return;

		new Insertion.Bottom(comp.getBox(), String.format(template, sName));
		if(!window.bPhotoPluginsEnable){
			var childEls = (comp.getBox()).getElementsByTagName("span");
			for(var k=0;k<childEls.length;k++){
				if(Element.hasClassName(childEls[k],"appendix-img-crop")){
					Element.hide(childEls[k]);
				}
			}
		}
		//bind event for crop button.
		Event.observe(cropBtnId, 'click', function(){cropImage(comp);});
	}

	/**
	*根据当前附件的类型，决定是否显示裁剪按钮
	*/
	function renderImgElements(comp){
		var sName = comp.initConfig['name'];
		var dom = $(sName);
		var bImage = /\.(jpg|gif|png|jpeg|bmp)$/.test(dom.value);
		Element[bImage ? 'show' : 'hide'](sName + appendixImgBox);
		
		if(comp.getValue().trim().length <= 0){
			return;
		}
		var img = $(sName + imgPreview);
		img.src = WCMConstants.WCM6_PATH + 'system/read_image.jsp?FileName='+comp.getValue();
		
		img.parentNode.setAttribute("href", img.src);//link original image
	}

	window.$G_RenderImgElements = renderImgElements;

	/**
	*隐藏裁剪按钮
	*/
	function hideCropBtn(comp){
		var sName = comp.initConfig['name'];		
		var dom = $(sName + appendixImgBox);
		if(dom){
			Element.hide(dom);
		}
	}

	//裁剪图片
	function cropImage(comp){
		var nWidth = 900;
		var nHeight = 600;
		var sImgUrl = WCMConstants.WCM6_PATH + "../file/read_image.jsp?FileName="+comp.getValue();
		var parameters = "photo="+encodeURIComponent(sImgUrl);
		var dom = $(comp.initConfig['name']);
		var nImgWidth = dom.getAttribute('data-img-width');
		if(nImgWidth){
			parameters += "&width="+nImgWidth;
		}
		var nImgHeight = dom.getAttribute('data-img-height');
		if(nImgHeight){
			parameters += "&height="+nImgHeight;
		}
		if(nImgWidth || nImgHeight){
			parameters += "&disabledSize=1";
		}
		var sUrl = WCMConstants.WCM6_PATH + "photo/photo_crop.html?"+parameters;
		var dialogArguments = window;
		var sFeatures = "center:yes;dialogWidth:" + nWidth + "px;dialogHeight:" + nHeight +"px;status:no;resizable:no;";
		var info = window.showModalDialog(sUrl, dialogArguments, sFeatures);

		if(info == null){
			return;
		}
		comp.setValue(info['FN']);
		renderImgElements(comp);
	}

	Event.observe(window, 'load', function(){

		var components = com.trs.ui.XAppendixMgr.getAllComponents();
		
		for(var index = 0, length = components.length; index < length; index++){
			var component = components[index];
			
			ensureImgElementsExists(component);
			renderImgElements(component);

			component.addListener('upload', function(){renderImgElements(this);});

			component.addListener('delete', function(){hideCropBtn(this);});

		}
	});

})();

