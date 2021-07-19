package com.gwssi.rodimus.rpc;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识参数名。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Target({java.lang.annotation.ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ParameterName {
    public abstract String value();
}