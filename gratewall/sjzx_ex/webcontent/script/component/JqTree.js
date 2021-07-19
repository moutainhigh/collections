var JqTree = new Object();
JqTree.Resource = {
	delete_root_message		:"\u4e0d\u80fd\u5220\u9664\u6839\u8282\u70b9!",
	selected_errorMessage	:"\u6ca1\u6709\u9009\u4e2d\u7684\u8282\u70b9!"
};

JqTree.treeConfig = {
	rootIcon        : "images/tree/folder.png",
	openRootIcon    : "images/tree/openfolder.png",
	folderIcon      : "images/tree/folder.png",
	openFolderIcon  : "images/tree/openfolder.png",
	fileIcon        : "images/tree/file.png",
	iIcon           : "images/tree/I.png",
	lIcon           : "images/tree/L.png",
	lMinusIcon      : "images/tree/Lminus.png",
	lPlusIcon       : "images/tree/Lplus.png",
	tIcon           : "images/tree/T.png",
	tMinusIcon      : "images/tree/Tminus.png",
	tPlusIcon       : "images/tree/Tplus.png",
	plusIcon        : "images/tree/plus.png",
	minusIcon       : "images/tree/minus.png",
	blankIcon       : "images/tree/blank.png",
	loadingIcon     : "images/tree/loading.gif",
	selectedColor   : "red",
	selectedBgColor : "#FFDDEE",
	checkedColor    : "blue",
	normalColor     : "black",
	focusColor		: "#EEEFFF",
	changedColor    : "#008000",
	newColor        : "#800F00",
	loadingText     : "loading...",
	paramPrefix     : "select-key:",
	dataNodeName    : "record",
	dragType		: "child", //child|firstChild|before|after
	modifyMessage   : "modify status!",
	defaultText     : "item",
	defaultAction   : null,
	defaultBehavior : "classic",
	usePersistence	: true
};
 
JqTree.create = function(sRootText,oTreeObject,sSrc,sChildSrc,aAttributes,oActionHandler,bShowCheckbox,bOnlySelectLeaf,bShowIcon,bIsDraggable,bIsDroppable,sRootId,aLinks,bOnlyCheckSelf,oSrcParams){
	this.rootText = (!sRootText)?"root":sRootText;
	this.rootId = (typeof(sRootId)=="undefined")?"":sRootId;
	this.treeObject = oTreeObject;
	this.src = sSrc;
	this.childSrc = (!sChildSrc)?sSrc:sChildSrc;
	this.attributes = (!aAttributes)?new Array():aAttributes;
	this.showCheckbox = (!bShowCheckbox)?false:bShowCheckbox;
	this.actionHandler = oActionHandler;
	this.showIcon = (bShowIcon!=false)?true:bShowIcon;//
	if(this.showCheckbox==false){
		this.showIcon = true;
	}
	this.onlySelectLeaf = bOnlySelectLeaf;
	this.isDraggable = bIsDraggable;
	this.isDroppable = bIsDroppable;
	this.links = aLinks;
	this.onlyCheckSelf = (bOnlyCheckSelf==null)?false:bOnlyCheckSelf;
	this.srcParams = oSrcParams;
	this.init();
};


