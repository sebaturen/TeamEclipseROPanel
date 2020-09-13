<%--
  Created by IntelliJ IDEA.
  User: Seba
  Date: 03/07/2020
  Time: 22:06
  To change this template use File | Settings | File Templates.
--%>
<%@include file="includes/globalObject.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="es">
<head>
    <title>${guild.name} - 404</title>
    <meta property="og:title" content="Page not found" />
    <%@include file="includes/header.jsp" %>
</head>
<body>
<%@include file="includes/menu.jsp" %>
<div class="container fill">
    ... <fmt:message key="label.404_error" /> <fmt:message key="label.404_error_msg" />
</div>
<%@include file="includes/footer.jsp" %>
</body>
</html>