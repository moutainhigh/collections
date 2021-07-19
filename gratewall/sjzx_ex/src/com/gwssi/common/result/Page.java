package com.gwssi.common.result;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * page 分页信息状态类
 * @author caiqian
 *
 */
public class Page {

    /** 是否存在上一页 */
    private boolean hasPrePage;
   
    /** 是否存在下一页数 */
    private boolean hasNextPage;
       
    /** 分页记录数 */
    private int everyPage;
   
    /** 总页数 */
    private int totalPage;
    
    /** 总记录数 */
    private long totalRecords;
       
    /** 当前页数 */
    private int currentPage;
   
    /** 当前开始记录数 */
    private int beginIndex;
    
    /**页面分页时必须传递的当前页数*/ 
    public static String CURRENT_PAGE="currentPage";
    
    /**页面分页时必须传递的当前页记录数*/
    public static String EVERY_PAGE="everyPage";
    
    /**保存的*/
    public static String SCROLLPAGE_PARAM="SCROLLPAGEPARAM";
   
    /** The default constructor */
    public Page(){
       
    }
   
    /** construct the page by everyPage
     * @param everyPage
     * */
    public Page(int everyPage){
        this.everyPage = everyPage;
    }
    
    public Page(int currentPage,int everyPage){
    	this.currentPage = currentPage;
        this.everyPage = everyPage;
    }   
    
    /** The whole constructor */
    public Page(boolean hasPrePage, boolean hasNextPage, 
                    int everyPage, int totalPage, long totalRecords,
                    int currentPage, int beginIndex) {
        this.hasPrePage = hasPrePage;
        this.hasNextPage = hasNextPage;
        this.everyPage = everyPage;
        this.totalPage = totalPage;
        this.totalRecords = totalRecords;
        this.currentPage = currentPage;
        this.beginIndex = beginIndex;
    }

    /**
     * @return
     * Returns the beginIndex.
     */
    public int getBeginIndex() {
        return beginIndex;
    }
   
    /**
     * @param beginIndex
     * The beginIndex to set.
     */
    public void setBeginIndex(int beginIndex) {
        this.beginIndex = beginIndex;
    }
   
    /**
     * @return
     * Returns the currentPage.
     */
    public int getCurrentPage() {
        return currentPage;
    }
   
    /**
     * @param currentPage
     * The currentPage to set.
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
   
    /**
     * @return
     * Returns the everyPage.
     */
    public int getEveryPage() {
        return everyPage;
    }
   
    /**
     * @param everyPage
     * The everyPage to set.
     */
    public void setEveryPage(int everyPage) {
        this.everyPage = everyPage;
    }
   
    /**
     * @return
     * Returns the hasNextPage.
     */
    public boolean getHasNextPage() {
        return hasNextPage;
    }
   
    /**
     * @param hasNextPage
     * The hasNextPage to set.
     */
    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }
   
    /**
     * @return
     * Returns the hasPrePage.
     */
    public boolean getHasPrePage() {
        return hasPrePage;
    }
   
    /**
     * @param hasPrePage
     * The hasPrePage to set.
     */
    public void setHasPrePage(boolean hasPrePage) {
        this.hasPrePage = hasPrePage;
    }
   
    /**
     * @return Returns the totalPage.
     *
     */
    public int getTotalPage() {
        return totalPage;
    }
   
    /**
     * @param totalPage
     * The totalPage to set.
     */
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    
    /**
     * @param totalRecords
     * The totalRecords to set.
     */
    public void setTotalRecords(long totalRecords)
    {
        this.totalRecords = totalRecords;
    }
    /**
     * @return Returns the totalRecords.
     *
     */
    public long getTotalRecords()
    {
        return this.totalRecords;
    }
    
    /**保存分页产生的查询条件*/
    public void setScrollParameter(HttpServletRequest request){
        request.setAttribute(SCROLLPAGE_PARAM,getParameter(request));
    }
    
    private Map getParameter(HttpServletRequest request){
        Map mapParam = new HashMap();
        Enumeration enumer = request.getParameterNames();
        while (enumer.hasMoreElements()){
            String name = (String)enumer.nextElement();
            String objs = request.getParameter(name);
            if (objs != null){
                mapParam.put(name,objs);
            }
        }
        return mapParam;
    }
}
