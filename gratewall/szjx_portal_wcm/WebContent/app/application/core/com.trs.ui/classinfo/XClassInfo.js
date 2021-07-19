Ext.ns('com.trs.ui');
/**
*分类法
*/
(function(){
	//private 
	var classInfoSelectBtn = "-select-btn";
	var classInfoText = "-text";
	var template = [
		'<div class="XClassInfo">',
			'<input type="hidden" name="{0}" id="{0}" value="{1}" />',
			'<input type="hidden" name="root-{0}" id="root-{0}" value="{3}" />',
			'<div class="classInfo-select" id="{0}-select-btn"></div>',
			'<div class="classInfo-text" id="{0}-text">{2}</div>',
		'</div>'
	].join("");

	com.trs.ui.XClassInfo = Ext.extend(com.trs.ui.BaseComponent, {
		/**
		*name,value,desc,rootId,treeType
		*/
		getHtml : function(){
			var config = this.initConfig;
			var sName = config['name'];
			//TODO
			//desc,rootId可能需要根据value去获取,但为了提供性能，先由外界传入
			//var sClassInfoText = (config['desc']||"无") + "[id=" + (config['value']||0) + "]";

			//20120606 by CC 调整初始化显示效果与编辑时显示效果一致

			var arIds = (config['value'] || "0").split(",");
			var arNames = (config['desc'] || "无").split(",");
			var arDescs = [];
			for(var i=0,len=arIds.length;i<len;i++){
				arDescs.push(arNames[i]+'[id='+arIds[i]+']');
			}
			if(arDescs.length == 0) arDescs.push('无[id=0]');
			var sClassInfoText = arDescs.join(",");

			if(!config['desc']){
				config['value'] = "";
				sClassInfoText = "";	
			}
			return String.format(template, config['name'], config['value'], sClassInfoText, config['rootId']);
		},
		setValue : function(info){
			var config = this.initConfig;
			var sName = config['name'];
			$(sName).value = info.value||"";
			Element.update(sName + classInfoText, info.desc||'无[id=0]');
		},
		selectClassInfo : function(){
			var config = this.initConfig;
			var sName = config['name'];
			if($('root-'+sName) == 0){
				Ext.Msg.alert("没有绑定分类法信息");
				return;
			}
			var classId = $(sName).value;
			var params = {
				objectId : $('root-'+sName).value,
				selectType : config["selectType"],
				treeType : config["treeType"],
				selectedValue:classId
			};
			wcm.ClassInfoSelector.selectClassInfoTree(params, function(_args){	
				var arIds = _args.ids || [];			
				var arNames = _args.names||[];
				var arDescs = [];
				for(var i=0,len=arIds.length;i<len;i++){
					arDescs.push(arNames[i]+'[id='+arIds[i]+']');
				}
				if(arDescs.length == 0) arDescs.push('无[id=0]');
				$(sName).value = arIds.join(",");	
				Element.update(sName + classInfoText, arDescs.join(","));
			});			
		},
		initActions : function(){
			if(this.initConfig['disabled']) return;
			Event.observe(this.initConfig['name'] + classInfoSelectBtn, 'click', this.selectClassInfo.bind(this));
		}
	});
})();