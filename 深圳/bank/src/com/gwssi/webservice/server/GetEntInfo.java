package com.gwssi.webservice.server;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface GetEntInfo {

	public String getEntInfo(@WebParam(name = "key") String key,@WebParam(name = "entset") String entset,@WebParam(name = "type") String type,@WebParam(name = "stime") String stime,@WebParam(name = "etime") String etime,@WebParam(name = "page") String page,@WebParam(name = "size") String size);
	
	
	
	public String getLogOffEntInfo(@WebParam(name = "key") String key,@WebParam(name = "entset") String entset,@WebParam(name = "type") String type,@WebParam(name = "stime") String stime,@WebParam(name = "etime") String etime,@WebParam(name = "page") String page,@WebParam(name = "size") String size);

}