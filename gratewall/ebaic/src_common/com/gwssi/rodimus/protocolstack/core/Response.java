//package com.gwssi.rodimus.protocolstack.core;
//
//import com.gwssi.torch.util.StringUtil;
//
///**
// * @author liuhailong(liuhailong2008#foxmail.com)
// */
//public class Response {
//	
//	private Request request;
//	//private byte[] data ;
//	
//	public Response(Request request) {
//		this.request = request;
//	}
//	public Request getRequest() {
//		return request;
//	}
//
//	public void setRequest(Request request) {
//		this.request = request;
//	}
//	
//	public String toString(){
//		if(request!=null){
//			return StringUtil.safe2String(request.getContext().get("DATA"));
//		}else{
//			return "";
//		}
//	}
//	
////	public byte[] getData(){
////		return this.data;
////	}
//	
////	public String toString(){
////		if(this.data!=null){
////			String ret = Base64.getEncoder().encodeToString(this.data);
////			return ret;
////			return null;
////		}
////		return null;
////	}
//
//}
