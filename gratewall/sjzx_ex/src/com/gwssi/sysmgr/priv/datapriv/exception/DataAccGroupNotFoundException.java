package com.gwssi.sysmgr.priv.datapriv.exception;

/**
 * 异常数据权限组不存在
 * @author 周扬
 *
 */
public class DataAccGroupNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public String toString() {
		return "数据权限组不存在";
	}
}
