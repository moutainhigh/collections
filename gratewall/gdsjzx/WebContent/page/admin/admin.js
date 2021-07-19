var dataFunc;
var id;
//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return decodeURI(r[2]); return null; //返回参数值
}
//修改title
function updateTitle(name){
    $(document).attr("title",name);//修改title值
}
function setHeight(){
    var heightwindow= $(window).height();
    var widthwindow= $(window).width();
    $('#left').css('height',$("#column_id").height());//jazz-column-element
    //$('#contload').css('height',"100%");
    var t = $("#left").height();
    //$('.cont').css('height',t);
    //$('#contload').css('height',t);
}
$(document).ready(function() {
    setHeight();
    //获取url传入参数
    //var name=  getUrlParam("name");
    //修改title名称
    //updateTitle(name);
    //获取当前模块id，以便查询当前用户在此模块下功能
    id= getUrlParam("id");
    //读取用户当前模块权限、使用jquery节目初始化函数
    $.ajax({
            url : '/gdsjzx/admin/getFunTreeByUser.do',//
            async: true,
            data : {
                id:id
            },
            type : 'post',
            cache : false,
            dataType : 'json',
            success : function(datajson) {
                dataFunc= datajson;
                initMenus(dataFunc,id);
                //组织菜单
                //buildMenus();
                //初始化第一个菜单模块的子菜单////
                $(".navs").accordion({
                    accordion: true,
                    speed: 500,
            	    closedSign: '',
            		openedSign: ''
            	});
                initContent(".navs li a:first",0);
                onMourseTo();
            },
            error : function() {
                alert("页面异常！请重新加载");
            }
       });
});

//初始化鼠标事件
function onMourseTo(){
	$("ul li a").mouseover(function() {
		//console.info();
         //this.style.border = "solid 2px #6600FF";
		//$(this).children("ul").parent("li").find("span:first").html(opts.openedSign);
		$(this).find("span").css("background","url('/gdsjzx/page/sjbd/img/27.png')");
		var imgScr = $(this).find("img").attr("src");
		if(imgScr=="/gdsjzx/page/sjbd/img/15.png"){
			$(this).find("img").attr("src","/gdsjzx/page/sjbd/img/22.png");
		}else if(imgScr=="/gdsjzx/page/sjbd/img/17.png"){
			$(this).find("img").attr("src","/gdsjzx/page/sjbd/img/23.png");
		}else if(imgScr=="/gdsjzx/page/sjbd/img/18.png"){
			$(this).find("img").attr("src","/gdsjzx/page/sjbd/img/24.png");
		}else if(imgScr=="/gdsjzx/page/sjbd/img/19.png"){
			$(this).find("img").attr("src","/gdsjzx/page/sjbd/img/25.png");
		}else {
			$(this).find("img").attr("src","/gdsjzx/page/sjbd/img/26.png");
		}
    }).mouseout( function(){
    	$(this).find("span").css("background","url('/gdsjzx/page/sjbd/img/28.png')");
    	var imgScr = $(this).find("img").attr("src");
    	if(imgScr=="/gdsjzx/page/sjbd/img/22.png"){
			$(this).find("img").attr("src","/gdsjzx/page/sjbd/img/15.png");
		}else if(imgScr=="/gdsjzx/page/sjbd/img/23.png"){
			$(this).find("img").attr("src","/gdsjzx/page/sjbd/img/17.png");
		}else if(imgScr=="/gdsjzx/page/sjbd/img/24.png"){
			$(this).find("img").attr("src","/gdsjzx/page/sjbd/img/18.png");
		}else if(imgScr=="/gdsjzx/page/sjbd/img/25.png"){
			$(this).find("img").attr("src","/gdsjzx/page/sjbd/img/19.png");
		}else {
			$(this).find("img").attr("src","/gdsjzx/page/sjbd/img/20.png");
		}
    });
}

function subt(str,index){
	var arr=str.split('a:first');
	for(var i=0;i<index;i++){
		arr[0] += "ul li ";
	}
	return arr[0]+"a:first";
}
//初始化右边的内容
function initContent(title,index){
	index++;
	if($(title).parent().find("ul").length != 0){//往下查找
		if(!$(title).parent().find("ul").is(':visible')){
			$(title).parent().find("ul").show();
			initContent(subt(title,index));
		}
	}else{
		if($(title).attr('uri')!='#'){
			var rid = $(title).attr('code');
			//$(title).css("background-color","#D3CEB8");
			//$(title).css("color","#675C7C");
			window.open('/gdsjzx/'+$(title).attr('uri')+"?rid="+rid,'contload','');// testLoad为iframe的name属性
		}
	}
}

