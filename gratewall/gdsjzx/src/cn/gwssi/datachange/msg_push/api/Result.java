package cn.gwssi.datachange.msg_push.api;

/**
 * 推送结果
 * @author wuyincheng
 * @date Jul 12, 2016
 *
 */
public abstract class Result implements Msg{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6072886809245338904L;

	@Override
	public MsgType getType() {
		return MsgType.RESULT;
	}
	
	public abstract int resultCode();
	
}
