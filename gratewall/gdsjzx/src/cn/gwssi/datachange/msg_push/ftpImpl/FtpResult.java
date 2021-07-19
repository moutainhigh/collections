package cn.gwssi.datachange.msg_push.ftpImpl;

import cn.gwssi.datachange.msg_push.api.Result;
/**
 * FTP返回码
 * @author wuyincheng
 * @date Jul 12, 2016
 */
public class FtpResult extends Result{
	
	private static final long serialVersionUID = -8013789275682131609L;
	
	public static final Result TIMEOUT = new Result(){
		private static final long serialVersionUID = -7638507093030896980L;

		@Override
		public int resultCode() {
			return -2;
		}};
	public static final Result SUCCESS = new Result(){
		private static final long serialVersionUID = -6541156458314433762L;

		@Override
		public int resultCode() {
			return 0;
		}};
		
	public static final Result FAILED = new Result(){
		private static final long serialVersionUID = -6541156458314433762L;
		@Override
		public int resultCode() {
			return -1;
		}};

	@Override
	public int resultCode() {
		return 0;
	}

}
