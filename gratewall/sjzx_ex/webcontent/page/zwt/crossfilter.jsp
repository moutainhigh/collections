<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Crossfilter</title>
<style>
body {
  font-family: "Helvetica Neue";
  margin: 10px auto;
  width: 800px;
  background: #ebf0f5;
}

#body {
  position: relative;
}

footer {
  padding: 2em 0 1em 0;
  font-size: 12px;
}

.title{
  font-size:14px;
  font-family: "宋体";
  color: #4682B4
  
}

h1 {
  font-size: 96px;
  margin-top: .3em;
  margin-bottom: 0;
}

h1 + h2 {
  margin-top: 0;
}

h2 {
  font-weight: 400;
  font-size: 28px;
}

h1, h2 {
  font-family: "Yanone Kaffeesatz";
  text-rendering: optimizeLegibility;
}

#body > p {
  line-height: 1.5em;
  text-rendering: optimizeLegibility;
}

#charts {
  padding: 3px 0;
}

.chart {
  display: inline-block;
  height: 151px;
  margin-bottom: 20px;
}

.reset {
  padding-left: 1em;
  font-size: smaller;
  color: #ccc;
}

.background.bar {
  fill: #ccc;
}

.foreground.bar {
  fill: steelblue;
}

.axis path, .axis line {
  fill: none;
  stroke: #333;
  shape-rendering: crispEdges;
}

.axis text {
  font: 10px sans-serif;
}

.brush rect.extent {
  fill: steelblue;
  fill-opacity: .125;
}

.brush .resize path {
  fill: #eee;
  stroke: #666;
}



#flight-list .date{
  margin-bottom: .4em;
}
#flight-list .day {
  margin-bottom: .4em;
  color: #333;
}

#flight-list .flight {
  line-height: 1.5em;
  background: #FFFFFF;
  width: 100%;
  margin-bottom: 1px;
  color: #333;
}

#flight-list .flight div {
  display: inline-block;
  width: 10%;
  font-family: "宋体";
  font-size: 14px;
  color: #333;
}

#flight-list .time {
  color: #999;
  border-right: 1px solid rgb(235,240,245);
  color: #333;
}

#flight-list div.origin {
  display: inline-block;
  width: 20%;
  border-right: 1px solid rgb(235,240,245);
  color: #333;
}

#flight-list div.destination {
  display: inline-block;
  width: 40%;
  border-right: 1px solid rgb(235,240,245);
  color: #333;
}

#flight-list div.distance{
  width: 13%;
  padding-right: 10px;
  text-align: right;
  border-right: 1px solid rgb(235,240,245);
  color: #333;
}

#flight-list div.delay {
  width: 13%;
  padding-right: 10px;
  text-align: right;
  color: #333;
}

#flight-list .early {
  color: green;
}

aside {
	top: 332px;
  position: absolute;
  left: 540px;
  font-size: smaller;
  width: 280px;
  color: rgb(70, 130, 160);
}

</style>

<div id="body">

<div id="charts">
  <div id="hour-chart" class="chart">
    <div class="title">24小时分布情况</div>
  </div>
  <div id="delay-chart" class="chart">
    <div class="title">共享次数分布情况</div>
  </div>
  <div id="distance-chart" class="chart">
    <div class="title">共享数据量分布情况</div>
  </div>
  <div id="date-chart" class="chart">
    <div class="title">近60天共享数量趋势</div>
  </div>
</div>

<aside id="totals">
   <span id="active">-</span> 条记录中的 <span id="total">-</span> 条记录被选择.<br/>
   
</aside>

<div id="lists" style="border: 1px solid #69c;height: 155px;padding:2px;overflow: auto;">
  <div id="flight-list" class="list"></div>
</div>


</div>

<script src="/page/zwt/js/crossfilter.v1.min.js"></script>
<script src="/page/zwt/js/d3.v3.min.js"></script>
<script>

