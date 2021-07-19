  var winWidth = $(window).width();

$(function() {

	$("#maincontent").parent().css("overflow","hidden");
    $("#maincontent").children().css({"overflow-y":"hidden","overflow-x":"hidden"});
	createTree();
	createMenu();
	expendMian();
/*	if (appCode==null || typeof(appCode)!="undefined"||appCode=="")
	{
		setTimeout(function(){$("#header_tab_menubar .button-main:first").button("triggerClick");},300);
	}else{
		setTimeout(function(){ selectButton(appCode); },300);
	}*/
	
  $("#lefttree").children().css({"overflow-x":"hidden"});//隐藏横向滚动条
  /*$(".right").click();
  $(".jazz-column-btn-r").css("display","none");*/
  $("#west").hide();
  
 /* $(window).resize(function() {
	  console.log()
	  window.location.reload; 
	});*/
  
});
			




function createTree(data) {
	$('#lefttree').tree({
		data : data,
		setting : {
			view : {
				dblClickExpand : false
			},
			data : {
				simpleData : {
					enable : true
				}
			},
			callback : {
				onClick : function onClick(e, treeId, treeNode) {
					if (treeNode.funUrl) {
						var contenturl = rootPath + treeNode.funUrl;
						$("#maincontent").panel("option", "frameurl",
								contenturl);
					} 
				}
			}
		}
	});

	$("#lefttree").layout({
		layout : "fit"
	});
}

function createMenu() {
	var params = {
		url:rootPath+"/home/getAppFuncByAppId.do?appCode="+appCode+"&random="+Math.random(), 
		callback : function(data,obj,res) {
			if(!!data.data){
				var menubar = $("#header_tab_menubar");
				if (menubar[0] && menubar.data('toolbar')) {
					menubar.destroy();
				}
				$("#header_tab_menubar").remove();
				$(".serarch").append("<div id='header_tab_menubar' ></div>");
				
				var nextItems = [];
				$.each(data.data,function(i,menu){
					if(!menu.superFuncCode){
						/*if(menu.functionCode=='SM01'){
							menu.imgpath = "../../static/images/other/playlist.png";
						}else if(menu.functionCode=='SM02'){
							menu.imgpath = "../../static/images/other/unlock.png";
						}else if(menu.functionCode=='SM03'){
							menu.imgpath = "../../static/images/other/file-alt.png";
						}else if(menu.functionCode=='SM04'){
							menu.imgpath = "../../static/images/other/settings-alt.png";
						}*/
						nextItems.push(menu);
					}
				});
				var opt = {
					vtype : "toolbar",
					width : "100%",
					height : "100%",
					items : []
				};
				
				for (var j = 0, nextlen = nextItems.length; j < nextlen; j++) {
					var subId = nextItems[j].functionCode;
					var subText = nextItems[j].functionName;
					var subPid = nextItems[j].superFuncCode;
					//var imgpath = nextItems[j].imgpath;
					var newdata = [{"id":subId,"pId":subPid,"name":subText,"open":true}];
					var tempArray = [];
					var tempFunName = [];
					var obj = {
						vtype : "button",
						name : "btn_" + subId,
						text : subText,
						defaultview : 2,
						height : 55,
						width : 120,
						group:"group1",
						checktype:"radio",
						issplitbutton : false,
						//iconurl : imgpath,
						items : null,
						click : function(id,newdata) {
							return function() {
								tempArray = [];
								tempFunName = [];
								var tempdata = newdata.slice();
								var olddata = findMenuTree(data.data,id);
								$.each(olddata,function(i,menu){
									var id = menu.functionCode;
									var name = menu.functionName;
									var pid = menu.superFuncCode;
									var funUrl = menu.functionUrl;
									var obj = {"id":id,"pId":pid,"name":name,"funUrl":funUrl};
									tempArray.push(funUrl);
									tempFunName.push(name);
									newdata.push(obj);
								});
								$('#lefttree').tree("loadData",newdata,null,"static");
								newdata = tempdata;
								
								var tempName = tempFunName[0];
								if(tempName!="首页"){
									/* $(".jazz-column-btn-r").css("display","block");
									 $(".jazz-column-btn-r").click();*/
									var aNode = $("#lefttree").find("a");
									aNode.each(function(){
										var _this = $(this);
										var text = _this.attr("title");
										if(text==tempFunName[0]){
											_this.addClass("curSelectedNode");
										}
									})
									
									$("#west").show();
									 $("#maincontent").panel("option", "frameurl","../.."+tempArray[0]);
									 $("div[region='center']").css("width",winWidth-210);
								}else if(tempName=="首页"){
									$("#west").hide();
									 $("div[region='center']").css("width",winWidth-10);
									 $("div[region='center']").children().css("width","100%");
									 $("div[region='center']").children().find(".jazz-panel-content").css("width","100%");
									 $("div[region='center']").children().find("#frame_maincontent").css("width","100%");
									 $(".jazz-column-btn-r").css("display","none");
									 $("#maincontent").panel("option", "frameurl","../.."+tempArray[0]);
								}
								
							};
							
							
						}(subId,newdata)
					}
					opt.items.push(obj);
				}
				$("#header_tab_menubar").toolbar(opt);
			}
			if (appCode==null || typeof(appCode)!="undefined"||appCode=="")
			{
				setTimeout(function(){$("#header_tab_menubar .button-main:first").button("triggerClick");},300);
			}else{
				setTimeout(function(){ selectButton(appCode); },300);
			}
		}
	};
	$.DataAdapter.submit(params);
	
	
}

function findMenuTree(data,pid){
	var result = [] , temp;
    for(var i in data){	
        if(data[i].superFuncCode==pid){
            result.push(data[i]);
            temp = findMenuTree(data,data[i].functionCode);           
            if(temp.length>0){
                result = result.concat(temp);
            }           
        }      
    }
    
    return result;
}

function expendMian(){
	$(".lefttop .right").click(function(){
		$(".jazz-column-btn-l").css("display","block");
		$(".jazz-column-border-cursor").css("background-color","#efefef");
		$("#allcontent").panel("layoutSwitch", "west");
		
		$(".jazz-column-btn-r").click(function(){
			$(this).css("background-color","white");
			$(".jazz-column-btn-l").css("display","none");
		}); 
	});
	
	$(".toparrow").click(function(){
		if($(this).hasClass("top")){
			$("#header").show();
			$(this).removeClass("top");
		}else{
			$("#header").hide();
			$(this).addClass("top"); 
		}
		$("#allcontent").panel("layoutSwitch", "north", 40);
		$(".jazz-row-border-cursor").css("background-color","white");
		$(".jazz-row-btn-b").css("display","none");
	});
	
}

function selectButton(code){
	$("#header_tab_menubar div[name=btn_"+code+"]").button("triggerClick");
}

