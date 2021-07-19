//画笔,设置画笔的粗细，颜色
new Pen();
public:
Pen.prototype.FontSize='12';
Pen.prototype.LineSize=1;
Pen.prototype.Color='black';
function Pen(){}
//刷子，对图形区域进行颜色填充
new Brush();
public:
Brush.prototype.Color;
function Brush(){}

//画布
new Canvas();
public:
Canvas.prototype.Top=0;//图形距顶端的位置
Canvas.prototype.Left=0;//图形居左边的位置
Canvas.prototype.Width=10;//图形的大小
Canvas.prototype.Height=10;//图形的大小
Canvas.prototype.Pen=new Pen();//画笔
Canvas.prototype.Brush=new Brush();//刷子
Canvas.prototype.Rect=CVS_rect;//画矩形
Canvas.prototype.MoveTo=CVS_moveto;//初始化图形的起始位置（x,y）
Canvas.prototype.LineTo=CVS_lineto;//画直线，直线的终点(x,y)
Canvas.prototype.RoundRect=CVS_roundrect;//园角矩形(width,height)
Canvas.prototype.PolyLine=CVS_polyline;//画线(points),points为点数组
Canvas.prototype.Oval=CVS_oval;//画椭圆(width,height)
Canvas.prototype.Curve=CVS_curve;//画曲线(points),points为点数组，至少有两个点from,to。control为控制点
Canvas.prototype.Arc=CVS_arc;//画弧线(width,height,startangle,endangle),width和height决定大小，startangle和endangle为起始角度
Canvas.prototype.Image=CVS_image;//图片(width,height,src),src为路径
Canvas.prototype.Textbox=CVS_textbox;//文本
Canvas.prototype.Group=CVS_group;
Canvas.prototype.Shape=CVS_shape;
function Canvas(div){	
     this.Point;//当前坐标x,y
	 if(div==null)div=document.body;
	 this.FrameDiv=div;//容纳图形的块容器
	 this.group;
	 return this;
}

