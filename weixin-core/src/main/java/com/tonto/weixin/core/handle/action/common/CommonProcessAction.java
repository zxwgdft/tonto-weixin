package com.tonto.weixin.core.handle.action.common;

import com.tonto.weixin.core.handle.ProcessContext;
import com.tonto.weixin.core.handle.ProcessStatus;
import com.tonto.weixin.core.handle.action.ProcessAction;
import com.tonto.weixin.core.handle.session.Session;
import com.tonto.weixin.core.message.request.RequestMessage;
import com.tonto.weixin.core.message.response.ResponseMessage;
import com.tonto.weixin.core.message.response.common.TextResponseMessage;

/**
 * <h2>通用的处理Action</h2>
 * <p>默认动作</p>
 * 
 * @author TontoZhou
 * @date 2015-4-22
 */
public abstract class CommonProcessAction implements ProcessAction {

	// 输入提示信息
	private String inputTip = "";

	// 是否需要输入
	private boolean needInput = true;

	@Override
	public ResponseMessage input(Session session, RequestMessage request, ProcessContext processContext) {
		if (needInput) {
			TextResponseMessage response = new TextResponseMessage();
			response.setContent(inputTip);
			return response;
		} else {
			// 直接跳转执行execute方法
			processContext.setProcessStatus(ProcessStatus.EXECUTE_BEGIN);
			return null;
		}
	}

	public String getInputTip() {
		return inputTip;
	}

	public void setInputTip(String inputTip) {
		this.inputTip = inputTip;
	}

	public boolean isNeedInput() {
		return needInput;
	}

	public void setNeedInput(boolean needInput) {
		this.needInput = needInput;
	}

}
