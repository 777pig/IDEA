package com.monitor;

import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bean.token;
import com.dao.login_token;
import com.util.http_util;

/**
 * 监听token消息的销毁
 */

@WebListener
public class token_destroy  implements HttpSessionAttributeListener {

	login_token l;
	public token_destroy(){
		System.out.println("监听器#########");
	}
	
	public void attributeAdded(HttpSessionBindingEvent se) {
		HttpSession session=se.getSession();
		String name=se.getName();
		System.out.println("添加"+name);
	}
	public void attributeReplaced(HttpSessionBindingEvent se) {
		
		HttpSession session=se.getSession();
		String name=se.getName();
		System.out.println("销毁"+name);
	}
	
	/**
	 * 并且删除在库中存储的token
	 * 
	 */
		public void attributeRemoved(HttpSessionBindingEvent arg0) {
		
			System.out.println("监听器");
			
			HttpSession session=arg0.getSession();
			String name=arg0.getName();
			l=(login_token)getObjectFromApplication(session.getServletContext(),"login_token");
			System.out.println("删除属性触发"+name);
			String token=arg0.getName();
			String var=(String) session.getAttribute("token_t");
			
		   if(token.equals("token")) {
			   List<com.bean.token> list=l.getAlltoken(var);
			   to_destroy_partloginState(list);
		   }
  		   l.remove_token(var);
		    System.out.println("监听都全局对象的销毁");  
		}

	/** 
     * 通过WebApplicationContextUtils 得到Spring容器的实例。根据bean的名称返回bean的实例。 
     * @param servletContext  ：ServletContext上下文。 
     * @param beanName  :要取得的Spring容器中Bean的名称。 
     * @return 返回Bean的实例。 
     */
	private Object getObjectFromApplication(ServletContext servletContext,String beanName){
			//通过WebApplicationContextUtils 得到Spring容器的实例。  
	ApplicationContext application=WebApplicationContextUtils.getWebApplicationContext(servletContext);
	//返回Bean的实例
	return application.getBean(beanName);
	}

	/**
	 *去销毁bean 
	 */
	public void to_destroy_partloginState( List<com.bean.token> list){
				
		for(token t:list) {
			String ulrt=t.getUrl().replace("file/index.jsp","st/inpuse.do");
			HashMap<String, String>h=new HashMap<String, String>();
			h.put("jesssionId", t.getJsssionId());
			http_util.postMap(ulrt,h);
			
			System.out.println("注销子系统urlt"+ulrt);
		}
	}
}
