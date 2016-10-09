package com.tonto.weixin.core.message.response.common;

import com.tonto.weixin.core.message.response.ResponseConstant;
import com.tonto.weixin.core.message.response.ResponseMessage;


public class TextResponseMessage extends ResponseMessage{
	// 消息内容  
    private String Content; 
    
    public TextResponseMessage()
    {
    	MsgType=ResponseConstant.RESP_MESSAGE_TYPE_TEXT;
    }
  
    public String getContent() {  
        return Content;  
    }  
  
    public void setContent(String content) {  
        Content = content;  
    }  
}
