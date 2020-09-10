<%--
  Created by IntelliJ IDEA.
  User: Seba
  Date: 03/07/2020
  Time: 22:03
  To change this template use File | Settings | File Templates.
--%>
<% String[] path = (request.getRequestURI()).split("/");
    String currentPath = ""; if (path.length > 0) currentPath = path[path.length-1]; %>
<div id="menu_content" class="fill container">
    <nav class="navbar navbar-expand-lg">
        <a class="navbar-brand" href="<%= request.getContextPath() %>/index.jsp">
            <img src="<%= request.getContextPath() %>/assets/img/eclipse_logo.png" height="30" alt="">
        </a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item <%= (currentPath.equals("index.jsp") || currentPath.equals(""))? "active":"" %>">
                    <a class="nav-link" href="<%= request.getContextPath() %>/index.jsp"><fmt:message key="label.home" /></a>
                </li>
                <li class="nav-item <%= (currentPath.equals("woe_breaker.jsp") || currentPath.equals(""))? "active":"" %>">
                    <a class="nav-link" href="<%= request.getContextPath() %>/woe_breaker.jsp"><fmt:message key="label.woe_breaker" /></a>
                </li>
                <li class="nav-item <%= (currentPath.equals("woe2_breaker.jsp") || currentPath.equals(""))? "active":"" %>">
                    <a class="nav-link" href="<%= request.getContextPath() %>/woe2_breaker.jsp"><fmt:message key="label.woe2_breaker" /></a>
                </li>
            </ul>
            <div class="form-inline my-2 my-lg-0" style="display: none">
                <a href="/login.jsp">
                    <button class="btn btn-outline-success" type="button">
                        <c:if test="${user.login}">
                            <fmt:message key="label.account_info" />
                        </c:if>
                        <c:if test="${!user.login}">
                            <fmt:message key="label.login" />
                        </c:if>
                    </button>
                </a>
            </div>
        </div>
    </nav>
</div>