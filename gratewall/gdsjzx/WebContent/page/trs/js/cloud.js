
function getQueryString(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) return unescape(r[2]); return null;
};

var vcolors=["blue","red","green"];
var width = 1024,
    height = 768;
    


var force = d3.layout.force()
    .charge(-1000)
    .linkDistance(100)
    .size([width-20, height-20])
    .gravity(0.2);

var svg = d3.select("#cloud");
var pripid=getQueryString('pripid');
pripid=pripid.replace("[",'');
pripid=pripid.replace("]",'');
d3.json("/gzaic_fb/gzgs/relationship.jsp?pripid="+pripid, function(error, graph) {
  d3.select("h2")
    .text(graph.nodes[0].name+"族谱分析");
  var nodeData=graph.nodes;

  function node_indx(pid){
    for(var i=0;i<nodeData.length;i++){
      if(pid==nodeData[i].pripid){
        return i;
      }
    }
  };
  var linkData=graph.links;
  var linksChange=[];
  
  


  linkData.forEach(function(el) {
    var s=node_indx(el.source);
    var t=node_indx(el.target);
    if(typeof s === 'undefined'){

    }else if(typeof t === 'undefined'){

    }else{
      el.source=s;
      el.target=t;
      linksChange.push(el);
    }
  });
  force
      .nodes(nodeData)
      .links(linksChange)
      .on("tick", tick)
      .start();

  var link = svg.selectAll(".link")
      .data(graph.links)
    .enter().append("line")
      .attr("class", "link")
       .attr("marker-end", "url(#arrow)");

//显示投资比例
// var link_text = svg.selectAll(".linetext")
//                 .data(graph.links)
//                 .enter()
//                 .append("text")
//                 .attr("class","linetext")
//                 .text(function(d){
//                   return d.value;
//                 });

    var drag=force.drag()
          .on("dragstart",function(d,i){
            d.fixed=true;
          });

  var node = svg.selectAll(".node")
      .data(graph.nodes)
      .enter().append("g")
      .attr("class", "node")
      .on("dblclick",function(d,i){
        d.fixed=false;
      })
      .call(drag);

node.append("circle")
      .attr("r", function(d){
        if(d.group==2){
          return 15;
        }else{
          return 10;
        }
      })
      .style("fill", function(d) { 
        
          return vcolors[parseInt(d.group)-1];
       
         });

 
 node.append("title")
      .text(function(d) { return d.name});

 node.append("text")
    .attr("x", 20)
    .attr("dy", ".35em")
    .attr("class",function(d,i){
      if(d.group==2){
        return "maintext";
      }else{
        return "linetext";
      }
    })
    .on("click",function(d){
      if(d.group=='3'){
      }else{
      //document.location.href = "http://10.66.188.15:8080/gzaic_fb/txn60110001.do?inner-flag:open-type=new-window&primary-key:pripid="+d.pripid+"&inner-flag:flowno=8570510333&inner-flag:pre-page=/txn60110101.do";
      window.open("http://10.66.188.15:8080/gzaic_fb/txn60110001.do?inner-flag:open-type=new-window&primary-key:pripid="+d.pripid+"&inner-flag:flowno=8570510333&inner-flag:pre-page=/txn60110101.do");
      };
    })
    .text(function(d) { return d.name; });
      
  /*force.on("tick", function() {
    link.attr("x1", function(d) { return d.source.x; })
        .attr("y1", function(d) { return d.source.y; })
        .attr("x2", function(d) { return d.target.x; })
        .attr("y2", function(d) { return d.target.y; });
    node
      .attr("transform", function(d) { 
              return "translate(" + d.x + "," + d.y + ")"; 
      });
  });*/
  function tick() {//打点更新坐标
    //为IE的兼容性添加的内容
    link.each(function() {this.parentNode.insertBefore(this, this); });

   

  link
      .attr("x1", function(d) { return d.source.x; })
      .attr("y1", function(d) { return d.source.y; })
      .attr("x2", function(d) { return d.target.x; })
      .attr("y2", function(d) { return d.target.y; });

  //更新连接线上文字的位置
     // link_text.attr("x",function(d){ return (d.source.x + d.target.x) / 2 ; });
     // link_text.attr("y",function(d){ return (d.source.y + d.target.y) / 2 ; });
         

  node
      .attr("transform", function(d) { 
              return "translate(" + d.x + "," + d.y + ")"; 
      });
}

});