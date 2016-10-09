package com.tonto.weixin.core;

import javax.servlet.http.HttpServletRequest;

import com.tonto.weixin.core.message.request.RequestMessage;

/**
 * 
 * 消息解码
 * 
 * @author TontoZhou
 *
 */
public interface MessageDecoder {
	
	/**
	 * 解析请求解码出{@link RequestMessage}
	 * @param request
	 * @return
	 */
	RequestMessage decode(HttpServletRequest request);
}
