package com.tonto.weixin.core.handle;

import com.tonto.weixin.core.handle.action.EventAdpaterContainer;
import com.tonto.weixin.core.handle.action.event.EventAdpater;
import com.tonto.weixin.core.handle.interceptor.MessageInterceptor;
import com.tonto.weixin.core.handle.interceptor.ProcessInvocation;
import com.tonto.weixin.core.message.request.event.EventMessage;
import com.tonto.weixin.core.message.response.ResponseMessage;

/**
 * 
 * <h2>微信事件拦截器</h2>
 * <p>作为默认处理器{@link DefaultMessageHandler}的默认拦截器</p>
 * 
 * @author TontoZhou
 *
 */
public class EventInterceptor implements MessageInterceptor {

	EventAdpaterContainer eventAdpaterContainer;
	
	@Override
	public ResponseMessage intercept(ProcessInvocation processInvocation) {
		
		EventMessage event = (EventMessage) processInvocation.getRequest();
		EventAdpater action = eventAdpaterContainer.getEventAction(event.getClass());
		if (action != null) {
			return action.process(processInvocation.getSession(), event, processInvocation.getProcessContext());
		}
		return null;
	}

	@Override
	public int getInterceptorLocation() {
		return -1;
	}

	public EventAdpaterContainer getEventAdpaterContainer() {
		return eventAdpaterContainer;
	}

	public void setEventAdpaterContainer(EventAdpaterContainer eventAdpaterContainer) {
		this.eventAdpaterContainer = eventAdpaterContainer;
	}

}
