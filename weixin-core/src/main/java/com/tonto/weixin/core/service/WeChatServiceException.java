package com.tonto.weixin.core.service;


/**
 * 
 * 微信服务异常
 * 
 * @author TontoZhou
 *
 */
public class WeChatServiceException extends RuntimeException{
	
	private static final long serialVersionUID = -1783681423199929989L;

	public WeChatServiceException(String msg)
	{
		super(msg);
	}
	
	public WeChatServiceException(String msg, Throwable cause)
	{
		super(msg, cause);
	}
	
}
