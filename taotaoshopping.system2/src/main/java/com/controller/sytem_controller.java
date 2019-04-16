package com.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dao.login_token;
import com.util.ShareVar;

@Controller
@RequestMapping("/st")
public class sytem_controller{
	
	@Resource
	public login_token l;
	/**
	 *注销实际操作，
	 */
	@RequestMapping("/inpuse.do")
	public String inpuse(HttpSession session, HttpServletRequest rquest, HttpServletResponse response,
			String jesssionId) {
		System.out.println("退出子系统2"+jesssionId);
		ShareVar.destory_session(jesssionId);
		return "redirect:/loginout.jsp";
	}
}
