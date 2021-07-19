var ChartStyle = {
	DEFAULT : ['#E5B059', '#4F87E9', '#E14F14'],
	SMOKE : ['#F6F6F6', '#DEDEFF', '#A5A5FF', '#8080FF', '#4040FF'],
	EARTH: ['#FFEBCC', '#FFDBA3', '#FFCA7A', '#FFBA51', '#FFA928'],
	PIE_VALUE_OUTER : 0, PIE_VALUE_INNER : 1,
	fill : function(_style){
		var result = [];
		var arr = _style || ChartStyle.DEFAULT;
		for (var i = 0; i < arr.length; i++){
			result[i] = arr[i];
		}
		return result;
	},
	fadeColor : function(_color){
		var color = _color;
		if(color == null || color.charAt(0) != '#') {
			return _color;
		}
		color = parseInt(color.substr(1), 16) - 80;

		return '#' + color.toString(16);
	}
}


var CHART_LINE    =  1;
var CHART_AREA    =  2;
var CHART_BAR     =  3;
var CHART_STACKED =  4;
var CHART_PIE	  =  5;

/*----------------------------------------------------------------------------\
|                                    Chart                                    |
|- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -|
| Chart class, control class that's used to represent a chart. Uses a painter |
| class for the actual drawing.  This is the only  class that should be  used |
| directly, the other ones are internal.                                      |
\----------------------------------------------------------------------------*/

function Chart(el, chartStyle) {
	this._cont             = el;
	this._yMin             = null;
	this._yMax             = null;
	//this._xGridDensity     = 0;
	this._yGridDensity     = 0;
	this._flags            = 0;
	this._series           = new Array();
	this._labelPrecision   = 0;
	this._horizontalLabels = new Array();
	this._barWidth         = 10;
	this._barDistance      = 2;
	this._bars             = 0;
	this._showLegend       = true;
	
	/*
	 * Determine painter implementation to use based on what's available and
	 * supported. CanvasChartPainter is the prefered one, JsGraphicsChartPainter
	 * the fallback one as it works in pretty much any browser. The
	 * SVGChartPainter implementation one will only be used if set explicitly as
	 * it's not up to pair with the other ones.
	 */
	if ((typeof CanvasChartPainterFactory != 'undefined') && (window.CanvasRenderingContext2D)) {
		this._painterFactory = CanvasChartPainterFactory;
	}
	else if (typeof JsGraphicsChartPainterFactory != 'undefined') {
		this._painterFactory = JsGraphicsChartPainterFactory;
	}
	else { this._painterFactory = null; }

	//ge gfc add @ 2007-12-7 14:10
	this._dtStart = null;
	this._dtEnd = null;
	this._interval = 1;

	this._style = ChartStyle.fill(chartStyle || ChartStyle.DEFAULT);
	this._colorBin = [];
	this._seriesKey = null;
	this._title = null;
	this._subtitle = null;
	this._dateTimePartial = CMyDateTime.DAY;

	this._pieValueStyle = ChartStyle.PIE_VALUE_OUTER;
}


Chart.prototype.setPainterFactory = function(f) {
	this._painterFactory = f;
};


Chart.prototype.setVerticalRange = function(min, max) {
	this._yMin = min;
	this._yMax = max;
};


Chart.prototype.setLabelPrecision = function(precision) {
	this._labelPrecision = precision;
};


Chart.prototype.setShowLegend = function(b) {
	this._showLegend = b;
};


Chart.prototype.setVerticalGridDensity = function(vertical) {
	//this._xGridDensity = horizontal;
	this._yGridDensity = vertical;
};


Chart.prototype.setHorizontalLabels = function(labels, _nPart) {
	this._horizontalLabels = labels;
	if(_nPart != null) {
		this._dateTimePartial = _nPart;
	}
};


Chart.prototype.setDefaultType = function(flags) {
	this._flags = flags;
};


Chart.prototype.setBarWidth = function(width) {
	this._barWidth = width;
};


Chart.prototype.setBarDistance = function(distance) {
	this._barDistance = distance;
};

