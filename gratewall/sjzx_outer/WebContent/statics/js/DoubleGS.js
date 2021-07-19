
$.extend({
    getUrlVars: function () {
        var vars = [], hash;
        var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
        for (var i = 0; i < hashes.length; i++) {
            hash = hashes[i].split('=');
            vars.push(hash[0]);
            vars[hash[0]] = hash[1];
        }
        return vars;
    },
    getUrlVar: function (name) {
        return $.getUrlVars()[name];
    }
});
function SetHeight(num) {
    var newHeight = $(window).height() - num ; // page_main
    alert(newHeight);
    $(".d_gs_box").css("min-height", newHeight);
}
var ajaxObj = null;
//截取字符长度（双公示首页和列表页）
function Size_Title(title, name, lengthStr) {
   /* if (title.length + name.length > lengthStr)
        return title.substring(0, lengthStr - name.length) + "…";
    else
        return title ;*/

}

//切换标签
$('.d_gs_nav li').live("click", function () {
    setTabs($(this));
});
///设置标签页
function setTabs(obj) {
    if (!$(obj).hasClass("on")) {
        if (ajaxObj) {
            ajaxObj.abort();
        }

        $('.d_gs_nav li').removeClass("on");
        $(obj).addClass("on");
        $(".d_gs_nav").attr("data", $(obj).attr("data"));
        $("#txtKeyWord").val("")
        setPosition($(".d_gs_nav").attr("data"));
        if ($(".d_gs_nav").attr("data") == "XZXK") {
        	$("#tagA").html("行政许可目录");
        	$("#tagA").attr("href","XKCatalog.html");
            getXZXKGSList("", "", 1, "init");
        }
        else {
        	$("#tagA").html("行政处罚目录");
        	$("#tagA").attr("href","CFCatalog.html");
            getXZCFGSList("", "", 1, "init");
        }
    }
}

///设置当前位置
function setPosition(type) {
	
    /*if (type.toUpperCase() == "XZXK") {
        $("#linkType").html("<a href='List.aspx?type=XZXK'>行政许可公示</a>");
    }
    else {
        $("#linkType").html("<a href='List.aspx?type=XZCF'>行政处罚公示</a>");
    }*/
	
	/*var xzxk = "./xzxk/getXZXKDetail.do";
	var xzcf = "./xzcf/getXZCFDetail.do";
	var url = '';
	if(type=='XZXK'){
		url = xzxk;
	}else{
		url = xzcf;
	}
	
	$.ajax({
		url:url,
		type:"get",
		success:function(data){
			console.log(data)
		}
		
	})*/
	
	
}

