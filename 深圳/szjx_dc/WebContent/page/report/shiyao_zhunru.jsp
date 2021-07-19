<%@ page contentType="text/html; charset=utf-8" language="java"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<script
	src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js"
	type="text/javascript"></script>

<script
	src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js"
	type="text/javascript"></script>
<script type="text/javascript">
	function reset() {
		$("#formpanel").formpanel('reset');
	}
	function queryUrl(obj) {
		_this = $(obj);
		
		
		
		var aa = $("#formpanel").formpanel('getValue');
		var begintime = aa.data.begintime;
		var endtime = aa.data.endtime;
		//var regs=["食药准入处","罗湖局","福田局","南山局","宝安局","龙岗局","盐田局","光明局","坪山局","龙华局","大鹏局",'深汕监管局'];
		if (begintime.length != 0 && endtime.length != 0) {
			if (begintime > endtime) {
				alert("初始日期大于截止日期");
				return;
			}
			if (begintime == endtime) {
				alert("初始日期等于截止日期");
				return;
			}
		}
		if (begintime.length == 0 || endtime.length == 0) {
			alert("日期为空");
			return;
		}
		
		
		var htmls = '<div>'
				+ '<a href="#" id=\'pluginurl\' onclick="downReport(' + '\''
				+ begintime
				+ '\''
				+','
				+ '\''
				+ endtime
				+ '\''
				+ ')" > 下     载</a>'
				+ '</div>'
				+ '<div>&nbsp;</div>'
				+"<div style='width:1100px;margin:0 auto;'>\n" +
				"\n" + 
				"<table id='table1' >\n" + 
				"<tr><td colspan='5' style='text-align:center;    font-size: 25px;'>食药准入业务工作完成情况报表（食品）</td></tr>"+
				"<tr><td colspan='5' style='text-align:center;    font-size: 18px;'>食品生产许可</td></tr>"+
				"<tr><td style='text-align:center'>辖区</td>"+
				"<td style='text-align:center'>单位</td>"+
				"<td></td>"+
				"<td >受理数(件)</td>"+
				"<td >办理数(件)</td>"+
				"</tr>";
				$.ajax({
					type : "post",
					url : rootPath + "/queryFoodstuff/queryShiPinZhunRu.do",
					data : "begintime=" + begintime+"&endtime="+endtime
							+ "&timess=" + new Date() ,
					dataType : "json",
					async : false,
					cach : false,
					beforeSend:function(){
						
					},
					success : function(data) {
						//var dataObj = eval("(" + data + ")");
						
						var dataObj = data.shengChangList;
						
						 for (var i = 0;i<dataObj.length; i++) {
							 if(dataObj[i].nyFlag=="当月数"){
								 htmls+="<tr> ";
								 htmls+="<td style='text-align:center' >"+dataObj[i].辖区+"</td>" + 
									"<td>"+dataObj[i].单位+"</td>" + 
									"  <td>当月数</td>" + 
									"  <td >"+dataObj[i].食品生产受理+"</td>" + 
									"  <td>"+dataObj[i].食品生产办理+"</td>" ;
							      htmls+=" </tr>";
							}else{
								 htmls+="<tr> ";
								 htmls+="<td  style='text-align:center' >"+dataObj[i].辖区+"</td>" + 
									"<td>"+dataObj[i].单位+"</td>" + 
									"  <td>本年度累计</td>" + 
									"  <td >"+dataObj[i].食品生产受理+"</td>" + 
									"  <td>"+dataObj[i].食品生产办理+"</td>" ;
							      htmls+=" </tr>";
							} 
							 
							 
						 }
						 
						htmls +="</table>";
						 
						 
						
						//htmls += "<div style='height:40px;'></div>";
						htmls += "<table id='table2' >\n" ;
						htmls += "<tr><td colspan='5' style='height:30px;'>&nbsp;</td></tr>\n" ;
						htmls += "<tr><td colspan='5' style='height:30px;'>&nbsp;&nbsp;</td></tr>\n" ;
						htmls += "<tr><td colspan='5' style='height:30px;'>&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>\n" ;
						htmls += "<tr><td colspan='5' style='text-align:center ;   font-size: 18px;'>食品经营许可（不含原餐饮许可数据）</td></tr>"+
						"<tr><td style='text-align:center;'>辖区</td>"+
						"<td style='text-align:center'>单位</td>"+
						"<td></td>"+
						"<td >受理数(件)</td>"+
						"<td >办理数(件)</td>"+
						"</tr>";
					
						dataObj = data.jingYingList;
						 for (var i = 0;i<dataObj.length; i++) {
							 if(dataObj[i].nyFlag=="当月数"){
								 htmls+="<tr> ";
								 htmls+="<td  style='text-align:center' >"+dataObj[i].辖区+"</td>" + 
									"<td>"+dataObj[i].单位+"</td>" + 
									"  <td>当月数</td>" + 
									"  <td >"+dataObj[i].食品经营受理+"</td>" + 
									"  <td>"+dataObj[i].食品经营办理+"</td>" ;
							      htmls+=" </tr>";
							}else{
								 htmls+="<tr> ";
								 htmls+="<td  style='text-align:center' >"+dataObj[i].辖区+"</td>" + 
									"<td>"+dataObj[i].单位+"</td>" + 
									"  <td>本年度累计</td>" + 
									"  <td >"+dataObj[i].食品经营受理+"</td>" + 
									"  <td>"+dataObj[i].食品经营办理+"</td>" ;
							      htmls+=" </tr>";
							} 
							 
							 
						 }
						
						htmls += '</table>' + '</div>';
					
				  }
				
				
				});
				
				var newWim = open("shiyao_zhunru_down.jsp", "_blank");
				window.setTimeout(function() {
					newWim.document.body.innerHTML = htmls;
				}, 1000);	
				
	}
</script>
</head>
<body>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
		titledisplay="true" width="100%" layout="table" showborder="false"
		layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="100%"
		title="食药准入业务工作完成情况报表（食品）  统计条件">
		<div id="begintime" name='begintime' name='begintime' vtype="datefield"
			label="开始日期" labelAlign="right" labelwidth='100px' width="310"></div>
		<div id="endtime" name='endtime' name='endtime' vtype="datefield"
			label="截止日期" labelAlign="right" labelwidth='100px' width="310"></div>
		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom"
			align="center">
			<div name="query_button" vtype="button" text="查询"
				icon="../query/queryssuo.png" onclick="queryUrl(this);"></div>
			<div name="reset_button" vtype="button" text="重置"
				icon="../query/queryssuo.png" click="reset();"></div>
		</div>
	</div>
</body>
</html>