package com.gwssi.common.util;

import javax.servlet.http.HttpServletRequest;
import com.genersoft.frame.base.ApplicationException;

/**
 * <p>Title: 国家统计数据项目-发布库管理</p>
 *
 * <p>Description: 数据缓存接口类.以后在此处做功能扩展</p>
 *
 * <p>Copyright: Copyright (c) 2007.8</p>
 *
 * <p>Company: gwssi</p>
 *
 * @author chenzw
 * @version 1.0
 */
public class DataCache {
    public DataCache() {
    }


    /**
     * 放置指定对象到缓存中
     * @param dataCacheName String 缓存对象的名称关键字
     * @param request HttpServletRequest当前请求对象.用来确定SESSION信息
     * @param data Object要缓存的数据对象
     * @throws ApplicationException
     */
    public static void putDataToCache(String dataCacheName, HttpServletRequest request, Object data) throws ApplicationException{
        try{
            request.getSession().setAttribute(dataCacheName, data);
        }catch(Exception e){
            throw new ApplicationException (e);
        }
    }

    /**
     * 从缓存中获取以前的对象.
     * @param dataCacheName String  缓存对象的名称关键字
     * @param request HttpServletRequest 当前请求对象.用来确定SESSION信息
     * @return Object 被缓存的数据对象
     * @throws ApplicationException
     */
    public static Object getDataFromCache(String dataCacheName, HttpServletRequest request) throws ApplicationException{
        try{
            return request.getSession().getAttribute(dataCacheName);
        }catch(Exception e){
            throw new ApplicationException(e);
        }
    }

}
