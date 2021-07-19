var pageInsertDataaccgroup = new Object();

pageInsertDataaccgroup.create = function(contextPath,dataaccgrpid){
	this.contextPath = contextPath;
	this.dataaccgrpid = (dataaccgrpid!="null"&&typeof(dataaccgrpid)!="undefined")?dataaccgrpid:"";
	
	this.init();
}

pageInsertDataaccgroup.create.prototype = {
	init:function(){
		this.initForm();
		this.initDataObjectTree();
	},
	
	initForm:function(){
		this.loadFormFiled();
		
		var groupForm = $("form[@name=groupForm]");
		var p = this;
		
		$("input#save_dataGroups").mousedown(function(){
			document.getElementById("record:saveXml").value = p.makeSaveXml().xml;
		});
		/**
		var options = {
			contentType:"application/x-www-form-urlencoded;charset=UTF-8",//头使用UTF-8编码
	        beforeSubmit:  function(formData, jqForm, options){
	        	//jqForm.filedValue();
	        	 //var saveXml = p.makeSaveXml();
	        	 //alert(saveXml.xml);
	        	 //var queryString = $.param(formData);
	        	 
	        	 //alert(queryString);
	        	 //return false;
	        },  // pre-submit callback 
	        success:       function(xml, statusText){
	        	if($("error-code",xml).text()=="000000"){
	        		var parentPage = window.dialogArguments[0];
	        		var href = parentPage.location.href;
	        		parentPage.location.href = href+"?select-key:dataaccgrpid="+p.dataaccgrpid;
	        		window.close();
	        		
	        	}else{
	        		alert($("error-desc",xml).text());
	        		
	        	}
	        }, // post-submit callback 
	        dataType:  "xml"//
		}
		
		groupForm.ajaxForm(options);
		* */
	},
	
	loadFormFiled:function(){
		if(this.dataaccgrpid!=""){
			//alert(".."+this.dataaccgrpid);
			var loadFiledSrc = this.contextPath+"/txn1030044.ajax?primary-key:dataaccgrpid="+this.dataaccgrpid;
			//alert(loadFiledSrc);
			$.get(loadFiledSrc,function(xml){
				//alert(xml.xml);
				if($("error-code",xml).text()=="000000"){
					$("record",xml).each(function(){
						var dataaccgrpid = $("dataaccgrpid",this).text();
						var dataaccgrpname = $("dataaccgrpname",this).text();
						var dataaccrule = $("dataaccrule",this).text();
						var dataaccgrpdesc = $("dataaccgrpdesc",this).text();
						
						$("input[@property=dataaccgrpid]").attr("value",dataaccgrpid);
						$("input[@property=dataaccgrpname]").attr("value",dataaccgrpname);
						
						$("option[@value="+dataaccrule+"]",$("select[@property=dataaccrule]")).attr("selected","selected");
					
						$("textarea[@property=dataaccgrpdesc]").attr("value",dataaccgrpdesc);
					});
					//var datagroupid
					
				}else{
					alert($("error-desc",xml).text());
				}
			});
		}
	},
	
	initDataObjectTree:function(){
		JqTree.treeConfig.rootIcon = this.contextPath+"/"+JqTree.treeConfig.rootIcon;
		JqTree.treeConfig.loadingIcon = this.contextPath+"/"+JqTree.treeConfig.loadingIcon;
		var p = this;
		//var dataObjectTree = 
		var pageUrl = "txn.ajax";
		var nodePageUrl = this.contextPath+"/txn103018.ajax?select-key:dataaccgrpid="+this.dataaccgrpid;
		
		var actionHandler = new JqTree.ActionHandler.create();
		actionHandler.click.handler = clickHandler;
		var attributes = new Array("objectid","objectsource");
		var srcParams = new Array();
		srcParams["objectsource"]="default";
		srcParams["objectid"]="default";
		
		var treeObject = new JqTree.TreeObject.create("id","name","parentcode");
		this.dataObjectTree = new JqTree.create("\u6570\u636e\u6743\u9650",treeObject,pageUrl,nodePageUrl,attributes,
			actionHandler,true,false,false,false,false,null,null,false,srcParams);
		
		//alert(this.dataObjectTree.tree.html());
		$("div#dataObjectTree").append(this.dataObjectTree.tree);
		
		var dataObjectUrl = this.contextPath+"/txn103011.ajax";
		
		
		$.get(dataObjectUrl,function(xml){
			p.dataObjectTree.treeRootDiv.click();
			p.dataObjectTree.treeRootDiv.mousedown();
			var rootNode = $("ul:first",p.dataObjectTree.treeRootDiv);
			
			var counts = $("record",xml).size();
			var count = 0;
			$("record",xml).each(function(){
				var objectid = $("objectid",this).text();
				var objectsource = $("objectsource",this).text();
				var dataObject = new Object();
				//dataObject['parentcode']= "0";
				//dataObject['src'] = nodePageUrl;
				//dataObject['expand'] = "false";
				//alert(objectsource);
				var idElement = xml.createElement("id");
				var objectidElement = xml.createElement("objectid");
				var nameElement = xml.createElement("name");
				var parentCodeElement = xml.createElement("parentcode");
				var srcElement = xml.createElement("src");
				idElement.text = objectid;
				nameElement.text = objectsource;
				parentCodeElement.text = "0";
				srcElement.text = nodePageUrl+"&select-key:parentcode=0&select-key:objectsource="+objectsource+"&select-key:objectid="+objectid;
				objectidElement.text = objectid;
				this.appendChild(idElement);
				this.appendChild(nameElement);
				this.appendChild(parentCodeElement);
				this.appendChild(srcElement);
				this.appendChild(objectidElement);
				var treeNodeDiv = p.dataObjectTree._addNode(rootNode,this);
				
				count++;
				if(count==counts){
					p.dataObjectTree._setLastNode(treeNodeDiv);
				}
			});
		});
		
		function clickHandler(){
			//alert("..."+this.jqTreeNode.node.attr("level"));
			//var node = this.jqTreeNode.node;
			//var level = node.attr("level");
			//var objectsource = node.attr("objectsource");
			//alert(node.parent().html());
			//$("input:first").attr("value",node.attr("src"));
			return false;
		}
		//103011
	},
	
	makeSaveXml:function(){
		var saveXml=new ActiveXObject("Microsoft.XMLDOM");
		var xmlHead = saveXml.createProcessingInstruction("xml"," version=\"1.0\"");
		saveXml.insertBefore(xmlHead,saveXml.firstChild);
		
		var rootElement = saveXml.createElement('objects');
		saveXml.appendChild(rootElement);
		
		var objectElement;
		var iCount = 0;
		$("input.jTree_checkbox[@type=checkbox]",this.dataObjectTree).each(function(){
			objectElement = saveXml.createElement('object');
			var treeNode = $(this.parentNode);
			var dataaccid = treeNode.attr("id").substring("5");
			
			var objectid = treeNode.attr("objectid");
			//alert(objectid);
			var pid = treeNode.attr("pid");
			var oChecked =  treeNode.attr("checked");//原始的选择状态
			//alert("oChecked:"+oChecked);
			if(oChecked==null||typeof(oChecked)=="undefined"){
				oChecked = "false";
			}
			
			//alert("oChecked-af:"+oChecked);
			//alert(oChekced);
			//oChekced = (oChecked==null||typeof(oChekced)=='undefined')?"false":oChekced;
			var checked = "false";
			if(this.checked){
				checked = "true";
			}
			var iCoint=0;
			if(pid!="item_0"){
				//alert(checked+"|"+oChecked);
				//如果现在的状态和原始状态不一致，则保存
				//如果原始记录为 oChecked=true 记录为check=false 时保存
				//如果原始记录为 oChecked=false 记录为check=true 时保存
				if((oChecked=="true"&&checked=="false")||(oChecked=="false"&&checked=="true")){
					objectElement.setAttribute("dataaccid",dataaccid);
					objectElement.setAttribute("objectid",objectid);
					objectElement.setAttribute("checked",checked);
					rootElement.appendChild(objectElement);
					iCount++;
				}
			}
			
		});
		return saveXml;
	},
	
	print:function(){
		
	}
}