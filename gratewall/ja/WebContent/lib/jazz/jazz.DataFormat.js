(function( $, factory ){
	if ( jazz.config.isUseRequireJS === true ) {
		define( ['jquery'], factory );
	} else {
		factory($);
	}	 
})(jQuery, function($){    
		
	jazz.dataFormater = function(data, format) {
		if(typeof format !="string"){
			return data;
		}
        jazz.__DataFormater._value = data;
        jazz.__DataFormater._format = format;
        var output = "",
            reg_date = "/Y|M|D|H|m|s|S/g";

        if (format.match(reg_date)) {
            output = jazz.__DataFormater.formatDate();
        } else if (format.indexOf("$") != -1) {
            output = jazz.__DataFormater.formatCurrency("$", Math.round);
        } else if (format.indexOf("¥") != -1) {
            output = jazz.__DataFormater.formatCurrency("¥", Math.round);
        } else if (format.indexOf("%") != -1) {
            output = jazz.__DataFormater.formatPercentage("%", 100, Math.round);
        } else if (format.indexOf("‰") != -1) {
            output = jazz.__DataFormater.formatPercentage("‰", 1000, Math.round);
        } else {
            output = jazz.__DataFormater.formatNumber(Math.round);
        }

        return output;

    }

    jazz.__DataFormater = {
        _value : "",
        _values : [],
        _format : "",
        formatDate : function() {

            this.fixValues();
            return this.matchDataFormat();

        },
        fixValues : function() {
            var datas = this._value.toString().split("");
            var datas_len = datas.length;
            var output = "";
            //过滤掉_value中非数字部分并补全剩余的0
            while (datas_len--) {
                if ((datas[datas_len] == " ") ||isNaN(datas[datas_len])) {
                    //如果发现不是数字删除该位
                    datas.splice(datas_len, 1);
                }
            }
            //多了不管只拿前14位(YYYYMMDDHHmmss)
            while (datas.length < 14) {
                datas.push("0");
            }
            //把这个数给对象
            this._values = datas;
        },
        matchDataFormat : function() {
            var formattingTokens = /(\[[^\[]*\])|(\\)?(Mo|MM?M?M?|Do|DDDo|DD?D?D?|ddd?d?|do?|w[o|w]?|W[o|W]?|Q|YYYYYY|YYYYY|YYYY|YY|gg(ggg?)?|GG(GGG?)?|e|E|a|A|hh?|HH?|mm?|ss?|S{1,4}|x|X|zz?|ZZ?|.)/g;
            var formats = this._format.match(formattingTokens);
            var formats_len = formats.length;

            while (formats_len--) {
                formats[formats_len] = this.matchDataFactory(formats[formats_len]);
            }

            return formats.join("");

        },
        matchDataFactory : function(formatName) {
            if (formatName == "YY") {
                return (this._values.slice(2, 4).join(""));
            } else if (formatName == "YYYY") {
                return (this._values.slice(0, 4).join(""));
            } else if (formatName == "MM") {
                return (this._values.slice(4, 6).join(""));
            } else if (formatName == "M") {
                return (this._values.slice(4, 6).join("").replace("0", ""));
            } else if (formatName == "DD") {
                return (this._values.slice(6, 8).join(""));
            } else if (formatName == "D") {
                return (this._values.slice(6, 8).join("").replace("0", ""));
            } else if (formatName == "HH") {
                return (this._values.slice(8, 10).join(""));
            } else if (formatName == "mm") {
                return (this._values.slice(10, 12).join(""));
            } else if (formatName == "ss") {
                return (this._values.slice(12, 14).join(""));
            } else {
                return formatName;
            }
        },
        formatCurrency : function(symbol, roundingFunction) {
            var format = this._format;
            var symbolIndex = format.indexOf(symbol), //记录￥符号的位置，等着数字转化完成后再放到原来位置上
                openParenIndex = format.indexOf('('),
                minusSignIndex = format.indexOf('-'),
                space = '',
                spliceIndex,
                output;

            // check for space before or after currency
            // 先清空非数字部分
            if (format.indexOf(' '+symbol) > -1) {
                space = ' ';
                format = format.replace(' '+symbol, '');
            } else if (format.indexOf(symbol+' ') > -1) {
                space = ' ';
                format = format.replace(symbol+' ', '');
            } else {
                format = format.replace(symbol, '');
            }
            this._format = format;

            // format the number
            output = this.formatNumber(roundingFunction);

            if(format === "" || format === undefined) return n._value;

            // 还原括号的位置
            // position the symbol
            if (symbolIndex <= 1) {
                if (output.indexOf('(') > -1 || output.indexOf('-') > -1) {
                    output = output.split('');
                    spliceIndex = 1;
                    if (symbolIndex < openParenIndex || symbolIndex < minusSignIndex) {
                        // the symbol appears before the "(" or "-"
                        spliceIndex = 0;
                    }
                    output.splice(spliceIndex, 0, symbol + space);
                    output = output.join('');
                } else {
                    output = symbol + space + output;
                }
            } else {
                if (output.indexOf(')') > -1) {
                    output = output.split('');
                    output.splice(-1, 0, space + symbol);
                    output = output.join('');
                } else {
                    output = output + space + symbol;
                }
            }

            return output;
        },
        formatPercentage : function(symbol, _n, roundingFunction) {
            var space = '',
                output,
                format = this._format;

            // check for space before %
            if (format.indexOf(' '+symbol) > -1) {
                space = ' ';
                format = format.replace(' '+symbol, '');
            } else {
                format = format.replace(symbol, '');
            }
            this._format = format;

            this._value = this._value * _n;
            output = this.formatNumber(roundingFunction);

            if (output.indexOf(')') > -1) {
                output = output.split('');
                output.splice(-1, 0, space + symbol);
                output = output.join('');
            } else {
                output = output + space + symbol;
            }

            return output;
        },
        formatNumber : function(roundingFunction) {
            var negP = false,
                signed = false,
                optDec = false,
                abbr = '',
                abbrK = false, // force abbreviation to thousands
                abbrM = false, // force abbreviation to millions
                abbrB = false, // force abbreviation to billions
                abbrT = false, // force abbreviation to trillions
                abbrForce = false, // force abbreviation
                bytes = '',
                ord = '',
                abs = Math.abs(value),
                suffixes = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'],
                min,
                max,
                power,
                w,
                precision,
                thousands,
                d = '',
                neg = false,
                format = this._format,
                value = this._value;

            // check if number is zero and a custom zero format has been set
            if (value === 0 && zeroFormat !== null) {
                return zeroFormat;
            } else {
                // see if we should use parentheses for negative number or if we should prefix with a sign
                // if both are present we default to parentheses

                if (format.indexOf('[.]') > -1) {
                    optDec = true;
                    format = format.replace('[.]', '.');
                }

                w = value.toString().split('.')[0];
                precision = format.split('.')[1];
                thousands = format.indexOf(',');

                if (precision) {
                    if (precision.indexOf('[') > -1) {
                        precision = precision.replace(']', '');
                        precision = precision.split('[');
                        d = this.toFixed(value, (precision[0].length + precision[1].length), roundingFunction, precision[1].length);
                    } else {
                        d = this.toFixed(value, precision.length, roundingFunction);
                    }

                    w = d.split('.')[0];

                    if (d.split('.')[1].length) {
                        d = "." + d.split('.')[1];
                    } else {
                        d = '';
                    }

                    if (optDec && Number(d.slice(1)) === 0) {
                        d = '';
                    }
                } else {
                    w = this.toFixed(value, null, roundingFunction);
                }

                // format number
                if (w.indexOf('-') > -1) {
                    w = w.slice(1);
                    neg = true;
                }

                if (thousands > -1) {
                    w = w.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1' + ",");
                }

                if (format.indexOf('.') === 0) {
                    w = '';
                }

                return ((negP && neg) ? '(' : '') + ((!negP && neg) ? '-' : '') + ((!neg && signed) ? '+' : '') + w + d + ((ord) ? ord : '') + ((abbr) ? abbr : '') + ((bytes) ? bytes : '') + ((negP && neg) ? ')' : '');
            }
        },
        toFixed : function(value, precision, roundingFunction, optionals) {
            var power = Math.pow(10, precision),
                optionalsRegExp,
                output;

            //roundingFunction = (roundingFunction !== undefined ? roundingFunction : Math.round);
            // Multiply up by precision, round accurately, then divide and use native toFixed():
            output = (roundingFunction(value * power) / power).toFixed(precision);

            if (optionals) {
                optionalsRegExp = new RegExp('0{1,' + optionals + '}$');
                output = output.replace(optionalsRegExp, '');
            }

            return output;
        }
    }

});
