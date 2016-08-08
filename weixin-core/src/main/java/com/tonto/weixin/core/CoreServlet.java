package com.tonto.weixin.core;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.tonto.weixin.core.message.request.RequestMessage;
import com.tonto.weixin.core.message.response.ResponseMessage;
import com.tonto.weixin.core.MessageDecoder;
import com.tonto.weixin.core.MessageEncoder;
import com.tonto.weixin.core.MessageHandler;

/**
 * 
 * <h2>微信接入服务器</h2>
 * <p>
 * GET请求提供微信接入验证
 * </p>
 * <p>
 * POST请求提供微信其他请求服务，分为解码，处理，编码3个部分
 * {@link MessageEncoder}， {@link MessageHandler}接口
 * </p>
 * 
 * @author TontoZhou
 * 
 */
public class CoreServlet extends HttpServlet {

	private static final long serialVersionUID = -6966873417148403626L;

	private static final Logger logger = Logger.getLogger(CoreServlet.class);

	private String token;

	private MessageDecoder decoder;
	private MessageEncoder encoder;
	private MessageHandler handler;

	private static MessageHandler messageHandler;
	private static CoreServlet coreServlet;

	@Override
	public void init() throws ServletException {

		logger.info("-----------------------初始化CoreServlet---------------------");

		token = getInitParameter("token");

		if (messageHandler != null)
			handler = messageHandler;

		if (decoder == null)
			decoder = new DefaultMessageDecoder();

		if (encoder == null)
			encoder = new DefaultMessageEncoder();

		coreServlet = this;
	}

	/**
	 * get方式用于接收微信链接验证
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");

		PrintWriter out = response.getWriter();

		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		try {

			logger.info("进行微信链接验证：signature:" + signature + "/timestamp:" + timestamp + "/echostr:" + echostr + "/nonce:"
					+ nonce);
			
			boolean result = SignUtil.checkSignature(signature, timestamp, nonce, token);

			if (result) {
				out.print(echostr);
			} else {
				logger.error("微信请求确认失败！不能确定为微信服务器发送请求！");
			}

		} catch (Exception e) {
			logger.error("微信请求确认错误！", e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		// 解码消息
		RequestMessage requestMessage = decoder.decode(request);
		// 处理消息
		ResponseMessage responseMessage = handler.handle(requestMessage);
		// 编码消息
		String respContent = encoder.encode(responseMessage);

		if (logger.isDebugEnabled())
			logger.debug("send context:" + respContent);

		PrintWriter out = response.getWriter();
		out.print(respContent);
		out.close();
	}

	public MessageDecoder getDecoder() {
		return decoder;
	}

	public void setDecoder(MessageDecoder decoder) {
		this.decoder = decoder;
	}

	public MessageEncoder getEncoder() {
		return encoder;
	}

	public void setEncoder(MessageEncoder encoder) {
		this.encoder = encoder;
	}

	public MessageHandler getHandler() {
		return handler;
	}

	public void setHandler(MessageHandler handler) {
		this.handler = handler;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public static void setMessageHandler(MessageHandler messageHandler) {
		CoreServlet.messageHandler = messageHandler;
		if (coreServlet != null)
			coreServlet.handler = messageHandler;
	}

	public static CoreServlet getCoreServlet() {
		return coreServlet;
	}

}
