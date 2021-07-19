CKEDITOR.plugins.add('db2sb',
{
	init : function( editor )
	{
		editor.addCommand('db2sbCommand',
		{
			exec : function(editor){
				var data = editor.getData();
				data = parseDb2Sb(data);
				setTimeout(function(){
					editor.setData(data, null, false); 
				}, 0);
				
				/* 转换全角为半角——调整原有实现逻辑，对所有全角字符做处理 */
				function parseDb2Sb(_sHtml){
					var result = '';
					for (i=0 ; i<_sHtml.length; i++){
						
						/* 获取当前字符的unicode编码 */
						code = _sHtml.charCodeAt(i);
						
						/* unicode编码范围是所有的英文字母以及各种字符 */
						if (code >= 65281 && code <= 65373){/* unicode编码转换，全->半 */
							result += String.fromCharCode(_sHtml.charCodeAt(i) - 65248);
						}else{/* 原字符返回 */
							result += _sHtml.charAt(i);
						}
					}
					return result;
				}
			}
		});
		editor.ui.addButton('db2sb', 
			{
				label: '全转半',
				command: 'db2sbCommand',
				icon : this.path + 'db2sb.jpg'
			});
	}
});
