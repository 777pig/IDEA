package com.controller;

import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bean.token;
import com.dao.login_token;

@Controller
public class sso_controller{
	
//	@Value("#{}")
//	public String tex;
	
	@Resource
	public login_token l;

	@RequestMapping("/text.do")
	public String Controller(HttpSession session, HttpServletRequest rquest, HttpServletResponse response) {
		session.setAttribute("sdf","sdf");
		session.removeAttribute("sdf");
		return null;
	}
	/**
	 * 登录成功后根据表单域的地址 回到页面（并带会tocken ）
	 */
	@RequestMapping("/login.do")
	public String login(
		HttpSession session ,HttpServletRequest rquest,Model mode,
		String uname,String passwd,String jesssionId
					   ){
		
		String rs_url=(String)session.getAttribute("rs_url");
		String verify_url=(String)session.getAttribute("verify_url");
		
		System.out.println("登录校验模块"+jesssionId);
		//成功与否
		if(uname.equals("root")&&passwd.equals("123"))
		{
			String token=UUID.randomUUID().toString();
			System.out.println("登录成功  随机生成tocken"+token);
			//集合存储 
			session.setAttribute("token", token);
			session.setAttribute("token_t", token);
			
			token y=new token(rs_url,token);
			y.setJsssionId(jesssionId);
			l.insert_token(y);
			mode.addAttribute("token", token);
//			rs_url= "redirect:"+verify_url+"?token="+token+"&rs_url="+rs_url;
			rs_url= "redirect:"+rs_url;
		}else {
			System.out.println("登录失败");
			session.setAttribute("error", "账户或用户名错误");	
			rs_url="forward:/index.jsp";
		}
		
		//成功后 并附加上tocken,并把token放到全局会话session中， 
		//失败 返回登录页面

				return rs_url;
	}
	/**判断有没有登录
	 * #rs_url 访问的资源页面
	 * #verify_url 子系统的统一验证页面
	 */
	@RequestMapping("/islogin.do")
	public String islogin(
		HttpSession session ,HttpServletRequest rquest,Model mode,
		String rs_url,String verify_url ,String jesssionId
						  ){
		 session.setAttribute("rs_url",rs_url);
		 session.setAttribute("verify_url", verify_url);
		 session.setAttribute("jesssionId", jesssionId);
		 
		 System.out.println("传输 "+jesssionId);
			String token=(String)session.getAttribute("token");
			
				if(token!=null)
				{//让它去创建局部会话 返回这个令牌 ，并且把令牌对应的值 md5加密后传输回去
				com.bean.token t=new token(rs_url,token);
				t.setJsssionId(jesssionId);
					
				l.insert_token(t);
				
				System.out.println("檢測到已存在token 返回去生成局部token "+verify_url);
				 //有全局会话，有没有必要创建这么多局部会话
				mode.addAttribute("token", token);
				return "redirect:"+rs_url;
			}else {
				//没有全局会话 保存这个全局会话
				
				System.out.println("没登录的情况下返回到这个地址"+rs_url);
				mode.addAttribute("jesssionId",jesssionId);
				return "index";
			}
	}	
	/**
	 * 全局会话的注销功能，并且搭配一个监听器来销毁对应的局部会话
	 */
	@RequestMapping("/loginout.do")
	public String loginOut(
		HttpSession session ,
		HttpServletRequest rquest,
		HttpServletResponse response
	
		//销毁局部会话，登录状态
					) {
		System.out.println("到达sso系统，注销全局请求");
		session.removeAttribute("token");
		return "loginout";
	}
	
}