//获取行政许可公示信息列表
function getXZXKGSList(keyword_, type, pageIndex, getDataType) {
    ajaxObj = $.ajax({
        type: 'post',
        url: "../xzxk/loading_xzxk_list.do",
        dataType: "json",
        data: { action: "getXZXKGSList", Type: type, keyword: keyword_, pageIndex: pageIndex},
        beforeSend:function(){
        	$(".d_gs_main").append("<img id='loading' src='../statics/images/loading-0.gif' style='position:absolute'>");
        },
        success: function (data) {
        	$("#loading").remove();
        	var data = data.data[0].data;
        	var html = "";
        	 var sxq ="";//生效期
        	 var recordId ="";//主键
        	 var xmmc="";//项目名称
        	 var xdr="";//行政相对人名称
        	 $(".List li").remove();
        	if(data[0].list.length==0){
        		 $(".List").append("<li class='notfound'>暂无数据！</li>");
        	}else{
        		 var PageCount = getPageCount(data[0].total);
                 xzxkCreatePageBar(PageCount, getDataType);
        		for(var i =0;i<data[0].list.length;i++){
           		 if ((i + 1) % 2 == 0) {
           			 sxq = data[0].list[i].XK_JDRQ;
           			 if(isEmpty(sxq)){
           				sxq = "无";//生效期
           				sxq =sxq.split(" ")[0]
           			 }
           			 recordId = data[0].list[i].RECORDID;
           			 if(isEmpty(recordId)){
           				recordId = "";//序号
           			 }
           			 xmmc = data[0].list[i].XK_XMMC;
           			 if(isEmpty(xmmc)){
           				xmmc = " ";// 项目名称
           			 }
           			
           			 var queryName = " ";
           			 if(isEmpty(data[0].list[i].XK_XMMC)){
           				queryName = " ";// 项目名称
           			 }else{
           				queryName = data[0].list[i].XK_XMMC;
           			 }
           			
           			 if(isEmpty(data[0].list[i].XK_XDR)){
           				xdr = " ";//生效期
           			 }else{
           				xdr =data[0].list[i].XK_XDR;
           			 }
           			 html += "<li class='list_bg'><a target='_blank' href='detail.html?id=" + recordId + "&type=XZXK&name="+encode(queryName)+"'>"+xmmc+"<span>[" + sxq.split(" ")[0] + "]</span>【" +  xdr + "】</a></li>";
                    }
                    else {
                    	 sxq = data[0].list[i].XK_JDRQ;
               			 if(isEmpty(sxq)){
               				sxq = "无";//决定日期
               			 }
               			 recordId = data[0].list[i].RECORDID;
               			 if(isEmpty(recordId)){
               				recordId = "";//序号
               			 }
               			
               			 var queryName = " ";
               			 if(isEmpty(data[0].list[i].XK_XMMC)){
                				queryName = " ";// 项目名称
                		 }else{
                			 queryName = data[0].list[i].XK_XMMC;
                		 }
               			 
               			 
               			if(isEmpty(data[0].list[i].XK_XMMC)){
               				xmmc = " ";// 项目名称
            			 }else{
            				 xmmc = data[0].list[i].XK_XMMC;
            			 }
               			 
               			if(isEmpty(data[0].list[i].XK_XDR)){
               				xdr = " ";// 项目名称
            			 }else{
            				 xdr = data[0].list[i].XK_XDR;
            			 }
               			 
              			 html += "<li ><a target='_blank' href='detail.html?id=" + recordId + "&type=XZXK&name="+encode(queryName)+"'>"+xmmc+"<span>[" + sxq.split(" ")[0] + "]</span>【" +  xdr + "】</a></li>";
                    }
        		}
        		$(".List").append(html);
        	}
        	
        	
        	
           /* if (results.IsSuccessed) {
                var html = "";
                $(".List li").remove();
                if (results.Data.RecordCount < 1) {
                    $(".List").append("<li class='notfound'>暂无数据！</li>");
                }
                var PageCount = getPageCount(results.Data.RecordCount);
                xzxkCreatePageBar(PageCount, getDataType);
                $.each(results.Data.Items, function (i) {
                    if ((i + 1) % 2 == 0) {
                        html += "<li class='list_bg'><a target='_blank' href='Detail.aspx?id=" + results.Data.Items[i].XK_XH + "&type=XZXK'><span>[" + results.Data.Items[i].XK_SXQ + "]</span>" + Size_Title(results.Data.Items[i].XK_XMMC, results.Data.Items[i].XK_XDR, 57) + "【" + results.Data.Items[i].XK_XDR + "】</a></li>";

                    }
                    else {
                        html += "<li><a target='_blank' href='Detail.aspx?id=" + results.Data.Items[i].XK_XH + "&type=XZXK'><span>[" + results.Data.Items[i].XK_SXQ + "]</span>" + Size_Title(results.Data.Items[i].XK_XMMC, results.Data.Items[i].XK_XDR, 57) + "【" + results.Data.Items[i].XK_XDR + "】</a></li>";

                    }

                });
                $(".List").append(html);
            }
            else {
               // alert(results.Message);
            }
            //$("#q_Msgbox").hide(); //隐藏等待
            return false;*/
        },
        error: function () {
            //$("#q_Msgbox").hide(); //隐藏等待
        	$("#loading").remove();
            return false;
        }
    });
    return false;
}




