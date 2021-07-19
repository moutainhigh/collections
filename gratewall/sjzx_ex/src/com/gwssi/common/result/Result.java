package com.gwssi.common.result;

import java.util.List;

/**
 * ���ڴ����ض��󣬶����а���ʧ�ܼ��ɹ���־����ʾ��Ϣ�����ؽ���ȵȡ�
 * @author caiqian
 */
public class Result {
	/**
	 * �ɹ���־
	 */
	private boolean sucessful;
	/**
	 * ��ʾ��Ϣ 
	 */
	private String message;
	
    /**
	 * ���ص���ϸhibernate BO����
	 */
	private Object value;
	
    
    /**��ҳ״̬����ʵ��*/
    private Page page;
    
    /**���ݿ�������ĵ�ǰҳ�����*/
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
