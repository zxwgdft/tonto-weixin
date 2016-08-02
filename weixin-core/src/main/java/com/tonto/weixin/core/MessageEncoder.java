package com.tonto.weixin.core;

import com.tonto.weixin.core.message.response.ResponseMessage;


/**
 * 
 * 消息编码
 * 
 * @author TontoZhou
 *
 */
public interface MessageEncoder {
	
	/**
	 * 对{@link ResponseMessage}进行编码，生成对应的xml格式字符串
	 * @param message
	 * @return
	 */
	String encode(ResponseMessage message);
}
