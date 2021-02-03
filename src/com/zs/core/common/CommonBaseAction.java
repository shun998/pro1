package com.zs.core.common;

import java.util.Date;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.zs.core.Constants;
import com.zs.core.util.QueryUtil;
//action基类
@Controller
public class CommonBaseAction  {
	/* 日志 */
	protected final Log log = LogFactory.getLog(getClass());
	public static final String LOGIN_ERR = Constants.LOGIN_ERR;
	public static final String LOGIN_PROMPT = Constants.LOGIN_PROMPT;
	public static final String HOME_PAGE = Constants.HOME_PAGE;
	public static final String ERROR = Constants.ERROR;
	public static final String SUCCESS = Constants.SUCCESS;
	public static final String INDEX_PAGE = Constants.INDEX_PAGE;
	
	/* 通用操作结果返回页 */
	public static final String EXECUTE_RESULT = Constants.EXECUTE_RESULT;
	private ExecuteResult executeResult;
	private ExecuteResult buildExecuteResult() {
		HttpSession session=getSession();		
		executeResult=(ExecuteResult)session.getAttribute(Constants.EXECUTE_RESULT_NAME);		
		if (executeResult == null) {
			executeResult = new ExecuteResult();		
			session.setAttribute(Constants.EXECUTE_RESULT_NAME, executeResult);			
		}
		return executeResult;	
	}
	public void setResult(String result) { buildExecuteResult().setResult(result); }
	public void addMessage(String message) { buildExecuteResult().addMessage(message);}
	public void addRedirURL(String desc, String url) { buildExecuteResult().addRedirURL(desc, url); }
	public ExecuteResult getExecuteResult() { return (ExecuteResult) getSession().getAttribute(Constants.EXECUTE_RESULT_NAME); }
	
	/* 获取基本环境 */
	/*public static Map getParameters() {//封装为Map的request parameters 
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder 
				.getRequestAttributes(); 
		return attrs.getRequest().getParameterMap(); 
	} */
	
	public static HttpSession getSession() { 
		return  getRequest().getSession(); 
	} 

	public static HttpServletRequest getRequest() { 
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder 
		.getRequestAttributes(); 
		return attrs.getRequest(); 
	} 
	public static HttpServletResponse getResponse() {
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder 
				.getRequestAttributes(); 
		return attrs.getResponse();
	}
	public static ServletContext getServletContext(){
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder 
				.getRequestAttributes(); 
		return attrs.getRequest().getServletContext();
	}
	/* 分页信息 */
	protected int pageNum = 1;
	protected int pageSize = Constants.DEFAULT_PAGE_SIZE;
	public int getPageNum() { return pageNum; }
	public void setPageNum(int pageNum) { this.pageNum = pageNum; }
	public int getPageSize() { return pageSize; }
	public void setPageSize(int pageSize) { this.pageSize = pageSize; }	
	
   /* public static String getQueryStringWithoutPageNum() {
		Map m = getParameters();
		m.remove("pageNum");
		return QueryUtil.getQueryString(m);
	}*/
    
    /*public static String getFullUrlWithoutPageNum() {
    	if(getRequest()!=null){
    		return getRequest().getServletPath() + "?"
				+ getQueryStringWithoutPageNum();
    	}
    	else{
    		return null;
    	}
	}*/

    /* 记录当前页面作为返回地址 */
	public static void setReferUrl() {
		String currUrl = QueryUtil.getRequestURL(getRequest());
		getSession().setAttribute(Constants.REFER_URL, currUrl);
		
	}
	/*获取url*/
	public static String getReferUrl() {
		String referUrl=(String) getSession().getAttribute(Constants.REFER_URL);
		return referUrl==null?"/":referUrl;
	}
	/*获取完整url*/
	public static String getFullReferUrl() {
		return getRequest().getContextPath() + getReferUrl();
	}
	
	/* 工具类方法 */
	public Date getNow() {
		return new Date();
	}
	
}