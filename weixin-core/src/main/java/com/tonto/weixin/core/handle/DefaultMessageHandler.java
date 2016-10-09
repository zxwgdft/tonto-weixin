package com.tonto.weixin.core.handle;

import java.util.List;

import com.tonto.weixin.core.MessageHandler;
import com.tonto.weixin.core.handle.action.ProcessAction;
import com.tonto.weixin.core.handle.interceptor.InterceptorContainer;
import com.tonto.weixin.core.handle.interceptor.MessageInterceptor;
import com.tonto.weixin.core.handle.interceptor.ProcessInvocation;
import com.tonto.weixin.core.handle.session.Session;
import com.tonto.weixin.core.handle.session.SessionContainer;
import com.tonto.weixin.core.message.request.RequestMessage;
import com.tonto.weixin.core.message.response.ResponseMessage;
import com.tonto.weixin.core.message.response.common.TextResponseMessage;

/**
 * <h2>微信消息处理</h2>
 * <ol>
 * 处理过程
 * <li>获取{@link Session},从中得到上次请求后保存在session中的{@link ProcessContext}</li>
 * <li>判断当前session是否有其他请求正在处理，如果有则当前线程等待处理，如果没则执行下面操作</li>
 * <li>设置{@link ProcessInvocation}，并调用他</li>
 * <li>判断当前session是否有其他请求正在等待，如果有则唤醒他们，并让他们返回处理结果</li>
 * <li>封装{@link ResponseMessage}的基本信息</li>
 * </ol>
 * <p>
 * 注：如果需要跳转执行某个方法或节点，在{@linkplain ProcessAction}的方法中执行改变{@link ProcessContext}
 * 的相应值就行
 * </p>
 * 
 * @author TontoZhou
 * @date 2015-4-20
 */
public class DefaultMessageHandler implements MessageHandler {

	private final static ThreadLocal<ProcessInvocation> processInvocations = new ThreadLocal<ProcessInvocation>() {

		@Override
		public ProcessInvocation get() {
			ProcessInvocation invocation = super.get();
			if (invocation == null) {
				invocation = new ProcessInvocation();
				super.set(invocation);
			}
			return invocation;
		}

	};

	// 拦截器栈管理器
	InterceptorContainer interceptorContainer;
	// 微信会话管理器
	SessionContainer sessionContainer;

	@Override
	public ResponseMessage handle(RequestMessage requestMessage) {

		if (requestMessage == null) {
			// 该错误不做处理
			return null;
		}

		String username = requestMessage.getFromUserName();
		if (username == null) {
			// 该错误不做处理
			return null;
		}

		Session session = null;

		synchronized (this) {
			session = sessionContainer.getSession(username);

			if (session == null) {
				// 没有则创建
				session = sessionContainer.addSession(username);
			}
		}

		// 跟新session的接触时间
		session.updateAccessTime();

		ProcessContext processContext = null;

		synchronized (session) {
			processContext = (ProcessContext) session.getAttribute(process_context);

			if (processContext == null) {
				processContext = new ProcessContext();
				session.putAttribute(process_context, processContext);
			}

			if (processContext.hasRequest()) {
				// 有正在执行的请求时，需要判断是否需要等待
				if (processContext.isSameRequest(requestMessage)) {
					// 判断是否已经有处理后的响应
					if (!processContext.hasResponse()) {
						// 没有响应则进入等待状态
						processContext.waitRequest();
						session.notifyAll();
						try {
							// System.out.println(Thread.currentThread()+" begin wait");
							session.wait(wait_time);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

					// 判断是否已经有处理后的响应
					if (processContext.hasResponse()) {
						// System.out.println(Thread.currentThread()+" response request");
						ResponseMessage response = processContext.response;
						// 清楚处理上下文中的请求状态
						processContext.endRequest();
						return response;
					} else {
						// System.out.println(Thread.currentThread()+" no response so i gone");
						// 没有需要返回的响应
						return null;
					}

				} else {
					// 当正在处理一个请求时，其他请求不予处理
					TextResponseMessage response = new TextResponseMessage();
					response.setContent(repeat_warning);
					response.setToUserName(session.getUsername());
					response.setFromUserName(requestMessage.getToUserName());
					response.setCreateTime(System.currentTimeMillis() / 1000);
					return response;
				}
			} else {
				// System.out.println(Thread.currentThread()+" begin request");
				// 判断是否是旧的请求，可能微信重发了某请求，而正好服务器也已经完成请求时，则可能接受到一个旧请求
				if (processContext.isOldRequest(requestMessage))
					return null;
				processContext.startRequest(requestMessage);
			}
		}

		ProcessInvocation processInvocation = processInvocations.get();
		processInvocation.setRequest(requestMessage);
		processInvocation.setSession(session);
		processInvocation.setProcessContext(processContext);

		List<MessageInterceptor> interceptors = interceptorContainer.getMessageInterceptors(requestMessage.getMsgType());
		processInvocation.setInterceptorStack(interceptors);

		ResponseMessage response = processInvocation.invoke();

		if (response != null) {
			response.setToUserName(session.getUsername());
			response.setFromUserName(requestMessage.getToUserName());
			response.setCreateTime(System.currentTimeMillis() / 1000);
		}

		// 同步线程属性到session中
		synchronized (session) {
			if (processContext.hasWaitRequest()) {				
				// System.out.println(Thread.currentThread()+" notify wait request to response");
				processContext.responseRequest(response);
				session.notifyAll();
				return null;
			} else {
				// System.out.println(Thread.currentThread()+" no wait request so i response");
				processContext.endRequest();
				return response;
			}
		}

	}

	private static final String repeat_warning = "在请求处理期间发送的消息将不予受理！";

	private static final int wait_time = 5000;

	private static final String process_context = "processcontext";

	public InterceptorContainer getInterceptorContainer() {
		return interceptorContainer;
	}

	public void setInterceptorContainer(InterceptorContainer interceptorContainer) {
		this.interceptorContainer = interceptorContainer;
	}

	public SessionContainer getSessionContainer() {
		return sessionContainer;
	}

	public void setSessionContainer(SessionContainer sessionContainer) {
		this.sessionContainer = sessionContainer;
	}

}
