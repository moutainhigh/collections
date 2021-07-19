package com.gwssi.Contorller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller Base Class.
 * 
 */
public abstract class BaseContorller {
	/**
	 * ¥¶¿Ì«Î«Û°£
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	 public abstract void handle(HttpServletRequest request, HttpServletResponse response) throws Exception;
	 
}
