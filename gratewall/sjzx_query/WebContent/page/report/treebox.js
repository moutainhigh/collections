function treeBox_init(inputId,treeOptions,treeBoxHelpers,checkedNodes){
	if(!inputId){
		alert('传入的输入控件id为空。');
		return ;
	}
	var oInput = $('#'+inputId);
	if(oInput.length<1){
		alert('传入的输入控件id不正确。');
		return ;
	}
	
	var oInputText = $('#'+inputId+'Text');
	if(oInputText.length<1){
		alert('传入的输入控件id不正确。');
		return ;
	}
	
	var treeBoxId = inputId + '_treeOptions';
	var treeBoxTableId = inputId + '_table';
	var treeBoxHtml = "<table id='"+treeBoxTableId+"' class='treebox-table'" +
			" style='border:solid 1px #999;width:398px;' ><tr><td>";
	
	treeBoxHtml += "<button onclick=\"onHelperAll('"+inputId+"')\">全部</button>&nbsp;<button onclick=\"onHelperClear('"+inputId+"')\">清空</button>";
	var oInputHelper = $('#'+inputId+'Helper');
	if(oInputHelper.length>0){
		var groupCode = oInputHelper.val();
		var helperList = groupCode && treeBoxHelpers && treeBoxHelpers[groupCode] ;
		if(helperList && helperList.length && helperList.length>0){
			for(var i = 0;i<helperList.length; ++i){
				var helper = helperList[i];
				if(!helper){
					continue;
				}
				var helperId = helper['id'] || '';
				var helperLabel = helper['label'] || '';
				var helperValue = helper['value'] || '';
				var helperMine = helper['mine'];
				if(helperLabel && helperValue){
					treeBoxHtml += "&nbsp;&nbsp;<span id='helper_"+helperId+"' class='report-helper' onclick=\"onHelperClick('"+inputId+"','"+helperValue
						+"');\">"+helperLabel
						+"</span>";
					if(helperMine=='true'){
						treeBoxHtml += "<span style='color:red;cursor:pointer;font-family:verdana;' title='删除查询条件' onclick=\"onHelperDelete('"
							+helperLabel+"')\">X</span>";
					}
				}
			}
		}
	}
	
	treeBoxHtml += "</td></tr><tr><td><div id='"+treeBoxId
		
		+"' class='ztree'></div><td></tr><tr><td>";

	treeBoxHtml += "<input type='text' style='width:60px;' id='"+inputId+"NewLabel'/><button style='' onclick=\"onHelperSave('"+inputId+"');\">保存查询条件</button>";	
	
	treeBoxHtml += "</td></tr></table>";
	oInput.after(treeBoxHtml);
	var oTreeBoxTable = $('#'+treeBoxTableId);
	var oTreeBox = $('#'+treeBoxId);

	$.fn.zTree.init(oTreeBox, {
		check: {enable: true,chkboxType:{ "Y":"ps","N":"ps"}},
	 	data : {simpleData:{enable:true}},
	 	callback: {
			onCheck: function(event, treeId, treeNode){
				
				var treeObj = $.fn.zTree.getZTreeObj(treeId);
				var nodes = treeObj.getCheckedNodes(true);
				updateCheck(inputId,treeObj);
			}
		}
	}, treeOptions);
	// 初始化时，根据输入框的值勾选树
	var inputValue = oInput.val();
	if(inputValue){
		var treeObj = $.fn.zTree.getZTreeObj(treeBoxId);
		var nodes = treeObj.getNodesByFilter(function(node){
			return true ;
		});
		$.each(nodes,function(idx,node){
			if(node.name){
				if(inputValue.indexOf(node.name)>-1){
					treeObj.checkNode(node,true,false,false);
				}
			}
		});
	}

	// 默认隐藏    
	oTreeBoxTable.hide();
	// 输入框获得焦点则显示
	oInputText.focus(showTreeBox);
	//oInputText.mouseenter(showTreeBox);
	//oInputText.click(showTreeBox);
	// 鼠标移出则隐藏
	//oTreeBoxTable.mouseleave(function(){
	//	oTreeBoxTable.hide();
	//});
	
	onHelperAll(inputId);
	
	function showTreeBox(){
		$(".treebox-table").hide();//全部隐藏，确保同时只显示一个
		
		var offset = oInputText.offset();
		
		oTreeBoxTable.css({left:offset.left+"px",
				top:offset.top+oInputText.outerHeight()+"px",
				position:'absolute',
				backgroundColor:'#FFF'})
			.slideDown("fast");
	
	}
}

$(document).bind('click',function(e){
	var target = $(e.target);
	if(target.closest(".treebox-table").length==0
			&& target.closest(".report-treebox-text").length==0
	){
		$(".treebox-table").hide();
	}
	
});
/**
 * 全选。
 * @param inputId
 */
