CKEDITOR.plugins.add('sb2db',
{
	init : function( editor )
	{
		editor.addCommand('sb2dbCommand',
		{
			exec : function(editor){
				var data = editor.getData();
				data = parseSb2Db(data);
				setTimeout(function(){
					editor.setData(data, null, false); 
				}, 0);

				/* 转换半角为全角 */
				function parseSb2Db(_sHtml){
					/* 对所有的tag标签及内容不过滤 */
					var reExpression = /<\s*\/?\w+[^>]*?>/im;
					var pFragments;
					var nLastIndex = 0;
					var result = "";
					while(true){
						//debugger;
						/* 表达式继续解析 */
						pFragments = reExpression.exec(_sHtml);
						/* 没有匹配的表达式时跳出循环 */
						if(pFragments==null){
							result += doSb2db(_sHtml);
							break;
						}
						/* 转化标签前的文本 */
						var temp = _sHtml.substring(0,pFragments.index);
						if(temp && temp.indexOf("&nbsp;") >= 0){
							var sub1 = temp.substring(0,temp.indexOf("&nbsp;"));
							temp = doSb2dbWithNbsp(temp);
						}else{
							temp = doSb2db(temp);
						}
						result += temp;
						/* 标签直接追加 */
						result += _sHtml.substring(pFragments.index,pFragments.lastIndex);
						/* 调整原串 */
						_sHtml = _sHtml.substring(pFragments.lastIndex);
					}
					return result;
				}

				function doSb2dbWithNbsp(_sHtml){
					var result = "";
					var index = _sHtml.indexOf("&nbsp;");
					var sub = _sHtml.substring(0,index);

					result += doSb2db(sub) + "&nbsp;";
					_sHtml = _sHtml.substring(index + "&nbsp;".length);
					if(_sHtml.indexOf("&nbsp;") >= 0){
						result += doSb2dbWithNbsp(_sHtml);
					}else{
						result += doSb2db(_sHtml);
					}
					return result;
				}

				function doSb2db(_sHtml){
					var result = '';
					for (i=0 ; i<_sHtml.length; i++){
						
						/* 获取当前字符的unicode编码 */
						code = _sHtml.charCodeAt(i);

						/* unicode编码范围是所有的英文字母以及各种字符 */
						if (code >= 33 && code <= 126){/* unicode编码转换,半->全 */
							result += String.fromCharCode(_sHtml.charCodeAt(i) + 65248);
						}else if (code == 32){/* 空格转换 */
							result += String.fromCharCode(_sHtml.charCodeAt(i) + 12288 - 32);
						}else{/* 原字符返回 */
							result += _sHtml.charAt(i);
						}
					}
					return result;
				}

			}
		});
		editor.ui.addButton('sb2db', 
			{
				label: '半转全',
				command: 'sb2dbCommand',
				icon : this.path + 'sb2db.jpg'
			});
	}
});
