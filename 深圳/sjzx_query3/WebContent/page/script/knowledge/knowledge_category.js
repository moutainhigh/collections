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
        message: 'ֻ��Ϊ����0����������'   
    }    
});
$(function(){
	loadTree();
	initCategoryDataGrid();
	if(window.name){
		$("#"+window.name,window.parent.document).parent().css("overflow","hidden");//���ظ����ڹ�����
	}
});

//���ڵ����¼�
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

//��ȡ�������  
function getCatType(){
	var tab = $('#tree_tab').tabs('getSelected');
	var index = $('#tree_tab').tabs('getTabIndex',tab);
	var catType=index==0?"1":"2";
	return catType;
}

//���¼�������
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

//��ʼ�������б�
function initCategoryDataGrid(){
	$('#categoryGrid').datagrid({
	    height: 325,
	    width:600,
	    url: '../../knowledge/manageAction.do?method=querySubCategoryList&rows=5',
	    method: 'POST',
	    idField: 'categoryId',
	    striped: true,
	    title:"�����б�",
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
	        { field: 'catCode', title: '���', width:"38%", align: 'left',fixed:true},
	        { field: 'catName', title: '����', width: "38%", align: 'left',fixed:true},
	        { field: 'option', title: '����', width: 100, align: 'center',fixed:true,formatter:optformat}
	    ]],
	    toolbar: [
	      { 
	        text: '����', 
	        iconCls: 'icon-add', 
	        handler: add_category 
	      },"-",
	      { 
	          text: 'ɾ��', 
	          iconCls: 'icon-remove', 
	          handler: deleteBatchCategory
	       }
	    ]
	});
}



//��Ⱦ������
function optformat(field,rowdata,rowindex){
	var categoryId=rowdata.categoryId;
	var html="<a href='javascript:void(0)' onclick=\"javascript:editCategory('"+categoryId+"');return false;\">�༭</a>&nbsp;&nbsp;"+
			 "<a href='javascript:void(0)' onclick=deleteCategory('"+categoryId+"')>ɾ��</a>&nbsp;&nbsp;";
	return html;
}

//ɾ��
function deleteCategory(categoryId){
	$.messager.confirm('ȷ��','��ѡ������ӷ��ཫһ��ɾ������ȷ����Ҫɾ����¼��',function(confirm){    
	    if (confirm){    
	       $.ajax({
	    	   url:"../../knowledge/manageAction.do?method=deleteCategory",
	    	   type:"post",
	    	   data:{categoryId:categoryId},
	    	   success:function(data){
	    		   $.messager.alert("��ʾ��Ϣ","ɾ���ɹ���","info",function(){
	    			   $("#categoryGrid").datagrid("reload");
						reloadTree();
	    		   });
	    	   }
	       });
	    }    
	}); 
}

var editFlag=false; //�༭���,�Ƿ������б��в����еı༭
//�༭
function editCategory(categoryId){
	initForm(categoryId);
	$("#save_cat_btn").show();
	editFlag=true;
}
//����ɾ��
function deleteBatchCategory(){
	var rows = $('#categoryGrid').datagrid("getChecked");
	if(rows.length==0){
		$.messager.alert("��ʾ��Ϣ","��ѡ��Ҫɾ�������ݡ�","info");
		return;
	}
	$.messager.confirm('ȷ��', '��ѡ������ӷ��ཫһ��ɾ������ȷ����Ҫɾ����¼��', function(confirm) {
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
					$.messager.alert("��ʾ��Ϣ", "ɾ���ɹ���", "info", function() {
						$("#categoryGrid").datagrid("reload");
						$("#categoryGrid").datagrid("clearChecked");
						reloadTree();
					});
				}
			});
		}
	});
}

//������
function loadTree(){
	$.ajax({
		url:"../../knowledge/manageAction.do?method=queryCategoryList",
		type:"post",
		async:false,
		success:function(data){
			if(data){
				var root={id:"0",pId:"-1",name:"���"};
				data.catCode.push(root);
				data.busiCode.push(root);
				initTree(data);
			}
		}
	});
}


//��ʼ����
function initTree(data){
	var catCodeList=data.catCode;
	var busiCodeList=data.busiCode;
	$.fn.zTree.init($("#zTree_categoryCode"), catcode_setting, catCodeList);
	$.fn.zTree.init($("#zTree_busiCode"), busicode_setting, busiCodeList);
	
	var zTree_catCode = $.fn.zTree.getZTreeObj("zTree_categoryCode");
	var zTree_busiCode = $.fn.zTree.getZTreeObj("zTree_busiCode");
	
	//չ���ڵ�
	zTree_catCode.expandAll(true);
	zTree_busiCode.expandAll(true);
}

//������ڵ� form����ʾ�ڵ������Ϣ
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

//����
function save_category(){
	$('.validatebox-text').validatebox('enableValidation').validatebox('validate');//ʹ����֤��Ч
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
					$.messager.alert("������Ϣ",data.error,"error");
				}else{
					$.messager.alert("��ʾ��Ϣ","����ɹ���","info",function(){
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

//����form��,����������json��ʽ����
function deal_form(){
	var data=$("#category_form").serializeArray();
	var jsonData={};
	$.each(data,function(){
		jsonData[this.name]=encodeURI(this.value);
	});
	return jsonData;
}

//�������
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
		$.messager.alert("��ʾ��Ϣ","����ѡ��һ���ڵ㡣","info");
		$("#save_cat_btn").hide();
		return;
	}
	
	$("#category_form").form("clear");
	$("#parentId").val(selectedNode.id);
	$("#catType").val(catType);
	$("#save_cat_btn").show();
	editFlag=false;
}

//ѡ��ĳ���ڵ�
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

//���ñ�
function resetForm(){
	$("#catCode").val("");
	$("#catName").val("");
	$("#catSort").val("");
	$("#catExp").val("");
}