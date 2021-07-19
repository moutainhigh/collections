//����,���û��ʵĴ�ϸ����ɫ
new Pen();
public:
Pen.prototype.FontSize='12';
Pen.prototype.LineSize=1;
Pen.prototype.Color='black';
function Pen(){}
//ˢ�ӣ���ͼ�����������ɫ���
new Brush();
public:
Brush.prototype.Color;
function Brush(){}

//����
new Canvas();
public:
Canvas.prototype.Top=0;//ͼ�ξඥ�˵�λ��
Canvas.prototype.Left=0;//ͼ�ξ���ߵ�λ��
Canvas.prototype.Width=10;//ͼ�εĴ�С
Canvas.prototype.Height=10;//ͼ�εĴ�С
Canvas.prototype.Pen=new Pen();//����
Canvas.prototype.Brush=new Brush();//ˢ��
Canvas.prototype.Rect=CVS_rect;//������
Canvas.prototype.MoveTo=CVS_moveto;//��ʼ��ͼ�ε���ʼλ�ã�x,y��
Canvas.prototype.LineTo=CVS_lineto;//��ֱ�ߣ�ֱ�ߵ��յ�(x,y)
Canvas.prototype.RoundRect=CVS_roundrect;//԰�Ǿ���(width,height)
Canvas.prototype.PolyLine=CVS_polyline;//����(points),pointsΪ������
Canvas.prototype.Oval=CVS_oval;//����Բ(width,height)
Canvas.prototype.Curve=CVS_curve;//������(points),pointsΪ�����飬������������from,to��controlΪ���Ƶ�
Canvas.prototype.Arc=CVS_arc;//������(width,height,startangle,endangle),width��height������С��startangle��endangleΪ��ʼ�Ƕ�
Canvas.prototype.Image=CVS_image;//ͼƬ(width,height,src),srcΪ·��
Canvas.prototype.Textbox=CVS_textbox;//�ı�
Canvas.prototype.Group=CVS_group;
Canvas.prototype.Shape=CVS_shape;
function Canvas(div){	
     this.Point;//��ǰ����x,y
	 if(div==null)div=document.body;
	 this.FrameDiv=div;//����ͼ�εĿ�����
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
function CVS_shape(width,height,path){                //shapeû����this.Div��ӽ�ȥ
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


/*ʹ��˵������Ҫnew Canvas(div);divΪ�ⲿ�����Ŀ��������ûһ������������һ��ͼ�ζ���
		   �����ڻ�ͼǰ��ͼ�ε����Խ������ã��������Ĭ��ֵ��
		   ��ֱ�ߣ�
				����  Pen.LineSize; Pen.Color;
				����  MoveTo(x,y),Ȼ��LineTo(x,y);
		   �����Σ�
				����  Top,Leftȷ��λ�ã�Brush.Color;
				����  Rect(width,height);
		   ԰�ž��Σ�
				����  Top,Leftȷ��λ�ã�Brush.Color;
				����  RoundRect(width,height);
			����Բ��
				����   Top,Leftȷ��λ�ã�Brush.Color;
				����   Oval(width,height);
			�����ߣ�
			    ����  Pen.LineSize; Pen.Color;
				����  PolyLine(points);pointsΪ�����飬��points=['10,10' '20,20' '30,30'];
			�����ߣ�
				����  Pen.LineSize; Pen.Color;
				����  Curve(points);pointsΪ�����飬points=[from,to,control1,control2,...]
					  fromΪ��㣬toΪ�յ㣬controliΪ���Ƶ�
					  �� points=var points=['0,0','100,10','40,30','70,30'];
			�����ߣ�
				����   Pen.LineSize; Pen.Color;
				����   Arc(width,height,startangle,endangle);
					   width,height�������ߵĴ�С��startangle,endangle���ߵĽǶ�
			ͼƬ��
				����   Top,Leftȷ��λ��
				����   Image(width,height,src);width,heightΪͼƬ��С��srcΪͼƬ·��
			�ı���
				����	   Top,Leftȷ��λ��
				����		Textbox(text);textΪҪ��ʾ���ı�,�������ı�����ɫ�������С
*/
