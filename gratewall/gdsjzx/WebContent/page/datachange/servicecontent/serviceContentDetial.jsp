<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<style type="text/css">
.btn{
width:40px;
height:20px;
}
h1{color:Green;}
#listTLeft #listTRight #listTable #listCLeft #listCRight{width:100%;height:100%;}
.normal{ color:Blue; font-weight:bold; font-size:14px; background:white;
    width: 100%;height:100%; line-height:71%;
}
.tablev{height: 30px;margin: 0;padding: 0;border : 1px black solid;width: 98%;isplay: inline-block;border-collapse: collapse;vertical-align: top;}
.tabbles{display: block;text-align: right;width: 119px;background-color: #dceeef;border-right: 1px solid #908686;height: 30px;margin: 0;line-height: 30px;padding: 0;clear: left;float: left;color: #666;font-family: '瀹嬩綋';font-size: 12px; font-weight: bold;word-wrap: break-word;border-collapse: collapse;}
.tableg{border-collapse: collapse; display: block;font-size: 12px; text-align: left; line-height: 30px;}
th{
	border:1px black solid;
}

table.gridtable {
	font-family: verdana,arial,sans-serif;
	font-size:11px;
	color:#333333;
	border-width: 1px;
	border-color: #666666;
	border-collapse: collapse;
}
table.gridtable th {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color:#b6d5d7;
}
table.gridtable td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #ffffff;
}
.gridtable th {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color:#b6d5d7;
}
.gridtable td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #ffffff;
}


</style>
<script>



	var id;
	$(function(){
		id = jazz.util.getParameter("id");
		$.ajax({
			url:rootPath+'/dataservice/previewContent.do',
			type:"post",
			data:{id : id},
			success:function(data){
				var data1=data.result;
				var columncode=data.columncode;
				var columnvalue=data.columnname;
				var fieldCodes=columncode.split(",");
				var fields=columnvalue.split(",");
				var fieldMap={};
				var field="";
				$("#tab4").append("<table class='gridtable' style='width:98%;border:1px black solid'></table>");
				for(var k=0;k<fields.length;k++){
					if(k==0){
					field="<tr>";
					}
					field=field+"<th>"+fields[k]+"</th>";
					if(k==fields.length-1){
					field=field + "</tr>";
					}
					fieldMap[fieldCodes[k]]=fields[k];
				}
				$("#tab4 table").append(field);
				for(var l=0;l<data1.length;l++){
					var html="<tr>";
					for(var str in fieldMap){
						if(data1[l][str.toLowerCase()]!=undefined){
							html=html+"<td>"+data1[l][str.toLowerCase()] +"</td>";
						}
					}
						html=html+"</tr>";
					$("#tab4 table").append(html);
				}
			}
		});
		
		
	    
	});
	function back() {
		parent.winEdit.window("close");
	}

	
	
</script>
</head>
<body>

	<div name="tab_name" vtype="tabpanel" overflowtype="2" colspan="2" tabtitlewidth="130"  width="100%" height="290" 
			 orientation="top" id="tab_name">    
		  
		    <div>    
		          <div id="tab4" style="height: 100%">   
		       	  </div>    
		    </div>    
		</div>
		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
			<div id="btn2" name="btn2" vtype="button" text="关闭"
				icon="../../../style/default/images/fh.png" click="back()"></div>
		</div>
</body>
</html>