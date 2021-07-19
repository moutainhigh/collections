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
var id;
function initData(res){
	id = res.getAttr("id");
	loadData(id);
}
function loadData(id){
$('div[name="formpanel"]').formpanel('option','dataurl',rootPath+
		'/trsquery/queryyichangxxdata.do?id='+id);
$('div[name="formpanel"]').formpanel('option', 'readonly', true);
$('div[name="formpanel"]').formpanel('reload');
}
</script>
<style>
/*文字超出不换行*/
table td{word-break: keep-all;white-space:nowrap;}
.jazz-form-text-label{
	color: #000;
	font-weight: normal;
}
</style>
</head>
<body>
 	<div id="formpanel" name="formpanel" vtype="formpanel" showborder="false" titledisplay="false" height="100%" width="100%" layout="table" layoutconfig="{border:false,columnwidth:['50%','*']}">
        	<div id="remark" name="remark" vtype="textfield" label="列入异常名录原因" labelwidth="150" labelalign="right" width="90%" rowspan="1" colspan="2" readonly="true"
			 tooltip="" rule="" ></div>
		 	 
		 	 <div id="createtime" name="createtime" vtype="datefield" dataformat="YYYY-MM-DD" label="列入日期" labelwidth="150" labelalign="right" width="80%"  readonly="true"
			 tooltip="" rule=""></div>
		 	<div id="resoleunit" name="resoleunit" vtype="textfield" label="列入决定机关" labelwidth="150" labelalign="right" width="80%"  readonly="true"
		 	 tooltip="" rule=""></div>
		 	 
		 	  <div id="removetypecn" name="removetypecn" vtype="textfield" label="移出异常名录原因" labelwidth="150" labelalign="right" width="90%" rowspan="1" colspan="2" readonly="true"
			 tooltip="" rule=""></div>
			 
		 	 <div id="removetime" name="removetime" vtype="datefield" dataformat="YYYY-MM-DD" label="移出日期" labelwidth="150" labelalign="right" width="80%" readonly="true" 
			 tooltip="" rule="" ></div>
		 	<div id="removedept" name="removedept" vtype="textfield" label="移出决定机关" labelwidth="150" labelalign="right" width="80%" readonly="true"
		 	 tooltip="" rule=""></div>
			 
		 	 <div id="type" name="type" vtype="textfield" label="异常名录状态" labelwidth="150" labelalign="right" width="90%" readonly="true"
			 tooltip="" rule="" ></div>
			 
		 	<div id="publictime" name="publictime" vtype="datefield" dataformat="YYYY-MM-DD" label="公告时间" labelwidth="150" labelalign="right" width="80%" readonly="true"
		 	 tooltip="" rule=""></div>
		 	 
		 	 <!--  -<div id="remark" name="remark" vtype="textfield" label="描述" labelwidth="150" labelalign="right" width="90%" rowspan="1" colspan="2" readonly="true"
			 tooltip="" rule="" ></div>-->
    </div>

    
    <script type="text/javascript">
    	$(function(){
    		 $("tr:even").css({background:"#EFF6FA"});
    		 $("tr:odd").css({background:"#FBFDFD"});
    	})
    
    </script> 
</body>
</html>
