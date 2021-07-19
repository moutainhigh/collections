package com.gwssi.sysmgr.priv.datapriv.exception;

/**
 * 异常角色不存在
 * @author 周扬
 *
 */
public class RoleNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public String toString() {
		return "角色不存在";
	}
}
