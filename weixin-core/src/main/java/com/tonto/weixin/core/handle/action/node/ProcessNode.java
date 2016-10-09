package com.tonto.weixin.core.handle.action.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tonto.weixin.core.handle.action.ProcessAction;



/**
 * <p>处理节点</p>
 * <p>
 * 		
 * 
 * </p>
 * @author	TontoZhou
 * @date	2015-4-20
 */
public class ProcessNode {
	
	Integer id;								//用于标识
	String name;							//节点名称
	ProcessNode parent;						//父节点
	List<ProcessNode> children;				//子节点
	Map<Integer,ProcessNode> childrenMap;	//子节点map
	ProcessAction action;					//执行动作
	
	public ProcessNode()
	{
		this.children=new ArrayList<ProcessNode>();
		this.childrenMap=new HashMap<Integer,ProcessNode>();
	}
	
	/**
	 * 根据子节点的Id获取节点
	 * @param id	子节点的Id
	 * @return
	 */
	public ProcessNode getChildNodeById(Integer id)
	{
		if(childrenMap==null)
			return null;
		return childrenMap.get(id);
	}	
	
	/**
	 * 根据子节点序列获取子节点
	 * @param index	子节点列表中的序列号
	 * @return	越界或无子节点返回null
	 */
	public ProcessNode getChildNodeByIndex(Integer index)
	{	
		if(children!=null&&children.size()>index)
			return children.get(index);
		return null;
	}
	
	public List<ProcessNode> getChildren() {
		return children;
	}
	
	/**
	 * 重新设置所有子节点
	 * @param children
	 */
	public void setChildren(List<ProcessNode> children) {
		this.children=new ArrayList<ProcessNode>();
		this.childrenMap=new HashMap<Integer,ProcessNode>();
		
		if(children!=null)
		{
			for(ProcessNode child:children)
			{
				Integer cid=child.getId();
				if(!childrenMap.containsKey(cid))
				{
					this.children.add(child);
					this.childrenMap.put(cid, child);
				}
			}
		}
	}
	
	/**
	 * 原有基础上添加一个子节点
	 * @param child
	 */
	public void addChild(ProcessNode child)
	{			
		if(child==null)
			return;
		
		Integer cid=child.getId();
		if(!childrenMap.containsKey(cid))
		{
			this.children.add(child);
			this.childrenMap.put(cid, child);
		}
	}
		
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ProcessNode getParent() {
		return parent;
	}
	public void setParent(ProcessNode parent) {
		this.parent = parent;
	}
	public ProcessAction getAction() {
		return action;
	}
	public void setAction(ProcessAction action) {
		this.action = action;
	}


}
