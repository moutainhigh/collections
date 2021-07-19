package com.gwssi.common.result;

import javax.servlet.http.HttpServletRequest;

/**
 * 分页工具类，初始化Page对象
 * @author caiqian
*/
public class PageUtil {
      
    /**
     * Use the origin page to create a new page
     * @param page 必须包含EveryPage 分页记录数,CurrentPage 当前页数,TotalRecords 总页数
     * @param totalRecords
     * @return
     */
    public static Page createPage(Page page, long totalRecords){
        return createPage(page.getEveryPage(), page.getCurrentPage(), totalRecords);
    }
    
    /** 
     * the basic page utils not including exception handler
     * @param everyPage 分页记录数
     * @param currentPage 当前页数
     * @param totalRecords 总页数
     * @return page
     */
    public static Page createPage(int everyPage, int currentPage, long totalRecords){
        everyPage = getEveryPage(everyPage);
        currentPage = getCurrentPage(currentPage);
        int beginIndex = getBeginIndex(everyPage, currentPage);
        int totalPage = getTotalPage(everyPage, totalRecords);
        boolean hasNextPage = hasNextPage(currentPage, totalPage);
        boolean hasPrePage = hasPrePage(currentPage);
       
        return new Page(hasPrePage, hasNextPage, 
                                everyPage, totalPage, totalRecords,
                                currentPage, beginIndex);
    }
   
    private static int getEveryPage(int everyPage){
        return everyPage == 0 ? 5 : everyPage;
    }
   
    private static int getCurrentPage(int currentPage){
        return currentPage == 0 ? 1 : currentPage;
    }
   
    private static int getBeginIndex(int everyPage, int currentPage){
        return (currentPage - 1) * everyPage;
    }
       
    private static int getTotalPage(int everyPage, long totalRecords){
        int totalPage = 0;
               
        if(totalRecords % everyPage == 0)
            totalPage = (int)totalRecords / everyPage;
        else
            totalPage = (int)totalRecords / everyPage + 1 ;
        
        return totalPage;
    }
   
    private static boolean hasPrePage(int currentPage){
        return currentPage == 1 ? false : true;
    }
   
    private static boolean hasNextPage(int currentPage, int totalPage){
        return currentPage == totalPage || totalPage == 0 ? false : true;
    }
   

} 