//获取行政处罚信息列表
function getXZCFGSList(keyword_, type, pageIndex, getDataType) {
    ajaxObj = $.ajax({
        type: 'post',
        url: "../xzcf/loading_xzcf_list.do",
        dataType: "json",
        data: { action: "getXZCFGSList", Type: type, keyword: keyword_, pageIndex: pageIndex },
        beforeSend:function(){
        	$(".d_gs_main").append("<img id='loading' src='../statics/images/loading-0.gif' style='position:absolute'>");
        },
        success: function (data) {
        	$("#loading").remove();
        	var data = data.data[0].data;
        	var html = "";
        	 var sxq ="";
        	 var rid ="";
        	 var cfmc ="";
        	 var xdrMc ="";
        	 $(".List li").remove();
        	if(data[0].list.length==0){
        		 $(".List").append("<li class='notfound'>暂无数据！</li>");
        	}else{
        		 var PageCount = getPageCount(data[0].total);
        		 xzcfCreatePageBar(PageCount, getDataType);
        		for(var i =0;i<data[0].list.length;i++){
           		 if ((i + 1) % 2 == 0) {
           			 sxq =  data[0].list[i].CF_JDRQ;
           			 if(isEmpty(sxq)){
           				sxq = "无";
           			 }
           			 rid =  data[0].list[i].RECORDID;
          			 if(isEmpty(rid)){
          				rid = "无";
          			 }
          			cfmc =  data[0].list[i].CF_CFMC;
          			 if(isEmpty(cfmc)){
          				cfmc = "无";
          			 }
          			 xdrMc =  data[0].list[i].CF_XDR_MC;
         			 if(isEmpty(xdrMc)){
         				xdrMc = "无";
         			 }
          			
          			 
                    html += "<li class='list_bg'><a target='_blank' href='detail.html?id=" + rid + "&type=XZCF&name="+encode(cfmc)+"'><span>[" + sxq.split(" ")[0] + "]</span>"+cfmc+"【" +  xdrMc + "】</a></li>";
                    }
                    else {
                    	 sxq =  data[0].list[i].CF_JDRQ;
               			 if(isEmpty(sxq)){
               				sxq = "无";
               			 }
               			 rid =  data[0].list[i].RECORDID;
              			 if(isEmpty(rid)){
              				rid = "无";
              			 }
              			cfmc =  data[0].list[i].CF_CFMC;
              			 if(isEmpty(cfmc)){
              				cfmc = "无";
              			 }
              			 xdrMc =  data[0].list[i].CF_XDR_MC;
             			 if(isEmpty(xdrMc)){
             				xdrMc = "无";
             			 }
             			 html += "<li ><a target='_blank' href='detail.html?id=" + rid + "&type=XZCF&name="+encode(cfmc)+"'><span>[" + sxq.split(" ")[0] + "]</span>"+cfmc+"【" +  xdrMc + "】</a></li>";
                    }
        		}
        		$(".List").append(html);
        	}
        	
        	
            /*if (results.IsSuccessed) {
                var html = "";
                $(".List li").remove();
                if (results.Data.RecordCount < 1) {
                    $(".List").append("<li class='notfound'>暂无数据！</li>");
                }
                var PageCount = getPageCount(results.Data.RecordCount);
                xzcfCreatePageBar(PageCount, getDataType);

                $.each(results.Data.Items, function (i) {
                    if ((i + 1) % 2 == 0) {
                        html += "<li class='list_bg'><a target='_blank' href='Detail.aspx?id=" + results.Data.Items[i].CF_XH + "&type=XZCF'><span>[" + results.Data.Items[i].CF_SXQ + "]</span>" + Size_Title(results.Data.Items[i].CF_CFMC, results.Data.Items[i].CF_XDR_MC, 57) + "【" + results.Data.Items[i].CF_XDR_MC + "】</a></li>";
                    }
                    else {
                        html += "<li><a target='_blank' href='Detail.aspx?id=" + results.Data.Items[i].CF_XH + "&type=XZCF'><span>[" + results.Data.Items[i].CF_SXQ + "]</span>" + Size_Title(results.Data.Items[i].CF_CFMC, results.Data.Items[i].CF_XDR_MC, 57) + "【" + results.Data.Items[i].CF_XDR_MC + "】</a></li>";
                    }
                });
                $(".List").append(html);
            }
            else {
               // alert(results.Message);
            }
            //$("#q_Msgbox").hide(); //隐藏等待
            return false;*/
        },
        error: function () {
        	$("#loading").remove();
            //$("#q_Msgbox").hide(); //隐藏等待
            return false;
        }
    });

    return false;
}












