var width = 0,
    height = 0,
    selected, chart = "actors",
    showTrends = !0,
    task_type = 'share',
    task_type_c = 'collect',
    collect_pos = 2, 
    ff = false, 
    share_pos = 2,
    x_size = 15,
    is_single = false;
//console.log(new Date(2013, 7, 10).getTime());

function initStarpaths() {
    selected = d3.map();
    showList();
}

function drawAllCharts() {
   //console.log("task_type ------= " + task_type);
    ff = true;
    null != d3.selectAll("#graph") && d3.selectAll("#graph").remove();
    $("#graphHolder").html("");
    $('#graphHolder').html('<div class="svrConfig" id="collect_config"></div><div id="svg_collect" class="collect_up"></div>'
        +'<div class="svrConfig" id="share_config"></div>'
        +'<div id="svg_share" class="share_down"></div>')
    var a = {
        top: 100,
        right: 40,
        bottom: 0,
        left: 50
    };
    //width = Math.round($(window).width() - 20 - $("#graphHolder").position().left) - a.left - a.right - 50;
    width = window.screen.width - 340;
    //width = Math.round(window.screen.width - $('#toolbox').width()) - 140;
    if(is_single){
        width = window.screen.width - 100;
        gwidth = width;
    }/*else{
        width = window.screen.width - 320;
    }*/
    $("#graphHolder").width(width).css({'overflow-x': "visible", 'overflow-y': "visible"});
    height = 420;
    $("#graphHolder").height(height);

    var c = null;
    "actors" == chart ? c = actors : "directors" == chart ? c = directors : "composers" == chart && (c = composers);
    dataObject = [];
    var b = "collect",
        d = "share";
    //从提供的数据中查找横坐标的起始结束日期
    //该数据将根据用户选择的日期范围决定
    //默认为近30天
    var b2 = dateAry[0].id;
    var d2 = dateAry[dateAry.length-1].id;
    //console.log(c.length);
    //开始计算横坐标
    c.forEach(function(a) {
    	selected.has(a.id) && (dataObject.push(a))
    });
    //数据范围doamin(dataStart, dataEnd)
    //网页显示范围range(posLeft, posRight)
    var eu = d3.scale.linear().domain([b2, d2]).range([a.left, width+a.left]);
    if(is_single){
        eu = d3.scale.linear().domain([b2, d2]).range([a.left, width]);
    }
    //console.log("-----"+task_type);
    drawChart(c, dataObject, "collect", 2, eu, "采集数据量", "Budget", "M$ (with inflation)", width, height / 2, a);
    drawChart(c, dataObject, "share", 2, eu, "共享数据量", "Rating", "/ 10 points", width, height / 2, a);
}

/**
 * a 整个数组结构
 * c 需要画图的数据数组
 * b 需要画图的第一个节点名称, d 需要做图的第二个节点名称
 * e 换算数据点实际横坐标位置的函数
 */
