Event.observe(window,'load',function(){
	//获取参数
	//请求数据
	//初始化界面
	//绑定事件
	var container = $('container');
	//document.cookie='isUsed=0';
	initTips();
	//绑定操作按钮的行为
	Event.observe(container, 'click', function(event){
		event = window.event || event;
		var srcEl = Event.element(event);
		if(!PageHandler.isTitleEl(srcEl))return;
		//绑定事件的行为
		var relationHandler = srcEl.getAttribute("relationHandler");
		if(relationHandler && PageHandler[relationHandler]){
			PageHandler[relationHandler](srcEl);
		}
		return;
	});
	Event.observe(container, 'contextmenu', function(event){
		event = window.event || event;
		var srcEl = Event.element(event);
		var menuEl =  PageHandler.getNearestMenuEl(srcEl);
		if(!menuEl)return;
		//开始绑定事件
		var relationMenu = menuEl.getAttribute("relationMenu");
		if(relationMenu && PageMenu[relationMenu]){
			Element.addClassName(menuEl,"active_menu_el");
			PageMenu[relationMenu](event, menuEl);
		}
		Event.stop(event);
		return;
	});
	Event.observe(container, 'keydown', function(event){
        event = window.event || event;
        if(event.keyCode != 13){
            return true;
        }
        var activeElement = document.activeElement;
        if(!activeElement || (activeElement.name != "optionName" && !Element.hasClassName(activeElement,"titleinput"))){
            return true;
        }
		if(activeElement.name == "optionName"){
			var tempNode = findItem(activeElement, "optionBox");
			if(!tempNode) return true;
			new Insertion.After(tempNode, $('optionTemplate').innerHTML);
			var newNode = Element.next(tempNode);
			var inputs = newNode.getElementsByTagName("input");
			inputs[0].focus();
		}else if(Element.hasClassName(activeElement,"titleinput")){
			activeElement.blur();
		}
     
    });
	//给选型的名称保存绑定事件
	var optionNameEls = document.getElementsByClassName('optionname_text',container);
	for(var i=0;i<optionNameEls.length;i++){
		var optionNameEl = optionNameEls[i];
		Event.observe(optionNameEl,'blur', function(event){
			event = window.event || event;
			var srcEl = Event.element(event);
			optionNameBlur(srcEl);
		});
		Event.observe(optionNameEl,'focus', function(event){
			event = window.event || event;
			var srcEl = Event.element(event);
			optionNameFocus(srcEl);
		});
	}
});
function optionNameBlur(srcEl){
	PageHandler.saveOption(srcEl);
	Element.removeClassName(srcEl,"input_active");
}
function optionNameFocus(srcEl){
	 Element.addClassName(srcEl,"input_active");
}
var basicDataHelper = new com.trs.web2frame.BasicDataHelper();
var PageHandler = {
	serviceId : 'wcm61_advisorcenter',
	titleElClassNames : ["grouptitlesp","steptitlesp"],
	menuElClassNames : ["header","stepheader","grouptitlesp","optionBox"],
	isEventEl : function(element,classNames){
		for(var i=0;i< classNames.length; i++){
			var className = classNames[i];
			if(Element.hasClassName(element,className))
				return true;
		}
		return false;
	},
	getNearestMenuEl : function(element){
		var classNames = this.menuElClassNames;
		for(var i=0;i<classNames.length;i++){
			var targetMenuEl = findItem(element,classNames[i]);
			if(targetMenuEl){
				return targetMenuEl;
			}
		}
		return null;
	},
	isBtnEl : function(element){
		if(!element)
			return false;
		return this.isEventEl(element,this.btnElClassNames);
	},
	isTitleEl : function(element){
		if(!element)
			return false;
		return this.isEventEl(element,this.titleElClassNames);
	},
	addStep : function(){
		var contentBox = $('content');
		var stepTemplate = $('stepTemplate').innerHTML;
		new Insertion.Bottom(contentBox, stepTemplate);
		var newEl = Element.last(contentBox);
		var inputs = newEl.getElementsByTagName("input");
        inputs[0].focus();
	},
	addOptionGroup : function(srcEl){
		var advisorStep = findItem(srcEl,"advisorstep");
		if(!advisorStep)
			return;
		var optionGroupTemplate = $('optionGroupTemplate').innerHTML;
		new Insertion.Bottom(advisorStep, optionGroupTemplate);
		var newEl = Element.last(advisorStep);
		var inputs = newEl.getElementsByTagName("input");
        inputs[0].focus();
	},
	addOption : function(srcEl){
		var groupFieldSet = findItem(srcEl,"groupFieldSet");
		if(!groupFieldSet)
			return;
		var optionTemplate = $('optionTemplate').innerHTML;
		new Insertion.Bottom(groupFieldSet, optionTemplate);
		var newEl = Element.last(groupFieldSet);
		var inputs = newEl.getElementsByTagName("input");
        inputs[0].focus();
	},
	reOrderStep : function(){
		wcm.CrashBoarder.get('reOrderStep').show({
            maskable:true,
            title : '调整步骤的顺序',
            src : './advisor_step_order.jsp',
            width:'500px',
            height:'300px',
            params : {
                AdvisorId : m_nAdvisorId
            },
			callback : function(){
				location.href = location.href;	
			}
        });
	},
	reOrderOptionGroup : function(srcEl){
		var advisorStep = findItem(srcEl,"advisorstep");
		if(!advisorStep)
			return;
		var stepId = advisorStep.id;
		wcm.CrashBoarder.get('reOrderOptionGroup').show({
            maskable:true,
            title : '调整选项组的顺序',
            src : './advisor_optiongroup_order.jsp',
            width:'500px',
            height:'300px',
            params : {
                StepId : stepId
            },
			callback : function(){
				location.href = location.href;	
			}
        });
	},
	reOrderOption : function(srcEl){
		var optionGroup = findItem(srcEl,"optionGroup");
		if(!optionGroup)
			return;
		var groupId = optionGroup.id;
		wcm.CrashBoarder.get('reOrderOption').show({
            maskable:true,
            title : '调整选项的顺序',
            src : './advisor_option_order.jsp',
            width:'500px',
            height:'300px',
            params : {
                OptionGroupId : groupId
            },
			callback : function(){
				location.href = location.href;	
			}
        });
	},
	delOptionGroup : function(delBtnEl){
		var optionGroup = findItem(delBtnEl,"optionGroup");
		if(!optionGroup)
			return; 
		var params = {
			ObjectId : optionGroup.id
		}
		//发送请求保存步骤的信息
		basicDataHelper.call(this.serviceId,"deleteOptionGroup", params, true, function(_oTrans, _json){
			Element.remove(optionGroup);
		});
	},
	delStep : function(delBtnEl){
		var step = findItem(delBtnEl,"advisorstep");
		if(!step)
			return; 
		//获取stepid，发送请求，删除掉，之后将页面中的元素移除
		var params = {
			ObjectId : step.id
		}
		//发送请求保存步骤的信息
		basicDataHelper.call(this.serviceId,"deleteStep", params, true, function(_oTrans, _json){
			Element.remove(step);
		});
	},
	delOption : function(srcEl){
		if(!Element.hasClassName(srcEl,"optionBox")){
			return;
		}
		var optionId = srcEl.id;
		if(optionId == 0){
			return;
		}
		var params = {
			ObjectId : srcEl.id
		}
		//发送请求保存步骤的信息
		basicDataHelper.call(this.serviceId,"deleteOption", params, true, function(_oTrans, _json){
			Element.remove(srcEl);
		});
	},
	editTitle : function(stepTitleEl){
		var currWidth = stepTitleEl.offsetWidth;
		var titleContent = stepTitleEl.innerHTML;
		var inputTitleContent = "<input type='text' value='" + titleContent + "' _value='" + titleContent + "' class='titleinput' onblur='PageHandler.saveTitle(this);return false;'/>";
		stepTitleEl.innerHTML = inputTitleContent;
		var inputEl = Element.first(stepTitleEl);
		inputEl.style.width = (currWidth > 100 ? currWidth : 100) + "px";
		inputEl.focus();
	},
	saveTitle : function(inputTitleEl){
		//判断是步骤的title还是选项组的title
		var currValue = inputTitleEl.value;
		/*
		if(currValue.length <= 0){
			Ext.Msg.alert("名称不能为空！",function(){
				inputTitleEl.focus();
			});
			return false;
		}
		if(currValue.length > 100){
			Ext.Msg.alert("名称的长度不能超过100！",function(){
				inputTitleEl.focus();
			});
			return false;
		}*/
		if(currValue.length <= 0){
			currValue = "请点击输入名称";
		}
		var bSave = true;
		var oldValue = inputTitleEl.getAttribute('_value');
		if(currValue == oldValue)bSave = false;
		var titleEl = findItem(inputTitleEl,"grouptitlesp");
		if(titleEl){
			if(bSave)this.saveGroup(titleEl,currValue);
			else titleEl.innerHTML = oldValue;
			return;
		}
		titleEl = findItem(inputTitleEl,"steptitlesp");
		if(titleEl){
			if(bSave)this.saveStep(titleEl,currValue);
			else titleEl.innerHTML = oldValue;
			return;
		}
	},
	saveStep : function(stepNameEl,newValue){
		var stepEl = findItem(stepNameEl, "advisorstep");
		var selectTypeEl = document.getElementsByClassName("selfieldtype",stepEl)[0];
		var params = {
			ObjectId: stepEl.id,
			STEPNAME: newValue,
			ADVISORID : m_nAdvisorId,
			ISMULTISELECT : selectTypeEl.value
		}
		if(stepEl.id == 0){
			Ext.apply(params,{
				AORDER : -1
			});
		}
		//发送请求保存步骤的信息
		basicDataHelper.call(this.serviceId, "saveStep", params, true, function(_oTrans, _json){
			stepNameEl.innerHTML = newValue;
			stepEl.id = _json.RESULT;
		});
	},
	changeType : function(currSelect){
		var stepNode = findItem(currSelect,"advisorstep");
		var stepId = stepNode.id;
		var params = {
			ObjectId: stepId,
			ADVISORID : m_nAdvisorId,
			ISMULTISELECT : currSelect.value
		}
		//发送请求保存步骤的信息
		basicDataHelper.call(this.serviceId,"saveStep", params,true,function(_oTrans, _json){
		});
	},
	saveGroup : function(groupNameEl,newValue){
		var optionGroupEl = findItem(groupNameEl, "optionGroup");
		var stepEl = findItem(optionGroupEl, "advisorstep");
		var params = {
			ObjectId: optionGroupEl.id,
			OPTIONGROUPNAME: newValue,
			STEPID : stepEl.id
		}
		if(optionGroupEl.id == 0){
			Ext.apply(params,{
				AORDER : -1
			});
		}
		//发送请求保存步骤的信息
		basicDataHelper.call(this.serviceId,"saveOptionGroup",params,true,function(_oTrans, _json){
			groupNameEl.innerHTML = newValue;
			optionGroupEl.id = _json.RESULT;
		});
	},
	saveOption : function(optionNameEl){
		var currValue = optionNameEl.value;
		var oldValue = optionNameEl.getAttribute("_value");
		/*
		if(currValue.length <= 0){
			Ext.Msg.alert("名称不能为空！",function(){
				optionNameEl.focus();
			});
			return false;
		}
		if(currValue.length > 100){
			Ext.Msg.alert("名称的长度不能超过100！",function(){
				optionNameEl.focus();
			});
			return false;
		}*/
		if(currValue == oldValue)return;
		var optionBoxEl = findItem(optionNameEl, "optionBox");
		var optionGroupBoxEl = findItem(optionNameEl, "optionGroup");
		var optionId = optionBoxEl.id;
		if(optionBoxEl && optionGroupBoxEl){
			var params = {
				ObjectId : optionId,
				GroupId : optionGroupBoxEl.id,
				OptionName : currValue
			}
			if(optionId == 0){
				Ext.apply(params,{
					AORDER : -1
				});
			}
			//发送请求保存选项的信息
			basicDataHelper.call(this.serviceId, "saveOption", params, true, function(_oTrans, _json){
				optionBoxEl.id = _json.RESULT;
				optionNameEl.setAttribute("_value", currValue);
			});
		}
	},
	uploadOptionPic : function(optionBox){
		var optionId = optionBox.id;
		if(optionId == 0){
			Ext.Msg.alert("请先新建选项，再设置其图片！");
			return;
		}
		//弹开上传文件的窗口
		wcm.CrashBoarder.get('setOptionPic').show({
            maskable:true,
            title : '设置选项图片',
            src : './option_pictrue_set.jsp',
            width:'350px',
            height:'200px',
            params : {
                OptionId : optionId
            }
        });
	},
	setNotAllowedOption : function(optionBox){
		var optionId = optionBox.id;
		if(optionId == 0){
			Ext.Msg.alert("请先新建选项，再进行此操作！");
			return;
		}
		//弹开选择选项的窗口
		wcm.CrashBoarder.get('setNotAllowedOption-Win').show({
            maskable:true,
            title : '设置不能选择的项',
            src : './option_select.jsp',
            width:'700px',
            height:'350px',
            params : {
                OptionId : optionId
            },
			callback : function(args){
				var selectedValus = args;
				basicDataHelper.call("wcm61_advisorcenter","saveOption", {
					ObjectId : optionId,
					NOTALLOWEDSELECTOPTIONS : selectedValus
				},true,function(_trans, _json){
					
				});	
				this.close();
			}
        });
	},
	setLastOptions : function(optionBox){
		var optionId = optionBox.id;
		if(optionId == 0){
			Ext.Msg.alert("请先新建选项，再进行此操作！");
			return;
		}
		//弹开选择选项的窗口
		wcm.CrashBoarder.get('setLastOption').show({
            maskable:true,
            title : '设置承接的上一个问题',
            src : './option_select.jsp',
            width:'700px',
            height:'350px',
            params : {
                OptionId : optionId,
				SelectFromOtherSteps : true
            },
			callback : function(args){
				var selectedValus = args;
				basicDataHelper.call("wcm61_advisorcenter","saveOption", {
					ObjectId : optionId,
					LASTOPTIONS : selectedValus
				},true,function(_trans,_json){
					
				});	
				this.close();
			}
        });
	},
	setGroupLastOptions : function(optionGroupNameEl){
		var optionGroup = findItem(optionGroupNameEl,"optionGroup");
		var groupId = optionGroup.id;
		//弹开选择选项的窗口
		wcm.CrashBoarder.get('setGroupLastOption').show({
            maskable: true,
            title : '设置承接的上一个问题',
            src : './option_select.jsp',
            width:'700px',
            height:'350px',
            params : {
                GroupId : groupId,
				SelectFromOtherSteps : true
            },
			callback : function(args){
				var selectedValus = args;
				basicDataHelper.call("wcm61_advisorcenter","saveOptionGroup", {
					ObjectId : groupId,
					LASTOPTIONS : selectedValus
				},true,function(_trans,_json){
					
				});	
				this.close();
			}
        });
	},
	setSearchConditon : function(optionBox){
		openSearchConditionWindow(optionBox);	
	},
	toogleDataType : function(dataTypeEl){
		var oldTypeEl = document.getElementsByClassName("oldSelectType",dataTypeEl)[0];
		var selTypeEl = document.getElementsByClassName("selType",dataTypeEl)[0];
		Element.show(selTypeEl);
		Element.hide(oldTypeEl);
	},
	setStepDesc : function(srcEl){
		var stepNode = findItem(srcEl,"advisorstep");
		var stepId = stepNode.id;
		if(stepId == 0)return;
		var titleEl = document.getElementsByClassName("steptitlesp",srcEl)[0];
		openObjDescWindow(titleEl,stepId,'输入步骤描述信息','STEPDESC','savestep');
	},
	setGroupDesc : function(srcEl){
		var groupNode = findItem(srcEl,"optionGroup");
		var groupId = groupNode.id;
		if(groupId == 0)return;
		if(!Element.hasClassName(srcEl,"grouptitlesp"))return;
		openObjDescWindow(srcEl,groupId,'输入选项组描述信息','OPTIONGROUPDESC','saveoptiongroup');
	},
	setOptionDesc : function(optionBox){
		var optionId = optionBox.id;
		if(optionId == 0){
			return;
		}
		var titleEl = document.getElementsByClassName("optionname_text",optionBox)[0];
		openObjDescWindow(titleEl,optionId,'输入选项描述信息','OPTIONDESC','saveoption');
	}
}
var PageMenu = {
	getSimpleMenu : function(){
		if(!window.simpleMenu){
			simpleMenu = new com.trs.menu.SimpleMenu();
			simpleMenu.addListener('hide', function(){
				var activeEls = document.getElementsByClassName('active_menu_el');
				if(activeEls){
					for(var i=0;i<activeEls.length;i++){
						Element.removeClassName(activeEls[i],"active_menu_el");
					}
				}
			});
		}
		return simpleMenu;
	},
	showAdvisorMenu : function(event,srcEl){
		var simpleMenu = this.getSimpleMenu();
		var oOpers = [{
			oprKey : 'addStep',
			desc : "添加步骤",
			cmd : function(){
				PageHandler.addStep();
			}
		},{
			oprKey : 'setStepOrder',
			desc : "设置步骤顺序",
			cmd : function(args){
				PageHandler.reOrderStep();
			}
		}]
		simpleMenu.show(oOpers, {
			x : Event.pointerX(event),
			y : Event.pointerY(event),
			srcEl : srcEl
		});
	},
	showStepMenu : function(event,srcEl){
		var simpleMenu = this.getSimpleMenu();
		var oOpers = [{
			oprKey : 'setStepDesc',
			desc : "设置步骤描述",
			cmd : function(args){
				var srcEl = args.srcEl;
				PageHandler.setStepDesc(srcEl);
			}
		},{
			oprKey : 'addGroup',
			desc : "添加选项组",
			cmd : function(args){
				var srcEl = args.srcEl;
				PageHandler.addOptionGroup(srcEl);
			}
		},{
			oprKey : 'orderGroup',
			desc : "设置选项组顺序",
			cmd : function(args){
				var srcEl = args.srcEl;
				PageHandler.reOrderOptionGroup(srcEl);
			}
		},{
			oprKey : 'deleteStep',
			desc : "删除步骤",
			cmd : function(args){
				var srcEl = args.srcEl;
				PageHandler.delStep(srcEl);
			}
		}]
		simpleMenu.show(oOpers, {
			x : Event.pointerX(event),
			y : Event.pointerY(event),
			srcEl : srcEl
		});
	},
	showGroupMenu : function(event,srcEl){
		var simpleMenu = this.getSimpleMenu();
		var oOpers = [{
			oprKey : 'setGroupDesc',
			desc : "设置选项组描述",
			cmd : function(args){
				var srcEl = args.srcEl;
				PageHandler.setGroupDesc(srcEl);
			}
		},{
			oprKey : 'addOption',
			desc : "添加选项",
			cmd : function(args){
				var srcEl = args.srcEl;
				PageHandler.addOption(srcEl);
			}
		},{
			oprKey : 'setLastOption',
			desc : "设置承接的上一个问题",
			cmd : function(args){
				var srcEl = args.srcEl;
				PageHandler.setGroupLastOptions(srcEl);
			}
		},{
			oprKey : 'setOptionOrder',
			desc : "设置选项顺序",
			cmd : function(args){
				var srcEl = args.srcEl;
				PageHandler.reOrderOption(srcEl);
			}
		},{
			oprKey : 'delGroup',
			desc : "删除选项组",
			cmd : function(args){
				var srcEl = args.srcEl;
				PageHandler.delOptionGroup(srcEl);
			}
		}]
		simpleMenu.show(oOpers, {
			x : Event.pointerX(event),
			y : Event.pointerY(event),
			srcEl : srcEl
		});
	},
	showOptionMenu : function(event,srcEl){
		var simpleMenu = this.getSimpleMenu();
		var oOpers = [{
			oprKey : 'setOptionDesc',
			desc : "设置选项描述",
			cmd : function(args){
				var srcEl = args.srcEl;
				PageHandler.setOptionDesc(srcEl);
			}
		},{
			oprKey : 'uploadPic',
			desc : "上传图片",
			cmd : function(args){
				var srcEl = args.srcEl;
				PageHandler.uploadOptionPic(srcEl);
			}
		},{
			oprKey : 'setSearchCondition',
			desc : "设置查询条件",
			cmd : function(args){
				var srcEl = args.srcEl;
				PageHandler.setSearchConditon(srcEl);
			}
		},{
			oprKey : 'setLastFAQ',
			desc : "设置承接的上一个问题",
			cmd : function(args){
				var srcEl = args.srcEl;
				 PageHandler.setLastOptions(srcEl);
			}
		},{
			oprKey : 'setNotAllowedOption',
			desc : "设置互斥项",
			cmd : function(args){
				var srcEl = args.srcEl;
				PageHandler.setNotAllowedOption(srcEl);
			}
		},{
			oprKey : 'deleteStep',
			desc : "删除选项",
			cmd : function(args){
				var srcEl = args.srcEl;
				PageHandler.delOption(srcEl);
			}
		}]
		simpleMenu.show(oOpers, {
			x : Event.pointerX(event),
			y : Event.pointerY(event),
			srcEl : srcEl
		});
	}
}
function initTips(){
	var hasUsed = getCookieValue("isUsed");
	if(hasUsed && hasUsed == '1'){
		Element.hide($("operationTip"));
	}
}
function getCookieValue(_cookieKey){
	var cookieValue = '';
	var arrStr = document.cookie.split(";");
	for(var k = 0; k < arrStr.length; k++){
		var temp = arrStr[k].split("=");
		if(temp[0].trim() == _cookieKey){
			cookieValue = temp[1];
			break;
		}
	}
	return cookieValue;
}
function closeTip(){
	var oOperationTip = $("operationTip");
	if(oOperationTip && oOperationTip.style.display==""){
		oOperationTip.style.display="none";
		Element.hide(oOperationTip);	
		//设置当前cookie
		document.cookie='isUsed=1';
	}
}
function openSearchConditionWindow(optionBox){
	var optionId = optionBox.id;
	if(optionId == 0){
		return;
	}
	var searchCondition = optionBox.getAttribute("searchCondition");
	var sHtml = String.format($('inputarea').innerHTML, searchCondition);
	var cb = new wcm.CrashBoard({
		id : 'edit_search_content',
		title : '填写选项查询条件',
		html : sHtml,
		renderTo : document.body,
		width: '380px',
		height : '140px',
		maskable : true, 
		btns : [
			{
				text :  '确定', 
				cmd : function(){
					var dom = this.getElement();
					var newContent = dom.getElementsByTagName("TEXTAREA")[0].value;
					if(searchCondition != newContent){
						var params = {
							ObjectId : optionId,
							SEARCHCONDITION : newContent
						}
						basicDataHelper.call('wcm61_advisorcenter', 'saveOption', params, true, function(){
							optionBox.setAttribute("searchCondition",newContent);
							this.close();
						}.bind(this));
					}else{
						this.close();
					}
					return false;
				}
			},
			{
				text : '取消'
			}
		]
	});
	cb.show();
	var dom = cb.getElement();
	dom.getElementsByTagName("TEXTAREA")[0].focus(); 
}

function openObjDescWindow(titleObj,objId,titleinfo,fieldName,methodname){
	if(!titleObj)return;
	var oldValue = titleObj.getAttribute("title");
	var sHtml = String.format($('inputarea').innerHTML, oldValue);
	var cb = new wcm.CrashBoard({
		id : 'edit_title',
		title : titleinfo,
		html : sHtml,
		renderTo : document.body,
		width: '380px',
		height : '140px',
		maskable : true, 
		btns : [
			{
				text :  '确定', 
				cmd : function(){
					var dom = this.getElement();
					var newContent = dom.getElementsByTagName("TEXTAREA")[0].value;
					if(oldValue != newContent){
						var params = {
							ObjectId : objId
						}
						params[fieldName] = newContent;
						basicDataHelper.call('wcm61_advisorcenter', methodname, params, true, function(){
							titleObj.setAttribute("title",newContent);
							this.close();
						}.bind(this));
					}else{
						this.close();
					}
					return false;
				}
			},
			{
				text : '取消'
			}
		]
	});
	cb.show();
	var dom = cb.getElement();
	dom.getElementsByTagName("TEXTAREA")[0].focus(); 
}