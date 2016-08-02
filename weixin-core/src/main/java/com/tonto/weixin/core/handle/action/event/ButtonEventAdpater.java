package com.tonto.weixin.core.handle.action.event;

import com.tonto.weixin.core.handle.ProcessContext;
import com.tonto.weixin.core.handle.action.EventActionContainer;
import com.tonto.weixin.core.handle.action.EventProcessAction;
import com.tonto.weixin.core.handle.session.Session;
import com.tonto.weixin.core.message.request.event.ButtonEventMessage;
import com.tonto.weixin.core.message.request.event.EventMessage;
import com.tonto.weixin.core.message.response.ResponseMessage;

/**
 * 
 * <h2>按钮事件适配器</h2>
 * <p>根据按钮事件的自定义KEY找到对应的{@link EventProcessAction}处理事件</p>
 * <p>默认事件适配器</p>
 * @author TontoZhou
 *
 */
public class ButtonEventAdpater implements EventAdpater {

	EventActionContainer eventActionContainer;

	@Override
	public ResponseMessage process(Session session, EventMessage message, ProcessContext processContext) {

		ButtonEventMessage buttonMsg = (ButtonEventMessage) message;
		String key = buttonMsg.getEventKey();

		EventProcessAction action = eventActionContainer.getEventProcessAction(key);

		if (action != null)
			return action.input(session, message, processContext);

		return null;
	}

	@Override
	public Class<? extends EventMessage> getEventType2Process() {
		return ButtonEventMessage.class;
	}

	public EventActionContainer getEventActionContainer() {
		return eventActionContainer;
	}

	public void setEventActionContainer(EventActionContainer eventActionContainer) {
		this.eventActionContainer = eventActionContainer;
	}

}
