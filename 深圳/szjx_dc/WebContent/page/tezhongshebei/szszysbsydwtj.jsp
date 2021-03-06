<%@ page contentType="text/html; charset=utf-8" language="java"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>

<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>
<style type="text/css">
</style>
<script type="text/javascript" charset="UTF-8">
	function reset() {
		$("#formpanel").formpanel('reset');
	}
	$(document).ready(
			function getcode() {
				$("div[name='sbjgs']").comboxfield("option","disabled",true);
				 $("div[name='xq']").comboxfield("option","change",getAdminbrancode);
				$.ajax({
					type : "post",
					url : rootPath + "/szysbsydwtj/code_value.do",
					data : "randan" + Math.random(),
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						var data = eval("(" + data + ")");
						var dataStr = data.data[0].data;
						for(var i in dataStr){ 
    						$("div[name='xq']").comboxfield('addOption', dataStr[i].text,  dataStr[i].value);
    					}
					}
				});
			});
	
	function getAdminbrancode(){
		
		$("div[name='sbjgs']").comboxfield("option","disabled",true);
		var text = $('div[name="xq"]').comboxfield('getValue');
		if(text!=""){
			$("div[name='sbjgs']").comboxfield("option","disabled",false);
			$("div[name='sbjgs']").comboxfield("option","dataurl","../../szysbsydwtj/adminbrancode.do?code="+text)
    		$("div[name='sbjgs']").comboxfield("reload");
		}
		
	}
	
	function queryUrl(obj) {
		var _this = $(obj);
		var aa = $("#formpanel").formpanel('getValue');
		var htmls ="";
		
		var tongJiLeiXing = aa.data.tjlx;
		var regCode = aa.data.xq;
		var adminbrancode = aa.data.sbjgs;
		var sheBeiLeiBie = aa.data.sblb;
		_this.find(".button-text").html("?????????");
		htmls +="<a href='"+rootPath+"/szysbsydwtj/downExcelSheBeiQuYu.do'>??????</a>";
		htmls +="<table border='1px' bordercolor='#000000' cellspacing='0px' style='border-collapse:collapse'>"+
				"<tr><td colspan='11'><h1>???????????????????????????????????????</h1></td></tr>";
		htmls+="<tr>"+
		"				<td>??????</td>"+
		"				<td>??????</td>"+
		"				<td>??????????????????</td>"+
		"				<td>?????????????????????</td>"+
		"				<td>??????</td>"+
		"				<td>????????????</td>"+
		"				<td>??????</td>"+
		"				<td>????????????</td>"+
		"				<td>????????????</td>"+
		"				<td>????????????</td>"+
		"				<td>??????</td>"+
		"</tr>";
				
		
	 	
		$.ajax({
			type : "post",
			url : rootPath + "/szysbsydwtj/tzsbtjpz.do",
			dataType : "text",
			async : false,
			cach : false,
			data:{"tongJiLeiXing":tongJiLeiXing,"regCode":regCode,"adminbrancode":adminbrancode,"sheBeiLeiBie":sheBeiLeiBie},
			success : function(data) {
				_this.find(".button-text").html("??????");
				var dataObj = eval("(" + data + ")");
				for (var i = 0; i < dataObj.length; i++) {
			 		htmls+="<tr>"+
					"				<td>"+dataObj[i].??????+"</td>"+
					"				<td>"+dataObj[i].??????+"</td>"+
					"				<td>"+dataObj[i].??????????????????+"</td>"+
					"				<td>"+dataObj[i].?????????????????????+"</td>"+
					"				<td>"+dataObj[i].??????+"</td>"+
					"				<td>"+dataObj[i].????????????+"</td>"+
					"				<td>"+dataObj[i].??????+"</td>"+
					"				<td>"+dataObj[i].????????????+"</td>"+
					"				<td>"+dataObj[i].????????????+"</td>"+
					"				<td>"+dataObj[i].????????????+"</td>"+
					"				<td>"+dataObj[i].??????+"</td>"+
					"			</tr>"; 
				}
			}
		});  
		
		
		htmls+="</table>";
		var newWim = open("szszysbsydwtj_down.jsp", "_blank");
		window.setTimeout(function() {
			_this.find(".button-text").html("??????");
			newWim.document.body.innerHTML = htmls;
		}, 200);

	}
</script>

</head>
<body>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel" titledisplay="true" width="100%" layout="table" showborder="false" layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="100%" title="??????????????????????????????">
		
		
		<div id="tjlx" name='tjlx' vtype="comboxfield" label="????????????" labelAlign="right" labelwidth='100px' width="310"></div>
		<div id="xq" name='xq' vtype="comboxfield" label="??????" labelAlign="right" labelwidth='100px' width="310" valuetip="???????????????????????????"	dataurl=""  ></div>
		<div id="sbjgs" name='sbjgs' vtype="comboxfield" label="???????????????"  labelAlign="right" labelwidth='100px' width="310" valuetip="??????????????????????????????" dataurl=""  ></div>
		<div id="sblb" name='sblb' vtype="comboxfield" label="????????????" labelAlign="right" labelwidth='100px' width="310"></div>

		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
			<div name="query_button" vtype="button" text="??????" icon="../query/queryssuo.png" onclick="queryUrl(this);"></div>
			<div name="reset_button" vtype="button" text="??????" icon="../query/queryssuo.png" click="reset();"></div>
		</div>
	</div>
</body>
</html>