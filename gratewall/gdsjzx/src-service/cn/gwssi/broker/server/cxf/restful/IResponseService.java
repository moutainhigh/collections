package cn.gwssi.broker.server.cxf.restful;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import cn.gwssi.common.exception.BrokerException;

/**
 * 服务接口类
 * @author xue
 * @version 1.0
 * @since 2016/4/28
 */
@Path(value = "/SOAService")
public interface IResponseService {

    @GET
    @Path(value = "/specialSynUnifiedService/{excuteServicePath}")
    @Produces({MediaType.APPLICATION_JSON })
	public String specialSynUnifiedService(@PathParam("excuteServicePath")String excuteServicePath,@QueryParam("xml")String xml) throws BrokerException;
	@POST
    @Path(value = "/specialSynUnifiedService/{excuteServicePath}")
    @Produces({MediaType.APPLICATION_JSON })
	public String specialSynUnifiedServicePost(@PathParam("excuteServicePath")String excuteServicePath,String xml) throws BrokerException;
    	
	@GET
    @Path(value = "/synUnifiedService/{excuteServicePath}")
    @Produces({MediaType.APPLICATION_JSON })
	public String synUnifiedService(@PathParam("excuteServicePath")String excuteServicePath,@QueryParam("xml")String xml);
	@POST
    @Path(value = "/synUnifiedService/{excuteServicePath}")
    @Produces({MediaType.APPLICATION_JSON })
	public String synUnifiedServicePost(@PathParam("excuteServicePath")String excuteServicePath,String xml);
	
	@GET
    @Path(value = "/asynUnifiedService/{excuteServicePath}")
    @Produces({MediaType.APPLICATION_JSON })
	public String asynUnifiedService(@PathParam("excuteServicePath")String excuteServicePath,@QueryParam("xml")String xml);
	@POST
    @Path(value = "/asynUnifiedService/{excuteServicePath}")
    @Produces({MediaType.APPLICATION_JSON })
	public String asynUnifiedServicePost(@PathParam("excuteServicePath")String excuteServicePath,String xml);
	
	@GET
    @Path(value = "/testApp/{excuteServicePath}")
    @Produces({MediaType.APPLICATION_JSON })
	public String testApp(@PathParam("excuteServicePath")String excuteServicePath,@QueryParam("xml")String xml);
    
    @POST
    @Path(value = "/testApp/{excuteServicePath}")
    @Produces({MediaType.APPLICATION_JSON })
	public String testAppPost(@PathParam("excuteServicePath")String excuteServicePath,String xml);
	
    @GET
    @Path(value = "/{id}/test")
    @Produces({MediaType.APPLICATION_JSON })
	public String test(@PathParam("id")String id,@QueryParam("name")String xml);
	@POST
    @Path(value = "/{id}/test")
    @Produces({MediaType.APPLICATION_JSON })
	public String test1(@PathParam("id")String id,String xml);
	
}