JqTree.create.prototype = {
	rootText:null,//
	
	treeObject:null,//JqTree.TreeObject 
	
	src:null,//
	
	childSrc:null,//
	
	attributes:new Array(),//
	
	showCheckbox:false,
	
	showIcon:true,
	
	tree:null,//jQuery element --ul
	
	treeRoot:null,//jQuery element--li
	
	selectedNode:null,//JqTree.JqTreeNode
	
	maxWidth:0,//width of the tree
	
	removedDataObjects:new Array(),//
	/**
	 *  
	 *  init tree
	 */
	init:function(){
		var p = this;
		this.tree = $("<div class=\"jTree_container\"></div>");
		this.treeUl = $("<ul class=\"jTree_ul\"></ul>");
		this.treeRoot =  $("<li class=\"jTree_root_li\"></li>");
		this.treeRootDiv = $("<div isRoot=\"true\" id=\"item_"+this.rootId+"\" class=\"jTree_item\"></div>");
		this.treeRootDiv.attr("hasLoad","true");
		this.treeRootDiv.attr("expanded","true");
		this.treeRootDiv.attr("hasChildren","true");
		
		var rootTextSpan = $("<span style=\"margin-top:3px;\" class=\"jTree_item_text\">"+this.rootText+"</span>");
		
		this.treeRootDiv.append(rootTextSpan);
		this.treeRootDiv.prepend("<div class=\"jTree_root_item_img\"><img src=\""+JqTree.treeConfig.rootIcon+"\"></img></div>");
		
		var jqTreeRootNode = new JqTree.JqTreeNode.create(this.treeRootDiv,null,this.treeObject);//
		
		this.treeRootDiv.mousedown(function(event){
			p.stopBubble(event);
			if(!p.selectedNode){//
	  			p.selectedNode = jqTreeRootNode;//
	  			$("span.jTree_item_text",this).get(0).style.color = JqTree.treeConfig.selectedColor;//
	  			$("span.jTree_item_text",this).get(0).style.backgroundColor=JqTree.treeConfig.selectedBgColor;//
	  		}else{//
	  			if(p.selectedNode.toString()==jqTreeRootNode.toString()){//
	  				
	  			}else{
	  				if(p.selectedNode.getAttribute("isModify")=="true"){
	  					alert(JqTree.treeConfig.modifyMessage);
	  					return;
	  				}
	  				
	  				if(p.selectedNode.node.attr("changed")=="true"){
	  					if(p.selectedNode.node.attr("isNew")=="true"){
	  						$("span.jTree_item_text:first",p.selectedNode.node).get(0).style.color=JqTree.treeConfig.newColor;
	  					}else{
	  						$("span.jTree_item_text:first",p.selectedNode.node).get(0).style.color=JqTree.treeConfig.changedColor;
	  					}
	  				}else{
	  					if(p.showCheckbox==true&&p.selectedNode.node.attr("showIcon")!="true"){
	  						if($("input.jTree_checkbox:first",p.selectedNode.node).get(0).checked==true){
			  					$("span.jTree_item_text:first",p.selectedNode.node).get(0).style.color=JqTree.treeConfig.checkedColor;
			  				}else{
			  					$("span.jTree_item_text:first",p.selectedNode.node).get(0).style.color=JqTree.treeConfig.normalColor;
			  				}
	  					}else{
		  					$("span.jTree_item_text:first",p.selectedNode.node).get(0).style.color=JqTree.treeConfig.normalColor;
		  				}
	  				}
	  				$("span.jTree_item_text",p.selectedNode.node).get(0).style.backgroundColor="";
	  				p.selectedNode = jqTreeRootNode;
	  				$("span.jTree_item_text",this).get(0).style.color = JqTree.treeConfig.selectedColor;//
	  				$("span.jTree_item_text",this).get(0).style.backgroundColor=JqTree.treeConfig.selectedBgColor;//
	  			}
	  		}
		});
		
		if(this.isDraggable==true){
			this._addDrag(this.treeRootDiv);
		}
		//this.treeRootDiv
		this.treeRoot.append(this.treeRootDiv);
		this.treeUl.append(this.treeRoot);
		this.tree.append(this.treeUl);
		this.loadSrc(this.treeRootDiv,this.src);
	},
	
	reload:function(){
		var container = $(this.tree.get(0).parentNode);
		this.tree.remove();
		container.html("");
		this.init();
		container.append(this.tree);
	},
	
	loadSrc:function(eParentNode,src,openId){
		var p = this;
		var parentNode = $(eParentNode);
		
		var node = $("<ul class=\"jTree_ul\"/>");
		$(parentNode).append(node);
		var loading = $("<li class=\"jTree_li\"/>");//
		var loadingImg = $("<img></img>");//
		loadingImg.attr("src",JqTree.treeConfig.loadingIcon);//add loading ico
		
		loading.append(loadingImg);
		loading.append("<span style=\"margin-top:-5px;\">"+JqTree.treeConfig.loadingText+"</span>");
		node.append(loading);
		parentNode.attr("hasChildren","true");
		parentNode.attr("hasLoad","true");//
		parentNode.attr("expanded","true");//
		if(src.length>2048){		
			var loadSrc = "";
			var params = null;
			if(src.indexOf("?")!=-1){
				loadSrc = src.substring(0,src.indexOf("?"));
				params = src.substring(src.indexOf("?")+1);
				//alert(params);
			}else{
				loadSrc = src;
			}
			$.ajax({
				contentType : "application/x-www-form-urlencoded;charset=UTF-8",//
				type		: "POST",
	  			url			: loadSrc,
	  			dataType	: "xml",
	  			success		:  doLoad,
	  			data		:params
			});
		}else{
			$.get(src,doLoad); 
		}
		
		function doLoad(xml){
			loading.remove();
			if($(JqTree.treeConfig.dataNodeName,xml).size()==0||$(JqTree.treeConfig.dataNodeName,xml).text()==""){//if has not child
				parentNode.attr("hasChildren","false");
				
				$("img.jTree_icon:first",parentNode).attr("src",parentNode.attr("fileIcon"));
				
				if(parentNode.attr("isLast")=="true"){
					$(parentNode.get(0).parentNode).removeClass("lastExpandable").removeClass("lastCollapsable").addClass("last");
				}else{
					$(parentNode.get(0).parentNode).removeClass("expandable").removeClass("collapsable");
				}
			}
			
			$(JqTree.treeConfig.dataNodeName,xml).each(function(){
				p._addNode(node,this,openId);
			});
			
			if($("div.jTree_item:last",node).attr("expand")!="true"){
				$("li:last",node).removeClass("expandable").addClass("lastExpandable");
			}else{
				$("li:last",node).removeClass("expandable").addClass("last");
			}
			$("div.jTree_item:last",node).attr("isLast","true");
			
		}
		
		return node;
	},
	
	_addNode:function(parentNode,record,openId){
		var id   = $(this.treeObject.getId(),record).text();//tree id
		
	  	var text = $(this.treeObject.getText(),record).text();//tree show text
	  	var pid = "item_"+$(this.treeObject.getPid(),record).text();
		
		if($("id",record).text()){
	  		id = $("id",record).text();
	  	}
	  	
	  	if($("pid",record).text()){
	  		pid = $("pid",record).text();
	  	}
	  	
	  	if($("text",record).text()){
	  		text = $("text",record).text();
	  	}
	  	var dataObject = new Array();//
	  	//dm_wxml
		var attributeName;
		var attributeValue;
		for(var i=0;i<this.attributes.length;i++){
	  		attributeName = this.attributes[i];
	  		attributeValue = $(attributeName,record).text();
	  		dataObject[attributeName]=attributeValue;//set aAttributes
	  		attributeName = null;
	  		attributeValue = null;
		}
		
		var src;
		var expand;
		var target;
		var checked;
		var showIcon;
		var fileIcon;
		var folderIcon;
		var openFolderIcon;
		
		src = $("src",record).text();
		expand = $("expand",record).text();
		target = $("target",record).text();
		checked = $("checked",record).text();
		
		showIcon = $("showIcon",record).text();
		fileIcon = $("fileIcon",record).text();
		folderIcon = $("folderIcon",record).text();
		openFolderIcon = $("openFolderIcon",record).text();
		if(src)dataObject["src"]=src;
		if(expand)dataObject["expand"]=expand;
		if(target)dataObject["target"]=target;
		if(showIcon)dataObject["showIcon"]=showIcon;
		if(checked!=null)dataObject["checked"]=checked;
		//alert(dataObject["checked"]);
		fileIcon = (!fileIcon)?JqTree.treeConfig.fileIcon:fileIcon;
		dataObject["fileIcon"]=fileIcon;
		
		folderIcon = (!folderIcon)?JqTree.treeConfig.folderIcon:folderIcon;
		dataObject["folderIcon"]=folderIcon;
		
		openFolderIcon = (!openFolderIcon)?JqTree.treeConfig.openFolderIcon:openFolderIcon;
		dataObject["openFolderIcon"]=openFolderIcon;
		
		$(parentNode).parent("div.jTree_item").attr("changed","false");//
	  	return this.addTreeNode(parentNode,id,text,dataObject,"child",pid,openId);//
	},
	
	addNode:function(id,text,type,dataObject,fileIcon,folderIcon,openFolderIcon){
		type = (!type)?"child":type;//
		var	jqTreeNode = this.getSelectedNode();
		if(!jqTreeNode)return;
		
		if((type=="before"||type=="after")&&jqTreeNode.getAttribute("isRoot")=="true"){
			return;
		}
		
		if(jqTreeNode.node.attr("hasLoad")=="true"){//
			var openPath = jqTreeNode.getAttribute("openPath");
			if(openPath){
				this.goOpenPath(openPath);
			}
			
			var pid =  jqTreeNode.getAttribute("id");
			var relativeNode;
			relativeNode = $("ul:first",jqTreeNode.node);
			if(relativeNode.size()==0){
				relativeNode = $("<ul class=\"jTree_ul\"></ul>");
				this.selectedNode.node.append(relativeNode);
			}
			//alert(this.selectedNode.node.html());
			var relativeDivNode = jqTreeNode.node;
			var relativeLiNode = $(jqTreeNode.node.get(0).parentNode);
			
			var treeNodeDiv = this.addTreeNode(relativeNode,id,text,dataObject,type,pid);
			var treeNodeLi = $(treeNodeDiv.get(0).parentNode);
			this._setNodeView(treeNodeDiv);
			//set default attribute
			treeNodeLi.removeClass("expandable");//remove the default add style expandable
			treeNodeDiv.attr("hasChildren","false");//
			treeNodeDiv.attr("hasLoad","true");//
			treeNodeDiv.attr("changed","true");
			treeNodeDiv.attr("isNew","true");
			treeNodeDiv.attr("expanded","true");
			fileIcon = (!fileIcon)?JqTree.treeConfig.fileIcon:fileIcon;
			
			folderIcon = (!folderIcon)?JqTree.treeConfig.folderIcon:folderIcon;
			openFolderIcon = (!openFolderIcon)?JqTree.treeConfig.openFolderIcon:openFolderIcon;
			$("img.jTree_icon",treeNodeDiv).attr("src",fileIcon);
			
			treeNodeDiv.attr("fileIcon",fileIcon);
			treeNodeDiv.attr("folderIcon",folderIcon);
			treeNodeDiv.attr("openFolderIcon",openFolderIcon);
			
			switch(type){
				case "child":
					//relativeLiNode
					if(relativeDivNode.attr("isRoot")=="true"){
						
					}else{
						this.expandNode(relativeDivNode);
					}
					var lastChildDiv = $("div.jTree_item[@isLast=true][@pid="+pid+"]",relativeDivNode);
					
					if(lastChildDiv.size()>0){
						this._setNormalNode(lastChildDiv);
					}else{
						if(relativeDivNode.attr("isRoot")!="true"){
							relativeDivNode.attr("hasChildren","true");
							var openFolderIcon = relativeDivNode.attr("openFolderIcon");
							openFolderIcon = (!openFolderIcon)?JqTree.treeConfig.openFolderIcon:openFolderIcon;
							$("img.jTree_icon:first",relativeDivNode).attr("src",openFolderIcon);
							if(relativeDivNode.attr("isLast")=="true"){
								relativeLiNode.removeClass("last").addClass("lastCollapsable");
							}else{
								relativeLiNode.addClass("collapsable");
							}
						}
					}
					//dropUl.append(dragLi);
					relativeLiNode.attr("pid",pid);
					this._setLastNode(treeNodeDiv);
					//relativeDivNode.attr("hasChildren",true);
					treeNodeDiv.focus();
					break;
				case "before":
					treeNodeDiv.focus();
					break;
				case "after":
					
					if(relativeDivNode.attr("isLast")=="true"){//
						relativeDivNode.removeAttr("isLast");
						treeNodeLi.addClass("last");
						treeNodeDiv.attr("isLast","true");
					}else{
						
					}
					
					if(relativeDivNode.attr("hasChildren")=="true"){//
						if(relativeDivNode.attr("expanded")=="true"){//
							relativeLiNode.removeClass("lastCollapsable").addClass("collapsable");
						}else{
							relativeLiNode.removeClass("lastExpandable").addClass("expandable");
						}
					}else{
						relativeLiNode.removeClass("last").removeClass("collapsable");
					}
					//relativeNode = $("ul:first",this.selectedNode.node.parent("li"));
					treeNodeDiv.focus();
					break;
				case "firstChild":
					if(relativeDivNode.attr("isRoot")!="true"){
						this.expandNode(relativeDivNode);
					}
					treeNodeDiv.focus();
					break;
			}
			
		}else{
			
		}
	},
	/**
	 * 
	 */
	removeNode:function(jqTreeNode){
		var p = this;
		if(!jqTreeNode){
			jqTreeNode = this.getSelectedNode();
		}
		if(!jqTreeNode)return;
		if(jqTreeNode.getAttribute("isRoot")=="true"){
			alert(JqTree.Resource.delete_root_message);
			return;
		}
		//set the attrubute isModify false
		jqTreeNode.node.removeAttr("isModify"); 
		//
		var parentJqTreeNode = this.getParent(jqTreeNode);
		var parentDivNode = parentJqTreeNode.node;
		
		this.removedDataObjects[this.removedDataObjects.length] = jqTreeNode.getDataObject();
		/**
		 * add the children to removed data set.
		 */
		$("div.jTree_item",jqTreeNode.node).each(function(){
			var textSpan = $("span.jTree_item_text",this).get(0);
			p.removedDataObjects[p.removedDataObjects.length] = textSpan.jqTreeNode.getDataObject();
		});
		//add the deleted the 
		//
		
		var nextJqTreeNode = this.getNextJqTreeNode(jqTreeNode);
		if(nextJqTreeNode!=null){
			nextJqTreeNode.node.mousedown();
		}else{
			this.selectedNode = null;
		}
		jqTreeNode.remove();
		var newLastDiv = $("div.jTree_item[@pid="+parentDivNode.attr("id")+"]:last",parentDivNode);
		//alert(parentDivNode.html());
		if(newLastDiv.size()>0){
			p._setLastNode(newLastDiv);
		}else{
			p._setFileNode(parentDivNode);
		}
		//$("",jqTreeNode.node.parent().next()).click();
	},
	/**
	 * 
	 */
	removeCheckedNodes:function(){
		var checkedNodes = this.getCheckedJqTreeNodes();
		for(var i=0;i<checkedNodes.length;i++){
			this.removeNode(checkedNodes[i]);
		}
	},
	/**
	 * @param parentNode
	 * @param id
	 * @param dataObject
	 * @param type
	 */
	addTreeNode:function(relativeNode,id,text,dataObject,type,pid,openId){
		var p = this;
		var treeNode;
	  	var pidName = this.treeObject.getPid();//
	  	//var pid = $(relativeNode).parent("div.jTree_item").attr("id");
	  	//var pid = $($(relativeNode).get(0).parentNode).attr("id");
	  	
	  	if(id){
	  		var params = pidName+"="+id;
	  		if(JqTree.treeConfig.paramPrefix){
	  			params = JqTree.treeConfig.paramPrefix+params;//
	  		}
		  	var src = this.childSrc;
		  	if(src.indexOf("?")==-1){
		  		src+="?";
		  	}else{
		  		src+="&";
		  	}
		  	src+=params;//get query url
		  	
		  	if(this.srcParams){
		  		for(var i=0;i<this.attributes.length;i++){
			  		var attributeName = this.attributes[i];
			  		attributeValue = dataObject[attributeName];
			  		if(this.srcParams[attributeName]){
			  			src+="&";
			  			src+=JqTree.treeConfig.paramPrefix;
			  			src+=attributeName;
			  			src+="=";
			  			src+=attributeValue;
			  		}
			  		attributeName = null;
			  		attributeValue = null;
				}
		  	}

		  	treeNode = $("<li class=\"jTree_li expandable\"/>");//li > div > input (span > div > img)
		  	
		  	treeNodeDiv = $("<div id=\"item_"+id+"\" class=\"jTree_item\"/>");
		  	
		  	var textSpan = $("<span class=\"jTree_item_text\">"+text+"</span>");
		  	treeNodeDiv.attr("title",textSpan.text());
		  	//textSpan.append("<a href=\"\">|kjl</a>");
		  	treeNodeDiv.append(textSpan);
		  	
		  	treeNodeDiv.attr("src",src);//set the node attribute "src"
		  	treeNodeDiv.attr("text",text);//set the node attribute "text"
		  	treeNodeDiv.attr("pidName",pidName);//set the node attribute "pidName"
		  	treeNodeDiv.attr("pid",pid);
		  	//alert(pid);
		  	//textSpan.Draggable();
		  	/*
		  	 * 
		  	 */
		  	var attributeValue;
		  	for(var attributeName in dataObject){
		  		attributeValue = dataObject[attributeName];
		  		treeNodeDiv.attr(attributeName,attributeValue);
		  	}
		  	var jqTreeNode = new JqTree.JqTreeNode.create(treeNodeDiv,this.actionHandler,this.treeObject,dataObject);//
		  	
		  	if(treeNodeDiv.attr("showIcon")!="true"&&this.showCheckbox==true){
		  		var checkbox = $("<input name=\"jTree_checkbox\" checkFlag=\"unchecked\" class=\"jTree_checkbox\" id=\"checkbox_"+id+"\" type=\"checkbox\"/>");
		  		if(dataObject["checked"]=="true"){
		  			checkbox = $("<input name=\"jTree_checkbox\" checked checkFlag=\"unchecked\" class=\"jTree_checkbox\" id=\"checkbox_"+id+"\" type=\"checkbox\"/>");
		  		}
		  		
		  		treeNodeDiv.prepend(checkbox);//checkbox
		  		checkbox.dblclick(function(event){
		  			p.stopBubble(event);
		  		});
		  		
		  		checkbox.mousedown(function(){
		  			if(!p.selectedNode){//
			  			p.selectedNode = jqTreeNode;//
			  			$("span.jTree_item_text:first",this.parentNode).get(0).style.color = JqTree.treeConfig.selectedColor;//
			  			$("span.jTree_item_text:first",this.parentNode).get(0).style.backgroundColor=JqTree.treeConfig.selectedBgColor;//
			  		}else{//
			  			if(p.selectedNode.toString()==jqTreeNode.toString()){//
			  				
			  			}else{
			  				if(p.selectedNode.getAttribute("isModify")=="true"){
			  					alert(JqTree.treeConfig.modifyMessage);
			  					return;
			  				}
			  				
			  				if(p.selectedNode.node.attr("changed")=="true"){
			  					if(p.selectedNode.node.attr("isNew")=="true"){
			  						$("span.jTree_item_text:first",p.selectedNode.node).get(0).style.color=JqTree.treeConfig.newColor;
			  					}else{
			  						$("span.jTree_item_text:first",p.selectedNode.node).get(0).style.color=JqTree.treeConfig.changedColor;
			  					}
			  				}else{
			  					if(p.showCheckbox==true&&p.selectedNode.node.attr("showIcon")!="true"){
			  						if($("input.jTree_checkbox:first",p.selectedNode.node).size()>0&&$("input.jTree_checkbox:first",p.selectedNode.node).get(0).checked==true){
					  					$("span.jTree_item_text:first",p.selectedNode.node).get(0).style.color=JqTree.treeConfig.checkedColor;
					  				}else{
					  					$("span.jTree_item_text:first",p.selectedNode.node).get(0).style.color=JqTree.treeConfig.normalColor;
					  				}
			  					}else{
				  					$("span.jTree_item_text:first",p.selectedNode.node).get(0).style.color=JqTree.treeConfig.normalColor;
				  				}
			  				}
			  				
			  				if(p.selectedNode.getAttribute("isRoot")=="true"){
			  					$("span.jTree_item_text:first",p.selectedNode.node).get(0).style.color=JqTree.treeConfig.normalColor;
			  				}
			  				$("span.jTree_item_text:first",p.selectedNode.node).get(0).style.backgroundColor="";
			  				p.selectedNode = jqTreeNode;
			  				$("span.jTree_item_text:first",this.parentNode).get(0).style.color = JqTree.treeConfig.selectedColor;//
			  				$("span.jTree_item_text:first",this.parentNode).get(0).style.backgroundColor=JqTree.treeConfig.selectedBgColor;//
			  			}
			  		}
		  			return false;
		  		});
		  		
		  		checkbox.click(function(event){
			  		p.stopBubble(event);
			  		if($(this.parentNode).attr("hasLoad")!="true"){
			  			$(this.parentNode).mousedown();
			  			return false;
			  		}
			  		var textSpan = $("span.jTree_item_text",$(this).parent());
			  		if(p.onlySelectLeaf==true&&$(this.parentNode).attr("hasLoad")=="true"&&$(this.parentNode).attr("hasChildren")=="true"){
			  			this.checked = false;
			  			
			  			return false;
			  		}else{
			  			if(p.onlyCheckSelf==true){
			  				if(this.checked==false){//
					  			$("span.jTree_item_text:first",$(this).parent()).css("color","black");
					  			//textSpan.css("color","black");
					  		}else{
					  			$("span.jTree_item_text:first",$(this).parent()).css("color",JqTree.treeConfig.checkedColor);
					  		}
			  			}else{
			  				if(!this.checked){//
					  			textSpan.css("color","black");
					  			textSpan.each(function(){
					  				if(p.selectedNode&&p.selectedNode.toString()==this.jqTreeNode.toString()){
						  				$(this).css("color",JqTree.treeConfig.selectedColor);
						  			}
					  			});
					  			
					  			$("input.jTree_checkbox",$(this).parent()).not(this).each(function(){
					  				this.checked = false;
					  			});
			
					  			$(this).parent().parents("div.jTree_item",p.tree).each(function(){
					  				p._setCheckFlag($("span.jTree_item_text",this).get(0).jqTreeNode);//
					  			});
					  		}else{
					  			$("input.jTree_checkbox",$(this).parent()).not(this).attr("checked","checked");
					  			textSpan.css("color",JqTree.treeConfig.checkedColor);
					  		}
			  			}
			  		}
			  		//
			  	});
		  	}
		  	
		  	if(this.showIcon==true||treeNodeDiv.attr("showIcon")=="true"){
		  		var iconSpan = $("<span class=\"jTree_icon_span\"/>");
		  		var iconDiv = $("<span class=\"jTree_icon_div\"/>");
		  		var icon = $("<img class=\"jTree_icon\"/>");
		  		var folderIcon = treeNodeDiv.attr("folderIcon");
		  		folderIcon = (!folderIcon)?JqTree.treeConfig.folderIcon:folderIcon;
		  		icon.attr("src",folderIcon);
		  		iconSpan.append(iconDiv);
		  		iconDiv.append(icon);
		  		treeNodeDiv.prepend(iconSpan);//checkbox
		  	}
		  	
		  	var imgDiv = $("<div class=\"jTree_item_img\"/>");//
		  	//var imgSpan = $("<span class=\"jTree_item_img\"></s>");
		  	treeNodeDiv.prepend(imgDiv);//
		  	
		  	var dragSpan;
		  	if(this.isDraggable==true){
		  		dragSpan = this._addDrag(treeNodeDiv);
		  	}

		  	/*
		  	 * 
		  	 */
			imgDiv.mousedown(function(event){
				p.stopBubble(event);
				$(this.parentNode).dblclick();
			});
			
			imgDiv.click(function(event){
				p.stopBubble(event);
			});
			
			imgDiv.dblclick(function(event){
				p.stopBubble(event);
			});
			
			
		  	treeNode.append(treeNodeDiv);//
			//treeNode.append(dragDiv);
			treeNode.get(0).onselectstart = function(){return false};
		  	treeNodeDiv.mousedown(function(event){
		  		p.stopBubble(event);//
		  		p._setNodeView($(this));
		  		
		  		$("body",document).mousedown();
		  		var nodeParents = $(this).parents("div.jTree_item",p.tree);
		  		var openPath = "";
		  		var openPathArray = new Array();
		  		openPathArray[0] = id;
		  		nodeParents.each(function(){
		  			var treeId = this.getAttribute("id");
		  			if(treeId){
		  				openPathArray[openPathArray.length] = treeId.substring(5);
		  			}
		  		});
		  		
		  		$(this).attr("openPath",openPathArray.reverse().join("/"));
	
		  		var level = nodeParents.size();
		  		$(this).attr("level",level);
		  		
		  		if(!p.selectedNode){//
		  			p.selectedNode = jqTreeNode;//
		  			$("span.jTree_item_text:first",this).get(0).style.color = JqTree.treeConfig.selectedColor;//
		  			$("span.jTree_item_text:first",this).get(0).style.backgroundColor=JqTree.treeConfig.selectedBgColor;//
		  		}else{//
		  			if(p.selectedNode.toString()==jqTreeNode.toString()){//
		  				
		  			}else{
		  				if(p.selectedNode.getAttribute("isModify")=="true"){
		  					alert(JqTree.treeConfig.modifyMessage);
		  					return;
		  				}
		  				
		  				if(p.selectedNode.node.attr("changed")=="true"){
		  					if(p.selectedNode.node.attr("isNew")=="true"){
		  						$("span.jTree_item_text:first",p.selectedNode.node).get(0).style.color=JqTree.treeConfig.newColor;
		  					}else{
		  						$("span.jTree_item_text:first",p.selectedNode.node).get(0).style.color=JqTree.treeConfig.changedColor;
		  					}
		  				}else{
		  					if(p.showCheckbox==true&&p.selectedNode.node.attr("showIcon")!="true"){
		  						if($("input.jTree_checkbox:first",p.selectedNode.node).size()>0&&$("input.jTree_checkbox:first",p.selectedNode.node).get(0).checked==true){
				  					$("span.jTree_item_text:first",p.selectedNode.node).get(0).style.color=JqTree.treeConfig.checkedColor;
				  				}else{
				  					$("span.jTree_item_text:first",p.selectedNode.node).get(0).style.color=JqTree.treeConfig.normalColor;
				  				}
		  					}else{
			  					$("span.jTree_item_text:first",p.selectedNode.node).get(0).style.color=JqTree.treeConfig.normalColor;
			  				}
		  				}
		  				
		  				if(p.selectedNode.getAttribute("isRoot")=="true"){
		  					$("span.jTree_item_text:first",p.selectedNode.node).get(0).style.color=JqTree.treeConfig.normalColor;
		  				}
		  				$("span.jTree_item_text:first",p.selectedNode.node).get(0).style.backgroundColor="";
		  				p.selectedNode = jqTreeNode;
		  				$("span.jTree_item_text:first",this).get(0).style.color = JqTree.treeConfig.selectedColor;//
		  				$("span.jTree_item_text:first",this).get(0).style.backgroundColor=JqTree.treeConfig.selectedBgColor;//
		  			}
		  		}
		  		
		  		if(this.getAttribute("hasChildren")=="false"){//
		  			return;
		  		}
		  		if(this.getAttribute("hasLoad")!="true"){//
		  			var goPath = this.getAttribute("goPath");
		  			var openId;
		  			if(goPath){
		  				var pathIdArray = new Array();
			  			pathIdArray = goPath.split("/");
			  			for(var i=0;i<pathIdArray.length;i++){
			  				if("item_"+pathIdArray[i]==this.getAttribute("id")){
			  					openId = pathIdArray[i+1];
			  					break;
			  				}
			  			}
		  			}
		  			p.loadSrc(this,this.src,openId);//
		  			p.expandNode(this);
		  		}else{
		  			
		  		}
		  		//this.focus();
		  	});
		  	
		  	treeNodeDiv.dblclick(function(event){
		  		p.stopBubble(event);//
		  		
		  		if(this.getAttribute("hasChildren")=="false"){//
		  			return;
		  		}
		  		if(this.getAttribute("hasLoad")!="true"){//
		  			p.loadSrc(this,this.src);//
		  			p.expandNode(this);
		  		}else{//
		  			if(this.getAttribute("expanded")=="true"){//if has expanded,toggle it
		  				p.collapseNode(this);
		  			}else{//else has not expanded,expand it
		  				p.expandNode(this);
		  			}
		  		}
		  		//this.focus();
		  	});
		  	/*
		  	 * 
		  	 */
		  	
		  	treeNodeDiv.click(function(){
		  		if(p.selectedNode.node.attr("id")==this.getAttribute("id")){
		  			$("span.jTree_item_text:first",this).click();
		  		}
		  		//return false;
		  	});
		  	treeNodeDiv.focus(function(){
		  		$("span.jTree_item_text",this).get(0).style.color=JqTree.treeConfig.newColor;//
		  	});
		  	
		  	treeNodeDiv.blur(function(){
		  		//$("span.jTree_item_text",this).get(0).style.backgroundColor=JqTree.treeConfig.normalColor;//
		  	});
		  	//contral expand
		  	switch(type){
		  		case "child":
					relativeNode.append(treeNode);//
					break;
				case "before":
					treeNode.insertBefore(relativeNode.parent().parent());
					break;
				case "after":
					treeNode.insertAfter(relativeNode.parent().parent());
					break;
				case "firstChild":
					relativeNode.prepend(treeNode);//
					break;
		  	}
		  	
		  	if(treeNodeDiv.attr("expand")=="true"){
		  		treeNodeDiv.attr("expanded","true");
		  		treeNodeDiv.attr("hasChildren","false");
		  		treeNodeDiv.attr("hasLoad","true");
		  		$("img.jTree_icon:first",treeNodeDiv).attr("src",treeNodeDiv.attr("fileIcon"));
		  		treeNode.removeClass("expandable");
		  	}
		  	
		  	if(openId&&openId==id){
		  		var goPath = treeNodeDiv.get(0).parentNode.parentNode.parentNode.getAttribute("goPath");
		  		var pathIdArray = new Array();
			  	pathIdArray = goPath.split("/");
		  		if(id!=pathIdArray[pathIdArray.length-1]){
		  			treeNodeDiv.attr("goPath",goPath);
		  			treeNodeDiv.mousedown();
		  		}else{
		  			treeNodeDiv.mousedown();
		  		}
		  	}
		  	/*
		  	 * do with width
		  	 */
		  	 
		  	if(dragSpan){
		  		var offsetWidth = parseInt(textSpan.get(0).offsetWidth);
		  		var offsetLeft = -5-offsetWidth;
		  		dragSpan.css("width",offsetWidth+10);
		  		dragSpan.css("left",offsetLeft);
		  		var dragId = jQuery.iDrag.helper.attr("dragId");
				if(typeof(dragId)!="undefined"&&this.isDroppable==true){
					dragSpan.addClass("jTree_item_drop_active");
				}
		  	}
		  	return treeNodeDiv;
	  	}
	},
	/**
	 * return checkFlag  checked|unchecked|half
	 */
	_setCheckFlag:function(jqTreeNode){

	},
	
	/**
	 * 
	 */
	_addDrag:function(treeDivNode){
		var p = this;
		var dragSpan = $("<span class=\"jTree_item_drag\"></span>");
		treeDivNode.append(dragSpan);
		
		dragSpan.Draggable({
			revert : true,
			autoSize : true,
			ghosting : true,
			cursorAt: { top: -5, left: -5 },
			frameClass:'jTree_item_drag_frame',
			onStart:function(){
				jQuery.iDrag.helper.html(treeDivNode.attr("text"));
				jQuery.iDrag.helper.css("width","auto");
				jQuery.iDrag.helper.css("cursor","pointer");
				jQuery.iDrag.helper.attr("dragTop",this.parentNode.offsetTop);
				if(p.isDroppable==true){
					jQuery.iDrag.helper.attr("dragId",treeDivNode.attr("id"));
					$("span.jTree_item_drag",p.tree).addClass("jTree_item_drop_active");
					
				}
			},
			onStop : function(){
				if(p.isDroppable==true){
					$("span.jTree_item_drag",p.tree).removeClass("jTree_item_drop_active");
					jQuery.iDrag.helper.removeAttr("dragId");
				}
			}
			
		});
		
		if(p.isDroppable==true){
			dragSpan.mouseover(function(){
				var dragId = jQuery.iDrag.helper.attr("dragId");
				var dropDiv = $(this.parentNode);
				var dropId = dropDiv.attr("id");
				
				var dragDiv = $("div.jTree_item[@id="+dragId+"]",p.tree);
				if(dragId==dropId||$("div.jTree_item[@id="+dropId+"]",dragDiv).size()>0){
					return;
				}
				
				if(typeof(dragId)!="undefined"){
					jQuery.iDrag.helper.attr("dropable","true");
					$(this).addClass("jTree_item_drop_hover");
					
					if(dropDiv.attr("isRoot")=="true")return;
					if(dropDiv.attr("hasLoad")=="true"){
						if(dropDiv.attr("expanded")=="false"){
							dropDiv.dblclick();
						}
					}else{
						dropDiv.mousedown();
					}
				}
			});
			
			dragSpan.mouseout(function(){
				if(jQuery.iDrag.helper.attr("dropable")=="true"){
					$(this).removeClass("jTree_item_drop_hover");
					var treeContainer = p.tree.get(0).parentNode;
					var offsetBottom = treeContainer.offsetHeight+treeContainer.scrollTop-this.parentNode.offsetTop-p.tree.get(0).offsetTop;
					//roll down
					if(offsetBottom<50&&offsetBottom>0){
						treeContainer.scrollTop = this.parentNode.offsetTop+51+p.tree.get(0).offsetTop-treeContainer.offsetHeight;
					}
					//roll up
					if(this.parentNode.offsetTop-treeContainer.scrollTop<30){
						treeContainer.scrollTop = treeContainer.scrollTop-20;
					}
						
				}
			});
			
			dragSpan.mouseup(function(){
				if(jQuery.iDrag.helper.attr("dropable")=="true"){
					$(this).removeClass("jTree_item_drop_hover");
					var dragId = jQuery.iDrag.helper.attr("dragId");
					var dragDiv = $("div.jTree_item[@id="+dragId+"]",p.tree);
					try{
						var dragLi = $(dragDiv.get(0).parentNode);
					}catch(err){
						return;
					}
					var dragPid = dragDiv.attr("pid");
					
					var dropDiv = $(this.parentNode);
					var dropLi = $(this.parentNode.parentNode);
					var dropUl = $("ul.jTree_ul:first",dropDiv);
					var dropId = dropDiv.attr("id");
					var dropPid = dropDiv.attr("pid");
					var dragParentDiv = $(dragLi.get(0).parentNode.parentNode);//li->ul->div
					var dragParentLi = $(dragDiv.get(0).parentNode.parentNode.parentNode);//li->ul->div->li
					var dragParentId = dragParentDiv.attr("id");
					
					if(dragId==dropId||dragId==dropPid||dragPid==dropId||$("div.jTree_item[@id="+dropId+"]",dragDiv).size()>0){
						return;
					}
					
					var isLast = dragDiv.attr("isLast");
					if(dropUl.size()==0){
						dropUl = $("<ul class=\"jTree_ul\"></ul>");
						dropDiv.append(dropUl);
					}
					//set the changed
					p._setChanged(dragDiv);
					//changedColor
					
					if(dropDiv.attr("hasLoad")){
						//drop relative node action
						switch(JqTree.treeConfig.dragType){
							case "child":
								//alert("div.jTree_item[@isLast=true][@pid="+dropId+"]");						
								var lastChildDiv = $("div.jTree_item[@isLast=true][@pid="+dropId+"]",dropDiv);
								if(lastChildDiv.size()>0){
									p._setNormalNode(lastChildDiv);
								}else{
									dropDiv.attr("hasChildren","true");
									var openFolderIcon = dropDiv.attr("openFolderIcon");
									openFolderIcon = (!openFolderIcon)?JqTree.treeConfig.openFolderIcon:openFolderIcon;
									$("img.jTree_icon:first",dropDiv).attr("src",openFolderIcon);
									if(dropDiv.attr("isLast")=="true"){
										dropLi.removeClass("last").addClass("lastCollapsable");
									}else{
										dropLi.addClass("collapsable");
									}
								}
								dropUl.append(dragLi);
								dragDiv.attr("pid",dropId);
								p._setLastNode(dragDiv);
								break;
							case "firstChild":
								dragDiv.attr("pid",dropId);
								dropUl.prepend(dragLi);
								if($("div.jTree_item",dropUl).size()==0){
									p._setLastNode(dragDiv);
								}
								break;
							case "before":
								dragDiv.attr("pid",dropPid);
								dragLi.insertBefore(dropLi);
								break;
							case "after":
								dragDiv.attr("pid",dropPid);
								dragLi.insertAfter(dropLi);
								if(dropDiv.attr("isLast")=="true"){
									p._setNormalNode(dropDiv);
									p._setLastNode(dragDiv);
								}
								break;
						}
						
						//drag relative node action
						var newLastDiv = $("div.jTree_item[@pid="+dragParentId+"]:last",dragParentDiv);
						if(newLastDiv.size()>0){
							p._setLastNode(newLastDiv);
						}else{
							p._setFileNode(dragParentDiv);
						}
					}
				}
			});
		}
		return dragSpan;
	},
	
	_setChanged:function(node){
		var jNode = $(node);
		jNode.attr("changed","true");
		$("span.jTree_item_text:first",jNode).get(0).style.color = JqTree.treeConfig.changedColor;
	},
	
	_setLastNode:function(node){
		var jNode = $(node);
		var liNode = $(jNode.get(0).parentNode);
		
		var hasChildren = jNode.attr("hasChildren");
		var expanded = jNode.attr("expanded");
		
		jNode.attr("isLast","true");
		
		if(jNode.attr("hasLoad")=="true"){
			if(hasChildren=="true"){
				if(expanded=="true"){
					liNode.removeClass("collapsable").addClass("lastCollapsable");
				}else{
					liNode.removeClass("expandable").addClass("lastExpandable");
				}
			}else{
				liNode.addClass("last");
			}
		}else{
			liNode.removeClass("expandable").addClass("lastExpandable");
		}
		
	},
	
	_setNormalNode:function(node){
		var jNode = $(node);
		var liNode = $(jNode.get(0).parentNode);
		//alert(liNode.html());
		var hasChildren = jNode.attr("hasChildren");
		var expanded = jNode.attr("expanded");
		//alert("hasChildren"+hasChildren);
		//alert("expanded"+expanded);
		jNode.removeAttr("isLast");
		if(jNode.attr("hasLoad")=="true"){
			if(hasChildren=="true"){
				if(expanded=="true"){
					liNode.removeClass("lastCollapsable").addClass("collapsable");
				}else{
					liNode.removeClass("lastExpandable").addClass("expandable");
				}
			}else{
				liNode.removeClass("last").removeClass("collapsable");
			}
		}else{
			liNode.removeClass("lastExpandable").addClass("expandable");
		}
	},
	/**
	 * 
	 */
	_setFileNode:function(node){
		var jNode = $(node);
		var liNode = $(jNode.get(0).parentNode);
		$(node).attr("hasChildren","false");
		var fileIcon = jNode.attr("fileIcon");
		fileIcon = (!fileIcon)?JqTree.treeConfig.fileIcon:fileIcon;
		$("img.jTree_icon:first",jNode).attr("src",fileIcon);
		var isLast = jNode.attr("isLast");
		if(isLast=="true"){
			liNode.removeClass("lastExpandable").removeClass("lastCollapsable").addClass("last");
		}else{
			liNode.removeClass("expandable").removeClass("collapsable");
		}
		//
	},
	
	/**
	 * get the checked jqTreeNodes
	 */
	getCheckedJqTreeNodes:function(){
		var checkedJqTreeNodes = new Array();
		$("input.jTree_checkbox[@checked]",this.tree).each(function(){
			var textSpan = $(this).next("span.jTree_item_text").get(0);
			checkedJqTreeNodes[checkedJqTreeNodes.length] = textSpan.jqTreeNode;
		});
		return checkedJqTreeNodes;
	},
	
	/**
	 * get the new nodes
	 */
	 getNewJqTreeNodes:function(){
	 	var newJqTreeNodes = new Array();
	 	$("div.jTree_item[@isNew=true]",this.tree).each(function(){
			var textSpan = $("span.jTree_item_text:first",this).get(0);
			newJqTreeNodes[newJqTreeNodes.length] = textSpan.jqTreeNode;
		});
		return newJqTreeNodes;
	 },
	/**
	 * 
	 */
	getChangedJqTreeNodes:function(){
		var changedJqTreeNodes = new Array();
		$("div.jTree_item[@changed=true]",this.tree).each(function(){
			var textSpan = $("span.jTree_item_text:first",this).get(0);
			changedJqTreeNodes[changedJqTreeNodes.length] = textSpan.jqTreeNode;
		});
		
		return changedJqTreeNodes;
	},	
	/**
	 * 
	 */
	getRemovedDataObjects:function(){
		return this.removedDataObjects;
	},
	/**
	 * 
	 */
	getJqTreeNode:function(id){
		var jNode = $("div.jTree_item#item_"+id,this.treeRoot);
		var textSpan = $("span.jTree_item_text:first",jNode).get(0);
		var jqTreeNode = null;
		try{
			jqTreeNode = textSpan.jqTreeNode;
		}catch(err){
			
		}
	
		return jqTreeNode;
	},

	/**
	 * 
	 */
	getSelectedNode:function(errorMessage){
		if(!this.selectedNode){
			errorMessage = errorMessage||JqTree.Resource.selected_errorMessage;
			alert(errorMessage);
			return;
		}
		return this.selectedNode;
	},
	/**
	 * 
	 */
	getRoot:function(){
		
	},
	/**
	 * 
	 */
	getParent:function(jqTreeNode){
		var parentJqTreeNode;
		var treeDivNode;
		if(!jqTreeNode){
			jqTreeNode = this.getSelectedNode();
		}
		treeDivNode = $(jqTreeNode.node.get(0).parentNode.parentNode.parentNode);//li->ul->div
		
		var textSpan = $("span.jTree_item_text",treeDivNode).get(0);
		parentJqTreeNode = textSpan.jqTreeNode;
		return parentJqTreeNode;
	},
	
	getParents:function(){
		//var parentJqTreeNodes = new Array();
		//$("div",this.tree).each(function(){
		//});
	},
	/**
	 * 
	 */
	goOpenPath:function(openPath){
		var pathIdArray = new Array();
		pathIdArray = openPath.split("/");
		
		var continueFlag = true;
		for(var i=0;i<pathIdArray.length;i++){
			var id = pathIdArray[i];
			if(id==this.rootId)continue;
			var jqTreeNode = this.getJqTreeNode(id);
			
			if(jqTreeNode&&jqTreeNode.getAttribute("hasLoad")!="true"){
				continueFlag = false;
				jqTreeNode.node.attr("goPath",openPath);
				jqTreeNode.node.mousedown();
			}
			
			if(jqTreeNode&&continueFlag == true){
				if(i==pathIdArray.length-1){
					jqTreeNode.node.mousedown();
					//$("span.jTree_item_text",jqTreeNode.node).get(0).style.color=JqTree.treeConfig.selectedColor;
				}else{
					this.expandNode(jqTreeNode.node);
				}
			}
		}
		
		
		
	},
	/**
	 * 
	 */
	getNextJqTreeNode:function(jqTreeNode){
		var findFlag = false;
		var nextJqTreeNode;
		var sameLevelJqTreeNodes = new Array();
		
		parentJqTreeNode = this.getParent(jqTreeNode);
		sameLevelJqTreeNodes = parentJqTreeNode.getChildren();
		
		for(var i=0;i<sameLevelJqTreeNodes.length;i++){
			if(findFlag==true){
				nextJqTreeNode = sameLevelJqTreeNodes[i];
				findFlag = false;
			}
			
			if(sameLevelJqTreeNodes[i].getId()==jqTreeNode.getId()){
				findFlag = true;
			}
		}
		if(nextJqTreeNode==null){
			nextJqTreeNode = parentJqTreeNode;
		}
		return nextJqTreeNode;
	},
	
	/**
	 * return 
	 */
	getSelectedOpenPath:function(node){
		
	},
	/**
	 * return 
	 */
	getText:function(){
		
	},
	
	/**
	 * return 
	 */
	/**
	 * 
	 */
	collapseNode:function(node){
		var jNode = $(node);
		jNode.attr("expanded","false");
		$("ul.jTree_ul",jNode).get(0).style.display = "none";
		var folderIcon = jNode.attr("folderIcon");
		folderIcon = (!folderIcon)?JqTree.treeConfig.folderIcon:folderIcon;
		$("img.jTree_icon:first",node).attr("src",folderIcon);
		if(jNode.attr("isLast")=="true"){
			$(jNode.get(0).parentNode).removeClass("lastCollapsable").addClass("lastExpandable");
			//jNode.parent().removeClass("lastCollapsable").addClass("lastExpandable");
			
		}else{
			//jNode.parent().removeClass("collapsable").addClass("expandable");	
			$(jNode.get(0).parentNode).removeClass("collapsable").addClass("expandable");
		}
		
	},
	/**
	 * 
	 */
	expandNode:function(node){
		var jNode = $(node);
		jNode.attr("expanded","true");
		$("ul.jTree_ul",jNode).get(0).style.display = "block";
		var openFolderIcon = jNode.attr("openFolderIcon");
		openFolderIcon = (!openFolderIcon)?JqTree.treeConfig.openFolderIcon:openFolderIcon;
		$("img.jTree_icon:first",node).attr("src",openFolderIcon);
		
		if(jNode.attr("isLast")=="true"){
			$(jNode.get(0).parentNode).removeClass("lastExpandable").addClass("lastCollapsable");
			//jNode.parent().removeClass("lastExpandable").addClass("lastCollapsable");
		}else{
			$(jNode.get(0).parentNode).removeClass("expandable").addClass("collapsable");
			//jNode.parent().removeClass("expandable").addClass("collapsable");	
		}
		
	},
	
	_moveUp:function(jqTreeNode,preJqTreeNode,swapAttributes){
		var preNodeDiv = preJqTreeNode.node;
		var preNodeLi = $(preNodeDiv.get(0).parentNode);
		var nodeDiv = jqTreeNode.node;
		$(nodeDiv.get(0).parentNode).insertBefore(preNodeLi);
		if(nodeDiv.attr("isLast")=="true"){
			this._setNormalNode(nodeDiv);
			this._setLastNode(preNodeDiv);
		}
		
		if(typeof(swapAttributes)=="object"&&swapAttributes.length>0){
			var attributeName;
			var tempValue;
			this._setChanged(nodeDiv);
			this._setChanged(preNodeDiv);
			for(var i=0;i<swapAttributes.length;i++){
				attributeName = swapAttributes[i];
				tempValue = nodeDiv.attr(attributeName);
				if(typeof(tempValue)!='undefine'){
					nodeDiv.attr(attributeName,preNodeDiv.attr(attributeName));
					preNodeDiv.attr(attributeName,tempValue);
					
				}
				tempValue = null;
				attributeName = null;
			}
		}
	},
	
	moveUp:function(swapAttributes,jqTreeNode,moveAction){
		if(!jqTreeNode){
			jqTreeNode = this.getSelectedNode();
		}
		if(!jqTreeNode)return;
		var nodeDiv = jqTreeNode.node;
		this._setNodeView(nodeDiv);
		var nodeLi = $(nodeDiv.get(0).parentNode);
		var preNodeLi = nodeLi.prev();
		if(preNodeLi.size()==0)return;
		var preNodeDiv =  $("div.jTree_item:first",preNodeLi);
		
		//if(nodeDiv.getId()==preNodeDiv.getId())return;
		if(moveAction){
			moveAction.apply(jqTreeNode,new Array($("span.jTree_item_text:first",preNodeDiv).get(0).jqTreeNode,swapAttributes,"up"));
		}else{
			this._moveUp(jqTreeNode,$("span.jTree_item_text:first",preNodeDiv).get(0).jqTreeNode,swapAttributes);
		}
	},
	
	_moveDown:function(jqTreeNode,nextJqTreeNode,swapAttributes){
		var nodeDiv = jqTreeNode.node;
		var nextNodeDiv = nextJqTreeNode.node;
		var nextNodeLi = $(nextNodeDiv.get(0).parentNode);
		
		if(nodeDiv.attr("isLast")=="true"){
			return;
		}else{
			//:: sp  $(nodeDiv.get(0).parentNode) not nodeLi
			$(nodeDiv.get(0).parentNode).insertAfter(nextNodeLi);//when use next(),the nodeLi changed to nextLi,
			if(nextNodeDiv.attr("isLast")=="true"){
				//nodeDiv.attr();
				this._setNormalNode(nextNodeDiv);
				this._setLastNode(nodeDiv);
			}
			
			if(typeof(swapAttributes)=="object"&&swapAttributes.length>0){
				var attributeName;
				var tempValue;
				this._setChanged(nodeDiv);
				this._setChanged(nextNodeDiv);
				//nodeDiv.attr("changed");
				for(var i=0;i<swapAttributes.length;i++){
					attributeName = swapAttributes[i];
					tempValue = nodeDiv.attr(attributeName);
					if(typeof(tempValue)!='undefine'){
						nodeDiv.attr(attributeName,nextNodeDiv.attr(attributeName));
						nextNodeDiv.attr(attributeName,tempValue);
					}
					tempValue = null;
					attributeName = null;
				}
			}
		}
	},
	
	moveDown:function(swapAttributes,jqTreeNode,moveAction){
		if(!jqTreeNode){
			jqTreeNode = this.getSelectedNode();
		}
		if(!jqTreeNode)return;
		var nodeDiv = jqTreeNode.node;
		this._setNodeView(nodeDiv);
		var nodeLi = $(nodeDiv.get(0).parentNode);
		
		var nextNodeLi = nodeLi.next();
		var nextNodeDiv = $("div.jTree_item:first",nextNodeLi);
		if(nextNodeDiv.size()==0)return;//
		
		if(moveAction){
			moveAction.apply(jqTreeNode,new Array($("span.jTree_item_text:first",nextNodeDiv).get(0).jqTreeNode,swapAttributes,"down"));
		}else{
			this._moveDown(jqTreeNode,$("span.jTree_item_text:first",nextNodeDiv).get(0).jqTreeNode,swapAttributes);
		}
	},
	/**
	 * 
	 */
	_getParentDiv:function(nodeDiv){
		var parentNodeDiv = $(nodeDiv.get(0).parentNode.parentNode.parentNode);//li->ul->div
		return parentNodeDiv;
	},
	
	initHelperPanel:function(position,isDraggable){
		var p = this;
		this.helperPanel = $("<div class=\"jTree_helper_panel\"></div>");
		var helperPanelHead = $("<div class=\"jTree_helper_panel_head\"></div>");
		var helperPanelBody = $("<div class=\"jTree_helper_panel_body\"></div>");
		this.helperPanel.append(helperPanelHead);
		this.helperPanel.append(helperPanelBody);
		if(position&&position=="before"){
			this.helperPanel.insertBefore(this.tree);
		}else{
			this.helperPanel.insertAfter(this.tree);
		}
		
		//this.tree.parent().addClass("jTree_container_scroll");
		//this.tree.parent().scroll(function(){
			//p.helperPanel.css("top",this.srollTop+);
			//p.helperPanel.get(0).offsetTop
		//});
		
		var treeContainer = this.tree.get(0).parentNode;
		var offsetLeft = treeContainer.offsetLeft;
		var offsetTop = treeContainer.offsetTop;
		var offsetHeight = treeContainer.offsetHeight;
		var offsetWidth = treeContainer.offsetWidth;
		
		if(isDraggable==true){
			this.helperPanel.Draggable({
				containment: new Array(offsetLeft, offsetTop,offsetWidth, offsetHeight)
			});
		}
	},
	/**
	 * @param caption
	 * @param searchUrl
	 * @param paramAttribute
	 * @param position
	 */
	showSearchPanel:function(caption,searchUrl,paramAttribute,position,isDraggable){
		var p = this;
		
		var searchBody = this._addHelperPanelTab(" \u67e5   \u8be2 ",position,isDraggable);
		
		//var searchCaption = $("<span>"+caption+"</span>");
		var selectResultDiv = $("<div></div>");
		var selectResultWidget = $("<span></span>");
		
		selectResultWidget.Select({
			width	:150,
			
			src		:searchUrl,
			
			searchParamPrefix: JqTree.treeConfig.paramPrefix,
			
			dataNodeName:JqTree.treeConfig.dataNodeName,
			
			textAttribute:paramAttribute['textAttribute'],
			
			valueAttribute:paramAttribute['valueAttribute'],
			
			onChange:function(){
				p.goOpenPath(this.selectedValue);
			}
		});
		
		searchBody.append("<span style=\"float:left;padding-top:5px;\">"+caption+"</span>");
		searchBody.append(selectResultWidget);
	},
	
	showMoveNodePanel:function(wrapAttributes,moveAction,position,isDraggable){
		var p = this;
		var moveNodeBody = this._addHelperPanelTab("\u8c03\u6574\u8282\u70b9",position,isDraggable);
		var moveUpBut = $("<input class=\"buttonface\" type=\"button\" value=\"\u4e0a \u79fb\"/>");
		var moveDownBut = $("<input class=\"buttonface\" type=\"button\" value=\"\u4e0b \u79fb\"/>");
		moveUpBut.css("marginTop","1pt");
		moveUpBut.css("marginLeft","10pt");
		moveDownBut.css("marginTop","1pt");
		
		
		moveUpBut.click(function(){
			p.moveUp(wrapAttributes,null,moveAction);
		});
		
		moveDownBut.click(function(){
			p.moveDown(wrapAttributes,null,moveAction);
		});
		moveNodeBody.append(moveUpBut);
		moveNodeBody.append("&nbsp;&nbsp;");
		moveNodeBody.append(moveDownBut);
	},
	
	_addHelperPanelTab:function(title,position,isDraggable){
		var p = this;
		var selectedTab = false;
		if(this.helperPanel==null){
			this.initHelperPanel(position,isDraggable);
			selectedTab = true;
		}
		var panelHead = $("div.jTree_helper_panel_head",this.helperPanel);
		var panelBody = $("div.jTree_helper_panel_body",this.helperPanel);
		
		var tabTitle = $("<span class=\"jTree_helper_panel_tabTitle\"><div style=\"margin-top:3px;\">"+title+"</div></span>");
		var tabBody = $("<div class=\"jTree_helper_panel_tabBody\"></div>");
		
		tabBody.mousedown(function(){
			$("body",document).mousedown();
			return false;
		});
		panelHead.append(tabTitle);
		panelBody.append(tabBody);
		
		tabTitle.click(function(){
			$("span.jTree_helper_panel_tabTitle",this.parentNode).removeClass("jTree_helper_panel_tabTitle_selected");
			$("div.jTree_helper_panel_tabBody",p.helperPanel).css("display","none");
			tabBody.css("display","block");
			$(this).addClass("jTree_helper_panel_tabTitle_selected");
		});
		
		if(selectedTab == true)tabTitle.click();
		
		return tabBody;
	},
	
	_setNodeView:function(nodeDiv){
		var treeContainer = this.tree.get(0).parentNode;
		var offsetBottom = treeContainer.offsetHeight+treeContainer.scrollTop-nodeDiv.get(0).offsetTop-this.tree.get(0).offsetTop;
		//roll down
		
		if(offsetBottom<50){
			treeContainer.scrollTop = nodeDiv.get(0).offsetTop+71+this.tree.get(0).offsetTop-treeContainer.offsetHeight;
		}
		//roll up
		if(nodeDiv.get(0).offsetTop-treeContainer.scrollTop<30){
			treeContainer.scrollTop = nodeDiv.get(0).offsetTop+51+this.tree.get(0).offsetTop-treeContainer.offsetHeight;
		}
	},
	
	/**
	 * 
	 */
	submit:function(isSubmitChanged,isSubmitAdd,isSubmitDelete){
		isSubmitChanged = (isSubmitChanged==null)?false:isSubmitChanged;
		isSubmitAdd = (isSubmitAdd==null)?false:isSubmitAdd;
		isSubmitDelete = (isSubmitDelete==null)?false:isSubmitDelete;
		
		if(isSubmitChanged==true){
			$("div.jTree_item[@changed=true]",this.tree).each(function(){
				$(this).attr("changed","false");
				$(this).removeAttr("isModify");
				$("span.jTree_item_text:first",this).get(0).style.color="";
			});
			//this.selectedNode = null;
		}
		
		if(isSubmitAdd==true){
			$("div.jTree_item[@isNew=true]",this.tree).each(function(){
				$(this).attr("isNew","false");
				$(this).attr("changed","false");
				$("span.jTree_item_text:first",this).get(0).style.color="";
			});
			//this.selectedNode = null;
		}
		
		if(isSubmitDelete==true){
			this.removedDataObjects = new Array();
		}
	},
	
	/**
	 * 
	 */
	stopBubble:function(event){
		event = (window.event)?window.event:event;
		event.cancelBubble=true;
	}
}

