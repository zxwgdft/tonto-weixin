package com.tonto.weixin.core.handle.interceptor;

import com.tonto.weixin.core.message.response.ResponseMessage;

/**
 * 
 * 消息拦截器
 * 
 * @author TontoZhou
 *
 */
public interface MessageInterceptor {
	
	/**
	 * <h2>拦截消息处理</h2>
	 * <p>如果返回<code>null</code>则继续其他拦截器，否则跳过其他拦截器</p>
	 * @param session
	 * @param request
	 * @return
	 */	
	ResponseMessage intercept(ProcessInvocation processInvocation);
	
	/**
	 * 获取拦截器位置
	 * @return
	 */
	int getInterceptorLocation();

	

}
