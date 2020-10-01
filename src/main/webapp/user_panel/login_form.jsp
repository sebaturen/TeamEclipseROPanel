<%--
  Created by IntelliJ IDEA.
  User: Seba
  Date: 01/10/2020
  Time: 0:24
  To change this template use File | Settings | File Templates.
--%>
<%@include file="../includes/globalObject.jsp" %>
<%
    if (! request.getParameter("login_redirect").equals("true")) {
        response.sendRedirect("index.jsp");
    } else if ( user.isLogin() ) { //login is complate and successful
        response.sendRedirect("login.jsp");
    }
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
        <% // "redirectUri" is generate in menu.jsp %>
        <a href="<%= redirectUri %>">
            <button type="button" class="btn btn-primary">
                <img src="assets/img/icons/Discord-Logo-White.png" alt="" style="width: 26px;">
                <fmt:message key="label.discord_login" />
            </button>
        </a><br><br>
    </div>
</div>
<%@include file="../includes/footer.jsp" %>
</body>
</html>
<%%>