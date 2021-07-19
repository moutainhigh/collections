package com.gwssi.common.result;

import java.util.List;

/**
 * 用于处理返回对象，对象中包含失败及成功标志、提示信息、返回结果等等。
 * @author caiqian
 */
public class Result {
	/**
	 * 成功标志
	 */
	private boolean sucessful;
	/**
	 * 提示信息 
	 */
	private String message;
	
    /**
	 * 返回的明细hibernate BO对象
	 */
	private Object value;
	
    
    /**分页状态变量实体*/
    private Page page;
    
    /**数据库检索到的当前页结果集*/
    private List content;
    
    /**
     * The default constructor
     */
    public Result() {
        super();
    }

    /**
     * The constructor using fields
     *
     * @param page
     * @param content
     */
    public Result(Page page, List content) {

        this.page = page;
        this.content = content;
    }
    
    /**
     * The constructor using fields
     * @param content
     */
    public Result(List content) {
        this.content = content;
    }
    
    public Result(Object value) {
        this.value = value;
    }


    /**
     * @return Returns the content.
     */
    public List getContent() {
        return content;
    }

    /**
     * @return Returns the page.
     */
    public Page getPage() {
        return page;
    }

    /**
     * The content to set.
     * @param content
     */
    public void setContent(List content) {
        this.content = content;
    }

    /**
     * The page to set.
     * @param page
     */
    public void setPage(Page page) {
        this.page = page;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSucessful() {
        return sucessful;
    }

    public void setSucessful(boolean sucessful) {
        this.sucessful = sucessful;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}
