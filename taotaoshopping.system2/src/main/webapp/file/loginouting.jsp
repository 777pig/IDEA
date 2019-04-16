<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<body>
<h2>正在退出</h2>
<%
System.out.println("退出子系统2");
session.removeAttribute("is_login");
try{
	session.invalidate();	
}catch(Exception e)
{}
response.sendRedirect("/loginout.jsp");
%>
</body>
</html>
