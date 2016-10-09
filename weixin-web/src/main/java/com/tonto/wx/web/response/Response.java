package com.tonto.wx.web.response;

/**
 * 
 * 数据返回
 * 
 * @author TontoZhou
 *
 */
public class Response<T> {

	public final static int STATUS_SERVICE_SUCCESS = 20010;	
	public final static int STATUS_SERVICE_ERROR = 50010;	
	public final static int STATUS_SERVICE_FAIL = 50020;
	public final static int STATUS_NO_PERMISSION = 40120;
	public final static int STATUS_NO_LOGIN = 40110;
		
	int status;
	String message;
	T result;
		
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}
}
