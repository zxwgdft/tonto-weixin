package com.tonto.weixin.core.message.request.event;

import com.tonto.weixin.core.message.request.RequestConstant;
import com.tonto.weixin.core.message.request.RequestMessage;

/**
 * 
 * 事件基类
 * 
 * @author TontoZhou
 *
 */
public abstract class EventMessage extends RequestMessage{
	
    // 事件类型
    protected String Event;
    
    public EventMessage()
    {
    	MsgType=RequestConstant.REQ_MESSAGE_TYPE_EVENT;
    }
    
	public String getEvent() {
		return Event;
	}
	public void setEvent(String event) {
		Event = event;
	}
}
