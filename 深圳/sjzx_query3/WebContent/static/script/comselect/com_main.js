//企业类型树
var tree;
var setting = {
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "pid"
			}
		},
		check: { 
            enable: true, 
            chkboxType:{ "Y":'s', "N":'s'} 
        }
	};
$(document).ready(function(){
	loadingtree();
	
		$('input:radio[name=enttype_radio]').change(function() {
			loadingtree();
		
		});
		
	});

function loadingtree(){
	var $selectedvalue =$('input:radio[name=enttype_radio]:checked').val();
	$("#enttypediv").empty();
	var treediv="<div id='enttpeTree' name='enttpeTree' class='ztree'  ></div>";
	$("#enttypediv").append(treediv);
	var params = {
			url : rootPath+'/comselect/queryEntytype.do',
			params: {
				radiovalue : $selectedvalue
			},
			callback : function(data, r, res) { 
				var data = data["data"];
				tree = $.fn.zTree.init($("#enttpeTree"), setting, data);
				tree.expandAll(false);
				if ($selectedvalue == 00) {//大类
				}
			}
		}

	$.DataAdapter.submit(params);
}
function deleteEntType(){
	$("input[name='enttype']").remove();
	
}
function dualtree(){
	var treeObj =tree;
	var  nodes=treeObj.getCheckedNodes(true);
    var  v="";
    var ids=""
    var list = new List(); //大类
    for(var i=0;i<nodes.length;i++){
    	if(nodes[i].id=="all"){
    		deleteEntType();
    		list.clear();
    		continue;
    		//return;
    	}
		var $selectedvalue =$('input:radio[name=enttype_radio]:checked').val();
		if ($selectedvalue == 00) {
				 if(list.contains(nodes[i].pid)){//只适用与当前层次 二层
					 list.add(nodes[i].id);
				 }else{
					 list.add(nodes[i].id);
			       	 var treein="<input name='enttype' type=hidden value='"+nodes[i].id+"'>";
			       	 $("#formID").append(treein);
				 }
		}else{
	    	if(nodes[i].stande=="Y"){
	       	 var treein="<input name='enttype' type=hidden value='"+nodes[i].id+"'>";
	       	 $("#formID").append(treein);
	       	}	
		}
		
    }
    list.clear();
 
}

function bigEntType(){
	
}

/**
 * 模拟JAVA list
 */
function List(){  
    this.init();  
};  
//初始化列表  
List.prototype.init = function(){  
    this.array = new Array();  
};  
//列表的大小  
List.prototype.size = function(){  
    return this.array.length;  
};  
//向列表添加一个元素  
List.prototype.add = function(element){  
    this.array.push(element);   
};  
//批量添加元素到列表  
List.prototype.addAll = function(list){  
    if(list && isList(list)){  
        var size = list.size();  
        var element;  
        for(var i = 0;i < size;i++){  
            element = list.get(i);  
            this.array.push(element);  
        }  
    }  
};  
//转化成数组  
List.prototype.toArray = function(){  
    return this.array;  
};  
//移除数组（根据下标）  
List.prototype.remove = function(index){  
    if(index >= this.size()){  
        index = this.size() - 1;  
    }  
    this.array.splice(index,1);  
};  
List.prototype.get = function(index){  
    return this.array[index];  
};  
//清除列表原色  
List.prototype.clear = function(){  
    this.init();  
};  
//是否为空列表  
List.prototype.isEmpty = function(){  
    return this.size() === 0;  
};  
//是否包含某个元素  
List.prototype.contains = function(element){  
    return this.indexOf(element) >= 0;  
};  
//判断列表的某个元素的第一个元素在列表的下标位置  
//-1则表示没有这个元素  
List.prototype.indexOf = function(element){  
    var size = this.size();  
    if(null === element){  
        for(var i = 0;i < size;i++){  
            if(this.array[i] === null){  
                return i;  
            }  
        }  
    }else{  
        for(var i = 0;i < size;i++){  
            if(this.array[i] === element){  
                return i;  
            }  
        }  
    }  
    return -1;  
};  
var isList = function(list){  
    return list instanceof List;  
};  





/*			        list.add("lynn");  
list.add("min");  
var list1 = new List();  
list1.add(1);  
list1.add("liu");  
list.addAll(list1);  
//list.clear();  
alert(list.contains("min1"));  
var size = list.size();  
for(var i = 0;i < size;i++){  
    //alert(list.get(i));  
}   */