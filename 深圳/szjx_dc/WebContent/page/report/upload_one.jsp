<%@ page contentType="text/html; charset=utf-8" language="java"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<title>单个上传</title>
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
	var myDate = new Date();
	$(document).ready(function (){
		/* 	 $.ajax({
					type : "post",
					url : rootPath + "/queryJieAn/getJieShouFangShiSelect.do",
					data : "id=" + Math.random(),
					dataType : "text",
					async : false,
					cach : false,
					success : function(data) {
						$("#jieshoufangshi").comboxfield('option',"dataurl",JSON.parse(data));
						$("#jieshoufangshi").comboxfield("reload");
						}
				});
			  */
			  getYear();
			  getReportType();
			//var a='[{"text":"2017", "value":"2017" },{"text":"2018", "value":"2018"},{"text":"2019", "value":"2019"},{"text":"2020", "value":"2020"}, {"text":"2021", "value":"2021"},{"text":"2022", "value":"2022"}]';
			
	});
	
	function uploadFunc(){
		$("#filePath").Attachment.getValue();
		$("#filePath").Attachment.getValue();
	}
	
	
	function getYear(){
		var year=myDate.getFullYear();
		var years="[";
		for (var i = 10; i >=0 ; i--) {
			 years +="{\"text\":\""+(year-i)+"\",\"value\":\""+(year-i)+"\"},";
		}
		for (var i = 1; i <= 10; i++) {
			if(i==10){
				years+="{\"text\":\""+(year+i)+"\",\"value\":\""+(year+i)+"\"}";
			}else {
			years+="{\"text\":\""+(year+i)+"\",\"value\":\""+(year+i)+"\"},";
			}
		}
		years+="]";
		 $("#reportYear").comboxfield('option',"dataurl",JSON.parse(years));
		 $("#reportYear").comboxfield("reload");
	}
	
	
	function getReportType(){
		var bgq='[{"text":"工商制式报表","value":"1"},{"text":"质检报表","value":"2"},{"text":"食药报表","value":"3"},{"text":"业务报表","value":"4"}]';
		 $("#reportType").comboxfield('option',"dataurl",JSON.parse(bgq));
		 $("#reportType").comboxfield("reload");
	}
	function closeWindow() {
		window.close();
	}
</script>


</head>
<body>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
		titledisplay="true" width="100%" layout="table" showborder="false"
		layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="100%"
		title="单个文件上传">
		<div id="reportName" name='reportName' vtype="textfield" label="报表名称"
			labelAlign="right" labelwidth='100px' width="310"></div>

		<div id="reportYear" name='reportYear' vtype="comboxfield"
			label="报表年份" labelAlign="right" labelwidth='100px' width="310"></div>

		<div id="bgq" name='bgq' vtype="comboxfield" label="报告期"
			labelAlign="right" dataurl='[{"text":"1月", "value":"01-03" },
					{"text":"2月", "value":"02-03" },
					{"text":"3月", "value":"03-03" },
					{"text":"一季度季报", "value":"03-02" },
					{"text":"4月", "value":"04-03" },
					{"text":"5月", "value":"05-03" },
					{"text":"6月", "value":"06-03" },
					{"text":"二季度季报", "value":"06-02" },
					{"text":"上半年年报", "value":"06-01" },
					{"text":"7月", "value":"07-03" },
					{"text":"8月", "value":"08-03" },
					{"text":"9月", "value":"09-03" },
					{"text":"三季度季报", "value":"09-02" },
					{"text":"10月", "value":"10-03" },
					{"text":"11月", "value":"11-03" },
					{"text":"12月", "value":"12-03" },
					{"text":"四季度季报", "value":"12-02" },
					{"text":"年报", "value":"12-01" }]' labelwidth='100px' width="310"></div>

		<div id="reportType" name='reportType' vtype="comboxfield"
			label="报表类型" labelAlign="right" labelwidth='100px' width="310"></div>

		<div id="regcode" name='regcode' vtype="comboxfield" label="行政区划"
			labelAlign="right" labelwidth='100px' dataurl='[ {"text":"深圳市", "value":"001" },
						{"text":"罗湖区", "value":"440303" },
						{"text":"福田区", "value":"440304" },
						{"text":"南山区", "value":"440305" },
						{"text":"宝安区", "value":"440306" },
						{"text":"龙岗区", "value":"440307" },
						{"text":"盐田区", "value":"440308" },
						{"text":"光明新区", "value":"440309" },
						{"text":"坪山新区", "value":"440310" },
						{"text":"龙华新区", "value":"440342" },
						{"text":"大鹏新区", "value":"440343" },
						{"text":"其他", "value":"440399" }]' width="310"></div>

		<div id="filePath" name="filePath" vtype="attachment" label="路径"
			labelAlign="right" labelwidth='100px' width="310"></div>

		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom"
			align="center">
			<div name="query_button" vtype="button" text="上传"
				icon="../query/queryssuo.png" onclick="queryUrl();"></div>
			<div name="reset_button" vtype="button" text="返回"
				icon="../query/queryssuo.png" click="closeWindow()"></div>
		</div>
	</div>
</body>
</html>