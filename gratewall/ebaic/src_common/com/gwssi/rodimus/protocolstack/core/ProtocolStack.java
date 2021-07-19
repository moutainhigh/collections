//package com.gwssi.rodimus.protocolstack.core;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//import org.apache.log4j.Logger;
//
//
///**
// * 协议栈。
// * 
// * @author liuhailong(liuhailong2008#foxmail.com)
// */
//public class ProtocolStack {
//	
//	public static final String INPUT_PARAMS_NAME_PROTOCOL_STACK = "p";
//	
//	protected final static Logger logger = Logger.getLogger(ProtocolStack.class);
//	
//	/**
//	 * 正序协议列表。
//	 */
//	protected List<Protocol> protocolsList = null;
//	/**
//	 * 反序协议列表。
//	 */
//	protected List<Protocol> protocolsReversedList = null;
//
//	/**
//	 * 用于正向执行协议的迭代器。
//	 */
//	protected Iterator<Protocol> inIterator = null;
//	/**
//	 * 用于反向执行协议的迭代器。
//	 */
//	protected Iterator<Protocol> outIterator = null;
//	public void reset() {
//		if (protocolsList != null) {
//			inIterator = protocolsList.iterator();
//		}
//		if (protocolsReversedList != null) {
//			outIterator = protocolsReversedList.iterator();
//		}
//		this.inWayProcessed = 0;
//	}
//
//	/**
//	 * 当前协议序号，用于当前执行协议的所在栈的位置。
//	 */
//	protected int inWayProcessed = 0;
//	/**
//	 * 用于计算执行时间。
//	 */
//	public long time = 0;
//	
//	
//	
//	/**
//	 * @return 协议个数
//	 */
//	public int getProtocolCnt() {
//		if (protocolsList == null) {
//			return 0;
//		} else {
//			return protocolsList.size();
//		}
//	}
//
//	/**
//	 * 构造函数。
//	 * @param protocols
//	 * @param context
//	 */
//	public ProtocolStack(List<Protocol> protocols) {
//		this();
//		setProtocols(protocols);
//		reset();
//	}
//	/**
//	 * 构造函数。
//	 */
//	public ProtocolStack() {
//		time = System.currentTimeMillis();
//		reset();
//	}
//	/**
//	 * Spring 注入。
//	 * 
//	 * @param protocols
//	 */
//	public void setProtocols(List<Protocol> protocols) {
//		// 如果传入参数为空
//		if (protocols == null || protocols.isEmpty()) {
//			protocols = new ArrayList<Protocol>();
//			this.protocolsList = protocols;
//			this.protocolsReversedList = protocols;
//			return ;
//		}
//		// 如果传入参数不为空
//		// 		正向
//		this.protocolsList = protocols;
//		
//		// 		反向
//		if (protocolsReversedList == null) {
//			protocolsReversedList = new ArrayList<Protocol>();
//		} else {
//			protocolsReversedList.clear();
//		}
//		for (int idx = protocols.size() - 1; idx > -1; --idx) {
//			Protocol pc = protocols.get(idx);
//			protocolsReversedList.add(pc);
//		}
//	}
//
//
//	/**
//	 * 入栈操作。
//	 * 
//	 * @param request
//	 */
//	public void inWay(Request request) {
//		Protocol currentProtocol = null;
//		if (inIterator != null && inIterator.hasNext()) {
//			currentProtocol = inIterator.next();
//		}
//		if (currentProtocol != null) {
//			// 记录日志
//			if (logger.isDebugEnabled()) {
//				logger.debug(String.format("ProtocolStack@%s(%s) in begin ... %s",Integer.toHexString(this.hashCode()), currentProtocol,(System.currentTimeMillis() - this.time)));
//			}
//			
//			// 执行当前协议入栈操作
//			currentProtocol.inWay(request);
//			
//			// 记录日志
//			if (logger.isDebugEnabled()) {
//				logger.debug(String.format("Protocol(%s) in end. %s",currentProtocol, (System.currentTimeMillis() - this.time)));
//			}
//			this.inWay(request);
//			
//			logger.debug(String.format("In End Time:%s.",(System.currentTimeMillis() - this.time)));
//		}
//	}
//
//	/**
//	 * 出栈操作。
//	 * 
//	 * @param request
//	 */
//	public void outWay(Request request) {
//		Protocol currentProtocol = null;
//		if (outIterator != null && outIterator.hasNext()) {
//			currentProtocol = outIterator.next();
//		} 
//		
//		if (currentProtocol != null) {
//			
//			if (logger.isDebugEnabled()) {
//				logger.debug(String.format(
//						"ProtocolChain@%s(%s) out begin ... %s",
//						Integer.toHexString(this.hashCode()), currentProtocol,
//						(System.currentTimeMillis() - this.time)));
//				//AcessLogUtil.log(request, response, accessId,(System.currentTimeMillis() - this.time), nextProtocol,"outWay");
//			}
//			currentProtocol.outWay(request);
//
//			if (logger.isDebugEnabled()) {
//				logger.debug(String.format("Protocol(%s) out end. %s",
//						currentProtocol, (System.currentTimeMillis() - this.time)));
//
//			}
//			this.outWay(request);
//			
//			logger.debug(String.format("Out End Time:%s.",
//					(System.currentTimeMillis() - this.time)));
//		}
//	}
//
//}
