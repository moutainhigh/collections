var catcode_setting={
	view: {
		selectedMulti: false
	},
	data:{
		simpleData:{
			enable:true
		}
	},
	check:{
		enable:false
	},
	callback: {
		onClick: zTreeOnClick
	}
};
var busicode_setting={
	view: {
		selectedMulti: false
	},
	data:{
		simpleData:{
			enable:true
		}
	},
	check:{
		enable:false
	},
	callback: {
		onClick: zTreeOnClick
	}
};

$.extend($.fn.validatebox.defaults.rules, {    
	number: {    
        validator: function(value, param){    
            return /^[0-9]+$/.test(value);   
        },    
        message: '只能为大于0的整数或不填'   
    }    
});
$(function(){
	loadTree();
	initCategoryDataGrid();
	if(window.name){
		$("#"+window.name,window.parent.document).parent().css("overflow","hidden");//隐藏父窗口滚动条
	}
});

//树节点点击事件
function zTreeOnClick(event, treeId, treeNode) {
    var categoryId=treeNode.id||"";
    if(categoryId=="0"){
    	reloadGridData(categoryId);
    	$("#category_form").form("clear");
		$("#save_cat_btn").hide();
    }else{
    	reloadGridData(categoryId);
    	$("#category_form").form("clear");
    	initForm(categoryId);
    	$("#save_cat_btn").show();
    }
    editFlag=false;
};

//获取类别类型  
function getCatType(){
	var tab = $('#tree_tab').tabs('getSelected');
	var index = $('#tree_tab').tabs('getTabIndex',tab);
	var catType=index==0?"1":"2";
	return catType;
}

//重新加载数据
function reloadGridData(categoryId){
	var catType=getCatType();
	var param={
				page:1,
				rows:5,
				categoryId:categoryId,
				catType: catType
			  };
	$('#categoryGrid').datagrid("load",param);
	 
}

//初始化分类列表
function initCategoryDataGrid(){
	$('#categoryGrid').datagrid({
	    height: 325,
	    width:600,
	    url: '../../knowledge/manageAction.do?method=querySubCategoryList&rows=5',
	    method: 'POST',
	    idField: 'categoryId',
	    striped: true,
	    title:"分类列表",
	    fitColumns: true,
	    singleSelect: false,
	    selectOnCheck:true,
	    checkOnSelect:false,
	    rownumbers: true,
	    pagination: true,
	    pageSize: 5,
        pageList: [5],
	    nowrap: true,
	    showFooter: true,
	    columns: [[
	        { field: 'ck',checkbox:true},
	        { field: 'categoryId', title: 'id', hidden:true},
	        { field: 'catCode', title: '编号', width:"38%", align: 'left',fixed:true},
	        { field: 'catName', title: '名称', width: "38%", align: 'left',fixed:true},
	        { field: 'option', title: '操作', width: 100, align: 'center',fixed:true,formatter:optformat}
	    ]],
	    toolbar: [
	      { 
	        text: '新增', 
	        iconCls: 'icon-add', 
	        handler: add_category 
	      },"-",
	      { 
	          text: '删除', 
	          iconCls: 'icon-remove', 
	          handler: deleteBatchCategory
	       }
	    ]
	});
}



//渲染操作列
function optformat(field,rowdata,rowindex){
	var categoryId=rowdata.categoryId;
	var html="<a href='javascript:void(0)' onclick=\"javascript:editCategory('"+categoryId+"');return false;\">编辑</a>&nbsp;&nbsp;"+
			 "<a href='javascript:void(0)' onclick=deleteCategory('"+categoryId+"')>删除</a>&nbsp;&nbsp;";
	return html;
}

//删除
function deleteCategory(categoryId){
	$.messager.confirm('确认','所选分类的子分类将一并删除，您确认想要删除记录吗？',function(confirm){    
	    if (confirm){    
	       $.ajax({
	    	   url:"../../knowledge/manageAction.do?method=deleteCategory",
	    	   type:"post",
	    	   data:{categoryId:categoryId},
	    	   success:function(data){
	    		   $.messager.alert("提示信息","删除成功。","info",function(){
	    			   $("#categoryGrid").datagrid("reload");
						reloadTree();
	    		   });
	    	   }
	       });
	    }    
	}); 
}

