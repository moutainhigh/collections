<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>综合查询</title>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.cookie.js" type="text/javascript"></script>
<style>
body{
	overflow-x: hidden;
	position:relative;
	height:424px;
	background-color: #F8F9FB
	}
	.jazz-grid-tables{
	 height: 100%;
    overflow-x: hidden;
    width: 100%;
	}
	.class_td1{
			width:15%;
			height:5%;
			padding:4px;
	}
	.class_td2{
			width:35%;
			height:5%;
	}
	
	
	.class_td4{
			width:18%;
			height:5%;
			padding:4px;
	}
	
	.class_td5{
			width:35%;
			height:5%;
	}
	
	.class_th77{
		height:0.1px;
		background-color:red;
	}
	.formpanel_table{
	display:none;
	background-color: #F8F9FB;
	width:100%;
	height:100%;
	margin:0px;
	padding:0px;
	position:relative; 
	bottom:0px;
	}
	
	.formpanel_table1{
	display:none;
	background-color: #F8F9FB;
	width:100%;
	height:100%;
	margin:0px;
	padding:0px;
	position:fixed; 
	bottom:17px;
	}
	
	.font_table{
	height:100%;
	width:100%;
	font-family:'微软雅黑', 'SimSun', 'helvetica', 'arial', 'sans-serif';
	}
	
	.font_title{
	font-family:'微软雅黑', 'SimSun', 'helvetica', 'arial', 'sans-serif';
	color: #327bb9;
    font-size: 14px;
    font-weight: bold;
    margin-left: 5px;
    text-transform: none;
	}
	
table.font_table {
	font-family: verdana,arial,sans-serif;
	font-size:11px;
	color:#333333;
	border-width: 1px;
	border-color: #a9c6c9;
	border-collapse: collapse;
}
table.font_table th {
	border-width: 1px;
	padding: 8px;
	font-size: 14px;
	border-style: solid;
	border-color: #a9c6c9;
}
table.font_table td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #a9c6c9;
}
.thleft{
 text-align:left;
 padding
 }
 .thright{
 text-align:right;
 padding
 }
#div1 {
    display:inline-block;
}
#estdate_end {
	position : relative;
	right : -65px;
}

#apprdate_end{
	position : relative;
	right : -65px;
}

.cleanButton{
	position : relative;
	right : -220px;
	font-family:'微软雅黑', 'SimSun', 'helvetica', 'arial', 'sans-serif';
	top : 13px;
}

.fontwidth{
	 display:inline-block;
 	width:110px;
 	/* font-size: 14px; */
 	font-family:'微软雅黑', 'SimSun', 'helvetica', 'arial', 'sans-serif';
}
.inputjuzhong{
	vertical-align:middle; 
}
.hangjianju { line-height:30px;}


</style>
<script src="<%=request.getContextPath()%>/static/script/comselect/com_main.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function(){
/*   $("#xuliehua").click(function(){
    $("#x1").text($("form").serialize());
  }); */
  
	getTcCurrency("regcapcur","id");//币种
	
	getTcCurrency("industryphy","id");//行业
	getTcCurrency("regorg","name");//登记机关

	//getOrgCode();
});
/**
 * 不同人员获取不同分局数据
 */
function getOrgCode(){
	$.ajax({
		   type: "POST",
		   url:rootPath+"/comselect/getCurrOrg.do",
		   dataType: 'json',
		   success: function(data) {
			   if(data.state=="-1"){
				   jazz.error(data.message);
			   }else{
				   if(data.istoporg=="N"){
		
					   $("select[name='regorg']").find("option[value='"+data.codeindexCode+"']").attr("selected",true);
					   
					   $("#dengjijiguan").css("display","none");
					 //  $("select[name='regorg']").attr("disabled", true);
					  // $("select[name='regorg']").find("option[value='440303']").attr("selected",true);
					//  alert( $("select[name='regorg']").find("option:selected").text());
				   }
			   }
		   },
		   error: function(error){
			   jazz.error("获取代码集出错！");
		
		   }
		});
}

