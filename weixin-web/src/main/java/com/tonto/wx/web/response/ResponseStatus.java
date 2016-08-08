package com.tonto.wx.web.response;

/**
 * 
 * 错误状况
 * 
 * @author TontoZhou
 * 
 */
public enum ResponseStatus {
	
	NO_LOGIN(401, Response.STATUS_NO_LOGIN, "您还未登录，请登录后继续访问！"), 
	NO_PERMISSION(401, Response.STATUS_NO_PERMISSION, "您没有访问该功能的权限！"), 
	SERVICE_SUCCESS(200, Response.STATUS_SERVICE_SUCCESS, "请求成功"), 
	SERVICE_ERROR(500, Response.STATUS_SERVICE_ERROR, "Internal Server Error"), 
	SERVICE_FAIL(503, Response.STATUS_SERVICE_FAIL, "Service Unavailable");

	int httpCode;
	int systemCode;
	String message;

	ResponseStatus(int httpCode, int systemCode, String message) {
		this.httpCode = httpCode;
		this.message = message;
		this.systemCode = systemCode;
	}

	public String getMessage() {
		return message;
	}

	public int getHttpCode() {
		return httpCode;
	}

	public int getSystemCode() {
		return systemCode;
	}

	/**
	 * 根据系统码获取状态
	 * 
	 * @param systemCode
	 * @return
	 */
	public static ResponseStatus getErrorStatus(int systemCode) {

		for (ResponseStatus status : ResponseStatus.values()) {
			if (status.systemCode == systemCode)
				return status;
		}

		return null;
	}

}
