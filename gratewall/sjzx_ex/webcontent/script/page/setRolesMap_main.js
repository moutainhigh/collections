var pageRoleSetOpenMain = new Object();

pageRoleSetOpenMain.create = function(contextPath){
	this.contextPath = contextPath;
	this.init();
}

pageRoleSetOpenMain.create.prototype = {
	contextPath:null,
	
	zdyDataaccgrpid:"",
	
	zdyDataaccrule:"", 
	
	init:function(){
		this.initRoles();
		var p = this;
		/**
		 * 直接该角色分配数据权限
		 */
		$("input#roleDataAcc").click(function(){
			var roleid = $("option[@selected]",$("select#roles")).attr("value");
			if(roleid){
				var page = new pageDefine("roleDataAccs.jsp?roleid="+roleid, "  ", "modal",650, 400);//?rolefunids="+rolefunids+"&dataaccgrpids="+dataaccgrpids+"&dataaccgrpid="+p.zdyDataaccgrpid+"&zdyDataaccrule="+p.zdyDataaccrule
				page.goPage(); 
			}
		});
		/**
		 * 打开选择功能权限界面
		 */
		 
		$("input#selectDataAcc").click(function(){
		 	
			var rolefunids = "";
			$("input.jTree_checkbox[@type=checkbox]",this.Tree).each(function(){
				    var treeNode = $(this.parentNode);
				    var rolefunid = treeNode.attr("id");
					if(rolefunid!=""){
					    $("span.jTree_item_text:first",$(this).parent()).css("color","blue");
					    if(this.checked&&rolefunids.indexOf(rolefunid)==-1){
					        rolefunids+=","+rolefunid;
					    }
					}else{
					    $("span.jTree_item_text:first",$(this).parent()).css("color","black");					
					}					
			});
			rolefunids=rolefunids.substring(1);
			if(rolefunids==""){
			    alert("\u6ca1\u6709\u9009\u4e2d\u7684\u529f\u80fd\u70b9");
			    return false;
			}
			var dataaccgrpids;
			var dataaccgrpidArray = new Array();
			$("option",$("select#dataObjects")).each(function(){
				//alert(this.dataacctype);
				if(this.dataacctype!=0){
					dataaccgrpidArray[dataaccgrpidArray.length] = this.value;
				}
			});
			dataaccgrpids = dataaccgrpidArray.join();
			//dataaccgrpids = "1,2,3";
			
			//var functionCodeArray = new Array();
			//$("option[@selected]",$("select#functions")).each(function(){
				//functionCodeArray[functionCodeArray.length] = this.value;
			//});
			//var rolefunids = functionCodeArray.join();
			//alert("selectDataAccs.jsp?rolefunids="+rolefunids+"&dataaccgrpids="+dataaccgrpids+"&dataaccgrpid="+p.zdyDataaccgrpid+"&zdyDataaccrule="+p.zdyDataaccrule);
			//alert("selectDataAccs.jsp?dataaccgrpid="+p.zdyDataaccgrpid+"&zdyDataaccrule="+p.zdyDataaccrule);
			var page = new pageDefine("selectDataAccs.jsp?dataaccgrpid="+p.zdyDataaccgrpid+"&zdyDataaccrule="+p.zdyDataaccrule, "  ", "modal",650, 400);//?rolefunids="+rolefunids+"&dataaccgrpids="+dataaccgrpids+"&dataaccgrpid="+p.zdyDataaccgrpid+"&zdyDataaccrule="+p.zdyDataaccrule
			window.dataaccgrpids = dataaccgrpids;
			window.rolefunids = rolefunids;
			//alert(window.rolefunids+"|"+rolefunids);
			page.addArgs($("div#functions"));
			page.goPage();
			
		   /**
			
			if($("option[@selected]",$("select#functions")).size()==0){
				return false;
			}
			var dataaccgrpids;
			var dataaccgrpidArray = new Array();
			$("option",$("select#dataObjects")).each(function(){
				//alert(this.dataacctype);
				if(this.dataacctype!=0){
					dataaccgrpidArray[dataaccgrpidArray.length] = this.value;
				}
			});
			dataaccgrpids = dataaccgrpidArray.join();
			//dataaccgrpids = "1,2,3";
			
			var functionCodeArray = new Array();
			$("option[@selected]",$("select#functions")).each(function(){
				functionCodeArray[functionCodeArray.length] = this.value;
			});
			var rolefunids = functionCodeArray.join();
			alert("selectDataAccs.jsp?rolefunids="+rolefunids+"&dataaccgrpids="+dataaccgrpids+"&dataaccgrpid="+p.zdyDataaccgrpid+"&zdyDataaccrule="+p.zdyDataaccrule);
			var page = new pageDefine("selectDataAccs.jsp?dataaccgrpid="+p.zdyDataaccgrpid+"&zdyDataaccrule="+p.zdyDataaccrule, "  ", "modal",650, 400);//?rolefunids="+rolefunids+"&dataaccgrpids="+dataaccgrpids+"&dataaccgrpid="+p.zdyDataaccgrpid+"&zdyDataaccrule="+p.zdyDataaccrule
			window.dataaccgrpids = dataaccgrpids;
			window.rolefunids = rolefunids;
			//alert(window.rolefunids+"|"+rolefunids);
			page.addArgs($("select#functions"));
			page.goPage();	
			
			
			*/		
			
		});
		/**
		 * 删除功能对应的数据权限组
		 */
		$("input#removeDataGroups").click(function(){
			if($("option[@selected]",$("select#dataObjects")).not("[@dataacctype=0]").size()==0){
				alert("\u8bf7\u9009\u62e9\u53ef\u4ee5\u5220\u9664\u7684\u6743\u9650");//请选择可以删除的权限。
				return false;
			}
			if(window.confirm("\u786e\u8ba4\u79fb\u9664\u6743\u9650\u7ec4")){//确认移除权限组
				var functionCodeArray = new Array();
				
				//$("option[@selected]",$("select#functions")).each(function(){
					//functionCodeArray[functionCodeArray.length] = this.value;
				//});
				
				$("input.jTree_checkbox[@type=checkbox]",this.Tree).each(function(){
					    var treeNode = $(this.parentNode);
					    var rolefunid = treeNode.attr("id");
                        functionCodeArray[functionCodeArray.length] = rolefunid;				
				});							
				var dataaccgrpids;
				var dataaccgrpidArray = new Array();
				
				$("option[@selected]",$("select#dataObjects")).each(function(){
					//alert(this.dataacctype);
					if(this.dataacctype!=0){
						dataaccgrpidArray[dataaccgrpidArray.length] = this.value;
					}else{
						$(this).removeAttr("selected");
					}
				});
				
				$("option[@selected]",$("select#dataObjects")).remove();
				dataaccgrpids = dataaccgrpidArray.join();
				
				//var rolefunids = functionCodeArray.join();
				var objectids = functionCodeArray.join();//功能代码集，用,号分隔
				if(dataaccgrpids){
					//dataaccdispobj使用默认值2
					var removeDataGroupsSrc = "txn103048.ajax";
					var params = "primary-key:dataaccdispobj=2&primary-key:objectids="+objectids+"&primary-key:dataaccgrpids="+dataaccgrpids;
					//alert(params)
					$.ajax({
						contentType : "application/x-www-form-urlencoded;charset=UTF-8",//
						type		: "POST",
			  			url			:  removeDataGroupsSrc,
			  			dataType	: "xml",
			  			success		:  doRemove,
			  			data		:  params
					});
					
					 function doRemove(xml){
						if($("error-code",xml).text()=="000000"){
							$("option[@selected]",$("select#dataObjects")).remove();
						}else{
							alert($("error-desc",xml).text());
						}
					 }
				}
				
			}
		});
	},
	/**
	 * load all roles and init rolesSelect's change event
	 */
	initRoles:function(){
	    var regname = "";
		var p = this;
		var getRolesUrl = this.contextPath+"/txn980329.ajax";
		var rolesSelect = document.getElementById("roles");
		$.get(getRolesUrl,function(xml){
			$("record",xml).each(function(){
				var roleid = $("roleid",this).text();
				var rolename = $("rolename",this).text();
				regname = $("regname",this).text();
				
				//var oOption =$("<option>" + rolename + "</option>");
				//$(rolesSelect).append(oOption);
				//oOption.removeAttr("selected");
				
				var oOption = document.createElement("option");
				oOption.text = rolename;
				oOption.value = roleid;
				rolesSelect.add(oOption);
			});
		});
		
		$(rolesSelect).change(function(){
			p.loadFunctions(this.value,regname);
		});
		
		$("select#functions").change(function(){
			
			var functionCodeArray = new Array();
			$("option[@selected]",this).each(function(){
				functionCodeArray[functionCodeArray.length] = this.value;
			});
			p.loadDataObjects(functionCodeArray,$("option[@selected]",rolesSelect).attr("value"));
			//alert(1);
		});
		
		$("input#selectAll").click(function(){
		    //alert();
			$("input.jTree_checkbox[@type=checkbox]",this.Tree).each(function(){
				    this.checked = true;		
			});		     
			//if()
			//if($("option[@selected]",$("select#functions")).size()!=$("option",$("select#functions")).size()){
				//$("option",$("select#functions")).attr("selected","selected");
				//$("select#functions").change();
			//}
		});
		
		$("input#selectDispose").click(function(){
			//$("select#dataObjects").empty();
			//$("option[@selected]",$("select#functions")).removeAttr("selected");
			//$("option[@selected]",$("select#functions")).each(function(){
				//this.selected = false;
			//});
			$("input.jTree_checkbox[@type=checkbox]",this.Tree).each(function(){
				    this.checked = false;		
			});	
		});
			
		$("input#selectDataAccGroup").click(function(){
		
		    var rolefunids = "";
			$("input.jTree_checkbox[@type=checkbox]",this.Tree).each(function(){
				    var treeNode = $(this.parentNode);
				    var rolefunid = treeNode.attr("id");
					if(rolefunid!=""){
					    $("span.jTree_item_text:first",$(this).parent()).css("color","blue");
					    if(this.checked&&rolefunids.indexOf(rolefunid)==-1){
					        rolefunids+=","+rolefunid;
					    }
					}else{
					    $("span.jTree_item_text:first",$(this).parent()).css("color","black");					
					}					
			});
			rolefunids=rolefunids.substring(1);
			if(rolefunids==""){
			    alert("\u6ca1\u6709\u9009\u4e2d\u7684\u529f\u80fd\u70b9");
			    return false;
			}				
	     	var functionCodeArray = new Array();
			var roleid = $("option[@selected]",$("select#roles")).attr("value");		
			functionCodeArray[0] = rolefunids;
		    p.loadDataObjects(functionCodeArray,roleid);													
		});
		
		//$("input.jTree_checkbox[@type=checkbox]",this.Tree).click(function(){
		    //alert("cccc");		
		//});
						
	},
	
	/**
	 * 加载功能权限
	 */
	loadFunctions:function(roleid,regname){
	/**	
		var p = this;
		var loadFunctionSrc = this.contextPath+"/txn103037.ajax?select-key:roleid="+roleid;
		var loadingSrc = this.contextPath+"/images/tree/loading.gif";
		var txntionsSelect = $("select#functions");
		txntionsSelect.empty();
		$.get(loadFunctionSrc,function(xml){
			var loading = $("<img src=\""+loadingSrc+"\"></img>");
			loading.insertBefore(txntionsSelect);
			$("record",xml).each(function(){
				var txncode = $("txncode",this).text();
				var txnname = $("txnname",this).text();
				var roleaccid = $("roleaccid",this).text();
				
				//var oOption =$("<option value=\""+roleaccid+"\">"+txnname+"</option>");
				var oOption = document.createElement("option");
				oOption.text = txnname;
				oOption.value = roleaccid;
				txntionsSelect.get(0).add(oOption);
				//oOption.removeAttr("selected");
			});
			loading.remove();
			txntionsSelect.get(0).selectedIndex = 0;
			var functionCodeArray = new Array();
			functionCodeArray[0] = $("option:first",txntionsSelect).attr("value");
			if(functionCodeArray[0]){
				p.loadDataObjects(functionCodeArray,roleid);
			}else{
				$("select#dataObjects").empty();
			}
		});
   
     	*/ 		   				 	
  		var p = this;
  		this.treeContainer=$("div#functions");
  		var pageUrl = this.contextPath+"/txn9803101.ajax?select-key:roleid="+roleid+"&select-key:regname="+regname;
		var nodePageUrl = this.contextPath+"/txn9803102.ajax?select-key:roleid="+roleid;		
		var attributes = new Array("id","name","fjd");
		var treeObject = new JqTree.TreeObject.create("funccode","funcname","parentcode");
		
		this.Tree = new JqTree.create("\u6570\u636e\u6743\u9650",treeObject,pageUrl,nodePageUrl,attributes,
			null,true,false,false,false);
		this.treeContainer.empty();		
		this.treeContainer.append(this.Tree.tree);	
		/**
		$("input.jTree_checkbox[@type=checkbox]",this.Tree).each(function(){
			    var treeNode = $(this.parentNode);
			    var rolefunid = treeNode.attr("id");
				if(rolefunid!=""){
				    if(this.checked){
				        rolefunids+=","+rolefunid;
				    }
				}					
		});
		rolefunids=rolefunids.substring(1);
		*/
        $("select#dataObjects").empty();		
	    //p.loadDataObjects(functionCodeArray,roleid);		    
		   				
	},
	/**
	 * 加载功能树
	 */	
	loadTreeFunctions:function(roleid,regname){
	
	},
	/**
	 * 加载数据权限
	 */
	loadDataObjects:function(functionCodeArray,roleid){
/**			
		var p = this;
		p.zdyDataaccgrpid = "";
		p.zdyDataaccrule = "";
		var roleaccids = functionCodeArray.join();
		//alert(roleaccid+roleid);
		roleid = $("option[@selected]",$("select#roles")).attr("value");
		var dataObjectsSelect = $("select#dataObjects");
		dataObjectsSelect.empty();
		
		var loadDataObjectsSrc = this.contextPath+"/txn103046.ajax";
		var params = "select-key:roleid="+roleid+"&select-key:roleaccids="+roleaccids;
		//alert(loadDataObjectsSrc);
		//alert(roleid);
		//alert(params);
		//参数长度超过了1024 使用POST
		$.ajax({
			type		: "POST",
  			url			: loadDataObjectsSrc,
  			dataType	: "xml",
  			success		:  function(xml){
  							 	dataObjectsSelect.empty();
  							 	var ztdOption = document.createElement("option");
								ztdOption.value= "";//
								ztdOption.text = "\u81ea\u5b9a\u4e49\u6570\u636e\u6743\u9650\u7ec4";//自定义数据权限组
								ztdOption.dataacctype = 0;
								dataObjectsSelect.get(0).add(ztdOption); 
								$("record",xml).each(function(){
									//alert(xml.xml);
									var dataaccgrpid = $("dataaccgrpid",this).text();
									var dataaccgrpname = $("dataaccgrpname",this).text();
									var dataacctype = $("dataacctype",this).text();
									var dataaccrule = $("dataaccrule",this).text(); 
									
									if(dataacctype==0){
										p.zdyDataaccgrpid = dataaccgrpid;
										p.zdyDataaccrule = dataaccrule;
										//alert(p.zdyDataaccgrpid);
									}else{
										var option =document.createElement("option");
										option.value = dataaccgrpid;
										option.text = dataaccgrpname;
										option.dataacctype = dataacctype;
										dataObjectsSelect.get(0).add(option); 
									}
								});
  						   },
  			data		:params
		});
		//$.get(loadDataObjectsSrc,function(xml){
		//	
		//});

		*/	
		
		var p = this;
		p.zdyDataaccgrpid = "";
		p.zdyDataaccrule = "";
		var roleaccids = functionCodeArray.join();
		//alert(roleaccid+roleid);
		roleid = $("option[@selected]",$("select#roles")).attr("value");
		var dataObjectsSelect = $("select#dataObjects");
		dataObjectsSelect.empty();
		
		var loadDataObjectsSrc = this.contextPath+"/txn103046.ajax";
		var params = "select-key:roleid="+roleid+"&select-key:roleaccids="+roleaccids;
		//alert(loadDataObjectsSrc);
		//alert(params);
		//参数长度超过了1024 使用POST
		$.ajax({
			type		: "POST",
  			url			: loadDataObjectsSrc,
  			dataType	: "xml",
  			success		:  function(xml){
  							 	dataObjectsSelect.empty();
  							 	var ztdOption = document.createElement("option");
								ztdOption.value= "";//
								ztdOption.text = "\u81ea\u5b9a\u4e49\u6570\u636e\u6743\u9650\u7ec4";//自定义数据权限组
								ztdOption.dataacctype = 0;
								dataObjectsSelect.get(0).add(ztdOption); 
								$("record",xml).each(function(){
									//alert(xml.xml);
									var dataaccgrpid = $("dataaccgrpid",this).text();
									var dataaccgrpname = $("dataaccgrpname",this).text();
									var dataacctype = $("dataacctype",this).text();
									var dataaccrule = $("dataaccrule",this).text(); 
									
									if(dataacctype==0){
										p.zdyDataaccgrpid = dataaccgrpid;
										p.zdyDataaccrule = dataaccrule;
										//alert(p.zdyDataaccgrpid);
									}else{
										var option =document.createElement("option");
										option.value = dataaccgrpid;
										option.text = dataaccgrpname;
										option.dataacctype = dataacctype;
										dataObjectsSelect.get(0).add(option); 
									}
								});
  						   },
  			data		:params
		});
		//$.get(loadDataObjectsSrc,function(xml){
		//	
		//});	
		
		
	},
	
	print:function(){
		
	}
}