var tab1_col_all = new Array; // 存放数据项的列表数据
var tab1_col_all_div = "#tab1_col_all_div"; 
var tab1_col_selected_div = "#tab1_col_selected_div";



function getTopic(key,container){
	if (typeof (currentSystem) == "undefined") {
		currentSystem = key;
	}
	
	//得到所属主题 下面的表
	$.ajax({
		  type: "post",
		  url: "../../advQuery/getTable.do?topicId="+key,
		  async: false,
		  dataType:"json",
		  success: function(data){
			  oper_table_List = data.data[0].data;
			  //绑定数据
			  $(function(){
			 		 var opt_table={
						data:oper_table_List,
						onClick:function(event,key){
							getTable(key,'tbl_div');
						}
					};
			 		$('#tbl_div').dataSelector(opt_table);
			  });
		  }
	});
} 


function getTable(key,container){
	if (typeof (currentSystem) == "undefined") {
		currentSystem = key;
	}
	//根据key得到对应的表
	$.ajax({
		  type: "post",
		  url: "../../advQuery/getTable.do?topicId="+key,
		  async: false,
		  dataType:"json",
		  success: function(data){
			  select_table_info = data.data[0].data;
			  //绑定数据
			  $(function(){
			 		 var opt_table={
						data:select_table_info,
						onClick:function(event,key){
							getColumn(key,'checkTbl_div');
						}
					};
			 		$('#checkTbl_div').dataSelector(opt_table);
			  });
		  }
	});
} 


function getColumn(key, container){
	if (typeof (currentSystem) == "undefined") {
		currentSystem = key;
	}
	
	//根据key得到对应表中的所有列名
	$.ajax({
		  type: "post",
		  url: "../../advQuery/getColumn.do?tableId="+key,
		  async: false,
		  dataType:"json",
		  success: function(data){
			  selectColumn = data.data[0].data;
			  tab1_col_all.push(selectColumn);
			  //绑定数据
			  $(function(){
			 		 var opt_table_column_list={
						data:selectColumn,
						onClick:function(event,key){
							getColumnSelect(key,'tab1_col_all_div');
						}
					};
			 		$('#tab1_col_all_div').dataSelector(opt_table_column_list);
			  });
		  }
	});
}


function  getColumnSelect(key, container){
	if (typeof (currentSystem) == "undefined") {
		currentSystem = key;
	}
	
	
	col2table(key);
	var col_selected = $(tab1_col_all_div).dataSelector("getSelectedNodes");
	for (var x in col_selected) {
	  $(tab1_col_all_div).dataSelector("removeSelectedNodes", x);
	}
}




//从已选择字段表格删除一条数据到字段列表
function table2col(index){
	$(tab1_col_selected_div).tablet("removeRow", index);
}



//从字段列表选择字段到表格
function col2table(key) {
//	alert(key);
  var selected_col = null;
  var rowData = [];
  var tempArray  = tab1_col_all[0];
  for (var ii = 0; ii < tempArray.length; ii++) {
    if (tempArray[ii].key == key) {
      selected_col = tempArray[ii];
      rowData = [{
        "table": {
          "id": selected_col.share_table_id,
          "name_en": selected_col.table_name_en,
          "name_cn": selected_col.table_name_cn
        },
        "column": {
          "id": selected_col.key,
          "name_en": selected_col.dataitem_name_en,
          "name_cn": selected_col.title
        },
        "alias": "",
        "column_type": selected_col.colType,
        "column_length":"" +selected_col.colLenght
      }];
      break;
    }
  };
  console.log(rowData);
  $(tab1_col_selected_div).tablet("addData", rowData);
}