function getTcCurrency(id,type){
	$.ajax({
		   type: "POST",
		   url:rootPath+"/comselect/code_value.do",
		   data : {'type':id},
		   dataType: 'json',
		   success: function(data) {
 			  var data= data.data[0]["data"];
 			  var str="";
		 		for (var i = 0; i < data.length; i++) {
		 			var text=data[i]["text"];
		 			var vale=data[i]["value"];
					if (vale.length>0){
						str =str+"<option value='"+vale+"'>"+text+"</option>";
					}else{
						str=str+"<option selected='selected'value=''>"+text+"</option>";
					}
			
				}
		 		str =str+"<option value='9999'>其他</option>";
		 		if(type=="id"){
		 			var id1="#"+id;
		 			 $(id1).append(str);
		 		}else{
		 			var  id2="select[name="+id+"]";
		 			$(id2).append(str);
		 		}
		 		
		 	  
		 		
		   },
		   error: function(error){
			   jazz.error("数据库连接超时，请重新刷新页面！");
		
		   }
		});
}
function adddiv(thi){
	var newdiv="<div>"+$(thi).parent().html()+"</div>";
	$(thi).parents().eq(1).append(newdiv);
	//alert($(thi).parents().eq(1).html());
}


function deldiv(thi){
	var size= $(thi).parent().siblings().size();
	
	if(size<1){
		jazz.info("该项已经为最后一个，不能删除");
	}else{
		$(thi).parent().remove();
	}
}

/* function showAdminBr(obj){
	    var opt = obj.options[obj.selectedIndex];
	    $(obj).parents("th").eq(0).next().next().find("select").eq(0).empty();
	    
	    var str="<option selected='selected' value=''>全部</option>";
	    $(obj).parents("th").eq(0).next().next().find("select").eq(0).append(str);
	  
    if(opt.value.length<1){
    	//后面不能点击
    	  $(obj).parents("th").eq(0).next().next().find("select").eq(0).attr("disabled","disabled");
    }else{
    	 $(obj).parents("th").eq(0).next().next().find("select").eq(0).removeAttr("disabled");
    	 setAdminbrancode("adminbrancode",$(obj).parents("th").eq(0).next().next().find("select").eq(0),opt.value);
    }
} */

function showAdminBr(obj,nextName){
	var opt = obj.options[obj.selectedIndex];
	var valueLength = opt.value.length;
	
	var nextDom =  $('#'+nextName);
	nextDom.empty();
	
	var str="<option selected='selected' value=''>全部</option>";
	nextDom.append(str);
	
	if(valueLength<1){
		nextDom.attr("disabled","disabled");
	}else{
		nextDom.removeAttr("disabled");
		setAdminbrancode(nextName,nextDom,opt.value);
	}
}


function setAdminbrancode(id,$obj,value){
	$.ajax({
		   type: "POST",
		   url:rootPath+"/comselect/code_value.do",
		   data : {'type':id,'parm':value},
		   dataType: 'json',
		   success: function(data) {
 			  var data= data.data[0]["data"];
 			  var str="";
		 		for (var i = 0; i < data.length; i++) {
		 			var text=data[i]["text"];
		 			var vale=data[i]["value"];
					if (vale.length>0){
						str =str+"<option value='"+vale+"'>"+text+"</option>";
					}else{
						str=str+"<option selected='selected'value=''>"+text+"</option>";
					}
			
				}
		 		$obj.append(str);	
		   },
		   error: function(error){
			   jazz.error("获取代码集出错！");
		   }
		});
}


function divappend4tr(thi){
	var $paren=$(thi).parents().eq(1);
/* 	var newdiv="<tr>"+$paren.prev().prev().prev().html()+"<tr>"+$paren.prev().prev().html()+"</tr>"+"<tr>"+$paren.prev().html()+"</tr>"; */
 	var newdiv="<tr>"+$paren.prev().prev().html()+"</tr>"+"<tr>"+$paren.prev().html()+"</tr>";
	$paren.before(newdiv);  
}

function reset(){
	$('form')[0].reset();
	//$("#formID").reset();
	var treeObj = $.fn.zTree.getZTreeObj('enttpeTree');
	treeObj.checkAllNodes(false);
	
	$("select[name='adminbrancode']").attr("disabled","disabled").empty();
	$("select[name='gongzuowangge']").attr("disabled","disabled").empty();
	$("select[name='danyuanwangge']").attr("disabled","disabled").empty();
}

