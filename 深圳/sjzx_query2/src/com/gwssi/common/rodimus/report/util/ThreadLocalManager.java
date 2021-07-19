package com.gwssi.common.rodimus.report.util;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"rawtypes","unchecked"})
public class ThreadLocalManager {
	public final static String HTTP_REQUEST = "HTTP_REQUEST";
    public final static String HTTP_RESPONSE = "HTTP_RESPONSE";
    public final static String HTTP_SESSION = "HTTP_SESSION";
    public final static String DAO_CACHE = "DAO_CACHE";
    public final static String EXPR_CONTEXT = "EXPR_CONTEXT";

	private static ThreadLocal pool = new ThreadLocal();

    public static Object get(String key) {
        Map map = (Map) pool.get();
        if (map == null) {
            return null;
        }
        return map.get(key);
    }

	public static void add(String key, Object value) {
        if (pool.get() == null) {
            pool.set(new HashMap());
        }
        Map map = (Map) pool.get();
        map.put(key, value);
    }

    public static Map getMap() {
        return ((Map) pool.get());
    }

    public static void clear() {
        pool.set(null);
    }
}
