/*********************************************************************************************/
var JqPopTree = new Object();

JqPopTree.create = function(config){
	this.config = {
		caption			:config.caption||"POP TREE",
		onlySelectLeaf	:config.onlySelectLeaf||false,
		bindElement		:config.bindElement,
		pageUrl			:config.pageUrl,//
		nodePageUrl		:config.nodePageUrl,//
		dataNodeName	:config.dataNodeName||"record",//
		paramPrefix		:config.paramPrefix||"",//
		treeRootText	:config.treeRootText,//
		treeRootId		:config.treeRootId,//
		attributeId		:config.attributeId,//
		attributeText	:config.attributeText,//
		attributePid	:config.attributePid,//
		attributeOpenPath : config.attributeOpenPath,
		attributes		:config.attributes,
		searchCaption	:config.searchCaption||"search:",
		searchSrc		:config.searchSrc,
		dragHandleId	:config.dragHandleId,
		onSubmit		:config.onSubmit
	};
	
	this.init();
};

JqPopTree.create.prototype = {
	init:function(){
		var myConfig = this.config;
		var p = this;
		
		JqTree.treeConfig.dataNodeName=myConfig.dataNodeName;
		JqTree.treeConfig.paramPrefix=myConfig.paramPrefix;
		
		this.popWindowContainer = $("<div></div>");
		var popContent = $("<div></div>");
		var popTreeContainer = $("<div class=\"JqPopTree_treeContainer\"></div>");
		var popTreeHead = $("<div></div>");
		var popTreeFoot = $("<div align=\"center\"></div>");
		var submitBut = $("<input class=\"buttonface\" type=\"button\" value=\"\u786e \u5b9a\">");
		var cancelBut = $("<input class=\"buttonface\" type=\"button\" value=\"\u53d6 \u6d88\">");
		
		$("body",document).append(this.popWindowContainer);
		$("body",document).append(popTreeContainer);
		
		var treeObject = new JqTree.TreeObject.create(myConfig.attributeId,myConfig.attributeText,myConfig.attributePid);
		//alert(JqTree.treeConfig.dataNodeName);
		var treeRootText = myConfig.treeRootText;
		var pageUrl = myConfig.pageUrl;
		var nodePageUrl = myConfig.nodePageUrl;
		
		var attributes = myConfig.attributes;
		
		var popActionHandler = new JqTree.ActionHandler.create("pop");
		popActionHandler.dblclick.handler = dblclickHandler;//
		var jqPopTree = new JqTree.create(treeRootText,treeObject,pageUrl,nodePageUrl,attributes,popActionHandler,false,false,false,false,false,0);//
		this.jqPopTree = jqPopTree;
		
		popTreeFoot.append(submitBut);
		popTreeFoot.append("&nbsp; &nbsp; &nbsp;");
		popTreeFoot.append(cancelBut);
		
		popContent.append(popTreeHead);
		popContent.append(popTreeContainer);
		popContent.append(popTreeFoot);
		
		popTreeContainer.append(jqPopTree.tree);
		
		var paramAttribute = new Array();
		paramAttribute['valueAttribute'] = myConfig.attributeOpenPath;
		paramAttribute['textAttribute'] = myConfig.attributeText;
		jqPopTree.showSearchPanel(myConfig.searchCaption,myConfig.searchSrc,paramAttribute,'before');
		$("div.jTree_helper_panel_head",jqPopTree.helperPanel).css("display","none");
		jqPopTree.helperPanel.css("width","100%");
		jqPopTree.helperPanel.css("height","25px");
		popTreeHead.append(jqPopTree.helperPanel);
		
		this.popWindowContainer.PopWindow({
			caption: myConfig.caption,
			content: popContent,
			dragHandleId:myConfig.dragHandleId,
			onClose:function(){
				//alert("close");
			}
		});
		
		var domJ = $(myConfig.bindElement);
		domJ.click(function(){
			p.popWindowContainer.show();
		});
		
		submitBut.click(function(){
			var selectedNode = jqPopTree.getSelectedNode();
			p.submitApply(selectedNode);
		});
		
		cancelBut.click(function(){
			p.popWindowContainer.hide();
		});
		
		function dblclickHandler(){
			var selectedNode = this.jqTreeNode;
			p.submitApply(selectedNode);
		}
	},
	
	submitApply:function(selectedNode){
		if(selectedNode.getAttribute("isRoot")!="true"){
			if(this.config.onlySelectLeaf==true){
				if(selectedNode.getAttribute("hasChildren")=="true"){
					return;
				}
			}
			this.config.onSubmit.apply(this,new Array(selectedNode));
			this.popWindowContainer.hide();
		}
	}
};
/*********************************************************************************************/

