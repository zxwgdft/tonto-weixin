package com.tonto.weixin.core.message.response.common;

import com.tonto.weixin.core.message.response.ResponseConstant;
import com.tonto.weixin.core.message.response.ResponseMessage;
import com.tonto.weixin.core.message.response.common.model.Voice;

public class VoiceResponseMessage extends ResponseMessage{
	
	private Voice Voice;
	
	public VoiceResponseMessage()
    {
    	MsgType=ResponseConstant.RESP_MESSAGE_TYPE_VOICE;
    }
	
	public Voice getVoice() {
		return Voice;
	}

	public void setVoice(Voice voice) {
		Voice = voice;
	}
	
}
