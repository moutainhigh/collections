<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<META http-equiv="X-UA-Compatible" content="IE=9" /> 
<title>index</title>
<!-- Stylesheet -->
<link rel="stylesheet" href="/script/jquery-plugin-boxy/css/common.css" type="text/css" />
<link rel="stylesheet" href="/script/jquery-plugin-boxy/css/boxy.css" type="text/css" />
<script type="text/javascript" src="/script/jquery-plugin-boxy/js/jquery-1.2.6.pack.js"></script>
<script type="text/javascript" src="/script/jquery-plugin-boxy/js/jquery.boxy.js"></script>
<script type="text/javascript">
$(function(){
	   //蛛网图
	   function zwt() {
		document.getElementById('frame_fenbu').src="zwt.jsp";
		document.getElementById('frame_fenbu').width="800px";
		document.getElementById('frame_fenbu').height="570px";
	    var box=new Boxy($("#showmodel"), {
	    modal: true,
	    title:"数据交换关系图",
	            closeText:"关闭"
	    });
	     box.resize(801,571);
	   };
	   $('#zwt').click(zwt);
	   
	   //分布图
	   function fenbu() {
		    document.getElementById('frame_fenbu').src="MLPie_L1.jsp";
		    document.getElementById('frame_fenbu').width="560px";
		    document.getElementById('frame_fenbu').height="555px";
		    var box=new Boxy($("#showmodel"), {
		    modal: true,
		    title:"共享服务分布图",
		            closeText:"关闭"
		    });
		     box.resize(565,556);
		};
		$('#fenbu').click(fenbu);
		
		//趋势图
		 function qst() {
			  document.getElementById('frame_fenbu').src="crossfilter.jsp";
			  document.getElementById('frame_fenbu').width="840px";
			  document.getElementById('frame_fenbu').height="570px";
			  var box=new Boxy($("#showmodel"), {
			  modal: true,
			  title:"共享服务趋势图",
			        closeText:"关闭"
			  });
			  box.resize(841,571);
			};
	     $('#qst').click(qst);
	     
	   //树形图
		 function tree() {
			  document.getElementById('frame_fenbu').src="tree.jsp";
			  document.getElementById('frame_fenbu').width="800px";
			  document.getElementById('frame_fenbu').height="540px";
			  var box=new Boxy($("#showmodel"), {
			  modal: true,
			  title:"共享资源查看",
			        closeText:"关闭"
			  });
			  box.resize(810,550);
			};
	     $('#tree').click(tree);
	   
	   //热力图
		 function rlt() {
			  document.getElementById('frame_fenbu').src="hotMap.jsp";
			  document.getElementById('frame_fenbu').width="770px";
			  document.getElementById('frame_fenbu').height="580px";
			  var box=new Boxy($("#showmodel"), {
			  modal: true,
			  title:"共享服务热力图",
			        closeText:"关闭"
			  });
			  box.resize(771,581);
			};
	     $('#rlt').click(rlt);
	   
	});
</script>


</head>
<body style="overflow: hidden;">
<input type="button" id='zwt' value="蛛网图" /><br/>
<input type="button" id='fenbu' value="分布图" /><br/>
<input type="button" id='qst' value="趋势图" /><br/>
<input type="button" id='rlt' value="热力图" /><br/>
<input type="button" id='tree' value="树形图" /><br/>

<Table style="width: 2000px;background: #000;height: 300px;" >

</Table>

<div id="showmodel" style="display: none;width: 95%;">
  <iframe id="frame_fenbu" width="800px" height="550px" src="zwt.jsp"  frameborder="0">
  </iframe>
</div>



</body>