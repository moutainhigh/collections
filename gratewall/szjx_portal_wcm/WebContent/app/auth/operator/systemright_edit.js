	var wcm = new Object();
	wcm.Rights = {
		indexConfigs : {},
		typeConfigs : {},
		add : function(config){
			var type = config['Type'];
			if(!type) return;
			if(!this.typeConfigs[type]) {
				this.typeConfigs[type]=[];
			}
			this.typeConfigs[type].push(config);

			var index = config['Index'];
			if(!this.indexConfigs[index]){
				this.indexConfigs[index]=[];
			}
			this.indexConfigs[index].push(config);
		},
		getIndexConfigs : function(index){
			return this.indexConfigs[index];
		},
		getTypeConfigs : function(type){
			return this.typeConfigs[type];
		}
	};

	wcm.RightIndex = {
		RightIndex : [],
		add : function(index){
			this.RightIndex=index;
		}
	};