/**
 * 
 */
JqTree.ActionHandler = new Object();

JqTree.ActionHandler.create = function(){
	this.init();
};
JqTree.ActionHandler.create.prototype = {
	click:null,
	
	dblclick:null,
	
	focus:null,
	
	keydown:null,
	
	keypress:null,
	
	keyup:null,
	
	mousedown:null,
	
	mousemove:null,
	
	mouseout:null,
	
	mouseover:null,
	
	mouseup:null,
	
	init:function(){
		this.click = {type:"click",handler:null};
		
		this.dblclick = {type:"dblclick",handler:null};
		
		this.focus = {type:"focus",handler:null};
		
		this.keydown = {type:"keydown",handler:null};
		
		this.keypress = {type:"keypress",handler:null};
		
		this.keyup = {type:"keyup",handler:null};
		
		this.mousedown = {type:"mousedown",handler:null};
		
		this.mousemove = {type:"mousemove",handler:null};
		
		this.mouseout = {type:"mouseout",handler:null};
		
		this.mouseover = {type:"mouseover",handler:null};
		
		this.mouseup = {type:"mouseup",handler:null};
	}
};
/**
 * 
 */
JqTree.JqTreeNode = new Object();
/**
 * @param eNode treeDivNoder
 * @param oActionHandler  JqTree.ActionHandler
 */
