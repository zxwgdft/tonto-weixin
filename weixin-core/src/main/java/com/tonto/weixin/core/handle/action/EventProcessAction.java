package com.tonto.weixin.core.handle.action;

/**
 * 事件业务处理
 * @author	TontoZhou
 * @date	2015-5-21
 */
public interface EventProcessAction extends ProcessAction{
	
	/**
	 * 事件key，对应click或view消息中的event key，也可以自定义对应
	 * @return
	 */
	public String getEventKey();
	
}