//ge gfc add @ 2007-12-7 14:10
Chart.prototype.setDateTimePartial = function( _nPart){
	this._dateTimePartial = _nPart || CMyDateTime.DAY;
}
Chart.prototype.setPointerVisible = function(_bPointerVisible){
	this._pointerVisible = _bPointerVisible;
}
Chart.prototype.setDateTimeRange = function(_sStart, _sEnd, _nMaxXGridLen, _nPart){
	this._dtStart = CMyDateTime.parseDate(_sStart);
	this._dtEnd = CMyDateTime.parseDate(_sEnd);	
	this._maxXGridLen = (_nMaxXGridLen && _nMaxXGridLen > 0) ?  _nMaxXGridLen : 0;
	
	//alert([_sStart, _sEnd, this._dtStart, this._dtEnd])
	if(_nPart != null) {
		this._dateTimePartial = _nPart;
	}
	var grids = CMyDateTime.dateDiff(this._dtEnd, this._dtStart, this._dateTimePartial);
	if(grids < 0 || (_nPart == CMyDateTime.DAY && grids == 0)) {
		throw new Error('start-date[' + _sStart + '] cannot be later than end-date[' + _sEnd + ']');
	}
	if( this._maxXGridLen > 0 && (grids / this._interval > this._maxXGridLen)) {
		this._interval = Math.ceil(grids / this._maxXGridLen)
	}
	if(!this._yGridDensity || this._yGridDensity <= 0) {
		this._yGridDensity = Math.floor(grids/2);
	}
	var arHLabels = CMyDateTime.makeRangeLabels(this._dtEnd, grids, this._dateTimePartial);

	this.setHorizontalLabels(arHLabels);
}

Chart.prototype.add = function(label, values, color, flags) {
	var o, offset;

	if (!flags) { flags = this._flags; }
	if ((flags & CHART_BAR) == CHART_BAR) { offset = this._barDistance + this._bars * (this._barWidth + this._barDistance); this._bars++; }
	else { offset = 0; }

	//ge gfc add 2007-12-7 14:37
	//TODO shift to auto judged from some parasm e.g. CHART_SERIES
	if(flags == CHART_LINE) {
		values = Chart.rearrangeDataSeriesData(values, this._dtStart, this._dateTimePartial);
		//alert(values)
	}

	//ge gfc add @ 2007-12-10 14:06 process the stroke-color
	if(color == null || !(color.length > 0)) {
		if(this._style.length == 0) {
			while(this._colorBin.length > 0){
				this._style.push(ChartStyle.fadeColor(this._colorBin.pop()));
			}
		}

		color = this._style.pop();
		this._colorBin.push(color);
	}

	o = new ChartSeries(label, color, values, flags, offset);

	this._series.push(o);
}

Chart.prototype.setPieData = function(_pieData){
	if(_pieData == null || !(_pieData.length > 0)) {
		throw new Error('need an array like "[[\'Bean\', 10.5, \'#FFA928\']" for arranging pie-data!');
	}
	//else
	for (var i = 0; i < _pieData.length; i++){
		var entity = _pieData[i];
		this.add(entity[0], [entity[1]], entity[2]);
	}
}
Chart.prototype.setPieValueStyle = function(_pieValueStyle){
	this._pieValueStyle = _pieValueStyle;
}
Chart.prototype.setInterval = function(_nInterval){
	this._interval = _nInterval || 1;
}

Chart.prototype.setTitle = function(_sTitle, _sSubtitle){
	this._title = _sTitle;
	this._subtitle = _sSubtitle;
}
Chart.prototype.setSeriesKey = function(_sKey){
	this._seriesKey = _sKey;
}

Chart.rearrangeDataSeriesData = function(_arRawData, _dtStart, _nPart){
	if(_arRawData == null || !(_arRawData.length > 0)) {
		return null;
	}
	if(_arRawData[0].constructor != Array) {
		return _arRawData;
	}
	//else
	var ar = _arRawData;
	//var s = '' + _dtStart + '\n';
	var result = [];
	var former = 0;
	for (var i = 0; i < ar.length; i++){
		var date = CMyDateTime.parseDate(ar[i][0]);
		if(!date) {
			continue;
		}
		//else
		var diff = CMyDateTime.dateDiff(date, _dtStart, _nPart);//86400000;
		var delta = (diff - former);
		result[i] = [delta, ar[i][1]];
		former = diff;
		//s += '(' + diff + ')' + ar[i][0] + ': ' + ar[i][1] + '\n';

	}
	//alert(s)
	return result;
}

