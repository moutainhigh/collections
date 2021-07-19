package cn.gwssi.broker.client.callback;

import cn.gwssi.common.model.AsynReponseContext;

public abstract class CallBack {
	public void execute(AsynReponseContext reponseContext){
	}
	
	public void execute(String path){
	}
}