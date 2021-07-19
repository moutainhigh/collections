//package com.gwssi.rodimus.protocolstack.config;
//
//import com.gwssi.rodimus.config.BaseConfigManager;
//import com.gwssi.rodimus.config.ConfigUtil;
//import com.gwssi.rodimus.exception.RodimusException;
//import com.gwssi.rodimus.protocolstack.core.Protocol;
//import com.gwssi.rodimus.util.StringUtil;
//
///**
// * 配置信息读取。
// * 
// * @author liuhailong(liuhailong2008#foxmail.com)
// */
//public class ProtocolManager extends BaseConfigManager<Protocol>{
//
//	public static ProtocolManager instance = new ProtocolManager();
//	/* (non-Javadoc)
//	 * @see com.gwssi.rodimus.config.BaseConfigManager#getCachePrefix()
//	 */
//	protected String getCachePrefix() {
//		return "protocol_";
//	}
//
//	/* (non-Javadoc)
//	 * @see com.gwssi.rodimus.config.BaseConfigManager#getConfigFromDb(java.lang.String)
//	 */
//	public Protocol getConfigFromDb(String protocolName) {
//		try {
//			if(StringUtil.isBlank(protocolName)){
//				throw new RodimusException("协议名不能为空。");
//			}
//			String configKey = String.format("protocol.%s", protocolName);
//			String className = ConfigUtil.get(configKey);
//			if(StringUtil.isBlank(protocolName)){
//				throw new RodimusException(String.format("请在 sys_config中配置%s的实现类。", protocolName));
//			}
//			Class<?> clz = Class.forName(className);
//			if(StringUtil.isBlank(className)){
//				throw new RodimusException(String.format("加载类%s失败。", className));
//			}
//			Object o = clz.newInstance();
//			if(o instanceof Protocol){
//				Protocol ret = (Protocol)o;
//				return ret;
//			}else{
//				throw new RodimusException(String.format("%s不是Protocol的实现类。", className));
//			}
//		} catch (Exception e) {
//			throw new RodimusException(e.getMessage(),e);
//		}
//	}
//
//}