JqTree.JqTreeNode.create = function(eNode,oActionHandler,oTreeObject,oDataObject){
	this.node =$(eNode);
	this.actionHandler = oActionHandler;
	this.dataObject = oDataObject;
	this.treeObject = oTreeObject;
	try{
		this.init();
	}catch(err){
		
	}
}
/**
 * 
 */
JqTree.JqTreeNode.create.prototype = {
	/**
	 * 
	 */
	init:function(){
		$("span.jTree_item_text:first",this.node).get(0).jqTreeNode = this;
		
		if(this.actionHandler){
			this.addActionHandler(this.actionHandler);
		}
	},
	/**
	 * 
	 */
	addActionHandler:function(actionHandler){
		for (var action in actionHandler){
			var type = actionHandler[action].type;
			var handler = actionHandler[action].handler;

			if(handler&&typeof(handler)=="function"){
				this.addAction(type,handler);
			}
		}
	},
	
	//$("p").bind("click", {foo: "bar"}, handler)
	addAction:function(type,handler){
		//this.node.bind(type,handler);
		//bind action 
		$("span.jTree_item_text",this.node).bind(type,handler);
		$("span.jTree_item_text",this.node).get(0).jqTreeNode = this;
	},
	
	getDataObject:function(){
		var dataObject = new Array();
		if(this.dataObject){
			dataObject = this.dataObject;//
		}
		var treeObject = this.treeObject;
		
		var idName,textName,pidName;
		var id,text,pid;
		var attributeValue;
		
		idName = treeObject.getId();
		textName = treeObject.getText();
		pidName = treeObject.getPid();
		
		id = this.getId();
		text = this.getText();
		pid = this.getPid();
		//alert("...."+id+":"+text+":"+pid);
		//alert(idName+":"+textName+":"+pidName);
		pid = (!pid)?"":pid;
		
		for(var attributeName in dataObject){
			 attributeValue = this.getAttribute(attributeName);
			 dataObject[attributeName] = attributeValue;
		}
		
		dataObject[idName]=id;
		dataObject[textName]=text;
		dataObject[pidName]=pid;	
		
		return dataObject;
	},
	
	removeAction:function(type){
		this.node.unbind(type);
	},
	
	remove:function(){
		$(this.node.get(0).parentNode).remove();
	},
	
	getId:function(){
		var treeId = this.getAttribute("id");
		var id = treeId.substring(5);//item_
		return id;
	},
	
	getPid:function(){
		var pid = this.getAttribute("pid");
		pid = pid.substring(5);//item_
		return pid;
	},
	
	getText:function(){
		return this.getAttribute("text");
	},
	
	getLevel:function(){
		return this.getAttribute("level");
	},
	
	getElement:function(){
		return this.node.get(0);
	},
	
	getAttribute:function(attributeName){
		return this.node.attr(attributeName);
	},
	
	getOpenPath:function(){
		return this.getAttribute("openPath");
	},
	/**
	 * children    JqTree.JqTreeNode
	 */
	getChildren:function(){
		var jqTreeNodes = new Array();
		if(this.getAttribute("hasLoad")=="true"){
			$("div.jTree_item[@pid="+this.getAttribute("id")+"]",this.node).each(function(){
				jqTreeNodes[jqTreeNodes.length] = $("span.jTree_item_text",this).get(0).jqTreeNode;
			});
		}
		return jqTreeNodes;
	},
	
	setText:function(text){
		if(text){
			this.node.attr("text",text);
			this.node.attr("title",text);
			var textSpan = $("span.jTree_item_text:first",this.node);
			textSpan.empty();
			textSpan.append(text);
		}
	},
	
	setAttribute:function(attributeName,attributeValue){
		if(attributeName){
			for(var iAttribute in this.dataObject){
				if(attributeName==iAttribute){
					this.node.attr(attributeName,attributeValue);
				}
			}
		}
	},
	
	setIcon:function(fileIcon,folderIcon,openFolderIcon,showIcon){
		if(fileIcon)this.node.attr("fileIcon",fileIcon);
		if(folderIcon)this.node.attr("folderIcon",folderIcon);
		if(openFolderIcon)this.node.attr("openFolderIcon",openFolderIcon);
		$("img:first",this.node).attr("src",showIcon);
	},
	
	setModifyStatus:function(){
		this.node.attr("isModify","true");
	},
	
	removeModifyStatus:function(){
		this.node.removeAttr("isModify");
	},
	
	toString:function(){
		return this.getId()+"|"+this.getLevel();
	}
}
/**
 * TreeObject
 */
