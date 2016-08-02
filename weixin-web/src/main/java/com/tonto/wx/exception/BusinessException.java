package com.tonto.wx.exception;

public class BusinessException extends RuntimeException{

	private static final long serialVersionUID = 8557617839514732444L;
	
	public BusinessException(String msg)
	{
		super(msg);
	}
}
