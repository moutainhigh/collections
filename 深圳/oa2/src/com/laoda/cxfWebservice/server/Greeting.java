package com.laoda.cxfWebservice.server;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface Greeting {
	public Integer greeting(@WebParam(name = "userId") String userId);

	public Integer greetGetZc(@WebParam(name = "userId") String userId);

	public Integer greetTodo(@WebParam(name = "userId") String userId);
}