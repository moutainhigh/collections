var indicatorQueryPage = new Object();

indicatorQueryPage.Resource  = {
	notSelect_indicator_errorMessage:"\u672a\u9009\u62e9\u6307\u6807!",
	notSelect_time_errorMessage:"\u672a\u9009\u62e9\u65f6\u95f4!",
	notSelect_area_errorMessage:"\u672a\u9009\u62e9\u5730\u533a!",
	move_rowToCol_errorMessage:"\u4e3b\u680f\u4e2d\u81f3\u5c11\u4fdd\u7559\u4e00\u9879!",
	move_colToRow_errorMessage:"\u5bbe\u680f\u4e2d\u81f3\u5c11\u4fdd\u7559\u4e00\u9879!",
	data_count_errorMessage:"\u6700\u591a\u53ef\u9009\u62e91000\u4e2a\u6570\u636e\u5355\u5143!",
	notCommon_group_errorMessage:"\u6ca1\u6709\u5171\u540c\u5206\u7ec4!"
}

indicatorQueryPage.Config = {
	fbzdTextAttributeName:'FBZD_MC',
	fbzdValueAttributeName:'FBZD_DM',
	groupPrefix:'group_',
	groupItemPrefix:'gitem_'
}

indicatorQueryPage.create = function(config){
	this.fbzdSrc = config.fbzdSrc;
	this.timeType = (!config.timeType)?"year":config.timeType;
	this.initIndicatorId = config.initIndicatorId;
	this.init();
}