function drawChart(a, c, b, d, e, m, n, p, q, g, f) {
    var a1 = a,
        c1 = c,
        b1 = b,
        d1 = d,
        e1 = e,
        m1 = m,
        n1 = n,
        p1 = p,
        q1 = q,
        g1 = g,
        f1 = f; //重绘备用数据,可能会用到
    var posi = d;

    //数据范围doamin(dataStart, dataEnd)
    //网页显示范围range(posLeft, posRight)

    var h = d3.min(c,
        function(a) {
            return d3.min(a[b],
                function(a) {
                    return parseFloat(a[posi])*0.9
                })
        });
    //h = d3.min(a[b])
    a = d3.max(c,
        function(a) {
            return d3.max((a[b]),
                function(a) {
                    return parseFloat(a[posi])*1.1
                })
        });
   var stype= b.indexOf("share")==-1 ? "collect" : "share";
   // console.log("纵向数据的范围： ["+h+", "+a+"], ["+(g)+", 0]");
    //计算纵坐标的显示范围
    var j = d3.scale.linear().domain([h, a]).range([(g-10), 0]),
        k = d3.scale.category10().domain(c.map(function(a) {
            return a.id
        })),
    
    h = d3.select("#svg_"+stype).append("svg:svg").attr("id", "graph_" + b + '_'+d).attr("width",
            is_single ? q : (q + f.left + f.right)).attr("height", 240).attr("transform", "translate(" + f.left + "," + f.top + ")");

    x_size = getAxisX(e1);
    a = make_x_axis(e);
    h.append("g").attr("class", "grid").attr("transform", "translate(0," + (g-10) + ")")
    	.call(make_x_axis(e).tickSize(-g, 0, 0).tickFormat(""));
    h.append("g").attr("class", "x axis").attr("transform", "translate(0," + (g-10) + ")")
        .call(a).append("text").attr("x", q / 2).attr("y", 20).attr("dy", "1.5em").style("text-anchor", "end").text("");
    g = make_y_axis(j);
    h.append("g").attr("class", "grid").attr("transform", "translate(" + f.left + ", 0)")
    	.call(make_y_axis(j).tickSize(-(q), 0, 0).tickFormat(""));
    h.append("g").attr("class", "y axis").attr("transform", "translate(" + f.left + ",0)").call(g)//.append("text")
       // .attr("transform", "rotate(0)").attr("y", (g1-5)).attr("dy", ".71em").style("text-anchor", "end").text(m);
    var r = d3.svg.line().x(function(a) {
        return e(a[0]);
    }).y(function(a) {
        return j(a[posi])
    }).interpolate("cardinal"),
        l = h.selectAll(".actor").data(c).enter().append("svg:g").attr("class", "actor");
    //输出线
    //var ind = (m == "共享数据量" ? (d == 'share' ? d : 'share_single') : 'collect');
    var lb = l;
    l.append("svg:path").attr("d",
        function(a) {
            return r(a[b])
        }).attr("id",
        function(a) {
            return "path_" + b + "_" + d;
            // return a.id
        }).attr("class", "actor").attr("type", b+'_'+d).style("stroke",
        function(a) {
            return k(a.id)
        });

    //如果有展示的数据，名称显示在图线的最后
    //没有则显示在左上角
    l.append("svg:text").attr("transform",
        function(a) {
            var posx = 60,
                posy = 15;
            if (a[b].length != 0) {
                return "translate(" + (e(a[b][a[b].length - 1][0]) - 50) + "," + (j(a[b][a[b].length - 1][2]) - 15) + ")";
            } else {
                return "translate(60, -5)";
            }
            //          return "translate(" + (e(a[b][a[b].length - 1][0]) + 5) + "," + j(a[b][a[b].length - 1][1]) + ")"
        }).attr("x", 3).attr("dy", ".35em").attr("id",
        function(a) {
            return a.id + "_text";
        }).attr("class", "actor").style("fill",
        function(a) {
            return k(a.id)
        }).text(function(a) {
            return a.name
    });
    c.forEach(function(a) {
        addPoints(a.id, l, e, j, a[b], n, p, k(a.id), d);
    })
}
//function enterPointer(){console.log("enterPointer()");}
function addPoints(a, c, b, d, e, m, n, p, t) {
    var pos = t;
    c.selectAll("data-point").data(e).enter().append("svg:circle").attr("class", "data-point "+t)
    // .attr("onclick", "enterPointer()")
    .style("opacity", 1).style("stroke", p).attr("cx",
        function(a) {
            return b(a[0])
        }).attr("cy",
        function(a) {
            return d(a[pos])
        }).attr("r",
        function() {
            return 4
        }).on("click", function(a) {
        // console.log("click here");
    })
    /*.attr('title', function(a){
        console.log(a);
    })*/
    ;
    $("svg circle").tipsy({
        gravity: "w",
        html: !0,
        title: function() {
            var a = this.__data__;
            //console.log(a[0] + ' === ' + a[1]);
            var date = a[0].length<5 ? a[1] : formatDateF(parseInt(a[0]), "%Y-%m-%d");
            return date + ', ' + a[pos];
        }
    })
}

function showList(size) {
    // console.log("showList()");
    if(size == 1) is_single = true;
    var a = null;
    "actors" == chart ? a = actors : "directors" == chart ? a = directors : "composers" == chart && (a = composers);
    var c = "";
    var t1 = "";
    a.forEach(function(a) {
        c += "<option value='" + a.id + "'>" + a.name + "</option>";
        t1 = a.name;
    });
    if(size == 1){
        $("#actor_list").html("<span class='actor_title'>服务对象："+ t1 +"</span><select id='actor_selector' "+
            (size == 1 ? 'disabled' : '')+" size='"+(typeof size == 'undefined' ? 14 : size)+
            "' multiple>" + c + "</select>");
    }else{
        $("#actor_list").html("<select id='actor_selector' "+(size == 1 ? 'disabled' : '')+" size='"+(typeof size == 'undefined' ? 14 : size)+"' multiple>" + c + "</select>");
    }
    $("#actor_selector").change(function() {
        selectActors($(this).val())
    })
    //默认显示第一个节点
    $("#actor_selector option:first").attr('selected', true);
    selectActors($('#actor_selector').val());
}

