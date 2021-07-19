package com.gwssi.common.util;

import javax.servlet.http.HttpServletRequest;
import com.genersoft.frame.base.ApplicationException;

/**
 * <p>Title: ����ͳ��������Ŀ-���������</p>
 *
 * <p>Description: ���ݻ���ӿ���.�Ժ��ڴ˴���������չ</p>
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
     * ����ָ�����󵽻�����
     * @param dataCacheName String �����������ƹؼ���
     * @param request HttpServletRequest��ǰ�������.����ȷ��SESSION��Ϣ
     * @param data ObjectҪ��������ݶ���
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
     * �ӻ����л�ȡ��ǰ�Ķ���.
     * @param dataCacheName String  �����������ƹؼ���
     * @param request HttpServletRequest ��ǰ�������.����ȷ��SESSION��Ϣ
     * @return Object ����������ݶ���
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
