package com.tonto.wx.web.response;

public class CommonResponse<T> extends Response<T>{

	public CommonResponse() {
	}

	public CommonResponse(int status) {
		this.status = status;
	}

	public CommonResponse(int status, T result, String msg) {
		this.status = status;
		this.result = result;
		this.message = msg;
	}	
	
	public CommonResponse(ResponseStatus status) {
		this.status = status.getSystemCode();
		this.message = status.message;
	}
	
	public static <T> CommonResponse<T> createSuccessResponse(T result, String msg){
		return new CommonResponse<T>(STATUS_SERVICE_SUCCESS, result, msg);
	}
	
	public static <T> CommonResponse<T> createSuccessResponse(T result){
		return new CommonResponse<T>(STATUS_SERVICE_SUCCESS, result, "请求成功");
	}
	
	public static CommonResponse<?> createSuccessResponse(){
		return new CommonResponse<Object>(STATUS_SERVICE_SUCCESS, null, "");
	}
	
	public static <T> CommonResponse<T> createErrorResponse(T result, String msg){
		return new CommonResponse<T>(STATUS_SERVICE_ERROR, result, msg);
	}
	
	public static CommonResponse<?> createErrorResponse(String msg){
		return new CommonResponse<Object>(STATUS_SERVICE_ERROR, null, msg);
	}
	
	public static CommonResponse<?> createErrorResponse(Exception ex){
		return new CommonResponse<Object>(STATUS_SERVICE_ERROR, null, ex.getMessage());
	}
}
