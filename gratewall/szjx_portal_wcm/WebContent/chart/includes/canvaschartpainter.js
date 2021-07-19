
function CanvasChartPainterFactory() {
	return new CanvasChartPainter();
}


function CanvasChartPainter() {
	this.base = AbstractChartPainter;
};


CanvasChartPainter.prototype = new AbstractChartPainter;


CanvasChartPainter.prototype.create = function(el) {
	while (el.firstChild) { el.removeChild(el.lastChild); }

	this.container = el;
	var title = document.createElement('div');
	this.container.appendChild(title);
	this.title = title;	

	var cbody = document.createElement('div');
	cbody.className = 'cbody';
	this.container.appendChild(cbody);
	this.el = cbody;	

	this.w = el.clientWidth;
	this.h = el.clientHeight;
	this.canvas = document.createElement('canvas');
	this.canvas.width  = this.w;
	this.canvas.height = this.h;
	this.canvas.style.width  = this.w + 'px';
	this.canvas.style.height = this.h + 'px';

	this.el.appendChild(this.canvas);
	
	/* Init explorercanvas emulation for IE */
	if ((!this.canvas.getContext) && (typeof G_vmlCanvasManager != "undefined")) {
		this.canvas = G_vmlCanvasManager.initElement(this.canvas);
	}

	this.top = 0;
};


CanvasChartPainter.prototype.init = function(xlen, ymin, ymax, xgd, ygd, bLegendLabels) {
	this.ctx = this.canvas.getContext('2d');

	this.chartx = 0;
	this.chartw	= this.w;
	this.charth	= this.h;
	this.charty = 0;
	
	this.xlen = xlen;
	this.ymin = ymin;
	this.ymax = ymax;
	this.xgd  = xgd;
	this.ygd  = ygd;

	this.calc(this.chartw, this.charth, xlen, ymin, ymax, xgd, ygd);
};

CanvasChartPainter.prototype.init2 = function() {
	this.ctx = this.canvas.getContext('2d');

	this.chartx = 0;
	this.chartw	= this.w;
	this.charth	= this.h;
	this.charty = 0;
}

CanvasChartPainter.prototype.drawTitle = function(sTitle, sSubtitle) {
	var title, main_title, sub_title;

	title = this.title;
	title.className = 'title';
	main_title = document.createElement('span');
	main_title.className = 'title_main';
	main_title.innerHTML = sTitle;
	title.appendChild(main_title);

	if(sSubtitle != null) {
		sub_title = document.createElement('span');
		sub_title.className = 'title_sub';
		sub_title.innerHTML = sSubtitle;
		title.appendChild(sub_title);
	}
	
	title.style.left = '0px';
	title.style.top  = '0px';
	
	/* Recalculate chart height and position based on the titles*/
	this.h = this.charth = this.h - (this.title.offsetHeight);
	this.top = this.title.offsetHeight;
	this.canvas.width  = this.w;
	this.canvas.height = this.h;
	this.canvas.style.width  = this.w + 'px';
	this.canvas.style.height = this.h + 'px';//*/
	
	this.calc(this.chartw, this.charth, this.xlen, this.ymin, this.ymax, this.xgd, this.ygd);//*/
};

CanvasChartPainter.prototype.drawLegend = function(series) {
	var legend, list, item, label;

	legend = document.createElement('div');
	legend.className = 'legend';
	legend.style.position = 'absolute';
	list = document.createElement('ul');

	for (i = 0; i < series.length; i++) {
		item = document.createElement('li');
		item.style.color = series[i].color;
		label = document.createElement('span');
		label.appendChild(document.createTextNode(series[i].label));
		label.style.color = 'black';
		item.appendChild(label);
		list.appendChild(item);
	}
	legend.appendChild(list);
	this.el.appendChild(legend);
	legend.style.right = '0px';
	legend.style.top  = this.charty + (this.charth / 2) - (legend.offsetHeight / 2) + 'px';
	this.legend = legend;
	
	/* Recalculate chart width and position based on labels and legend */
	this.chartw	= this.w - (this.legend.offsetWidth + 5);
	
	this.calc(this.chartw, this.charth, this.xlen, this.ymin, this.ymax, this.xgd, this.ygd);
};