Chart.prototype.draw = function() {
	if(this._flags == CHART_PIE) {
		this._drawPie();
		return;
	}
	var painter, i, o, o2, len, xlen, ymin, ymax, series, type, self, bLabels;
	
	if (!this._painterFactory) { return; }
	
	/* Initialize */
	series = new Array();
	stackedSeries = new Array();
	xlen = 0;
	ymin = this._yMin;
	ymax = this._yMax;

	/* Separate stacked series (as they need processing). */
	for (i = 0; i < this._series.length; i++) {
		o = this._series[i]
		if ((o.flags & CHART_STACKED) == CHART_STACKED) { series.push(o); }
	}

	/* Calculate values for stacked series */
	for (i = series.length - 2; i >= 0; i--) {
		o  = series[i].values;
		o2 = series[i+1].values;
		len = (o2.length > o.length)?o2.length:o.length;
		for (j = 0; j < len; j++) {
			if ((o[j]) && (!o2[j])) { continue; }
			if ((!o[j]) && (o2[j])) { o[j] = o2[j]; }
			else { o[j] = parseInt(o[j]) + parseFloat(o2[j]); }
	}	}

	/* Append non-stacked series to list */
	for (i = 0; i < this._series.length; i++) {
		o = this._series[i];
		if ((o.flags & CHART_STACKED) != CHART_STACKED) { series.push(o); }
	}

	/* Determine maximum number of values, ymin and ymax */
	var bIsSeries = false;
	var bMapedValue = ((this._seriesKey != null) && (this._seriesKey.length > 0));
	try{
		firstVal = series[0].values[0];
		if(firstVal.constructor == Array) {
			bIsSeries = true;
			firstVal = firstVal[1];
		}
		bMapedValue = bMapedValue && (typeof(firstVal) == 'object');
	}catch(err){
		//just skip it;
	}
	for (i = 0; i < series.length; i++) {
		o = series[i];
		if (o.values.length > xlen) { xlen = o.values.length; }
		for (j = 0; j < o.values.length; j++) {
			var val = o.values[j];
			if(bIsSeries) {
				val = val[1]; 
			}
			val = bMapedValue ? val[this._seriesKey] : val;
			if ((val < ymin) || (ymin == null))  {
				ymin = val; 
			}
			if (val > ymax) { 
				ymax = val; 
			}
		}	
	}

	/*
	 * For bar only charts the number of charts is the same as the length of the
	 * longest series, for others combinations it's one less. Compensate for that
	 * for bar only charts.
	 */
	if (this._series.length == this._bars) {
		xlen++;
		//this._xGridDensity++;
	}

	/*
	 * Determine whatever or not to show the legend and axis labels
	 * Requires density and labels to be set.
	 */
	var axisCount = (this._horizontalLabels.length - 1) / this._interval;
	var temp = Math.ceil(axisCount);
	if(axisCount != temp) {
		axisCount = temp;
	}//*/
	axisCount += 1;

	bLabels = ((this._yGridDensity) && (this._horizontalLabels.length >= axisCount));

	/* Create painter object */
	painter = this._painterFactory();
	painter.create(this._cont);

	/* Initialize painter object */
	painter.init(xlen, ymin, ymax, axisCount, this._yGridDensity, bLabels);

	/* Draw chart */
	painter.drawBackground();

	if(this._title != null) {
		painter.drawTitle(this._title, this._subtitle);
	}

	/*
	 * If labels and grid density where specified, draw legend and labels.
	 * It's drawn prior to the chart as the size of the legend and labels
	 * affects the size of the chart area.
	 */
	if (this._showLegend && (this._series.length > 0)) { painter.drawLegend(series); }
	if (bLabels) {
		painter.drawVerticalAxis(this._yGridDensity, this._labelPrecision);
		painter.drawHorizontalAxis(xlen, this._horizontalLabels, axisCount, this._interval, this._labelPrecision);
	}
	
	/* Draw chart */
	painter.drawChart();

	/* Draw series */
	for (i = 0; i < series.length; i++) {
		type = series[i].flags & ~CHART_STACKED;
		switch (type) {
			case CHART_LINE: painter.drawLine(series[i].color, series[i].values, series[i].label, (this._cont.id + '_' + i), ((axisCount > 1 ? (axisCount - 1) : 1) * this._interval), this._seriesKey, this._pointerVisible); break;
			case CHART_AREA: painter.drawArea(series[i].color, series[i].values); break;
			case CHART_BAR:  painter.drawBars(series[i].color, series[i].values, xlen-1, series[i].offset, this._barWidth); break;
			default: ;
		};
	}

	/*
	 * Draw axis (after the series since the anti aliasing of the lines may
	 * otherwise be drawn on top of the axis)
	 */
	painter.drawAxis();

};

