package com.tonto.weixin.core.handle.action.common;

import java.util.List;

import com.tonto.weixin.core.handle.ProcessContext;
import com.tonto.weixin.core.handle.ProcessStatus;
import com.tonto.weixin.core.handle.action.ProcessAction;
import com.tonto.weixin.core.handle.action.node.ProcessNode;
import com.tonto.weixin.core.handle.session.Session;
import com.tonto.weixin.core.message.EditConstant;
import com.tonto.weixin.core.message.request.RequestMessage;
import com.tonto.weixin.core.message.request.common.TextRequestMessage;
import com.tonto.weixin.core.message.response.ResponseMessage;
import com.tonto.weixin.core.message.response.common.TextResponseMessage;


/**
 * <h2>菜单动作处理</h2>
 * <p>默认动作</p>
 * 
 * @author TontoZhou
 *
 */
public class MenuAction implements ProcessAction{

	@Override
	public ResponseMessage input(Session session, RequestMessage request,ProcessContext processContext) {
		
		ProcessNode processNode =processContext.getProcessNode();
		
		//拼接回复菜单信息
		StringBuilder sb=new StringBuilder();
		
		String line_break=EditConstant.LINE_BREAK;
		
		sb.append(processNode.getName())
			.append(line_break).append("请选择：").append(line_break);
		
		List<ProcessNode> nodes=processNode.getChildren();
		
		if(nodes!=null)
		{
			int size=nodes.size();
			for(int i=0;i<size;i++)
			{
				ProcessNode node=nodes.get(i);
				sb.append(i+1).append(". ")
					.append(node.getName()).append(line_break);
			}
		}	
		
		sb.append(0).append(". ")
			.append("返回上一级菜单").append(line_break);
		
		String content=sb.toString();			
		
		
		TextResponseMessage response=new TextResponseMessage();
		response.setContent(content);
		
		return response;
	}

	@Override
	public ResponseMessage execute(Session session, RequestMessage message,ProcessContext processContext) {
		
		ProcessNode processNode =processContext.getProcessNode();
		
		if(message instanceof TextRequestMessage)
		{
			TextRequestMessage textMessage=(TextRequestMessage) message;
			String content=textMessage.getContent();
				
			try{
				Integer index=Integer.valueOf(content);
				ProcessNode childNode=processNode.getChildNodeByIndex(index-1);
				if(childNode!=null)
				{
					processContext.setProcessNode(childNode);
					processContext.setProcessStatus(ProcessStatus.INPUT_BEGIN);				
					return null;
				}
			}
			catch(Exception e)
			{
				//输入错误字符，不做处理
				
			}
			
		}
		
		
		return input(session,message,processContext);
		
	}
	
}
