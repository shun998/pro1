package com.zs.core.common;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.zs.core.Constants;
import com.zs.core.util.QueryUtil;
import com.zs.model.User;


//登录拦截器，用于阻止未登录用户访问系统
public class LoginInterceptor implements HandlerInterceptor {
//DispatcherServlet进行视图的渲染之后
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
//Controller方法处理完之后
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
//Controller方法处理之前
	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		// TODO Auto-generated method stub
	
		if (arg2 instanceof Anonymous ) {
			return true;
		}
		else{
			User user=(User)CommonBaseAction.getSession().getAttribute(
					Constants.LOGIN_USER);
			if (user != null) {
				
				if(arg0.getServletPath().startsWith("/admin")&&
						user.getUserRank()==0){
					arg0.getRequestDispatcher(Constants.ADMIN_LOGIN_URL).forward(arg0, arg1);
					return false;
				}
				else{
					return true;
				}
			}
		} 
		System.out.println("----logininterceptor:"+arg0.getHeader("Referer")+","+QueryUtil.getRequestURL(arg0));
		System.out.println("========"+arg0.getServletPath());
		System.out.println("========"+QueryUtil.getRequestURL(arg0));
		CommonBaseAction.getSession().setAttribute(Constants.ORIGINAL_URL,
					QueryUtil.getRequestURL(CommonBaseAction.getRequest()));
		String redirUrl=arg0.getContextPath()+QueryUtil.getRequestURL(arg0);
		
		//ExecuteResult er=new ExecuteResult();
		if(arg0.getHeader("x-requested-with")==null){//若客户端请求是传统同步请求
		
			CommonBaseAction.getSession().setAttribute("redirUrl", redirUrl);
			if(arg0.getServletPath().startsWith("/admin")){
				
				arg0.getRequestDispatcher(Constants.ADMIN_LOGIN_URL).forward(arg0, arg1);
			}
			else{			
				arg0.getRequestDispatcher(Constants.LOGIN_URL).forward(arg0, arg1);
			}
		}
		else{//若客户端请求是Ajax异步请求
			/*er.addMessage(Constants.LOGIN_PROMPT);
			er.addRedirURL("关闭窗口", "");
			arg0.setAttribute("execute_result", er);	
			arg0.getRequestDispatcher("/execute_result").forward(arg0, arg1);*/
			
			arg1.setContentType("text/json;charset=UTF-8");
			PrintWriter out=arg1.getWriter();
			out.print("{\"login\":\""+Constants.LOGIN_PROMPT+"\"}");
			out.flush();
			out.close(); 
		}  
		return false;
	}

}
