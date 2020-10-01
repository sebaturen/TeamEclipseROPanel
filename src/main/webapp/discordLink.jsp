<%--
  Created by IntelliJ IDEA.
  User: sebastianturen
  Date: 30/09/2020
  Time: 20:29
  To change this template use File | Settings | File Templates.
--%>
<%@include file="includes/globalObject.jsp" %>
<%
    if (request.getParameter("code") == null) {
        response.sendRedirect("index.jsp");
} else { %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="es">
<head>
    <title>RO Del mal Panel - Discord Login</title>
    <meta property="og:title" content="Discord Login" />
    <%@include file="includes/header.jsp" %>
</head>
<body>
<%@include file="includes/menu.jsp" %>
<div class="container fill">
    <div id="loading" class="justify-content-md-center"><div class="loader"></div></div>
    <%
        user.copy(new User.Builder(request.getParameter("code")).build());
        if (user != null)  {
            if (session.getAttribute("internal_redirect") == null) {
                response.sendRedirect("login.jsp");
            } else {
                String dirRed = (String) session.getAttribute("internal_redirect");
                session.removeAttribute("internal_redirect");
                response.sendRedirect(dirRed);
            }
        } else {
            out.write("ERROR! when try save your information!");
        }
    %>
</div>
<%@include file="includes/footer.jsp" %>
</body>
</html>
<%}%>