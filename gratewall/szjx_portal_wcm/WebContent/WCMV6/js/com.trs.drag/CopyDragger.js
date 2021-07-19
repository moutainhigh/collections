$package("com.trs.drag");

$import('com.trs.logger.Logger');
com.trs.drag.CopyDragger=Class.create('drag.CopyDragger');
com.trs.drag.CopyDragger.prototype={
	initialize : function(o, oRoot, minX, maxX, minY, maxY, bSwapHorzRef, bSwapVertRef, fXMapper, fYMapper){
		this.logger			= $log();
		this.logger.info("Darg.init");
		this.handle			= this.oldHandle		= o;
		this.root 			= this.oldRoot			= oRoot && oRoot != null ? oRoot : o ;
		this.logger.debug("this.root:"+this.root);
		this.hmode			= bSwapHorzRef ? false : true ;
		this.vmode			= bSwapVertRef ? false : true ;
		if (this.hmode  && isNaN(parseInt(this.root.style.left  ))) this.root.style.left   = "0px";
		if (this.vmode  && isNaN(parseInt(this.root.style.top   ))) this.root.style.top    = "0px";
		if (!this.hmode && isNaN(parseInt(this.root.style.right ))) this.root.style.right  = "0px";
		if (!this.vmode && isNaN(parseInt(this.root.style.bottom))) this.root.style.bottom = "0px";
		this.minX			= typeof minX != 'undefined' ? minX : null;
		this.minY			= typeof minY != 'undefined' ? minY : null;
		this.maxX			= typeof maxX != 'undefined' ? maxX : null;
		this.maxY			= typeof maxY != 'undefined' ? maxY : null;
		this.xMapper 		= fXMapper ? fXMapper : null;
		this.yMapper 		= fYMapper ? fYMapper : null;
		this.onDragStart	= new Function();
		this.onDragEnd		= new Function();
		this.onDrag			= new Function();
		o.onmousedown		= this.createCopy.bindAsEventListener(this);
		o.onmousemove		= null;
	},
	createCopy : function(ev){
		this.logger.info("Drag.init....");
		var ev				= window.event || ev;
		if((ev.button||ev.which)!=1){
			Event.stop(ev);
			return false;
		}
		var o				= this.handle;
		o.onmousemove		= this.createCopy.bindAsEventListener(this);
		o.onmouseup			= function(){
			this.onmousemove= null;
		}
		if( ev.type != 'mousemove'){
			Event.stop(ev);
			return false;
		}
		var oCopy			= (this.copy || Prototype.K)(o , this.root);
		this.root			= oCopy[1];
		oCopy				= oCopy[0];
		this.handle			= oCopy;
		//document
		
		o.onmousedown 		= null;
		o.onmousemove 		= null;
		o.mouseup			= null;
		oCopy.onmousedown 	= this.start.bindAsEventListener(this);
		oCopy.onmousemove 	= null;
		if(oCopy.fireEvent){
			oCopy.fireEvent("onmousedown" , ev);
			oCopy.fireEvent("onmousemove" , ev);
		}
		else{
			oCopy.onmousedown(ev);
		}
		Event.stop(ev);
	},
	copy : function(o,root){
		var eCopy=document.createElement('DIV');
		eCopy.innerHTML=root.innerHTML;
		eCopy.style.position='absolute';
		document.body.appendChild(eCopy);
		Position.clone(root,eCopy);
		return [eCopy,eCopy];
	},
	destroyCopy : function(){
		var o	= this.root;
		if(o){
			try{
				o.parentNode.removeChild(o);
				delete o;
			}catch(err){}
		}
		o		= this.handle;
		if(o){
			try{
				o.parentNode.removeChild(o);
				delete o;
			}catch(err){}
		}
	},
	start : function(ev){
		this.logger.info("Darg.start");
		ev					= window.event || ev;
		if(this.root.style.position!='absolute'){
			Position.absolutize(this.root);
		}
		var y 				= parseInt(this.vmode ? this.root.style.top  : this.root.style.bottom);
		var x 				= parseInt(this.hmode ? this.root.style.left : this.root.style.right );
		this.onDragStart(x, y);
		this.lastMouseX		= Event.pointerX(ev);
		this.lastMouseY		= Event.pointerY(ev);

		if (this.hmode) {
			if (this.minX != null)	this.minMouseX	= Event.pointerX(ev) - x + this.minX;
			if (this.maxX != null)	this.maxMouseX	= this.minMouseX + this.maxX - this.minX;
		}
		else {
			if (this.minX != null) this.maxMouseX 	= -this.minX + Event.pointerX(ev) + x;
			if (this.maxX != null) this.minMouseX 	= -this.maxX + Event.pointerX(ev) + x;
		}

		if (this.vmode) {
			if (this.minY != null)	this.minMouseY	= Event.pointerY(ev) - y + this.minY;
			if (this.maxY != null)	this.maxMouseY	= this.minMouseY + this.maxY - this.minY;
		}
		else {
			if (this.minY != null) this.maxMouseY 	= -this.minY + Event.pointerY(ev) + y;
			if (this.maxY != null) this.minMouseY 	= -this.maxY + Event.pointerY(ev) + y;
		}
		var	o				= this.handle;
		this.document_mousemove	= this.drag.bindAsEventListener(this);
		this.document_mouseup	= this.end.bindAsEventListener(this);
		Event.observe(document.body,'mousemove',this.document_mousemove);
		Event.observe(document.body,'mouseup',this.document_mouseup);
		Event.stop(ev);
	},
	drag : function(ev){
		this.logger.info("Darg");
		ev					= window.event || ev;
		if((ev.button||ev.which)!=1){
			return this.end(ev);
		}
		var ey				= Event.pointerY(ev);
		var ex				= Event.pointerX(ev);
		var y 				= parseInt(this.vmode ? this.root.style.top  : this.root.style.bottom);
		var x 				= parseInt(this.hmode ? this.root.style.left : this.root.style.right );
		var nx, ny;
		if (this.minX != null) ex = this.hmode ? Math.max(ex, this.minMouseX) : Math.min(ex, this.maxMouseX);
		if (this.maxX != null) ex = this.hmode ? Math.min(ex, this.maxMouseX) : Math.max(ex, this.minMouseX);
		if (this.minY != null) ey = this.vmode ? Math.max(ey, this.minMouseY) : Math.min(ey, this.maxMouseY);
		if (this.maxY != null) ey = this.vmode ? Math.min(ey, this.maxMouseY) : Math.max(ey, this.minMouseY);

		nx 					= x + ((ex - this.lastMouseX) * (this.hmode ? 1 : -1));
		ny 					= y + ((ey - this.lastMouseY) * (this.vmode ? 1 : -1));
		if (this.xMapper)		nx = this.xMapper(y);
		else if (this.yMapper)	ny = this.yMapper(x);
		this.root.style[this.hmode ? "left" : "right"] = nx + "px";
		this.root.style[this.vmode ? "top" : "bottom"] = ny + "px";
		this.lastMouseX		= ex;
		this.lastMouseY		= ey;
		this.onDrag(nx, ny, [ex,ey], ev);
		Event.stop(ev);
		return false;
	},
	end : function(ev){
		this.logger.info("Darg End");
		var	o					= this.handle;
		ev						= window.event || ev;
		o.onmousedown	   		= null;
		Event.stopObserving(document.body,'mousemove',this.document_mousemove);
		Event.stopObserving(document.body,'mouseup',this.document_mouseup);
		this.destroyCopy();
		this.onDragEnd(parseInt(this.root.style[this.hmode ? "left" : "right"]),parseInt(this.root.style[this.vmode ? "top" : "bottom"]));
		this.oldHandle.onmousedown	= this.createCopy.bindAsEventListener(this);
		this.root					= this.oldRoot;
		this.handle					= this.oldHandle;
		Event.stop(ev);
		return false;
	}
}