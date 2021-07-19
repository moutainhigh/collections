<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>标记信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>


<script>

	//http://localhost:8088/sjbd/datacompre/returnHistoryExsists.do?entityNo=c7f9f0e9-013d-1000-e000-184e0a0e0115
	//case:1  bjxx
	//376e7dd0-011a-1000-e001-00c30a0c0115
  	 $(function(){
  		var entityNo=getUrlParam("entityNo");
  		var priPid=getUrlParam("priPid");
  		//获取传递过来的参数，进行初始化请求
  		if(entityNo!=null){
  			$("#jbxxGrid").gridpanel("hideColumn", "modfydate");
  			$("#jbxxGrid").gridpanel("showColumn", "approvedate");
  			//alert(entityNo);
  			//alert(priPid);
 			queryHistory(entityNo,priPid);
  		}
 	}); 
	function queryUrl() {
		$("#jbxxGrid").gridpanel("hideColumn", "approvedate");
			$("#jbxxGrid").gridpanel("showColumn", "modfydate");
		$('#jbxxGrid').gridpanel('option', 'dataurl',rootPath+
				'/reg/detail.do');
		$('#jbxxGrid').gridpanel('query', [ 'formpanel']);
	}
	
	function queryHistory(entityNo,priPid){
		$('#jbxxGrid').gridpanel('option', 'dataurl',rootPath+
		'/reg/detail.do?flag='+entityNo+'&priPid='+priPid);
		$('#jbxxGrid').gridpanel('query', [ 'formpanel']);
	}

	function reset() {
		$("#jbxxGrid").formpanel('reset');
	}
	
	 function getUrlParam(name) {
         var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
         var r = window.location.search.substr(1).match(reg);  //匹配目标参数
         if (r != null) return unescape(r[2]); return null; //返回参数值
     }

	function returnOrgNo(orgno){
		//路径  rootPath+'/datacompre/writeZzjgDm.do'
		 $.ajax( {    
		    url:rootPath+'/datacompre/writeZzjgDm.do',//
		    data:{    
		    	orgno:orgno  
		    },    
		    type:'post',  
		    cache:false,    
		    dataType:'json',    
		    success:function(data) {    
		        
		     },    
		     error : function() {    
		          // view("异常！");    
		          alert("异常！");    
		     }    
		});  
	} 
	
	
	
	//数据渲染函数
	   function renderdata(data){
				for(var i=0;i<data.length;i++){
					data[i]["@toolsopration"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="callcustom()">'+data[i]["name"]+'</a></div>';
					data[i]["custom"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="callcustom()">'+data[i]["name"]+'</a></div>';
				}
				return data;
		    }
		    
				 function closeshow(){
					 $("#formpanel").hide();
				 }      
				 


        /*

         * 添加事件处理程序

         * @param object object 要添加事件处理程序的元素

         * @param string type 事件名称，如click

         * @param function handler 事件处理程序，可以直接以匿名函数的形式给定，或者给一个已经定义的函数名。匿名函数方式给定的事件处理程序在IE6 IE7 IE8中可以移除，在标准浏览器中无法移除。

         * @param boolean remove 是否是移除的事件，本参数是为简化下面的removeEvent函数而写的，对添加事件处理程序不起任何作用

        */

        function addEvent(object,type,handler,remove){
                if(typeof object!='object'||typeof handler!='function') return;
                try{
                        object[remove?'removeEventListener':'addEventListener'](type,handler,false);
                }catch(e){

                        var xc='_'+type;

                        object[xc]=object[xc]||[];
	
                        if(remove){

                                var l=object[xc].length;

                                for(var i=0;i<l;i++){

                                        if(object[xc][i].toString()===handler.toString()) object[xc].splice(i,1);

                                }

                        }else{

                                var l=object[xc].length;

                                var exists=false;

                                for(var i=0;i<l;i++){                                                

                                        if(object[xc][i].toString()===handler.toString()) exists=true;

                                }

                                if(!exists) object[xc].push(handler);

                        }

                        object['on'+type]=function(){

                                var l=object[xc].length;

                                for(var i=0;i<l;i++){

                                        object[xc][i].apply(object,arguments);

                                }

                        }

                }

        }

        /*

         * 移除事件处理程序

        */

        function removeEvent(object,type,handler){

                addEvent(object,type,handler,true);

        }
		//延迟加载数据
		function timedata(){
			setTimeout("handler()",500);  
		}
      function handler(){
							$('.jazz-grid-row').dblclick( function () {
									var tds=$(this).children("td");
									for(var i=0;i<tds.length;i++){							
							 		//发送ajax请求，讲数据封装到frompanel里面，让其展示
							 		}
									$("#formpanel").show(); 
							 });
							 }	
	$(
	  function(){
	        addEvent(window,'load',timedata);
	        }
	)

$(document).ready(function() {
	
	$("#formpanel").formpanel('option', 'readonly', true);
		//alert("dd");
		//parent.document.all("includeframe1").style.height=document.body.scrollHeight; 
		//parent.document.all("includeframe1").style.width=document.body.scrollWidth; 
		//parent.document.all("includeframe").style.width=document.body.scrollHeight; 
		//$('#includeframe').height = document.body.scrollHeight;
		//parent.setWinHeight(document.documentElement.scrollHeight);
		//选取面板上的字号名称：
		//$('#formpanel div:first > div span:first').html("<a style='font-size:20px;'>字号名称："+"XXX公司</a>");
		
		//行间间距缩小
		$('.jazz-textfield-comp').css("height","10px");
		//详细信息的字体大小和宽度
		$('.jazz-field-comp-label').css({width:"130px","font-size":"12px"});
		
});

</script>
			 
				 
</head>
<body >
<div vtype="gridpanel" name="jbxxGrid" height="100" width="100%"  id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()"
				titledisplay="true" title="标记信息"   layout="fit" showborder="false" isshowselecthelper="false"  selecttype="2">
	<!-- 表头 -->
	<div vtype="gridcolumn" name="grid_column" width="100%">
		<div> 
		<!-- 标记信息 -->
				<div name='SCZTBJXXID' text="" textalign="center"  width="5%"></div>
				<div name='isAdCorp' text="是否广告企业" textalign="center" width="5%" ></div>
				<div name='isBrandPrintCorp' text="是否商标印制企业" textalign="left" width="5%"></div>
				<div name='isBroker' text="是否经纪人" textalign="left" width="5%"></div>
				<div name='isChangeEntityT' text="是否改制" textalign="left" width="5%"></div>
				<div name='isChangeFromGTH' text="标志该企业是否由个体户升级而来" textalign="center" width="5%" ></div>
				<div name='isCheckLicence' text="是否验照" textalign="center"  width="5%"></div>
				<div name='isInvestCompany' text="是否投资性公司" textalign="center"  width="5%"></div>
				<div name='isRuralBroker' text="是否农村经纪人" textalign="center"  width="5%"></div>
				<div name='isSecrecy' text="是否保密" textalign="center"  width="5%"></div>
				
				<div name='isSmallMicro' text=" 是否小微" textalign="center"  width="5%"></div>
				<div name='isStockMerger' text="是否股权并购" textalign="center"  width="5%"></div>
				<div name='PriPID' text="主体身份代码" textalign="center"  width="5%"></div>
				<div name='isUrban' text="城乡标志（是否城镇）" textalign="center"  width="5%"></div>
				<div name='perilIndustry' text="高危行业" textalign="center"  width="5%"></div>
	
				<div name='stockPurchase' text="是否A股并购" textalign="center"  width="5%"></div>
				
				<div name='TIMESTAMP' text="" textalign="center"  width="5%"></div>
				
	<!-- 隶属信息 -->			
				<div name='SCZTLSXXID' text="替换数据" textalign="center"  width="5%"></div>
				<div name='EntName' text="替换数据" textalign="center"  width="5%"></div>
				<div name='RegNO' text="替换数据" textalign="center"  width="5%"></div>
				<div name='PriPID' text="替换数据" textalign="center"  width="5%"></div>
				<div name='Addr' text="替换数据" textalign="center"  width="5%"></div>
				<div name='DomDistrict' text="替换数据" textalign="center"  width="5%"></div>
				<div name='RegOrg' text="替换数据" textalign="center"  width="5%"></div>
				<div name='OpScoAndForm' text="替换数据" textalign="center"  width="5%"></div>
				<div name='Country' text="替换数据" textalign="center"  width="5%"></div>
				<div name='entityCharacter' text="替换数据" textalign="center"  width="5%"></div>
				<div name='enterpriseType' text="替换数据" textalign="center"  width="5%"></div>
				<div name='foreignName' text="替换数据" textalign="center"  width="5%"></div>
				<div name='EstDate' text="替换数据" textalign="center"  width="5%"></div>
				<div name='subordinateRelation' text="替换数据" textalign="center"  width="5%"></div>
				<div name='isBranch' text="替换数据" textalign="center"  width="5%"></div>
				<div name='isForeign' text="替换数据" textalign="center"  width="5%"></div>
				<div name='ParComID' text="替换数据" textalign="center"  width="5%"></div>
				<div name='operBeginDate' text="替换数据" textalign="center"  width="5%"></div>
				<div name='operEndDate' text="替换数据" textalign="center"  width="5%"></div>
				<div name='PrilName' text="替换数据" textalign="center"  width="5%"></div>
				<div name='Tel' text="替换数据" textalign="center"  width="5%"></div>
				<div name='TIMESTAMP' text="替换数据" textalign="center"  width="5%"></div>
								
	</div>
	</div>
	<!-- 表格 -->
	<div vtype="gridtable" name="grid_table"></div>
	<!-- 卡片 -->
	<!-- <div vtype="gridcard" name="grid_card" width="205px" height="90px"></div> -->
	<!-- 分页 -->
	<!-- <div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div> -->
</div>
<!-- 标记信息 -->
  	<div style="display:none;" id="formpanel" name="formpanel" vtype="formpanel"
		titledisplay="true" width="100%" layout="table"  showborder="false" displayrows="1"
	    layoutconfig="{cols:2, columnwidth: ['50%','*'], border: false}" height="auto" title="详细信息" readonly="true">
			<div name='SCZTBJXXID' vtype="textfield" label="测试数据" labelAlign="right" labelwidth='100px'  width="410"></div>
			<div name='isAdCorp' vtype="textfield" label="测试数据" labelAlign="right" labelwidth='100px'  width="410"></div>
			<div name='isBrandPrintCor' vtype="textfield" label="测试数据" labelAlign="right" labelwidth='100px'  width="410"></div>
			<div name='isBroker' vtype="textfield" label="测试数据" labelAlign="right" labelwidth='100px'  width="410"></div>
			<div name='isChangeEntityT' vtype="textfield" label="测试数据" labelAlign="right" labelwidth='100px'  width="410"></div>
			<div name='isChangeFromGTH' vtype="textfield" label="测试数据" labelAlign="right" labelwidth='100px'  width="410"></div>
			<div name='isCheckLicence' vtype="textfield" label="测试数据" labelAlign="right" labelwidth='100px'  width="410"></div>
			<div name='isInvestCompany' vtype="textfield" label="测试数据" labelAlign="right" labelwidth='100px'  width="410"></div>
			<div name='isRuralBroker' vtype="textfield" label="测试数据" labelAlign="right" labelwidth='100px'  width="410"></div>
			<div name='isSecrecy' vtype="textfield" label="测试数据" labelAlign="right" labelwidth='100px'  width="410"></div>
			<div name='isSmallMicro' vtype="textfield" label="测试数据" labelAlign="right" labelwidth='100px'  width="410"></div>
			<div name='isStockMerger' vtype="textfield" label="测试数据" labelAlign="right" labelwidth='100px'  width="410"></div>
			<div name='PriPID' vtype="textfield" label="测试数据" labelAlign="right" labelwidth='100px'  width="410"></div>
			<div name='isUrban' vtype="textfield" label="测试数据" labelAlign="right" labelwidth='100px'  width="410"></div>
			<div name='perilIndustry' vtype="textfield" label="测试数据" labelAlign="right" labelwidth='100px'  width="410"></div>
			<div name='stockPurchase' vtype="textfield" label="测试数据" labelAlign="right" labelwidth='100px'  width="410"></div>
			<div name='TIMESTAMP' vtype="textfield" label="测试数据" labelAlign="right" labelwidth='100px'  width="410"></div>



<!-- 隶属信息-->

			<div name='SCZTLSXXID' vtype="textfield" label="替换数据" labelAlign="right" labelwidth='100px'  width="410"></div>
			<div name='EntName' vtype="textfield" label="替换数据" labelAlign="right" labelwidth='100px'  width="410"></div>
			<div name='RegNO' vtype="textfield" label="替换数据" labelAlign="right" labelwidth='100px'  width="410"></div>
			<div name='PriPID' vtype="textfield" label="替换数据" labelAlign="right" labelwidth='100px'  width="410"></div>
			<div name='Addr' vtype="textfield" label="替换数据" labelAlign="right" labelwidth='100px'  width="410"></div>
			<div name='DomDistrict' vtype="textfield" label="替换数据" labelAlign="right" labelwidth='100px'  width="410"></div>
			<div name='RegOrg' vtype="textfield" label="替换数据" labelAlign="right" labelwidth='100px'  width="410"></div>
			<div name='OpScoAndForm' vtype="textfield" label="替换数据" labelAlign="right" labelwidth='100px'  width="410"></div>
			<div name='Country' vtype="textfield" label="替换数据" labelAlign="right" labelwidth='100px'  width="410"></div>
			<div name='entityCharacter' vtype="textfield" label="替换数据" labelAlign="right" labelwidth='100px'  width="410"></div>
			<div name='enterpriseType' vtype="textfield" label="替换数据" labelAlign="right" labelwidth='100px'  width="410"></div>
			<div name='foreignName' vtype="textfield" label="替换数据" labelAlign="right" labelwidth='100px'  width="410"></div>
			<div name='EstDate' vtype="textfield" label="替换数据" labelAlign="right" labelwidth='100px'  width="410"></div>
			<div name='subordinateRelation' vtype="textfield" label="替换数据" labelAlign="right" labelwidth='100px'  width="410"></div>
			<div name='isBranch' vtype="textfield" label="替换数据" labelAlign="right" labelwidth='100px'  width="410"></div>
			<div name='isForeign' vtype="textfield" label="替换数据" labelAlign="right" labelwidth='100px'  width="410"></div>
			<div name='ParComID' vtype="textfield" label="替换数据" labelAlign="right" labelwidth='100px'  width="410"></div>
			<div name='operBeginDate' vtype="textfield" label="替换数据" labelAlign="right" labelwidth='100px'  width="410"></div>
			<div name='operEndDate' vtype="textfield" label="替换数据" labelAlign="right" labelwidth='100px'  width="410"></div>
			<div name='PrilName' vtype="textfield" label="替换数据" labelAlign="right" labelwidth='100px'  width="410"></div>
			<div name='Tel' vtype="textfield" label="替换数据" labelAlign="right" labelwidth='100px'  width="410"></div>
			<div name='TIMESTAMP' vtype="textfield" label="替换数据" labelAlign="right" labelwidth='100px'  width="410"></div>
						

		<div id="toolbar" name="toolbar" vtype="toolbar" >
			<div id="btn1" name="btn1" vtype="button" text="关闭" align="center" height="28" width="70" click="closeshow()"></div>
	    </div>
   </div>
  	

		
</body>
</html>