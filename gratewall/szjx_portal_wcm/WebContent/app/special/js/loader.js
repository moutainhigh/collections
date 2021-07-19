Ext.ns('wcm.util.loader');
(function(){
	wcm.util.loader = function(){
		this.init();
	};
	wcm.util.loader.prototype = {
		cachedLoadedPath : [],
		basePath : '',
		loadFinished : true,
		/**      
		 * @var onLoad  when load a individual file completed ,      
		 * this event will be fired 
		 * @param string name loaded script file name      
		 */    
		 onLoad : function(name){

		 },     
		/**      
		 * @var onReady when all scripts loaded, this event will be fired      
		 */    
		 onReady : function(){
			
		 },
		 setBasePath : function(_basePath){
			basePath = _basePath;
		 },
		/**      
		 * a empty constructor      
		 */    
		 init : function(){
			var scriptDoms = document.getElementsByTagName('script');
			for(var i=0;i<scriptDoms.length;i++){
				this.cachedLoadedPath.push(scriptDoms[i].src);
			}
			var cssDoms = document.getElementsByTagName('link');
			for(var i=0;i<cssDoms.length;i++){
				this.cachedLoadedPath.push(cssDoms[i].src);
			}
		 },    
		/**      
		 * a empty error handler     
		 */    
		 handlerError : function(e){
			alert(e);
		 }, 
		 isLoaded : function(path){
			for(var i=0;i<this.cachedLoadedPath.length;i++){
				if(this.cachedLoadedPath[i] == path){
					return true;
				}
			}
			return false;
		 },
		/**      
		 * event register      
		 * @param string evt event name      
		 * @param function handler      
		 */    
		on : function(evt, handler){
			switch (evt.toLowerCase()){
				case 'load' :                
					this.onLoad = handler;            
					break;            
				case 'ready' :                
					this.onReady = handler;            
					break;            
				default :
					break;        
			}       
			return true;     
		},     
		/**      
		 * private method      
		 */   
		_load : function(path, callback){
			try{
				if(this.isLoaded(path)){
					callback();
					return;
				}
				var jsPathReg = /\.js$/i;
				var cssPathReg = /\.css$/i;
				var addDom;
				var bJS = false;
				if(jsPathReg.test(path)){
					bJS = true;
					addDom = document.createElement('script');
					addDom.type = "text/javascript";
				}else if(cssPathReg.test(path)){
					addDom = document.createElement('link');
					addDom.setAttribute("rel","stylesheet");
					addDom.setAttribute("type","text/css");
				}
				addDom.setAttribute("charset","utf-8");
				addDom.setAttribute("commonresource","1");
				if(addDom.addEventListener ){
					addDom.addEventListener("load", callback, false);
				}else if(addDom.attachEvent){
					addDom.attachEvent("onreadystatechange", function(){
						if(addDom.readyState == 4 || addDom.readyState == 'complete' || addDom.readyState == 'loaded'){
							callback();                        
						}              
					});
				}
				if(bJS){
					addDom.src = this.basePath + path;
				}else{
					addDom.href = this.basePath + path;
				}
				document.getElementsByTagName("head")[0].appendChild(addDom);
				this.cachedLoadedPath.push(path);
			}catch(e){
				//this.handlerError(e);
				callback();
			}
		},   
	  /**   
		* start loding process      
		* @param array scripts  files want to be loaded      
		*/
		load : function(scripts,callback){
			var total = scripts.length;
			var _self  = this;
			var indicator = arguments[2] || 0;
			if(indicator >= total){
				if(callback)
					callback();
				_self.onReady();
				this.loadFinished = true;
				return true;
			}
			if(this.loadFinished){
				this.loadFinished = false;
			}
			var callee = arguments.callee;
			var script = scripts[indicator];
			this._load(script, function(){
				_self.onLoad(script, callback);
				callee.apply(_self, [scripts,callback,++indicator]);
			});        
			return false;
		},
		isFinished : function(){
			return this.loadFinished;
		}
	}
})();
var ResourceLoader = new wcm.util.loader();