function cleanDate(startDate,endDate){
	var startDate = $("div[name = '"+startDate+"']").datefield('setValue','');
	var endDate = $("div[name = '"+endDate+"']").datefield('setValue','');	
}

function getFormJson(form) {
	var o = {};
	var a = $(form).serializeArray();
	$.each(a, function () {
	if (o[this.name] !== undefined) {
	if (!o[this.name].push) {
	o[this.name] = [o[this.name]];
	}
	o[this.name].push(this.value || '');
	} else {
	o[this.name] = this.value || '';
	}
	});
	return o;
	}
	
function query(){

		var estdate_start = $("div[name = 'estdate_start']").datefield('getValue');
		var estdate_end = $("div[name = 'estdate_end']").datefield('getValue');
		var apprdate_start = $("div[name = 'apprdate_start']").datefield('getValue');
		var apprdate_end = $("div[name = 'apprdate_end']").datefield('getValue');
	
		var queryCondition = "";
		$("input[type='text']").each(function(){
			var title = $(this).parents("th").prev ().html();
			//queryCondition += title + $(this).val() + "  " ; //这里的value就是每一个input的value值~
			queryCondition += title  + $(this).val() + "  ";
		});
		
		$("select option:selected").each(function(){
			var title = $(this).parents("th").prev ().html();
			queryCondition += title + $(this).text() + "  ";
		});
		
		$("#coditions").val(queryCondition);
		
		
		
		
		dualtree();
 	    $("#formID").attr("action",rootPath+"/comselectqh/topange.do?estdate_start="+estdate_start+"&estdate_end="+estdate_end+"&apprdate_start="
 	    		+apprdate_start+"&apprdate_end="+apprdate_end);
  	  
  		$("#formID").submit(); 
	  deleteEntType(); 
}

	function openWin(){

/* 		   $("#postForm").html('');//防止元素重复

		   $("#postForm").append('<input type="hidden" name="id" value="12"/>');

		   $("#postForm").attr("target","newWin");

		   $("#postForm").attr("action",rootPath+"/comselect/topange.do");

		   window.open("about:blank","newWin","");//newWin 是上面form的target

		   $("#postForm").submit(); */
		   

/* 		   $("#formID").attr("target","newWin");

		   $("#formID").attr("action",rootPath+"/comselect/topange.do");

		   window.open("about:blank","newWin","");//newWin 是上面form的target

		   $("#formID").submit(); */

		   $("#formID").attr("action",rootPath+"/comselect/topange.do");
		   $("#formID").submit();	   

		}
	
	//校验成立日期中起始日期不能晚于截止日期
	function validate(Time1,Time2){
		var endTime = $("div[name = '"+Time1+"']").datefield('getValue');
		var startTime = $("div[name = '"+Time2+"']").datefield('getValue');
		
		if(!endTime){
			endTime = "9999-12-31";
		}
		
		if(!startTime){
			startTime = "0000-01-01";
		}
		
		var endTimeArray = endTime.split("-");
		var startTimeArray = startTime.split("-");
		
		if(endTimeArray[0]<startTimeArray[0]){
			return false;
		}else if(endTimeArray[0]==startTimeArray[0]){
			if(endTimeArray[1]<startTimeArray[1]){
				return false;
			}else if(endTimeArray[1]==startTimeArray[1]){
				if(endTimeArray[2]<startTimeArray[2]){
					return false;
				}else{
					return true;
				}
			}else{
				return true;
			}
		}else{
			return true;
		}
		return true;
	}
	
	$(function(){
		$("div[name = 'estdate_end']").datefield("option","change",function(){
			if(!validate("estdate_end","estdate_start")){
				jazz.warn("企业成立起始日期不能晚于结束日期!");
			}
		});
		$("div[name = 'estdate_start']").datefield("option","change",function(){
			if(!validate("estdate_end","estdate_start")){
				jazz.warn("企业成立起始日期不能晚于结束日期!");
			}
		});
		
		$("div[name = 'apprdate_start']").datefield("option","change",function(){
			if(!validate("apprdate_end","apprdate_start")){
				jazz.warn("企业核准起始日期不能晚于结束日期!");
			}
		});
		$("div[name = 'apprdate_end']").datefield("option","change",function(){
			if(!validate("apprdate_end","apprdate_start")){
				jazz.warn("企业核准起始日期不能晚于结束日期!");
			}
		});
	});
