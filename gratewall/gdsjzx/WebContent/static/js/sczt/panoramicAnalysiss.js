var oChart = null;
var marketflag=null;
var marketpriPid=null;
var marketjbxxid = null;
var marketsourceflag=null;
var marketenttype=null;
var marketeconomicproperty=null;
var marketenttypeName = null;
var markettype=null;
var marketentname = null;
var marketopen = null;
$(function(){
	$("#tab1").loading();
	$("#tab1").loading("show");
	$('#content').hide();
	marketflag=getUrlParam("flag");
	marketpriPid=getUrlParam("priPid");
	marketjbxxid = getUrlParam("jbxxid");
	marketsourceflag=getUrlParam("sourceflag");
	marketenttype=getUrlParam("enttype");
	marketeconomicproperty=getUrlParam("economicproperty");
	marketenttypeName = getUrlParam("enttypeName");
	markettype=getUrlParam("type");
	marketentname = getUrlParam("entname");
	marketopen = getUrlParam("open");
	marketregno = getUrlParam("regno");
	var url = rootPath+'/page/reg/regDetail.jsp?flag='+marketflag+'&priPid='+marketpriPid+'&jbxxid='+marketjbxxid+'&sourceflag='+marketsourceflag+'&enttype='+marketenttype+'&economicproperty='+marketeconomicproperty+'&enttypeName='+marketenttypeName+'&type='+markettype+'&regno='+marketregno+'&entname='+marketentname+'&open='+marketopen;
	var a = {
		tabid: 'detail',	
		tabtitle: '市场主体基本信息',	
		tabindex: '2',
		//tabContentHeight:'5000',
		taburl: url
	};
	$.ajax({
		url: rootPath+'/analysis/panoramicAnalysiss.do',
		data:{
			priPid : marketpriPid,
			sourceflag : marketsourceflag,
			regno:marketregno,
			entname:marketentname
		},
	 	//async : false,
		dataType : 'json',
		type : "POST",
		success : function(data) {
			if(data.length>0){
				var dataAll=new Array(data.length);
				for(var j=0;j<data.length;j++){
					var dataDeal=new Array(2);
					var flag = "[";
					var spline="[";
					if(data[j].length>0){
						for(var i=0;i<data[j].length;i++){
							var params = JSON.stringify(data[j][i]); 
							if(data[j][i].type=='211' || data[j][i].type=='257'){//守重
								//if(i!=data.length-1){
									flag+="{x:"+data[j][i].operationtime+",align: 'left',marker:{radius: 7,fillColor: '#00FF00',lineColor: '#00FF00',states:{hover:{fillColor: '#00FF00',lineColor: '#00FF00'}}},title:'"+data[j][i].title+"<span style=\"display:none;\">zhongguo</span>',events:{click:function(e){getSignData(e,'"+params+"');}},text: '',name:'"+data[j][i].describe+"'},";
								//}
								spline+="{x:"+data[j][i].operationtime+",y:"+data[j][i].value+",name:'"+data[j][i].describe+"',marker:{radius: 7,fillColor: '#00FF00',lineColor: '#00FF00',states:{hover:{fillColor: '#00FF00',lineColor: '#00FF00'}}},events:{click:function(e){getDotData(e,'"+params+"');}}},";
							}else if(data[j][i].type=='210' || data[j][i].type=='209' || data[j][i].type=='214' || data[j][i].type=='255' || data[j][i].type=='259'){
								//if(i!=data.length-1){
									flag+="{x:"+data[j][i].operationtime+",align: 'left',fillColor: '#EE2C2C',marker:{radius: 7,fillColor: '#EE2C2C',lineColor: '#EE2C2C',states:{hover:{fillColor: '#EE2C2C',lineColor: '#EE2C2C'}}},title:'"+data[j][i].title+"<span style=\"display:none;\">zhongguo</span>',events:{click:function(e){getSignData(e,'"+params+"');}},text: '',name:'"+data[j][i].describe+"'},";
								//}
								spline+="{x:"+data[j][i].operationtime+",y:"+data[j][i].value+",name:'"+data[j][i].describe+"',marker:{radius: 7,fillColor: '#EE2C2C',lineColor: '#EE2C2C',states:{hover:{fillColor: '#EE2C2C',lineColor: '#EE2C2C'}}},events:{click:function(e){getDotData(e,'"+params+"');}}},";
							}else{
								//if(i!=data.length-1){
								    //填充提示弹出层的颜色
									flag+="{x:"+data[j][i].operationtime+",align: 'center',fillColor:'#fff',title:'"+data[j][i].title+"<span style=\"display:none;\">zhongguo</span>',events:{click:function(e){getSignData(e,'"+params+"');}},text: '',name:'"+data[j][i].describe+"'},";
								//}
								//导航指示器的圆点
								spline+="{x:"+data[j][i].operationtime+",y:"+data[j][i].value+",name:'"+data[j][i].describe+"',events:{click:function(e){getDotData(e,'"+params+"');}}},";
							}
						}
						flag = flag.substring(0,flag.length-1);
						spline = spline.substring(0,spline.length-1);
					}else{
						
					}
					flag +="]";
					spline +="]";
					dataDeal[0]=spline;
					dataDeal[1]=flag;
					dataAll[j]=dataDeal;
				}
				var xName=new Array(6);
				xName[0]='登记';
				xName[1]='其他行政处罚';
				xName[2]='广告';
				xName[3]='商标';
				xName[4]='监管';
				xName[5]='其他行政许可';
				var xColor=new Array(6);
				xColor[0]='#B400FF';
				xColor[1]='#FE0036';
				xColor[2]='#1BCAAC';
				xColor[3]='#FFC000';
				xColor[4]='#24BEF8';
				xColor[5]='#B8DA15';
				drawHighChart(marketentname+"(生命周期)",xName,dataAll,xColor);
			}
			$("#tab1").loading("hide");
		},
		error: function(e) { 
			$("#tab1").loading("hide");
		}
	});
	$("#tab_name").tabpanel("addTab",a);
	$('#tab_name').tabpanel('select',0); 
	
	$("#tab_name").tabpanel({
		change: function( event, index){ 
			if(index==1){
				$('#jazz_tabpanel_custom_detail_undefined').css('height','88.2%');
			}
		} 
	});
});
function drawHighChart(title,xName,dataAll,xColor){
	var h = $('#tab1').width()-15;
	var returnStr="{chart: {backgroundColor:'#EEF8FA', renderTo: 'container',type: 'spline',height: 500,width: "+h+",style:{'fontFamily':'微软雅黑'}},";
	//returnStr +="title: {text: '"+title+"'},";
	returnStr +="title: {style:{'font-family':'微软雅黑','color':'#666','font-size':'14px','font-weight':'bold'},text: '"+title+"'},";
	returnStr +="height: 500,";
	returnStr +="width: "+h+",";
	returnStr +="legend:{align:'left',verticalAlign:'top',layout:'horizontal',enabled:true,itemMarginTop:10},";
	returnStr +="exporting: {enabled: false},";
	returnStr +="credits:{enabled: false,href: 'http://www.msnui.tk/Admin',text: '微源网络科技'},";
	returnStr +="yAxis: [{title: {text: 'GOOGL'},visible:false,opposite:false}],";
	returnStr +="tooltip:{xDateFormat: '%Y-%m-%d %H:%M:%S',enabled:true},";
	returnStr +="xAxis:{type:'datetime',tickColor: 'green',tickLength: 10,tickWidth: 3,tickPosition: 'inside',dateTimeLabelFormats:{second: '%Y-%m-%d<br/>%H:%M:%S',minute: '%Y-%m-%d<br/>%H:%M',hour: '%Y-%m-%d<br/>%H:%M',day: '%Y<br/>%m-%d',week: '%Y<br/>%m-%d',month: '%Y-%m',year: '%Y'},title:{text:''},labels: {formatter: function() {return getXNa(this.value);},align: 'center'}},";
	returnStr +="rangeSelector: {selected: 6,buttons: [{type: 'month',count: 1,text: '1月'}, {type: 'month',count: 3,text: '3月'}, {type: 'month',count: 6,text: '6月'}, {type: 'year',count: 1,text: '1年'},{type: 'year',count: 3,text: '3年'}, {type: 'all',text: '所有'}],inputEnabled: true,inputDateFormat:'%Y-%m-%d',inputEditDateFormat: '%Y-%m-%d',inputDateParser:function(value){return value;}},";
	//returnStr +="scrollbar:{enabled:true},";
	returnStr +="scrollbar:{enabled:false},";
	returnStr +="navigator:{enabled:true,maskFill:'rgba(128,179,236,0.3)',xAxis:{tickWidth: 0,lineWidth: 0,gridLineWidth: 1,tickPixelInterval: 200,labels: {format: '{value:%Y.%m.%d}',align: 'left',style: {color: 'red'},x: 3,y: -4}},series:{type: 'areaspline',color: '#4572A7',fillOpacity: 0.05,dataGrouping: {smoothed: true},lineWidth: 1,marker: {enabled: false}}},";
	returnStr +="series:[";
	for(var i=0;i<dataAll.length;i++){
		if(dataAll[i].length>0){
			if(i==dataAll.length-1){
				returnStr +="{type: 'spline',lineWidth:5,color:'"+xColor[i]+"',tooltip:{pointFormat:''},allowPointSelect:true,name:'"+xName[i]+"',id: 'dataseries"+i+"',marker : {enabled : true,radius : 5},data: "+dataAll[i][0]+"},";//events:{click:function(e){getDotData();}},
				returnStr +="{type: 'flags',name: '标记图"+i+"',showInLegend: false,onSeries: 'dataseries"+i+"',shape: 'flag',data: "+dataAll[i][1]+"}";
			}else{	
				returnStr +="{type: 'spline',lineWidth:5,color:'"+xColor[i]+"',tooltip:{pointFormat:''},allowPointSelect:true,name:'"+xName[i]+"',id: 'dataseries"+i+"',marker : {enabled : true,radius : 5},data: "+dataAll[i][0]+"},";//events:{click:function(e){getDotData();}},
				//returnStr +="{type: 'spline',lineWidth:5,color:'#23BEF7',tooltip:{pointFormat:''},allowPointSelect:true,name:'"+xName[i]+"',id: 'dataseries"+i+"',marker : {enabled : true,radius : 5},data: "+dataAll[i][0]+"},";//events:{click:function(e){getDotData();}},
				//描边颜色
				returnStr +="{type: 'flags',name: '标记图"+i+"',color:'#9e9e9e',showInLegend: false,align:'center',width:62,height:20,y:-40,style:{fontFamily:'微软雅黑',color:'#666',fontSize:12},onSeries: 'dataseries"+i+"',shape: 'flag',data: "+dataAll[i][1]+"},";
			}
		}
	}
	returnStr +="]}";
	var dataObj=eval("("+returnStr+")");
	Highcharts.setOptions({
		lang: {
			months:['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
			shortMonths : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
			weekdays:['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],
			rangeSelectorZoom:'时间维度',
			rangeSelectorFrom: '从',
			rangeSelectorTo: '到'
		}
    });
	oChart = new Highcharts.StockChart(dataObj);
}
function getXNa(value){
	 var vDate=new Date(value);
	 return vDate.getFullYear()+'-'+(vDate.getMonth()+1)+'-'+vDate.getDate();
}
function getDotData(e,params){
	//params = '{"localtime":"2016-06-20","operationtime":1466438400000,"pripid":"03f01d71-d91a-465a-aa5a-6b72be8d28fa","sourceflag":"440000","type":"207","id":13934737,"timestamp":"2016-08-11 10:29:48","title":"年报","pkidname":"ANCHEID","pkidvalue":"5a4982fd-4070-4fe1-b37c-5dd120208955","tablename":"T_NDBG_QYBSJBXX","name":"","describe":"","value":499}';
	var obj = eval('(' + params + ')');//JSON.parse(params);  
	obj.marketflag=marketflag;
	obj.marketpriPid=marketpriPid;
	obj.marketsourceflag=marketsourceflag;
	obj.markettype=markettype;
	obj.marketeconomicproperty=marketeconomicproperty;
	tableLoad(obj);
}
function getSignData(e,params) {
	var obj =  eval('(' + params + ')');//JSON.parse(params); 
	obj.marketflag=marketflag;
	obj.marketpriPid=marketpriPid;
	obj.marketsourceflag=marketsourceflag;
	obj.markettype=markettype;
	obj.marketeconomicproperty=marketeconomicproperty;
	tableLoad(obj);
}
function tableLoad(obj){
	$("#tab1").loading("show");
	$.ajax({
		url:rootPath+'/analysis/panoramicAnalysisDetail.do',
		data:obj,
		type:"post",
		dataType : 'json',
		success:function(data){
			$('#content').show();
			$('#tabs tbody').empty();
			$('#tabs tbody').append(data.returnString);
			$('#tabs tbody tr:even').css("background","#EEF6F9");//奇数行
			$('#tabs tbody tr:odd').css("background","#FBFDFC");//偶数行
			$('#entname').empty();
			$('#entname').append("主体名称:"+marketentname);
			$('#regno').empty();
			$("#tab1").loading("hide");
		},
		error: function(e) { 
			$("#tab1").loading("hide");
		} 
	});
	
	
}
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(decodeURI(r[2])); return null; //返回参数值 
}
function getUrlParamName(name) {
    var reg = new RegExp(".*" + name + "\\s*=([^=&#]*)(?=&|#|).*","g"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.replace(reg, "$1");  //匹配目标参数
    if (r != null) return decodeURIComponent(r); return null; //返回参数值
}