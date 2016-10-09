package com.tonto.weixin.core.handle.action.event;

import com.tonto.weixin.core.handle.ProcessContext;
import com.tonto.weixin.core.handle.session.Session;
import com.tonto.weixin.core.message.request.event.EventMessage;
import com.tonto.weixin.core.message.request.event.LocationEventMessage;
import com.tonto.weixin.core.message.response.ResponseMessage;

public class LocationEventAdpater implements EventAdpater{

	@Override
	public ResponseMessage process(Session session, EventMessage message,ProcessContext processContext) {
		LocationEventMessage locationEvent=(LocationEventMessage) message;
		
		System.out.println("经度："+locationEvent.getLatitude());
		return null;
	}

	@Override
	public Class<? extends EventMessage> getEventType2Process() {
		return LocationEventMessage.class;
	}

}
