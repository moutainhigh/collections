package com.gwssi.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

/**
 * 后台使用的Cache机制，可以在任何地方调用使用
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