var editFlag=false; //编辑标记,是否点击了列表中操作列的编辑
//编辑
function editCategory(categoryId){
	initForm(categoryId);
	$("#save_cat_btn").show();
	editFlag=true;
}
//批量删除
function deleteBatchCategory(){
	var rows = $('#categoryGrid').datagrid("getChecked");
	if(rows.length==0){
		$.messager.alert("提示信息","请选择要删除的数据。","info");
		return;
	}
	$.messager.confirm('确认', '所选分类的子分类将一并删除，您确认想要删除记录吗？', function(confirm) {
		if (confirm) {
			var categoryIds = "";
			$.each(rows, function(i, row) {
				categoryIds += row.categoryId + ",";
			});
			categoryIds = categoryIds.substring(0, categoryIds.length - 1);
			$.ajax({
				url : "../../knowledge/manageAction.do?method=deleteBatchCategory",
				type : "post",
				data : {
					categoryIds : categoryIds
				},
				success : function(data) {
					$.messager.alert("提示信息", "删除成功。", "info", function() {
						$("#categoryGrid").datagrid("reload");
						$("#categoryGrid").datagrid("clearChecked");
						reloadTree();
					});
				}
			});
		}
	});
}

//加载树
function loadTree(){
	$.ajax({
		url:"../../knowledge/manageAction.do?method=queryCategoryList",
		type:"post",
		async:false,
		success:function(data){
			if(data){
				var root={id:"0",pId:"-1",name:"类别"};
				data.catCode.push(root);
				data.busiCode.push(root);
				initTree(data);
			}
		}
	});
}


//初始化树
function initTree(data){
	var catCodeList=data.catCode;
	var busiCodeList=data.busiCode;
	$.fn.zTree.init($("#zTree_categoryCode"), catcode_setting, catCodeList);
	$.fn.zTree.init($("#zTree_busiCode"), busicode_setting, busiCodeList);
	
	var zTree_catCode = $.fn.zTree.getZTreeObj("zTree_categoryCode");
	var zTree_busiCode = $.fn.zTree.getZTreeObj("zTree_busiCode");
	
	//展开节点
	zTree_catCode.expandAll(true);
	zTree_busiCode.expandAll(true);
}

//点击树节点 form表单显示节点相关信息
function initForm(categoryId){
	$.ajax({
		url:"../../knowledge/manageAction.do?method=queryCategory",
		type:"post",
		data:{categoryId:categoryId},
		success:function(data){
			if(data){
				$("#category_form").form("load",data);
			}
		}
	});
}

//保存
function save_category(){
	$('.validatebox-text').validatebox('enableValidation').validatebox('validate');//使表单验证生效
	if($("#category_form").form("validate")){
		var categoryId=$("#categoryId").val();
		var id=$("#categoryId").val();
		if(categoryId==""){
			categoryId=$("#parentId").val();
		}
		var cat=deal_form();
		$.ajax({
			url:"../../knowledge/manageAction.do?method=saveCategory",
			type:"post",
			data:cat,
			success:function(data){
				if(data&&data.error){
					$.messager.alert("错误信息",data.error,"error");
				}else{
					$.messager.alert("提示信息","保存成功。","info",function(){
						reloadTree();
						if(editFlag){
							$('#categoryGrid').datagrid("reload");
						}else{
							reloadGridData(categoryId);
						}
						if(!id){
							resetForm();
							$('.validatebox-text').validatebox('disableValidation');
						}
						
					});
				}
				
			}
		});
	}
}

//处理form表单,将表单数据以json格式返回
function deal_form(){
	var data=$("#category_form").serializeArray();
	var jsonData={};
	$.each(data,function(){
		jsonData[this.name]=encodeURI(this.value);
	});
	return jsonData;
}

//点击新增
function add_category(){
	var catType=getCatType();
	var treeObj ;
	if(catType=="1"){
		treeObj= $.fn.zTree.getZTreeObj("zTree_categoryCode");
	}else{
		treeObj= $.fn.zTree.getZTreeObj("zTree_busiCode");
	}
	var selectedNode = treeObj.getSelectedNodes()[0];
	if(!selectedNode){
		$.messager.alert("提示信息","请先选择一个节点。","info");
		$("#save_cat_btn").hide();
		return;
	}
	
	$("#category_form").form("clear");
	$("#parentId").val(selectedNode.id);
	$("#catType").val(catType);
	$("#save_cat_btn").show();
	editFlag=false;
}

//选中某个节点
function reloadTree(){
	var catType=getCatType();
	var treeObj ;
	if(catType=="1"){
		treeObj= $.fn.zTree.getZTreeObj("zTree_categoryCode");
	}else{
		treeObj= $.fn.zTree.getZTreeObj("zTree_busiCode");
	}
	var tempNode = treeObj.getSelectedNodes()[0];
	var categoryId=tempNode.id;
	var selectNode=treeObj.getNodeByParam("id", categoryId, null);
	loadTree();
	treeObj.selectNode(selectNode);
}

//重置表单
function resetForm(){
	$("#catCode").val("");
	$("#catName").val("");
	$("#catSort").val("");
	$("#catExp").val("");
}