</script>

</head>
<body>
<style>
.jazz-field-comp-out{
	margin-top: -8px;
}

</style>
<div class="title_nav">当前位置：通用查询> 通用查询> <span>商事主体综合查询</span></div>
<form method="post" target="_blank"  id="formID" action="" accept-charset="utf-8" onsubmit="document.charset='utf-8';">
<input type="hidden" id="coditions" name="sky">
					<table id="datashow" class="font_table">
					<tr class="class_hg">
						<th  class="thleft" colspan="4">查询条件：</th>

					</tr>
					<tr>
						<th class="class_td4" style="padding:0;height: 20px;">成立日期：</th>
						<th class="class_td5 thleft" colspan="3" style="padding:0;height: 20px;">
						<!-- <input type="text" style="height:25px; width:300px" name="estdate_start" value="" /> -->
							<div name="estdate_start" vtype="datefield" label="成立起始日期"  labelwidth="120" labelalign="right" width="350" height = "45" tooltip="" 
									rule="" readonly=""></div>
						<!--	<div  id ="div1" style="width:30px;height:1px;margin:5px auto;padding:0px;background-color:black;overflow:hidden;"></div> -->
						<!-- <input type="text" style="height:25px; width:300px" name="estdate_end" value="" /> -->
							<div name="estdate_end" id = "estdate_end" vtype="datefield" label="成立结束日期"  labelwidth="120" labelalign="right" width="350" height = "45" tooltip="" 
									rule="" readonly=""></div>
						<!--	<input type = "button" class="cleanButton" value = "清空成立日期" style = "width:100px;height:30px" onclick = "cleanDate('estdate_start','estdate_end');"/> -->
						</th>
						<!-- 清空成立日期 -->
					</tr>
					<tr>
						<th class="class_td4" style="padding:0;height: 20px;">核准日期：</th>
						<th class="class_td5 thleft" colspan="3" style="padding:0;height: 20px;">
							<div name="apprdate_start" vtype="datefield" label="核准起始日期"  labelwidth="120" labelalign="right" width="350" height = "45" tooltip="" 
										rule="" readonly=""></div>
							<!-- <input type="text" style="height:25px; width:300px" name="apprdate_start" value="" /> -->
						<!--	<div  id ="div1" style="width:30px;height:1px;margin:5px auto;padding:0px;background-color:black;overflow:hidden;"></div> -->
							<!-- <input type="text" style="height:25px; width:300px" name="apprdate_end" value="" /> -->
							<div name="apprdate_end" id = "apprdate_end" vtype="datefield" label="核准结束日期"  labelwidth="120" labelalign="right" width="350" height = "45" tooltip="" 
									rule="" readonly=""></div>
						<!--	<input type = "button" class="cleanButton" value = "清空核准日期" style = "width:100px;height:30px" onclick = "cleanDate('apprdate_start','apprdate_end');"/> -->
						</th>
						
					</tr>
					<tr>
						<th class="class_td4" nowrap="nowrap">认缴注册资本总额(万元)：</th>
						<th class="class_td5 thleft" id="row5" style="padding-left: 4px;">
							&nbsp;&nbsp;&nbsp;<input type="text" style="height:22px; width:40%" name="reccap_start" value="" />
							至&nbsp;&nbsp;&nbsp;
							<input type="text" style="height:22px; width:38%" name="reccap_end" value="" />
						</th>
						<th class="class_td4">币种:</th>
						<th class="class_td5 thleft">
							<select name="regcapcur" id="regcapcur" style="height:25px; width:76%">
								 <option selected="selected"value="">全部</option> 
							</select>
						</th>
					</tr>
					<tr>
						<th>商事主体名称:
						</th>
						<th>
						<div>
								<select name="entname_term" class="inputjuzhong" style="height:25px; width:20%">
										<option selected="selected" value="dengyu">等于</option>
										<option value="include">包含</option>
								 	  	<option value="notinclude">不包含</option>
								</select>
								<input type="text" class="inputjuzhong" style="height:25px; width:54%" name="entname" value="" />
								<input type="button" class="inputjuzhong" value="增加" onclick= "adddiv(this);"/>
								<input type="button"  class="inputjuzhong" value="删除"  onclick= "deldiv(this);"/>
							</div>
						</th>
						
						<th class="class_td4">行业类别:</th>
						<th class="class_td5 thleft">
							<select name="industryphy" id="industryphy" style="height:25px; width:76%">
								 <option selected="selected"value="">全部</option> 
							</select>
						
						</th>
					</tr>
					<!-- <tr>
						<th class="class_td4">商事主体名称:</th>
						<th class="class_td5 thleft hangjianju" colspan="3"  >
							<div>
								<select name="entname_term" class="inputjuzhong" style="height:25px; width:10%">
										<option selected="selected" value="include">包含</option>
								 	  	<option value="notinclude">不包含</option>
								</select>
								<input type="text" class="inputjuzhong" style="height:25px; width:40%" name="entname" value="" />
								<input type="button" class="inputjuzhong" value="增加" onclick= "adddiv(this);"/>
								<input type="button"  class="inputjuzhong" value="删除"  onclick= "deldiv(this);"/>
							</div>
						</th>
					</tr> -->
					<tr>
						<th>地址:
						</th>
						<th>
							<div>
								<select name="dom_term"  class="inputjuzhong" style="height:25px; width:20%">
										<option selected="selected" value="include">包含</option>
								 	  	<option  value="notinclude">不包含</option>
								</select>
								<input type="text"  class="inputjuzhong" style="height:25px; width:54%" name="dom" value="" />
								<input type="button"  class="inputjuzhong" value="增加" onclick= "adddiv(this);"/>
								<input type="button"  class="inputjuzhong" value="删除"  onclick= "deldiv(this);"/>
							</div>
						</th>
						
						<th class="class_td4">经营范围:</th>
						<th class="class_td5 thleft hangjianju" >
							<div>
								<select name="opscope_term"  class="inputjuzhong" style="height:25px; width:20%">
										<option selected="selected" value="include">包含</option>
								 	  	<option  value="notinclude">不包含</option>
								</select>
								<input type="text"  class="inputjuzhong" style="height:25px; width:53%" name="opscope" value="" />
								<input type="button"  class="inputjuzhong" value="增加" onclick= "adddiv(this);"/>
								<input type="button"  class="inputjuzhong" value="删除"  onclick= "deldiv(this);"/>
							</div>
						</th> 
					</tr>
					<tr>
						<th class="class_td4">片区:</th>
						<th class="class_td5 thleft">
							&nbsp;
							<select name="addrflag" id="addrflag" style="height:25px; width:76%">
								 <option selected="selected" value="">全部</option>
								 <option value="0">蛇口</option>
								 <option value="1">前海</option>  
							</select>
						</th>
						<th class="class_td4">异常名录:</th>
						<th class="class_td5 thleft">
								<select name="yichangminglu" id = 'yichangminglu' style="height:25px; width:76%" >
										<option selected="selected" value="">请选择</option>
								 	  	<option  value="1">是</option>
								 	  	<option  value="0">否</option>
								</select>
						</th>
					</tr>
					<tr>
						<th class="class_td4">港资企业:</th>
						<th class="class_td5 thleft">
							&nbsp;
							<select name="gangzi" id="gangzi" style="height:25px; width:76%">
								 <option selected="selected" value="">请选择</option>
								 <option  value="1">是</option>
							     <option  value="0">否</option>  
							</select>
						</th>
					</tr>
					 <!-- <tr>
						<th class="class_td4">地址:</th>
						<th class="class_td5 thleft hangjianju" colspan="3"  >
							<div>
								<select name="dom_term"  class="inputjuzhong" style="height:25px; width:10%">
										<option selected="selected" value="include">包含</option>
								 	  	<option  value="notinclude">不包含</option>
								</select>
								<input type="text"  class="inputjuzhong" style="height:25px; width:40%" name="dom" value="" />
								<input type="button"  class="inputjuzhong" value="增加" onclick= "adddiv(this);"/>
								<input type="button"  class="inputjuzhong" value="删除"  onclick= "deldiv(this);"/>
							</div>
						</th>
					</tr> -->				

