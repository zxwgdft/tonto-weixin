package com.tonto.weixin.core.handle.interceptor.common;

import com.tonto.weixin.core.handle.ProcessContext;
import com.tonto.weixin.core.handle.ProcessStatus;
import com.tonto.weixin.core.handle.action.node.ProcessNodeTreeBuilder;
import com.tonto.weixin.core.handle.interceptor.MessageInterceptor;
import com.tonto.weixin.core.handle.interceptor.ProcessInvocation;
import com.tonto.weixin.core.message.request.RequestMessage;
import com.tonto.weixin.core.message.request.common.TextRequestMessage;
import com.tonto.weixin.core.message.response.ResponseMessage;

/**
 * <h2>默认拦截器</h2>
 * <p>
 * 	拦截一些共同操作信息，例如当用户发送#符号时候表示跳到上一级，
 * 	这些默认操作符还有如下：
 *  <ul>
 *  	<li>##：返回主菜单</li>
 *  	<li>#：返回上一级菜单</li>
 *  	<li>?：显示当前需要输入的信息，或刷新当前信息</li>
 *  </ul>
 * </p>
 * @author	TontoZhou
 * @date	2015-4-22
 */
public class CommonMessageInterceptor implements MessageInterceptor{

	/*
	 * 处理节点树构建者
	 */
	ProcessNodeTreeBuilder processNodeTreeBuilder;
	
	@Override
	public ResponseMessage intercept(ProcessInvocation processInvocation) {
		
		RequestMessage request=processInvocation.getRequest();
		ProcessContext context=processInvocation.getProcessContext();
		
		if(request!=null&&request instanceof TextRequestMessage)
		{
			TextRequestMessage textRequest=(TextRequestMessage) request;
			String content=textRequest.getContent();
			if(content!=null)
			{
				content=content.trim();
				if(content.equals(BACK_TO_MAIN))
				{
					context.setProcessNode(processNodeTreeBuilder.root());
					context.setProcessStatus(ProcessStatus.INPUT_BEGIN);
				}
				else if(content.equals(BACK_TO_PARENT))
				{		
					context.setProcessNode(context.getProcessNode().getParent());
					context.setProcessStatus(ProcessStatus.INPUT_BEGIN);
				}
				else if(content.equals(TO_INPUT))
				{
					context.setProcessStatus(ProcessStatus.INPUT_BEGIN);
				}
				else
				{
					//do nothing
				}
			}
		}
		
		return processInvocation.invoke();
	}
	
	private final static String BACK_TO_MAIN="##";
	private final static String BACK_TO_PARENT="#";
	private final static String TO_INPUT="?";
	
	
	@Override
	public int getInterceptorLocation() {
		return 0;
	}


	public ProcessNodeTreeBuilder getProcessNodeTreeBuilder() {
		return processNodeTreeBuilder;
	}


	public void setProcessNodeTreeBuilder(ProcessNodeTreeBuilder processNodeTreeBuilder) {
		this.processNodeTreeBuilder = processNodeTreeBuilder;
	}
}
