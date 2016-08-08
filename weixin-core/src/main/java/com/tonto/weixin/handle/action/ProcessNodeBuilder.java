package com.tonto.weixin.handle.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.tonto.weixin.core.handle.action.ProcessAction;
import com.tonto.weixin.core.handle.action.common.CommonProcessAction;
import com.tonto.weixin.core.handle.action.common.MenuAction;
import com.tonto.weixin.core.handle.action.node.ProcessNode;
import com.tonto.weixin.core.handle.action.node.ProcessNodeTreeBuilder;
import com.tonto.weixin.spring.SpringBeanHolder;

/**
 * 
 * 处理节点构建器
 * 
 * @author TontoZhou
 * 
 */
public class ProcessNodeBuilder implements ProcessNodeTreeBuilder {

	private static final Logger logger = Logger.getLogger(ProcessNodeBuilder.class);

	private ProcessNode root;

	// -------------------------------------
	//
	// 根据xml生成节点根目录
	//
	// -------------------------------------

	String xmlPath;

	@Override
	public ProcessNode build() {

		logger.info("-------------开始创建处理节点-------------");

		InputStream input = null;

		if (xmlPath == null) {
			input = ProcessNodeBuilder.class.getClassLoader().getResourceAsStream("menu.xml");
			if (input == null) {
				throw new RuntimeException("类根目录下未找到menu.xml文件！");
			}
		} else {
			try {
				input = new FileInputStream(xmlPath);
			} catch (FileNotFoundException e) {
				throw new RuntimeException("未找到文件：" + xmlPath, e);
			}
		}

		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(input);
			Element root = document.getRootElement();

			ProcessNode rootNode = parseNode(root, null);
			rootNode.setParent(rootNode);

			logger.info("-------------处理节点初始化完成！-------------");	
			this.root = rootNode;
			return rootNode;

		} catch (Exception e) {
			throw new RuntimeException("解析xml文件错误！", e);
		}

	}

	private final static String MENU = "menu";
	private final static String ACTION = "action";
	private final static String ATTRIBUTE_NAME = "name";
	private final static String ATTRIBUTE_ID = "id";
	private final static String ATTRIBUTE_CLASS = "class";
	private final static String ATTRIBUTE_INPUT = "input";

	private ProcessNode parseNode(Element element, ProcessNode parent) {

		String type = element.getName();
		String name = element.attributeValue(ATTRIBUTE_NAME);
		Integer id = Integer.valueOf(element.attributeValue(ATTRIBUTE_ID));

		ProcessNode processNode = new ProcessNode();

		processNode.setId(id);
		processNode.setName(name);
		processNode.setParent(parent);

		@SuppressWarnings("unchecked")
		List<Element> elementList = element.elements();

		if (elementList != null && elementList.size() > 0) {
			for (Element childElement : elementList) {
				processNode.addChild(parseNode(childElement, processNode));
			}
		}

		if (type.equals(MENU)) {
			processNode.setAction(new MenuAction());
		} else if (type.equals(ACTION)) {

			String clazzNmae = element.attributeValue(ATTRIBUTE_CLASS);
			String input = element.attributeValue(ATTRIBUTE_INPUT);

			ProcessAction action = (ProcessAction) SpringBeanHolder.getBean(clazzNmae);

			if (action != null) {
				processNode.setAction(action);

				if (action instanceof CommonProcessAction) {
					CommonProcessAction commonAction = (CommonProcessAction) action;

					if (input != null) {
						commonAction.setInputTip(input);
						commonAction.setNeedInput(true);
					} else {
						commonAction.setNeedInput(false);
					}
				}
			} else {
				logger.error("ProcessNode:" + name + " init error!ProcessAction is null!");
			}

		}

		if (logger.isDebugEnabled()) {
			logger.info("创建节点:id:" + processNode.getId() + "/name:" + processNode.getName());
		}
		return processNode;
	}

	public String getXmlPath() {
		return xmlPath;
	}

	public void setXmlPath(String xmlPath) {
		this.xmlPath = xmlPath;
	}

	public ProcessNode root() {
		return root;
	}
}
