package com.tonto.weixin.core.handle.action;

import com.tonto.weixin.core.IContainer;
import com.tonto.weixin.core.handle.action.event.EventAdpater;
import com.tonto.weixin.core.message.request.event.EventMessage;

/**
 * 
 * 事件适配器容器
 * 
 * @author TontoZhou
 *
 */
public interface EventAdpaterContainer extends IContainer{
	
	
	/**
	 * 
	 * 根据事件类型获取对应事件适配器
	 * 
	 * @param clazz
	 * @return
	 */
	public EventAdpater getEventAction(Class<? extends EventMessage> clazz);
}
