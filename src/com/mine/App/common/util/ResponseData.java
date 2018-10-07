package com.mine.App.common.util;

import java.io.Serializable;

import org.apache.log4j.Logger;

/**
 * 所有请求的返回值
 * @author db2admin
 *
 */
public class ResponseData  implements Serializable{

	private static final long serialVersionUID = -4022778560853812030L;

	private Logger logger=Logger.getLogger(this.getClass().getName());
	/**
	 * 请求成功
	 */
	private boolean success=true;

	/**
	 * 请求状态 0-失败 1-成功
	 */
	private int status=1;

	/**
	 * 提示信息
	 */
	private String message="请求成功";

	/**
	 * 返回数据data
	 */
	private Object data=null;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		logger.info("ResponseData字符串：");
		return "ResponseData [data=" + data + "\n,message=" + message
				+ "\n, status=" + status + "\n, success=" + success
				+ "\n, getData()=" + getData() + "\n, getMessage()=" + getMessage()
				+ "\n, getStatus()=" + getStatus() + "\n, isSuccess()="
				+ isSuccess() + "\n, getClass()=" + getClass() + "\n, hashCode()="
				+ hashCode() + "\n, toString()=" + super.toString() + "]";
	}
}
