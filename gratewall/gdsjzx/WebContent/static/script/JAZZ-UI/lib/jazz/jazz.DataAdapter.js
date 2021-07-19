(function($) {
	
	/**
	 * @version 1.0
	 * @name jazz.DataAdapter
	 * @description 
	 */
	$.DataAdapter = {
		submit: function(params, $this){
	 		var adapter = new $.dataAdapterObject();
	 		adapter.submit(params, $this);
		},
//		query: function(params){
//	 		var adapter = new $.dataAdapterObject();
//	 		adapter.query(params);
//		},	
		query: function(params){
	 		var adapter = new $.dataAdapterObject();
	 		adapter.createAdapter();
	 		adapter.options.components = params['components'];
	 		adapter.componentsData2();
	 		return adapter.dataArray;
		},
		vtypeset: ['gridpanel', 'formpanel', 'querypanel', 'comboxtreefield', 'comboxfield', 'icon' , 'attachment', 'boxlist', 'tree']
	};

	$.dataAdapterObject = function() {
		
		 this.options = {
			 sourceobject: null,   //组件对象   
		     contextpath: '',
			
			 name: null,   //组件对应的名称
			 vtype: null,  //vtype类型
			 
			 components: null, 
			
			 url: null,
			 type: 'post',
	    	 data: null,        //数据
			 //dataType: 'json',  //数据类型

			 success: null,    
		     error: null,
		     callback: null,    //回调函数
		     params: null,      //参数{}
		     pageparams: null,  //分页所需要的必要参数
		     queryparams: null, //查询条件必要参数
		     
		     showloading: null    //默认
	     };
	 };
	 
	 $.dataAdapterObject.prototype = {
	    
		//UI调用
	    createAdapter: function(){
			this.jazzData = new Object();
			this.dataArray = new Array();
			this.jazzData["data"] = this.dataArray;
	 	},
	 	
	 	/**
	 	 * @desc 添加参数
	 	 * @param {name}  key值 
	 	 * @param {value} value值
	 	 */
	 	addAttr: function(name, value) {
	 		if (!!name && !!vtype) {
	 			var temp = {};
	 			temp['name'] = name;
	 			temp['vtype'] = 'attr';
	 			temp['data'] = value;
	 			this.dataArray[name] = value;
	 		}
	 	},	 	
	 	
	 	/**
	 	 * 添加form格式数据
	 	 * @param name
	 	 * @param vtype
	 	 * @param treeData
	 	 */		 	
	 	addForm: function(name, vtype, formData) {
	 		this.dataArray.push(formData);
	 	},

	 	/**
	 	 * 添加grid格式数据
	 	 * @param name
	 	 * @param vtype
	 	 * @param treeData
	 	 * @returns {Object}
	 	 */	 	
	 	addGrid: function(name, vtype, gridData) {
	 		var obj = {};
	 		obj["name"] = name;
	 		obj["vtype"] = vtype;
	 		obj["data"] = {};
	 		obj["data"]["rows"] = gridData || [];
	 		this.dataArray.push(obj);
	 	},
	 	
	 	/**
	 	 * 添加select格式数据
	 	 * @param name
	 	 * @param vtype
	 	 * @param treeData
	 	 * @returns {Object}
	 	 */	 	
	 	addSelect: function(name, vtype, selectData){
	 		this.dataArray.push(selectData);
	 	},
	 	
	 	/**
	 	 * 添加tree格式数据
	 	 * @param name
	 	 * @param vtype
	 	 * @param treeData
	 	 * @returns {Object}
	 	 */
	 	addTree: function(name, vtype, treeData) {
	 		this.dataArray.push(treeData);
	 	},	 	
	 	
	 	/**
	 	 * 添加icon格式数据
	 	 * @param name
	 	 * @param vtype
	 	 * @param iconData
	 	 * @returns {Object}
	 	 */
	 	addIcon: function(name, vtype, iconData){
	 		var obj = new Object();
	 		this.dataArray.push(iconData);
	 		return obj;	 		
	 	},	 	

	 	/**
	 	 * @desc 获取提交数据的string格式
	 	 * @returns string
	 	 */
	 	getJazzDataString: function() {
	 		return jazz.jsonToString(this.jazzData);
	 	},
	 	
	 	/**
	 	 * @desc 获取提交的JSON数据
	 	 * @returns {}
	 	 */
	 	getSubmitJsonData: function() {
	 		return this.jazzData;
	 	},
	 	
	 	/**
	 	 * @desc ajax提交方法
	 	 */
	 	ajaxSubmit: function(){
	 		var $this = this;
	 		
	 		//返回参数attr
	 		var initRes = {
 				obj: {},
 				setAttr: function(key, value){
 					this.obj[key] = value;
 				},
 				getAttr: function(key){
 					return this.obj[key];
 				}
	 		};	 		

	 		var data = this.getJazzDataString(); //this.getSubmitJsonData();

	 		//jazz.log("====提交数据格式====="+data);
	 		
			$.ajax({
				url: $this.options.url,
				type: $this.options.type,
				dataType: 'json',
				data: {"postData": data},
				async: true,
				contentType: "application/x-www-form-urlencoded; charset=utf-8", 
				error: $this.options.error || function(responseObj) {
				    if(responseObj["responseText"]){
				    	var err = jazz.stringToJson(responseObj["responseText"]);
				    	if(err['exceptionMes']){
				    		jazz.error('<font color="blue" >错误信息</font> : ' + err['exceptionMes']);				    					    		
				    	}else{
				    		jazz.error('与服务器连接断开，请尝试重新登录或与管理员联系!');
				    	}
				    }else{
				    	jazz.error('与服务器连接断开，请尝试重新登录或与管理员联系!');
				    }
				    return false;
				},
				complete: function(){
					if(((jazz.config.submitShowLoading == true && $this.options.showloading == null) || $this.options.showloading == true) && $this.modality){
						$this.modality.remove();
					}
					if($.isFunction($this.options.complete)){
						$this.options.complete.apply(this);
					}
				},
				success: function(responseObj){
					if(responseObj['message'] == "noSessionRight"){
						return false;
					}
					if(responseObj['exception']){
						jazz.error('<font color="blue" >错误名称</font> : ' + responseObj['exception']
		                        + '<br><font color="blue" >错误信息</font> : ' + responseObj['exceptionMes']);
						return false;
					}
					//返回给回调函数的数据
					var callbackdata = null;
					
					var jsonData = responseObj['data'];
				    if(typeof(jsonData) == "object"){
				    	if(jsonData != null && $.isArray(jsonData)){
				    		var newJsonData = [];
				    		$.each(jsonData, function(i, jsonObj){
				    			var vtype = jsonObj['vtype'];
								if(vtype=='attr'){
									initRes.setAttr(jsonObj['name'], jsonObj['data']);
								}
								if($.inArray(vtype, $.DataAdapter.vtypeset) >= 0){
									newJsonData.push(jsonData[i]);
								}
				    		});
				    		//数组中只有一个对象时，返回对象
				    		if(newJsonData.length == 1){
				    			callbackdata = newJsonData[0];
				    		}
				    	}
				    }else{
				    	//与定义格式不符时，原样返回
				    	callbackdata = jsonData;
				    }
					//jazz.log("callbackdata===="+JSON.stringify(callbackdata));
					if($.isFunction($this.options.callback)){
						$this.options.callback.call(this, callbackdata, $this.options.sourceobject, initRes);
					}				
				}
			}); 		
	 	},
		
	 	/**
	 	 * @desc 创建UI组件的JSON数据
	 	 */
		componentsData: function () {
			var flag=true;
			//组件集合
			var components = this.options.components;
			if($.isArray(components)){			
				for(var i=0, len=components.length; i<len; i++){
					var compJson = $('div[name="'+components[i]+'"]');
					var vtype = compJson.attr('vtype');
					var name = compJson.attr('name');
					//验证
					if(vtype=='formpanel')
					{
						$('div[name="'+components[i]+'"]').find('div[vtype]').each(function(index,element){
							var el = $(element);
							var vtype=el.attr('vtype'), $field = el.data(vtype);
							var options = $field.options;
							var rule=options.rule;
							if(rule && vtype!="gridpanel" && vtype!="hiddenfield" && vtype!="checkboxfield" && vtype!="radiofield" && options.isrule){//过滤掉formpanel中嵌套的gridpanel
								var msg = options.msg;
								var f = jazz.doTooltip($field, $field.getText(), rule, msg);
							    if(f==false){
							    	flag=false;
							    	$field._validateStyle(f);
							    }
							}
						});
					}
					if(!flag)
					{return false;}

					//是否在vtype集合中定义，如果不存在则不解析
					if($.inArray(vtype, $.DataAdapter.vtypeset) >= 0){
						if(vtype=='formpanel' || vtype=='querypanel'){
							var data = compJson[vtype]('getValue');
							this.addForm(name, vtype, data);
						}else if(vtype=='gridpanel'){
							var data = compJson[vtype]('getSelection');
							this.addGrid(name, vtype, data);
						}
						
//						else if(vtype=='icon'){
//							this.addIcon(this.options.name, vtype, data);
//						}else if(vtype=='comboxtreefield'){
//							var data = compJson[vtype]('getValue');
//							this.addTree(this.options.name, vtype, data);
//						}					
						
					}else{
						alert('解析的vtype类型错误！！！ ');
						return false;
					}
			
				}
				
			}
			
			var qparams = this.options.queryparams; 
			if(qparams){
				var $this = this;
				$.each(qparams, function(){
					$this.dataArray.push(this);
				});
			};
			
			//分页提交的必要参数
			var params = this.options.pageparams;
			if(params && typeof(params)=='object'){
				for(var p in params){
					var param = {};
					param['vtype']='pagination';
					param['name']=p;
					param['data']=params[p];
					this.dataArray.push(param);
			    }
			}			
			
			//解析需要提交的参数
			var params = this.options.params;
			if(params && typeof(params)=='object'){
				for(var p in params){
					var param = {};
					param['vtype']='attr';
					param['name']=p;
					param['data']=params[p];
					this.dataArray.push(param);
			    }
			}
			return flag;
		},
		
	 	/**
	 	 * @desc 创建UI组件的JSON数据
	 	 */
		componentsData2: function () {
			
			var params = this.options.params;
			if(params && typeof(params)=='object'){
				for(var p in params){
					var param = {};
					param['vtype']='attr';
					param['name']=p;
					param['data']=params[p];
					this.dataArray.push(param);
			    }
			}			
			
			//组件集合
			var components = this.options.components;
			if($.isArray(components)){
				for(var i=0, len=components.length; i<len; i++){
					var name = components[i];
					var compJson = $('div[name="'+name+'"]');
					var vtype = compJson.attr('vtype');

					//是否在vtype集合中定义，如果不存在则不解析
					if(vtype=='formpanel' || vtype=='querypanel'){
						var data = compJson[vtype]('getValue');
						this.addForm(this.options.name, vtype, data);
					}else
					if(vtype=='textfield' || vtype=='comboxfield' || vtype=='datefield' || vtype=='comboxtreefield'
					|| vtype=='checkboxfield' || vtype=='radiofield' || vtype=='autofield' || vtype=='numberfield'
					|| vtype=='passwordfield'){
							var data = compJson[vtype]('getValue');
							var param = {};
							param['vtype']='attr';
							param['name']=name;
							param['data']=data;
							this.dataArray.push(param);
					}
			
				}
			}
		},		
		
//		/**
//		 * @desc 查询方法
//		 * @param {params} ajax提交的参数对象
//		 * @param {$sourcethis} 该参数会通过回调函数原样返回
//		 */		
//		query: function(params){
//			 this.createAdapter();
//			 $.extend(this.options, params);
//			 this.componentsData2();
//			 //this.ajaxSubmit();
//		},
		
		/**
		 * @desc 提交方法
		 * @param {params} ajax提交的参数对象
		 * @param {$sourcethis} 该参数会通过回调函数原样返回
		 */
		submit: function(params, $sourcethis){
			$.extend(this.options, params);
			if((jazz.config.submitShowLoading == true && this.options.showloading == null) || this.options.showloading == true){
				this.modality = $('<div id="submit_"'+jazz.getRandom()+'"></div>').css({
                     'position': 'fixed',
                     'top': '0px',
                     'left': '0px',
                     'width': jazz.windowWidth(),
                     'height': jazz.windowHeight(),
                     'z-index': ++jazz.config.zindex
				}).appendTo(document.body).loading({blank: true});			
			}
			//数据提交
			var flag=true;
            this.createAdapter();
            this.options.sourceobject = $sourcethis;
        	flag=this.componentsData();
        	if(String(flag)=='false'){
        		if(((jazz.config.submitShowLoading == true && this.options.showloading == null) || this.options.showloading == true) && this.modality){
        			this.modality.remove();
        		}
        		return false;
        	}
        	this.ajaxSubmit();
		}
	 
	 };
	 
     $(function(){
		  //返回参数attr
		  var initRes = {
			  obj: {},
			  setAttr: function(key, value){
				  this.obj[key] = value;
			  },
			  getAttr: function(key){
				  return this.obj[key];
			  }
		 };
		 var initPageData = $('#OptimusData').attr('data');
		 if(typeof(initPageData) != 'object'){
			 if(initPageData){
				 initPageData = jazz.stringToJson(initPageData);
			 }
		 }
		 if(initPageData){
			 var jsonData = initPageData['data'];
			 if($.isArray(jsonData)){
					$.each(jsonData, function(i, jsonObj){
						var vtype = jsonObj['vtype'], name = jsonObj['name'];
						if(vtype=='attr'){
							initRes.setAttr(name, jsonObj['data']);
						}
//						if($.inArray(vtype, $.DataAdapter.vtypeset) >= 0){
//							var componentObj = $('div[name="'+name+'"]').data(vtype);
//							if(componentObj){
//								if(vtype=="comboxfield"){
//									componentObj.setOptions(jsonObj);
//								}else{
//									componentObj.setValue(jsonObj);
//								}
//							}
//						}
					});			 
			 }
			 if($.isFunction(window.initData)){
			     //页面初始化数据
			     initData(initRes);
		     }
		 }
     });

})(jQuery);