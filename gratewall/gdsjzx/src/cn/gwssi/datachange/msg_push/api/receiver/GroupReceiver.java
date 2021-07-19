package cn.gwssi.datachange.msg_push.api.receiver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 组用户(暂时没用到)
 * @author wuyincheng
 * @date Jul 12, 2016
 */
public abstract class GroupReceiver implements Iterable<Receiver>, Receiver{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2821311508853426214L;
	
	private List<Receiver> receivers = new ArrayList<Receiver>();
	
	public void addReceiver(Receiver receiver) {
		receivers.add(receiver);
	}
	
	public abstract String groupName();


	@Override
	public Iterator<Receiver> iterator() {
		return receivers.iterator();
	}
	
	
}
