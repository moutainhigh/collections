CKEDITOR.plugins.add('clearClasses',
{
	init : function( editor )
	{
		editor.addCommand('clearClassesCommand',
		{
			exec : function(editor){
				var data = editor.getData();
				var sNewData = unionStyle(data, 'Custom_UnionStyle') ;
				if(sNewData!='' && sNewData!=data){
					setTimeout(function(){
						editor.setData(sNewData, null, false); 
					}, 0);
				}

				function unionStyle(_sHtml, _sClassName){
					var exg = /(<html>[\s\S]+?<body>)([\s\S]+?)(<\/body>[\s\S]+?<\/html>)/img;
					_sHtml = _sHtml.replace(exg,function(oldStr,beforeContent,content,aftercontent){
						var vReturn = content;
						// 如果是粘贴过程触发的统一格式，需要在顶级元素追加
						var vMatchAll = new RegExp('^<div class=\"?'+_sClassName+'\"?>((\n|\r|.)*)<\/div>$','img');
						vReturn = vReturn.replace(vMatchAll,'$1');
						//抽取里面的table元素，因为table不需要进行样式处理，否则table将变形
						var oTablesHtml = {};
						var vReturn = ExtractTable(vReturn, oTablesHtml);

						vReturn = CleanCustomeWord(vReturn);

						vReturn = vReturn.replace(/<style(\s.*>|>)(.|\n|\r)*?<\/style>/gi, '');

						vReturn = clearFontStyleTags(vReturn);

						vReturn = clearTagStyleFontAbout(vReturn);

						vReturn = removeNoStyleTag(vReturn , "span");// 删除span元素

						//将table里面的内容，还原回去
						vReturn = RestoreTable(vReturn, oTablesHtml);
						//清除空行
						vReturn = clearBlankLine(vReturn);
						vReturn = '<div class="'+_sClassName+'">' + vReturn + '</div>';
						content = vReturn;
						return beforeContent + content + aftercontent;
						});
					return _sHtml;
				}

				function ExtractTable(sHtml, oTablesHtml){	
					//将table标记转成小写
					var regexp = /(<\/?)table([^>]*>)/ig;
					sHtml = sHtml.replace(regexp, "$1table$2");
					
					//被替换后的唯一标识前缀
					var guidPrefix = '$trs-table-';	
					//被替换后的唯一标识后缀，一直递增的序列
					var id = 0;
					var suffix = '$';	

					//返回后的HTML内容数组
					var aHtml = [];

					var index = 0;
					while(true){
						var pos = GetNextTagRange(sHtml, index, 'table');
						if(pos.s == -1){
							aHtml.push(sHtml.substring(index));
							return aHtml.join("");
						}
						//记录完整的table片段到oTablesHtml对象中
						var propertyName = guidPrefix + (++id) + suffix;
						oTablesHtml[propertyName] = sHtml.substring(pos.s, pos.e + 1);

						//组装返回的Html结果
						aHtml.push(sHtml.substring(index, pos.s), propertyName);

						//继续设置下一个偏移
						index = pos.e + 1;
					}
					return "";
				}

				function CleanCustomeWord( sHtml){
					var html = sHtml ;
					// reomve text-indent,margin styles
					html = html.replace( /\s*face="[^"]*"/gi, '' ) ;
					html = html.replace( /\s*face=[^ >]*/gi, '' ) ;
					html = html.replace( /([\s";])FONT-FAMILY:[^;"]*;?/gi, '$1' ) ;
					html = html.replace( /<(U|I|STRIKE)>&nbsp;<\/\1>/g, '&nbsp;' ) ;
					html = html.replace(/<(\w[^>]*) class=([^ |>]*)([^>]*)/gi, function(_a0, _a1, _a2, _a3){
						if(_a2.toLowerCase().indexOf('fck_')!=-1){
							return '<' + _a1 + ' class=' + _a2 + _a3;
						}
						return '<' + _a1 + _a3;
					});//"<$1$3") ;

					html = html.replace( new RegExp('([\\s";\'])text-indent[^:]*:[^;"\']*;?', 'gi'), '$1' ) ;
					html = html.replace( new RegExp('([\\s";\'])margin[^:]*:[^;"\']*;?', 'gi'), '$1' ) ;
					html = html.replace(/<o:p>\s*<\/o:p>/g, '') ;
					html = html.replace(/<o:p>.*?<\/o:p>/g, '&nbsp;') ;
					html = html.replace( /(["'])\s*PAGE-BREAK-BEFORE: [^\s;\']+;?"/gi, "$1" ) ;
					html = html.replace( /\s*FONT-VARIANT: [^\s;\']+;?"/gi, "\"" ) ;
					html = html.replace( /\s*tab-stops:[^;"\']*;?/gi, '' ) ;
					html = html.replace( /\s*tab-stops:[^"\']*/gi, '' ) ;

					html = html.replace( /([\s";'])mso-[a-z\-]+:[^;'"]*/img, '$1' ) ;
					html = html.replace( /\s+class\s*=\s*(["'])MsoNormal\1/img, ' ' ) ;

					html = html.replace( /<SPAN\s*[^>]*>\s*&nbsp;\s*<\/SPAN>/img, '&nbsp;' ) ;
					html = html.replace(/<\\?\?xml[^>]*>/gi, '' ) ;
					html = html.replace(/<\/?\w+:[^>]*>/gi, '' ) ;
					html = html.replace(/<\!--.*?-->/g, '' ) ;
					//HTML链接中可能存在链入本页面的锚点
					html = html.replace(/(<\w+[^>]*\s)href="([^"]*)(#.+)"/ig,
						function(a0,a1,a2,a3){
							if(a2.indexOf('fckeditor')!=-1 && a2.indexOf("?InstanceName=TRS_Editor")!=-1){
								return a1+'href="'+a3+'"';
							}
							return a0;
						});
					html = html.replace( /<H(\d)([^>]*)>/gi, '<h$1>' ) ;		
					return html ;
				}

				/**
				* 统一格式中需要去除与font相关的HTML置标(如font,h1等)
				*/
				function clearFontStyleTags(_sHtml){
					var vReturn=_sHtml;
					var vPattern='</?(?:font|strong|b|i|u|em|sub|sup|strike)(?:>|\\s+[^>]*>)';
					var vRegExp = new RegExp(vPattern, 'img');
					vReturn = vReturn.replace(vRegExp, "");
					vPattern = '<(/?)h[1-6](?:>|\\s+[^>]*>)';
					var vRegExp = new RegExp(vPattern, 'img');
					vReturn = vReturn.replace(vRegExp, "<$1p>");
					return vReturn;
				}

				/**
				* 统一格式中需要去除HTML置标中的style下的与font相关的属性
				*/
				function clearTagStyleFontAbout(_sHtml){
					var html = _sHtml;
					html = html.replace( /\s*face="[^"]*"/gi, '' ) ;
					html = html.replace( /\s*face=[^ >]*/gi, '' ) ;
					html = html.replace( /\s*color="[^"]*"/gi, '' ) ;
					html = html.replace( /\s*color=[^ >]*/gi, '' ) ;
					var aFontStyles=[
						'font', 'color', 'font-family', 'font-size', 
						'font-size-adjust', 'font-stretch', 'font-style', 
						'font-weight', 'text-indent','text-decoration', 'text-underline-position',
						'text-shadow', 'font-variant', 'text-transform',
						'line-height', 'letter-spacing', 'word-spacing'
					];
					for(var i=0;i<aFontStyles.length;i++){
						html = html.replace( new RegExp('([\\s";\'])'+ aFontStyles[i] +':[^;"]*;?', 'gi'), '$1' ) ;
					}
					//需要排除margin相关属性
					html =  html.replace( new RegExp('([\\s";\'])margin[^:]*:[^;"]*;?', 'gi'), '$1' ) ;
					return html;
				}

				/**
				*将sHtml里面的特殊标记（为oTablesHtml对象属性名）转换成oTablesHtml对象属性值
				*/
				function RestoreTable(sHtml, oTablesHtml){
					return sHtml.replace(/\$trs-table-\d+\$/g, function($0){
						return oTablesHtml[$0] || 0;
					});
				}

				function removeNoStyleTag(_sContent, _sTagName){
					// 去除三次，避免嵌套
					var vMatchAll = new RegExp('<'+_sTagName+'>(.*?)<\/'+_sTagName+'>','img');
					_sContent = _sContent.replace(vMatchAll, '$1');
					_sContent = _sContent.replace(vMatchAll, '$1');
					_sContent = _sContent.replace(vMatchAll, '$1');

					return _sContent;	
				}

				/**
				* 去除空行
				*/
				window.clearBlankLine = function clearBlankLine(_sHtml){
					var vReturn=_sHtml;
					var vPatterns=[
						'<(p|div|center)(>|\\s+[^>]*>)(<(strong|em|u|span)>)*(<\/(strong|em|u|span)>)*(&nbsp|&nbsp;|\\s|　|<br\\s*(\/)?>)*(<\/(strong|em|u|span)>)*<\/(p|div|center)(>|\\s+[^>]*>)',
						'(<br\\s*(\/)?>((\\s|&nbsp;|&nbsp|　)*)){2,}',
						'(<\/p\\s*>|<\/div\\s*>)<br\\s*(\/)?>((\\s|&nbsp;|&nbsp|　)*)',
						'(<p(>|\\s+[^>]*>))((&nbsp|&nbsp;|\\s)*<br\\s*(\/)?>)*((.|\n|\r)*?<\/p(>|\\s+[^>]*>))'
					];
					var vReplaces=[
						'',
						'<br>$3',
						'$1',
						'$1$6'
					];
					for(var i=0;i<vPatterns.length;i++){
						var vRegExp = new RegExp(vPatterns[i], 'img');
						vReturn=vReturn.replace(vRegExp,vReplaces[i]);
					}
					vReturn = vReturn.replace( /<SPAN\s*[^>]*><\/SPAN>/gi, '' ) ;	
					return vReturn;
				}

				/**
				*从sHtml的nIndex位置开始，获取到html标记为sTagName的完整内容的位置范围{s:m,e:n}
				*如果没有找到匹配的内容，返回{s:-1,e:-1}
				*/
				function GetNextTagRange(sHtml, nIndex, sTagName){
					var st = "<" + sTagName;
					var et = "</" + sTagName + ">";
					if(sHtml.indexOf(st, nIndex) < 0) return {s:-1, e:-1};

					var stack = [];
					var lastIndex;
					var index = nIndex;
					while(true){
						var sp = sHtml.indexOf(st, index);
						var ep = sHtml.indexOf(et, index);		
						if(sp == -1){
							lastIndex = stack.pop();
							index = ep + et.length;
						}else if(sp < ep){
							stack.push(sp);
							index = sp + st.length;
						}else{
							lastIndex = stack.pop();
							index = ep + et.length;
						}
						if(stack.length <= 0) return {s:lastIndex, e:index}
					}
					return {s:-1, e:-1};
				}

			}
		});
		editor.ui.addButton('clearClasses', 
			{
				label: '统一样式',
				command: 'clearClassesCommand',
			});
	}
});
