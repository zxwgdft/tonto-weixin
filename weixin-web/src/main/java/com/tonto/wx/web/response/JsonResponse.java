package com.tonto.wx.web.response;

public class JsonResponse<T> {
	
	
	private int status;
	private String message;
	private T result;
	
	public JsonResponse()
	{
	}
	
	public JsonResponse(int status)
	{
		this.status=status;
	}
	
	public JsonResponse(int status,T result,String msg)
	{
		this.status=status;
		this.result=result;
		this.message=msg;
	}
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
	
	
	
	public static JsonResponse<?> getSuccessResponse()
	{
		return new JsonResponse<Object>(Response.STATUS_SUCCESS);
	}
	public static JsonResponse<?> getSuccessResponse(String msg)
	{
		return new JsonResponse<Object>(Response.STATUS_SUCCESS,null,msg);
	}
	public static JsonResponse<?> getUnLoginResponse()
	{
		return new JsonResponse<Object>(Response.STATUS_NO_LOGIN);
	}
	public static JsonResponse<?> getUnLoginResponse(String msg)
	{
		return new JsonResponse<Object>(Response.STATUS_NO_LOGIN,null,msg);
	}
	public static JsonResponse<?> getNoPermissionResponse()
	{
		return new JsonResponse<Object>(Response.STATUS_NO_PERMISSION);
	}
	public static JsonResponse<?> getNoPermissionResponse(String msg)
	{
		return new JsonResponse<Object>(Response.STATUS_NO_PERMISSION,null,msg);
	}
	public static JsonResponse<?> getErrorResponse()
	{
		return new JsonResponse<Object>(Response.STATUS_ERROR);
	}
	public static JsonResponse<?> getErrorResponse(String msg)
	{
		return new JsonResponse<Object>(Response.STATUS_ERROR,null,msg);
	}
	public static JsonResponse<?> getFailResponse()
	{
		return new JsonResponse<Object>(Response.STATUS_FAIL);
	}
	public static JsonResponse<?> getFailResponse(String msg)
	{
		return new JsonResponse<Object>(Response.STATUS_FAIL,null,msg);
	}
	
	

	
}
