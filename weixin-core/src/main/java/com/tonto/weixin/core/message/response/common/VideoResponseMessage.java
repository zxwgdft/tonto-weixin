package com.tonto.weixin.core.message.response.common;

import com.tonto.weixin.core.message.response.ResponseConstant;
import com.tonto.weixin.core.message.response.ResponseMessage;
import com.tonto.weixin.core.message.response.common.model.Video;

public class VideoResponseMessage extends ResponseMessage{
	private Video Video;
	
	public VideoResponseMessage()
    {
    	MsgType=ResponseConstant.RESP_MESSAGE_TYPE_VIDEO;
    }
	
	public Video getVideo() {
		return Video;
	}

	public void setVideo(Video video) {
		Video = video;
	}
}
