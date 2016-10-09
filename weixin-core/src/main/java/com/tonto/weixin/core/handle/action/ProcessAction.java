package com.tonto.weixin.core.handle.action;

import com.tonto.weixin.core.handle.ProcessContext;
import com.tonto.weixin.core.handle.session.Session;
import com.tonto.weixin.core.message.request.RequestMessage;
import com.tonto.weixin.core.message.response.ResponseMessage;

/**
 * <p>处理动作</p>
 * <p>每一个处理动作分输入(input)和执行(execute)两部，分先后顺序</p>
 * <ul>
 * 		<li>输入：让用户输入一些用于具体执行处理时需要的资源，例如几个参数或者图片声音等</li>
 * 		<li>执行：对用户输入的内容进行处理并返回，从而提供一项服务</li>
 * </ul>
 * <p>返回{@link ResponseMessage}可以不赋值，处理程序会自动填上，但是其子类的属性需要自己set进去</p>
 * @author	TontoZhou
 * @date	2015-4-20
 */
public interface ProcessAction {
	
	/**
	 * <p>输入步骤，如果没有则可以直接跳转到下一步->执行</p>
	 * @param session	与微信用户的会话
	 * @param request	请求消息
	 * @param context	处理上下文
	 * @return	回复消息
	 */
	ResponseMessage input(Session session,RequestMessage request,ProcessContext context);
	
	/**
	 * 执行步骤，执行具体的业务 
	 * @param session	与微信用户的会话
	 * @param request	请求消息
	 * @param context	处理上下文
	 * @return	回复消息
	 */
	ResponseMessage execute(Session session,RequestMessage message,ProcessContext context);
	
}
