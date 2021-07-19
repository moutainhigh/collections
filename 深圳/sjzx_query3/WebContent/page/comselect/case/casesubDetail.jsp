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
	$('div[name="formpanel"]').formpanel('option','dataurl',rootPath+
			'/caseShow/formdetail.do?id='+id);
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
        <div name="goodsname" vtype="textfield" label="物资名称" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="specifications" vtype="textfield" label="规格" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="meaunit" vtype="textfield" label="物资单位" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="count" vtype="textfield" label="数量" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div> 
        <div name="abolish" vtype="textfield" label="是否有效" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="abolishdate" vtype="datefield" dataformat="YYYY-MM-DD" label="删除时间" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="abolisher" vtype="textfield" label="删除人" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="manu" vtype="textfield" label="生产厂家" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="pacconstocon" vtype="textfield" label="包装" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="itemsetcoutype" vtype="textfield" label="处理结果" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="detainnumber" vtype="textfield" label="物品扣留单号" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
        <div name="trademark" vtype="textfield" label="商标名称" maxlength="50" labelwidth="100" labelAlign="right" width="90%"></div>
      	<div name="remarks" vtype="textfield" label="备注" maxlength="50" labelwidth="100" labelAlign="right" width="90%" colspan="2" rowspan="1"></div>
      	
    </div>
    
    <script type="text/javascript">
    	$(function(){
    		 $("tr:even").css({background:"#EFF6FA"});
    		 $("tr:odd").css({background:"#FBFDFD"});
    	})
    
    </script> 
</body>
</html>
