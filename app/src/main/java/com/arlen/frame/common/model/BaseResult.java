package com.arlen.frame.common.model;

import java.io.Serializable;

public class BaseResult<T> implements Serializable {

	public int statusCode;
	public String msg;
	public T data;

	public boolean isSuccess() {
		return statusCode == 0;
	}
}
