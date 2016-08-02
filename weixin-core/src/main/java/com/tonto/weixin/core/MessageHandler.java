package com.tonto.weixin.core;

import com.tonto.weixin.core.message.request.RequestMessage;
import com.tonto.weixin.core.message.response.ResponseMessage;

/**
 * 
 * 消息请求处理接口
 * 
 * @author TontoZhou
 *
 */
public interface MessageHandler {
	
	/**
	 * 处理消息
	 * @param message
	 * @return
	 */
	ResponseMessage handle(RequestMessage message);
	
}
