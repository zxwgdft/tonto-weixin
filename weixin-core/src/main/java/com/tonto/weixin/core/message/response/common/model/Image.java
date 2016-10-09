package com.tonto.weixin.core.message.response.common.model;

import com.tonto.weixin.core.message.MessageObject;

public class Image implements MessageObject{
	private String MediaId;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
}
