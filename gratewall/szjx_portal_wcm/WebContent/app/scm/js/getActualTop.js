 /*
 * 计算当前window的实际top，之所以不用top，是因为防止iframe嵌入页面的时候，用top会有跨域的问题
 */
function getActualTop(){
	if(window.__actualTop)return window.__actualTop;
	var actualTop = window;
	var nSafeFactor = 0;
	while(true && nSafeFactor<1000){
		try{
			if(actualTop.__actualTop != null){
				window.__actualTop = actualTop.__actualTop;
				return actualTop.__actualTop;
			}
		}catch(err){
			break;
		}
		if(actualTop==top)break;
		try{
			var testDoc = actualTop.parent.window;
			actualTop = actualTop.parent;
		}catch(err){
			break;
		}
		nSafeFactor++ ;
	}
	window.__actualTop = window;
	return window;
}