package com.tonto.weixin.core.handle.action.event;

import com.tonto.weixin.core.handle.ProcessContext;
import com.tonto.weixin.core.handle.session.Session;
import com.tonto.weixin.core.message.request.event.EventMessage;
import com.tonto.weixin.core.message.response.ResponseMessage;

/**
 * 
 * 事件适配器，根据事件类型处理事件
 * 
 * @author	TontoZhou
 * @date	2015-5-5
 */
public interface EventAdpater {
	
	ResponseMessage process(Session session,EventMessage message,ProcessContext processContext);
	
	/**
	 * 获取处理的的event消息类
	 * @return
	 */
	Class<? extends EventMessage> getEventType2Process();
}
