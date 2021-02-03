package com.zs.core.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//通用执行结果类，用于报错执行的结果信息
public class ExecuteResult {

	private String result = "success";//操作结果
	private String message = "";//提示信息
	private Map redirURLs = new HashMap();//下一步操作
	
	public void setResult(String result) {
		this.result = result;
	}

	public void addRedirURL(String desc, String url) {
		this.redirURLs.put(desc, url);
	}

	public void addMessage(String msg) {
		this.message=(msg);
	}

	public String getResult() {
		return result;
	}

	public Map getRedirURLs() {
		return redirURLs;
	}
	
	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "ExecuteResult [result=" + result + ", message=" + message + ", redirURLs=" + redirURLs + "]";
	}
	
}