package com.gwssi.sysmgr.priv.datapriv.exception;

/**
 * 异常用户不存
 * @author 周扬
 *
 */
public class UserNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public String getMessage() {
		return super.getMessage();
	}

	public String toString() {
		return "用户不存在";
	}
}
