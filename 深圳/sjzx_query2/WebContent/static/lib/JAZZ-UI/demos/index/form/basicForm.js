/**
 * 表单保存
 */
function save(){
	var aa = $("#formpanel").formpanel('getValue');
	alert(JSON.stringify(aa));
	var params = {
		url: '/JAZZ/demo/saveUser.do',
		components: ['formpanel'],
   		params: {id:2},
    	callback: function(){
    		alert("保存成功");
    	}	 
	};
	//$.DataAdapter.submit(params); 
}
/**
 * 表单重置
 */
function reset(){
	$("#formpanel").formpanel('reset');
}
/**
 * 下拉树选中的回调函数
 */
function chktree(event, treeId, treeNode){
	//alert(treeNode.name);
}

function changeradio(event,value){
	alert(value);
}
	
$(function(){
	/**
	 * 下拉框切换回调函数
	 * comboxfieldchange
	 */
	$('div[name="combox_name"]').on("comboxfieldchange",function(event, ui){ 
		//jazz.log(ui);
		alert('aaa');
	});
	$('#radiofieldId').radiofield('option','itemselect',changeradio);
	
	$('#mainSc').autocompletecomboxfield('option','itemselect',function(){
		alert('123');
	})
	//调整父页面iframe高度
    try{
        parent.setWinHeight(500);           
    }catch(e){
        jazz.log(e);
    }
    $('#message').textfield({
	    name: 'test',
		label: '备注',
		width: '500',
		labelwidth: '100'
	});
});