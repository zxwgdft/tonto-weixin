package com.tonto.weixin.core.message.response.common;

import com.tonto.weixin.core.message.response.ResponseConstant;
import com.tonto.weixin.core.message.response.ResponseMessage;
import com.tonto.weixin.core.message.response.common.model.Image;

public class ImageResponseMessage extends ResponseMessage{
	
	private Image Image; 
	
	public ImageResponseMessage()
    {
    	MsgType=ResponseConstant.RESP_MESSAGE_TYPE_IMAGE;
    }
	
	public Image getImage() {
		return Image;
	}

	public void setImage(Image image) {
		Image = image;	
	}
	
	
}