Chart.prototype._drawPie = function(){
	var nPrecision, bShowValue, nValueStyle, painter;

	//prepare
	nPrecision	= this._labelPrecision;
	bShowValue	= (this._pointerVisible == null ? true : this._pointerVisible);
	nValueStyle = this._pieValueStyle;
	var style = {show_value: true, value_style : 1};
	var totle = 0;
	//chartData: {label, color, values}
	for (i = 0; i < this._series.length; i++) {
		var chartData = this._series[i];
		totle += chartData['values'][0];
	}

	//process the data and draw
	painter = this._painterFactory();
	painter.create(this._cont);
	painter.init2();
	var chartData, label, val, pecent, style, color, angle;
	//style : {color, show_value, value_style}
	var series = [], formerAngle = 0, style;
	for (i = 0; i < this._series.length; i++) {
		chartData = this._series[i];
		label	= chartData['label'];
		val		= chartData['values'][0];
		percent = val / totle;
		angle	= formerAngle + Math.PI * 2 * (percent >= 1 ? (percent - 0.0001) : percent);
		formerAngle = angle;
		style = {color: chartData['color'], show_value: bShowValue, value_style : nValueStyle};

		series.push({
			'name'		: label,
			'value'		: Chart.parseFloat2(val, nPrecision),
			'percent'	: Chart.getPercent(percent, nPrecision),
			'angle'		: angle,
			'style'		: style
		});
	}
	if(this._title != null) {
		painter.drawTitle(this._title, this._subtitle);
	}
	painter.drawPie(series);
}
Chart.getPercent = function(percent, precision){
	percent *= 100;
	return Chart.parseFloat2(percent, precision) + '%';
}
Chart.parseFloat2 = function(raw, precision){
	if(precision == null || precision == 0) {
		raw = Math.round(raw);
	}else{
		var temp = Math.floor(raw);
		var aparts = raw - temp;
		if(aparts == 0) {
			return raw;
		}
		aparts = Math.round(Math.pow(10, precision) * aparts);
		aparts /= Math.pow(10, precision);
		raw = temp + aparts;
	}			

	return raw;
}
Chart.prototype.refresh = function(){
	//TODO do some collection works here before re-draw the chart
	this.draw();
}

/*----------------------------------------------------------------------------\
|                                 ChartSeries                                 |
|- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -|
| Internal class for representing a series.                                   |
\----------------------------------------------------------------------------*/

function ChartSeries(label, color, values, flags, offset) {
	this.label  = label;
	this.color  = color;
	this.values = values;
	this.flags  = flags;
	this.offset = offset;
}


/*----------------------------------------------------------------------------\
|                            AbstractChartPainter                             |
|- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -|
| Abstract base class defining the painter API. Can not be used directly.     |
\----------------------------------------------------------------------------*/
function AbstractChartPainter() {

};


AbstractChartPainter.prototype.calc = function(w, h, xlen, ymin, ymax, xgd, ygd) {
	this.range = ymax - ymin;
	this.xstep = w / (xlen - 1);
	this.xgrid = (xgd)?w / (xgd - 1):0;
	this.ygrid = (ygd)?h / (ygd - 1):0;
	this.ymin  = ymin;
	this.ymax  = ymax;
};


