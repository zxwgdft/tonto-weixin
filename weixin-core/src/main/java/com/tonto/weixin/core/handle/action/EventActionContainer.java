package com.tonto.weixin.core.handle.action;

import com.tonto.weixin.core.IContainer;


/**
 * 
 * 事件动作容器
 * 
 * @author TontoZhou
 *
 */
public interface EventActionContainer extends IContainer{
		
	/**
	 * 
	 * 根据事件类型获取对应的事件动作
	 * 
	 * @param eventkey
	 * @return
	 */
	public EventProcessAction getEventProcessAction(String eventkey);
	
}
