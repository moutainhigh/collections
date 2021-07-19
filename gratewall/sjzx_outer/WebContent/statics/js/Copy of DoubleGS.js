
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
    //alert(newHeight);
    $("#section").css("min-height", newHeight);
}
var ajaxObj = null;
//截取字符长度（双公示首页和列表页）
function Size_Title(title, name, lengthStr) {
    if (title.length + name.length > lengthStr)
        return title.substring(0, lengthStr - name.length) + "…";
    else
        return title ;

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
            getXZXKGSList("", "", 1, "init");
        }
        else {
            getXZCFGSList("", "", 1, "init");
        }
    }
}

///设置当前位置
function setPosition(type) {
	/*alert(type)
    if (type.toUpperCase() == "XZXK") {
        $("#linkType").html("<a href='List.aspx?type=XZXK'>行政许可公示</a>");
    }
    else {
        $("#linkType").html("<a href='List.aspx?type=XZCF'>行政处罚公示</a>");
    }*/
}

//获取行政许可公示信息列表
function getXZXKGSList(keyword_, type, pageIndex, getDataType) {
	alert("  == " +type)
    ajaxObj = $.ajax({
        type: 'post',
        url: "xzxk/loading_xzxk_list.do",
        dataType: "json",
        data: { action: "getXZXKGSList", Type: type, keyword: keyword_, PageIndex: pageIndex},
        success: function (results) {
            if (results.IsSuccessed) {
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
            return false;
        },
        error: function () {
            //$("#q_Msgbox").hide(); //隐藏等待
            return false;
        }
    });
    return false;
}




//获取行政处罚信息列表
function getXZCFGSList(keyword_, type, pageIndex, getDataType) {
    ajaxObj = $.ajax({
        type: 'post',
        url: "xzcf/loading_xzcf_list.do",
        dataType: "json",
        data: { action: "getXZCFGSList", Type: type, keyword: keyword_, PageIndex: pageIndex },
        success: function (results) {
            if (results.IsSuccessed) {
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
            return false;
        },
        error: function () {
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
///获取事项目录列表分页栏
var getSXMLListCreatePageBar = function (PageCount, type) {
    if (type == "init") {//是否初始化
        $("#Pagination").pagination(PageCount, {
            num_edge_entries: 1, //边缘页数
            callback: function (page_index, jq) {
                getSXMLList(page_index + 1, "");
            }
        });
    }
}

//获取事项目录列表
function getSXMLList(pageIndex, getDataType) {
    ajaxObj = $.ajax({
        type: 'post',
        url: "record/loadingCatory",
        dataType: "json",
        data: { action: "getSXMLList", PageIndex: pageIndex },
        success: function (results) {
            if (results.IsSuccessed) {
                var html = "";
                $("#List tr:gt(0)").remove();
                if (results.Data.RecordCount < 1) {
                    $("#List").append("<td colspan='7' class='notfound'>暂无数据！</td>");
                }
                var PageCount = getPageCount(results.Data.RecordCount);
                getSXMLListCreatePageBar(PageCount, getDataType);
                $.each(results.Data.Items, function (i) {
                    if ((i + 1) % 2 == 0) {
                        html += "<tr class='list_bg'>";
                    }
                    else {
                        html += "<tr>";
                    }
                    html += "<td>" + results.Data.Items[i].rownum + "</td>";

                    var sxmc = $.trim(results.Data.Items[i].SXMC);
                    if (sxmc.length > 30)
                        sxmc = sxmc.substring(0, 30) != "" ? sxmc.substring(0, 30) + "..." : "";
                    html += "<td title='" + results.Data.Items[i].SXMC + "'>" + sxmc + "</td>";
                    html += "<td>" + results.Data.Items[i].XZJDBM + "</td>";
                    html += "<td>" + results.Data.Items[i].XZJDLB + "</td>";
                    var sdyj = $.trim(results.Data.Items[i].SDYJ);
                    //if (sdyj.length > 30)
                        //sdyj = sdyj.substring(0, 30) != "" ? sdyj.substring(0, 30) + "...<a href='javascript:;'>查看详细</a>" : "";
                    //html += "<td title='" + $.trim(results.Data.Items[i].SDYJ) + "'>" + sdyj + "</td>";
                    html += "<td>" + results.Data.Items[i].XZXDRLB + "</td>";
                    html += "<td ><a sdyj='" + sdyj + "' href='javascript:;'>查看详细</a></td>";
//                    html += "<td>" + results.Data.Items[i].QZXH + "</td>";
                    html += "</tr>";
                });
                $("#List").append(html);
            }
            else {
               // alert(results.Message);
            }
            return false;
        },
        error: function () {
            return false;
        }
    });
    return false;
}

//事项目录查看
$(".SXCatalog tr td a").live("mouseover", function () {
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










