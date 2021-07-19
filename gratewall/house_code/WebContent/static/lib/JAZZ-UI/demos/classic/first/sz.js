$(function() {
	createTree();
	createMenu();
	expendMian();
});

var zNodes =[ 
             { id:1, pId:0, name:"父节点1 - 展开", open:true}, 
             { id:11, pId:1, name:"父节点11 - 折叠", font:{'font-weight':'bold'}}, 
             { id:111, pId:1111, name:"叶子节点111"}, 
             { id:112, pId:1111, name:"叶子节点112"}, 
             { id:113, pId:1111, name:"叶子节点113"}, 
             { id:114, pId:11, name:"叶子节点114"}, 
             { id:12, pId:1, name:"父节点12"}, 
             { id:121, pId:12, name:"叶子节点121"}, 
             { id:122, pId:12, name:"叶子节点122"}, 
             { id:123, pId:12, name:"叶子节点123"}, 
             { id:124, pId:12, name:"叶子节点124"}, 
             { id:13, pId:1, name:"父节点13 - 链接", url:"http://www.baby666.cn", target:"_blank"}, 
             { id:2, pId:0, name:"父节点2 - 折叠"}, 
             { id:21, pId:2, name:"父节点21 - 展开", open:true}, 
             { id:211, pId:21, name:"叶子节点211"}, 
             { id:212, pId:21, name:"叶子节点212"}, 
             { id:213, pId:21, name:"叶子节点213"}, 
             { id:214, pId:21, name:"叶子节点214"}, 
             { id:22, pId:2, name:"父节点22 - 折叠"}, 
             { id:221, pId:22, name:"叶子节点221"}, 
             { id:222, pId:22, name:"叶子节点222"}, 
             { id:223, pId:22, name:"叶子节点223"}, 
             { id:224, pId:22, name:"叶子节点224"}, 
             { id:23, pId:2, name:"父节点23 - 折叠"}, 
             { id:231, pId:23, name:"叶子节点231"}, 
             { id:232, pId:23, name:"叶子节点232"}, 
             { id:233, pId:23, name:"叶子节点233"}, 
             { id:234, pId:23, name:"叶子节点234"}, 
             { id:3, pId:0, name:"父节点3 - 无跳转链接", url:"", target:"_blank", click:""} 
         ]; 


function createTree() {
	$('#lefttree').tree({
		data : zNodes,
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
						$("#maincontent").panel("option", "frameurl", "http://www.qq.com");
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
		url:"button.json", 
		callback : function(data,obj,res) {
			if(!!data.data){
				var menubar = $("#header_tab_menubar");
				if (menubar[0] && menubar.data('toolbar')) {
					menubar.destroy();
				}
				$(".header_tab_menubar").remove();
				$(".serarch").append("<div id='header_tab_menubar' ></div>");
				
				var nextItems = [];
				$.each(data.data,function(i,menu){
					if(menu.functionType == 1){
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
					var subId = nextItems[j].pkFunction;
					var subText = nextItems[j].functionNameShort;
					var subPid = nextItems[j].superFuncCode;
					var newdata = [{"id":subId,"pId":subPid,"name":subText,"open":true}];
					var obj = {
						vtype : "button",
						name : "btn_" + subId,
						text : subText,
						defaultview : 2,
						height : 44,
						width : 120,
						group:"group1",
						checktype:"radio",
						issplitbutton : false,
						iconurl : "images/playlist.png",
						items : null,
						click : function(id,newdata) {
							return function() {
								var tempdata = newdata.slice();
								var olddata = findMenuTree(data.data,id);
								$.each(olddata,function(i,menu){
									var id = menu.pkFunction;
									var name = menu.functionNameShort;
									var pid = menu.superFuncCode;
									var funUrl = menu.functionUrl;
									var obj = {"id":id,"pId":pid,"name":name,"funUrl":funUrl};
									newdata.push(obj);
								});
								$('#lefttree').tree("loadData",newdata,null,"static");
								newdata = tempdata;
							};
						}(subId,newdata)
					}
					opt.items.push(obj);
				}
				$("#header_tab_menubar").toolbar(opt);
				
				//默认点击button
				setTimeout(function(){$("#header_tab_menubar .button-main:first").button("triggerClick");},300);
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
            temp = findMenuTree(data,data[i].pkFunction);           
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
		$(".jazz-column-btn-l").click();
		$(".jazz-column-border-cursor").css("background-color","#efefef");
	});
	
	$(".jazz-column-border-cursor").click(function(){
		$(this).css("background-color","white");
		$(".jazz-column-btn-l").css("display","none");
	});
	
	$(".toparrow").click(function(){
		if($(this).hasClass("top")){
			$("#header").show();
			$(this).removeClass("top");
			$(".jazz-row-btn-b").click();
		}else{
			$("#header").hide();
			$(this).addClass("top");
			$(".jazz-row-btn-t").click();
		}
		$(".jazz-row-border-cursor").css("background-color","white");
		$(".jazz-row-btn-b").css("display","none");
	});
}