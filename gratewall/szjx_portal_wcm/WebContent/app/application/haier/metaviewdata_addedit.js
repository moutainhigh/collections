/*在此处添加一些自定义js处理*/

(function(){
	Event.observe(window, 'load', function(){

		var components = com.trs.ui.XAppendixMgr.getAllComponents();
		
		for(var index = 0, length = components.length; index < length; index++){
			var component = components[index];
			
			var sName = component.getId();

			//如果是原图，则绑定图片上传后的事件，结尾数字为org0时，表示的是原图字段
			if(sName.endsWith("org0")){
				component.addListener('upload', originalPicUploaded);
				continue;
			}

			//元数据字段名称作为分辨率信息的描述，如：app1_230_450表示图片大小为：230×450
			var infos = sName.split("_");
			if(infos.length > 2){
				var dom = $(sName);
				if(infos[infos.length - 1]){
					dom.setAttribute('data-img-height', infos[infos.length - 1]);
				}
				if(infos[infos.length - 1]){
					dom.setAttribute('data-img-width', infos[infos.length - 2]);
				}
			}
		}
	});

	
	//原始图片上传完成之后，其他不同分辨率下的图片控件，如果没有值，那么默认将使用原图图片，
	//这样就方便用户进行裁剪，不需要用户再上传图片
	function originalPicUploaded(){
		var sName = this.getId();
		var sPrefixName = sName.substring(0, sName.length - "org0".length);
		var sValue = this.getValue();

		var components = com.trs.ui.XAppendixMgr.getAllComponents();
		
		for(var index = 0, length = components.length; index < length; index++){
			var component = components[index];
			
			var sTmpName = component.getId();
			if(sTmpName.startsWith(sPrefixName) && component.getValue() == ""){
				component.setValue(sValue);
				$G_RenderImgElements(component);
			}
		}
	}
})();