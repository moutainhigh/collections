<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
	String contextpath = request.getContextPath();
%>

<script src="<%=contextpath%>/static/script/jazz.js" type="text/javascript"></script>
<script>
var pkOsbLog=null;
function initData(res){
	pkOsbLog=res.getAttr("pkOsbLog");
	
}
function html2Escape(sHtml) {
 return sHtml.replace(/[<>&"]/g,function(c){return {'<':'&lt;','>':'&gt;','&':'&amp;','"':'&quot;'}[c];});
}

$(function(){

	$("#formpanel").formpanel("option","readonly",true);
 	$("#formpanel .jazz-panel-content").css({"overflow":"none","overflow-y":"auto","overflow-x":"hidden"});


	
	
	var params = {
			url : rootPath+'/log/osblogDetailBypk.do?pkOsbLog='+pkOsbLog,
			callback : function(data, r, res) { 
				var data1 = data.data;			
				$.each(data1,function(name,value) {
						 if((name=="outboutparamms")||(name=="inboundpar")){
							 data.data[name]=html2Escape(value);
							 
						}
					});
				$('div[name="formpanel"]').formpanel('setValue',data);

			} 
		}
		$.DataAdapter.submit(params);

});



function goback(){

		window.top.$("#frame_maincontent").get(0).contentWindow.leave();
	
}






</script>
</head>
<body>
	   		<div id="formpanel" name="formpanel" vtype="formpanel" showborder="false" titledisplay="false" 
				height="100%" width="100%" layout="table" layoutconfig="{border:false,columnwidth:['50%','*']}">
				
				<div name='pkOsbLog' id="pkOsbLog" vtype="hiddenfield"></div>	
				<!-- <div name='pok' id="pok" vtype="textfield" label=""  width="90%" readonly="true" labelAlign="right"></div>					 
			 -->	 
				<div  name='serviceCode' vtype="textfield" label="服务代码" 
					labelAlign="right" width="90%"
					dataurl=''></div>				
									 
				<div name='requestUrl' id="requestUrl" vtype="textfield" label="调用地址"   labelAlign="right"
					 width="90%"	 ></div>
					 
				<div id='requestMeth' name='requestMeth' vtype="textfield" label="调用方法"
					labelAlign="right" width="90%"
				></div>	
					
				<div id='requestIp' name='requestIp' vtype="textfield" label="请求ip"
					labelAlign="right"  width="90%"
				></div>		
				<div id='starttime' name='starttime' vtype="textfield" label="开始时间"
					labelAlign="right"  width="90%"
				></div>		
				<div id='endtime' name='endtime' vtype="textfield" label="结束时间"
					labelAlign="right"  width="90%"
				></div>	
				<div id='issuccess' name='issuccess' vtype="comboxfield" label="是否成功"
					labelAlign="right"  width="90%" dataurl='[{"text":"成功","value":"Y"},{"text":"失败","value":"N"}]'
				></div>										
				<div name='inboundpar' colspan="2" rowspan="1"   
             		vtype="textareafield" label="请求参数" labelalign="right" width="95%" height="85">             	
             	</div> 			
					<div name='outboutparamms' colspan="2" rowspan="1"    
             		vtype="textareafield" label="返回参数" labelalign="right" width="95%" height="85">             	
             	</div> 										
				<div id="toolbar" name="toolbar" vtype="toolbar" align="center">
					<div id="btn2" name="btn2" vtype="button" text="返 回"
						icon="../../../style/default/images/fh.png" align="center" defaultview="1" click="goback()"></div>
				</div>
			</div>
  
  
</body>

</html>