CanvasChartPainter.prototype.drawVerticalAxis = function(ygd, precision) {
	var axis, item, step, y, ty, n, yoffset, value, multiplier, w, items, pos;

	/* Calculate step size and rounding precision */
	multiplier = Math.pow(10, precision);
	step       = this.range / (ygd - 1);

	/* Create container */
	axis = document.createElement('div');
	axis.style.position = 'absolute';
	axis.style.left  = '0px';
	axis.style.top   = this.top + 'px';
	axis.style.textAlign = 'right';
	axis.style.border = '0px solid blue';
	this.el.appendChild(axis);

	/* Draw labels and points */
	this.ctx.fillStyle = 'black';
	w = 0;
	items = new Array();
	for (n = 0, i = this.ymax; (i > this.ymin) && (n < ygd - 1); i -= step, n++) {
		item = document.createElement('span');
		value = parseInt(i * multiplier) / multiplier;
		item.appendChild(document.createTextNode(value));
		axis.appendChild(item);
		items.push([i, item]);
		if (item.offsetWidth > w) { w = item.offsetWidth; }
	}

	/* Draw last label and point (lower left corner of chart) */
	item = document.createElement('span');
	item.appendChild(document.createTextNode(this.ymin));
	axis.appendChild(item);
	items.push([this.ymin, item]);
	if (item.offsetWidth > w) { w = item.offsetWidth; }
	
	/* Set width of container to width of widest label */
	axis.style.width = w + 'px';
	
	/* Recalculate chart width and position based on labels and legend */
	this.chartx = w + 5;
	this.charty = item.offsetHeight / 2;
	this.charth = this.h - ((item.offsetHeight * 1.5) + 5);
	this.chartw	= this.w - (((this.legend)?this.legend.offsetWidth:0) + w + 10);
	this.calc(this.chartw, this.charth, this.xlen, this.ymin, this.ymax, this.xgd, this.ygd);
	
	/* Position labels on the axis */
	n          = this.range / this.charth;
	yoffset    = (this.ymin / n);
	for (i = 0; i < items.length; i++) {
		item = items[i][1];
		pos = items[i][0];
		if (pos == this.ymin) { y = this.charty + this.charth - 1; }
		else { y = this.charty + (this.charth - (pos / n) + yoffset); }
		this.ctx.fillRect(this.chartx - 5, y, 5, 1);
		ty = y - (item.offsetHeight/2);
		item.style.position = 'absolute';
		item.style.right = '0px';
		item.style.top   = ty + 'px';
}	};


CanvasChartPainter.prototype.drawHorizontalAxis = function(xlen, labels, axisCount, interval, precision) {
	var axis, item, step, x, tx, n, multiplier;

	this.xlabels = labels;
	var labelsLen = labels.length; 

	/* Calculate offset, step size and rounding precision */
	multiplier = Math.pow(10, precision);

	/* Create container */
	axis = document.createElement('div');
	axis.style.position = 'absolute';
	axis.style.left   = '0px';
	axis.style.top    = (this.top + this.charty + this.charth + 5) + 'px';
	axis.style.width  = this.w + 'px';
	this.el.appendChild(axis);

	/* Draw labels and points */
	this.ctx.fillStyle = 'black';
	n = this.chartw / (axisCount > 1 ? (axisCount - 1) : 1);
	var bTooNarrow = n < 80;
	for (i = 0; i < axisCount; i++) {
		item = document.createElement('span');
		item.appendChild(document.createTextNode(labels[i * interval] || ''));
		axis.appendChild(item);
		x = this.chartx + (n * i);
		tx = x - (item.offsetWidth/2);
		item.style.position = 'absolute';
		item.style.left = tx + 'px';
		item.style.top  = ((bTooNarrow && i%2 == 1) ? 12 : 0) + 'px';
		this.ctx.fillRect(x, this.charty + this.charth, 1, 5);
	}
};


CanvasChartPainter.prototype.drawAxis = function() {
	this.ctx.fillStyle = 'black';
	this.ctx.fillRect(this.chartx, this.charty, 1, this.charth-1);
	this.ctx.fillRect(this.chartx, this.charty + this.charth - 1, this.chartw+1, 1);
};


CanvasChartPainter.prototype.drawBackground = function() {
	//this.ctx.fillStyle = 'white';
	//this.ctx.fillRect(0, 0, this.w, this.h);
};


CanvasChartPainter.prototype.drawChart = function() {
	this.ctx.fillStyle = 'silver';
	if (this.xgrid) {
		for (i = this.xgrid; i < this.chartw; i += this.xgrid) {
			this.ctx.fillRect(this.chartx + i, this.charty, 1, this.charth-1);
	}	}
	if (this.ygrid) {
		for (i = this.charth - this.ygrid; i > 0; i -= this.ygrid) {
			this.ctx.fillRect(this.chartx + 1, this.charty + i, this.chartw, 1);
}	}	};