indicatorQueryPage.create.prototype = {
	fbzdSelect:null,
	
	indicatorTree:null,
	
	indicatorTreeContainer:null,
	
	indicatorCounts:0,
	
	
	comboGroups:new Array(),
	
	comboTimes:new Array(),
	
	yearTimes :new Array(),
	
	pcBgqbs :new Array(),//
	
	initComboGroupsFlag:true,
	initLayoutFlag:false,
	//initTimeFlag:true,
	//initAreaFlag:true,
	initTimeAndAreaFlag:true,
	
	init:function(){
		this.queryTypeInput = $("<input name=\"queryType\" type=\"hidden\"/>");
		this.timeTypeInput = $("<input name=\"timeType\" type=\"hidden\"/>");
		this.errorSpan = $('<span style="color:red;" class="indicator-query-errorMessage"/>');
		$('body',document).append(this.errorSpan);
		$(document.forms["queryUtil"]).append(this.queryTypeInput);
		$(document.forms["queryUtil"]).append(this.timeTypeInput);
		var p = this;
		
		this.initIndicatorPanel();
		this.initGroupPanel();
		//this.initTimePanel();
		//this.initAreaPanel();
		this.initTimeAndAreaPanel();
		this.initLayoutPanel();
		
		$("input#result_preview").click(function(){
			p.postSubmitData("preview");
		});
		
		$("input#result_query").click(function(){
			p.postSubmitData("query");
		});
	},
	/**
	 * 初始化指标面板
	 */
	initIndicatorPanel:function(){
		var p = this;
		this.getIndicatorPanel().panel({
			expandable:true,
			after:function(){
				p.fbzdSelect = $("select#indicator_dczd_select");
				p.indicatorTreeContainer = $("div#indicator_tree_container");
				p.indicatorSelectedSelect = $("select#indicator_selected_select");
				p.initFbzdSelect();
				p.indicatorCounts = 0;
				
				if(p.initIndicatorId!=""){
					var getIndicatorByZbidSrc = "indicatorYear.action?method=getIndicatorByZbid&zb_id="+p.initIndicatorId;
					$.get(getIndicatorByZbidSrc,function(xml){
						var fbzdDm = null;
						$("Datas/data",xml).each(function(){
							fbzdDm = $("FBZD_FJD",this).text();//
							var fbzdMc = $("option[@value="+fbzdDm+"]",p.fbzdSelect).html();
							p.initIndicatorTree(fbzdDm,fbzdMc);
							var zb_id =  $("ZB_ID",this).text();
							var zb_bb = $("ZB_BB",this).text();
							var zb_cn_qc = $("ZB_CN_QC",this).text();
							p.addOption(p.indicatorSelectedSelect,zb_bb+"/"+zb_id,zb_cn_qc);
							p.setIndicatorCount();
							$("option[@value="+fbzdDm+"]",p.fbzdSelect).attr("selected","selected");
						});
					});
				}
			}
		});
	},
	
	/**
	 * 指标面板上的按钮动作
	 */
	initIndicatorMoveBut:function(){
		var p = this;
		$("img#select_checked_indicators").unbind().click(function(){
			var checkedJqTreeNodes  = p.indicatorTree.getCheckedJqTreeNodes();//checked nodes
			var addIndicatorIdArray = new Array();//add to select node
			var checkedIndicatorText;
			var checkedIndicatorValue;
			for(var i=0;i<checkedJqTreeNodes.length;i++){
				var jqTreeNode = checkedJqTreeNodes[i];
				checkedIndicatorText = jqTreeNode.element.getAttribute('ZB_CN_QC')||jqTreeNode.getText();
				checkedIndicatorValue = jqTreeNode.getId();
				addIndicatorIdArray[addIndicatorIdArray.length] = checkedIndicatorValue;//
				if($("option[@value="+checkedIndicatorValue+"]",p.indicatorSelectedSelect).size()>0){
					continue;
				}
				
				var oOption = $("<option>"+checkedIndicatorText+"</option>");
				oOption.attr("value",checkedIndicatorValue);
				
				p.getIndicatorSelect().append(oOption);
				checkedIndicatorValue = null;
				checkedIndicatorText = null;
			}
			
			if(addIndicatorIdArray.length>0){
				p.changeIndictorsAction();
			}
			p.setIndicatorCount();
		});
		
		$("img#delete_checked_indicators").unbind().click(function(){
			if($("option[@selected]",p.indicatorSelectedSelect).size()==0)return;
			var checkedJqTreeNodes  = p.indicatorTree.getCheckedJqTreeNodes();
			var indicatorIdArray = new Array();
			$("option",p.indicatorSelectedSelect).each(function(){
				if(this.selected){
					for(var i=0;i<checkedJqTreeNodes.length;i++){
						var jqTreeNode = checkedJqTreeNodes[i];
						if(this.value==jqTreeNode.getId()){
							$(jqTreeNode.element).click();
						}
					}
					$(this).remove();
				}else{
					indicatorIdArray[indicatorIdArray.length]=this.value;
				}
			});
			p.setIndicatorCount();
			//if(indicatorIdArray.length>0){
				p.changeIndictorsAction();
			//}else{

			//}
		});
	},
	/**
	 * 改变指标数量引发的动作
	 */
	changeIndictorsAction:function(){
		this.initComboGroupsFlag = true;
		this.initLayoutFlag = true;//
		//this.initTimeFlag = true;
		//this.initAreaFlag = true;
		this.initTimeAndAreaFlag = true;
		
		this.getGroupPanel().collapsePanel();
		//this.getTimePanel().collapsePanel();
		//this.getAreaPanel().collapsePanel();
		this.getTimeAndAreaPanel().collapsePanel();
		this.getLayoutPanel().collapsePanel();
		
		this.setAreaCount(0);
		this.disposeComboTimes();
		$("div#groupItem_select_panel").empty();
		$("span.groupItem_count").remove();
	},
	/**
	 * 改变分组引发的动作
	 */
	changeGroupAction:function(){
		this.initLayoutFlag = true;//
		//this.initTimeFlag = true;
		//this.initAreaFlag = true;
		this.initTimeAndAreaFlag = true;

		//this.getTimePanel().collapsePanel();
		//this.getAreaPanel().collapsePanel();
		this.getTimeAndAreaPanel().collapsePanel();
		this.getLayoutPanel().collapsePanel();
		
		this.setAreaCount(0);
		this.disposeComboTimes();
	},
	/**
	 * 初始化分组面板
	 */
	initGroupPanel:function(){
		var p = this;
		this.getGroupPanel().panel({
			expandable:true,
			beforeExpand:function(expand){
				if(expand==false){
					return p.hasSelectIndicators();
				}
			},
			afterExpand:function(expand){
				if(expand==true){
					//展开面板后根据标志读取分组数据
					if(p.initComboGroupsFlag==true){
						p.initGroups();
						p.initComboGroupsFlag = false;
					}else{
						if(p.comboGroups.length==0){
							p.initLayoutFlag = true;//
							p.getGroupPanel().collapsePanel();
							p.showPanelError(indicatorQueryPage.Resource.notCommon_group_errorMessage,p.getGroupPanel());
							//alert(indicatorQueryPage.Resource.notCommon_group_errorMessage);
						}
					}
				}
			}
		}).trigglePanel();
		this.getGroupSelect().change(function(){
			if(this.value){
				p.initGroupSelects(this.value);
			}else{
				//p.disposeGroups();
				$("span.groupItem_count").html("0");
				$('.groupItem_select_panel').empty();
			}
			p.changeGroupAction();
		});
	},
	/**
	 * 初始化时间面板
	 */
	initTimeAndAreaPanel:function(){
		var p = this;
		this.getTimeAndAreaPanel().panel({
			expandable:true,
			beforeExpand:function(expand){
				if(expand==false){
					return p.hasSelectIndicators();
				}
			},
			afterExpand:function(expand){
				if(expand==true&&p.initTimeAndAreaFlag==true){
					p.initTime();
					p.initArea();
					window.setTimeout(function(){
						p.showTime2();
					},10);
					
					p.initTimeAndAreaFlag = false;
				}
			}
		}).trigglePanel();
	},

	/**
	 * 初始化布局面板
	 */
	initLayoutPanel:function(){
		var p = this;
		this.getLayoutPanel().panel({
			expandable:true,
			beforeExpand:function(expand){
				if(expand==false){
					if(p.getTimeCount()==0){
						//alert(indicatorQueryPage.Resource.notSelect_time_errorMessage);
						p.showPanelError(indicatorQueryPage.Resource.notSelect_time_errorMessage,p.getTimeAndAreaPanel());
						return false;
					}
					if(p.getAreaCount()==0){
						//alert(indicatorQueryPage.Resource.notSelect_area_errorMessage);
						p.showPanelError(indicatorQueryPage.Resource.notSelect_area_errorMessage,p.getTimeAndAreaPanel());
						return false;
					}
					return p.hasSelectIndicators();
				}
			},
			afterExpand:function(expand){
				if(expand==true){
					p.initLayout();
				}
			},
			after:function(){
				p.initLayoutPanelBut();
			}
		}).trigglePanel();;
	},
	/**
	 * 获得指标选择面板
	 */
	getIndicatorPanel:function(){return $('#indicator_panel');},
	/**
	 * 获得分组选择面板
	 */
	getGroupPanel:function(){return $('#group_panel');},
	/**
	 * 获得时间选择面板
	 */
	getTimeAndAreaPanel:function(){return $('#timeAndArea_panel');},
	/**
	 * 获得地区选择面板
	 */
	getAreaPanel:function(){return $('#area_panel');},
	/**
	 * 获得布局调整面板
	 */
	getLayoutPanel:function(){return $('#layout_panel');},
	
	getIndicatorSelect:function(){return $('#indicator_selected_select');},//获得已选指标下拉框
	/**
	 * 获得选择的指标
	 */
	getSelectedIndicators:function(){
		var indicators = [];
		var rSelect = $('select#indicator_selected_select');
		$('option',rSelect).each(function(){
			indicators.push({
				indicatorId:this.value.split('/')[1],
				indicatorMc:this.text
			});
		});
		return indicators;
	},
	
	getIndicatorIdc:function(){
		var indicatorIds = [];
		var rSelect = $('select#indicator_selected_select');
		$('option',rSelect).each(function(){
			indicatorIds.push(this.value.split('/')[1]);
		});
		return indicatorIds.join();
	},
	
	/**
	 * 获得分组下拉框
	 */
	getGroupSelect:function(){return $('select#group_selected_select');},
	
	/**
	 * 判断是否选择了指标
	 * @return boolean
	 */
	hasSelectIndicators:function(){
		if(this.getSelectedIndicators().length==0){
			//alert(indicatorQueryPage.Resource.notSelect_indicator_errorMessage);
			this.showPanelError(indicatorQueryPage.Resource.notSelect_indicator_errorMessage,this.getIndicatorPanel());
			return false;
		}
		return true;
	},
	
	initFbzdSelect:function(){
		var oValue,text;
		var p = this;
		$.get(this.fbzdSrc,function(xml){
			$("data",xml).each(function(){
				oValue = $(indicatorQueryPage.Config.fbzdValueAttributeName,this).text();
				text = $(indicatorQueryPage.Config.fbzdTextAttributeName,this).text();
				var oOption = $("<option>"+text+"</option>");
				oOption.attr("value",oValue);
				
				p.fbzdSelect.append(oOption);
				oOption.removeAttr("selected");
				if(p.timeType=="pc"){
					p.pcBgqbs["L"+oValue] = $("LB_DYBGQB",this).text();
				}
				oValue = null;
				text = null;
			});
			
			p.fbzdSelect.change();
		});
		
		p.fbzdSelect.change(function(){
			var fbzdDm,fbzdMc;
			
			fbzdDm = this.options[this.selectedIndex].value;
			fbzdMc = this.options[this.selectedIndex].text;
			if(p.timeType=="pc"){
				p.initPcTime(fbzdDm);
			}
			p.initIndicatorTree(fbzdDm,fbzdMc);//
			p.changeIndictorsAction();
			p.setIndicatorCount(0);
		});
	},
	
	initIndicatorTree:function(fbzdDm,fbzdMc,pageUrl,nodePageUrl){
		var p = this;
		this.initLayoutFlag = true;//
		this.indicatorCounts = 0;
		this.indicatorSelectedSelect.empty();//
		this.initIndicatorMoveBut();
		if(this.timeType!='pc'){
			pageUrl = this.fbzdSrc+"&FBZD_FJD="+fbzdDm+"&timeType="+this.timeType;
		}else{
			pageUrl = "indicatorYear.action?method=getDczdsXml&FBZD_FJD="+fbzdDm+"&timeType="+this.timeType;
		}
		nodePageUrl = "indicatorYear.action?method=getIndicatorsXml&timeType="+this.timeType;
		var $tree = $('<div style="width:1000"/>');
		$tree.append('<ul><li expanded="true" id="'+fbzdDm+'"><a><span>'+fbzdMc+'</span></a><ul/></li></ul>');
		var $treeRootUl = $tree.find('li>ul');
		this.indicatorTreeContainer.empty();
		this.indicatorTreeContainer.append($tree);
		$.get(pageUrl,function(xml){
			var fbzdNodeSourceArray;
			var fbzd_dm;
			var fbzd_mc;
			
			$('data',xml).each(function(){
				fbzdNodeSourceArray = [];
				fbzd_dm = $('FBZD_DM',this).text();
				fbzd_mc = $('FBZD_MC',this).text();
				fbzdNodeSourceArray.push('<li attributes="ZB_CN_QC"  src="');
				fbzdNodeSourceArray.push(nodePageUrl+'&FBZD_FJD='+fbzd_dm);
				fbzdNodeSourceArray.push('"><a><span>');
				fbzdNodeSourceArray.push(fbzd_mc);
				fbzdNodeSourceArray.push('</span></a></li>');
				$treeRootUl.append(fbzdNodeSourceArray.join(''));
				fbzd_dm = null;
				fbzd_mc = null;
			}) ;
			
			p.indicatorTree = $tree.youiTree({
				treeMapObject:{
					mapId	:'id',
					mapText	:'text',
					mapPid	:'pid'
				}
			});
		});
	},
	/**
	 * 
	 */
	initGroups:function(){
		var p = this;
		var indicatorSelectedSelect = this.getIndicatorSelect();//选择的指标
		var addIndicatorIdArray = new Array();
		var indicatorArray = new Array();//remove zb_bb //  1/33
		$("option",indicatorSelectedSelect).each(function(){
			addIndicatorIdArray[addIndicatorIdArray.length] = this.value;
		});
		for(var i=0;i<addIndicatorIdArray.length;i++){
			var bIndex = addIndicatorIdArray[i].indexOf("/");
			if(bIndex!=-1){
				indicatorArray[i] = addIndicatorIdArray[i].substring(bIndex+1);
			}
		}
		var indicators = indicatorArray.join();
		
		var groupId;
		var groupMc;
		var comboGroupText;
		this.disposeGroups();
		if(!indicators)return;
		
		var searchGroupsSrc = "indicatorYear.action?method=getGroupAndGroupItemXml&indicators="+indicators;
		switch(this.timeType){
			case "year":
				searchGroupsSrc+="&dclbDm=1&bgqbDm=1";
				break;
			case "quarter":
				searchGroupsSrc+="&dclbDm=1&bgqbDm=3";
				break;
			case "month":
				searchGroupsSrc+="&dclbDm=1&bgqbDm=4";
				break;
			case "pc":
				var pcZtdm = $("option[@selected]",this.fbzdSelect).attr("value");
				var pcBgqbDm = p.pcBgqbs["L"+pcZtdm];
				searchGroupsSrc+="&dclbDm=3&bgqbDm="+pcBgqbDm;
				break;
		}
		$.get(searchGroupsSrc,function(xml){
			if($("Groups/comboGroup",xml).size()==0){
				//p.collapseGroup();如果没有，关闭分组选择面板
				p.getGroupPanel().collapsePanel();
				//alert(indicatorQueryPage.Resource.notCommon_group_errorMessage);
				p.showPanelError(indicatorQueryPage.Resource.notCommon_group_errorMessage,p.getGroupPanel());
				return false;
			}else{
				$("Groups/comboGroup",xml).each(function(){
					comboGroupText = this.getAttribute("comboGroupText");
					comboGroupId = this.getAttribute("comboGroupId");
					var comboGroupValue = p.comboGroups.length;
					p.comboGroups[comboGroupValue] = {
						comboGroupValue:comboGroupValue,
						comboGroupId   :comboGroupId,
						comboGroupText :comboGroupText,
						groups:new Array()
					};
	
					$("group",this).each(function(){
						groupId = this.getAttribute("FZ_ID");
						groupMc = this.getAttribute("FZ_CN_QC");
						var groupKey = "o"+groupId;
						
						p.comboGroups[comboGroupValue].groups[groupKey] = {
							groupId:groupId,
							groupMc:groupMc,
							groupItems:new Array()
						};
						
						$("groupItem",this).each(function(){
							groupItemId = this.getAttribute("FZX_ID");
							groupItemMc = this.getAttribute("FZX_CN_QC");
							groupItemFjd = this.getAttribute("FZX_FJD");
							groupItemCjm = this.getAttribute("FZX_CJM");
							var groupItemKey = "o"+groupItemId;
							
							p.comboGroups[comboGroupValue].groups[groupKey].groupItems[groupItemKey]={
								groupItemId:groupItemId,
								groupItemMc:groupItemMc,
								groupItemFjd:groupItemFjd,
								groupItemCjm:groupItemCjm
							}
							groupItemId = null;
							groupItemMc = null;
							groupItemFjd = null;
							groupItemCjm = null;
						});
						groupId = null;
						groupMc = null;
					});
					comboGroupText = null;
					comboGroupId = null;
				});
				p.initComboGroupsSelect();
			}
		});
	},
	
	initComboGroupsSelect:function(){
		var comboGroup;
		var group;
		var comboGroupValue;
		var comboGroupText;
		
		var groupItemId;
		var groupItemMc;
		var groupSelect = $('select#group_selected_select');
		groupSelect.empty();
		groupSelect.append('<option value="">\u603b\u8ba1</option>');//合计
		for(var i=0;i<this.comboGroups.length;i++){
			comboGroup = this.comboGroups[i];
			comboGroupValue = comboGroup.comboGroupValue;
			comboGroupText = comboGroup.comboGroupText;	
			var oOption = $("<option>"+comboGroupText+"</option>");
			oOption.attr("value",comboGroupValue);
			groupSelect.append(oOption);
		}
		if(this.comboGroups.length!=0){
			groupSelect.change();
		}
	},
	
	initGroupSelects:function(comboGroupValue){
		var p = this;
		this.initLayoutFlag = true;//
		var comboGroup = this.comboGroups[comboGroupValue];
		var groupItemSelectsPanel = $("div#groupItem_select_panel");
		groupItemSelectsPanel.empty();
		var groups = comboGroup.groups;
		var group;
		var groupId;
		var groupMc;
		
		var selectWidth = 0;
		for(var iGroup in groups){
			group = groups[iGroup];
			if(group&&iGroup!="indexOf"){
				var groupItemSelectPanel = $("<span style=\"margin-left:10px;float:left;\" class=\"groupItem_select_panel\"></span>");
				var groupItmeSelect = $("<select multiple=\"multiple\" style=\"width:120px;\" size=\"5\" class=\"groupItem_select\"></select>");
				groupId = group.groupId;
				groupMc = group.groupMc;
				
				groupItmeSelect.attr("groupId",groupId);
				groupItmeSelect.attr("groupMc",groupMc);
				
				var groupCountPanel = $("<div align=\"center\" groupId=\""+groupId+"\"></div>");
				var groupCountSpan = $("<span class=\"groupItem_count count_show\" groupId=\""+groupId+"\">0</span>");
				groupCountPanel.append("\u5171\u9009\u4e2d");
				groupCountPanel.append(groupCountSpan);
				groupCountPanel.append("\u4e2a");
				
				groupItmeSelect.attr("groupId",groupId);
				groupItmeSelect.attr("groupMc",groupMc);
				this.initGroupItemsSelect(group,groupItmeSelect);
				//alert($("option",groupItmeSelect).size());
				if($("option",groupItmeSelect).size()==0){
					groupItemSelectPanel.empty();//no common groupItem
					return;
				}
				groupItemSelectPanel.append(groupItmeSelect);
				groupItemSelectPanel.append(groupCountPanel);
				
				groupItemSelectsPanel.append(groupItemSelectPanel);
				groupItmeSelect.change(function(){
					var groupId = this.value;
					var inLayoutFlag = false;
					$("option","div#layout_panel_body").each(function(){
						if(groupId == this.value){
							inLayoutFlag = true;
						}
					});
					if(inLayoutFlag == false){
						p.initLayoutFlag = true;//
						p.getLayoutPanel().collapsePanel();
					}

					var groupItemCount = 0;
					var thisGroupCountSpan = $("span",this.parentNode);
				
					groupItemCount = $("option[@selected]",this).size()
					if(groupItemCount==0){
						p.initLayoutFlag = true;//
						p.getLayoutPanel().collapsePanel();
					}
					
					thisGroupCountSpan.html(groupItemCount||'0');
					p.setDataCellsCount();
				});
				
				selectWidth+=130;
			}
		}
		groupItemSelectsPanel.css("width",selectWidth+1);
		var left = (580-selectWidth-1)/2;

		groupItemSelectsPanel.css("margin-left",left);
	},
	
	initGroupItemsSelect:function(group,groupItmeSelect){
		var groupItemId;
		var groupItemMc;
		var groupItemFjd;
		var groupItemCjm;
		if(group){
			var groupItems = group.groupItems;
			var fLevelCount = 0;
			for(var iGroupItem in groupItems){
				if(iGroupItem&&iGroupItem!="indexOf"&&!groupItems[iGroupItem].groupItemFjd){
					fLevelCount++;
				}
			}
			this.sortGroupItem(groupItems,groupItmeSelect,"",fLevelCount);
		}
	},
	/**
	 * 
	 */
	disposeGroups:function(){
		var comboGroup;
		$("span.groupItem_count").html("0");
		for(var i=0;i<this.comboGroups.length;i++){
			comboGroup = this.comboGroups[i];
			
			comboGroup = null;
		}
		this.comboGroups = new Array();
	},
	
	sortGroupItem:function(groupItems,groupItemSelect,sGroupItemFjd,fLevelCount,level){
		var groupItemId;
		var groupItemMc;
		var groupItemFjd;
		var groupItemCjm;
		level = (level)?(level+1):1;
		
		for(var iGroupItem in groupItems){
			if(iGroupItem&&iGroupItem!="indexOf"){
				groupItemId = groupItems[iGroupItem].groupItemId;
				groupItemMc = groupItems[iGroupItem].groupItemMc;
				groupItemFjd = groupItems[iGroupItem].groupItemFjd;
				groupItemCjm = groupItems[iGroupItem].groupItemCjm;
				if(fLevelCount!=null&&fLevelCount==0){
						var oOption = $("<option>"+groupItemMc+"</option>");
						oOption.attr("value",groupItemId);
						groupItemSelect.append(oOption);
				}else{
					if((!sGroupItemFjd&&!groupItemFjd)||sGroupItemFjd==groupItemFjd){
						//if(!groupItemCjm)level = 1;
						var space = "";
						//level = groupItemCjm/4;
						for(var iSpace=1;iSpace<level;iSpace++){
							space+="&nbsp;&nbsp";
						}
						var oOption = $("<option>"+space+groupItemMc+"</option>");
						oOption.attr("value",groupItemId);
						groupItemSelect.append(oOption);
						this.sortGroupItem(groupItems,groupItemSelect,groupItemId,fLevelCount,level);
					}
				}
				
				
				groupItemId = null;
				groupItemMc = null;
				groupItemFjd = null;
				groupItemCjm = null;
			}
		}
		$("option",groupItemSelect).removeAttr("selected");
	},
	
	setIndicatorCount:function(){
		this.indicatorCounts = $("option",this.indicatorSelectedSelect).size();
		if(!this.indicatorCounts)this.indicatorCounts="0";
		//alert(this.indicatorCounts);
		$("span#selected_Indicator_count").html(this.indicatorCounts);
		this.setDataCellsCount();
	},
	
	setGroupItemCount:function(){
		this.groupItemCount = 0;
		//$("option",$)
	},
	
	showTime1:function(){
		$("img#img_showTime_1").attr("src","images/form/button_time1.gif");
		$("img#img_showTime_2").attr("src","images/form/button_time4.gif");
		$("div#time_1").css("display","block");
		$("div#time_2").css("display","none");
		
		$('select',$("div#time_1")).show();
		$('select',$("div#time_2")).hide();
	},
	
	showTime2:function(){
		$("img#img_showTime_1").attr("src","images/form/button_time3.gif");
		$("img#img_showTime_2").attr("src","images/form/button_time2.gif");
		$("div#time_1").css("display","none");
		$("div#time_2").css("display","block");
		
		$('select',$("div#time_1")).hide();
		$('select',$("div#time_2")).show();
	},
	
	initTime:function(){
		var indicators = this.getIndicatorIdc();
		var groupsIndex = $('option[@selected]',this.getGroupSelect()).attr("value");
		var comboGroupId = "";
		//alert(groupsIndex);
		if(this.comboGroups[groupsIndex]){
			comboGroupId = this.comboGroups[groupsIndex].comboGroupId;
		}
		
		var p = this;
		switch(this.timeType){
			case "year":
				this.initYearTime(indicators,comboGroupId);
				break;
			case "month":
				this.initMonthTime(indicators,comboGroupId);
				break;
			case "quarter":
				this.initQuarterTime(indicators,comboGroupId);
				break;
			case "pc":
				this.initPcTime();
				break;
		}
		
		if(this.timeType!="pc"){
			$("img#img_showTime_1").click(function(){
				if($("div#time_1").css("display")=="none"){
					p.showTime1();
					$("select#time_begin_select").change();
				}
			});
			
			$("img#img_showTime_2").click(function(){
				if($("div#time_2").css("display")=="none"){
					p.showTime2();
					$("select#time_select").change();
					if(p.timeType=="month"||p.timeType=="quarter"){
						p.setTime2Count();
					}
					
				}
			});
		}
	},
	
	addOption:function(select,value,text){
		var oOption = $("<option value=\""+value+"\">"+text+"</option>");
		var lastOption = $("option:last",select);
		var lastValue = lastOption.attr("value");
		//alert(value+":last:"+lastValue);
		var pre = lastOption;
		if(value&&value>lastValue){
			var insertFalg = true;
			$("option",select).each(function(){
				var iValue = this.value;
				if(iValue<value&&insertFalg==true){//
					oOption.insertBefore(this);
					insertFalg = false;
				}
			});
		}else{
			select.append(oOption);
		}
		oOption.removeAttr("selected");
		return oOption;
	},
	
	disposeComboTimes:function(){
		this.comboTimes = new Array();
		this.setTimeCount(0);
	},
	
	initYearTime:function(indicators,comboGroupId){
		var p = this;
		var timeBeginSelect = $("select#time_begin_select").empty();
		var timeEndSelect = $("select#time_end_select").empty();
		var timeSelect = $("select#time_select").empty();
		this.yearTimes = new Array();
		var src = "bgqXml.action?dclbDm=1&bgqbDm=1&indicators="+indicators+"&comboGroupId="+comboGroupId;
		$.get(src,function(xml){
			if($("Datas/year",xml).size()==0){
				p.showPanelError('\u6ca1\u6709\u5173\u8054\u65f6\u95f4',p.getTimeAndAreaPanel());//没有关联时间
				p.getTimeAndAreaPanel().collapsePanel();
				p.initTimeAndAreaFlag = true;
				return;
			}
			
			$("Datas/year",xml).each(function(){
				var yearItem = $("item",this);
				var timeMc = yearItem.attr("BGQ_MC");
				var timeId = yearItem.attr("BGQ_DM");
				p.yearTimes["L"+timeId] = timeMc;
				p.addOption(timeBeginSelect,timeId,timeMc);
				p.addOption(timeEndSelect,timeId,timeMc);
				p.addOption(timeSelect,timeId,timeMc);
			});
			
			if($("Datas/year",xml).size()==1){
				p.setTimeCount(1);
				$('option',timeSelect).attr('selected','selected');
			}
			
			$("select#time_begin_select").change(function(){
				setTime1Count();
	 		});
			
			$("select#time_end_select").change(function(){
				setTime1Count();
			});
			
			function setTime1Count(){
				var count = 0;
				var timeBeginSelect = $("select#time_begin_select");
				var timeEndSelect = $("select#time_end_select");
				
				var timeBegin;
				var timeEnd;
				timeBegin = $("option[@selected]",timeBeginSelect).attr("value");
				timeEnd = $("option[@selected]",timeEndSelect).attr("value");
				
				for(var iYear in p.yearTimes){
					if(iYear!="indexOf"){
						var timeId = iYear.substring(1);
						if((timeId>=timeBegin&&timeId<=timeEnd)||timeId<=timeBegin&&timeId>=timeEnd){
							count++;
						}
					}
				}
				
				p.setTimeCount(count);
			} 
			
			$("select#time_select").change(function(){
				var count = 0;
				count = $("option[@selected]",this).size();
				p.setTimeCount(count);
			});
		});
	},
	
	initMonthTime:function(indicators,comboGroupId){
		var p = this;
		this.disposeComboTimes();
		//this.initYear(begin,end);
		var monthBeginSelect = $("select#month_begin_select");
		var monthEndSelect = $("select#month_end_select");
		var monthSelect = $("select#month_select");
		
		var timeBeginSelect = $("select#time_begin_select").empty();
		var timeEndSelect = $("select#time_end_select").empty();
		var timeSelect = $("select#time_select").empty();
		var src = "bgqXml.action?dclbDm=1&bgqbDm=4&indicators="+indicators+"&comboGroupId="+comboGroupId;
		
		$.get(src,function(xml){
			if($("Datas/year",xml).size()==0){
				p.showPanelError('\u6ca1\u6709\u5173\u8054\u65f6\u95f4',p.getTimeAndAreaPanel());//没有关联时间
				p.getTimeAndAreaPanel().collapsePanel();
				p.initTimeAndAreaFlag = true;
				return;
			}
			$("Datas/year",xml).each(function(){
				var id = this.getAttribute("id");
				
				p.addOption(timeBeginSelect,id,id);
				p.addOption(timeEndSelect,id,id);
				p.addOption(timeSelect,id,id+"\u5e74");
				var itemArray = new Array();
				$("item",this).each(function(){
					var timeMc = $(this).attr("BGQ_MC");
					var timeId = $(this).attr("BGQ_DM");
					itemArray[itemArray.length] = {
						timeMc:timeMc,
						timeId:timeId
					};
				});
				p.comboTimes["L"+id] = itemArray;
			});	
			timeSelect.get(0).selectedIndex = 0;
			timeBeginSelect.change(function(){
				p.initTimeItemSelect(monthBeginSelect,this.value);
				setTime1Count();
			});
			
			timeEndSelect.change(function(){
				p.initTimeItemSelect(monthEndSelect,this.value);
				setTime1Count();
			});
			
			timeBeginSelect.change();
			timeEndSelect.change();
			window.setTimeout(function(){timeSelect.change();p.setTimeCount(0);},0);
			
			monthBeginSelect.bind("change",setTime1Count);
			monthEndSelect.bind("change",setTime1Count);
			
			function setTime1Count(){
				p.setTime1Count(timeBeginSelect,timeEndSelect,monthBeginSelect,monthEndSelect);
			}
			
			monthSelect.change(function(){
				var year = $(this).attr("year");
				var itemArray = new Array();
				itemArray = p.comboTimes["L"+year];
				for(var i=0;i<itemArray.length;i++){
					itemArray[i].selected = false;
					var timeId = itemArray[i].timeId;
					
					$("option[@selected]",this).each(function(){
						if(this.value==timeId){
							itemArray[i].selected = true;
						}
					});
				}
				setTime2Count();
			});

			timeSelect.change(function(){
				p.initTimeItemSelect(monthSelect,this.value);
				monthSelect.attr("year",this.value);
				var itemArray = new Array();
				itemArray = p.comboTimes["L"+this.value];
				for(var i=0;i<itemArray.length;i++){
					if(itemArray[i].selected==true){
						var timeId = itemArray[i].timeId;
						$("option",monthSelect).each(function(){
							if(this.value==timeId){
								this.selected = "selected";
							}
						});
					}
				}
			});
			
			function setTime2Count(){
				p.setTime2Count();
			}
		});
	},
	
	setTime1Count:function(timeBeginSelect,timeEndSelect,itemBeginSelect,itemEndSelect){
		var count = 0;
		var timeBegin;
		var timeEnd;
		
		var monthBegin = $("option[@selected]",itemBeginSelect).attr("value");
		var monthEnd = $("option[@selected]",itemEndSelect).attr("value");
		
		timeBegin = $("option[@selected]",timeBeginSelect).attr("value");
		timeEnd = $("option[@selected]",timeEndSelect).attr("value");
		//p.comboTimes["L"+id]
		
		if(timeBegin > timeEnd){
			var temp = timeBegin;
			timeBegin = timeEnd;
			timeEnd = temp;
			temp = monthBegin;
			monthBegin = monthEnd;
			monthEnd = temp;
		}
		if(timeBegin==timeEnd){
			var itemArray = new Array();
			itemArray = this.comboTimes["L"+timeBegin];
			for(var i=0;i<itemArray.length;i++){
				var timeId = itemArray[i].timeId;
				if((timeId>=monthBegin&&timeId<=monthEnd)||(timeId<=monthBegin&&timeId>=monthEnd)){
					count++;
				}
			}
		}else{
			for(var iYear in this.comboTimes){
				if(iYear!="indexOf"){
					var year = iYear.substring(1);
					var itemArray = new Array();
					itemArray = this.comboTimes[iYear];
					
					if(year==timeBegin){
						for(var i=0;i<itemArray.length;i++){
							if(itemArray[i].timeId>=monthBegin){
								count++;
							}
						}
					}else if(year==timeEnd){
						for(var i=0;i<itemArray.length;i++){
							if(itemArray[i].timeId<=monthEnd){
								count++;
							}
						}
					}else{
						if((year>timeBegin&&year<timeEnd)||(year>timeEnd&&year<timeBegin)){
							count+=itemArray.length;
						}
					}
				}
			}
		}
		this.setTimeCount(count);
	},
	
	addTimeElements:function(queryXml,timesElement,timeBeginSelect,timeEndSelect,itemBeginSelect,itemEndSelect){
		var timeBegin;
		var timeEnd;
		
		var itemBegin = $("option[@selected]",itemBeginSelect).attr("value");
		var itemEnd = $("option[@selected]",itemEndSelect).attr("value");
		
		timeBegin = $("option[@selected]",timeBeginSelect).attr("value");
		timeEnd = $("option[@selected]",timeEndSelect).attr("value");
		if(timeBegin > timeEnd){
			var temp = timeBegin;
			var tempTime = itemBegin;
			timeBegin = timeEnd;
			timeEnd = temp;
			
			itemBegin = itemEnd;
			itemEnd = tempTime;
		}
		//p.comboTimes["L"+id]
		if(timeBegin==timeEnd){
			var itemArray = new Array();
			itemArray = this.comboTimes["L"+timeBegin];
			for(var i=0;i<itemArray.length;i++){
				var timeId = itemArray[i].timeId;
				if((timeId>=itemBegin&&timeId<=itemEnd)||(timeId<=itemBegin&&timeId>=itemEnd)){
					this.addTimeElement(itemArray[i].timeId,itemArray[i].timeMc,timeBegin,timesElement,queryXml);
				}
			}
		}else{
			for(var iYear in this.comboTimes){
				if(iYear!="indexOf"){
					var year = iYear.substring(1);
					var itemArray = new Array();
					itemArray = this.comboTimes[iYear];
					if(year==timeBegin){
						for(var i=0;i<itemArray.length;i++){
							if(itemArray[i].timeId>=itemBegin){
								this.addTimeElement(itemArray[i].timeId,itemArray[i].timeMc,timeBegin,timesElement,queryXml);
							}
						}
					}else if(year==timeEnd){
						for(var i=0;i<itemArray.length;i++){
							if(itemArray[i].timeId<=itemEnd){
								this.addTimeElement(itemArray[i].timeId,itemArray[i].timeMc,timeEnd,timesElement,queryXml);
							}
						}
					}else if((year>timeBegin&&year<timeEnd)||(year>timeEnd&&year<timeBegin)){
						for(var i=0;i<itemArray.length;i++){
							this.addTimeElement(itemArray[i].timeId,itemArray[i].timeMc,year,timesElement,queryXml);
						}
					}
				}
			}
		}
	},
	
	addTimeElement:function(timeId,timeMc,year,timesElement,queryXml){
		var timeElement = queryXml.createElement('time');
		timeElement.setAttribute("timeId",timeId);
		timeElement.setAttribute("timeMc",year+"\u5e74"+timeMc);
		timesElement.appendChild(timeElement);
	},
					
	setTime2Count:function(){
		var count = 0;
		for(var iYear in this.comboTimes){
			if(iYear!="indexOf"){
				var itemArray = new Array();
				itemArray = this.comboTimes[iYear];
				for(var i=0;i<itemArray.length;i++){
					if(itemArray[i].selected==true){
						count++;
					}
				}
			}
		}
		this.setTimeCount(count);
	},
	
	initQuarterTime:function(indicators,comboGroupId){
		var p = this;
		this.disposeComboTimes();
		var quarterBeginSelect = $("select#quarter_begin_select");
		var quarterEndSelect = $("select#quarter_end_select");
		var quarterSelect = $("select#quarter_select");
		
		var timeBeginSelect = $("select#time_begin_select").empty();
		var timeEndSelect = $("select#time_end_select").empty();
		var timeSelect = $("select#time_select").empty();
		
		var src = "bgqXml.action?dclbDm=1&bgqbDm=3&indicators="+indicators+"&comboGroupId="+comboGroupId;
		
		$.get(src,function(xml){
			if($("Datas/year",xml).size()==0){
				p.showPanelError('\u6ca1\u6709\u5173\u8054\u65f6\u95f4',p.getTimeAndAreaPanel());//没有关联时间
				p.getTimeAndAreaPanel().collapsePanel();
				p.initTimeAndAreaFlag = true;
				return;
			}
			$("Datas/year",xml).each(function(){
				var id = this.getAttribute("id");
				p.addOption(timeBeginSelect,id,id);
				p.addOption(timeEndSelect,id,id);
				p.addOption(timeSelect,id,id+"\u5e74");
				
				var itemArray = new Array();
				$("item",this).each(function(){
					var timeMc = $(this).attr("BGQ_MC");
					var timeId = $(this).attr("BGQ_DM");
					itemArray[itemArray.length] = {
						timeMc:timeMc,
						timeId:timeId
					};
				});
				p.comboTimes["L"+id] = itemArray;
			});
			timeSelect.get(0).selectedIndex = 0;
			quarterBeginSelect.bind("change",setTime1Count);
			quarterEndSelect.bind("change",setTime1Count);
			
			timeBeginSelect.change(function(){
				p.initTimeItemSelect(quarterBeginSelect,this.value);
				setTime1Count();
			});
			
			timeEndSelect.change(function(){
				p.initTimeItemSelect(quarterEndSelect,this.value);
				setTime1Count();
			});
			
			timeBeginSelect.change();
			timeEndSelect.change();
			window.setTimeout(function(){
				timeSelect.change();
				p.setTimeCount(0);
			},0);
			
			function setTime1Count(){
				p.setTime1Count(timeBeginSelect,timeEndSelect,quarterBeginSelect,quarterEndSelect);
			}
			
			quarterSelect.change(function(){
				var year = $(this).attr("year");
				var itemArray = new Array();
				itemArray = p.comboTimes["L"+year];
				for(var i=0;i<itemArray.length;i++){
					itemArray[i].selected = false;
					var timeId = itemArray[i].timeId;
					
					$("option[@selected]",this).each(function(){
						if(this.value==timeId){
							itemArray[i].selected = true;
						}
					});
				}
				setTime2Count();
			});

			timeSelect.change(function(){
				//alert(this.value);
				p.initTimeItemSelect(quarterSelect,this.value);
				quarterSelect.attr("year",this.value);
				var itemArray = new Array();
				itemArray = p.comboTimes["L"+this.value];
				for(var i=0;i<itemArray.length;i++){
					if(itemArray[i].selected==true){
						var timeId = itemArray[i].timeId;
						$("option",quarterSelect).each(function(){
							if(this.value==timeId){
								this.selected = "selected";
							}
						});
					}
				}
			});
			
			function setTime2Count(){
				p.setTime2Count();
			}
		});
		/**
		this.initYear(begin,end);
		quarterBeginSelect.get(0).selectedIndex=0;
		quarterEndSelect.get(0).selectedIndex=0;
		
		
		* */
	},
	
	initTimeItemSelect:function(select,year){
		select.empty();
		var itemArray = new Array();
		itemArray = this.comboTimes["L"+year];
		for(var i=0;i<itemArray.length;i++){
			var timeId = itemArray[i].timeId;
			var timeMc = itemArray[i].timeMc;
			this.addOption(select,timeId,timeMc);
		}
	},
	
	initPcTime:function(fbzdDm){
		var p = this;
		try{
			var lbXh = fbzdDm;
		}catch(err){
			return;
		}
		var pbBgqb = p.pcBgqbs["L"+lbXh];
		var src = "getPcBgq.action?bgqbDm="+pbBgqb;
		
		var timeSelect = $("select#time_select");
		
		timeSelect.empty();
		$.get(src,function(xml){
			$("Datas/data",xml).each(function(){
				var bgqDm = $("BGQ_DM",this).text(); //"BGQ_DM","BGQ_MC","BGQ_SSND"
				var bgqMc = $("BGQ_MC",this).text();
				var bgqSsnd = $("BGQ_SSND",this).text();
				
				var showText = bgqMc+"("+bgqSsnd+")";
				var oOption = $("<option>"+showText+"</option>");
				oOption.attr("value",bgqDm);
				timeSelect.append(oOption);
				oOption.removeAttr("selected");
			});
			
			timeSelect.change(function(){
				var count = 0;
				count = $("option[@selected]",this).size();
				p.setTimeCount(count);
			});
		});
	},
	
	setTimeCount:function(count){
		if(!count){
			count="0";
			//this.collapseLayout();
		}
		this.disposeLayoutPanel();
		$("span#selected_time_count").html(count);
		this.setDataCellsCount();
	},
	
	initArea:function(){
		var p = this;
		var indicators = this.getIndicatorIdc();
		var groupsIndex = $('option[@selected]',this.getGroupSelect()).attr("value");
		var comboGroupId = "";
		if(this.comboGroups[groupsIndex])comboGroupId = this.comboGroups[groupsIndex].comboGroupId;
		
		var areaSelect = $("select#area_select");
		var src = "indicatorYear.action?method=getXZQYXml&indicators="+indicators+"&comboGroupId="+comboGroupId;
		switch(this.timeType){
			case "year":
				src+='&dclbDm=1&bgqbDm=1';
				break;
			case "month":
				src+='&dclbDm=1&bgqbDm=4';
				break;
			case "quarter":
				src+='&dclbDm=1&bgqbDm=3';
				break;
			case "pc":
				break;
		}
		
		areaSelect.empty();
		p.setAreaCount(0);
		areaSelect.change(function(){
			var areaCount = 0;
			$("option",this).each(function(){
				if(this.selected){
					areaCount++;
				}
			});
			p.setAreaCount(areaCount);
		});
		
		$.get(src,function(xml){
			if($("data",xml).size()==0){
				//
				p.showPanelError('\u6ca1\u6709\u5173\u8054\u5730\u533a',p.getTimeAndAreaPanel());//没有关联地区
				p.getTimeAndAreaPanel().collapsePanel();
				p.initTimeAndAreaFlag = true;
				return;
			}
			
			$("data",xml).each(function(){
				var text = $("XZQY_MC",this).text();
				var value = $("XZQY_NM",this).text();
				var areaDm = $("XZQY_DM",this).text();
				var oOption = $("<option>"+text+"</option>");
				oOption.attr("value",value);
				if(areaDm)oOption.attr("areaDm",areaDm);
				areaSelect.append(oOption);
				oOption.removeAttr("selected");
			});
			
			if($("data",xml).size()==1){
				window.setTimeout(function(){p.setAreaCount(1);},0);
				$("option",areaSelect).attr("selected","selected");
				
			}
			//$("option",areaSelect).removeAttr("selected");
		});
	},
	
	setAreaCount:function(count){
		if(!count){
			count="0";
			//this.collapseLayout();
			this.initLayoutFlag = true;
		}
		this.disposeLayoutPanel();
		$("span#selected_area_count").html(count);
		this.setDataCellsCount();
	},
	
	setDataCellsCount:function(){
		var dataCellCount = 0;
		var indicatorCount = this.indicatorCounts;
		var groupItemCountArray = new Array();
		var timeCount;
		var areaCount;
		
		timeCount = parseInt($("span#selected_time_count").html());
		areaCount = parseInt($("span#selected_area_count").html());
		
		dataCellCount = timeCount*areaCount*indicatorCount;
		
		$("span.groupItem_count").each(function(){
			var groupItemCount = $(this).html();
			if(groupItemCount&&groupItemCount!="0"){
				dataCellCount*=groupItemCount;
			}
		});
		if(!dataCellCount)dataCellCount="0";
		if(dataCellCount>1000){
			alert(indicatorQueryPage.Resource.data_count_errorMessage);
		}
		$("span#selected_data_count").html(dataCellCount);
	},
	
	getAreaCount:function(){
		return $('#selected_area_count').text();
	},
	
	getTimeCount:function(){
		return $('#selected_time_count').text();
	},
	
	getZlLevel:function(){
		return $('option',$('select#row_selelcted_select')).length;
	},
	
	getBlLevel:function(){
		return $('option',$('select#col_selelcted_select')).length;
	},
	
	initLayout:function(){
		var rowSelect = $("select#row_selelcted_select");
		var colSelect = $("select#col_selelcted_select");
		
		rowSelect.empty();
		colSelect.empty();
		var indicatorOption = $("<option layoutType=\"indicator\" value=\"indicator\">\u6307\u6807</option>");

		var timeOption = $("<option layoutType=\"time\" value=\"time\">\u65f6\u95f4</option>");
		var areaOption = $("<option layoutType=\"area\" value=\"area\">\u5730\u533a</option>");
		
		if(this.getAreaCount()>1){
			rowSelect.append(areaOption);
			areaOption.removeAttr("selected");
		}
		
		if(this.indicatorCounts>1){
			colSelect.prepend(indicatorOption);
			indicatorOption.removeAttr("selected");
		}
		if(this.getTimeCount()>1){
			colSelect.append(timeOption);
			timeOption.removeAttr("selected");
		}
		
		//add group
		$("span.groupItem_count").each(function(){
			var groupId = $(this).attr("groupId");
			
			var groupItemSelect = $("select[@groupId="+groupId+"]",this.parentNode.parentNode);
			var groupMc = groupItemSelect.attr("groupMc");
			var addGroupFlag = false;
			$("option",groupItemSelect).each(function(){
				if(this.selected){
					addGroupFlag = true;
				}
			});
			
			if(addGroupFlag == true){
				var groupOption = $("<option layoutType=\"group\">"+groupMc+"</option>");
				groupOption.attr("value",groupId);
				rowSelect.append(groupOption);
				groupOption.removeAttr("selected");
			}
		});
		
		//如果主栏没有项目,加入指标
		if(this.getZlLevel()==0){
			rowSelect.prepend(indicatorOption);
			indicatorOption.removeAttr("selected");
		}
		//如果宾栏没有项目,主栏有两个以上的项目
		if(this.getBlLevel()==0){
			if(this.getZlLevel()>1){
				//移动最后一项
				colSelect.append($('option:last',rowSelect));
			}else{
				//此时优先显示指标(只有一个指标，主栏有一项并且不是指标)
				if(this.indicatorCounts==1&&$('option[@value=indicator]',rowSelect).length==0){
					colSelect.prepend(indicatorOption);
					indicatorOption.removeAttr("selected");
				}else{
					colSelect.append(timeOption);
					timeOption.removeAttr("selected");
				}
			}
		}
		this.initLayoutFlag=false;
	},
	
	initLayoutPanelBut:function(){
		var p = this;
		var moveRightBut = $("img#button_moveRight");
		var moveLeftBut = $("img#button_moveLeft");
		var rowMoveDownBut = $("img#row_button_moveDown");
		var colMoveDownBut = $("img#col_button_moveDown");
		
		var rowSelect = $("select#row_selelcted_select");
		var colSelect = $("select#col_selelcted_select");
		
		moveRightBut.click(function(){
			if($("option[@selected]",rowSelect).size()==$("option",rowSelect).size()){
				//alert(indicatorQueryPage.Resource.move_rowToCol_errorMessage);
				p.showPanelError(indicatorQueryPage.Resource.move_rowToCol_errorMessage,p.getLayoutPanel());
				return;
			}
			var sOption = $("option[@selected]",rowSelect);
			colSelect.append(sOption);
		});
		
		moveLeftBut.click(function(){
			if($("option[@selected]",colSelect).size()==$("option",colSelect).size()){
				//alert(indicatorQueryPage.Resource.move_colToRow_errorMessage);
				p.showPanelError(indicatorQueryPage.Resource.move_colToRow_errorMessage,p.getLayoutPanel());
				return;
			}
			var sOption = $("option[@selected]",colSelect);
			rowSelect.append(sOption);
		});
		
		rowMoveDownBut.click(function(){
			$("option[@selected]",rowSelect).each(function(){
				var nextOption = $(this).next();
				nextOption.insertBefore($(this));
			});
		});
		
		colMoveDownBut.click(function(){
			$("option[@selected]",colSelect).each(function(){
				var nextOption = $(this).next();
				nextOption.insertBefore($(this));
			});
		});
		
	},
	
	disposeLayoutPanel:function(){
		this.getLayoutPanel().collapsePanel();
		this.initLayoutFlag = true;
	},
	
	
	showPanelError:function(errorMessage,panel){
		//alert(errorMessage);
		var p = this;
		this.errorSpan.html("---"+errorMessage);
		$('.youi-panel-header:first',panel).append(this.errorSpan);
		window.setTimeout(function(){p.errorSpan.empty();},2000);
	},
	
	
	selectdCountCheck:function(){
		var indicatorCount = this.indicatorCounts;
		var timeCount;
		var areaCount;
		timeCount = parseInt($("span#selected_time_count").html());
		areaCount = parseInt($("span#selected_area_count").html());
		
		if(!indicatorCount||indicatorCount=="0"){
			alert(indicatorQueryPage.Resource.notSelect_indicator_errorMessage);
			return false;
		}
		
		if(!timeCount||timeCount=="0"){
			alert(indicatorQueryPage.Resource.notSelect_time_errorMessage);
			return false;
		}
		
		if(!areaCount||areaCount=="0"){
			alert(indicatorQueryPage.Resource.notSelect_area_errorMessage);
			return false;
		}
		
		return true;
	},
	/**
	 * 
	 */
	postSubmitData:function(type){
		if(this.selectdCountCheck()!=true){
			return;
		}
		
		if($("span#selected_data_count").html()>1000){
			alert(indicatorQueryPage.Resource.data_count_errorMessage);
			return;
		}
		
		//this.expandLayout();
		//this.collapseLayout();
		
		var queryXml;
		queryXml = this.getQueryXml();
		
		var queryXmlStr;
		if($.browser.msie){
			queryXmlStr = queryXml.xml;
		}else{
			var oSerializer = new XMLSerializer();
		 	queryXmlStr = oSerializer.serializeToString(queryXml);
		}
		
		$("input#page_queryXml").attr("value",queryXmlStr);
		this.queryTypeInput.attr("value",type);
		this.timeTypeInput.attr("value",this.timeType);
		document.forms["queryUtil"].submit();
		//$("div#out").text(queryXml.xml);
		
		/**
		$.ajax({
			type		: "POST",
  			url			: "indicatorYear.action?method=queryIndicatorsData",
  			dataType	: "xml",
  			success		:  function(data){
  							 alert(data.xml);
  						   },
  			data		:queryXml,
  			processData :false
		});
		*/
	},
	
	getQueryXml:function(){
		var queryXml;
		var fbzdDm = $("option[@selected]",this.fbzdSelect).attr("value");
		var rootElement;
		if($.browser.msie==false){
			//queryXml=new XMLDOM();
			queryXml = document.implementation.createDocument("", "query", null);
			rootElement = queryXml.documentElement;
		}else if(window.ActiveXObject){
			queryXml=new ActiveXObject("Microsoft.XMLDOM");
			var xmlHead = queryXml.createProcessingInstruction("xml"," version=\"1.0\"");
			queryXml.insertBefore(xmlHead,queryXml.firstChild);
			rootElement = queryXml.createElement('query');
			queryXml.appendChild(rootElement);
		}
		rootElement.setAttribute("fbzdDm",fbzdDm);
		this.getLayoutPanel().trigglePanel().collapsePanel();
		
		/*
		 * 
		 */
		var indicatorsElement = queryXml.createElement('indicators');
		rootElement.appendChild(indicatorsElement);
		var indicatorElement;//
		$("option",this.indicatorSelectedSelect).each(function(){
			indicatorElement = queryXml.createElement('indicator');
			var indicatorId = this.value;
			var indicatorMc = $(this).html();
			indicatorElement.setAttribute("indicatorId",indicatorId);
			indicatorElement.setAttribute("indicatorMc",indicatorMc);
			indicatorsElement.appendChild(indicatorElement);
		});
		
		/*
		 * 
		 */
		var groupsElement = queryXml.createElement('groups');
		rootElement.appendChild(groupsElement);
		var groupElement;
		var groupItemElement;
		$("select.groupItem_select").each(function(){
			if($("option[@selected]",this).size()>0){//
				var groupId = $(this).attr("groupId");
				var groupMc = $(this).attr("groupMc");
				groupElement = queryXml.createElement('group');
				groupElement.setAttribute("groupId",groupId);
				groupElement.setAttribute("groupMc",groupMc);
				groupsElement.appendChild(groupElement);
				
				$("option[@selected]",this).each(function(){
					var groupItemId = this.value;
					var groupItemMc = $(this).html().replace(/\&nbsp;/g,'');
					groupItemElement = queryXml.createElement('groupItem');
					groupItemElement.setAttribute("groupItemId",groupItemId);
					groupItemElement.setAttribute("groupItemMc",groupItemMc);
					groupElement.appendChild(groupItemElement);
				});
			}
		});
		/*
		 * 
		 */
		var areaSelect = $("select#area_select");
		var areasElement = queryXml.createElement('areas');
		rootElement.appendChild(areasElement);
		var areaElement;//
		$("option[@selected]",areaSelect).each(function(){
			var areaId = this.value;
			var areaMc = $(this).html();
			areaElement = queryXml.createElement('area');
			areaElement.setAttribute("areaId",areaId);
			areaElement.setAttribute("areaMc",areaMc);
			areasElement.appendChild(areaElement);
		});
		/*
		 * 
		 */
		var timesElement = queryXml.createElement('times');
		var timeElement;
		rootElement.appendChild(timesElement);
		this.makeTimeElements(timesElement,queryXml);
		
		/*
		 * layout
		 */
		var layoutsElement;
		var rowLayoutElement;
		var colLayoutElement;
		layoutsElement = queryXml.createElement('layouts');
		rootElement.appendChild(layoutsElement);
		
		var layoutElement;
		var level=0;
		$("option",$("select#row_selelcted_select")).each(function(){
			level++;
			var layoutType = $(this).attr("layoutType");
			var layoutId = this.value;
			layoutElement = queryXml.createElement('layout');
			layoutElement.setAttribute("position","row");
			layoutElement.setAttribute("layoutType",layoutType);
			layoutElement.setAttribute("layoutId",layoutId);
			layoutElement.setAttribute("level",level);
			layoutsElement.appendChild(layoutElement);
		});
		
		level=0;
		$("option",$("select#col_selelcted_select")).each(function(){
			level++;
			var layoutType = $(this).attr("layoutType");
			var layoutId = this.value;
			layoutElement = queryXml.createElement('layout');
			layoutElement.setAttribute("position","col");
			layoutElement.setAttribute("layoutType",layoutType);
			layoutElement.setAttribute("layoutId",layoutId);
			layoutElement.setAttribute("level",level);
			layoutsElement.appendChild(layoutElement);
		});
		return queryXml;
	},
	
	makeTimeElements:function(timesElement,queryXml){
		switch(this.timeType){
			case "year":
				this.makeYearTimeElements(timesElement,queryXml);
				break;
			case "month":
				this.makeMonthTimeElements(timesElement,queryXml);
				break;
			case "quarter":
				this.makeQuarterTimeElements(timesElement,queryXml);
				break;
			case "pc":
				this.makePcTimeElements(timesElement,queryXml);
				break;
		}
	},
	
	makeYearTimeElements:function(timesElement,queryXml){
		if($("div#time_1").css("display")=="block"){
			var timeBeginSelect = $("select#time_begin_select");
			var timeEndSelect = $("select#time_end_select");
			
			var timeBegin = $("option[@selected]",timeBeginSelect).attr("value");
			var timeEnd = $("option[@selected]",timeEndSelect).attr("value");
			
			for(var iYear in this.yearTimes){
				if(iYear!="indexOf"){
					var timeId = iYear.substring(1);
					var timeMc = this.yearTimes[iYear];
					if((timeId>=timeBegin&&timeId<=timeEnd)||timeId<=timeBegin&&timeId>=timeEnd){
						timeElement = queryXml.createElement('time');
						timeElement.setAttribute("timeId",timeId);
						timeElement.setAttribute("timeMc",timeMc);
						timesElement.appendChild(timeElement);
					}
				}
			}
		}else{
			$("option[@selected]",$("select#time_select")).each(function(){
				timeElement = queryXml.createElement('time');
				var timeId = this.value;
				var timeMc = $(this).html();
				timeElement.setAttribute("timeId",timeId);
				timeElement.setAttribute("timeMc",timeMc);
				timesElement.appendChild(timeElement);
			});
		}
	},
	
	makeMonthTimeElements:function(timesElement,queryXml){
		if($("div#time_1").css("display")=="block"){
			var timeBeginSelect = $("select#time_begin_select");
			var timeEndSelect = $("select#time_end_select");
			
			var monthBeginSelect = $("select#month_begin_select");
			var monthEndSelect = $("select#month_end_select");
			
			this.addTimeElements(queryXml,timesElement,timeBeginSelect,timeEndSelect,monthBeginSelect,monthEndSelect);
			
		}else{
			for(var iYear in this.comboTimes){
				if(iYear!="indexOf"){
					var year = iYear.substring(1);
					var itemArray = new Array();
					itemArray = this.comboTimes[iYear];
					for(var i=0;i<itemArray.length;i++){
						if(itemArray[i].selected==true){
							var timeId = itemArray[i].timeId;
							var timeMc = year+"\u5e74"+itemArray[i].timeMc;
							var timeElement = queryXml.createElement('time');
							timeElement.setAttribute("timeId",timeId);
							timeElement.setAttribute("timeMc",timeMc);
							timesElement.appendChild(timeElement);
						}
					}
				}
			}
		}
	},
	
	makeQuarterTimeElements:function(timesElement,queryXml){
		if($("div#time_1").css("display")=="block"){
			var timeBeginSelect = $("select#time_begin_select");
			var timeEndSelect = $("select#time_end_select");
			var timeSelect = $("select#time_select");
			
			var quarterBeginSelect = $("select#quarter_begin_select");
			var quarterEndSelect = $("select#quarter_end_select");
			
			this.addTimeElements(queryXml,timesElement,timeBeginSelect,timeEndSelect,quarterBeginSelect,quarterEndSelect);
		}else{
			for(var iYear in this.comboTimes){
				if(iYear!="indexOf"){
					var year = iYear.substring(1);
					var itemArray = new Array();
					itemArray = this.comboTimes[iYear];
					for(var i=0;i<itemArray.length;i++){
						if(itemArray[i].selected==true){
							var timeId = itemArray[i].timeId;
							var timeMc = year+"\u5e74"+itemArray[i].timeMc;
							var timeElement = queryXml.createElement('time');
							timeElement.setAttribute("timeId",timeId);
							timeElement.setAttribute("timeMc",timeMc);
							timesElement.appendChild(timeElement);
						}
					}
				}
			}
		}
	},
	
	makePcTimeElements:function(timesElement,queryXml){
		$("option[@selected]",$("select#time_select")).each(function(){
			timeElement = queryXml.createElement('time');
			var timeId = this.value;
			var timeMc = $(this).html();
			timeElement.setAttribute("timeId",timeId);
			timeElement.setAttribute("timeMc",timeMc);
			timesElement.appendChild(timeElement);
		});
	}
}