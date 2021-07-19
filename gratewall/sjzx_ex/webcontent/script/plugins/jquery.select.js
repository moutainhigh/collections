jQuery.iSelect = {
	optionsPanel:null,
	
	selectedInput:null,
	
	bMadeRequest:true,
	
	arrOptions:new Array(),
	
	create:function(config){
		this.config = {
			/**
			 * src 动态加载数据路径
			 */
			src			:config.src,
			
			searchParamPrefix:config.searchParamPrefix||"",
			
			dataNodeName:config.dataNodeName||'record',
			/**
			 * width 输入框宽度
			 */
			width		:config.width,
			/**
			 * 是否支持typeHead
			 */
			typeHead	:false,
			/**
			 * 是否可编辑
			 */
			editable	:true,
			
			emptyText	:'select...',
			
			data		:config.data,
			
			textAttribute :config.textAttribute,
			
			valueAttribute:config.valueAttribute,
			/**
			 * 下拉图标
			 */
			iconSrc		:(config.iconSrc)?config.iconSrc:'images/form/selectIcon.gif',
			
			onSelect	:config.onSelect,
			
			onChange	:config.onChange
		};

		var selectSpan = jQuery("<div class=\"jqForm_trigger\"></div>");
		var selectInput = jQuery("<input type=\"text\">");
		var selectIcon = jQuery("<img></img>");
		var optionsPanel = jQuery("<div class=\"jqForm_options_panel\"></div>");
		
		optionsPanel.bind("mousewheel",jQuery.iSelect.onScroll);
		
		selectInput.attr("selectedIndex","-1");
		selectInput.css("border",0);
		selectInput.css("marginTop","-1px");
		selectInput.css("padding","0px");
		selectInput.css("line-height","20px");
		selectInput.mousedown(function(){return false});
		optionsPanel.mousedown(function(){return false});
		//selectInput.keyup(function(){
		//	jQuery.iSelect.buildOptions();
		//});
		
		selectInput.width(this.config.width);

		this.selectInput = selectInput;
		jQuery.iSelect.config = this.config;
		
		selectIcon.attr("src",this.config.iconSrc);
		selectIcon.css("height","1px");
		selectIcon.css("width","1px");
		
		selectSpan.css("width",this.config.width+15);
		
		selectSpan.get(0).optionsPanel = optionsPanel;
		selectSpan.bind("mousedown",jQuery.iSelect.trigger);
		
		selectSpan.append(selectInput);
		selectSpan.append(selectIcon);
		jQuery('body', document).append(optionsPanel);
		
		var moveButDiv = $("<div class=\"jqForm_options_move\"></div>");

		var moveupImg = $("<img alt=\"\u4e0a\u4e00\u6761\" class=\"jqForm_options_moveUp\"></img>");
		var movedownImg = $("<img alt=\"\u4e0b\u4e00\u6761\" class=\"jqForm_options_moveDown\"></img>");
		
		moveupImg.attr("src","images/form/arrow_up.gif");
		movedownImg.attr("src","images/form/arrow_down.gif");
		
		moveButDiv.append(moveupImg);
		moveButDiv.append(movedownImg);
		var p = this;
		moveupImg.click(function(){
			var prevItem = jQuery.iSelect.findPre(selectInput,optionsPanel);
			prevItem.mousedown();
		});
		
		movedownImg.click(function(){
			var nextItem = jQuery.iSelect.findNext(selectInput,optionsPanel);
			nextItem.mousedown();
		});
		
		var searchBut = $("<input class=\"buttonface\" type=\"button\" value=\"\u67e5 \u8be2\"/>");
		searchBut.css("marginTop","1pt");
		
		
		this.append(selectSpan);
		this.append(moveButDiv);
		this.append(searchBut);
		
		selectInput.keypress(function(event){
			var keyCode = event.keyCode;
			switch(keyCode){
				case 13://enter
					searchBut.click();
					break;
			}
		});
		
		searchBut.click(function(){
			var searchStr = p.selectInput.attr("value");
			if(searchStr){
				var searchSrc = p.config.src;
				if(searchSrc.indexOf("?")==-1){
			  		searchSrc+="?";
			  	}else{
			  		searchSrc+="&";
			  	}
			  	
			  	searchSrc+=p.config.searchParamPrefix;
			  	searchSrc+=p.config.textAttribute;
			  	searchSrc+="=";
			  	searchSrc+=searchStr;
				jQuery.iSelect.loadSrc(optionsPanel,searchSrc,p.config.valueAttribute,p.config.textAttribute,p.selectInput,p.config.onChange);
			}
		});
	},
	
	loadSrc:function(optionsPanel,src,valueAttribute,textAttribute,selectInput,onChange){
		var p = this;
		var text;
		var value;
		p.bMadeRequest=true;
		jQuery.iSelect.arrOptions = new Array();
		
		selectInput.attr("selectedIndex","-1");
		optionsPanel.html("");
		$.get(src,function(xml){
			$(jQuery.iSelect.config.dataNodeName,xml).each(function(){
				value = $(valueAttribute,this).text();
				text = $(textAttribute,this).text();
				jQuery.iSelect.arrOptions[jQuery.iSelect.arrOptions.length] = [value,text];
				text = null;
				value = null;
			});
			p.buildOptions(optionsPanel,selectInput,onChange);
		});
	},
	
	buildOptions:function(optionsPanel,selectInput,onChange){
		if(this.bMadeRequest==true){
			var arrOptions = jQuery.iSelect.arrOptions;
			var value = selectInput.attr("value");
			value = value+"(\u627e\u5230"+arrOptions.length+"\u6761\u8bb0\u5f55)";
			selectInput.attr("value",value);
			for(var i=0;i<arrOptions.length;i++){
				this.addOption(optionsPanel,arrOptions[i][0],arrOptions[i][1],selectInput,onChange);
			}
			if(arrOptions.length>10){
				optionsPanel.css("height","150px");
				optionsPanel.css("overflow-y","auto");
			}
			//jQuery.iSelect.expand();
			this.bMadeRequest=false;
		}
	},
	
	addOption:function(optionsPanel,value,text,selectInput,onChange,addIndex){
		var p = this;

		var lastOption = $("div.jqForm_options_item:last",optionsPanel);
		var index = 1;
		
		if(lastOption.size()>0){
			var lastIndex = lastOption.attr("selectIndex");
			index = lastIndex+1;
		}else{
			
		}
		var optionItem = $("<div class=\"jqForm_options_item\"></div>");
		optionItem.append(text);
		optionItem.attr("value",value);
		optionItem.attr("text",text);
		optionItem.attr("title",text);
		optionItem.attr("selectIndex",index);
		
		optionItem.mouseover(function(){
			$(this).addClass("jqForm_options_item_over");
		});
		
		optionItem.mouseout(function(){
			$(this).removeClass("jqForm_options_item_over");
		});
		
		optionItem.mousedown(function(){
			p.collapse();
			var selectedIndex = selectInput.attr("selectedIndex");
			
			selectInput.attr("selectedIndex",$(this).attr("selectIndex"));
			selectInput.attr("selectedValue",$(this).attr("value"));
			selectInput.attr("selectedText",$(this).attr("text"));

			selectInput.attr("value",$(this).attr("text"));
			
			if(typeof(selectedIndex)==-1||selectedIndex!=index){
				onChange.apply(selectInput.get(0));
			}
			return false;
		});
		
		optionsPanel.append(optionItem);
	},
	
	expand:function(optionsPanel){
		jQuery.iSelect.optionsPanel = optionsPanel;
		optionsPanel.css("display","block");
		jQuery('body', document).bind("mousedown",jQuery.iSelect.collapse);
		jQuery('body', document).bind("mousewheel",jQuery.iSelect.collapse);

	},
	
	collapse:function(){
		jQuery.iSelect.optionsPanel.css("display","none");
		jQuery('body', document).unbind("mousedown",jQuery.iSelect.collapse);
		jQuery('body', document).unbind("mousewheel",jQuery.iSelect.collapse);
	},
	/**
	 * private
	 * callback function
	 */
	trigger:function(){
		var optionsPanel = this.optionsPanel;
		var theElement  = this;
		var selectedPosX = 0;
        var selectedPosY = 0;
		while(theElement != null){
          selectedPosX += theElement.offsetLeft;
          selectedPosY += theElement.offsetTop;
          theElement = theElement.offsetParent;
        }
		
		var scrollNode = this;
		var scrollLength = 0;
		while(scrollNode!=null){
			if(scrollNode.scrollTop>0){
				scrollLength = parseInt(scrollNode.scrollTop);
				scrollNode = null;
			}else{
				scrollNode = scrollNode.parentNode;
			}
		}
		//alert(scrollLength);
		optionsPanel.css("width",this.offsetWidth+1);
		
		optionsPanel.css("left",selectedPosX);;

		optionsPanel.css("top",selectedPosY+this.offsetHeight-scrollLength+1);
		var display = optionsPanel.css("display");
		
		if(display=="block"){
			jQuery.iSelect.collapse();
		}else{
			jQuery.iSelect.expand(optionsPanel);
		}
		return false;
	},
	
	onScroll:function(){
		if(window.event.wheelDelta<0){
			this.scrollTop=this.scrollTop+18;
		}else{
			this.scrollTop=this.scrollTop-18;
		}
		
		return false;
	},
	
	findNext:function(selectInput,optionsPanel){
		var selectedIndex = selectInput.attr("selectedIndex");
		var selectedItem = $("div.jqForm_options_item[@selectIndex="+selectedIndex+"]",optionsPanel);

		if(selectedIndex==-1){
			return $("div.jqForm_options_item:first",optionsPanel);
		}
		return selectedItem.next();
	},
	
	findPre:function(selectInput,optionsPanel){
		var selectedIndex = selectInput.attr("selectedIndex");
		var selectedItem = $("div.jqForm_options_item[@selectIndex="+selectedIndex+"]",optionsPanel);
		return selectedItem.prev();
	},
	
	reload:function(){
		
	}
};

jQuery.fn.extend(
	{
		Select : jQuery.iSelect.create
	}
);