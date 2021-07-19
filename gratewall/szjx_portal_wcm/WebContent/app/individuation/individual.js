//操作面板的设置
var IndividualDealer = {
	serviceName : 'wcm6_individuation',
	saveMethod : 'save',
	leftNavTree : null,
	form : null,

	saveAllConfigs : function(){
		if(window.globalValid === false) return;
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		var aCombine = new Array();
		
		if($('uploadImgBtn') && ($('uploadImgBtn').disabled == false)) {
			if(!confirm(wcm.LANG['INDIVIDUAL_32'] || '最新浏览的图片没执行上传.您确定不上传图片?'))
				return false;
		}

		//1.获取基本数据
		var elements = Form.getElements(this.form);
		for (var i = 0; i < elements.length; i++){			
			var element = elements[i];
			if(element.getAttribute("ignore") || element.getAttribute("changed") == false) continue;
			var params = {
				objectId	: element.getAttribute("objectId") || 0,
				paramName	: element.name
			};	
			switch(element.type.toLowerCase()){
				case "checkbox" :
					Object.extend(params, {paramValue : element.checked ? "1" : "0"});
					break;
				case "radio" :
					Object.extend(params, {paramValue : $$F(element.name)});						
					while(elements[i+1] && (elements[i+1].name == elements[i].name)){
						i++;
					}
					if(params.paramValue == "") continue;
					break;
				default : 
					Object.extend(params, {paramValue : element.value});
			}
			aCombine.push(oHelper.Combine(this.serviceName, this.saveMethod, params));
		}

		//2.获取额外数据
		var treeNodes = this.leftNavTree.getElementsByTagName("div");
		for (var i = 0; i < treeNodes.length; i++){
			var tabType = treeNodes[i].getAttribute("tabType");
			if(tabType == null){
				continue;
			}
			tabType = tabType.charAt(0).toUpperCase() + tabType.substr(1);
			try{
				eval(tabType + "TabDealer.setSaveParams(aCombine, oHelper);");
			}catch(error){
				//alert(error.message);
			}
		}
		
		//3.保存数据
		oHelper.MultiCall(aCombine, function(){	
			//topHandler.location.reload();
			if(topHandler.location.search){
				var params = topHandler.location.search.parseQuery();
			}else{
				var params = {};
			}
			params["r"] = new Date().getTime();
			var href = "http://"+topHandler.location.host+topHandler.location.pathname+"?"+$toQueryStr(params);
			topHandler.location.href = href;
			window.close();
		});
	},

	initConfigs : function(container){	
		var configs = topHandler.m_CustomizeInfo;
		// 使用下面的方法，丢掉了id为formId中的input，导致每次进行个性化定制都会多出这个设置
		// var elements = this.getElements(container);
		// 改为Form.getElements(container)方法
		var elements = Form.getElements(container);
		for (var i = 0; i < elements.length; i++){					
			var element = elements[i];
			if(!configs[element.name]) continue;
			switch(element.type.toLowerCase()){
				case "checkbox" :
					element.checked = configs[element.name].paramValue == "1" ? true : false;					
					break;
				case "radio" :
					if(elements[i].value == configs[element.name].paramValue){
						elements[i].checked = true;
					}	
					break;
				default : 
					element.value = configs[element.name].paramValue;
					element.setAttribute("oldValidValue", element.value);
			}
			// 只设置非系统级的个性化定制
			if(!configs[element.name].isSystem || configs[element.name].isSystem=== "0"){
				element.setAttribute("objectId", configs[element.name].objectId);
			}
		}
	},

	getElements : function(container){
		var inputs = $A(container.getElementsByTagName("input"));
		var inputEls = [];
		for(var index=0; index<inputs.length; index++){
			if(inputs[index].type=='file')continue;
			inputEls.push(inputs[index]);
		}
		var selects = $A(container.getElementsByTagName("select"));
		var textAreas = $A(container.getElementsByTagName("textarea"));
		return inputEls.concat(selects, textAreas);
	},

	initIndividuationEvent : function(){
		//邦定树节点的事件
		Event.observe(this.leftNavTree, 'click', function(event){
			var event = window.event || event;
			var srcElement = Event.element(event);

			if(srcElement.className.indexOf("treeNode") < 0){//点击的不是树节点
				return;
			}
			var tabType = srcElement.getAttribute("tabType");
			if(IndividualDealer.lastClickTreeNode){
				Element.removeClassName(IndividualDealer.lastClickTreeNode, "treeNodeActive");
				var lastTabType = IndividualDealer.lastClickTreeNode.getAttribute("tabType");
				if(lastTabType){
					Element.hide(lastTabType + "Div");
				}
			}
			IndividualDealer.lastClickTreeNode = srcElement;//记录最后一次点击的树节点

			if(srcElement.className.indexOf("treeNodePage") < 0){
				var ulDom = Element.next(srcElement);
				if(srcElement.className.indexOf("treeNodeOpen") >= 0){
					Element.removeClassName(srcElement, "treeNodeOpen");
					Element.addClassName(srcElement, "treeNodeClose");                    
					Element.hide(ulDom);
				}else{
					Element.removeClassName(srcElement, "treeNodeClose");
					Element.addClassName(srcElement, "treeNodeOpen");  
					Element.show(ulDom);                    
				}
			}
			Element.addClassName(srcElement, "treeNodeActive");

			if(tabType){
				Element.show(tabType + "Div");
				try{
					eval(tabType + "TabLoad();")
				}catch(error){
					
				}
			}
		});	
		var elements = this.getElements(this.form);
		for (var i = 0; i < elements.length; i++){
			element = elements[i];
			var validation = element.getAttribute("validation");
			var validtiondesc = element.getAttribute("validation_desc");
			if(validation){
				eval("validation = {" + validation + "};");
				if(validtiondesc)validation.validtiondesc=validtiondesc;
				if(validation.type == "number"){
					Event.observe(element, 'blur', function(validation, event){
						if(Position.within($('cancelBtn'),
								Event.pointerX(event),
								Event.pointerY(event)) || Event.pointerY(event) < 0){
							return;
						}
						var oldValidValue = this.getAttribute("oldValidValue") || '';
						if(!this.value.match(/^\d{1,}$/)){
							Ext.Msg.alert(String.format((wcm.LANG['INDIVIDUAL_7'] || "{0}的输入格式错误,必须为数字"), validation.validtiondesc), function(){
								this.value = oldValidValue;
								if(this.select){
									(this.select)();
								}else{
									(Ext.emptyFn)();
								}
								this.focus();
							}.bind(this));																	
							window.globalValid = false;
							return false;
						}
						if(validation.min && parseInt(this.value) < validation.min){
							Ext.Msg.alert(String.format((wcm.LANG['INDIVIDUAL_8'] || "{0}的输入值小于最小值:{1}"), validation.validtiondesc, validation.min), function(){
								this.value = oldValidValue;
								if(this.select){
									(this.select)();
								}else{
									(Ext.emptyFn)();
								}
								this.focus();
							}.bind(this));
							window.globalValid = false;
							return false;
						}
						if(validation.max && parseInt(this.value) > validation.max){
							Ext.Msg.alert(String.format((wcm.LANG['INDIVIDUAL_9'] || "{0}的输入值大于最大值:{1}"), validation.validtiondesc, validation.max), function(){
								this.value = oldValidValue;
								if(this.select){
									(this.select)();
								}else{
									(Ext.emptyFn)();
								}
								this.focus();
							}.bind(this));
							window.globalValid = false;
							return false;
						}
						window.globalValid = true;
						this.setAttribute("oldValidValue", this.value);
					}.bind(element, validation));
				}
			}
		}
	},
	initIndividuationValue : function(){
		this.initConfigs(this.form);		
		$((getParameter("path") || 'login') + "TreeNode").fireEvent("onclick");
//		$('loginTreeNode').fireEvent("onclick");
	},

	initIndividuation : function(){
		//ValidationHelper.initValidation();
		this.leftNavTree = $("leftNavTree");
		this.form = $("individuationId");
		this.initIndividuationEvent();
		this.initIndividuationValue();
	}
};
Event.observe(window, 'load', IndividualDealer.initIndividuation.bind(IndividualDealer));
Object.extend(Event, {
  pointerX: function(event) {
    return event.pageX || (event.clientX +
      (document.documentElement.scrollLeft || document.body.scrollLeft));
  },

  pointerY: function(event) {
    return event.pageY || (event.clientY +
      (document.documentElement.scrollTop || document.body.scrollTop));
  }
});