JqTree.TreeObject = new Object();
/**
 * @param id
 * @param text
 * @param pid
 */
JqTree.TreeObject.create = function(sId,sText,sPid){
	if(!sId||!sText||!sPid){
		alert("not enough treeObject params!");
		return;
	}
	this.id = sId;
	this.text = sText;
	this.pid = sPid;
}

JqTree.TreeObject.create.prototype = {
	getId:function(){
		return this.id;
	},
	
	getText:function(){
		return this.text;
	},
	
	getPid:function(){
		return this.pid;
	}
}
/*****************************************************************************************/
JqTree.TreeNodeLink = new Object();

JqTree.TreeNodeLink.create = function(sLinkSrc,sLinkText,aLinkParams,sLinkTarget,sLinkTitle){
	this.linkSrc = sLinkSrc;
	this.linkText = sLinkText;
	this.linkTarget = sLinkTarget;
	this.linkTitle = sLinkTitle;
	this.linkParams = aLinkParams;
	
}

JqTree.TreeNodeLink.create.prototype = {
	getLinkSrc:function(){
		return this.linkSrc;
	},
	
	getLinkText:function(){
		return this.linkText;
	},
	
	getLinkTarget:function(){
		return this.linkTarget;
	},
	
	getLinkTitle:function(){
		return this.linkTitle;
	},
	
	getLinkParams:function(){
		return this.linkParams;
	}
}