package com.tonto.weixin.core.message.request.common;

import com.tonto.weixin.core.message.request.RequestConstant;


public class TextRequestMessage extends CommonRequestMessage{
	// 消息内容  
    private String Content; 
    
    public TextRequestMessage()
    {
    	MsgType=RequestConstant.REQ_MESSAGE_TYPE_TEXT;
    }
  
    public String getContent() {  
        return Content;  
    }  
  
    public void setContent(String content) {  
        Content = content;  
    }  
}
