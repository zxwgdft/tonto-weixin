package com.tonto.weixin.core.handle;

import org.apache.log4j.Logger;

import com.tonto.weixin.core.handle.action.ProcessAction;
import com.tonto.weixin.core.handle.action.node.ProcessNode;
import com.tonto.weixin.core.handle.action.node.ProcessNodeTreeBuilder;
import com.tonto.weixin.core.handle.interceptor.MessageInterceptor;
import com.tonto.weixin.core.handle.interceptor.ProcessInvocation;
import com.tonto.weixin.core.handle.session.Session;
import com.tonto.weixin.core.message.request.RequestMessage;
import com.tonto.weixin.core.message.response.ResponseMessage;
import com.tonto.weixin.core.message.response.common.TextResponseMessage;

/**
 * <h2>请求最终处理拦截器</h2>
 * <p>
 * 根据{@link ProcessStatus}处理当前{@link ProcessNode}中的{@link ProcessAction},规则
 * </p>
 * <ol>
 * <li>INPUT_BEGIN:执行input方法，并再次检查状态是否继续执行方法</li>
 * <li>EXECUTE_BEGIN:执行execute方法，并再次检查状态是否继续执行方法</li>
 * <li>INPUT_END:不执行方法，状态跳转到EXECUTE_BEGIN，结束</li>
 * <li>EXECUTE_END:不执行input方法，结束</li>
 * </ol>
 * <p>
 * 作为默认处理器{@link DefaultMessageHandler}的默认拦截器
 * </p>
 * 
 * @author TontoZhou
 * @date 2015-5-5
 */
public class ActionInterceptor implements MessageInterceptor {

	private final static Logger logger = Logger.getLogger(ActionInterceptor.class);

	/*
	 * 处理节点树构建者
	 */
	ProcessNodeTreeBuilder processNodeTreeBuilder;

	@Override
	public ResponseMessage intercept(ProcessInvocation processInvocation) {

		ProcessContext processContext = processInvocation.getProcessContext();
		RequestMessage requestMessage = processInvocation.getRequest();
		Session session = processInvocation.getSession();

		ResponseMessage response = null;
		try {
			while (true) {
				// 没节点则缺省为根节点
				if (processContext.processNode == null) {
					processContext.processNode = processNodeTreeBuilder.root();
					processContext.processStatus = ProcessStatus.INPUT_BEGIN;
					
					
					if (processContext.processNode == null)
					{
						TextResponseMessage textResponse = new TextResponseMessage();
						textResponse.setContent(exception_no_node);
						return textResponse;
					}
				}

				if (processContext.processStatus == null) {
					processContext.processStatus = ProcessStatus.INPUT_BEGIN;
				}
	
				ProcessAction action = processContext.processNode.getAction();

				if (action == null)
					throw new Exception("There is no ProcessAction in ProcessNode:" + processContext.processNode.getName());

				if (processContext.processStatus == ProcessStatus.INPUT_BEGIN) {
					// 设置状态结束，从而无需在action执行方法中设置，除非有特殊需要
					processContext.processStatus = ProcessStatus.INPUT_END;
					response = action.input(session, requestMessage, processContext);
				} else if (processContext.processStatus == ProcessStatus.EXECUTE_BEGIN) {
					// 设置状态结束，从而无需在action执行方法中设置，除非有特殊需要
					processContext.processStatus = ProcessStatus.EXECUTE_END;
					response = action.execute(session, requestMessage, processContext);
				}

				if (processContext.processStatus == ProcessStatus.INPUT_END
						|| processContext.processStatus == ProcessStatus.EXECUTE_END) {
					processContext.processStatus = ProcessStatus.EXECUTE_BEGIN;
					break;
				}

			}
		} catch (Exception e) {
			// 异常处理
			logger.error("动作拦截器异常", e);

			response = new TextResponseMessage();
			((TextResponseMessage) response).setContent(exception_response);

			// 回到主菜单
			processContext.processNode = processNodeTreeBuilder.root();
			processContext.processStatus = ProcessStatus.INPUT_BEGIN;
		}

		return response;
	}

	private static final String exception_response = "抱歉！服务器发生异常，请重试或待会再试。";
	private static final String exception_no_node = "对不起！并没有可用服务提供。";

	@Override
	public int getInterceptorLocation() {
		return -1;
	}

	public ProcessNodeTreeBuilder getProcessNodeTreeBuilder() {
		return processNodeTreeBuilder;
	}

	public void setProcessNodeTreeBuilder(ProcessNodeTreeBuilder processNodeTreeBuilder) {
		this.processNodeTreeBuilder = processNodeTreeBuilder;
	}

}
