<%--
  Created by IntelliJ IDEA.
  User: Seba
  Date: 01/10/2020
  Time: 0:24
  To change this template use File | Settings | File Templates.
--%>
<%@include file="../includes/globalObject.jsp" %>
<%
    if (    request.getParameter("login_redirect") == null ||
            !request.getParameter("login_redirect").equals("true") ||
            !user.isLogin()
    ) {
        response.sendRedirect("../login.jsp");
    } else {
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="es">
<head>
    <title>RO Del mal Panel - <fmt:message key="label.login" /></title>
    <%@include file="../includes/header.jsp" %>
</head>
<body>
<%@include file="../includes/menu.jsp" %>
<div class="container fill">
    <div class="row justify-content-md-center">
        * Page in construction...
        <% System.out.println(user); %>
    </div>
</div>
<%@include file="../includes/footer.jsp" %>
</body>
</html>
<%}%>