<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<body>
<h2>统一认证中心登录界面</h2>

来自<h2 > ${rs_url}</h2>
 <form id="loginForm" method="post" action="/taotaoshopping-sso/login.do" ">
 	<input type="hidden"  name="jesssionId" value="${jesssionId}" />
	<input class="text" type="text" id="userId" name="uname" "/> <br/>
	<input class="text" type="password" id="password" name="passwd"  "/><br/>
	<input type="submit" value=登录" />
</form>

<c:if test="${ not empty error}">
	<h2>${error}</h2>
</c:if>

</body>
</html>