CanvasChartPainter.prototype.drawArea = function(color, values) {
	var i, len, x, y, n, yoffset;

	/* Determine distance between points and offset */
	n = this.range / this.charth;
	yoffset = (this.ymin / n);

	len = values.length;
	if (len) {
		this.ctx.fillStyle = color;

		/* Begin line in lower left corner */
		x = this.chartx + 1;
		this.ctx.beginPath();
		this.ctx.moveTo(x, this.charty + this.charth - 1);

		/* Determine position of first point and draw it */
		y = this.charty + this.charth - (values[0] / n) + yoffset;
		this.ctx.lineTo(x, y);

		/* Draw lines to succeeding points */
		for (i = 1; i < len; i++) {
			y = this.charty + this.charth - (values[i] / n) + yoffset;
			x += this.xstep;
			this.ctx.lineTo(x, y);
		}

		/* Close path and fill it */
		this.ctx.lineTo(x, this.charty + this.charth - 1);
		this.ctx.closePath();
		this.ctx.fill();
}	};


CanvasChartPainter.prototype.drawLine = function(color, values, sname, seriesIndex, totleAxisLen, seriesKey, pointerVisible) {
	var i, len, x, y, n, yoffset;

	/* Determine distance between points and offset */
	n = this.range / this.charth;
	yoffset = (this.ymin / n);

	valuesLen = values.length;
	if (valuesLen) {
		this.ctx.lineWidth   = 2.5;
		this.ctx.strokeStyle = color;

		/* Determine position of first point and draw it */
		var firstVal = values[0];
		var bIsSeries = false;
		var nCurrXlabelIndex = 0;
		if(firstVal.constructor == Array) {
			bIsSeries = true;
			firstVal = firstVal[1];
		}
		x = this.chartx + 1;
		y = this.charty + this.charth - (firstVal / n) + yoffset;
		this.ctx.beginPath();
		var stepDelta = this.chartw / totleAxisLen;
		
		/* Draw lines to succeeding points */
		var bMapedValue = ((seriesKey != null) && (seriesKey.length > 0) && typeof(firstVal) == 'object');
		for (i = 0; i < valuesLen; i++) {
			var val = values[i];
			if(bIsSeries) {
				nCurrXlabelIndex += val[0];
				x += stepDelta * val[0];
				val = val[1];
			}else{
				nCurrXlabelIndex = i;
				x = this.chartx + 1 + stepDelta * i;
			}
			val = bMapedValue ? val[seriesKey] : val;
			var xlabel = this.xlabels[nCurrXlabelIndex];
			if(xlabel == null) {
				continue;
			}
			y = this.charty + this.charth - (val / n) + yoffset;
			
			this.ctx.lineTo(x, y);
			
			if(pointerVisible == true) {
				this.ctx.arc(x, y, 2.5, 0, 2*Math.PI/180, true);//, 2*Math.PI);
				this.ctx.moveTo(x, y);
				this.embedAlt(sname, x, y, xlabel, val, seriesIndex + '_' + i);
			}else{
				this.ctx.moveTo(x, y);
			}

		}

		/* Stroke path */
		this.ctx.stroke();
}	};

CanvasChartPainter.prototype.embedAlt = function(sname, x, y, label, val, flag){
	var id = 'value_tip_' + flag;
	var alt = document.getElementById(id);
	if(alt != null) {
		return;
	}
	//else
	alt = document.createElement('span');
	alt.id = id;
	alt.style.display = '';
	alt.className = 'value_alt';
	alt.style.position = 'absolute';
	this.el.appendChild(alt);
	alt.style.left = (x - 7) + 'px';
	alt.style.top = (this.top + y - 8) + 'px';
	alt.innerHTML = '&nbsp;';
	var painter = this;
	alt.onmouseover = function(event){
		painter.showValuePanel(event, sname, label, flag, val);
	};
	alt.onmouseout = function(event){
		painter.hideValuePanel(event, flag);
	};
	
}
CanvasChartPainter.prototype.showValuePanel = function(event, sname, label, flag, val){
	var id = 'value_panel_' + flag;
	var panel = document.getElementById(id);
	if(panel == null) {
		panel = document.createElement('div');
		panel.id = id;
		event = event || window.event;
		var x = event.pageX || (event.clientX +
			(document.documentElement.scrollLeft || document.body.scrollLeft));
		var y = event.pageY || (event.clientY +
			(document.documentElement.scrollTop || document.body.scrollTop));
		//x -= this.el.offsetLeft;
		//y -= this.el.offsetTop;
		panel.className = 'value_panel';
		panel.style.position = 'absolute';
		document.body.appendChild(panel);
		panel.style.left = (x - 10) + 'px';
		panel.style.top = (y - 40) + 'px';
		panel.innerHTML = sname + '<br>' + '(' + val + ', ' + label + ')';
	}
	panel.style.display = '';
}
CanvasChartPainter.prototype.hideValuePanel = function(event, flag){
	var panel = document.getElementById('value_panel_' + flag);
	if(panel == null) {
		return;
	}
	panel.style.display = 'none';
}
CanvasChartPainter.prototype.drawBars = function(color, values, xlen, xoffset, width) {
	var i, len, x, y, n, yoffset;

	/* Determine distance between points and offset */
	n = this.range / this.charth;
	yoffset = (this.ymin / n);

	len = values.length;
	if (len > xlen) { len = xlen; }
	if (len) {
		this.ctx.fillStyle = color;

		/* Determine position of each bar and draw it */
		x = this.chartx + xoffset + 1;
		for (i = 0; i < len; i++) {
			y = this.charty + this.charth - (values[i] / n) + yoffset;

			this.ctx.beginPath();
			this.ctx.moveTo(x, this.charty + this.charth-1);
			this.ctx.lineTo(x, y );
			this.ctx.lineTo(x+width, y);
			this.ctx.lineTo(x+width, this.charty + this.charth-1);
			this.ctx.closePath();
			this.ctx.fill();

			x += this.xstep;
}	}	};

