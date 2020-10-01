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
    <nav class="navbar navbar-expand-lg navbar-light bg-inverse">
        <a class="navbar-brand" href="<%= request.getContextPath() %>/index.jsp">
            <img src="<%= request.getContextPath() %>/assets/img/eclipse_logo.png" height="30" alt="">
        </a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item <%= (currentPath.equals("guild_info.jsp"))? "active":"" %>">
                    <a class="nav-link" href="<%= request.getContextPath() %>/guild_info.jsp"><fmt:message key="label.guilds" /></a>
                </li>
                <li class="nav-item <%= (currentPath.equals("class_list.jsp"))? "active":"" %>">
                    <a class="nav-link" href="<%= request.getContextPath() %>/class_list.jsp"><fmt:message key="label.classes" /></a>
                </li>
                <li class="nav-item <%= (currentPath.equals("woe_breaker.jsp"))? "active":"" %>">
                    <a class="nav-link" href="<%= request.getContextPath() %>/woe_breaker.jsp"><fmt:message key="label.woe_breaker" /></a>
                </li>
                <li class="nav-item <%= (currentPath.equals("woe2_breaker.jsp"))? "active":"" %>">
                    <a class="nav-link" href="<%= request.getContextPath() %>/woe2_breaker.jsp"><fmt:message key="label.woe2_breaker" /></a>
                </li>
            </ul>
            <div class="form-inline my-2 my-lg-0">
                <% // Link Blizzard account
                    String redirectUri = request.getContextPath() +"/login.jsp";
                    if(!user.isLogin()) {
                        redirectUri = "https://discord.com/api/oauth2/authorize?client_id=739522446411169972&redirect_uri=https%3A%2F%2Fpanel.delmal.cl%2FdiscordLink.jsp&response_type=code&scope=identify";
                    }
                %>
                <a href="<%= redirectUri %>">
                    <button class="btn btn-dark" type="button">
                        <c:if test="${user.login}">
                            ${user.nick}
                        </c:if>
                        <c:if test="${!user.login}">
                            <img src="assets/img/icons/Discord-Logo-Color.png" alt="" style="width: 26px;">
                            <fmt:message key="label.discord_login" />
                        </c:if>
                    </button>
                </a>
            </div>
        </div>
    </nav>
</div>