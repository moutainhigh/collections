package com.gwssi.optimus.core.web.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gwssi.ebaic.apply.util.CpParamUtil;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.fileupload.OptimusFileItem;
import com.gwssi.optimus.util.BeanUtil;
import com.gwssi.optimus.util.JSON;
import com.gwssi.optimus.util.StringUtil;
//import com.gwssi.rodimus.util.DaoUtil;

/**
 * 该类是自定义的Controller方法中的参数之一，可视为对HttpServletRequest对象的包装，
 * 利用了Spring MVC提供的Handler参数定制功能实现。当前端使用JAZZ UI对接时，使用该类与前端对接，
 * 按与JAZZ UI协定的数据格式对前端请求进行解析。
 * <p>该类提供了一些方法，用于便捷地获取JAZZ UI各种组件提交到后台的数据。
 * 该类也提供了获取原生HttpServletRequest对象及请求 参数的方法。
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class OptimusRequest extends BaseDataSet {

    private final static Logger logger = LoggerFactory.getLogger(OptimusRequest.class);

    private HttpServletRequest httpRequest;
   
	private Map<String, Map> formDataSet;
    private Map<String, Map> gridDataSet;
    private Map<String, Object> paginationParams;
    private Map<String, Map> attrDataSet;
    private int requestType;

    public OptimusRequest(HttpServletRequest httpRequest) {
        this.httpRequest = httpRequest;
        requestType = REQUEST_TYPE_NORMAL;
        String dataJsonStr = httpRequest.getParameter(POST_DATA_FLAG);
        if (StringUtils.isNotEmpty(dataJsonStr))
            build(dataJsonStr);
    }

    private void build(String dataJsonStr) {
        Map postDataMap = JSON.parse(dataJsonStr);
        if (!postDataMap.containsKey("data")) {
            logger.debug("postData中没有data内容");
            return;
        }
        List groupList = (List) postDataMap.get("data");
        for (int i = 0, len = groupList.size(); i < len; i++) {
            Map group = (Map) groupList.get(i);
            String vtype = (String) group.get("vtype");
            String name = (String) group.get("name");
            if (WIDGET_TYPE_FORM.equals(vtype)) {
                if (formDataSet == null) {
                    formDataSet = new HashMap<String, Map>();
                }
                formDataSet.put(name, group);
            } else if (WIDGET_TYPE_GRID.equals(vtype)) {
                if (gridDataSet == null) {
                    gridDataSet = new HashMap<String, Map>();
                }
                gridDataSet.put(name, group);
            } else if (WIDGET_TYPE_ATTR.equals(vtype)) {
                if (attrDataSet == null) {
                    attrDataSet = new HashMap<String, Map>();
                }
                attrDataSet.put(name, group);
            } else if (WIDGET_TYPE_PAGINATION.equals(vtype)) {
                if(paginationParams==null){
                    paginationParams = new HashMap<String, Object>();
                }
                buildPaginationParams(group);
            }
        }
    }
    
    private void buildPaginationParams(Map group){
        String name = StringUtil.getMapStr(group, "name");
        Object data = group.get("data");
        if(StringUtils.isEmpty(name) || data==null){
            logger.error("请求分页参数有误："+group.toString());
            return;
        }
        if(name.equals(PaginationManager.TOTAL_ROWS)){
            int totalRows = (Integer)data;
            if(totalRows>0)
                requestType = REQUEST_TYPE_PAGINATION;   
        }
        paginationParams.put(name, data);
    }

//    public boolean buildPaginationParams(Map postDataMap) {
//        if (!postDataMap.containsKey("page") || !postDataMap.containsKey("widgetName"))
//            return false;
//        paginationParams = new HashMap<String, Object>();
//        Integer page = (Integer)postDataMap.get("page");
//        Object widgetName = postDataMap.get("widgetName");
//        paginationParams.put("page", page);
//        paginationParams.put("widgetName", widgetName);
//        if (postDataMap.containsKey("pageRows")) {
//            Integer value = (Integer)postDataMap.get("pageRows");
//            paginationParams.put("pageRows", value);
//        }
//        if (postDataMap.containsKey("totalRows")) {
//            Integer value = (Integer)postDataMap.get("totalRows");
//            paginationParams.put("totalRows", value);
//        }
//        return true;
//    }

    /**
     * 获取前端提交的Form组件的数据，转换为指定的类对象。类中的属性名称需与Form组件中的各子项名称一致。
     * @param widgetName Form组件的name属性值
     * @param type 将提交的Form数据要转换成的类
     * @return Form组件数据转换后的类对象
     * @throws OptimusException
     */
	public <T> T getForm(String widgetName, Class<T> type) throws OptimusException {
        if (formDataSet == null || !formDataSet.containsKey(widgetName))
            return null;
        Map form = (Map) formDataSet.get(widgetName);
        if (!form.containsKey("data"))
            return null;
        Object obj = null;
        try {
            obj = BeanUtil.newInstance(type);
            Map formData = (Map)form.get("data");
            BeanUtil.mapToBean(formData, obj, true);
//            Iterator ite = formData.entrySet().iterator();
//            while(ite.hasNext()){
//                Entry entry = (Entry)ite.next();
//                String formItemName = (String)entry.getKey();
//                String formItemValue = (String)entry.getValue();
//                ReflectHelper.setVal(obj, formItemName, formItemValue);
//            }
        } catch (Exception e) {
            throw new OptimusException("", e);
        }
        return (T)obj;
    }

    /**
     * 获取前端提交的Form组件的数据，转换为Map<String, String>对象，
     * 其中key为Form组件中子项的名称，value为子项的值。
     * @param widgetName Form组件的name属性值
     * @return Form组件的数据转换成的Map对象
     */
    public Map<String, String> getForm(String widgetName) {
        if (formDataSet == null || !formDataSet.containsKey(widgetName))
            return null;
        Map group = (Map) formDataSet.get(widgetName);
        if (!group.containsKey("data"))
            return null;
        Map formData = (Map) group.get("data");
//        for (int i = 0, len = formData.size(); i < len; i++) {
//            Map formItem = formData.get(i);
//            String formItemName = (String) formItem.get("name");
//            String formItemValue = transObj2String(formItem.get("value"));
//            resultMap.put(formItemName, formItemValue);
//        }
        return formData;
    }

    /**
     * 获取提交的Grid组件的数据，通常是grid的选中记录；获取到的数据被转换为指定的类对象集。
     * @param widgetName Grid组件name属性值
     * @param type 将提交的Grid数据要转换成的类
     * @return 转换成指定类对象集的Grid数据
     * @throws OptimusException
     */
    public List<Object> getGrid(String widgetName, Class<?> type) throws OptimusException {
        if (gridDataSet == null || !gridDataSet.containsKey(widgetName))
            return null;
        Map group = (Map)gridDataSet.get(widgetName);
        Map data = (Map)group.get("data");
        if (data==null || !data.containsKey("rows"))
            return null;
        List<Map> rowList = (List<Map>)data.get("rows");
        List objectList = new ArrayList();
        try {
            for(Map row : rowList){
                Object obj = BeanUtil.newInstance(type);
                BeanUtil.mapToBean(row, obj);
                objectList.add(obj);
            }
        } catch (Exception e) {
            throw new OptimusException("", e);
        }
        return objectList;
    }

    /** 
     * 获取提交的Grid组件的数据，通常是grid的选中记录；获取到的数据被转换为Map<String, String>对象集。
     * @param widgetName Grid组件name属性值
     * @return 转换成Map对象集的Grid数据
     */
    public List<Map<String, String>> getGrid(String widgetName) {
        if (gridDataSet == null || !gridDataSet.containsKey(widgetName))
            return null;
        Map group = (Map)gridDataSet.get(widgetName);
        Map data = (Map)group.get("data");
        if (data==null || !data.containsKey("rows"))
            return null;
        List rowList = (List)data.get("rows");
        return rowList;
    }
    
    /**
     * 获取前端组件传来的参数。
     * @param attrName 参数名
     * @return 参数值
     */
    public Object getAttr(String attrName) {
        if(attrDataSet!=null){
            Map group = attrDataSet.get(attrName);
            if(group==null){
                return null;
            }else{
                return group.get("data");
            }
        }
        return null;
    }
    public Map<String, Map> getAttrSet(){
    	return attrDataSet;
    } 
    
    /**
     * 获取前端传来的请求参数。该方法同原生HttpServletRequest的getParameter方法一致。
     * @param key 参数名
     * @return 参数值
     */
    public String getParameter(String key) {
        return httpRequest.getParameter(key);
    }
    
    /**
     * 获取上传文件。
     * @param formName form组件name属性值
     * @param fieldName 附件上传组件name属性值
     * @return FileItem对象集
     * @throws OptimusException 
     */
    public List<OptimusFileItem> getUploadList(String formName, String fieldName) 
            throws OptimusException{
        Map formMap = getForm(formName);
        String attachJson = StringUtil.getMapStr(formMap, fieldName);
        List<Map> attachList = JSON.parseArray(attachJson);
        List<OptimusFileItem> fileItemList = new ArrayList<OptimusFileItem>();
        if(attachList!=null){
            for(Map attach : attachList){
                String name = StringUtil.getMapStr(attach, "name");
                String fileId = StringUtil.getMapStr(attach, "fileId");
                OptimusFileItem fileItem = new OptimusFileItem(name, fileId);
                fileItemList.add(fileItem);
            }
        }
        return fileItemList;
    }
    
    //    public  List<Map<String, Object>> getTree(String paramString);

    //    public  List<Map<String, String>> getInsertTableData(String paramString);
    //
    //    public  List<?> getInsertTable(String paramString, Class<?> paramClass);
    //
    //    public  List<?> getInsertTable(String paramString);
    //
    //    public  List<Map<String, String>> getDeleteTableData(String paramString);
    //
    //    public  List<?> getDeleteTable(String paramString, Class<?> paramClass);
    //
    //    public  List<?> getDeleteTable(String paramString);

    //    public  List<Map<String, String>> getUpdateTableData(String paramString);
    //
    //    public  List<?> getUpdateTable(String paramString, Class<?> paramClass);
    //
    //    public  List<?> getUpdateTable(String paramString);

    //    public  List<FileItem> getUploadList()
    //      throws UnsupportedEncodingException, FileUploadException{
    //        
    //    }
    //
    //    public  List<FileItem> getUploadList(String paramString1, int paramInt1, int paramInt2, String paramString2)
    //      throws UnsupportedEncodingException, FileUploadException;
    //
    //    public  List<FileItem> getUploadList(String paramString1, int paramInt1, int paramInt2, int paramInt3, String paramString2)
    //      throws UnsupportedEncodingException, FileUploadException;

    protected Map getPaginationParams() {
        return paginationParams;
    }

    protected void setPaginationParams(Map paginationParams) {
        this.paginationParams = paginationParams;
    }

    protected int getRequestType() {
        return requestType;
    }

    protected void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    public HttpServletRequest getHttpRequest() {
        return httpRequest;
    }
    
    private String entId = null;
//    private String catId = null;
//    private CpWkEntBO entBo = null;
//    private CpWkRequisitionBO reqBo = null;

    public String getGid() throws OptimusException{
    	return CpParamUtil.getId();
    }
    public String getEntId() throws OptimusException{
    	if(this.entId==null){
//    		this.entId = this.getReqBo().getEntId();
    	}
    	return this.entId;
    }
//    public String getCatId() throws OptimusException{
//    	if(this.catId==null){
//    		this.catId = this.getReqBo().getEntTypeCategory();
//    	}
//    	return this.catId;
//    }
//    public CpWkEntBO getEntBo() throws OptimusException{
//    	if(entBo==null){
//    		entBo = DaoUtil.get(CpWkEntBO.class, this.getEntId());
//    	}
//    	return this.entBo;
//    }
//    public CpWkRequisitionBO getReqBo() throws OptimusException{
//    	if(this.reqBo==null){
//    		this.reqBo = DaoUtil.get(CpWkRequisitionBO.class, this.getGid());
//    	}
//    	return this.reqBo;
//    }
}
