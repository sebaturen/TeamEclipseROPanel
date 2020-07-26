<%--
  Created by IntelliJ IDEA.
  User: Seba
  Date: 26/07/2020
  Time: 0:19
  To change this template use File | Settings | File Templates.
--%>
<%@include file="includes/globalObject.jsp" %>
<%@ page import ="com.eclipse.panel.viewController.GuildController" %>
<%@ page import ="com.eclipse.panel.gameObject.woe.CastleBreaker" %>
<%@ page import ="com.eclipse.panel.gameObject.woe.Castle" %>
<%@ page import ="java.util.List" %>
<c:if test="${empty param.time}">
    <c:set var="breaks" scope="request" value="${GuildController.getBreakers(\"\")}"/>
</c:if>
<c:if test="${not empty param.time}">
    <c:set var="breaks" scope="request" value="${GuildController.getBreakers(param.time)}"/>
</c:if>
<html>
    <head>
        <title>WoE Cast Breaker TimeLine</title>
        <%@include file="includes/header.jsp" %>
    </head>
    <body>
        <%@include file="includes/menu.jsp" %>
        <div class="content fill">
            ${breaks}
        </div>
        <%@include file="includes/footer.jsp" %>
    </body>
</html>