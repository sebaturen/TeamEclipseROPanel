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
<html>
    <head>
        <title>WoE Cast Breaker TimeLine</title>
        <meta property="og:title" content="WoE 2 Break Timeline" />
        <%@include file="includes/header.jsp" %>
        <link rel="stylesheet" href="assets/css/woe_breaker.css">
        <script src="assets/js/breaker_show.js"></script>
        <script src="assets/js/woe_breaker.js"></script>
    </head>
    <body>
        <%@include file="includes/menu.jsp" %>
        <div class="dateTimeContent container fill">
            <div id="date_times" class="row"></div>
        </div>
        <div class="infoBreaksContent container fill">
            <div id="loading">
                Loading... :eyes
            </div>
            <div id="break_info" style="padding-top: 48px;">
            </div>
            <div id="break_no_data" style="display: none">
                <fmt:message key="label.woe_breaker_no_data" />
            </div>
        </div>
        <script></script>
        <%@include file="includes/footer.jsp" %>
    </body>
</html>