///根据总记录数获取总页数
var getPageCount = function (RecordCount) {
    //var PageCount = parseInt(RecordCount / 20); Math.ceil(5 / 2)
    //if (RecordCount % 20 != 0) PageCount++; //如果有余数，则+1
    return Math.ceil(RecordCount / 10);
}

///行政许可分页栏
var xzxkCreatePageBar = function (PageCount, type) {
    if (type == "init") {//是否初始化
        $("#Pagination").pagination(PageCount, {
            num_edge_entries: 1, //边缘页数
            callback: function (page_index, jq) {
                getXZXKGSList($.trim($("#txtKeyWord").val()), "", page_index + 1,"");
            }
        });
    }
}

///行政处罚分页栏
var xzcfCreatePageBar = function (PageCount, type) {
    if (type == "init") {//是否初始化
        $("#Pagination").pagination(PageCount, {
            num_edge_entries: 1, //边缘页数
            callback: function (page_index, jq) {
                getXZCFGSList($.trim($("#txtKeyWord").val()), "", page_index + 1, "");
            }
        });
    }
}





///获取事项目录列表分页栏--- 行政许可
var getSXML_XK_ListCreatePageBar = function (PageCount, type) {
    if (type == "init") {//是否初始化
        $("#Pagination").pagination(PageCount, {
            num_edge_entries: 1, //边缘页数
            callback: function (page_index, jq) {
            	getSXML_XK_List(page_index + 1, "",'行政许可');
            }
        });
    }
}

//获取事项目录列表
function getSXML_XK_List(pageIndex, getDataType,title) {
    $.ajax({
        type: 'post',
        url: "../catalog/loading_catalog.do",
        dataType: "json",
        data: { action: "getSXMLList", pageIndex: pageIndex ,title:title},
        beforeSend:function(){
        	$(".d_gs_main").append("<img id='loading' src='../statics/images/loading-0.gif' style='position:absolute'>");
        },
        success: function (data) {
        	$("#loading").remove();
        	var data = data.data[0].data;
        	var html = "";
        	$("#contents").empty();
        	if(data[0].list.length==0){
        		$("#contents").append("<li class='notfound'>暂无数据！</li>");
        	}else{
        		var PageCount = getPageCount(data[0].total);
        		getSXML_XK_ListCreatePageBar(PageCount, getDataType);
        		for(var i =0;i<data[0].list.length;i++){
					 if ((i + 1) % 2 == 0) {
						 if ((i + 1) % 2 == 0) {
		                        html += "<tr class='list_bg'>";
		                    }
					 }else{
						  html += "<tr>";
					 }
					 html += "<td>" + data[0].list[i].RN + "</td>";
	
	                 var sxmc = $.trim(data[0].list[i].XK_XMMC);
	                 if (sxmc.length > 30){
	                     	sxmc = sxmc.substring(0, 30) != "" ? sxmc.substring(0, 30) + "..." : "";
	                     }
	                 html += "<td title='" + data[0].list[i].XK_SXMC + "'>" + sxmc + "</td>";
	                 html += "<td>" + data[0].list[i].XK_XZBM + "</td>";
	                 html += "<td>" + data[0].list[i].XK_XZXKLB + "</td>";
	                 var sdyj = $.trim(data[0].list[i].XK_YZ);
	                 html += "<td>" + data[0].list[i].XK_XZLB + "</td>";
	                 html += "<td ><a sdyj='" + sdyj + "' href='javascript:;'>查看详细</a></td>";
	                 html += "</tr>";
			 	}
       		}
       		$("#contents").append(html);
       		preview();//绑定预览
       },
       error:function(data){
    	   
       }
    });
}




