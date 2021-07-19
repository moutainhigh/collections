Ext.ns('com.trs.ui');
/**
*栏目选择树
*/
(function(){
	//private 
	var channelTreeSelectBtn = "-select-btn";
	var channelTreeText = "-text";
	var template = [
		'<div class="XChannelTree">',
			'<input type="hidden" name="{0}" id="{0}" value="{1}" />',
			'<div class="channelTree-select" id="{0}-select-btn"></div>',
			'<div class="channelTree-text" id="{0}-text">{2}</div>',
		'</div>'
	].join("");

	com.trs.ui.XChannelTree = Ext.extend(com.trs.ui.BaseComponent, {
		/**
		*name,value,desc,rootId,treeType
		*/
		getHtml : function(){
			var config = this.initConfig;
			var sName = config['name'];
			//TODO
			//desc,rootId可能需要根据value去获取,但为了提供性能，先由外界传入
			var sChannelTreeText = (config['desc']||"无") + "[id=" + (config['value']||0) + "]";
			if(!config['desc']){
				config['value'] = "";
				sChannelTreeText = "";	
			}
			return String.format(template, config['name'], config['value'], sChannelTreeText);
		},
		selectChannelTree : function(){
			var config = this.initConfig;
			var sName = config['name'];
			var chnlIds = $(sName).value;
			var params = {
				treeType : config["treeType"],
				selectedValue:chnlIds
			};
			wcm.ChannelTreeSelector.selectChannelTreeTree(params, function(_args){
				var arIds = _args[0]|| [];			
				var arNames = _args[1]||[];
				var arDescs = [];
				for(var i=0,len=arIds.length;i<len;i++){
					arDescs.push(arNames[i]+'[id='+arIds[i]+']');
				}
				if(arDescs.length == 0) arDescs.push(wcm.LANG.XChannelTree_1000 || '无[id=0]');
				$(sName).value = arIds.join(",");	
				Element.update(sName + channelTreeText, arDescs.join(","));
			});			
		},
		initActions : function(){
			if(this.initConfig['disabled']) return;
			Event.observe(this.initConfig['name'] + channelTreeSelectBtn, 'click', this.selectChannelTree.bind(this));
		}
	});
})();