CanvasChartPainter.prototype.ANGLE_OFFSET = -1 * Math.PI/3;
CanvasChartPainter.prototype.m_currAngle = 0;
CanvasChartPainter.prototype.drawPie = function(series){
	if(series == null || !(series.length > 0)) {
		return ;
	}
	//TODO title of pie
	var x, y, r;
	x = this.w/2;
	y = this.h/2;
	r = this.w * 0.242;
	//series: [{name, value, percent, angle, style{color, show_value, value_style}}]
	var angle, entity;
	this.m_currAngle = this.ANGLE_OFFSET;
	for (var i = 0; i < series.length; i++){
		entity	= series[i];
		angle	= entity['angle'] + this.ANGLE_OFFSET;
		this.drawSector(x, y, r, this.m_currAngle, angle, entity);
		
		this.m_currAngle = angle;
		if(this.m_currAngle >= Math.PI * 2) {
			break;
		}
	}
	//reset
	this.m_currAngle = 0;
}
CanvasChartPainter.prototype.drawSector = function(x, y, r, sa, ea, series){
	var alpha, apart, style;
	alpha = (sa + ea)/2;
	apart = r/200;
	style = series['style'];
	x = x + apart * (Math.cos(alpha));
	y = y + apart * (Math.sin(alpha));
	this.ctx.fillStyle = style['color'];
	this.ctx.beginPath();
	this.ctx.moveTo(x, y);
	this.ctx.lineTo(x, y);
	this.ctx.arc(x, y, r, sa, ea, false); // Outer circle

	this.ctx.fill();
	//œ‘ æ÷µ
	if(style['show_value'] != true) {
		return;
	}
	//else
	if(style['value_style'] == 1) {// ChartStyle.PIE_VALUE_INNER

		var m = x + r * (Math.cos(alpha))/2;
		var n = y + r * (Math.sin(alpha))/2;
		this.embedPieTip(m, n, series['name'], series['percent'], series['value'], 1, true, (m < x), (n > y));
	}else{ // ChartStyle.PIE_VALUE_OUTER (used as default)
		
		var m = x + r * (Math.cos(alpha));
		var n = y + r * (Math.sin(alpha));
		this.ctx.strokeStyle = 'silver';
		this.ctx.lineWidth = 2;
		this.ctx.moveTo(m, n);
		m += r * 0.618 * ( m > x ? 1 : -1);
		n += ( n > y ? 1 : -1);
		r += r/15;
		var p = x + r * (Math.cos(alpha));
		var q = y + r * (Math.sin(alpha));
		this.ctx.lineTo(p, q);
		p += r*0.425*(m>x?1:-1);
		this.ctx.lineTo(p, q);
		this.ctx.stroke();
		this.embedPieTip(p, q, series['name'], series['percent'], series['value'], 1, false, (m < x));
	}
	
}
CanvasChartPainter.prototype.embedPieTip = function(x, y, title, percent, val, flag, bInner, bShrinkedX, bShrinkedY){
	var id = 'value_panel_pie_' + flag;
	var alt = document.getElementById(id);
	if(alt == null) {
		var alt = document.createElement('span');
		alt.className = 'value_panel_pie';
		alt.style.position = 'absolute';
		this.el.appendChild(alt);
	}
	alt.innerHTML = title + '&nbsp;' + percent + '&nbsp;&nbsp;(' + val + ')';
	//alert([alt.outerHTML, alt.offsetWidth])
	if(bInner == true) {
		alt.style.left = (x + (bShrinkedX ? -1 : -0.5) * alt.offsetWidth / 2) + 'px';
	}else{
		alt.style.left = (bShrinkedX ? (x - alt.offsetWidth) : x ) + 'px';
	}
	alt.style.top = this.top + (y - 10) + 'px';
	
	//alert(alt.offsetWidth);
}