function CVS_rect(width,height){
	var rect=document.createElement('v:rect');
	rect.style.top=this.Top;
	rect.style.left=this.Left;
	rect.style.width=width;
	rect.style.height=height;
	rect.fillcolor=this.Brush.Color;
	rect.style.position='absolute';
	this.FrameDiv.appendChild(rect);
	return rect;
}
function CVS_moveto(x,y){	
	this.Point=x+','+y;
	this.Top=x;
	this.Left=y;
}
function CVS_lineto(x,y){
	var line=document.createElement('v:line'); 	
	line.from=this.Point;	
	line.to=x+','+y;
	line.position='absolute';
	line.strokecolor=this.Pen.Color;
	//line.Strokeweight=this.Pen.LineSize;
	line.strokeweight=this.Pen.LineSize+"pt";
	this.FrameDiv.appendChild(line);
	return line;
}
function CVS_roundrect(width,height){
	var roundrect=document.createElement('v:roundrect');
	roundrect.style.top=this.Top;
	roundrect.style.left=this.Left;
	roundrect.style.width=width;
	roundrect.style.height=height;
	roundrect.strokecolor=this.Pen.Color;
	roundrect.strokeweight=this.Pen.LineSize+"pt";
	roundrect.fillcolor=this.Brush.Color;
	roundrect.style.position='absolute';
	this.FrameDiv.appendChild(roundrect);
	return roundrect;
}
function CVS_polyline(points){
	var polyline=document.createElement('v:polyline');
	  polyline.setAttribute('o:forcedash',"True");

	polyline.points=points;	
	polyline.strokecolor=this.Pen.Color;
	polyline.strokeweight=this.Pen.LineSize+"pt";
	polyline.position='absolute';
	this.FrameDiv.appendChild(polyline);
	return polyline;
}
function CVS_oval(width,height){
	var oval=document.createElement('v:oval');
	oval.style.top=this.Top;
	oval.style.left=this.Left;
	oval.style.width=width;
	oval.style.height=height;
	oval.strokecolor=this.Pen.Color;
	oval.strokeweight=this.Pen.LineSize+"pt";
	oval.fillcolor=this.Brush.Color;
	oval.style.position='absolute';
	this.FrameDiv.appendChild(oval);
	return oval;
}
function CVS_curve(points){
	var curve=document.createElement('v:curve');
	curve.from=points[0];
	curve.to=points[1];	
	for(var i=2;i<points.length;i++){ 
		var q='control'+(i-1);
		curve.setAttribute(q,points[i]);		
			}
	curve.position='absolute';
	curve.strokecolor=this.Pen.Color;
	curve.strokeweight=this.Pen.LineSize+"pt";
	this.FrameDiv.appendChild(curve);
	return curve;
}
function CVS_arc(width,height,startangle,endangle){
	var arc=document.createElement('v:arc');
	arc.style.top=this.Top;
	arc.style.left=this.Left;
	arc.style.width=width;
	arc.style.height=height;
	arc.startangle=startangle;
	arc.endangle=endangle;
	arc.position='absolute';
	arc.strokecolor=this.Pen.Color;
	arc.strokeweight=this.Pen.LineSize+"pt";
	this.FrameDiv.appendChild(arc);
	return arc;
}
function CVS_image(width,height,src){
	var image=document.createElement('v:image');
	image.style.top=this.Top;
	image.style.left=this.Left;
	image.style.width=width;
	image.style.height=height;
	image.src=src;
	image.style.position='absolute';
	this.FrameDiv.appendChild(image);
	return image;
}
function CVS_textbox(text){
	var textbox=document.createElement('v:textbox');
	textbox.style.top=this.Top;
	textbox.style.left=this.Left;
	textbox.innerText=text;
	textbox.style.color=this.Pen.Color;
	textbox.style.fontSize=this.Pen.FontSize;
	textbox.style.position='absolute';
	this.FrameDiv.appendChild(textbox);
	return textbox;
}
function CVS_group(width,height,coordsize){
	this.group=document.createElement('v:group'); 
	this.group.style.width=width;
	this.group.style.height=height;
	this.group.coordsize=coordsize;
	this.FrameDiv.appendChild(this.group);
	return this.group;
}
function CVS_shape(width,height,path){                //shape没有向this.Div添加进去
	var shape=document.createElement('v:shape');
	shape.style.width=width;
	shape.style.height=height;
	shape.style.position='absolute';
	shape.fillcolor=this.Brush.Color;
	shape.strokeweight=this.Pen.LineSize+"pt";
	shape.strokecolor=this.Pen.Color;
	shape.path=path;
	this.group.appendChild(shape);
	return shape;
}


/*使用说明：先要new Canvas(div);div为外部创建的块对象。其中没一个方法都返回一个图形对象。
		   必须在画图前对图形的属性进行设置，否则采用默认值。
		   画直线：
				属性  Pen.LineSize; Pen.Color;
				方法  MoveTo(x,y),然后LineTo(x,y);
		   画矩形：
				属性  Top,Left确定位置，Brush.Color;
				方法  Rect(width,height);
		   园脚矩形：
				属性  Top,Left确定位置，Brush.Color;
				方法  RoundRect(width,height);
			画椭圆：
				属性   Top,Left确定位置，Brush.Color;
				方法   Oval(width,height);
			画折线：
			    属性  Pen.LineSize; Pen.Color;
				方法  PolyLine(points);points为点数组，如points=['10,10' '20,20' '30,30'];
			画曲线：
				属性  Pen.LineSize; Pen.Color;
				方法  Curve(points);points为点数组，points=[from,to,control1,control2,...]
					  from为起点，to为终点，controli为控制点
					  如 points=var points=['0,0','100,10','40,30','70,30'];
			画弧线：
				属性   Pen.LineSize; Pen.Color;
				方法   Arc(width,height,startangle,endangle);
					   width,height决定弧线的大小；startangle,endangle弧线的角度
			图片：
				属性   Top,Left确定位置
				方法   Image(width,height,src);width,height为图片大小，src为图片路径
			文本：
				属性	   Top,Left确定位置
				方法		Textbox(text);text为要显示的文本,可设置文本的颜色，字体大小
*/
