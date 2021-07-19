define(['observe'], function(observe){
	 var Scope = function(fun){
		 observe.make(Scope.prototype);
		 if(fun && Array.isArray(fun)){
			 for(var i = 0,len = fun.length; i < len; i++){
				 this.addSubscriber(fun[i]);
			 } 
		 }
		 
			// this.addSubscriber(this.loadDom);
		// this._init(data);
	 }; 
	 
	 Scope.defaults = {
			type:'6',
			suffix:'企业依法自主选择经营项目，开展经营活动；依法须经批准的项目，经相关部门批准后依批准的内容开展经营活动；不得从事本市产业政策禁止和限制类项目的经营活动。'
	 };
	 
	 Scope.prototype._init = function(data){
		// data = {aaa:'aaa',bbb:'bbb'};
			 this.data = data || {};
			// this.data.type = data.type || Scope.defaults.type;
			 this.data.type = Scope.defaults.type;
			 this.data.suffix = data.suffix || Scope.defaults.suffix;
			 this.data.scope = data.scope || [];
			 this.publish(data);
	 };
	 
	 Scope.prototype.addScope = function(scope){  //scope:{group:group,name:name,code:code,index:index}
		 if(!(scope && scope.group && scope.name && scope.code && scope.index)){
			 alert('新增的经营范围有错误！');
			 return;
		 }
		 var sc = this.data.scope;
		 if(!sc){
			 sc = [];
			 sc[scope.index] = [{name:scope.name,code:scope.code,group:scope.group}]; 
			 this.data.scope = sc;
			 this.publish(this.data);
		 }else{
			 var scopeIndex = sc[scope.index];
			 if(!scopeIndex){
				 var scopeIndex = [{name:scope.name,code:scope.code,group:scope.group}];
				 sc[scope.index] = scopeIndex;
				 this.data.scope = sc;
				 this.publish(this.data);
			 }else{
				 var indexstr = JSON.stringify(scopeIndex);
				 if(indexstr.indexOf(scope.code) == -1){
					 var item = {name:scope.name,code:scope.code,group:scope.group}; 
					 scopeIndex.push(item);
					 sc[scope.index] = scopeIndex;
					 this.data.scope = sc;
					 this.publish(this.data);
				 }
			 }
		 }
	 };
	 
	 Scope.prototype.removeScope = function(scope){  //scope:{group:group,name:name,index:index,code:code}
		 if(!(scope && scope.group && scope.name && scope.code && scope.index)){
			 alert('删除经营范围有错误！');
			 return;
		 }
		 var sc = this.data.scope;
		 if(!sc){
			 alert('删除经营范围有错误！');
			 return;
		 }else{
			 var scopeIndex = sc[scope.index];
			 if(!scopeIndex){
				 alert('删除经营范围有错误！');
				 return;
			 }else{
				 var indexstr = JSON.stringify(scopeIndex);
				 if(indexstr.indexOf(scope.code) == -1){
					 alert('删除经营范围有错误！');
					 return;
				 }else{
					for(var i = 0, len = scopeIndex.length; i < len; i++){
						if(scopeIndex[i].code == scope.code){
							scopeIndex.splice(i,1);
							break;
						}
					}
					sc[scope.index] = scopeIndex;
					this.data.scope = sc;
					this.publish(this.data);
				 }
			 }
		 }
	 };
	 
	 Scope.prototype.addLicense = function(license){  //license:{name:name,code:code} or []
		 if(!Array.isArray(license)){
			 license = [license];
		 }
		 var lic = this.data.license;
		 if(!lic){
			 lic = license;
			 this.data.license = lic;
			 this.publish(this.data);
		 }else{
			 for(var i = 0,len = license.length; i < len; i ++){
				 var item = license[i];
				 if(!(item && item.name && item.code)){
					 alert('新增的许可经营范围有错误！');
					 return;
				 }else{
					 var licstr = JSON.stringify(lic);
					 if(licstr.indexOf(license[i].code) == -1){
						 var item = {name:license[i].name,code:license[i].code}; 
						 lic.push(item);
						 
					 }
				 }
				 
			 }
			 this.data.license = lic;
			 this.publish(this.data);

		 }
		 
		 
	 };
	 
	 Scope.prototype.removeLicense = function(license){  //license:{name:name,code:code}
		 if(!(license && license.name && license.code)){
			 alert('删除许可经营范围有错误！');
			 return;
		 }
		 var lic = this.data.license;
		 if(!lic){
			 alert('删除许可经营范围有错误！');
			 return;
		 }else{
			 var licstr = JSON.stringify(lic);
			 if(licstr.indexOf(license.name) == -1){
				 alert('删除许可经营范围有错误！');
				 return;
				
			 }else{
				 for(var i = 0, len = lic.length; i < len; i++){
					 if(license.code == lic[i].code){
						 lic.splice(i,1);
						 break;
					 }
				 }
				 this.data.license = lic;
				 this.publish(this.data);
			 }
		 }
	 };
	 
	 Scope.prototype.changeLicense = function(license){  //license:[{name:name,code:code}]
		 if(!(license && Array.isArray(license))){
			 alert('新增的许可经营范围有错误！');
			 return;
		 }else{
			 this.data.license = license;
			 this.publish(this.data);
		 }
	 };
	 
	 Scope.prototype.addMainScope = function(mainScope){ //mainScope:{text,isStandard}
		 if(mainScope){
			 var mScope = this.data.mainScope;
			 if(mScope){
				 if(mScope.text != mainScope.text){
					 mScope = mainScope;
					 this.data.mainScope = mScope;
					 this.publish(this.data);
				 }else{
					 mScope = mainScope;
				 }
			 }else{
				 mScope = mainScope;
				 this.data.mainScope = mScope;
				 this.publish(this.data);
			 }
		 }else{
			 alert('新增的主要经营范围有错误！');
			 return;
		 }
	 };
	 
	 Scope.prototype.addCustomScope = function(customScope){ //customScope:string
		 if(typeof(customScope) == 'string'){
			 var cScope = this.data.customScope;
			 if(cScope){
				 if(cScope != customScope){
					 cScope = customScope;
					 this.data.customScope = cScope;
					 this.publish(this.data);
				 }
			 }else{
				 cScope = customScope;
				 this.data.customScope = cScope;
				 this.publish(this.data);
			 }
		 }else{
			 alert('新增的自定义经营范围有错误！');
			 return;
		 }
	 };
	 
	 Scope.prototype.getScope = function(){
		 return this.data;
	 };
	 
	 Scope.prototype.getCommonScope = function(){
		 return this.data.scope;
	 };
	 
	 Scope.prototype.setCommonScope = function(commonScope){
		 if(!Array.isArray(commonScope)){
			 alert('新增的经营范围有错误！');
			 return;
		 }else{
			 this.data.scope = commonScope;
			 this.publish(this.data);
		 }
	 };
	 
	 Scope.prototype.setSuffix = function(suffix){
		 if(!suffix){
			 alert('项下标注有错误！');
			 return;
		 }else{
			 this.data.suffix = suffix;
			// this.publish(this.data);
		 }
	 };
	 
	 Scope.prototype.getLicense = function(){
		 return this.data.license || [];
	 };
	 
	 Scope.prototype.clearScope = function(){
		 this.data.scope = [];
	 };

	 Scope.prototype.clearCustomScope = function(){
		 this.data.customScope = '';
	 };
	 
	 Scope.prototype.clearMainScope = function(){
		 this.data.mainScope = {};
	 };
	 
	 Scope.prototype.changeType = function(type){
		 if(typeof(type) == 'string'){
			 this.data.type = type;
			 if(type == '1'){
				 this.data.scope = [];
				 this.data.customScope = '';
			 }else if(type == '2'){
				 this.data.mainScope = {};
			 }
			 this.publish(this.data);
		 }else{
			 alert('设置经营范围出错！');
		 }
		 
	 };
	 
	 Scope.prototype.getScopeText = function(){
 		var data = this.data;
		var text = '';
		var scope = '';
		var license = '';
		var customScope = '';
		var suffix = '';
		var mainScope = '';
		if(data.mainScope){
			mainScope = (data.mainScope.text||'');
		}
		if(data.scope){
			for(var i = 0, len = data.scope.length; i < len; i++){
				var scopCon = data.scope[i];
				if(scopCon){
					var itemScope = '';
					for(var j = 0, lenj = scopCon.length; j < lenj; j++){
						if(mainScope.indexOf(scopCon[j].name) == -1){
							if(scope.indexOf('销售') != -1 || itemScope.indexOf('销售') !=-1){
								var itemName = scopCon[j].name.replace('销售','');
								itemScope += itemName + '、';
							}else{
								itemScope += scopCon[j].name + '、';
							}
						}
					}
					var index = itemScope.length - 1;
					if(index > 0){
						if(i == 0){
							mainScope += '、' + itemScope.substring(0,index);
						}else{
							scope += itemScope.substring(0,index);
							scope += '；';
						}
					}
				}
				
			}
		}
		if(data.license){
			var lic = data.license;
			for(var i = 0, len = lic.length; i < len; i++){
				license += lic[i].name + '、';
			}
			var index = license.length - 1;
			if(index >= 0){
				license = license.substring(0,index);
				license += '；';
			}
		}
		if(data.customScope){
			customScope += data.customScope + ';';
		}
		if(data.suffix){
			
			if(license){
				var suf = Scope.defaults.suffix;
    			var index = 0;
    			var before = '';
    			var after = '';
				index = suf.indexOf('依法须经批准的项目');
				before = suf.substring(0, index);
				after = '以及' + suf.substring(index);
				var inText = license.length - 1;
				var item = ''
				if(inText >= 0){
					item = license.substring(0,inText);
				}
				suffix = before + item;
				suffix = suffix + after;
				this.setSuffix(suffix);
				suffix = '（' + suffix + '）';
			}else{
				suffix = '（' + data.suffix +'）';
			}
			
		}
		if(mainScope){
			mainScope = mainScope + '；';
		}
		text = mainScope + scope + customScope + license ;
		//最后一个标点符号是句号
		text = Scope.wipeUpString(text);
		text = text + suffix;
		return text;
	};
	
	Scope.wipeUpString = function (param){
		
		if(param){
			// 去掉所有空格
			param = param.replace(/\s/g,'');
			// 将句子中连续重复出现的标点符号替换为一个分号
			param = param.replace(/[。|;|；]+/g,'；');		
			// 去掉句首的标点符号
			param = param.replace(/^[。|\.|;|；|,|，|、]*/,'');
			// 去掉句尾的点号和分号（如果有），在句尾加句号
			if(param){
				param = param.replace(/[\.|；]?$/,'。');
			}
			//处理句子中的.
			// 将句子中连续重复(2次以上)出现的.替换为一个分号
			param = param.replace(/[\.]{2,}/g,'；');
			//处理单个的.号，小数不处理，其他的替换成分号
			var index = param.indexOf('.');
			var rgexp = /[0-9]\.[0-9]/; //形如 3.5这样的小数数字
			while(index>0){
				var substr=param.substring(index-1,index+2);
				if(rgexp.test(substr)){
					//数字中的小数点，不处理
				}else{
					//其他情况，替换成分号
					param = param.substring(0,index)+'；'+param.substring(index+1,param.length);
				}
				//查找下一个
				index = param.indexOf('.',index+1);
			}
		}
		return param ;
	};
	 
	 return Scope;
});