// (It's CSV, but GitHub Pages only gzip's JSON at the moment.)
d3.csv("/page/zwt/json/crossfilter.json", function(error, flights) {

  // Various formatters.
  var formatNumber = d3.format(",d"),
      formatChange = d3.format("+,d"),
      formatDate = d3.time.format("%Y-%m-%d "),
      formatTime = d3.time.format("%H:%M ");

  // A nest operator, for grouping the flight list.
  var nestByDate = d3.nest()
      .key(function(d) { return d3.time.day(d.date); });

  // A little coercion, since the CSV is untyped.
  flights.forEach(function(d, i) {
    d.index = i;
    d.date = parseDate(d.date);
    d.delay = +d.delay;
    d.distance = +d.distance;
  });

  // Create the crossfilter for the relevant dimensions and groups.
  var flight = crossfilter(flights), beginDate = "",  endDate = "", count = 0, //记录总数
      all = flight.groupAll(),
      date = flight.dimension(function(d, i) { if(i==0){beginDate=d.ymd} count=i+1; return d.date; }),
      dates = date.group(d3.time.day),
      hour = flight.dimension(function(d, i) { if(count==(i+1)){endDate=d.ymd} return d.date.getHours() + d.date.getMinutes() / 60; }),
      hours = hour.group(Math.floor),
      delay = flight.dimension(function(d) { return Math.max(-60, Math.min(149, d.delay)); }),
      delays = delay.group(function(d) { return Math.floor(d / 10) * 10; }),
      distance = flight.dimension(function(d) { return Math.min(1999, d.distance); }),
      distances = distance.group(function(d) { return Math.floor(d / 50) * 50; });

	  var by,bm,bd,ey,em,ed;
	  if(beginDate!=""){
          by = beginDate.substring(0,4);
		  bm = beginDate.substring(4,6);
		  bd = beginDate.substring(6,8);
          ey = endDate.substring(0,4);
		  em = endDate.substring(4,6);
		  ed = endDate.substring(6,8);		   
	  }
	  
	  
  var charts = [

    barChart()
        .dimension(hour)
        .group(hours)
      .x(d3.scale.linear()
        .domain([0, 24])
        .rangeRound([0, 10 * 24])),

    barChart()
        .dimension(delay)
        .group(delays)
      .x(d3.scale.linear()
        .domain([0, 300])
        .rangeRound([0, 10 * 20])),

    barChart()
        .dimension(distance)
        .group(distances)
      .x(d3.scale.linear()
        .domain([0, 2000])
        .rangeRound([0, 5 * 48])),

    barChart()
        .dimension(date)
        .group(dates)
        .round(d3.time.day.round)
      .x(d3.time.scale()
        .domain([new Date(by, (bm-1), bd), new Date(ey, (em-1), ed)])
        .rangeRound([0, 10 * 78]))
        //.filter([new Date(2001, 1, 1), new Date(2001, 2, 1)])

  ];

  // Given our array of charts, which we assume are in the same order as the
  // .chart elements in the DOM, bind the charts to the DOM and render them.
  // We also listen to the chart's brush events to update the display.
  var chart = d3.selectAll(".chart")
      .data(charts)
      .each(function(chart) { chart.on("brush", renderAll).on("brushend", renderAll); });

  // Render the initial lists.
  var list = d3.selectAll(".list")
      .data([flightList]);

  // Render the total.
  d3.selectAll("#total")
      .text(formatNumber(flight.size()));

  renderAll();

  // Renders the specified chart or list.
  function render(method) {
    d3.select(this).call(method);
  }

  // Whenever the brush moves, re-rendering everything.
  function renderAll() {
    chart.each(render);
    list.each(render);
    d3.select("#active").text(formatNumber(all.value()));
  }

  // Like d3.time.format, but faster.
  function parseDate(d) {
       
    return new Date(2013,
        d.substring(0, 2) - 1,
        d.substring(2, 4),
        d.substring(4, 6),
        d.substring(6, 8));
  }

  window.filter = function(filters) {
    filters.forEach(function(d, i) { charts[i].filter(d); });
    renderAll();
  };

  window.reset = function(i) {
    charts[i].filter(null);
    renderAll();
  };

  function flightList(div) {
    var flightsByDate = nestByDate.entries(date.top(400));

    div.each(function() {
      var date = d3.select(this).selectAll(".date")
          .data(flightsByDate, function(d) { return d.key; });

      date.enter().append("div")
          .attr("class", "date")
        .append("div")
          .attr("class", "day")
          .text(function(d) { return formatDate(d.values[0].date); });

      date.exit().remove();

      var flight = date.order().selectAll(".flight")
          .data(function(d) { return d.values; }, function(d) { return d.index; });

      var flightEnter = flight.enter().append("div")
          .attr("class", "flight");

      flightEnter.append("div")
          .attr("class", "time")
          .text(function(d) { return formatTime(d.date); });

      flightEnter.append("div")
          .attr("class", "origin")
          .text(function(d) { return d.origin; });

      flightEnter.append("div")
          .attr("class", "destination")
          .text(function(d) { return d.destination; });

      flightEnter.append("div")
          .attr("class", "distance")
          .text(function(d) { return formatNumber(d.distance) + " 条"; });

      flightEnter.append("div")
          .attr("class", "delay")
          .classed("early", function(d) { return d.delay < 0; })
          .text(function(d) { return d.delay + " 次"; });

      flight.exit().remove();

      flight.order();
    });
  }

  function barChart() {
    if (!barChart.id) barChart.id = 0;

    var margin = {top: 10, right: 10, bottom: 20, left: 10},
        x,
        y = d3.scale.linear().range([100, 0]),
        id = barChart.id++,
        axis = d3.svg.axis().orient("bottom"),
        brush = d3.svg.brush(),
        brushDirty,
        dimension,
        group,
        round;

    function chart(div) {
      var width = x.range()[1],
          height = y.range()[0];

      y.domain([0, group.top(1)[0].value]);

      div.each(function() {
        var div = d3.select(this),
            g = div.select("g");

        // Create the skeletal chart.
        if (g.empty()) {
          div.select(".title").append("a")
              .attr("href", "javascript:reset(" + id + ")")
              .attr("class", "reset")
              .text("reset")
              .style("display", "none");

          g = div.append("svg")
              .attr("width", width + margin.left + margin.right)
              .attr("height", height + margin.top + margin.bottom)
            .append("g")
              .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

          g.append("clipPath")
              .attr("id", "clip-" + id)
            .append("rect")
              .attr("width", width)
              .attr("height", height);

          g.selectAll(".bar")
              .data(["background", "foreground"])
            .enter().append("path")
              .attr("class", function(d) { return d + " bar"; })
              .datum(group.all());

          g.selectAll(".foreground.bar")
              .attr("clip-path", "url(#clip-" + id + ")");

          g.append("g")
              .attr("class", "axis")
              .attr("transform", "translate(0," + height + ")")
              .call(axis);

          // Initialize the brush component with pretty resize handles.
          var gBrush = g.append("g").attr("class", "brush").call(brush);
          gBrush.selectAll("rect").attr("height", height);
          gBrush.selectAll(".resize").append("path").attr("d", resizePath);
        }

        // Only redraw the brush if set externally.
        if (brushDirty) {
          brushDirty = false;
          g.selectAll(".brush").call(brush);
          div.select(".title a").style("display", brush.empty() ? "none" : null);
          if (brush.empty()) {
            g.selectAll("#clip-" + id + " rect")
                .attr("x", 0)
                .attr("width", width);
          } else {
            var extent = brush.extent();
            g.selectAll("#clip-" + id + " rect")
                .attr("x", x(extent[0]))
                .attr("width", x(extent[1]) - x(extent[0]));
          }
        }

        g.selectAll(".bar").attr("d", barPath);
      });

      function barPath(groups) {
        var path = [],
            i = -1,
            n = groups.length,
            d;
        while (++i < n) {
          d = groups[i];
          path.push("M", x(d.key), ",", height, "V", y(d.value), "h9V", height);
        }
        return path.join("");
      }

      function resizePath(d) {
        var e = +(d == "e"),
            x = e ? 1 : -1,
            y = height / 3;
        return "M" + (.5 * x) + "," + y
            + "A6,6 0 0 " + e + " " + (6.5 * x) + "," + (y + 6)
            + "V" + (2 * y - 6)
            + "A6,6 0 0 " + e + " " + (.5 * x) + "," + (2 * y)
            + "Z"
            + "M" + (2.5 * x) + "," + (y + 8)
            + "V" + (2 * y - 8)
            + "M" + (4.5 * x) + "," + (y + 8)
            + "V" + (2 * y - 8);
      }
    }

    brush.on("brushstart.chart", function() {
      var div = d3.select(this.parentNode.parentNode.parentNode);
      div.select(".title a").style("display", null);
    });

    brush.on("brush.chart", function() {
      var g = d3.select(this.parentNode),
          extent = brush.extent();
      if (round) g.select(".brush")
          .call(brush.extent(extent = extent.map(round)))
        .selectAll(".resize")
          .style("display", null);
      g.select("#clip-" + id + " rect")
          .attr("x", x(extent[0]))
          .attr("width", x(extent[1]) - x(extent[0]));
      dimension.filterRange(extent);
    });

    brush.on("brushend.chart", function() {
      if (brush.empty()) {
        var div = d3.select(this.parentNode.parentNode.parentNode);
        div.select(".title a").style("display", "none");
        div.select("#clip-" + id + " rect").attr("x", null).attr("width", "100%");
        dimension.filterAll();
      }
    });

    chart.margin = function(_) {
      if (!arguments.length) return margin;
      margin = _;
      return chart;
    };

    chart.x = function(_) {
      if (!arguments.length) return x;
      x = _;
      axis.scale(x);
      brush.x(x);
      return chart;
    };

    chart.y = function(_) {
      if (!arguments.length) return y;
      y = _;
      return chart;
    };

    chart.dimension = function(_) {
      if (!arguments.length) return dimension;
      dimension = _;
      return chart;
    };

    chart.filter = function(_) {
      if (_) {
        brush.extent(_);
        dimension.filterRange(_);
      } else {
        brush.clear();
        dimension.filterAll();
      }
      brushDirty = true;
      return chart;
    };

    chart.group = function(_) {
      if (!arguments.length) return group;
      group = _;
      return chart;
    };

    chart.round = function(_) {
      if (!arguments.length) return round;
      round = _;
      return chart;
    };

    return d3.rebind(chart, brush, "on");
  }
});

</script>
