/**
 * @package youi.tree
 * @功能描述 树组件
 * @author zhyi_12
 * @time 2007-10-26
 * @since V1.0.2
 * @requires 	jquery.js
 * 				youi.object.js
 * 				youi.data.js
 * 				youi.actionFactory.js  
 * @修改记录
 * 	2007-11-22：增加初始化参数 isShowRoot控制根节点的显示，isShowRoot=false时不显示。
 *  2007-12-01：增加openPath
 */
(function($) {
	/**
	 * 构造函数
	 */
	$.youi.tree = function(el,o){
		$.youi.createObject(this,el,o);
	};
	
	$.youi.treeNode = function(el,o){
		$.youi.createObject(this,el,o);
	};
	/**
	 * 原型对象
	 */
	$.extend($.youi.tree.prototype,$.youi.object.prototype,{
		treeNodes:{},
		
		root:null,
		
		selectedNode:null,
		
		attrs:function(){
			var el = this.element;
			return {
				action			:el.getAttribute('youiAction'),
				actionHandle	:el.getAttribute('actionHandle'),
				fileIcon		:el.getAttribute('fileIcon'),
				folderIcon		:el.getAttribute('folderIcon'),
				openFolderIcon	:el.getAttribute('openFolderIcon'),
				checked			:el.getAttribute('youiChecked'),
				draggable		:el.getAttribute('draggable'),
				droppable		:el.getAttribute('droppable'),
				attributes		:(typeof(el.getAttribute('attributes'))=='object')?null:el.getAttribute('attributes')
			};
		},
		
		createElement:function(){
			var self = this;
			$(this.element).addClass("youi-tree");
			this.element.onselectstart = function(){return false;};
			var rootLi = $("li:first",this.element);
			this.root = new $.youi.treeNode(rootLi.get(0),{tree:self,expanded:"true",hasInit:"true"});
			this.defaultOptions = {
				tree			:self,
				action			:self.options.action,
				actionHandle	:self.options.actionHandle,
				fileIcon		:self.options.fileIcon,
				folderIcon		:self.options.folderIcon,
				openFolderIcon	:self.options.openFolderIcon,
				checked			:self.options.checked,
				draggable		:self.options.draggable,
				droppable		:self.options.droppable,
				attributes		:self.options.attributes
			};
			rootLi.find(">ul>li").each(function(){
				var treeNodeOptions = $.extend({},self.defaultOptions);
				new $.youi.treeNode(this,treeNodeOptions);
			});
			
			if(this.options.isShowRoot==false){
				$('>ul>li',this.element).removeClass();
				$('>ul>li>div',this.element).hide();
				$('>ul>li>a',this.element).hide();
			}
		},
		
		goOpenPath:function(openPath,afterOpen){
			if(!openPath)return;
			var self = this;
			var firstTreeNode;
			var traceIdArray = openPath.split('/');
			traceIdArray = traceIdArray.reverse();
			
			if(traceIdArray.length>1){
				var firstId = traceIdArray.pop();
				firstTreeNode = this.treeNodes[firstId];
				if(firstTreeNode){
					firstTreeNode.expand(function(){
						self.goOpenPath(traceIdArray.reverse().join('/'),afterOpen);
					});
				}
			}else if(traceIdArray.length==1){
				firstTreeNode = this.treeNodes[traceIdArray[0]];
				if(firstTreeNode){
					firstTreeNode.$element.click();
					if($.isFunction(afterOpen)){
						afterOpen.apply(firstTreeNode);
					}
				}
			}
		}
	});
	
	$.extend($.youi.treeNode.prototype,$.youi.object.prototype,{
		attrs:function(){
			var el = this.element;
			return {
				id				:el.getAttribute('id'),
				text			:el.getAttribute('youiText'),
				src				:el.getAttribute('src'),
				attributes		:(typeof(el.getAttribute('attributes'))=='object')?null:el.getAttribute('attributes'),
				srcType			:el.getAttribute('srcType'),
				action			:el.getAttribute('youiAction'),
				expanded		:el.getAttribute('expanded'),
				fileIcon		:el.getAttribute('fileIcon'),
				folderIcon		:el.getAttribute('folderIcon'),
				openFolderIcon	:el.getAttribute('openFolderIcon'),
				checked			:el.getAttribute('youiChecked'),
				draggable		:el.getAttribute('draggable'),
				droppable		:el.getAttribute('droppable'),
				actionHandle	:el.getAttribute('actionHandle')
			};
			
		},
		
		createElement:function(){
			var self = this;
			var $element = $(this.element);
			this.$element = $element;
			if($element.find('ul').length===0){
				$element.append('<ul/>');
			}
			var text = this.options.text;
			if(!text){
				this.options.text =$element.find(">a>span").text();
			}else{
				$element.find(">a>span").append(text);
			}
			$element.attr("title",$element.find(">a>span").text());
			
			this.options.tree.treeNodes[this.options.id]=self;//把当前节点加入数组
			this.initIcon();
			this.initClass();
			this.initAction();
			this.initDragAndDrop();
			
			var expandHandle = $('<div class="youi-tree-item-expand"/>');
			$element.prepend(expandHandle);
			$element.addClass("youi-tree-item");
			expandHandle.bind("click",trigger);
			expandHandle.bind("dblclick",function(){return false;});
			$element.bind("dblclick",trigger);
			
			/**
			 * trigger callback
			 */
			function trigger(){
				var expanded = self.options.expanded;
				if(!self.hasChildren()&&!self.options.src){return false;}
				
				if(expanded=="true"){
					self.collapse();
				}else{
					self.expand();
				}
				return false;
			}
		},
		
		initAction:function(){
			var self = this;
			self.options.actionHandle = (!self.options.actionHandle)?'click':self.options.actionHandle;
			var $element = this.$element;
			
			$element.bind("click",clickHandle);
			$element.bind("dblclick",actionHandle);
			var action = this.options.action;
			function clickHandle(event){
				var checked = self.options.checked;
				self.selectedNode();
				var icon = $element.find(">a>img");
				if(checked=="true"){
					self.options.checked = "false";
					icon.removeClass("youi-tree-checked-icon").addClass("youi-tree-unchecked-icon");
				}else if(checked=="false"){
					self.options.checked = "true";
					icon.removeClass("youi-tree-unchecked-icon").addClass("youi-tree-checked-icon");
				}else{
					
				}
				actionHandle(event);
				return false;
			}
			
			function actionHandle(event){
				if(event.type==self.options.actionHandle){
					//alert(self.options.actionHandle);
					if(typeof(action)=="string"){
						$.actionFactory[action].apply(self,[self.options.checked]);
					}else if(typeof(action)=="function"){
						action.apply(self,[self.options.checked]);
					}
				}
				return false;
			}
		},
		
		initIcon:function(){
			var $element = this.$element;
			var icon = $('<img align="middle" src="'+$.youi.serverPath+'images/youi/s.gif" class="youi-tree-icon"/>');
			$element.find('>a:first').prepend(icon);
			this.setIcon();
		},
		
		initClass:function(){
			var $element = this.$element;
			var expanded = this.options.expanded;
			if(this.hasChildren()){
				if(expanded=="true"){
					if(this.isLast()){
						$element.addClass("lastCollapsable");
					}else{
						$element.addClass("collapsable");
					}
					this.expand();
				}else{
					$element.find(">ul").hide();
					if(this.isLast()){
						$element.addClass("lastExpandable");
					}else{
						$element.addClass("expandable");
					}
				}
			}else{
				if(this.options.src&&expanded!="true"){
					if(this.isLast()){
						$element.addClass("lastExpandable");
					}else{
						$element.addClass("expandable");
					}
				}else{
					if(this.isLast()){
						$element.addClass("last");
					}
				}
			}
		},
		
		initDragAndDrop:function(){
			if(this.options.draggable=="true"){
				var dragOptions = $.extend({},this.options,{ghosting:true,appendTo:$("body",document)});
				//$(this.element).draggable(dragOptions);
				this.$element.find(">a>span").draggable(dragOptions);
			}
		},
		/**
		 * 从外部数据加载树
		 */
		loadSrc:function(afterLoadSrc){
			var begTime = (new Date()).getTime();
			var self = this;
			var src = this.options.src;
			var srcType = this.options.srcType||'xml';
			var attributes = (this.options.attributes)?this.options.attributes.split(','):[];
			//alert(attributes);
			var treeMapObject =  this.options.treeMapObject||this.options.tree.options.treeMapObject;
			if(!treeMapObject)return;
			
			attributes = $.merge(attributes,[treeMapObject.mapId,treeMapObject.mapText,treeMapObject.mapPid,'src','expanded','fileIcon','youiChecked']);

			var lastLiNode = this.$element.find('>ul>li:last');//记录未加入前的最后一个节点
			
			var loadingLi = $('<li class="youi-tree-item last"><img src="'+$.youi.serverPath+'images/youi/s.gif" class="youi-icon-small-loading"/>loading...</li>');
			this.$element.append(loadingLi);
			
			var datas = $.datas.parse({src:src,srcType:srcType,attributes:attributes,afterParse:afterAdd},addNode);
			/**
			 * 解析数据集过程回调函数
			 */
			function addNode(attributes,returnObjects){
				var id   = this[treeMapObject.mapId];
				var text = this[treeMapObject.mapText];
				var pid  = this[treeMapObject.mapPid];
				var childSrc = this['src'];
				if(!childSrc){
					childSrc = src;
					if(childSrc.indexOf("?")==-1){
						childSrc+='?';
					}else{
						var paramArray = childSrc.substring(childSrc.indexOf('?')+1).split('$');
						var lastParam = paramArray.pop();
						if(lastParam.indexOf(treeMapObject.mapPid+'=')!=-1){
							childSrc = childSrc.substring(0,childSrc.indexOf('?')+1);
							if(paramArray.length>0){
								childSrc+=paramArray.join('&');
								childSrc+='&';
							}else{

							}
						}else{
							childSrc+='&';
						}
					}
					childSrc+=treeMapObject.mapPid;
					childSrc+='=';
					childSrc+=self.options.id;
				}
				if(this['expanded']!='true')this['src'] = childSrc;
				this[treeMapObject.mapId] = null;
				this[treeMapObject.mapText] = null;
				returnObjects.push(self.addLiNode(id,text,this));
			}
			/**
			 * 解析完成后的回调函数
			 */
			function afterAdd(returnObjects){
				loadingLi.remove();
				if(lastLiNode.length>0){
					lastLiNode.removeClass("last");
				}
				
				$(returnObjects).each(function(){
					this.each(function(){
						var treeNode = new $.youi.treeNode(this,{tree:self.options.tree,attributes:self.options.attributes});
					});
				});
				
				if(afterLoadSrc)afterLoadSrc.apply(self);
				if(returnObjects.length==0){
					setLeafLi($(self.element),self.options);
				}
				//var endTime = (new Date()).getTime();
				//$.youi.log.info('加载'+returnObjects.length+'个节点，总耗时：'+(endTime - begTime)+'毫秒！');
			}
		},
		
		getTree:function(){
			return this.options.tree;
		},
		/**
		 * @private
		 * 加入节点元素，供加入节点和载入节点调用
		 */
		addLiNode:function(id,text,dataObject,type){
			var treeNodeLi = $('<li attributes="'+this.options.attributes+'" id="'+id+'"><a><span>'+text+'</span></a><ul/></li>');
			//alert('<li id="'+id+'"><a><span>'+text+'</span></a><ul/></li>');
			for(var iData in dataObject){
				if(dataObject[iData]){
					treeNodeLi.attr(iData,dataObject[iData]);
				}
			}
			var tree = this.options.tree;
			type = (!type)?'child':type;
			
			switch(type){
				case 'child':
					$(this.element).find('>ul').append(treeNodeLi);
					break;
				case 'firstChild':
					$(this.element).find('>ul').prepend(treeNodeLi);
					break;
				case 'before':
					break;
				case 'after':
					break;
			}
			
			return treeNodeLi;
		},
		
		addNode:function(id,text,dataObject,type){
			var self = this;
			var $element = this.$element;
			var icon = $element.find(">a>img");
			type = (!type)?'child':type;
			var lastLiNode = null;
			switch(type){
				case 'child':
					if(this.hasChildren()){
						lastLiNode = $element.find('>ul>li:last');
						setNormalLi(lastLiNode);
					}else{
						icon.addClass("youi-tree-folder-open-icon");
						if(this.isLast()){
							$element.removeClass("last").addClass("lastCollapsable");
						}else{
							$element.addClass("collapsable");
						}
					}
					break;
				case 'firstChild':
				
					break;
				case 'before':
					break;
				case 'after':
					break;
			}
			var treeNodeLi = this.addLiNode(id,text,dataObject,type);
			this.expand();
			treeNodeLi.each(function(){
				new $.youi.treeNode(this,{tree:self.options.tree});
			});
		},
		
		removeNode:function(){
			var $element = this.$element;
			if(this.equals(this.options.tree.root)){return;}//不能删除根节点
			var parentNode = this.getParent();
			var prevLi = $element.prev();
			var nextLi = $element.next();
			$element.remove();
			
			if(parentNode.hasChildren()){
				if(nextLi.length>0){
					nextLi.click();
				}else if(prevLi.length>0){
					prevLi.click();
					setLastLi(prevLi);
				}
			}else{
				$(parentNode.element).click();
				if(parentNode.isLast()){
					$(parentNode.element).removeClass("lastCollapsable").addClass("last");
				}else{
					$(parentNode.element).removeClass("collapsable");
				}
				var parentIcon = $(parentNode.element).find(">a>img");
				parentIcon.removeClass("youi-tree-folder-open-icon");
			}
		},
		
		setIcon:function(){
			var icon = this.$element.find(">a>img");
			var checked = this.options.checked;
			var expanded = this.options.expanded;
			if(checked=="true"){
				icon.addClass("youi-tree-checked-icon");
			}else if(checked=="false"){
				icon.addClass("youi-tree-unchecked-icon");
			}else{
				var fileIcon = this.options.fileIcon;
				var folderIcon = this.options.folderIcon;
				var openFolderIcon = this.options.openFolderIcon;
				var iconSrc = '';
				if(fileIcon){
					icon.css("backgroundImage","url("+fileIcon+")");
				}
				if(this.hasChildren()){
					if(expanded=="true"){
						if(openFolderIcon){
							icon.css("backgroundImage","url("+openFolderIcon+")");
						}else{
							icon.addClass("youi-tree-folder-open-icon");
						}
					}else{
						if(folderIcon){
							icon.css("backgroundImage","url("+folderIcon+")");
						}else{
							icon.addClass("youi-tree-folder-icon");
						}
					}
				}else{
					if(this.options.src&&this.options.hasLoad!="true"){
						if(folderIcon){
							icon.css("backgroundImage","url("+folderIcon+")");
						}else{
							icon.addClass("youi-tree-folder-icon");
						}
					}
				}
			}
		},
		
		expand:function(afterLoadSrc){
			var self = this;
			var $element = this.$element;
			$element.find(">ul").show();
			this.options.expanded = "true";
			if(this.isLast()){
				$element.removeClass("lastExpandable").addClass("lastCollapsable");
			}else{
				$element.removeClass("expandable").addClass("collapsable");
			}
			
			var icon = $element.find(">a>img");
			icon.removeClass("youi-tree-folder-icon").addClass("youi-tree-folder-open-icon");
			
			if(this.options.hasInit!="true"){
				$element.find(">ul>li").each(function(){
					var treeNodeOptions = $.extend({},self.options.tree.defaultOptions);
					new $.youi.treeNode(this,treeNodeOptions);
				});
				this.options.hasInit="true";
			}
			
			if(this.options.hasLoad!="true"&&this.options.src){
				this.options.hasLoad="true";
				$element.attr("hasLoad","true");
				this.loadSrc(afterLoadSrc);
			}else{
				if(afterLoadSrc){
					afterLoadSrc.apply(self);
				}
			}
		},
		
		collapse:function(){
			if(!this.hasChildren())return;
			var $element = this.$element;
			this.options.src=null;
			$element.removeAttr('src');
					
			$element.find(">ul").hide();
			this.options.expanded = "false";
			if(this.isLast()){
					$element.removeClass("lastCollapsable").addClass("lastExpandable");
			}else{
				$element.removeClass("collapsable").addClass("expandable");
			}
			var icon = $element.find(">a>img");
			icon.removeClass("youi-tree-folder-open-icon").addClass("youi-tree-folder-icon");
			
		},
		
		isLast:function(){
			var $element = this.$element;
			var nextLiNode = $element.next("li");
			if(nextLiNode.length==0){
				return true;
			}
			return false;
		},
		
		selectedNode:function(){
			var selectedNode = this.options.tree.selectedNode;
			if(this.equals(selectedNode)){
				return;
			}
			if(selectedNode){
				$(selectedNode.element).find(">a>span").css("color","black");
			}
			this.options.tree.selectedNode = this;
			$(this.element).find(">a>span").css("color","red");
		},
		
		hasChildren:function(){
			var childCounts = $(">ul>li",this.element).length;
			if(childCounts>0){
				return true;
			}
			return false;
		},
		
		getText:function(){
			return this.options.text;
		},
		
		getId:function(){
			return this.options.id;
		},
		
		getParent:function(){
			var parentNode;
			var parentLi = this.$element.parent().parent();
			if(parentLi.length>0){
				var parentId = parentLi.attr("id");
				parentNode =  this.options.tree.treeNodes[parentId];
			}
			if(parentNode.element===parentLi.get(0)){
				return parentNode;
			}
			return null;
		}
	});
	/**
	 * jquery 函数扩展
	 */
	$.fn.extend({
		youiTree:function(o){
			var self = this;
			return this.each(function() {
				var youiTree = new $.youi.tree(this, o);
				if(youiTree){
					$.extend(self,{
						getNode:function(id){
							return youiTree.treeNodes[id];
						},
						
						getSelectedNode:function(){
							return youiTree.selectedNode;
						},
						
						openPath:function(xPath,afterOpen){
							youiTree.goOpenPath(xPath,afterOpen);
						},
						
						getCheckedJqTreeNodes:function(){
							var checkedJqTreeNodes = [];
							$('img.youi-tree-checked-icon',this).each(function(){
								var treeNodeId = this.parentNode.parentNode.getAttribute('id');
								checkedJqTreeNodes.push(self.getNode(treeNodeId));
							});
							return checkedJqTreeNodes;
						}
					});
				}
			});
		}
	});
	/*************************************************************************************
	 * 私有函数
	 *************************************************************************************/
	/**
	 * 设置为普通节点
	 */
	function setNormalLi(liNode){
		liNode.each(function(){
			var className = this.className;
			if(className.indexOf('lastExpandable')!=-1){
				liNode.removeClass("lastExpandable").addClass("expandable");
			}else if(className.indexOf('lastCollapsable')!=-1){
				liNode.removeClass("lastCollapsable").addClass("collapsable");
			}else{
				liNode.removeClass("last");
			}
		});
	}
	
	/**
	 * 设置为最末的节点
	 */
	function setLastLi(liNode){
		liNode.each(function(){
			var className = this.className;
			if(className.indexOf('expandable')!=-1){
				liNode.removeClass("expandable").addClass("lastExpandable");
			}else if(className.indexOf('collapsable')!=-1){
				liNode.removeClass("collapsable").addClass("lastCollapsable");
			}else{
				liNode.addClass("last");
			}
		});
	}
	
	function setLeafLi(liNode,o){
		liNode.each(function(){
			o.src = null;
			liNode.removeAttr('src');
			if(!o.checked){
				$('img:first',this).removeClass('youi-tree-folder-open-icon ').addClass('youi-tree-file-icon');
			}
			var className = this.className;
			if(className.indexOf('expandable')!=-1){
				liNode.removeClass('expandable');
			}else if(className.indexOf('collapsable')!=-1){
				liNode.removeClass('collapsable');
			}else{
				liNode.removeClass('lastExpandable').removeClass('lastCollapsable').addClass('last');
			}
		});
	}
})(jQuery);