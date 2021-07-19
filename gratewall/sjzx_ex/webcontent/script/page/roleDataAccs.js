var pageRoleDataAccs = new Object();

pageRoleDataAccs.create = function(contextPath,roleid,dataaccgrpidArray){
	this.contextPath = contextPath;
	this.roleid = roleid;
	
	this.dataaccgrpidArray = dataaccgrpidArray||new Array();
	this.init();
}

pageRoleDataAccs.create.prototype = {
	init:function(){
		//alert(this.dataaccgrpid);
		var dataGroupsSelect = $("select#dataGroups");
		var dataGroupsSrc = this.contextPath+"/txn1030047.ajax?select-key:dataacctype=1";
		var p = this;
	
		$.get(dataGroupsSrc,function(xml){
			//alert(xml.xml);
			$("record",xml).each(function(){
				var dataaccgrpid = $("dataaccgrpid",this).text();
				var dataaccgrpname = $("dataaccgrpname",this).text();
				var dataacctype = $("dataacctype",this).text();
				//alert(dataaccgrpname);
				if(dataacctype!=0){
					//var oOption = $("<option value=\""+dataaccgrpid+"\">"+dataaccgrpname+"</option>");
					var oOption = document.createElement("option");
					oOption.text = dataaccgrpname;
					oOption.value = dataaccgrpid;
					dataGroupsSelect.get(0).add(oOption);
					var exsitFlag = false;
					//alert(dataaccgrpid+"||"+dataaccgrpidArray);
					for(var i=0;i<p.dataaccgrpidArray.length;i++){
						if(p.dataaccgrpidArray[i]==dataaccgrpid){
							exsitFlag = true;
						}
					}
					//alert(exsitFlag);
					if(exsitFlag == true){
						oOption.selected = "selected";
					}
				}
			});
		});
		
		dataGroupsSelect.change(function(){
			var selectGroups = new Array();
			$("option[@selected]",this).each(function(){
				selectGroups[selectGroups.length] = this.value;
			});
			document.getElementById("record:selectGroups").value = selectGroups.join();
		});
		
		this.initDataObjectTree();
		this.initForm();
		
	},
	
	initForm:function(){
		var groupForm = $("form[@name=groupForm]");
		var p = this;
		
		$("input#save_dataGroups").mousedown(function(){
			document.getElementById("record:saveXml").value = p.makeSaveXml().xml;
		});
		
		var options = {
	        beforeSubmit:  function(formData, jqForm, options){
	        	 
	        	 var queryString = $.param(formData);
	        	 
	        	 //alert(queryString);
	        	// return false;
	        },  // pre-submit callback 
	        success:       function(xml, statusText){
	        	//alert(xml.xml);
	        	if($("error-code",xml).text()=="000000"){
	        		var parentPage = window.dialogArguments[0];
	        		$(parentPage.functionsSelect).change();
	        		window.close();
	        	}else{
	        		alert($("error-desc",xml).text());
	        		
	        	}
	        }, // post-submit callback 
	        dataType:  "xml"//
		}
		
		groupForm.ajaxForm(options);
	},
	
	initDataObjectTree:function(){
		JqTree.treeConfig.rootIcon = this.contextPath+"/"+JqTree.treeConfig.rootIcon;
		JqTree.treeConfig.loadingIcon = this.contextPath+"/"+JqTree.treeConfig.loadingIcon;
		var p = this;
		//var dataObjectTree = 
		var pageUrl = "txn.ajax";
		var nodePageUrl = this.contextPath+"/txn103017.ajax?select-key:dataaccdispobj=1&select-key:rolefunids="+this.roleid;//not use
		
		var actionHandler = new JqTree.ActionHandler.create();
		actionHandler.click.handler = clickHandler;
		var attributes = new Array("objectid","objectsource");
		var srcParams = new Array();
		srcParams["objectsource"]="default";
		srcParams["objectid"]="default";
		
		var treeObject = new JqTree.TreeObject.create("id","name","parentcode");
		this.dataObjectTree = new JqTree.create("\u6570\u636e\u6743\u9650",treeObject,pageUrl,nodePageUrl,attributes,
			actionHandler,true,false,false,false,false,null,null,false,srcParams);
			
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
				//alert(objectid);
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
			var node = this.jqTreeNode.node;
			var level = node.attr("level");
			var objectsource = node.attr("objectsource");
			//alert(node.parent().html());
			//alert(node.attr("src"));
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