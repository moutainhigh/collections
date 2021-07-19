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
		if(!Element.hasClassName(srcEl,'grouptitlesp'))return;
		PageHandler.editTitle(srcEl);
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
		if(activeElement.name == 'optionName'){
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
			optionBlur(srcEl);
		});
		Event.observe(optionNameEl,'focus', function(event){
			event = window.event || event;
			var srcEl = Event.element(event);
			optionFocus(srcEl);
		});
	}
});
function optionBlur(srcEl){
	PageHandler.saveOption(srcEl);
	Element.removeClassName(srcEl,"input_active");
}
function optionFocus(srcEl){
	Element.addClassName(srcEl,"input_active");
}
var basicDataHelper = new com.trs.web2frame.BasicDataHelper();
var PageHandler = {
	serviceId : 'wcm61_filtercenter',
	menuElClassNames : ["header","grouptitlesp","optionBox"],
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
	addOptionGroup : function(){
		var contentBox = $('content');
		var groupTemplate = $('optionGroupTemplate').innerHTML;
		new Insertion.Bottom(contentBox, groupTemplate);
		var newEl = Element.last(contentBox);
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
	setDisabledOptions : function(){
		//弹开选择选项的窗口
		wcm.CrashBoarder.get('setDisabledOptions-Win').show({
            maskable:true,
            title : '设置不能选择的项',
            src : './filter_option_select.jsp',
            width:'700px',
            height:'350px',
            params : {
                filterId : m_nFilterId
            },
			callback : function(args){
				var selectedValus = args;
				basicDataHelper.call("wcm61_filtercenter","saveFilter", {
					ObjectId : m_nFilterId,
					disabledOptions : selectedValus
				},true,function(_trans, _json){
					
				});	
				this.close();
			}
        });
	},
	reOrderOptionGroup : function(){
		wcm.CrashBoarder.get('reOrderOptionGroup').show({
            maskable:true,
            title : '调整选项组的顺序',
            src : './filter_group_order.jsp',
            width:'500px',
            height:'300px',
            params : {
                FilterId : m_nFilterId
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
            src : './filter_option_order.jsp',
            width:'500px',
            height:'300px',
            params : {
                GroupId : groupId
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
		basicDataHelper.call(this.serviceId,"deleteFilterOptionGroup", params, true, function(_oTrans, _json){
			Element.remove(optionGroup);
		});
	},
	setOptionGroupOtherProperties : function(srcEl){
		var optionGroup = findItem(srcEl,"optionGroup");
		if(!optionGroup)
			return; 

		wcm.CrashBoarder.get('setOptionGroupOtherProperties-win').show({
            maskable:true,
            title : '设置其他属性',
            src : './filter_option_group_properties.jsp',
            width:'500px',
            height:'120px',
            params : {
                GroupId : optionGroup.id
            }			
		});
	},
	delOption : function(srcEl){
		if(!Element.hasClassName(srcEl,"optionBox")){
			return;
		}
		var optionId = srcEl.id;
		if(optionId == 0)
			return;
		var params = {
			ObjectId : srcEl.id
		}
		//发送请求保存步骤的信息
		basicDataHelper.call(this.serviceId,"deleteFilterOption", params, true, function(_oTrans, _json){
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
	},
	saveGroup : function(groupNameEl,newValue){
		var optionGroupEl = findItem(groupNameEl, "optionGroup");
		var params = {
			ObjectId: optionGroupEl.id,
			OPTIONGROUPNAME: newValue,
			FILTERID : m_nFilterId
		}
		if(optionGroupEl.id == 0){
			Ext.apply(params,{
				FORDER : -1
			});
		}
		//发送请求保存步骤的信息
		basicDataHelper.call(this.serviceId,"saveFilterOptionGroup",params,true,function(_oTrans, _json){
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
				OPTIONNAME : currValue
			}
			if(optionId == 0){
				Ext.apply(params,{
					FORDER : -1
				});
			}
			//发送请求保存选项的信息
			basicDataHelper.call(this.serviceId, "saveFilterOption", params, true, function(_oTrans, _json){
				optionBoxEl.id = _json.RESULT;
				optionNameEl.setAttribute("_value", currValue);
			});
		}
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
				basicDataHelper.call("wcm61_filtercenter","saveFilterOption", {
					ObjectId : optionId,
					NOTALLOWEDSELECTOPTIONS : selectedValus
				},true,function(_trans, _json){
					
				});	
				this.close();
			}
        });
	},
	uploadOptionPic : function(optionBox,fieldName,title){
		var optionId = optionBox.id;
		if(optionId == 0){
			Ext.Msg.alert("请先新建选项，再设置其图片！");
			return;
		}
		//弹开上传文件的窗口
		wcm.CrashBoarder.get('setOptionPic').show({
            maskable:true,
            title : title,
            src : './option_pictrue_set.jsp',
            width:'350px',
            height:'200px',
            params : {
                OptionId : optionId,
				PicFieldName : fieldName
            }
        });
	},
	setSearchConditon : function(optionBox){
		openSearchConditionWindow(optionBox);	
	},
	setOptionGroupDesc : function(srcEl){
		var groupNode = findItem(srcEl,"optionGroup");
		var groupId = groupNode.id;
		if(groupId == 0)return;
		if(!Element.hasClassName(srcEl,"grouptitlesp"))return;
		openObjDescWindow(srcEl, groupId, '输入选项组描述信息','OPTIONGROUPDESC','saveFilterOptionGroup');
	},
	setOptionDesc : function(optionBox){
		var optionId = optionBox.id;
		if(optionId == 0){
			return;
		}
		var titleEl = document.getElementsByClassName("optionname_text",optionBox)[0];
		openObjDescWindow(titleEl,optionId,'输入选项描述信息','OPTIONDESC','saveFilterOption');
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
	showFilterMenu : function(event,srcEl){
		var simpleMenu = this.getSimpleMenu();
		var oOpers = [{
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
			oprKey : 'setDisabledOptions',
			desc : "设置禁用选项",
			cmd : function(args){
				var srcEl = args.srcEl;
				PageHandler.setDisabledOptions(srcEl);
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
			oprKey : 'setOptionGroupDesc',
			desc : "设置选项组描述",
			cmd : function(args){
				var srcEl = args.srcEl;
				PageHandler.setOptionGroupDesc(srcEl);
			}
		},{
			oprKey : 'addOption',
			desc : "添加选项",
			cmd : function(args){
				var srcEl = args.srcEl;
				PageHandler.addOption(srcEl);
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
		},{
			oprKey : 'setOptionGroupOtherProperties',
			desc : "设置选项组其他属性",
			cmd : function(args){
				var srcEl = args.srcEl;
				PageHandler.setOptionGroupOtherProperties(srcEl);
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
			desc : "设置选型描述",
			cmd : function(args){
				var srcEl = args.srcEl;
				PageHandler.setOptionDesc(srcEl);
			}
		},{
			oprKey : 'setSearchCondition',
			desc : "设置查询条件",
			cmd : function(args){
				var srcEl = args.srcEl;
				PageHandler.setSearchConditon(srcEl);
			}
		},{
			oprKey : 'setNotAllowedOption',
			desc : "设置互斥项",
			cmd : function(args){
				var srcEl = args.srcEl;
				PageHandler.setNotAllowedOption(srcEl);
			}
		},{
			oprKey : 'uploadFirstPic',
			desc : "上传默认显示图片",
			cmd : function(args){
				var srcEl = args.srcEl;
				PageHandler.uploadOptionPic(srcEl,"FIRSTPIC","上传默认显示图片");
			}
		},{
			oprKey : 'uploadSecondPic',
			desc : "上传点击后的图片",
			cmd : function(args){
				var srcEl = args.srcEl;
				PageHandler.uploadOptionPic(srcEl,"SECONDPIC","上传点击后的图片");
			}
		},{
			oprKey : 'uploadThirdPic',
			desc : "上传置灰图片",
			cmd : function(args){
				var srcEl = args.srcEl;
				PageHandler.uploadOptionPic(srcEl,"THIRDPIC","上传置灰图片");
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
						basicDataHelper.call('wcm61_filtercenter', 'saveFilterOption', params, true, function(){
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
	//焦点的设置
	var dom = cb.getElement();
	var newDom = dom.getElementsByTagName("TEXTAREA")[0].focus();
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
						basicDataHelper.call('wcm61_filtercenter', methodname, params, true, function(){
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