package com.zhangwei95.exception;

import com.netflix.hystrix.exception.HystrixBadRequestException;

/**
 * 继承HystrixBadRequestException  不会被熔断
 */
public class BusinessException extends HystrixBadRequestException {
	
	private String message;
	
	public BusinessException(String message) {
		super(message);
		this.message = message;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
