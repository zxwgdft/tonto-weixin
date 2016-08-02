package com.tonto.weixin.core.handle.interceptor;

import java.util.List;

import com.tonto.weixin.core.handle.ProcessContext;
import com.tonto.weixin.core.handle.session.Session;
import com.tonto.weixin.core.message.request.RequestMessage;
import com.tonto.weixin.core.message.response.ResponseMessage;

/**
 * 
 * 处理调用对象，用于拦截器间的上下调用
 * 
 * @author TontoZhou
 *
 */
public class ProcessInvocation {

	ProcessContext processContext;
	Session session;
	RequestMessage request;

	List<MessageInterceptor> interceptorStack;
	int currentInterceptorIndex;

	public ResponseMessage invoke() {
		if (currentInterceptorIndex < interceptorStack.size()) {
			MessageInterceptor interceptor = interceptorStack.get(currentInterceptorIndex++);
			return interceptor.intercept(this);
		} else {
			return null;
		}
	}

	public ProcessContext getProcessContext() {
		return processContext;
	}

	public void setProcessContext(ProcessContext processContext) {
		this.processContext = processContext;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public RequestMessage getRequest() {
		return request;
	}

	public void setRequest(RequestMessage request) {
		this.request = request;
	}

	public List<MessageInterceptor> getInterceptorStack() {
		return interceptorStack;
	}

	public void setInterceptorStack(List<MessageInterceptor> interceptorStack) {
		this.interceptorStack = interceptorStack;
		currentInterceptorIndex = 0;
	}

}
