package com.tonto.weixin.core.message.request.common;

import com.tonto.weixin.core.message.request.RequestConstant;

public class VoiceRequestMessage extends CommonRequestMessage{
	//语音消息媒体id，可以调用多媒体文件下载接口拉取数据
	private String MediaId;
	//语音格式，如amr，speex等
	private String Format;
	
	public VoiceRequestMessage()
	{
		MsgType=RequestConstant.REQ_MESSAGE_TYPE_VOICE;
	}

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getFormat() {
		return Format;
	}

	public void setFormat(String format) {
		Format = format;
	}
	
}
