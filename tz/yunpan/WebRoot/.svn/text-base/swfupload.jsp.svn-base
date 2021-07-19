<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>文件上传组件调用</title>
	<!-- 引入 -->
	<link href="js/upload/upload.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="js/upload/tz_upload.js"></script>
  </head>
  <body>
  	<span id="upload"></span>
  	<div id="console"></div>
  	<script type="text/javascript">
  		//原理swfupload-二次开发
  		//一定要有上传类:upload.jsp
  		//  url:"/yunpan/upload.jsp",//上传的路径
		// 	limitSize:"1000 MB",/*MB*///上传文件吸纳子的大小
		// 	params:{},//传递给服务器的参数，key-value
		// 	fileTypes:"*.*",//过来文件选择的类型*.jpg;*.gif	
		// 	fileTypesDes:"所有文件",
		// 	postName:"file",
		// 	uploadLimit:100,
		// 	queueLimit:0,
		// 	multiple : true,//控制你选择文件是选择单个还是多个
		// 	btnId:"tmupload",//按钮
		// 	btnImage: "js/upload/btn_wj.jpg",//对应的上传图标
		// 	btnWidth:90,//图标的宽度
		// 	btnHeight:32,//图标的高度
		// 	callback:function(serverData,file){//上传成功的回调方法
		// 	}
		
	  		$.tmUpload({
	  			btnId:"upload",//上传组件附加的位置
	  			url:"/yunpan/upload.jsp",//文件上传上类的服务器路径
	  			limitSize:"100 MB",//限制文件上传的大小
	  			fileTypes:"*.jpg;*.gif;*.png",//限制文件上传的格式 *.*代表不限制
	  			params:{"username":"kekek","password":"123456"},//给服务器端传递参数 request.getParameter("username")
	  			multiple:true,//控制文件多选还是单选
	  			callback:function(serverData,file){//上传成功的回调方法
	  				var jsonData = eval("("+serverData+")");
	  				$("#console").append(jsonData.name+"<img src='"+jsonData.url+"' width='140' height='140'>大小是:"+jsonData.sizeString);
	  				
	  			}
	  		});
  		
  	</script>
  </body>
</html>
