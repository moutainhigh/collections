(function(){
	var defaultValue4NeedFocus = true;
	var getExcludeElName = function(item){
		if(typeof item == 'string'){
			return item;
		}
		for(sExcludeElName in item) return sExcludeElName;
	};
	var isNeedFocus = function(excludes, sElName){
		for(var index = 0; index < excludes.length; index++){
			var tempName = getExcludeElName(excludes[index]);
			if(tempName != sElName) continue;
			if(typeof excludes[index] == 'string'){
				return defaultValue4NeedFocus;				
			}
			return excludes[index][tempName];
		}
	};
	window.BlurMgr = {
		add : function(config){
			var currExcludeElName;
			var excludes = config['excludes'];
			for(var index = 0; index < excludes.length; index++){
				var excludeElName = getExcludeElName(excludes[index]);
				if(!$(excludeElName)) continue;
				Event.observe(excludeElName, 'mouseover', function(event){
					currExcludeElName = this;
				}.bind(excludeElName));
				Event.observe(excludeElName, 'mouseout', function(event){
					currExcludeElName = false;
				});
			}
			Event.observe(config["element"], 'blur', function(event){
				if(!currExcludeElName){
					if(config['blur'].apply(config, arguments)) {
						$(config["element"]).focus();
						return;
					}
				}
				if(isNeedFocus(excludes, currExcludeElName)) $(config["element"]).focus(); return;
			});
		}
	};
})();
 