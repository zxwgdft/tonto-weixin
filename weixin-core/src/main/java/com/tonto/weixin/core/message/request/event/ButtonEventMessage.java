package com.tonto.weixin.core.message.request.event;

public class ButtonEventMessage extends EventMessage{
	
	private String EventKey;
	
	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}
}