function getImgSrc(ram,cs){
	var imgSrc ="";
	if(ram%cs==0){
		imgSrc ="15";
	}else if(ram%cs==1){
		imgSrc ="17";
	}else if(ram%cs==2){
		imgSrc ="18";
	}else if(ram%cs==3){
		imgSrc ="19";
	}else {
		imgSrc ="20";
	}
	return imgSrc;
}
//初始化一级菜单
function initMenus(datas,v){
    var index=0;
    var ram = 0;
    var imgSrc ="";
    var navTreeMenuPic = 5;
    //var navTreeMenuPicOther = 5;
    for(var i=0;i<datas.length;i++){
        if(v==datas[i].pid){
        	//imgSrc =getImgSrc(ram,5);
        	if(navTreeMenuPic==6){
        		navTreeMenuPic++;
        	}
            if(datas[i].url==null||datas[i].url==' '){
               // $('<li><a code="'+datas[i].id+'" href="#" uri="#">'+datas[i].title+'</a>' ).appendTo($('.navs'));
                //初始化第一个二级菜单
                //otherMenus(dataFunc,datas[i].id);
                //$(otherMenus(dataFunc,datas[i].id)).appendTo($('.navs'));
                //$('</li>' ).appendTo($('.navs'));
            	//alert(otherMenus(dataFunc,datas[i].id));
                $('<li style="border-bottom:1px solid #D5E2E5"><a code="'+datas[i].id+'" href="javascript:void(0);" uri="#" title="'+datas[i].title+'" style="vertical-align:middle;"><img style="height:15px;width:15px;margin-top:3px;margin-right:10px" code="'+imgSrc+'" src="/gdsjzx/page/sjbd/img/1'+navTreeMenuPic+'.png" /><label style="top:-3;position: relative;">'+datas[i].title+'</label><span style="background:url(\'/gdsjzx/page/sjbd/img/28.png\');width:15px;height:7px;margin-top:8px;">&#12288;</span></a>'+otherMenus(dataFunc,datas[i].id)+'</li>').appendTo($('.navs'));
                navTreeMenuPic++;
            }else{
            	//navTreeMenuPicOther++;
            	//$('<li><a code="'+datas[i].id+'" href="javascript:void(0);" uri="'+datas[i].url+'" title="'+datas[i].title+'" style="vertical-align:middle;"><img style="height:15px;width:15px;margin-top:3px;" code="'+imgSrc+'" src="/gdsjzx/page/sjbd/img/1'+navTreeMenuPic+'.png" /><label style="top:-3;position: relative;">'+datas[i].title+'</label></a>' ).appendTo($('.navs'));
                //初始化报表
            	var html ="";
            	/*html+="<ul style='display:block'>" +
            			"<li><a code='' href='javascript:void(0);' uri="+datas[i].url+"?rid=nz"+" title='' style='vertical-align:middle;'><img style='height:15px;width:15px;margin-top:3px;margin-right:5px;' code='' src='/gdsjzx/page/sjbd/img/20.png'><label style='top:-3;position: relative;'>内资</label></a><li>" +
            			"<li><a code='' href='javascript:;' uri="+datas[i].url+"?rid=wz"+" title='' style='vertical-align:middle;'><img style='height:15px;width:15px;margin-top:3px;margin-right:5px;' code='' src='/gdsjzx/page/sjbd/img/20.png'><label style='top:-3;position: relative;'>外资</label></a><li>" +
            			"<li><a code='' href='javascript:;' uri="+datas[i].url+"?rid=gt"+" title='' style='vertical-align:middle;'><img style='height:15px;width:15px;margin-top:3px;margin-right:5px;' code='' src='/gdsjzx/page/sjbd/img/20.png'><label style='top:-3;position: relative;'>个体</label></a><li>" +
            			"<li><a code='' href='javascript:;' uri="+datas[i].url+"?rid=zh"+" title='' style='vertical-align:middle;'><img style='height:15px;width:15px;margin-top:3px;margin-right:5px;' code='' src='/gdsjzx/page/sjbd/img/20.png'><label style='top:-3;position: relative;'>综合</label></a><li>" +
            			"<li><a code='' href='javascript:;' uri="+datas[i].url+"?rid=nh"+" title='' style='vertical-align:middle;'><img style='height:15px;width:15px;margin-top:3px;margin-right:5px;' code='' src='/gdsjzx/page/sjbd/img/20.png'><label style='top:-3;position: relative;'>农合</label></a><li>" +
            			"</ul>";*/
            	html+="<ul style='display:block'>" +
    			"<li><a code='' href='javascript:void(0);' uri='/page/report/query_report.jsp' title='' style='vertical-align:middle;'><img style='height:15px;width:15px;margin-top:3px;margin-right:5px;' code='' src='/gdsjzx/page/sjbd/img/20.png'><label style='top:-3;position: relative;'>内资</label></a><li>" +
    			"<li><a code='' href='javascript:;' uri='/page/report/queryWz.jsp' title='' style='vertical-align:middle;'><img style='height:15px;width:15px;margin-top:3px;margin-right:5px;' code='' src='/gdsjzx/page/sjbd/img/20.png'><label style='top:-3;position: relative;'>外资</label></a><li>" +
    			"<li><a code='' href='javascript:;' uri='/page/report/queryGt.jsp' title='' style='vertical-align:middle;'><img style='height:15px;width:15px;margin-top:3px;margin-right:5px;' code='' src='/gdsjzx/page/sjbd/img/20.png'><label style='top:-3;position: relative;'>个体</label></a><li>" +
    			"<li><a code='' href='javascript:;' uri='/page/report/queryZh.jsp' title='' style='vertical-align:middle;'><img style='height:15px;width:15px;margin-top:3px;margin-right:5px;' code='' src='/gdsjzx/page/sjbd/img/20.png'><label style='top:-3;position: relative;'>综合</label></a><li>" +
    			"<li><a code='' href='javascript:;' uri='/page/report/queryNh.jsp' title='' style='vertical-align:middle;'><img style='height:15px;width:15px;margin-top:3px;margin-right:5px;' code='' src='/gdsjzx/page/sjbd/img/20.png'><label style='top:-3;position: relative;'>农合</label></a><li>" +
    			"</ul>";
            	 $('<li style="border-bottom:1px solid #D5E2E5"><a code="'+datas[i].id+'" href="javascript:void(0);" uri="#" title="'+datas[i].title+'" style="vertical-align:middle;"><img style="height:15px;width:15px;margin-top:3px;margin-right:10px" code="'+imgSrc+'" src="/gdsjzx/page/sjbd/img/1'+navTreeMenuPic+'.png" /><label style="top:-3;position: relative;">'+datas[i].title+'</label><span style="background:url(\'/gdsjzx/page/sjbd/img/28.png\');width:15px;height:7px;margin-top:8px;">&#12288;</span></a>'+html+'</li>').appendTo($('.navs'));
            }
            //ram++;
        }
    }
    
}
function otherMenus(datas,pid){
	var index=0;
	var str = "";
	var ram = parseInt(5*Math.random());
	var imgSrc ="";
	for (var i = 0; i < datas.length; i++) {
        var data = datas[i];
        var uri = data.url;
        var sfcd=data.sfcd;
        if(pid==datas[i].pid){
        	//imgSrc =getImgSrc(ram,5);
        	if(datas[i].url==null||datas[i].url==' '){
    			str=str+'<li><a code="'+datas[i].id+'" href="javascript:void(0);" uri="#" title="'+datas[i].title+'" style="vertical-align:middle;"><img style="height:15px;width:15px;margin-top:3px;" code="'+imgSrc+'" src="/gdsjzx/page/sjbd/img/18.png" /><label style="top:-3;position: relative;">'+datas[i].title+'</label><span style="background:url(\'/gdsjzx/page/sjbd/img/28.png\');width:15px;height:7px;margin-top:8px;">&#12288;</span></a>';
    		}else{
    			str=str+'<li><a code="'+datas[i].id+'" href="javascript:void(0);" uri="'+datas[i].url+'" title="'+datas[i].title+'" style="vertical-align:middle;"><img style="height:15px;width:15px;margin-top:3px;margin-right:5px;" code="'+imgSrc+'" src="/gdsjzx/page/sjbd/img/20.png" /><label style="top:-3;position: relative;">'+datas[i].title+'</label></a>' ;
    		}
    		str =str+otherMenus(dataFunc,datas[i].id);
    		str =str+"</li>";
    		ram++;
        }
    }
	if(str!=""){
		str="<ul>"+str+"</ul>";
	}
	return str;
}
