package com.gwssi.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

/**
 * ��̨ʹ�õ�Cache���ƣ��������κεط�����ʹ��
 * @author AndyLee
 *
 */
public class CacheManage{

    private static final Log logger = LogFactory.getLog(CacheManage.class);

    private GeneralCacheAdministrator admin;

    public CacheManage() {
        admin = new GeneralCacheAdministrator();
    }

    public void add(Object key, Object value) {
    	this.admin.putInCache(key.toString(), value);
    }

    public Object get(Object key) {
        try {
            return this.admin.getFromCache(key.toString());
        }catch (NeedsRefreshException ex) {
        	return null;
        }
    }

    public void remove(Object key) {
    	this.admin.flushEntry(key.toString());
    }
    

    public void removeAll() {
    	this.admin.flushAll();
    }

}
