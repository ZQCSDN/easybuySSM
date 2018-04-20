package com.easybuy.servlet.web;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 公共的servlet抽象类
 * @author lrg
 */

public abstract class AbstractServlet extends HttpServlet {

	public abstract Class getServletClass();
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String action=request.getParameter("action");
		Method method=null;
		Object result=null;
		
		if(action==null){
			//action为空，返回前台首页
			result=execute(request,response);
		}else{
			//action不为空，则拿到要执行的方法
			try {
				//action指的是要拿到的方法的方法名，request.class与response.class都是那个方法的参数
				method=getServletClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
				//获得要去的页面的字符串
				result=method.invoke(this, request,response);
				//执行去某个页面的方法
				toView(request,response,result);
			} catch (Exception e) {
				// TODO 所有的异常，应该给用户一个良好的提示
				e.printStackTrace();
			} 
		}
		

	}
	
	
	protected void toView(HttpServletRequest request,
		HttpServletResponse response,Object result) throws IOException, ServletException{
		
		if(result!=null){
			String viewName=result.toString()+".jsp";
			request.getRequestDispatcher(viewName).forward(request, response);
		}
		
		
	}

	public Object execute(HttpServletRequest request, HttpServletResponse response ){
		return "pre/index";
	}
	
	
}

