function onHelperAll(inputId){
	var treeId = inputId + '_treeOptions';
	var treeObj = $.fn.zTree.getZTreeObj(treeId);
	treeObj.checkAllNodes(true);
//	var nodes = treeObj.getNodesByFilter(function(node){
//		return true ;
//	});
//	$.each(nodes,function(idx,node){
//		treeObj.checkNode(node,true,false,false);
//	});
	updateCheck(inputId,treeObj,true);
}
/**
 * 清空。
 * @param inputId
 */
function onHelperClear(inputId){
	var treeId = inputId + '_treeOptions';
	var treeObj = $.fn.zTree.getZTreeObj(treeId);
	treeObj.checkAllNodes(false);
	updateCheck(inputId,treeObj);
}
/**
 * 通过Helper选择。
 * @param inputId
 * @param value
 */
function onHelperClick(inputId,value){
	if(!value){
		return ;
	}
	
	var treeId = inputId + '_treeOptions';
	var treeObj = $.fn.zTree.getZTreeObj(treeId);
	
	var nodes = treeObj.getNodesByFilter(function(node){
		return true ;
	});
	
	$.each(nodes,function(idx,node){
		if(node.id){
			if(value.indexOf(node.id)>-1){
				treeObj.checkNode(node,true,false,false);
			}else{
				treeObj.checkNode(node,false,false,false);
			}
		}
	});
	updateCheck(inputId,treeObj);
}
function onHelperDelete(label){
	var isSure = window.confirm("确认删除查询条件“"+label+"”？");
	if(!isSure){
		return ;
	}
	
	label = encodeURI(label);
	$.ajax({
		url : '../../report/reportHelperAction.do?method=delete',
		dataType:'json',
		async:false,
		data:{
			"label":label
		},
		type:'POST',
		encoding:'UTF-8',
		contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
		error:function(obj){
			return false;
		},
		complete: function(){
			return true;
		},
		success: function(responeText){
			var msg = responeText;
			if(msg=='ok'){
				window.location.href = window.location.href;
			}
		}
	});// end of ajax
}
function onHelperSave(inputId){
	var oNewLabel = $('#'+inputId+'NewLabel');
	var label = oNewLabel.val();
	if(!label){
		alert('请输入“查询条件”名称');
		oNewLabel.focus();
		return ;
	}
	
	//var treeId = inputId + '_treeOptions';
	//var treeObj = $.fn.zTree.getZTreeObj(treeId);
	var oInput = $('#'+inputId);
	var config = oInput.val();
	if(!config){
		alert('您没有选择任何查询条件');
		return ;
	}
	
	var oInputHelper = $('#'+inputId+'Helper');
	var groupCode = oInputHelper.val();
	label = encodeURI(label);
	$.ajax({
		url : '../../report/reportHelperAction.do?method=save',
		dataType:'json',
		async:false,
		data:{
			"helper.label":label,
			"helper.groupCode":groupCode,
			"helper.value":config
		},
		type:'POST',
		encoding:'UTF-8',
		contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
		error:function(obj){
			alert('获取Treebox Helper出错:'+obj.responseText);
			return false;
		},
		complete: function(){
			return true;
		},
		success: function(responeText){
			var msg = responeText;
			if(msg=='ok'){
				window.location.href = window.location.href;
			}
		}
	});// end of ajax
}
function updateCheck(inputId,treeObj,isAll){
	var oInput = $('#'+inputId);
	var oInputText = $('#'+inputId+'Text');
	if(isAll==true){
		oInput.val('');
		oInputText.val('全部');
		return ;
	}
	var nodesStr ,nodesIdStr ;
//	var nodes = treeObj.getCheckedNodes(true);
//	
//	$.each(nodes,function(idx,item){
//		if(item.name){
//			if(!item.isParent){
//				if(nodesStr){
//					nodesStr += ',' + item.name;
//				}else{
//					nodesStr = item.name;
//				}
//				if(nodesIdStr){
//					nodesIdStr += ',' + item.id;
//				}else{
//					nodesIdStr = item.id;
//				}
//			}
//		}
//	});
	
	// 处理全部选中的情况
	

	var nodes = treeObj.getNodesByFilter(function(node){
		return true ;
	});
	var isAllSelected = true;
	$.each(nodes,function(idx,node){
		if(node.checked==false){
			isAllSelected = false;
		}else{
			if(nodesStr){
				nodesStr += ',' + node.name;
			}else{
				nodesStr = node.name;
			}
			var idValue = (node.target)?node.target:node.id;
			if(nodesIdStr){
				nodesIdStr += ',' + idValue;
			}else{
				nodesIdStr = idValue;
			}
		}
	});
	
	nodesStr = nodesStr||'';
	nodesIdStr = nodesIdStr||'';
	
	
	oInput.val(nodesIdStr);
	oInputText.val(nodesStr);
	
	if(isAllSelected){
		oInput.val('');
		oInputText.val('全部');
	}
}
