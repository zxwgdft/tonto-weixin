package com.tonto.weixin.core.handle.interceptor;

import java.util.List;

import com.tonto.weixin.core.IContainer;


/**
 * 
 * <h2>拦截器容器</h2>
 * <p></p>
 * 
 * 
 * @author TontoZhou
 *
 */
public interface InterceptorContainer extends IContainer{
	
	/**
	 * 获取该消息类型拦截器列表
	 * @param messageType
	 * @return
	 */
	public List<MessageInterceptor> getMessageInterceptors(String messageType);

	/**
	 * 获取事件拦截器列表
	 * @return
	 */
	public List<MessageInterceptor> getEventInterceptors();
	
}