function showTheOne() {
    // console.log("showList()");
    var a = null;
    "actors" == chart ? a = actors : "directors" == chart ? a = directors : "composers" == chart && (a = composers);
    var c = "";
    a.forEach(function(a) {
        c += "<option value='" + a.id + "'>" + a.name + "</option>"
    });
    $("#actor_list").html("<select id='actor_selector' size='14' multiple>" + c + "</select>");
    $("#actor_selector").change(function() {
        selectActors($(this).val())
    })
    //默认显示第一个节点
    // $("#actor_selector option:first").attr('selected', true);
    // selectActors($('#actor_selector').val());
}

function selectActors(a) {
    // console.log("ff = " + ff);
    null != a && (selected = d3.map(), a.forEach(function(a) {
        selected.set(a, 1)
    }), ff ? drawAllCharts2() : drawAllCharts())
}

function drawAllCharts2() {
    $('#share_config').html('');
    var share_str = '<label class="radio_span" ><input onclick="drawShareLine(\'share_single\')" value="share_single" type="radio" name="r1" />单条</label>' +
        '<label class="radio_span selected" ><input onclick="drawShareLine(\'share\')" checked value="share" type="radio" name="r1" />批量</label>' +
        '&nbsp;&nbsp;&nbsp;<select onchange="drawAIndex()" id="share_c">' +
        '<option value="share_amount">数据量</option>' +
        '<option value="share_count">共享次数</option>' +
        '<option value="share_time">共享耗时</option></select>';
    
    $('#share_config').html(share_str);

    var collect_str = '<label class="cradio_span" ><input onclick="drawCollectLine(\'collect_etl\')" value="collect_etl" type="radio" name="r2" />ETL采集</label>' +
        '<label class="cradio_span selected" ><input onclick="drawCollectLine(\'collect\')" checked value="collect" type="radio" name="r2" />采集</label>' +
        '&nbsp;&nbsp;&nbsp;<select onchange="drawCIndex()" id="collect_c">' +
        '<option value="collect_amount">数据量</option>' +
        '<option value="collect_count">运行次数</option>' +
        '<!-- <option value="collect_time">采集耗时</option> --></select>';
    $('#collect_config').html(collect_str);
    $('input[name="r1"]')[1].click();
    $('input[name="r2"]')[1].click();
    //console.log("task_type ===> " + task_type + ' ------------- ' + 'task_type_c='+task_type_c);
    drawAIndex();
    drawCIndex();
}

function chartChange(a) {
    chart = a;
    //console.log("onchange()" + a.name);
    initStarpaths()
}

function toggleTrends() {
    showTrends = $("#trend_checkbox").is(":checked") ? !0 : !1;
    drawAllCharts()
}
//2013-11-30
function formatDateS(dd) {
    //console.log("formatDateS()");
    if(dd < 10000) 
        return dateAry[parseInt(dd)-1].md.length == 10 ? dateAry[parseInt(dd)-1].md.substr(5) : dateAry[parseInt(dd)-1].md;
    var newTime = new Date(dd);
    var formatDateStr = d3.time.format("%m-%d");
    // console.log("MM-dd  ===> "+dd + ' ----- ' );
    return formatDateStr(newTime);
}

function formatDateF(dd, formatString) {
    // console.log("formatDateS()");
    var newTime = new Date(dd);
    var formatDateStr = d3.time.format(formatString);
    //console.log(newTime.Format("MM-dd"));
    return formatDateStr(newTime);
}

function make_x_axis(a) {
    return d3.svg.axis().scale(a).orient("bottom").ticks(x_size).tickFormat(formatDateS);
}

function parseDate(d) {
    return d.getFullYear();
}

function make_y_axis(a) {
    return d3.svg.axis().scale(a).orient("left").ticks(12)
}
// console.log(new Date(1357661400000) + " = = =" +new Date("1357661400000"))
function about() {
    $("#about").dialog("open");
    return !1
};
function getAxisX(e){
    // console.log("范围: "+e.domain());
    var domain = e.domain();
    //var days = Math.floor((domain[1] - domain[0]) / (24*3600*1000));
    var days = dateAry.length; 
    if(days>0 && days<15){
        return days-1
    }else{
        return 15;
    }
}