////

///获取事项目录列表分页栏----行政处罚
var getSXML_CF_List_CF_CreatePageBar = function (PageCount, type) {
    if (type == "init") {//是否初始化
        $("#Pagination").pagination(PageCount, {
            num_edge_entries: 1, //边缘页数
            callback: function (page_index, jq) {
            	getSXML_CF_List(page_index + 1,"", "行政处罚");
            }
        });
    }
}

//获取事项目录列表---行政处罚
function getSXML_CF_List(pageIndex, getDataType,title) {
    $.ajax({
        type: 'post',
        url: "../catalog/loading_catalog.do",
        dataType: "json",
        data: { action: "getSXMLList", pageIndex: pageIndex,title:title},
        beforeSend:function(){
        	$(".d_gs_main").append("<img id='loading' src='../statics/images/loading-0.gif' style='position:absolute'>");
        },
        success: function (data) {
        	$("#loading").remove();
        	var data = data.data[0].data;
        	var html = "";
        	$("#contents").empty();
        	if(data[0].list.length==0){
        		$("#contents").append("<li class='notfound'>暂无数据！</li>");
        	}else{
        		var PageCount = getPageCount(data[0].total);
        		getSXML_CF_List_CF_CreatePageBar(PageCount, getDataType);
        		for(var i =0;i<data[0].list.length;i++){
					 if ((i + 1) % 2 == 0) {
						 if ((i + 1) % 2 == 0) {
		                        html += "<tr class='list_bg'>";
		                    }
					 }else{
						  html += "<tr>";
					 }
					 html += "<td>" + data[0].list[i].RN + "</td>";
	
	                 var sxmc = $.trim(data[0].list[i].XK_XMMC);
	                 if (sxmc.length > 30){
	                     	sxmc = sxmc.substring(0, 30) != "" ? sxmc.substring(0, 30) + "..." : "";
	                     }
	                 html += "<td title='" + data[0].list[i].XK_SXMC + "'>" + sxmc + "</td>";
	                 html += "<td>" + data[0].list[i].XK_XZBM + "</td>";
	                 html += "<td>" + data[0].list[i].XK_XZXKLB + "</td>";
	                 var sdyj = $.trim(data[0].list[i].XK_YZ);
	                 html += "<td>" + data[0].list[i].XK_XZLB + "</td>";
	                 html += "<td ><a sdyj='" + sdyj + "' href='javascript:;'>查看详细</a></td>";
	                 html += "</tr>";
			 	}
       		}
       		$("#contents").append(html);
       		preview();//绑定预览
       },
       error:function(data){
    	   
       }
    });
}






////













function preview(){
	

//事项目录查看
$(".SXCatalog tr td a").on("click", function () {
    var sdyj = $(this).attr("sdyj").replace(/\r\n/g, "<BR>").replace(/\n/g, "<BR>");
    if (sdyj.length > 500) {
        //页面层
        layer.open({
            type: 1,
            shadeClose: true,
            title: "设定依据",
            area: ['700px'],
            fix: false, //不固定
            move: false,
            scrollbar: false,
            content: "<div style='  padding: 20px;'>" + sdyj + "</div>"
        });
    }
    else if (sdyj.length > 200) {
        //页面层
        layer.open({
            type: 1,
            shadeClose: true,
            title: "设定依据",
            area: ['500px'],
            fix: false, //不固定
            move: false,
            scrollbar: false,
            content: "<div style='  padding: 20px;'>" + sdyj + "</div>"
        });
    }
    else {
        //页面层
        layer.open({
            type: 1,
            shadeClose: true,
            title: "设定依据",
            fix: false, //不固定
            move: false,
            scrollbar: false,
            content: "<div style='  padding: 20px;'>" + sdyj + "</div>"
        });
    }

});
}







