Ext.ns('com.trs.ui');
/**
*视频
*/
(function(){
	//private 
	var videoSelectBtn = "-select-btn";
	var videoText = "-text";
	var template = [
		'<div class="XVideo">',
			'<input type="hidden" name="{0}" id="{0}" value="{1}" />',
			'<div class="video-select" id="{0}-select-btn"></div>',
			'<div class="video-text" id="{0}-text">{2}</div>',
		'</div>'
	].join("");

	com.trs.ui.XVideo = Ext.extend(com.trs.ui.BaseComponent, {
		/**
		*name,value,desc,rootId,treeType
		*/
		getHtml : function(){
			var config = this.initConfig;
			var sName = config['name'];
			
			var sVideoText = (config['desc']||"无") + "[id=" + (config['value']||0) + "]";
			if(!config['desc']){
				config['value'] = "";
				sVideoText = "";	
			}
			return String.format(template, config['name'], config['value'], sVideoText);
		},
		setValue : function(info){
			var config = this.initConfig;
			var sName = config['name'];
			$(sName).value = info.value||"";
			Element.update(sName + videoText, info.desc||'无[id=0]');
		},
		selectVideo : function(){
			var config = this.initConfig;
			var sName = config['name'];
			var videoId = $(sName).value;
			var params = {
				ChannelId : config["channelId"],
				ObjectIds:videoId
			};
			wcm.VideoSelector.selectVideo(params, function(_args){	
				var arIds = _args;			

				$(sName).value = arIds;	
				Element.update(sName + videoText, arIds);
			});			
		},
		initActions : function(){
			if(this.initConfig['disabled']) return;
			Event.observe(this.initConfig['name'] + videoSelectBtn, 'click', this.selectVideo.bind(this));
		}
	});
})();