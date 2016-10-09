package com.tonto.weixin.core.message.request.common;

import com.tonto.weixin.core.message.request.RequestConstant;

public class ImageRequestMessage extends CommonRequestMessage{
	//图片链接
	private String PicUrl;
	//图片消息媒体id，可以调用多媒体文件下载接口拉取数据
	private String MediaId;
	
	public ImageRequestMessage()
	{
		MsgType=RequestConstant.REQ_MESSAGE_TYPE_IMAGE;
	}
	
	public String getPicUrl() {
		return PicUrl;
	}
	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
	public String getMediaId() {
		return MediaId;
	}
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	
}
