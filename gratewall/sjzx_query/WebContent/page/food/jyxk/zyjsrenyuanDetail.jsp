<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<head>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script>

$(function(){
	var id = getUrlParam("id");
	var lictype = '13';
	$('div[name="formpanel"]').formpanel('option','dataurl',rootPath+
			'/food/formdetail.do?id='+id+"&lictype="+lictype);
	$('div[name="formpanel"]').formpanel('option', 'readonly', true);
	$('div[name="formpanel"]').formpanel('reload');
});

function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
	if (r != null)
		return unescape(r[2]);
	return null; // 返回参数值
}
/* function viewCerno(obj){
	
	//$('#cerno1').html(cerno);
	var _this = $(obj);
	if(cerno==""||cerno=="undefined"||cerno==undefined){
		_this.html("").addClass("none");
	}else{
		_this.html(cerno).addClass("none");	
	}
	$.ajax({
		url:rootPath+'/reg/showphone.do',
		data:{
			flag : "查询主要人员证照号码"
		},
		type:"post",
		dataType : 'json',
		success:function(data){

		}
	});
}
*/
</script>
<style>
/*文字超出不换行*/
table td{word-break: keep-all;white-space:nowrap;}
.jazz-form-text-label{
	color: #000;
	font-weight: normal;
}

.none{
	text-decoration: none;
	color: #000;
}
</style>
</head>
<body>
    <div id="formpanel" name="formpanel" vtype="formpanel" showborder="false" titledisplay="false" height="100%" width="100%" layout="table" layoutconfig="{border:false,columnwidth:['50%','*']}">
        <div name="name" vtype="textfield" label="姓名" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="staffType" vtype="textfield" label="人员类型" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="position" vtype="textfield" label="职务" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <!--  <div id="cerno1" name="cerno1" vtype="textfield" label="证件号码" maxlength="50" labelwidth="100" labelAlign="right" width="90%"><a href="javascript:void(0)" onclick="viewCerno(this)">查看(该操作会记录日志)</a></div> -->
        <div name="sex" vtype="textfield" label="性别" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div> 
        <div name="certificateCode" vtype="textfield" label="证书编码" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="credNo" vtype="textfield" label="证件号码" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="credType" vtype="textfield" label="证件类型" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="healthCertno" vtype="textfield" label="健康证编号" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="healthDept" vtype="textfield" label="发证单位" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="phone" vtype="textfield" label="移动电话" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="telphone" vtype="textfield" label="固定电话" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="licenseType" vtype="textfield" label="许可证类型" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="validityTo" vtype="datefield" dataformat="YYYY-MM-DD" label="有效期至" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="nation" vtype="textfield" label="民族" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="educationDegree" vtype="textfield" label="文化程度" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="major" vtype="textfield" label="专业" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="censusAddress" vtype="textfield" label="户籍登记地址" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div> 
    </div>
    
    <script type="text/javascript">
    	$(function(){
    		 $("tr:even").css({background:"#EFF6FA"});
    		 $("tr:odd").css({background:"#FBFDFD"});
    	})
    
    </script> 
</body>
</html>
