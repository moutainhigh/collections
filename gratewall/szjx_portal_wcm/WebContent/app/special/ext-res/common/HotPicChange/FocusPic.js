Ext.ns("com.trs.FocusPic");
/*lky添加注释*/
(function(){
	//定义FocusPic函数
	var FocusPic = function(BigPicID,SmallPicsID,TitleID,DescripID,width,height){

		//用户输入的宽高添加px
		width=width+"px";
		height=height+"px";

		//定义空数组
		this.Data=[];
		this.ImgLoad=[];
		//通过id获取dom元素
		this.findById=function(objName){
			if(document.getElementById){
				return eval('document.getElementById("'+objName+'")');
			}else{
				return eval('document.all.'+objName);
			}
		};
		//判断图片容器是否为空，不为空设置则它和父容器的宽高
		if(BigPicID){
			if(this.findById(BigPicID)){
				this.findById(BigPicID).style.width=width;
				this.findById(BigPicID).style.height=height;
				if(this.findById(BigPicID).parentNode){
					this.findById(BigPicID).parentNode.style.width=width;
					this.findById(BigPicID).parentNode.style.height=height;
				}
				if(this.findById(BigPicID).parentNode.parentNode){
					this.findById(BigPicID).parentNode.parentNode.style.width=width;
					this.findById(BigPicID).parentNode.parentNode.style.height=height;
				}
			}
		};
		//参数定义
		this.TimeOut=5000;
		var isIE=navigator.appVersion.indexOf("MSIE")!=-1?true:false;
		this.width=width;
		this.height=height;
		this.adNum=0;
		var TimeOutObj;
		if(!FocusPic.childs){FocusPic.childs=[]};
		this.showTime=null;
		this.showSum=10;
		this.ID=FocusPic.childs.push(this)-1;
		//alert("this.ID1:"+this.ID);
		//添加记录
		this.Add=function(BigPic,SmallPic,Title,Url,Descrip){
			var ls;
			this.Data.push([BigPic,SmallPic,Title,Url,Descrip]);
			ls=this.ImgLoad.length;
			this.ImgLoad.push(new Image);
			this.ImgLoad[ls].src=BigPic;
		};
		//时间过期后开始
		this.TimeOutBegin=function(){
			clearInterval(TimeOutObj);
			TimeOutObj=setInterval("com.trs.FocusPic.childs["+this.ID+"].next()",this.TimeOut);
		};
		//时间过期后,清理定时执行
		this.TimeOutEnd=function(){
			clearInterval(TimeOutObj);
		};
		//当前选中
		this.select=function(num){
			if(num>this.Data.length-1){return};
			if(num==this.adNum){return};
			this.TimeOutBegin();
			//添加图片链接
			if(BigPicID){
				if(this.findById(BigPicID)){
					var aObj=this.findById(BigPicID).getElementsByTagName("a")[0];
					aObj.href=this.Data[num][2];
					if(this.aImgY){
						this.aImgY.style.display='none';
						this.aImg.style.position="relative";
						this.aImg.style.left=0;
						this.aImg.style.top=0;
						this.aImg.style.zIndex=0;
					};
					this.aImgY=this.findById('F'+this.ID+'BF'+this.adNum);
					this.aImg=this.findById('F'+this.ID+'BF'+num);
					clearTimeout(this.showTime);
					this.showSum=10;
					//alert("this.ID:"+this.ID);
					this.showTime=setTimeout("com.trs.FocusPic.childs["+this.ID+"].show()",10);
				}
			};
			//添加标题
			if(TitleID){
				if(this.findById(TitleID)){
					this.findById(TitleID).innerHTML="<a>"+this.Data[num][3]+"</a>";
				}
			};
			//添加描述信息
			if(DescripID){
				if(this.findById(DescripID)){
					this.findById(DescripID).innerHTML=this.Data[num][4];
				}
			};
			//更改小图片样式
			if(SmallPicsID){
				if(this.findById(SmallPicsID)){
					var sImg=this.findById(SmallPicsID).getElementsByTagName("span"),i;
					for(i=0;i<sImg.length;i++){
						if(i==num||num==(i-this.Data.length)){
							sImg[i].className="selected";
						}else{
							sImg[i].className="";
						}
					}
				}
			};
			if(this.onselect){
				this.onselect();
			};
			this.adNum=num;
		};
		//绝对定位
		var absPosition=function(obj,parentObj){
			var left=obj.offsetLeft,top=obj.offsetTop,tempObj=obj;
			while(tempObj.id!="VBody"&tempObj.id!="VHtml"&tempObj!=parentObj){
				if(tempObj){
					tempObj=tempObj.offsetParent;
					left+=tempObj.offsetLeft;
					top+=tempObj.offsetTop;
				}
			};
			return {left:left,top:top}
		};
		//显示
		this.show=function(){
			this.showSum--;
			if(!this.aImgY){return false;}
			this.aImgY.style.display='block';
			this.aImg.style.position="absolute";
			var XY=absPosition(this.aImgY,this.findById(BigPicID));
			this.aImg.style.top=XY.top+"px";this.aImg.style.left=XY.left+"px";
			this.aImg.style.display='block';
			if(isIE){
				this.aImg.style.filter="alpha(opacity=0)";
				this.aImg.style.filter="alpha(opacity="+(10-this.showSum)*10+")";
			}else{
				this.aImg.style.opacity=0;this.aImg.style.opacity=(10-this.showSum)*0.1;
			};
			if(this.showSum<=0){
				this.aImgY.style.display='none';
				this.aImg.style.position="relative";
				this.aImg.style.left=0;
				this.aImg.style.top=0;
				this.aImg.style.zIndex=0;
				this.aImgY=null;
			}else{
				this.aImg.style.zIndex=2;
				this.showTime=setTimeout("com.trs.FocusPic.childs["+this.ID+"].show()",10);
			}
		};
		//下一个
		this.next=function(){
			var temp=this.adNum;
			temp++;
			if(temp>=this.Data.length){temp=0;};
			this.select(temp);
		};
		//上一个
		this.pre=function(){
			var temp=this.adNum;
			temp--;
			if(temp<0){
				temp=this.Data.length-1;
			};
			this.select(temp);
		};
		//鼠标放在图片上图片不切换事件
		this.MInStopEvent=function(ObjID){
			if(ObjID){
				if(this.findById(ObjID)){
					if(this.findById(ObjID).attachEvent){
						this.findById(ObjID).attachEvent("onmouseover",Function("com.trs.FocusPic.childs["+this.ID+"].TimeOutEnd()"));
						this.findById(ObjID).attachEvent("onmouseout",Function("com.trs.FocusPic.childs["+this.ID+"].TimeOutBegin()"));
					}else{
						this.findById(ObjID).addEventListener("mouseover",Function("com.trs.FocusPic.childs["+this.ID+"].TimeOutEnd()"),false);
						this.findById(ObjID).addEventListener("mouseout",Function("com.trs.FocusPic.childs["+this.ID+"].TimeOutBegin()"),false);
					}
				}
			}
		};
		//开始
		this.begin=function(){
			this.MInStopEvent(TitleID);
			this.MInStopEvent(SmallPicsID);
			this.MInStopEvent(DescripID);
			this.MInStopEvent(BigPicID);
			this.adNum=0;
			var i,temp="";
			if(BigPicID){
				if(this.findById(BigPicID)){
					var aObj=this.findById(BigPicID).getElementsByTagName("a")[0];
					aObj.style.zoom=1;
					this.findById(BigPicID).style.position="relative";
					for(i=0;i<this.Data.length;i++){
						temp+='<img src="'+this.Data[i][0]+'" id="F'+this.ID+'BF'+i+'" style="display:'+(i==this.adNum?'block':'none')+'" galleryimg="no"'+(this.width?' width="'+this.width+'"':'')+(this.height?' height="'+this.height+'"':'')+' alt="'+this.Data[i][3]+'" />'
					};
					aObj.innerHTML=temp;
				}
			};
			if(SmallPicsID){
				if(this.findById(SmallPicsID)){
					temp="";
					for(i=0;i<this.Data.length;i++){
						temp+="<span onmouseover=\"com.trs.FocusPic.childs["+this.ID+"].select("+i+")\" id=focus_small"+SmallPicsID+i+(this.adNum==i?' class="selected"':"")+"></span>";
					};
					this.findById(SmallPicsID).innerHTML=temp;
				}
			};
			this.TimeOutBegin();
			this.findById(BigPicID).getElementsByTagName("a")[0].href=this.Data[0][2];
			this.findById(TitleID).innerHTML="<a href=\""+this.Data[0][2]+"\" target=\"_blank\">"+this.Data[0][3]+"</a>";
			if(this.findById(DescripID))this.findById(DescripID).innerHTML=this.Data[0][4];
		};
	};
	com.trs.FocusPic = FocusPic;
})();