/*********************************************************************************************/
var JqPopCheckBoxTree = new Object();

JqPopCheckBoxTree.Resource = {
	noChecked_errorMessage  : "\u672a\u9009\u62e9\u4efb\u4f55\u8282\u70b9!"
};

/**
 * @param type one|multiple
 * @param
 */
JqPopCheckBoxTree.create = function(config){
	this.config = {
		caption			:config.caption||"POP CHECKBOX TREE",
		bindElement		:config.bindElement,
		onlySelectLeaf	:config.onlySelectLeaf||false,
		pageUrl			:config.pageUrl,//
		nodePageUrl		:config.nodePageUrl,//
		dataNodeName	:config.dataNodeName||"record",//
		paramPrefix		:config.paramPrefix||"",//
		treeRootText	:config.treeRootText,//
		treeRootId		:config.treeRootId,//
		attributeId		:config.attributeId,//
		attributeText	:config.attributeText,//
		attributePid	:config.attributePid,//
		attributeOpenPath : config.attributeOpenPath,
		attributes		:config.attributes,
		searchCaption	:config.searchCaption||"search:",
		searchSrc		:config.searchSrc,
		dragHandleId	:config.dragHandleId,
		onSubmit		:config.onSubmit
	};
	
	this.init();
};

JqPopCheckBoxTree.create.prototype = {
	jqPopTree:null,
	
	popWindowContainer:null,
	
	init:function(){
		var myConfig = this.config;
		var p = this;
		
		JqTree.treeConfig.dataNodeName=myConfig.dataNodeName;
		JqTree.treeConfig.paramPrefix=myConfig.paramPrefix;
		
		this.popWindowContainer = $("<div></div>");
		var popContent = $("<div></div>");
		var popTreeContainer = $("<div class=\"JqPopTree_treeContainer\"></div>");
		var popTreeHead = $("<div></div>");
		var popTreeFoot = $("<div align=\"center\"></div>");
		var submitBut = $("<input class=\"buttonface\" type=\"button\" value=\"\u786e \u5b9a\">");
		var cancelBut = $("<input class=\"buttonface\" type=\"button\" value=\"\u53d6 \u6d88\">");
		$("body",document).append(this.popWindowContainer);
		$("body",document).append(popTreeContainer);
		
		var treeObject = new JqTree.TreeObject.create(myConfig.attributeId,myConfig.attributeText,myConfig.attributePid);
		//alert(JqTree.treeConfig.dataNodeName);
		var treeRootText = myConfig.treeRootText;
		var pageUrl = myConfig.pageUrl;
		var nodePageUrl = myConfig.nodePageUrl;
		
		var attributes = myConfig.attributes;
		var jqPopTree = new JqTree.create(treeRootText,treeObject,pageUrl,nodePageUrl,attributes,null,true,myConfig.onlySelectLeaf,false,false,false,0);//
		this.jqPopTree = jqPopTree;
		popTreeFoot.append(submitBut);
		popTreeFoot.append("&nbsp; &nbsp; &nbsp;");
		popTreeFoot.append(cancelBut);
		
		popContent.append(popTreeHead);
		popContent.append(popTreeContainer);
		popContent.append(popTreeFoot);
		
		popTreeContainer.append(jqPopTree.tree);
		
		var paramAttribute = new Array();
		paramAttribute['valueAttribute'] = myConfig.attributeOpenPath;
		paramAttribute['textAttribute'] = myConfig.attributeText;
		jqPopTree.showSearchPanel(myConfig.searchCaption,myConfig.searchSrc,paramAttribute,'before');
		$("div.jTree_helper_panel_head",jqPopTree.helperPanel).css("display","none");
		jqPopTree.helperPanel.css("width","100%");
		jqPopTree.helperPanel.css("height","25px");
		popTreeHead.append(jqPopTree.helperPanel);
		
		this.popWindowContainer.PopWindow({
			caption: myConfig.caption,
			dragHandleId:myConfig.dragHandleId,
			content: popContent,
			onClose:function(){
				//alert("close");
			}
		});
		
		var domJ = $(myConfig.bindElement);
		domJ.click(function(){
			p.popWindowContainer.show();
		});
		
		submitBut.click(function(){
			var checkedJqTreeNodes = p.jqPopTree.getCheckedJqTreeNodes();
			if(checkedJqTreeNodes.length==0){
				alert(JqPopCheckBoxTree.Resource.noChecked_errorMessage);
				return;
			}
			if(myConfig.onSubmit){
				myConfig.onSubmit.apply(p,new Array(checkedJqTreeNodes));
				p.popWindowContainer.hide();
			}
		});
		
		cancelBut.click(function(){
			p.popWindowContainer.hide();
		});
	}
}