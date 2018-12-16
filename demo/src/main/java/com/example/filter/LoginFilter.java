package com.example.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter{
	private String redirectUrl = "/html/login.html";
	private String sessionKey = "user";

	/** 
	* 需要排除的页面 
	*/  
	private String excludedPages;  
	  
	private String[] excludedPageArray; 
	
	
	public void init(FilterConfig filterConfig) throws ServletException {
			String url = filterConfig.getInitParameter(redirectUrl);
		    String key = filterConfig.getInitParameter(sessionKey);
		    redirectUrl = url == null? redirectUrl:url;
		    sessionKey = key == null ? sessionKey : key ;
		    
		    excludedPages = filterConfig.getInitParameter("excludedPages");  
		    if (excludedPages != null) {  
		    excludedPageArray = excludedPages.split(",");  
		    }  
		    return;  		     
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException  {
		HttpServletRequest servletRequest = (HttpServletRequest)request;
		HttpServletResponse servletResponse = (HttpServletResponse)response;
		HttpSession session = servletRequest.getSession();
		
		/*//获取用户请求的uri
		

		if(session.getAttribute("admin")==null && path.indexOf("/html/login")==-1) {
			//System.out.println("filter 没通过");
			servletResponse.sendRedirect(servletRequest.getContextPath()+"/html/login.html");
			return;
		}
		else {
			filterChain.doFilter(request, response);
			//System.out.println("filter 通过");
		}*/
		
		String path = servletRequest.getRequestURI();
		
		boolean isExcludedPage = false;  
		for (String page : excludedPageArray) {//判断是否在过滤url之外  
			if(path.indexOf(page)!= -1){  
				isExcludedPage = true;  
				break;
				}  
			}  
		
			if (isExcludedPage) {//在过滤url之外  
				filterChain.doFilter(request, response);  
			} 
			else {
				if( session == null || session.getAttribute(sessionKey) == null && path.indexOf("/html/login")==-1){
					//如果判断是 AJAX 请求,直接设置为session超时
					if( servletRequest.getHeader("x-requested-with") != null && servletRequest.getHeader("x-requested-with").equals("XMLHttpRequest") ) {
						servletResponse.setHeader("sessionstatus", "timeout"); 
						} else {
							servletResponse.sendRedirect( servletRequest.getContextPath() + redirectUrl);
						}
				}else {
					filterChain.doFilter(request, response);
				}   
			}
	}
	
	public void destroy() {
		
	}
	
}
