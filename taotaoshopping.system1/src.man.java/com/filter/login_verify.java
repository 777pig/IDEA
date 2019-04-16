package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.dao.login_token;
import com.util.ShareVar;
import com.util.propertieyUtil;

/**
 * Servlet Filter implementation class login_verify
 */

public class login_verify implements Filter {

	
	public login_token l;
	
	
    /**
     * Default constructor. 
     */
    public login_verify() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}
	int count=0;
	/**
	 * 用于完成一些初始化工作
	 */
	private void init(HttpSession s) {
		if(count==0)
		{
			s.setAttribute("loginout",propertieyUtil.TAOTAO_SYSTEM1_LOGINOUT); 	
		}
		count++;
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("OK子系统2");
			
		//判断有没有登录
		HttpServletRequest rq=(HttpServletRequest)request;
		HttpServletResponse rs=(HttpServletResponse) response;
		HttpSession session=rq.getSession();
		ServletContext app=rq.getServletContext();
		//传输时传递完整url，存储时用uri
		String servlet_url=rq.getServletPath().toString();
		String url=rq.getRequestURL().toString();
		String id=session.getId();
		Boolean is_login=(Boolean)session.getAttribute("is_login");
		String token=request.getParameter("token");
		
		init(session);
		System.out.println("session "+ id+" 当前访问受保护资源的地址 "+servlet_url);

		
		Recorad_One_session(id, session);
		
		if( is_login!=null&&is_login)		
		 	{
			//在这里进行和httpclient进行tock交互
			chain.doFilter(request, response);
			 System.out.println("已经登录"); 
			 	return ;
		 	}
		if(token!=null&&!token.equals(""))
		{
			//有token  假设是登录过或伪造令牌 需去校验
			boolean real=check_tocken(token);
			if(real) {
				//验证通过创建局部会话和记录会话的session和id用于以后销毁
				System.out.println("令牌验证通过"+id);
				session.setAttribute("is_login",true); 
			   chain.doFilter(request, response);
			   ShareVar.record_session(id, session);
			}else {//这个逻辑可以没有
				System.out.println("令牌造假");
				rs.sendRedirect(propertieyUtil.getVerify_errorUrl());
			}
			return ;
		}
 		//第一次登录走的逻辑
			//去验证中心 检测有没有登录 (是否需要生成令牌)
			verify_check_SSOLogin(rq, rs,id);			
	}
	
	/**
	 * 记录第一次session请求
	 */
	public void  Recorad_One_session(String id,HttpSession session) {
		if(count==1)
		{
			ShareVar.record_session(id, session);
			//纪录 第一次客户端和服务器子系统的会话。
		}
		count++;	
	}
	
	public boolean  check_tocken(String token) {
		return 	l.select_token(token);
	}
	/**
	 *去统一认证中心检查
	 */
	public void verify_check_SSOLogin(HttpServletRequest rq,HttpServletResponse rs,String id)  {
		
		try {
		String url=rq.getRequestURL().toString();
	
		StringBuilder urlt=new StringBuilder(propertieyUtil.getHostUrl());
		urlt.append("?rs_url="+url)
		    .append("&verify_url="+propertieyUtil.getVerifyUrl() )
		    .append("&jesssionId="+id);
		rs.sendRedirect(urlt.toString());
		
		  System.out.println("尝试去中心检测登没登录 jesssionId="+id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化注入的bean
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(fConfig.getServletContext());
		 l=( login_token) context.getBean("login_token");
	}

}
