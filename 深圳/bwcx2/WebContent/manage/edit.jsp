<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;

	request.setAttribute("path", basePath);
%>

<link href="../statics/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="../statics/inner/uploader.css" rel="stylesheet" type="text/css" />
<script src="${path}/statics/js/jquery-V30.min.js"></script>
<script src="${path}/statics/js/tzcommon.js"></script>
<title></title>
<style>
</style>
</head>
<body>
	<div class="controls">
		<input type="hidden" id="Remark" name="Remark" value="PageAdmin网站管理系统">
		<button type="button" class="btn btn-primary ui-pagePost" onclick="openNew()">新建</button>
	</div>
	<div >
		 <table class="table table-bordered" id="documentLists" >
         <thead>
             <tr><th>序号</th><th>标题</th><th>文稿状态</th><th>操作</th></tr>
         </thead>
         <tbody>
            
         </tbody>

     </table>
	</div>


	<script>
		function getQueryString(name) {
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
			var r = window.location.search.substr(1).match(reg);
			if (r != null) {
				return unescape(r[2]);
			}
			;
			return null;
		}

		function loading() {
			var url = parent.document.getElementById("mainContainer").contentWindow.location.href;
			
			type = getQueryString("type");
			$.ajax({
				url : "../getDocsByChannel.do",
				type : "post",
				data : {
					cid : type,
					pageNo:1,
					pageSize:20,
					
				},
				beforeSend : function() {

				},
				success : function(data) {
					var tm = "";
					var list = data.list;
					if (list.length == 0) {
						tm = "<tr><td colspan='4'>当前无内容</td></tr>";
					} else {
						for (var i = 0; i < list.length; i++) {
							var id = list[i].docid;
							var title = list[i].title;
							var status = list[i].isPubOrSave;
							if(status=="0"){
								status = "草稿";
							}else if(status==null){
								status = "已编";
							}else{
								status = "已发布";
							}
							
							tm+=" <tr><td>"+(i+1)+"</td><td><a href='docEdit.jsp?channel="+type+"&docid="+id+"' target='_blank'>"+title+"</a></td><td>"+status+"</td><td><a href='javascript:;' onclick='del(this)' data-id='"+id+"'>删除</td></tr>";
						}
					}
					$("#documentLists tbody").append(tm);
				}

			});

		}
		loading();
		
		
		function openNew(){
			var url = parent.document.getElementById("mainContainer").contentWindow.location.href;
			var cid = getUuid();
			type = getQueryString("type");
			window.open("docEditNew.jsp?docid="+cid+"&type="+type,'')
		}
		
		
		function del(obj){
			var docid= $(obj).data("id");
			var x;
		    var r=confirm("是否要删除?");
		    if (r==true){
		    	$(obj).parents("tr").remove();
		        $.ajax({
		        	url:"../del.do",
		        	data:{docid:docid},
		        	type:"post",
		        	dataType:'json',
		        	success:function(data){
		        		alert("删除成功")
		        	}
		        });
		    }
			
		}
	</script>
</body>