package cn.gwssi.datachange.msg_push.component;
/**
 * 配置文件，暂硬编码实现
 * @author wuyincheng
 * @date Jul 12, 2016
 */
public class Config {

	public static final int MSG_RETRY_TIMES = 2;    //消息发送失败时，默认重试次数
	public static final long MSG_RETRY_INTERVALS = 3; //尝试发送间隔时间=1s
	
	public static final boolean IS_FIXED_POOL = true;//固定线程数量
	public static final int POOL_SIZE = 5;   //线程池大小
	public static final int TIMEOUT = 30; //超时时间 s
	
}
