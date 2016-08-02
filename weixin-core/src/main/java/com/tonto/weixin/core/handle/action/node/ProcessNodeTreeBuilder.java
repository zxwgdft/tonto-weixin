package com.tonto.weixin.core.handle.action.node;


/**
 * 
 * 处理节点数构建者接口
 * 
 * @author TontoZhou
 *
 */
public interface ProcessNodeTreeBuilder {
	
	/**
	 * 构建节点树
	 * @return 返回根节点
	 */
	ProcessNode build();
	
	/**
	 * 获取根节点
	 * @return
	 */
	ProcessNode root();
}
