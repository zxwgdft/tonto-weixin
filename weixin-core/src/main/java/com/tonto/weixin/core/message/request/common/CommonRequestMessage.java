package com.tonto.weixin.core.message.request.common;

import com.tonto.weixin.core.message.request.RequestMessage;

public abstract class CommonRequestMessage extends RequestMessage{
	// 消息id，64位整型  
    protected long MsgId;
    
    public long getMsgId() {
		return MsgId;
	}
	public void setMsgId(long msgId) {
		MsgId = msgId;
	}
    
}
