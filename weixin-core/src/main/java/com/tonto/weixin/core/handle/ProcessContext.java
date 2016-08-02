package com.tonto.weixin.core.handle;

import com.tonto.weixin.core.handle.action.node.ProcessNode;
import com.tonto.weixin.core.message.request.RequestMessage;
import com.tonto.weixin.core.message.request.common.CommonRequestMessage;
import com.tonto.weixin.core.message.request.event.EventMessage;
import com.tonto.weixin.core.message.response.ResponseMessage;

/**
 * 处理上下文
 * 
 * <p>
 * 	
 * </p>
 * 
 * @author TontoZhou
 * @date 2015-4-20
 */
public class ProcessContext {

	ProcessNode processNode; // 处理节点
	ProcessStatus processStatus; // 处理状态（用于action执行的状态）

	RequestMessage request; // 处理的请求消息
	ResponseMessage response; // 处理得到的相应消息
	
	long createTime;
	int status; 		// 处理状态（用于消息排重的状态）0:没请求，1：有请求，但没等待的请求，2：有等待的请求，3：处理结束

	/**
	 * 是否有请求在执行
	 * @return
	 */
	boolean hasRequest() {
		return status>0;
	}
	
	boolean hasWaitRequest() {
		return status==2;
	}
	
	boolean hasResponse() {
		return status==3;
	}
	
	/**
	 * 是否同一请求
	 * <p>如果是事件消息，比较创建时间，如果是普通消息比较消息ID</p>
	 * @param request
	 * @return
	 */
	boolean isSameRequest(RequestMessage request) {
		if (this.request instanceof EventMessage) {
			if (request instanceof EventMessage) {
				return request.getCreateTime() == this.request.getCreateTime();
			}
		} else {
			if (request instanceof CommonRequestMessage) {

				return ((CommonRequestMessage) request).getMsgId() == ((CommonRequestMessage) this.request)
						.getMsgId();
			}
		}
		return false;
	}
	
	boolean isOldRequest(RequestMessage requestMessage) {
		return requestMessage.getCreateTime()<createTime;
	}

	
	void startRequest(RequestMessage request)
	{
		this.request=request;
		createTime=request.getCreateTime();
		status=1;
	}
	
	void waitRequest() {
		status=2;
	}
	
	void responseRequest(ResponseMessage response)
	{
		this.response=response;
		status=3;
	}
	
	void endRequest()
	{
		request=null;
		response=null;
		status=0;
	}

	public ProcessNode getProcessNode() {
		return processNode;
	}

	public void setProcessNode(ProcessNode processNode) {
		this.processNode = processNode;
	}

	public ProcessStatus getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(ProcessStatus processStatus) {
		this.processStatus = processStatus;
	}

	public RequestMessage getRequest() {
		return request;
	}
	
}
