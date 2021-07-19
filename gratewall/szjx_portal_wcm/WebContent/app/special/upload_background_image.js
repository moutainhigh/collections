// 处理上传到临时目录的图片，在 upload_dowith 页面中使用
function dealWithUploadedPicFile(_saveFileHttpPath, _saveFileName){
	if(_saveFileHttpPath.indexOf("webpic")<0){
		Ext.Msg.alert("上传文件失败");
	}
	if(!_saveFileHttpPath&&_saveFileHttpPath=="")
		return;
	$("imageUrl").value = _saveFileHttpPath;
	render(_saveFileHttpPath);
}

// 将缩略图还原为默认状态
function resetImage(){
	if($("imageUrl").value == ""){
		Ext.Msg.alert("未发现上传的图片！");
		return;
	}
	Ext.Msg.confirm('您确定要清除此图片吗？', {
		yes : function(){
			$("imageUrl").value = "";
			render("");
		}
	});
}
// 是否已经上传了图片
var bHasImage = false;
function getValue(name){
	var doms = document.getElementsByName(name);
	for(var index = 0; index < doms.length; index++){
		if(doms[index].checked){
			return doms[index].value;
		}
	}
	return '';
}

function render(url){
	if(url == ""){
		bHasImage = false;
	}else{
		bHasImage = true;
	}
	$("preview-box").style.background = "url("+url+") " + getValue('backgroundPositionX') +" " +  getValue('backgroundPositionY') +" " +getValue('backgroundRepeat');
	Element[url?'hide':'show']('tip');
}

Event.observe(window, 'load', function(){
	Event.observe(document, 'click', function(event){
		var srcElement = Event.element(event);
		if(srcElement.tagName == 'INPUT' && srcElement.getAttribute('type') == 'radio'){
			// 为了兼容某些浏览器不支持backgroundPositionX，backgroundPositionY做的处理
			if("backgroundPositionX" == srcElement.name){
				$("preview-box").style['backgroundPosition'] = srcElement.value + " " + getValue('backgroundPositionY');
			}else if ("backgroundPositionY" == srcElement.name){
				$("preview-box").style['backgroundPosition'] = getValue('backgroundPositionX') + " " + srcElement.value;
			}else{
				$("preview-box").style[srcElement.name] = srcElement.value;
			}
		}
	});
});

function init(params){
	if(!params) return;
	if(params.background){
		bHasImage = true;
		$("preview-box").style.background = params.background;
		$("imageUrl").value = $("preview-box").style.backgroundImage;
		Element.hide('tip');
	}

	if(params.backgroundRepeat){
		$(params.backgroundRepeat).checked = true;
	}

	if(params.backgroundPosition){
		// 因为chrome浏览器中都是使用百分比0%，50%，100%表示，所以这里先做处理
		var positions = getPositions(params.backgroundPosition);
		$('backgroundPositionX-'+positions[0]).checked = true;
		$('backgroundPositionY-'+positions[1]).checked = true;
	}
}
function getPositions(sPosition){
	if(!sPosition) return ["center","center"];

	var positions = sPosition.split(" ");
	var sPositionX =  positions[0];
	var sPositionY =  positions[1];

	switch(sPositionX){
		case '0%':
			sPositionX = "left";
			break;
		case '50%':
			sPositionX = "center";
			break;
		case '100%':
			sPositionX = "right";
			break;
		default:
			break;
	}

	switch(sPositionY){
		case '0%':
			sPositionY = "top";
			break;
		case '50%':
			sPositionY = "center";
			break;
		case '100%':
			sPositionY = "bottom";
			break;
		default:
			break;
	}
	return [sPositionX,sPositionY];
}
window.m_cbCfg = {
	btns : [
		{
			text : '确定',
			cmd : function(){
				var sBackground = $("preview-box").style.background;
				// firefox下，url会带双引号，这里需要将url中的双引号去掉，否则取出来的样式会出现问题
				sBackground = sBackground.replace(/\"/g, "");
				var oResult = {
					background: sBackground,
					hasImage: bHasImage
				};
				this.callback(oResult);
			}
		},
		{
			extraCls : 'wcm-btn-close',
			text : '取消'
		}
	]
};
