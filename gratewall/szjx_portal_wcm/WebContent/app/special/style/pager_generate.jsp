<!--
    需要引用:样式表../portalviewmgr/css/pager1.css
    样式大体如:
        0,上一页 1 2 3 ...21 下一页
        1,上一页 1 ...16 17 18 ...21 下一页
        2,上一页 1 ...18 19 20 21 下一页
-->
<%!
        private static String doGenerateGotoFirstPage(int _nCrIndex ){
        if(_nCrIndex>1)
            return "<a href='#' class='num' onclick='gotoPage(1,event)'>"+LocaleServer.getString("pager_generate.jsp.label.home_page", "首页")+"</a>&nbsp;";
        return "<span class='disabled' >"+LocaleServer.getString("pager_generate.jsp.label.home_page", "首页")+"</span>&nbsp;";
    }
    private static String doGenerateGotoLastPage(int _nPageCount, int _nCrIndex){
        if(_nCrIndex<_nPageCount)
            return "<a href='#' class='num' onclick='gotoPage("+_nPageCount+",event)'>"+LocaleServer.getString("pager_generate.jsp.label.bottom_page","尾页")+"</a>";
        return "<span class='disabled' >"+LocaleServer.getString("pager_generate.jsp.label.bottom_page","尾页")+"</span>";
    }
    private static String doGenerateGotoPrePage( int _nCrIndex ){
        if(_nCrIndex>1)
            return "<a href='#' class='num' onclick='gotoPage("+(_nCrIndex-1)+",event)'>"+LocaleServer.getString("pager_generate.jsp.label.preview_page","上一页")+"</a>&nbsp;";
        return "<span class='disabled' >"+LocaleServer.getString("pager_generate.jsp.label.preview_page","上一页")+"</span>&nbsp;";
        
    }
    private static String doGenerateGotoNextPage(int _nPageCount, int _nCrIndex){
        if(_nCrIndex<_nPageCount)
            return "<a href='#' class='num' onclick='gotoPage("+(_nCrIndex+1)+",event)'>"+LocaleServer.getString("pager_generate.jsp.label.next_page","下一页")+"</a>&nbsp;";
        return "<span class='disabled' >"+LocaleServer.getString("pager_generate.jsp.label.next_page","下一页")+"</span>&nbsp;";
    }

    private static String doGenerateEllipsis() {
        return "<span >...</span>" ;
    }

    public static String doGenerateOnePage(int _nIndex, int _nCrIndex) {
        String s = "";
        if (_nIndex == _nCrIndex)
            s = "<span class='numred'>"+_nIndex+"</span>&nbsp;";
        else
            s = "<a href='#' class='num1' onclick='gotoPage("+_nIndex+",event)'>"+_nIndex+"</a>&nbsp;";
        return s;

    }
    /**
     * @param _nPageCount
     * @param _nCrIndex
     * @return TrsArcher 2009-7-17上午10:03:54
     */
    private static String doGeneratePager(int _nPageCount, int _nCrIndex) {
        StringBuffer sPagerBuffer = new StringBuffer();
        // 获取当前页码的前一页
        int nPreIndex = _nCrIndex - 1;
        if (_nCrIndex == 1) {
            nPreIndex = 1;
        }
        // 获取当前页码的后一页
        int nNextIndex = _nCrIndex + 1;
        if (_nCrIndex == _nPageCount) {
            nNextIndex = _nPageCount;
        }
        /* 输出首页 和 上一页 */
        //sPagerBuffer.append( doGenerateGotoFirstPage(_nCrIndex) );
        sPagerBuffer.append( doGenerateGotoPrePage(_nCrIndex) );
        /* 当页面总数小于等于6页时,页码全部输出 */
        if (_nPageCount <= 6) {
            for (int i = 1; i <= _nPageCount; i++) {
                sPagerBuffer.append( doGenerateOnePage(i, _nCrIndex) );
            }
        } else{
            /* 当页面总数大于6页时,页面按照策略输出  */
            // 确保最前1个页码是必须显示的
            if ( nPreIndex > 1  ) {
                sPagerBuffer.append( doGenerateOnePage(1, _nCrIndex) );
            }
            // 当第一段页码的最后一个页码与当前页面的前一页之间有1个及以上页面的时候,输出...;否则输出中间的这个页码
            if (nPreIndex - 2 > 1) {
                sPagerBuffer.append( doGenerateEllipsis() );
            } else if ((nPreIndex - 2) == 1) {
                sPagerBuffer.append( doGenerateOnePage(nPreIndex - 1, _nCrIndex) );
            }

            // 当前页是最后一页的时候输出倒数第三页
            if (_nCrIndex == _nPageCount)
                sPagerBuffer.append( doGenerateOnePage(nPreIndex - 1, _nCrIndex) );
            // 输出当前页码和其前后页码
            if (nPreIndex != _nCrIndex) {
                sPagerBuffer.append( doGenerateOnePage(nPreIndex, _nCrIndex) );
            }
            sPagerBuffer.append( doGenerateOnePage(_nCrIndex, _nCrIndex) );
            if (nNextIndex != _nCrIndex) {
                sPagerBuffer.append( doGenerateOnePage(nNextIndex, _nCrIndex) );
            }
            // 当前页是第一页的时候输出第三页
            if (_nCrIndex == 1)
                sPagerBuffer.append( doGenerateOnePage(nNextIndex + 1, _nCrIndex) );
            // 当最后一段代码的最前一个页码与当前页面的后一页之间有1个及以上的页码的时候,输出...;否则输出中间的这个页码
            if (_nPageCount - nNextIndex > 2) {
                sPagerBuffer.append( doGenerateEllipsis() );
            } else if ((_nPageCount - nNextIndex) == 2) {
                sPagerBuffer.append( doGenerateOnePage(nNextIndex + 1, _nCrIndex) );
            }
            // 确保最后1个页码是必须显示的
            if(_nPageCount>nNextIndex){
                    sPagerBuffer.append( doGenerateOnePage(_nPageCount, _nCrIndex) );
            }
        }
        
        /* 输出尾页 和 下一页 */
        sPagerBuffer.append( doGenerateGotoNextPage(_nPageCount,_nCrIndex) );
        //sPagerBuffer.append( doGenerateGotoLastPage(_nPageCount, _nCrIndex) );
        
        return sPagerBuffer.toString();
    }
    private String generatePager(CPager currPage) {
        if(currPage==null) 
            return "";
        
        int nPageCount = currPage.getPageCount();
        if( nPageCount ==0 )
            return "";
        int nCurrPageIndex = currPage.getCurrentPageIndex();

        return doGeneratePager( nPageCount, nCurrPageIndex );
    }
    
%>