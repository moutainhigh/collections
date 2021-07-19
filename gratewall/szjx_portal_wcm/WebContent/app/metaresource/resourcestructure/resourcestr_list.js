/**分类树显示和隐藏事件**/
$(document).ready(function(){
	$('#sepBar').bind("click",function(){
		var dom = $('#container');
		dom.toggleClass('shrinkNavTree');
	});
	$('#search_btn').bind("click",function(){
		$('#search_input').attr("name",$('#search_select').val());
		$('#search_form').submit();
	});
});

$(function() {
	$(".toolbar a:first").imgbutton();
	$(".toolbar a:eq(1)").imgbutton();
	$(".toolbar a:eq(2)").imgbutton();
	$(".toolbar a:eq(3)").imgbutton();
	$(".toolbar a:last").imgbutton();
});

function newResStr(){
	parent.$(document.body).tabpage({
			URL : 'resourcestructure/resourcestr_addedit_step01.jsp',
			params : {id:'resourcestr_addedit_step01',text:'新建资源结构',withClose:true},
			callback : null
		});
}
function importResStr(){
	$.CrashBoard({
		id : 'importResStr-crashboard',
		maskable : true,
		draggable : true,
		url : 'resourcestr_import.html',
		width : '500px',
		height : '200px',
		params : {},
		callback : function(){
			window.location.reload();
		}
	}).show();
}

function download(sUrl){
	var frm = $El('iframe4download');
	if(!frm) {
		frm = document.createElement('IFRAME');
		frm.id = "iframe4download";
		frm.style.display = 'none';
		document.body.appendChild(frm);
	}
	frm.src = sUrl;		
}

function exportResStr(){
	var metaViewIds = "";
	$("input[name='metaView']").each(function() {
		var temp = $(this);
		if(temp.attr('checked')){
				if(metaViewIds == "") {
					metaViewIds = temp.attr("objectId");
				} else {
					metaViewIds += "," + temp.attr("objectId");
				}
			}
	});
	if(metaViewIds == ""){
		alert("资源结构导出不能为空！");
		return;
	}
	var data = {objectIds: metaViewIds};
	//2. ajax export
	$.wcmAjax("wcm61_resourcestructure","exportResourceStructure",data,{dataType:"html"},function(data, textStatus){
		alert("导出进行中！！！");
		download("/wcm/file/read_file.jsp?DownName=" + data + "&FileName=" + data);
	},function(data) {
		alert("导出出错了" + data);
	});
}
function deleteResStr(){
	var metaViewIds = "";
	$("input[name='metaView']").each(function() {
		var temp = $(this);
		if(temp.attr('checked')){
			if(metaViewIds == "") {
				metaViewIds = temp.attr("objectId");
			} else {
				metaViewIds += "," + temp.attr("objectId");
			}
		}
	});
	if(metaViewIds == ""){
		alert("资源结构删除不能为空！");
		return;
	} else {
		confirm("确定要删除选中资源结构吗？");
	}
	var data = {objectIds: metaViewIds};
	//2. ajax delete
	var val = document.location.search;
	$.wcmAjax("wcm61_resourcestructure","delete",data,function(data, textStatus){
		window.location.href="resourcestr_list.jsp" + val;
	});
}
function editResStrclassinfo(){
	$.CrashBoard({
		id : 'editResStrclassinfo-crashboard',
		title : '资源结构分类维护',
		maskable : true,
		draggable : true,
		url : '../category/category_config.html?objectid=1&objectname=资源结构所属分类',
		width : '560px',
		height : '380px',
		params : {},
		callback : function(){
			//这是处理导入资源结构的url
			alert("这里是回调函数的链接url");
		}
	}).show();
}

$(document).ready(function(){
	$("#list-data").bind("dblclick",function(event){
		event = event || window.event;
		var element = event.srcElement||event.target;
		var dom = findThumbItem(element);
		if(dom==null)return;
		var nResourceStrId = dom.getAttribute("itemid");
		parent.$(document.body).tabpage({
			URL : 'metafield/metafield_list.jsp?viewId='+nResourceStrId,
			params : {id:'metafield_list',text:'资源结构字段',withClose:true},
			callback : null
		});
	})
	function findThumbItem(dom){
		while(dom!=null&&dom.tagName.toUpperCase()!='BODY'){
			if($(dom).hasClass('thumb')){
				return dom;
			}
			dom = dom.parentNode;
		}
		return null;
	}
});