<!-- 					<tr>	
					//横线
						<th class="class_td5 thleft" colspan="4">
					
						<div  id ="div1" style="width:100%;height:1px;margin:1px auto;padding:0px;background-color:black;overflow:hidden;"></div> 
				
						</th>
					</tr> -->				
					<!-- <tr id="dengjijiguan" bordercolor="#FF0000" >
						<th class="class_td4">管辖区域 :</th>
						<th class="class_td5 thleft"   >
							<div>
								&nbsp;
								<select name="regorg" style="height:25px; width:76%" onchange="showAdminBr(this,'adminbrancode')">
										<option selected="selected" value="">全部</option>	
								</select>
							</div>
						</th>
				  		<th class="class_td4">所属监管所:</th>
						<th class="class_td5 thleft"  >
							<div>
								<select name="adminbrancode" id = 'adminbrancode' style="height:25px; width:80% " onchange="showAdminBr(this,'gongzuowangge')">
										<option selected="selected" value="">全部</option>
								 	  	
								</select>
							</div>
						</th>
					
					</tr>	
					<tr  bordercolor="#FF0000"  >
						<th class="class_td4">工作网格:</th>
						<th class="class_td5 thleft"   >
							<div>
								&nbsp;
								<select name="gongzuowangge" id = 'gongzuowangge' disabled="disabled"  style="height:25px; width:76%" onchange="showAdminBr(this,'danyuanwangge')">
										<option selected="selected" value="">全部</option>
								 	  	<option  value="">其他</option>
								</select>
							</div>
						</th>
						<th class="class_td4">单元网格:</th>
						<th class="class_td5 thleft"  >
							<div>
								<select name="danyuanwangge" id= 'danyuanwangge' disabled="disabled" style="height:25px; width:80% ">
										<option selected="selected" value="">全部</option>
								 	  	<option  value="001">其他</option>
								</select>
							</div>
						</th>
					</tr>
					<!--  <tr>
						<th class="class_td5 thright" colspan="4" >
							<input type="button"  value="添加多项" onclick="divappend4tr(this);"/>
						    &nbsp;
						    <input type="button"  value="删除多项" onclick="divdelete4tr(this);"/>
						    &nbsp;
						</th>
					</tr>
					-->
							
					<tr>
						<th class="class_td4">经济性质：</th>
						<th class="class_td5 thleft hangjianju" colspan="3" >
								<label>
								<input type="radio"  class="inputjuzhong"  name="enttype_radio" value="00"  checked="true"/> <font  color="red">主体大类</font> &nbsp;&nbsp;</label>
								<label> <input type="radio"  class="inputjuzhong" name="enttype_radio" value="01" /> <font   color="red">详细小类</font> <label>
								<p></p>
								<p></p>
							  <div id="enttypediv" class="hangjianju">
								<font class="fontwidth"> <input name="enttype"   class="inputjuzhong" type="checkbox" value="" /> 所有</font>
								<font class="fontwidth"> <input name="enttype"  disabled="disabled"   class="inputjuzhong" type="checkbox" value="01" /> 内资法人</font>
								<font class="fontwidth"> <input name="enttype"  disabled="disabled"   class="inputjuzhong" type="checkbox" value="02" /> 内资非法人</font>
								<font class="fontwidth"> <input name="enttype"  disabled="disabled"    class="inputjuzhong" type="checkbox" value="03" /> 外资法人</font>
								<font class="fontwidth"> <input name="enttype"    disabled="disabled"  class="inputjuzhong" type="checkbox" value="04" /> 外资非法人</font>		
								
								<label style="display:inline-block;">
								<font class="" > <input name="enttype"   disabled="disabled"   class="inputjuzhong" type="checkbox" value="05" /> 私营法人</font>		
								<font class="" > 
									<font color="red">(其中:</font> 
									<input name="enttype"    class="inputjuzhong" type="checkbox" disabled="disabled"   value="0501" /> 合伙法人
									<input name="enttype"    class="inputjuzhong" type="checkbox" disabled="disabled"  value="0502" /> 个独法人
									<font style=" display:inline-block;width:20px"color="red">)</font> 
								</font>	
								</label>
								
								<label style="display:inline-block;">
								<font class="" > <input name="enttype"   disabled="disabled"   class="inputjuzhong" type="checkbox" value="06" /> 私营非法人</font>		
								<font class="" > 
									<font color="red">(其中:</font> 
									<input name="enttype"    class="inputjuzhong" type="checkbox" disabled="disabled"   value="0601" /> 合伙非法人
									<input name="enttype"    class="inputjuzhong" type="checkbox" disabled="disabled"  value="0602" /> 个独非法人
									<font style=" display:inline-block;width:20px"color="red">)</font> 
								</font>	
								</label>																
								<font class="fontwidth"> <input name="enttype"   disabled="disabled"  class="inputjuzhong" type="checkbox" value="07" /> 个体</font>	
								<font class="fontwidth"> <input name="enttype"   disabled="disabled"   class="inputjuzhong" type="checkbox" value="08" /> 三来一补</font>		
							</div>
						</th>
					</tr>	
					<tr>
						<th class="class_td4">注册状态:</th>
						<th class="class_td5 thleft" colspan="3"  >
							<div class="hangjianju">
								<!-- <font class="fontwidth"> <input name="regstate"  checked="cheked" class="inputjuzhong" type="checkbox" value="" /> 全部</font> -->
								<font class="fontwidth" style="width:110px;"> <input name="regstate"  class="inputjuzhong" type="checkbox" checked="checked" value="1" /> 开业（存续）</font>
								<font class="fontwidth" style="width:80px;"> <input name="regstate"     class="inputjuzhong" type="checkbox" value="2" /> 吊销</font>
								<font class="fontwidth" style="width:80px;"> <input name="regstate"     class="inputjuzhong" type="checkbox" value="4" /> 注销</font>
								<font class="fontwidth" style="width:80px;"> <input name="regstate"     class="inputjuzhong" type="checkbox" value="6" /> 迁出</font>
								<font class="fontwidth" style="width:100px;"> <input name="regstate"     class="inputjuzhong" type="checkbox" value="8" /> 撤销登记</font>
								<font class="fontwidth" style="width:130px;"> <input name="regstate"     class="inputjuzhong" type="checkbox" value="7" /> 个体暂时吊销</font>
								<font class="fontwidth" style="width:80px;"> <input name="regstate"     class="inputjuzhong" type="checkbox" value="11" /> 停业</font>
								<font class="fontwidth" style="width:80px;"> <input name="regstate"      class="inputjuzhong" type="checkbox" value="9" /> 其他</font>		
						<!-- 		<font class="fontwidth"> <input name="regstate"     class="inputjuzhong" type="checkbox" value="03" /> 个体拟注销</font>
								<font class="fontwidth"> <input name="regstate"      class="inputjuzhong" type="checkbox" value="04" /> 撤销登记</font>		 -->							
							</div>
						</th>
					</tr>	
					<!--  <tr>
						<th class="class_td4">最新年度报告:</th>
						<th class="class_td5 thleft" colspan="3"  >
							<select name="year"  style="height:25px; width:20%">
									<option selected="selected" value="">全部…</option>
							 	  	 <option value="2015">2015</option>
									<option value="2014">2014</option>
									<option value="2013">2013</option>
							</select>
						
						</th>
					</tr>					
					-->		
				</table>
</form>
				<div id="div1" style="height:20px;"></div>
			<div id="toolbar" name="toolbar" vtype="toolbar" >			
		    	<div id="btn3" name="btn3" vtype="button" align="center" defaultview="1" text="开始查询"  click="query()"></div>
		    	<div id="btn4" name="btn4" vtype="button" align="center" defaultview="1" text="清除查询条件"  click="reset()"></div>
		    </div>
<!-- <button id="xuliehua">序列化表单值</button>
<div id="x1"></div> -->


</body>
</html>