AbstractChartPainter.prototype.create = function(el) {};
AbstractChartPainter.prototype.init = function(xlen, ymin, ymax, xgd, ygd, bLabels) {};
AbstractChartPainter.prototype.drawLegend = function(series) {};
AbstractChartPainter.prototype.drawVerticalAxis = function(ygd, precision) {};
AbstractChartPainter.prototype.drawHorizontalAxis = function(xlen, labels, xgd, precision) {};
AbstractChartPainter.prototype.drawAxis = function() {};
AbstractChartPainter.prototype.drawBackground = function() {};
AbstractChartPainter.prototype.drawChart = function() {};
AbstractChartPainter.prototype.drawArea = function(color, values) {};
AbstractChartPainter.prototype.drawLine = function(color, values) {};
AbstractChartPainter.prototype.drawBars = function(color, values, xlen, xoffset, width) {};


var CMyDateTime = {
	DAY		: 0,
	WEEK	: 1,
	MONTH	: 2,
	YEAR	: 3,
	dateDiff : function(_d1, _d2, _nPart, _bAbs){
		_nPart = _nPart || CMyDateTime.DAY;
		if(_nPart == CMyDateTime.MONTH) {
			return CMyDateTime.dateDiff_month(_d1, _d2, _bAbs);
		}else if(_nPart == CMyDateTime.YEAR){
			return CMyDateTime.dateDiff_year(_d1, _d2, _bAbs); 
		}
		//else
		return (_d1 - _d2)/86400000;
	},
	dateDiff_month : function(_d1, _d2, _bAbs){

		var nMonths1, nMonths2; //totle month-num
		var nDay1, nDay2; //the day of the month

		nMonths1 = _d1.getYear() * 12 + _d1.getMonth();
		nMonths2 = _d2.getYear() * 12 + _d2.getMonth();

		if(_bAbs != true) {
			return (nMonths1 - nMonths2);
		}
		//else need an 'absolutely' caculation
		nDay1 = _d1.getDate();
		nDay2 = _d2.getDate();
		if (nMonths1 == nMonths2)
			return 0;
		else if (nMonths1 > nMonths2)
			return nMonths1 - nMonths2 + (nDay1 < nDay2 ? -1 : 0);
		else
			return nMonths1 - nMonths2 + (nDay1 > nDay2 ? 1 : 0);
	},
	dateDiff_year : function(_d1, _d2, _bAbs){

		var nYears1, nYears2, nMonths1, nMonths2;

		nYears1 = _d1.getYear();
		nYears2 = _d2.getYear();

		if(_bAbs != true) {
			return (nYears1 - nYears2);
		}
		//else need an 'absolutely' caculation
		nMonths1 = _d1.getMonth();
		nMonths2 = _d2.getMonth();
		if (nYear1 == nYear2)
			return 0;
		else if (nYear1 > nYear2)
			return (nYear1 - nYear2) + (nMonth1 >= nMonth2 ? 0 : -1);
		else
			return (nYear1 - nYear2) + (nMonth1 > nMonth2 ? 1 : 0);
	},
	parseDate : function(_sDateTime){
		try{
			var arrPart1 = _sDateTime.split('-');
			var arrPart2 = arrPart1[2].split(' ');
			return new Date(arrPart1[0], arrPart1[1] - 1, arrPart2[0]);
		}catch(err){
			//TODO
			alert(err.message);
			return null;
		}
		
	},
	makeRangeLabels: function(_dtEnd, _nSteps, _nPart){
		//alert([_dtEnd, _nSteps, _nPart])
		var result = [];

		var date = new Date(_dtEnd.valueOf());
		switch(_nPart){
			case CMyDateTime.YEAR:
				for (var i = _nSteps; i >= 0; --i){
					result[i] = (date.getFullYear());
					date.setYear(date.getYear() - 1);
				}
				break;
			case CMyDateTime.MONTH:
				for (var i = _nSteps; i >= 0; --i){
					result[i] = (date.getFullYear() + '-' + (date.getMonth() + 1));
					date.setMonth(date.getMonth() - 1);
				}
				break;
			default:
				for (var i = _nSteps; i >= 0; --i){
					result[i] = (date.getFullYear() + '-' + (date.getMonth() + 1)  + '-' + date.getDate());
					date.setDate(date.getDate() - 1);
				}
				break;
		}
		return result;
	}
}