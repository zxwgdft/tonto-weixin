package com.tonto.weixin.core.message.request.common;

import com.tonto.weixin.core.message.request.RequestConstant;

public class VideoRequestMessage extends CommonRequestMessage{
	
	//视频消息媒体id，可以调用多媒体文件下载接口拉取数据
	private String MediaId;
	
	//视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
	private String ThumbMediaId;
	
	public VideoRequestMessage(){
		MsgType=RequestConstant.REQ_MESSAGE_TYPE_VIDEO;
	}

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}
}
